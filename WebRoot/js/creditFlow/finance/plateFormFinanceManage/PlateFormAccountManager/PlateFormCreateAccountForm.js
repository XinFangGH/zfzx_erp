//PlateFormCreateAccountForm
PlateFormCreateAccountForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
			
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				PlateFormCreateAccountForm.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 300,
							width : 640,
//							maximizable : true,
							title : '开通平台账户',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										hidden:this.isAllReadOnly,
										disabled:this.isAllReadOnly,
										handler : this.save
									}, {
										text : '重置',
										iconCls : 'btn-reset',
										scope : this,
										hidden:this.isAllReadOnly,
										disabled:this.isAllReadOnly,
										handler : this.reset
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
									columnWidth : 1,
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
									 columnWidth : 1,
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
									 columnWidth : 1,
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
									 columnWidth : 1,
								     layout : 'form',
									 items :[{
									 	    fieldLabel : '账户类型',
											xtype : 'combo',
											allowBlank : false,
											hiddenName : "obSystemAccount.accountType",
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											triggerAction : "all",
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
									 columnWidth : 1,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '账户名',
											allowBlank : false,
											name : 'obSystemAccount.accountName',
											anchor : '98%'
										  
									 }]
								},{  
									 columnWidth : .95,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '账户期初金额',
											allowBlank : false,
											name : 'obSystemAccount.totalMoney',
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
								 },{
										 html:"<div style='padding:10px 0px 0px 100px;color:red;'  >注：默认期初金额0元，填写了具体金额后，在交易明细中会自动生成一笔调账记录!</div> "
								 }]
						});
			},

			/**
			 * 重置
			 * 
			 * @param {}
			 *  formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
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
			$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/creditFlow/creditAssignment/bank/savePlateFormAccountObSystemAccount.do',
							callback : function(fp, action) {
								this.close();
							}
						});
			}// end of save
		});