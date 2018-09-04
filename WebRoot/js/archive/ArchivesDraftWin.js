Ext.ns('ArchivesDraftWin');
/**
 * @createtime:2010-01-20
 * @author csx
 * @description 公文拟稿发文界面
 * @class ArchivesDraftWin
 * @extends Ext.Panel
 */
ArchivesDraftWin = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.init();
		ArchivesDraftWin.superclass.constructor.call(this, {
					title : '发文修改',
					id : 'ArchivesDraftWin',
					iconCls : 'menu-archive-draft',
					layout : 'fit',
					bodyStyle : 'padding:2px 20px 2px 2px;',
					border : false,
//					layoutConfig : {
//						padding : '5',
//						pack : 'center',
//						align : 'middle'
//					},
					modal : true,
					height : 530,
					width : 800,
					maximizable : true,
					autoScroll : true,
					buttonAlign : 'center',
					buttons :  [ {
									text : '保存',
									iconCls : 'btn-save',
									handler : this.onSend,
									scope : this
								}, {
									text : '关闭',
									iconCls : 'close',
									handler : this.closePanel,
									scope : this
								}],
					items : [this.formPanel]
				});
	},
	/**
	 * 关闭Panel
	 */
	closePanel : function() {
		this.close();
	},
	/**
	 * 保存公文的方法,传入公文状态作为参数
	 * @param {} _status
	 */
	onSave : function(win) {
		
		if (this.formPanel.getForm().isValid()) {

			// 发文的文档附件
			var docParams = [];

			for (var i = 0, cnt = this.store.getCount(); i < cnt; i++) {
				docParams.push(this.store.getAt(i).data);
			}
			
			var detailPanel = this.detailPanel;
			
			this.formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						params : {
							docs : Ext.encode(docParams)
						},
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							if(detailPanel !=null && detailPanel !='undefined'){
								detailPanel.getUpdater().refresh();
							}
							win.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
					});
		}
	},
	/**
	 * 拟搞完成,发送给指定的人员核稿
	 */
	onSend : function(){
		this.onSave(this);
	},
	/**
	 * 添加附件文档
	 */
	addArchiveDoc : function() {
	    var store = this.store;
		var curView = this;
		new ArchTemplateSelector({
					// scope : this,
					callback : function(fileId) {
						
						// 返回文档附加记录
						var callback = function(archivesDoc) {
							// alert('coming in callback.' + archivesDoc);
							curView.insertNewDoc(store, archivesDoc);
						};
						new ArchivesDocForm({
									fileId : fileId,
									//docPath : tempPath,
									callback : callback
								}).show();
					}
				}).show();
	},
	/**
	 * 插入附加文件记录
	 * 
	 * @param {}
	 *            store
	 * @param {}
	 *            archivesDoc
	 */
	insertNewDoc : function(store, archivesDoc) {
		var orec;
		if (store.recordType) {
			orec = new store.recordType();
			orec.data = {};
			orec.data['docId'] = archivesDoc.docId;
			orec.data['fileId'] = archivesDoc.fileId;
			orec.data['docPath'] = archivesDoc.docPath;
			orec.data['docName'] = archivesDoc.docName;
			orec.data['curVersion'] = archivesDoc.curVersion
					? archivesDoc.curVersion
					: 1;
			orec.data.newRecord = true;
			orec.commit();
			store.add(orec);
		}
	},

	/**
	 * 添加新的公文文档，以一个空白的文档开始
	 */
	addNewArchiveDoc : function() {
		var store = this.store;
		var curView = this;
		// 返回文档附加记录
		var callback = function(archivesDoc) {
			curView.insertNewDoc(store, archivesDoc);
		};
		new ArchivesDocForm({
					callback : callback
				}).show();
	},
	/**
	 * 上传附件
	 */
	uploadArchiveDoc : function() {
		var store = this.store;
		var curView = this;
		var callback = function(data) {
			for (var i = 0; i < data.length; i++) {
				var archivesDoc = {
					docId : 0,// 用于标记尚未持久化的记录
					fileId : data[i].fileId,
					docPath : data[i].filePath,
					docName : data[i].fileName,
					curVersion : 1
				};
				curView.insertNewDoc(store, archivesDoc);
			}
		};
		var dialog = App.createUploadDialog({
					file_cat : 'archive',
					callback : callback
				});
		dialog.show();
	},
	/**
	 * 删除公文附件
	 */
	deleteArchiveDoc : function() {
		var grid = Ext.getCmp("archiveDocGrid");

		var selectRecords = grid.getSelectionModel().getSelections();

		if (selectRecords.length == 0) {
			Ext.Msg.alert("信息", "请选择要查看的文档！");
			return;
		}

		var record = selectRecords[0];
		var store = grid.getStore();

		var docId = record.data.docId;

		Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
							url : __ctxPath + '/archive/multiDelArchivesDoc.do',
							params : {
								ids : docId
							},
							method : 'POST',
							success : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '成功删除该文档附件！');
								// Ext.getCmp('ArchivesGrid').getStore().reload();
								store.remove(record);
							},
							failure : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
							}
						});
			}
		});// end of comfirm
		
	},
	/**
	 * 查看公文附件
	 */
	detailArchivesDoc : function() {
		var grid = Ext.getCmp("archiveDocGrid");

		var selectRecords = grid.getSelectionModel().getSelections();

		if (selectRecords.length == 0) {
			Ext.Msg.alert("信息", "请选择要查看的文档！");
			return;
		}
		var record = selectRecords[0];
		var path = record.data.docPath;
		var docId = record.data.docId;
        var fileId=null;
        if(record.data.fileAttach){
         	fileId = record.data.fileAttach.fileId;
        }else{
        	fileId=record.data.fileId;
        }
		var store = grid.getStore();
		var curView = this;//Ext.getCmp('ArchivesDraftFolwView');
		// 返回文档附加记录
		var callback = function(archivesDoc) {
			store.remove(record);
			curView.insertNewDoc(store, archivesDoc);
		};
		new ArchivesDocForm({
			        fileId:fileId,
					docId : docId,
					docPath : path,
					callback : callback
				}).show();
	},

	/**
	 * init the components
	 */
	init : function() {
		// 加载数据至store TODO change the archiveIds
		this.store = new Ext.data.JsonStore({
					url : __ctxPath+ '/archive/listArchivesDoc.do?archivesId='+ this.archivesId,
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'docId',
								type : 'int'
							}, 'fileAttach', 'creator', 'creatorId', 'menderId',
							'mender', 'docName', 'docStatus', 'curVersion',
							'docPath', 'updatetime', 'createtime']
				});
		this.store.setDefaultSort('docId', 'desc');
		if(this.archivesId !=null && this.archivesId !='' && this.archivesId != 'undefined'){
			this.store.load();
		}
		this.toolbar = new Ext.Toolbar({
					height : 30,
					items : [{
								text : '按模板在线添加',
								iconCls : 'menu-archive-template',
								handler : this.addArchiveDoc,
								scope : this
							},'-', {
								text : '在线添加',
								iconCls : 'btn-edit-online',
								handler : this.addNewArchiveDoc,
								scope : this
							},'-', {
								text : '上传文档',
								iconCls : 'btn-upload',
								handler : this.uploadArchiveDoc,
								scope : this
							},'-', {
								text : '删除附件文档',
								iconCls : 'btn-del',
								scope : this,
								handler : this.deleteArchiveDoc
							},'-', {
								text : '查看修改文档',
								iconCls : 'menu-archive-issue-manage',
								scope : this,
								handler : this.detailArchivesDoc
							}]
				});

		var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true
				});
		// 初始化附件文档
		this.docGridPanel = new Ext.grid.EditorGridPanel({
					title : '公文正文',
					iconCls : 'menu-attachment',
					//columnWidth : .96,
					border : true,
					id : 'archiveDocGrid',
					autoHeight : true,
					store : this.store,
					tbar : this.toolbar,
					sm : sm,
					columns : [new Ext.grid.RowNumberer(), sm, {
								dataIndex : 'docId',
								hidden : true
							}, {
								dataIndex : 'fileAttach',
								hidden : true,
								renderer : function(value) {
									// return value.fileId;
								}
							}, {
								dataIndex : 'docStatus',
								hidden : true
							}, {
								dataIndex : 'menderId',
								hidden : true
							}, {
								dataIndex : 'creatorId',
								hidden : true
							}, {
								dataIndex : 'docName',
								width : 150,
								header : '文档名称'
							}, {
								dataIndex : 'docPath',
								header : '文档路径' ,
								width : 300
							}, {
								dataIndex : 'curVersion',
								header : '当前版本',
								renderer : function(value){
									return '第' + value + '版'; 
								}
							},  {
								header : '管理',
								width : 100,
								dataIndex : 'docId',
								sortable : false,
								renderer : function(value, metadata, record, rowIndex,
										colIndex) {
									var str = '';
										str += '<button title="历史版本" value=" " class="btn-archive-history" onclick="ArchivesDraftWin.attach('
											+ value + ')">&nbsp;&nbsp;</button>';
									return str;
								}
							}]
				});
		// 初始化表单
		this.formPanel = new Ext.FormPanel({
			
			bodyStyle:'padding: 4px 8px 4px 8px',
			layout : 'form',
			autoHeight : true,
			//style : 'padding:6px 6px 16px 5%',
			url : __ctxPath + '/archive/saveIssueArchives.do',
			//id : 'ArchivesForm',
//			defaults : {
//				anchor : '96%,96%'
//			},
			items : [{
						name : 'archives.archivesId',
						id : 'archivesWin.archivesId',
						xtype : 'hidden',
						value : this.archivesId == null ? '' : this.archivesId
					}, {
						xtype : 'compositefield',
						fieldLabel : '所属类型',
						items : [{
									name : 'archives.typeName',
									xtype : 'textfield',
									width : 250,
									readOnly : true,
									allowBlank : false
								}, {
									xtype : 'button',
									text : '选择类型',
									iconCls : 'btn-select',
									scope : this,
									handler : function() {
										var fPanel = this;
										new GlobalTypeSelector({
													catKey : 'ARCHIVES_TYPE',
													isSingle : true,
													callback : function(typeId,typeName) {
														fPanel.getCmpByName('archives.typeId').setValue(typeId);
														fPanel.getCmpByName('archives.typeName').setValue(typeName);
													}
												}).show();
									}

								}]
						}, {
						xtype : 'fieldset',
						title : '发文设置',
						border : true,
						defaults : {
							anchor : '98%,98%'
						},
						items : [{
									layout : 'form',
									// columnWidth : .4,
									border : false,
									items : {
										fieldLabel : '发文字号',
										name : 'archives.archivesNo',
										id : 'archivesWin.archivesNo',
										xtype : 'textfield',
										allowBlank : false,
										anchor : '100%'
									}
								}, {
									layout : 'form',
									border : false,
									style : 'padding:0px 0px 7px 0px;',
									defaults : {
										anchor : '96%,96%'
									},
									items : [{
										layout : 'column',
										border : false,
										items : [{
											layout : 'form',
											anchor : '99%',
											style : 'padding:0px 0px 0px 0px;',
											border : false,
											items : {
												fieldLabel : '密级',
												width : 200,
												name : 'archives.privacyLevel',
												id : 'archivesWin.privacyLevel',
												triggerAction : 'all',
												lazyRender : true,
												allowBlank : false,
												emptyText : '选择密级',
												xtype : 'combo',
												store : ['普通', '秘密', '机密', '绝密']
											}
										}, {
											layout : 'form',
											border : false,
											items : {
												fieldLabel : '紧急程度',
												width : 200,
												name : 'archives.urgentLevel',
												id : 'archivesWin.urgentLevel',
												triggerAction : 'all',
												lazyRender : true,
												allowBlank : false,
												emptyText : '选择紧急程度',
												xtype : 'combo',
												store : ['普通', '紧急', '特急', '特提']
											}
										}]
									}]
								}, {
									fieldLabel : '文件标题',
									name : 'archives.subject',
									id : 'archivesWin.subject',
									xtype : 'textfield',
									allowBlank : false
								}, {
									xtype : 'container',
									layout : 'column',
									style : 'padding-left:0px;margin-left:0px;',
									height : 30,
									defaults : {
										border : false
									},
									items : [{
												xtype : 'label',
												text : '发文机关或部门',
												style : 'padding:0px 0px 0px 0px;',
												width : 105
											}, {
												// columnWidth:.4,
												name : 'archives.issueDep',
												id : 'archivesWin.issueDep',
												xtype : 'textfield',
												width : '70%',
												allowBlank : false,
												readOnly : true
											}, {
												name : 'archives.depId',
												id : 'archivesWin.depId',
												xtype : 'hidden'
											}, {
												xtype : 'button',
												iconCls : 'menu-department',
												text : '选择部门',
												handler : function() {
													DepSelector.getView(
															function(depId,
																	depName) {
																Ext
																		.getCmp('archivesWin.issueDep')
																		.setValue(depName);
																Ext
																		.getCmp('archivesWin.depId')
																		.setValue(depId);
															}, true).show();
												}
											}]
								}, {
									xtype : 'container',
									layout : 'column',
									style : 'padding:0px 0px 8px 0px;margin-left:0px;',
									defaults : {
										border : false
									},
									items : [{
												xtype : 'label',
												style : 'padding:0px 0px 0px 0px;',
												text : '接收单位或部门',
												width : 105
											}, {
												// columnWidth:.6,
												xtype : 'textarea',
												name : 'archives.recDepNames',
												width : '70%',
												readOnly : true,
												id : 'archivesWin.recDepNames'
											}, {
												xtype : 'hidden',
												name : 'archives.recDepIds',
												id : 'archivesWin.recDepIds'
											}, {
												xtype : 'button',
												iconCls : 'menu-department',
												text : '选择部门',
												handler : function() {
													DepSelector.getView(
															function(depIds,
																	depNames) {
																Ext.getCmp('archivesWin.recDepIds').setValue(depIds);
																Ext.getCmp('archivesWin.recDepNames').setValue(depNames);
															}, false).show();
												}
											}]
								}, {
									fieldLabel : '主题词',
									name : 'archives.keywords',
									id : 'archivesWin.keywords',
									xtype : 'textfield'
								}, {
									fieldLabel : '内容简介',
									name : 'archives.shortContent',
									id : 'archivesWin.shortContent',
									xtype : 'textarea'
								}, {
									fieldLabel : '公文来源',
									name : 'archives.sources',
									id : 'archivesWin.sources',
									xtype : 'textfield'
								}, {
									name : 'archives.typeId',
									xtype : 'hidden'
								}]
						// end of the field set items
					},// end of fieldset
					this.docGridPanel

			]
		});
		// 加载表单对应的数据
		if (this.archivesId != null && this.archivesId != 'undefined') {
			var fPanel = this.formPanel;
			this.formPanel.loadData({
				root : 'data',
				preName : 'archives',
				url : __ctxPath + '/archive/getIssueArchives.do?archivesId='
						+ this.archivesId,
				success : function(form, action) {
//					var res = action.result.data[0];
//					fPanel.getCmpByName('archives.typeName').setValue(res.archivesType.typeName);
				},
				failure : function(form, action) {
				}
			});
		}
	}// end of init
});

ArchivesDraftWin.attach = function(value){
	var grid = Ext.getCmp("archiveDocGrid");
	var selectRecords = grid.getSelectionModel().getSelections();

	if (selectRecords.length == 0) {
		Ext.Msg.alert("信息", "请选择要查看的文档！");
		return;
	}
	var record = selectRecords[0];
	var curView = Ext.getCmp('ArchivesDraftWin');
	var store = grid.getStore();
		// 返回文档附加记录
	var callback = function(archivesDoc) {
		store.remove(record);
		curView.insertNewDoc(store, archivesDoc);
	};
	new ArchivesDocHistoryWin({
		docId : value,
		callback : callback
	}).show();
}