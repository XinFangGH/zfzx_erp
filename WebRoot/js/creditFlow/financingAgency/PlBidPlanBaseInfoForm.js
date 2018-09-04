/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PlBidPlanBaseInfoForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 调用父类构造
		PlBidPlanBaseInfoForm.superclass.constructor.call(this, {
			layout : 'column',
			items : [{
				columnWidth : .66,
				layout : 'form',
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
					xtype : 'textfield',
					fieldLabel : '招标项目名称',
					readOnly : true,
					name : 'bidPlan.bidProName',
					anchor : '95.5%'
				}]
			},{
				columnWidth : .34,
				layout : 'form',
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
					xtype : 'textfield',
					fieldLabel : '招标项目编号',
					readOnly : true,
					name : 'bidPlan.bidProNumber',
					anchor : '89%'
				}]
			},{
				columnWidth : .33, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
					anchor : '91%',
					xtype : 'combo',
					name : 'bidPlan.biddingTypeId',
					fieldLabel : '产品类型',
					valueField : 'typeId',
					displayField : 'typeName',
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					allowBlank : false,
					readOnly : true,
					store : new Ext.data.SimpleStore({
						url : __ctxPath
								+ "/creditFlow/financingAgency/typeManger/loadItemPlBiddingType.do?Q_biddingTypeId_L_LT=3",
						autoLoad : true,
						fields : ['typeId', 'typeName']

					}),
					listeners : {
						scope : this,
						afterrender : function(combox) {
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
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
					hiddenName : 'bidPlan.startInterestType',
					xtype : 'combo',
					fieldLabel : '起息日类型',
					allowBlank : false,
					editable : false,
					readOnly : true,
					mode : 'local',
					anchor : '91%',
					triggerAction : 'all',
					store : [['0', 'T(投标日+1天)'],
							['1', 'T(招标截止日+1天)'],
							['2', 'T(满标日+1天)']]
				}]
			}, {
				columnWidth : .3, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
							fieldLabel : '投资起点金额',
							name : 'bidPlan.startMoney',
							fieldClass : 'field-align',
							readOnly : true,
							xtype : 'moneyfield',
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
							fieldLabel : '递增单位',
							name : 'bidPlan.riseMoney',
							fieldClass : 'field-align',
							readOnly : true,
							xtype : 'moneyfield',
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
							fieldLabel : '单笔投资上限',
							name : 'bidPlan.maxMoney',
							fieldClass : 'field-align',
							readOnly : true,
							xtype : 'moneyfield',
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
			},{
				columnWidth : .3, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
							fieldLabel : '预售公告时间',
							name : 'bidPlan.prepareSellTime',
							readOnly : true,
							xtype : 'datefield',
							format : 'Y-m-d H:i:s',
							anchor : '100%',
							dateRangeText:"预售公告时间不能大于开始投标时间",
							anchor : '100%'

						}]
			},{
				columnWidth : 1,
				layout : 'column',
				items : [{
				columnWidth : .3, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
							fieldLabel : '招标开放期限',
							name : 'bidPlan.bidStartTime',
							readOnly : true,
							xtype : 'numberfield',
							allowBlank : false,
							anchor : '100%'

						}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 24,
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
							name : 'bidPlan.publishSingeTime',
							readOnly : true,
							xtype : 'datefield',
							allowBlank : false,
							format : 'Y-m-d H:i:s',
							// allowBlank : false,
							anchor : '100%'

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
							name : 'bidPlan.bidEndTime',
							xtype : 'datefield',
							format : 'Y-m-d H:i:s',
							anchor : '100%',
							readOnly : true

						}]
			}]
			}, {
				columnWidth : .3, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 90,
				labelAlign : 'right',
				items : [{
							fieldLabel : '补偿息天数',
							name : 'bidPlan.penaltyDays',
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : true,
							value :0,
							anchor : '100%'

						}]
			},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "天 ",
									labelSeparator : '',
									anchor : "90%"
								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '募集期利率',
									name : 'bidPlan.raiseRate',
									xtype : 'numberfield',
									allowBlank : false,
									readOnly : true,
									value :0,
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
					} ,{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '是否新手专享',
									name : 'bidPlan.novice',
									xtype : 'checkbox',
									boxLabel : '适用于第一次投标用户',
									allowBlank : false,
									readOnly : true,
									anchor : '100%',
									disabled:true

								}]
					},   {
						columnWidth : .55, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '可用优惠券',
									name : 'bidPlan.coupon',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									readOnly : true,
									anchor : '100%',
									disabled:true

								}]
					}, {
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '普通加息',
									name : 'ptjx',
									xtype : 'checkbox',
									boxLabel : '是',
									allowBlank : false,
									readOnly : true,
									anchor : '100%',
									disabled:true

								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '<font color="red">*</font>面值折现比',
									name : 'bidPlan.returnRatio',
								//	id :'ratio_text',
									xtype : 'numberfield',
									anchor : '100%',
									readOnly:true

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
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '单笔最大面值',
									name : 'bidPlan.maxCouponMoney',
								//	id : 'maxMoney_text',
									xtype : 'numberfield',
									anchor : '100%',
									scope:this,
									readOnly:true

								}]
					},{
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元",
									labelSeparator : '',
									anchor : "90%"
								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '普通加息利率',
									name : 'bidPlan.addRate',
								//	id : 'addRate_text',
									xtype : 'numberfield',
									anchor : '100%',
									readOnly:true

								}]
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '返利类型',
									name : 'check_e',
									xtype : 'checkbox',
									boxLabel : '返现',
									allowBlank : false,
									readOnly : this.readOnly,
									anchor : '100%',
									disabled:true
								},{
									fieldLabel : '返利方式',
									name : 'check_ee',
									xtype : 'checkbox',
									boxLabel : '立返',
									allowBlank : false,
									readOnly : this.readOnly,
									anchor : '100%',
									disabled:true
								},{
									name : 'bidPlan.rebateType',
									xtype : 'numberfield',
									hidden:true
								},{
									name : 'bidPlan.rebateWay',
									xtype : 'numberfield',
									hidden:true
								}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						labelAlign : 'right',
						items : [{
							fieldLabel : '',
							name : 'check_f',
							xtype : 'checkbox',
							boxLabel : '返息',
							anchor : '100%',
							disabled:true
						},{
							fieldLabel : '',
							name : 'check_ff',
							xtype : 'checkbox',
							boxLabel : '随期',
							anchor : '100%',
							disabled:true
						}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						labelAlign : 'right',
						items : [{
									fieldLabel : '',
									name : 'check_g',
									xtype : 'checkbox',
									boxLabel : '返息现',
									anchor : '100%',
									disabled:true

								},{
									fieldLabel : '',
									name : 'check_ii',
									xtype : 'checkbox',
									boxLabel : '到期',
									anchor : '100%',
									disabled:true
								}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						labelAlign : 'right',
						items : [{
									fieldLabel : '',
									name : 'check_h',
									xtype : 'checkbox',
									boxLabel : '加息',
									anchor : '100%',
									disabled:true

								}]
					},{
				columnWidth : 1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelAlign : 'right',
				labelWidth : 90,
				items : [{
							fieldLabel : "招标说明 ",
							xtype : 'textarea',
							readOnly : true,
							name : 'bidPlan.bidRemark',
							anchor : '96%'
						}]
			}]
		});
		
		if(this.bidId){
			this.loadData({
				url : __ctxPath + "/creditFlow/financingAgency/getToTimeDatePlBidPlan.do?bidId="+this.bidId,
				root : 'data',
				preName : 'bidPlan',
				scope : this,
				success : function(response, options) {
					var respText = response.responseText;
					var result = Ext.util.JSON.decode(respText);
					if(result !=null && result != "undefined"){
						this.getCmpByName('startMoney1').setValue(Ext.util.Format.number(result.data.startMoney,'0,000.00'));
						this.getCmpByName('riseMoney1').setValue(Ext.util.Format.number(result.data.riseMoney,'0,000.00'));
						this.getCmpByName('maxMoney1').setValue(Ext.util.Format.number(result.data.maxMoney,'0,000.00'));
					}
				}
			});
		}
		
	}
});
