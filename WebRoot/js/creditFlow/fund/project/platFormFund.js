platFormFund = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isReadOnly:false,
	idDefinition:'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isReadOnly) {
			this.isReadOnly = _cfg.isReadOnly;
		}
		if (typeof(_cfg.isStartDateReadOnly)!="undefined") {
			this.isStartDateReadOnly = _cfg.isStartDateReadOnly;
		}else{
			this.isStartDateReadOnly=true
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		var idDefinition1=this.idDefinition;
		Ext.applyIf(this, _cfg);
		this.idDefinition=this.projectId+this.idDefinition;
		var leftlabel = 85;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod="[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i+ ", " + i + "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		platFormFund.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "对接金额",
						fieldClass : 'field-align',
						name : "platFormBpFundProjectMoney",
						readOnly : this.isReadOnly,
						allowNegative : false, // 允许负数
						style : {
							imeMode : 'disabled'
						},
						blankText : "贷款金额不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%",// 控制文本框的长度
						listeners : {
							scope : this,
							afterrender : function(obj) {
								obj.on("keyup")
							},
							change : function(nf,nv,ov) {
								var value = nf.getValue();
								var temp=value;
								var index = value.indexOf(".");
								if(this.ownerCt.ownerCt){
									if(this.ownerCt.ownerCt.getCmpByName('slSmallloanProject.projectMoney')){
										var o = this.ownerCt.ownerCt.getCmpByName('slSmallloanProject.projectMoney');
										var last = value.substring(index + 1,value.length);
										if (last == 0 && index!=-1) {
											temp = value.substring(0, index);
											temp = temp.replace(/,/g, "");
										}else{
											temp = value.replace(/,/g, "");
										}
										if(eval(temp)>eval(o.getValue())){
											Ext.ux.Toast.msg('警告','对接金额大于贷款金额!');
											nf.setValue(ov);
											return;
										}
									}
								}
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									this.getCmpByName("platFormBpFundProject.platFormJointMoney").setValue(value);
								} else {
									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,'0,000.00')
										nf.setValue(ke);
										this.getCmpByName("platFormBpFundProject.platFormJointMoney").setValue(value);
									} else {
										var last = value.substring(index + 1,value.length);
										if (last == 0) {
											temp = value.substring(0, index);
											temp = temp.replace(/,/g, "");
											this.getCmpByName("platFormBpFundProject.platFormJointMoney").setValue(temp);
											nf.setValue(Ext.util.Format.number(temp, '0,000.00'))
										} else {
											temp = value.replace(/,/g, "");
											this.getCmpByName("platFormBpFundProject.platFormJointMoney").setValue(temp);
											nf.setValue(Ext.util.Format.number(temp, '0,000.00'))
										}
									}
								}
								if(typeof(this.projectInfoFinance)!='undefined' && null!=this.projectInfoFinance){
									var projectMoney=this.projectInfoFinance.getCmpByName('slSmallloanProject.projectMoney').getValue()
									if(eval(temp)<eval(projectMoney)){
										var taskSubmitObj=this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName('taskSubmit')
										if(typeof(taskSubmitObj)!='undefined' && null!=taskSubmitObj){
											var taskItems=taskSubmitObj.items;
											for(var i=0;i<taskItems.length;i++){
												if(taskItems.get(i).boxLabel.indexOf('完成')!=-1){
													taskItems.get(i).setValue(false)
												}else{
													taskItems.get(i).setValue(true)
												}
											}
										}
										var money=projectMoney-temp
										this.ownerCt.ownerCt.ownerCt.getCmpByName('ownBpFundProjectMoney').setValue(Ext.util.Format.number(money, '0,000.00'))
										this.ownerCt.ownerCt.ownerCt.getCmpByName('ownBpFundProject.ownJointMoney').setValue(money)
									}else{
										var taskSubmitObj=this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.getCmpByName('taskSubmit')
										if(typeof(taskSubmitObj)!='undefined' && null!=taskSubmitObj){
											var taskItems=taskSubmitObj.items;
											for(var i=0;i<taskItems.length;i++){
												if(taskItems.get(i).boxLabel.indexOf('完成')!=-1){
													taskItems.get(i).setValue(true)
												}else{
													taskItems.get(i).setValue(false)
												}
											}
										}
										this.ownerCt.ownerCt.ownerCt.getCmpByName('ownBpFundProjectMoney').setValue(Ext.util.Format.number(0, '0,000.00'))
										this.ownerCt.ownerCt.ownerCt.getCmpByName('ownBpFundProject.ownJointMoney').setValue(0)
									}
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "platFormBpFundProject.platFormJointMoney"
					}, {
						xtype : "hidden",
						name : "platFormBpFundProject.id"
					},{
						xtype:'hidden',
						id:'investId',
						name:'platFormBpFundProject.investorIds'
					}]
				}, {
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 50,
					defaults : {
						anchor : '96%'
					},
					items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'platFormBpFundProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								value : 449,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {

													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
									}

								}
							}]
				}, {
					columnWidth : .36,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					labelWidth:rightlabel,
					items : [{
						xtype:'dicIndepCombo',
						allowBlank : false,
						fieldLabel : '日期模型',
						readOnly : this.isAllReadOnly,
						anchor : '100%',
						editable : false,
						nodeKey : 'dateModel',
						lazyInit : true,
						lazyRender : true,
						hiddenName : 'platFormBpFundProject.dateMode',
						listeners : {
							scope : this,
							afterrender :function(combox){
								var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
													combox.clearInvalid();
												})
								/*
								var st=com.getStore();
								st.on('load',function(){
									debugger
									com.setValue(st.getAt(0).data.dicKey)
									com.clearInvalid();
								})
								
							*/},
							change : function(combo) {
						    	var yearAccrualRate = this.getCmpByName('platFormBpFundProject.yearAccrualRate')
								var dayAccrualRaten = this.getCmpByName('platFormBpFundProject.dayAccrualRate')
								var yearAccrualnm = this.getCmpByName('platFormBpFundProject.yearManagementConsultingOfRate')
								var dayAccrualRatenm = this.getCmpByName('platFormBpFundProject.dayManagementConsultingOfRate')
							    var yearAccrualnf = this.getCmpByName('platFormBpFundProject.yearFinanceServiceOfRate')
								var dayAccrualRatenf = this.getCmpByName('platFormBpFundProject.dayFinanceServiceOfRate')
								 if(combo.value == 'dateModel_365'){
								 	dayAccrualRaten.setValue(yearAccrualRate.getValue()/365);
								 	dayAccrualRatenm.setValue(yearAccrualnm.getValue()/365);
								 	dayAccrualRatenf.setValue(yearAccrualnf.getValue()/365);
								 }
								  if(combo.value == 'dateModel_360'){
								 	dayAccrualRaten.setValue(yearAccrualRate.getValue()/360);
								 	dayAccrualRatenm.setValue(yearAccrualnm.getValue()/360);
								 	dayAccrualRatenf.setValue(yearAccrualnf.getValue()/360);
								 }
							}
						}
					}]
				},{
				columnWidth : 1,
					layout:'column',
					items : [
					 {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					labelAlign :'right',
					items : [{
						 fieldLabel : "还款方式 ",
						 fieldClass : 'field-align',
						//html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
						anchor : "100%"
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '等额本金',
						// fieldLabel : "计息方式",
						name : 'f1'+this.idDefinition,
						id : "jixifs1" +this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs1"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this
											.getCmpByName('platFormBpFundProject.accrualtype')
											.setValue("sameprincipal");
											  Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								          
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	           // this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setValue("");
							 	    
								              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
								            this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('platFormBpFundProject.payintentPeriod').setDisabled(false);
								         /*     this.getCmpByName('mqhkri1').hide();
							                   this.getCmpByName('mqhkri').show();*/
							                   Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '等额本息',
						anchor : "100%",
						name : 'f1'+this.idDefinition,
						id : "jixifs2" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.accrualtype').setValue("sameprincipalandInterest");
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
								           this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	           //this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('platFormBpFundProject.payintentPeriod').setDisabled(false);
								            /*  this.getCmpByName('mqhkri1').hide();
							                    this.getCmpByName('mqhkri').show();*/
							                     Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '等本等息',
						anchor : "100%",
						name : 'f1'+this.idDefinition,
						id : "jixifs5" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs5"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.accrualtype').setValue("sameprincipalsameInterest");
									if(this.isAllReadOnly==true){
										Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									}else{
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									}
								           this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	          // this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setValue("");
								           // this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('platFormBpFundProject.payintentPeriod').setDisabled(false);
								             this.getCmpByName('platFormBpFundProject.payintentPerioDate').setDisabled(true);
							                     Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(true);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setValue(true)
							                   
							                    
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '按期收息,到期还本',
						name : 'f1'+this.idDefinition,
						id : "jixifs3" +this.idDefinition,
						inputValue : false,
						checked : true,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs3"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.accrualtype').setValue("singleInterest");
									  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
								           this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(false);
								           Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs6"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
							               
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "platFormBpFundProject.accrualtype",
						value : "singleInterest"

					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						
						xtype : 'radio',
						boxLabel : '其他还款方式',
						anchor : "100%",
						name : 'f1'+this.idDefinition,
						id : "jixifs6" +this.idDefinition,
						inputValue : false,
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixifs6"+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.accrualtype').setValue("otherMothod");
									if( this.isAllReadOnly==true){
										 Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									}else{
								    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
								      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
								        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
								          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
								           
								           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									}
								           this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	          // this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setValue("");
								          
							 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
								             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
								            this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("monthPay");
								            
								             this.getCmpByName('platFormBpFundProject.payintentPeriod').setDisabled(false);
								            /*  this.getCmpByName('mqhkri1').hide();
							                    this.getCmpByName('mqhkri').show();*/
							                     Ext.getCmp("jixifs2"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs5"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs3"+this.idDefinition).setValue(false);
							                   Ext.getCmp("jixifs1"+this.idDefinition).setValue(false);
							                   Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
							                   Ext.getCmp("meiqihkrq2"+this.idDefinition).setDisabled(false);
								}
							}
						}
					
					}, {
						xtype : "hidden",
						name : "platFormBpFundProject.payaccrualType",
						value : "monthPay"

					}]
				}]
				
				},{
					columnWidth : 1,
					layout:'column',
						items : [ {
						columnWidth : .1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 85,
						labelAlign :'right',
						items : [{
							 fieldLabel : "还款周期 "
						}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '日',
						name : 'z1'+this.idDefinition,
						id : "jixizq1" + this.idDefinition,
						inputValue : true,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq1"	+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									Ext.getCmp("jixizq2"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									 	this.getCmpByName('platFormBpFundProject.isStartDatePay').setValue("2");
								var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								     
								     
								}else {
								       
								       
								        Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									  this.getCmpByName('platFormBpFundProject.payintentPerioDate').setDisabled(false);
									 }
								       }
								 
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '月',
						name : 'z1'+this.idDefinition,
						id : "jixizq2" +this.idDefinition,
						inputValue : false,
						checked : true,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq2"
										+this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("monthPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '季',
						name : 'z1'+this.idDefinition,
						id : "jixizq3" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq3"
										+this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("seasonPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
								var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '年',
						name : 'z1'+this.idDefinition,
						id : "jixizq4" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq4"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("yearPay");
									Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					}]
				}, {
					columnWidth : .098,
					labelWidth :1,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '自定义周期',
						name : 'z1'+this.idDefinition,
						id : "jixizq6" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : this.isAllReadOnly,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq6"
										+ this.idDefinition).getValue();
								if (flag == true && this.isAllReadOnly!=true) {
									this.getCmpByName('platFormBpFundProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
									 Ext.getCmp("jixizq1"+this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq3"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq4"+ this.idDefinition).setValue(false);
									 Ext.getCmp("jixizq2"+ this.idDefinition).setValue(false);
									 
								}else{
								 this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setDisabled(true);
							 	 //   this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('platFormBpFundProject.payintentPerioDate').setDisabled(false);
										 }
									  }
								    
								}
							}
						}
					}]
				}, {
					columnWidth : 0.06,
					labelWidth : 1,
					layout : 'form',
					items : [{
					 xtype:'textfield',
					 anchor : "100%",
				 	readOnly : this.isAllReadOnly,
				 	fieldClass : 'field-align',
					 name:'platFormBpFundProject.dayOfEveryPeriod',
					 listeners : {
					 	scope : this,
					 	'change' : function(){
					 		var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
							var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
							var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
							var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
							var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
							setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
					 	}
					 }
					}
					]}, {
						columnWidth : 0.07,
						labelWidth :40,
						layout : 'form',
						items : [{
						fieldLabel : "日/期",
							labelSeparator : '',
							anchor : "100%"
								}]}
					
					]}, {
					columnWidth : 1,
					layout:'column',
					items:[{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						items : [{
								fieldLabel : "贷款期限",
								xtype : "textfield",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : "platFormBpFundProject.payintentPeriod",
								anchor : "100%",
								listeners : {
								 	scope : this,
								 	'change' : function(){
								 		var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
										var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
										var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								 	}
								 }

							}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "期",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .28,
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : '96%'
					},
					items : [{
						fieldLabel : "放款日期",
						xtype : (this.changeType==null||this.changeType==false)?"datefield":"hidden",
						style : {
							imeMode : 'disabled'
						},
						name : "platFormBpFundProject.startDate",
						allowBlank : false,
						//hidden:(this.changeType==null||this.changeType==false)?false:true,
						readOnly : this.isStartDateReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function(nf){
								var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
								if(this.getCmpByName('platFormBpFundProject.startDate')){
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue();
									var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
									setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel)
								}
							}
						}
					},{
						fieldLabel :"起息日期",
						xtype : (this.changeType==null||this.changeType==false)?"hidden":"datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "platFormBpFundProject.startInterestDate",
						allowBlank : false,
						//hidden:(this.changeType==null||this.changeType==false)?true:false,
						readOnly : this.isStartDateReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function(nf,nv,ov){
								var startDate=this.getCmpByName('platFormBpFundProject.startDate');
								startDate.setValue(nv);
								var payAccrualType=this.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
								var dayOfEveryPeriod=this.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
								var payintentPeriod=this.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
								if(this.getCmpByName('platFormBpFundProject.startDate')){
									var startInterestDate=this.getCmpByName('platFormBpFundProject.startInterestDate').getValue();
									var intentDatePanel=this.getCmpByName('platFormBpFundProject.intentDate');
									setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startInterestDate,intentDatePanel)
								}
							}
						}
					}]
				},{
					columnWidth : .36,
					layout : 'form',
					labelWidth:rightlabel,
					items : [{
						fieldLabel : "<font color='red'>*</font>还款日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "platFormBpFundProject.intentDate",
						readOnly : true,
						blankText : "还款日期不能为空，请正确填写!",
						anchor : "100%",
						format : 'Y-m-d'
					}]
				}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .1,
						layout : 'form',
						labelWidth : 85,
						labelAlign :'right',
						items : [{
							fieldLabel : '还款选项'
						}]
					},{
						columnWidth : .08, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 1,
						items : [{
							xtype : "checkbox",
							boxLabel : "前置付息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isPreposePayAccrualCheck",
							// value :true
							checked : this.record == null
									|| this.record.data.isPreposePayAccrual== 0
									? null
									: true,
							listeners : {
								scope : this,
								'check' : function(box,value){
									if(value==true){
										this.getCmpByName('platFormBpFundProject.isPreposePayAccrual').setValue(1);
									}else{
										this.getCmpByName('platFormBpFundProject.isPreposePayAccrual').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'platFormBpFundProject.isPreposePayAccrual',
							value:0
						}]
					},{
						columnWidth : .14, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 1,
						items : [{
							xtype : "checkbox",
							boxLabel : "一次性支付利息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isInterestByOneTimeCheck",
							// value :true
							checked : this.record == null
									|| this.record.data.isInterestByOneTime== 0
									? null
									: true,
							listeners : {
								scope : this,
								'check' : function(box,value){
									if(value==true){
										this.getCmpByName('platFormBpFundProject.isInterestByOneTime').setValue(1);
									}else{
										this.getCmpByName('platFormBpFundProject.isInterestByOneTime').setValue(0);
									}
								}
							}
						},{
							xtype :'hidden',
							name : 'platFormBpFundProject.isInterestByOneTime',
							value:0
						}]
					},{
					columnWidth : .5, 
					name :"mqhkri",
					layout : "column", 
					items : [ {
							columnWidth : 0.28,
							labelWidth : 76,
							layout : 'form',
							items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1'+ this.idDefinition,
										id : "meiqihkrq1" + this.idDefinition,
										inputValue : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('platFormBpFundProject.isStartDatePay').setValue("1");
												this.getCmpByName('platFormBpFundProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "platFormBpFundProject.isStartDatePay"

									}]
						}, {
							columnWidth : 0.276,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "platFormBpFundProject.payintentPerioDate",
										xtype : 'combo',
										mode : 'local',
										disabled :true,
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "platFormBpFundProject.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
						}, {
							columnWidth : .08,
							labelWidth :45,
							layout : 'form',
							items : [{
							fieldLabel : "号还款",
								labelSeparator : '',
								anchor : "100%"
									}]
						}, {
							columnWidth : 0.364,
							labelWidth : 10,
							anchor:'100%',
							layout : 'form',
							items : [{
										xtype : 'radio',
										boxLabel : '按实际放款日对日还款',
										name : 'q1'+ this.idDefinition,
										id : "meiqihkrq2" + this.idDefinition,
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq2"
													+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('platFormBpFundProject.isStartDatePay').setValue("2");
												this.getCmpByName('platFormBpFundProject.payintentPerioDate').setValue(null);
														this.getCmpByName('platFormBpFundProject.payintentPerioDate').setDisabled(true);
											}
										}
									}

									}]
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth :.08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '贷款利率',
							allowBlank : false
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.yearAccrualRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							decimalPrecision : 8,
							anchor : '100%',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.accrual')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumAccrualRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									accrualnf.setValue((nf.getValue()/(12*10))*10)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										this.getCmpByName('platFormBpFundProject.accrualnew').setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										this.getCmpByName('platFormBpFundProject.accrualnew').setValue(nf.getValue()/365)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/365*days)
										}
									}
								}
							}
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "月化利率:",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.accrual",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							anchor : '100%',
							decimalPrecision : 8,
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumAccrualRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									if(null!=dateModel && dateModel=='dateModel_360'){
									 dayAccrualRatenf.setValue(nf.getValue()/30)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									}
									this.getCmpByName('platFormBpFundProject.accrualnew').setValue(nf.getValue()/30)
									} else if(null!=dateModel && dateModel=='dateModel_365'){
									 dayAccrualRatenf.setValue(nf.getValue()*12/365)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*12/365*days)
									}
									this.getCmpByName('platFormBpFundProject.accrualnew').setValue(nf.getValue()*12/365)
									}
							
								}
							}
							
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.dayAccrualRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearAccrualRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumAccrualRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									
									this.getCmpByName('platFormBpFundProject.accrualnew').setValue(nf.getValue())
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360)
										accrualnf.setValue(nf.getValue()*30)
										
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
										accrualnf.setValue(nf.getValue()*365/12)
									}
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*days)
									}
								}
							}
						},{
						 	xtype : 'hidden',
						 	name : 'platFormBpFundProject.accrualnew',
						 	value : 0
						}]
					},{
						columnWidth : .045,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlgin : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .27,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.sumAccrualRate",
							readOnly : this.isReadOnly,
							anchor : '100%',
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.accrual')
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearAccrualRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayAccrualRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
											accrualnf.setValue(rate*30)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
											accrualnf.setValue(rate*365/12)
										}
										this.getCmpByName('platFormBpFundProject.accrualnew').setValue(rate)
									}
								}
							}
						}]
					},{
						columnWidth : .02,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth :.08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '管理咨询费率',
							allowBlank :false
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.yearManagementConsultingOfRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.managementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									accrualnf.setValue((nf.getValue()/(12*10))*10)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/365*days)
										}
									}
								}
							}
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "月化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.managementConsultingOfRate",
							readOnly : this.isReadOnly,
							fieldClass : 'field-align',
							anchor : '100%',
							decimalPrecision : 8,
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearManagementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									if(null!=dateModel && dateModel=='dateModel_360'){
									 dayAccrualRatenf.setValue(nf.getValue()/30)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
										startDate=Ext.util.Format.date(startDate,'Y-m-d')
										var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									 }
									}
									else if(null!=dateModel && dateModel=='dateModel_365'){
									 dayAccrualRatenf.setValue(nf.getValue()*12/365)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
										startDate=Ext.util.Format.date(startDate,'Y-m-d')
										var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*12/365*days)
									 }
									}
								
								}
							}
							
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.dayManagementConsultingOfRate",
							readOnly : this.isReadOnly,
							anchor : '100%',
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.managementConsultingOfRate')
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearManagementConsultingOfRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumManagementConsultingOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360)
										accrualnf.setValue(nf.getValue()*30)
										
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
										accrualnf.setValue(nf.getValue()*365/12)
									}
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*days)
									}
								}
							}
						}]
					},{
						columnWidth : .045,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .27,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.sumManagementConsultingOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.managementConsultingOfRate')
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearManagementConsultingOfRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayManagementConsultingOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
											accrualnf.setValue(rate*30)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
											accrualnf.setValue(rate*365/12)
										}
										
									}
								}
							}
						}]
					},{
						columnWidth : .02,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth :.08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '财务服务费率',
							allowBlank : false
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "年化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.yearFinanceServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.financeServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									accrualnf.setValue((nf.getValue()/(12*10))*10)
									if(null!=dateModel && dateModel=='dateModel_360'){
										dayAccrualRatenf.setValue(nf.getValue()/360)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/360*days)
										}
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										dayAccrualRatenf.setValue(nf.getValue()/365)
										if(startDate!='' && intentDate!=''){
											intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
											sumAccrualRatenf.setValue(nf.getValue()/365*days)
										}
									}
								}
							}
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "月化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.financeServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearFinanceServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									yearAccrualRatenf.setValue(nf.getValue()*12)
									if(null!=dateModel && dateModel=='dateModel_360'){
									 dayAccrualRatenf.setValue(nf.getValue()/30)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()/30*days)
									}
									}
									else if(null!=dateModel && dateModel=='dateModel_365'){
									 dayAccrualRatenf.setValue(nf.getValue()*12/365)
									 if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*12/365*days)
									}
									}
								
								}
							}
							
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "日化利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							anchor : '100%',
							name : "platFormBpFundProject.dayFinanceServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.financeServiceOfRate')
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearFinanceServiceOfRate')
									var sumAccrualRatenf=this.getCmpByName('platFormBpFundProject.sumFinanceServiceOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									
									if(null!=dateModel && dateModel=='dateModel_360'){
										yearAccrualRatenf.setValue(nf.getValue()*360)
										accrualnf.setValue(nf.getValue()*30)
										
									}else if(null!=dateModel && dateModel=='dateModel_365'){
										yearAccrualRatenf.setValue(nf.getValue()*365)
										accrualnf.setValue(nf.getValue()*365/12)
									}
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										sumAccrualRatenf.setValue(nf.getValue()*days)
									}
								}
							}
						}]
					},{
						columnWidth : .045,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "合计利率",
							labelSeparator : ''
						}]
					},{
						columnWidth : .27,
						layout : 'form',
						items : [{
							hideLabel :true,
							xtype : 'numberfield',
							name : "platFormBpFundProject.sumFinanceServiceOfRate",
							readOnly : this.isReadOnly,
							decimalPrecision : 8,
							anchor : '100%',
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							listeners : {
								scope : this,
								'change' : function(nf){
									var dateModel=this.getCmpByName('platFormBpFundProject.dateMode').getValue()
									var accrualnf=this.getCmpByName('platFormBpFundProject.financeServiceOfRate')
									var yearAccrualRatenf=this.getCmpByName('platFormBpFundProject.yearFinanceServiceOfRate')
									var dayAccrualRatenf=this.getCmpByName('platFormBpFundProject.dayFinanceServiceOfRate')
									var startDate=this.getCmpByName('platFormBpFundProject.startDate').getValue()
									var intentDate=this.getCmpByName('platFormBpFundProject.intentDate').getValue()
									
									if(startDate!='' && intentDate!=''){
										intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
											startDate=Ext.util.Format.date(startDate,'Y-m-d')
											var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
										var rate=nf.getValue()/days
										dayAccrualRatenf.setValue(rate);
										if(null!=dateModel && dateModel=='dateModel_360'){
											yearAccrualRatenf.setValue(rate*360)
											accrualnf.setValue(rate*30)
										
										}else if(null!=dateModel && dateModel=='dateModel_365'){
											yearAccrualRatenf.setValue(rate*365)
											accrualnf.setValue(rate*365/12)
										}
										
									}
								}
							}
						}]
					},{
						columnWidth : .02,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .08,
						layout : 'form',
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
							fieldLabel : '违约金比例'
						}]
					},{
						columnWidth : .06,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
							fieldLabel : "贷款总额",
							labelSeparator : ''
						}]
					},{
						columnWidth : .16,
						layout : 'form',
						items : [{
							xtype : "numberfield",
							name : "platFormBpFundProject.breachRate",
							decimalPrecision : 3,
							readOnly : this.isAllReadOnly,
							hideLabel : true,
							fieldClass : 'field-align',
							style : {
								imeMode : 'disabled'
							},
							value :0,
							anchor : "100%"
						}]
					},{
						columnWidth : .018,
						layout : 'form',
						labelWidth : 20,
						labelAlgin : 'left',
						items : [{
							fieldLabel : "%",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .28,
						layout : 'form',
						labelWidth : 89,
						labelAlign : 'right',
						items : [{
							fieldLabel : "逾期贷款利率",
							xtype : "numberfield",
							fieldClass : 'field-align',
							name : "platFormBpFundProject.overdueRateLoan",
							decimalPrecision : 3,
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							readOnly : this.isAllReadOnly,
							blankText : "逾期费率不能为空，请正确填写!",
							anchor : "100%",
							value :0,
							listeners : {
								afterrender : function(comp) {
									comp.on("keydown", function() {
											})
								}
							}

						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 35,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%/日",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					},{
						columnWidth : .345,
						layout : 'form',
						labelWidth : 89,
						labelAlign : 'right',
						items : [{
							fieldLabel : '逾期罚息利率',
							xtype : "textfield",
							readOnly : this.isAllReadOnly,
							name : "platFormBpFundProject.overdueRate",
							fieldClass : 'field-align',
							allowBlank : false,
							blankText : "逾期罚息利率不能为空，请正确填写!",
							decimalPrecision : 3,
							anchor : '100%',
							value :0
						}]
					},{
						columnWidth : .03,
						layout : 'form',
						labelWidth : 35,
						labelAlign : 'left',
						items : [{
							fieldLabel : "%/日",
							labelSeparator : '',
							anchor : "100%"
						
						}]
					}]
				},{
					columnWidth:1,
					hidden:true,
					layout:'column',
					items:[{
						layout:'form',
						labelWidth : 89,
						columnWidth:.3,
						items:[{
							xtype:'textfield',
							fieldLabel:'余额托管费率',
							readOnly : this.isAllReadOnly,
							anchor:'100%',
							name:'platFormBpFundProject.balanceRate',
							value:'0'
						}]
					},{
						layout:'form',
						columnWidth:.03,
						items:[{
							style:'margin-top:3px;margin-left:3px;',
							html:'‰'
						}]
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					buttonAlign : 'center',
					style : 'margin-left : 450px',
					items : [{
						xtype : 'button',
						text : '贷款试算',
						iconCls : 'btn-search',
						width : 110,
						hidden : this.isHiddencalculateBtn,
						scope : this,
						handler : function() {
							var isHiddenbackBtn=this.isHiddenbackBtn;
							 var projectMoney=this.getCmpByName("platFormBpFundProject.platFormJointMoney").getValue();
							 var startDate=this.getCmpByName("platFormBpFundProject.startDate").getValue();
							 var payaccrualType=this.getCmpByName("platFormBpFundProject.payaccrualType").getValue();
							 var dayOfEveryPeriod=this.getCmpByName("platFormBpFundProject.dayOfEveryPeriod").getValue();
							 var payintentPeriod=this.getCmpByName("platFormBpFundProject.payintentPeriod").getValue();
							 var isStartDatePay=this.getCmpByName("platFormBpFundProject.isStartDatePay").getValue();
							 var payintentPerioDate=this.getCmpByName("platFormBpFundProject.payintentPerioDate").getValue();
							 var intentDate=this.getCmpByName("platFormBpFundProject.intentDate").getValue();
							 var accrual=this.getCmpByName("platFormBpFundProject.accrual").getValue();
							 var managementConsultingOfRate=this.getCmpByName("platFormBpFundProject.managementConsultingOfRate").getValue();
							 var accrualtype=this.getCmpByName("platFormBpFundProject.accrualtype").getValue();
							 var isPreposePayAccrual=this.getCmpByName("platFormBpFundProject.isPreposePayAccrual").getValue();
							 var overdueRate=this.getCmpByName("platFormBpFundProject.overdueRate").getValue();
							 var overdueRateLoan=this.getCmpByName("platFormBpFundProject.overdueRateLoan").getValue();
							 var isInterestByOneTime=this.getCmpByName("platFormBpFundProject.isInterestByOneTime").getValue();
							 var yearAccrualRate=this.getCmpByName("platFormBpFundProject.yearAccrualRate").getValue();
							 var dayAccrualRate=this.getCmpByName("platFormBpFundProject.dayAccrualRate").getValue();
							 var sumAccrualRate=this.getCmpByName("platFormBpFundProject.sumAccrualRate").getValue();
							 var yearManagementConsultingOfRate=this.getCmpByName("platFormBpFundProject.yearManagementConsultingOfRate").getValue();
							 var dayManagementConsultingOfRate=this.getCmpByName("platFormBpFundProject.dayManagementConsultingOfRate").getValue();
							 var sumManagementConsultingOfRate=this.getCmpByName("platFormBpFundProject.sumManagementConsultingOfRate").getValue();
							 var yearFinanceServiceOfRate=this.getCmpByName("platFormBpFundProject.yearFinanceServiceOfRate").getValue();
							 var dayFinanceServiceOfRate=this.getCmpByName("platFormBpFundProject.dayFinanceServiceOfRate").getValue();
							 var sumFinanceServiceOfRate=this.getCmpByName("platFormBpFundProject.sumFinanceServiceOfRate").getValue();
							 var accrualnew=this.getCmpByName("platFormBpFundProject.accrualnew").getValue();
							 var financeServiceOfRate=this.getCmpByName("platFormBpFundProject.financeServiceOfRate").getValue();
							 var dateMode=this.getCmpByName("platFormBpFundProject.dateMode").getValue();
							 var breachRate=this.getCmpByName("platFormBpFundProject.breachRate").getValue();
							 new calculateFundIntent({
								 projectMoney:projectMoney,
								 startDate:startDate,
								 payaccrualType:payaccrualType,
								 dayOfEveryPeriod:dayOfEveryPeriod,
								 payintentPeriod:payintentPeriod,
								 isStartDatePay:isStartDatePay,
								 payintentPerioDate:payintentPerioDate,
								 intentDate:intentDate,
								 accrual:accrual,
								 managementConsultingOfRate:managementConsultingOfRate,
								 accrualtype:accrualtype,
								 projectId:this.projectId,
								 isPreposePayAccrual:isPreposePayAccrual,
								 overdueRate:overdueRate,
								 overdueRateLoan:overdueRateLoan,
								 isInterestByOneTime:isInterestByOneTime,
								 yearAccrualRate:yearAccrualRate,
								 dayAccrualRate:dayAccrualRate,
								 sumAccrualRate:sumAccrualRate,
								 yearManagementConsultingOfRate:yearManagementConsultingOfRate,
								 dayManagementConsultingOfRate:dayManagementConsultingOfRate,
								 sumManagementConsultingOfRate:sumManagementConsultingOfRate,
								 yearFinanceServiceOfRate:yearFinanceServiceOfRate,
								 dayFinanceServiceOfRate:dayFinanceServiceOfRate,
								 sumFinanceServiceOfRate:sumFinanceServiceOfRate,
								 accrualnew:accrualnew,
								 financeServiceOfRate:financeServiceOfRate,
								 dateMode:dateMode,
								 breachRate:breachRate,
								 idDefinition1:idDefinition1,
								 objectfinace:this,
								 isHiddenbackBtn:isHiddenbackBtn
								}).show();
						}
					}]
				}]
			}]
		});
	}
});