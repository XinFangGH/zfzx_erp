/**
 * @author 
 * @createtime 
 * @class BpPersonNetCheckInfoForm
 * @extends Ext.Window
 * @description BpPersonNetCheckInfo表单
 * @company 智维软件
 */
BpPersonNetCheckInfoForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpPersonNetCheckInfoForm.superclass.constructor.call(this, {
							id : 'BpPersonNetCheckInfoFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpPersonNetCheckInfo]详细信息',
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
							//id : 'BpPersonNetCheckInfoForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpPersonNetCheckInfo.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '项目Id',	
								 								name : 'bpPersonNetCheckInfo.projectId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '查询对象',	
								 								name : 'bpPersonNetCheckInfo.serachObj'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '查询内容',	
								 								name : 'bpPersonNetCheckInfo.serachInfo'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '有无异常 0无 1有',	
								 								name : 'bpPersonNetCheckInfo.isException'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '备注',	
								 								name : 'bpPersonNetCheckInfo.remark'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '审核员Id',	
								 								name : 'bpPersonNetCheckInfo.checkUserId'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow.personrelation.netcheck/getBpPersonNetCheckInfo.do?id='+ this.id,
								root : 'data',
								preName : 'bpPersonNetCheckInfo'
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
						url:__ctxPath + '/creditFlow.personrelation.netcheck/saveBpPersonNetCheckInfo.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpPersonNetCheckInfoGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});