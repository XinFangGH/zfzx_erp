/**
 * @author
 * @createtime
 * @class SlFundQlideForm
 * @extends Ext.Window
 * @description SlFundQlide表单
 * @company 智维软件
 */
VirtualReconciliationForm = Ext.extend(Ext.Window, {
			isIncomeMoneyReadOnly : false,
			isPayMoneyReadOnly : false,
			// 构造函数
			constructor : function(_cfg) {
			
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				VirtualReconciliationForm.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 640,
							title : '虚拟对账详细信息',
							buttonAlign : 'center',
							buttons : [{
										text : ' 对账',
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
				if(this.fundType == 'principalLending') {
					this.isIncomeMoneyReadOnly = true;
				}else if (this.fundType == 'principalRepayment') {
					this.isPayMoneyReadOnly
				}
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
							}, { columnWidth : .5,
								     layout : 'form',
									 items :[{
									 	
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
											 format : 'Y-m-d',
											xtype : 'datefield',
											anchor : '98%'
									}]},{ 
										     columnWidth : .46,
										     layout : 'form',
											 items :[{
										    fieldLabel : '收入金额',
											xtype:'textfield',
											fieldClass:'field-align',
											id:'slFundQlideincomeMoney',
											allowNegative: false, // 允许负数 
											readOnly : this.isIncomeMoneyReadOnly,
											anchor : '98%',
											listeners: { 
											  	scope : this,
												change: function(p){
												  	var value= p.getValue();
													p.setValue(Ext.util.Format.number(value,'0,000.00'))
													this.getCmpByName("slFundQlide.incomeMoney").setValue(value);
												},
												afterrender : function(obj) {
													if(this.fundType == 'principalRepayment') {
														obj.setValue(Ext.util.Format.number(this.incomeMoney,'0,000.00'));
													}
												}
											}
									},{
									    xtype : 'hidden',
										name : 'slFundQlide.incomeMoney',
										value : this.incomeMoney != null?this.incomeMoney:null
									}]},{
													 columnWidth : .04, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth :13,
													items : [{
														fieldLabel : "<span style='margin-left:1px'>元</span> ",
														labelSeparator : '',
														anchor : "100%"
													
												 }]},{
									 columnWidth : .46,
								     layout : 'form',
									 items :[
											 {
											fieldLabel : '支出金额',
											id :'slFundQlidepayMoney',
											xtype:'textfield',
											fieldClass:'field-align',
											allowNegative: false, // 允许负数 
											anchor : '98%',
											readOnly : this.isPayMoneyReadOnly,
											listeners: {  
											      scope :this,
												  change: function(p){
													  var value= p.getValue();
													  this.getCmpByName("slFundQlide.payMoney").setValue(value);
													  p.setValue(Ext.util.Format.number(value,'0,000.00'))
											 	  },
													afterrender : function(obj) {
											 		  	if(this.fundType == 'principalLending') {
											 		  		obj.setValue(Ext.util.Format.number(this.payMoney,'0,000.00'));
											 		  	}
													}																		  
											 }
											
												},
									{
									    xtype : 'hidden',
										name : 'slFundQlide.payMoney',
										value : this.payMoney != null?this.payMoney:null
										
									}]},{
										 columnWidth : .04, // 该列在整行中所占的百分比
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
											readOnly:this.isAllReadOnly,
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
															readOnly:this.isAllReadOnly,
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
											readOnly:this.isAllReadOnly,
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
			var slFundQlideincomeMoney=	Ext.getCmp("slFundQlideincomeMoney").getValue();
			var slFundQlidepayMoney=Ext.getCmp("slFundQlidepayMoney").getValue();
			if((slFundQlideincomeMoney==null || slFundQlideincomeMoney=="")&&(slFundQlidepayMoney==null || slFundQlidepayMoney=="")){
				Ext.Msg.alert("","收入与支出不能同时为空");
			}else{
				$postForm({
					formPanel : this.formPanel,
					params : {
						fundIntentId : this.fundIntentId,
						fundType : this.fundType
					},
					scope : this,
					url : __ctxPath + '/creditFlow/finance/autoReconcileSlFundIntent.do',
					callback : function(fp, action) {
						if(typeof(this.object) != 'undefined' && this.object != null) {
							var factDate = this.getCmpByName('slFundQlide.factDate').getValue();
		             		this.object.getCmpByName('slSmallloanProject.startDate').setValue("");
			             	this.object.getCmpByName('slSmallloanProject.startDate').setValue(Ext.util.Format.date(factDate, 'Y-m-d'));
			             	Ext.Ajax.request({
			        			url : __ctxPath + "/project/updateStartDateSlSmallloanProject.do",
			        			method : 'POST',
			        			scope:this,
			        			success : function(response, request) {
			             			
			        			},
			        			failure : function(response,request) {
			        				Ext.ux.Toast.msg('操作信息', '更新放款日期失败!');
			        			},
			        			params : {
			        				projectId : this.projectId,
			        				factDate : Ext.util.Format.date(factDate, 'Y-m-d')
			        			}
			        		});
		             	}
						var tabflag = this.tabflag;
						var gridPanel = this.gridPanel;
						if (gridPanel != null) {
							gridPanel.getStore().reload();
							gridPanel.getView().refresh();
						}
						var gridPanel1 = Ext.getCmp('SlFundIntentGrid1'+tabflag);
						var gridPanel2 = Ext.getCmp('SlFundIntentGrid2'+tabflag);
						var gridPanel3 = Ext.getCmp('SlFundIntentGrid3'+tabflag);
						var gridPanel4 = Ext.getCmp('SlFundIntentGrid4'+tabflag);
							
		                if (gridPanel1 != null) {
		                	gridPanel1.getStore().reload();
			            }
		             	if (gridPanel2 != null) {
				           gridPanel2.getStore().reload();
		             	}
		             	if (gridPanel3 != null) {
				             gridPanel3.getStore().reload();
		             	}
		             	if (gridPanel4 != null) {
				             gridPanel4.getStore().reload();
		             	}
						this.close();
					}
				});
						
			}
			}// end of save
        
		});

