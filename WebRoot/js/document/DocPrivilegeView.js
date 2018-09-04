Ext.ns('DocPrivilegeView');
/**
 * ������������������������������������������������������������列表
 */

var DocPrivilegeView = function() {
}

DocPrivilegeView.prototype.getView = function() {
	return new Ext.Panel({
		id : 'DocPrivilegeView',
		title : '权限列表',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'DocPrivilegeSearchForm',
			region:'north',
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
					}
					, {
						text : '名称'
					}, {
						xtype : 'textfield',
						name : 'Q_udrName_S_LK'
					}, {
						text : '属性'
					}, {
						xtype : 'combo',
						anchor : '95%',
						hiddenName : 'Q_flag_SN_EQ',
						id : 'title',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '用户'], ['2', '部门'], ['3', '角色']]

					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('DocPrivilegeSearchForm');
							var gridPanel = Ext.getCmp('DocPrivilegeGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}, {
						xtype : 'button',
						iconCls : 'btn-reseted',
						text : '重置',
						handler : function() {
							Ext.getCmp('DocPrivilegeSearchForm').getForm()
									.reset();
						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
DocPrivilegeView.prototype.setup = function() {
	return this.grid();
};

DocPrivilegeView.prototype.setFolderId = function(folderId) {
	this.folderId = folderId;
	DocPrivilegeView.folderId = folderId;

};

DocPrivilegeView.prototype.getFolderId = function() {
	return this.folderId;
};

/**
 * 建立DataGrid
 */
DocPrivilegeView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();

	var onMouseDown = function(e, t) {
		if (t.className && t.className.indexOf('x-grid3-cc-' + this.id) != -1) {
			e.stopEvent();
			var index = this.grid.getView().findRowIndex(t);
			var cindex = this.grid.getView().findCellIndex(t);
			var record = this.grid.store.getAt(index);
			var field = this.grid.colModel.getDataIndex(cindex);
			if (isGranted('_DocPrivilegeEdit')) {
				if (field != 'rightR') {

					var e = {
						grid : this.grid,
						record : record,
						field : field,
						originalValue : record.data[this.dataIndex],
						value : !record.data[this.dataIndex],
						row : index,
						column : cindex,
						cancel : false
					};
					if (this.grid.fireEvent("validateedit", e) !== false
							&& !e.cancel) {
						delete e.cancel;
						record
								.set(this.dataIndex,
										!record.data[this.dataIndex]);
						this.grid.fireEvent("afteredit", e);
					}

				} else {
					Ext.ux.Toast.msg("信息提示", "可读为基本权限！");
				}
			} else {
				Ext.ux.Toast.msg("信息提示", "你没有修改的权限！");
			}
		}

	}

	var checkColumnR = new Ext.grid.CheckColumn({
				id : 'read',
				header : '可读',
				dataIndex : 'rightR',
				width : 55,
				onMouseDown : onMouseDown
			});
	var checkColumnM = new Ext.grid.CheckColumn({
				header : '可修改',
				dataIndex : 'rightU',
				width : 55,
				onMouseDown : onMouseDown
			});
	var checkColumnD = new Ext.grid.CheckColumn({
				header : '可删除',
				dataIndex : 'rightD',
				width : 55,
				onMouseDown : onMouseDown
			});

	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'privilegeId',
					dataIndex : 'privilegeId',
					hidden : true
				}, {
					header : '名称',
					dataIndex : 'udrName'
				}, {
					header : '文件夹',
					dataIndex : 'folderName'
				}, {
					header : '属性',
					dataIndex : 'flag',
					renderer : function(value, metadata, record) {
						if (value == 1) {
							return '<img title="员工" src="' + __ctxPath
									+ '/images/flag/user.jpg"/>';
						}
						if (value == 2) {
							return '<img title="部门" src="' + __ctxPath
									+ '/images/flag/department.jpg"/>';
						}
						if (value == 3) {
							return '<img title="角色" src="' + __ctxPath
									+ '/images/flag/role.jpg"/>';
						}
					}
				}, checkColumnR, checkColumnM, checkColumnD, {
					header : '管理',
					dataIndex : 'privilegeId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.privilegeId;
						var str = '';
						if (isGranted('_DocPrivilegeDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="DocPrivilegeView.remove('
									+ editId + ')">&nbsp;</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.EditorGridPanel({
				id : 'DocPrivilegeGrid',
				tbar : this.topbar(this),
				trackMouseOver : true,
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				cm : cm,
				sm : sm,
				plugins : [checkColumnR, checkColumnM, checkColumnD],
				clicksToEdit : 1,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('afteredit', function(e) {
				Ext.Ajax.request({
							url : __ctxPath + '/document/changeDocPrivilege.do',
							params : {
								field : e.field,
								fieldValue : e.value,
								privilegeId : e.record.data.privilegeId
							},
							success : function() {

							},
							failure : function() {
								Ext.Msg.show({
											title : '错误提示',
											msg : '修改数据发生错误,操作将被回滚!',
											fn : function() {
												e.record.set(e.field,
														e.originalValue);
											},
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.ERROR
										});

							}

						});

			});
	return grid;

};

/**
 * 初始化数据
 */
DocPrivilegeView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/document/listDocPrivilege.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'privilegeId',
										type : 'int'
									}

									,
									{
										name : 'folderName',
										mapping : 'folderName'
									}
									// ,'docId'
									, 'rightR', 'rightU', 'rightD', 'udrId',
									'udrName', 'flag']
						}),
				remoteSort : true
			});
	store.setDefaultSort('privilegeId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
DocPrivilegeView.prototype.topbar = function(docFolderObject) {
	var toolbar = new Ext.Toolbar({
				id : 'DocPrivilegeFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_DocPrivilegeAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加文件夹权限',
					handler : function() {
						var forlderId = docFolderObject.getFolderId();
						if (forlderId != null && forlderId > 0) {
							new DocFolderSharedForm(null, forlderId);
						} else {
							Ext.ux.Toast.msg('提示', '请选择文件夹!');
						}
					}
				}));
	}
	if (isGranted('_DocPrivilegeDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除文件夹权限',
					handler : function() {

						var grid = Ext.getCmp("DocPrivilegeGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.privilegeId);
						}

						DocPrivilegeView.remove(ids);
					}
				}));
	}
	return toolbar;

};

/**
 * 删除单个记录
 */
DocPrivilegeView.remove = function(id) {
	var grid = Ext.getCmp("DocPrivilegeGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/document/multiDelDocPrivilege.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 
 */
DocPrivilegeView.edit = function(id) {
	new DocPrivilegeForm(id);
}
