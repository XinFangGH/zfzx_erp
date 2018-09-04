Ext.ns('FileAttachView');
/**
 * 附件信息列表
 */
var FileAttachView = function() {
	return new Ext.Panel({
		id : 'FileAttachView',
		title : '附件列表',
		iconCls : 'menu-attachment',
		layout:'border',
		autoScroll : true,
		items : [new Ext.FormPanel({
			region:'north',
			height : 40,
			frame : false,border:false,
			id : 'FileAttachSearchForm',
			layout : 'form',
			keys : [{
				key : Ext.EventObject.ESC,
				fn : FileAttachView.reset,
				scope : this
			}, {
				key : Ext.EventObject.ENTER,
				fn : FileAttachView.search,
				scope : this
			}],
			layoutConfig: {
		        padding:'5',
		        align:'middle'
		    },
			items : [{
				xtype : 'container',
				layout : 'column',
				border : false,
				style : 'margin-top:8px;',
				defaults : {
					xtype : 'label',
					border : false,
					height : 25
				},
				items : [	{
					style : 'margin:5px 5px 5px 5px;',
					text : '请输入查询条件:'
				},{
					style : 'margin:5px 5px 5px 5px;',
					text : '文件名'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_fileName_S_LK'
				}, {
					style : 'margin:5px 5px 5px 5px;',
					text : '创建时间'
				}, {
					columnWidth : .2,
					xtype : 'datefield',
					name : 'Q_createtime_D_GE',
					editable : false,
					format : 'Y-m-d'
				}, {
					style : 'margin:5px 5px 5px 5px;',
					text : '扩展名'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_ext_S_LK'
				}, {
					style : 'margin: 5px 5px 5px 5px;',
					text : '上传者'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_creator_S_LK'
				}, {
					style : 'margin-left:5px;',
					xtype : 'button',
					text : '查询',
					iconCls : 'search',
					handler : function(){
						FileAttachView.search();
					}
				}, {
					xtype : 'button',
					text : '重置',
					iconCls : 'reset',
					handler : function(){
						FileAttachView.reset();
					}
				}]
			}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
FileAttachView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
FileAttachView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'fileId',
					dataIndex : 'fileId',
					hidden : true
				}, {
					header : '文件名',
					dataIndex : 'fileName'
				}, {
					header : '文件路径',
					dataIndex : 'filePath'
				}, {
					header : '创建时间',
					dataIndex : 'createtime'
				}, {
					header : '扩展名',
					dataIndex : 'ext'
				}, {
					header : '附件类型',
					dataIndex : 'fileType'
				}, {
					header : '类型名称',
					sortable : false,
					dataIndex : 'fileTypeName'
				}, {
					header : '说明',
					dataIndex : 'note'
				}, {
					header : '上传者',
					dataIndex : 'creator'
				}, {
					header : '状态',
					dataIndex : 'delFlag',
					renderer: function(value){
						if(value){
							if(value ==1){
								return '<font color="red">已删除</font>';
							}else{
								return '<font color="green">可用</font>';
							}
						}else{
							return '';
						}
					}
				},{
					header : '管理',
					dataIndex : 'fileId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.fileId;
						var str = '';
						if (isGranted('_FileAttachEdit')) {
							str += '&nbsp;<button title="查看" value=" " class="btn-detail" onclick="FileAttachDetail.show('
									+ editId + ')"></button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : true,
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
	var grid = new Ext.grid.GridPanel({
				id : 'FileAttachGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if (isGranted('_FileAttachEdit')) {
								FileAttachDetail.show(rec.data.fileId);
							}
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
FileAttachView.prototype.store = function() {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : __ctxPath + '/system/listAllFileAttach.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'result',
			totalProperty : 'totalCounts',
			id : 'id',
			fields : [{
				name : 'fileId',
				type : 'int'
			} , 'fileName', 'filePath', 'createtime','delFlag',
			'ext', 'type', 'note', 'creator','fileType','fileTypeName']
		}),
		remoteSort : true
	});
	store.setDefaultSort('fileId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
FileAttachView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
		id : 'FileAttachFootBar',
		height : 30,
		bodyStyle : 'text-align:left',
		items : [{
				text : '删除',
				iconCls : 'btn-del',
				hidden : !isGranted('_FileAttachDel'),
				handler : FileAttachView.removeAll,
				scope : this
			}]

	});
	return toolbar;
},
/**
	 * 删除多条记录操作
	 */
	FileAttachView.removeAll = function(){
		var grid = Ext.getCmp("FileAttachGrid");
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg('操作提示', '请选择要删除的记录！');
			return;
		}
		var ids = '';
		for (var i = 0; i < selectRecords.length; i++) {
			ids += selectRecords[i].data.fileId + ',';
		}
		if(ids != ''){
			ids = ids.substring(0, ids.length);
		}
		FileAttachView.remove(ids);
	},
/**
 * 删除单个记录
 */
FileAttachView.remove = function(id) {
	var grid = Ext.getCmp("FileAttachGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/system/multiDelFileAttach.do',
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
 * 查看
 */
FileAttachView.edit = function(id) {
	new FileAttachForm(id);
};

/**
 * 搜索
 */
FileAttachView.search = function() {
	var searchPanel = Ext.getCmp('FileAttachSearchForm');
	var gridPanel = Ext.getCmp('FileAttachGrid');
	if (searchPanel.getForm().isValid()) {
		$search({
			searchPanel :searchPanel,
			gridPanel : gridPanel
		});
	}
};

/**
 * 清空
 */
FileAttachView.reset = function(){
	var searchPanel = Ext.getCmp('FileAttachSearchForm');
	searchPanel.getForm().reset();
};