Ext.ns('PersonalWorkPlanView');
PersonalWorkPlanView = Ext.extend(Ext.Panel,{
    searchFormPanel : null,
    gridPanel : null,
	constructor : function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   PersonalWorkPlanView.superclass.constructor.call(this,{
	        id : 'PersonalWorkPlanView',
			title : '我的计划列表',
			iconCls:'menu-myplan',
			region:'center',
			layout:'border',
			keys : {
		   		key : Ext.EventObject.ENTER,
		   		scope : this,
		   		fn : this.search
	   		},
	   		items : [this.leftPanel,this.centerPanel]
	   });
	},
	initUI : function(){
		this.leftPanel = new Ext.Panel({
			 	region:'west',
			 	title:'分类管理',
			 	layout:'anchor',
			 	collapsible : true,
				split : true,
				width : 200,
			 	items:[{  
			             xtype:'treePanelEditor',
			             id:'PersonalPlanTypeTree',
			             split : true, 
			             border:false,
			             height:380,
			             autoScroll:true,
			             url:__ctxPath+'/system/pwTreeGlobalType.do?catKey=PWP',
			             onclick:function(node){
			             	var type=node.id;
			             	var grid=Ext.getCmp('PersonalWorkPlanGrid');
			             	if(grid!=null){
			             		var store=grid.getStore();
			             		store.url = __ctxPath + '/task/personalWorkPlan.do';
			             		if(type!=0){
			             			store.baseParams={'Q_globalType.proTypeId_L_EQ':type};
			             	    }else{
			             	        store.baseParams={'Q_globalType.proTypeId_L_EQ':null};
			             	    }
								store.reload();
			             	}
			             },
			             contextMenuItems:[  
			                         {  
			                             text : '新建分类',  
			                             scope : this,  
			                             iconCls:'btn-add',  
			                             handler : function(){
			                             	var globalTypeTree=Ext.getCmp('PersonalPlanTypeTree');
			                             	var parentId=globalTypeTree.selectedNode.id;

			                             	var globalTypeForm=new PersonalPlanTypeForm({
			                             		parentId:parentId,
			                             		catKey:'PWP',
			                             		callback:function(){
			                             			Ext.getCmp('PersonalPlanTypeTree').root.reload();
			                             			Ext.getCmp('PersonalWorkPlanGrid').getStore().reload();
			                             		}
			                             	});
			                             	globalTypeForm.show();
			                               
			                             }  
			                         },{
			                         	text:'修改分类',
			                         	scope:this,
			                         	iconCls:'btn-edit',
			                         	handler:function(){
			                         		var globalTypeTree=Ext.getCmp('PersonalPlanTypeTree');
			                             	var proTypeId=globalTypeTree.selectedNode.id;
			                             	var globalTypeForm = new PersonalPlanTypeForm({
			                             		proTypeId:proTypeId,
			                             		callback:function(){
			                             			Ext.getCmp('PersonalPlanTypeTree').root.reload();
			                             			Ext.getCmp('PersonalWorkPlanGrid').getStore().reload();
			                             		}
			                             	});
			                             	globalTypeForm.show();
			                         	}
			                         },{
			                            text:'删除分类',
			                            scope:this,
			                            iconCls:'btn-del',
			                            handler:function(){
			                               var globalTypeTree=Ext.getCmp('PersonalPlanTypeTree');
			                               var proTypeId=globalTypeTree.selectedNode.id;
			                               Ext.Msg.confirm('信息确认', '您确认要删除所选分类吗？', function(btn) {
												if (btn == 'yes') {
													Ext.Ajax.request({
																url : __ctxPath + '/system/multiDelGlobalType.do',
																params : {
																	ids : proTypeId
																},
																method : 'POST',
																success : function(response, options) {
																	Ext.ux.Toast.msg('操作信息','成功删除该分类！');
																	Ext.getCmp('PersonalWorkPlanGrid').getStore().reload();
																	Ext.getCmp('PersonalPlanTypeTree').root.reload();
																},
																failure : function(response, options) {
																	Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
																}
															});
												}
											});
			                            }
			                         }
			                         ]
					 }
			 	]
			 }
			 
		 );
		
		this.searchFormPanel=new Ext.FormPanel({
			height : 40,
			region:'north',
			frame : false,border:false,
			id : 'PersonalWorkPlanSearchForm',
			layout : 'hbox',
			layoutConfig: {
                padding:'5',
                align:'middle'
            },
			defaults : {
				xtype : 'label',
				margins:{top:0, right:4, bottom:4, left:4}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '计划名称'
					}, {
						xtype : 'textfield',
						name : 'Q_planName_S_LK'
					},{
						text : '计划类型'
					}, {
						xtype : 'textfield',
						hiddenName : 'Q_globalType.proTypeId_L_EQ',
						xtype : 'combo',
						editable : false,
						triggerAction : 'all',
						displayField : 'name',
						valueField : 'id',
						mode : 'local',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							baseParams:{catKey:'PWP'},
							url : __ctxPath + '/system/comboGlobalType.do',
							fields : ['id', 'name']
						})
					},{
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						scope : this,
						handler : this.search
					},{
						iconCls : 'reset',
						 xtype : 'button',
						 text : '重置',
						 scope : this,
						 handler : function(){
							 var searchPanel = this.searchFormPanel;
							 searchPanel.getForm().reset();
						 }
					}]
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'planId',
						dataIndex : 'planId',
						hidden : true
					}, {
						header : '标识',
						dataIndex : 'icon',
						renderer:function(value){
						   return '<div class="'+value+'"></div>';
						}
					}, {
						header : '计划名称',
						dataIndex : 'planName'
					}, {
						header : '开始日期',
						dataIndex : 'startTime'
					}, {
						header : '结束日期',
						dataIndex : 'endTime'
					}, 
						{
						header : '计划类型',
						dataIndex : 'globalType',
						renderer:function(obj){
						    if(obj!=null){
						    
						    	return obj.typeName;
						    }
						}
					},
					{
						header : '创建人',
						dataIndex : 'userName'
					}, {
						header : '负责人',
						dataIndex : 'principal'
					},{
					   header:'是否生效',
					   dataIndex:'startTime',
					   renderer:function(value, metadata, record, rowIndex,
									colIndex){
					      var startTime=new Date(getDateFromFormat(value, "yyyy-MM-dd H:mm:ss"));				
					     var endTime=new Date(getDateFromFormat(record.data.endTime, "yyyy-MM-dd H:mm:ss"));
					      var today=new Date();
					      if(startTime>today){
					        return '<a style="color:blue;">未生效</a>';
					      }else if(startTime<=today&&endTime>=today){
					        return '<a style="color:green;">已生效</a>'; 
					      }else if(endTime<today){
					       return '<a style="color:red;">已失效</a>';
					      }
					   }
					}],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.store= new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/task/personalWorkPlan.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'planId',
										type : 'int'
									}

									, 'planName', 'planContent', 'startTime',
									'endTime','globalType',
										{
									  name:'userName',
									  mapping:'appUser.fullname'
									},
									'principal',
									'note', 'status', 'isPersonal', 'icon']
						}),
				remoteSort : true
			});
	    this.store.setDefaultSort('planId', 'desc');
		this.store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
			
		this.topbar=new Ext.Toolbar({
		   items:[{
		       xtype:'button',
		       text:'添加个人计划',
		       iconCls:'btn-add',
		       handler:this.createRecord
		   },{
		       xtype:'button',
		       text:'编辑个人计划',
		       iconCls:'btn-edit',
		       scope:this,
		       handler:this.editRecord
		   },{
		       xtype:'button',
		       text:'删除',
		       iconCls:'btn-del',
		       scope:this,
		       handler:this.delRecord
		   }]
		});
			
		this.gridPanel=new Ext.grid.GridPanel({
			    region : 'center',
				id : 'PersonalWorkPlanGrid',
				tbar : this.topbar,
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
//				autoHeight : true,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : this.store})
			});
			
	  this.gridPanel.addListener('rowdblclick',this.rowdblclickaction);
			
	   this.centerPanel=new Ext.Panel({
	        layout:'border',
	        region:'center',
	        items:[this.searchFormPanel,this.gridPanel]
	   });
	},
	search:function() {
		var searchPanel = this.searchFormPanel;
		var gridPanel =this.gridPanel;
		if (searchPanel.getForm().isValid()) {
			$search({
				searchPanel :searchPanel,
				gridPanel : gridPanel
			});
		}

	},
	rowdblclickaction:function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			        new PersonalWorkPlanForm({planId:rec.data.planId}).show();
				});
	},
	createRecord:function(){
	   new PersonalWorkPlanForm().show();
	},
	editRecord:function(){
	    var selRs = this.gridPanel.getSelectionModel().getSelections();
	    if(selRs.length==0){
	        Ext.ux.Toast.msg("操作信息", "请选择要编辑的记录");
	        return;
	    }else if(selRs.length>1){
	        Ext.ux.Toast.msg("操作信息", "只可以编辑一条记录");
	        return;
	    }
	    var rec=selRs[0];
	    new PersonalWorkPlanForm({planId:rec.data.planId}).show();
	},
	delRecord:function(){
	    $delGridRs({
                url:__ctxPath + '/task/multiDelWorkPlan.do',
                grid:this.gridPanel,
                idName:'planId'
         });
	}
	
});
