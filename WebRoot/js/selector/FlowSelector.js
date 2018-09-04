/**
 * 流程选择器
 */
var FlowSelector = {
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
						header : 'defId',
						dataIndex : 'defId',
						hidden : true
					},{
						header : '流程的名称',
						dataIndex : 'name'
					}, {
						header : '描述',
						dataIndex : 'description'
					}]
		});

		var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/listProDefinition.do',
							params : {
								typeId : this.typeId == null ? 0 : this.typeId
							}
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'defId',
										type : 'int'
									}, 'proType', 'name', 'description',
									'createtime', 'deployId','processName']
						}),
				remoteSort : true
			});
	     store.setDefaultSort('defId', 'desc');

		var gridPanel = new Ext.grid.GridPanel({
					id : 'FlowSelectorGrid',
					width : 400,
					height : 300,
					region : 'center',
					title : '流程列表',
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
						limit : 10
					}
				});
		// --------------------------------end grid
		// panel-------------------------------------

		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'FlowSearchForm',
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
						text : '流程的名称'
					}, {
						xtype : 'textfield',
						name : 'Q_name_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							if (formPanel.getForm().isValid()) {
								formPanel.getForm().submit({
									waitMsg : '正在提交查询',
									url : __ctxPath + '/flow/listProDefinition.do',
									success : function(fp, action) {
										var result = Ext.util.JSON.decode(action.response.responseText);
										gridPanel.getStore().loadData(result);
									}
								});
							}

						}
					}]
		});

		var window = new Ext.Window({
			title : '流程选择',
			iconCls:'menu-flow',
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
							var grid = Ext.getCmp('FlowSelectorGrid');
							var rows = grid.getSelectionModel().getSelections();
							var flowIds = '';
							var flowNos = '';
							var processNames = '';
							for (var i = 0; i < rows.length; i++) {

								if (i > 0) {
									flowIds += ',';
									flowNos += ',';
									processNames += ','
								}

								flowIds += rows[i].data.defId;
								flowNos += rows[i].data.name;
								processNames += rows[i].data.processName;

							}

							if (callback != null) {
								callback.call(this, flowIds, flowNos ,processNames);
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