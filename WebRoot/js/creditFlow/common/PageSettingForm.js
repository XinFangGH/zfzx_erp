/**
 * @author
 * @createtime
 * @class GlobalTypeForm
 * @extends Ext.Window
 * @description GlobalType表单
 * @company 智维软件
 */
PageSettingForm = Ext.extend(Ext.Window, {
			// 内嵌FormPanel
			formPanel : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				PageSettingForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 180,
					width : 400,
					maximizable : true,
					title : '分类详细信息',
					iconCls:'menu-globalType',
					buttonAlign : 'center',
					buttons : this.buttons,
					keys : {
						key : Ext.EventObject.ENTER,
						fn : this.save.createCallback(this.formPanel, this),
						scope : this
					}
				});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px 10px 10px 10px',
							border : false,
							url : __ctxPath + '/creditFlow/common/saveBpPageSetting.do',
							defaults : {
								anchor : '98%,98%'
							},
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								xtype : 'textfield',
								name : 'bpPageSetting.pageName',
								allowBlank : false,
								fieldLabel : '分类名称'
							},{
								xtype : 'textfield',
								name : 'bpPageSetting.pageKey',
								allowBlank : false,
								fieldLabel : '分类Key'
							},{
								xtype : 'hidden',
								name : 'bpPageSetting.pageSetId'
							},{
								xtype : 'hidden',
								name : 'bpPageSetting.parentId',
								value : this.parentId
							}]
						});

				// 加载表单对应的数据
				if (this.pageSetId != null && this.pageSetId != 'undefined'&&this.pageSetId!=0) {
					this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/common/getBpPageSetting.do?pageSetId='
								+ this.pageSetId,
						root : 'data',
						preName : 'bpPageSetting',
						scope : this,
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
							handler : this.save.createCallback(this.formPanel, this)
						}, {
							text : '重置',
							iconCls : 'btn-reset',
							handler : this.reset.createCallback(this.formPanel)
						}, {
							text : '取消',
							iconCls : 'btn-cancel',
							handler : this.cancel.createCallback(this)
						}];
			},// end of the initcomponents

			/**
			 * 重置
			 * 
			 * @param {}
			 *            formPanel
			 */
			reset : function(formPanel) {
				formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function(window) {
				window.close();
			},
			/**
			 * 保存记录
			 */
			save : function(formPanel, window) {
				
				var callback=window.callback;
				
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
					            	Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var gridPanel = Ext.getCmp('GlobalTypeGrid');
									
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
									
									if(callback!=null && callback!=undefined){
										callback.call(this);
									}
									
									window.close();
							           
								
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									window.close();
								}
							});
				}
			}//end of save

		});