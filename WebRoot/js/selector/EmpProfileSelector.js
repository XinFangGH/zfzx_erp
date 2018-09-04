/**
 * @author lyy
 * @createtime 2010-01-21
 * @class EmpProfileSelector
 * @extends Ext.Window
 * @description EmpProfileSelector
 * @company 智维软件
 */
/**
 * 单一档案选择器
 */
var EmpProfileSelector = {
	/**
	 * @param callback
	 *            回调函数
	 */
	getView : function(callback) {
		// ---------------------------------start grid
		// panel--------------------------------
		var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true
				});
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'profileId',
								dataIndex : 'profileId',
								hidden : true
							}, {
								header : '档案编号',
								dataIndex : 'profileNo'
							}, {
								header : '员工姓名',
								dataIndex : 'fullname'
							}]
				});

		var store = new Ext.data.JsonStore({
					url : __ctxPath + "/hrm/listEmpProfile.do",
					root : 'result',
					baseParams : {
						'Q_approvalStatus_SN_EQ' : 1
					},
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'profileId',
								type : 'int'
							}, 'profileNo', 'fullname', 'jobId', 'position',
							'standardMiNo', 'standardMoney', 'standardName',
							'standardId', 'approvalStatus', 'depName', 'depId',
							'delFlag', 'userId', 'idCard']
				});

		var gridPanel = new Ext.grid.GridPanel({
					id : 'EmpProfileSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '档案列表',
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

		store.setDefaultSort('profileId', 'desc');
		// 加载数据
		store.load({
					params : {
						start : 0,
						limit : 10
						// 'Q_approvalStatus_SN_EQ' : 0//通过审核
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'EmpProfileForm',
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
						text : '请输入查询条件:'
					}, {
						text : '姓名'
					}, {
						xtype : 'textfield',
						name : 'Q_fullname_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('EmpProfileForm');
							var grid = Ext.getCmp('EmpProfileSelectorGrid');
							if (searchPanel.getForm().isValid()) {
								searchPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath + '/hrm/listEmpProfile.do',
									params : {
										start : 0,
										limit : 10,
										'Q_approvalStatus_SN_EQ' : 1
										// 通过审核
									},
									method : 'post',
									success : function(formPanel, action) {
										var result = Ext.util.JSON
												.decode(action.response.responseText);
										var gridStore = grid.getStore();
										gridStore.loadData(result);
										gridStore.setDefaultSort('profileId',
												'desc');
									}
								});
							}

						}
					}]
		});

		var window = new Ext.Window({
			title : '档案选择',
			iconCls : 'menu-profile',
			width : 630,
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
							var grid = Ext.getCmp('EmpProfileSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var array = Array();
							array.push(rows[0].data.profileId);// 0
							array.push(rows[0].data.profileNo);// 1
							array.push(rows[0].data.fullname);// 2
							array.push(rows[0].data.jobId);// 3
							array.push(rows[0].data.position);// 4
							array.push(rows[0].data.depId);// 5
							array.push(rows[0].data.depName);// 6
							array.push(rows[0].data.standardMiNo);// 7
							array.push(rows[0].data.standardName);// 8
							array.push(rows[0].data.standardMoney);// 9
							array.push(rows[0].data.standardId);// 10
							array.push(rows[0].data.idCard);// 11
							array.push(rows[0].data.userId);// 12
							if (callback != null) {
								callback.call(this, array);
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