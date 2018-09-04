Ext.ns('DocumentView');
/**
 * 文档列表
 */
var DocumentView = function() {
	
};
/**
 * 显示列表
 * @return {}
 */
DocumentView.prototype.getView=function(){
	return new Ext.Panel({
		id : 'DocumentView',
		title : '所有文档',
		autoScroll : true,
		region:'center',
		layout:'border',
		items : [new Ext.FormPanel({
			region:'north',
			height : 40,
			frame : false,
			border:false,
			id : 'DocumentSearchForm',
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
						text : '文档名称'
					}, {
						xtype : 'textfield',
						name : 'Q_docName_S_LK'
					}, {
						text : '创建时间 从'
					}, {
						xtype : 'datefield',
						format:'Y-m-d',
						name : 'Q_createtime_D_GE'
					},{
						text : '至'
					},{
						xtype : 'datefield',
						format:'Y-m-d',
						name : 'Q_createtime_D_LE'
					},{
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('DocumentSearchForm');
							var gridPanel = Ext.getCmp('DocumentGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					},{
						xtype:'button',
						text:'重置',
						iconCls:'reset',
						hander:function(){
							var searchPanel = Ext.getCmp('DocumentSearchForm');
							searchPanel.getForm().reset();
						}
					}]
		}), this.setup()]
	});
};

DocumentView.prototype.setFolderId=function(folderId){
	this.folderId=folderId;
	DocumentView.folderId=folderId;
};

DocumentView.prototype.getFolderId=function(){
	return this.folderId;
};

/**
 * 建立视图
 */
DocumentView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
DocumentView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'docId',
					dataIndex : 'docId',
					hidden : true
				}, {
					header : '文档名称',
					dataIndex : 'docName',
					width:120
				}
//				, {
//					header : '内容',
//					dataIndex : 'content',
//					width:120
//				}
				, {
					header : '创建时间',
					dataIndex : 'createtime'
				},{
					header : '共享',
					width:40,
					dataIndex : 'isShared',
					renderer:function(value){
						if(value==1){
							return '<img src="'+__ctxPath+'/images/flag/shared.png" alt="共享" title="共享" />';
						}else{
							return '<img src="'+__ctxPath+'/images/flag/lock.png" alt="私有" title="私有" />';
						}
					}
				},{
					header : '附件',
					dataIndex : 'haveAttach',
					renderer:function(value,metadata,record){
						
						if(value=='' || value=='0'){
							return '无附件';
						}else{
							var attachFiles=record.data.attachFiles;
							var str='';
							for(var i=0;i<attachFiles.length;i++){
								str+='<a href="#" onclick="FileAttachDetail.show('+attachFiles[i].fileId+');" class="attachment">'+attachFiles[i].fileName+'</a>';
								str+='&nbsp;';
							}
							
							return str;
						}
					}
				}, {
					header : '管理',
					dataIndex : 'docId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.docId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="DocumentView.remove('
								+ editId + ')">&nbsp;</button>';
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DocumentView.edit('
								+ editId + ')">&nbsp;</button>';
								
						str+= '&nbsp;<button title="共享" value=" " class="btn-shared" onclick="DocumentView.shared('+editId+')">&nbsp;</button>';
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
	var grid = new Ext.grid.GridPanel({
				id : 'DocumentGrid',
				tbar : this.topbar(this),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				maxHeight:600,
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
							DocumentView.edit(rec.data.docId);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
DocumentView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/document/listDocument.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'docId',
										type : 'int'
									},'docName', 'content', 'createtime','haveAttach','attachFiles','isShared'
									]
						}),
				remoteSort : true
			});
	store.setDefaultSort('docId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
DocumentView.prototype.topbar = function(docViewObj) {
	var toolbar = new Ext.Toolbar({
				id : 'DocumentFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加文档',
							xtype : 'button',
							handler : function() {
								    new DocumentForm(null);
							}
						}, {
							iconCls : 'btn-del',
							text : '删除文档',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("DocumentGrid");

								var selectRecords = grid.getSelectionModel().getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.docId);
								}

								DocumentView.remove(ids);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
DocumentView.remove = function(id) {
	var grid = Ext.getCmp("DocumentGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/document/multiDelDocument.do',
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
DocumentView.edit = function(id) {
	new DocumentForm(id);
};
/**
 * 文档共享
 * @param {} id
 */
DocumentView.shared=function(id){
	new DocumentSharedForm(id).getView().show();
};
