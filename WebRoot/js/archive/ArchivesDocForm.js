/**
 * @author csx
 * @createtime
 * @class ArchivesDocForm
 * @extends Ext.Window
 * @description ArchivesDoc表单
 * @company 智维软件
 */
ArchivesDocForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ArchivesDocForm.superclass.constructor.call(this, {
					id : 'ArchivesDocFormWin',
					layout : 'fit',
					border : false,
					items : this.formPanel,
					modal : true,
					height : 600,
					width : 800,
					iconCls : 'btn-archive-attachment',
					maximizable : true,
					title : '发文附件',
					buttonAlign : 'center',
					buttons : this.buttons
				});
		// this.on('show',this.onShow());
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.docPanel=new NtkOfficePanel({
							showToolbar : true,
							fileId:this.fileId,
							height : 480
						});

		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					// bodyStyle : 'padding:10px 10px 10px 10px',
					frame : true,
					url : __ctxPath + '/archive/saveArchivesDoc.do',
					id : 'ArchivesDocForm',
					defaults : {
						anchor : '98%,98%'
					},
					items : [{
								name : 'archivesDoc.docId',
								id : 'docId',
								xtype : 'hidden',
								value : this.docId == null ? '' : this.docId
							}, {
								xtype : 'hidden',
								name : 'archivesDoc.fileId',
								id : 'fileId'
							}, {
								fieldLabel : '文档名称',
								name : 'archivesDoc.docName',
								xtype : 'textfield',
								allowBlank : false,
								id : 'docName'
							}, {
								xtype : 'hidden',
								fieldLabel : '文档路径',
								name : 'archivesDoc.docPath',
								id : 'docPath'
							},this.docPanel.panel]
				});
		// 加载表单对应的数据
		if (this.docId != null && this.docId != 'undefined' && this.docId !=0) {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/archive/getArchivesDoc.do?docId='
						+ this.docId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
				},
				failure : function(form, action) {
				}
			});
		}
		// 初始化功能按钮
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : this.onSave
				}, {
					text : '重置',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.onReset
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					scope : this,
					handler : this.onCancel
				}];
	},// end of the initcomponents

	// overwrite the show method
	show : function() {

		ArchivesDocForm.superclass.show.call(this);
		if(this.fileId){
			this.docPanel.openDoc(this.fileId);
		}
	},

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	onReset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	onCancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	onSave : function() {
		// 取到当前窗口对象
		var curWin = this;
		var callback = this.callback;
		if (this.formPanel.getForm().isValid()) {
			var docPath = null;
			var fileId = null;
			var docName = this.formPanel.getCmpByName('archivesDoc.docName').getValue();
			var obj = this.docPanel.saveDoc({
						fileId : fileId,
						docName : docName,
						doctype : 'doc'
					});
			if (obj && obj.success) {
				fileId = obj.fileId;
			   	docPath = obj.filePath;
			    Ext.getCmp('docPath').setValue(docPath);
			} else {
				Ext.ux.Toast.msg('操作信息', '保存文档出错！');
				return;
			}
			if(this.docId == 0){
				var archivesDoc = {
					docId : 0,// 用于标记尚未持久化的记录
					fileId : fileId,
					docPath : docPath,
					docName : docName,
					curVersion : 1
				};
				callback.call(this, archivesDoc);
				curWin.close();
				return ;
			}
			this.formPanel.getForm().submit({
				method : 'POST',
				// waitMsg : '正在提交数据...',
				params : {
					docPath : docPath,
					fileId : fileId
				},
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存附加文档！');
					var result = Ext.util.JSON.decode(action.response.responseText);
					// 把添加的文档记录实体返回
					callback.call(this, result.data);
					curWin.close();
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
	},// end of save
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
		//alert(archivesDoc.fileId);
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
	}
});
