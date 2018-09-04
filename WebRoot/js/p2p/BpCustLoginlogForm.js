/**
 * @author 
 * @createtime 
 * @class BpCustLoginlogForm
 * @extends Ext.Window
 * @description BpCustLoginlog表单
 * @company 智维软件
 */
BpCustLoginlogForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCustLoginlogForm.superclass.constructor.call(this, {
							id : 'BpCustLoginlogFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpCustLoginlog]详细信息',
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
							//id : 'BpCustLoginlogForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpCustLoginlog.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : '动作类型（0登录，1退出）',	
								 								name : 'bpCustLoginlog.type'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '登录IP',	
								 								name : 'bpCustLoginlog.loginIp'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '操作时间',	
								 								name : 'bpCustLoginlog.loginTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'bpCustLoginlog.memberId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'bpCustLoginlog.exitTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p/getBpCustLoginlog.do?id='+ this.id,
								root : 'data',
								preName : 'bpCustLoginlog'
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
						url:__ctxPath + '/p2p/saveBpCustLoginlog.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpCustLoginlogGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});