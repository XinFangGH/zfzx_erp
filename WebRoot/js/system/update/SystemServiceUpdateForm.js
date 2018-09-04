/**
 * @author
 * @createtime
 * @class SystemServiceUpdateForm
 * @extends Ext.Window
 * @description SystemServiceUpdate表单
 * @company 智维软件
 */
SystemServiceUpdateForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SystemServiceUpdateForm.superclass.constructor.call(this, {
							id : 'SystemServiceUpdateFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '详细信息',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '重置',
										iconCls : 'btn-reset',
										scope : this,
										handler : this.reset
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
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
							// id : 'SystemServiceUpdateForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
										name : 'systemServiceUpdate.id',
										xtype : 'hidden',
										value : this.id == null ? '' : this.id
									}, {
										fieldLabel : '上传时间',
										name : 'systemServiceUpdate.updatetime',
										xtype : 'datefield',
										format : 'Y-m-d',
										value : new Date()
									}, {
										fieldLabel : '文件大小',
										name : 'systemServiceUpdate.updatesize',
										xtype : 'numberfield'
									}, {
										fieldLabel : '上传路径',
										name : 'systemServiceUpdate.filepath',
										xtype : 'textarea',
										maxLength : 512
									}, {
										fieldLabel : '操作人姓名',
										name : 'systemServiceUpdate.operatorname',
										maxLength : 64
									}, {
										fieldLabel : '是否成功',
										name : 'systemServiceUpdate.ifsuccess',
										xtype : 'numberfield'
									}, {
										xtype : 'textfield',
										fieldLabel : "上传的文件",
										columnWidth : 1,
										allowBlank : true,
										blankText : '不能为空',
										id : 'fileUpload',
										name : 'myUpload',
										fileUpload : true,
										frame : true,
										inputType : 'file'
									}]
						});
				// 加载表单对应的数据
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath
										+ '/p2p/getSystemServiceUpdate.do?id='
										+ this.id,
								root : 'data',
								preName : 'systemServiceUpdate'
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

				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/p2p/saveSystemServiceUpdate.do',
							callback : function(fp, action) {
								var gridPanel = Ext
										.getCmp('SystemServiceUpdateGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});