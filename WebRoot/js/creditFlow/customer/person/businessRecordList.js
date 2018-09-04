function businessRecordListWin(cardnumber){
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
   	 	method:'post',   
    	params:{perosnName:cardnumber},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var personIdValue = obj.data.id;
        	showBusinessRecordWin(personIdValue);
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
	function showBusinessRecordWin(personId){
		var jStoreBusinessRecord = new Ext.data.JsonStore( {
			url : __ctxPath+'/credit/customer/person/findBusinessRecord.do',
			totalProperty : 'totalProperty',
			root : 'topics',
			fields : [ {name : 'projectId'}, {name : 'projectNumber'}, {name : 'operationType'}, {name:'projectMoney'}, {name:'dateMode'}, {name:''}, {name:''}, {name:''} ],
			baseParams :{personId : personId }
		});
		jStoreBusinessRecord.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		var button_see = new Ext.Button({
			text : '查看',
			tooltip : '查看与本担保公司的业务记录信息',
			iconCls : 'seeIcon',
			scope : this,
			handler : function() {
				seeBusinessRecord(gPanelBusinessRecord);
			}
		});
		var cModelBusinessRecord = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer( {
				header : '序号',
				width : 35
			}),{
				header : "项目编号",
				width : 130,
				sortable : true,
				dataIndex : 'projectNumber'
			},{
				header : "业务品种",
				width : 150,
				sortable : true,
				dataIndex : 'operationType'
			} ,
			{
				header : "项目金额(万元)",
				width : 120,
				sortable : true,
				dataIndex : 'projectMoney'
			}, {
				header : "担保余额(万元)",
				width : 120,
				sortable : true,
				dataIndex : ''
			}, {
				header : "日期模型",
				width : 120,
				sortable : true,
				dataIndex : 'dateMode'
			}
		]);

		var pagingBar = new Ext.PagingToolbar( {
			pageSize : 20,
			store : jStoreBusinessRecord,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});

		var gPanelBusinessRecord = new Ext.grid.GridPanel( {
			id : 'gPanelBusinessRecord',
			store : jStoreBusinessRecord,
			colModel : cModelBusinessRecord,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : myMask,
			height : 387,
			autoWidth : true,
			bbar : pagingBar,
			tbar : [button_see],
			listeners : {
				'rowdblclick' : function(grid,index,e){
					seeBusinessRecord(this);
				}
			}
		});
		var businessRecordWin = new Ext.Window({
			title : '与本贷款公司的业务记录列表',
			width : (screen.width-180)*0.7 - 50,
			height: 420,
			border : false,
			layout : 'fit',
			modal : true,
			constrainHeader : true ,
			collapsible : true, 
			items :[gPanelBusinessRecord]
		}).show();
	}
	var seeBusinessRecord = function(obj){
		var selected = obj.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var id = selected.get('id');
			Ext.Ajax.request({
				url : __ctxPath+'/credit/customer/person/seeBusinessRecord.do',
				method : 'POST',
				success : function(response,request){
					obj = Ext.util.JSON.decode(response.responseText);
					var businessRecordData = obj.data;	
					var seeBusinessRecordPanel = new Ext.form.FormPanel({
						labelAlign : 'right',
						bodyStyle:'padding:3px ; font-size: 14px',
						layout : 'column',
						autoScroll : false ,
						border : true,
						frame : true ,
						width : 488,
						height : 378,
						items : [{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'label',
								fieldLabel : '项目名称',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300">'+businessRecordData.projectName+'</span>'
							}]
						},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'label',
								fieldLabel : '业务品种',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300">'+businessRecordData.operationType+'</span>'
							},{
								xtype : 'label',
								fieldLabel : '担保余额(万)',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300"></span>'
							},{
								xtype : 'label',
								fieldLabel : '贷款起始日期',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300">'+(businessRecordData.startDate==null)? "" : businessRecordData.startDate +'</span>'
							}]
						},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'label',
								fieldLabel : '项目金额(万)',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300">'+businessRecordData.projectMoney+'</span>'
								
							},{
								xtype : 'label',
								fieldLabel : '日期模型',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300">'+businessRecordData.dateMode+'</span>'
							},{
								xtype : 'label',
								fieldLabel : '贷款结束日期',
								html : '<span style="font-size: 14px;font-style: italic;color : #003300">'+(businessRecordData.intentDate==null)? "" : businessRecordData.intentDate +'</span>'
							}]
						}]
					});
					
					var seeBusinessRecordWin = new Ext.Window({
						id : 'seeBusinessRecordWin',
						title: '查看与本担保公司的业务记录信息',
						layout : 'fit',
						width : 500,
						height : 410,
						closable : true,
						collapsible : true,
						resizable : true,
						plain : true,
						border : false,
						autoScroll : true ,
						modal : true,
						buttonAlign: 'right',
						minHeight : 350,
						minWidth : 330,
						bodyStyle:'overflowX:hidden',
					    items :[seeBusinessRecordPanel]
					}).show();
					},
					failure : function(response) {			
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
			});	
		}
	}
}
