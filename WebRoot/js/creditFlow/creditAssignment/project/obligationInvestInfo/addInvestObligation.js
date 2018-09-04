//addInvestObligation
addInvestObligation = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		if(typeof(_cfg.isReadOnly) != "undefined")
		{
			this.isRead=_cfg.isReadOnly;
		};
		if(null!=_cfg.personData){
		    this.isflag=true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		
		addInvestObligation.superclass.constructor.call(this, {
					id : 'addInvestObligation',
					layout : 'fit',
					autoScroll:true,
					items : [{
								xtype : 'fieldset',
								title : '匹配债权',
								collapsible : true,
								autoHeight : true,
								items : [this.formPanel]
							},{
								xtype : 'fieldset',
								title : '收益计划表',
								collapsible : true,
								autoHeight : true,
								bodyStyle : 'padding-left: 0px',
								items : [this.obligationFundIntentViewVM]
							}],
					modal : true,
					height : 500,
					width : 1000,
					maximizable : true,
					title : '匹配债权',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								hidden : this.isLook,
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		var storepayintentPeriod="[";
		  for (var i = 1; i <31; i++) {
				storepayintentPeriod = storepayintentPeriod + "[" + i
						+ ", " + i + "],";
			}
			
			storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
			
			var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel){
				
				Ext.Ajax.request({
					url : __ctxPath + "/project/getIntentDateSlSmallloanProject.do",
					method : 'POST',
					scope:this,
					params : {
						payAccrualType:payAccrualType,
						dayOfEveryPeriod:dayOfEveryPeriod,
						payintentPeriod:payintentPeriod,
						startDate:startDate
					},
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						obj.intentDate;
						intentDatePanel.getCmpByName("obObligationInvestInfo.investEndDate").setValue(obj.intentDate);
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					}
				});
			}
			
		this.formPanel = new Ext.form.FormPanel({
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					labelWidth : 60
				},
				
			// defaultType : 'textfield',
			items : [{
							xtype : "hidden",
							name : "obObligationInvestInfo.obligationId"
			},{
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "combo",
							name : "obObligationInvestInfo.obligationName",
							triggerClass : 'x-form-search-trigger',
							allowBlank : false,
							readOnly : this.isReadOnly,
							fieldLabel : '产品名称',
							fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							onTriggerClick : function() {
								var op=this.ownerCt.ownerCt.ownerCt.ownerCt;
								var selectProductInfo=function(obj){
									/*op.getCmpByName('obObligationInvestInfo.obligationName').setValue("");
									op.getCmpByName('obObligationProject.obligationNumber').setValue("");
									op.getCmpByName('obObligationProject.intentDate').setValue("");
									op.getCmpByName('projectMoney').setValue("");
									op.getCmpByName('mappingMoney').setValue("");*/	
									
									if(obj.id!=0 && obj.id!="")	{
										op.getCmpByName('obObligationInvestInfo.obligationId').setValue(obj.id);
									}
									if(obj.obligationName!=0 && obj.obligationName!="")	{
										op.getCmpByName('obObligationInvestInfo.obligationName').setValue(obj.obligationName);
									}
									if(obj.obligationNumber!=0 && obj.obligationNumber!="")	{
										op.getCmpByName('obObligationProject.obligationNumber').setValue(obj.obligationNumber);
									}
									if(obj.intentDate!=0 && obj.intentDate!="")	{
										op.getCmpByName('obObligationProject.intentDate').setValue(obj.intentDate);
									}
									if(obj.projectMoney!=0 && obj.projectMoney!="")	{
										op.getCmpByName('obObligationProject.projectMoney').setValue(obj.projectMoney);
									}
									if(obj.projectMoney!=0 && obj.projectMoney!="")	{
										op.getCmpByName('projectMoney').setValue(obj.projectMoney);
									}
									//已匹配金额
									if(obj.mappingMoney!="" && obj.mappingMoney!=0)	{
										op.getCmpByName('mappingMoney').setValue(obj.mappingMoney);
										op.getCmpByName('obObligationProject.mappingMoney').setValue(obj.mappingMoney);
									}
									//未匹配金额
									if(obj.unmappingMoney!=0 && obj.unmappingMoney!="")	{
										op.getCmpByName('unmappingMoney').setValue(obj.unmappingMoney);
										op.getCmpByName('obObligationProject.unmappingMoney').setValue(obj.unmappingMoney);
									}
									//贷款期限
									if(obj.payintentPeriod!=0 && obj.payintentPeriod!="")	{
										op.getCmpByName('obObligationProject.payintentPeriod').setValue(obj.payintentPeriod);
									}
									
									//年化利率
									if(obj.accrual!=0 && obj.accrual!="")	{
										//op.getCmpByName('obObligationProject.accrual').setValue(obj.accrual);
										op.getCmpByName('obObligationInvestInfo.obligationAccrual').setValue(obj.accrual);
									}
									//最小投资额
									if(obj.minInvestMent!="" && obj.minInvestMent!=0 )	{
										op.getCmpByName('minInvestMent').setValue(obj.minInvestMent);
										op.getCmpByName('obObligationProject.minInvestMent').setValue(obj.minInvestMent);
									}
									/*//最小投资份额
									if(obj.minQuotient!=""&&obj.minQuotient!=0 )	{
										op.getCmpByName('obObligationProject.minQuotient').setValue(obj.minQuotient);
									}*/
									//总份额
									if(obj.totalQuotient!=0 && obj.totalQuotient!="")	{
										op.getCmpByName('obObligationProject.totalQuotient').setValue(obj.totalQuotient);
									}
									//未匹配份额
									if(obj.unmappingQuotient!="" && obj.unmappingQuotient!=0)	{
										op.getCmpByName('obObligationProject.unmappingQuotient').setValue(obj.unmappingQuotient);
									}
									//计息方式
									if(obj.accrualtype!=0 && obj.accrualtype!="")	{
										op.getCmpByName('obObligationProject.accrualtype').setValue(obj.accrualtype);
										if("singleInterest"==obj.accrualtype){
											Ext.getCmp("jixifs3" +obj.idDefinition).setValue(true)
										}else if("sameprincipal"==obj.accrualtype){
											Ext.getCmp("jixifs1" +obj.idDefinition).setValue(true)
										}else if("sameprincipalandInterest"==obj.accrualtype){
											Ext.getCmp("jixifs2" +obj.idDefinition).setValue(true)
										}else if("sameprincipalsameInterest"==obj.accrualtype){
											Ext.getCmp("jixifs5" +obj.idDefinition).setValue(true)
										}else if("ontTimeAccrual"==obj.accrualtype){
											Ext.getCmp("jixifs4" +obj.idDefinition).setValue(true)
										}
									}
									//计息周期
									if(obj.payaccrualType!=0 && obj.payaccrualType!="")	{
										op.getCmpByName('obObligationProject.payaccrualType').setValue(obj.payaccrualType);
										if("dayPay"==obj.payaccrualType){
											Ext.getCmp("jixizq1" +obj.idDefinition).setValue(true)
										}else if("monthPay"==obj.payaccrualType){
											Ext.getCmp("jixizq2" +obj.idDefinition).setValue(true)
										}else if("seasonPay"==obj.payaccrualType){
											Ext.getCmp("jixizq3" +obj.idDefinition).setValue(true)
										}else if("yearPay"==obj.payaccrualType){
											Ext.getCmp("jixizq4" +obj.idDefinition).setValue(true)
										}else if("owerPay"==obj.payaccrualType){
											Ext.getCmp("jixizq6" +obj.idDefinition).setValue(true)
										}
									}
									//每期还款日
									if(obj.payintentPerioDate!=0 && obj.payintentPerioDate!=""){
										Ext.getCmp("meiqihkrq1"+obj.idDefinition).setValue("1")
										Ext.getCmp("payintentPerioDate"+obj.idDefinition).setValue(obj.payintentPerioDate)
									}
									//按实际放款日还款
									if(obj.isStartDatePay!=0 && obj.isStartDatePay!=""){
										Ext.getCmp("meiqihkrq2"+obj.idDefinition).setValue("1")
									}
									//是否前置付息 0否1是
									if(obj.isPreposePayAccrual!=0 && obj.isPreposePayAccrual!=""){
										Ext.getCmp("isPreposePayAccrual").setValue("1")
									}
												
								}
								selectProduct(selectProductInfo);
							},
							anchor : '93%'
						}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "obObligationProject.obligationNumber",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '产品编号',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '90%'
							
						}]
					},{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 100,
							labelAlign :'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '自动关闭时间',
								allowBlank : false,
								//fieldClass : 'field-align',
								readOnly : true,
								name : "obObligationProject.intentDate",
								anchor : '90%',
								format : 'Y-m-d'
							}]
				
				},{
						columnWidth : .37, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "projectMoney",
							allowBlank : false,
							readOnly : true,
							style : {
								imeMode : 'disabled'
							},
							//fieldClass : 'field-align',
							fieldLabel : '债权金额',
							blankText :'债权金额不能为空，请正确填写债权金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.projectMoney"
							
						}]
					},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
						columnWidth : .27, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "mappingMoney",
							allowBlank : false,
							readOnly : true,
							style : {
								imeMode : 'disabled'
							},
							//fieldClass : 'field-align',
							fieldLabel : '已匹配金额',
							blankText :'债权金额不能为空，请正确填写债权金额！',
							anchor : '100%',
							value:0.00,
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.mappingMoney",
							value:0.00
							
						}]
					},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
						columnWidth : .27, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "unmappingMoney",
							allowBlank : false,
							readOnly : true,
							style : {
								imeMode : 'disabled'
							},
							//fieldClass : 'field-align',
							fieldLabel : '未匹配金额',
							blankText :'债权金额不能为空，请正确填写债权金额！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.unmappingMoney"
							
						}]
					},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
							columnWidth : .37, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '债权期限',
								allowBlank : false,
								readOnly : true,
								anchor : '100%',
								name : "obObligationProject.payintentPeriod"
							}]
				
				},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "月 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
							columnWidth : .27, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '年化收益率',
								allowBlank : false,
								anchor : '100%',
								readOnly : true,
								name : "obObligationInvestInfo.obligationAccrual"
							}]
				
				},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "% ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
						columnWidth : .27, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "minInvestMent",
							readOnly : true,
							allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							fieldLabel : '最小投资额',
							blankText :'最小投资额不能为空，请正确填写最小投资额！',
							anchor : '100%',
							scope : this,
							listeners : {
								scope : this,
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								},
								blur:function(){
									
													var obj1=this.getCmpByName("obObligationProject.projectMoney");
													var obj2=this.getCmpByName("obObligationProject.totalQuotient");
													
													var projectMoney=obj1.getValue(); //贷款金额
													var minInvestMent=this.getCmpByName("obObligationProject.minInvestMent").getValue();//最小投资份额
													obj2.setValue(projectMoney/minInvestMent);
												}
							}
						},{
							xtype : "hidden",
							name : "obObligationProject.minInvestMent"
							
						}]
					},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},/*{
							columnWidth : .35, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '最小投资份额',
								allowBlank : false,
								readOnly : true,
								name : "obObligationProject.minQuotient",
								anchor : '100%'
							}]
				
				},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 30,
						items : [{
									fieldLabel : "份 ",
									labelSeparator : '',
									anchor : "90%"
								}]
				},*/{
							columnWidth : .67, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '总份额',
								allowBlank : false,
								readOnly : true,
								name : "obObligationProject.totalQuotient",
								anchor : '100%'
							}]
				
				},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "份 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
							columnWidth : .27, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 100,
							labelAlign :'right',
							items : [{
								xtype : 'textfield',
								fieldLabel : '未匹配份额',
								allowBlank : false,
								readOnly : true,
								name : "obObligationProject.unmappingQuotient",
								anchor : '100%'
							}]
				
				},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "份 ",
									labelSeparator : '',
									anchor : "100%"
								}]
				},{
						columnWidth : 1,
						layout:'column',
						items : [
						 {
						columnWidth : .13, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						items : [{
							 fieldLabel : "计息方式 ",
							anchor : "100%"
						}]
					}, {
						columnWidth : 0.12,
						labelWidth : 5,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '等额本金',
							name : 'f1',
							id : "jixifs1" +this.idDefinition,
							inputValue : false,
							anchor : "100%",
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs1"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("sameprincipal");
										Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
									          
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
								 	            this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
								 	    
									              Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
									            
									             this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(false);  //修改的  zcb
									              this.getCmpByName('mqhkri1').hide();
								                   this.getCmpByName('mqhkri').show();
									}
								}
							}
						}]
					}, {
						columnWidth : 0.12,
						labelWidth : 5,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '等额本息',
							anchor : "100%",
							name : 'f1',
							id : "jixifs2" +this.idDefinition,
							inputValue : false,
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs2"+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("sameprincipalandInterest");
									    Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
									           
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
								 	           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
									          
								 	           Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
									            
									             this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(false); //修改的  zcb
									              this.getCmpByName('mqhkri1').hide();
								                    this.getCmpByName('mqhkri').show();
									}
								}
							}
						}]
					},{
						columnWidth : 0.12,
						labelWidth : 5,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '等本等息',
							anchor : "100%",
							name : 'f1',
							id : "jixifs5" +this.idDefinition,
							inputValue : false,
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj,checked){
									
									var flag = Ext.getCmp("jixifs5"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').hide();
										this.getCmpByName('periord').show()
										this.getCmpByName('obObligationProject.accrualtype').setValue("sameprincipalsameInterest");
										  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									//          this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	                                         if(this.getCmpByName('obObligationProject.payaccrualType').getValue()==""){
									          Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
	                                         }
									}
								
								}
							}
						}]
					}, {
						columnWidth : 0.14,
						labelWidth : 5,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '按期收息,到期还本',
							name : 'f1',
							id : "jixifs3" +this.idDefinition,
							inputValue : false,
							checked : true,
							anchor : "100%",
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs3"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("singleInterest");
										  Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);  
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
									//          this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	                                         if(this.getCmpByName('obObligationProject.payaccrualType').getValue()==""){
									          Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
									             Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
	                                         }
									}
								}
							}
						}, {
							xtype : "hidden",
							name : "obObligationProject.accrualtype",
							value : "singleInterest"

						}]
					}, {
						columnWidth : 0.15,
						labelWidth : 5,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '一次性还本付息',
							name : 'f1',
							id : "jixifs4" +this.idDefinition,
							inputValue : true,
							anchor : "100%",
							disabled : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("jixifs4"+ this.idDefinition).getValue();
									if (flag == true) {
										this.getCmpByName('month').show();
										this.getCmpByName('periord').hide()
										this.getCmpByName('obObligationProject.accrualtype').setValue("ontTimeAccrual");
										Ext.getCmp("jixizq1"+this.idDefinition).setDisabled(true);
									      Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
									        Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
									          Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);  
								                
									           Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
									           this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
								 	            this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
								 	    
									              Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
									             Ext.getCmp("jixizq1" +this.idDefinition).setValue(false);
									             Ext.getCmp("jixizq2" +this.idDefinition).setValue(false);
									            this.getCmpByName('obObligationProject.payaccrualType').setValue("");
									            
									             
								                   
										 // this.getCmpByName('payintentPeriod1').setDisabled(true);
										  //this.getCmpByName('payintentPeriod1').setValue(1);
									      this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(true); //修改的  zcb
										  this.getCmpByName('obObligationProject.payintentPeriod').setValue(1);
									   this.getCmpByName('mqhkri').hide();
								       this.getCmpByName('mqhkri1').show();
								        
									}else{
									  // this.getCmpByName('payintentPeriod1').setDisabled(false);
									   this.getCmpByName('obObligationProject.payintentPeriod').setDisabled(false); //修改的  zcb
										
									   this.getCmpByName('mqhkri1').hide();
								       this.getCmpByName('mqhkri').show();
									
									}
								}
							}
						}, {
							xtype : "hidden",
							name : "obObligationProject.payaccrualType",
							value : "monthPay"

						}]
					}
					]
				},{
					
				columnWidth : 1,
					layout:'column',
					items : [ {
					columnWidth : .13, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					items : [{
						 fieldLabel : "计息周期 ",
						// fieldClass : 'field-align',
						//html : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
						anchor : "100%"
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 5,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '日',
						name : 'z1',
						id : "jixizq1" + this.idDefinition,
						inputValue : true,
						anchor : "100%",
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq1"	+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("dayPay");
									
									 Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
								
									 	this.getCmpByName('obObligationProject.isStartDatePay').setValue("2");
									 	
									 
								     
								     
								}else {
								       
								       
								     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
									 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
									 this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(false);
									 }
								       }
								 
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 5,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '月',
						name : 'z1',
						id : "jixizq2" +this.idDefinition,
						inputValue : false,
						checked : true,
						anchor : "100%",
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq2"
										+this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("monthPay");
									
								}
							}
						}
					}]
				}, {
					columnWidth : 0.12,
					labelWidth : 5,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '季',
						name : 'z1',
						id : "jixizq3" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq3"
										+this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("seasonPay");
								
								}
							}
						}
					}]
				}, {
					columnWidth : 0.14,
					labelWidth : 5,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '年',
						name : 'z1',
						id : "jixizq4" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq4"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("yearPay");
									
								}
							}
						}
					}]
				}, {
					columnWidth : 0.1,
					labelWidth : 5,
					layout : 'form',
					items : [{
						xtype : 'radio',
						boxLabel : '自定义周期',
						name : 'z1',
						id : "jixizq6" + this.idDefinition,
						inputValue : false,
						anchor : "100%",
						disabled : true,
						listeners : {
							scope : this,
							check : function(obj, checked) {
								var flag = Ext.getCmp("jixizq6"
										+ this.idDefinition).getValue();
								if (flag == true) {
									this.getCmpByName('obObligationProject.payaccrualType').setValue("owerPay");
									
								   this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(false);
								   
								    Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq1"+ this.idDefinition).setValue(false);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(true);
									 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setValue(true);
								}else{
								 this.getCmpByName('obObligationProject.dayOfEveryPeriod').setDisabled(true);
							 	    this.getCmpByName('obObligationProject.dayOfEveryPeriod').setValue("");
							 	    if(Ext.getCmp("jixizq1"+ this.idDefinition).getValue()==false){
								 	     Ext.getCmp("meiqihkrq1"+this.idDefinition).setDisabled(false);
										 Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
										 if(Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue()==true){
										  this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(false);
										 }
									  }
								    
								}
							}
						}
					}]
				}, {
					columnWidth : 0.06,
					labelWidth : 5,
					layout : 'form',
					items : [{
					 xtype:'textfield',
					 anchor : "100%",
				 	readOnly : true,
				 	//fieldClass : 'field-align',
					 name:'obObligationProject.dayOfEveryPeriod'
					}
					]}, {
						columnWidth : 0.04,
						labelWidth :20,
						layout : 'form',
						items : [{
						fieldLabel : "天",
							labelSeparator : '',
							anchor : "100%"
								}]}
					
					]
				},{
					
					columnWidth : 1,
					layout:'column',
					items:[ {
					columnWidth : .5, 
					name :"mqhkri",
					layout : "column", 
					items : [ {
							columnWidth : 0.328,
							labelWidth : 69,
							layout : 'form',
							items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1',
										id : "meiqihkrq1" + this.idDefinition,
										inputValue : true,
										anchor : "100%",
										disabled : true,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq1"+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('obObligationProject.isStartDatePay').setValue("1");
												this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(false);
											}
										}
									}

									}, {
										xtype : "hidden",
										name : "obObligationProject.isStartDatePay"
									}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										readOnly : this.isReadOnly,
										id:'payintentPerioDate'+this.idDefinition,
										name : "obObligationProject.payintentPerioDate",
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
										hiddenName : "obObligationProject.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
						}, {
							columnWidth : 0.12,
							labelWidth :45,
							layout : 'form',
							items : [{
							fieldLabel : "日还款",
								labelSeparator : '',
								anchor : "100%"
									}]
						}, {
							columnWidth : 0.42,
							labelWidth : 10,
							layout : 'form',
							items : [{
										xtype : 'radio',
										boxLabel : '按实际放款日对日还款',
										name : 'q1',
										id : "meiqihkrq2" + this.idDefinition,
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : true,
										listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("meiqihkrq2"
													+ this.idDefinition).getValue();
											if (flag == true) {
												this.getCmpByName('obObligationProject.isStartDatePay').setValue("2");
												this.getCmpByName('obObligationProject.payintentPerioDate').setValue(null);
														this.getCmpByName('obObligationProject.payintentPerioDate').setDisabled(true);
											}
										}
									}

									}]
						}]}
						,  {
							columnWidth : .12, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 13,
							items : [{
								xtype : "checkbox",
								boxLabel : "是否前置付息",
								disabled : true,
								anchor : "100%",
								id : "isPreposePayAccrual",
								name : "isPreposePayAccrual",
								listeners : {
									scope : this,
									'check' : function(box,value){
										if(value==true){
											this.getCmpByName('obObligationProject.isPreposePayAccrual').setValue(1);
										}else{
											this.getCmpByName('obObligationProject.isPreposePayAccrual').setValue(0);
										}
									}
								}
							},{
								xtype : 'hidden',
								name : 'obObligationProject.isPreposePayAccrual',
								value : 0
							}]
						}]
				
				},{
					
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "hidden",
							name : "obObligationInvestInfo.investMentPersonId",
							value:this.investPersonId,
							anchor : '100%'
							
						},{
							xtype : "hidden",
							name : "obObligationInvestInfo.investObligationStatus",
							value:0,
							anchor : '100%'
							
						},{
							xtype : "hidden",
							name : "obObligationInvestInfo.systemInvest",
							value:1,
							anchor : '100%'
							
						},{
							xtype : "textfield",
							name : "obObligationInvestInfo.investPersonName",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '投资人姓名',
							value:this.investPersonName,
							anchor : '100%'
							
						}]
					
				},{
					
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "obObligationInvestInfo.investQuotient",
							allowBlank : false,
							readOnly : this.isReadOnly,
							fieldLabel : '投资份额',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%',
							listeners : {
									scope:this,
								   blur : function(v){
									  if(v.getValue()==""){
									   v.setValue("0.00")
									  }else{
									    var ss=v.getValue()*(this.getCmpByName('obObligationProject.minInvestMent').getValue());
									    this.getCmpByName('investMoney').setValue(ss);
									   	this.getCmpByName('obObligationInvestInfo.investMoney').setValue(ss);
									    var rate =(ss/(this.getCmpByName('obObligationProject.projectMoney').getValue()))*100;
									   	this.getCmpByName('obObligationInvestInfo.investRate').setValue(rate);
									  }
								   }
								}
							
							
						}]
					
				},{
							xtype : "hidden",
							name : "obObligationInvestInfo.investRate"
														  
				},{
					
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "textfield",
							name : "investMoney",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '投资金额',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%',
							listeners : {
								change : function(nf) {
									var value = nf.getValue();
									var continuationMoneyObj = nf.nextSibling();
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入 没有进行格式化
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										continuationMoneyObj.setValue(value);
									} else {
										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(value,'0,000.00')
											nf.setValue(ke);
											continuationMoneyObj.setValue(value);
										} else {
											var last = value.substring(index + 1,value.length);
											if (last == 0) {
												var temp = value.substring(0, index);
												temp = temp.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											} else {
												var temp = value.replace(/,/g, "");
												continuationMoneyObj.setValue(temp);
											}
										}
									}
									
								}
							}
						},{
							xtype : "hidden",
							name : "obObligationInvestInfo.investMoney"
						}]
					
				},{
					
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "datefield",
							name : "obObligationInvestInfo.investStartDate",
							allowBlank : false,
							readOnly : this.isReadOnly,
							fieldLabel : '资金投资日',
							format : 'Y-m-d',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%',
							listeners : {
								scope : this,
								'change' : function(nf){
									 var startDate=nf.getValue();
									 var period =this.getCmpByName("obObligationProject.payintentPeriod").getValue();
										setIntentDate("monthPay",null,period,startDate,this)
									}
						}
							
						}]
					
				},{
					
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign :'right',
						items : [{
							xtype : "datefield",
							name : "obObligationInvestInfo.investEndDate",
							allowBlank : false,
							readOnly : true,
							fieldLabel : '债权结束日',
							format : 'Y-m-d',
							//name:"systemAccountNumber",
							//fieldClass : 'field-align',
							blankText :'系统账号不能为空，请正确填写系统账号！',
							anchor : '100%'
							
						}]
					
				},{
				            	columnWidth:.3,
				                layout: 'form',
				                labelWidth : 70,
				                labelAlign :'right',
				                defaults : {anchor:anchor},
				                items :[{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "degree",
										editable : false,
										fieldLabel : "操作人员",
										readOnly : this.isReadOnly,
										blankText : "操作人员不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%",
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择操作人员",
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
						}
					}, {
						xtype : "hidden",
						value : ""
					}]
				            }]
		});
		
		this.obligationFundIntentViewVM=new obligationFundIntentViewVM({
			object:this.formPanel,
			projectId : this.obligationId,
			obligationInfoId:this.investPersonInfo,
			investPersonId:this.investPersonId,
			isHiddenautocreateBtn:this.isReadOnly,
			keyValue:"oneSlFundIntentCreat"
		})
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		// 加载表单对应的数据
		if (this.investPersonInfo != null && this.investPersonInfo != 'undefined') {
			var panel=this
			this.formPanel.loadData({
						url : __ctxPath + '/creditFlow/creditAssignment/bank/getInfoObObligationInvestInfo.do?id='+ this.investPersonInfo,
						root : 'data',
						preName : ['obObligationInvestInfo','obObligationProject'],
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							this.getCmpByName('minInvestMent').setValue(Ext.util.Format.number(result.data.obObligationProject.minInvestMent,',000,000,000.00'));
							this.getCmpByName('unmappingMoney').setValue(Ext.util.Format.number(result.data.obObligationProject.unmappingMoney,',000,000,000.00'));
							this.getCmpByName('mappingMoney').setValue(Ext.util.Format.number(result.data.obObligationProject.mappingMoney,',000,000,000.00'));
							this.getCmpByName('projectMoney').setValue(Ext.util.Format.number(result.data.obObligationProject.projectMoney,',000,000,000.00'));
							this.getCmpByName('investMoney').setValue(Ext.util.Format.number(result.data.obObligationInvestInfo.investMoney,',000,000,000.00'));
							this.getCmpByName('degree').setValue(result.data.obObligationInvestInfo.creatorId);
							this.getCmpByName('degree').nextSibling().setValue(result.data.obObligationInvestInfo.creatorId);
							this.getCmpByName('degree').setRawValue(result.data.creatorName);
							fillData(this,result,panel.idDefinition);
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
		this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
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
		var obligationId=this.getCmpByName("obObligationInvestInfo.obligationId").getValue();
		var  date =this.obligationFundIntentViewVM.getGridDate();
		var gridPanel =this.gridPanel
		var investPersonId =this.investPersonId;
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/creditFlow/creditAssignment/bank/saveInvestObligationObObligationInvestInfo.do',
					params : {
							'obligationId':obligationId,
							'slFundIntentList':date
						},
					callback : function(fp, action) {
						var gridstore= gridPanel.getStore()
						gridstore.on('beforeload', function(gridstore, o) {
							Ext.apply(o.baseParams, {investPersonId:investPersonId});
						});
						gridstore.reload();
						this.close();
					}
				});
	}// end of save

});