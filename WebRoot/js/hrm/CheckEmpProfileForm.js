/**
 * @author
 * @createtime
 * @class CheckEmpProfileForm
 * @extends Ext.Window
 * @description Job表单
 * @company 智维软件
 */
CheckEmpProfileForm = Ext.extend(Ext.Window, {
			// 内嵌FormPanel
			formPanel : null,
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				// 必须先初始化组件
				this.initComponents();
				CheckEmpProfileForm.superclass.constructor.call(this, {
							id : 'CheckEmpProfileFormWin',
							iconCls : 'btn-empProfile-pass',
							layout : 'form',
							items : [this.formPanel,this.displayPanel],
							modal : true,
							//autoScroll : true,
//							height : 580,
							autoHeight:true,
							shadow : false,
						    y:10,
							width : 820,
							maximizable : true,
							title : '档案审核',
							buttonAlign : 'center',
							buttons : this.buttons
						});
			},// end of the constructor
			// 初始化组件
			initComponents : function() {
				this.displayPanel = new Ext.Panel({
					id : 'CheckEmpProfileFormPanel',
					height : 430,
//					width : 800,
					autoScroll : true,
					//autoHeight:true,
					border : false,
					autoLoad : {
						url : __ctxPath
								+ '/pages/hrm/CheckEmpProfile.jsp?profileId='
								+ this.profileId
					}
				})
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							border : false,
							url : __ctxPath + '/hrm/checkEmpProfile.do?profileId=' + this.profileId,
							id : 'CheckEmpProfileForm',
							bodyStyle : 'padding:10px 10px 0 10px;',
							defaultType : 'textfield',
							items : [{
								fieldLabel : '审核意见',
								xtype : 'textarea',
								anchor : '98%',
								allowBlank : false,
								blankText : '审核意见为必填!',
								name : 'empProfile.opprovalOpinion',
								id : 'CheckEmpProfileForm.opprovalOpinion'
							},{
								xtype : 'hidden',
								name : 'empProfile.approvalStatus',
								id : 'CheckEmpProfileForm.approvalStatus'
							}]
						});
				// 初始化功能按钮
				this.buttons = [{
							text : '审核通过',
							id:'CheckEmpProfileButY',
							iconCls : 'btn-finish',
							handler : this.check.createCallback(this.formPanel,this)
						}, {
							text : '审核未通过',
							id:'CheckEmpProfileButN',
							iconCls : 'btn-disagree',
							handler : this.refuse.createCallback(this.formPanel,this)
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
			refuse : function(formPanel,window) {
				Ext.getCmp('CheckEmpProfileForm.approvalStatus').setValue('2');//表审核不通过
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var gridPanel = Ext.getCmp('EmpProfileGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
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
			},//end of refuse
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
			check : function(formPanel, window) {
				Ext.getCmp('CheckEmpProfileForm.approvalStatus').setValue('1');//表审核通过
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var gridPanel = Ext.getCmp('EmpProfileGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
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
			}// end of check

		});