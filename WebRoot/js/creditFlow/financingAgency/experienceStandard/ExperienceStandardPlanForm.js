/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
ExperienceStandardPlanForm = Ext.extend(Ext.Window, {
	isCreate:false,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.isCreate) != "undefined") {
			this.isCreate = _cfg.isCreate;
		}
		// 必须先初始化组件
		this.initUIComponents();
		ExperienceStandardPlanForm.superclass.constructor.call(this, {
					id : 'ExperienceStandardPlanForm',
					layout : 'fit',
					border : false,
					items : [this.formPanel],
					modal : true,
					height : 600,
					width : 1000,
					title : '制定体验标计划',
					buttonAlign : 'center',
					buttons : [{
							//	text :this.isAllReadOnly?"起息":'保存',
						        text :"起息",
								iconCls : 'btn-transition1',
								hidden : this.isHidden,
								scope : this,
								handler : this.save
							}, {
								text :"保存",
								iconCls : 'btn-save',
								hidden : this.isAllReadOnly,
								scope : this,
								handler : this.submit
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								hidden : this.isAllReadOnly,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								hidden : this.isAllReadOnly,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var leffwidth = 100;
	    this.ExperienceStandardPlanOrder=	new ExperienceStandardPlanOrder({
	      mmplanId: this.mmplanId,
	      keystr:"experience",
	      isAutocreate:this.isAutocreate,
	      state:this.state
	  	});	
	  	
		this.formPanel = new Ext.FormPanel({
			name:'experienceFormPanel',
			layout : 'column',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			items : [{
				columnWidth : .63,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
							xtype : 'hidden',
							name : 'plManageMoneyPlan.isStartDatePay',
							value:'2'
						},{
							xtype : 'hidden',
							name : 'plManageMoneyPlan.mmplanId'
						}, {
							fieldLabel : '招标项目名称',
							xtype : 'textfield',
							name:"plManageMoneyPlan.mmName",
							readOnly : this.isAllReadOnly,
							allowBlank : false,
							anchor : '100%',
							blankText : "招标项目名称不能为空，请正确填写!"
						}]
						},{
							columnWidth : .03,
							layout : 'form',
							labelWidth : 20,
							labelAlgin : 'left',
							items : [{
								fieldLabel : " ",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .31,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '招标项目编号',
										name : 'plManageMoneyPlan.mmNumber',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										xtype : 'textfield',
										anchor : '100%',	
										listeners : {
											scope:this,
											'blur' : function(f) {
													var mmNumber = f.getValue();
													Ext.Ajax.request({
									                   url:  __ctxPath + '/creditFlow/financingAgency/isCheckMmNumberPlManageMoneyPlan.do?mmNumber='+mmNumber,
									                   method : 'POST',
									                   success : function(response,request) {
															var obj=Ext.util.JSON.decode(response.responseText);
						                            		if(obj.flag=="1"){					                            			
						                            			Ext.ux.Toast.msg('操作信息',"该类型的编号已经重复，请重新输入");
						                            			f.setValue("");
						                            		}
								                      }
						                             });
											}
										}
									}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 18,
							items : [{
										fieldLabel : " ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},{
							columnWidth :.12,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
								fieldLabel : '贷款利率',
								allowBlank : false
							}]
					    },{
							columnWidth : .24,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '年利率',
										name : 'plManageMoneyPlan.yeaRate',
										xtype : 'numberfield',
										fieldClass : 'field-align',
										readOnly : this.isAllReadOnly,
										decimalPrecision : 8,
										allowBlank : false,
										anchor : '100%',
										listeners : {
											scope : this,
											'change' : function(nf){
												var monthRate=this.getCmpByName('plManageMoneyPlan.monthRate');
												var dayRate=this.getCmpByName('plManageMoneyPlan.dayRate');
												monthRate.setValue(nf.getValue()/12)
												dayRate.setValue(nf.getValue()/360)
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
							columnWidth : .24,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '月利率',
										name : 'plManageMoneyPlan.monthRate',
										xtype : 'numberfield',
										fieldClass : 'field-align',
										readOnly : true,
										allowBlank : false,
										decimalPrecision : 8,
										anchor : '100%'
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
							columnWidth : .31,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '日利率',
										name : 'plManageMoneyPlan.dayRate',
										xtype : 'numberfield',
										fieldClass : 'field-align',
										readOnly : true,
										allowBlank : false,
										decimalPrecision : 8,
										anchor : '100%'
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
							columnWidth :.14,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
								fieldLabel : '还款方式',
								allowBlank : false
							}]
					    },{
							columnWidth :.19, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [{
										name : 'plManageMoneyPlan.isOne',
										xtype : 'radio',
										fieldClass : 'field-align',
										checked:true,
										inputValue : 1,
										boxLabel : '一次性付息',
										anchor : '100%'
									}]
						},{
							columnWidth :.14,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
								fieldLabel : '付息周期',
								allowBlank : false
							}]
					    },{
							columnWidth :.19, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 10,
							items : [{
										name : 'plManageMoneyPlan.payaccrualType',
										xtype : 'radio',
										fieldClass : 'field-align',
										inputValue : 'dayPay',
										checked:true,
										boxLabel : '按日付息',
										anchor : '100%'
									}]
						},{
							columnWidth : .31,
							labelWidth : leffwidth,
							layout : 'form',
							items : [{
										fieldLabel : '投资期限',
										name : 'plManageMoneyPlan.investlimit',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										fieldClass : 'field-align',
										xtype : 'numberfield',
										anchor : '100%'
			
									}]
						},{
							columnWidth : .03,
							layout : 'form',
							labelWidth : 20,
							labelAlgin : 'left',
							items : [{
								fieldLabel : "天",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .28,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '本次招标金额',
										name : 'plManageMoneyPlan.sumMoney',
										xtype : 'moneyfield',
										fieldClass : 'field-align',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										anchor : '100%'
									}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 25,
							items : [{
										fieldLabel : "<span style='margin-left:1px'>元</span> ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},{
							columnWidth : .3,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '投资起点金额',
										name : 'plManageMoneyPlan.startMoney',
										xtype : 'moneyfield',
										fieldClass : 'field-align',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										anchor : '100%'
									}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 25,
							items : [{
										fieldLabel : "<span style='margin-left:1px'>元</span> ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},{
							columnWidth : .31,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '单笔投资上限',
										name : 'plManageMoneyPlan.limitMoney',
										xtype : 'moneyfield',
										fieldClass : 'field-align',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										anchor : '100%'
									}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 18,
							items : [{
										fieldLabel : "<span style='margin-left:1px'>元</span> ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},{
							columnWidth : .28,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '招标开放时间',
										name : 'plManageMoneyPlan.plmmStartTime',
										xtype : 'numberfield',
										maxValue : 10000000,
										fieldClass : 'field-align',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										anchor : '100%',
										listeners : {
											scope : this,
											'blur' : function(nf){
												var investlimit=this.getCmpByName('plManageMoneyPlan.plmmStartTime').getValue();
												var dtstr=this.getCmpByName('plManageMoneyPlan.buyStartTime').getValue();
												if(dtstr){
													if(Ext.isEmpty(investlimit) ||Ext.isEmpty(dtstr) ){
														return;
													}
													var dtstr =parseInt(dtstr.getTime());
													var n=parseInt(investlimit);
													var s=dtstr+n*60*60*1000
													this.getCmpByName("plManageMoneyPlan.buyEndTime").setValue(new Date(s));
												}
											}
										}
									}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 25,
							
							items : [{
										fieldLabel : "<span style='margin-left:1px'>小时</span> ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '开始投标时间',
										name : 'plManageMoneyPlan.buyStartTime',
										xtype : 'datetimefield',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										format : 'Y-m-d H:i:s',
										//value : new Date(),
										anchor : '100%',
								//		minValue:new Date(),
										listeners : {
											scope : this,
											'blur' : function(nf){
												var investlimit=this.getCmpByName('plManageMoneyPlan.plmmStartTime').getValue();
												var dtstr=this.getCmpByName('plManageMoneyPlan.buyStartTime').getValue();
												if(Ext.isEmpty(investlimit) ||Ext.isEmpty(dtstr) ){
													return;
												}
												var dtstr =parseInt(dtstr.getTime());
												var n=parseInt(investlimit);
												var s=dtstr+n*60*60*1000
												this.getCmpByName("plManageMoneyPlan.buyEndTime").setValue(new Date(s));
											},
											'change' : function(nf){
											var investlimit=this.getCmpByName('plManageMoneyPlan.plmmStartTime').getValue();
												var dtstr=this.getCmpByName('plManageMoneyPlan.buyStartTime').getValue();
												if(Ext.isEmpty(investlimit) ||Ext.isEmpty(dtstr) ){
													return;
												}
												var dtstr =parseInt(dtstr.getTime());
												var n=parseInt(investlimit);
												var s=dtstr+n*60*60*1000
												this.getCmpByName("plManageMoneyPlan.buyEndTime").setValue(new Date(s));
											}
										}
									}]
						}, {
							columnWidth : .31,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '招标截止时间！',
										name : 'plManageMoneyPlan.buyEndTime',
										readOnly : true,//this.isAllReadOnly,
										allowBlank : false,
										xtype : 'datetimefield',
										format : 'Y-m-d H:i:s',
										anchor : '100%'
									}]
						},{
							columnWidth : .03, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 18,
							items : [{
										fieldLabel : " ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},/*{
							columnWidth : .31,
							layout : 'form',
							labelWidth : leffwidth,
							items : [{
										fieldLabel : '预售公告时间',
										name : 'plManageMoneyPlan.preSaleTime',
										xtype : 'datetimefield',
										fieldClass : 'field-align',
										format : 'Y-m-d H:i:s',
										readOnly : this.isAllReadOnly,
										anchor : '100%'
									}]
						},{
							columnWidth : .02, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 18,
							items : [{
										fieldLabel : " ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},*/{
							columnWidth : .28,
							layout : 'form',
							labelWidth : leffwidth,
							hidden:!this.isAllReadOnly,
							items : [{
										fieldLabel : '起息日期',  
										name : 'plManageMoneyPlan.startinInterestTime',
										xtype : 'datefield',
										format : 'Y-m-d H:i:s',
										readOnly : this.isHidden==true?this.isAllReadOnly:false,
										allowBlank : !this.isAllReadOnly,
										anchor : '100%',
										listeners : {
											scope : this,
											'change' : function(nf){
												var dtstr = Ext.util.Format.date(nf.getValue(), 'Y-m-d H:i:s');
												var date = new Date(dtstr)
												var day=this.getCmpByName("plManageMoneyPlan.investlimit").getValue();
												date.setDate(date.getDate()+day);
												var month=date.getMonth()+1;
												var enddate=new Date(date.getFullYear()+"-"+month+"-"+date.getDate()+" 00:00:00")
										        this.getCmpByName("plManageMoneyPlan.endinInterestTime").setValue( enddate);
											}
									    }
									}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 25,
							hidden:this.isAllReadOnly==true?false:true,
							items : [{
										fieldLabel : "<span style='margin-left:1px'></span> ",
										labelSeparator : '',
										anchor : "100%"
			
									}]
						},{
							columnWidth : .3,
							layout : 'form',
							labelWidth : leffwidth,
							hidden:!this.isAllReadOnly,
							items : [{
										fieldLabel : '到期日期',
										name : 'plManageMoneyPlan.endinInterestTime',
										xtype : 'datefield',
										format : 'Y-m-d H:i:s',
										readOnly : true,
										allowBlank : !this.isAllReadOnly,
										anchor : '100%'
									}]
						},{
							columnWidth : 1,
							labelWidth : leffwidth,
							layout : 'form',
							items : [this.isAllReadOnly==true?{
							fieldLabel : '招标说明',
										name : 'plManageMoneyPlan.bidRemark',
										readOnly : this.isAllReadOnly,
										xtype : 'label',
										anchor : '100%'
										}:{
										fieldLabel : '招标说明',
										name : 'plManageMoneyPlan.bidRemark',
										xtype : 'fckeditor',
										value:this.isCreate==true?this.bidRemark:"",
										anchor : '100%'
									}]
						}, { 
							columnWidth : 1,
							hidden : !this.isAllReadOnly,
							labelWidth : leffwidth,
							layout : 'form',
							items : [this.ExperienceStandardPlanOrder]
						}]
		});

		// 加载表单对应的数据
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='+this.mmplanId,
				root : 'data',
			//	preName : ['plManageMoneyPlan'],
				scope:this,
				success : function(response, options) {
					 var respText = response.responseText;  
				     var alarm_fields = Ext.util.JSON.decode(respText); 
				     if(this.isAllReadOnly==true){
						this.getCmpByName('plManageMoneyPlan.bidRemark').getEl().dom.innerHTML=alarm_fields.data.plManageMoneyPlan.bidRemark;
					}
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
		var fpl = this.formPanel;
		/*if(this.isAllReadOnly){*/
		var order=this.ExperienceStandardPlanOrder.gridPanel.store.data;
		var data=null;
		if(order){
			data=order.items[0].data
			if(data.intentDate){
				 $postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/startExperiencePlManageMoneyPlan.do?keystr=experience',
					callback : function(fp, action) {
						 this.close();
					}
			    });
			}else{
				Ext.Msg.alert('操作信息','<font color=red>请先生成款项台账！<font>')
			}
		}
			  
		/*}else{
		
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/saveExperiencePlManageMoneyPlan.do?keystr=experience',
			callback : function(fp, action) {
				var obj = Ext.util.JSON.decode(action.response.responseText);
				fpl.getCmpByName("plManageMoneyPlan.mmplanId").setValue(obj.id);
		
				var gridPanel = Ext.getCmp('ExperienceStandardPlanPublishGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				
			}
		});
		}*/
		

	},// end of save
    submit:function(){

	$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/saveExperiencePlManageMoneyPlan.do?keystr=experience',
			callback : function(fp, action) {
				
				var gridPanel = Ext.getCmp('ExperienceStandardPlanPublishGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				 this.close();
			}
		});
	
	
}
});