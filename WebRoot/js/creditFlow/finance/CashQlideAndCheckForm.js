/**
 * @author
 * @createtime
 * @class SlFundQlideForm
 * @extends Ext.Window
 * @description SlFundQlide表单
 * @company 智维软件
 */
CashQlideAndCheckForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
			
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				CashQlideAndCheckForm.superclass.constructor.call(this, {
							id : 'CashQlideAndCheckFormWin',
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 640,
//							maximizable : true,
							title : '现金流水对账',
							buttonAlign : 'center',
							buttons : [{
										text : '对账',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
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
								labelWidth : 90,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [{
								name : 'slFundQlide.fundQlideId',
								xtype : 'hidden',
								value : this.fundQlideId == null
										? ''
										: this.fundQlideId
							}
									 ,  { columnWidth : .5,
								     layout : 'form',
									 items :[
{
									 	
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
							                	   readOnly : this.ischeck,
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
									columnWidth : .5,
								     layout : 'form',
									 items :[	 {
										   fieldLabel : '到账时间',
											name : 'slFundQlide.factDate',
											allowBlank : false,
											value :new Date,
											 format : 'Y-m-d H:i:s',
											xtype : 'datetimefield',
											anchor : '98%'
									}]},{ 
										     columnWidth : .47,
										     layout : 'form',
											 items :[{
										    fieldLabel : '收入金额',
											xtype:'textfield',
											fieldClass:'field-align',
											id:'slFundQlideincomeMoney',
											readOnly:true, 
											value: Ext.util.Format.number(this.incomeMoney,'0,000.00'),
											anchor : '98%'
										
													},{
													    xtype : 'hidden',
														name : 'slFundQlide.incomeMoney',
														value :this.incomeMoney
														
													
												}]},{
													 columnWidth : .03, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth :13,
													items : [{
														fieldLabel : "<span style='margin-left:1px'>元</span> ",
														labelSeparator : '',
														anchor : "100%"
													
												 }]},{
									 columnWidth : .47,
								     layout : 'form',
									 items :[
											 {
											fieldLabel : '支出金额',
											id :'slFundQlidepayMoney',
											xtype:'textfield',
											fieldClass:'field-align',
											value: Ext.util.Format.number(this.payMoney,'0,000.00'),
											anchor : '98%',
											readOnly : true
											
											
												},
									{
									    xtype : 'hidden',
										name : 'slFundQlide.payMoney',
										value :this.payMoney
										
									}]},{
										 columnWidth : .03, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :13,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
													
								 }]},{ 
									columnWidth : .5,
								     layout : 'form',
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
									}]},{ columnWidth : .5,
													     layout : 'form',
														 items :[{
														   fieldLabel : '交易类型',
															name : 'slFundQlide.bankTransactionType',
															hiddenName : "slFundQlide.bankTransactionType",
															xtype:'dicIndepCombo',
														   editable : false,
															lazyInit : false,
															forceSelection : false,
															nodeKey : "bankTransactionType",
															anchor : '98%',
													  listeners : {
													  	   scope :this,
															afterrender : function(combox) {
																var st = combox.getStore();
																if(this.fundQlideId == null || this.fundQlideId == 'undefined'){
																	combox.clearInvalid();
																	st.on("load", function() {
																		var record = st.getAt(0);
																		var v = record.data.dicKey;
																		combox.setValue(v);
																	})
																}else{
																		st.on("load", function() {
																			combox.setValue(combox.getValue());
																		})
																}
																
															}
							
														}
											}]}, 
										{  columnWidth : 1,
								     layout : 'form',
									 items :[	 {
									   fieldLabel : '交易摘要',
											name : 'slFundQlide.transactionType',
											xtype:'textarea',
											value:this.projectName+"，此项目的"+this.actualChargeType,
											anchor : '99%'
									 }]}
							
						
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
		     var obj=this.obj;
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/creditFlow/finance/savecashqlideAndcheckSlActualToCharge.do?actualChargeId='+this.actualChargeId,
							callback : function(fp, action) {
								var gridPanel =obj;
								if (gridPanel != null) {
									gridPanel.getStore().reload();
									gridPanel.getView().refresh();
								}
							this.close();
							}
						});
						
			
			}// end of save
        
		});

