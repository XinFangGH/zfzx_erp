/**
 * @author
 * @createtime
 * @class CheckSalaryPayoffForm
 * @extends Ext.Window
 * @description Job表单
 * @company 智维软件
 */
CheckSalaryPayoffForm = Ext.extend(Ext.Window, {
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
				CheckSalaryPayoffForm.superclass.constructor.call(this, {
							id : 'CheckSalaryPayoffFormWin',
							iconCls : 'btn-empProfile-pass',
							layout : 'form',
							items : [this.displayPanel,this.formPanel],
							modal : true,
							height : 415,
//							autoHeight:true,
//							y:20,
							shadow : false,
							autoScroll : true,
							width : 520,
							maximizable : true,
							title : '薪酬发放审核',
							buttonAlign : 'center',
							buttons : this.buttons
						});
			},// end of the constructor
			// 初始化组件
			initComponents : function() {
				this.displayPanel = new Ext.Panel({
					id : 'CheckSalaryPayoffFormPanel',
					autoHeight : true,
					//columnWidth : 1,
					border : false,
					autoLoad : {
						url : __ctxPath
								+ '/pages/hrm/checkSalaryPayoff.jsp?recordId='
								+ this.recordId
					}
				})
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							//columnWidth : 1,
							border : false,
							url : __ctxPath + '/hrm/checkSalaryPayoff.do?recordId=' + this.recordId,
							id : 'CheckSalaryPayoffForm',
							bodyStyle : 'padding:0 0 0 10px;',
							defaultType : 'recordId',
							items : [{
								fieldLabel : '审核意见',
								xtype : 'textarea',
								anchor : '98%',
								allowBlank : false,
								blankText : '审核意见为必填!',
								name : 'salaryPayoff.checkOpinion',
								id : 'CheckSalaryPayoffForm.checkOpinion'
							},{
								xtype : 'hidden',
								name : 'salaryPayoff.checkStatus',
								id : 'CheckSalaryPayoffForm.checkStatus'
							}]
						});
				// 初始化功能按钮
				this.buttons = [{
							text : '审核通过',
							iconCls : 'btn-finish',
							id:'salaryPayoffbtnY',
							handler : this.check.createCallback(this.formPanel,this)
						}, {
							text : '审核未通过',
							id:'salaryPayoffbtnN',
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
				Ext.getCmp('CheckSalaryPayoffForm.checkStatus').setValue('2');//表审核不通过
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var gridPanel = Ext.getCmp('SalaryPayoffGrid');
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
				Ext.getCmp('CheckSalaryPayoffForm.checkStatus').setValue('1');//表审核通过
				if (formPanel.getForm().isValid()) {
					formPanel.getForm().submit({
								method : 'POST',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									var gridPanel = Ext.getCmp('SalaryPayoffGrid');
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