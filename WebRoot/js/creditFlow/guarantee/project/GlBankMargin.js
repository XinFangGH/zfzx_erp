/**
 * @author lisl
 * @class GlBankMargin
 * @description 银行保证金
 * @extends Ext.Window
 */
GlBankMargin = Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				GlBankMargin.superclass.constructor.call(this, {
							title : '银行保证金',
							width : 892,
							height : 156,
							modal : true,
							border : false,
							iconCls : '',
							autoScroll : true,
							layout : 'fit',
							items : [],
							buttonAlign : 'center',
							buttons : this.isAllReadOnly ? null : [{
										text : '保存',
										scope : this,
										iconCls : 'btn-save',
										handler : this.submit
									}, {
										text : '取消',
										scope : this,
										iconCls : 'btn-cancel',
										scope : this,
										handler : function() {
											this.close();
										}
									}]
						});
			},
			initUIComponents : function() {
				var jsArr = [
				__ctxPath+'/js/creditFlow/guarantee/guaranteefinance/accountBankManage/accountBankManageTreeWin.js',
				__ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoneyrelease.js'];
				$ImportSimpleJs(jsArr, this.constructPanel, this);
			},// 初始化组件
			constructPanel : function() {
				this.creditlineRelease = new BankGuaranteeMoneyrelease({projectId : this.projectId,isReadOnlyFrozen : this.isReadOnlyFrozen,isReadOnlyRelease : this.isReadOnlyRelease});
				this.formPanel = new Ext.FormPanel();
				this.formPanel.add(this.creditlineRelease);
				this.add(this.formPanel);
				this.doLayout();
			},
			submit : function() {
				this.formPanel.getForm().submit({
				    clientValidation: true, 
					url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/save1GlBankGuaranteemoney.do',
					params : {
						projId : this.projectId,
						operationType : this.operationType
					},
					method : 'post',
					scope : this,
					waitMsg : '数据正在提交，请稍后...',
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息', '保存信息成功!');
						this.close();
						
					},
					failure : function(fp, action) {
		
						Ext.MessageBox.show({
							title : '操作信息',
							msg : '信息保存出错，请联系管理员！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
					}
				})
			}
		});