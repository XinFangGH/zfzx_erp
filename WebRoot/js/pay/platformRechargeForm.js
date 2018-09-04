platformRechargeForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				platformRechargeForm.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 179,
							width : 557,
//							maximizable : true,
							title : '账户充值',
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
							items : [/*{  
									columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '第三方支付名称',
											allowBlank : false,
											name : 'thirdPayName',
											anchor : '98%',
											readOnly:true,
											value:"联动优势支付"
											
										  
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
											value:"账户托管类型"
										  
									}]
								},*/{  
									 columnWidth : 0.5,
								     layout : 'form',
								     hidden:this.isHidden,
									 items :[{
									 	    fieldLabel : '支付方式',
											xtype : 'combo',
											allowBlank :this.isHidden?true: false,
											hiddenName : "payType",
											mode : 'local',
											displayField : 'itemName',
											triggerAction : "all",
											valueField : 'itemId',
											editable : false,
											store : new Ext.data.SimpleStore({
												fields : ['itemName','itemId'],
													data : [["企业网银", "B2BBANK"],
															["个人借记卡网银", "B2CDEBITBANK"]]
												}),
											anchor :'98%',
											listeners : {
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
																var record = st.getAt(0);
																var v = record.data.itemId;
																combox.setValue(v);
																combox.fireEvent("select",combox, record, 0);
															})
													combox.clearInvalid();
												},
												select : function(combox, record, index) {
													var v = record.data.itemId;
													var arrStore = new Ext.data.ArrayStore({
														url : __ctxPath+ '/pay/getBankListPay.do',
														baseParams:{payType : v},
														fields : ['itemId','itemName'],
														autoLoad : true
													});
													var opr_obj = this.ownerCt.ownerCt.getCmpByName('bankcode')
													opr_obj.enable();
													opr_obj.setValue();
													opr_obj.store.removeAll()
													opr_obj.store=arrStore;
													arrStore.load({"callback" : test});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														if (typeof(arrStore.getAt(0)) != "undefined") {
															var itmeId = arrStore.getAt(0).data.itemId;
															var itemName = arrStore.getAt(0).data.itemName;
															opr_obj.setRawValue(itemName);
															opr_obj.setValue(itmeId);
															var recordN = arrStore.getAt(0);
															opr_obj.fireEvent("select",opr_obj, arrStore.getAt(0), 0);
														}
													}
												}
											}
								     }]
								},{  
									 columnWidth :0.5,
								     layout : 'form',
								     hidden:this.isHidden,
									 items :[{
											xtype :'combo',
											fieldLabel : '支付银行',
											displayField : 'itemName',
											allowBlank :this.isHidden?true: false,
											valueField : 'itemId',
											triggerAction : 'all',
											mode : 'remote',
											disable : true,
											hiddenName : "bankcode",
											editable : false,
											store: new Ext.data.ArrayStore({
														url : __ctxPath+ '/pay/getBankListPay.do',
														fields : ['itemId','itemName'],
														autoLoad : true
													}),
											anchor : "100%",
											listeners : {
												scope : this,
												select : function(combox, record, index) {
													var v = record.data.itemId;
													var arrStore = new Ext.data.ArrayStore({
																url : __ctxPath+ '/pay/getBankListPay.do',
																fields : ['itemId','itemName'],
																baseParams : {
																	payType : v
																}
															});
													var opr_obj = this.ownerCt.get(0).getCmpByName('payType');
													opr_obj.store = arrStore;
													arrStore.load({"callback" : test});
													function test() {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														if (typeof(arrStore.getAt(0)) != "undefined") {
															var itmeId = arrStore.getAt(0).data.itemId;
															var itemName = arrStore.getAt(0).data.itemName;
															opr_obj.setRawValue(itemName);
															opr_obj.setValue(itmeId);
														}
													}
												}
											}
										  
									},{
										xtype:'hidden',
										name:"bankName"
									}]
								},{  
									 columnWidth : .95,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '充值金额',
											allowBlank : false,
											name : 'incomMoney',
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
			   var refreshPanel =this.refreshPanel;
			   var rerchargeMoney=this.formPanel.getCmpByName("incomMoney").getValue();
			   var bankCode=this.formPanel.getCmpByName("bankcode").getValue();
			   var payType=this.formPanel.getCmpByName("payType").getValue();
			   if(bankCode==null||bankCode==""){
			   	    Ext.ux.Toast.msg('操作信息','请选择支付银行');
			   	    return;
			   }else if(eval(rerchargeMoney)==eval(0)){
			   		Ext.ux.Toast.msg('操作信息','充值金额不能为0');
			   	    return;
			   }else{
			   			window.open(__ctxPath + '/pay/platformRecharePay.do?rerchargeMoney='+rerchargeMoney+'&bankcode='+bankCode+'&payType='+payType,
										'平台账户充值','_blank');
								refreshPanel.getStore().reload();
								this.close();			
			   	}			  
			}// end of save
		});