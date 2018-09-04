/**
 * @author 
 * @createtime 
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
StartDivertPanel =Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				StartDivertPanel.superclass.constructor.call(this, {
							id : 'StartDivertPanelWin',
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 245,
							width : 610,
//							maximizable : true,
							title : '挪用处理-'+this.record.data.projectName,
							buttonAlign : 'center',
							buttons : [
										{
											text : '启动',
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
				var punishAccrual=Ext.util.Format.number((this.record.data.accrual+this.record.data.managementConsultingOfRate)*4/30,'0000.00');
				var incomeMoney=this.record.data.projectMoney;
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
								value : this.fundIntentId == null ? '' : this.fundIntentId
							},{
								name : 'slFundIntent.projectId',
								xtype : 'hidden',
								value : this.record.data.projectId
							},{
								 columnWidth : .45,
								  labelWidth : 120,
								 layout : 'form',
								 items :[{
									  fieldLabel : '挪用金额',	
								 		xtype:'textfield',
								 		anchor : '100%',
								 		allowBlank:false,
								 		fieldClass:'field-align',
								 		id:'slbankrawMoney',
								 		listeners: {  
									      scope :this,
										  change: function(nf){
									      
										     var value= nf.getValue();
												var index=value.indexOf(".");
											    if(index<=0){ //如果第一次输入 没有进行格式化
											       nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											       this.getCmpByName("slFundIntent.incomeMoney").setValue(value);
											    }
											    else{
											       
											       if(value.indexOf(",")<=0){
											       	     var ke=Ext.util.Format.number(value,'0,000.00')
											       	     nf.setValue(ke);
											             this.getCmpByName("slFundIntent.incomeMoney").setValue(value);
											       }
											       else{
											       	    var last=value.substring(index+1,value.length);
											       	    if(last==0){
											       	       var temp=value.substring(0,index);
											       	       temp=temp.replace(/,/g,"");
											       	       this.getCmpByName("slFundIntent.incomeMoney").setValue(temp);
											       	    }
											       	    else{
											       	      var temp=value.replace(/,/g,"");
											              this.getCmpByName("slFundIntent.incomeMoney").setValue(temp);
											       	    }
											       }
											    }	
											    var inc=this.getCmpByName("slFundIntent.incomeMoney").getValue();
												 if(inc>incomeMoney){
													 this.getCmpByName("slFundIntent.incomeMoney").setValue(0);
													 Ext.getCmp("slbankrawMoney").setValue(0);
											      }
										  }					
								
																		  }
									
									 
								 },{
									    xtype : 'hidden',
										 name : 'slFundIntent.incomeMoney'
										
									}]},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :18,
									items : [{
										fieldLabel : "<span style='margin-left:1px'>元</span> ",
										labelSeparator : '',
										anchor : "100%"
									
									 }]},{
										 columnWidth : .35,
										  labelWidth : 74,
										 layout : 'form',
										 items :[{
										 fieldLabel : '罚息利率',	
									 		xtype:'textfield',
									 		anchor : '100%',
									 		allowBlank:false,
									 		fieldClass:'field-align',
											 name : 'slFundIntent.punishAccrual',
											 value:punishAccrual
											
										}]},{
											 columnWidth : .05, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth :18,
												items : [{
													fieldLabel : "<span style='margin-left:1px'>%</span> ",
													labelSeparator : '',
													anchor : "100%"
												
												 }]},{
								 columnWidth : 1,
								  labelWidth : 120,
								 layout : 'form',
								 items :[{
										fieldLabel : '挪用罚息开始日期',	
								 	name : 'slFundIntent.intentDate',
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
										fieldLabel : '备注',	
								 	    name : 'slFundIntent.remark',
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
					url : __ctxPath + '/creditFlow/finance/saveDivertSlFundIntent.do',
					success : function(form ,action) {
							Ext.ux.Toast.msg('操作信息', '成功信息保存！');
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

