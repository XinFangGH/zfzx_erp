/**
 * @author
 * @createtime
 * @class PlOrReleaseForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
PlBusinessOrPlanForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlBusinessOrPlanForm.superclass.constructor.call(this, {
					id : 'PlBusinessOrPlanFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					width : 1000,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isReadOnly,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								hidden : this.isReadOnly,
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
		Ext.apply(Ext.form.VTypes, {  
	        dateRange: function(val, field){
	            if(field.dateRange){  
	                var beginId = field.dateRange.begin;  
	                this.beginField = Ext.getCmp(beginId);  
	                var endId = field.dateRange.end;  
	                this.endField = Ext.getCmp(endId);  
	                var beginDate = this.beginField.getValue();  
	                var endDate = this.endField.getValue(); 
	                if(!Ext.isEmpty(beginDate)&&!Ext.isEmpty(endDate)){
	                  	if(beginDate.getTime()>endDate.getTime()){
	                   		return false;
	                  	}else{
	                  		return true;
	                 	}
	                }
	                return true;
	            }  
	        },  
	        //验证失败信息  
	        dateRangeText: '预售公告时间不能大于开始投标时间'  
    });

		this.formPanel = new Ext.form.FormPanel({
			id : 'PlBusinessBidOrPlanFormPanel',
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},

			items : [{
						name : 'plBidPlan.bidId',
						xtype : 'hidden',
						value : this.bidId == null ? '' : this.bidId
					}, {
						name : 'plBidPlan.borProId',
						id : 'borProId',
						xtype : 'hidden'
					}, {
						name : 'plBidPlan.proType',
						id : 'proType',
						xtype : 'hidden'
					}, {
						name : 'plBidPlan.payIntersetWay',
						id : 'payIntersetWay',
						xtype : 'hidden'
					}, 
					//swj
						{
						name : 'plBidPlan.payAcctualType',
						id : 'payAcctualType',
						xtype : 'hidden'
					},
						{
						name : 'plBidPlan.payMoneyTimeType',
						id : 'payMoneyTimeType',
						xtype : 'hidden'
					}, {
						name : 'plBidPlan.state',
						xtype : 'hidden',
						value:0
					}, {
						xtype : 'hidden',
						id : 'loanProMoney'
					}, {
						columnWidth : .7, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									fieldLabel : '贷款项目名称',
									id : 'loanProName',
									allowBlank : false,
									readOnly : true,
									anchor : '100%'

								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									allowBlank : false,
									fieldLabel : '贷款项目编号',
									id : 'loanProNubmer',
									readOnly : true,
									anchor : '100%'

								}]
					}, {
						columnWidth : .7, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									fieldLabel : '招标项目名称',
									name : 'plBidPlan.bidProName',
									readOnly :this.isReadOnly,
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									xtype : "textfield",
									fieldLabel : '招标项目编号',
									name : 'plBidPlan.bidProNumber',
									readOnly :this.isReadOnly,
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						labelAlign : 'right',
						items : [{
									columnWidth : .25, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 110,
									labelAlign : 'right',
									items : [{
												fieldLabel : '借款开始日',
												id : 'loanStarTime',
												xtype : 'datefield',
												format : 'Y-m-d ',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .25, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '借款截至日',
												xtype : 'datefield',
												format : 'Y-m-d',
												id : 'loanEndTime',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								},{
									columnWidth : .25, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 110,
									labelAlign : 'right',
									items : [{
												fieldLabel : '债权持有人名称',
												xtype : 'textfield',
												id : 'receiverName',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								},{
									columnWidth : .25, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 110,
									labelAlign : 'right',
									items : [{
												fieldLabel : '债权持有人P2P账号',
												xtype : 'textfield',
												id : 'reciverP2PAccount',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}/*,{
									columnWidth : .25, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '债权买入日',
												xtype : 'datefield',
												format : 'Y-m-d H:i:s',
												id : 'payTime',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}*/]

					}, {

						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						labelAlign : 'right',
						items : [{
									columnWidth : .08, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									labelAlign : 'right',
									items : [{
												fieldLabel : "贷款利率 ",
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '年利率',
												xtype : 'numberfield',
												id : 'yearInterestRate',
												allowBlank : false,
												readOnly : true,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '月利率',
												xtype : 'numberfield',
												id : 'monthInterestRate',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '日利率',
												xtype : 'numberfield',
												id : 'dayInterestRate',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
									items : [{
												fieldLabel : "%",
												labelSeparator : '',
												anchor : "100%"
											}]
								}, {
									columnWidth : .2, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '合计利率',
												xtype : 'numberfield',
												id : 'totalInterestRate',
												readOnly : true,
												allowBlank : false,
												anchor : '100%'

											}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 20,
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
									columnWidth : 0.1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
											fieldLabel : "还款方式",
											anchor : "100%"
											}]
								},{
									columnWidth : 0.2,
									labelWidth : 5,
									layout : 'form',
									labelAlign : 'right',
									items : [{
										xtype : 'radio',
										boxLabel : '按期收息，到期还本',
										name : 'plOrRelease.paymentWay',
										id : "paymentWay1",
										inputValue : 1,
										anchor : "100%",
										checked:true,
										disabled : false
									}]
								},{
									columnWidth : 0.1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 70,
									items : [{
										fieldLabel : "还款周期 ",
										anchor : "100%"
									}]
								}, {
									columnWidth : 0.2,
									labelWidth : 5,
									layout : 'form',
									labelAlign : 'right',
									items : [{
										xtype : 'radio',
										boxLabel : '月',
										name : 'plOrRelease.payAcctualType',
										id : "payAcctualType1",
										inputValue : 1,
										anchor : "100%",
										checked:true,
										disabled : false
									}]
								},{
									columnWidth : .36, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 147,
									labelAlign : 'right',
									items : [{
												fieldLabel : '提前还款补偿息天数',
												name : 'plBidPlan.penaltyDays',
												readOnly :this.isReadOnly,
												xtype : 'numberfield',
												allowBlank : false,
												value :0,
												anchor : '100%'
			
											}]
								}

						]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						labelAlign : 'right',
						items : [{
									fieldLabel : '当前可转让金额',
									readOnly : true,
									id : 'residueMoney',
									xtype : 'numberfield',
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
							fieldLabel : '本次转让金额',
							name : 'plBidPlan.bidMoney',
							readOnly :this.isReadOnly,
							id : 'orMoney',
							value : 0,
							listeners : {
								scope : this,
								'change' : function(nf) {
									var totalMoney = Ext.getCmp('loanProMoney').getValue();
									var currMoney = Ext.getCmp('orMoney').getValue();
									var residueMoney = Ext.getCmp('residueMoney').getValue();
									if (currMoney > residueMoney) {
										alert("本次转让金额不能大于剩余金额！");
									//	currMoney = residueMoney;
										Ext.getCmp('orMoney').setValue();
									    currMoney=0;
									}
									Ext.getCmp('orMoneyScale').setValue(currMoney / totalMoney * 100);
									this.getCmpByName('plBidPlan.maxMoney').setValue(currMoney);
								}
							},
							xtype : 'numberfield',
							allowBlank : false,
							anchor : '100%'

						}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '占比',
									name : 'plBidPlan.bidMoneyScale',
									id : 'orMoneyScale',
									readOnly : true,
									xtype : 'numberfield',
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "% ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						labelAlign : 'right',
						items : [{
							columnWidth : .33, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 110,
							labelAlign : 'right',
							items : [{
								anchor : '91%',
								xtype : 'combo',
								hiddenName : 'plBidPlan.biddingTypeId',
								id : 'projType',
								fieldLabel : '起息模式',
								valueField : 'typeId',
								displayField : 'typeName',
								readOnly :this.isReadOnly,
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								forceSelection : true,
								selectOnFocus : true,
								allowBlank : false,
								store : new Ext.data.SimpleStore({
									url : __ctxPath
											+ "/creditFlow/financingAgency/typeManger/loadItemPlBiddingType.do?Q_biddingTypeId_L_LT=3",
									autoLoad : true,
									fields : ['typeId', 'typeName']

								}),
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue()=="") {
												if (st.getCount() > 0) {
													var record = st.getAt(0);
													var v = record.data.typeId;
													combox.setValue(v);
												}
											}
											if (combox.getValue() > 0) {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											}
										})
									}
								}
							}]
						}, {
							columnWidth : .33, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',

							items : [{
								hiddenName : 'plBidPlan.startInterestType',
								xtype : 'combo',
								fieldLabel : '起息日类型',
								allowBlank : false,
								editable : false,
								mode : 'local',
								readOnly :this.isReadOnly,
								anchor : '91%',
								triggerAction : 'all',
								store : [['0', 'T(投标日+1天)'],
										['1', 'T(招标截止日+1天)'],
										['2', 'T(满标日+1天)']],
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										if (combox.getValue()=="") {
											if (st.getCount() > 0) {
												var record = st.getAt(0);
												var v =record.data.field1;
												combox.setValue(v);
											}
										}
									}
								}
							}]
						}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '投资起点金额',
									name : 'plBidPlan.startMoney',
									readOnly :this.isReadOnly,
									xtype : 'numberfield',
									allowBlank : false,
									value:100,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						labelAlign : 'right',
						items : [{
									fieldLabel : '递增单位',
									name : 'plBidPlan.riseMoney',
									readOnly :this.isReadOnly,
									xtype : 'numberfield',
									allowBlank : false,
									value:100,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '单笔投资上限',
									name : 'plBidPlan.maxMoney',
									readOnly :this.isReadOnly,
									xtype : 'numberfield',
									allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '预售公告时间',
									name : 'plBidPlan.prepareSellTime',
									id:'plBidPlan.prepareSellTime',
									xtype : (this.isReadOnly==true?'datefield':'datetimefield'),
									format : 'Y-m-d H:i:s',
									readOnly :this.isReadOnly,
									dateRange: {begin: 'plBidPlan.prepareSellTime', end: 'plBidPlan.publishSingeTime' },  
									// allowBlank : false,
									anchor : '100%',
									vtype: 'dateRange' ,
									dateRangeText:"预售公告时间不能大于开始投标时间",
									// allowBlank : false,
									anchor : '100%'

								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						labelAlign : 'right',
						items : [{
									fieldLabel : '招标开放期限',
									name : 'plBidPlan.bidStartTime',
									id:'plBidPlan.bidStartTime',
									xtype : 'numberfield',
									allowBlank : false,
									readOnly :this.isReadOnly,
									anchor : '100%',
									listeners : {
										scope : this,
										'blur' : function(nf){
											
											
											var investlimit=Ext.getCmp('plBidPlan.bidStartTime').getValue();
											var dtstr=Ext.getCmp('plBidPlan.publishSingeTime').getValue();
											if(Ext.isEmpty(investlimit) ||Ext.isEmpty(dtstr) ){
												return;
											}
											var dtstr =parseInt(dtstr.getTime());
											var n=parseInt(investlimit);
											 var s=dtstr+n*60*60*1000
											     Ext.getCmp("plBidPlan.bidEndTime").setValue(new Date(s));
										}
										}

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 27,
						items : [{
									fieldLabel : "小时",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '开始投标时间',
									name : 'plBidPlan.publishSingeTime',
									id:'plBidPlan.publishSingeTime',
									xtype : (this.isReadOnly==true?'datefield':'datetimefield'),
									allowBlank : false,
									format : 'Y-m-d H:i:s',
									readOnly :this.isReadOnly,
									dateRange: {begin: 'plBidPlan.prepareSellTime', end: 'plBidPlan.publishSingeTime' },  
									// allowBlank : false,
									anchor : '100%',
									vtype: 'dateRange' ,
									listeners : {
										scope : this,
										'blur' : function(nf){
											
											
										//	var prepareSellTime=Ext.getCmp('plBidPlan.prepareSellTime').getValue();
											var investlimit=Ext.getCmp('plBidPlan.bidStartTime').getValue();
											var dtstr=Ext.getCmp('plBidPlan.publishSingeTime').getValue();
										/*	if(parseInt(dtstr.getTime())<parseInt(prepareSellTime.getTime())){
											   return;
											}*/
											if(Ext.isEmpty(investlimit) ||Ext.isEmpty(dtstr) ){
												return;
											}
											var dtstr =parseInt(dtstr.getTime());
											var n=parseInt(investlimit);
											 var s=dtstr+n*60*60*1000
											     Ext.getCmp("plBidPlan.bidEndTime").setValue(new Date(s));
										}
										}

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 24,
						items : [{
									fieldLabel : "   ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '招标截至时间',
									name : 'plBidPlan.bidEndTime',
									id:'plBidPlan.bidEndTime',
									xtype : 'datefield',
										format : 'Y-m-d H:i:s',
									// allowBlank : false,
									readOnly : true,
									anchor : '100%'

								}]
					},  /* {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '加息利率',
									name : 'plBidPlan.addRate',
									xtype : 'numberfield',
									allowBlank : false,
									readOnly : this.readOnly,
									value :0,
									anchor : '100%'

								}]
					},  {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "% ",
									labelSeparator : '',
									anchor : "90%"
								}]
					},*/ {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						style :'width:140px',
						labelAlign : 'right',
						items : [{
									fieldLabel : '可用优惠券',
									name : 'plBidPlan.coupon',
									id : 'check_coupon',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									disabled : this.isReadOnly,
									value :0,
									anchor : '100%',
											listeners : {
											scope : this,
											check : function(obj, checked) {
												if(!checked){
													Ext.getCmp("maxMoney_text").setValue("");
													Ext.getCmp("ratio_text").setValue("");
													 Ext.getCmp('plBidPlan.rebateType').setValue("");
													 Ext.getCmp('plBidPlan.rebateWay').setValue("");
													Ext.getCmp("hide_a").hide();
													Ext.getCmp("hide_b").hide();
													/*Ext.getCmp("hide_c").hide();*/
													/*Ext.getCmp("hide_d").hide();*/
													/*Ext.getCmp("hide_e").hide();*/
													Ext.getCmp("hide_f").hide();
													Ext.getCmp("hide_g").hide();
													Ext.getCmp("hide_h").hide();
													Ext.getCmp("check_f").setValue(false);
													Ext.getCmp("check_g").setValue(false);
													Ext.getCmp("check_h").setValue(false);
													Ext.getCmp("check_e").setValue(false);
													Ext.getCmp("check_ff").setValue(false);
													Ext.getCmp("check_ee").setValue(false);
													Ext.getCmp("check_ii").setValue(false);
													
												}else{
													Ext.getCmp("hide_a").show();
													Ext.getCmp("hide_b").show();
													/*Ext.getCmp("hide_c").show();*/
													/*Ext.getCmp("hide_d").show();*/
													/*Ext.getCmp("hide_e").show();*/
													Ext.getCmp("hide_f").show();
													Ext.getCmp("hide_g").show();
													Ext.getCmp("hide_h").show();
													Ext.getCmp("check_addRate").setValue(false);
													Ext.getCmp("addRate_text").setValue("");
													Ext.getCmp("hide_addRate_1").hide();
													Ext.getCmp("hide_addRate_2").hide();
												}
											}
										}

								}]
					}, {
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									fieldLabel : '普通加息',
									name : 'checkaddRate',
									id : 'check_addRate',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									disabled : this.isReadOnly,
									value :0,
									anchor : '100%',
											listeners : {
											scope : this,
											check : function(obj, checked) {
												if(!checked){
													Ext.getCmp("hide_addRate_1").hide();
													Ext.getCmp("hide_addRate_2").hide();
													Ext.getCmp("addRate_text").setValue("");
													Ext.getCmp('plBidPlan.rebateType').setValue("");
													Ext.getCmp('plBidPlan.rebateWay').setValue("");
												}else{
													Ext.getCmp("hide_a").hide();
													Ext.getCmp("hide_b").hide();
													/*Ext.getCmp("hide_c").hide();
													Ext.getCmp("hide_d").hide();
													Ext.getCmp("hide_e").hide();*/
													Ext.getCmp("hide_f").hide();
													Ext.getCmp("hide_g").hide();
													Ext.getCmp("hide_h").hide();
													Ext.getCmp("check_f").setValue(false);
													Ext.getCmp("check_g").setValue(false);
													Ext.getCmp("check_h").setValue(false);
													Ext.getCmp("check_e").setValue(false);
													Ext.getCmp("check_ff").setValue(false);
													Ext.getCmp("check_ee").setValue(false);
													Ext.getCmp("check_ii").setValue(false);
													Ext.getCmp("check_coupon").setValue(false);
													Ext.getCmp("maxMoney_text").setValue("");
													Ext.getCmp("ratio_text").setValue("");
													Ext.getCmp("ratio_text").removeClass("readOnlyClass");
													Ext.getCmp("ratio_text").setReadOnly(false);
													Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
													Ext.getCmp("maxMoney_text").setReadOnly(false);
													Ext.getCmp("hide_addRate_1").show();
													Ext.getCmp("hide_addRate_2").show();
													//(系统默认)rebateType=0普通加息
													//(可选)rebateWay=1,立还。rebateWay=2随期。rebateWay=3到期
													Ext.getCmp('plBidPlan.rebateType').setValue("0");
													Ext.getCmp('plBidPlan.rebateWay').setValue("2");
												}
											}
										}

								}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									fieldLabel : '新手专享',
									name : 'plBidPlan.novice',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									disabled : this.isReadOnly,
									value :0,
									anchor : '100%',
									handler : function(ck, checked) {
													if (checked) {
														this.sendMsg = true;
													} else {
														this.sendMsg = false;
													}
												}

								}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 70,
						labelAlign : 'right',
						items : [{
									fieldLabel : '募集期利率',
									name : 'plBidPlan.raiseRate',
									xtype : 'numberfield',
									allowBlank : true,
									readOnly :this.isReadOnly,
									allowNegative:false,
									anchor : '100%'

								}]
					},  {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "%",
									labelSeparator : '',
									anchor : "90%"
								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						id : 'hide_addRate_1',
						hidden: true,
						allowBlank : false,
						labelAlign : 'right',
						items : [{
									fieldLabel : '<font color="red">*</font>普通加息利率',
									name : 'plBidPlan.addRate',
									id : 'addRate_text',
									readOnly : this.isReadOnly,
									xtype : 'numberfield',
									anchor : '100%'

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 310,
						id : 'hide_addRate_2',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : "%/年 (普通用户加息，随期奖励，不需要使用优惠券)",
									labelSeparator : '',
									anchor : "100%"
								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						id : 'hide_a',
						hidden: true,
						allowBlank : false,
						labelAlign : 'right',
						items : [{
									fieldLabel : '<font color="red">*</font>面值折现比',
									name : 'plBidPlan.returnRatio',
									id :'ratio_text',
									readOnly : this.isReadOnly,
									xtype : 'numberfield',
									anchor : '100%'

								},{
									fieldLabel : '<font color="red">*</font>单笔最大面值',
									name : 'plBidPlan.maxCouponMoney',
									id : 'maxMoney_text',
									readOnly : this.isReadOnly,
									xtype : 'numberfield',
									anchor : '100%'

								},{
									fieldLabel : '返利类型',
									name : 'check_e',
									xtype : 'checkbox',
									boxLabel : '返现',
									id : 'check_e',
									allowBlank : false,
									disabled : this.isReadOnly,
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 Ext.getCmp('plBidPlan.rebateType').setValue("1");
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												Ext.getCmp("ratio_text").removeClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(false);
												Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
												Ext.getCmp("maxMoney_text").setReadOnly(false);
												/*Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();*/
										    }else{
										}
										}
									
									}

								},{
									fieldLabel : '返利方式',
									name : 'check_ee',
									xtype : 'checkbox',
									id : 'check_ee',
									boxLabel : '立返',
									allowBlank : false,
									disabled : this.isReadOnly,
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	Ext.getCmp('plBidPlan.rebateWay').setValue("1");
												Ext.getCmp("check_ff").setValue(false);
												Ext.getCmp("check_ii").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												/*Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();*/
										    }else{
										}
										}
									
									}
								}]
					}, {
						columnWidth : .6, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 380,
						id : 'hide_b',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : "% (例如：如果优惠券面值为1000元折现比例10%则有效面值为100元)",
									labelSeparator : '',
									anchor : "100%"
								},{
									fieldLabel : "元 (例如：单笔最大面值1000元此标不能使用1000元以上面值的优惠券)",
									labelSeparator : '',
									anchor : "100%"
								}]
					},/*{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						id : 'hide_c',
						allowBlank : false,
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : '单笔最大面值',
									name : 'plBidPlan.maxCouponMoney',
									xtype : 'numberfield',
									id : 'maxMoney_text',
									anchor : '100%'

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 430,
						id : 'hide_d',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : "元 (例如：单笔最大面值1000元此标不能使用1000元以上面值的优惠券)",
									labelSeparator : '',
									anchor : "90%"
								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 110,
						id : 'hide_e',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : '返利类型',
									name : 'check_e',
									xtype : 'checkbox',
									boxLabel : '返现',
									id : 'check_e',
									allowBlank : false,
									readOnly : this.readOnly,
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 Ext.getCmp('plBidPlan.rebateType').setValue("1");
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();
										    }else{
										 Ext.getCmp('plBidPlan.rebateType').setValue("");
										}
										}
									
									}

								},{
									fieldLabel : '返利方式',
									name : 'check_ee',
									xtype : 'checkbox',
									id : 'check_ee',
									boxLabel : '立返',
									allowBlank : false,
									readOnly : this.readOnly,
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	Ext.getCmp('plBidPlan.rebateWay').setValue("1");
												Ext.getCmp("check_ff").setValue(false);
												Ext.getCmp("check_ii").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();
										    }else{
											Ext.getCmp('plBidPlan.rebateWay').setValue("");
										}
										}
									
									}
								}]
					},*/{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_f',
						hidden: true,
						labelAlign : 'right',
						items : [{
							fieldLabel : '',
							name : 'check_f',
							xtype : 'checkbox',
							disabled : this.isReadOnly,
							id : 'check_f',
							boxLabel : '返息',
							anchor : '100%',
							listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 Ext.getCmp('plBidPlan.rebateType').setValue("2");
												Ext.getCmp("check_e").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												Ext.getCmp("ratio_text").removeClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(false);
												Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
												Ext.getCmp("maxMoney_text").setReadOnly(false);
												/*Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();*/
										    }else{
										}
										}
									
									}
						},{
							fieldLabel : '',
							name : 'check_ff',
							xtype : 'checkbox',
							disabled : this.isReadOnly,
							id : 'check_ff',
							boxLabel : '随期',
							anchor : '100%',
							listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	Ext.getCmp('plBidPlan.rebateWay').setValue("2");
												Ext.getCmp("check_ee").setValue(false);
												Ext.getCmp("check_ii").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												/*Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();*/
										    }else{
										}
										}
									
									}
						}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_g',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : '',
									name : 'check_g',
									xtype : 'checkbox',
									disabled : this.isReadOnly,
									id : 'check_g',
									boxLabel : '返息现',
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	 Ext.getCmp('plBidPlan.rebateType').setValue("3");
												Ext.getCmp("check_e").setValue(false);
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_h").setValue(false);
												Ext.getCmp("hide_a").show();
												Ext.getCmp("hide_b").show();
												Ext.getCmp("ratio_text").removeClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(false);
												Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
												Ext.getCmp("maxMoney_text").setReadOnly(false);
												
												/*Ext.getCmp("hide_c").show();
												Ext.getCmp("hide_d").show();*/
										    }else{
										}
										}
									
									}

								},{
									fieldLabel : '',
									name : 'check_ii',
									xtype : 'checkbox',
									id : 'check_ii',
									disabled : this.isReadOnly,
									boxLabel : '到期',
									anchor : '100%',
									listeners : {
												"check" : function(el, checked) {
												 if (checked) {
												 	Ext.getCmp('plBidPlan.rebateWay').setValue("3");
														Ext.getCmp("check_ee").setValue(false);
														Ext.getCmp("check_ff").setValue(false);
														Ext.getCmp("hide_a").show();
														Ext.getCmp("hide_b").show();
														/*Ext.getCmp("hide_c").show();
														Ext.getCmp("hide_d").show();*/
												    }else{
												}
												}
											
											}
								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_h',
						hidden: true,
						labelAlign : 'right',
						items : [{
									fieldLabel : '',
									name : 'check_h',
									xtype : 'checkbox',
									disabled : this.isReadOnly,
									boxLabel : '加息',
									id : 'check_h',
									anchor : '100%',
									listeners : {
										"check" : function(el, checked) {
										 if (checked) {
										 	    Ext.getCmp('plBidPlan.rebateType').setValue("4");
										 	    Ext.getCmp("maxMoney_text").setValue("");
										 	    Ext.getCmp("maxMoney_text").addClass("readOnlyClass");
										 	    Ext.getCmp("maxMoney_text").setReadOnly(true);
										 	          
												Ext.getCmp("ratio_text").setValue("");
												Ext.getCmp("ratio_text").addClass("readOnlyClass");
												Ext.getCmp("ratio_text").setReadOnly(true);
												
												Ext.getCmp("check_e").setValue(false);
												Ext.getCmp("check_f").setValue(false);
												Ext.getCmp("check_g").setValue(false);
												
											/*	Ext.getCmp("hide_a").hide();*/
												/*Ext.getCmp("hide_b").hide();*/
											/*	Ext.getCmp("hide_c").hide();
												Ext.getCmp("hide_d").hide();*/
										    }else{
										}
										}
									
									}


								}]
					},{
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelAlign : 'right',
						labelWidth : 110,
						items : [{
							xtype : 'textarea',
							name : 'plBidPlan.bidRemark',
							readOnly :this.isReadOnly,
							fieldLabel : "招标说明 ",
							anchor : "100%"
						},{
									xtype:"hidden",
									id : 'plBidPlan.rebateType',
								    name : 'plBidPlan.rebateType'
								},{
									xtype:"hidden",
									id : 'plBidPlan.rebateWay',
								    name : 'plBidPlan.rebateWay'
								}]
					}]
		});
		// 加载表单对应的数据
		if (this.bidId != null && this.bidId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
					+ '/creditFlow/financingAgency/getPlBidPlan.do?bidId='
						+ this.bidId,
				root : 'data',
				preName : 'plBidPlan',
				success : function(response, options) {
					var respText = response.responseText;
					var result = Ext.util.JSON.decode(respText);
					if(result.data.rebateType==1){
					  	Ext.getCmp("check_e").setValue(true);
					 }else if(result.data.rebateType==2){
					  	Ext.getCmp("check_f").setValue(true);
					 }else if(result.data.rebateType==3){
					  	Ext.getCmp("check_g").setValue(true);
					 }else if(result.data.rebateType==4){
					  	Ext.getCmp("check_h").setValue(true);
					 }
					 if(result.data.rebateWay==1){
					 	Ext.getCmp("check_ee").setValue(true);
					 
					 }else if(result.data.rebateWay==2){
					 	Ext.getCmp("check_ff").setValue(true);
					 	
					 }else if(result.data.rebateWay==3){
					 	Ext.getCmp("check_ii").setValue(true);
					 	
					 }
					 if(result.data.addRate>0){
					 	Ext.getCmp("check_addRate").setValue(true);
					 }
					 /*Ext.getCmp("check_e").setDisabled(true);
					 Ext.getCmp("check_f").setDisabled(true);
					 Ext.getCmp("check_g").setDisabled(true);
					 Ext.getCmp("check_h").setDisabled(true);
						Ext.getCmp("check_ii").setDisabled(true);
					 	Ext.getCmp("check_ff").setDisabled(true);
					 	Ext.getCmp("check_ee").setDisabled(true);
					 	
						Ext.getCmp("maxMoney_text").addClass("readOnlyClass");
						Ext.getCmp("maxMoney_text").setReadOnly(true);
										 	          
						Ext.getCmp("ratio_text").addClass("readOnlyClass");
						Ext.getCmp("ratio_text").setReadOnly(true);*/
				}
			});
		}

		if (this.borProId != null && this.borProId != 'undefined') {
			//获取 剩余招标金额
	    Ext.Ajax.request({
			url : __ctxPath+ '/creditFlow/financingAgency/business/getBidInfoBpBusinessOrPro.do?borProId='+ this.borProId,
			method:"post",
			scope:this,
			success:function(response,opts){
				var res = Ext.util.JSON.decode(response.responseText);
				var data = res.data;
				Ext.getCmp('residueMoney').setValue(data.residueMoney);//剩余招标金额
				Ext.getCmp('borProId').setValue(this.borProId);
				Ext.getCmp('proType').setValue(this.proType);
				
				Ext.getCmp('loanProName').setValue(data.proName);
				Ext.getCmp('loanProNubmer').setValue(data.proNumber);
				Ext.getCmp('yearInterestRate').setValue(data.yearInterestRate);
				Ext.getCmp('monthInterestRate').setValue(data.monthInterestRate);
				Ext.getCmp('dayInterestRate').setValue(data.dayInterestRate);
				Ext.getCmp('totalInterestRate').setValue(data.totalInterestRate);
				Ext.getCmp('loanStarTime').setValue(data.loanStarTime);
				Ext.getCmp('loanEndTime').setValue(data.loanEndTime);
				Ext.getCmp('loanProMoney').setValue(data.bidMoney);
				Ext.getCmp('payIntersetWay').setValue(data.payIntersetWay);
				Ext.getCmp('payAcctualType').setValue(data.payAcctualType);
				Ext.getCmp('receiverName').setValue(data.receiverName);
				Ext.getCmp('reciverP2PAccount').setValue(data.receiverP2PAccountNumber);
				Ext.getCmp('payMoneyTimeType').setValue(data.payAcctualType);//还款时间方式 D 天/M月/Q季/Y年
				
	            this.fillPayWay(data.payIntersetWay);//还款方式
	            this.fillPayWay1(data.payAcctualType);//还款周期/付息方式
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
		var bidMoney=this.getCmpByName('plBidPlan.bidMoney').getValue();
		var startMoney=this.getCmpByName('plBidPlan.startMoney');
		if(startMoney.getValue()>bidMoney){
		   Ext.Msg.alert('操作信息','投资起点金额不能大于本次转让金额，请修改！');
		   startMoney.setValue("");
			return;
		}
		var riseMoney=this.getCmpByName('plBidPlan.riseMoney');
		if(riseMoney.getValue()>bidMoney){
		    Ext.Msg.alert('操作信息','递增金额不能大于本次转让金额，请修改！');
		   riseMoney.setValue("");
			return;
		}
		var maxMoney=this.getCmpByName('plBidPlan.maxMoney');
		if(maxMoney.getValue()>bidMoney){
		    Ext.Msg.alert('操作信息','单笔投资上限金额不能大于本次转让金额，请修改！');
		   maxMoney.setValue("");
			return;
		}
		var bidEndTime=this.getCmpByName('plBidPlan.bidEndTime').getValue();
		if(null!=bidEndTime && bidEndTime!='' && (new Date(bidEndTime)).getTime()<(new Date()).getTime()){
			Ext.Msg.alert('操作信息','招标截止日不能早于当前时间，请修改！');
			return;
		}
		var raiseRate=this.getCmpByName('plBidPlan.raiseRate');
		if(""!=raiseRate.getValue() && null!=raiseRate.getValue() && raiseRate.getValue()<0){
			Ext.Msg.alert('操作信息','募集期利率不能为负数，请修改！');
			raiseRate.setValue("");
			return;
		}
			var coupon = this.getCmpByName('plBidPlan.coupon').getValue();
			var checkaddRate = this.getCmpByName('checkaddRate').getValue();
		if(coupon==true){
			var rebateType=this.getCmpByName('plBidPlan.rebateType').getValue();
			if(rebateType==""){
				Ext.Msg.alert('操作信息','请选择返利类型');
				return;
			}
			if(rebateType!=4){
				var returnRatio = this.getCmpByName('plBidPlan.returnRatio').getValue();
				var maxCouponMoney = this.getCmpByName('plBidPlan.maxCouponMoney').getValue();
				if(returnRatio==""){
					Ext.Msg.alert('操作信息','面值折现例不能为空');
					return;
				}
				if(maxCouponMoney==""){
					Ext.Msg.alert('操作信息','单笔最大面值不能为空');
					return;
				}
			}
			var rebateWay=this.getCmpByName('plBidPlan.rebateWay').getValue();
			if(rebateWay==""){
				Ext.Msg.alert('操作信息','请选择返利方式');
				return;
			}
		}
		if(checkaddRate==true){
			var addRate = this.getCmpByName('plBidPlan.addRate').getValue();
			if(addRate==""){
				Ext.Msg.alert('操作信息','请填写普通加息利率');
				return;
			}
		}
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/savePlBidPlan.do',

			callback : function(fp, action) {
				if (this.gp != null) {
					this.gp.getStore().reload();
				}
				this.close();
			}
		});
	},// end of save
   fillPayWay:function(payWay){
   	 	if(payWay==1){
   	 		Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML=Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML.replace(Ext.getCmp('paymentWay1').boxLabel,"等额本息")
   	 	}else if(payWay==2){
   	 		Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML=Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML.replace(Ext.getCmp('paymentWay1').boxLabel,"等额本金 ")
   	 	}else if(payWay==3){
   	 		Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML=Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML.replace(Ext.getCmp('paymentWay1').boxLabel,"等本等息 ")
   	 	}else if(payWay==4){
   			Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML=Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML.replace(Ext.getCmp('paymentWay1').boxLabel,"按期收息,到期还本")
   	 	}else{
   			Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML=Ext.getCmp('paymentWay1').resizeEl.dom.innerHTML.replace(Ext.getCmp('paymentWay1').boxLabel,"其他还款方式")
   	 	}
   	},
     fillPayWay1:function(payWay){
   		if(payWay=='dayPay'){
   			Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML=Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML.replace(Ext.getCmp('payAcctualType1').boxLabel,"日")
   		}else if(payWay=='monthPay'){
   			Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML=Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML.replace(Ext.getCmp('payAcctualType1').boxLabel,"月")
   		}else if(payWay=='seasonPay'){
   			Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML=Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML.replace(Ext.getCmp('payAcctualType1').boxLabel,"季 ")
   		}else if(payWay=='yearPay'){
   			Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML=Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML.replace(Ext.getCmp('payAcctualType1').boxLabel,"年")
   		}else if(payWay=='owerPay'){
   			Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML=Ext.getCmp('payAcctualType1').resizeEl.dom.innerHTML.replace(Ext.getCmp('payAcctualType1').boxLabel,"自定义周期,"+custDate+"天每期")
   		}
   }
});