/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */
function DateDiff(sDate1, sDate2) { // sDate1和sDate2是2006-12-18格式11
	var aDate, oDate1, oDate2, iDays
	aDate = sDate1.split("-")
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) // 转换为12-18-2006格式
	aDate = sDate2.split("-")
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
	iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24) // 把相差的毫秒数转换为天数
	return iDays
}
var browserType = function() {

	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	return Sys;
}
/**
 * 比较日期 如果 sDate大于 eDate 返回true 否则 返回false by shendexuan
 * 
 * @param {}
 *            eDate
 * @param {}
 *            sDate
 * @return {Boolean}
 */
var compareDate = function(eDate, sDate) {
	var bTime = Date.parseDate(sDate.getValue(), 'Y-m-d');
	var eTime = Date.parseDate(eDate.getValue(), 'Y-m-d');
	if (eDate.getValue() > sDate.getValue()) {
		return true;
	} else {
		return false
	}
}
var computeYearOfAccrual = function(formPanel) {
	var yearAccrual = 0;
	var accrualVal = 0;
	var accrualObj = formPanel
			.getCmpByName('slSmallloanProject.yearAccrualRate');// 贷款利率
	var managementConsultingOfRateObj = formPanel
			.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate');// 贷款利率
	var financeServiceOfRateObj = formPanel
			.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate');// 贷款利率
	accrualVal = accrualObj.getValue();
	managementConsultingOfRateVal = managementConsultingOfRateObj.getValue();
	financeServiceOfRateVal = financeServiceOfRateObj.getValue()
	yearAccrual = accrualVal + managementConsultingOfRateVal
			+ financeServiceOfRateVal;
	return yearAccrual;
}

/**
 * 根据贷款期限获得相应基准利率
 * @param {} paytype  还款方式
 * @param {} ownLimit 自定义周期长度
 * @param {} priod  还款期数
 * @return {Boolean}
 */
function findBaseRate(paytype,ownLimit,priod) {
	var baseRate=0;
	if(priod.getValue()==""){
		baseRate="-3";
	}else{
		if(paytype.getValue()=="dayPay"){
			baseRate=priod.getValue()/30;
		}else if(paytype.getValue()=="monthPay"){
			baseRate=priod.getValue();
		}else if(paytype.getValue()=="seasonPay"){
			baseRate=priod.getValue()*3;
		}else if(paytype.getValue()=="yearPay"){
			baseRate=priod.getValue()*12;
		}else if(paytype.getValue()=="owerPay"){
			if(ownLimit.getValue()==""){
				baseRate="-1";
			}else{
				baseRate=(priod.getValue()*ownLimit.getValue())/30;
			}
		}else{
			baseRate="-2";
		}
	}
	
	if(baseRate!="-1" && baseRate!="-2" && baseRate!="-3"){
		Ext.Ajax.request({
			url : __ctxPath + "/creditFlow/finance/listProfitRateMaintenance.do",
			method : 'POST',
			scope:this,
			async: false,
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				var result=obj.result[0];
				if(baseRate<=6){//6个月
					baseRate=result.sixMonthLow;
				}else if(6<baseRate<=12){//一年
					baseRate=result.oneYear;
				}else if(12<baseRate<=36){//三年
					baseRate=result.threeYear;
				}else if(36<baseRate<=60){//五年
					baseRate=result.fiveYear;
				}else{
					baseRate=result.fiveYearUp;
				}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询失败!');
			},
			params : {}
		});
	}
	return baseRate;
}
var computeDayOfAccrual = function(formPanel) {

	var yearAccrual = 0;
	var accrualVal = 0;
	var accrualObj = formPanel.getCmpByName('slSmallloanProject.accrual');// 贷款利率
	var managementConsultingOfRateObj = formPanel
			.getCmpByName('slSmallloanProject.managementConsultingOfRate');// 管理咨询费率
	accrualVal = accrualObj.getValue();
	managementConsultingOfRateVal = managementConsultingOfRateObj.getValue();

	yearAccrual = (accrualVal + managementConsultingOfRateVal) * 2 / 30;// 年利率
	var styearAccrual = yearAccrual.toString().split(".");
	if (styearAccrual.length == 2) {

		yearAccrual = styearAccrual[0] + "."
				+ Ext.util.Format.substr(styearAccrual[1], 0, 3)

	}
	// return Ext.util.Format.round(yearAccrual, 3)
	// return Ext.util.Format.number(yearAccrual,'0000.0004');

	return yearAccrual
}
/**
 * 计算两个日期相差天数
 * 
 * @param {}
 *            sDate1
 * @param {}
 *            sDate2
 * @return {}
 */
function getTwoDateDays(sDate1, sDate2) {
	sDate1 = sDate1.format("Y-m-d");
	sDate2 = sDate2.format("Y-m-d");
	var aDate, oDate1, oDate2, iDays;
	oDate1 = new Date(sDate1.split("-").join("/"));// 转换为2002/12/18格式
	oDate2 = new Date(sDate2.split("-").join("/"));
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); // 把相差的毫秒数转换为天数
	return iDays;
}
Ext.namespace("ExtUD.Ext");// 相当于java中包的作用
ExtUD.Ext.CustomerType = Ext.extend(Ext.form.FieldSet, {
	flag : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		var type = this.type;
		Ext.applyIf(this, _cfg);
		var conf = this.flag ? [{
					boxLabel : '企业',
					style : "margin-left:20px;",
					inputValue : "1",
					name : "rg",
					hidden : type == null ? false : type == "person"
							? true
							: false,
					checked : this.flag ? true : false
				}] : [{
					boxLabel : '个人',
					name : "rg",
					inputValue : "2",
					style : "margin-left:20px;",
					hidden : type == null ? false : type == "person"
							? false
							: true,
					checked : this.flag ? false : true
				}];
		ExtUD.Ext.CustomerType.superclass.constructor.call(this, {
			anchor : '100%',
			layout : "column",
			border : false,
			/*
			 * items : [{ //xtype : 'panel', isFormField : true,
			 */
			items : [{
						xtype : "hidden",
						name : 'oppositeType',
						value : this.flag
								? 'company_customer'
								: 'person_customer'
					},/*
						 * { xtype : "textfield", fieldLabel :"1" }
						 */
					new Ext.form.RadioGroup({
						fieldLabel : "radioGroup",
						items : conf,
						listeners : {
							"change" : function(com, checked) {
								var inputValue = com.getValue().inputValue;
								var op = Ext.getCmp('createNewSLFunctionForm');
								var customerInfo = op
										.getCmpByName("customerInfo");
								ressetProjuect();// 用来置空贷款申请的项目名称
								op.remove(customerInfo, true);
								op.doLayout();

								if (inputValue == 1) {
									var testFieldSet = new Ext.form.FieldSet({
										title : '企业客户信息',
										collapsible : true,
										labelAlign : 'right',
										bodyStyle : 'padding-left:0px;',
										autoHeight : true,
										name : 'customerInfo',
										items : [
												new ExtUD.Ext.CustomerType({
															flag : true
														}),
												new ExtUD.Ext.PeerMainInfoPanel(
														{
															isHiddenCustomerDetailBtn : true,
															isAllReadOnly : false,
															isEditEnterprise : true,
															isEnterpriseNameReadOnly : false,
															isHidden : true
														})]
									});
									op.insert(2, testFieldSet);
									op.doLayout();
									return false;

								} else if (inputValue == 2) {
									var testFieldSet = new Ext.form.FieldSet({
										title : '个人客户信息',
										collapsible : true,
										labelAlign : 'right',
										bodyStyle : 'padding-left:0px;',
										autoHeight : true,
										name : 'customerInfo',
										items : [new ExtUD.Ext.CustomerType({
															flag : false
														}), {
													xtype : 'peerPersonMainInfoPanel',
													isHiddenCustomerDetailBtn : true,
													isAllReadOnly : false,
													isEditPerson : true,
													isPersonNameReadOnly : false,
													isHidden : true
												}]
									});
									op.insert(2, testFieldSet);
									op.doLayout();
									return false;
								}
							}
						}
					})]
				/* }] */
		});
	}
});
ExtUD.Ext.ExecutiveOpinion = Ext.extend(Ext.Panel, {
	projectId : null,
	businessType : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.headTitle) != "undefined") {
			this.headTitle = _cfg.headTitle;
		}
		this.businessType = _cfg.businessType;
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ExtUD.Ext.ExecutiveOpinion.superclass.constructor.call(this, {
					layout : 'anchor',
					anchor : '100%',
					id : 'SlProcreditAssuretenetFormWin',
					items : this.gridPanel
				});
	},
	initUIComponents : function() {

		this.gridPanel = new HT.GridPanel({
			isShowTbar : false,
			showPaging : false,
			autoHeight : true,
			hiddenCm : true,
			url : __ctxPath + '/flow/listByRunIdTaskSign.do',
			fields : ['voteName', 'position', 'isAgree', 'comments',
					'createTime', 'taskLimitTime'],
			baseParams : {
				projectId : this.projectId,
				businessType : this.businessType
			},
			columns : [/*
						 * { header : '职位/岗位', width : 126, fixed : true,
						 * dataIndex : 'position', renderer : function(val) { if
						 * (val == null || "" == val) { return '<span
						 * style="color:gray;">无</span>'; } else { return val; } } },
						 */{
						header : '执行人',
						width : 65,
						dataIndex : 'voteName'
					}, {
						header : '开始时间',
						width : 65,
						dataIndex : 'createTime'
					}, {
						header : '截止时间',
						width : 65,
						dataIndex : 'taskLimitTime'
					}, {
						header : '投票结果',
						dataIndex : 'isAgree',
						width : 60,
						renderer : function(val) {
							if (val == null || val == '' || val == 'null') {
								return '<span style="color:gray;">尚未投票</span>';
							} else if (val == 1) {
								return '<span style="color:green;">同意</span>';
							} else if (val == 2) {
								return '<span style="color:red;">否决</span>';
							} else if (val == 3) {
								return '<span style="color:gray;">打回</span>';
							} else if (val == 4) {
								return '<span style="color:blue;">有条件通过</span>';
							} else {
								return '<span style="color:gray;">弃权</span>';
							}
							/*
							 * if (val == 1) { return '<span
							 * style="color:gree;">同意</span>'; } else if (val ==
							 * 2) { return '<span style="color:red;">否决</span>'; }
							 * else if(val == 0){ return '<span
							 * style="color:gray;">弃权</span>'; } else{ return '<span
							 * style="color:gray;">尚未审核</span>'; }
							 */
						}
					}, {
						header : '意见与说明',
						dataIndex : 'comments',
						renderer : function(val) {
							if (val == null || "" == val) {
								return '<span style="color:gray;">无意见与说明</span>';
							} else {
								return val;
							}
						}
					}]
		})

	}
});
ExtUD.Ext.ZmNormalBasicProjectInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 87;
		var rightlabel = 90;
		ExtUD.Ext.ZmNormalBasicProjectInfo.superclass.constructor.call(this, {
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
							xtype : 'hidden',
							name : 'gLGuaranteeloanProject.projectId'
						}, {
							xtype : 'hidden',
							name : 'businessType'
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										name : "gLGuaranteeloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%"// 控制文本框的长度

									}]
						}, {
							columnWidth : .295, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "gLGuaranteeloanProject.projectNumber",
										allowBlank : false,
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "100%"

									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : "textfield",
										anchor : "100%",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										name : "businessTypeKey",
										fieldLabel : "业务类别"
									}]
						}, {
							columnWidth : .405, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										fieldLabel : "业务品种",
										xtype : "textfield",
										name : "operationTypeKey",
										readOnly : true,
										anchor : "100%"
									}]
						}, {
							columnWidth : .295, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										fieldLabel : "流程类别",
										xtype : "textfield",
										name : "flowTypeKey",
										anchor : '100%',
										readOnly : true
									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							items : [{
										fieldLabel : "我方主体类型",
										xtype : "textfield",
										name : "mineTypeKey",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										anchor : "100%"
									}]

						}, {
							columnWidth : .405, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										xtype : "textfield",
										name : "gLGuaranteeloanProject.mineName",
										fieldLabel : "我方主体",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										anchor : "100%"
									}]

						}, {
							columnWidth : .295, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										fieldLabel : "项目经理",
										xtype : "textfield",
										name : "manager",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										anchor : "100%"
									}]

						}]
			}]
		});
	}
});
ExtUD.Ext.ZmNormalProjectInfo = Ext.extend(Ext.Panel, {

	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 86;
		var centerlabel = 67;
		var rightlabel = 86;
		ExtUD.Ext.ZmNormalProjectInfo.superclass.constructor.call(this, {
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
							name : 'gLGuaranteeloanProject.projectStatus',
							xtype : 'hidden'
						}, {
							name : 'gLGuaranteeloanProject.bmStatus',
							xtype : 'hidden'
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "textfield",
								labelWidth : rightlabel,
								fieldLabel : "贷款金额",
								fieldClass : 'field-align',
								name : "projectMoney1",
								readOnly : this.isAllReadOnly,
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
									change : function(nf) {

										var value = nf.getValue();
										var index = value.indexOf(".");
										if (index <= 0) { // 如果第一次输入 没有进行格式化
											nf.setValue(Ext.util.Format.number(
													value, '0,000.00'))
											this
													.getCmpByName("gLGuaranteeloanProject.projectMoney")
													.setValue(value);
										} else {

											if (value.indexOf(",") <= 0) {
												var ke = Ext.util.Format
														.number(value,
																'0,000.00')
												nf.setValue(ke);
												this
														.getCmpByName("gLGuaranteeloanProject.projectMoney")
														.setValue(value);
											} else {
												var last = value
														.substring(index + 1,
																value.length);
												if (last == 0) {
													var temp = value.substring(
															0, index);
													temp = temp.replace(/,/g,
															"");
													this
															.getCmpByName("gLGuaranteeloanProject.projectMoney")
															.setValue(temp);
												} else {
													var temp = value.replace(
															/,/g, "");
													this
															.getCmpByName("gLGuaranteeloanProject.projectMoney")
															.setValue(temp);
												}
											}

										}
									}
								}
							}, {
								xtype : "hidden",
								name : "gLGuaranteeloanProject.projectMoney"
							}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 22,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>元</span> ",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .27, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								name : 'gLGuaranteeloanProject.currencyType',
								hiddenName : 'gLGuaranteeloanProject.currencyType',
								nodeKey : 'gLGuaranteeloan_currencyType',
								readOnly : this.isAllReadOnly,
								value : null,
								emptyText : "请选择",
								anchor : "100%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == null) {

												st.load({
													"callback" : function() {
														if (st.getCount() > 0) {
															var record = st
																	.getAt(0);
															var v = record.data.itemId;
															combox.setValue(v);
														}
													}
												});
											}
											if (combox.getValue() > 0) {
												combox.setValue(combox
														.getValue());

											}
										})
									}
								}
							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
								fieldLabel : "信贷种类",
								xtype : "dickeycombo",
								allowBlank : false,
								name : 'gLGuaranteeloanProject.creditType',
								nodeKey : 'gLGuaranteeloan_creditType',
								hiddenName : 'gLGuaranteeloanProject.creditType',
								readOnly : this.isAllReadOnly,
								value : null,
								emptyText : "请选择",
								anchor : "100%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.clearInvalid();
													if (combox.getValue() > 0) {
														combox.setValue(combox
																.getValue());

													}
												})
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "datefield",
								format : 'Y-m-d',
								name : "gLGuaranteeloanProject.acceptDate",
								allowBlank : false,
								fieldLabel : "担保起始日期",
								fieldClass : 'field-align',
								readOnly : this.isAllReadOnly,
								blankText : "担保起始日期不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf, date) {
										var date1 = Ext.util.Format.date(
												nf.ownerCt.nextSibling()
														.nextSibling().get(0)
														.getValue(), 'Y-m-d');
										if (date1 == "") {
											return false;
										}
										var date2 = Ext.util.Format.date(nf
														.getValue(), 'Y-m-d');
										var month1 = parseInt(
												date1.split("-")[0], 10)
												* 12
												+ parseInt(date1.split("-")[1],
														10);// 注意分隔符是-
										var month2 = parseInt(
												date2.split("-")[0], 10)
												* 12
												+ parseInt(date2.split("-")[1],
														10);
										var dueTime = month1 - month2;
										if (dueTime > 0) // 结束月大于起始月
										{

										} else if (dueTime == 0) { // 结束月等于起始月
											var days = (DateDiff(date1, date2));
											if (days > 0) {
												dueTime = dueTime + 1;
											}

										} else {
											nf.ownerCt.nextSibling()
													.nextSibling()
													.nextSibling().get(0)
													.setValue(null);
											return false;
										}
										nf.ownerCt.nextSibling().nextSibling()
												.nextSibling().get(0)
												.setValue(dueTime);
									}
								}
							}]
						}, {
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : " ",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .27, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
								xtype : "datefield",
								format : 'Y-m-d',
								name : "gLGuaranteeloanProject.intentDate",
								allowBlank : false,
								fieldLabel : "担保截至日期",
								fieldClass : 'field-align',
								readOnly : this.isAllReadOnly,
								blankText : "担保截至日期不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf, date) {
										var date1 = Ext.util.Format.date(nf
														.getValue(), 'Y-m-d');
										var date2 = Ext.util.Format.date(
												nf.ownerCt.previousSibling()
														.previousSibling()
														.get(0).getValue(),
												'Y-m-d');
										if (date2 == "") {
											return false;
										}
										var month1 = parseInt(
												date1.split("-")[0], 10)
												* 12
												+ parseInt(date1.split("-")[1],
														10);// 注意分隔符是-
										var month2 = parseInt(
												date2.split("-")[0], 10)
												* 12
												+ parseInt(date2.split("-")[1],
														10);
										var temp;
										var dueTime = month1 - month2;
										if (dueTime > 0) {
										} else if (dueTime == 0) {
											var days = (DateDiff(date1, date2));
											if (days > 0) {
												dueTime = dueTime + 1;
											}
										} else {
											nf.ownerCt.nextSibling().get(0)
													.setValue(null);
											return false;
										}
										nf.ownerCt.nextSibling().get(0)
												.setValue(dueTime);

									}
								}
							}]
						}, {
							columnWidth : .38, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										xtype : "numberfield",
										name : "gLGuaranteeloanProject.dueTime",
										style : {
											imeMode : 'disabled'
										},
										fieldClass : 'field-align',
										allowBlank : false,
										fieldLabel : "期   限",
										fieldClass : 'field-align',
										readOnly : this.isAllReadOnly,
										blankText : "贷款期限不能为空，请正确填写!",
										anchor : "100%"
									}]
						}, {
							columnWidth : .02, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "月",
										labelSeparator : '',
										anchor : "100%"
									}]
						}]
			}]
		});
	}
});

ExtUD.Ext.BankInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	isAllReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		var centerlabel = 110;
		var rightlabel = 85;
		ExtUD.Ext.BankInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					labelWidth : leftlabel
				},
				items : [{
							columnWidth : .6,
							layout : "form",
							labelWidth : leftlabel,
							items : [{
										xtype : 'hidden',
										name : 'customerBankRelationPerson.id' // 主键ID
									}, {
										xtype : 'hidden',
										name : 'customerBankRelationPerson.fenbankid' // 主键ID
									}, {
										xtype : 'hidden',
										name : 'customerBankRelationPerson.bankid' // 主键ID
									}, {
										fieldLabel : "贷款银行",
										name : 'customerBankRelationPerson.bankName',
										triggerClass : 'x-form-search-trigger',
										xtype : "textfield",
										blankText : "贷款银行不能为空，请正确填写!",
										allowBlank : false,
										anchor : "100%",
										readOnly : true
									}]
						}, {
							columnWidth : .4,
							layout : "form",
							labelWidth : rightlabel,
							items : [{
								fieldLabel : "银行联系人",
								triggerClass : 'x-form-search-trigger',
								xtype : "combo",
								allowBlank : false,
								name : 'customerBankRelationPerson.name',
								readOnly : this.isAllReadOnly,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%",
								scope : this,
								onTriggerClick : function(cc) {
									var personObj = this;
									var operateObj = personObj.ownerCt
											.previousSibling();
									var testValue = function(obj) {
										var id = obj.id;
										var bankid = obj.bankid;
										var fenbankid = obj.fenbankid;

										Ext.Ajax.request({
											url : __ctxPath
													+ '/system/getDicAreaDynam.do',
											method : 'post',
											success : function(response,
													request) {

												var obj1 = Ext
														.decode(response.responseText);
												var remarks = obj1.data.remarks;
												var name = obj.name;
												var email = obj.email;
												var duty = obj.duty;
												var blmphone = obj.blmphone;
												var blmtelephone = obj.blmtelephone;
												var bankname = obj.bankname;
												if (obj.fenbankvalue != "") {
													bankname = bankname + "_"
															+ obj.fenbankvalue;
												}
												if (remarks != ""
														&& typeof(remarks) != "undefined") {
													bankname = remarks;
												}
												personObj.setValue(name);
												operateObj.get(3)
														.setValue(bankname); // 银行名称
												operateObj.get(0).setValue(id); // 银行名称
												operateObj.get(1)
														.setValue(fenbankid); // 银行名称
												personObj.ownerCt.nextSibling()
														.get(0)
														.setValue(blmtelephone);
												personObj.ownerCt.nextSibling()
														.nextSibling().get(0)
														.setValue(duty);
												personObj.ownerCt.nextSibling()
														.nextSibling()
														.nextSibling().get(0)
														.setValue(email);

											},
											failure : function(response) {
											},
											params : {
												id : fenbankid
											}
										});

									}
									selectBankRelationPersonTotal(testValue,
											"bank")

									Ext.Ajax.request({
										url : __ctxPath
												+ '/system/getDicAreaDynam.do',
										method : 'post',
										success : function(response, request) {

											var obj1 = Ext
													.decode(response.responseText);
											var remarks = obj1.data.remarks;
											var name = obj.name;
											var email = obj.email;
											var duty = obj.duty;
											var blmphone = obj.blmphone;
											var blmtelephone = obj.blmtelephone;
											var bankname = obj.bankname;
											if (obj.fenbankvalue != "") {
												bankname = bankname + "_"
														+ obj.fenbankvalue;
											}
											if (remarks != ""
													&& typeof(remarks) != "undefined") {
												bankname = remarks;
											}
											personObj.setValue(name);
											operateObj.get(3)
													.setValue(bankname); // 银行名称
											operateObj.get(0).setValue(id); // 银行名称
											operateObj.get(1)
													.setValue(fenbankid); // 银行名称
											personObj.ownerCt.nextSibling()
													.get(0)
													.setValue(blmtelephone);
											personObj.ownerCt.nextSibling()
													.nextSibling().get(0)
													.setValue(duty);
											personObj.ownerCt.nextSibling()
													.nextSibling()
													.nextSibling().get(0)
													.setValue(email);

										},
										failure : function(response) {
										},
										params : {
											id : fenbankid
										}
									});
									selectBankRelationPersonTotal(testValue,
											"bank")
								}

							}]
						}, {
							columnWidth : .3,
							layout : "form",
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "电话号码",
								xtype : "textfield",
								name : 'customerBankRelationPerson.blmtelephone',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								blankText : "联系电话不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									blur : function(obj) {
										if (obj.getValue() == ""
												|| obj.getValue() == null) {
											obj.clearInvalid();
										}
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "职务",
								xtype : "textfield",
								name : "customerBankRelationPerson.duty",
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								blankText : "职务不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									blur : function(obj) {
										if (obj.getValue() == ""
												|| obj.getValue() == null) {
											obj.clearInvalid();
										}
									}
								}

							}]
						}, {
							columnWidth : .4,
							layout : "form",
							labelWidth : rightlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
								fieldLabel : "邮箱",
								xtype : "textfield",
								readOnly : this.isAllReadOnly,
								name : 'customerBankRelationPerson.email',
								allowBlank : true,// update by gao 统一不进行非空验证
								blankText : "邮箱不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									blur : function(obj) {
										if (obj.getValue() == ""
												|| obj.getValue() == null) {
											obj.clearInvalid();
										}
									}
								}

							}]
						}]
			}]
		});
	}
});
ExtUD.Ext.ProjectNameTextField = Ext.extend(Ext.form.TextField, {
	fieldLabel : "项目名称",
	name : "projectName",
	anchor : "100%",
	hiddenMineType : false,
	blankText : "项目名称不能为空，请正确填写!",
	allowBlank : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.hiddenMineType) {
			this.hiddenMineType = _cfg.hiddenMineType;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.ProjectNameTextField.superclass.constructor.call(this, {
			listeners : {
				render : function(p) {
					var hiddenMineType = this.hiddenMineType;
					p.getEl().on('click', function(p) {

						if (Ext.getCmp("NewProjectForm")
								.getCmpByName('projectName').getValue() != "") {
							return;
						}
						var cusName = "";
						var mType = 'person_customer';
						var businessType = Ext
								.getCmp("createNewSLFunctionForm").getForm()
								.findField('businessType').getValue(); // 业务类别
						if (Ext.getCmp("NewProjectForm")
								.getCmpByName('oppositeType') != null) {
							mType = Ext.getCmp("NewProjectForm")
									.getCmpByName('oppositeType').getValue();
						}
						if (businessType == 1067) {
							cusName = Ext.getCmp("NewProjectForm")
									.getCmpByName('investPerson.perName')
									.getValue();
						} else {
							if (mType == "company_customer") {
								cusName = Ext
										.getCmp("NewProjectForm")
										.getCmpByName('enterprise.enterprisename')
										.getValue();
							} else if (mType == "person_customer") {
								cusName = Ext.getCmp("NewProjectForm")
										.getCmpByName('person.name').getValue(); // 对方主体类型
							}
						}
						var flowType = Ext.getCmp("createNewSLFunctionForm")
								.getForm().findField('flowType').getValue(); // 业务品种\
						var mineType = hiddenMineType ? "company_ourmain" : Ext
								.getCmp("NewProjectForm")
								.getCmpByName('mineType').getValue(); // 我方主体类型
						var mineId = Ext.getCmp("createNewSLFunctionForm")
								.getForm().findField('mineId').getValue(); // 我发主体ID
						var operationType = Ext
								.getCmp("createNewSLFunctionForm").getForm()
								.findField('operationType').getValue(); // 业务品种
						var mineTypeV = hiddenMineType ? "企业" : Ext
								.getCmp("createNewSLFunctionForm").getForm()
								.findField('mineType').getRawValue(); // 我方主体类型
						var mineIdV = Ext.getCmp("createNewSLFunctionForm")
								.getForm().findField('mineId').getRawValue(); // 我发主体ID
						// var businessType =
						// Ext.getCmp("createNewSLFunctionForm").getForm().findField('businessType').getValue();
						// // 业务类别
						var operationTypev;
						if (operationType == 'TwoGrades') {
							operationTypev = "TwoGrades";// 两级目录不需要operationType有值,为了适应所有分级，此处设置一个非空值，可以往下执行。
						} else {
							operationTypev = Ext
									.getCmp("createNewSLFunctionForm")
									.getForm().findField('operationType')
									.getRawValue(); // 业务品种
						}
						if (mineTypeV == "" || mineIdV == "" || cusName == ""
								|| operationTypev == "") {
							return false;
						}
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/getProjectNameCreditProject.do',
							params : {
								"mineType" : mineType,
								"mineId" : mineId,
								"operationType" : operationType,
								"cusName" : cusName,
								"flowType" : flowType,
								"mType" : mType,
								"businessType" : businessType
							},
							method : 'post',
							success : function(response, options) {
								var obj = Ext.decode(response.responseText);
								if (Ext.getCmp("NewProjectForm")) {
									Ext.getCmp("NewProjectForm")
											.getCmpByName('projectName')
											.setValue(obj.data[0].dd);
								}
								if (Ext.getCmp("createNewSLFunctionForm")) {
									Ext.getCmp("createNewSLFunctionForm")
											.getCmpByName('projectName')
											.setValue(obj.data[0].dd);
								}
							}
						});
					})
				}
			}
		})
	}
});
ExtUD.Ext.ProjectBuildButton = Ext.extend(Ext.Button, {
	id : 'getNoButton',
	xtype : 'button',
	text : '系统生成',
	hiddenMineType : false,// 隐藏我方主体类型，则默认为企业类型mineType ：company_ourmain，
							// mineTypeV：企业
	iconCls : 'btn-system-setting',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.hiddenMineType) {
			this.hiddenMineType = _cfg.hiddenMineType;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.ProjectBuildButton.superclass.constructor.call(this, {
			listeners : {
				render : function(p) {
					p.on('click', function(p) {
						if (Ext.getCmp("NewProjectForm")
								.getCmpByName('projectName').getValue() != "") {
							return;
						}
						var cusName = "";
						var mType = 'person_customer';
						var businessType = Ext
								.getCmp("createNewSLFunctionForm").getForm()
								.findField('businessType').getValue(); // 业务类别
						if (Ext.getCmp("NewProjectForm")
								.getCmpByName('oppositeType') != null) {
							mType = Ext.getCmp("NewProjectForm")
									.getCmpByName('oppositeType').getValue();
						}
						if (businessType == 1067) {
							cusName = Ext.getCmp("NewProjectForm")
									.getCmpByName('investPerson.perName')
									.getValue();
						} else {
							if (mType == "company_customer") {
								cusName = Ext
										.getCmp("NewProjectForm")
										.getCmpByName('enterprise.enterprisename')
										.getValue();
							} else if (mType == "person_customer") {
								cusName = Ext.getCmp("NewProjectForm")
										.getCmpByName('person.name').getValue(); // 对方主体类型
							}
						}
						var flowType = Ext.getCmp("createNewSLFunctionForm")
								.getForm().findField('flowType').getValue(); // 业务品种\

						var mineType = p.hiddenMineType
								? "company_ourmain"
								: Ext.getCmp("NewProjectForm")
										.getCmpByName('mineType').getValue(); // 我方主体类型
																				// gaoqingrui

						var mineId = Ext.getCmp("createNewSLFunctionForm")
								.getForm().findField('mineId').getValue(); // 我发主体ID
						var operationType = Ext
								.getCmp("createNewSLFunctionForm").getForm()
								.findField('operationType').getValue(); // 业务品种

						var mineTypeV = p.hiddenMineType ? "企业" : Ext
								.getCmp("createNewSLFunctionForm").getForm()
								.findField('mineType').getRawValue(); // 我方主体类型
																		// gaoqingrui

						var mineIdV = Ext.getCmp("createNewSLFunctionForm")
								.getForm().findField('mineId').getRawValue(); // 我发主体ID
						// var businessType =
						// Ext.getCmp("createNewSLFunctionForm").getForm().findField('businessType').getValue();
						// // 业务类别
						var operationTypev;
						if (operationType == 'TwoGrades') {
							operationTypev = "TwoGrades";// 两级目录不需要operationType有值,为了适应所有分级，此处设置一个非空值，可以往下执行。
						} else {
							operationTypev = Ext
									.getCmp("createNewSLFunctionForm")
									.getForm().findField('operationType')
									.getRawValue(); // 业务品种
						}
						if (mineTypeV == "" || mineIdV == "" || cusName == ""
								|| operationTypev == "") {
							return false;
						}
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/getProjectNameCreditProject.do',
							params : {
								"mineType" : mineType,
								"mineId" : mineId,
								"operationType" : operationType,
								"cusName" : cusName,
								"flowType" : flowType,
								"mType" : mType,
								"businessType" : businessType
							},
							method : 'post',
							success : function(response, options) {
								var obj = Ext.decode(response.responseText);
								if (Ext.getCmp("NewProjectForm")) {
									Ext.getCmp("NewProjectForm")
											.getCmpByName('projectName')
											.setValue(obj.data[0].dd);
								}
								if (Ext.getCmp("createNewSLFunctionForm")) {
									Ext.getCmp("createNewSLFunctionForm")
											.getCmpByName('projectName')
											.setValue(obj.data[0].dd);
								}
							}
						});
					})
				}
			}

		});
	}
});

/*
 * // 3级分类 ExtUD.Ext.TypeSelectInfoThreeGradesPanel = Ext.extend(Ext.Panel, {
 * layout : "form", autoHeight : true, bodyStyle : 'padding-right:18px;',
 * constructor : function(_cfg) { if (_cfg == null) { _cfg = {}; }
 * Ext.applyIf(this, _cfg); this.initComponents();
 * ExtUD.Ext.TypeSelectInfoThreeGradesPanel.superclass.constructor.call(this, {
 * items : [{ layout : "column", defaults : { anchor : '100%', columnWidth : 1,
 * isFormField : true, labelWidth : 70 }, items : [{ columnWidth : .3, //
 * 该列在整行中所占的百分比 layout : "form", // 从上往下的布局 labelWidth : 85, items : [{ xtype :
 * 'hidden', name : 'definitionType', value : null },{ xtype : "combo", anchor :
 * "100%", hiddenName : "businessType", displayField : 'itemName', valueField :
 * 'itemId', triggerAction : 'all', store : new Ext.data.SimpleStore({ autoLoad :
 * true, url : __ctxPath + '/creditFlow/getBusinessTypeListCreditProject.do',
 * fields : ['itemId', 'itemName'] }), fieldLabel : "业务类别", blankText :
 * "业务类别不能为空，请正确填写!", listeners : { afterrender : function(combox) { var st =
 * combox.getStore(); st.on("load", function() { var record = st.getAt(0); var v =
 * record.data.itemId; combox.setValue(v); combox.fireEvent("select",
 * combox,record, 0); }) combox.clearInvalid(); }, select : function(combox,
 * record, index) { var v = record.data.itemId; var arrStore = new
 * Ext.data.ArrayStore({ url : __ctxPath +
 * '/creditFlow/getBusinessTypeListByParentIdCreditProject.do', baseParams : {
 * parentId : v }, fields : ['itemId', 'itemName'], autoLoad : true }); var
 * opr_obj = this.ownerCt.ownerCt.getCmpByName('operationType')
 * opr_obj.clearValue(); opr_obj.store = arrStore; arrStore.load({ "callback" :
 * test }); function test(r) { if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
 * opr_obj.view.setStore(arrStore); } if (typeof(arrStore.getAt(0)) !=
 * "undefined") { var itmeId = arrStore.getAt(0).data.itemId; var itemName =
 * arrStore.getAt(0).data.itemName; opr_obj.setRawValue(itemName);
 * opr_obj.setValue(itmeId); var recordN = arrStore.getAt(0);
 * opr_obj.fireEvent("select", opr_obj,arrStore.getAt(0), 0); } } var obj_n =
 * combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt; ressetProjuect(obj_n); } } }] }, {
 * columnWidth : .3, // 该列在整行中所占的百分比 layout : "form", // 从上往下的布局 items : [{
 * fieldLabel : "业务品种", xtype : "combo", displayField : 'itemName', valueField :
 * 'itemId', triggerAction : 'all', mode : 'remote', hiddenName :
 * "operationType", editable : false, blankText : "业务品种不能为空，请正确填写!", anchor :
 * "100%", listeners : { scope : this, select : function(combox, record, index) {
 * var v = record.data.itemId; var arrStore = new Ext.data.ArrayStore({ url :
 * __ctxPath + '/creditFlow/getProDefinitionByGlobalTypeIdCreditProject.do',
 * fields : ['itemId', 'itemName'], baseParams : { parentId : v } }); var
 * opr_obj = this.ownerCt.get(0).getCmpByName('flowType'); opr_obj.clearValue();
 * opr_obj.store = arrStore; arrStore.load({ "callback" : test }); function
 * test() { if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
 * opr_obj.view.setStore(arrStore); } if (typeof(arrStore.getAt(0)) !=
 * "undefined") { var itmeId = arrStore.getAt(0).data.itemId; var itemName =
 * arrStore.getAt(0).data.itemName; opr_obj.setRawValue(itemName);
 * opr_obj.setValue(itmeId); if (itmeId == "zmNormalFlow") { var bankInfo =
 * Ext.getCmp("NewProjectForm").getCmpByName('bankinfo'); if (null == bankInfo) {
 * opr_obj.fireEvent("change",opr_obj); } } } } var opsideType =
 * this.ownerCt.ownerCt.ownerCt.getCmpByName('customerInfo').getCmpByName('oppositeType').getValue();
 * if (opsideType == "person_customer") { return false; } var obj_n =
 * combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt; ressetProjuect(obj_n); },
 * afterrender : function(combox) {
 *  } } }] }, { columnWidth : .3, // 该列在整行中所占的百分比 layout : "form", // 从上往下的布局
 * items : [{ fieldLabel : "流程类别", xtype : "combo", anchor : "100%", id :
 * "childxilaid_liucheng", mode : 'local', store : new Ext.data.SimpleStore({
 * fields : ['displayText', 'value'], data : [['CI', 3]] }), hiddenName :
 * "flowType", allowBlank : false, editable : false, displayField :
 * 'itemName',// 显示字段值 valueField : 'itemId', blankText : "流程类别不能为空，请正确填写!",
 * emptyText : "请选择", triggerAction : 'all', listeners : { afterrender :
 * function(combox) { combox.clearInvalid(); }, change : function(combox) { var
 * op = Ext.getCmp('createNewSLFunctionForm'); var comValue = combox.getValue();
 * if (comValue == "zmNormalFlow") { var bankSet = new Ext.form.FieldSet({ title :
 * '银行信息', collapsible : true, labelAlign : 'right', name : 'bankinfo',
 * autoHeight : true, items : [new ExtUD.Ext.BankInfoPanel({})] }); var
 * financeSet = new Ext.form.FieldSet({ title : '财务信息', collapsible : true, name :
 * 'financeInfo', labelAlign : 'right', items : [new
 * ExtUD.Ext.ZmNormalProjectInfo( {})] }); bankSet.doLayout(true); op.insert(3,
 * bankSet); op.insert(4, financeSet); op.doLayout(true); } else { var items =
 * op.items; var item1 = items.get(3); var item2 = items.get(4); if (item1.name ==
 * "bankinfo" && item2.name == "financeInfo") { op.remove(item1, true);
 * op.remove(item2, true); op.doLayout(true); } } var obj_n =
 * combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt; ressetProjuect(obj_n); } } }] }] }, {
 * layout : "column", defaults : { anchor : '100%', columnWidth : 1, isFormField :
 * true, labelWidth : 70 }, items : [{ columnWidth : .3, // 该列在整行中所占的百分比 layout :
 * "form", // 从上往下的布局 labelWidth : 85, items : [{ xtype : "dicIndepCombo",
 * labelWidth : 85, fieldLabel : "我方类型", emptyText : "请选择", nodeKey :
 * 'ourmainType', allowBlank : false, anchor : "100%", editable : false,
 * hiddenName : "mineType", scope : this, listeners : { change :
 * function(combox, record, index) { var comboxValue = combox.getValue(); var
 * url = ''; var store = null; var combo = Ext.getCmp('dd_Degree');
 * combo.clearValue(); if (comboxValue == "company_ourmain")// 企业 { url =
 * __ctxPath + '/creditFlow/ourmain/listSlCompanyMain.do'; store = new
 * Ext.data.Store({ proxy : new Ext.data.HttpProxy({ url : url }), reader : new
 * Ext.data.JsonReader({ root : 'result' }, [{ name : 'itemName', mapping :
 * 'corName' }, { name : 'itemValue', mapping : 'companyMainId' }]) }) } else if
 * (comboxValue == "person_ourmain") // 个人 { url = __ctxPath +
 * '/creditFlow/ourmain/listSlPersonMain.do'; store = new Ext.data.Store({ proxy :
 * new Ext.data.HttpProxy({ url : url }), reader : new Ext.data.JsonReader({
 * root : 'result' }, [{ name : 'itemName', mapping : 'name' }, { name :
 * 'itemValue', mapping : 'personMainId' }]) }) } combo.store = store;
 * combo.valueField = "itemValue"; store.load(); if (combo.view) { //
 * 刷新视图,避免视图值与实际值不相符 combo.view.setStore(combo.store); } var obj_n =
 * this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt; ressetProjuect(obj_n); },
 * afterrender : function(combox) { combox.clearInvalid(); }
 *  } }] }, { columnWidth : .3, // 该列在整行中所占的百分比 layout : "form", // 从上往下的布局
 * items : [{ xtype : "combo", id : "dd_Degree", anchor : "100%", fieldLabel :
 * "我方主体", store : new Ext.data.SimpleStore({ fields : ['displayText', 'value'],
 * data : [['地区', 1]] }), emptyText : "请选择", allowBlank : false, displayField :
 * 'itemName', hiddenName : 'mineId', typeAhead : true, mode : 'local', editable :
 * false, selectOnFocus : true, triggerAction : 'all', blankText :
 * "我方主体不能为空，请正确填写!", listeners : { afterrender : function(combox) {
 * combox.clearInvalid(); }, change : function(combox) { var obj_n =
 * combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt; ressetProjuect(obj_n); } }
 *  }] }, { columnWidth : .3, // 该列在整行中所占的百分比 layout : "form", // 从上往下的布局 items : [{
 * xtype : "combo", triggerClass : 'x-form-search-trigger', hiddenName :
 * "degree", editable : false, fieldLabel : "项目经理", blankText :
 * "项目经理不能为空，请正确填写!", allowBlank : false, anchor : "100%", onTriggerClick :
 * function(cc) { var obj = this; var appuerIdObj = obj.nextSibling(); var
 * userIds = appuerIdObj.getValue(); if ("" == obj.getValue()) { userIds = ""; }
 * new UserDialog({ userIds : userIds, userName : obj.getValue(), single : true,
 * title : "选择项目经理", callback : function(uId, uname) { obj.setValue(uId);
 * obj.setRawValue(uname); appuerIdObj.setValue(uId); } }).show(); } }, { xtype :
 * "hidden", value : "" }] }] }] });
 *  }, initComponents : function() { } })
 */

// 3级分类
ExtUD.Ext.TypeSelectInfoThreeGradesPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var isSystemGroup = true
		if (isGroup == 'false') {
			isSystemGroup = false
		}
		var pType = this.pType;
		ExtUD.Ext.TypeSelectInfoThreeGradesPanel.superclass.constructor.call(
				this, {
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : .3, // 该列在整行中所占的百分比
							hidden : true,
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "businessType",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								hidden : true,
								disable : true,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/getBusinessTypeListCreditProject.do',
									fields : ['itemId', 'itemName']
								}),
								fieldLabel : "业务类别",
								blankText : "业务类别不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
													combox.fireEvent("select",
															combox, record, 0);
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore(
												{
													url : __ctxPath
															+ '/creditFlow/getBusinessTypeListByParentIdCreditProject.do',
													baseParams : {
														parentId : v
													},
													fields : ['itemId',
															'itemName'],
													autoLoad : true
												});
										var opr_obj = this.ownerCt.ownerCt
												.getCmpByName('operationType')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												var recordN = arrStore.getAt(0);
												opr_obj.fireEvent("select",
														opr_obj, arrStore
																.getAt(0), 0);
											}
										}

										var op = Ext
												.getCmp('createNewSLFunctionForm');
										var comValue = combox.getValue();
										if (comValue == 1067) {
											var item1 = op.items.get(1).items
													.get(0)
													.getCmpByName("leasingType").ownerCt
													.hide();//
											op.items.get(1).items
													.get(0)
													.getCmpByName("leasingType")
													.setDisabled(true);
											var investInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '投资人资料',
														bodyStyle : 'padding-left:0px;padding-top:4px',
														collapsible : true,
														labelAlign : 'right',
														name : 'investInfo',
														autoHeight : true,
														items : [new ExtUD.Ext.PersonInfo(
																{
																	resetProject : true
																})]
													});
											var accountInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '收息账号信息',
														bodyStyle : 'padding-left:0px;padding-top:4px',
														collapsible : true,
														labelAlign : 'right',
														name : 'accountInfo',
														autoHeight : true,
														items : [new ExtUD.Ext.PersonAccountInfo()]
													});

											var items = op.items;
											var item1 = items.get(2);
											var item2 = items.get(3);
											op.remove(item1, true);
											if (items.length >= 4) {
												op.remove(item2, true)
											}
											op.insert(2, investInfo);
											op.insert(3, accountInfo);
											op.doLayout(true);
										} else if (comValue == 1191) {// add
																		// by
																		// gaoqingrui
																		// 有问题，融资租赁的id号是会动态改变的
											var item1 = op.items.get(1).items
													.get(0)
													.getCmpByName("leasingType").ownerCt
													.show();//
											op.items.get(1).items
													.get(0)
													.getCmpByName("leasingType")
													.setDisabled(false);
										} else {
											op.items.get(1).items
													.get(0)
													.getCmpByName("leasingType").ownerCt
													.hide();//
											op.items.get(1).items
													.get(0)
													.getCmpByName("leasingType")
													.setDisabled(true);
											var items = op.items;
											var item1 = items.get(2);
											var item2 = items.get(3);
											var conf = pType
													? [
															new ExtUD.Ext.CustomerType(
																	{
																		flag : true
																	}),
															new ExtUD.Ext.PeerMainInfoPanel(
																	{
																		isHiddenCustomerDetailBtn : true,
																		isAllReadOnly : false,
																		isEditEnterprise : true,
																		isEnterpriseNameReadOnly : false,
																		isHidden : true
																	})]
													: [
															new ExtUD.Ext.CustomerType(
																	{
																		flag : pType
																	}), {
																xtype : 'peerPersonMainInfoPanel',
																isHiddenCustomerDetailBtn : true,
																isAllReadOnly : false,
																isEditPerson : true,
																isPersonNameReadOnly : false,
																isHidden : true
															}]
											var cusInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : pType
																? '企业客户基本信息'
																: '个人客户基本信息',
														bodyStyle : 'padding-left:0px;',
														collapsible : true,
														name : 'customerInfo',
														labelAlign : 'right',
														autoHeight : true,
														// disabled : true,
														items : conf
													});
											if (op.getCmpByName('investInfo') != null
													&& op
															.getCmpByName('accountInfo') != null) {
												op.remove(item1, true);
												op.remove(item2, true);
												op.insert(2, cusInfo);
												op.doLayout(true);
											} else {
												op.remove(item1, true);
												op.insert(2, cusInfo);
												op.doLayout(true);
											}
											if (op.items.get(1).items
													.get(0)
													.getCmpByName("fFLeaseFinanceProject.leaseFinanceType")) {
												op.items.get(1).items
														.get(0)
														.getCmpByName("fFLeaseFinanceProject.leaseFinanceType").ownerCt
														.hide();
												op.items.get(1).items
														.get(0)
														.getCmpByName("fFLeaseFinanceProject.leaseFinanceType")
														.setDisabled(true);
											}
										}
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							hidden : true,
							layout : "form", // 从上往下的布局
							items : [{
								fieldLabel : "业务品种",
								xtype : "combo",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								mode : 'remote',
								hidden : true,
								disable : true,
								hiddenName : "operationType",
								editable : false,
								blankText : "业务品种不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore(
												{
													url : __ctxPath
															+ '/creditFlow/getProDefinitionByGlobalTypeIdCreditProject.do',
													fields : ['itemId',
															'itemName'],
													baseParams : {
														parentId : v
													}
												});
										var opr_obj = this.ownerCt.get(0)
												.getCmpByName('flowType');
										// opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test() {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												if (itmeId == "zmNormalFlow") {
													var bankInfo = Ext
															.getCmp("NewProjectForm")
															.getCmpByName('bankinfo');
													if (null == bankInfo) {
														opr_obj.fireEvent(
																"change",
																opr_obj);
													}
												}
											}
										}
										/*
										 * var opsideType =
										 * this.ownerCt.ownerCt.ownerCt.getCmpByName('customerInfo').getCmpByName('oppositeType').getValue();
										 * if (opsideType == "person_customer") {
										 * return false; }
										 */
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									},
									afterrender : function(combox) {

									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							hidden : true,
							labelWidth : 85,
							items : [{
										xtype : 'hidden',
										name : "flowType",
										value : 'SmallloanBusinessType'
									}, {
										fieldLabel : "流程类别",
										xtype : "combo",
										hidden : true,
										disable : true,
										anchor : "100%",
										id : "childxilaid_liucheng",
										mode : 'local',
										store : new Ext.data.SimpleStore({
													fields : ['displayText',
															'value'],
													data : [['CI', 3]]
												}),
										hiddenName : "flowType",
										allowBlank : true,
										editable : false,
										value : 'SmallloanBusinessType',
										displayField : 'itemName',// 显示字段值
										valueField : 'itemId',
										blankText : "流程类别不能为空，请正确填写!",
										emptyText : "请选择",
										triggerAction : 'all',
										listeners : {
											afterrender : function(combox) {
												combox.clearInvalid();
											},
											change : function(combox) {
												var op = Ext
														.getCmp('createNewSLFunctionForm');
												var comValue = combox
														.getValue();
												if (comValue == "zmNormalFlow") {
													var bankSet = new Ext.form.FieldSet(
															{
																title : '银行信息',
																collapsible : true,
																labelAlign : 'right',
																name : 'bankinfo',
																autoHeight : true,
																items : [new ExtUD.Ext.BankInfoPanel(
																		{})]
															});
													var financeSet = new Ext.form.FieldSet(
															{
																title : '财务信息',
																collapsible : true,
																name : 'financeInfo',
																labelAlign : 'right',
																items : [new ExtUD.Ext.ZmNormalProjectInfo(
																		{})]
															});
													bankSet.doLayout(true);
													op.insert(3, bankSet);
													op.insert(4, financeSet);
													op.doLayout(true);
												} else {
													var items = op.items;
													var item1 = items.get(3);
													var item2 = items.get(4);
													if (item1.name == "bankinfo"
															&& item2.name == "financeInfo") {
														op.remove(item1, true);
														op.remove(item2, true);
														op.doLayout(true);
													}
												}
												var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
												ressetProjuect(obj_n);
											}
										}
									}]
						}]
					}, {
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [isSystemGroup ? {
							hidden : true,
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							items : [{
										fieldLabel : "我方类型",
										nodeKey : 'ourmainType',
										hiddenName : "mineType",
										value : "company_ourmain"
									}]
						} : {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 100,
							items : [{
								xtype : "dicIndepCombo",
								labelWidth : 90,
								fieldLabel : "我方类型",
								emptyText : "请选择",
								nodeKey : 'ourmainType',
								allowBlank : false,
								anchor : "100%",
								editable : false,
								hiddenName : "mineType",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = Ext.getCmp('dd_Degree');
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
										var obj_n = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									},
									afterrender : function(combox) {
										combox.clearInvalid();
									}

								}
							}]
						}, {
							columnWidth : isSystemGroup ? .6 : .7, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							items : [{
								xtype : "combo",
								id : "dd_Degree",
								anchor : "100%",
								fieldLabel : isSystemGroup ? "主体单位名称" : "我方主体",
								displayField : 'itemName',
								valueField : 'itemValue',
								store : isSystemGroup
										? new Ext.data.SimpleStore({
											autoLoad : true,
											// proxy : new Ext.data.HttpProxy({
											url : isSystemGroup
													? __ctxPath
															+ '/system/getControlNameOrganization.do'
													: __ctxPath
															+ '/creditFlow/ourmain/listSlCompanyMain.do',
											// }),
											baseParams : {
												isGroup : isSystemGroup
											},
											fields : ['itemValue', 'itemName']
										})
										: new Ext.data.SimpleStore({
													fields : ['displayText',
															'value'],
													data : [['地区', 1]]
												}),
								emptyText : "请选择",
								allowBlank : false,
								hiddenName : 'mineId',
								typeAhead : true,
								mode : 'local',
								editable : false,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : isSystemGroup
										? "主体单位名称不能为空"
										: "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									},
									change : function(combox) {
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}

							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							hidden : true,
							items : [{
								fieldLabel : "租赁类型",
								xtype : "dicIndepCombo",
								allowBlank : false,
								name : 'leasingType',
								nodeKey : 'leasingType',
								hiddenName : 'leasingType',
								readOnly : this.isAllReadOnly,
								disabled : true,
								value : null,
								emptyText : "请选择",
								anchor : "100%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.clearInvalid();
													if (combox.getValue() > 0) {
														combox.setValue(combox
																.getValue());

													}
												})
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							hidden : true,
							labelWidth : 85,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degreeName",
								editable : false,
								fieldLabel : "项目经理",
								hidden : true,
								disable : true,
								labelWidth : 85,
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : true,
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
												title : "选择项目经理",
												callback : function(uId, uname) {
													obj.setValue(uId);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
								}
							}, {
								xtype : "hidden",
								disable : true,
								name : 'degree',
								value : ""
							}]
						}]
					}, {
						layout : 'column',
						hidden : true,
						items : [{
							layout : 'form',
							columnWidth : .3,
							items : [{
								xtype : 'combo',
								hiddenName : 'slSmallloanProject.availableType',
								name : 'availableType',
								displayField : 'itemName',
								valueField : 'itemValue',
								triggerAction : 'all',
								anchor : '100%',
								mode : 'local',
								store : new Ext.data.ArrayStore({
											fields : ['itemValue', 'itemName'],
											data : [['1', '小贷公司'],
													['2', '担保公司']]
										}),
								fieldLabel : '推介机构类型',
								listeners : {
									scope : this,
									afterrender : function(combo) {
										// combo.fireEvent('select',combo,null,0);
									},
									select : function(combo, record, index) {
										var type = '';
										if (record != null) {

											if (record.data.itemValue == '2') {
												type = 'Guarantee';
											} else if (record.data.itemValue == '1') {
												type = 'SmallLoan';
											}

										}
										var store = new Ext.data.ArrayStore({
											// autoLoad:true,
											url : __ctxPath
													+ "/customer/arrayListInvestEnterprise.do",
											fields : ['itemValue', 'itemName',
													'nowCreditLimit'],
											baseParams : {
												businessType : type
											}
										});
										var cb = combo.ownerCt.nextSibling()
												.get(0);
										cb.store = store;
										store.load({
													callback : test
												});
										function test() {
											if (cb.view) {
												cb.view.setStore(store);
											}
										}
										//  
									}
								}
							}]
						}, {
							layout : 'form',
							columnWidth : .4,
							labelWidth : 120,
							items : [{
								xtype : 'combo',
								hiddenName : 'slSmallloanProject.availableId',
								name : 'iId',
								anchor : '100%',
								displayField : 'itemName',
								valueField : 'itemValue',
								triggerAction : 'all',
								mode : 'remote',
								labelWidth : 120,
								/*
								 * store:new Ext.data.ArrayStore({ url:'',
								 * fields:['itemValue','itemName'],
								 * data:[['1','小贷公司'],['2','担保公司']] }),
								 */
								fieldLabel : '机构名称',
								listeners : {
									select : function(combo, record, index) {

										var credit = combo.ownerCt
												.nextSibling().get(0);
										credit
												.setValue(record.data.nowCreditLimit);
										// var o =
										// credit.ownerCt.ownerCt.ownerCt.getCmpByName('poupseM');
										// var o =
										// credit.ownerCt.ownerCt.ownerCt.getCmpByName('poupseM');
										var money = credit.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt
												.getCmpByName('slSmallloanProject.money')
												.getValue()
										if (money > record.data.nowCreditLimit) {
											Ext.ux.Toast.msg('警告',
													'可用授信额度小于期望贷款金额!');
											combo.reset();
											credit.reset();
										}
									}
								}
							}]
						}, {
							layout : 'form',
							labelWidth : 85,
							columnWidth : .3,
							items : [{
										xtype : 'numberfield',
										readOnly : true,
										anchor : '100%',
										fieldLabel : '可用额度',
										name : 'slSmallloanProject.credit'
									}]
						}]
					}, {
						xtype : 'hidden',
						name : 'definitionType',
						value : null
					}, isSystemGroup ? {
						xtype : 'hidden',
						name : 'mineType',
						value : "company_ourmain"
					} : {}, {
						xtype : 'hidden',
						name : 'isSubCompany',
						value : isSystemGroup
					}]
				});
		this.getCmpByName('degree').setValue(currentUserId)
		this.getCmpByName('degreeName').setValue(currentUserFullName)
	},
	initComponents : function() {
	}
})

// 3级分类,流程节点vm文件用到
ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel.superclass.constructor
				.call(this, {
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 85
						},
						items : [{
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 80,
									items : [{
												xtype : 'hidden',
												name : 'definitionType',
												value : null
											}, {
												xtype : "textfield",
												fieldLabel : "业务类别",
												anchor : "100%",
												readOnly : true,
												name : "businessTypeKey"
											}]
								}, {
									columnWidth : .31, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 96,
									items : [{
												xtype : "textfield",
												fieldLabel : "业务品种",
												anchor : "97%",
												readOnly : true,
												name : "operationTypeKey"
											}]
								}, {
									columnWidth : .39, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 75,
									items : [{
												xtype : "textfield",
												fieldLabel : "流程类别",
												name : "flowTypeKey",
												readOnly : true,
												anchor : "97.5%"
											}, {
												xtype : 'hidden',
												name : 'flFinancingProject.projectId'
											}]
								}]
					}]
				});

	},
	initComponents : function() {
	}
})

// 所有vm节点上用的填写我方主体的panel
ExtUD.Ext.TypeSelectInfoMineType = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoMineType.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "我方类型",
										name : "mineTypeKey",
										readOnly : true,
										anchor : "100%"
									}]
						}, {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 96,
							items : [{
										xtype : 'textfield',
										fieldLabel : '我方主体',
										name : 'mineName',
										anchor : "98.5%",
										readOnly : true
									}]
						}, {
							columnWidth : .39, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : false,
								anchor : "99%",

								readOnly : this.isAllReadOnly,
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
												title : "选择项目经理",
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
			}]
		});

	},
	initComponents : function() {
	}
})

// 二级分类
ExtUD.Ext.TypeSelectInfoTwoGradesPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : this.isFrame,
	bodyStyle : 'padding-right:18px;',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoTwoGradesPanel.superclass.constructor.call(
				this, {
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "businessType",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/getBusinessTypeListCreditProject.do',
									fields : ['itemId', 'itemName']
								}),
								fieldLabel : "业务类别",
								blankText : "业务类别不能为空，请正确填写!",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
													combox.fireEvent("select",
															combox, record, 0);
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore(
												{
													url : __ctxPath
															+ '/creditFlow/getProDefinitionByGlobalTypeIdCreditProject.do',
													fields : ['itemId',
															'itemName'],
													baseParams : {
														parentId : v
													}
												});
										var opr_obj = this.ownerCt
												.getCmpByName('flowType');
										/*
										 * var opr_obj =
										 * this.ownerCt.get(0).getCmpByName('flowType');
										 * alert("flowType==="+opr_obj);
										 */
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test() {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												if (itmeId == "zmNormalFlow") {
													var bankInfo = Ext
															.getCmp("NewProjectForm")
															.getCmpByName('bankinfo');
													if (null == bankInfo) {
														opr_obj.fireEvent(
																"change",
																opr_obj);
													}
												}
											}
										}

										var op = Ext
												.getCmp('createNewSLFunctionForm');
										var comValue = combox.getValue();
										if (comValue == 1067) {
											var investInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '投资人资料',
														bodyStyle : 'padding-left:0px;padding-top:4px',
														collapsible : true,
														labelAlign : 'right',
														name : 'investInfo',
														autoHeight : true,
														items : [new ExtUD.Ext.PersonInfo()]
													});
											var accountInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '收息账号信息',
														bodyStyle : 'padding-left:0px;padding-top:4px',
														collapsible : true,
														labelAlign : 'right',
														name : 'accountInfo',
														autoHeight : true,
														items : [new ExtUD.Ext.PersonAccountInfo()]
													});

											var items = op.items;
											var item1 = items.get(2);
											op.remove(item1, true);
											op.insert(2, investInfo);
											op.insert(3, accountInfo);
											op.doLayout(true);
										} else {
											var items = op.items;
											var item1 = items.get(2);
											var item2 = items.get(3);
											var cusInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '客户基本信息',
														bodyStyle : 'padding-left:0px;',
														collapsible : true,
														name : 'customerInfo',
														labelAlign : 'right',
														autoHeight : true,
														// disabled : true,
														items : [
																new ExtUD.Ext.CustomerType(
																		{
																			flag : true
																		}),
																new ExtUD.Ext.PeerMainInfoPanel(
																		{
																			isHiddenCustomerDetailBtn : true,
																			isAllReadOnly : false,
																			isEditEnterprise : true,
																			isEnterpriseNameReadOnly : false,
																			isHidden : true
																		})]
													});
											if (op.getCmpByName('investInfo') != null
													&& op
															.getCmpByName('accountInfo') != null) {
												op.remove(item1, true);
												op.remove(item2, true);
												op.insert(2, cusInfo);
												op.doLayout(true);
											} else {
												op.remove(item1, true);
												op.insert(2, cusInfo);
												op.doLayout(true);
											}
										}

										/*
										 * var opsideType =
										 * this.ownerCt.ownerCt.ownerCt.getCmpByName('customerInfo').getCmpByName('oppositeType').getValue();
										 * if (opsideType == "person_customer") {
										 * return false; }
										 */
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}
							}]
						}, {
							xtype : 'hidden',
							name : 'operationType',
							value : 'TwoGrades'
						}, {
							columnWidth : .35, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								fieldLabel : "流程类别",
								xtype : "combo",
								anchor : "100%",
								id : "childxilaid_liucheng",
								mode : 'local',
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['CI', 3]]
										}),
								hiddenName : "flowType",
								allowBlank : false,
								editable : false,
								displayField : 'itemName',// 显示字段值
								valueField : 'itemId',
								blankText : "流程类别不能为空，请正确填写!",
								emptyText : "请选择",
								triggerAction : 'all',
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									},
									change : function(combox) {
										var op = Ext
												.getCmp('createNewSLFunctionForm');
										var comValue = combox.getValue();
										if (comValue == "zmNormalFlow") {
											var bankSet = new Ext.form.FieldSet(
													{
														title : '银行信息',
														collapsible : true,
														labelAlign : 'right',
														name : 'bankinfo',
														autoHeight : true,
														items : [new ExtUD.Ext.BankInfoPanel(
																{})]
													});
											var financeSet = new Ext.form.FieldSet(
													{
														title : '财务信息',
														collapsible : true,
														name : 'financeInfo',
														labelAlign : 'right',
														items : [new ExtUD.Ext.ZmNormalProjectInfo(
																{})]
													});
											bankSet.doLayout(true);
											op.insert(3, bankSet);
											op.insert(4, financeSet);
											op.doLayout(true);
										} else {
											var items = op.items;
											var item1 = items.get(3);
											var item2 = items.get(4);
											if (item1.name == "bankinfo"
													&& item2.name == "financeInfo") {
												op.remove(item1, true);
												op.remove(item2, true);
												op.doLayout(true);
											}
										}
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									},
									select : function(combox, record, index) {
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}
							}]
						}]
					}, {
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
								xtype : "dicIndepCombo",
								labelWidth : 85,
								fieldLabel : "我方类型",
								emptyText : "请选择",
								nodeKey : 'ourmainType',
								allowBlank : false,
								anchor : "100%",
								editable : false,
								hiddenName : "mineType",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = Ext.getCmp('dd_Degree');
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
										var obj_n = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									},
									afterrender : function(combox) {
										combox.clearInvalid();
									}

								}
							}]
						}, {
							columnWidth : .35, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								xtype : "combo",
								id : "dd_Degree",
								anchor : "100%",
								fieldLabel : "我方主体",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								allowBlank : false,
								displayField : 'itemName',
								hiddenName : 'mineId',
								typeAhead : true,
								mode : 'local',
								editable : false,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									},
									change : function(combox) {
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}

							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
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
												title : "选择项目经理",
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
					}]
				});

	},
	initComponents : function() {
	}
})

// 二级分类流程节点vm文件使用。
ExtUD.Ext.TypeSelectInfoTwoGradesReadOnlyPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoTwoGradesReadOnlyPanel.superclass.constructor
				.call(this, {
					items : [{
								layout : "column",
								defaults : {
									anchor : '100%',
									columnWidth : 1,
									isFormField : true,
									labelWidth : 85
								},
								items : [{
											columnWidth : .61, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 80,
											items : [{
														xtype : "textfield",
														fieldLabel : "业务类别",
														anchor : "99%",
														readOnly : true,
														name : "businessTypeKey"
													}]
										},/*
											 * { xtype : 'hidden', name :
											 * 'operationType', value :
											 * 'TwoGrades' },
											 */{
											columnWidth : .39, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 80,
											items : [{
														xtype : "textfield",
														fieldLabel : "流程类别",
														name : "flowTypeKey",
														readOnly : true,
														anchor : "99%"
													}]
										}]
							}]
				});

	},
	initComponents : function() {
	}
})

// 四级分类
ExtUD.Ext.TypeSelectInfoFourGradesPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoFourGradesPanel.superclass.constructor.call(
				this, {
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
								xtype : "combo",
								anchor : "100%",
								hiddenName : "businessType",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/getBusinessTypeListCreditProject.do',
									fields : ['itemId', 'itemName']
								}),
								fieldLabel : "业务类别",
								blankText : "业务类别不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
													combox.fireEvent("select",
															combox, record, 0);
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore(
												{
													url : __ctxPath
															+ '/creditFlow/getBusinessTypeListByParentIdCreditProject.do',
													baseParams : {
														parentId : v
													},
													fields : ['itemId',
															'itemName'],
													autoLoad : true
												});
										var opr_obj = this.ownerCt.ownerCt
												.getCmpByName('operationType')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												var recordN = arrStore.getAt(0);
												opr_obj.fireEvent("select",
														opr_obj, arrStore
																.getAt(0), 0);
											}
										}

										var op = Ext
												.getCmp('createNewSLFunctionForm');
										var comValue = combox.getValue();
										if (comValue == 1067) {
											var investInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '投资人资料',
														bodyStyle : 'padding-left:0px;padding-top:4px',
														collapsible : true,
														labelAlign : 'right',
														name : 'investInfo',
														autoHeight : true,
														items : [new ExtUD.Ext.PersonInfo()]
													});
											var accountInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '收息账号信息',
														bodyStyle : 'padding-left:0px;padding-top:4px',
														collapsible : true,
														labelAlign : 'right',
														name : 'accountInfo',
														autoHeight : true,
														items : [new ExtUD.Ext.PersonAccountInfo()]
													});

											var items = op.items;
											var item1 = items.get(2);
											op.remove(item1, true);
											op.insert(2, investInfo);
											op.insert(3, accountInfo);
											op.doLayout(true);
										} else {
											var items = op.items;
											var item1 = items.get(2);
											var item2 = items.get(3);
											var cusInfo = new Ext.form.FieldSet(
													{
														xtype : 'fieldset',
														title : '客户基本信息',
														bodyStyle : 'padding-left:0px;',
														collapsible : true,
														name : 'customerInfo',
														labelAlign : 'right',
														autoHeight : true,
														// disabled : true,
														items : [
																new ExtUD.Ext.CustomerType(
																		{
																			flag : true
																		}),
																new ExtUD.Ext.PeerMainInfoPanel(
																		{
																			isHiddenCustomerDetailBtn : true,
																			isAllReadOnly : false,
																			isEditEnterprise : true,
																			isEnterpriseNameReadOnly : false,
																			isHidden : true
																		})]
													});
											if (op.getCmpByName('investInfo') != null
													&& op
															.getCmpByName('accountInfo') != null) {
												op.remove(item1, true);
												op.remove(item2, true);
												op.insert(2, cusInfo);
												op.doLayout(true);
											} else {
												op.remove(item1, true);
												op.insert(2, cusInfo);
												op.doLayout(true);
											}
										}

										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}
							}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								fieldLabel : "业务品种",
								xtype : "combo",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								mode : 'remote',
								hiddenName : "operationType",
								editable : false,
								blankText : "业务品种不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									/*
									 * afterrender : function(combox) { var st =
									 * combox.getStore(); alert("stst==="+st+"
									 * combox=="+combox); st.on("load",
									 * function() { var record = st.getAt(0);
									 * var v = record.data.itemId;
									 * combox.setValue(v);
									 * combox.fireEvent("select", combox,record,
									 * 0); }) combox.clearInvalid(); },
									 */
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore(
												{
													url : __ctxPath
															+ '/creditFlow/getBusinessTypeListByParentIdCreditProject.do',
													baseParams : {
														parentId : v
													},
													fields : ['itemId',
															'itemName'],
													autoLoad : true
												});
										var opr_obj = this.ownerCt.ownerCt
												.getCmpByName('definitionType');
										// opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												var recordN = arrStore.getAt(0);
												opr_obj.fireEvent("select",
														opr_obj, arrStore
																.getAt(0), 0);
											}
										}
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
									/*
									 * scope : this, select : function(combox,
									 * record, index) { var v =
									 * record.data.itemId; var arrStore = new
									 * Ext.data.ArrayStore({ url : __ctxPath +
									 * '/creditFlow/getProDefinitionByGlobalTypeIdCreditProject.do',
									 * fields : ['itemId', 'itemName'],
									 * baseParams : { parentId : v } }); var
									 * opr_obj =
									 * this.ownerCt.get(0).getCmpByName('flowType');
									 * opr_obj.clearValue(); opr_obj.store =
									 * arrStore; arrStore.load({ "callback" :
									 * test }); function test() { if
									 * (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
									 * opr_obj.view.setStore(arrStore); } if
									 * (typeof(arrStore.getAt(0)) !=
									 * "undefined") { var itmeId =
									 * arrStore.getAt(0).data.itemId; var
									 * itemName =
									 * arrStore.getAt(0).data.itemName;
									 * opr_obj.setRawValue(itemName);
									 * opr_obj.setValue(itmeId); if (itmeId ==
									 * "zmNormalFlow") { var bankInfo =
									 * Ext.getCmp("NewProjectForm").getCmpByName('bankinfo');
									 * if (null == bankInfo) {
									 * opr_obj.fireEvent("change",opr_obj); } } } }
									 * var opsideType =
									 * this.ownerCt.ownerCt.ownerCt.getCmpByName('customerInfo').getCmpByName('oppositeType').getValue();
									 * if (opsideType == "person_customer") {
									 * return false; } var obj_n =
									 * combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
									 * ressetProjuect(obj_n); }, afterrender :
									 * function(combox) {
									 *  }
									 */}
							}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								fieldLabel : "自定义类型",
								xtype : "combo",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								mode : 'remote',
								hiddenName : "definitionType",
								editable : false,
								blankText : "自定义类型不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore(
												{
													url : __ctxPath
															+ '/creditFlow/getProDefinitionByGlobalTypeIdCreditProject.do',
													fields : ['itemId',
															'itemName'],
													baseParams : {
														parentId : v
													}
												});
										var opr_obj = this.ownerCt.get(0)
												.getCmpByName('flowType');
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test() {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												if (itmeId == "zmNormalFlow") {
													var bankInfo = Ext
															.getCmp("NewProjectForm")
															.getCmpByName('bankinfo');
													if (null == bankInfo) {
														opr_obj.fireEvent(
																"change",
																opr_obj);
													}
												}
											}
										}
										var opsideType = this.ownerCt.ownerCt.ownerCt
												.getCmpByName('customerInfo')
												.getCmpByName('oppositeType')
												.getValue();
										if (opsideType == "person_customer") {
											return false;
										}
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									},
									afterrender : function(combox) {

									}
								}
							}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								fieldLabel : "流程类别",
								xtype : "combo",
								anchor : "100%",
								id : "childxilaid_liucheng",
								mode : 'local',
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['CI', 3]]
										}),
								hiddenName : "flowType",
								allowBlank : false,
								editable : false,
								displayField : 'itemName',// 显示字段值
								valueField : 'itemId',
								blankText : "流程类别不能为空，请正确填写!",
								emptyText : "请选择",
								triggerAction : 'all',
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									},
									change : function(combox) {
										var op = Ext
												.getCmp('createNewSLFunctionForm');
										var comValue = combox.getValue();
										if (comValue == "zmNormalFlow") {
											var bankSet = new Ext.form.FieldSet(
													{
														title : '银行信息',
														collapsible : true,
														labelAlign : 'right',
														name : 'bankinfo',
														autoHeight : true,
														items : [new ExtUD.Ext.BankInfoPanel(
																{})]
													});
											var financeSet = new Ext.form.FieldSet(
													{
														title : '财务信息',
														collapsible : true,
														name : 'financeInfo',
														labelAlign : 'right',
														items : [new ExtUD.Ext.ZmNormalProjectInfo(
																{})]
													});
											bankSet.doLayout(true);
											op.insert(3, bankSet);
											op.insert(4, financeSet);
											op.doLayout(true);
										} else {
											var items = op.items;
											var item1 = items.get(3);
											var item2 = items.get(4);
											if (item1.name == "bankinfo"
													&& item2.name == "financeInfo") {
												op.remove(item1, true);
												op.remove(item2, true);
												op.doLayout(true);
											}
										}
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}
							}]
						}]
					}, {
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [{
								xtype : "dicIndepCombo",
								labelWidth : 85,
								fieldLabel : "我方类型",
								emptyText : "请选择",
								nodeKey : 'ourmainType',
								allowBlank : false,
								anchor : "100%",
								editable : false,
								hiddenName : "mineType",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = Ext.getCmp('dd_Degree');
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
										var obj_n = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									},
									afterrender : function(combox) {
										combox.clearInvalid();
									}

								}
							}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								xtype : "combo",
								id : "dd_Degree",
								anchor : "100%",
								fieldLabel : "我方主体",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								allowBlank : false,
								displayField : 'itemName',
								hiddenName : 'mineId',
								typeAhead : true,
								mode : 'local',
								editable : false,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									},
									change : function(combox) {
										var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
										ressetProjuect(obj_n);
									}
								}

							}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
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
												title : "选择项目经理",
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
					}]
				});

	},
	initComponents : function() {
	}
})

// 四级分类只读，流程节点vm文件使用
ExtUD.Ext.TypeSelectInfoFourGradesReadOnlyPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoFourGradesReadOnlyPanel.superclass.constructor
				.call(this, {
					items : [{
								layout : "column",
								defaults : {
									anchor : '100%',
									columnWidth : 1,
									isFormField : true,
									labelWidth : 70
								},
								items : [{
											columnWidth : .2, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 80,
											items : [{
														xtype : "textfield",
														fieldLabel : "业务类别",
														anchor : "100%",
														readOnly : true,
														name : "businessTypeKey"
													}]
										}, {
											columnWidth : .2, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											items : [{
														xtype : "textfield",
														fieldLabel : "业务品种",
														anchor : "100%",
														readOnly : true,
														name : "operationTypeKey"
													}]
										}, {
											columnWidth : .21, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 80,
											items : [{
														xtype : "textfield",
														fieldLabel : "自定义类型",
														anchor : "98.5%",
														readOnly : true,
														name : "definitionTypeKey"
													}]
										}, {
											columnWidth : .39, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 80,
											items : [{
														xtype : "textfield",
														fieldLabel : "流程类别",
														name : "flowTypeKey",
														readOnly : true,
														anchor : "99%"
													}]
										}]
							}]
				});

	},
	initComponents : function() {
	}
})

// 小贷流程中节点调用的客户信息页面
// 公司客户
ExtUD.Ext.PeerMainInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : true,
	bussinessType : null,
	isHideGudongInfo : false,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isEditEnterprise : false,
	isEnterpriseNameReadOnly : true,
	isNewProjectFormId:null,
	isGudongDiseditable : true,// 高庆瑞新加,与isAllReadOnly相与，不影响之前配置的
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.bussinessType) != "undefined") {
			this.bussinessType = _cfg.bussinessType;
		}
		if (typeof(_cfg.isEnterpriseNameReadOnly) != "undefined") {
			this.isEnterpriseNameReadOnly = _cfg.isEnterpriseNameReadOnly;
		}if (typeof(_cfg.isNewProjectFormId) != "undefined") {
			this.isNewProjectFormId = _cfg.isNewProjectFormId;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
			this.isLoadShareequity = true;
			this.isEnterpriseShortNameReadonly = true;
		}
		if (_cfg.isHideGudongInfo) {
			this.isHideGudongInfo = _cfg.isHideGudongInfo;
		}
		if (_cfg.isEnterprisenameReadonly) {
			this.isEnterprisenameReadonly = _cfg.isEnterprisenameReadonly;
		}
		if (_cfg.isEnterpriseShortNameReadonly) {
			this.isEnterpriseShortNameReadonly = _cfg.isEnterpriseShortNameReadonly;
		}
		if (_cfg.isHiddenCustomerDetailBtn) {
			this.isHiddenCustomerDetailBtn = _cfg.isHiddenCustomerDetailBtn;
		}
		if (typeof(_cfg.isEditEnterprise) != "undefined") {
			this.isEditEnterprise = _cfg.isEditEnterprise;
		}
		if (typeof(_cfg.isGudongDiseditable) != 'undefined') {
			this.isGudongDiseditable = _cfg.isGudongDiseditable;
		}
		Ext.applyIf(this, _cfg);
		var bankAreaRootControl = false;
		if (_cfg.bankAreaRootControl) {
			bankAreaRootControl = _cfg.bankAreaRootControl;
		}
		var leftlabel = 100;
		var rightlabel = 90
		var formPanelId=this.formPanelId;
		var createNewSLFunctionForm=this.createNewSLFunctionForm;
		ExtUD.Ext.PeerMainInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column", // 定义该元素为布局为列布局方式
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true
				},
				border : false,
				scope : this,
				items : [{
					bodyStyle : 'padding-right:10px',
					columnWidth : .6, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'fieldset',
						title : '基本信息',
						layout : "column",
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
							columnWidth : 0.7, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : 'hidden',
										name : 'enterprise.id'
									}, {
										xtype : 'combo',
										anchor : '100%',
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "企业名称",
										name : "enterprise.enterprisename",
										readOnly : this.isEnterpriseNameReadOnly,
										blankText : "企业名称不能为空，请正确填写!",
										allowBlank : false,
										scope : this,
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												op.getCmpByName('enterprise.enterprisename').setValue("");
												op.getCmpByName('enterprise.id').setValue("");
												op.getCmpByName('enterprise.shortname').setValue("");
												op.getCmpByName('enterprise.surplusMoney').setValue("");
												op.getCmpByName('enterprise.area').setValue("");
												op.getCmpByName('enterprise.cciaa').setValue("");
												op.getCmpByName('enterprise.organizecode').setValue("");
												op.getCmpByName('enterprise.telephone').setValue("");
												op.getCmpByName('enterprise.postcoding').setValue("");
												op.getCmpByName('enterprise.hangyeType').setValue("");
												op.getCmpByName('enterprise.hangyeName').setValue("");
												op.getCmpByName('enterprise.documentType').setValue("");
												op.getCmpByName('enterprise.taxnum').setValue("");
												op.getCmpByName('person.id').setValue("");
												op.getCmpByName('person.name').setValue("");
												op.getCmpByName('person.sex').setValue("");
												op.getCmpByName('person.cardtype').setValue("");
												op.getCmpByName('person.cardnumber').setValue("");
												op.getCmpByName('person.cellphone').setValue("");
												op.getCmpByName('person.selfemail').setValue("");
												
												if (obj.enterprisename != 0 && obj.enterprisename != "")
													op.getCmpByName('enterprise.enterprisename').setValue(obj.enterprisename);
												if (obj.id != 0 && obj.id != "")
													op.getCmpByName('enterprise.id').setValue(obj.id);
                                               var enterpriseMoney = op.getCmpByName('enterprise.surplusMoney').setValue(obj.surplusMoney);
												if (obj.shortname != 0 && obj.shortname != "")
													op.getCmpByName('enterprise.shortname').setValue(obj.shortname);
												if (obj.area != 0 && obj.area != "")
													op.getCmpByName('enterprise.area').setValue(obj.area);
												if (obj.cciaa != 0&& obj.cciaa != "")
													op.getCmpByName('enterprise.cciaa').setValue(obj.cciaa);
												if (obj.organizecode != 0 && obj.organizecode != ""){
													var organizecode=op.getCmpByName('enterprise.organizecode');
													organizecode.setValue(obj.organizecode);
													if(obj.isCheckCard){
														organizecode.setReadOnly(true);
														if(organizecode.getEl().dom.className.indexOf("readOnlyClass")==-1){
															organizecode.getEl().dom.className+=" readOnlyClass";
														}
													}
												}
												if (obj.telephone != 0 && obj.telephone != "")
													op.getCmpByName('enterprise.telephone').setValue(obj.telephone);
												if (obj.postcoding != 0 && obj.postcoding != "")
													op.getCmpByName('enterprise.postcoding').setValue(obj.postcoding);
												if (obj.hangyetype != 0 && obj.hangyetype != "") {
													op.getCmpByName('enterprise.hangyeType').setValue(obj.hangyetype);
													op.getCmpByName('enterprise.hangyeName').setValue(obj.hangyetypevalue);
												}
												if (obj.documentType != 0 && obj.documentType != ""){
													var documentType=op.getCmpByName('enterprise.documentType');
													documentType.setValue(obj.documentType);
													if(obj.isCheckCard){
														documentType.setReadOnly(true);
														if(documentType.getEl().dom.className.indexOf("readOnlyClass")==-1){
															documentType.getEl().dom.className+=" readOnlyClass";
														}
													}
													var combo = op.getCmpByName('enterprise.organizecode');
													if(obj.documentType==1 ){
														var a = combo.el.parent().parent().first();
														combo.allowBlank=false;
										 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码";
										 				combo.fieldLabel = '社会信用代码';
														op.findById('cciaa11').setVisible(false);  
														op.findById('taxnum11').setVisible(false);  
													}else{
														var a = combo.el.parent().parent().first();
														combo.allowBlank=false;
										 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
										 				combo.fieldLabel = '组织机构代码';
														op.findById('cciaa11').setVisible(true);  
														op.findById('taxnum11').setVisible(true);
													}
												}
												if (obj.taxnum != 0 && obj.taxnum != "")
													op.getCmpByName('enterprise.taxnum').setValue(obj.taxnum);
													Ext.Ajax.request({
													url : __ctxPath
															+ '/creditFlow/customer/person/seeInfoPerson.do',
													method : "post",
													params : {
														id : obj.legalpersonid
													},
													success : function(d) {

														var c = Ext.util.JSON
																.decode(d.responseText);
														var id = c.data.id;
														var name = c.data.name;
														var sex = c.data.sex;
														var cardtype = c.data.cardtype;
														var cardnumber = c.data.cardnumber;
														var cellphone = c.data.cellphone;
														var selfemail = c.data.selfemail;
														var availableMoney = c.data.availableMoney;
														if (id != 0 && id != "")
															op
																	.getCmpByName('person.id')
																	.setValue(id);
														if (name != 0
																&& name != "")
															op
																	.getCmpByName('person.name')
																	.setValue(name);
														if (sex != 0
																&& sex != "")
															op
																	.getCmpByName('person.sex')
																	.setValue(sex);
														if (cardtype != 0
																&& cardtype != "")
															op
																	.getCmpByName('person.cardtype')
																	.setValue(cardtype);
														if (cardnumber != 0
																&& cardnumber != "")
															op
																	.getCmpByName('person.cardnumber')
																	.setValue(cardnumber);
														if (cellphone != 0
																&& cellphone != "")
															op
																	.getCmpByName('person.cellphone')
																	.setValue(cellphone);
														if (selfemail != 0
																&& selfemail != "")
															op
																	.getCmpByName('person.selfemail')
																	.setValue(selfemail);

                                                            op
                                                                .getCmpByName('person.availableMoney')
                                                                .setValue(availableMoney);

													}

												})
												// 
												var edGrid = op.ownerCt.ownerCt.getCmpByName('gudong_store').get(0).get(1);
												//var edGrid = Ext.getCmp(formPanelId).getCmpByName('gudong_store').get(0).get(1);
												var store = edGrid.getStore();
												var url = __ctxPath
														+ '/creditFlow/common/getShareequity.do?enterpriseId='
														+ obj.id;
												store.proxy.conn.url = url;
												store.load();
											}
											selectEnterprise(EnterpriseNameStockUpdateNew);

										},
										resizable : true,
										mode : 'romote',
										// editable : false,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										triggerAction : 'all'
									}]
						}, {
                            columnWidth : 0.3, // 该列在整行中所占的百分比
                            layout : "form", // 从上往下的布局
                            labelWidth : leftlabel,
                            items : [{
                                xtype : "textfield",
                                anchor : '100%',
                                name : "enterprise.surplusMoney",
                                fieldLabel : "企业剩余额度",
                                regex: /^[1-9]\d*$/,
                                readOnly : true,
                                regexText : "企业额度不足"

                            }]
                        }, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "textfield",
										anchor : '100%',
										name : "enterprise.shortname",
										fieldLabel : "企业简称",
										readOnly : this.isAllReadOnly,
										blankText : "企业简称不能为空，请正确填写!"

									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								name : "enterprise.hangyeName",
								fieldLabel : "行业类别",
								anchor : '100%',
								scope : this,
								editable : false,
								readOnly : this.isAllReadOnly,
								onTriggerClick : function(e) {
									var obj = this;
									var oobbj = obj.ownerCt.ownerCt
											.getCmpByName("enterprise.hangyeType");
									selectTradeCategory(obj, oobbj);
								}
							}]
								/*
								 * columnWidth : 0.5, // 该列在整行中所占的百分比 layout :
								 * "form", // 从上往下的布局 labelWidth : rightlabel,
								 * items : [{ xtype : "combo", triggerClass :
								 * 'x-form-search-trigger', name :
								 * "enterprise.hangyeName", fieldLabel : "行业类别",
								 * anchor : '100%', scope : this, allowBlank :
								 * false,//客户添加页面这个为必填项 blankText :
								 * "行业类别不能为空，请正确填写!", readOnly :
								 * this.isAllReadOnly, onTriggerClick :
								 * function(e) { var obj = this; var oobbj =
								 * obj.ownerCt.ownerCt.getCmpByName("enterprise.hangyeType");
								 * var rootId
								 * =obj.ownerCt.ownerCt.getCmpByName("enterprise.rootHangYeType");
								 * var selectHangYe = function(array){
								 * oobbj.setValue(array[0].id)
								 * obj.setValue(array[0].text);
								 * rootId.setValue(array[array.length-1].id) };
								 * selectDictionary('hangye',selectHangYe); } }]
								 */
						}, {
									columnWidth : .5,
									layout : 'form',
									labelWidth : leftlabel,
									items : [{
										xtype : 'combo',
										fieldLabel : '证件类型',
										readOnly:this.isAllReadOnly,
										allowBlank : false,
										mode : 'local',
										editable : false,
										displayField : 'typeValue',
						                valueField : 'typeId',
						                triggerAction : 'all',
										hiddenName : 'enterprise.documentType',
										anchor : '100%',
										store : new Ext.data.SimpleStore({
								        data : [['三证合一',1],['非三证合一',2]],
								        fields:['typeValue','typeId']
							            }),
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var combo = this.getCmpByName('enterprise.organizecode'); 
												if(combox.getValue() == 1){
//													var a = combo.el.parent().parent().first();
//									 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码";
													combo.allowBlank=false;
									 				combo.fieldLabel = '社会信用代码';
													this.findById('cciaa11').setVisible(false);  
													this.findById('taxnum11').setVisible(false);  
												}else{
//													var a = combo.el.parent().parent().first();
//									 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
													combo.allowBlank=false;
									 				combo.fieldLabel = '组织机构代码';
													this.findById('cciaa11').setVisible(true);  
													this.findById('taxnum11').setVisible(true);  
												}
											},
											select:function(combox){
												var combo = this.getCmpByName('enterprise.organizecode'); 
												if(combox.getValue() == 1){
													var a = combo.el.parent().parent().first();
													combo.allowBlank=false;
									 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码";
									 				combo.fieldLabel = '社会信用代码';
													this.findById('cciaa11').setVisible(false);  
													this.findById('taxnum11').setVisible(false);  
												}else{
													var a = combo.el.parent().parent().first();
													combo.allowBlank=false;
									 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
									 				combo.fieldLabel = '组织机构代码';
												    this.findById('cciaa11').setVisible(true);  
													this.findById('taxnum11').setVisible(true);  
												}
											}
										}
									}]
								}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
								xtype : "textfield",
								name : "enterprise.organizecode",
								allowBlank : false,
								fieldLabel : "组织机构代码",
								//regex : /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/,
								regexText : '组织机构代码格式不正确',
								readOnly : this.isAllReadOnly,
								blankText : "组织机构代码不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									'blur' : function(tf) {
										var organizecode = tf.getValue();
										var enterpriseId = this
												.getCmpByName("enterprise.id")
												.getValue();
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/enterprise/verificationOrganizecodeEnterprise.do',
											method : 'POST',
											params : {
												organizecode : organizecode,
												enterpriseId : enterpriseId
											},
											success : function(response,
													request) {

												var obj = Ext.util.JSON
														.decode(response.responseText);
												if (obj.msg = "false") {

													Ext.ux.Toast.msg('操作信息',
															"该组织机构代码已存在，请重新输入");
													tf.setValue("");
												}
											}
										});
									}
								}
							}]
						}, {
							xtype : "hidden",
							name : 'enterprise.hangyeType'
						}, {
							xtype : "hidden",
							name : 'enterprise.rootHangYeType'
						},{
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							id:'cciaa11',
							items : [{
										xtype : "textfield",
										name : "enterprise.cciaa",
										fieldLabel : "营业执照号码",
										readOnly : this.isAllReadOnly,
										blankText : "营业执照号码不能为空，请正确填写!",
										allowBlank : true,
										anchor : "100%"
									}]
						}, {
									columnWidth:.5,
									layout:'form',
									id:'taxnum11',
									labelWidth : 90,
									items:[{
										xtype:'textfield',
									//	emptyText : '请输入税务登记号码',
										blankText : '税务登记号码不允许空',
										readOnly:this.isAllReadOnly,
										allowBlank : true,
										regex : /^[0-9]*$/ ,
										regexText : '税务登记号码格式不正确',
										fieldLabel:'税务登记号码',
										name:'enterprise.taxnum',
										anchor : "100%"
									}]
								},{
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "textfield",
								name : "enterprise.telephone",
								allowBlank : false,
								fieldLabel : "联系电话",
								readOnly : this.isAllReadOnly,
								blankText : "联系电话不能为空，请正确填写!",
								regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
								regexText : '联系电话格式不正确',
								anchor : "100%"
							}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										xtype : "textfield",
										// allowBlank : false,
										name : "enterprise.postcoding",
										fieldLabel : "邮政编码",
										readOnly : this.isAllReadOnly,
										// blankText : "邮政编码不能为空，请正确填写!",
										// regex : /^[0-9]{6}$/,
										// regexText : '邮政编码格式不正确',
										anchor : "100%"
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "textfield",
										fieldLabel : "通讯地址",
										readOnly : this.isAllReadOnly,
										// allowBlank : false,
										name : "enterprise.area",
										anchor : '100%'
									}]
						}]
					}]

				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					bodyStyle : 'padding-right:2px',
					layout : "form", // 从上往下的布局
					items : [{
						layout : "form", // 从上往下的布局
						xtype : 'fieldset',
						title : '法定代表人信息',
						items : [{
							layout : "column",
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 75
							},
							items : [/*
										 * { xtype : 'hidden', name :
										 * 'person.marry' },
										 */{
										xtype : 'hidden',
										name : 'person.id'
									}, {
										columnWidth : 0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
													xtype : "combo",
                                                    triggerClass : 'x-form-search-trigger',
													fieldLabel : "法定代表人姓名",
													name : "person.name",
                                                    id:"personName",
													readOnly : this.isAllReadOnly,
													allowBlank : false,
													anchor : '100%',
                                           			scope : this,
													blankText : "法定代表人姓名不能为空，请正确填写!",
                                         		    onTriggerClick : function() {
                                                        var personName = Ext.getCmp('personName').getValue();
                                                        if (personName != null && personName != ""){
                                                        linkManEnterprise(personName);
                                                        }else {
                                                            Ext.Msg.alert('提示信息','请先选择企业');
                                                            return;
                                                        }
                                         		    }
												}]
									},
                               		 {
                               		     columnWidth : 0.5, // 该列在整行中所占的百分比
                               		     layout : "form", // 从上往下的布局
                               		     labelWidth : 110,
                               		     items : [{
                               		         xtype : "textfield",
                               		         fieldLabel : "法人可用额度",
                               		         name : "person.availableMoney",
                               		         readOnly :true,
                               		         // allowBlank : true,
                                             regex: /^[1-9]\d*$/,
                                             regexText: "法人额度不足",
                               		         anchor : '100%'
                               		     }]
                               		 }, {
										columnWidth : 0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
											fieldLabel : "法定代表人性别",
											xtype : "diccombo",
											hiddenName : 'person.sex',
											displayField : 'itemName',
											readOnly : this.isAllReadOnly,
											itemName : '性别', // xx代表分类名称
											allowBlank : false,
											// emptyText : "请选择",
											editable : false,
											blankText : "性别不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
																combox
																		.setValue(combox
																				.getValue());
																combox
																		.clearInvalid();
															})
												}
											}
										}]
									}, {
										columnWidth : 0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 65,
										items : [{
											xtype : "diccombo",
											hiddenName : "person.cardtype",
											fieldLabel : "证件类型",
											readOnly : this.isAllReadOnly,
											itemName : '证件类型', // xx代表分类名称
											allowBlank : false,
											editable : false,
											// emptyText : "请选择",
											blankText : "证件类型不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												scope : this,
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
														if (combox.getValue() == ''
																|| combox
																		.getValue() == null) {
															combox
																	.setValue(st
																			.getAt(0).data.itemId);
															combox
																	.clearInvalid();
														} else {
															combox
																	.setValue(combox
																			.getValue());
														}
													})
												}
											}
										}]
									}, {
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
											xtype : "textfield",
											name : "person.cardnumber",
											allowBlank : false,
											fieldLabel : "证件号码",
											readOnly : this.isAllReadOnly,
											blankText : "证件号码不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												scope : this,
												'blur' : function(tf) {
													var cardNum = tf.getValue();
													var personId = this.getCmpByName("person.id").getValue();
													var sex=this.getCmpByName("person.sex");
													if (this.getCmpByName('person.cardtype').getValue() == 309) {
														if (validateIdCard(tf.getValue()) == 1) {
															Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
															tf.setValue("")
															return;
														} else if (validateIdCard(tf.getValue()) == 2) {
															Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
															tf.setValue("")
															return;
														} else if (validateIdCard(tf.getValue()) == 3) {
															Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
															tf.setValue("")
															return;
														}
													}
													Ext.Ajax.request({
														url : __ctxPath+ '/creditFlow/customer/person/verificationPerson.do',
														method : 'POST',
														params : {
															cardNum : cardNum,
															personId : personId
														},
														success : function(response, request) {
															var obj = Ext.util.JSON.decode(response.responseText);
															if (obj.msg == false) {
																Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
																tf.setValue("")
															}else{
																if(cardNum.split("").reverse()[1]%2==0){
						                            				sex.setValue(313);
						                            				sex.setRawValue("女")
						                            			}else{
						                            				sex.setValue(312);
						                            				sex.setRawValue("男")
						                            			}
															}
														}
													});
												}
											}
										}]
									}, {
										columnWidth : this.isHiddenCustomerDetailBtn
												? 1
												: .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 110,
										items : [{
													xtype : "textfield",
													name : "person.cellphone",
													readOnly : this.isAllReadOnly,
													fieldLabel : "手机号码",
													anchor : "100%",
													regex : /^[1][34578][0-9]{9}$/,
													regexText : '手机号码格式不正确'
												}]
									}, {
										columnWidth : this.isHiddenCustomerDetailBtn
												? 1
												: .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : this.isHiddenCustomerDetailBtn
												? 110
												: 65,
										items : [{
											xtype : "textfield",
											name : "person.selfemail",
											readOnly : this.isAllReadOnly,
											fieldLabel : "电子邮箱",
											anchor : "100%",
											// regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
											// regexText : '电子邮箱格式不正确',
											listeners : {/*
															 * afterrender :
															 * function(obj) {
															 * if
															 * (obj.getValue() == "" ||
															 * obj.getValue() ==
															 * null) {
															 * Ext.apply(obj, {
															 * vtype : "" });
															 * }else{
															 * Ext.apply(obj, {
															 * vtype : 'email'
															 * }); //obj.regex
															 * ="/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/";
															 * //obj.regexText="'电子邮箱格式不正确'"; } },
															 * blur :
															 * function(obj) {
															 * 
															 * if
															 * (obj.getValue() == "" ||
															 * obj.getValue() ==
															 * null) {
															 * Ext.apply(obj, {
															 * vtype : "" }); }
															 * else {
															 * Ext.apply(obj, {
															 * vtype : 'email'
															 * });
															 *  } }
															 * 
															 */}
										}]
									}]
						}]
					}, {
						border : false,
						layout : "form",
						scope : this,
						items : [{
							xtype : 'button',
							text : this.isEditEnterprise ? '编辑客户资料' : '查看客户资料',
							iconCls : 'btn-customer',
							width : 110,
							hidden : this.isHiddenCustomerDetailBtn,
							scope : this,
							handler : function() {
								var oppositeId = this
										.getCmpByName('enterprise.id')
										.getValue();
								var flag = "detail";
								if (this.isEditEnterprise == true) {
									flag = "edit";
								}
								if (flag == "detail") {
									seeCustomer("company_customer", oppositeId);
								} else if (flag == "edit") {
									editCustomer("company_customer",
											oppositeId, this);
								}
							}
						}]
					}]
				}, {
					columnWidth : 1, // 该列在整行中所占的百分比
					bodyStyle : 'padding-right:2px',
					layout : "form", // 从上往下的布局
					hidden : this.isHidden,
					items : [{
						layout : "form", // 从上往下的布局
						xtype : 'fieldset',
						title : '贷款账户信息',
						items : [{
							layout : "column",
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 75
							},
							items : [{
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									anchor : "100%",
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人", "0"],
														["公司", "1"]]
											}),
									triggerAction : "all",
									hiddenName : "enterpriseBank.openType",
									fieldLabel : '开户类型',
									allowBlank : true,
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									name : 'enterpriseBank.openType',
									listeners : {
										scope : this,
										afterrender : function(combox) {
											var st = combox.getStore();
											/*combox
													.setValue(st.getAt(1).data.id);
											combox.fireEvent("select", combox,
													st.getAt(1), 0);*/
										},
										select : function(combox, record, index) {
											var v = record.data.id;
											var obj = this
													.getCmpByName('enterpriseBank.accountType');
											obj.enable();
											obj.setValue();
											obj.store.removeAll()
											if (v == 0) {
												arrStore = new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : [
																	["个人储蓄户",
																			"0"],
																	["基本户", "1"],
																	["一般户", "2"]]
														});
												obj.store.insert(0, arrStore
																.getAt(0));
											} else {
												arrStore = new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : [
																	["个人储蓄户",
																			"0"],
																	["基本户", "1"],
																	["一般户", "2"]]
														});
												obj.store.insert(0, arrStore
																.getAt(1));
												obj.store.insert(1, arrStore
																.getAt(2));
											}
										}
										

									}
								}]
							}, {
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									width : 70,
									triggerAction : "all",
									hiddenName : "enterpriseBank.accountType",
									fieldLabel : '账户类型',
									allowBlank : true,
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人储蓄户", "0"],
														["基本户", "1"],
														["一般户", "2"]]
											}),
									anchor : "100%",
									listeners : {
										scope : this,
										loadData : function(obj){
											var v =this.getCmpByName('enterpriseBank.openType').getValue();
											obj.enable();
											obj.store.removeAll()
											if (v == 0) {
												arrStore = new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : [
																	["个人储蓄户",
																			"0"],
																	["基本户", "1"],
																	["一般户", "2"]]
														});
												obj.store.insert(0, arrStore.getAt(0));
											} else {
												arrStore = new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : [
																	["个人储蓄户",
																			"0"],
																	["基本户", "1"],
																	["一般户", "2"]]
														});
												obj.store.insert(0, arrStore.getAt(1));
												obj.store.insert(1, arrStore.getAt(2));
											}
										}
									}
								}]
							}, {
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
											fieldLabel : '开户名称',
											name : 'enterpriseBank.name',
											xtype : 'textfield',
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											allowBlank : true,
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 80,
								items : [{
											xtype : 'textfield',
											fieldLabel : '账号',
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											allowBlank : true,
											name : 'enterpriseBank.accountnum',
											readOnly : this.isReadOnly,
											anchor : "100%"
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80,
								items : [{
											name : 'enterpriseBank.id',
											xtype : 'hidden'
										}, {
											fieldLabel : "银行名称",
											xtype : "combo",
											displayField : 'itemName',
											valueField : 'itemId',
											triggerAction : 'all',
											allowBlank : true,
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											store : new Ext.data.ArrayStore({
												url : __ctxPath
														+ '/creditFlow/common/getBankListCsBank.do',
												fields : ['itemId', 'itemName'],
												autoLoad : true
											}),
											mode : 'remote',
											hiddenName : "enterpriseBank.bankid",
											editable : false,
											blankText : "银行名称不能为空，请正确填写!",
											anchor : "100%",
											listeners : {
												scope : this,
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
																combox
																		.setValue(combox
																				.getValue());

															})
													combox.clearInvalid();
												}

											}

										}]
							}, {
								columnWidth : .34,
								layout : 'form',
								items : [{
											fieldLabel : "网点名称",
											name : 'enterpriseBank.bankOutletsName',
											xtype : 'textfield',
											allowBlank : true,
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											editable : true,
											anchor : "100%"

										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
											name : 'enterpriseBank.areaId',
											xtype : 'hidden'
										}, {
											// id:'bankName',
											name : 'enterpriseBank.areaName',
											hiddenName : 'enterpriseBank.areaName',
											fieldLabel : '开户地区',
											anchor : '100%',
											// value : '中国银行',
											// submitValue:false,
											xtype : 'trigger',
											triggerClass : 'x-form-search-trigger',
											editable : false,
											allowBlank : true,
											readOnly : this.isReadOnly,
											hidden : this.isHidden,
											hideLabel : this.isHidden,
											scope : this,
											onTriggerClick : function() {
												var com = this
												var selectBankLinkMan = function(
														array) {
													var str = "";
													var idStr = ""
													for (var i = array.length
															- 1; i >= 0; i--) {
														str = str
																+ array[i].text
																+ "-"
														idStr = idStr
																+ array[i].id
																+ ","
													}
													if (str != "") {
														str = str.substring(0,
																str.length - 1);
													}
													if (idStr != "") {
														idStr = idStr
																.substring(
																		0,
																		idStr.length
																				- 1)
													}
													com.previousSibling()
															.setValue(idStr)
													com.setValue(str);
												};
												// areaTree(selectBankLinkMan,bankAreaRootControl)
												selectDictionary('area',
														selectBankLinkMan,
														bankAreaRootControl);// add
																				// by
																				// gao
																				// extRootControl
																				// 只可以双击选择
																				// 23级
																				// 回填
											}

										}]
							}]
						}]
					}]
				}, {
					columnWidth : 1, // 该列占用的宽度，标识为50％
					border : false,
					name : "gudong_store",
					layout : "form",
					style : 'margin-top:10px',
					bodyStyle : 'padding-left: 0px;text-align:left;',
					hidden : this.isHideGudongInfo,
					scope : this,
					items : [new EnterpriseShareequity({
								type : this.isLoadShareequity,
								bussinessType : this.bussinessType,
								projectId : this.projectId,
								isHidden : this.isGudongDiseditable
										&& this.isAllReadOnly
							})]

				}]

			}]
		});
	}
})

// 小贷节点页面个人基本信息
ExtUD.Ext.PeerPersonMainInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isPersonNameReadOnly : true,
	isEditPerson : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isPersonNameReadOnly) != "undefined") {
			this.isPersonNameReadOnly = _cfg.isPersonNameReadOnly;
		}
		if (_cfg.isHiddenCustomerDetailBtn) {
			this.isHiddenCustomerDetailBtn = _cfg.isHiddenCustomerDetailBtn;
		}
		if (typeof(_cfg.isEditPerson) != "undefined") {
			this.isEditPerson = _cfg.isEditPerson;
		}
		var bankAreaRootControl = false;
		if (_cfg.bankAreaRootControl) {
			bankAreaRootControl = _cfg.bankAreaRootControl;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.PeerPersonMainInfoPanel.superclass.constructor.call(this, {
			items : [{
				columnWidth : 1,
				layout : "form",
				style : 'padding-left:17px',
				scope : this,
				items : [{
					layout : "form", // 从上往下的布局
					xtype : 'fieldset',
					title : '客户基本信息',
					items : [{
						layout : "column",
						defaults : {
							anchor : '100%',
							// columnWidth : 1,
							isFormField : true,
							labelWidth : 75
						},
						items : [{
									xtype : 'hidden',
									name : 'person.id'
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : 'combo',
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "客户姓名",
										name : "person.name",
										readOnly : this.isPersonNameReadOnly,
										allowBlank : false,
										blankText : "客户名称不能为空，请正确填写!",
										anchor : "100%",
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var selectCusNew = function(obj) {
												op.getCmpByName('person.id').setValue("");
												op.getCmpByName('person.name').setValue("");
												op.getCmpByName('person.sex').setValue("");
												op.getCmpByName('person.cardtype').setValue("");
												op.getCmpByName('person.cardnumber').setValue("");
												op.getCmpByName('person.marry').setValue("");
												op.getCmpByName('person.cellphone').setValue("");
												op.getCmpByName('person.postcode').setValue("");
												op.getCmpByName('person.postaddress').setValue("");
												op.getCmpByName('person.archivesBelonging').setValue("");

												if (obj.id != 0 && obj.id != "")
													op.getCmpByName('person.id').setValue(obj.id);
												if (obj.name != 0 && obj.name != "")
													op.getCmpByName('person.name').setValue(obj.name);
												if (obj.sex != 0 && obj.sex != "")
													op.getCmpByName('person.sex').setValue(obj.sex);
												if (obj.cardtype != 0 && obj.cardtype != ""){
													var cardtype=op.getCmpByName('person.cardtype');
													cardtype.setValue(obj.cardtype);
													if(obj.isCheckCard){
														cardtype.setReadOnly(true);
														if(cardtype.getEl().dom.className.indexOf("readOnlyClass")==-1){
															cardtype.getEl().dom.className+=" readOnlyClass";
														}
													}
												}
												if (obj.cardnumber != 0 && obj.cardnumber != ""){
													var cardnumber=op.getCmpByName('person.cardnumber');
													cardnumber.setValue(obj.cardnumber);
													if(obj.isCheckCard){
														cardnumber.setReadOnly(true);
														if(cardnumber.getEl().dom.className.indexOf("readOnlyClass")==-1){
															cardnumber.getEl().dom.className+=" readOnlyClass";
														}
													}
												}
												if (obj.marry != 0 && obj.marry != "")
													op.getCmpByName('person.marry').setValue(obj.marry);
												if (obj.cellphone != 0 && obj.cellphone != "")
													op.getCmpByName('person.cellphone').setValue(obj.cellphone);
												if (obj.familypostcode != 0 && obj.familypostcode != "")
													op.getCmpByName('person.postcode').setValue(obj.familypostcode);
												if (obj.selfemail != 0 && obj.selfemail != "")
													op.getCmpByName('person.selfemail').setValue(obj.selfemail);
												if (obj.postaddress != 0 && obj.postaddress != "")
													op.getCmpByName('person.postaddress').setValue(obj.postaddress);
												if (obj.archivesBelonging != 0 && obj.archivesBelonging != "")
													op.getCmpByName('person.archivesBelonging').setValue(obj.archivesBelonging);
											}
											selectPWName(selectCusNew);
										},
										resizable : true,
										mode : 'romote',
										// editable : false,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										/*
										 * store : new Ext.data.JsonStore({}),
										 * displayField : 'name', valueField :
										 * 'id',
										 */
										triggerAction : 'all',
										listeners : {
											scope : this,
											'select' : function(combo, record,
													index) {
												selectCusCombo(record);
											},
											'blur' : function(f) {
											},
											'change' : function(combox, record,index) {
												var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
												ressetProjuect(obj_n);
											}

										}

									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										fieldLabel : "客户性别",
										xtype : "diccombo",
										hiddenName : 'person.sex',
										readOnly : this.isAllReadOnly,
										itemName : '性别', // xx代表分类名称
										allowBlank : false,
										emptyText : "请选择",
										editable : false,
										blankText : "性别不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											afterrender : function(combox) {
												combox.clearInvalid();
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
											}
										}

									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "diccombo",
										hiddenName : "person.marry",
										fieldLabel : "婚姻状况",
										itemName : '婚姻状况', // xx代表分类名称
										allowBlank : false,
										editable : false,
										readOnly : this.isAllReadOnly,
										blankText : "婚姻状况不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												combox.clearInvalid();
												var st = combox.getStore();
												st.on("load", function() {
													if (combox.getValue() == 0) {
														combox.setValue(null);
														return false;
													}
													combox.setValue(combox.getValue());
												})
											},
											'select' : function(combox) {
												if (combox.getValue() == 317) {
													if (this.ownerCt.ownerCt.getCmpByName('spousePanel') != null) {
														this.ownerCt.ownerCt.getCmpByName('spousePanel').show()
														this.ownerCt.ownerCt.doLayout();
													}
												} else {
													if (this.ownerCt.ownerCt.getCmpByName('spousePanel') != null) {
														this.ownerCt.ownerCt.getCmpByName('spousePanel').hide()
														this.ownerCt.ownerCt.doLayout();
													}
												}
											}
										}
									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "diccombo",
										hiddenName : "person.cardtype",
										itemName : '证件类型', // xx代表分类名称
										fieldLabel : "证件类型",
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										editable : false,
										emptyText : "请选择",
										blankText : "证件类型不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												combox.clearInvalid();
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
												})
											}
										}

									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "textfield",
										name : "person.cardnumber",
										allowBlank : false,
										fieldLabel : "证件号码",
										readOnly : this.isAllReadOnly,
										readOnly : this.isAllReadOnly,
										blankText : "证件号码不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											'blur' : function(tf) {
												var cardNum = tf.getValue();
												var personId = this.getCmpByName("person.id").getValue();
												var sex=this.getCmpByName("person.sex");
												Ext.Ajax.request({
													url : __ctxPath+ '/creditFlow/customer/person/verificationPerson.do',
													method : 'POST',
													params : {
														cardNum : cardNum,
														personId : personId
													},
													success : function(response, request) {
														var obj = Ext.util.JSON.decode(response.responseText);
														if (obj.msg == false) {
															Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
															tf.setValue("")
														}
														if(cardNum.split("").reverse()[1]%2==0){
				                            				sex.setValue(313);
				                            				sex.setRawValue("女")
				                            			}else{
				                            				sex.setValue(312);
				                            				sex.setRawValue("男")
				                            			}
													}
												});
												if (this.getCmpByName('person.cardtype').getValue() == 309) {
													if (validateIdCard(tf.getValue()) == 1) {
														Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
														tf.setValue("")
														return;
													} else if (validateIdCard(tf.getValue()) == 2) {
														Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
														tf.setValue("")
														return;
													} else if (validateIdCard(tf.getValue()) == 3) {
														Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
														tf.setValue("")
														return;
													}
												}
											}
										}
									}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
												xtype : "textfield",
												fieldLabel : "手机号码",
												name : "person.cellphone",
												allowBlank : false,
												readOnly : this.isAllReadOnly,
												anchor : "100%",
												regex : /^[1][34578][0-9]{9}$/,
												regexText : '手机号码格式不正确'
											}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
												xtype : "textfield",
												name : "person.postcode",
												fieldLabel : "邮政编码",
												// allowBlank : false,
												readOnly : this.isAllReadOnly,
												blankText : "邮政编码不能为空，请正确填写!",
												regex : /^[0-9]{6}$/,
												regexText : '邮政编码格式不正确',
												anchor : "100%"
											}]
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										xtype : "textfield",
										name : "person.selfemail",
										fieldLabel : "电子邮箱",
										anchor : "100%",
										readOnly : this.isAllReadOnly,
										regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
										regexText : '电子邮箱格式不正确'
									}]
								}, {
									columnWidth : this.isHiddenCustomerDetailBtn
											? 0.33
											: 0.21, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{ // 上面是第三行
										xtype : "textfield",
										fieldLabel : "通讯地址",
										readOnly : this.isAllReadOnly,
										 allowBlank : false,
										name : "person.postaddress",
										anchor : "100%"// 控制文本框的长度

									}]
								},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 70,
									labelAlign : 'right',
									hidden : (this.isHiddenArchives==false?false:true), 
									items : [{
										xtype : "dickeycombo",
										hiddenName : "person.archivesBelonging",
										nodeKey : 'archivesBelonging', // xx代表分类名称
										fieldLabel : "档案归属地",
										readOnly : this.isRead,
										editable : false,
										anchor : "100%",
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
									       }
										}
				
									}]
								}, {
									columnWidth : .12, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									style : 'padding-left:17px',
									items : [{ // 上面是第三行
										xtype : 'button',
										text : this.isEditPerson
												? '编辑客户资料'
												: '查看客户资料',
										iconCls : 'btn-customer',
										width : 110,
										hidden : this.isHiddenCustomerDetailBtn,
										scope : this,
										handler : function() {
											var oppositeId = this
													.getCmpByName('person.id')
													.getValue();
											var flag = "detail";
											if (this.isEditPerson == true) {
												flag = "edit";
											}
											if (flag == "detail") {
												seeCustomer("person_customer",
														oppositeId);
											} else if (flag == "edit") {
												editCustomer("person_customer",
														oppositeId, this);
											}
										}
									}]
								}]
					}]
				}]
			}, {
				columnWidth : .1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				style : 'padding-left:17px',
				hidden : this.isHidden,
				items : [{
					layout : "form", // 从上往下的布局
					xtype : 'fieldset',
					title : '贷款账户信息',
					items : [{
						layout : "column",
						defaults : {
							anchor : '95%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : 70
						},
						items : [{
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								anchor : "100%",
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["个人", "0"], ["公司", "1"]]
										}),
								triggerAction : "all",
								hiddenName : "enterpriseBank.openType",
								fieldLabel : '开户类型',
								allowBlank : true,
								readOnly : true,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								name : 'enterpriseBank.openType',
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										combox.setValue(st.getAt(0).data.id);
										combox.fireEvent("select", combox, st
														.getAt(0), 0);
									},
									select : function(combox, record, index) {
										var v = record.data.id;
										var obj = this
												.getCmpByName('enterpriseBank.accountType');
										obj.enable();
										obj.setValue();
										obj.store.removeAll()
										if (v == 0) {
											arrStore = new Ext.data.SimpleStore(
													{
														fields : ["name", "id"],
														data : [["个人储蓄户", "0"],
																["基本户", "1"],
																["一般户", "2"]]
													});
											obj.store.insert(0, arrStore
															.getAt(0));
										} else {
											arrStore = new Ext.data.SimpleStore(
													{
														fields : ["name", "id"],
														data : [["个人储蓄户", "0"],
																["基本户", "1"],
																["一般户", "2"]]
													});
											obj.store.insert(0, arrStore
															.getAt(1));
											obj.store.insert(1, arrStore
															.getAt(2));
										}
									}

								}
							}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								width : 70,
								triggerAction : "all",
								hiddenName : "enterpriseBank.accountType",
								fieldLabel : '账户类型',
								allowBlank : true,
								readOnly : this.isReadOnly,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["个人储蓄户", "0"],
													["基本户", "1"], ["一般户", "2"]]
										}),
								name : 'enterpriseBank.accountType',
								anchor : "100%"
							}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
										fieldLabel : '开户名称',
										name : 'enterpriseBank.name',
										xtype : 'textfield',
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										allowBlank : true,
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							items : [{
										xtype : 'textfield',
										fieldLabel : '账号',
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										allowBlank : true,
										name : 'enterpriseBank.accountnum',
										readOnly : this.isReadOnly,
										anchor : "100%"
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 70,
							items : [{
										name : 'enterpriseBank.id',
										xtype : 'hidden'
									}, {
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										allowBlank : true,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										store : new Ext.data.ArrayStore({
											url : __ctxPath
													+ '/creditFlow/common/getBankListCsBank.do',
											fields : ['itemId', 'itemName'],
											autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());

														})
												combox.clearInvalid();
											}

										}

									}]
						}, {
							columnWidth : .34,
							layout : 'form',
							items : [{
										fieldLabel : "网点名称",
										name : 'enterpriseBank.bankOutletsName',
										xtype : 'textfield',
										allowBlank : true,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										anchor : "100%"

									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										name : 'enterpriseBank.areaId',
										xtype : 'hidden'
									}, {
										// id:'bankName',
										name : 'enterpriseBank.areaName',
										hiddenName : 'enterpriseBank.areaName',
										fieldLabel : '开户地区',
										anchor : '100%',
										// value : '中国银行',
										// submitValue:false,
										xtype : 'trigger',
										triggerClass : 'x-form-search-trigger',
										editable : false,
										allowBlank : true,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										scope : this,
										onTriggerClick : function() {
											var com = this
											var selectBankLinkMan = function(
													array) {
												var str = "";
												var idStr = ""
												for (var i = array.length - 1; i >= 0; i--) {
													str = str + array[i].text
															+ "-"
													idStr = idStr + array[i].id
															+ ","
												}
												if (str != "") {
													str = str.substring(0,
															str.length - 1);
												}
												if (idStr != "") {
													idStr = idStr.substring(0,
															idStr.length - 1)
												}
												com.previousSibling()
														.setValue(idStr)
												com.setValue(str);
											};
											// areaTree(selectBankLinkMan,bankAreaRootControl)
											selectDictionary('area',
													selectBankLinkMan);
										}

									}]
						}]
					}]
				}]
			}]
		});
	}
})

// 微贷的客户信息
ExtUD.Ext.CustomerInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isEditPerson : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isPersonNameReadOnly) {
			this.isPersonNameReadOnly = _cfg.isPersonNameReadOnly;
		}
		if (_cfg.isHiddenCustomerDetailBtn) {
			this.isHiddenCustomerDetailBtn = _cfg.isHiddenCustomerDetailBtn;
		}
		if (typeof(_cfg.isEditPerson) != "undefined") {
			this.isEditPerson = _cfg.isEditPerson;
		}
		if (typeof(_cfg.isNameEdit) != "undefined") {
			this.isNameEdit = _cfg.isNameEdit;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.CustomerInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
							xtype : 'hidden',
							name : 'person.id'
						}, {
							columnWidth : .5,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'combo',
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "中文姓名",
								name : "person.name",
								editable : this.isNameEdit,
								readOnly : true,
								allowBlank : false,
								blankText : "中文名称不能为空，请正确填写!",
								anchor : "100%",
								scope : this,
								onTriggerClick : function() {
									var op = this.ownerCt.ownerCt;
									var selectCustomer = function(obj) {
										op.getCmpByName('person.id')
												.setValue("");
										op.getCmpByName('person.name')
												.setValue("");
										op.getCmpByName('person.sex')
												.setValue("");
										op.getCmpByName('person.cardtype')
												.setValue("");
										op.getCmpByName('person.cardnumber')
												.setValue("");
										op.getCmpByName('person.marry')
												.setValue("");
										op.getCmpByName('person.cellphone')
												.setValue("");
										op.getCmpByName('person.birthday')
												.setValue("");
										op.getCmpByName('person.postcode')
												.setValue("");
										op.getCmpByName('person.postaddress')
												.setValue("");
										op
												.getCmpByName('person.currentcompany')
												.setValue("");
										op.getCmpByName('person.unitaddress')
												.setValue("");
										op.getCmpByName("person.familyaddress")
												.setValue("");
										op.getCmpByName('person.hukou')
												.setValue("");
										// op.getCmpByName('enterpriseBank.bankname').setValue("");
										op
												.getCmpByName('enterpriseBank.accountnum')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.openType')
												.setValue("");
										op.getCmpByName('enterpriseBank.name')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.accountType')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.bankid')
												.setValue("");
										op.getCmpByName('enterpriseBank.id')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.areaId')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.areaName')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.bankOutletsId')
												.setValue("");

										if (obj.id != 0 && obj.id != "")
											op.getCmpByName('person.id')
													.setValue(obj.id);
										if (obj.name != 0 && obj.name != "")
											op.getCmpByName('person.name')
													.setValue(obj.name);

										if (obj.sex != 0 && obj.sex != "")
											op.getCmpByName('person.sex')
													.setValue(obj.sex);
										if (obj.cardtype != 0
												&& obj.cardtype != "")
											op.getCmpByName('person.cardtype')
													.setValue(obj.cardtype);
										if (obj.cardnumber != 0
												&& obj.cardnumber != "")
											op
													.getCmpByName('person.cardnumber')
													.setValue(obj.cardnumber);
										if (obj.marry != 0 && obj.marry != "")
											op.getCmpByName('person.marry')
													.setValue(obj.marry);
										if (obj.cellphone != 0
												&& obj.cellphone != "")
											op.getCmpByName('person.cellphone')
													.setValue(obj.cellphone);
										if (obj.birthday != 0
												&& obj.birthday != "")
											op.getCmpByName('person.birthday')
													.setValue(obj.birthday);
										if (obj.familypostcode != 0
												&& obj.familypostcode != "")
											op
													.getCmpByName('person.postcode')
													.setValue(obj.familypostcode);
										if (obj.postaddress != 0
												&& obj.postaddress != "")
											op
													.getCmpByName('person.postaddress')
													.setValue(obj.postaddress);
										if (obj.currentcompany != 0
												&& obj.currentcompany != "")
											op
													.getCmpByName('person.currentcompany')
													.setValue(obj.currentcompany);
										if (obj.unitaddress != 0
												&& obj.unitaddress != "")
											op
													.getCmpByName('person.unitaddress')
													.setValue(obj.unitaddress);
										if (obj.familyaddress != 0
												&& obj.familyaddress != "")
											op
													.getCmpByName('person.familyaddress')
													.setValue(obj.familyaddress);
										if (obj.hukou != 0 && obj.hukou != "")
											op.getCmpByName('person.hukou')
													.setValue(obj.hukou);
										/*
										 * if(obj.bankName!=0 &&
										 * obj.bankName!="")
										 * op.getCmpByName('enterpriseBank.bankname').setValue(obj.bankName);
										 */
										if (obj.bankNum != 0
												&& obj.bankNum != "")
											op
													.getCmpByName('enterpriseBank.accountnum')
													.setValue(obj.bankNum);

										op
												.getCmpByName('enterpriseBank.openType')
												.setValue(obj.openType);

										if (obj.khname != 0 && obj.khname != "")
											op
													.getCmpByName('enterpriseBank.name')
													.setValue(obj.khname);
										op
												.getCmpByName('enterpriseBank.accountType')
												.setValue(obj.accountType);
										if (obj.bankId != "")
											op
													.getCmpByName('enterpriseBank.bankid')
													.setValue(obj.bankId);
										if (obj.enterpriseBankId != "")
											op
													.getCmpByName('enterpriseBank.id')
													.setValue(obj.enterpriseBankId);
										op
												.getCmpByName('enterpriseBank.areaId')
												.setValue(obj.areaId);
										op
												.getCmpByName('enterpriseBank.areaName')
												.setValue(obj.areaName);
										op
												.getCmpByName('enterpriseBank.bankOutletsId')
												.setValue(obj.bankOutletsId);
									}
									selectPWName(selectCustomer);
								},
								resizable : true,
								mode : 'romote',
								lazyInit : false,
								typeAhead : true,
								minChars : 1,
								store : new Ext.data.JsonStore({/*
																 * url :
																 * __ctxPath +
																 * '/credit/customer/person/ajaxQueryPersonForCombo.do?isAll=' +
																 * isGranted('_detail_sygrkh'),
																 * root :
																 * 'topics',
																 * autoLoad :
																 * true, fields : [{
																 * name : 'id' }, {
																 * name : 'name' }, {
																 * name : 'sex' }, {
																 * name :
																 * 'marry' }, {
																 * name :
																 * 'cardtype' }, {
																 * name :
																 * 'cardnumber' }, {
																 * name :
																 * 'telphone' }, {
																 * name :
																 * 'postcode' }, {
																 * name :
																 * 'selfemail' }, {
																 * name :
																 * 'postaddress'
																 * }], listeners : {
																 * scope : this,
																 * 'load' :
																 * function(s,
																 * r, o) { if
																 * (s.getCount() ==
																 * 0) { this
																 * .getCmpByName('person.name')
																 * .markInvalid('没有查找到匹配的记录'); } } }
																 */}),
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								listeners : {
									scope : this,
									'select' : function(combo, record, index) {
										// selectCusCombo(record);
									},
									'blur' : function(f) {
										/*
										 * if (f.getValue() != null &&
										 * f.getValue() != '') {
										 * this.getCmpByName('person.id')
										 * .setValue(f.getValue()); }
										 */
									}

								}
							}, {
								xtype : "dickeycombo",
								nodeKey : 'card_type_key',
								hiddenName : "person.cardtype",
								itemName : '证件类型', // xx代表分类名称
								fieldLabel : "证件类型",
								allowBlank : false,
								editable : true,
								readOnly : this.isAllReadOnly,
								blankText : "证件类型不能为空，请正确填写!",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}

							}, {
								xtype : 'datefield',
								fieldLabel : '出生日期',
								name : 'person.birthday',
								format : 'Y-m-d',
								// allowBlank:false,
								readOnly : this.isAllReadOnly

							}, {
								xtype : 'textfield',
								fieldLabel : '通信地址',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : 'person.postaddress'

							}, {
								xtype : 'textfield',
								fieldLabel : '手机号码',
								name : 'person.cellphone',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								regex : /^[1][34578][0-9]{9}$/,
								regexText : '手机号码格式不正确'
							}, {
								xtype : 'textfield',
								fieldLabel : '工作单位',
								name : 'person.currentcompany',
								allowBlank : false,
								readOnly : this.isAllReadOnly
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'sex_key',
								hiddenName : 'person.sex',
								fieldLabel : "性别",
								allowBlank : false,
								editable : true,
								blankText : "性别不能为空，请正确填写!",
								readOnly : this.isAllReadOnly,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							}, {
								xtype : 'textfield',
								fieldLabel : '证件号码',
								name : 'person.cardnumber',
								allowBlank : false,
								blankText : '证件号码为必填内容',
								readOnly : this.isAllReadOnly,
								listeners : {
									scope : this,
									'blur' : function(tf) {
										if (this
												.getCmpByName('person.cardtype')
												.getValue() == 309) {
											if (validateIdCard(tf.getValue()) == 1) {
												Ext.Msg.alert('身份证号码验证',
														'证件号码不正确,请仔细核对')
												return;
											} else if (validateIdCard(tf
													.getValue()) == 2) {
												Ext.Msg.alert('身份证号码验证',
														'证件号码地区不正确,请仔细核对')
												return;
											} else if (validateIdCard(tf
													.getValue()) == 3) {
												Ext.Msg.alert('身份证号码验证',
														'证件号码生日日期不正确,请仔细核对')
												return;
											}
										}
										var cardNum = tf.getValue();
										var personId = this
												.getCmpByName("person.id")
												.getValue();
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/person/verificationPerson.do',
											method : 'POST',
											params : {
												cardNum : cardNum,
												personId : personId
											},
											success : function(response,
													request) {

												var obj = Ext.util.JSON
														.decode(response.responseText);
												if (obj.msg = "false") {
													Ext.ux.Toast.msg('操作信息',
															"该证件号码已存在，请重新输入");
													tf.setValue("")
												}
											}
										});
									}

								}

							}, {
								xtype : "dickeycombo",
								nodeKey : '8',
								hiddenName : 'person.marry',
								fieldLabel : "婚姻状况",
								itemName : '婚姻状况', // xx代表分类名称
								allowBlank : false,
								editable : true,
								blankText : "婚姻状况不能为空，请正确填写!",
								readOnly : this.isAllReadOnly,
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									},
									'select' : function(combox) {
										if (combox.getValue() == 317) {
											if (this.ownerCt.ownerCt
													.getCmpByName('spousePanel') != null) {
												this.ownerCt.ownerCt
														.getCmpByName('spousePanel')
														.show()
												this.ownerCt.ownerCt.doLayout();
											}
										} else {
											if (this.ownerCt.ownerCt
													.getCmpByName('spousePanel') != null) {
												this.ownerCt.ownerCt
														.getCmpByName('spousePanel')
														.hide()
												this.ownerCt.ownerCt.doLayout();
											}
										}
									}
								}

							}, {
								xtype : 'textfield',
								fieldLabel : '邮政编码',
								name : 'person.postcode',
								// allowBlank:false,
								regex : /^[0-9]{6}$/,
								regexText : '邮政编码格式不正确',
								readOnly : this.isAllReadOnly
							}, {
								xtype : 'textfield',
								fieldLabel : '单位地址',
								name : 'person.unitaddress',
								allowBlank : false,
								readOnly : this.isAllReadOnly
							}, {
								xtype : 'textfield',
								fieldLabel : '家庭住址',
								name : 'person.familyaddress',
								allowBlank : false,
								readOnly : this.isAllReadOnly

							}]
						}, {
							columnWidth : .8,
							layout : 'form',
							defaults : {
								anchor : '98%'
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : '户口所在地',
										readOnly : this.isAllReadOnly,
										// allowBlank:false,
										name : 'person.hukou'

									}]
						}, {
							columnWidth : .2,
							layout : 'form',
							/*
							 * defaults : { anchor : '90%' },
							 */
							items : [{
								xtype : 'button',
								text : this.isEditPerson ? '编辑客户资料' : '查看客户资料',
								iconCls : 'btn-customer',
								width : 110,
								hidden : this.isHiddenCustomerDetailBtn,
								scope : this,
								handler : function() {
									var oppositeId = this
											.getCmpByName('person.id')
											.getValue();
									var flag = "detail";
									if (this.isEditPerson == true) {
										flag = "edit";
									}
									if (flag == "detail") {
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/person/seeInfoPerson.do',
											method : 'POST',
											scope : this,
											success : function(response,
													request) {
												obj = Ext.util.JSON
														.decode(response.responseText);
												var personData = obj.data;
												var randomId = rand(100000);
												var id = "see_person"
														+ randomId;
												var anchor = '100%';
												var window_see = new Ext.Window(
														{
															title : '查看个人客户详细信息',
															layout : 'fit',
															width : (screen.width - 180)
																	* 0.7 + 160,
															maximizable : true,
															height : 460,
															closable : true,
															modal : true,
															plain : true,
															border : false,
															items : [new personObj(
																	{
																		url : null,
																		id : id,
																		personData : personData,
																		isReadOnly : true
																	})],
															listeners : {
																'beforeclose' : function(
																		panel) {
																	window_see
																			.destroy();
																}
															}
														});
												window_see.show();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态',
														'操作失败，请重试');
											},
											params : {
												id : oppositeId
											}
										});
									} else if (flag == "edit") {
										var customerPanel = this
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/person/seeInfoPerson.do',
											method : 'POST',
											scope : this,
											success : function(response,
													request) {
												obj = Ext.util.JSON
														.decode(response.responseText);
												var personData = obj.data;
												var randomId = rand(100000);
												var id = "update_person"
														+ randomId;
												var url = __ctxPath
														+ '/creditFlow/customer/person/updateInfoPerson.do';
												var window_update = new Ext.Window(
														{
															title : '编辑个人客户详细信息',
															height : 460,
															constrainHeader : true,
															collapsible : true,
															frame : true,
															iconCls : 'btn-edit',
															border : false,
															bodyStyle : 'overflowX:hidden',
															buttonAlign : 'right',
															iconCls : 'newIcon',
															width : (screen.width - 180)
																	* 0.7 + 160,
															resizable : true,
															layout : 'fit',
															autoScroll : false,
															constrain : true,
															closable : true,
															modal : true,
															items : [new personObj(
																	{
																		personData : personData,
																		url : url,
																		id : id
																	})],
															tbar : [new Ext.Button(
																	{
																		text : '更新',
																		tooltip : '更新基本信息',
																		iconCls : 'btn-refresh',
																		hideMode : 'offsets',
																		handler : function() {
																			var vDates = "";
																			var panel_add = window_update
																					.get(0);
																			formSavePersonObj1(
																					panel_add,
																					window_update,
																					null,
																					customerPanel);
																		}
																	})],
															listeners : {
																'beforeclose' : function(
																		panel) {
																	window_update
																			.destroy();
																}
															}
														});
												window_update.show();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态',
														'操作失败，请重试');
											},
											params : {
												id : oppositeId
											}
										});
										/*
										 * editCustomer("person_customer",
										 * oppositeId,this);
										 */
									}
								}

							}]
						}, {
							columnWidth : 1,
							layout : 'form',
							hidden : this.isHidden,
							items : [{
								xtype : 'panel',
								border : false,
								bodyStyle : 'padding:10px 10px 10px 10px',
								html : '<B><font class="x-myZW-fieldset-title">【贷款账户信息】：</font></B>'
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								anchor : '100%',
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["个人", "0"], ["公司", "1"]]
										}),
								triggerAction : "all",
								hiddenName : "enterpriseBank.openType",
								fieldLabel : '开户类型',
								allowBlank : this.isHidden,
								readOnly : true,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								name : 'enterpriseBank.openType',
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										combox.setValue(st.getAt(0).data.id);
										combox.fireEvent("select", combox, st
														.getAt(0), 0);
									},
									select : function(combox, record, index) {
										var v = record.data.id;
										var obj = this
												.getCmpByName('enterpriseBank.accountType');
										obj.enable();
										obj.setValue();
										obj.store.removeAll()
										if (v == 0) {
											arrStore = new Ext.data.SimpleStore(
													{
														fields : ["name", "id"],
														data : [["个人储蓄户", "0"],
																["基本户", "1"],
																["一般户", "2"]]
													});
											obj.store.insert(0, arrStore
															.getAt(0));
										} else {
											arrStore = new Ext.data.SimpleStore(
													{
														fields : ["name", "id"],
														data : [["个人储蓄户", "0"],
																["基本户", "1"],
																["一般户", "2"]]
													});
											obj.store.insert(0, arrStore
															.getAt(1));
											obj.store.insert(1, arrStore
															.getAt(2));
										}
									}

								}
							}, {
								fieldLabel : '开户名称',
								name : 'enterpriseBank.name',
								xtype : 'textfield',
								anchor : '100%',
								readOnly : this.isReadOnly,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								allowBlank : this.isHidden
							}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								anchor : '100%',
								triggerAction : "all",
								hiddenName : "enterpriseBank.accountType",
								fieldLabel : '账户类型',
								allowBlank : this.isHidden,
								readOnly : this.isReadOnly,
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["个人储蓄户", "0"],
													["基本户", "1"], ["一般户", "2"]]
										}),
								name : 'enterpriseBank.accountType'

							}, {
								xtype : 'textfield',
								fieldLabel : '账号',
								anchor : '100%',
								hidden : this.isHidden,
								hideLabel : this.isHidden,
								allowBlank : this.isHidden,
								name : 'enterpriseBank.accountnum',
								readOnly : this.isReadOnly
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										name : 'enterpriseBank.id',
										xtype : 'hidden'
									}, {
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										allowBlank : this.isHidden,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										store : new Ext.data.ArrayStore({
											url : __ctxPath
													+ '/creditFlow/common/getBankListCsBank.do',
											fields : ['itemId', 'itemName'],
											autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());

														})
												combox.clearInvalid();
											}

										}

									}]
						}, {
							columnWidth : .34,
							layout : 'form',
							items : [{
										fieldLabel : "网点名称",
										name : 'enterpriseBank.bankOutletsName',
										xtype : 'textfield',
										allowBlank : this.isHidden,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										editable : true,
										anchor : "100%"
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										name : 'enterpriseBank.areaId',
										xtype : 'hidden'
									}, {
										name : 'enterpriseBank.areaName',
										hiddenName : 'enterpriseBank.areaName',
										fieldLabel : '开户地区',
										anchor : '100%',
										xtype : 'trigger',
										triggerClass : 'x-form-search-trigger',
										editable : false,
										allowBlank : this.isHidden,
										readOnly : this.isReadOnly,
										hidden : this.isHidden,
										hideLabel : this.isHidden,
										scope : this,
										onTriggerClick : function() {
											var com = this
											var selectBankLinkMan = function(
													array) {
												var str = "";
												var idStr = ""
												for (var i = array.length - 1; i >= 0; i--) {
													str = str + array[i].text
															+ "-"
													idStr = idStr + array[i].id
															+ ","
												}
												if (str != "") {
													str = str.substring(0,
															str.length - 1);
												}
												if (idStr != "") {
													idStr = idStr.substring(0,
															idStr.length - 1)
												}
												com.previousSibling()
														.setValue(idStr)
												com.setValue(str);
											};
											// areaTree(selectBankLinkMan,bankAreaRootControl)
											selectDictionary('area',
													selectBankLinkMan);
										}

									}]
						}]
			}]
		});
	}
})

// 项目信息
ExtUD.Ext.ProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 100;
		var rightlabel = 85;
		ExtUD.Ext.ProjectInfoPanel.superclass.constructor.call(this, {
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
							xtype : 'hidden',
							name : 'slSmallloanProject.projectId'
						}, {
							xtype : 'hidden',
							name : 'slSmallloanProject.companyId'
						}, {
							xtype : 'hidden',
							name : 'slSmallloanProject.businessType'
						}, {
							xtype : 'hidden',
							name : 'slSmallloanProject.operationType'
						}, {
							xtype : 'hidden',
							name : 'slSmallloanProject.flowType'
						}, {
							xtype : 'hidden',
							name : 'slSmallloanProject.mineTypeKey'
						}, {
							columnWidth : .6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										name : "slSmallloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										// allowBlank : false,
										anchor : "100%"// 控制文本框的长度

									}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "slSmallloanProject.projectNumber",
										// allowBlank : false,
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "100%"

									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : "textfield",
										anchor : "100%",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										name : "businessTypeKey",
										fieldLabel : "业务类别"
									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 70,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : "业务品种",
										xtype : "textfield",
										name : "operationTypeKey",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly
									}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 86,
							items : [{
										fieldLabel : "流程类别",
										xtype : "textfield",
										name : "flowTypeKey",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										anchor : "100%"

									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
										fieldLabel : "贷款利息收取单位",
										xtype : "textfield",
										name : "mineTypeKey",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										anchor : "100%"

									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							labelWidth : 10,
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										name : "slSmallloanProject.mineName",
										// fieldLabel : "我方主体(贷款利息)",
										readOnly : this.isAllReadOnly,
										readOnly : this.isDiligenceReadOnly,
										anchor : "100%"
									}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							labelWidth : leftlabel,
							layout : "form", // 从上往下的布局
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "信贷员",
								blankText : "信贷员不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
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
												title : "选择信贷员",
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
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "咨询管理费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineType',
								nodeKey : 'ourmainType',
								readOnly : this.isAllReadOnly,
								// allowBlank : false,
								value : null,
								anchor : "100%",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();

													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isAllReadOnly ? {
								xtype : "textfield",
								name : "managementConsultingMineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "100%"

							} : {
								xtype : "combo",
								anchor : "100%",
								// fieldLabel : "我方主体(咨询管理费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineId',
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								readOnly : this.isAllReadOnly,
								mode : 'local',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							labelWidth : rightlabel,
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										name : "slSmallloanProject.recommendUser",
										editable : false,
										fieldLabel : "项目推荐人",
										readOnly : this.isAllReadOnly,
										anchor : "100%"
									}, {
										xtype : "hidden",
										value : ""
									}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "财务服务费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.financeServiceMineType',
								nodeKey : 'ourmainType',
								// allowBlank : false,
								value : null,
								anchor : "100%",
								readOnly : this.isAllReadOnly,
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isAllReadOnly ? {
								xtype : "textfield",
								name : "financeServiceMineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "100%"

							} : {
								xtype : "combo",
								anchor : "100%",
								// fieldLabel : "我方主体(财务服务费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								mode : 'local',
								readOnly : this.isAllReadOnly,
								hiddenName : 'slSmallloanProject.financeServiceMineId',
								name : 'slSmallloanProject.financeServiceMineId',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								fieldLabel : "贷款用途",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款用途',
								nodeKey : 'smallloan_purposeType',
								emptyText : "请选择",
								anchor : "100%",
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
						}]

			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});
// 微贷项目信息
ExtUD.Ext.McroLoanProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	// isDiligenceReadOnly : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			// this.isDiligenceReadOnly = true;
		}
		if (typeof(_cfg.isDiligenceReadOnly) != "undefined") {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		// alert(isDiligenceReadOnly)
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 100;
		var rightlabel = 85;
		ExtUD.Ext.McroLoanProjectInfoPanel.superclass.constructor.call(this, {
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
				items : [
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.projectId'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.companyId'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.businessType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.operationType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.flowType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.mineTypeKey'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.loanLimit'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.mineId'
						},
						{
							columnWidth : 0.6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : true,
										name : "slSmallloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										// allowBlank : false,
										anchor : "99%"// 控制文本框的长度

									}]
						},
						{
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "slSmallloanProject.projectNumber",
										// allowBlank : false,
										readOnly : true,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "95%"

									}]
						},{
							columnWidth : .6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : '门店名称',
								anchor : '99%',
								xtype : 'combo',
								readOnly : true,
								triggerClass : 'x-form-search-trigger',
								hiddenName : "slSmallloanProject.departMentName",
								onTriggerClick : function() {
									var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
									var EnterpriseNameStockUpdateNew = function(obj) {
										if (null != obj.orgName && "" != obj.orgName)
											op.getCmpByName('slSmallloanProject.departMentName').setValue(obj.orgName);
										if (null != obj.orgId && "" != obj.orgId)
											op.getCmpByName('slSmallloanProject.departId').setValue(obj.orgId);
									}
									selectShop(EnterpriseNameStockUpdateNew);
								}
							},{
								xtype:'hidden',
								name:'slSmallloanProject.departId'
							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
								xtype : "combo",
								readOnly : true,
								triggerAction : 'all',
							    hiddenName : "bpProductParameter.productName",
								displayField:'productName',
								valueField:'id',
								fieldLabel : "产品类型",
								editable : false,
								anchor :"95%",
								store : new Ext.data.JsonStore({
									url : __ctxPath
											+ "/system/listBpProductParameter.do",
									totalProperty : 'totalCounts',
									autoLoad : true,
									root : 'result',
									fields : [{
												name : 'id'
											}, {
												name : 'productName'
											}]													
								}),
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox
															.setValue(combox
																	.getValue());
													combox
															.clearInvalid();
												})
									}
								}
								
							},{
								xtype:'hidden',
								name:'slSmallloanProject.productId'
							}]
						
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "咨询管理费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineType',
								nodeKey : 'ourmainType',
								readOnly : this.isDiligenceReadOnly,
								// allowBlank : false,
								value : null,
								anchor : "98%",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();

													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "managementConsultingMineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(咨询管理费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineId',
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								readOnly : this.isDiligenceReadOnly,
								mode : 'local',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
									},
									loadData : function(combo){
										var comboxValue = this.getCmpByName('slSmallloanProject.managementConsultingMineType').getValue();
										var managementConsultingMineId=this.getCmpByName('slSmallloanProject.managementConsultingMineId').getValue();
										var url = '';
										var store = null;
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load({
											callback : test
										});
										function test(){
											if (combo.view) { // 刷新视图,避免视图值与实际值不相符
												combo.view.setStore(combo.store);
											}
											combo.setValue(managementConsultingMineId)
										}
										
									
									}
								}

							}]
						}, {
							columnWidth : .2, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "slSmallloanProject.teamManagerName",
								editable : false,
								fieldLabel : "项目主管",
								blankText : "项目主管不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "100%",
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if (null == obj.getValue() || "" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												type : 'branch',
												title : "选择项目主管",
												callback : function(uId, uname) {
													obj.setValue(uname);
													//obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
		
								}
							}, {
								xtype : "hidden",
								name : "slSmallloanProject.teamManagerId"
							}]
						}, {
							columnWidth : .2, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 60,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "slSmallloanProject.appUserName",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "90%",
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if (null == obj.getValue() || "" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												type : 'branch',
												title : "选择项目经理",
												callback : function(uId, uname) {
													obj.setValue(uname);
													//obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
		
								}
							}, {
								xtype : "hidden",
								name : "slSmallloanProject.appUserId"
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "财务服务费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.financeServiceMineType',
								nodeKey : 'ourmainType',
								// allowBlank : false,
								value : null,
								anchor : "98%",
								readOnly : this.isDiligenceReadOnly,
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "financeServiceMineName",
								readOnly : this.isDiligenceReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(财务服务费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								mode : 'local',
								readOnly : this.isDiligenceReadOnly,
								hiddenName : 'slSmallloanProject.financeServiceMineId',
								name : 'slSmallloanProject.financeServiceMineId',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										combox.clearInvalid();
									},
									loadData : function(combo){
										var comboxValue = this.getCmpByName('slSmallloanProject.financeServiceMineType').getValue();
										var financeServiceMineId=this.getCmpByName('slSmallloanProject.financeServiceMineId').getValue();
										var url = '';
										var store = null;
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load({
											callback : test
										});
										function test(){
											if (combo.view) { // 刷新视图,避免视图值与实际值不相符
												combo.view.setStore(combo.store);
											}
											combo.setValue(financeServiceMineId)
										}
										
									
									
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "主担保方式",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.assuretypeid',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'zdbfs',
								emptyText : "请选择",
								editable : false,
								anchor : "95%",
								allowBlank : false,
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
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "贷款用途",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款用途',
								nodeKey : 'smallloan_purposeType',
								emptyText : "请选择",
								editable : false,
								anchor : "98%",
								allowBlank : false,
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
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
								fieldLabel : "项目来源",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.customerChannel',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'customer_channel',
								emptyText : "请选择",
								editable : false,
								anchor : "98%",
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
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
										fieldLabel : "推荐人",
										xtype : "textfield",
										name : "slSmallloanProject.recommendUser",
										readOnly : this.isAllReadOnly,
										anchor : "95%"

									}]

						}]

			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});

ExtUD.Ext.HistoryProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 100;
		var rightlabel = 85;
		ExtUD.Ext.HistoryProjectInfoPanel.superclass.constructor.call(this, {
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
				items : [
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.projectId'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.companyId'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.businessType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.operationType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.flowType'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.mineTypeKey'
						},
						{
							xtype : 'hidden',
							name : 'slSmallloanProject.loanLimit'
						},
						{
							columnWidth : 0.6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : true,
										name : "slSmallloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										// allowBlank : false,
										anchor : "99%"// 控制文本框的长度

									}]
						},
						{
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "slSmallloanProject.projectNumber",
										// allowBlank : false,
										readOnly : true,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "95%"

									}]
						},
						new ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel(),/*
																				 * new
																				 * ExtUD.Ext.TypeSelectInfoTwoGradesReadOnlyPanel()
																				 * ，new
																				 * ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel(),new
																				 * ExtUD.Ext.TypeSelectInfoFourGradesReadOnlyPanel()这里用不同的级别分类填充
																				 */
						/*
						 * new
						 * ExtUD.Ext.TypeSelectInfoMineType({isAllReadOnly:this.isAllReadOnly}),
						 *//*
																										 * {
																										 * columnWidth :
																										 * .3, //
																										 * 该列在整行中所占的百分比
																										 * layout :
																										 * "form", //
																										 * 从上往下的布局
																										 * labelWidth :
																										 * 90,
																										 * labelAlign :
																										 * 'right',
																										 * defaults : {
																										 * anchor :
																										 * '100%' },
																										 * items : [{
																										 * xtype :
																										 * "textfield",
																										 * anchor :
																										 * "100%",
																										 * readOnly :
																										 * true,
																										 * name :
																										 * "businessTypeKey",
																										 * fieldLabel :
																										 * "业务类别" }] }, {
																										 * columnWidth :
																										 * .3, //
																										 * 该列在整行中所占的百分比
																										 * layout :
																										 * "form", //
																										 * 从上往下的布局
																										 * labelWidth :
																										 * 80,
																										 * labelAlign :
																										 * 'right',
																										 * defaults : {
																										 * anchor :
																										 * '100%' },
																										 * items : [{
																										 * fieldLabel :
																										 * "业务品种",
																										 * xtype :
																										 * "textfield",
																										 * name :
																										 * "operationTypeKey",
																										 * readOnly
																										 * :true }] }, {
																										 * columnWidth :
																										 * .4, //
																										 * 该列在整行中所占的百分比
																										 * layout :
																										 * "form", //
																										 * 从上往下的布局
																										 * labelWidth :
																										 * 80,
																										 * labelAlign :
																										 * 'right',
																										 * items : [{
																										 * fieldLabel :
																										 * "流程类别",
																										 * xtype :
																										 * "textfield",
																										 * name :
																										 * "flowTypeKey",
																										 * readOnly :
																										 * true,
																										 * anchor :
																										 * "100%"
																										 *  }] }, {
																										 * columnWidth :
																										 * .3, //
																										 * 该列在整行中所占的百分比
																										 * layout :
																										 * "form", //
																										 * 从上往下的布局
																										 * labelWidth :
																										 * 90,
																										 * labelAlign :
																										 * 'right',
																										 * hidden :
																										 * true,
																										 * items : [{
																										 * fieldLabel :
																										 * "我方主体类型",
																										 * xtype :
																										 * "textfield",
																										 * name :
																										 * "mineTypeKey",
																										 * readOnly :
																										 * true,
																										 * anchor :
																										 * "100%"
																										 *  }] }, {
																										 * columnWidth :
																										 * .3, //
																										 * 该列在整行中所占的百分比
																										 * labelWidth
																										 * :90,
																										 * layout :
																										 * "form", //
																										 * 从上往下的布局
																										 * items : [{
																										 * fieldLabel :
																										 * '我方主体名称',
																										 * xtype :
																										 * 'textfield',
																										 * name :
																										 * 'mineName',
																										 * anchor :
																										 * "100%",
																										 * readOnly :
																										 * true }] }, {
																										 * columnWidth :
																										 * .4, //
																										 * 该列在整行中所占的百分比
																										 * labelWidth :
																										 * 80,
																										 * layout :
																										 * "form", //
																										 * 从上往下的布局
																										 * items :
																										 * [{fieldLabel :
																										 * "项目经理",
																										 * xtype :
																										 * "textfield",
																										 * name :
																										 * "slSmallloanProject.appUsers",
																										 * readOnly :
																										 * true,
																										 * anchor :
																										 * "100%" }, {
																										 * xtype :
																										 * "hidden",
																										 * name :
																										 * 'slSmallloanProject.appUserId' }] },
																										 *//*
								 * { columnWidth : .3, // 该列在整行中所占的百分比 layout :
								 * "form", // 从上往下的布局 labelWidth : leftlabel+50,
								 * items : [{ fieldLabel : "贷款利息收取单位", xtype :
								 * "textfield", name : "mineTypeKey", readOnly :
								 * this.isAllReadOnly, anchor : "98%"
								 *  }] }, { columnWidth : .3, // 该列在整行中所占的百分比
								 * labelWidth :10, layout : "form", // 从上往下的布局
								 * items : [{ xtype : "textfield", name :
								 * "slSmallloanProject.mineName", //fieldLabel :
								 * "我方主体(贷款利息)", readOnly : this.isAllReadOnly,
								 * anchor : "98%" }] },
								 */{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "贷款利息收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.mineType',
								nodeKey : 'ourmainType',
								readOnly : this.isDiligenceReadOnly,
								// allowBlank : false,
								value : null,
								anchor : "98%",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();

													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "mineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(咨询管理费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.mineId',
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								readOnly : this.isDiligenceReadOnly,
								mode : 'local',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : "95%",
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
												title : "选择项目经理",
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
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "咨询管理费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineType',
								nodeKey : 'ourmainType',
								readOnly : this.isDiligenceReadOnly,
								// allowBlank : false,
								value : null,
								anchor : "98%",
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();

													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "managementConsultingMineName",
								readOnly : this.isAllReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(咨询管理费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.managementConsultingMineId',
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								readOnly : this.isDiligenceReadOnly,
								mode : 'local',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "贷款用途",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款用途',
								nodeKey : 'smallloan_purposeType',
								emptyText : "请选择",
								editable : false,
								anchor : "95%",
								allowBlank : false,
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
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel + 50,
							items : [{
								xtype : "dicIndepCombo",
								fieldLabel : "财务服务费收取单位",
								emptyText : "请选择",
								hiddenName : 'slSmallloanProject.financeServiceMineType',
								nodeKey : 'ourmainType',
								// allowBlank : false,
								value : null,
								anchor : "98%",
								readOnly : this.isDiligenceReadOnly,
								scope : this,
								listeners : {
									change : function(combox, record, index) {
										var comboxValue = combox.getValue();
										var url = '';
										var store = null;
										var combo = combox.ownerCt
												.nextSibling().get(0);
										combo.clearValue();
										if (comboxValue == "company_ourmain")// 企业
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlCompanyMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'corName'
																}, {
																	name : 'itemValue',
																	mapping : 'companyMainId'
																}])
											})
										} else if (comboxValue == "person_ourmain") // 个人
										{
											url = __ctxPath
													+ '/creditFlow/ourmain/listSlPersonMain.do';
											store = new Ext.data.Store({
												proxy : new Ext.data.HttpProxy(
														{
															url : url
														}),
												reader : new Ext.data.JsonReader(
														{
															root : 'result'
														}, [{
																	name : 'itemName',
																	mapping : 'name'
																}, {
																	name : 'itemValue',
																	mapping : 'personMainId'
																}])
											})
										}
										combo.store = store;
										combo.valueField = "itemValue";
										store.load();
										if (combo.view) { // 刷新视图,避免视图值与实际值不相符
											combo.view.setStore(combo.store);
										}
									},
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												})
										combox.clearInvalid();
									}
								}
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [this.isDiligenceReadOnly ? {
								xtype : "textfield",
								name : "financeServiceMineName",
								readOnly : this.isDiligenceReadOnly,
								readOnly : this.isDiligenceReadOnly,
								anchor : "98%"

							} : {
								xtype : "combo",
								anchor : "98%",
								// fieldLabel : "我方主体(财务服务费)",
								store : new Ext.data.SimpleStore({
											fields : ['displayText', 'value'],
											data : [['地区', 1]]
										}),
								emptyText : "请选择",
								// allowBlank : false,
								displayField : 'itemName',
								typeAhead : true,
								mode : 'local',
								readOnly : this.isDiligenceReadOnly,
								hiddenName : 'slSmallloanProject.financeServiceMineId',
								name : 'slSmallloanProject.financeServiceMineId',
								value : null,
								selectOnFocus : true,
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										combox.clearInvalid();
									}
								}

							}]
						}, {
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "主担保方式",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.assuretypeid',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'zdbfs',
								emptyText : "请选择",
								editable : false,
								anchor : "95%",
								allowBlank : false,
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

							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								fieldLabel : "产品类型",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.productTypeId',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'ProductType',
								emptyText : "请选择",
								editable : false,
								anchor : "98%",
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
							columnWidth : .15, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								fieldLabel : "项目来源",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.customerChannel',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'customer_channel',
								emptyText : "请选择",
								editable : false,
								anchor : "98%",
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
							columnWidth : .15, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 50,
							labelAlign : 'right',
							items : [{
										fieldLabel : "推荐人",
										xtype : "textfield",
										name : "slSmallloanProject.recommendUser",
										readOnly : this.isAllReadOnly,
										anchor : "95%"

									}]

						}, {
							columnWidth : .15,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								xtype : 'radio',
								boxLabel : '已完成贷款',
								fieldLabel : '项目状态',
								name : 'finishstatus',
								// checked:true,
								allowBlank : false,
								listeners : {
									scope : this,
									'check' : function(Checkbox, checked) {
										if (checked == true) {
											this
													.getCmpByName('slSmallloanProject.projectStatus')
													.setValue(2)
											this.getCmpByName('fkstatus')
													.setValue(false);
										}
									}
								}
							}]
						}, {
							columnWidth : .1,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '放款后贷款',
								name : 'fkstatus',
								hideLabel : true,
								listeners : {
									scope : this,
									'check' : function(Checkbox, checked) {
										if (checked == true) {
											this
													.getCmpByName('slSmallloanProject.projectStatus')
													.setValue(1)
											this.getCmpByName('finishstatus')
													.setValue(false);
										}
									}
								}
							}, {
								xtype : 'hidden',
								name : 'slSmallloanProject.projectStatus'
							}]
						}, {
							columnWidth : .15,
							layout : 'form',
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								fieldLabel : "项目属性",
								xtype : "dicIndepCombo",
								hiddenName : 'slSmallloanProject.projectProperties',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								nodeKey : 'projectProperties',
								emptyText : "请选择",
								editable : false,
								anchor : "90%",
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
						}]

			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});
// 财务表单上
// 财务表单上
ExtUD.Ext.showCustomerInfo = function(projectId, oppositeType, businessType) {
	var mainInfo = null;
	var title = "查看企业信息";
	if (oppositeType == 306) {
		mainInfo = new ExtUD.Ext.PeerMainInfoPanel({
					projectId : projectId,
					bussinessType : businessType,
					isAllReadOnly : true,
					frame : true
				});
	} else if (oppositeType == 307) {
		mainInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
					isAllReadOnly : true,
					frame : true
				});
		title = "查看个人信息";
	}
	var Employee = new Ext.data.Record.create([{
				name : 'enterprise.enterprisename',
				mapping : 'enterprisename'
			}, {
   			     name : 'enterprise.surplusMoney',
   			     mapping : 'surplusMoney'
   			 }, {
				name : 'enterprise.shortname',
				mapping : 'shortname'
			}, {
				name : 'enterprise.organizecode',
				mapping : 'organizecode'
			},{
				name : 'enterprise.cciaa',
				mapping : 'cciaa'
			}, {
				name : 'enterprise.hangyeType',
				mapping : 'hangyeType'
			}, {
				name : 'enterprise.telephone',
				mapping : 'telephone'
			}, {
				name : 'enterprise.postcoding',
				mapping : 'postcoding'
			}, {
				name : 'enterprise.address',
				mapping : 'address'
			}, {
				name : 'enterprise.id',
				mapping : 'eid'
			}, {
				name : 'person.name',
				mapping : 'pname'
			}, {
				name : 'person.telphone',
				mapping : 'ptelphone'
			}, {
				name : 'person.selfemail',
				mapping : 'pselfemail'
			}, {
				name : 'person.cardnumber',
				mapping : 'pcardnumber'
			}, {
				name : 'person.sex',
				mapping : 'psex'
			}, {
				name : 'person.marry',
				mapping : 'marry'
			}, {
				name : 'person.cardtype',
				mapping : 'pcardtype'
			}]);
	var fp = new Ext.FormPanel({
				autoHeight : true
			})

	var empReader = new Ext.data.JsonReader({
				root : "data"
			}, Employee);

	fp.getForm().reader = empReader;
	fp.getForm().load({
				url : __ctxPath + '/project/loadInfoSlSmallloanProject.do',
				method : "POST",
				params : {
					"taskId" : projectId,
					businessType : businessType
				}
			});
	fp.add(mainInfo);

	var _window = new Ext.Window({
				border : false,
				title : title,
				modal : true,
				autoHeight : true,
				width : "60%",
				labelWidth : 45,
				plain : true,
				buttonAlign : 'center',
				layout : 'fit',
				resizable : false,
				closable : true,
				buttons : [{
							xtype : 'button',
							text : '关闭',
							iconCls : 'close',
							scope : this,
							handler : function() {
								_window.close();
							}
						}]
			})
	_window.add(fp);
	_window.doLayout();
	_window.show();
};
ExtUD.Ext.ProjectInfoFinancePanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isProjectMoneyReadOnly : false,// 判断放款金额是否只读
	isDiligenceReadOnly : false,
	idDefinition : 'liucheng',
	isHiddencalculateBtn : true,
	isBlurFlag : false,// 用来判断是否比较修改放款金额大小false表示不比较，true表示比较
	isHiddenbackBtn : true,
	isHiddenParams : false,// 一个vm文件中所new的两个对象都包含了项目ID等隐藏属性，对应的值就是一个object，而非实际的值。所以需要设置一个对象的隐藏属性disabled为true
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isProjectMoneyReadOnly) {
			this.isProjectMoneyReadOnly = _cfg.isProjectMoneyReadOnly;
		}
		if (_cfg.isBlurFlag) {
			this.isBlurFlag = _cfg.isBlurFlag;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		if (typeof(_cfg.isHiddencalculateBtn) != "undefined") {
			this.isHiddencalculateBtn = _cfg.isHiddencalculateBtn;
		}
		if (typeof(_cfg.isHiddenbackBtn) != "undefined") {
			this.isHiddenbackBtn = _cfg.isHiddenbackBtn;
		}
		if (typeof(_cfg.isHiddenParams) != "undefined") {
			this.isHiddenParams = _cfg.isHiddenParams;
		}
		Ext.applyIf(this, _cfg);
		this.idDefinition = this.projectId + this.idDefinition;
		var leftlabel = 100;
		var centerlabel = 100;
		var rightlabel = 100;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ExtUD.Ext.ProjectInfoFinancePanel.superclass.constructor.call(this, {
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
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						xtype : "textfield",
						fieldLabel : this.projectMoneyTitle != null
								? this.projectMoneyTitle
								: "贷款金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isProjectMoneyReadOnly,
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
							change : function(nf) {
								var value = nf.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									this
											.getCmpByName("slSmallloanProject.projectMoney")
											.setValue(value);
								} else {
									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("slSmallloanProject.projectMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										}
									}

								}
							},
							blur : function() {
								var currentMoney = this
										.getCmpByName("slSmallloanProject.projectMoney")
										.getValue();
								var projectMoneyPass = this
										.getCmpByName("slSmallloanProject.projectMoneyPass")
										.getValue();
								if (this.isBlurFlag) {
									if (parseFloat(currentMoney) > parseFloat(projectMoneyPass)) {
										alert("期望放款金额只能小于或者等于"
												+ projectMoneyPass);
										this.getCmpByName("projectMoney1")
												.setValue("");
										this
												.getCmpByName("slSmallloanProject.projectMoney")
												.setValue("");
									}
								}
							}
						}
					}, {
						xtype : "hidden",
						name : "slSmallloanProject.projectMoney"
					}, {
						xtype : "hidden",
						name : "slSmallloanProject.projectMoneyPass"
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								anchor : "100%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
												})
									}

								}
							}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						fieldLabel : this.loanTitle != null
								? this.loanTitle
								: "放款日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "slSmallloanProject.startDate",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						anchor : "89%",
						format : 'Y-m-d'
					}]
				}, {

					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .13, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
											fieldLabel : "计息方式 ",
											// fieldClass : 'field-align',
											// html :
											// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本金',
									// fieldLabel : "计息方式",
									name : 'f1',
									id : "jixifs1" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this.getCmpByName('month')
														.show();
												this.getCmpByName('periord')
														.hide()
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("sameprincipal");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('payintentPeriod1')
														.setDisabled(false);
												this.getCmpByName('mqhkri1')
														.hide();
												this.getCmpByName('mqhkri')
														.show();
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
									id : "jixifs2" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this.getCmpByName('month')
														.show();
												this.getCmpByName('periord')
														.hide()
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('payintentPeriod1')
														.setDisabled(false);
												this.getCmpByName('mqhkri1')
														.hide();
												this.getCmpByName('mqhkri')
														.show();
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
									boxLabel : '等本等息',
									anchor : "100%",
									name : 'f1',
									id : "jixifs5" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {

											var flag = Ext.getCmp("jixifs5"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this.getCmpByName('month')
														.hide();
												this.getCmpByName('periord')
														.show()
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("sameprincipalsameInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(false);
												// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
												if (this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.getValue() == "") {
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setValue(true);
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setValue(false);
													this
															.getCmpByName('slSmallloanProject.payaccrualType')
															.setValue("monthPay");
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
									boxLabel : '按期收息,到期还本',
									name : 'f1',
									id : "jixifs3" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this.getCmpByName('month')
														.show();
												this.getCmpByName('periord')
														.hide()
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("singleInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(false);
												// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
												if (this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.getValue() == "") {
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setValue(true);
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setValue(false);
													this
															.getCmpByName('slSmallloanProject.payaccrualType')
															.setValue("monthPay");
												}
											}
										}
									}
								}, {
									xtype : "hidden",
									name : "slSmallloanProject.accrualtype",
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
									id : "jixifs4" + this.idDefinition,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs4"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this.getCmpByName('month')
														.show();
												this.getCmpByName('periord')
														.hide()
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("ontTimeAccrual");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("");

												this
														.getCmpByName('payintentPeriod1')
														.setDisabled(true);
												this
														.getCmpByName('payintentPeriod1')
														.setValue(1);
												this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.setValue(1);
												this.getCmpByName('mqhkri')
														.hide();
												this.getCmpByName('mqhkri1')
														.show();

											} else {
												this
														.getCmpByName('payintentPeriod1')
														.setDisabled(false);

												this.getCmpByName('mqhkri1')
														.hide();
												this.getCmpByName('mqhkri')
														.show();

											}
										}
									}
								}, {
									xtype : "hidden",
									name : "slSmallloanProject.payaccrualType",
									value : "monthPay"

								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .13, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
											fieldLabel : "计息周期 ",
											// fieldClass : 'field-align',
											// html :
											// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
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
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("dayPay");

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);

												this
														.getCmpByName('slSmallloanProject.isStartDatePay')
														.setValue("2");

											} else {

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.getValue() == true) {
													this
															.getCmpByName('slSmallloanProject.payintentPerioDate')
															.setDisabled(false);
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
									id : "jixizq2" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("monthPay");

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
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("seasonPay");

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
									boxLabel : '年',
									name : 'z1',
									id : "jixizq4" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("yearPay");

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
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("owerPay");

												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(false);

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);
											} else {
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");
												if (Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.getValue() == false) {
													Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("meiqihkrq2"
																	+ this.idDefinition)
															.setDisabled(false);
													if (Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.getValue() == true) {
														this
																.getCmpByName('slSmallloanProject.payintentPerioDate')
																.setDisabled(false);
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
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'slSmallloanProject.dayOfEveryPeriod'
								}]
							}, {
								columnWidth : 0.04,
								labelWidth : 20,
								layout : 'form',
								items : [{
											fieldLabel : "天",
											labelSeparator : '',
											anchor : "100%"
										}]
							}

					]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .28, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						items : [{
							fieldLabel : "贷款期数",
							xtype : "textfield",
							fieldClass : 'field-align',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : "payintentPeriod1",
							anchor : "100%",
							listeners : {
								scope : this,
								change : function(nf) {
									var nfvalue = nf.getValue();
									this
											.getCmpByName('slSmallloanProject.payintentPeriod')
											.setValue(nfvalue);
								}
							}

						}, {
							xtype : "hidden",
							name : "slSmallloanProject.payintentPeriod"

						}]
					}, {
						columnWidth : .055, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "期",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : .5,
						name : "mqhkri",
						layout : "column",
						items : [{
							columnWidth : 0.328,
							labelWidth : centerlabel + 21,
							layout : 'form',
							items : [{
								xtype : 'radio',
								fieldLabel : '每期还款日',
								boxLabel : '固定在',
								name : 'q1',
								id : "meiqihkrq1" + this.idDefinition,
								inputValue : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq1"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.isStartDatePay')
													.setValue("1");
											this
													.getCmpByName('slSmallloanProject.payintentPerioDate')
													.setDisabled(false);
										}
									}
								}

							}, {
								xtype : "hidden",
								name : "slSmallloanProject.isStartDatePay"

							}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								readOnly : this.isAllReadOnly,
								name : "slSmallloanProject.payintentPerioDate",
								xtype : 'combo',
								mode : 'local',
								disabled : true,
								displayField : 'name',
								valueField : 'id',
								editable : true,
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : obstorepayintentPeriod
										}),
								triggerAction : "all",
								hiddenName : "slSmallloanProject.payintentPerioDate",
								fieldLabel : "",
								anchor : '100%'
							}]
						}, {
							columnWidth : 0.12,
							labelWidth : 45,
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
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq2"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.isStartDatePay')
													.setValue("2");
											this
													.getCmpByName('slSmallloanProject.payintentPerioDate')
													.setValue(null);
											this
													.getCmpByName('slSmallloanProject.payintentPerioDate')
													.setDisabled(true);
										}
									}
								}

							}]
						}]
					}, {
						columnWidth : .275, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : centerlabel - 5,
						name : "mqhkri1",
						hidden : true,
						hideLabel : true,
						items : [{
									fieldLabel : "<font color='red'>*</font>还款日期",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "slSmallloanProject.intentDate",
									readOnly : this.isAllReadOnly,
									blankText : "还款日期不能为空，请正确填写!",
									anchor : "100%",
									format : 'Y-m-d'
								}]
					}, {
						columnWidth : .12, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 13,
						items : [{
							xtype : "checkbox",
							boxLabel : "是否前置付息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isPreposePayAccrual",
							listeners : {
								scope : this,
								'check' : function(box, value) {
									if (value == true) {
										this
												.getCmpByName('slSmallloanProject.isPreposePayAccrual')
												.setValue(1);
									} else {
										this
												.getCmpByName('slSmallloanProject.isPreposePayAccrual')
												.setValue(0);
									}
								}
							}
						}, {
							xtype : 'hidden',
							name : 'slSmallloanProject.isPreposePayAccrual',
							value : false
						}]
					}]
				}, {
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "slSmallloanProject.accrual",
						fieldClass : 'field-align',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						decimalPrecision : 4,
						anchor : "100%",
						fieldLabel : "贷款利率",
						listeners : {
							scope : this,
							change : function(nf) {
								this
										.getCmpByName("slSmallloanProject.accrualnew")
										.setValue(nf.getValue());
								var dayaccrual = computeDayOfAccrual(this);
								// 自动计算
								// this.getCmpByName("slSmallloanProject.overdueRate").setValue(dayaccrual);
								// this.getCmpByName("slSmallloanProject.overdueRateLoan").setValue(dayaccrual);
							}
						}

					}, {
						xtype : 'hidden',
						name : 'slSmallloanProject.accrualnew'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					name : 'month',
					items : [{
								fieldLabel : "%/月",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					name : 'periord',
					hidden : true,
					items : [{
								fieldLabel : "%/期",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.managementConsultingOfRate",
								allowBlank : false,
								fieldLabel : "管理咨询费率",
								decimalPrecision : 4,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "管理咨询费率不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
										var value = nf.getValue();
										if (value == null || value == "") {
											nf.setValue(0)
										}
										var dayaccrual = computeDayOfAccrual(this);
										// 自动计算
										// this.getCmpByName("slSmallloanProject.overdueRate").setValue(dayaccrual);
										// this.getCmpByName("slSmallloanProject.overdueRateLoan").setValue(dayaccrual);
									}
								}
							}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					items : [{
								fieldLabel : "%/月",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .23, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 100,
					items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.financeServiceOfRate",
								allowBlank : false,
								fieldLabel : "财务服务费率",
								decimalPrecision : 10,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "财务服务率不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
										var value = nf.getValue();
										if (value == null || value == "") {
											nf.setValue(0)
										}
									}
								}
							}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 30,
					items : [{
								fieldLabel : "%/期",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .28,
						layout : 'form',
						labelWidth : centerlabel,
						labelAlign : 'right',
						items : [{
									fieldLabel : '逾期贷款利率',
									xtype : "textfield",
									readOnly : this.isAllReadOnly,
									name : "slSmallloanProject.overdueRateLoan",
									allowBlank : false,
									blankText : "逾期贷款利率不能为空，请正确填写!",
									fieldClass : 'field-align',
									anchor : '100%'
								}]
					}, {
						columnWidth : .05,
						layout : 'form',
						labelWidth : 30,
						items : [{
									fieldLabel : "%/日",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : .28,
						layout : 'form',
						labelWidth : centerlabel,
						labelAlign : 'right',
						items : [{
									fieldLabel : '逾期罚息利率',
									xtype : "textfield",
									readOnly : this.isAllReadOnly,
									name : "slSmallloanProject.overdueRate",
									fieldClass : 'field-align',
									allowBlank : false,
									blankText : "逾期罚息利率不能为空，请正确填写!",
									anchor : '100%'
								}]
					}, {
						columnWidth : .05,
						layout : 'form',
						labelWidth : 30,
						items : [{
									fieldLabel : "%/日",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : 0.15, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						items : [{
									xtype : "numberfield",
									name : "slSmallloanProject.breachRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									fieldLabel : "违约金比例",
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									anchor : "100%"
								}, {
									xtype : "hidden",
									name : "slSmallloanProject.projectStatus"
								}]
					}, {
						columnWidth : .05,
						layout : 'form',
						labelWidth : 30,
						items : [{
									fieldLabel : "%/日",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {

						columnWidth : .1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 15,
						items : [{
							xtype : 'button',
							text : '贷款试算',
							iconCls : 'btn-customer',
							width : 110,
							anchor : "40%",
							hidden : this.isHiddencalculateBtn,
							scope : this,
							handler : function() {
								var isHiddenbackBtn = this.isHiddenbackBtn;
								var projectMoney = this
										.getCmpByName("slSmallloanProject.projectMoney")
										.getValue();
								var startDate = this
										.getCmpByName("slSmallloanProject.startDate")
										.getValue();
								var payaccrualType = this
										.getCmpByName("slSmallloanProject.payaccrualType")
										.getValue();
								var dayOfEveryPeriod = this
										.getCmpByName("slSmallloanProject.dayOfEveryPeriod")
										.getValue();
								var payintentPeriod = this
										.getCmpByName("slSmallloanProject.payintentPeriod")
										.getValue();
								var isStartDatePay = this
										.getCmpByName("slSmallloanProject.isStartDatePay")
										.getValue();
								var payintentPerioDate = this
										.getCmpByName("slSmallloanProject.payintentPerioDate")
										.getValue();
								// var
								// intentDate=this.getCmpByName("slSmallloanProject.intentDate").getValue();
								var accrual = this
										.getCmpByName("slSmallloanProject.accrual")
										.getValue();
								var managementConsultingOfRate = this
										.getCmpByName("slSmallloanProject.managementConsultingOfRate")
										.getValue();
								var accrualtype = this
										.getCmpByName("slSmallloanProject.accrualtype")
										.getValue();
								var isPreposePayAccrual = this
										.getCmpByName("slSmallloanProject.isPreposePayAccrual")
										.getValue();
								var overdueRate = this
										.getCmpByName("slSmallloanProject.overdueRate")
										.getValue();
								var overdueRateLoan = this
										.getCmpByName("slSmallloanProject.overdueRateLoan")
										.getValue();

								new calculateFundIntent({
									projectMoney : projectMoney,
									startDate : startDate,
									payaccrualType : payaccrualType,
									dayOfEveryPeriod : dayOfEveryPeriod,
									payintentPeriod : payintentPeriod,
									isStartDatePay : isStartDatePay,
									payintentPerioDate : payintentPerioDate,
									// intentDate:intentDate,
									accrual : accrual,
									managementConsultingOfRate : managementConsultingOfRate,
									accrualtype : accrualtype,
									projectId : this.projectId,
									isPreposePayAccrual : isPreposePayAccrual,
									overdueRate : overdueRate,
									overdueRateLoan : overdueRateLoan,
									objectfinace : this,
									isHiddenbackBtn : isHiddenbackBtn

								}).show();
							}
						}]

					}]
				}, {
					columnWidth : .17,
					layout : "column",
					items : [{
						columnWidth : 0.01,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '',
							hidden : true,
							name : 'yuqifljsfs',
							id : "yuqifljsfs1" + this.idDefinition,
							inputValue : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("yuqifljsfs1"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this
												.getCmpByName('slSmallloanProject.overdueRateType')
												.setValue("1");
										this
												.getCmpByName('slSmallloanProject.overdueRate')
												.setDisabled(false);
										this
												.getCmpByName('slSmallloanProject.overdueRateLoan')
												.setDisabled(false);
									}
								}
							}

						}, {
							xtype : "hidden",
							name : "slSmallloanProject.overdueRateType",
							value : 1

						}]
					}, {
						columnWidth : .01, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						hidden : true,
						labelWidth : 1,
						items : [{
							xtype : 'radio',
							boxLabel : '按期计算',
							name : 'yuqifljsfs',
							id : "yuqifljsfs2" + this.idDefinition,
							inputValue : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("yuqifljsfs2"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this
												.getCmpByName('slSmallloanProject.overdueRateType')
												.setValue("2");
										this
												.getCmpByName('slSmallloanProject.overdueRate')
												.setValue(null);
										this
												.getCmpByName('slSmallloanProject.overdueRate')
												.setDisabled(true);
										this
												.getCmpByName('slSmallloanProject.overdueRateLoan')
												.setValue(null);
										this
												.getCmpByName('slSmallloanProject.overdueRateLoan')
												.setDisabled(true);
									}
								}
							}

						}]
					}]
				}]
			}, {
				columnWidth : .17, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				hidden : true,
				labelWidth : 50,
				items : [{
					xtype : "checkbox",
					name : "isAheadPay",
					disabled : this.isAllReadOnly,
					boxLabel : "是否允许提前还款",
					anchor : "100%",
					listeners : {
						scope : this,
						check : function(obj, isChecked) {
							if (isChecked) {
								var operateObj = this
										.getCmpByName('slSmallloanProject.aheadDayNum');
								Ext.apply(operateObj, {
											allowBlank : false,
											blankText : "提前还款通知天数不能为空!"
										});
								operateObj.setDisabled(false)
							} else {
								var operateObj = this
										.getCmpByName('slSmallloanProject.aheadDayNum');
								operateObj.setValue("");
								Ext.apply(operateObj, {
											allowBlank : true
										});
								operateObj.clearInvalid()
								operateObj.setDisabled(true)
							}
						}
					}
				}]
			}, {
				columnWidth : .08, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				hidden : true,
				labelWidth : 90,
				labelWidth : 40,
				items : [{
							fieldLabel : "提前",
							xtype : "numberfield",
							fieldClass : 'field-align',
							name : "slSmallloanProject.aheadDayNum",
							// allowBlank : false,
							style : {
								imeMode : 'disabled'
							},
							readOnly : this.isAllReadOnly,
							blankText : "提前还款通知天数不能为空，请正确填写!",
							anchor : "100%"

						}]
			}, {
				xtype : "hidden",
				name : "slSmallloanProject.intentDate"
			}, {
				xtype : 'hidden',
				name : 'slSmallloanProject.projectId',
				disabled : this.isHiddenParams
			}, {
				xtype : 'hidden',
				name : 'slSmallloanProject.companyId',
				disabled : this.isHiddenParams
			}, {
				xtype : 'hidden',
				name : 'slSmallloanProject.businessType',
				disabled : this.isHiddenParams
			}, {
				xtype : 'hidden',
				name : 'slSmallloanProject.operationType',
				disabled : this.isHiddenParams
			}, {
				xtype : 'hidden',
				name : 'slSmallloanProject.flowType',
				disabled : this.isHiddenParams
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});
var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,objectPanel){
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
					intentDatePanel.setValue(obj.intentDate);
					var intentDate=obj.intentDate
				
					if(startDate!='' && intentDate!=''){
						intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
						startDate=Ext.util.Format.date(startDate,'Y-m-d')
						var days=((new Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
						var dayAccrualRate=objectPanel.getCmpByName('slSmallloanProject.dayAccrualRate').getValue()
						if(dayAccrualRate!=''){
							objectPanel.getCmpByName('slSmallloanProject.sumAccrualRate').setValue(dayAccrualRate*days)
						}
						var dayManagementConsultingOfRate=objectPanel.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate').getValue()
						if(dayManagementConsultingOfRate!=''){
							objectPanel.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate').setValue(dayManagementConsultingOfRate*days)
						}
						var dayFinanceServiceOfRate=objectPanel.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate').getValue()
						if(dayFinanceServiceOfRate!=''){
							objectPanel.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate').setValue(dayFinanceServiceOfRate*days)
						}
						
					}
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
	}
ExtUD.Ext.newProjectInfoFinancePanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	idDefinition : 'liucheng',
	name : 'projectInfoFinance',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (typeof(_cfg.isStartDateReadOnly) != "undefined") {
			this.isStartDateReadOnly = _cfg.isStartDateReadOnly;
		} else {
			this.isStartDateReadOnly = true
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		var idDefinition1 = this.idDefinition;
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition = this.projectId + this.idDefinition;
		var leftlabel = 85;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ExtUD.Ext.newProjectInfoFinancePanel.superclass.constructor.call(this,
				{
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
							columnWidth : .15, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : 'hidden',
										name : 'bpFunds',
										value : this.isAllReadOnly ? '0' : '1'
									}, {
										xtype : "textfield",
										fieldLabel : "贷款金额",
										fieldClass : 'field-align',
										name : "projectMoney1",
										readOnly : this.isAllReadOnly,
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
											change : function(nf, nv, ov) {
                                                var o = this.ownerCt.ownerCt
                                                    .getCmpByName('canUseMoney');
                                                if (nv > parseFloat(o
                                                        .getValue())) {
                                                    Ext.ux.Toast
                                                        .msg('警告',
                                                            '贷款金额大于可用额度!');
												// this.ownerCt.ownerCt.getCmpByName('slSmallloanProject.credit').getValue()
												if (this.ownerCt.ownerCt) {
													if (this.ownerCt.ownerCt
															.getCmpByName('slSmallloanProject.money')) {

														var o = this.ownerCt.ownerCt
																.getCmpByName('slSmallloanProject.money');
														if (nv > parseFloat(o
																.getValue())) {
															Ext.ux.Toast
																	.msg('警告',
																			'贷款金额大于申请额度!');
															nf.setValue(ov);
															return;
														}
													}
												}
                                                }
												var value = nf.getValue();
												var index = value.indexOf(".");
												if (index <= 0) { // 如果第一次输入
																	// 没有进行格式化
													nf
															.setValue(Ext.util.Format
																	.number(
																			value,
																			'0,000.00'))
													this
															.getCmpByName("slSmallloanProject.projectMoney")
															.setValue(value);
												} else {

													if (value.indexOf(",") <= 0) {
														var ke = Ext.util.Format
																.number(value,
																		'0,000.00')
														nf.setValue(ke);
														this
																.getCmpByName("slSmallloanProject.projectMoney")
																.setValue(value);
													} else {
														var last = value
																.substring(
																		index
																				+ 1,
																		value.length);
														if (last == 0) {
															var temp = value
																	.substring(
																			0,
																			index);
															temp = temp
																	.replace(
																			/,/g,
																			"");
															this
																	.getCmpByName("slSmallloanProject.projectMoney")
																	.setValue(temp);
															nf
																	.setValue(Ext.util.Format
																			.number(
																					temp,
																					'0,000.00'))
														} else {
															var temp = value
																	.replace(
																			/,/g,
																			"");
															this
																	.getCmpByName("slSmallloanProject.projectMoney")
																	.setValue(temp);
															nf
																	.setValue(Ext.util.Format
																			.number(
																					temp,
																					'0,000.00'))
														}
													}

												}
											}
										}
									}, {
										xtype : "hidden",
										name : "slSmallloanProject.projectMoney"
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
                            columnWidth : .15, // 该列在整行中所占的百分比
                            layout : "form", // 从上往下的布局

                            items : [{
                                xtype : "textfield",
                                fieldLabel : "可用额度",
                                fieldClass : 'field-align',
                                name : "canUseMoney",
                                readOnly : true,
                                allowNegative : false, // 允许负数
                                anchor : "100%",// 控制文本框的长度

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
							columnWidth : .15,
							layout : 'form',
							labelWidth : 50,
							defaults : {
								anchor : '96%'
							},
							items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.currency',
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
							labelWidth : rightlabel,
							items : [{
								xtype : 'dicIndepCombo',
								allowBlank : false,
								fieldLabel : '日期模型',
								readOnly : this.isAllReadOnly,
								anchor : '100%',
								editable : false,
								nodeKey : 'dateModel',
								lazyInit : true,
								lazyRender : true,
								value :'dateModel_360',
								hiddenName : 'slSmallloanProject.dateMode',
								listeners : {
									scope : this,
									afterrender : function(com) {
										var st = com.getStore();
										st.on('load', function() {
											com
													.setValue(st.getAt(0).data.dicKey)
											com.clearInvalid();
										})

									},
									change : function(combo) {
									    	var yearAccrualRate = this.getCmpByName('slSmallloanProject.yearAccrualRate')
											var dayAccrualRaten = this.getCmpByName('slSmallloanProject.dayAccrualRate')
											var yearAccrualnm = this.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
											var dayAccrualRatenm = this.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
										    var yearAccrualnf = this.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
											var dayAccrualRatenf = this.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
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
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										labelAlign : 'right',
										items : [{
													fieldLabel : "还款方式 ",
													fieldClass : 'field-align',
													// html :
													// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
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
											name : 'f1',
											id : "jixifs1" + this.idDefinition,
											inputValue : false,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext.getCmp("jixifs1"+ this.idDefinition).getValue();
													if (flag == true) {
														this.getCmpByName('slSmallloanProject.accrualtype').setValue("sameprincipal");
														Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
														this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
														// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

														Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
														Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
														this.getCmpByName('slSmallloanProject.payaccrualType').setValue("monthPay");
														this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
														/*
														 * this.getCmpByName('mqhkri1').hide();
														 * this.getCmpByName('mqhkri').show();
														 */
														Ext.getCmp("jixifs2"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs5"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs3"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs6"+ this.idDefinition).setValue(false);
														Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(false);
														Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
														
														
														
														//不支持前置付息
														this.getCmpByName('isPreposePayAccrualCheck').setValue(false);
														this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(0);
														this.getCmpByName('isPreposePayAccrualCheck').setDisabled(true);
														//不支持一次性
														this.getCmpByName('isInterestByOneTimeCheck').setValue(false);
														this.getCmpByName('slSmallloanProject.isInterestByOneTime').setValue(0);
														this.getCmpByName('isInterestByOneTimeCheck').setDisabled(true);
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
											name : 'f1',
											id : "jixifs2" + this.idDefinition,
											inputValue : false,
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixifs2"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this.getCmpByName('slSmallloanProject.accrualtype').setValue("sameprincipalandInterest");
														Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq2"	+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);
														Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
														this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
														// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

														Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
														Ext.getCmp("jixizq1"+ this.idDefinition).setValue(false);
														this.getCmpByName('slSmallloanProject.payaccrualType').setValue("monthPay");
														this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
														/*
														 * this.getCmpByName('mqhkri1').hide();
														 * this.getCmpByName('mqhkri').show();
														 */
														Ext.getCmp("jixifs1"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs5"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs3"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs6"+ this.idDefinition).setValue(false);
														Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(false);
														Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
														
														//不支持前置付息
														this.getCmpByName('isPreposePayAccrualCheck').setValue(false);
														this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(0);
														this.getCmpByName('isPreposePayAccrualCheck').setDisabled(true);
														//不支持一次性
														this.getCmpByName('isInterestByOneTimeCheck').setValue(false);
														this.getCmpByName('slSmallloanProject.isInterestByOneTime').setValue(0);
														this.getCmpByName('isInterestByOneTimeCheck').setDisabled(true);
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
											name : 'f1',
											id : "jixifs5" + this.idDefinition,
											inputValue : false,
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext.getCmp("jixifs5"+ this.idDefinition).getValue();
													if (flag == true) {
														this.getCmpByName('slSmallloanProject.accrualtype').setValue("sameprincipalsameInterest");
														if (this.isAllReadOnly == true) {
															Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(true);
															Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(true);
															Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(true);
															Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(true);
															Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(true);
														} else {
															Ext.getCmp("jixizq1"+ this.idDefinition).setDisabled(false);
															Ext.getCmp("jixizq2"+ this.idDefinition).setDisabled(false);
															Ext.getCmp("jixizq3"+ this.idDefinition).setDisabled(false);
															Ext.getCmp("jixizq4"+ this.idDefinition).setDisabled(false);
															Ext.getCmp("jixizq6"+ this.idDefinition).setDisabled(false);
															Ext.getCmp("jixizq6"+ this.idDefinition).setValue(false);
															Ext.getCmp("jixizq2"+ this.idDefinition).setValue(true);
														}
														this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);
														this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue();

														this.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
														Ext.getCmp("jixifs2"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs1"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs3"+ this.idDefinition).setValue(false);
														Ext.getCmp("jixifs6"+ this.idDefinition).setValue(false);
														// 等本等息可以选择固定在，update by
														// gao for dltc
														this.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(false);
														Ext.getCmp("meiqihkrq1"+ this.idDefinition).setDisabled(false);
														Ext.getCmp("meiqihkrq2"+ this.idDefinition).setDisabled(false);
														
														/*this.getCmpByName('isPreposePayAccrualCheck').setDisabled(false);	
														this.getCmpByName('isInterestByOneTimeCheck').setDisabled(false);*/
														
														
														//不支持前置付息
														this.getCmpByName('isPreposePayAccrualCheck').setValue(false);
														this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(0);
														this.getCmpByName('isPreposePayAccrualCheck').setDisabled(true);
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
											name : 'f1',
											id : "jixifs3" + this.idDefinition,
											inputValue : false,
											checked : true,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixifs3"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.accrualtype')
																.setValue("singleInterest");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq6"
																		+ this.idDefinition)
																.setDisabled(false);
														this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.setDisabled(false);
														Ext
																.getCmp("jixifs2"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixifs5"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixifs1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixifs6"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setDisabled(false);
														this.getCmpByName('isPreposePayAccrualCheck').setDisabled(false);	
														this.getCmpByName('isInterestByOneTimeCheck').setDisabled(false);		

													}
												}
											}
										}, {
											xtype : "hidden",
											name : "slSmallloanProject.accrualtype",
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
											name : 'f1',
											id : "jixifs6" + this.idDefinition,
											inputValue : false,
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixifs6"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.accrualtype')
																.setValue("otherMothod");
														if (this.isAllReadOnly == true) {
															Ext
																	.getCmp("jixizq1"
																			+ this.idDefinition)
																	.setDisabled(true);
															Ext
																	.getCmp("jixizq2"
																			+ this.idDefinition)
																	.setDisabled(true);
															Ext
																	.getCmp("jixizq3"
																			+ this.idDefinition)
																	.setDisabled(true);
															Ext
																	.getCmp("jixizq4"
																			+ this.idDefinition)
																	.setDisabled(true);

															Ext
																	.getCmp("jixizq6"
																			+ this.idDefinition)
																	.setDisabled(true);
														} else {
															Ext
																	.getCmp("jixizq1"
																			+ this.idDefinition)
																	.setDisabled(false);
															Ext
																	.getCmp("jixizq2"
																			+ this.idDefinition)
																	.setDisabled(false);
															Ext
																	.getCmp("jixizq3"
																			+ this.idDefinition)
																	.setDisabled(false);
															Ext
																	.getCmp("jixizq4"
																			+ this.idDefinition)
																	.setDisabled(false);

															Ext
																	.getCmp("jixizq6"
																			+ this.idDefinition)
																	.setDisabled(false);
														}
														this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.setDisabled(true);
														// this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue("");

														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(true);
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("monthPay");

														this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.setDisabled(false);
														/*
														 * this.getCmpByName('mqhkri1').hide();
														 * this.getCmpByName('mqhkri').show();
														 */
														Ext
																.getCmp("jixifs2"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixifs5"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixifs3"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixifs1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setDisabled(false);
														this.getCmpByName('isPreposePayAccrualCheck').setDisabled(false);	
														this.getCmpByName('isInterestByOneTimeCheck').setDisabled(false);		
													}
												}
											}

										}, {
											xtype : "hidden",
											name : "slSmallloanProject.payaccrualType",
											value : "monthPay"

										}]
									}]

						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										labelAlign : 'right',
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
											name : 'z1',
											id : "jixizq1" + this.idDefinition,
											inputValue : true,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("dayPay");

														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setValue(true);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq6"
																		+ this.idDefinition)
																.setValue(false);
														this
																.getCmpByName('slSmallloanProject.isStartDatePay')
																.setValue("2");
														var payAccrualType = this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.getValue();
														var dayOfEveryPeriod = this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.getValue();
														var payintentPeriod = this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.getValue();
														var startDate = this
																.getCmpByName('slSmallloanProject.startDate')
																.getValue();
														var intentDatePanel = this
																.getCmpByName('slSmallloanProject.intentDate');
														setIntentDate(
																payAccrualType,
																dayOfEveryPeriod,
																payintentPeriod,
																startDate,
																intentDatePanel,this)

													} else {

														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setDisabled(false);
														if (Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.getValue() == true) {
															this
																	.getCmpByName('slSmallloanProject.payintentPerioDate')
																	.setDisabled(false);
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
											name : 'z1',
											id : "jixizq2" + this.idDefinition,
											inputValue : false,
											checked : true,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("monthPay");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq6"
																		+ this.idDefinition)
																.setValue(false);
														var payAccrualType = this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.getValue();
														var dayOfEveryPeriod = this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.getValue();
														var payintentPeriod = this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.getValue();
														var startDate = this
																.getCmpByName('slSmallloanProject.startDate')
																.getValue();
														var intentDatePanel = this
																.getCmpByName('slSmallloanProject.intentDate');
														setIntentDate(
																payAccrualType,
																dayOfEveryPeriod,
																payintentPeriod,
																startDate,
																intentDatePanel,this)
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
											name : 'z1',
											id : "jixizq3" + this.idDefinition,
											inputValue : false,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("seasonPay");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq6"
																		+ this.idDefinition)
																.setValue(false);
														var payAccrualType = this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.getValue();
														var dayOfEveryPeriod = this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.getValue();
														var payintentPeriod = this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.getValue();
														var startDate = this
																.getCmpByName('slSmallloanProject.startDate')
																.getValue();
														var intentDatePanel = this
																.getCmpByName('slSmallloanProject.intentDate');
														setIntentDate(
																payAccrualType,
																dayOfEveryPeriod,
																payintentPeriod,
																startDate,
																intentDatePanel,this)
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
											name : 'z1',
											id : "jixizq4" + this.idDefinition,
											inputValue : false,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("yearPay");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq6"
																		+ this.idDefinition)
																.setValue(false);
														var payAccrualType = this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.getValue();
														var dayOfEveryPeriod = this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.getValue();
														var payintentPeriod = this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.getValue();
														var startDate = this
																.getCmpByName('slSmallloanProject.startDate')
																.getValue();
														var intentDatePanel = this
																.getCmpByName('slSmallloanProject.intentDate');
														setIntentDate(
																payAccrualType,
																dayOfEveryPeriod,
																payintentPeriod,
																startDate,
																intentDatePanel,this)
													}
												}
											}
										}]
									}, {
										columnWidth : .098,
										labelWidth : 1,
										layout : 'form',
										items : [{
											xtype : 'radio',
											boxLabel : '自定义周期',
											name : 'z1',
											id : "jixizq6" + this.idDefinition,
											inputValue : false,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("owerPay");

														this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.setDisabled(false);

														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.setValue(true);
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setValue(false);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(false);

													} else {
														this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.setDisabled(true);
														this
																.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
																.setValue("");
														if (Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.getValue() == false) {
															Ext
																	.getCmp("meiqihkrq1"
																			+ this.idDefinition)
																	.setDisabled(false);
															Ext
																	.getCmp("meiqihkrq2"
																			+ this.idDefinition)
																	.setDisabled(false);
															if (Ext
																	.getCmp("meiqihkrq1"
																			+ this.idDefinition)
																	.getValue() == true) {
																this
																		.getCmpByName('slSmallloanProject.payintentPerioDate')
																		.setDisabled(false);
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
											xtype : 'textfield',
											anchor : "100%",
											readOnly : this.isAllReadOnly,
											fieldClass : 'field-align',
											name : 'slSmallloanProject.dayOfEveryPeriod',
											listeners : {
												scope : this,
												'change' : function() {
													var payAccrualType = this
															.getCmpByName('slSmallloanProject.payaccrualType')
															.getValue();
													var dayOfEveryPeriod = this
															.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
															.getValue();
													var payintentPeriod = this
															.getCmpByName('slSmallloanProject.payintentPeriod')
															.getValue();
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue();
													var intentDatePanel = this
															.getCmpByName('slSmallloanProject.intentDate');
													setIntentDate(
															payAccrualType,
															dayOfEveryPeriod,
															payintentPeriod,
															startDate,
															intentDatePanel,this)
												}
											}
										}]
									}, {
										columnWidth : 0.07,
										labelWidth : 40,
										layout : 'form',
										items : [{
													fieldLabel : "日/期",
													labelSeparator : '',
													anchor : "100%"
												}]
									}

							]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
								columnWidth : .3, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
									fieldLabel : "贷款期限",
									xtype : "textfield",
									fieldClass : 'field-align',
									allowBlank : false,
									readOnly : this.isAllReadOnly,
									name : "slSmallloanProject.payintentPeriod",
									anchor : "100%",
									listeners : {
										scope : this,
										'change' : function() {
											var payAccrualType = this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.getValue();
											var dayOfEveryPeriod = this
													.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
													.getValue();
											var payintentPeriod = this
													.getCmpByName('slSmallloanProject.payintentPeriod')
													.getValue();
											var startDate = this
													.getCmpByName('slSmallloanProject.startDate')
													.getValue();
											var intentDatePanel = this
													.getCmpByName('slSmallloanProject.intentDate');
											setIntentDate(payAccrualType,
													dayOfEveryPeriod,
													payintentPeriod, startDate,
													intentDatePanel,this)
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
							}, {
								columnWidth : .28,
								layout : 'form',
								labelWidth : 60,
								defaults : {
									anchor : '96%'
								},
								items : [{
									fieldLabel : "放款日期",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "slSmallloanProject.startDate",
									allowBlank : false,
									readOnly : this.isStartDateReadOnly,
									blankText : "放款日期不能为空，请正确填写!",
									format : 'Y-m-d',
									listeners : {
										scope : this,
										afterrender : function(obj) {
											// obj.fireEvent('change',obj);
										},
										'change' : function(nf) {
											var ZDY=Ext.getCmp('jixizq6'+this.idDefinition);
											var payAccrualType = this.getCmpByName('slSmallloanProject.payaccrualType').getValue();
											var dayOfEveryPeriod = this.getCmpByName('slSmallloanProject.dayOfEveryPeriod').getValue();
											var payintentPeriod = this.getCmpByName('slSmallloanProject.payintentPeriod').getValue();
											var startDate = this.getCmpByName('slSmallloanProject.startDate').getValue();
											var intentDatePanel = this.getCmpByName('slSmallloanProject.intentDate');
											if(ZDY.checked){
												if(dayOfEveryPeriod){
													setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod, startDate,intentDatePanel,this)
												}else{
													Ext.ux.Toast.msg('操作信息', '自定义周期不能为空!');
												}
											}else{
												setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod, startDate,intentDatePanel,this)
											}
										}
									}
								}]
							}, {
								columnWidth : .36,
								layout : 'form',
								labelWidth : rightlabel,
								items : [{
									fieldLabel : "<font color='red'>*</font>还款日期",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "slSmallloanProject.intentDate",
									readOnly : true,
									blankText : "还款日期不能为空，请正确填写!",
									anchor : "100%",
									format : 'Y-m-d'
								}]
							}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .1,
										layout : 'form',
										labelWidth : 85,
										labelAlign : 'right',
										items : [{
													fieldLabel : '还款选项'
												}]
									}, {
										columnWidth : .12, // 该列在整行中所占的百分比
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
													|| this.record.data.isPreposePayAccrual == 0
													? null
													: true,
											listeners : {
												scope : this,
												'check' : function(box, value) {
													if (value == true) {
														this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(1);
													} else {
														this.getCmpByName('slSmallloanProject.isPreposePayAccrual').setValue(0);
													}
												}
											}
										}, {
											xtype : 'hidden',
											name : 'slSmallloanProject.isPreposePayAccrual',
											value : 0
										}]
									}, {
										columnWidth : .1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 1,
										items : [{
											xtype : "checkbox",
											boxLabel : "一次性支付全部利息",
											disabled : this.isAllReadOnly,
											anchor : "100%",
											name : "isInterestByOneTimeCheck",
											// value :true
											checked : this.record == null
													|| this.record.data.isInterestByOneTime == 0
													? null
													: true,
											listeners : {
												scope : this,
												'check' : function(box, value) {
													if (value == true) {
														this.getCmpByName('slSmallloanProject.isInterestByOneTime').setValue(1);
													} else {
														this.getCmpByName('slSmallloanProject.isInterestByOneTime').setValue(0);
													}
												}
											}
										}, {
											xtype : 'hidden',
											name : 'slSmallloanProject.isInterestByOneTime',
											value : 0
										}]
									}, {
										columnWidth : .5,
										name : "mqhkri",
										layout : "column",
										items : [{
											columnWidth : 0.255,
											labelWidth : 76,
											layout : 'form',
											items : [{
												xtype : 'radio',
												fieldLabel : '每期还款日',
												boxLabel : '固定在',
												name : 'q1',
												id : "meiqihkrq1"
														+ this.idDefinition,
												inputValue : true,
												anchor : "100%",
												disabled : this.isAllReadOnly,
												listeners : {
													scope : this,
													check : function(obj,
															checked) {
														var flag = Ext
																.getCmp("meiqihkrq1"
																		+ this.idDefinition)
																.getValue();
														if (flag == true) {
															this
																	.getCmpByName('slSmallloanProject.isStartDatePay')
																	.setValue("1");
															this
																	.getCmpByName('slSmallloanProject.payintentPerioDate')
																	.setDisabled(false);
														}
													}
												}

											}, {
												xtype : "hidden",
												name : "slSmallloanProject.isStartDatePay"

											}]
										}, {
											columnWidth : 0.3,
											labelWidth : 1,
											layout : 'form',
											items : [{
												xtype : 'textfield',
												readOnly : this.isAllReadOnly,
												name : "slSmallloanProject.payintentPerioDate",
												xtype : 'combo',
												mode : 'local',
												disabled : true,
												displayField : 'name',
												valueField : 'id',
												editable : true,
												store : new Ext.data.SimpleStore(
														{
															fields : ["name",
																	"id"],
															data : obstorepayintentPeriod
														}),
												triggerAction : "all",
												hiddenName : "slSmallloanProject.payintentPerioDate",
												fieldLabel : "",
												anchor : '100%'
											}]
										}, {
											columnWidth : .08,
											labelWidth : 45,
											layout : 'form',
											items : [{
														fieldLabel : "号还款",
														labelSeparator : '',
														anchor : "100%"
													}]
										}, {
											columnWidth : 0.25,
											labelWidth : 10,
											layout : 'form',
											items : [{
												xtype : 'radio',
												boxLabel : '按实际放款日对日还款',
												name : 'q1',
												id : "meiqihkrq2"
														+ this.idDefinition,
												inputValue : true,
												checked : true,
												anchor : "100%",
												disabled : this.isAllReadOnly,
												listeners : {
													scope : this,
													check : function(obj,
															checked) {
														var flag = Ext
																.getCmp("meiqihkrq2"
																		+ this.idDefinition)
																.getValue();
														if (flag == true) {
															this
																	.getCmpByName('slSmallloanProject.isStartDatePay')
																	.setValue("2");
															this
																	.getCmpByName('slSmallloanProject.payintentPerioDate')
																	.setValue(null);
															this
																	.getCmpByName('slSmallloanProject.payintentPerioDate')
																	.setDisabled(true);
														}
													}
												}

											}]
										}]
									}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .08,
										layout : 'form',
										labelWidth : 85,
										labelAlign : 'right',
										items : [{
													fieldLabel : '贷款利率',
													allowBlank : false
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "年化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .16,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.yearAccrualRate",
											readOnly : this.isAllReadOnly,
											fieldClass : 'field-align',
											decimalPrecision : 8,
											anchor : '100%',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.accrual')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayAccrualRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumAccrualRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													accrualnf
															.setValue((nf
																	.getValue() / (12 * 10))
																	* 10)
													if (null != dateModel
															&& dateModel == 'dateModel_360') {
														dayAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 360)
														this
																.getCmpByName('slSmallloanProject.accrualnew')
																.setValue(nf
																		.getValue()
																		/ 360)
														if (startDate != ''
																&& intentDate != '') {
															intentDate = Ext.util.Format
																	.date(
																			intentDate,
																			'Y-m-d')
															startDate = Ext.util.Format
																	.date(
																			startDate,
																			'Y-m-d')
															var days = ((new Date(
																	intentDate
																			.substring(
																					0,
																					4),
																	intentDate
																			.substring(
																					5,
																					intentDate
																							.lastIndexOf('-'))
																			- 1,
																	intentDate
																			.substring(
																					intentDate
																							.lastIndexOf('-')
																							+ 1,
																					intentDate.length)))
																	.getTime() - (new Date(
																	startDate
																			.substring(
																					0,
																					4),
																	startDate
																			.substring(
																					5,
																					startDate
																							.lastIndexOf('-'))
																			- 1,
																	startDate
																			.substring(
																					startDate
																							.lastIndexOf('-')
																							+ 1,
																					startDate.length)))
																	.getTime())
																	/ 1000
																	/ 60
																	/ 60 / 24
															sumAccrualRatenf
																	.setValue(nf
																			.getValue()
																			/ 360
																			* days)
														}
													} else if (null != dateModel
															&& dateModel == 'dateModel_365') {
														dayAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 365)
														this
																.getCmpByName('slSmallloanProject.accrualnew')
																.setValue(nf
																		.getValue()
																		/ 365)
														if (startDate != ''
																&& intentDate != '') {
															intentDate = Ext.util.Format
																	.date(
																			intentDate,
																			'Y-m-d')
															startDate = Ext.util.Format
																	.date(
																			startDate,
																			'Y-m-d')
															var days = ((new Date(
																	intentDate
																			.substring(
																					0,
																					4),
																	intentDate
																			.substring(
																					5,
																					intentDate
																							.lastIndexOf('-'))
																			- 1,
																	intentDate
																			.substring(
																					intentDate
																							.lastIndexOf('-')
																							+ 1,
																					intentDate.length)))
																	.getTime() - (new Date(
																	startDate
																			.substring(
																					0,
																					4),
																	startDate
																			.substring(
																					5,
																					startDate
																							.lastIndexOf('-'))
																			- 1,
																	startDate
																			.substring(
																					startDate
																							.lastIndexOf('-')
																							+ 1,
																					startDate.length)))
																	.getTime())
																	/ 1000
																	/ 60
																	/ 60 / 24
															sumAccrualRatenf
																	.setValue(nf
																			.getValue()
																			/ 365
																			* days)
														}
													}
												}
											}
										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlign : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlgin : 'right',
										items : [{
													fieldLabel : "月化利率:",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.accrual",
											readOnly : this.isAllReadOnly,
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											anchor : '100%',
											decimalPrecision : 8,
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearAccrualRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayAccrualRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumAccrualRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													yearAccrualRatenf.setValue(nf.getValue()* 12)		
													if(null != dateModel && dateModel == 'dateModel_360'){
													   	dayAccrualRatenf
															.setValue(nf
																	.getValue()
																	/ 30)
													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 30
																		* days)
													}
													this
															.getCmpByName('slSmallloanProject.accrualnew')
															.setValue(nf
																	.getValue()
																	/ 30)
													
													
													}		
												else if(null != dateModel && dateModel == 'dateModel_365'){
													   	dayAccrualRatenf.setValue(nf.getValue()*12/365)
													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()*12
																		/ 365
																		* days)
													}
													this
															.getCmpByName('slSmallloanProject.accrualnew')
															.setValue(nf
																	.getValue()*12
																	/365)
													
													}			
												}
											}

										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlgin : 'right',
										items : [{
													fieldLabel : "日化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.dayAccrualRate",
											readOnly : this.isAllReadOnly,
											fieldClass : 'field-align',
											anchor : '100%',
											decimalPrecision : 8,
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.accrual')
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearAccrualRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumAccrualRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													
													this
															.getCmpByName('slSmallloanProject.accrualnew')
															.setValue(nf
																	.getValue())
													if (null != dateModel
															&& dateModel == 'dateModel_360') {
														yearAccrualRatenf.setValue(nf.getValue()* 360);
														accrualnf.setValue(nf.getValue()* 30);

													} else if (null != dateModel
															&& dateModel == 'dateModel_365') {
														yearAccrualRatenf
																.setValue(nf
																		.getValue()
																		* 365);
													    accrualnf.setValue(nf.getValue()* 365/12);
													}
													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()
																		* days)
													}
												}
											}
										}, {
											xtype : 'hidden',
											name : 'slSmallloanProject.accrualnew',
											value : 0
										}]
									}, {
										columnWidth : .045,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlgin : 'right',
										items : [{
													fieldLabel : "合计利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .27,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.sumAccrualRate",
											readOnly : this.isAllReadOnly,
											anchor : '100%',
											decimalPrecision : 8,
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.accrual')
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearAccrualRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayAccrualRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()

													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														var rate = nf
																.getValue()
																/ days
														dayAccrualRatenf
																.setValue(rate);
														if (null != dateModel
																&& dateModel == 'dateModel_360') {
															yearAccrualRatenf
																	.setValue(rate
																			* 360);
														  accrualnf.setValue(rate
																* 30)

														} else if (null != dateModel
																&& dateModel == 'dateModel_365') {
															yearAccrualRatenf
																	.setValue(rate
																			* 365)
															accrualnf.setValue(rate
																* 365/12)
														}
												
														this
																.getCmpByName('slSmallloanProject.accrualnew')
																.setValue(rate)
													}
												}
											}
										}]
									}, {
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
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .08,
										layout : 'form',
										labelWidth : 85,
										labelAlign : 'right',
										items : [{
													fieldLabel : '管理咨询费率',
													allowBlank : false
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "年化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .16,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.yearManagementConsultingOfRate",
											readOnly : this.isAllReadOnly,
											fieldClass : 'field-align',
											anchor : '100%',
											decimalPrecision : 8,
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.managementConsultingOfRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													accrualnf
															.setValue((nf
																	.getValue() / (12 * 10))
																	* 10)
													if (null != dateModel
															&& dateModel == 'dateModel_360') {
														dayAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 360)
														if (startDate != ''
																&& intentDate != '') {
															intentDate = Ext.util.Format
																	.date(
																			intentDate,
																			'Y-m-d')
															startDate = Ext.util.Format
																	.date(
																			startDate,
																			'Y-m-d')
															var days = ((new Date(
																	intentDate
																			.substring(
																					0,
																					4),
																	intentDate
																			.substring(
																					5,
																					intentDate
																							.lastIndexOf('-'))
																			- 1,
																	intentDate
																			.substring(
																					intentDate
																							.lastIndexOf('-')
																							+ 1,
																					intentDate.length)))
																	.getTime() - (new Date(
																	startDate
																			.substring(
																					0,
																					4),
																	startDate
																			.substring(
																					5,
																					startDate
																							.lastIndexOf('-'))
																			- 1,
																	startDate
																			.substring(
																					startDate
																							.lastIndexOf('-')
																							+ 1,
																					startDate.length)))
																	.getTime())
																	/ 1000
																	/ 60
																	/ 60 / 24
															sumAccrualRatenf
																	.setValue(nf
																			.getValue()
																			/ 360
																			* days)
														}
													} else if (null != dateModel
															&& dateModel == 'dateModel_365') {
														dayAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 365)
														if (startDate != ''
																&& intentDate != '') {
															intentDate = Ext.util.Format
																	.date(
																			intentDate,
																			'Y-m-d')
															startDate = Ext.util.Format
																	.date(
																			startDate,
																			'Y-m-d')
															var days = ((new Date(
																	intentDate
																			.substring(
																					0,
																					4),
																	intentDate
																			.substring(
																					5,
																					intentDate
																							.lastIndexOf('-'))
																			- 1,
																	intentDate
																			.substring(
																					intentDate
																							.lastIndexOf('-')
																							+ 1,
																					intentDate.length)))
																	.getTime() - (new Date(
																	startDate
																			.substring(
																					0,
																					4),
																	startDate
																			.substring(
																					5,
																					startDate
																							.lastIndexOf('-'))
																			- 1,
																	startDate
																			.substring(
																					startDate
																							.lastIndexOf('-')
																							+ 1,
																					startDate.length)))
																	.getTime())
																	/ 1000
																	/ 60
																	/ 60 / 24
															sumAccrualRatenf
																	.setValue(nf
																			.getValue()
																			/ 365
																			* days)
														}
													}
												}
											}
										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "月化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.managementConsultingOfRate",
											readOnly : this.isAllReadOnly,
											fieldClass : 'field-align',
											anchor : '100%',
											decimalPrecision : 8,
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													yearAccrualRatenf
															.setValue(nf
																	.getValue()
																	* 12)
													
												  if(null != dateModel && dateModel == 'dateModel_360'){
												  		if (startDate != ''
															&& intentDate != '') {
														dayAccrualRatenf
															.setValue(nf
																	.getValue()
																	/ 30)
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 30
																		* days)
													}
												  }
											     else if(null != dateModel && dateModel == 'dateModel_365'){
												  		if (startDate != ''
															&& intentDate != '') {
													  dayAccrualRatenf
															.setValue(nf
																	.getValue()*12
																	/ 365)
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()*12
																		/ 365
																		* days)
													}
												  }
										
												}
											}

										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "日化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.dayManagementConsultingOfRate",
											readOnly : this.isAllReadOnly,
											anchor : '100%',
											decimalPrecision : 8,
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.managementConsultingOfRate')
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													
													if (null != dateModel
															&& dateModel == 'dateModel_360') {
														yearAccrualRatenf
																.setValue(nf
																		.getValue()
																		* 360);
														accrualnf.setValue(nf
															.getValue()*360/12
															)

													} else if (null != dateModel
															&& dateModel == 'dateModel_365') {
														yearAccrualRatenf
																.setValue(nf
																		.getValue()
																		* 365);
													   	accrualnf.setValue(nf
															.getValue()*365/12
															)
													}
													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()
																		* days)
													}
												}
											}
										}]
									}, {
										columnWidth : .045,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "合计利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .27,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.sumManagementConsultingOfRate",
											readOnly : this.isAllReadOnly,
											decimalPrecision : 8,
											anchor : '100%',
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.managementConsultingOfRate')
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearManagementConsultingOfRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()

													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														var rate = nf
																.getValue()
																/ days
														dayAccrualRatenf
																.setValue(rate);
														if (null != dateModel
																&& dateModel == 'dateModel_360') {
															yearAccrualRatenf
																	.setValue(rate
																			* 360);
														    accrualnf.setValue(rate
																* 30)

														} else if (null != dateModel
																&& dateModel == 'dateModel_365') {
															yearAccrualRatenf
																	.setValue(rate
																			* 365);
														   	accrualnf.setValue(rate
																* 365/12)
														}
													
													}
												}
											}
										}]
									}, {
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
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .08,
										layout : 'form',
										labelWidth : 85,
										labelAlign : 'right',
										items : [{
													fieldLabel : '财务服务费率',
													allowBlank : false
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "年化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .16,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.yearFinanceServiceOfRate",
											readOnly : this.isAllReadOnly,
											decimalPrecision : 8,
											anchor : '100%',
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.financeServiceOfRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													accrualnf
															.setValue((nf
																	.getValue() / (12 * 10))
																	* 10)
													if (null != dateModel
															&& dateModel == 'dateModel_360') {
														dayAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 360)
														if (startDate != ''
																&& intentDate != '') {
															intentDate = Ext.util.Format
																	.date(
																			intentDate,
																			'Y-m-d')
															startDate = Ext.util.Format
																	.date(
																			startDate,
																			'Y-m-d')
															var days = ((new Date(
																	intentDate
																			.substring(
																					0,
																					4),
																	intentDate
																			.substring(
																					5,
																					intentDate
																							.lastIndexOf('-'))
																			- 1,
																	intentDate
																			.substring(
																					intentDate
																							.lastIndexOf('-')
																							+ 1,
																					intentDate.length)))
																	.getTime() - (new Date(
																	startDate
																			.substring(
																					0,
																					4),
																	startDate
																			.substring(
																					5,
																					startDate
																							.lastIndexOf('-'))
																			- 1,
																	startDate
																			.substring(
																					startDate
																							.lastIndexOf('-')
																							+ 1,
																					startDate.length)))
																	.getTime())
																	/ 1000
																	/ 60
																	/ 60 / 24
															sumAccrualRatenf
																	.setValue(nf
																			.getValue()
																			/ 360
																			* days)
														}
													} else if (null != dateModel
															&& dateModel == 'dateModel_365') {
														dayAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 365)
														if (startDate != ''
																&& intentDate != '') {
															intentDate = Ext.util.Format
																	.date(
																			intentDate,
																			'Y-m-d')
															startDate = Ext.util.Format
																	.date(
																			startDate,
																			'Y-m-d')
															var days = ((new Date(
																	intentDate
																			.substring(
																					0,
																					4),
																	intentDate
																			.substring(
																					5,
																					intentDate
																							.lastIndexOf('-'))
																			- 1,
																	intentDate
																			.substring(
																					intentDate
																							.lastIndexOf('-')
																							+ 1,
																					intentDate.length)))
																	.getTime() - (new Date(
																	startDate
																			.substring(
																					0,
																					4),
																	startDate
																			.substring(
																					5,
																					startDate
																							.lastIndexOf('-'))
																			- 1,
																	startDate
																			.substring(
																					startDate
																							.lastIndexOf('-')
																							+ 1,
																					startDate.length)))
																	.getTime())
																	/ 1000
																	/ 60
																	/ 60 / 24
															sumAccrualRatenf
																	.setValue(nf
																			.getValue()
																			/ 365
																			* days)
														}
													}
												}
											}
										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "月化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.financeServiceOfRate",
											readOnly : this.isAllReadOnly,
											decimalPrecision : 8,
											anchor : '100%',
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													yearAccrualRatenf
															.setValue(nf
																	.getValue()
																	* 12)
												
												    if (null != dateModel && dateModel == 'dateModel_360')
													{
													   dayAccrualRatenf
															.setValue(nf
																	.getValue()
																	/ 30)
														if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()
																		/ 30
																		* days)
													}	
												 }
											     else  if (null != dateModel && dateModel == 'dateModel_365')
													{
													   dayAccrualRatenf
															.setValue(nf
																	.getValue()*12
																	/ 365)
														if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()*12
																		/ 365
																		* days)
													}	
												 }
												}
											}

										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "日化利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											anchor : '100%',
											name : "slSmallloanProject.dayFinanceServiceOfRate",
											readOnly : this.isAllReadOnly,
											decimalPrecision : 8,
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.financeServiceOfRate')
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
													var sumAccrualRatenf = this
															.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()
													
													if (null != dateModel
															&& dateModel == 'dateModel_360') {
														yearAccrualRatenf
																.setValue(nf
																		.getValue()
																		* 360);
														accrualnf.setValue(nf
															.getValue()
															* 30)

													} else if (null != dateModel
															&& dateModel == 'dateModel_365') {
														yearAccrualRatenf
																.setValue(nf
																		.getValue()
																		* 365);
														accrualnf.setValue(nf
															.getValue()
															* 365/12)
													}
													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														sumAccrualRatenf
																.setValue(nf
																		.getValue()
																		* days)
													}
												}
											}
										}]
									}, {
										columnWidth : .045,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"
												}]
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "合计利率",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .27,
										layout : 'form',
										items : [{
											hideLabel : true,
											xtype : 'numberfield',
											name : "slSmallloanProject.sumFinanceServiceOfRate",
											readOnly : this.isAllReadOnly,
											decimalPrecision : 8,
											anchor : '100%',
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											listeners : {
												scope : this,
												'change' : function(nf) {
													var dateModel = this
															.getCmpByName('slSmallloanProject.dateMode')
															.getValue()
													var accrualnf = this
															.getCmpByName('slSmallloanProject.financeServiceOfRate')
													var yearAccrualRatenf = this
															.getCmpByName('slSmallloanProject.yearFinanceServiceOfRate')
													var dayAccrualRatenf = this
															.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate')
													var startDate = this
															.getCmpByName('slSmallloanProject.startDate')
															.getValue()
													var intentDate = this
															.getCmpByName('slSmallloanProject.intentDate')
															.getValue()

													if (startDate != ''
															&& intentDate != '') {
														intentDate = Ext.util.Format
																.date(
																		intentDate,
																		'Y-m-d')
														startDate = Ext.util.Format
																.date(
																		startDate,
																		'Y-m-d')
														var days = ((new Date(
																intentDate
																		.substring(
																				0,
																				4),
																intentDate
																		.substring(
																				5,
																				intentDate
																						.lastIndexOf('-'))
																		- 1,
																intentDate
																		.substring(
																				intentDate
																						.lastIndexOf('-')
																						+ 1,
																				intentDate.length)))
																.getTime() - (new Date(
																startDate
																		.substring(
																				0,
																				4),
																startDate
																		.substring(
																				5,
																				startDate
																						.lastIndexOf('-'))
																		- 1,
																startDate
																		.substring(
																				startDate
																						.lastIndexOf('-')
																						+ 1,
																				startDate.length)))
																.getTime())
																/ 1000
																/ 60
																/ 60 / 24
														var rate = nf
																.getValue()
																/ days
														dayAccrualRatenf
																.setValue(rate);
														if (null != dateModel
																&& dateModel == 'dateModel_360') {
															yearAccrualRatenf
																	.setValue(rate
																			* 360);
															accrualnf.setValue(rate
																* 30)

														} else if (null != dateModel
																&& dateModel == 'dateModel_365') {
															yearAccrualRatenf
																	.setValue(rate
																			* 365);
														    accrualnf.setValue(rate
																* 365/12)
														}
														
													}
												}
											}
										}]
									}, {
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
						}, {
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
									}, {
										columnWidth : .06,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
													fieldLabel : "贷款总额",
													labelSeparator : ''
												}]
									}, {
										columnWidth : .16,
										layout : 'form',
										items : [{
											xtype : "numberfield",
											name : "slSmallloanProject.breachRate",
											decimalPrecision : 3,
											readOnly : this.isAllReadOnly,
											hideLabel : true,
											fieldClass : 'field-align',
											style : {
												imeMode : 'disabled'
											},
											value : 0,
											anchor : "100%"
										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										labelAlgin : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"

												}]
									}, {
										columnWidth : .13,
										layout : 'form',
										labelWidth : 60,
										hidden : true,
										labelAlign : 'right',
										items : [{
											fieldLabel : "风险保证",
											xtype : "numberfield",
											fieldClass : 'field-align',
											name : "slSmallloanProject.riskRate",
											decimalPrecision : 3,
											allowBlank : false,
											style : {
												imeMode : 'disabled'
											},
											readOnly : this.isAllReadOnly,
											blankText : "风险保证不能为空，请正确填写!",
											anchor : "100%",
											value : 0,
											listeners : {
												afterrender : function(comp) {
													comp.on("keydown",
															function() {
															})
												}
											}

										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 20,
										hidden : true,
										labelAlign : 'left',
										items : [{
													fieldLabel : "%",
													labelSeparator : '',
													anchor : "100%"

												}]
									}, {
										columnWidth : .13,
										layout : 'form',
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
											fieldLabel : "逾期利率",
											xtype : "numberfield",
											fieldClass : 'field-align',
											name : "slSmallloanProject.overdueRateLoan",
											decimalPrecision : 3,
											allowBlank : false,
											style : {
												imeMode : 'disabled'
											},
											readOnly : this.isAllReadOnly,
											blankText : "逾	期费率不能为空，请正确填写!",
											anchor : "100%",
											value : 0,
											listeners : {
												afterrender : function(comp) {
													comp.on("keydown",
															function() {
															})
												}
											}

										}]
									}, {
										columnWidth : .03,
										layout : 'form',
										labelWidth : 35,
										labelAlign : 'left',
										items : [{
													fieldLabel : "%/日",
													labelSeparator : '',
													anchor : "100%"

												}]
									}, {
										columnWidth : .306,
										layout : 'form',
										labelWidth : 89,
										labelAlign : 'right',
										items : [{
											fieldLabel : '逾期罚息利率',
											xtype : "textfield",
											readOnly : this.isAllReadOnly,
											name : "slSmallloanProject.overdueRate",
											fieldClass : 'field-align',
											allowBlank : false,
											blankText : "逾期罚息利率不能为空，请正确填写!",
											decimalPrecision : 3,
											anchor : '100%',
											value : 0
										}]
									}, {
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
						}, {
							columnWidth : 1,
							hidden : true,
							layout : 'column',
							items : [{
								layout : 'form',
								hidden : false,
								labelWidth : 89,
								columnWidth : .3,
								items : [{
											xtype : 'textfield',
											fieldLabel : '余额托管费率',
											readOnly : this.isAllReadOnly,
											anchor : '100%',
											name : 'slSmallloanProject.balanceRate',
											value : '0'
										}]
							}, {
								layout : 'form',
								hidden : true,
								columnWidth : .03,
								items : [{
											style : 'margin-top:3px;margin-left:3px;',
											html : '‰'
										}]
							}, {
								layout : 'form',
								hidden : true,
								columnWidth : 1,// .67,
								buttonAlign : 'center',
								items : [{
									xtype : 'button',
									text : '贷款试算',
									iconCls : 'btn-search',
									width : 110,
									hidden : this.isHiddencalculateBtn,
									scope : this,
									handler : function() {
										var isHiddenbackBtn = this.isHiddenbackBtn;
										var projectMoney = this
												.getCmpByName("slSmallloanProject.projectMoney")
												.getValue();
										var startDate = this
												.getCmpByName("slSmallloanProject.startDate")
												.getValue();
										var payaccrualType = this
												.getCmpByName("slSmallloanProject.payaccrualType")
												.getValue();
										var dayOfEveryPeriod = this
												.getCmpByName("slSmallloanProject.dayOfEveryPeriod")
												.getValue();
										var payintentPeriod = this
												.getCmpByName("slSmallloanProject.payintentPeriod")
												.getValue();
										var isStartDatePay = this
												.getCmpByName("slSmallloanProject.isStartDatePay")
												.getValue();
										var payintentPerioDate = this
												.getCmpByName("slSmallloanProject.payintentPerioDate")
												.getValue();
										var intentDate = this
												.getCmpByName("slSmallloanProject.intentDate")
												.getValue();
										var accrual = this
												.getCmpByName("slSmallloanProject.accrual")
												.getValue();
										var managementConsultingOfRate = this
												.getCmpByName("slSmallloanProject.managementConsultingOfRate")
												.getValue();
										var accrualtype = this
												.getCmpByName("slSmallloanProject.accrualtype")
												.getValue();
										var isPreposePayAccrual = this
												.getCmpByName("slSmallloanProject.isPreposePayAccrual")
												.getValue();
										var overdueRate = this
												.getCmpByName("slSmallloanProject.overdueRate")
												.getValue();
										var overdueRateLoan = this
												.getCmpByName("slSmallloanProject.overdueRateLoan")
												.getValue();
										var isInterestByOneTime = this
												.getCmpByName("slSmallloanProject.isInterestByOneTime")
												.getValue();
										var yearAccrualRate = this
												.getCmpByName("slSmallloanProject.yearAccrualRate")
												.getValue();
										var dayAccrualRate = this
												.getCmpByName("slSmallloanProject.dayAccrualRate")
												.getValue();
										var sumAccrualRate = this
												.getCmpByName("slSmallloanProject.sumAccrualRate")
												.getValue();
										var yearManagementConsultingOfRate = this
												.getCmpByName("slSmallloanProject.yearManagementConsultingOfRate")
												.getValue();
										var dayManagementConsultingOfRate = this
												.getCmpByName("slSmallloanProject.dayManagementConsultingOfRate")
												.getValue();
										var sumManagementConsultingOfRate = this
												.getCmpByName("slSmallloanProject.sumManagementConsultingOfRate")
												.getValue();
										var yearFinanceServiceOfRate = this
												.getCmpByName("slSmallloanProject.yearFinanceServiceOfRate")
												.getValue();
										var dayFinanceServiceOfRate = this
												.getCmpByName("slSmallloanProject.dayFinanceServiceOfRate")
												.getValue();
										var sumFinanceServiceOfRate = this
												.getCmpByName("slSmallloanProject.sumFinanceServiceOfRate")
												.getValue();
										var accrualnew = this
												.getCmpByName("slSmallloanProject.accrualnew")
												.getValue();
										var financeServiceOfRate = this
												.getCmpByName("slSmallloanProject.financeServiceOfRate")
												.getValue();
										var dateMode = this
												.getCmpByName("slSmallloanProject.dateMode")
												.getValue();
										var breachRate = this
												.getCmpByName("slSmallloanProject.breachRate")
												.getValue();
										new calculateFundIntent({
											projectMoney : projectMoney,
											startDate : startDate,
											payaccrualType : payaccrualType,
											dayOfEveryPeriod : dayOfEveryPeriod,
											payintentPeriod : payintentPeriod,
											isStartDatePay : isStartDatePay,
											payintentPerioDate : payintentPerioDate,
											intentDate : intentDate,
											accrual : accrual,
											managementConsultingOfRate : managementConsultingOfRate,
											accrualtype : accrualtype,
											projectId : this.projectId,
											isPreposePayAccrual : isPreposePayAccrual,
											overdueRate : overdueRate,
											overdueRateLoan : overdueRateLoan,
											isInterestByOneTime : isInterestByOneTime,
											yearAccrualRate : yearAccrualRate,
											dayAccrualRate : dayAccrualRate,
											sumAccrualRate : sumAccrualRate,
											yearManagementConsultingOfRate : yearManagementConsultingOfRate,
											dayManagementConsultingOfRate : dayManagementConsultingOfRate,
											sumManagementConsultingOfRate : sumManagementConsultingOfRate,
											yearFinanceServiceOfRate : yearFinanceServiceOfRate,
											dayFinanceServiceOfRate : dayFinanceServiceOfRate,
											sumFinanceServiceOfRate : sumFinanceServiceOfRate,
											accrualnew : accrualnew,
											financeServiceOfRate : financeServiceOfRate,
											dateMode : dateMode,
											breachRate : breachRate,
											idDefinition1 : idDefinition1,
											objectfinace : this,
											isHiddenbackBtn : isHiddenbackBtn
										}).show();
									}

								}]
							}]
						},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 85,
							labelAlign : 'right',
							hidden : this.isHiddenRemark==false?false:true,
							items : [{
								xtype : 'textarea',
								fieldLabel : '修改备注',
								name : 'slSmallloanProject.remarks',
								readOnly : this.isAllReadOnly,
								anchor : '97%'
							}]
						}, {
							columnWidth : 1,
							hidden : true,
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
									var isHiddenbackBtn = this.isHiddenbackBtn;
									var projectMoney = this
											.getCmpByName("slSmallloanProject.projectMoney")
											.getValue();
									var startDate = this
											.getCmpByName("slSmallloanProject.startDate")
											.getValue();
									var payaccrualType = this
											.getCmpByName("slSmallloanProject.payaccrualType")
											.getValue();
									var dayOfEveryPeriod = this
											.getCmpByName("slSmallloanProject.dayOfEveryPeriod")
											.getValue();
									var payintentPeriod = this
											.getCmpByName("slSmallloanProject.payintentPeriod")
											.getValue();
									var isStartDatePay = this
											.getCmpByName("slSmallloanProject.isStartDatePay")
											.getValue();
									var payintentPerioDate = this
											.getCmpByName("slSmallloanProject.payintentPerioDate")
											.getValue();
									var intentDate = this
											.getCmpByName("slSmallloanProject.intentDate")
											.getValue();
									var accrual = this
											.getCmpByName("slSmallloanProject.accrual")
											.getValue();
									var managementConsultingOfRate = this
											.getCmpByName("slSmallloanProject.managementConsultingOfRate")
											.getValue();
									var accrualtype = this
											.getCmpByName("slSmallloanProject.accrualtype")
											.getValue();
									var isPreposePayAccrual = this
											.getCmpByName("slSmallloanProject.isPreposePayAccrual")
											.getValue();
									var overdueRate = this
											.getCmpByName("slSmallloanProject.overdueRate")
											.getValue();
									var overdueRateLoan = this
											.getCmpByName("slSmallloanProject.overdueRateLoan")
											.getValue();
									var isInterestByOneTime = this
											.getCmpByName("slSmallloanProject.isInterestByOneTime")
											.getValue();
									var yearAccrualRate = this
											.getCmpByName("slSmallloanProject.yearAccrualRate")
											.getValue();
									var dayAccrualRate = this
											.getCmpByName("slSmallloanProject.dayAccrualRate")
											.getValue();
									var sumAccrualRate = this
											.getCmpByName("slSmallloanProject.sumAccrualRate")
											.getValue();
									var yearManagementConsultingOfRate = this
											.getCmpByName("slSmallloanProject.yearManagementConsultingOfRate")
											.getValue();
									var dayManagementConsultingOfRate = this
											.getCmpByName("slSmallloanProject.dayManagementConsultingOfRate")
											.getValue();
									var sumManagementConsultingOfRate = this
											.getCmpByName("slSmallloanProject.sumManagementConsultingOfRate")
											.getValue();
									var yearFinanceServiceOfRate = this
											.getCmpByName("slSmallloanProject.yearFinanceServiceOfRate")
											.getValue();
									var dayFinanceServiceOfRate = this
											.getCmpByName("slSmallloanProject.dayFinanceServiceOfRate")
											.getValue();
									var sumFinanceServiceOfRate = this
											.getCmpByName("slSmallloanProject.sumFinanceServiceOfRate")
											.getValue();
									var accrualnew = this
											.getCmpByName("slSmallloanProject.accrualnew")
											.getValue();
									var financeServiceOfRate = this
											.getCmpByName("slSmallloanProject.financeServiceOfRate")
											.getValue();
									var dateMode = this
											.getCmpByName("slSmallloanProject.dateMode")
											.getValue();
									var breachRate = this
											.getCmpByName("slSmallloanProject.breachRate")
											.getValue();
									new calculateFundIntent({
										projectMoney : projectMoney,
										startDate : startDate,
										payaccrualType : payaccrualType,
										dayOfEveryPeriod : dayOfEveryPeriod,
										payintentPeriod : payintentPeriod,
										isStartDatePay : isStartDatePay,
										payintentPerioDate : payintentPerioDate,
										intentDate : intentDate,
										accrual : accrual,
										managementConsultingOfRate : managementConsultingOfRate,
										accrualtype : accrualtype,
										projectId : this.projectId,
										isPreposePayAccrual : isPreposePayAccrual,
										overdueRate : overdueRate,
										overdueRateLoan : overdueRateLoan,
										isInterestByOneTime : isInterestByOneTime,
										yearAccrualRate : yearAccrualRate,
										dayAccrualRate : dayAccrualRate,
										sumAccrualRate : sumAccrualRate,
										yearManagementConsultingOfRate : yearManagementConsultingOfRate,
										dayManagementConsultingOfRate : dayManagementConsultingOfRate,
										sumManagementConsultingOfRate : sumManagementConsultingOfRate,
										yearFinanceServiceOfRate : yearFinanceServiceOfRate,
										dayFinanceServiceOfRate : dayFinanceServiceOfRate,
										sumFinanceServiceOfRate : sumFinanceServiceOfRate,
										accrualnew : accrualnew,
										financeServiceOfRate : financeServiceOfRate,
										dateMode : dateMode,
										breachRate : breachRate,
										idDefinition1 : idDefinition1,
										objectfinace : this,
										isHiddenbackBtn : isHiddenbackBtn

									}).show();
								}

							}]
						}]
					}]
				});

	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});
ExtUD.Ext.NothopeProjectInfoFinancePanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	idDefinition : 'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition = this.projectId + this.idDefinition;
		var leftlabel = 115;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ExtUD.Ext.NothopeProjectInfoFinancePanel.superclass.constructor.call(
				this, {
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
								fieldLabel : "期望贷款金额",
								fieldClass : 'field-align',
								name : "projectMoney1",
								readOnly : this.isAllReadOnly,
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
									change : function(nf) {

										var value = nf.getValue();
										var index = value.indexOf(".");
										if (index <= 0) { // 如果第一次输入 没有进行格式化
											nf.setValue(Ext.util.Format.number(
													value, '0,000.00'))
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(value);
										} else {

											if (value.indexOf(",") <= 0) {
												var ke = Ext.util.Format
														.number(value,
																'0,000.00')
												nf.setValue(ke);
												this
														.getCmpByName("slSmallloanProject.projectMoney")
														.setValue(value);
											} else {
												var last = value
														.substring(index + 1,
																value.length);
												if (last == 0) {
													var temp = value.substring(
															0, index);
													temp = temp.replace(/,/g,
															"");
													this
															.getCmpByName("slSmallloanProject.projectMoney")
															.setValue(temp);
												} else {
													var temp = value.replace(
															/,/g, "");
													this
															.getCmpByName("slSmallloanProject.projectMoney")
															.setValue(temp);
												}
											}

										}
									}
								}
							}, {
								xtype : "hidden",
								name : "slSmallloanProject.projectMoney"
							}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元 ",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .33, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel,
							labelWidth : 50,
							items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'slSmallloanProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								anchor : "89%",
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
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : centerlabel + 21,
							items : [{
										fieldLabel : "放款日期",
										xtype : "datefield",
										style : {
											imeMode : 'disabled'
										},
										name : "slSmallloanProject.startDate",
										allowBlank : false,
										readOnly : this.isAllReadOnly,
										blankText : "放款日期不能为空，请正确填写!",
										anchor : "89%",
										format : 'Y-m-d'
									}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
										columnWidth : .13, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										items : [{
													fieldLabel : "计息方式 ",
													fieldClass : 'field-align',
													// html :
													// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
													anchor : "100%"
												}]
									}, {
										columnWidth : 0.1,
										labelWidth : 5,
										layout : 'form',
										items : [{
											xtype : 'radio',
											boxLabel : '等额本金',
											// fieldLabel : "计息方式",
											name : 'f1',
											id : "jixifs1" + this.idDefinition,
											inputValue : true,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixifs1"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.accrualtype')
																.setValue("sameprincipal");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq5"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(true);
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("monthPay");

														this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.setDisabled(false);
														this
																.getCmpByName('mqhkri1')
																.hide();
														this
																.getCmpByName('mqhkri')
																.show();
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
											boxLabel : '等额本息',
											anchor : "100%",
											name : 'f1',
											id : "jixifs2" + this.idDefinition,
											inputValue : false,
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixifs2"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.accrualtype')
																.setValue("sameprincipalandInterest");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq5"
																		+ this.idDefinition)
																.setDisabled(true);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setValue(true);
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setValue(false);
														this
																.getCmpByName('slSmallloanProject.payaccrualType')
																.setValue("monthPay");

														this
																.getCmpByName('slSmallloanProject.payintentPeriod')
																.setDisabled(false);
														this
																.getCmpByName('mqhkri1')
																.hide();
														this
																.getCmpByName('mqhkri')
																.show();
													}
												}
											}
										}]
									}, {
										columnWidth : 0.67,
										labelWidth : 5,
										layout : 'form',
										items : [{
											xtype : 'radio',
											boxLabel : '单利',
											name : 'f1',
											id : "jixifs3" + this.idDefinition,
											inputValue : false,
											checked : true,
											anchor : "100%",
											disabled : this.isAllReadOnly,
											listeners : {
												scope : this,
												check : function(obj, checked) {
													var flag = Ext
															.getCmp("jixifs3"
																	+ this.idDefinition)
															.getValue();
													if (flag == true) {
														this
																.getCmpByName('slSmallloanProject.accrualtype')
																.setValue("singleInterest");
														Ext
																.getCmp("jixizq1"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq2"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq3"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq4"
																		+ this.idDefinition)
																.setDisabled(false);
														Ext
																.getCmp("jixizq5"
																		+ this.idDefinition)
																.setDisabled(false);

													}
												}
											}
										}, {
											xtype : "hidden",
											name : "slSmallloanProject.accrualtype",
											value : "singleInterest"

										}]
									}]

						}, {
							columnWidth : .13, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							items : [{
										fieldLabel : "计息周期 ",
										fieldClass : 'field-align',
										// html :
										// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.1,
							labelWidth : 5,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '日',
								name : 'z1',
								id : "jixizq1" + this.idDefinition,
								inputValue : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq1"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.setValue("dayPay");

											Ext.getCmp("meiqihkrq1"
													+ this.idDefinition)
													.setDisabled(true);
											Ext.getCmp("meiqihkrq1"
													+ this.idDefinition)
													.setValue(false);
											Ext.getCmp("meiqihkrq2"
													+ this.idDefinition)
													.setDisabled(true);
											Ext.getCmp("meiqihkrq2"
													+ this.idDefinition)
													.setValue(true);

											this
													.getCmpByName('slSmallloanProject.isStartDatePay')
													.setValue("2");

										} else {

											Ext.getCmp("meiqihkrq1"
													+ this.idDefinition)
													.setDisabled(false);
											Ext.getCmp("meiqihkrq2"
													+ this.idDefinition)
													.setDisabled(false);
											if (Ext.getCmp("meiqihkrq1"
													+ this.idDefinition)
													.getValue() == true) {
												this
														.getCmpByName('slSmallloanProject.payintentPerioDate')
														.setDisabled(false);
											}
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
								boxLabel : '月',
								name : 'z1',
								id : "jixizq2" + this.idDefinition,
								inputValue : false,
								checked : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq2"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.setValue("monthPay");

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
								boxLabel : '季',
								name : 'z1',
								id : "jixizq3" + this.idDefinition,
								inputValue : false,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq3"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.setValue("seasonPay");

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
								boxLabel : '年',
								name : 'z1',
								id : "jixizq4" + this.idDefinition,
								inputValue : false,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq4"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.setValue("yearPay");

										}
									}
								}
							}]
						}, {
							columnWidth : 0.47,
							labelWidth : 5,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '一次性',
								name : 'z1',
								id : "jixizq5" + this.idDefinition,
								inputValue : false,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq5"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.setValue("ontTimeAccrual");

											this
													.getCmpByName('slSmallloanProject.payintentPeriod')
													.setValue(1);
											this
													.getCmpByName('slSmallloanProject.payintentPeriod')
													.setDisabled(true);

											this.getCmpByName('mqhkri').hide();
											this.getCmpByName('mqhkri1').show();

										} else {
											this
													.getCmpByName('slSmallloanProject.payintentPeriod')
													.setDisabled(false);

											this.getCmpByName('mqhkri1').hide();
											this.getCmpByName('mqhkri').show();

										}
									}
								}
							}, {
								xtype : "hidden",
								name : "slSmallloanProject.payaccrualType",
								value : "monthPay"

							}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
								columnWidth : .3, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
									fieldLabel : "贷款期限",
									xtype : "textfield",
									fieldClass : 'field-align',
									allowBlank : false,
									readOnly : this.isAllReadOnly,
									name : "slSmallloanProject.payintentPeriod",
									anchor : "100%"

								}]
							}, {
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : "期",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .45,
								name : "mqhkri",
								layout : "column",
								items : [{
									columnWidth : 0.278,
									labelWidth : 73,
									layout : 'form',
									items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1',
										id : "meiqihkrq1" + this.idDefinition,
										inputValue : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext
														.getCmp("meiqihkrq1"
																+ this.idDefinition)
														.getValue();
												if (flag == true) {
													this
															.getCmpByName('slSmallloanProject.isStartDatePay')
															.setValue("1");
													this
															.getCmpByName('slSmallloanProject.payintentPerioDate')
															.setDisabled(false);
												}
											}
										}

									}, {
										xtype : "hidden",
										name : "slSmallloanProject.isStartDatePay"

									}]
								}, {
									columnWidth : 0.132,
									labelWidth : 1,
									layout : 'form',
									items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										name : "slSmallloanProject.payintentPerioDate",
										xtype : 'combo',
										mode : 'local',
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "slSmallloanProject.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
								}, {
									columnWidth : 0.12,
									labelWidth : 45,
									layout : 'form',
									items : [{
												fieldLabel : "号还款",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.47,
									labelWidth : 10,
									layout : 'form',
									items : [{
										xtype : 'radio',
										boxLabel : '按实际放款日还款',
										name : 'q1',
										id : "meiqihkrq2" + this.idDefinition,
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext
														.getCmp("meiqihkrq2"
																+ this.idDefinition)
														.getValue();
												if (flag == true) {
													this
															.getCmpByName('slSmallloanProject.isStartDatePay')
															.setValue("2");
													this
															.getCmpByName('slSmallloanProject.payintentPerioDate')
															.setValue(null);
													this
															.getCmpByName('slSmallloanProject.payintentPerioDate')
															.setDisabled(true);
												}
											}
										}

									}]
								}]
							}, {
								columnWidth : .45, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 140,
								name : "mqhkri1",
								// hidden:true,
								// hideLabel :true,
								items : [{
									fieldLabel : "<font color='red'>*</font>还款日期",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "slSmallloanProject.intentDate",
									readOnly : this.isAllReadOnly,
									blankText : "期望还款日期不能为空，请正确填写!",
									anchor : "64.7%",
									format : 'Y-m-d'
								}]
							}, {
								columnWidth : .2, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 13,
								items : [{
									xtype : "checkbox",
									boxLabel : "是否前置付息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isPreposePayAccrualCheck",
									// value :true
									checked : this.record == null
											|| this.record.data.isPreposePayAccrual == 0
											? null
											: true
								}]
							}]
						}, {
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "numberfield",
										name : "slSmallloanProject.accrual",
										fieldClass : 'field-align',
										allowBlank : false,
										readOnly : this.isAllReadOnly,
										anchor : "100%",
										fieldLabel : "贷款利率"

									}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "%/期",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .29, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 140,
							items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.managementConsultingOfRate",
								allowBlank : false,
								fieldLabel : "管理咨询费率",
								decimalPrecision : 10,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "管理咨询费率不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
										var value = nf.getValue();
										if (value == null || value == "") {
											nf.setValue(0)
										}
									}
								}
							}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "%/期",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .26, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 126,
							items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.financeServiceOfRate",
								allowBlank : false,
								fieldLabel : "财务服务费率",
								decimalPrecision : 10,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "财务服务率不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
										var value = nf.getValue();
										if (value == null || value == "") {
											nf.setValue(0)
										}
									}
								}
							}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "%/期",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .17, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 50,
							items : [{
								xtype : "checkbox",
								name : "isAheadPay",
								disabled : this.isAllReadOnly,
								boxLabel : "是否允许提前还款",
								anchor : "100%",
								listeners : {
									scope : this,
									check : function(obj, isChecked) {
										if (isChecked) {
											var operateObj = this
													.getCmpByName('slSmallloanProject.aheadDayNum');
											Ext.apply(operateObj, {
														allowBlank : false,
														blankText : "提前还款通知天数不能为空!"
													});
											operateObj.setDisabled(false)
										} else {
											var operateObj = this
													.getCmpByName('slSmallloanProject.aheadDayNum');
											operateObj.setValue("");
											Ext.apply(operateObj, {
														allowBlank : true
													});
											operateObj.clearInvalid()
											operateObj.setDisabled(true)
										}
									}
								}
							}]
						}, {
							columnWidth : .13, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelWidth : 40,
							items : [{
										fieldLabel : "提前",
										xtype : "numberfield",
										fieldClass : 'field-align',
										name : "slSmallloanProject.aheadDayNum",
										// allowBlank : false,
										style : {
											imeMode : 'disabled'
										},
										readOnly : this.isAllReadOnly,
										blankText : "提前还款通知天数不能为空，请正确填写!",
										anchor : "100%"

									}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 50,
							items : [{
										fieldLabel : "天通知",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .29, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 140,
							items : [{
										xtype : "numberfield",
										name : "slSmallloanProject.breachRate",
										readOnly : this.isAllReadOnly,
										fieldClass : 'field-align',
										fieldLabel : "违约金比例",
										fieldClass : 'field-align',
										style : {
											imeMode : 'disabled'
										},
										anchor : "100%"
									}, {
										xtype : "hidden",
										name : "slSmallloanProject.projectStatus"
									}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "%",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .26, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 126,
							items : [{
										fieldLabel : "逾期费率",
										xtype : "numberfield",
										fieldClass : 'field-align',
										name : "slSmallloanProject.overdueRate",
										allowBlank : false,
										style : {
											imeMode : 'disabled'
										},
										readOnly : this.isAllReadOnly,
										blankText : "逾	期费率不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											afterrender : function(comp) {
												comp.on("keydown", function() {
														})
											}
										}

									}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "‰/天",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .26, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 126,
							items : [{
										fieldLabel : "逾期贷款利率",
										xtype : "numberfield",
										fieldClass : 'field-align',
										name : "slSmallloanProject.overdueRateLoan",
										allowBlank : false,
										style : {
											imeMode : 'disabled'
										},
										readOnly : this.isAllReadOnly,
										blankText : "逾	期费率不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											afterrender : function(comp) {
												comp.on("keydown", function() {
														})
											}
										}

									}]
						}, {
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 40,
							items : [{
										fieldLabel : "‰/天",
										labelSeparator : '',
										anchor : "100%"
									}]
						}

						]
					}]
				});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});
ExtUD.Ext.DeferApplyInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	isAllReadOnly : false,
	labelAlign : 'right',
	record : null,
	continuationMoney : 0,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		};
		if (typeof(_cfg.record) != "undefined") {
			this.record = _cfg.record;
		};
		if (typeof(_cfg.continuationMoney) != "undefined") {
			this.continuationMoney = _cfg.continuationMoney;
		};
		Ext.applyIf(this, _cfg);
		var leftlabel = 90;
		var rightlabel = 110;
		var continuationMoney = 0;
		continuationMoney = this.record != null
				? this.record.data.continuationMoney
				: this.continuationMoney;
		var formartMoney = Ext.util.Format
				.number(continuationMoney, '0,000.00');
		ExtUD.Ext.DeferApplyInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					labelWidth : leftlabel
				},
				items : [{
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textarea",
						name : "slSuperviseRecord.reason",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						value : this.record != null
								? this.record.data.reason
								: "",
						fieldLabel : '申请原因',
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						name : "continuationMoney1",
						allowBlank : false,
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						fieldClass : 'field-align',
						fieldLabel : '展期贷款金额',
						blankText : "展期贷款金额不能为空，请正确填写!",
						value : formartMoney,
						anchor : '100%',
						listeners : {
							change : function(nf) {

								var value = nf.getValue();
								var continuationMoneyObj = nf.nextSibling();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									continuationMoneyObj.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										continuationMoneyObj.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
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
					}, {
						xtype : "hidden",
						name : "slSuperviseRecord.continuationMoney",
						value : continuationMoney
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "slSuperviseRecord.continuationRate",
						allowBlank : false,
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						fieldClass : 'field-align',
						fieldLabel : '展期贷款利率',
						value : this.record != null
								? this.record.data.continuationRate
								: 0,
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "slSuperviseRecord.managementConsultingOfRate",
						allowBlank : false,
						fieldLabel : "管理咨询费率",
						decimalPrecision : 10,
						value : this.record != null
								? this.record.data.managementConsultingOfRate
								: 0,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						blankText : "管理咨询费率不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "slSuperviseRecord.financeServiceOfRate",
						allowBlank : false,
						fieldLabel : "财务服务费率",
						value : this.record != null
								? this.record.data.financeServiceOfRate
								: 0,
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						blankText : "财务服务费率不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "计息方式",
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'interestType',// 独立数据字典的
						readOnly : this.isAllReadOnly,
						value : this.record != null
								? this.record.data.accrualtype
								: null,
						allowBlank : false,
						hiddenName : "slSuperviseRecord.accrualtype",
						emptyText : "请选择",
						blankText : "计息方式不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .17, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 13,
					items : [{
						xtype : "checkbox",
						boxLabel : "是否前置付息",
						disabled : this.isAllReadOnly,
						anchor : "100%",
						name : "isPreposePayAccrualsupervise",
						// value :true
						checked : this.record == null
								|| this.record.data.isPreposePayAccrualsupervise == 0
								? null
								: true
					}]
				}, {
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 68,
					items : [{
						fieldLabel : "付息方式",
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'payType',// 独立数据字典的
						readOnly : this.isAllReadOnly,
						allowBlank : false,
						value : this.record != null
								? this.record.data.payaccrualType
								: null,
						hiddenName : "slSuperviseRecord.payaccrualType",
						emptyText : "请选择",
						blankText : "计息方式不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : 'datefield',
						fieldLabel : '展期开始时间',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						name : "slSuperviseRecord.startDate",
						value : this.record != null
								? new Date(this.record.data.startDate.replace(
										/-/g, "/")).format("Y-m-d")
								: null,
						// new
						// Date(record.data.startDate.replace(/-/g,"/")).format("Y-m-d")
						anchor : '100%',
						format : 'Y-m-d'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : 'datefield',
						fieldLabel : '展期结束时间',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						value : this.record != null
								? new Date(this.record.data.endDate.replace(
										/-/g, "/")).format("Y-m-d")
								: null,
						name : "slSuperviseRecord.endDate",
						anchor : '100%',
						format : 'Y-m-d'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textarea",
						name : "slSuperviseRecord.remark",
						readOnly : this.isAllReadOnly,
						value : this.record != null
								? this.record.data.remark
								: null,
						fieldLabel : '备注',
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}]
			}]
		});
	}
});

ExtUD.Ext.EarlyRepaymentPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	isAllReadOnly : false,
	labelAlign : 'right',
	record : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		};
		if (typeof(_cfg.surplusnotMoney) != "undefined") {
			this.surplusnotMoney = _cfg.surplusnotMoney;
		};
		if (typeof(_cfg.intentDate) != "undefined") {
			this.intentDate = _cfg.intentDate;
		};
		if (typeof(_cfg.payintentPeriod) != "undefined") {
			this.payintentPeriod = _cfg.payintentPeriod;
		};
		if (typeof(_cfg.accrualtype) != "undefined") {
			this.accrualtype = _cfg.accrualtype;
		};
		if (typeof(_cfg.record) != "undefined") {
			this.record = _cfg.record;
		};
		Ext.applyIf(this, _cfg);
		var leftlabelEarly = 125;
		var rightlabel = 110;
		var centerlabel = 110;
		var intentDate = this.intentDate;
		var surplusnotMoney = this.surplusnotMoney;
		var accrualtype = this.accrualtype;
		var payintentPeriod = this.payintentPeriod;
		var flagsingleInterest = false;
		var storepayintentPeriod = "";
		var obstorepayintentPeriod = [[null, null]];
		if (accrualtype == "sameprincipalandInterest"
				|| accrualtype == "sameprincipal") {
			storepayintentPeriod = "[";
			flagsingleInterest = true;
			for (var i = 1; i < payintentPeriod; i++) {
				storepayintentPeriod = storepayintentPeriod + "['第" + i
						+ "期', " + i + "],";
			}
			storepayintentPeriod = storepayintentPeriod.substring(0,
					storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			// storepayintentPeriod="[['1',1],['2',2]]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		}
		ExtUD.Ext.EarlyRepaymentPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					labelWidth : leftlabelEarly
				},
				items : [{
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabelEarly,
					items : [{
						xtype : "textarea",
						name : "slEarlyRepaymentRecord.reason",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						fieldLabel : '申请原因',
						value : this.record != null
								? this.record.data.reason
								: "",
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 125,
					items : [{
						xtype : "textfield",
						allowBlank : true,
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						value : this.record != null
								? this.record.data.earlyProjectMoney
								: 0,
						allowBlank : false,
						fieldClass : 'field-align',
						fieldLabel : '提前还款本金金额',
						blankText : "提前还款本金金额不能为空，请正确填写!",
						anchor : '100%',
						listeners : {
							change : function(nf) {

								var value = nf.getValue();
								var continuationMoneyObj = nf.nextSibling();
								var continuationMoneyObjintivalue = continuationMoneyObj
										.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									continuationMoneyObj.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										continuationMoneyObj.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										} else {
											var temp = value.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										}
									}

								}
								var continuationMoneyObj1 = nf.nextSibling()
										.nextSibling();
								if (continuationMoneyObj.getValue() > surplusnotMoney) {
									Ext.Msg.alert("", "不能大于未还本金金额");
									continuationMoneyObj1
											.setValue(surplusnotMoney);
									continuationMoneyObj.setValue(0);
									nf.setValue(0);
								} else {

									continuationMoneyObj1
											.setValue(Ext.util.Format
													.number(
															surplusnotMoney
																	- continuationMoneyObj
																			.getValue(),
															'0,000.00'));
								}
							}
						}
					}, {
						xtype : "hidden",
						value : 0,
						name : "slEarlyRepaymentRecord.earlyProjectMoney"
					}, {
						fieldLabel : '剩余本金金额',
						xtype : "textfield",
						allowBlank : true,
						anchor : '100%',
						readOnly : true,
						fieldClass : 'field-align',
						value : surplusnotMoney
					}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "100%"
							}, {
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : 'datefield',
						fieldLabel : '提前还款日期',
						readOnly : this.isAllReadOnly,
						hidden : flagsingleInterest,
						hideLabel : flagsingleInterest,
						name : "slEarlyRepaymentRecord.earlyDate",
						anchor : '100%',
						value : this.record != null ? Ext.util.Format.date(
								this.record.get('earlyDate'), 'Y-m-d') : "",
						format : 'Y-m-d'
					}, {
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : obstorepayintentPeriod
								}),
						triggerAction : "all",
						fieldLabel : '提前还款期数',
						readOnly : this.isAllReadOnly,
						hidden : !flagsingleInterest,
						hideLabel : !flagsingleInterest,
						hiddenName : "slEarlyRepaymentRecord.prepayintentPeriod",
						name : "slEarlyRepaymentRecord.prepayintentPeriod",
						anchor : '100%',
						value : this.record != null
								? this.record.data.prepayintentPeriod
								: null
					}, {
						xtype : 'datefield',
						fieldLabel : '剩余还款日期',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						value : this.record != null ? Ext.util.Format.date(
								intentDate, 'Y-m-d') : intentDate,
						name : "slEarlyRepaymentRecord.surplusEndDate",
						anchor : '100%',
						format : 'Y-m-d'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								labelSeparator : '',
								anchor : "100%"
							}, {
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabelEarly,
					items : [{
						fieldLabel : "计息方式",
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'interestType1',// 独立数据字典的
						// 计息方式
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						hiddenName : "slEarlyRepaymentRecord.accrualtype",
						emptyText : "请选择",
						value : this.record != null
								? this.record.data.accrualtype
								: null,
						blankText : "计息方式不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}]
				}, {
					columnWidth : .19, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .11, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 3,
					items : [{
						xtype : "checkbox",
						boxLabel : "前置付息",
						disabled : this.isAllReadOnly,
						anchor : "100%",
						value : this.record != null
								? this.record.data.isPreposePayAccrual
								: 0,
						name : "isPreposePayAccrualCheck"
					}]
				}, {
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						fieldLabel : "日期模型",
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'dateModel',// 独立数据字典的
						hiddenName : "slEarlyRepaymentRecord.dateMode",
						emptyText : "请选择",
						readOnly : this.isAllReadOnly,
						allowBlank : false,
						blankText : "日期模型不能为空，请正确填写!",
						anchor : "100%",
						value : this.record != null
								? this.record.data.dateMode
								: null,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}

					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabelEarly,
					items : [{
						// fieldLabel : '<img
						// src="'+__ctxPath+'/images/help.gif"
						// '+this.fuxiTip+'/>付息方式',
						fieldLabel : '<span class="helpfield" onclick=openHelpWindow("付息方式","PayInterest")><u>付息方式</u></span >',
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'payType',// 独立数据字典的 付息方式
						// nodeKey
						allowBlank : false,
						value : null,
						readOnly : this.isAllReadOnly,
						name : "slEarlyRepaymentRecord.payaccrualType",
						hiddenName : "slEarlyRepaymentRecord.payaccrualType",
						emptyText : "请选择",
						blankText : "付息方式不能为空，请正确填写!",
						anchor : "100%",
						value : this.record != null
								? this.record.data.payaccrualType
								: null,
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})

							},
							select : function(combox, record, index) {
								var v = record.data.dicKey;
								var operateObj = this
										.getCmpByName('payintentPeriodDays');
								var operateObj1 = this
										.getCmpByName('slEarlyRepaymentRecord.payintentPeriod');
								var operateObj2 = this
										.getCmpByName('payintentPeriodDaysbutton');
								if (v == "ownerPay") {
									operateObj1.setValue(null);
									operateObj1.setDisabled(false);
									// operateObj1.getEl().up('.x-form-item').setDisplayed(true);
									operateObj.setValue(null);
									operateObj2.setDisabled(false);
								} else {
									operateObj1.setDisabled(true);
									operateObj1.setValue(null);
									operateObj2.setDisabled(true);
									operateObj.setValue(null);
								}
							}
						}
					}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						fieldLabel : "还款期数",
						xtype : "textfield",
						fieldClass : 'field-align',
						name : "slEarlyRepaymentRecord.payintentPeriod",
						readOnly : this.isAllReadOnly,
						disabled : true,
						anchor : "100%",
						value : this.record != null
								? this.record.data.payintentPeriod
								: null,
						listeners : {
							scope : this,
							afterrender : function(comp) {
								comp.on("keydown", function() {
										})
							},
							change : function(nf, date) {

								if (nf.getValue() == 0) {
									nf.setValue(null);
									Ext.Msg.alert("", "还款期数不能为0");
									this.getCmpByName('payintentPeriodDays')
											.setValue(null);

								}
							}
						}

					}]
				}, {
					columnWidth : .18, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
								fieldLabel : "间隔天数",
								xtype : "textfield",
								fieldClass : 'field-align',
								name : "payintentPeriodDays",
								// allowBlank : false,
								style : {
									imeMode : 'disabled'
								},
								readOnly : true,
								anchor : "100%",
								listeners : {
									afterrender : function(comp) {
										comp.on("keydown", function() {
												})
									}
								}

							}]
				}, {
					columnWidth : .02,// 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						iconCls : 'btn-pred',
						scope : this,
						xtype : "button",
						disabled : true,
						anchor : "100%",
						name : "payintentPeriodDaysbutton",
						handler : function() {
							var payintentPeriod = this
									.getCmpByName('slEarlyRepaymentRecord.payintentPeriod')
									.getValue()

							if (null != this
									.getCmpByName('slEarlyRepaymentRecord.earlyDate')
									.getValue()
									&& this
											.getCmpByName('slEarlyRepaymentRecord.earlyDate')
											.getValue() != ""
									&& null != this
											.getCmpByName('slEarlyRepaymentRecord.surplusEndDate')
											.getValue()
									&& this
											.getCmpByName('slEarlyRepaymentRecord.surplusEndDate')
											.getValue() != "") {
								var startdate = Ext.util.Format
										.date(
												this
														.getCmpByName('slEarlyRepaymentRecord.earlyDate')
														.getValue(), 'Y-m-d');
								var enddate = Ext.util.Format
										.date(
												this
														.getCmpByName('slEarlyRepaymentRecord.surplusEndDate')
														.getValue(), 'Y-m-d');
								if (payintentPeriod != null
										&& payintentPeriod != ""
										&& payintentPeriod != 0) {
									this.getCmpByName('payintentPeriodDays')
											.setValue((DateDiff(enddate,
													startdate) + 1)
													/ payintentPeriod);

								} else {
									Ext.Msg.alert("", "还款期数不能为空");
								}
							} else {
								Ext.Msg.alert("", "提前还款日期不能为空");

							}

						}

					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabelEarly,
					items : [{
						xtype : "numberfield",
						name : "slEarlyRepaymentRecord.accrual",
						allowBlank : false,
						fieldLabel : "每期贷款利率",
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						blankText : "贷款利率不能为空，请正确填写!",
						anchor : "100%",
						value : this.record != null
								? this.record.data.accrual
								: ""
					}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "% ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "slEarlyRepaymentRecord.managementConsultingOfRate",
						fieldLabel : "每期咨询管理费率",
						value : this.record != null
								? this.record.data.managementConsultingOfRate
								: 0,
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " %",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabelEarly,
					items : [{
						xtype : "numberfield",
						name : "slEarlyRepaymentRecord.financeServiceOfRate",
						fieldLabel : "每期财务服务费率",
						value : this.record != null
								? this.record.data.financeServiceOfRate
								: 0,
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " %",
								labelSeparator : '',
								anchor : "100%"
							}]
				}
				// {
				// columnWidth : .4, // 该列在整行中所占的百分比
				// layout : "form", // 从上往下的布局
				// items : [{
				// xtype : 'fieldset',
				// layout : "column",
				// defaults : {
				// anchor : '100%',
				// columnWidth : 1,
				// isFormField : true,
				// labelWidth : 100
				// },
				// items : [
				// ]
				// }]
				//				        
				// }
				// ,{columnWidth : .4, // 该列在整行中所占的百分比
				// layout : "form", // 从上往下的布局
				// items : [{
				// xtype : 'fieldset',
				// layout : "column",
				// defaults : {
				// anchor : '100%',
				// columnWidth : 1,
				// isFormField : true,
				// labelWidth : 100},
				// items : [
				// ]
				// }]
				//				        
				// }

				]
			}]
		});
	}
});

ExtUD.Ext.AlterAccrualPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	isAllReadOnly : false,
	labelAlign : 'right',
	record : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		};
		if (typeof(_cfg.surplusnotMoney) != "undefined") {
			this.surplusnotMoney = _cfg.surplusnotMoney;
		};
		if (typeof(_cfg.intentDate) != "undefined") {
			this.intentDate = _cfg.intentDate;
		};
		if (typeof(_cfg.payintentPeriod) != "undefined") {
			this.payintentPeriod = _cfg.payintentPeriod;
		};
		if (typeof(_cfg.accrualtype) != "undefined") {
			this.accrualtype = _cfg.accrualtype;
		};
		Ext.applyIf(this, _cfg);
		var slAlterAccrualRecorddata = this.slAlterAccrualRecorddata;
		var leftlabelEarly = 125;
		var rightlabel = 110;
		var centerlabel = 110;
		var intentDate = this.intentDate;
		var surplusnotMoney = this.surplusnotMoney;
		var accrualtype = this.accrualtype;
		var payintentPeriod = this.payintentPeriod;
		var flagsingleInterest = false;
		var storepayintentPeriod = "";
		if (accrualtype == "sameprincipalandInterest"
				|| accrualtype == "sameprincipal") {
			storepayintentPeriod = "[";
			flagsingleInterest = true;
			for (var i = 1; i < payintentPeriod; i++) {
				storepayintentPeriod = storepayintentPeriod + "['第" + i
						+ "期', " + i + "],";
			}
			storepayintentPeriod = storepayintentPeriod.substring(0,
					storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			// storepayintentPeriod="[['1',1],['2',2]]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		}
		ExtUD.Ext.AlterAccrualPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					labelWidth : leftlabelEarly
				},
				items : [{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabelEarly,
					items : [{
						xtype : "textarea",
						name : "slAlterAccrualRecord.reason",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						fieldLabel : '申请原因',
						anchor : '95%',
						value : slAlterAccrualRecorddata.reason != null
								? slAlterAccrualRecorddata.reason
								: ""
					}]
				}, {
					columnWidth : .45, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 125,
					items : [{
						xtype : "textfield",
						allowBlank : true,
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						value : surplusnotMoney,
						allowBlank : false,
						fieldClass : 'field-align',
						fieldLabel : '剩余本金金额',
						anchor : '100%',
						listeners : {
							change : function(nf) {

								var value = nf.getValue();
								var continuationMoneyObj = nf.nextSibling();
								var continuationMoneyObjintivalue = continuationMoneyObj
										.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									continuationMoneyObj.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										continuationMoneyObj.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										} else {
											var temp = value.replace(/,/g, "");
											continuationMoneyObj.setValue(temp);
										}
									}

								}
								var continuationMoneyObj1 = nf.nextSibling()
										.nextSibling();
								if (continuationMoneyObj.getValue() > surplusnotMoney) {
									Ext.Msg.alert("", "不能大于未还本金金额");
									continuationMoneyObj1
											.setValue(surplusnotMoney);
									continuationMoneyObj.setValue(0);
									nf.setValue(0);
								} else {

									continuationMoneyObj1
											.setValue(Ext.util.Format
													.number(
															surplusnotMoney
																	- continuationMoneyObj
																			.getValue(),
															'0,000.00'));
								}
							}
						}
					}, {
						xtype : "hidden",
						value : surplusnotMoney,
						name : "slAlterAccrualRecord.alterProjectMoney"
					}, {
						xtype : 'datefield',
						fieldLabel : '变更开始日期',
						readOnly : this.isAllReadOnly,
						hidden : flagsingleInterest,
						hideLabel : flagsingleInterest,
						name : "slAlterAccrualRecord.alelrtDate",
						anchor : '100%',
						value : slAlterAccrualRecorddata.alelrtDate != null
								? Ext.util.Format.date(
										slAlterAccrualRecorddata.alelrtDate,
										'y-m-d')
								: "",
						format : 'Y-m-d'
					}, {
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : obstorepayintentPeriod
								}),
						triggerAction : "all",
						fieldLabel : '变更开始还款期数',
						readOnly : this.isAllReadOnly,
						hidden : !flagsingleInterest,
						hideLabel : !flagsingleInterest,
						hiddenName : "slAlterAccrualRecord.alterpayintentPeriod",
						name : "slAlterAccrualRecord.alterpayintentPeriod",
						anchor : '100%',
						format : 'Y-m-d'
					}, {
						fieldLabel : "计息方式",
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'interestType1',// 独立数据字典的
						// 计息方式
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						hiddenName : "slAlterAccrualRecord.accrualtype",
						emptyText : "请选择",
						value : slAlterAccrualRecorddata.accrualtype,
						blankText : "计息方式不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})
							}
						}
					}, {
						// fieldLabel : '<img
						// src="'+__ctxPath+'/images/help.gif"
						// '+this.fuxiTip+'/>付息方式',
						fieldLabel : '<span class="helpfield" onclick=openHelpWindow("付息方式","PayInterest")><u>付息方式</u></span >',
						xtype : 'dicIndepCombo',
						editable : true,
						lazyInit : false,
						nodeKey : 'payType',// 独立数据字典的 付息方式
						// nodeKey
						allowBlank : false,
						value : slAlterAccrualRecorddata.payaccrualType,
						readOnly : this.isAllReadOnly,
						name : "slAlterAccrualRecord.payaccrualType",
						hiddenName : "slAlterAccrualRecord.payaccrualType",
						emptyText : "请选择",
						blankText : "付息方式不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
											combox.clearInvalid();
										})

							},
							select : function(combox, record, index) {
								var v = record.data.dicKey;
								var operateObj = this
										.getCmpByName('payintentPeriodDays');
								var operateObj1 = this
										.getCmpByName('slAlterAccrualRecord.payintentPeriod');
								var operateObj2 = this
										.getCmpByName('payintentPeriodDaysbutton');
								if (v == "ownerPay") {
									operateObj1.setValue(null);
									operateObj1.setDisabled(false);
									// operateObj1.getEl().up('.x-form-item').setDisplayed(true);
									operateObj.setValue(null);
									operateObj2.setDisabled(false);
								} else {
									operateObj1.setDisabled(true);
									operateObj1.setValue(null);
									operateObj2.setDisabled(true);
									operateObj.setValue(null);
								}
							}
						}
					}, {
						xtype : "numberfield",
						name : "slAlterAccrualRecord.accrual",
						allowBlank : false,
						value : slAlterAccrualRecorddata.accrual,
						fieldLabel : "每期贷款利率",
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						blankText : "贷款利率不能为空，请正确填写!",
						anchor : "100%"
					}, {
						xtype : "numberfield",
						name : "slAlterAccrualRecord.financeServiceOfRate",
						fieldLabel : "每期财务服务费率",
						value : slAlterAccrualRecorddata.financeServiceOfRate,
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .55, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .55, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 220,
					// align:'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '变更结束日期',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						value : intentDate,
						name : "slAlterAccrualRecord.surplusEndDate",
						anchor : '90%',
						value : slAlterAccrualRecorddata.surplusEndDate != null
								? Ext.util.Format
										.date(
												slAlterAccrualRecorddata.surplusEndDate,
												'Y-m-d')
								: "",
						format : 'Y-m-d'
					}]
				}, {
					columnWidth : .19, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .11, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 3,
					items : [{
						xtype : "checkbox",
						boxLabel : "前置付息",
						disabled : this.isAllReadOnly,
						checked : slAlterAccrualRecorddata.isPreposePayAccrual == 1
								? true
								: false,
						anchor : "100%",
						name : "isPreposePayAccrualCheck"
					}]
				}, {
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
								fieldLabel : "日期模型",
								xtype : 'dicIndepCombo',
								editable : true,
								lazyInit : false,
								nodeKey : 'dateModel',// 独立数据字典的
								hiddenName : "slAlterAccrualRecord.dateMode",
								value : slAlterAccrualRecorddata.dateMode,
								emptyText : "请选择",
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								blankText : "日期模型不能为空，请正确填写!",
								anchor : "100%",
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
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						fieldLabel : "还款期数",
						xtype : "textfield",
						fieldClass : 'field-align',
						name : "slAlterAccrualRecord.payintentPeriod",
						readOnly : this.isAllReadOnly,
						disabled : true,
						anchor : "100%",
						listeners : {
							scope : this,
							afterrender : function(comp) {
								comp.on("keydown", function() {
										})
							},
							change : function(nf, date) {

								if (nf.getValue() == 0) {
									nf.setValue(null);
									Ext.Msg.alert("", "还款期数不能为0");
									this.getCmpByName('payintentPeriodDays')
											.setValue(null);

								}
							}
						}

					}]
				}, {
					columnWidth : .18, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
								fieldLabel : "间隔天数",
								xtype : "textfield",
								fieldClass : 'field-align',
								name : "payintentPeriodDays",
								// allowBlank : false,
								style : {
									imeMode : 'disabled'
								},
								readOnly : true,
								anchor : "100%",
								listeners : {
									afterrender : function(comp) {
										comp.on("keydown", function() {
												})
									}
								}

							}]
				}, {
					columnWidth : .02,// 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						iconCls : 'btn-pred',
						scope : this,
						xtype : "button",
						disabled : true,
						anchor : "100%",
						name : "payintentPeriodDaysbutton",
						handler : function() {
							var payintentPeriod = this
									.getCmpByName('slAlterAccrualRecord.payintentPeriod')
									.getValue()

							if (null != this
									.getCmpByName('slAlterAccrualRecord.alelrtDate')
									.getValue()
									&& this
											.getCmpByName('slAlterAccrualRecord.alelrtDate')
											.getValue() != ""
									&& null != this
											.getCmpByName('slAlterAccrualRecord.surplusEndDate')
											.getValue()
									&& this
											.getCmpByName('slAlterAccrualRecord.surplusEndDate')
											.getValue() != "") {
								var startdate = Ext.util.Format
										.date(
												this
														.getCmpByName('slAlterAccrualRecord.alelrtDate')
														.getValue(), 'Y-m-d');
								var enddate = Ext.util.Format
										.date(
												this
														.getCmpByName('slAlterAccrualRecord.surplusEndDate')
														.getValue(), 'Y-m-d');
								if (payintentPeriod != null
										&& payintentPeriod != ""
										&& payintentPeriod != 0) {
									this.getCmpByName('payintentPeriodDays')
											.setValue((DateDiff(enddate,
													startdate) + 1)
													/ payintentPeriod);

								} else {
									Ext.Msg.alert("", "还款期数不能为空");
								}
							} else {
								Ext.Msg.alert("", "变更开始日期不能为空");

							}

						}

					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "slAlterAccrualRecord.managementConsultingOfRate",
						fieldLabel : "每期咨询管理费率",
						value : slAlterAccrualRecorddata.managementConsultingOfRate,
						decimalPrecision : 10,
						fieldClass : 'field-align',
						style : {
							imeMode : 'disabled'
						},
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						listeners : {
							scope : this,
							change : function(nf) {
								var value = nf.getValue();
								if (value == null || value == "") {
									nf.setValue(0)
								}
							}
						}
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "% ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}

				]
			}]
		});
	}
});

ExtUD.Ext.PersonInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : false,
	resetProject : false,

	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.resetProject) != "undefined") {
			this.resetProject = _cfg.resetProject
		}
		var anchor = "100%";
		Ext.applyIf(this, _cfg);
		var resetProject = this.resetProject
		ExtUD.Ext.PersonInfo.superclass.constructor.call(this, {
			items : [{
				layout : "column", // 定义该元素为布局为列布局方式
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				border : false,
				scope : this,
				bodyStype : 'padding-left:10px',
				items : [{
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'hidden',
								name : 'investPerson.perId',
								value : 0
							}, {
								xtype : 'combo',
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "客户姓名",
								name : "investPerson.perName",
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								blankText : "客户名称不能为空，请正确填写!",
								anchor : "100%",
								onTriggerClick : function() {
									if (resetProject == true) {
										ressetProjuect(Ext
												.getCmp('createNewSLFunctionForm'));
									}
									var op = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;// fromPanel
									var selectPerson = function(obj) {
										op.getCmpByName('investPerson.perId')
												.setValue("");
										op.getCmpByName('investPerson.perName')
												.setValue("");
										op.getCmpByName('investPerson.perSex')
												.setValue("");
										op
												.getCmpByName('investPerson.cardType')
												.setValue("");
										op
												.getCmpByName('investPerson.cardNumber')
												.setValue("");
										op
												.getCmpByName('investPerson.phoneNumber')
												.setValue("");
										op
												.getCmpByName('investPerson.perBirthday')
												.setValue("");
										op
												.getCmpByName('investPerson.postAddress')
												.setValue("");
										op.getCmpByName('investPerson.perId')
												.setValue(obj.perId);
										op.getCmpByName('investPerson.perName')
												.setValue(obj.perName);
										op.getCmpByName('investPerson.perSex')
												.setValue(obj.perSex);
										op
												.getCmpByName('investPerson.cardType')
												.setValue(obj.cardType);
										op
												.getCmpByName('investPerson.cardNumber')
												.setValue(obj.cardNumber);
										op
												.getCmpByName('investPerson.phoneNumber')
												.setValue(obj.phoneNumber);
										op
												.getCmpByName('investPerson.perBirthday')
												.setValue(obj.perBirthday);
										op
												.getCmpByName('investPerson.postAddress')
												.setValue(obj.postAddress);
										op.getCmpByName('investPerson.areaId')
												.setValue(obj.areaId);
										op.getCmpByName('areaName')
												.setValue(obj.areaText);
										op.getCmpByName('investPerson.remarks')
												.setValue(obj.remarks);

										op
												.getCmpByName('investPerson.linkmanName')
												.setValue(obj.linkmanName);
										op
												.getCmpByName('investPerson.linkmanPhone')
												.setValue(obj.linkmanPhone);
										op
												.getCmpByName('investPerson.filiation')
												.setValue(obj.filiation);
										op
												.getCmpByName('investPerson.customerLevel')
												.setValue(obj.customerLevel);
										op
												.getCmpByName('investPerson.perEmail')
												.setValue(obj.perEmail);

										if (op
												.getCmpByName('investPerson.cardType')
												.getValue() == 309) {
											if (validateIdCard(op
													.getCmpByName('investPerson.cardNumber')
													.getValue()) == 1) {
												Ext.ux.Toast.msg('身份证号码验证',
														'证件号码不正确,请仔细核对')
											} else if (validateIdCard(op
													.getCmpByName('investPerson.cardNumber')
													.getValue()) == 2) {
												Ext.ux.Toast.msg('身份证号码验证',
														'证件号码地区不正确,请仔细核对')

											} else if (validateIdCard(op
													.getCmpByName('investPerson.cardNumber')
													.getValue()) == 3) {
												Ext.ux.Toast.msg('身份证号码验证',
														'证件号码生日日期不正确,请仔细核对')
											}
										}
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/common/getCreditBankEnterpriseBank.do?id='
													+ obj.perId,
											method : 'POST',
											scope : this,
											success : function(response,
													request) {
												var obj = Ext.util.JSON
														.decode(response.responseText);
												op
														.getCmpByName('enterpriseBank.id')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.id')
														.setValue(obj.data.id);
												var openType = obj.data.openType;
												op
														.getCmpByName('enterpriseBank.openType')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.openType')
														.setValue(openType);
												var accountType = op
														.getCmpByName('enterpriseBank.accountType');
												accountType.enable();
												var arrStore = null;
												if (openType == 0) {
													op
															.getCmpByName('enterpriseBank.openType')
															.setRawValue('个人');
													arrStore = new Ext.data.SimpleStore(
															{
																fields : [
																		"name",
																		"id"],
																data : [[
																		"个人储蓄户",
																		"0"]]
															});
												} else if (openType == 1) {
													op
															.getCmpByName('enterpriseBank.openType')
															.setRawValue('企业');
													arrStore = new Ext.data.SimpleStore(
															{
																fields : [
																		"name",
																		"id"],
																data : [
																		["基本户",
																				"1"],
																		["一般户",
																				"2"]]
															});
												}
												accountType.clearValue();
												accountType.store = arrStore;
												accountType
														.setValue(obj.data.accountType);
												if (obj.data.accountType == 0) {
													accountType
															.setRawValue("个人储蓄户");
												} else if (obj.data.accountType == 1) {
													accountType
															.setRawValue("基本户");
												} else {
													accountType
															.setRawValue("一般户");
												}
												op
														.getCmpByName('enterpriseBank.openCurrency')
														.setValue(obj.data.openCurrency);
												op
														.getCmpByName('enterpriseBank.bankid')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.bankid')
														.setValue(obj.data.bankid);
												op
														.getCmpByName('enterpriseBank.bankOutletsId')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.bankOutletsId')
														.setValue(obj.data.bankOutletsId);
												op
														.getCmpByName('enterpriseBank.areaId')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.areaId')
														.setValue(obj.data.areaId);
												op
														.getCmpByName('enterpriseBank.areaName')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.areaName')
														.setValue(obj.data.areaName);
												op
														.getCmpByName('enterpriseBank.name')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.name')
														.setValue(obj.data.name);
												op
														.getCmpByName('enterpriseBank.accountnum')
														.setValue("");
												op
														.getCmpByName('enterpriseBank.accountnum')
														.setValue(obj.data.accountnum);
											}
										});
									}
									selectInvestPerson(selectPerson);
								},
								resizable : true,
								mode : 'romote',
								editable : true,
								lazyInit : false,
								typeAhead : true,
								minChars : 1,
								store : new Ext.data.JsonStore({}),
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								listeners : {
									scope : this,
									'select' : function(combo, record, index) {
										selectCusCombo(record);
										// var
										// obj1=Ext.getCmp('createNewSLFunctionForm');
										// var
										// projectNameObject=obj1.getCmpByName('projectName');
										// if(projectNameObject.getValue().trim()!=""){
										// projectNameObject.setValue("");
										// projectNameObject.clearInvalid();
										// }
										ressetProjuect();// 用来置空贷款申请的项目名称
									},
									'change' : function(combox) {
										ressetProjuect();// 用来置空贷款申请的项目名称
									},
									'blur' : function(f) {
									}
								}

							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'sex_key',
						hiddenName : 'investPerson.perSex',
						fieldLabel : "性别",
						allowBlank : false,
						editable : true,
						blankText : "性别不能为空，请正确填写!",
						readOnly : this.isAllReadOnly,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
							},
							select : function(combo, record, index) {
								var display = Ext.getCmp('displayProfilePhoto');
								if (combo.value == '313') {// 312
									display.body
											.update('<img src="'
													+ __ctxPath
													+ '/images/default_image_female.jpg" />');
								} else {
									display.body
											.update('<img src="'
													+ __ctxPath
													+ '/images/default_image_male.jpg" />');
								}
							}
						}
					}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '手机号码',
								name : 'investPerson.phoneNumber',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								regex : /^[1][34578][0-9]{9}$/,
								regexText : '手机号码格式不正确'
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'card_type_key',
						hiddenName : "investPerson.cardType",
						itemName : '证件类型', // xx代表分类名称
						fieldLabel : "证件类型",
						allowBlank : false,
						editable : true,
						readOnly : this.isAllReadOnly,
						blankText : "证件类型不能为空，请正确填写!",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == ''
											|| combox.getValue() == null) {
										combox
												.setValue(st.getAt(0).data.itemId);
										combox.clearInvalid();
									} else {
										combox.setValue(combox.getValue());
										combox.clearInvalid();
									}
								})
							}
						}
					}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : 'textfield',
						fieldLabel : '证件号码',
						name : 'investPerson.cardNumber',
						allowBlank : false,
						blankText : '证件号码为必填内容',
						readOnly : this.isAllReadOnly,
						value : personData == null
								? null
								: personData.cardnumber,
						listeners : {
							scope : this,
							'beforerender' : function(com) {
								if (this.getCmpByName('investPerson.cardType')
										.getValue() == 309) {
									if (validateIdCard(com.getValue()) == 1) {
										Ext.ux.Toast.msg('身份证号码验证',
												'证件号码不正确,请仔细核对')
									} else if (validateIdCard(com.getValue()) == 2) {
										Ext.ux.Toast.msg('身份证号码验证',
												'证件号码地区不正确,请仔细核对')

									} else if (validateIdCard(com.getValue()) == 3) {
										Ext.ux.Toast.msg('身份证号码验证',
												'证件号码生日日期不正确,请仔细核对')
									}
								}
							},
							'blur' : function(f) {
								if (this.getCmpByName('investPerson.cardType')
										.getValue() == 309) {
									if (validateIdCard(f.getValue()) == 1) {
										Ext.Msg.alert('身份证号码验证',
												'证件号码不正确,请仔细核对')
										return;
									} else if (validateIdCard(f.getValue()) == 2) {
										Ext.Msg.alert('身份证号码验证',
												'证件号码地区不正确,请仔细核对')
										return;
									} else if (validateIdCard(f.getValue()) == 3) {
										Ext.Msg.alert('身份证号码验证',
												'证件号码生日日期不正确,请仔细核对')
										return;
									}
								}
								var penal = this
										.getCmpByName("investPerson.perBirthday");
								var cardNumber = f.getValue();
								var personId = 0;
								Ext.Ajax.request({
									url : __ctxPath
											+ '/customer/verificationPersonInvestPerson.do',
									method : 'POST',
									scope : this,
									params : {
										cardNum : cardNumber,
										personId : personId
									},
									success : function(response, request) {
										var obj = Ext.util.JSON
												.decode(response.responseText);
										if (obj.msg == false) {
											Ext.ux.Toast.msg('操作信息',
													"该证件号码已存在，请重新输入");
											f.setValue("");
										} else {
											// 拆分身份证号码 ，拿出出生年月日
											var brithday = cardNumber.substr(6,
													8);
											var formatBrithday = brithday
													.substr(0, 4)
													+ "-"
													+ brithday.substr(4, 2)
													+ "-"
													+ brithday.substr(6, 2);
											penal.setValue(formatBrithday)
										}
									}
								});
							}
						}
					}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								fieldLabel : '出生日期',
								readOnly : this.isAllReadOnly,
								name : 'investPerson.perBirthday',
								xtype : 'datefield',
								anchor : "100%",
								format : 'Y-m-d'
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								fieldLabel : '联系人姓名',
								readOnly : this.isAllReadOnly,
								name : 'investPerson.linkmanName',
								xtype : 'textfield',
								anchor : "100%"
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								fieldLabel : '联系人电话',
								readOnly : this.isAllReadOnly,
								name : 'investPerson.linkmanPhone',
								xtype : 'textfield',
								anchor : "100%"
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								fieldLabel : '关系',
								readOnly : this.isAllReadOnly,
								name : 'investPerson.filiation',
								xtype : 'textfield',
								anchor : "100%"
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : "dickeycombo",
								editable : false,
								hiddenName : "investPerson.customerLevel",
								nodeKey : 'finaing_customerLevel', // xx代表分类名称
								fieldLabel : "客户级别",
								readOnly : this.isAllReadOnly,
								anchor : '100%',
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
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								name : 'investPerson.areaId',
								xtype : 'hidden'
							}, {
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "areaName",
								editable : false,
								fieldLabel : "所在区域",
								blankText : "所在区域不能为空，请正确填写!",
								allowBlank : false,
								anchor : "100%",
								readOnly : this.isAllReadOnly,

								onTriggerClick : function() {
									var com = this
									var selectBankLinkMan = function(array) {
										var str = "";
										var idStr = ""
										for (var i = array.length - 1; i >= 0; i--) {
											str = str + array[i].text + "-"
											idStr = idStr + array[i].id + ","
										}
										if (str != "") {
											str = str.substring(0, str.length
															- 1);
										}
										if (idStr != "") {
											idStr = idStr.substring(0,
													idStr.length - 1)
										}
										com.previousSibling().setValue(idStr)
										com.setValue(str);
									};
									selectDictionary('area', selectBankLinkMan);
								}
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '邮箱',
								readOnly : this.isAllReadOnly,
								name : 'investPerson.perEmail'
							}]
				}, {
					columnWidth : .84,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '通讯地址',
								// allowBlank : false,
								readOnly : this.isAllReadOnly,
								name : 'investPerson.postAddress'
							}]
				}, {
					columnWidth : .84,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'textarea',
								fieldLabel : '备注',
								readOnly : this.isAllReadOnly,
								name : 'investPerson.remarks'
							}]
				}]
			}]

		});
	}
})
ExtUD.Ext.PersonAccountInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	border : false,
	autoHeight : true,
	labelAlign : 'right',
	projectId : null,
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		var anchor = "100%";
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.PersonAccountInfo.superclass.constructor.call(this, {
			items : [{
				layout : 'column',
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
					columnWidth : .28,
					layout : 'form',
					items : [{
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						readOnly : this.isAllReadOnly,
						width : 70,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["个人", "0"], ["公司", "1"]]
								}),
						triggerAction : "all",
						hiddenName : "enterpriseBank.openType",
						fieldLabel : '开户类型',
						anchor : anchor,
						allowBlank : true,
						name : 'enterpriseBank.openType',
						listeners : {
							scope : this,
							select : function(combox, record, index) {
								var v = record.data.id;
								var formPanel = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
								var obj = formPanel
										.getCmpByName('enterpriseBank.accountType');
								obj.enable();
								var arrStore = null;
								if (v == 0) {
									arrStore = new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人储蓄户", "0"]]
											});
								} else {
									arrStore = new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["基本户", "1"],
														["一般户", "2"]]
											});
								}
								obj.clearValue();
								obj.store = arrStore;
								obj.view.setStore(arrStore);
							}
						}
					}]
				}, {
					columnWidth : .28,
					layout : 'form',
					items : [{
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								readOnly : this.isAllReadOnly,
								width : 70,
								triggerAction : "all",
								hiddenName : "enterpriseBank.accountType",
								fieldLabel : '账户类型',
								anchor : anchor,
								allowBlank : true,
								name : 'enterpriseBank.accountType'
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'radiogroup',
								fieldLabel : '银行开户类别',
								name : 'enterpriseBank.openCurrency',
								items : [{
											boxLabel : '本币开户',
											name : 'enterpriseBank.openCurrency',
											inputValue : "0",
											disabled : this.isAllReadOnly,
											checked : true
										}, {
											boxLabel : '外币开户',
											name : 'enterpriseBank.openCurrency',
											inputValue : "1",
											disabled : this.isAllReadOnly,
											checked : false
										}]
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					items : [{
						fieldLabel : "银行名称",
						xtype : "combo",
						displayField : 'itemName',
						valueField : 'itemId',
						allowBlank : true,
						triggerAction : 'all',
						readOnly : this.isAllReadOnly,
						store : new Ext.data.ArrayStore({
							url : __ctxPath
									+ '/creditFlow/common/getBankListCsBank.do',
							fields : ['itemId', 'itemName'],
							autoLoad : true
						}),
						mode : 'remote',
						hiddenName : "enterpriseBank.bankid",
						editable : false,
						blankText : "银行名称不能为空，请正确填写!",
						anchor : anchor,
						readOnly : this.isAllReadOnly,
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox.getValue());
										})
								combox.clearInvalid();
							}
						}
					}]
				}, {
					columnWidth : .28,
					layout : 'form',
					items : [{
								fieldLabel : "网点名称",
								name : 'enterpriseBank.bankOutletsName',
								xtype : 'textfield',
								allowBlank : true,
								readOnly : this.isAllReadOnly,
								anchor : anchor
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					items : [{
								name : 'enterpriseBank.areaId',
								xtype : 'hidden'
							}, {
								name : 'enterpriseBank.areaName',
								hiddenName : 'enterpriseBank.areaName',
								fieldLabel : '开户地区',
								anchor : anchor,
								allowBlank : true,
								xtype : 'trigger',
								readOnly : this.isAllReadOnly,
								triggerClass : 'x-form-search-trigger',
								editable : false,
								scope : this,
								onTriggerClick : function() {
									var com = this
									var selectBankLinkMan = function(array) {
										var str = "";
										var idStr = ""
										for (var i = array.length - 1; i >= 0; i--) {
											str = str + array[i].text + "-"
											idStr = idStr + array[i].id + ","
										}
										if (str != "") {
											str = str.substring(0, str.length
															- 1);
										}
										if (idStr != "") {
											idStr = idStr.substring(0,
													idStr.length - 1)
										}
										com.previousSibling().setValue(idStr)
										com.setValue(str);
									};
									// areaTree(selectBankLinkMan,true)
									selectDictionary('area', selectBankLinkMan);
								}
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					items : [{
								fieldLabel : '开户名称',
								name : 'enterpriseBank.name',
								xtype : 'textfield',
								anchor : anchor,
								readOnly : this.isAllReadOnly,
								allowBlank : true
							}]
				}, {
					columnWidth : .28,
					layout : 'form',
					items : [{
								fieldLabel : '账号',
								name : 'enterpriseBank.accountnum',
								maxLength : 100,
								xtype : 'textfield',
								readOnly : this.isAllReadOnly,
								anchor : anchor,
								allowBlank : true
							}]
				}, {
					xtype : 'hidden',
					name : 'enterpriseBank.companyId'
				}, {
					xtype : 'hidden',
					name : 'enterpriseBank.enterpriseid'
				}, {
					xtype : 'hidden',
					name : 'enterpriseBank.isEnterprise',
					value : "1"
				}, {
					xtype : 'hidden',
					name : 'enterpriseBank.isInvest',
					value : "1"
				}, {
					xtype : 'hidden',
					name : 'enterpriseBank.iscredit',
					value : 0
				}, {
					xtype : 'hidden',
					name : 'enterpriseBank.id',
					value : 0
				}]
			}]
		});
	}
});

// 融资资金款项
ExtUD.Ext.FinanceInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	idDefinition : 'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (typeof(_cfg.idDefinition) != 'undefined') {
			this.idDefinition = _cfg.idDefinition;
		}
		if (typeof(_cfg.bigMoneyHidden) != 'undefined') {
			this.bigMoneyHidden = _cfg.bigMoneyHidden
		} else {
			this.bigMoneyHidden = true;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var idDefinition1 = this.idDefinition;
		this.idDefinition = this.projectId + this.idDefinition;
		var leftlabel = 115;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ExtUD.Ext.FinanceInfoPanel.superclass.constructor.call(this, {
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
					columnWidth : .24, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : "textfield",
						fieldLabel : "融资金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
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
							change : function(nf) {

								var value = nf.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									this
											.getCmpByName("flFinancingProject.projectMoney")
											.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("flFinancingProject.projectMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("flFinancingProject.projectMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("flFinancingProject.projectMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										}
									}

								}
							},
							'blur' : function(f) {
								var onlyId = this.idDefinition;
								var moneys = this
										.getCmpByName("flFinancingProject.projectMoney")
										.getValue();
								Ext.Ajax.request({
									url : __ctxPath
											+ '/flFinancing/changeMoneyFlFinancingProject.do',
									params : {
										"moneys" : moneys
									},
									method : 'post',
									success : function(response, options) {
										Ext
												.getCmp('bigmoneys' + onlyId)
												.setValue(response.responseText);
									}
								})

							}
						}
					}, {
						xtype : "hidden",
						name : "flFinancingProject.projectMoney"
					}]
				}, {
					columnWidth : .045, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .22,
					layout : "form",
					labelWidth : 60,
					hidden : this.bigMoneyHidden,
					items : [{
								fieldLabel : "大写",
								id : 'bigmoneys' + this.idDefinition,
								xtype : "textfield",
								name : "flFinancingProject.projectBigMoney",
								anchor : "100%",
								readOnly : true
							}]
				}, {
					columnWidth : .22, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 60,
					items : [{
								fieldLabel : "币种",
								xtype : "dickeycombo",
								hiddenName : 'flFinancingProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								anchor : "100%",
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
					columnWidth : .24, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : 'dicIndepCombo',
						allowBlank : false,
						fieldLabel : '日期模型',
						readOnly : this.isAllReadOnly,
						anchor : '100%',
						editable : false,
						nodeKey : 'dateModel',
						lazyInit : true,
						lazyRender : true,
						hiddenName : 'flFinancingProject.dateMode',
						listeners : {
							scope : this,
							afterrender : function(com) {
								var st = com.getStore();
								st.on('load', function() {
											com
													.setValue(st.getAt(0).data.dicKey)
											com.clearInvalid();
										})

							}
						}
					}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : "还款方式 ",
											fieldClass : 'field-align',
											// html :
											// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
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
									name : 'f1',
									id : "jixifs1" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.accrualtype')
														.setValue("sameprincipal");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.setDisabled(false);
												/*
												 * this.getCmpByName('mqhkri1').hide();
												 * this.getCmpByName('mqhkri').show();
												 */
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
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
									name : 'f1',
									id : "jixifs2" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.accrualtype')
														.setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.setDisabled(false);
												/*
												 * this.getCmpByName('mqhkri1').hide();
												 * this.getCmpByName('mqhkri').show();
												 */
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
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
									name : 'f1',
									id : "jixifs5" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs5"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.accrualtype')
														.setValue("sameprincipalsameInterest");
												if (this.isAllReadOnly == true) {

													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(true);
												} else {

													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(false);
												}

												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(true);
												// this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("monthPay");
												this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.setDisabled(false);
												/*
												 * this.getCmpByName('mqhkri1').hide();
												 * this.getCmpByName('mqhkri').show();
												 */
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);

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
									name : 'f1',
									id : "jixifs3" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.accrualtype')
														.setValue("singleInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(false);
												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(false);
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs6"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);

											}
										}
									}
								}, {
									xtype : "hidden",
									name : "flFinancingProject.accrualtype",
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
									name : 'f1',
									id : "jixifs6" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs6"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.accrualtype')
														.setValue("otherMothod");
												if (this.isAllReadOnly == true) {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(true);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(true);

													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(true);
												} else {
													Ext
															.getCmp("jixizq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq2"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq3"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("jixizq4"
																	+ this.idDefinition)
															.setDisabled(false);

													Ext
															.getCmp("jixizq6"
																	+ this.idDefinition)
															.setDisabled(false);

												}
												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(true);

												// this.getCmpByName('flFinancingProject.dayOfEveryPeriod').setValue("");
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.setDisabled(false);
												/*
												 * this.getCmpByName('mqhkri1').hide();
												 * this.getCmpByName('mqhkri').show();
												 */
												Ext.getCmp("jixifs2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs5"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixifs1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
											}
										}
									}

								}, {
									xtype : "hidden",
									name : "flFinancingProject.payaccrualType",
									value : "monthPay"

								}]
							}]

				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
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
									name : 'z1',
									id : "jixizq1" + this.idDefinition,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("dayPay");

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('flFinancingProject.isStartDatePay')
														.setValue("2");
												var payAccrualType = this
														.getCmpByName('flFinancingProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('flFinancingProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('flFinancingProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)

											} else {

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.getValue() == true) {
													this
															.getCmpByName('flFinancingProject.payintentPerioDate')
															.setDisabled(false);
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
									name : 'z1',
									id : "jixizq2" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("monthPay");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												var payAccrualType = this
														.getCmpByName('flFinancingProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('flFinancingProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('flFinancingProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)
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
									name : 'z1',
									id : "jixizq3" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("seasonPay");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												var payAccrualType = this
														.getCmpByName('flFinancingProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('flFinancingProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('flFinancingProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)
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
									name : 'z1',
									id : "jixizq4" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("yearPay");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(false);
												var payAccrualType = this
														.getCmpByName('flFinancingProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('flFinancingProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('flFinancingProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('flFinancingProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)
											}
										}
									}
								}]
							}, {
								columnWidth : 0.09,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '自定义周期',
									name : 'z1',
									id : "jixizq6" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('flFinancingProject.payaccrualType')
														.setValue("owerPay");

												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(false);

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(false);

											} else {
												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('flFinancingProject.dayOfEveryPeriod')
														.setValue("");
												if (Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.getValue() == false) {
													Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("meiqihkrq2"
																	+ this.idDefinition)
															.setDisabled(false);
													if (Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.getValue() == true) {
														this
																.getCmpByName('flFinancingProject.payintentPerioDate')
																.setDisabled(false);
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
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'flFinancingProject.dayOfEveryPeriod',
									listeners : {
										scope : this,
										'change' : function() {
											var payAccrualType = this
													.getCmpByName('flFinancingProject.payaccrualType')
													.getValue();
											var dayOfEveryPeriod = this
													.getCmpByName('flFinancingProject.dayOfEveryPeriod')
													.getValue();
											var payintentPeriod = this
													.getCmpByName('flFinancingProject.payintentPeriod')
													.getValue();
											var startDate = this
													.getCmpByName('flFinancingProject.startDate')
													.getValue();
											var intentDatePanel = this
													.getCmpByName('flFinancingProject.intentDate');
											setIntentDate(payAccrualType,
													dayOfEveryPeriod,
													payintentPeriod, startDate,
													intentDatePanel,this)
										}
									}
								}]
							}, {
								columnWidth : 0.07,
								labelWidth : 40,
								layout : 'form',
								items : [{
											fieldLabel : "日/期",
											labelSeparator : '',
											anchor : "100%"
										}]
							}

					]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .24, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 85,
						items : [{
							fieldLabel : "融资期数",
							xtype : "textfield",
							fieldClass : 'field-align',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : "flFinancingProject.payintentPeriod",
							anchor : "100%",
							listeners : {
								scope : this,
								'change' : function() {
									var payAccrualType = this
											.getCmpByName('flFinancingProject.payaccrualType')
											.getValue();
									var dayOfEveryPeriod = this
											.getCmpByName('flFinancingProject.dayOfEveryPeriod')
											.getValue();
									var payintentPeriod = this
											.getCmpByName('flFinancingProject.payintentPeriod')
											.getValue();
									var startDate = this
											.getCmpByName('flFinancingProject.startDate')
											.getValue();
									var intentDatePanel = this
											.getCmpByName('flFinancingProject.intentDate');
									setIntentDate(payAccrualType,
											dayOfEveryPeriod, payintentPeriod,
											startDate, intentDatePanel,this)
								}
							}

						}]
					}, {
						columnWidth : .045, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "期",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : .22,
						layout : 'form',
						labelWidth : 80,
						items : [{
							fieldLabel : "开始日期",
							xtype : "datefield",
							style : {
								imeMode : 'disabled'
							},
							name : "flFinancingProject.startDate",
							allowBlank : false,
							readOnly : this.isStartDateReadOnly,
							blankText : "融资开始日期不能为空，请正确填写!",
							anchor : "100%",
							format : 'Y-m-d',
							listeners : {
								scope : this,
								'change' : function(nf) {
									var payAccrualType = this
											.getCmpByName('flFinancingProject.payaccrualType')
											.getValue();
									var dayOfEveryPeriod = this
											.getCmpByName('flFinancingProject.dayOfEveryPeriod')
											.getValue();
									var payintentPeriod = this
											.getCmpByName('flFinancingProject.payintentPeriod')
											.getValue();
									var startDate = this
											.getCmpByName('flFinancingProject.startDate')
											.getValue();
									var intentDatePanel = this
											.getCmpByName('flFinancingProject.intentDate');
									setIntentDate(payAccrualType,
											dayOfEveryPeriod, payintentPeriod,
											startDate, intentDatePanel,this)
								}
							}
						}]
					}, {
						columnWidth : .24,
						layout : 'form',
						labelWidth : 85,
						items : [{
									fieldLabel : "<font color='red'>*</font>截止日期",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "flFinancingProject.intentDate",
									readOnly : true,
									blankText : "融资截止日期不能为空，请正确填写!",
									anchor : "100%",
									format : 'Y-m-d'
								}]
					}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '还款选项'
										}]
							}, {
								columnWidth : .12, // 该列在整行中所占的百分比
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
											|| this.record.data.isPreposePayAccrual == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this
														.getCmpByName('flFinancingProject.isPreposePayAccrual')
														.setValue(1);
											} else {
												this
														.getCmpByName('flFinancingProject.isPreposePayAccrual')
														.setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'flFinancingProject.isPreposePayAccrual',
									value : 0
								}]
							}, {
								columnWidth : .15, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "一次性支付全部利息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isInterestByOneTimeCheck",
									// value :true
									checked : this.record == null
											|| this.record.data.isInterestByOneTime == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this
														.getCmpByName('flFinancingProject.isInterestByOneTime')
														.setValue(1);
											} else {
												this
														.getCmpByName('flFinancingProject.isInterestByOneTime')
														.setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'flFinancingProject.isInterestByOneTime',
									value : 0
								}]
							}, {
								columnWidth : .45,
								name : "mqhkri",
								layout : "column",
								items : [{
									columnWidth : 0.278,
									labelWidth : 76,
									layout : 'form',
									items : [{
										xtype : 'radio',
										fieldLabel : '每期付息日',
										boxLabel : '固定在',
										name : 'q1',
										id : "meiqihkrq1" + this.idDefinition,
										inputValue : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext
														.getCmp("meiqihkrq1"
																+ this.idDefinition)
														.getValue();
												if (flag == true) {
													this
															.getCmpByName('flFinancingProject.isStartDatePay')
															.setValue("1");
													this
															.getCmpByName('flFinancingProject.payintentPerioDate')
															.setDisabled(false);
												}
											}
										}

									}, {
										xtype : "hidden",
										name : "flFinancingProject.isStartDatePay"

									}]
								}, {
									columnWidth : 0.132,
									labelWidth : 1,
									layout : 'form',
									items : [{
										xtype : 'textfield',
										readOnly : this.isAllReadOnly,
										// name :
										// "flFinancingProject.payintentPerioDate",
										xtype : 'combo',
										mode : 'local',
										disabled : true,
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "flFinancingProject.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
								}, {
									columnWidth : 0.12,
									labelWidth : 45,
									layout : 'form',
									items : [{
												fieldLabel : "号还款",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.47,
									labelWidth : 10,
									layout : 'form',
									items : [{
										xtype : 'radio',
										boxLabel : '按实际融资日还款',
										name : 'q1',
										id : "meiqihkrq2" + this.idDefinition,
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext
														.getCmp("meiqihkrq2"
																+ this.idDefinition)
														.getValue();
												if (flag == true) {
													this
															.getCmpByName('flFinancingProject.isStartDatePay')
															.setValue("2");
													this
															.getCmpByName('flFinancingProject.payintentPerioDate')
															.setValue(null);
													this
															.getCmpByName('flFinancingProject.payintentPerioDate')
															.setDisabled(true);
												}
											}
										}

									}]
								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '融资利率',
											allowBlank : false
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .08,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "flFinancingProject.yearAccrualRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									decimalPrecision : 8,
									anchor : '100%',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var dateModel = this
													.getCmpByName('flFinancingProject.dateMode')
													.getValue()
											var accrualnf = this
													.getCmpByName('flFinancingProject.accrual')
											var dayAccrualRatenf = this
													.getCmpByName('flFinancingProject.dayAccrualRate')
											var sumAccrualRatenf = this
													.getCmpByName('flFinancingProject.sumAccrualRate')
											var accrualNewnf = this
													.getCmpByName('flFinancingProject.accrualNew')
											var startDate = this
													.getCmpByName('flFinancingProject.startDate')
													.getValue()
											var intentDate = this
													.getCmpByName('flFinancingProject.intentDate')
													.getValue()
											accrualnf
													.setValue((nf.getValue() / (12 * 10))
															* 10)
											if (null != dateModel
													&& dateModel == 'dateModel_360') {
												dayAccrualRatenf.setValue(nf
														.getValue()
														/ 360)
												accrualNewnf.setValue(nf
														.getValue()
														/ 360)
												if (startDate != ''
														&& intentDate != '') {
													intentDate = Ext.util.Format
															.date(intentDate,
																	'Y-m-d')
													startDate = Ext.util.Format
															.date(startDate,
																	'Y-m-d')
													var days = ((new Date(
															intentDate
																	.substring(
																			0,
																			4),
															intentDate
																	.substring(
																			5,
																			intentDate
																					.lastIndexOf('-'))
																	- 1,
															intentDate
																	.substring(
																			intentDate
																					.lastIndexOf('-')
																					+ 1,
																			intentDate.length)))
															.getTime() - (new Date(
															startDate
																	.substring(
																			0,
																			4),
															startDate
																	.substring(
																			5,
																			startDate
																					.lastIndexOf('-'))
																	- 1,
															startDate
																	.substring(
																			startDate
																					.lastIndexOf('-')
																					+ 1,
																			startDate.length)))
															.getTime())
															/ 1000
															/ 60
															/ 60
															/ 24
													sumAccrualRatenf
															.setValue(nf
																	.getValue()
																	/ 360
																	* days)
												}
											} else if (null != dateModel
													&& dateModel == 'dateModel_365') {
												dayAccrualRatenf.setValue(nf
														.getValue()
														/ 365)
												accrualNewnf.setValue(nf
														.getValue()
														/ 365)
												if (startDate != ''
														&& intentDate != '') {
													intentDate = Ext.util.Format
															.date(intentDate,
																	'Y-m-d')
													startDate = Ext.util.Format
															.date(startDate,
																	'Y-m-d')
													var days = ((new Date(
															intentDate
																	.substring(
																			0,
																			4),
															intentDate
																	.substring(
																			5,
																			intentDate
																					.lastIndexOf('-'))
																	- 1,
															intentDate
																	.substring(
																			intentDate
																					.lastIndexOf('-')
																					+ 1,
																			intentDate.length)))
															.getTime() - (new Date(
															startDate
																	.substring(
																			0,
																			4),
															startDate
																	.substring(
																			5,
																			startDate
																					.lastIndexOf('-'))
																	- 1,
															startDate
																	.substring(
																			startDate
																					.lastIndexOf('-')
																					+ 1,
																			startDate.length)))
															.getTime())
															/ 1000
															/ 60
															/ 60
															/ 24
													sumAccrualRatenf
															.setValue(nf
																	.getValue()
																	/ 365
																	* days)
												}
											}
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlign : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .08,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "flFinancingProject.accrual",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									anchor : '100%',
									decimalPrecision : 8,
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var dateModel = this
													.getCmpByName('flFinancingProject.dateMode')
													.getValue()
											var yearAccrualRatenf = this
													.getCmpByName('flFinancingProject.yearAccrualRate')
											var dayAccrualRatenf = this
													.getCmpByName('flFinancingProject.dayAccrualRate')
											var accrualNewnf = this
													.getCmpByName('flFinancingProject.accrualNew')
											var sumAccrualRatenf = this
													.getCmpByName('flFinancingProject.sumAccrualRate')
											var startDate = this
													.getCmpByName('flFinancingProject.startDate')
													.getValue()
											var intentDate = this
													.getCmpByName('flFinancingProject.intentDate')
													.getValue()
											yearAccrualRatenf.setValue(nf
													.getValue()
													* 12)
											dayAccrualRatenf.setValue(nf
													.getValue()
													/ 30)
											accrualNewnf.setValue(nf.getValue()
													/ 30)
											if (startDate != ''
													&& intentDate != '') {
												intentDate = Ext.util.Format
														.date(intentDate,
																'Y-m-d')
												startDate = Ext.util.Format
														.date(startDate,
																'Y-m-d')
												var days = ((new Date(
														intentDate.substring(0,
																4),
														intentDate
																.substring(
																		5,
																		intentDate
																				.lastIndexOf('-'))
																- 1,
														intentDate
																.substring(
																		intentDate
																				.lastIndexOf('-')
																				+ 1,
																		intentDate.length)))
														.getTime() - (new Date(
														startDate.substring(0,
																4),
														startDate
																.substring(
																		5,
																		startDate
																				.lastIndexOf('-'))
																- 1,
														startDate
																.substring(
																		startDate
																				.lastIndexOf('-')
																				+ 1,
																		startDate.length)))
														.getTime())
														/ 1000 / 60 / 60 / 24
												sumAccrualRatenf.setValue(nf
														.getValue()
														/ 30 * days)
											}
										}
									}

								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .08,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "flFinancingProject.dayAccrualRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var dateModel = this
													.getCmpByName('flFinancingProject.dateMode')
													.getValue()
											var accrualnf = this
													.getCmpByName('flFinancingProject.accrual')
											var accrualNewnf = this
													.getCmpByName('flFinancingProject.accrualNew')
											var yearAccrualRatenf = this
													.getCmpByName('flFinancingProject.yearAccrualRate')
											var sumAccrualRatenf = this
													.getCmpByName('flFinancingProject.sumAccrualRate')
											var startDate = this
													.getCmpByName('flFinancingProject.startDate')
													.getValue()
											var intentDate = this
													.getCmpByName('flFinancingProject.intentDate')
													.getValue()
											accrualnf.setValue(nf.getValue()
													* 30)
											accrualNewnf
													.setValue(nf.getValue())
											if (null != dateModel
													&& dateModel == 'dateModel_360') {
												yearAccrualRatenf.setValue(nf
														.getValue()
														* 360)

											} else if (null != dateModel
													&& dateModel == 'dateModel_365') {
												yearAccrualRatenf.setValue(nf
														.getValue()
														* 365)
											}
											if (startDate != ''
													&& intentDate != '') {
												intentDate = Ext.util.Format
														.date(intentDate,
																'Y-m-d')
												startDate = Ext.util.Format
														.date(startDate,
																'Y-m-d')
												var days = ((new Date(
														intentDate.substring(0,
																4),
														intentDate
																.substring(
																		5,
																		intentDate
																				.lastIndexOf('-'))
																- 1,
														intentDate
																.substring(
																		intentDate
																				.lastIndexOf('-')
																				+ 1,
																		intentDate.length)))
														.getTime() - (new Date(
														startDate.substring(0,
																4),
														startDate
																.substring(
																		5,
																		startDate
																				.lastIndexOf('-'))
																- 1,
														startDate
																.substring(
																		startDate
																				.lastIndexOf('-')
																				+ 1,
																		startDate.length)))
														.getTime())
														/ 1000 / 60 / 60 / 24
												sumAccrualRatenf.setValue(nf
														.getValue()
														* days)
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'flFinancingProject.accrualNew'
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "合计利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .08,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "flFinancingProject.sumAccrualRate",
									readOnly : this.isAllReadOnly,
									anchor : '100%',
									decimalPrecision : 8,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var dateModel = this
													.getCmpByName('flFinancingProject.dateMode')
													.getValue()
											var accrualnf = this
													.getCmpByName('flFinancingProject.accrual')
											var yearAccrualRatenf = this
													.getCmpByName('flFinancingProject.yearAccrualRate')
											var dayAccrualRatenf = this
													.getCmpByName('flFinancingProject.dayAccrualRate')
											var startDate = this
													.getCmpByName('flFinancingProject.startDate')
													.getValue()
											var intentDate = this
													.getCmpByName('flFinancingProject.intentDate')
													.getValue()
											var accrualNewnf = this
													.getCmpByName('flFinancingProject.accrualNew')
											if (startDate != ''
													&& intentDate != '') {
												intentDate = Ext.util.Format
														.date(intentDate,
																'Y-m-d')
												startDate = Ext.util.Format
														.date(startDate,
																'Y-m-d')
												var days = ((new Date(
														intentDate.substring(0,
																4),
														intentDate
																.substring(
																		5,
																		intentDate
																				.lastIndexOf('-'))
																- 1,
														intentDate
																.substring(
																		intentDate
																				.lastIndexOf('-')
																				+ 1,
																		intentDate.length)))
														.getTime() - (new Date(
														startDate.substring(0,
																4),
														startDate
																.substring(
																		5,
																		startDate
																				.lastIndexOf('-'))
																- 1,
														startDate
																.substring(
																		startDate
																				.lastIndexOf('-')
																				+ 1,
																		startDate.length)))
														.getTime())
														/ 1000 / 60 / 60 / 24
												var rate = nf.getValue() / days
												dayAccrualRatenf.setValue(rate);
												accrualNewnf.setValue(rate);
												if (null != dateModel
														&& dateModel == 'dateModel_360') {
													yearAccrualRatenf
															.setValue(rate
																	* 360)

												} else if (null != dateModel
														&& dateModel == 'dateModel_365') {
													yearAccrualRatenf
															.setValue(rate
																	* 365)
												}
												accrualnf.setValue(rate * 30)
											}
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}, {
					columnWidth : 1,
					layout : 'form',
					buttonAlign : 'center',
					style : 'margin-left : 450px',
					items : [{
						xtype : 'button',
						text : '融资试算',
						iconCls : 'btn-search',
						width : 110,
						hidden : this.isHiddencalculateBtn,
						scope : this,
						handler : function() {
							var isHiddenbackBtn = this.isHiddenbackBtn;
							var projectMoney = this
									.getCmpByName("flFinancingProject.projectMoney")
									.getValue();
							var startDate = this
									.getCmpByName("flFinancingProject.startDate")
									.getValue();
							var payaccrualType = this
									.getCmpByName("flFinancingProject.payaccrualType")
									.getValue();
							var dayOfEveryPeriod = this
									.getCmpByName("flFinancingProject.dayOfEveryPeriod")
									.getValue();
							var payintentPeriod = this
									.getCmpByName("flFinancingProject.payintentPeriod")
									.getValue();
							var isStartDatePay = this
									.getCmpByName("flFinancingProject.isStartDatePay")
									.getValue();
							var payintentPerioDate = this
									.getCmpByName("flFinancingProject.payintentPerioDate")
									.getValue();
							var intentDate = this
									.getCmpByName("flFinancingProject.intentDate")
									.getValue();
							var accrualtype = this
									.getCmpByName("flFinancingProject.accrualtype")
									.getValue();
							var accrual = this
									.getCmpByName("flFinancingProject.accrual")
									.getValue();
							var isPreposePayAccrual = this
									.getCmpByName("flFinancingProject.isPreposePayAccrual")
									.getValue();
							var isInterestByOneTime = this
									.getCmpByName("flFinancingProject.isInterestByOneTime")
									.getValue();
							var yearAccrualRate = this
									.getCmpByName("flFinancingProject.yearAccrualRate")
									.getValue();
							var dayAccrualRate = this
									.getCmpByName("flFinancingProject.dayAccrualRate")
									.getValue();
							var sumAccrualRate = this
									.getCmpByName("flFinancingProject.sumAccrualRate")
									.getValue();
							var dateMode = this
									.getCmpByName("flFinancingProject.dateMode")
									.getValue();
							new FinanceCalculateFundIntent({
										projectMoney : projectMoney,
										startDate : startDate,
										payaccrualType : payaccrualType,
										dayOfEveryPeriod : dayOfEveryPeriod,
										payintentPeriod : payintentPeriod,
										isStartDatePay : isStartDatePay,
										payintentPerioDate : payintentPerioDate,
										intentDate : intentDate,
										accrual : accrual,
										accrualtype : accrualtype,
										projectId : this.projectId,
										isPreposePayAccrual : isPreposePayAccrual,
										isInterestByOneTime : isInterestByOneTime,
										yearAccrualRate : yearAccrualRate,
										dayAccrualRate : dayAccrualRate,
										sumAccrualRate : sumAccrualRate,
										dateMode : dateMode,
										idDefinition1 : idDefinition1,
										objectfinace : this,
										isHiddenbackBtn : isHiddenbackBtn

									}).show();
						}

					}]
				}]
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});

/*******************************************************************************
 * 信贷流程项目基本信息
 * 
 * @author zcb
 * @class ExtUD.Ext.PerCreditLoanProjectInfoPanel
 * @extends Ext.Panel
 */
ExtUD.Ext.PerCreditLoanProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isCPLX : false,
	
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}else if(typeof(_cfg.isCPLX) != "undefined") {
			this.isCPLX = _cfg.isCPLX;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		ExtUD.Ext.PerCreditLoanProjectInfoPanel.superclass.constructor.call(
				this, {
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							// columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
									xtype : 'hidden',
									name : 'slSmallloanProject.projectId'
								}, {
									columnWidth : (this.product == null || this.product == false)
											? .5
											: .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 80,
									items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : true,
										name : "slSmallloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										anchor : "100%"// 控制文本框的长度
									}]
								}, {
									columnWidth : (this.product == null || this.product == false)
											? .5
											: .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 80,
									labelAlign : 'right',
									items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "slSmallloanProject.projectNumber",
										readOnly : true,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "100%"
									}]
								}, {
									columnWidth : .2,
									layout : 'form',
									labelWidth : 100,
									hidden : this.product == null
											? false
											: !this.product,
									labelAlign : 'right',
									items : [{
										labelWidth : 85,
										xtype : "combo",
										mode : 'local',
										anchor : '100%',
										disabled : this.product == null
												? false
												: !this.product,
										allowBlank : false,
										readOnly : this.isCPLX,
										triggerAction : 'all',
										hiddenName : "slSmallloanProject.productId",
										displayField : 'productName',
										valueField : 'id',
										fieldLabel : "产品类型",
										editable : false,
										store : new Ext.data.JsonStore({
											url : __ctxPath
													+ "/system/listBpProductParameter.do",
											totalProperty : 'totalCounts',
											autoLoad : true,
											root : 'result',
											fields : [{
														name : 'id'
													}, {
														name : 'productName'
													}]													
										}),
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
															combox
																	.setValue(combox
																			.getValue());
															combox
																	.clearInvalid();
														})
														/*Ext.Ajax.request({
															url : __ctxPath
															+ "/system/listBpProductParameter.do",
															method : 'POST',
															scope:this,
															success : function(response, request) {
																obj = Ext.util.JSON.decode(response.responseText);
																	//var projectName = obj.data.projectName;
																//var createTime = obj.data.createTime;
																//var dueTime = obj.data.dueTime;
																//var creator = obj.data.creator;
																if(obj.success==true){
																	this.getCmpByName('projectInfo').setTitle('项目基本信息【任务分配时间：'+createTime+'&nbsp;&nbsp;任务完成时限：'+dueTime+'&nbsp;&nbsp;当前处理人：'+creator+'】');
																}else{
																	Ext.ux.Toast.msg('操作信息', '查询的项目信息不存在!');
																}
															},
															failure : function(response,request) {
																Ext.ux.Toast.msg('操作信息', '查询的项目信息不存在!');
															},
															params : {
																//taskId : taskId,
																//businessType : '$!businessType',
																id :'$!id'
															}
															
														});*/
											}/*
												 * ,
												 * select:function(combox,recode,index){
												 *   }
												 */
									 /*   'blur' : function(f) {
													var projuctId = f.getValue();					
													Ext.Ajax.request({
														url : __ctxPath
														+ "/system/listBpProductParameter.do",
														method : 'POST',
														scope:this,
														success : function(response, request) {
															obj = Ext.util.JSON.decode(response.responseText);
																//var projectName = obj.data.projectName;
															//var createTime = obj.data.createTime;
															//var dueTime = obj.data.dueTime;
															//var creator = obj.data.creator;
															if(obj.success==true){
																this.getCmpByName('projectInfo').setTitle('项目基本信息【任务分配时间：'+createTime+'&nbsp;&nbsp;任务完成时限：'+dueTime+'&nbsp;&nbsp;当前处理人：'+creator+'】');
															}else{
																Ext.ux.Toast.msg('操作信息', '查询的项目信息不存在!');
															}
														},
														failure : function(response,request) {
															Ext.ux.Toast.msg('操作信息', '查询的项目信息不存在!');
														},
														params : {
															//taskId : taskId,
															//businessType : '$!businessType',
															id :'$!id'
														}
														
													});

												}*/
									
										}
									
									}]
								}, {
									columnWidth : .2,
									layout : 'form',
									labelWidth : 100,
									hidden : this.product == null
											? false
											: !this.product,
									labelAlign : 'right',
									disabled : this.product == null
											? false
											: !this.product,
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "slSmallloanProject.appUserName",
										editable : false,
										fieldLabel : "项目经理",
										labelWidth : 85,
										blankText : "项目经理不能为空，请正确填写!",
										allowBlank : true,
										anchor : "100%",
										readOnly : this.readOnly,
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj
													.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择项目经理",
												callback : function(uId, uname) {
													obj.setValue(uname);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
										}
									}, {
										xtype : "hidden",
										disable : true,
										name : 'slSmallloanProject.appUserId',
										value : ""
									}]
								}, {
									columnWidth : .2,
									layout : 'form',
									labelWidth : 100,

									hidden : this.product == null
											? false
											: !this.product,
									labelAlign : 'right',
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "slSmallloanProject.teamManagerName",
										editable : false,
										fieldLabel : "团队经理",
										labelWidth : 85,
										blankText : "团队经理不能为空，请正确填写!",
										allowBlank : true,
										readOnly : this.readOnly,
										anchor : "100%",
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj
													.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择项目经理",
												callback : function(uId, uname) {
													obj.setValue(uname);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
										}
									}, {
										xtype : "hidden",
										disable : true,
										name : 'slSmallloanProject.teamManagerId',
										value : ""
									}]
								}]
					}]
				});
	}
});
//信用贷款项目基本信息

ExtUD.Ext.CreditLoanProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isCPLX : false,
	
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}else if(typeof(_cfg.isCPLX) != "undefined") {
			this.isCPLX = _cfg.isCPLX;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		ExtUD.Ext.CreditLoanProjectInfoPanel.superclass.constructor.call(
				this, {
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							// columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
									xtype : 'hidden',
									name : 'slSmallloanProject.projectId'
								}, {
									columnWidth : .33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 80,
									items : [{
										fieldLabel : "项目名称",
										xtype : "textfield",
										readOnly : true,
										name : "slSmallloanProject.projectName",
										blankText : "项目名称不能为空，请正确填写!",
										anchor : "100%"// 控制文本框的长度
									}]
								}, {
									columnWidth :.33, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
										fieldLabel : "项目编号",
										xtype : "textfield",
										name : "slSmallloanProject.projectNumber",
										readOnly : true,
										blankText : "项目编号不能为空，请正确填写!",
										anchor : "100%"
									}]
								},{
										columnWidth : .33, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign : 'right',
										items : [{
										fieldLabel : '门店名称',
										anchor : '100%',
										xtype : 'combo',
										readOnly : true,
										triggerClass : 'x-form-search-trigger',
										hiddenName : "slSmallloanProject.departMentName",
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												if (null != obj.orgName && "" != obj.orgName)
													op.getCmpByName('slSmallloanProject.departMentName').setValue(obj.orgName);
												if (null != obj.orgId && "" != obj.orgId)
													op.getCmpByName('slSmallloanProject.departId').setValue(obj.orgId);
											}
											selectShop(EnterpriseNameStockUpdateNew);
										}
									},{
										xtype:'hidden',
										name:'slSmallloanProject.departId'
									}]
									},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 80,

									hidden : this.product == null
											? false
											: !this.product,
									labelAlign : 'right',
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "slSmallloanProject.teamManagerName",
										editable : false,
										fieldLabel : "项目主管",
										labelWidth : 85,
										blankText : "项目主管不能为空，请正确填写!",
										allowBlank : true,
										readOnly : this.readOnly,
										anchor : "100%",
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj
													.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择项目主管",
												callback : function(uId, uname) {
													obj.setValue(uname);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
										}
									}, {
										xtype : "hidden",
										disable : true,
										name : 'slSmallloanProject.teamManagerId',
										value : ""
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										hiddenName : "slSmallloanProject.appUserName",
										editable : false,
										fieldLabel : "项目经理",
										labelWidth : 85,
										blankText : "项目经理不能为空，请正确填写!",
										allowBlank : true,
										anchor : "100%",
										readOnly : this.readOnly,
										onTriggerClick : function(cc) {
											var obj = this;
											var appuerIdObj = obj.nextSibling();
											var userIds = appuerIdObj
													.getValue();
											if ("" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "选择项目经理",
												callback : function(uId, uname) {
													obj.setValue(uname);
													obj.setRawValue(uname);
													appuerIdObj.setValue(uId);
												}
											}).show();
										}
									}, {
										xtype : "hidden",
										disable : true,
										name : 'slSmallloanProject.appUserId',
										value : ""
									}]
								}, {
									columnWidth : .33,
									layout : 'form',
									labelWidth : 60,
									hidden : this.product == null
											? false
											: !this.product,
									labelAlign : 'right',
									items : [{
										labelWidth : 85,
										xtype : "combo",
										mode : 'local',
										anchor : '100%',
										disabled : this.product == null
												? false
												: !this.product,
										allowBlank : false,
										readOnly : this.isCPLX,
										triggerAction : 'all',
										hiddenName : "bpProductParameter.productName",
										displayField : 'productName',
										valueField : 'id',
										valueField : 'productName',
										fieldLabel : "产品类型",
										editable : false,
										store : new Ext.data.JsonStore({
											url : __ctxPath+ "/system/listBpProductParameter.do",
											totalProperty : 'totalCounts',
											autoLoad : true,
											root : 'result',
											fields : [{
												name : 'id'
											}, {
												name : 'productName'
											}]													
										}),
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
											}
										}
									
									},{
										xtype : "hidden",
										name:'slSmallloanProject.productId'
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 80,
									labelAlign : 'right',
									hidden : (this.isArchivesHidden==false?false:true),
									items : [{
										xtype : "dickeycombo",
										hiddenName : "slSmallloanProject.archivesBelonging",
										nodeKey : 'archivesBelonging', // xx代表分类名称
										fieldLabel : "档案归属地",
										readOnly : this.readOnly,
										editable : false,
										anchor : "100%",
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
									       }
										}
				
									}]
								}]
					}]
				});
	}
});

/*******************************************************************************
 * 产品基础信息面板
 * 
 * @author zcb
 * @class ExtUD.Ext.productPanel
 * @extends Ext.Panel
 */
ExtUD.Ext.productBaseInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isFlow : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isFlow) {
			this.isFlow = _cfg.isFlow;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.productBaseInfoPanel.superclass.constructor.call(this, {
			layout : 'anchor',
			frame : true,
			// title:this.isFlow?'产品信息':'',
			border : false,
			scope : this,
			defaults : {
				anchor : '98%',
				columnWidth : 1,
				isFormField : true,
				labelWidth : '115'
			},
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html : '<br/><B><font class="x-myZW-fieldset-title">【产品简介】:</font></B>'
			}, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 100
				},
				items : [{
					xtype : 'panel',
					border : false,
					bodyStyle : 'margin-bottom:5px',
					html : '<br/><B><font class="x-myZW-fieldset-title">【'
							+ this.titleText + '】:</font></B>',
					hidden : this.isFlow
				}, {
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
								name : 'bpProductParameter.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '产品名称',
								name : 'bpProductParameter.productName',
								xtype : 'textfield',
								readOnly : this.isAllReadOnly,
								anchor : '100%',
								maxLength : 255
							}]
				}, {
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : "dicIndepCombo",
						labelWidth : 85,
						fieldLabel : "我方主体类型",
						emptyText : "请选择",
						nodeKey : 'ourmainType',
						allowBlank : true,
						anchor : "100%",
						readOnly : this.isAllReadOnly,
						editable : true,
						hiddenName : "bpProductParameter.mineType",
						scope : this,
						listeners : {
							change : function(combox, record, index) {
								var comboxValue = combox.getValue();
								var url = '';
								var store = null;
								var combo = Ext.getCmp('dd_Degree');
								combo.clearValue();
								if (comboxValue == "company_ourmain") {// 企业
									url = __ctxPath
											+ '/creditFlow/ourmain/listSlCompanyMain.do';
									store = new Ext.data.Store({
										proxy : new Ext.data.HttpProxy({
													url : url
												}),
										reader : new Ext.data.JsonReader({
													root : 'result'
												}, [{
															name : 'itemName',
															mapping : 'corName'
														}, {
															name : 'itemValue',
															mapping : 'companyMainId'
														}])
									})
								} else if (comboxValue == "person_ourmain") { // 个人
									url = __ctxPath
											+ '/creditFlow/ourmain/listSlPersonMain.do';
									store = new Ext.data.Store({
										proxy : new Ext.data.HttpProxy({
													url : url
												}),
										reader : new Ext.data.JsonReader({
													root : 'result'
												}, [{
															name : 'itemName',
															mapping : 'name'
														}, {
															name : 'itemValue',
															mapping : 'personMainId'
														}])
									})
								}
								combo.store = store;
								combo.valueField = "itemValue";
								store.load();
								if (combo.view) { // 刷新视图,避免视图值与实际值不相符
									combo.view.setStore(combo.store);
								}
								this.fireEvent("afterrender", combo);
								/*
								 * var obj_n =
								 * this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
								 * ressetProjuect(obj_n);
								 */
							},
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											var v = combox.getValue();
											combox.setValue(v);
										});
								combox.clearInvalid();
							},
							loadData : function(comp) {
								var st = comp.getStore();
								st.on("load", function() {
											comp.setValue(comp.getValue());
											comp.clearInvalid();
										})

								this.fireEvent("change", comp);
							}
						}
					}]
				}, {
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
								fieldLabel : '我方主体名称',
								xtype : "combo",
								id : "dd_Degree",
								anchor : "100%",
								displayField : 'itemName',
								valueField : 'itemValue',
								emptyText : "请选择",
								allowBlank : true,
								readOnly : this.isAllReadOnly,
								editable : true,
								hiddenName : 'bpProductParameter.mineId',
								typeAhead : true,
								mode : 'local',
								editable : false,
								selectOnFocus : true,
								store : new Ext.data.JsonStore({}),
								triggerAction : 'all',
								blankText : "我方主体不能为空，请正确填写!",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var v = combox.getValue();
													combox.setValue(v);
												});
										combox.clearInvalid();
									}
								}
							}]
				}, {
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
								xtype : 'radiogroup',
								fieldLabel : '借款人类型',
								name : 'bpProductParameter.borrowerType',
								items : [{
											boxLabel : '企业',
											name : 'bpProductParameter.borrowerType',
											inputValue : "0",
											disabled : this.isAllReadOnly,
											checked : true
										}, {
											boxLabel : '个人',
											name : 'bpProductParameter.borrowerType',
											inputValue : "1",
											disabled : this.isAllReadOnly,
											checked : false
										}]
							}]
				}, {
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
								xtype : 'textarea',
								anchor : '100%',
								fieldLabel : '产品描述',
								readOnly : this.isAllReadOnly,
								name : 'bpProductParameter.productDescribe'
							}]
				}]
			}]
		});
	}
});

/*******************************************************************************
 * 产品
 * 
 * @author zcb
 * @class ExtUD.Ext.productPanel
 * @extends Ext.Panel
 */
ExtUD.Ext.productPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	frame : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isFlow : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isFlow) {
			this.isFlow = _cfg.isFlow;
		}
		Ext.applyIf(this, _cfg);
		// 每期还款日固定在
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		// 贷款期数
		var payintentPeriods = "[";
		for (var i = 1; i <= 36; i++) {
			payintentPeriods = payintentPeriods + "[" + i + ", " + i + "],";
		}
		payintentPeriods = payintentPeriods.substring(0,
				payintentPeriods.length - 1);
		payintentPeriods = payintentPeriods + "]";
		var obpayintentPeriods = Ext.decode(payintentPeriods);

		ExtUD.Ext.productPanel.superclass.constructor.call(this, {
			title : this.isFlow ? '' : '款项信息',
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : '110'
				},
				items : [{
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : "还款方式 ",
											fieldClass : 'field-align',
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本金',
									name : 'f1',
									id : "jixifs1",
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1")
													.getValue();
											if (flag == true) {
												Ext.getCmp("jixizq1")
														.setDisabled(true);
												Ext.getCmp("jixizq2")
														.setDisabled(true);
												Ext.getCmp("jixizq3")
														.setDisabled(true);
												Ext.getCmp("jixizq4")
														.setDisabled(true);
												Ext.getCmp("jixizq6")
														.setDisabled(true);

												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(true);

												Ext.getCmp("jixizq2")
														.setValue(true);
												Ext.getCmp("jixizq1")
														.setValue(false);
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('bpProductParameter.payintentPeriod')
														.setDisabled(false);
												Ext.getCmp("jixifs2")
														.setValue(false);
												Ext.getCmp("jixifs5")
														.setValue(false);
												Ext.getCmp("jixifs3")
														.setValue(false);
												Ext.getCmp("jixifs6")
														.setValue(false);
												Ext.getCmp("meiqihkrq1")
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(false);
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
									name : 'f1',
									id : "jixifs2",
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.accrualtype')
														.setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1")
														.setDisabled(true);
												Ext.getCmp("jixizq2")
														.setDisabled(true);
												Ext.getCmp("jixizq3")
														.setDisabled(true);
												Ext.getCmp("jixizq4")
														.setDisabled(true);

												Ext.getCmp("jixizq6")
														.setDisabled(true);
												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(true);

												Ext.getCmp("jixizq2")
														.setValue(true);
												Ext.getCmp("jixizq1")
														.setValue(false);
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('bpProductParameter.payintentPeriod')
														.setDisabled(false);
												Ext.getCmp("jixifs1")
														.setValue(false);
												Ext.getCmp("jixifs5")
														.setValue(false);
												Ext.getCmp("jixifs3")
														.setValue(false);
												Ext.getCmp("jixifs6")
														.setValue(false);
												Ext.getCmp("meiqihkrq1")
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(false);
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
									name : 'f1',
									id : "jixifs5",
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs5")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.accrualtype')
														.setValue("sameprincipalsameInterest");
												if (this.isAllReadOnly == true) {
													Ext.getCmp("jixizq1")
															.setDisabled(true);
													Ext.getCmp("jixizq2")
															.setDisabled(true);
													Ext.getCmp("jixizq3")
															.setDisabled(true);
													Ext.getCmp("jixizq4")
															.setDisabled(true);
													Ext.getCmp("jixizq6")
															.setDisabled(true);
												} else {
													Ext.getCmp("jixizq1")
															.setDisabled(false);
													Ext.getCmp("jixizq2")
															.setDisabled(false);
													Ext.getCmp("jixizq3")
															.setDisabled(false);
													Ext.getCmp("jixizq4")
															.setDisabled(false);
													Ext.getCmp("jixizq6")
															.setDisabled(false);
												}
												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("monthPay");
												this
														.getCmpByName('bpProductParameter.payintentPeriod')
														.setDisabled(false);
												this
														.getCmpByName('bpProductParameter.payintentPerioDate')
														.setDisabled(true);
												Ext.getCmp("jixifs2")
														.setValue(false);
												Ext.getCmp("jixifs1")
														.setValue(false);
												Ext.getCmp("jixifs3")
														.setValue(false);
												Ext.getCmp("jixifs6")
														.setValue(false);
												Ext.getCmp("meiqihkrq1")
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1")
														.setValue(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2")
														.setValue(true)
											}
										}
									}
								}]
							}, {
								columnWidth : 0.15,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '按期收息,到期还本',
									name : 'f1',
									id : "jixifs3",
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.accrualtype')
														.setValue("singleInterest");
												Ext.getCmp("jixizq1")
														.setDisabled(false);
												Ext.getCmp("jixizq2")
														.setDisabled(false);
												Ext.getCmp("jixizq3")
														.setDisabled(false);
												Ext.getCmp("jixizq4")
														.setDisabled(false);
												Ext.getCmp("jixizq6")
														.setDisabled(false);

												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(false);

												Ext.getCmp("jixifs2")
														.setValue(false);
												Ext.getCmp("jixifs5")
														.setValue(false);
												Ext.getCmp("jixifs1")
														.setValue(false);
												Ext.getCmp("jixifs6")
														.setValue(false);
												Ext.getCmp("meiqihkrq1")
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(false);
											}
										}
									}
								}, {
									xtype : "hidden",
									name : "bpProductParameter.accrualtype",
									value : "singleInterest"
								}]
							}, {
								columnWidth : 0.15,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '其他还款方式',
									anchor : "100%",
									name : 'f1',
									id : "jixifs6",
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs6")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.accrualtype')
														.setValue("otherMothod");
												if (this.isAllReadOnly == true) {
													Ext.getCmp("jixizq1")
															.setDisabled(true);
													Ext.getCmp("jixizq2")
															.setDisabled(true);
													Ext.getCmp("jixizq3")
															.setDisabled(true);
													Ext.getCmp("jixizq4")
															.setDisabled(true);
													Ext.getCmp("jixizq6")
															.setDisabled(true);
												} else {
													Ext.getCmp("jixizq1")
															.setDisabled(false);
													Ext.getCmp("jixizq2")
															.setDisabled(false);
													Ext.getCmp("jixizq3")
															.setDisabled(false);
													Ext.getCmp("jixizq4")
															.setDisabled(false);
													Ext.getCmp("jixizq6")
															.setDisabled(false);
												}
												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(true);
												Ext.getCmp("jixizq2")
														.setValue(true);
												Ext.getCmp("jixizq1")
														.setValue(false);
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("monthPay");
												this
														.getCmpByName('bpProductParameter.payintentPeriod')
														.setDisabled(false);
												Ext.getCmp("jixifs2")
														.setValue(false);
												Ext.getCmp("jixifs5")
														.setValue(false);
												Ext.getCmp("jixifs3")
														.setValue(false);
												Ext.getCmp("jixifs1")
														.setValue(false);
												Ext.getCmp("meiqihkrq1")
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(false);
											}
										}
									}

								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 85,
								labelAlign : 'right',
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
									name : 'z1',
									id : "jixizq1",
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("dayPay");
												Ext.getCmp("meiqihkrq1")
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1")
														.setValue(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2")
														.setValue(true);
												Ext.getCmp("jixizq2")
														.setValue(false);
												Ext.getCmp("jixizq3")
														.setValue(false);
												Ext.getCmp("jixizq4")
														.setValue(false);
												Ext.getCmp("jixizq6")
														.setValue(false);
												this
														.getCmpByName('bpProductParameter.isStartDatePay')
														.setValue("2");
											} else {
												Ext.getCmp("meiqihkrq1")
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(false);
												if (Ext.getCmp("meiqihkrq1")
														.getValue() == true) {
													this
															.getCmpByName('bpProductParameter.payintentPerioDate')
															.setDisabled(false);
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
									name : 'z1',
									id : "jixizq2",
									inputValue : false,
									checked : true,
									anchor : "100%",
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("monthPay");
												Ext.getCmp("jixizq1")
														.setValue(false);
												Ext.getCmp("jixizq3")
														.setValue(false);
												Ext.getCmp("jixizq4")
														.setValue(false);
												Ext.getCmp("jixizq6")
														.setValue(false);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : "bpProductParameter.payaccrualType",
									value : "monthPay"
								}]
							}, {
								columnWidth : 0.12,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '季',
									name : 'z1',
									id : "jixizq3",
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("seasonPay");
												Ext.getCmp("jixizq1")
														.setValue(false);
												Ext.getCmp("jixizq2")
														.setValue(false);
												Ext.getCmp("jixizq4")
														.setValue(false);
												Ext.getCmp("jixizq6")
														.setValue(false);
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
									name : 'z1',
									id : "jixizq4",
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("yearPay");
												Ext.getCmp("jixizq1")
														.setValue(false);
												Ext.getCmp("jixizq3")
														.setValue(false);
												Ext.getCmp("jixizq2")
														.setValue(false);
												Ext.getCmp("jixizq6")
														.setValue(false);
											}
										}
									}
								}]
							}, {
								columnWidth : .13,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '自定义周期',
									name : 'z1',
									id : "jixizq6",
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('bpProductParameter.payaccrualType')
														.setValue("owerPay");
												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(false);
												Ext.getCmp("meiqihkrq1")
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1")
														.setValue(false);
												Ext.getCmp("meiqihkrq2")
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2")
														.setValue(true);
												Ext.getCmp("jixizq1")
														.setValue(false);
												Ext.getCmp("jixizq3")
														.setValue(false);
												Ext.getCmp("jixizq4")
														.setValue(false);
												Ext.getCmp("jixizq2")
														.setValue(false);
											} else {
												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('bpProductParameter.dayOfEveryPeriod')
														.setValue("");
												if (Ext.getCmp("jixizq1")
														.getValue() == false) {
													Ext.getCmp("meiqihkrq1")
															.setDisabled(false);
													Ext.getCmp("meiqihkrq2")
															.setDisabled(false);
													if (Ext
															.getCmp("meiqihkrq1")
															.getValue() == true) {
														this
																.getCmpByName('bpProductParameter.payintentPerioDate')
																.setDisabled(false);
													}
												}
											}
										}
									}
								}]
							}, {
								columnWidth : 0.08,
								labelWidth : 1,
								layout : 'form',
								items : [{
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'bpProductParameter.dayOfEveryPeriod'
								}]
							}, {
								columnWidth : 0.07,
								labelWidth : 40,
								layout : 'form',
								items : [{
											fieldLabel : "日/期",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 85,
						labelAlign : 'right',
						items : [{
									fieldLabel : "贷款期限 ",
									xtype : "combo",
									readOnly : this.isAllReadOnly,
									hiddenName : "bpProductParameter.payintentPeriod",
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : obpayintentPeriods
											}),
									triggerAction : "all",
									anchor : "100%"
								}]
					}, {
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "期",
									labelSeparator : '',
									anchor : "100%"
								}]
					}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '还款选项'
										}]
							}, {
								columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "前置付息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isPreposePayAccrualCheck",
									checked : this.record == null
											|| this.record.data.isPreposePayAccrual == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this
														.getCmpByName('bpProductParameter.isPreposePayAccrual')
														.setValue(1);
											} else {
												this
														.getCmpByName('bpProductParameter.isPreposePayAccrual')
														.setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'bpProductParameter.isPreposePayAccrual',
									value : 0
								}]
							}, {
								columnWidth : .16, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 1,
								items : [{
									xtype : "checkbox",
									boxLabel : "一次性支付全部利息",
									disabled : this.isAllReadOnly,
									anchor : "100%",
									name : "isInterestByOneTimeCheck",
									checked : this.record == null
											|| this.record.data.isInterestByOneTime == 0
											? null
											: true,
									listeners : {
										scope : this,
										'check' : function(box, value) {
											if (value == true) {
												this
														.getCmpByName('bpProductParameter.isInterestByOneTime')
														.setValue(1);
											} else {
												this
														.getCmpByName('bpProductParameter.isInterestByOneTime')
														.setValue(0);
											}
										}
									}
								}, {
									xtype : 'hidden',
									name : 'bpProductParameter.isInterestByOneTime',
									value : 0
								}]
							}, {
								columnWidth : .6,
								name : "mqhkri",
								layout : "column",
								items : [{
									columnWidth : 0.27,
									labelWidth : 80,
									layout : 'form',
									items : [{
										xtype : 'radio',
										fieldLabel : '每期还款日',
										boxLabel : '固定在',
										name : 'q1',
										id : "meiqihkrq1",
										inputValue : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext
														.getCmp("meiqihkrq1")
														.getValue();
												if (flag == true) {
													this
															.getCmpByName('bpProductParameter.isStartDatePay')
															.setValue("1");
													this
															.getCmpByName('bpProductParameter.payintentPerioDate')
															.setDisabled(false);
												}
											}
										}
									}, {
										xtype : "hidden",
										name : "bpProductParameter.isStartDatePay"
									}]
								}, {
									columnWidth : 0.1,
									labelWidth : 1,
									layout : 'form',
									items : [{
										readOnly : this.isAllReadOnly,
										xtype : 'combo',
										mode : 'local',
										disabled : true,
										displayField : 'name',
										valueField : 'id',
										editable : true,
										store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : obstorepayintentPeriod
												}),
										triggerAction : "all",
										hiddenName : "bpProductParameter.payintentPerioDate",
										fieldLabel : "",
										anchor : '100%'
									}]
								}, {
									columnWidth : 0.12,
									labelWidth : 45,
									layout : 'form',
									items : [{
												fieldLabel : "号还款",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.45,
									labelWidth : 10,
									layout : 'form',
									items : [{
										xtype : 'radio',
										boxLabel : '按实际放款日对日还款',
										name : 'q1',
										id : "meiqihkrq2",
										inputValue : true,
										checked : true,
										anchor : "100%",
										disabled : this.isAllReadOnly,
										listeners : {
											scope : this,
											check : function(obj, checked) {
												var flag = Ext
														.getCmp("meiqihkrq2")
														.getValue();
												if (flag == true) {
													this
															.getCmpByName('bpProductParameter.isStartDatePay')
															.setValue("2");
													this
															.getCmpByName('bpProductParameter.payintentPerioDate')
															.setValue(null);
													this
															.getCmpByName('bpProductParameter.payintentPerioDate')
															.setDisabled(true);
												}
											}
										}
									}]
								}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '贷款利率'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.yearAccrualRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									decimalPrecision : 8,
									anchor : '100%',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'blur' : function(nf) {
											var accrualmonth = this
													.getCmpByName('bpProductParameter.accrual')
											var dayAccrualRate = this
													.getCmpByName('bpProductParameter.dayAccrualRate')
											var payintentPeriod = this
													.getCmpByName('bpProductParameter.payintentPeriod');
											accrualmonth.setValue(nf.getValue()
													/ 12);
											dayAccrualRate.setValue(nf
													.getValue()
													/ 360);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlign : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.accrual",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									anchor : '100%',
									decimalPrecision : 8,
									value : 0,
									listeners : {
										scope : this,
										'blur' : function(nf) {
											var yearAccrualRate = this
													.getCmpByName('bpProductParameter.yearAccrualRate')
											var dayAccrualRate = this
													.getCmpByName('bpProductParameter.dayAccrualRate')
											var payintentPeriod = this
													.getCmpByName('bpProductParameter.payintentPeriod');
											yearAccrualRate.setValue(nf
													.getValue()
													* 12);
											dayAccrualRate.setValue(nf
													.getValue()
													/ 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlgin : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.dayAccrualRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'blur' : function(nf) {
											var accrualmonth = this
													.getCmpByName('bpProductParameter.accrual')
											var yearAccrualRate = this
													.getCmpByName('bpProductParameter.yearAccrualRate')
											var payintentPeriod = this
													.getCmpByName('bpProductParameter.payintentPeriod');
											yearAccrualRate.setValue(nf
													.getValue()
													* 360);
											accrualmonth.setValue(nf.getValue()
													* 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '管理咨询费率'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.yearManagementConsultingOfRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this
													.getCmpByName('bpProductParameter.managementConsultingOfRate')
											var dayAccrualRatenf = this
													.getCmpByName('bpProductParameter.dayManagementConsultingOfRate')
											accrualnf.setValue(nf.getValue()
													/ 12);
											dayAccrualRatenf.setValue(nf
													.getValue()
													/ 360);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlign : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.managementConsultingOfRate",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									anchor : '100%',
									decimalPrecision : 8,
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var yearAccrualRatenf = this
													.getCmpByName('bpProductParameter.yearManagementConsultingOfRate')
											var dayAccrualRatenf = this
													.getCmpByName('bpProductParameter.dayManagementConsultingOfRate')
											yearAccrualRatenf.setValue(nf
													.getValue()
													* 12);
											dayAccrualRatenf.setValue(nf
													.getValue()
													/ 30);
										}
									}

								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.dayManagementConsultingOfRate",
									readOnly : this.isAllReadOnly,
									anchor : '100%',
									decimalPrecision : 8,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this
													.getCmpByName('bpProductParameter.managementConsultingOfRate')
											var yearAccrualRatenf = this
													.getCmpByName('bpProductParameter.yearManagementConsultingOfRate')
											yearAccrualRatenf.setValue(nf
													.getValue()
													* 360);
											accrualnf.setValue(nf.getValue()
													* 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .1,
								layout : 'form',
								labelWidth : 85,
								labelAlign : 'right',
								items : [{
											fieldLabel : '财务服务费率'
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "年化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.yearFinanceServiceOfRate",
									readOnly : this.isAllReadOnly,
									decimalPrecision : 8,
									anchor : '100%',
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this
													.getCmpByName('bpProductParameter.financeServiceOfRate')
											var dayAccrualRatenf = this
													.getCmpByName('bpProductParameter.dayFinanceServiceOfRate')
											accrualnf.setValue(nf.getValue()
													/ 12);
											dayAccrualRatenf.setValue(nf
													.getValue()
													/ 360);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "月化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									name : "bpProductParameter.financeServiceOfRate",
									readOnly : this.isAllReadOnly,
									decimalPrecision : 8,
									anchor : '100%',
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var yearAccrualRatenf = this
													.getCmpByName('bpProductParameter.yearFinanceServiceOfRate')
											var dayAccrualRatenf = this
													.getCmpByName('bpProductParameter.dayFinanceServiceOfRate')
											yearAccrualRatenf.setValue(nf
													.getValue()
													* 12)
											dayAccrualRatenf.setValue(nf
													.getValue()
													/ 30)
										}
									}

								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
								columnWidth : .07,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : "日化利率",
											labelSeparator : ''
										}]
							}, {
								columnWidth : .15,
								layout : 'form',
								items : [{
									hideLabel : true,
									xtype : 'numberfield',
									anchor : '100%',
									name : "bpProductParameter.dayFinanceServiceOfRate",
									readOnly : this.isAllReadOnly,
									decimalPrecision : 8,
									fieldClass : 'field-align',
									style : {
										imeMode : 'disabled'
									},
									value : 0,
									listeners : {
										scope : this,
										'change' : function(nf) {
											var accrualnf = this
													.getCmpByName('bpProductParameter.financeServiceOfRate')
											var yearAccrualRatenf = this
													.getCmpByName('bpProductParameter.yearFinanceServiceOfRate')
											yearAccrualRatenf.setValue(nf
													.getValue()
													* 360);
											accrualnf.setValue(nf.getValue()
													* 30);
										}
									}
								}]
							}, {
								columnWidth : .05,
								layout : 'form',
								labelWidth : 20,
								labelAlgin : 'left',
								items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							}]
				}]
			}]
		});
	}
});

// 实实在在启动页面中个人客户信息
ExtUD.Ext.PersonMainInfoPanelSSZZ = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isWAD:true,
	
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isWAD) != "undefined") {
			this.isWAD = _cfg.isWAD;
		}
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.PersonMainInfoPanelSSZZ.superclass.constructor.call(this, {
			items : [{
						xtype : 'hidden',
						name : 'preHandler',
						value : 'creditProjectService.startCreditFlowProjectSSZZ'
					}, {
						columnWidth : 1,
						layout : "form",
						style : 'padding-left:17px',
						scope : this,
						items : [{
							layout : "form", // 从上往下的布局
							xtype : 'fieldset',
							title : '客户基本信息',
							items : [{
								layout : "column",
								defaults : {
									anchor : '100%',
									isFormField : true,
									labelWidth : 60
								},
								items : [{
											xtype : 'hidden',
											name : 'person.id',
											value : 0
										}, {
											columnWidth : .33, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 70,
											items : [{
												xtype : 'combo',
												triggerClass : 'x-form-search-trigger',
												fieldLabel : "客户姓名",
												name : "person.name",
												readOnly : this.isPersonNameReadOnly,
												allowBlank : false,
												blankText : "客户名称不能为空，请正确填写!",
												anchor : "100%",
												onTriggerClick : function() {
													var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
													var selectCusNew = function(
															obj) {
														op
																.getCmpByName('person.id')
																.setValue("");
														op
																.getCmpByName('person.name')
																.setValue("");
														op
																.getCmpByName('person.cardtype')
																.setValue("");
														op
																.getCmpByName('person.cardnumber')
																.setValue("");

														if (obj.id != 0
																&& obj.id != "") {
															op
																	.getCmpByName('person.id')
																	.setValue(obj.id);
														}
														if (obj.name != 0
																&& obj.name != "") {
															op
																	.getCmpByName('person.name')
																	.setValue(obj.name);
														}
														if (obj.cardtype != 0
																&& obj.cardtype != "") {
															op
																	.getCmpByName('person.cardtype')
																	.setValue(obj.cardtype);
														}
														if (obj.cardnumber != 0
																&& obj.cardnumber != "") {
															op
																	.getCmpByName('person.cardnumber')
																	.setValue(obj.cardnumber);
														}
													}
													selectPWName(selectCusNew);
												},
												resizable : true,
												mode : 'romote',
												lazyInit : false,
												typeAhead : true,
												minChars : 1,
												triggerAction : 'all',
												listeners : {
													scope : this,
													'select' : function(combo,
															record, index) {
														selectCusCombo(record);
													},
													'blur' : function(f) {
													},
													'change' : function(combox,
															record, index) {
														var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
														ressetProjuect(obj_n);
													}
												}
											}]
										}, {
											columnWidth : .33, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 60,
											items : [{
												xtype : "diccombo",
												hiddenName : "person.cardtype",
												itemName : '证件类型', // xx代表分类名称
												fieldLabel : "证件类型",
												readOnly : this.isAllReadOnly,
												allowBlank : this.allowALLblank,
												editable : false,
												emptyText : "请选择",
												blankText : "证件类型不能为空，请正确填写!",
												anchor : "100%",
												listeners : {
													scope : this,
													afterrender : function(
															combox) {
														combox.clearInvalid();
														var st = combox
																.getStore();
														st.on("load",
																function() {
																	combox
																			.setValue(combox
																					.getValue());
																})
													}
												}

											}]
										}, {
											columnWidth : .33, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 60,
											items : [{
												xtype : "textfield",
												name : "person.cardnumber",
												allowBlank : false,
												fieldLabel : "证件号码",
												readOnly : this.isAllReadOnly,
												readOnly : this.isAllReadOnly,
												blankText : "证件号码不能为空，请正确填写!",
												anchor : "100%",
												listeners : {
													scope : this,
													'blur' : function(tf) {
														var cardNum = tf
																.getValue();
														var personId = this
																.getCmpByName("person.id")
																.getValue();
														Ext.Ajax.request({
															url : __ctxPath
																	+ '/credit/customer/person/verificationPerson.do',
															method : 'POST',
															params : {
																cardNum : cardNum,
																personId : personId
															},
															success : function(
																	response,
																	request) {
																var obj = Ext.util.JSON
																		.decode(response.responseText);
																if (obj.msg = "false") {
																	Ext.ux.Toast
																			.msg(
																					'操作信息',
																					"该证件号码已存在，请重新输入");
																	tf
																			.setValue("")
																}
															}
														});
													},
													'blur' : function(com) {
														if (this
																.getCmpByName('person.cardtype')
																.getValue() == 309) {
															if (validateIdCard(com
																	.getValue()) == 1) {
																Ext.Msg
																		.alert(
																				'身份证号码验证',
																				'证件号码不正确,请仔细核对')
																return;
															} else if (validateIdCard(com
																	.getValue()) == 2) {
																Ext.Msg
																		.alert(
																				'身份证号码验证',
																				'证件号码地区不正确,请仔细核对')
																return;
															} else if (validateIdCard(com
																	.getValue()) == 3) {
																Ext.Msg
																		.alert(
																				'身份证号码验证',
																				'证件号码生日日期不正确,请仔细核对')
																return;
															}

														}
														var cardNum = com
																.getValue();
														var personId = this
																.getCmpByName("person.id")
																.getValue();
														Ext.Ajax.request({
															url : __ctxPath
																	+ '/creditFlow/customer/person/verificationPerson.do',
															method : 'POST',
															params : {
																cardNum : cardNum,
																personId : personId
															},
															success : function(
																	response,
																	request) {
																var obj = Ext.util.JSON
																		.decode(response.responseText);
																if (obj.msg == false) {
																	Ext.ux.Toast
																			.msg(
																					'操作信息',
																					"该证件号码已存在，请重新输入");
																	com
																			.setValue("")
																}
															}
														});
													}
												}
											}]
										},{

											columnWidth : .33, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 70,
											hidden:this.isWAD,
											items : [{
												labelWidth : 70,
												xtype : "combo",
												hidden:this.isWAD,
												disabled:this.isWAD,
												mode : 'local',
												editable : false,
												lazyInit : false,
												allowBlank:false,
												triggerAction : 'all',
												hiddenName : "slSmallloanProject.productId",
												displayField:'productName',
												valueField:'id',
												fieldLabel : "产品类型",
												editable : false,
												anchor :"100%",
												store : new Ext.data.JsonStore({
													url : __ctxPath + "/system/listBpProductParameter.do",
													totalProperty : 'totalCounts',
													autoLoad : true,
													root : 'result',
													fields : [{
																name : 'id'
															}, {
																name : 'productName'
															}]													
												}),
												listeners : {
													scope : this,
													afterrender : function(combox) {
														var st = combox.getStore();
														st.on("load", function() {
																	combox.setValue(combox.getValue());
																	combox.clearInvalid();
																})
															
													}
												}
												
											}]
										
										}]
							}]
						}]
					}]
		});
	}
});

// 速贷的客户信息
ExtUD.Ext.CustomerInfoFastPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isEditPerson : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (_cfg.isPersonNameReadOnly) {
			this.isPersonNameReadOnly = _cfg.isPersonNameReadOnly;
		}
		if (_cfg.isHiddenCustomerDetailBtn) {
			this.isHiddenCustomerDetailBtn = _cfg.isHiddenCustomerDetailBtn;
		}
		if (typeof(_cfg.isEditPerson) != "undefined") {
			this.isEditPerson = _cfg.isEditPerson;
		}
		if (typeof(_cfg.isNameEdit) != "undefined") {
			this.isNameEdit = _cfg.isNameEdit;
		} 
		Ext.applyIf(this, _cfg);
		ExtUD.Ext.CustomerInfoFastPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
							xtype : 'hidden',
							name : 'person.id',
							value : this.personId
						}, {
							columnWidth : .3,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'combo',
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "客户姓名",
								name : "person.name",
								editable : this.isNameEdit,
								readOnly : true,
								allowBlank : false,
								blankText : "客户姓名不能为空，请正确填写!",
								anchor : "100%",
								scope : this,
								onTriggerClick : function() {
									var op = this.ownerCt.ownerCt;
									var selectCustomer = function(obj) {
										op.getCmpByName('person.id')
												.setValue("");
										op.getCmpByName('person.name')
												.setValue("");
										op.getCmpByName('person.sex')
												.setValue("");
										op.getCmpByName('person.cardtype')
												.setValue("");
										op.getCmpByName('person.cardnumber')
												.setValue("");
										op.getCmpByName('person.marry')
												.setValue("");
										op.getCmpByName('person.cellphone')
												.setValue("");
										op.getCmpByName('person.birthday')
												.setValue("");
										op.getCmpByName('person.postcode')
												.setValue("");
										op.getCmpByName('person.postaddress')
												.setValue("");
										op
												.getCmpByName('person.currentcompany')
												.setValue("");
										op.getCmpByName('person.unitaddress')
												.setValue("");
										op.getCmpByName("person.familyaddress")
												.setValue("");
										op.getCmpByName('person.hukou')
												.setValue("");
										// op.getCmpByName('enterpriseBank.bankname').setValue("");
										op
												.getCmpByName('enterpriseBank.accountnum')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.openType')
												.setValue("");
										op.getCmpByName('enterpriseBank.name')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.accountType')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.bankid')
												.setValue("");
										op.getCmpByName('enterpriseBank.id')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.areaId')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.areaName')
												.setValue("");
										op
												.getCmpByName('enterpriseBank.bankOutletsId')
												.setValue("");
										op
												.getCmpByName("person.familypostcode")
												.setValue("");// 家庭邮编
										op.getCmpByName("person.telphone")
												.setValue("");// 住宅电话
										op.getCmpByName("person.homeshape")
												.setValue("");// 居住地方式
										op
												.getCmpByName("person.homecreditexpend")
												.setValue("");// 月还款额
										op.getCmpByName("person.homeexpend")
												.setValue("");// 家庭月支出
										op.getCmpByName("person.jobincome")
												.setValue("");// 个人月收入
										op.getCmpByName("person.selfemail")
												.setValue("");// 电子邮箱

										if (obj.telphone != 0
												&& obj.telphone != "")
											op.getCmpByName('person.telphone')
													.setValue(obj.telphone);
										if (obj.familypostcode != 0
												&& obj.familypostcode != "")
											op
													.getCmpByName('person.familypostcode')
													.setValue(obj.familypostcode);
										if (obj.homeshape != 0
												&& obj.homeshape != "")
											op.getCmpByName('person.homeshape')
													.setValue(obj.homeshape);
										if (obj.homecreditexpend != 0
												&& obj.homecreditexpend != "")
											op
													.getCmpByName('person.homecreditexpend')
													.setValue(obj.homecreditexpend);
										if (obj.homeexpend != 0
												&& obj.homeexpend != "")
											op
													.getCmpByName('person.homeexpend')
													.setValue(obj.homeexpend);
										if (obj.jobincome != 0
												&& obj.jobincome != "")
											op.getCmpByName('person.jobincome')
													.setValue(obj.jobincome);
										if (obj.selfemail != 0
												&& obj.selfemail != "")
											op.getCmpByName('person.selfemail')
													.setValue(obj.selfemail);

										if (obj.id != 0 && obj.id != "")
											op.getCmpByName('person.id')
													.setValue(obj.id);
										if (obj.name != 0 && obj.name != "")
											op.getCmpByName('person.name')
													.setValue(obj.name);
										if (obj.sex != 0 && obj.sex != "")
											op.getCmpByName('person.sex')
													.setValue(obj.sex);
										if (obj.cardtype != 0
												&& obj.cardtype != "")
											op.getCmpByName('person.cardtype')
													.setValue(obj.cardtype);
										if (obj.cardnumber != 0
												&& obj.cardnumber != "")
											op
													.getCmpByName('person.cardnumber')
													.setValue(obj.cardnumber);
										if (obj.marry != 0 && obj.marry != "")
											op.getCmpByName('person.marry')
													.setValue(obj.marry);
										if (obj.cellphone != 0
												&& obj.cellphone != "")
											op.getCmpByName('person.cellphone')
													.setValue(obj.cellphone);
										if (obj.birthday != 0
												&& obj.birthday != "")
											op.getCmpByName('person.birthday')
													.setValue(obj.birthday);
										if (obj.familypostcode != 0
												&& obj.familypostcode != "")
											op
													.getCmpByName('person.postcode')
													.setValue(obj.familypostcode);
										if (obj.postaddress != 0
												&& obj.postaddress != "")
											op
													.getCmpByName('person.postaddress')
													.setValue(obj.postaddress);
										if (obj.currentcompany != 0
												&& obj.currentcompany != "")
											op
													.getCmpByName('person.currentcompany')
													.setValue(obj.currentcompany);
										if (obj.unitaddress != 0
												&& obj.unitaddress != "")
											op
													.getCmpByName('person.unitaddress')
													.setValue(obj.unitaddress);
										if (obj.familyaddress != 0
												&& obj.familyaddress != "")
											op
													.getCmpByName('person.familyaddress')
													.setValue(obj.familyaddress);
										if (obj.hukou != 0 && obj.hukou != "")
											op.getCmpByName('person.hukou')
													.setValue(obj.hukou);

										if (obj.bankNum != 0
												&& obj.bankNum != "")
											op
													.getCmpByName('enterpriseBank.accountnum')
													.setValue(obj.bankNum);

										op
												.getCmpByName('enterpriseBank.openType')
												.setValue(obj.openType);

										if (obj.khname != 0 && obj.khname != "")
											op
													.getCmpByName('enterpriseBank.name')
													.setValue(obj.khname);
										op
												.getCmpByName('enterpriseBank.accountType')
												.setValue(obj.accountType);
										if (obj.bankId != "")
											op
													.getCmpByName('enterpriseBank.bankid')
													.setValue(obj.bankId);
										if (obj.enterpriseBankId != "")
											op
													.getCmpByName('enterpriseBank.id')
													.setValue(obj.enterpriseBankId);
										op
												.getCmpByName('enterpriseBank.areaId')
												.setValue(obj.areaId);
										op
												.getCmpByName('enterpriseBank.areaName')
												.setValue(obj.areaName);
										op
												.getCmpByName('enterpriseBank.bankOutletsId')
												.setValue(obj.bankOutletsId);
									}
									selectPWName(selectCustomer);
								},
								resizable : true,
								mode : 'romote',
								lazyInit : false,
								typeAhead : true,
								minChars : 1,
								store : new Ext.data.JsonStore({}),
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								listeners : {
									scope : this,
									'select' : function(combo, record, index) {
									},
									'blur' : function(f) {

									}

								}
							}, {
								xtype : "dickeycombo",
								nodeKey : 'card_type_key',
								hiddenName : "person.cardtype",
								itemName : '证件类型', // xx代表分类名称
								fieldLabel : "证件类型",
								allowBlank : false,
								editable : true,
								readOnly : this.isAllReadOnly,
								blankText : "证件类型不能为空，请正确填写!",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}

							},{
								xtype : 'datefield',
								fieldLabel : '出生日期',
								name : 'person.birthday',
								//allowBlank : false,
								format : 'Y-m-d',
								// allowBlank:false,
								readOnly : this.isAllReadOnly

							}, {
								xtype : "dickeycombo",
								nodeKey : 'dgree',
								fieldLabel : '最高学历',
								hiddenName : 'person.dgree',
								emptyText : '请选择学历',
//								allowBlank : false,
								blankText : "客户学历不能为空，请正确填写!",
								width : 80,
								editable : false,
								value : personData == null
										? null
										: personData.dgree,
								readOnly : this.isRead,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							},{
								xtype : "numberfield",
								name : "person.qq",
								fieldLabel : "QQ",
								readOnly : this.isRead,
								regex : /^\d{5,10}$/,
								regexText : 'QQ号格式不正确',
								value : personData == null
										? null
										: personData.qq
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 85 + 30,
							defaults : {
								anchor : '90%'
							},
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'sex_key',
								hiddenName : 'person.sex',
								fieldLabel : "客户性别",
								allowBlank : true,
								editable : true,
								blankText : "客户性别不能为空，请正确填写!",
								readOnly : this.isAllReadOnly,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							}, {
								xtype : 'textfield',
								fieldLabel : '证件号码',
								name : 'person.cardnumber',
								allowBlank : false,
								blankText : '证件号码为必填内容',
								readOnly : this.isAllReadOnly,
								listeners : {
									scope : this,
									'blur' : function(tf) {
										if (this.getCmpByName('person.cardtype').getValue() == 309) {
											if (validateIdCard(tf.getValue()) == 1) {
												Ext.Msg.alert('身份证号码验证',
														'证件号码不正确,请仔细核对')
												return;
											} else if (validateIdCard(tf
													.getValue()) == 2) {
												Ext.Msg.alert('身份证号码验证',
														'证件号码地区不正确,请仔细核对')
												return;
											} else if (validateIdCard(tf
													.getValue()) == 3) {
												Ext.Msg.alert('身份证号码验证',
														'证件号码生日日期不正确,请仔细核对')
												return;
											}
										}
										var cardNum = tf.getValue();
										var personId = this.getCmpByName("person.id").getValue();
										var penal=this.getCmpByName("person.birthday");
										var penalAge=this.getCmpByName("person.age");
										var sex=this.getCmpByName("person.sex");
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/person/verificationPerson.do',
											method : 'POST',
											params : {
												cardNum : cardNum,
												personId : personId
											},
											success : function(response,request) {
												var obj = Ext.util.JSON.decode(response.responseText);
												if (obj.msg == false) {
													Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
													tf.setValue("")
												}else{
													if(cardNum.split("").reverse()[1]%2==0){
			                            				sex.setValue(313);
			                            				sex.setRawValue("女")
			                            			}else{
			                            				sex.setValue(312);
			                            				sex.setRawValue("男")
			                            			}
													var brithday= cardNum.substr(6,8);
													var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
			                            			penal.setValue(formatBrithday)//出生日期
			                            			var nowDate=new Date();
			                            			var nowYear=Ext.util.Format.date(nowDate,'Y-m-d').substr(0,4);
			                            			var brith=brithday.substr(0,4);
			                            			penalAge.setValue(Number(nowYear)-Number(brith))//年龄
												}
											}
										});
									},
									afterrender : function(v){//渲染时，如果cardNumber不为null，自动计算出生日期和生日
							        	if(this.getCmpByName('person.cardtype').getValue() == 309){
								        	var cardNum = v.getValue();
								        	var penal=this.getCmpByName("person.birthday");
											var penalAge=this.getCmpByName("person.age");
											if(null != cardNum&& '' !=cardNum && !this.isAllReadOnly){
												var brithday= cardNum.substr(6,8);
												var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
		                            			penal.setValue(formatBrithday)//出生日期
		                            			var nowDate=new Date();
		                            			var nowYear=Ext.util.Format.date(nowDate,'Y-m-d').substr(0,4);
		                            			var brith=brithday.substr(0,4);
		                            			penalAge.setValue(Number(nowYear)-Number(brith))//年龄
											}
							        	}
									}

								}

							},{
								xtype : "numberfield",
								name : "person.age",
								fieldLabel : "年龄",
								allowBlank : true,
								blankText : "客户年龄不能为空，请正确填写!",
								readOnly : this.isRead,
								value : personData == null ? null : personData.age
							}, {
								xtype : 'numberfield',
								fieldLabel : '信用卡最高额度',
								name : 'person.befMonthBalance',
								allowBlank : true,
								readOnly : this.isAllReadOnly
							},{
								xtype : "textfield",
								name : "person.microMessage",
								fieldLabel : "微信",
								readOnly : this.isRead,
								regex : /^[0-9a-zA-Z]{5,20}$/,
								regexText : '微信号格式不正确',
								value : personData == null
										? null
										: personData.microMessage
							}]
						}, {
							columnWidth : .34,
							layout : 'form',
							defaults : {
								anchor : '100%',
								labelWidth : 85 + 40
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : '手机号码',
										name : 'person.cellphone',
										allowBlank : false,
										readOnly : this.isAllReadOnly,
										regex : /^[1][34578][0-9]{9}$/,
										regexText : '手机号码格式不正确'
									},{
										xtype : 'datefield',
										fieldLabel : '证件有效期至',
										name : 'person.validity',
										// allowBlank : false,
										format : 'Y-m-d',
										// allowBlank:false,
										readOnly : this.isAllReadOnly
						
									}, {
										xtype : "dickeycombo",
										nodeKey : '8',
										hiddenName : 'person.marry',
										fieldLabel : "婚姻状况",
										itemName : '婚姻状况', // xx代表分类名称
//										allowBlank : false,
										editable : true,
										blankText : "婚姻状况不能为空，请正确填写!",
										readOnly : this.isAllReadOnly,
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
												if (combox.getValue() == 0 
													|| combox .getValue() == 1
													|| combox .getValue() == ""
													|| combox .getValue() == null) {
														combox.setValue("");
												} else {
													combox.setValue(combox.getValue());
												}
													combox.clearInvalid();
												})
											},
											loadData : function(com){
												if(com.getValue()==317){
													var spouse={
														xtype : 'fieldset',
														title : '配偶信息',
														bodyStyle : 'padding-left:0px',
														collapsible : true,
														labelAlign : 'right',
														name : 'spousePanel',
														autoHeight : true,
														hidden:this.personHidden,
														items : [new SpousePanel({spouseHidden:true,isReadOnly:this.isAllReadOnly})]
													}
													this.add(spouse)
													this.doLayout();
												}
											},
											'select' : function(combox) {
												if (combox.getValue() == 317) {
													if (this.getCmpByName('spousePanel')== null) {
														var spouse={
															xtype : 'fieldset',
															title : '配偶信息',
															bodyStyle : 'padding-left:0px',
															collapsible : true,
															labelAlign : 'right',
															name : 'spousePanel',
															autoHeight : true,
															hidden:this.personHidden,
															items : [new SpousePanel({spouseHidden:false,isReadOnly:this.isAllReadOnly})]
														}
														this.add(spouse)
														this.doLayout();
													}
												} else {
													if (this.getCmpByName('spousePanel') != null) {
														this.getCmpByName('spousePanel').destroy()
													}
												}
											}
										}

									},{
										xtype : 'textfield',
										fieldLabel : '子女个数',
										name : 'person.childrenCount',
										labelWidth : 40,
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.childrenCount
									},{
										xtype : 'textfield',
										fieldLabel : '住宅电话',
										name : 'person.telphone',
										// allowBlank : false,
										readOnly : this.isAllReadOnly,
										// regex : /^[1][358][0-9]{9}$/,
										regexText : '手机号码格式不正确'
									}

							]
						}]
			}, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
					columnWidth : .3,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					items : [ {
						xtype : 'textfield',
						fieldLabel : App.isCustomized4DLTC() ? "通讯地址" : '现家庭住址',
						name : App.isCustomized4DLTC()
								? 'person.postaddress'
								: 'person.familyaddress',
//						allowBlank : false,
						readOnly : this.isAllReadOnly

					},  {
						xtype : "combo",
						fieldLabel : '户口所在地',
						readOnly : this.isAllReadOnly,
						hiddenName : "person.parenthukou",
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						allowBlank : true,
						store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
									fields : ['itemId', 'itemName'],
									baseParams:{parentId:6591}
								}),
								fieldLabel : "户口所在地:省",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
										//	 
													combox.setValue(combox.getValue());
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
									//	 
										var v = record.data.itemId;
									var arrStore = new Ext.data.SimpleStore({
										url : __ctxPath
												+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
										fields : ['itemId', 'itemName'],
										baseParams:{parentId:v}
									})
									var opr_obj = this.getCmpByName('person.hukou')
									opr_obj.clearValue();
									opr_obj.store = arrStore;
									arrStore.load({
												"callback" : test
											});
									function test(r) {
										if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
											opr_obj.view.setStore(arrStore);
										}
										
									}
									}
								}
					}]

				}, {
					columnWidth : .33,
					layout : 'form',
					labelWidth : 85 + 30,
					defaults : {
						anchor : '90%'
					},
					items : [ {
						xtype : 'textfield',
						fieldLabel : '邮政编码',
						name : 'person.postcode',
						allowBlank : true,
						regex : /^[0-9]{6}$/,
						regexText : '邮政编码格式不正确',
						readOnly : this.isAllReadOnly
					}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth :115,
							items : [{
										xtype : "combo",
										anchor : "100%",
										hiddenName : "person.hukou",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										readOnly : this.isRead,
										store : new Ext.data.SimpleStore({
											url : __ctxPath
													+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
											fields : ['itemId', 'itemName']
										}),
										fieldLabel : "市",
										listeners : {
											scope : this,
											
											loadData :  function(opr_obj) {
												var v=this.getCmpByName('person.parenthukou').getValue()
												var hukou=this.getCmpByName('person.hukou').getValue()
													var arrStore = new Ext.data.SimpleStore({
														url : __ctxPath
																+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
														fields : ['itemId', 'itemName'],
														baseParams:{parentId:v}
													})
													opr_obj.clearValue();
													opr_obj.store = arrStore;
													arrStore.load({
																"callback" : test
															});
													function test(r) {
														if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
															opr_obj.view.setStore(arrStore);
														}
														opr_obj.setValue(hukou)
													}
											}
										}
							}]
						}]

				}, {
					columnWidth : .34,
					layout : 'form',
					defaults : {
						anchor : '100%',
						labelWidth : 85 + 40
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '电子邮箱',
								name : 'person.selfemail',
								readOnly : this.isRead,
								value : personData == null
										? null
										: personData.selfemail,
								regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
								regexText : '电子邮箱格式不正确',
								listeners : {
									'afterrender' : function(com) {
										com.clearInvalid();
									}
								}
							}, {
								xtype : 'textfield',
								fieldLabel : '每月家庭支出',
								name : 'person.homeexpend',
								allowBlank : true,
								readOnly : this.isAllReadOnly
							}]
				}]
			}, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
					columnWidth : .3,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					items : [{
						xtype : 'textfield',
						fieldLabel : '月还款额',
						name : 'person.homecreditexpend',
						allowBlank : true,
						readOnly : this.isAllReadOnly
					}, {
						xtype : "dickeycombo",
						nodeKey : 'jzzk',
						fieldLabel : '居住状况',
						hiddenName : 'person.employway',
						emptyText : '请选择居住状况',
						editable : false,
						readOnly : this.isRead,
						value : personData == null
								? null
								: personData.employway,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
							}
						}

					}/*, {
						xtype : 'datefield',
						fieldLabel : '来本地时间',
						readOnly : this.isRead,
						format : 'Y-m-d',
						value : personData == null
								? null
								: personData.residenceDate,
						name : 'person.residenceDate'
					}*/]
				}, {
					columnWidth : .33,
					layout : 'form',
					labelWidth : 115,
					defaults : {
						anchor : '90%'
					},
					items : [{
						xtype : 'numberfield',
						fieldLabel : '月租金',
						name : 'person.monthRent',
						readOnly : this.isRead,
						value : personData == null
								? null
								: personData.monthRent
					},{
						xtype : 'textfield',
						fieldLabel : '共同居住者',
						allowBlank : true,
						readOnly : this.isAllReadOnly,
						name : 'person.relationname'
					}]
				}, {
					columnWidth : .34,
					layout : 'form',
					defaults : {
						anchor : '100%',
						labelWidth : 85 + 40
					},
					items : [{
						xtype : 'numberfield',
						fieldLabel : '个人年收入',
						readOnly : this.isAllReadOnly,
						name : 'person.jobYearincom'
					}]
				},{
					columnWidth : .34,
					layout : 'form',
					defaults : {
						anchor : '100%',
						labelWidth : 85 + 40
					},
					items : [{
						xtype : "datefield",
						name : "person.residenceDate",
						fieldLabel : "起始居住时间",
						format : 'Y-m-d',
						readOnly : this.isRead,
						value : personData == null
								? null
								: personData.residenceDate
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 84,
					labelAlign : 'right',
					items : [{
						xtype : "dickeycombo",
						hiddenName : "person.archivesBelonging",
						nodeKey : 'archivesBelonging', // xx代表分类名称
						fieldLabel : "档案归属地",
						readOnly : this.isRead,
						editable : false,
						anchor : "91%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
					       }
						}

					}]
				},{
				columnWidth : .17,
				layout : 'form',
				style : 'margin-left:50px',
				hidden : this.isHiddenCustomerDetailBtn,
				items : [{
					xtype : 'button',
					text : this.isEditPerson ? '编辑客户资料' : '查看客户资料',
					iconCls : 'btn-customer',
					width : 110,
					
					scope : this,
					handler : function() {
						var oppositeId = this.getCmpByName('person.id')
								.getValue();
						var flag = "detail";
						if (this.isEditPerson == true) {
							flag = "edit";
						}
						if (flag == "detail") {
							Ext.Ajax.request({
								url : __ctxPath
										+ '/creditFlow/customer/person/seeInfoPerson.do',
								method : 'POST',
								scope : this,
								success : function(response, request) {
									obj = Ext.util.JSON
											.decode(response.responseText);
									var personData = obj.data;
									var randomId = rand(100000);
									var id = "see_person" + randomId;
									var anchor = '100%';
									var window_see = new Ext.Window({
												title : '查看个人客户详细信息',
												layout : 'fit',
												width : (screen.width - 180)
														* 0.7 + 160,
												maximizable : true,
												height : 460,
												closable : true,
												modal : true,
												plain : true,
												border : false,
												items : [new personObj({
															url : null,
															id : id,
															personData : personData,
															isReadOnly : true
														})],
												listeners : {
													'beforeclose' : function(
															panel) {
														window_see.destroy();
													}
												}
											});
									window_see.show();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试');
								},
								params : {
									id : oppositeId
								}
							});
						} else if (flag == "edit") {
							var customerPanel = this
							Ext.Ajax.request({
								url : __ctxPath
										+ '/creditFlow/customer/person/seeInfoPerson.do',
								method : 'POST',
								scope : this,
								success : function(response, request) {
									obj = Ext.util.JSON
											.decode(response.responseText);
									var personData = obj.data;
									var randomId = rand(100000);
									var id = "update_person" + randomId;
									var url = __ctxPath
											+ '/creditFlow/customer/person/updateInfoPerson.do';
									var window_update = new Ext.Window({
										title : '编辑个人客户详细信息',
										height : 460,
										constrainHeader : true,
										collapsible : true,
										frame : true,
										iconCls : 'btn-edit',
										border : false,
										bodyStyle : 'overflowX:hidden',
										buttonAlign : 'right',
										iconCls : 'newIcon',
										width : (screen.width - 180) * 0.7
												+ 160,
										resizable : true,
										layout : 'fit',
										autoScroll : false,
										constrain : true,
										closable : true,
										modal : true,
										items : [new personObj({
													personData : personData,
													url : url,
													id : id
												})],
										tbar : [new Ext.Button({
													text : '更新',
													tooltip : '更新基本信息',
													iconCls : 'btn-refresh',
													hideMode : 'offsets',
													handler : function() {
														var vDates = "";
														var panel_add = window_update
																.get(0);
														formSavePersonObj1(
																panel_add,
																window_update,
																null,
																customerPanel);
													}
												})],
										listeners : {
											'beforeclose' : function(panel) {
												window_update.destroy();
											}
										}
									});
									window_update.show();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试');
								},
								params : {
									id : oppositeId
								}
							});

						}
					}

				}]

			},{
				columnWidth : .5,
				style : 'margin-left:20px',
				hidden : ((typeof(this.loanId)!='undefined' && this.loanId!=null && this.loanId!='')?false:true),
				items : [{
					xtype : 'button',
					text :  '查看线上用户资料',
					iconCls : 'btn-customer',
					width : 110,
					scope : this,
					handler : function() {
						Ext.Ajax.request({
							url : __ctxPath + '/p2p/getInfoBpFinanceApplyUser.do?loanId='+this.loanId,
							method:'post',
							async:false,
							success: function(resp,opts) {
								var respText = resp.responseText;
								alarm_fields = Ext.util.JSON.decode(respText);
								appUser=alarm_fields.data;
								new BpCustMemberForm({
									memObj:alarm_fields.data.bpCustMember,
									appUser:alarm_fields.data.bpFinanceApplyUser,
									userId:alarm_fields.data.bpCustMember.id,
									userName:alarm_fields.data.bpCustMember.loginname
									
								}).show();
							}
						});
						
					}

				}]
				}]
			}]
		});
	}
})

ExtUD.Ext.ProjectInfoFundPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	idDefinition : 'liucheng',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		this.idDefinition = this.projectId + this.idDefinition;
		var leftlabel = 85;
		var centerlabel = 115;
		var rightlabel = 115;
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i
					+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,
				storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		ExtUD.Ext.ProjectInfoFundPanel.superclass.constructor.call(this, {
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
						fieldLabel : "拟贷款金额",
						fieldClass : 'field-align',
						name : "projectMoney1",
						// id : 'projectMoney1',
						readOnly : this.isAllReadOnly,
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
							change : function(nf) {

								var value = nf.getValue();
								var index = value.indexOf(".");
								if (index <= 0) { // 如果第一次输入 没有进行格式化
									nf.setValue(Ext.util.Format.number(value,
											'0,000.00'))
									this
											.getCmpByName("slSmallloanProject.projectMoney")
											.setValue(value);
								} else {

									if (value.indexOf(",") <= 0) {
										var ke = Ext.util.Format.number(value,
												'0,000.00')
										nf.setValue(ke);
										this
												.getCmpByName("slSmallloanProject.projectMoney")
												.setValue(value);
									} else {
										var last = value.substring(index + 1,
												value.length);
										if (last == 0) {
											var temp = value
													.substring(0, index);
											temp = temp.replace(/,/g, "");
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										} else {
											var temp = value.replace(/,/g, "");
											this
													.getCmpByName("slSmallloanProject.projectMoney")
													.setValue(temp);
											nf.setValue(Ext.util.Format.number(
													temp, '0,000.00'))
										}
									}

								}
							}
						}
					}, {
						xtype : "hidden",
						name : "slSmallloanProject.projectMoney"
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
								hiddenName : 'slSmallloanProject.currency',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '注册资金币种', // xx代表分类名称
								allowBlank : false,
								editable : false,
								blankText : "币种不能为空，请正确填写!",
								nodeKey : 'capitalkind',
								emptyText : "请选择",
								// anchor : "89%",
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
					labelWidth : rightlabel,
					items : [{
						fieldLabel : "期望资金到位日期",
						xtype : "datefield",
						style : {
							imeMode : 'disabled'
						},
						name : "slSmallloanProject.startDate",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						blankText : "放款日期不能为空，请正确填写!",
						// anchor : "89%",
						format : 'Y-m-d',
						listeners : {
							scope : this,
							'change' : function() {
								var payAccrualType = this
										.getCmpByName('slSmallloanProject.payaccrualType')
										.getValue();
								var dayOfEveryPeriod = this
										.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
										.getValue();
								var payintentPeriod = this
										.getCmpByName('slSmallloanProject.payintentPeriod')
										.getValue();
								var startDate = this
										.getCmpByName('slSmallloanProject.startDate')
										.getValue();
								var intentDatePanel = this
										.getCmpByName('slSmallloanProject.intentDate');
								setIntentDate(payAccrualType, dayOfEveryPeriod,
										payintentPeriod, startDate,
										intentDatePanel,this)

							}
						}
					}]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
											fieldLabel : "计息方式 ",
											fieldClass : 'field-align',
											// html :
											// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.12,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '等额本金',
									// fieldLabel : "计息方式",
									name : 'f1',
									id : "jixifs1" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("sameprincipal");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.setDisabled(false);
												this.getCmpByName('mqhkri1')
														.hide();
												this.getCmpByName('mqhkri')
														.show();
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
									id : "jixifs2" + this.idDefinition,
									inputValue : false,
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("sameprincipalandInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");

												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("monthPay");

												this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.setDisabled(false);
												this.getCmpByName('mqhkri1')
														.hide();
												this.getCmpByName('mqhkri')
														.show();
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
									boxLabel : '按期收息,到期还本',
									name : 'f1',
									id : "jixifs3" + this.idDefinition,
									inputValue : false,
									checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("singleInterest");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false)
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false)
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(false);

											}
										}
									}
								}, {
									xtype : "hidden",
									name : "slSmallloanProject.accrualtype",
									value : "singleInterest"

								}]
							}, {
								columnWidth : 0.51,
								labelWidth : 5,
								layout : 'form',
								items : [{
									xtype : 'radio',
									boxLabel : '一次性收取利息',
									name : 'f1',
									id : "jixifs4" + this.idDefinition,
									inputValue : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixifs4"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.accrualtype')
														.setValue("ontTimeAccrual");
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq3"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("jixizq4"
														+ this.idDefinition)
														.setDisabled(true);

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");

												Ext.getCmp("jixizq6"
														+ this.idDefinition)
														.setValue(true);
												Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.setValue(false);
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("owerPay");

												this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.setValue(1);
												this.getCmpByName('mqhkri')
														.hide();
												this.getCmpByName('mqhkri1')
														.show();

											} else {
												this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.setDisabled(false);

												this.getCmpByName('mqhkri1')
														.hide();
												this.getCmpByName('mqhkri')
														.show();

											}
										}
									}
								}, {
									xtype : "hidden",
									name : "slSmallloanProject.payaccrualType",
									value : "monthPay"

								}]
							}]

				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
								columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : leftlabel,
								items : [{
											fieldLabel : "计息周期 ",
											fieldClass : 'field-align',
											// html :
											// "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计息周期:",
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
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq1"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("dayPay");

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);

												this
														.getCmpByName('slSmallloanProject.isStartDatePay')
														.setValue("2");
												var payAccrualType = this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSmallloanProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSmallloanProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)

											} else {

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(false);
												if (Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.getValue() == true) {
													this
															.getCmpByName('slSmallloanProject.payintentPerioDate')
															.setDisabled(false);
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
									id : "jixizq2" + this.idDefinition,
									inputValue : false,
									// checked : true,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq2"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("monthPay");
												var payAccrualType = this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSmallloanProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSmallloanProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)
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
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq3"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("seasonPay");
												var payAccrualType = this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSmallloanProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSmallloanProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)

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
									boxLabel : '年',
									name : 'z1',
									id : "jixizq4" + this.idDefinition,
									inputValue : false,
									anchor : "100%",
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq4"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("yearPay");
												var payAccrualType = this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.getValue();
												var dayOfEveryPeriod = this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.getValue();
												var payintentPeriod = this
														.getCmpByName('slSmallloanProject.payintentPeriod')
														.getValue();
												var startDate = this
														.getCmpByName('slSmallloanProject.startDate')
														.getValue();
												var intentDatePanel = this
														.getCmpByName('slSmallloanProject.intentDate');
												setIntentDate(payAccrualType,
														dayOfEveryPeriod,
														payintentPeriod,
														startDate,
														intentDatePanel,this)

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
									disabled : this.isAllReadOnly,
									listeners : {
										scope : this,
										check : function(obj, checked) {
											var flag = Ext.getCmp("jixizq6"
													+ this.idDefinition)
													.getValue();
											if (flag == true) {
												this
														.getCmpByName('slSmallloanProject.payaccrualType')
														.setValue("owerPay");

												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(false);

												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq1"
														+ this.idDefinition)
														.setValue(false);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setDisabled(true);
												Ext.getCmp("meiqihkrq2"
														+ this.idDefinition)
														.setValue(true);
											} else {
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setDisabled(true);
												this
														.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
														.setValue("");
												if (Ext.getCmp("jixizq1"
														+ this.idDefinition)
														.getValue() == false) {
													Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.setDisabled(false);
													Ext
															.getCmp("meiqihkrq2"
																	+ this.idDefinition)
															.setDisabled(false);
													if (Ext
															.getCmp("meiqihkrq1"
																	+ this.idDefinition)
															.getValue() == true) {
														this
																.getCmpByName('slSmallloanProject.payintentPerioDate')
																.setDisabled(false);
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
									xtype : 'textfield',
									anchor : "100%",
									readOnly : this.isAllReadOnly,
									fieldClass : 'field-align',
									name : 'slSmallloanProject.dayOfEveryPeriod',
									listeners : {
										scope : this,
										'change' : function() {
											var payAccrualType = this
													.getCmpByName('slSmallloanProject.payaccrualType')
													.getValue();
											var dayOfEveryPeriod = this
													.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
													.getValue();
											var payintentPeriod = this
													.getCmpByName('slSmallloanProject.payintentPeriod')
													.getValue();
											var startDate = this
													.getCmpByName('slSmallloanProject.startDate')
													.getValue();
											var intentDatePanel = this
													.getCmpByName('slSmallloanProject.intentDate');
											setIntentDate(payAccrualType,
													dayOfEveryPeriod,
													payintentPeriod, startDate,
													intentDatePanel,this)

										}
									}
								}]
							}, {
								columnWidth : 0.04,
								labelWidth : 20,
								layout : 'form',
								items : [{
											fieldLabel : "天",
											labelSeparator : '',
											anchor : "100%"
										}]
							}

					]
				}, {
					columnWidth : 1,
					layout : 'column',
					items : [{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						items : [{
							fieldLabel : "贷款期限",
							xtype : "textfield",
							fieldClass : 'field-align',
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							name : "slSmallloanProject.payintentPeriod",
							anchor : "100%",
							listeners : {
								scope : this,
								'change' : function() {
									var payAccrualType = this
											.getCmpByName('slSmallloanProject.payaccrualType')
											.getValue();
									var dayOfEveryPeriod = this
											.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
											.getValue();
									var payintentPeriod = this
											.getCmpByName('slSmallloanProject.payintentPeriod')
											.getValue();
									var startDate = this
											.getCmpByName('slSmallloanProject.startDate')
											.getValue();
									var intentDatePanel = this
											.getCmpByName('slSmallloanProject.intentDate');
									setIntentDate(payAccrualType,
											dayOfEveryPeriod, payintentPeriod,
											startDate, intentDatePanel,this)

								}
							}

						}]
					}, {
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "期",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : .45,
						name : "mqhkri",
						layout : "column",
						items : [{
							columnWidth : 0.278,
							labelWidth : 73,
							layout : 'form',
							items : [{
								xtype : 'radio',
								fieldLabel : '每期还款日',
								boxLabel : '固定在',
								name : 'q1',
								id : "meiqihkrq1" + this.idDefinition,
								inputValue : true,
								anchor : "100%",
								disabled : this.isAllReadOnly,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq1"
												+ this.idDefinition).getValue();
										if (flag == true) {
											this
													.getCmpByName('slSmallloanProject.isStartDatePay')
													.setValue("1");
											this
													.getCmpByName('slSmallloanProject.payintentPerioDate')
													.setDisabled(false);
										}
									}
								}

							}, {
								xtype : "hidden",
								name : "slSmallloanProject.isStartDatePay"

							}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								readOnly : this.isAllReadOnly,
								name : "slSmallloanProject.payintentPerioDate",
								xtype : 'combo',
								mode : 'local',
								disabled : true,
								displayField : 'name',
								valueField : 'id',
								editable : true,
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : obstorepayintentPeriod
										}),
								triggerAction : "all",
								hiddenName : "slSmallloanProject.payintentPerioDate",
								fieldLabel : "",
								anchor : '100%'
							}]
						}, {
							columnWidth : 0.12,
							labelWidth : 45,
							layout : 'form',
							items : [{
										fieldLabel : "号还款",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.47,
							labelWidth : 10,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '按实际放款日还款',
								name : 'q1',
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
											this
													.getCmpByName('slSmallloanProject.isStartDatePay')
													.setValue("2");
											this
													.getCmpByName('slSmallloanProject.payintentPerioDate')
													.setValue(null);
											this
													.getCmpByName('slSmallloanProject.payintentPerioDate')
													.setDisabled(true);
										}
									}
								}

							}]
						}]
					}, {
						columnWidth : .45, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 140,
						name : "mqhkri1",
						hidden : true,
						hideLabel : true,
						items : [{
									fieldLabel : "<font color='red'>*</font>还款日期",
									xtype : "datefield",
									style : {
										imeMode : 'disabled'
									},
									name : "slSmallloanProject.intentDate",
									readOnly : this.isAllReadOnly,
									blankText : "还款日期不能为空，请正确填写!",
									anchor : "58.3%",
									format : 'Y-m-d'
								}]
					}, {
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 13,
						items : [{
							xtype : "checkbox",
							boxLabel : "是否前置付息",
							disabled : this.isAllReadOnly,
							anchor : "100%",
							name : "isPreposePayAccrualCheck",
							// value :true
							checked : this.record == null
									|| this.record.data.isPreposePayAccrual == 0
									? null
									: true
						}]
					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.accrual",
								fieldClass : 'field-align',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								decimalPrecision : 4,
								anchor : "100%",
								fieldLabel : "贷款利率"

							}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
								fieldLabel : "%/期",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 105,
					items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.managementConsultingOfRate",
								allowBlank : false,
								fieldLabel : "管理咨询费率",
								decimalPrecision : 4,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "管理咨询费率不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
										var value = nf.getValue();
										if (value == null || value == "") {
											nf.setValue(0)
										}
									}
								}
							}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
								fieldLabel : "%/期",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .34, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 126,
					items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.financeServiceOfRate",
								allowBlank : false,
								fieldLabel : "财务服务费率",
								decimalPrecision : 4,
								value : 0,
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "财务服务率不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									change : function(nf) {
										var value = nf.getValue();
										if (value == null || value == "") {
											nf.setValue(0)
										}
									}
								}
							}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
								fieldLabel : "%/期",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .17, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 50,
					items : [{
						xtype : "checkbox",
						name : "isAheadPay",
						disabled : this.isAllReadOnly,
						boxLabel : "是否允许提前还款",
						anchor : "100%",
						listeners : {
							scope : this,
							check : function(obj, isChecked) {
								if (isChecked) {
									var operateObj = this
											.getCmpByName('slSmallloanProject.aheadDayNum');
									Ext.apply(operateObj, {
												allowBlank : false,
												blankText : "提前还款通知天数不能为空!"
											});
									operateObj.setDisabled(false)
								} else {
									var operateObj = this
											.getCmpByName('slSmallloanProject.aheadDayNum');
									operateObj.setValue("");
									Ext.apply(operateObj, {
												allowBlank : true
											});
									operateObj.clearInvalid()
									operateObj.setDisabled(true)
								}
							}
						}
					}]
				}, {
					columnWidth : .13, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 90,
					labelWidth : 40,
					items : [{
								fieldLabel : "提前",
								xtype : "numberfield",
								fieldClass : 'field-align',
								name : "slSmallloanProject.aheadDayNum",
								// allowBlank : false,
								style : {
									imeMode : 'disabled'
								},
								readOnly : this.isAllReadOnly,
								blankText : "提前还款通知天数不能为空，请正确填写!",
								anchor : "100%"

							}]
				}, {
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 50,
					items : [{
								fieldLabel : "天通知",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .27,
					layout : 'form',
					labelWidth : 106,
					items : [{
								xtype : "numberfield",
								name : "slSmallloanProject.breachRate",
								readOnly : this.isAllReadOnly,
								fieldClass : 'field-align',
								fieldLabel : "违约金比例",
								fieldClass : 'field-align',
								style : {
									imeMode : 'disabled'
								},
								anchor : "100%"
							}, {
								xtype : "hidden",
								name : "slSmallloanProject.projectStatus"
							}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .35,
					layout : "column",
					items : [{
						columnWidth : 0.45,
						labelWidth : 70,
						layout : 'form',
						items : [{
							xtype : 'radio',
							fieldLabel : '逾期费率',
							boxLabel : '按天计算:每天',
							name : 'yuqifljsfs',
							id : "yuqifljsfs1" + this.idDefinition,
							inputValue : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("yuqifljsfs1"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this
												.getCmpByName('slSmallloanProject.overdueRateType')
												.setValue("1");
										this
												.getCmpByName('slSmallloanProject.overdueRate')
												.setDisabled(false);
									}
								}
							}

						}, {
							xtype : "hidden",
							name : "slSmallloanProject.overdueRateType"

						}]
					}, {
						columnWidth : 0.15,
						labelWidth : 1,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									readOnly : this.isAllReadOnly,
									name : "slSmallloanProject.overdueRate",
									fieldClass : 'field-align',
									disabled : true,
									fieldLabel : "",
									anchor : '100%'
								}]
					}, {
						columnWidth : .06, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 15,
						items : [{
									fieldLabel : "‰",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : .09,
						labelWidth : 30,
						layout : 'form',
						items : [{
									fieldLabel : "罚息",
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : 0.2,
						labelWidth : 10,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '按期计算',
							name : 'yuqifljsfs',
							id : "yuqifljsfs2" + this.idDefinition,
							inputValue : true,
							// checked : true,
							anchor : "100%",
							disabled : this.isAllReadOnly,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("yuqifljsfs2"
											+ this.idDefinition).getValue();
									if (flag == true) {
										this
												.getCmpByName('slSmallloanProject.overdueRateType')
												.setValue("2");
										this
												.getCmpByName('slSmallloanProject.overdueRate')
												.setValue(null);
										this
												.getCmpByName('slSmallloanProject.overdueRate')
												.setDisabled(true);
									}
								}
							}

						}]
					}]
				}

				]
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});

ExtUD.Ext.TypeSelectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true
				},
				items : [{
					columnWidth : .28, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						// columnWidth : .295, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 100,
						items : [{
							xtype : "textfield",
							fieldLabel : "拟贷款金额",
							fieldClass : 'field-align',
							name : "poupseM",
							readOnly : this.isAllReadOnly,
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
								change : function(nf, nv, ov) {
									var credit = nf.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt
											.getCmpByName('slSmallloanProject.credit');

									var value = nf.getValue();
									//  
									if (credit) {
										if (null != credit.getValue()
												&& "" != credit.getValue()
												&& value > parseFloat(credit
														.getValue())) {

											Ext.ux.Toast.msg('警告',
													'期望贷款金额大于可用授信额度!');
											nf.setValue(ov);
											return;
										}
									}
									var index = value.indexOf(".");
									if (index <= 0) { // 如果第一次输入
										// 没有进行格式化
										nf.setValue(Ext.util.Format.number(
												value, '0,000.00'))
										this
												.getCmpByName("slSmallloanProject.money")
												.setValue(value);
									} else {

										if (value.indexOf(",") <= 0) {
											var ke = Ext.util.Format.number(
													value, '0,000.00')
											nf.setValue(ke);
											this
													.getCmpByName("slSmallloanProject.money")
													.setValue(value);
										} else {
											var last = value.substring(index
															+ 1, value.length);
											if (last == 0) {
												var temp = value.substring(0,
														index);
												temp = temp.replace(/,/g, "");
												this
														.getCmpByName("slSmallloanProject.money")
														.setValue(temp);
											} else {
												var temp = value.replace(/,/g,
														"");
												this
														.getCmpByName("slSmallloanProject.money")
														.setValue(temp);
											}
										}

									}
								}
							}
						}, {
							xtype : "hidden",
							name : "slSmallloanProject.money"
						}]
					}]
				}, {
					columnWidth : .02, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "元 ",
								labelSeparator : '',
								anchor : "100%"
							}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 120,
					items : [{
								fieldLabel : "期望资金到位日期",
								xtype : "datefield",
								style : {
									imeMode : 'disabled'
								},
								name : "slSmallloanProject.poupseDate",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								blankText : "期望资金到位日期不能为空，请正确填写!",
								anchor : "100%",
								format : 'Y-m-d'
							}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					items : [{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "degree",
						editable : false,
						fieldLabel : "项目经理",
						blankText : "项目经理不能为空，请正确填写!",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if (null == obj.getValue() || "" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										type : 'branch',
										title : "选择项目经理",
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
			}, {
				xtype : 'textfield',
				name : 'slSmallloanProject.loanPurpose',
				fieldLabel : '贷款用途',
				labelWidth : 85,
				anchor : '100%'
			}, {
				layout : 'column',
				items : [{
					layout : 'column',
					columnWidth : .3,
					items : [{
						layout : 'form',
						columnWidth : .6,
						items : [{
									xtype : 'numberfield',
									fieldLabel : '期望利率',
									name : 'slSmallloanProject.intentRateStart',
									anchor : '100%'
								}]
					}, {
						layout : 'form',
						style : 'margin-left:3px;',
						columnWidth : .03,
						items : [{
									html : '~',
									anchor : '100%'
								}]
					}, {
						layout : 'form',
						columnWidth : .27,
						items : [{
									xtype : 'numberfield',
									name : 'slSmallloanProject.intentRateEnd',
									fieldLabel : '',
									labelSeparator : '',
									labelWidth : 0,
									hideLabel : true,
									anchor : '100%'
								}]
					}, {
						layout : 'form',
						columnWidth : .1,
						items : [{
									html : '%/期',
									style : 'margin-top:3px;'

								}]
					}]
				}, {
					layout : 'form',
					columnWidth : .4,
					labelWidth : 120,
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'zdbfs',
						fieldLabel : '可提供的主担保方式',
						anchor : '100%',
						hiddenName : 'slSmallloanProject.assuretypeid',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
							}
						}
					}]
				}, {
					layout : 'column',
					columnWidth : .3,
					labelWidth : 85,
					items : [{
								layout : 'form',
								columnWidth : .95,
								items : [{
											xtype : 'numberfield',
											fieldLabel : '贷款期限',
											name : 'slSmallloanProject.poupsePeriod',
											anchor : '100%'
										}]
							}, {
								layout : 'form',
								columnWidth : .05,
								items : [{
											html : '月',
											style : 'margin-left:3px;margin-top:3px;'
										}]
							}]
				}]
			}, {/*
				 * layout:'column', items:[{ layout:'form', columnWidth:.3,
				 * items:[{ xtype:'combo', hiddenName
				 * :'slSmallloanProject.availableType', name:'availableType',
				 * displayField :'itemName', valueField :'itemValue',
				 * triggerAction :'all', anchor:'100%', mode : 'local',
				 * store:new Ext.data.ArrayStore({
				 * fields:['itemValue','itemName'],
				 * data:[['1','小贷公司'],['2','担保公司']] }), fieldLabel:'推介机构类型',
				 * listeners:{ scope:this, afterrender:function(combo){
				 * //combo.fireEvent('select',combo,null,0); },
				 * select:function(combo,record,index){ var type = '';
				 * if(record!=null){
				 * 
				 * if(record.data.itemValue=='2'){ type = 'Guarantee'; }else
				 * if(record.data.itemValue=='1'){ type = 'SmallLoan'; }
				 *  } var store = new Ext.data.ArrayStore({ //autoLoad:true,
				 * url:__ctxPath + "/customer/arrayListInvestEnterprise.do",
				 * fields:['itemValue','itemName','nowCreditLimit'], baseParams
				 * :{businessType:type} }); var cb =
				 * combo.ownerCt.nextSibling().get(0); cb.store = store;
				 * store.load({ callback:test }); function test(){ if(cb.view){
				 * cb.view.setStore(store); } } //  } } }] },{
				 * layout:'form', columnWidth:.4, labelWidth : 120, items:[{
				 * xtype:'combo', hiddenName :'slSmallloanProject.availableId',
				 * name:'iId', anchor:'100%', displayField :'itemName',
				 * valueField :'itemValue', triggerAction :'all', mode :
				 * 'remote', labelWidth : 120, store:new Ext.data.ArrayStore({
				 * url:'', fields:['itemValue','itemName'],
				 * data:[['1','小贷公司'],['2','担保公司']] }), fieldLabel:'机构名称',
				 * listeners:{ select:function(combo,record,index){
				 * 
				 * var credit = combo.ownerCt.nextSibling().get(0);
				 * credit.setValue(record.data.nowCreditLimit); var o =
				 * credit.ownerCt.ownerCt.ownerCt.getCmpByName('poupseM'); //
				 * var o =
				 * credit.ownerCt.ownerCt.ownerCt.getCmpByName('poupseM'); var
				 * money =
				 * credit.ownerCt.ownerCt.ownerCt.getCmpByName('slSmallloanProject.money').getValue();
				 * 
				 * if(money>record.data.nowCreditLimit){
				 * Ext.ux.Toast.msg('警告','可用授信额度小于期望贷款金额!'); combo.reset();
				 * credit.reset(); } } } }] },{ layout:'form', labelWidth : 85,
				 * columnWidth:.3, items:[{ xtype:'numberfield', readOnly:true,
				 * anchor:'100%', fieldLabel:'可用额度',
				 * name:'slSmallloanProject.credit' }] }]
				 */}/*
				 * , { layout : "column", defaults : { anchor : '100%',
				 * columnWidth : 1, isFormField : true, labelWidth : 110 },
				 * items : [{ columnWidth : .3, // 该列在整行中所占的百分比 layout : "form", //
				 * 从上往下的布局 labelWidth : 120, items : [{ xtype : "dickeycombo",
				 * nodeKey : '9', fieldLabel : '可提供的主担保方式', anchor : '100%',
				 * hiddenName : 'slSmallloanProject.loanType', listeners : {
				 * afterrender : function(combox) { var st = combox.getStore();
				 * st.on("load", function() { if (combox.getValue() == 0 ||
				 * combox.getValue() == 1) { combox.setValue(""); } else {
				 * combox.setValue(combox .getValue()); } combox.clearInvalid(); }) } } }] }, {
				 * columnWidth : .4, // 该列在整行中所占的百分比 layout : "form", // 从上往下的布局
				 * labelWidth : 170, items : [{ fieldLabel : "资金用途", xtype :
				 * "dickeycombo", hiddenName : 'slSmallloanProject.purposeType',
				 * displayField : 'itemName', readOnly : this.isAllReadOnly,
				 * itemName : '资金用途', width : 203, nodeKey :
				 * 'smallloan_purposeType', emptyText : "请选择", //anchor :
				 * "100%", listeners : { afterrender : function(combox) { var st =
				 * combox.getStore(); st.on("load", function() {
				 * 
				 * combox.setValue(combox .getValue()); combox.clearInvalid(); }) }
				 *  } }] }]
				 *  }
				 */]
		});

	},
	initComponents : function() {
	}
})

ExtUD.Ext.BankAccountPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	// labelAlign : 'right',
	isAllReadOnly : true,
	customizedForZYHT : false,
	isLoadShareequity : false,
	isHiddenCustomerDetailBtn : false,
	isPersonNameReadOnly : true,
	isEditPerson : false,
	prefixName : 'slSmallloanProject',
	isEnterprise : false,
	constructor : function(_cfg) {
		_cfg = _cfg || {};
		if (typeof(_cfg.isReadOnly) != "undefined") {
			this.isReadOnly = _cfg.isReadOnly;
		}
		if (typeof(_cfg.prefixName) != 'undefined') {
			this.prefixName = _cfg.prefixName;
		}
		if (typeof(_cfg.isEnterprise) != "undefined") {
			this.isEnterprise = _cfg.isEnterprise
		}
		var leftlabel = 80
		ExtUD.Ext.BankAccountPanel.superclass.constructor.call(this, {
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
				// columnWidth : .1, // 该列在整行中所占的百分比
				// layout : "form", // 从上往下的布局
				// hidden:this.isHidden,
				items : [{
					layout : "column",
					defaults : {
						anchor : '100%',
						columnWidth : 1,
						isFormField : true,
						labelWidth : 70
					},
					items : [{
						columnWidth : 0.5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
									xtype : 'hidden',
									name : this.prefixName + '.bankId'
								}, {
									name : 'enterpriseBank.id',
									xtype : 'hidden',
									listeners : {
										scope : this,
										loadData : function(comp) {
											// 要考虑客户原本一条银行账户信息都没有的情况时的保存，那么此时的
											// prefixName.bankId必然为空，所以后台务必要做处理
											var val = comp.getValue()
											var bankIdInProj = this
													.getOriginalContainer()
													.getCmpByName(this.prefixName
															+ ".bankId")
											if (val) {
												bankIdInProj.setValue(val);
											}
										}
									}
								}, {
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									anchor : "100%",
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["个人", "0"],
														["公司", "1"]]
											}),
									triggerAction : "all",
									hiddenName : "enterpriseBank.openType",
									fieldLabel : '开户类型',
									allowBlank : this.isHidden,
									readOnly : true,
									name : 'enterpriseBank.openType',
									listeners : {
										scope : this,
										afterrender : function(combox) {
											var st = combox.getStore();
											combox
													.setValue(st.getAt(0).data.id);
											combox.fireEvent("select", combox,
													st.getAt(0), 0);
											var obj = this
													.getCmpByName('enterpriseBank.accountType');
											obj.setValue("0")
										},
										select : function(combox, record, index) {
											/*
											 * var v = record.data.id; var obj
											 * =this.getCmpByName('enterpriseBank.accountType');
											 * obj.enable(); obj.setValue();
											 * obj.store.removeAll() if(v==0){
											 * arrStore = new
											 * Ext.data.SimpleStore({ fields :
											 * ["name", "id"], data : [["个人储蓄户",
											 * "0"],["基本户", "1"],["一般户", "2"]]
											 * });
											 * obj.store.insert(0,arrStore.getAt(0));
											 * }else{ arrStore = new
											 * Ext.data.SimpleStore({ fields :
											 * ["name", "id"], data : [["个人储蓄户",
											 * "0"],["基本户", "1"],["一般户", "2"]]
											 * });
											 * obj.store.insert(0,arrStore.getAt(1));
											 * obj.store.insert(1,arrStore.getAt(2)); }
											 */
										}

									}
								}]
					}, {
						columnWidth : 0.5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
							xtype : 'combo',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							width : 70,
							triggerAction : "all",
							hiddenName : "enterpriseBank.accountType",
							fieldLabel : '账户类型',
							allowBlank : this.isHidden,
							readOnly : this.isReadOnly,
							store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["个人储蓄户", "0"], ["基本户", "1"],
												["一般户", "2"]]
									}),
							name : 'enterpriseBank.accountType',
							anchor : "100%"
						}]
					}, {
						columnWidth : 0.5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
									fieldLabel : '开户名称',
									name : 'enterpriseBank.name',
									xtype : 'textfield',
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									allowBlank : false,// this.isHidden,
									anchor : "100%"
								}]
					}, {
						columnWidth : 0.5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
							xtype : 'textfield',
							fieldLabel : '账号',
							hidden : this.isHidden,
							hideLabel : this.isHidden,
							allowBlank : false,// this.isHidden,
							name : 'enterpriseBank.accountnum',
							readOnly : this.isReadOnly,
							anchor : "100%",
							listeners : {
								scope : this,
								'beforerender' : function(com) {
								},
								'blur' : function(f) {
									var accountnum = f.getValue();
									var id = 0;
									var obj = this
											.getCmpByName('enterpriseBank.id');
									if (obj) {
										id = obj.getValue();
									}
									Ext.Ajax.request({
										url : __ctxPath
												+ '/creditFlow/customer/common/queryAlreadyAccountEnterpriseBank.do',
										method : 'POST',
										params : {
											accountnum : accountnum,
											id : id
										},
										success : function(response, request) {
											var obj = Ext.util.JSON
													.decode(response.responseText);
											if (obj.msg) {
												Ext.ux.Toast.msg('操作信息',
														"该卡号已存在，请重新输入");
												f.setValue("");
												// penal.setValue("");
											}
										}
									});

								}
							}
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : 80,
						items : [{
							fieldLabel : "银行名称",
							xtype : "combo",
							displayField : 'itemName',
							valueField : 'itemId',
							triggerAction : 'all',
							allowBlank : false,// this.isHidden,
							readOnly : this.isReadOnly,
							hidden : this.isHidden,
							hideLabel : this.isHidden,
							store : new Ext.data.ArrayStore({
								url : __ctxPath
										+ '/creditFlow/common/getBankListCsBank.do',
								fields : ['itemId', 'itemName'],
								autoLoad : true
							}),
							mode : 'remote',
							hiddenName : "enterpriseBank.bankid",
							editable : false,
							blankText : "银行名称不能为空，请正确填写!",
							anchor : "100%",
							listeners : {
								scope : this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
												combox.setValue(combox
														.getValue());

											})
									combox.clearInvalid();
								}

							}

						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
									fieldLabel : "网点名称",
									name : 'enterpriseBank.bankOutletsName',
									xtype : 'textfield',
									allowBlank : false,// this.isHidden,
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									anchor : "100%"

								}]
					}, {
						columnWidth : .34,
						layout : 'form',
						items : [{
									name : 'enterpriseBank.areaId',
									xtype : 'hidden'
								}, {
									// id:'bankName',
									name : 'enterpriseBank.areaName',
									hiddenName : 'enterpriseBank.areaName',
									fieldLabel : '开户地区',
									anchor : '100%',
									// value : '中国银行',
									// submitValue:false,
									xtype : 'combo',// srcmarker
													// 原来的trigger改为combo 就支持回填了
													// 不用写this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
													// 这句话了
									triggerClass : 'x-form-search-trigger',
									editable : false,
									allowBlank : false,// this.isHidden,
									readOnly : this.isReadOnly,
									hidden : this.isHidden,
									hideLabel : this.isHidden,
									scope : this,
									onTriggerClick : function() {
										var com = this
										var selectBankLinkMan = function(array) {
											var str = "";
											var idStr = ""
											for (var i = array.length - 1; i >= 0; i--) {
												str = str + array[i].text + "-"
												idStr = idStr + array[i].id
														+ ","
											}
											if (str != "") {
												str = str.substring(0,
														str.length - 1);
											}
											if (idStr != "") {
												idStr = idStr.substring(0,
														idStr.length - 1)
											}
											com.previousSibling()
													.setValue(idStr)
											com.setValue(str);
										};
										// areaTree(selectBankLinkMan,bankAreaRootControl)
										selectDictionary('area',
												selectBankLinkMan);
									}
								}]
					}]
				}]
			}]
		})
	}
})



//标准流程项目启动页面  项目基本信息
ExtUD.Ext.newProjectBaseInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : true,
	isWAD:true,
	userType:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isWAD) != "undefined") {
			this.isWAD = _cfg.isWAD;
		}
		if (typeof(_cfg.userType) != "undefined") {
			this.userType = _cfg.userType;
		}
		Ext.applyIf(this, _cfg);
		var url=__ctxPath + "/system/listBpProductParameter.do?Q_operationType_S_EQ="+this.operationType;
		if(null!=this.userType){
			var operationType='person';
			if(this.userType=='b_loan'){
				operationType='enterprise';
			}
			var url=__ctxPath + "/p2p/listP2pLoanProduct.do?Q_operationType="+operationType;
		}
		ExtUD.Ext.newProjectBaseInfo.superclass.constructor.call(this, {
			items : [ {
							layout : "column",
							border : false,
							scope : this,
							defaults : {
								anchor : '100%',
								columnWidth : 1,
								isFormField : true,
								labelWidth : 80
							},
							items : [{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 70,
										items : [{
										fieldLabel : '门店名称',
										//readOnly : this.isAllReadOnly,
										anchor : '100%',
										editable:false,
										xtype : 'combo',
										editable:false,
										triggerClass : 'x-form-search-trigger',
										hiddenName : "slSmallloanProject.departMentName",
										value:"null"==shopName?'':shopName,
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												if (null != obj.orgName && "" != obj.orgName)
													op.getCmpByName('slSmallloanProject.departMentName').setValue(obj.orgName);
												if (null != obj.orgId && "" != obj.orgId)
													op.getCmpByName('slSmallloanProject.departId').setValue(obj.orgId);
											}
											selectShop(EnterpriseNameStockUpdateNew);
										}
									},{
										xtype:'hidden',
										name:'slSmallloanProject.departId',
										value:"null"==shopId?'':shopId
									}]
									}, {
											columnWidth : .3, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 70,
											items : [{
												xtype : "combo",
												triggerClass : 'x-form-search-trigger',
												hiddenName : "slSmallloanProject.teamManagerName",
												editable : false,
												fieldLabel : "项目主管",
												blankText : "项目主管不能为空，请正确填写!",
												allowBlank : false,
												//readOnly : this.isAllReadOnly,
												anchor : "100%",
												onTriggerClick : function(cc) {
													var obj = this;
													var appuerIdObj = obj.nextSibling();
													var userIds = appuerIdObj.getValue();
													if (null == obj.getValue() || "" == obj.getValue()) {
														userIds = "";
													}
													new UserDialog({
																userIds : userIds,
																userName : obj.getValue(),
																single : false,
																type : 'branch',
																title : "选择项目主管",
																callback : function(uId, uname) {
																	obj.setValue(uname);
																	//obj.setRawValue(uname);
																	appuerIdObj.setValue(uId);
																}
															}).show();
						
												}
											}, {
												xtype : "hidden",
												name : "slSmallloanProject.teamManagerId"
											}]
										}, {
											columnWidth : .3, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 70,
											items : [{
												xtype : "combo",
												triggerClass : 'x-form-search-trigger',
												hiddenName : "slSmallloanProject.appUserName",
												editable : false,
												fieldLabel : "项目经理",
												blankText : "项目经理不能为空，请正确填写!",
												allowBlank : false,
												//readOnly : this.isAllReadOnly,
												anchor : "100%",
													listeners:{
											        scope:this,
											          //设置默认用户
											        'afterRender': function(combo) {
												     this.getCmpByName('slSmallloanProject.appUserName').setValue(currentUserFullName);
												     this.getCmpByName('slSmallloanProject.appUserId').setValue(currentUserId);
											}
										},
												onTriggerClick : function(cc) {
													var obj = this;
													var appuerIdObj = obj.nextSibling();
													var userIds = appuerIdObj.getValue();
													if (null == obj.getValue() || "" == obj.getValue()) {
														userIds = "";
													}
													new UserDialog({
																userIds : userIds,
																userName : obj.getValue(),
																single : false,
																type : 'branch',
																title : "选择项目经理",
																callback : function(uId, uname) {
																	obj.setValue(uname);
																	//obj.setRawValue(uname);
																	appuerIdObj.setValue(uId);
																}
															}).show();
						
												}
											}, {
												xtype : "hidden",
												name : "slSmallloanProject.appUserId"
											}]
										},{
											columnWidth : .4, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : 70,
											items : [{
												labelWidth : 70,
												xtype : "combo",
												//hidden:this.isWAD,
												//disabled:this.isWAD,
												mode : 'local',
												editable : false,
												lazyInit : false,
												allowBlank:false,
												triggerAction : 'all',
											    name : "slSmallloanProject.productName",
												displayField:'productName',
												valueField:'productName',
												fieldLabel : "产品类型",
												editable : false,
												anchor :"100%",
												readOnly:this.isProductReadOnly,
												store : new Ext.data.JsonStore({
													url : url,
													totalProperty : 'totalCounts',
													autoLoad : true,
													root : 'result',
													fields : [{
																name : 'id'
															}, {
																name : 'productName'
															}]													
												}),
												listeners : {
													scope : this,
													/*afterrender : function(combox) {
														var st = combox.getStore();
														var obj=this.getCmpByName("slSmallloanProject.productId")
														st.on("load", function() {
																	combox.setValue(combox.getValue());
																	obj.setValue(combox.getValue());
																	combox.clearInvalid();
																})
															
													},*/
													select : function(combox,record) {
														var st = combox.getStore();
														var obj=this.getCmpByName("slSmallloanProject.productId")
														obj.setValue(record.data.id);
													
															
													}
												}
												
											},{
												xtype : "hidden",
												name : "slSmallloanProject.productId"
											}]
										
										}]
						}]
		});
		
			//加载
		this.loadData({
			url : __ctxPath + "/system/getByUserIdToStoreNameAndStoreNameIdOrganization.do",
			method : "POST",
			root : 'result',
			success : function(response, options) {
				var respText = response.responseText;
				var result = Ext.util.JSON.decode(respText);
				if(result.result !=null && result.result != "undefined"){
					this.getCmpByName("slSmallloanProject.departId").setValue(result.result.orgId)
					this.getCmpByName("slSmallloanProject.departMentName").setValue(result.result.orgName)
				}
			}
		});
	}
});
//债券基本信息
ExtUD.Ext.CreditBaseInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	isHideP2PAccount : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isHideP2PAccount) != "undefined") {
			this.isHideP2PAccount = _cfg.isHideP2PAccount;
		}

		Ext.applyIf(this, _cfg);
		ExtUD.Ext.CreditBaseInfo.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
					columnWidth : .2, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					labelAlign : 'right',
					items : [{
						xtype : "combo",
						mode : 'local',
						labelWidth : 85,
						fieldLabel : "债权人类型",
						emptyText : "请选择",
						displayField : 'name',
						valueField : 'id',
						editable : false,
						anchor : "100%",
						triggerAction : "all",
						hiddenName : "ownBpFundProject.reciverType",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["个人", "person"], ["企业", "enterprise"]]
									}),
						scope : this,
						listeners : {
							scope : this,
							select : function(combox){
								this.getCmpByName('ownBpFundProject.receiverName').setValue(null);
								this.getCmpByName('ownBpFundProject.reciverId').setValue(null);
								this.getCmpByName('ownBpFundProject.receiverP2PAccountNumber').setValue(null);
							},
							afterrender : function(combox) {
											var st = combox.getStore();
											combox.setValue(st.getAt(0).data.id);
											combox.fireEvent("select", combox,st.getAt(0), 0);
											var obj = this.getCmpByName('ownBpFundProject.reciverType');
											obj.setValue("person")
										}
						}
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 85,
					labelAlign : 'right',
					name:'receiverName',
					items : [{
								fieldLabel : '债权人名称',
								xtype : "combo",
								anchor : "100%",
								displayField : 'itemName',
								valueField : 'itemValue',
								emptyText : "请选择",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								hiddenName : 'ownBpFundProject.receiverName',
								typeAhead : true,
								mode : 'local',
								editable : false,
								selectOnFocus : true,
								store : new Ext.data.JsonStore({}),
								triggerClass : 'x-form-search-trigger',
								triggerAction : 'all',
								blankText : "债权人名称不能为空，请正确填写!",
								scope : this,
								onTriggerClick : function() {
											var isHideP2PAccount = this.ownerCt.ownerCt.isHideP2PAccount;
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var obtype=op.getCmpByName("ownBpFundProject.reciverType").getValue();
											if(obtype==""){
												Ext.ux.Toast.msg('状态', '请先选择债权人类型');
												return;
											}else{
												var EnterpriseNameStockUpdateNew = function(obj) {
													op.getCmpByName('ownBpFundProject.receiverName').setValue(null);
													op.getCmpByName('ownBpFundProject.reciverId').setValue(null);
													op.getCmpByName('ownBpFundProject.receiverP2PAccountNumber').setValue(null);
													if (null != obj.mateid && "" != obj.mateid)
														op.getCmpByName('ownBpFundProject.receiverName').setValue(obj.mateid);
													if (null != obj.id && "" != obj.id){
														op.getCmpByName('ownBpFundProject.reciverId').setValue(obj.id);
													}
													if (null != obj.p2pAccount && "" != obj.p2pAccount){
														op.getCmpByName('ownBpFundProject.receiverP2PAccountNumber').setValue(obj.p2pAccount);
													}
													
												}
												if(obtype=="enterprise"){
													selectCsCooperationEnterprise(EnterpriseNameStockUpdateNew,'lenders',isHideP2PAccount);
												}else{
													selectCsCooperationPerson(EnterpriseNameStockUpdateNew,'lenders',isHideP2PAccount);
												}	
													
											}
											
											
										},
								listeners : {
									scope : this
								}
							}]
				}, {
					columnWidth : .45,
					layout : 'form',
					labelWidth : 110,
					hidden:true,
					labelAlign : 'right',
					items : [{
						xtype : 'textfield',
						name : 'ownBpFundProject.reciverId',
						anchor : '100%',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						fieldLabel : '债权人名称'
					}]
				},{
					columnWidth : .45,
					layout : 'form',
					labelWidth : 110,
					labelAlign : 'right',
					name:'receiverP2PAccountNumber',
					hidden : this.isHideP2PAccount,
					items : [{
						xtype : 'textfield',
						name : 'ownBpFundProject.receiverP2PAccountNumber',
						anchor : '100%',
						allowBlank : this.isHideP2PAccount ? true : false,
					    readOnly : true,
						fieldLabel : '债权人P2P账号'
					}]
				}]
		});
	}
});
//招标款项信息
ExtUD.Ext.BidPlanFinanceInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.applyIf(this, _cfg);
		ExtUD.Ext.BidPlanFinanceInfo.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
				columnWidth : .3,
				layout : 'form',
				labelWidth : 80,
				labelAlign : 'right',
				items : [{
					xtype : 'moneyfield',
					fieldLabel : '本次招标金额',
					fieldClass : 'field-align',
					anchor : '100%',
					allowBlank : false,
					readOnly : true,
					name : 'plBidPlan.bidMoney'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
							fieldLabel : "元 ",
							labelSeparator : '',
							anchor : "100%"
						}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '起息日期',
						name : 'plBidPlan.startIntentDate',
						readOnly : this.readOnly,
						allowBlank : false,
						format : 'Y-m-d',
						anchor : '100%',
						listeners : {
							scope : this,
							change : function(nf){
								var startDate=nf.getValue()
								var intentDatePanel=this.getCmpByName('plBidPlan.endIntentDate');
								if(startDate!=''){
									if(this.proType=='P_Dir' || this.proType=='B_Dir'){
										var payAccrualType=this.objectInfo.getCmpByName('platFormBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.objectInfo.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.objectInfo.getCmpByName('platFormBpFundProject.payintentPeriod').getValue();
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
									}/*else{
										var payAccrualType=this.objectInfo.getCmpByName('ownBpFundProject.payaccrualType').getValue();
										var dayOfEveryPeriod=this.objectInfo.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue();
										var payintentPeriod=this.objectInfo.getCmpByName('ownBpFundProject.payintentPeriod').getValue();
										setIntentDate(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,this)
									}*/
								}
								
							}
						}
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '还款日期',
						name : 'plBidPlan.endIntentDate',
						readOnly : true,
						allowBlank : false,
						format : 'Y-m-d',
						anchor : '100%'
					}]
				}]
		});
	}
});


Ext.reg('typeSelectInfoPanel', ExtUD.Ext.TypeSelectInfoPanel);
Ext.reg('FinanceInfoPanel', ExtUD.Ext.FinanceInfoPanel);
Ext.reg('PersonInfo', ExtUD.Ext.PersonInfo);
Ext.reg('PersonAccountInfo', ExtUD.Ext.PersonAccountInfo);
Ext.reg('TypeSelectInfoTwoGradesReadOnlyPanel',
		ExtUD.Ext.TypeSelectInfoTwoGradesReadOnlyPanel);
Ext.reg('TypeSelectInfoFourGradesReadOnlyPanel',
		ExtUD.Ext.TypeSelectInfoFourGradesReadOnlyPanel);
Ext.reg('TypeSelectInfoTwoGradesPanel', ExtUD.Ext.TypeSelectInfoTwoGradesPanel);
Ext.reg('TypeSelectInfoFourGradesPanel',
		ExtUD.Ext.TypeSelectInfoFourGradesPanel);
Ext.reg('projectNameTextField', ExtUD.Ext.ProjectNameTextField);
Ext.reg('TypeSelectInfoThreeGradesPanel',
		ExtUD.Ext.TypeSelectInfoThreeGradesPanel);
Ext.reg('TypeSelectInfoThreeGradesReadOnlyPanel',
		ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel);
Ext.reg('TypeSelectInfoMineType', ExtUD.Ext.TypeSelectInfoMineType);// vm节点使用的我方主体项目经理
Ext.reg('peerMainInfoPanel', ExtUD.Ext.PeerMainInfoPanel);
Ext.reg('peerPersonMainInfoPanel', ExtUD.Ext.PeerPersonMainInfoPanel);
Ext.reg('projectInfoPanel', ExtUD.Ext.ProjectInfoPanel);
Ext.reg('projectInfoFinancePanel', ExtUD.Ext.ProjectInfoFinancePanel);
Ext.reg('projectNameBuildButton', ExtUD.Ext.ProjectBuildButton);
Ext.reg('bankinfoPanel', ExtUD.Ext.BankInfoPanel);
Ext.reg('deferApplyInfoPanel', ExtUD.Ext.DeferApplyInfoPanel);
Ext.reg('EarlyRepaymentPanel', ExtUD.Ext.EarlyRepaymentPanel);
Ext.reg('AlterAccrualPanel', ExtUD.Ext.AlterAccrualPanel);
Ext.reg('NothopeProjectInfoFinancePanel',
		ExtUD.Ext.NothopeProjectInfoFinancePanel);
Ext.reg('customerInfoPanel', ExtUD.Ext.CustomerInfoPanel);
Ext.reg('customerType', ExtUD.Ext.CustomerType);

/*
 * { columnWidth : 1, // 该列在整行中所占的百分比 layout : "form", // 从上往下的布局 items : [{
 * xtype : 'fieldset', layout : "column", defaults : { anchor : '100%',
 * columnWidth : 1, isFormField : true, labelWidth : 100 }, checkboxToggle :
 * true, title : '允许提前还款', autoHeight : true, anchor : "100%", defaultType :
 * 'textfield', collapsed : true, items : [{ columnWidth : .25, // 该列在整行中所占的百分比
 * layout : "form", // 从上往下的布局 items : [] }, { columnWidth : .25, //
 * 该列在整行中所占的百分比 layout : "form", // 从上往下的布局 items : [] }] }] }
 */