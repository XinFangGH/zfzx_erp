/**
 * @author 
 * @createtime 
 * @class BpPersonPhonecheckInfoForm
 * @extends Ext.Window
 * @description BpPersonPhonecheckInfo表单
 * @company 智维软件
 */
BpPersonPhonecheckInfoForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpPersonPhonecheckInfoForm.superclass.constructor.call(this, {
							id : 'BpPersonPhonecheckInfoFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpPersonPhonecheckInfo]详细信息',
							buttonAlign : 'center',
							buttons : [
										{
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
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'BpPersonPhonecheckInfoForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpPersonPhonecheckInfo.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '联系人信息表主键',	
								 								name : 'bpPersonPhonecheckInfo.personRelationId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '是否知悉贷款 0知道  1不知道',	
								 								name : 'bpPersonPhonecheckInfo.isKnowLoan'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '电核内容',	
								 								name : 'bpPersonPhonecheckInfo.phoneText'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '审核员Id',	
								 								name : 'bpPersonPhonecheckInfo.checkUserId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '项目表主键',	
								 								name : 'bpPersonPhonecheckInfo.projectId'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.personrelation.phonecheck/getBpPersonPhonecheckInfo.do?id='+ this.id,
								root : 'data',
								preName : 'bpPersonPhonecheckInfo'
							});
				}
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow.personrelation.phonecheck/saveBpPersonPhonecheckInfo.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpPersonPhonecheckInfoGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});