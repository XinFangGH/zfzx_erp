/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
SuperviseFundIntent = Ext.extend(Ext.Window, {
	
			// 构造函数
			constructor : function(_cfg) {
			if(typeof(_cfg.projId)!="undefined")
			{
			      this.projectId=_cfg.projId;
			}
			if(typeof(_cfg.businessType)!="undefined")
			{
			      this.businessType=_cfg.businessType;
			}
			if(typeof(_cfg.projectMoney)!="undefined")
			{
			      this.projectMoney=_cfg.projectMoney;
			}
			if(typeof(_cfg.startDate)!="undefined")
			{
			      this.startDate=_cfg.startDate;
			}
			if(typeof(_cfg.payaccrualType)!="undefined")
			{
			      this.payaccrualType=_cfg.payaccrualType;
			}
			if(typeof(_cfg.dayOfEveryPeriod)!="undefined")
			{
			      this.dayOfEveryPeriod=_cfg.dayOfEveryPeriod;
			}
			if(typeof(_cfg.payintentPeriod)!="undefined")
			{
			      this.payintentPeriod=_cfg.payintentPeriod;
			}
			if(typeof(_cfg.isStartDatePay)!="undefined")
			{
			      this.isStartDatePay=_cfg.isStartDatePay;
			}
			if(typeof(_cfg.payintentPerioDate)!="undefined")
			{
			      this.payintentPerioDate=_cfg.payintentPerioDate;
			}
			if(typeof(_cfg.intentDate)!="undefined")
			{
			      this.intentDate=_cfg.intentDate;
			}
			if(typeof(_cfg.accrual)!="undefined")
			{
			      this.accrual=_cfg.accrual;
			}
			if(typeof(_cfg.managementConsultingOfRate)!="undefined")
			{
			      this.managementConsultingOfRate=_cfg.managementConsultingOfRate;
			}
			if(typeof(_cfg.accrualtype)!="undefined")
			{
			      this.accrualtype=_cfg.accrualtype;
			}
			if(typeof(_cfg.objectfinace)!="undefined")
			{
			      this.objectfinace=_cfg.objectfinace;
			}
			if(typeof(_cfg.isHiddenbackBtn)!="undefined")
			{
			      this.isHiddenbackBtn=_cfg.isHiddenbackBtn;
			}
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SuperviseFundIntent.superclass.constructor.call(this, {
							id : 'SuperviseFundIntent',
							  border : false,
					            title: "贷款试算", 
					            autoScroll: true, 
					            maximizable :true,
					            height:450,
					            width:1100,  
					            modal : false,
					            labelWidth:45,  
					            plain:true,  
					            resizable:true,  
					            frame:true,
					            buttonAlign:"center",  
					            closable:true,
					            items:[
						                 this.fp
						            ]
						         ,listeners: {}

						});
			},// end of the constructor
			// 初始化组件
			
			initUIComponents : function() {
				var objectfinace=this.objectfinace;
				var projectInfoFinance= new SuperviseFinancePanel({
					projectId:this.projectId,
					businessType:this.businessType,
					idDefinition:'supervise' ,
					 continuationMoney:this.continuationMoney,
					 startDate:this.startDate,
					 endDate:this.endDate,
					 payaccrualType:this.payaccrualType,
					 dayOfEveryPeriod:this.dayOfEveryPeriod,
					 payintentPeriod:this.payintentPeriod,
					 isStartDatePay:this.isStartDatePay,
					 payintentPerioDate:this.payintentPerioDate,
					 intentDate:this.intentDate,
					 continuationRate:this.continuationRate,
					 managementConsultingOfRate:this.managementConsultingOfRate,
					 financeServiceOfRate : this.financeServiceOfRate,
					 accrualtype:this.accrualtype,
					 objectfinace:objectfinace,
					 isPreposePayAccrualsupervise:this.isPreposePayAccrualsupervise,
					 isInterestByOneTime:this.isInterestByOneTime,
					 yearAccrualRate:this.yearAccrualRate,
					 dayAccrualRate:this.dayAccrualRate,
					 sumAccrualRate:this.sumAccrualRate,
					 yearManagementConsultingOfRate:this.yearManagementConsultingOfRate,
					 dayManagementConsultingOfRate:this.dayManagementConsultingOfRate,
					 sumManagementConsultingOfRate:this.sumManagementConsultingOfRate,
					 yearFinanceServiceOfRate:this.yearFinanceServiceOfRate,
					 dayFinanceServiceOfRate:this.dayFinanceServiceOfRate,
					 sumFinanceServiceOfRate:this.sumFinanceServiceOfRate,
					 continuationRateNew:this.continuationRateNew,
					 financeServiceOfRate:this.financeServiceOfRate,
					 dateMode:this.dateMode,
					 idDefinition1:this.idDefinition1,
					 isHiddenbackBtn:this.isHiddenbackBtn
					 
					
				});
				
				var gridPanel = new SuperviseIntentGrid({
					projId : this.projectId,
					businessType:this.businessType,
					object : projectInfoFinance,
					calcutype : 1 ,           //贷款
					isHidden:false
					
					
				});
			
				fillDataSupervise(projectInfoFinance,'supervise',this.projectId,this.continuationMoney,this.payaccrualType,this.accrualtype,this.isStartDatePay,this.isPreposePayAccrualsupervise,this.isInterestByOneTime,this.dateMode);
				 this.fp=  new Ext.FormPanel({
						modal : true,
						labelWidth : 100,
						frame:true,
						buttonAlign : 'center',
						layout : 'form',
						border : false,
						autoHeight: true,  
						defaults : {
							anchor : '100%',
							xtype : 'fieldset',
							columnWidth : 1,
							labelAlign : 'right',
							collapsible : true,
							autoHeight : true
						},
						labelAlign : "right",
						items : [
						 
						{
							title : '资金款项信息',
							xtype : 'fieldset',
							autoHeight : true,
							name:"historyfinance",
							collapsible : true,
							width : '100%',
							bodyStyle : 'padding-left:8px',
							items : [projectInfoFinance,gridPanel]
						}]
        })
				
			},
		closeac:function(){
			
			
			
		}

		});

