/**
 * 项目选择器
 */
var ProjectSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, isSingle) {
		
		// ---------------------------------start grid
		// panel--------------------------------
		var sm = null;
		if (isSingle) {
			var sm = new Ext.grid.CheckboxSelectionModel({
						singleSelect : true
					});
		} else {
			sm = new Ext.grid.CheckboxSelectionModel();
		}
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'projectId',
						dataIndex : 'projectId',
						hidden : true
					},{
						header : '项目编号',
						dataIndex : 'projectNo',
						width : 60
					}, {
						header : "项目名称",
						dataIndex : 'projectName',
						width : 60
					}, {
						header : '所属客户',
						dataIndex : 'customer',
						width : 60,
						renderer:function(value){
							return value.customerName;
						}
					},{
						header : '联系人',
						dataIndex : 'fullname',
						width : 60
					},{
						header : '项目描述',
						dataIndex : 'reqDesc',
						hidden:true
					}]
		});

		var store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/customer/listProject.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'projectId',
								fields : [{
											name : 'projectId',
											type : 'int'
										}, 'projectNo','projectName',
										'customer','fullname','reqDesc']
							}),
					remoteSort : true
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'ProjectSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '项目列表',
					store : store,
					shim : true,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					// paging bar on the bottom
					bbar : new HT.PagingBar({store : store})
				});

		store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'ProjectForm',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '查询条件:'
					},{
						text : '项目编号'
					},{
						xtype : 'textfield',
						width : 80,
						name : 'Q_projectNo_S_LK'
					},{
						text : '项目名称'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_projectName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ProjectForm');
							var grid = Ext.getCmp('ProjectSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath+ '/customer/listProject.do',
									success : function(formPanel, action) {
										var result = Ext.util.JSON.decode(action.response.responseText);
										grid.getStore().loadData(result);
									}
								});
							}

						}
					}]
		});

		var window = new Ext.Window({
			title : '项目选择器',
			iconCls:'menu-project',
			width : 430,
			height : 380,
			layout : 'border',
			border : false,
			items : [formPanel, gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('ProjectSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var projectId = '';
							var projectName = '';
							var projectNo = ''; 
//							var customerName = '';
//							var fullname = '';
//							var reqDesc = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									projectId += ',';
									projectName += ',';
									projectNo +=','; 
//									customerName += ',';
//									fullname += ',';
//									reqDesc += ',';
								}

								projectId += rows[i].data.projectId;
								projectName += rows[i].data.projectName;
								projectNo += rows[i].data.projectNo;
//								customerName += rows[i].data.customer.customerName;
//								fullname += rows[i].data.fullname;
//								reqDesc += rows[i].data.reqDesc;

							}

							if (callback != null) {
								callback.call(this, projectId, projectName,projectNo
								//customerName,fullname,reqDesc
								);
							}
							window.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;
	}

};