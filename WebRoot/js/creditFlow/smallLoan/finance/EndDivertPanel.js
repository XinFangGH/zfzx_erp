/**
 * @author 
 * @createtime 
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
EndDivertPanel =Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				EndDivertPanel.superclass.constructor.call(this, {
							id : 'EndDivertPanelWin',
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 245,
							width : 610,
//							maximizable : true,
							title : '终止挪用',
							buttonAlign : 'center',
							buttons : [
										{
											text : '终止',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
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
				var punishAccrual=this.record.data.accrual*4;
				var incomeMoney=this.record.data.projectMoney*4;
				this.formPanel = new Ext.FormPanel({
                            layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						plain : true,
						labelAlign : "right",
							//id : 'SlPersonMainForm',
							defaults : {
								anchor : '96%',
								labelWidth : 80,
								columnWidth : 1,
							    layout : 'column'
							},
							//defaultType : 'textfield',
							items : [{
								name : 'slFundIntent.fundIntentId',
								xtype : 'hidden',
								value : this.record.data.fundIntentId
							},{
								name : 'slFundIntent.projectId',
								xtype : 'hidden',
								value : this.record.data.projectId
							},{
								 columnWidth : 1,
								  labelWidth : 120,
								 layout : 'form',
								 items :[{
										fieldLabel : '终止日期',	
								 	name : 'slFundIntent.factDate',
								    xtype:'datefield',
								  anchor : '45%',
								   format:'Y-m-d',
								   value:new Date(),
								   allowBlank:false
											 
										 }]
								 },
								 	{
									 columnWidth : 1,
									  labelWidth :120,
								     layout : 'form',
								      items :[{
										fieldLabel : '终止说明',	
								 	    name : 'slFundIntent.remark1',
								        anchor : '88%',
								         xtype:'textarea'
								
											 
										 }]
								 }
							
																																			]
						});
				
				//加载表单对应的数据	
				
				
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
				
				this.formPanel.getForm().submit({
					method : 'POST',
					scope :this,
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					url : __ctxPath + '/creditFlow/finance/endDivertSlFundIntent.do',
					success : function(form ,action) {
							Ext.ux.Toast.msg('操作信息', '成功信息保存！');
							var grid=Ext.getCmp("DivertListGrid");
							grid.getStore().reload();
							this.close();
					},
					failure : function(form ,action) {
						 Ext.MessageBox.show({
			            title : '操作信息',
			            msg : '信息保存出错，请联系管理员！',
			            buttons : Ext.MessageBox.OK,
			            icon : 'ext-mb-error'
			        });
					}
				})
			}//end of save

		});

