/**
 * @author YungLocke
 * @class ProInstanceView
 * @extends Ext.Panel
 */
ProInstanceView=Ext.extend(Ext.Panel,{
    searchPanel:null,
    gridPanel:null,
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    ProInstanceView.superclass.constructor.call(this,{
	        layout : 'border',
	        iconCls:'menu-instance',
	        title :  this.flowName+'--实例管理',
	        items : [this.searchPanel, this.gridPanel]
	    });
	},
	initUI:function(){
		 // 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			layout : 'form',
			region : 'north',
			width : '100%',
			height : 38,
			keys : [{
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			}, {
				key : Ext.EventObject.ESC,
				fn : this.reset,
				scope : this
			}],
			items : [{
				xtype : 'container',
				layout : 'column',
				border : false,
				style : 'padding-left:5px; padding-right:5px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				items : [{
					columnWidth : .3,
					layout : 'form',
					xtype : 'container',
					items : [{
						width : '95%',
						fieldLabel : '标题',
						name : 'Q_subject_S_LK',
						xtype : 'textfield'
					}]
				}, {
					columnWidth : .3,
					width : 100,
					layout : 'column',
					xtype : 'container',
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '查询',
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}, {
						text : '重置',
						scope : this,
						iconCls : 'reset',
						handler : this.reset
					}]
				}, {
				    xtype : 'hidden',
				    name : 'Q_proDefinition.defId_L_EQ',
				    value : this.defId
				}, {
				    xtype : 'hidden',
				    name : 'Q_runStatus_SN_EQ',
				    value : 1
				}]
			}]
		});
				
		 this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-refresh',
				text : '刷新',
				xtype : 'button',
				scope : this,
				handler : this.refreshRs
			}, '-', {
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope : this,
				handler : this.detailRsM
			}, '-', {
			    iconCls : 'btn-close',
				text : '结束实例',
				xtype : 'button',
				scope : this,
				handler : this.closeIns
				
			}]
		});
	     this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			url : __ctxPath + '/flow/instanceProcessRun.do',
			baseParams:{'Q_proDefinition.defId_L_EQ':this.defId,
			             'Q_runStatus_SN_EQ':1},//正在运行的实例
			fields : [{
						name : 'runId',
						type : 'int'
					}, 'subject','createtime',
					'defId', 'piId','runStatus'],
			columns : [{
						header : 'runId',
						dataIndex : 'runId',
						hidden : true
					},{
					    header:'标题',
					    dataIndex:'subject'
					},{
					    header:'创建时间',
					    dataIndex:'createtime'
					
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 32,
								fixed:true,
			    				resizable:false,
								actions : [{
											iconCls : 'btn-detail',
											qtip : '查看',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
				// end of columns
		});
			
		
	
	},
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// 刷新记录
	refreshRs : function() {
		this.gridPanel.getStore().reload();
	},
	detailRsM:function(){
	    var selRs = this.gridPanel.getSelectionModel().getSelections();
		if(selRs.length==0){
		   Ext.ux.Toast.msg('操作信息','请选择记录！');
		   return;
		}
		if(selRs.length>1){
		   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
		   return;
		}
		this.detailRs(selRs[0]);
	},
	closeIns:function(){
	   var gridPanel=this.gridPanel;
	   var ids=$getGdSelectedIds(gridPanel,'runId');
	   if (ids.length == 0) {
		   Ext.ux.Toast.msg('操作信息','请选择记录！');
		   return;
	   }
	   var strIds='';
	   for(var i=0;i<ids.length;i++){
	      if(strIds!=''){
	          strIds+=',';
	      }
	      strIds+=ids[i];
	   }
	   Ext.Msg.confirm('信息确认', '您确认要结束所选实例吗？', function(btn) {
			if (btn == 'yes') {
			   Ext.Ajax.request({
			       url:__ctxPath+'/flow/endProcessRun.do',
			       params:{
			          runIds:strIds,
			          operation : 'stop'//add by lu 2012.02.17(结束流程实例的时候更改项目状态)
			       },
			       method:'post',
			       success:function(resp,op){
			       	   var res=Ext.util.JSON.decode(resp.responseText);
			       	   if(res.success){
				           Ext.ux.Toast.msg('信息提示','成功结束实例！');
				           gridPanel.getStore().reload();
			       	   }else{
			       	       Ext.ux.Toast.msg('信息提示','出错，请联系管理员！');
			       	       gridPanel.getStore().reload();
			       	   }
			       },
			       failure:function(){
			           Ext.ux.Toast.msg('信息提示','出错，请联系管理员！');
			           gridPanel.getStore().reload();
			       }
			   
			   });
	   }});
	},
	detailRs:function(record){
	    new ProInstanceDetail({runId:record.data.runId,subject:record.data.subject}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-detail' :
				this.detailRs.call(this, record);
				break;
			default :
				break;
		}
	}

});