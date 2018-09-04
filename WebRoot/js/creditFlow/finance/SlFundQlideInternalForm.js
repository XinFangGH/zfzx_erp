/**
 * @author
 * @createtime
 * @class SlFundQlideForm
 * @extends Ext.Window
 * @description SlFundQlide表单
 * @company 智维软件
 */
SlFundQlideInternalForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SlFundQlideInternalForm.superclass.constructor.call(this, {
							id : 'SlFundQlideInternalFormWin',
							layout : 'fit',
							items : this.formPanel,
							border : false,
							modal : true,
							height : 290,
							width : 680,
//							maximizable : true,
							title : '己方账户资金互转记录 ',
							buttonAlign : 'center',
							buttons : [{
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
							// id : 'SlFundQlideForm',
							defaults : {
								anchor : '96%',
								labelWidth : 85,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [{
								name : 'slFundQlide.fundQlideId',
								xtype : 'hidden',
								value : this.fundQlideId == null
										? ''
										: this.fundQlideId
							},  {
								     columnWidth : 1,
								     layout : 'form',
									 items :[{
									 fieldLabel : '转出银行',
											name : 'slFundQlide.bankName',
											id:'slFundQlide.bankName',
											xtype:'textfield',
											anchor : '99%',
											  readOnly : true
									 }
									 
									 ]}
									 ,  { columnWidth : .5,
								     layout : 'form',
									 items :[
//									 	{
//								        fieldLabel : '资金流水号',
//							        	name : 'slFundQlide.glideNum',
//								        allowBlank : false,
//								        xtype:'numberfield',
//								        anchor : '98%'
//									},
										{
										   fieldLabel : '转出账户名称',
											name : 'slFundQlide.openName',
											id:'slFundQlide.openName',
											xtype:'textfield',
											anchor : '98%',
										    readOnly : true
										},
												 {
										   fieldLabel : '转出时间',
											name : 'slFundQlide.factDate',
											allowBlank : false,
											format : 'Y-m-d H:i:s',
											xtype : 'datetimefield',
											anchor : '98%'
										}]}
							, {  columnWidth : .5,
								     layout : 'form',
									 items :[ {
												fieldLabel : '转出账号',
												name : 'slFundQlide.myAccount',
												id:'dialAccounts',
												maxLength : 50,
												allowBlank : false,
												anchor : '98%',
												xtype:'trigger',
												editable : false,
											    triggerClass :'x-form-search-trigger',
										     	onTriggerClick : function(){
												selectAccountlForm(selectAccountkLinkMan2);
											}
												},{
									 	
									 	     	 // xtype:'dickeycombo',
										       	xtype:'combo',
										   //       mode : 'local',
										    //      nodeKey :'capitalkind',
									              editable : false,
								                  width : 70,
								                  hiddenName:"slFundQlide.currency",
								                  store : new Ext.data.ArrayStore({
											                autoLoad : true,
											                baseParams : {
											                    nodeKey : "capitalkind"
											                },
											                url : __ctxPath + '/system/loadItemByNodeKey1Dictionary.do',
											                fields : ['itemId', 'itemName']
											            }),
											       triggerAction : "all",
							                	  fieldLabel : '币种',
							                	   displayField : 'itemId',
									              valueField : 'itemName',
							                      anchor : '98%',
							                	  allowBlank:false,
									          	  name : 'slFundQlide.currency',
									          	  listeners : {
													afterrender : function(combox) {						        
														combox.clearInvalid();
														var st = combox.getStore();
														st.on("load", function() {
															var record = st.getAt(0);
															var v = record.data.itemId;
															combox.setValue(v);
														})
													}
					
												}
										   
											
									
									}]}, {
										columnWidth : .45,
										labelWidth :85,
								        layout : 'form',
									    items :[{
											fieldLabel : '转出金额',
											fieldClass:'field-align',
											id :'slFundQlidepayMoney',
											allowBlank : false,
											xtype:'textfield',
											allowNegative: false, // 允许负数 
											anchor : '98%',
											listeners: {  
												      scope :this,
													  change: function(p){
													  	var value= p.getValue();
													    this.getCmpByName("slFundQlide.payMoney").setValue(value);
														p.setValue(Ext.util.Format.number(value,'0,000.00'))
													
													  		
													  }																		  
																		  
																		  }
											
												},
									{
									    xtype : 'hidden',
										name : 'slFundQlide.payMoney'
										
									}]},{
										 columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :18,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
													
								 }]},{
										columnWidth : .5,
								        layout : 'form',
								        labelWidth :86,
									    items :[{
										   fieldLabel : '是否项目相关',
											name : 'slFundQlide.isProject',
							//				 allowBlank:false,
											xtype : 'combo',
											allowBlank : false,
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											editable : false,
											store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : [["是", "是"],
															["否", "否"]]
												}),
											value : "是"	,
											triggerAction : "all",
											hiddenName : "slFundQlide.isProject",
											fieldLabel : '是否项目相关',
											anchor : '98%'
									}]},{
							         columnWidth : 1,
								     layout : 'form',
									 items :[ {
										   fieldLabel : '转入银行',
											name : 'slFundQlide.opBankName',
											xtype:'textfield',
											readOnly : true,
											id:'opBankName',
											anchor : '99%'
										}
									]
									},{
							          columnWidth : .5,
								     layout : 'form',
									 items :[{
										   fieldLabel : '转入账户名称',
											name : 'slFundQlide.opOpenName',
											id :'opOpenName',
											readOnly : true,
											xtype:'textfield',
											anchor : '98%'
								}]
									}
									,{
							           columnWidth : .5,
								     layout : 'form',
									 items :[ {
										   fieldLabel : '转入账号',
											name : 'slFundQlide.opAccount',
												id:'opAccount',
												maxLength : 50,
												allowBlank : false,
												anchor : '98%',
												xtype:'trigger',
												editable : false,
											    triggerClass :'x-form-search-trigger',
										     	onTriggerClick : function(){
												selectAccountlForm(selectopaccount);
												}
										}]
									}
							,{
							columnWidth : 1,
								     layout : 'form',
									 items :[{
										   fieldLabel : '交易摘要',
											name : 'slFundQlide.transactionType',
											xtype:'textfield',
											anchor : '99%'
									}]
									}
								 ]
						});
				// 加载表单对应的数据
				if (this.fundQlideId != null && this.fundQlideId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath
										+ '/creditFlow/finance/getSlFundQlide.do?fundQlideId='
										+ this.fundQlideId,
								root : 'data',
								preName : 'slFundQlide',
								scope :this,
								success:function(resp,options){
             						var result=Ext.decode(resp.responseText);
             					//	alert(result.data.factDate);
             						Ext.getCmp('dialAccounts').setValue(result.data.myAccount);
             						Ext.getCmp('slFundQlideincomeMoney').setValue(Ext.util.Format.number(result.data.incomeMoney,'0,000.00'));
             						Ext.getCmp('slFundQlidepayMoney').setValue(Ext.util.Format.number(result.data.payMoney,'0,000.00'));
								}
							});
				}

			},// end of the initcomponents

			/**
			 * 重置
			 * 
			 * @param {}
			 *            formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
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
							url : __ctxPath + '/creditFlow/finance/saveInternalqlideSlFundQlide.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('SlFundQlideGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});

var selectAccountkLinkMan2=function(a,b,c){
	Ext.getCmp('dialAccounts').setValue(a) ;
	Ext.getCmp('slFundQlide.openName').setValue(c) ;
	Ext.getCmp('slFundQlide.bankName').setValue(b) ;
	
}
var selectopaccount=function(a,b,c){
	Ext.getCmp('opAccount').setValue(a) ;
	Ext.getCmp('opOpenName').setValue(c) ;
	Ext.getCmp('opBankName').setValue(b) ;
	
}
var selectAccountkLinkMan3=function(a){
	Ext.getCmp('recAccounts').setValue(a) ;
}