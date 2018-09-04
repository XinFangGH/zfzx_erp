//PlateFormAccountWithdraw.js
PlateFormAccountWithdraw = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				PlateFormAccountWithdraw.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 700,
//							maximizable : true,
							title : '账户取现',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
										scope : this,
										hidden:this.isAllReadOnly,
										disabled:this.isAllReadOnly,
										handler : this.cancel
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						    plain : true,
						    labelAlign : "right",
							defaults : {
								anchor : '96%',
								labelWidth : 110,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [{  
									columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付名称',
											allowBlank : false,
											name : 'thirdPayName',
											anchor : '98%',
											readOnly:true,
											value:this.thirdPayName
											
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付类型',
											allowBlank : false,
											name : 'thirdPayType',
											anchor : '98%',
											readOnly:true,
											value:this.thirdPayType
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '商户号',
											allowBlank : false,
											name : 'plateFormnumber',
											anchor : '98%',
											readOnly:true,
											value:this.plateFormnumber
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
									 	    fieldLabel : '账户类型',
											xtype : 'combo',
											allowBlank : false,
											hiddenName : "obSystemAccount.accountType",
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											readOnly:true,
											editable : false,
											store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : [["平台普通资金账户", "plateFormAccount"],
															["平台风险保证金账户", "plateFormRiskAccount"],
															["担保账户", "plateFormRiskAccount"]]
												}),
											anchor :'98%',
											value:this.accountType
											
								     }]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '账户名',
											allowBlank : false,
											name : 'obSystemAccount.accountName',
											anchor : '98%',
											readOnly:true,
											value:this.accountName
										  
									 },{
									    xtype:'hidden',
									    name:'obAccountDealInfo.accountId',
									    value:this.accountId
									 }]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '账号',
											allowBlank : false,
											readOnly:true,
											name : 'obSystemAccount.accountNumber',
											anchor : '98%',
											value:this.accountNumber
										  
									 }]
								},{  
									 columnWidth : .45,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '账户余额',
											allowBlank : false,
											readOnly:true,
											name : 'obSystemAccount.totalMoney',
											anchor : '98%',
											value:this.balanceMoney
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   },{  
									 columnWidth : .45,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '取现金额',
											allowBlank : false,
											name : 'obAccountDealInfo.payMoney',
											anchor : '98%',
											value:0
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   }]
						});
						
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *  window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
			   var openNewPage=this.openNewPage;
			   var accountId=this.accountId;
			   var accountType=this.accountType;
			   var thirdPayName=this.thirdPayName
			   var thirdPayType=this.thirdPayTypeName;
			   var plateFormnumber=this.platFormNumber;
			   var accountName=this.accountName;
			   var accountId=this.accountId;
			   var accountNumber=this.accountNumber;
			   var balanceMoney=this.balanceMoney;
			   var refreshPanel =this.refreshPanel;
			   var rerchargeMoney=this.formPanel.getCmpByName("obAccountDealInfo.payMoney").getValue();
			   /*var bankCode=this.formPanel.getCmpByName("bankcode").getValue();*/
			   if(openNewPage==1){
			   		if(eval(rerchargeMoney)==eval(0)){
			   			Ext.ux.Toast.msg('操作信息','取现金额不能为0');
			   	    	return;
			   		}else{
			   			window.open(
										__ctxPath + '/creditFlow/creditAssignment/bank/openNewPageSaveDealInfoObSystemAccount.do?accountId='+ accountId+'&rerchargeMoney='+rerchargeMoney+'&bankCode='+bankCode,
										'平台普通资金账户取现',
										'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no',
										'_blank');
			   		}
			   }else{
			       this.formPanel.getForm().submit({
							clientValidation: false, 
							scope : this,
							method : 'post',
							waitMsg : '数据正在提交，请稍后...',
							scope: this,
							url : __ctxPath + '/creditFlow/creditAssignment/bank/normalWithDrawSaveDealInfoObSystemAccount.do',
							success : function(fp, action) {
								var object = Ext.util.JSON.decode(action.response.responseText)
								Ext.ux.Toast.msg('操作信息',object.msg);
								refreshPanel.getStore().reload();
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
						});
			   }
			}// end of save
		});