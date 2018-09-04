/**
 * @author
 * @createtime
 * @class RollFileListForm
 * @extends Ext.Window
 * @description RollFileList表单
 * @company 智维软件
 */
RollFileListForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RollFileListForm.superclass.constructor.call(this, {
					id : 'RollFileListFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 350,
					width : 500,
					maximizable : true,
					title : '附件详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '确定',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'RollFileListForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'displayfield',
			labelWidth :60,
			labelPad:3,
			items : [{
						fieldLabel : '主键ID',
						name : 'rollFileList.listId',
						xtype : 'hidden',
						value : this.listId == null ? '' : this.listId
					}, {
						fieldLabel : '卷ID',
						name : 'rollFileList.rollFileId',
						allowBlank : false,
						xtype : 'hidden',value : this.rollFileId == null ? '' : this.rollFileId
					}, {

						fieldLabel : '附ID',
						id : 'rollFileList.fileAttach.fileId',
						name : 'rollFileList.fileId',
						allowBlank : false,
						xtype : 'hidden'

					},
					{
									fieldLabel : '文件名称',
									id : 'rollFileList.fileAttach.fileName',
									name : 'rollFileList.fileAttach.fileName',
									allowBlank : false,
									xtype : 'displayfield',
									width:261
								}

					,

//					{
//						xtype : 'compositefield',
//						fieldLabel : '文件名称',
//						msgTarget : 'side',
//						border : true,
//						items : [{
//									
//									id : 'rollFileList.fileAttach.fileName',
//									name : 'rollFileList.fileAttach.fileName',
//									allowBlank : false,
//									xtype : 'textfield',
//									width:261,
//									readOnly : true,
//									maxLength : 128
//								}, {
//									xtype : 'button',
//									text : '选择文件',
//									iconCls : 'btn-upload',
//									handler : function() {
//										App.createUploadDialog({
//											file_cat : 'arch/upload',
//											callback : function(data) {
//												var fileId = data[0].fileId;
//												var fileName = data[0].filename;
//												var filePath = data[0].filepath;
//												Ext
//														.getCmp('rollFileList.fileAttach.fileId')
//														.setValue(fileId);
//												Ext
//														.getCmp('rollFileList.fileAttach.fileName')
//														.setValue(fileName);
//												Ext
//														.getCmp('rollFileList.fileAttach.filePath')
//														.setValue(filePath);
//
//											}
//										}).show();
//
//									}
//								}]
//					}

					 {
						fieldLabel : '文件路径',xtype : 'displayfield',
						readOnly : true,
						id : 'rollFileList.fileAttach.filePath',
						name : 'rollFileList.fileAttach.filePath',
						allowBlank : false,
						maxLength : 256
					},

					{
						fieldLabel : '文件类型',xtype : 'displayfield',
						readOnly : true,
						name : 'rollFileList.fileAttach.ext',
						maxLength : 128
					},

					{
						fieldLabel : '下载次数',
						readOnly : true,
						name : 'rollFileList.downLoads',
						value:0,
						xtype : 'displayfield'
					}, {
						fieldLabel : '序号',
						name : 'rollFileList.sn',
						xtype : 'displayfield'
					}, {
						fieldLabel : '概要',
						name : 'rollFileList.shortDesc',
						xtype : 'displayfield'
					},

					{
						xtype : 'compositefield',
						msgTarget : 'side',
						border : true,
						fieldLabel : '录入人',
						items : [{
									
									readOnly : true,
									name : 'rollFileList.fileAttach.creator',
									value : curUserInfo.fullname,
									readOnly : true,xtype : 'displayfield',
									maxLength : 128
								}, {
									xtype : 'displayfield',
									value:'录入时间:'
								},{
									//fieldLabel : '录入时间',
									readOnly : true,
									name : 'rollFileList.fileAttach.createtime',
									xtype : 'displayfield',
									format : 'Y-m-d',
									readOnly : true,
									value : new Date().format('Y-m-d')
								}]
					}]
		});
		// 加载表单对应的数据
		if (this.listId != null && this.listId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/arch/getRollFileList.do?listId='
								+ this.listId,
						root : 'data',
						preName : 'rollFileList'
					});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		
		
//		$postForm({
//					formPanel : this.formPanel,
//					scope : this,
//					url : __ctxPath + '/arch/saveRollFileList.do',
//					callback : function(fp, action) {
//						var gridPanel = Ext.getCmp('RollFileListGrid');
//						if (gridPanel != null) {
//							gridPanel.getStore().reload();
//						}
//						this.close();
//					}
//				});
				
				
				
		}// end of save
	
	
	

});