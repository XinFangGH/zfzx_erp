/**
 * @author
 * @createtime
 * @class PersonalPlanTypeForm
 * @extends Ext.Window
 * @description 表单
 * @company 智维软件
 */
PersonalPlanTypeForm = Ext.extend(Ext.Window, {
			// 内嵌FormPanel
			formPanel : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				PersonalPlanTypeForm.superclass.constructor.call(this, {
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 180,
							width : 400,
							maximizable : true,
							title : '个人计划类型详细信息',
							buttonAlign : 'center',
							buttons : this.buttons
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px 10px 10px 10px',
							border : false,
							url : __ctxPath + '/system/saveGlobalType.do',
							id : 'PersonalPlanTypeForm',
							defaults : {
								anchor : '98%,98%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'globalType.proTypeId',
								id : 'proTypeId',
								xtype : 'hidden',
								value : this.proTypeId == null
										? ''
										: this.proTypeId
							}, {
								fieldLabel : '名称',
								name : 'globalType.typeName',
								id : 'typeName',
								allowBlank:false
							}, {
								fieldLabel : '父节点',
								value:0,
								xtype:'hidden',
								name : 'globalType.parentId'
							}, {
								fieldLabel : '节点Key',
								name : 'globalType.nodeKey',
								allowBlank:false,
								xtype:'hidden',
								id : 'nodeKey',
								value:'PWP'
							}, {
								fieldLabel : '节点分类Key',
								name : 'globalType.catKey',
								allowBlank:false,
								xtype:'hidden',
								id : 'catKey',
								value:'PWP'
							},{
							    xtype:'hidden',
							    name:'globalType.userId',
							    value:curUserInfo.userId
							}
							,{
							    xtype:'hidden',
							    name:'isPublic',
							    value:false
							}
							]
						});

				// 加载表单对应的数据
				if (this.proTypeId != null && this.proTypeId != 'undefined') {
					this.formPanel.getForm().load({
						deferredRender : false,
						url : __ctxPath
								+ '/system/getGlobalType.do?proTypeId='
								+ this.proTypeId,
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
							handler : this.save.createCallback(this.formPanel,
									this)
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