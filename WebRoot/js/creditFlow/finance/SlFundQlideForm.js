/**
 * @author
 * @createtime
 * @class SlFundQlideForm
 * @extends Ext.Window
 * @description SlFundQlide表单
 * @company 智维软件
 */
SlFundQlideForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SlFundQlideForm.superclass.constructor.call(this, {
							id : 'SlFundQlideFormWin',
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 320,
							width : 680,
//							maximizable : true,
							title : '业务往来资金记录',
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
									},{
								     columnWidth : 1,
								     layout : 'form',
									 items :[{
										    fieldLabel : '我方开户银行',
											name : 'slFundQlide.bankName',
											id:'slFundQlide.bankName',
											xtype:'textfield',
											anchor : '99%',
											readOnly : true
										 }
									 
									 ]},{ 
									 columnWidth : .5,
								     layout : 'form',
									 items :[
	//									 {
	//								        fieldLabel : '资金流水号',
	//							        	name : 'slFundQlide.glideNum',
	//								        allowBlank : false,
	//								        xtype:'numberfield',
	//								        anchor : '98%'
    //									},
										{
										    fieldLabel : '我方户名',
											name : 'slFundQlide.openName',
											id:'slFundQlide.openName',
											xtype:'textfield',
											anchor : '98%',
										    readOnly :true
										},{
										    fieldLabel : '到账时间',
											name : 'slFundQlide.factDate',
											allowBlank : false,
										    readOnly : this.ischeck,
											format : 'Y-m-d H:i:s',
											xtype : 'datetimefield',
											anchor : '98%'
										}]},{  
									 columnWidth : .5,
								     layout : 'form',
								     labelWidth : 90,
									 items :[{
											fieldLabel : '我方账号',
											name : 'slFundQlide.myAccount',
											id:'dialAccounts',
											maxLength : 50,
											allowBlank : false,
											anchor : '98%',
											xtype:'trigger',
											editable : false,
											readOnly : this.isAllReadOnly,
										    triggerClass :'x-form-search-trigger',
									     	onTriggerClick : function(){
											selectAccountlForm(selectAccountkLinkMan2);
											}},{									 	
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
									 columnWidth : .45,
								     layout : 'form',
									 items :[ {
										    fieldLabel : '收入金额',
											xtype:'textfield',
											fieldClass:'field-align',
											id:'slFundQlideincomeMoney',
											allowNegative: false, // 允许负数 
											readOnly : this.ischeck,
											anchor : '98%',
											listeners: { 
												  	scope : this,
													change: function(nf){
													  	if(nf.getValue() !=""){
													  		var slFundQlidepayMoney = Ext.getCmp('slFundQlidepayMoney');
													  		slFundQlidepayMoney.setValue(null);
													  		this.getCmpByName("slFundQlide.payMoney").setValue(null);
													  	}
													  		
//													  	var value= nf.getValue();
//														nf.setValue(Ext.util.Format.number(value,'0,000.00'))
//														this.getCmpByName("slFundQlide.incomeMoney").setValue(value);
														
												     var value= nf.getValue();
													 var index=value.indexOf(".");
												     if(index<=0){ //如果第一次输入 没有进行格式化
													       nf.setValue(Ext.util.Format.number(value,'0,000.00'))
													       this.getCmpByName("slFundQlide.incomeMoney").setValue(value);
													 }else{
													       if(value.indexOf(",")<=0){
													       	     var ke=Ext.util.Format.number(value,'0,000.00')
													       	     nf.setValue(ke);
													             this.getCmpByName("slFundQlide.incomeMoney").setValue(value);
													       }else{
													       	    var last=value.substring(index+1,value.length);
													       	    if(last==0){
													       	       var temp=value.substring(0,index);
													       	       temp=temp.replace(/,/g,"");
													       	       this.getCmpByName("slFundQlide.incomeMoney").setValue(temp);
													       	    }else{
													       	      var temp=value.replace(/,/g,"");
													              this.getCmpByName("slFundQlide.incomeMoney").setValue(temp);
													       	    }
													       }
													 }
									
													  }							  
																		  
											}
									},{
									    xtype : 'hidden',
										name : 'slFundQlide.incomeMoney'
										
									
									}]},{
										 columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :18,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
													
								 }]} ,{
										columnWidth : .45,
										labelWidth : 91,
									     layout : 'form',
										 items :[ {
											fieldLabel : '支出金额',
											id :'slFundQlidepayMoney',
											fieldClass:'field-align',
											xtype:'textfield',
											allowNegative: false, // 允许负数 
											anchor : '98%',
											readOnly : this.ischeck,
											listeners: {  
										      scope :this,
											  change: function(nf){
											  	if(nf.getValue() !=""){
											  		var slFundQlideincomeMoney = Ext.getCmp('slFundQlideincomeMoney');
											  		this.getCmpByName("slFundQlide.incomeMoney").setValue(null);
											  		slFundQlideincomeMoney.setValue(null);
											  		}
													  		
													  		
//													  	var value= p.getValue();
//													    this.getCmpByName("slFundQlide.payMoney").setValue(value);
//														p.setValue(Ext.util.Format.number(value,'0,000.00'))
													
													  		
												 var value= nf.getValue();
												var index=value.indexOf(".");
											    if(index<=0){ //如果第一次输入 没有进行格式化
											       nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											       this.getCmpByName("slFundQlide.payMoney").setValue(value);
											    }
											    else{
											       
											       if(value.indexOf(",")<=0){
											       	     var ke=Ext.util.Format.number(value,'0,000.00')
											       	     nf.setValue(ke);
											             this.getCmpByName("slFundQlide.payMoney").setValue(value);
											       }
											       else{
											       	    var last=value.substring(index+1,value.length);
											       	    if(last==0){
											       	       var temp=value.substring(0,index);
											       	       temp=temp.replace(/,/g,"");
											       	       this.getCmpByName("slFundQlide.payMoney").setValue(temp);
											       	    }
											       	    else{
											       	      var temp=value.replace(/,/g,"");
											              this.getCmpByName("slFundQlide.payMoney").setValue(temp);
											       	    }
											       }
											    }
									
													  }																			  
																		  
																		  }
											
												},
									{
									    xtype : 'hidden',
										name : 'slFundQlide.payMoney'
										
									}]},{
										 columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :13,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
													
								 }]},{
									columnWidth : .5,
								     layout : 'form',
									 items :[ {
										fieldLabel : '对方账号',
									   name : 'slFundQlide.opAccount',
									   id :'slFundQlide.opAccount',
										flex : 1,
										displayField : 'accountnum',
									   valueField : 'accountnum',
									   readOnly : this.isAllReadOnly,
									    triggerAction : 'all',
										xtype : 'combo',
										 mode : 'local',
										store : new Ext.data.JsonStore({
										url : __ctxPath + '/creditFlow/customer/common/listbycheckboxEnterpriseBank.do',
										autoLoad : true,
										fields : [{
												name : 'id'
											},{
												name : 'bankname'
											},{
												name : 'name'
											},{
												name : 'accountnum'
											}
												],
											root:'result',
					                       totalProperty :'totalCounts',
					                       listeners : {
													scope : this,
													'load' : function(s, r, o) {
														if (s.getCount() == 0) {
																	  Ext.getCmp('slFundQlide.opAccount')
																	.markInvalid('没有查找到匹配的记录');
														}
													}
												}
										     	}),
										anchor : '98%',
										triggerClass : 'x-form-search-trigger',
									onTriggerClick : function() {
										                    var setop=function(a,b,c){
										                      Ext.getCmp("slFundQlide.opOpenName").setValue(c);
										                       Ext.getCmp("slFundQlide.opBankName").setValue(b);
										                         Ext.getCmp("slFundQlide.opAccount").setValue(a);
										                    }
										
															selectCustomAccount(setop);
														
													},
									listeners : {
														scope : this,
														'select' : function(combo, record,
																index) {
														 	  Ext.getCmp("slFundQlide.opOpenName").setValue(record.data.name);
										                       Ext.getCmp("slFundQlide.opBankName").setValue(record.data.bankname);
															 
														}
													}
													
										
									
							
								
								},{
										   fieldLabel : '对方账号名称',
											name : 'slFundQlide.opOpenName',
											id:'slFundQlide.opOpenName',
											xtype:'textfield',
											readOnly : true,
											anchor : '98%'
										}
//										,{
//								           fieldLabel : '银行交易类型',
//											name : 'slFundQlide.bankTransactionType',
//											hiddenName : "slFundQlide.bankTransactionType",
//											xtype:'dicIndepCombo',
//										   editable : false,
//											nodeKey : "bankTransactionType",
//											anchor : '98%',
//											 listeners : {
//													  	   scope :this,
//															afterrender : function(combox) {
//																var st = combox.getStore();
//																if(this.fundQlideId == null || this.fundQlideId == 'undefined'){
//																	combox.clearInvalid();
//																	st.on("load", function() {
//																		var record = st.getAt(0);
//																		var v = record.data.dicKey;
//																		combox.setValue(v);
//																	})
//																}else{
//																		st.on("load", function() {
//																			combox.setValue(combox.getValue());
//																		})
//																}
//																
//															}
//							
//														}
//
//								}
								]},{
										columnWidth : .5,
										labelWidth : 90,
									     layout : 'form',
										 items :[ {
										   fieldLabel : '对方开户银行',
											name : 'slFundQlide.opBankName',
											id:'slFundQlide.opBankName',
											xtype:'textfield',
											readOnly : true,
											anchor : '98%'
										},{
										   fieldLabel : '是否业务款项',
											name : 'slFundQlide.isProject',
							//				 allowBlank:false,
											xtype : 'combo',
											allowBlank : false,
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											editable : false,
											readOnly : this.isAllReadOnly,
											store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : [["是", "是"],
															["否", "否"]]
												}),
											value : "是"	,
											triggerAction : "all",
											hiddenName : "slFundQlide.isProject",
											anchor : '98%'
									}]}
							
							,{
										columnWidth : 1,
										labelWidth : 85,
									     layout : 'form',
										 items :[ {
										   fieldLabel : '交易摘要',
											name : 'slFundQlide.transactionType',
											xtype:'textarea',
											readOnly : this.isAllReadOnly,
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
			var slFundQlideincomeMoney=	Ext.getCmp("slFundQlideincomeMoney").getValue();
			var slFundQlidepayMoney=Ext.getCmp("slFundQlidepayMoney").getValue();
			if((slFundQlideincomeMoney==null || slFundQlideincomeMoney=="")&&(slFundQlidepayMoney==null || slFundQlidepayMoney=="")){
			
				Ext.Msg.alert("","收入与支出不能同时为空");
			}else{
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/creditFlow/finance/saveSlFundQlide.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('SlFundQlideGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}
			}// end of save

		});

var selectAccountkLinkMan2=function(a,b,c){
	Ext.getCmp('dialAccounts').setValue(a) ;
	Ext.getCmp('slFundQlide.openName').setValue(c) ;
	Ext.getCmp('slFundQlide.bankName').setValue(b) ;
	
}
var selectAccountkLinkMan3=function(a){
	Ext.getCmp('recAccounts').setValue(a) ;
}