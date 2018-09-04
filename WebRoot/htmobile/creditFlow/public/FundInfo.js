
//ownFund.js
var only;
Ext.define('htmobile.creditFlow.public.FundInfo', {
    extend: 'Ext.form.Panel',
    name: 'FundInfo',
    constructor: function (config) {
    	config = config || {};
    	Ext.apply(this,config);
    	this.data=config.result;
    	if(this.team==1){
    		only=false;
    	}else{
    		only=true;
    	}
    	this.projectId=config.projectId
    	Ext.apply(config,{
    		style:'background-color:white;',
    		title:'资金款项信息',
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	labelWidth:"45%",
		                	readOnly: this.readOnly
		                },
		                items : [{
							xtype : "hiddenfield",
							name : "projectId",
							value : this.projectId

						}, {
							readOnly : only,
							label : '贷款金额',
							name : "slSmallloanProject.projectMoney",
							value : this.data.projectMoney,
							readOnly:this.MoneyIsRdOnly
						}, {
							xtype : 'dickeycombo',
							nodeKey : 'capitalkind',
							label : "币种",
							readOnly : true,
							name : "slSmallloanProject.currency",
							value : Ext.isEmpty(this.data.currency)
									? 449
									: this.data.currency
						}, {
							xtype : 'dicIndepCombo',
							label : "日期模型",
							readOnly : true,
							name : "slSmallloanProject.dateModel",
							nodeKey : 'dateModel',
							value : Ext.isEmpty(this.data.dateMode)
									? "dateModel_360"
									: this.data.dateMode
						},
						// {
						// label: "<div class='fieldlabel'>还款方式: </div>",
						// readOnly: true,
						// value:
						// Ext.isEmpty(this.data.accrualtype)?"按期收息,到期还本":(this.data.accrualtype=='sameprincipal'?"等额本金":(this.data.accrualtype=='sameprincipalandInterest'?"等额本息":(this.data.accrualtype=='sameprincipalsameInterest'?"等本等息":(this.data.accrualtype=='singleInterest'?"按期收息,到期还本":"其他还款方式"))))
						// },
						{
							xtype : 'hiddenfield',
							name : "slSmallloanProject.accrualtype",
							value : "singleInterest"
						}, {
							label : "还款周期",
							xtype : 'selectfield',
							name : 'slSmallloanProject.payaccrualType',
							value : data.payaccrualType,
							id : 'slSmallloanProjectpayaccrualType',
							options : [{
										text : '月',
										value : 'monthPay',
										selected : true
									}, {
										text : '日',
										value : 'dayPay'
									}]

						},
						/*
						 * { label: "<div class='fieldlabel'>还款周期(日/期):</div>",
						 * hidden:this.data.payaccrualType=='owerPay'?false:true,
						 * value:
						 * this.data.payaccrualType=='owerPay'?this.data.dayOfEveryPeriod:"-"
						 *  },
						 */

						{
							label : "贷款期限(期)",
							name : "slSmallloanProject.payintentPeriod",
							id : "slSmallloanProjectpayintentPeriod",
							value : Ext.isEmpty(this.data)
									? null
									: this.data.payintentPeriod,
							listeners : {
								scope : this,
								'change' : function(nf) {
									var payAccrualType = Ext
											.getCmp('slSmallloanProjectpayaccrualType')
											.getValue();
									/*
									 * var dayOfEveryPeriod = this
									 * .getCmpByName('slSmallloanProject.dayOfEveryPeriod')
									 * .getValue();
									 */
									var payintentPeriod = Ext
											.getCmp('slSmallloanProjectpayintentPeriod')
											.getValue();
									var startDate = Ext
											.getCmp('slSmallloanProjectstartDate')
											.getValue();
									var intentDatePanel = Ext
											.getCmp('slSmallloanProjectintentDate');
									this.setIntentDate(payAccrualType, null,
											payintentPeriod, startDate,
											intentDatePanel, this)

								}

							}
						}, {
							label : "放款日期",
							name : "slSmallloanProject.startDate",
							id : "slSmallloanProjectstartDate",
							xtype : 'datepickerfield',
							style : "width:100%;",
							minWidth : 20,
							dateFormat : 'Y-m-d',
							picker : {
								xtype : 'todaypickerux'
							},
							value : Ext.isEmpty(this.data.startDate)
									? null
									: new Date(this.data.startDate),
							listeners : {
								scope : this,
								'change' : function(nf) {
									var payAccrualType = Ext
											.getCmp('slSmallloanProjectpayaccrualType')
											.getValue();
									/*
									 * var dayOfEveryPeriod = this
									 * .getCmpByName('slSmallloanProject.dayOfEveryPeriod')
									 * .getValue();
									 */
									var payintentPeriod = Ext
											.getCmp('slSmallloanProjectpayintentPeriod')
											.getValue();
									var startDate = Ext
											.getCmp('slSmallloanProjectstartDate')
											.getValue();
									var intentDatePanel = Ext
											.getCmp('slSmallloanProjectintentDate');
									this.setIntentDate(payAccrualType, null,
											payintentPeriod, startDate,
											intentDatePanel, this)

								}

							}
						}, {
							label : "还款日期",
							readOnly : true,
							name : "slSmallloanProject.intentDate",
							id : "slSmallloanProjectintentDate",
							xtype : 'datepickerfield',
							style : "width:100%;",
							minWidth : 20,
							dateFormat : 'Y-m-d',
							picker : {
								xtype : 'todaypickerux'
							},
							value : Ext.isEmpty(this.data.intentDate)
									? null
									: new Date(this.data.intentDate)
						}

						, {
							xtype : 'togglefield',
							label : "前置付息",
							name : "slSmallloanProject.isPreposePayAccrual",
							value : Ext.isEmpty(this.data)
									? 0
									: this.data.isPreposePayAccrual
						}, {
							xtype : 'togglefield',
							label : "一次性支付利息",
							name : "slSmallloanProject.isInterestByOneTime",
							value : Ext.isEmpty(this.data)
									? 0
									: this.data.isInterestByOneTime
						}, {

							label : "每期还款日",
							value : Ext.isEmpty(this.data.isStartDatePay)
									? 2
									: (this.data.isStartDatePay == 1 ? "固定在："
											+ this.data.payintentPerioDate
											+ " 号还款" : "按实际放款日对日还款")
						}, {
							label : "贷款利率(%)",
							name : "slSmallloanProject.accrual",
							value : this.data.accrual
						}, {
							label : "管理咨询费率(%)",
							name : "slSmallloanProject.managementConsultingOfRate",
							value : this.data.managementConsultingOfRate
						}, {
							label : "财务服务费率(%)",
							name : "slSmallloanProject.financeServiceOfRate",
							value : this.data.financeServiceOfRate
						}, {
							label : "逾期贷款利率(%/日)",
							name : "slSmallloanProject.overdueRateLoan",
							value : this.data.overdueRateLoan
						}, {
							label : "逾期罚息利率(%/日)",
							name : "slSmallloanProject.overdueRate",
							value : this.data.overdueRate
						}, {
							label : "评估费",
							name : "slSmallloanProject.assessmentFee",
							value : this.data.assessmentFee
						}, {
							label : "抵质费",
							name : "slSmallloanProject.creditQualityFee",
							value : this.data.creditQualityFee
						}, {
							xtype : (this.data != null && this.data.productId == 107)
									? 'hiddenfield'
									: 'textfield',
							label : "停车费",
							name : "slSmallloanProject.parkingFee",
							value : this.data.parkingFee
						}, {
							xtype : 'hiddenfield',
							name : "slSmallloanProject.productId",
							value : this.data.productId
						}, {
							label : "违章保证金",
							name : "slSmallloanProject.illegalDeposit",
							value : this.data.illegalDeposit
						}

				]},
				        {
				            xtype: this.readOnly==true?'hiddenfield':'button',
				            docked:'bottom',
				            name: 'submit',
				            text:'保存',
				            cls : 'submit-button',
				            handler:this.formSubmit
				        }]
    	});

  

    	this.callParent([config]);
    	
    },
    
  setIntentDate:function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel,objectPanel){
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
					if(!Ext.isEmpty(intentDatePanel)){
						intentDatePanel.setValue(new Date(obj.intentDate));
					}
					
					/*
					 * var intentDate=obj.intentDate
					 * 
					 * if(startDate!='' && intentDate!=''){
					 * intentDate=Ext.util.Format.date(intentDate,'Y-m-d')
					 * startDate=Ext.util.Format.date(startDate,'Y-m-d') var
					 * days=((new
					 * Date(intentDate.substring(0,4),intentDate.substring(5,intentDate.lastIndexOf('-'))-1,intentDate.substring(intentDate.lastIndexOf('-')+1,intentDate.length))).getTime()-(new
					 * Date(startDate.substring(0,4),startDate.substring(5,startDate.lastIndexOf('-'))-1,startDate.substring(startDate.lastIndexOf('-')+1,startDate.length))).getTime())/1000/60/60/24
					 * var
					 * dayAccrualRate=objectPanel.getCmpByName('slSmallloanProject.dayAccrualRate').getValue()
					 * if(dayAccrualRate!=''){
					 * objectPanel.getCmpByName('slSmallloanProject.sumAccrualRate').setValue(dayAccrualRate*days) }
					 * var
					 * dayManagementConsultingOfRate=objectPanel.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate').getValue()
					 * if(dayManagementConsultingOfRate!=''){
					 * objectPanel.getCmpByName('slSmallloanProject.sumManagementConsultingOfRate').setValue(dayManagementConsultingOfRate*days) }
					 * var
					 * dayFinanceServiceOfRate=objectPanel.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate').getValue()
					 * if(dayFinanceServiceOfRate!=''){
					 * objectPanel.getCmpByName('slSmallloanProject.sumFinanceServiceOfRate').setValue(dayFinanceServiceOfRate*days) }
					 *  }
					 */
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				}
			});
	},
    formSubmit:function(){	
		 var loginForm = this.up('formpanel');
	     var projectMoney=loginForm.getCmpByName("slSmallloanProject.projectMoney").getValue(); 
		  if(Ext.isEmpty(projectMoney)){
		    Ext.Msg.alert('','贷款金额不能为空');
			return;
		  }
		 var currency=loginForm.getCmpByName("slSmallloanProject.currency").getValue(); 
		  if(Ext.isEmpty(currency)){
		    Ext.Msg.alert('','币种不能为空');
			return;
		  }
		var dateModel=loginForm.getCmpByName("slSmallloanProject.dateModel").getValue(); 
		  if(Ext.isEmpty(dateModel)){
		    Ext.Msg.alert('','日期模型不能为空');
			return;
		  }
		  var payintentPeriod=loginForm.getCmpByName("slSmallloanProject.payintentPeriod").getValue(); 
		  if(Ext.isEmpty(payintentPeriod)){
		    Ext.Msg.alert('','贷款期限不能为空');
			return;
		  }
		 if(!only){
		 var startDate=loginForm.getCmpByName("slSmallloanProject.startDate").getValue(); 
		  if(Ext.isEmpty(startDate)){
		    Ext.Msg.alert('','放款日期不能为空');
			return;
		  }
		var intentDate=loginForm.getCmpByName("slSmallloanProject.intentDate").getValue(); 
		  if(Ext.isEmpty(intentDate)){
		    Ext.Msg.alert('','还款日期不能为空');
			return;
		  }
		 }
		 var accrual=loginForm.getCmpByName("slSmallloanProject.accrual"); 
		  if(Ext.isEmpty(accrual.getValue())){
		    Ext.Msg.alert('','贷款利率不能为空');
			return;
		  }
		var managementConsultingOfRate=loginForm.getCmpByName("slSmallloanProject.managementConsultingOfRate"); 
		  if(Ext.isEmpty(managementConsultingOfRate.getValue())){
		    Ext.Msg.alert('','管理咨询费率不能为空');
			return;
		  }
		 var financeServiceOfRate=loginForm.getCmpByName("slSmallloanProject.financeServiceOfRate"); 
		  if(Ext.isEmpty(financeServiceOfRate.getValue())){
		    Ext.Msg.alert('','财务服务费率不能为空');
			return;
		  }
		var overdueRateLoan=loginForm.getCmpByName("slSmallloanProject.overdueRateLoan").getValue(); 
		  if(Ext.isEmpty(overdueRateLoan)){
		    Ext.Msg.alert('','逾期贷款利率不能为空');
			return;
		  }
		 var overdueRate=loginForm.getCmpByName("slSmallloanProject.overdueRate").getValue(); 
		  if(Ext.isEmpty(overdueRate)){
		    Ext.Msg.alert('','逾期罚息利率不能为空');
			return;
		  }
		 var assessmentFee=loginForm.getCmpByName("slSmallloanProject.assessmentFee").getValue(); 
		  if(Ext.isEmpty(assessmentFee)){
		    Ext.Msg.alert('','评估费不能为空');
			return;
		  }
		 var creditQualityFee=loginForm.getCmpByName("slSmallloanProject.creditQualityFee").getValue(); 
		  if(Ext.isEmpty(creditQualityFee)){
		    Ext.Msg.alert('','抵质费不能为空');
			return;
		  }
		   var productId=loginForm.getCmpByName("slSmallloanProject.productId").getValue(); 
		  if(productId!=107){
		  	var parkingFee=loginForm.getCmpByName("slSmallloanProject.parkingFee").getValue(); 
		  	if(Ext.isEmpty(parkingFee)){
			    Ext.Msg.alert('','停车费不能为空');
				return;
		  	}
		  }
		 var illegalDeposit=loginForm.getCmpByName("slSmallloanProject.illegalDeposit").getValue(); 
		  if(Ext.isEmpty(illegalDeposit)){
		    Ext.Msg.alert('','违章保证金不能为空');
			return;
		  }
		 var payaccrualType=loginForm.getCmpByName("slSmallloanProject.payaccrualType").getValue();
		  if(payaccrualType=="monthPay"){
		  var dayAccrualRate = accrual.getValue()/30;
		  var dayManagementConsultingOfRate = managementConsultingOfRate.getValue()/30;
		  var dayFinanceServiceOfRate = financeServiceOfRate.getValue()/30;
		  }else 
		  if(payaccrualType=="dayPay"){
		  var dayAccrualRate= accrual.getValue();
		  var dayManagementConsultingOfRate = managementConsultingOfRate.getValue();
		  var dayFinanceServiceOfRate = financeServiceOfRate.getValue();
		  accrual.setValue(accrual.getValue()*30);
		  managementConsultingOfRate.setValue(managementConsultingOfRate.getValue()*30);
		  financeServiceOfRate.setValue(financeServiceOfRate.getValue()*30);
		  }
	    var strDate=  formatTime(startDate,"yyyy-MM-dd");
	    var intDate=  formatTime(intentDate,"yyyy-MM-dd");
       	loginForm.submit({
            url: __ctxPath+'/htmobile/creditLoanProjectVmInfo.do',
        	method : 'POST',
        	params:{
        	 "slSmallloanProject.startDate":strDate,
        	 "slSmallloanProject.intentDate":intDate,
        	 "slSmallloanProject.dayAccrualRate":dayAccrualRate,
        	 "slSmallloanProject.dayManagementConsultingOfRate":dayManagementConsultingOfRate,
        	 "slSmallloanProject.dayFinanceServiceOfRate":dayFinanceServiceOfRate
        	 
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','提交成功');
		        		   mobileNavi.pop();
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});}

});
