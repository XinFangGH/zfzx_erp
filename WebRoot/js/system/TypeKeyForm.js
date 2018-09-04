/**
 * @author
 * @createtime
 * @class TypeKeyForm
 * @extends Ext.Window
 * @description TypeKey表单
 * @company 智维软件
 */
TypeKeyForm = Ext.extend(Ext.Window, {
			// 内嵌FormPanel
			formPanel : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				TypeKeyForm.superclass.constructor.call(this, {
					id : 'TypeKeyFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 200,
					width : 400,
					maximizable : true,
					title : '分类详细信息',
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
				this.formPanel = new HT.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px 10px 10px 10px',
							border : false,
							url : __ctxPath + '/system/saveTypeKey.do',
							id : 'TypeKeyForm',
							defaults : {
								anchor : '98%,98%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'typeKey.typekeyId',
								xtype : 'hidden',
								value : this.typekeyId == null
										? ''
										: this.typekeyId
							}, {
								fieldLabel : '类型名',
								name : 'typeKey.typeName'
							}, {
								fieldLabel : '类型KEY',
								name : 'typeKey.typeKey'
							}, {
								fieldLabel : '',
								name : 'typeKey.sn',
								xtype:'hidden',
								value:1
							}

							]
						});
				// 加载表单对应的数据
				if (this.typekeyId != null && this.typekeyId != 'undefined') {
					this.formPanel.loadData({
						url : __ctxPath + '/system/getTypeKey.do?typekeyId='
								+ this.typekeyId,
						preName:'TypeKey',
						root:'data'
					});
				}
				// 初始化功能按钮
				this.buttons = [{
							text : '保存',
							iconCls : 'btn-save',
							handler : this.save.createCallback(this.formPanel, this)
						},{
							text : '取消',
							iconCls : 'btn-cancel',
							handler : this.cancel.createCallback(this)
						}];
			},// end of the initcomponents

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
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var type = Ext.getCmp('comboGlobalType');
									if (type != null) {
										type.getStore().reload();
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
			}// end of save

		});