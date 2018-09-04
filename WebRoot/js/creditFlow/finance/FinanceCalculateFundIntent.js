/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
FinanceCalculateFundIntent = Ext.extend(Ext.Window, {
	
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
				FinanceCalculateFundIntent.superclass.constructor.call(this, {
							id : 'FinanceCalculateFundIntent1',
							  border : false,
					            title: "贷款试算", 
					            autoScroll: true, 
					            maximizable :true,
					            height:400,
					            width:"85%",  
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
						         ,listeners: {/*
										scope:this,
										beforeclose : function(){
				                           if(this.isHiddenbackBtn==false){
									             var fp=this.fp;
									            
									             var objectfinace=this.objectfinace;
									             var projectId=this.projectId;
									             var this1=this;
									             
									 			var projectMoney=fp.getCmpByName("slSmallloanProject.projectMoney").getValue();
												 var startDate=fp.getCmpByName("slSmallloanProject.startDate").getValue();
												 var payaccrualType=fp.getCmpByName("slSmallloanProject.payaccrualType").getValue();
												 var dayOfEveryPeriod=fp.getCmpByName("slSmallloanProject.dayOfEveryPeriod").getValue();
												 var payintentPeriod=fp.getCmpByName("slSmallloanProject.payintentPeriod").getValue();
												 var isStartDatePay=fp.getCmpByName("slSmallloanProject.isStartDatePay").getValue();
												 var payintentPerioDate=fp.getCmpByName("slSmallloanProject.payintentPerioDate").getValue();
												 var intentDate=fp.getCmpByName("slSmallloanProject.intentDate").getValue();
												 var accrual=fp.getCmpByName("slSmallloanProject.accrual").getValue();
												 var managementConsultingOfRate=fp.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue();
												 var accrualtype=fp.getCmpByName("slSmallloanProject.accrualtype").getValue();
												 
												 var dayAccrual = (accrual+managementConsultingOfRate)*2/30;
													var styearAccrual=dayAccrual.toString().split(".");
													if(styearAccrual.length==2){
														dayAccrual=styearAccrual[0]+"."+Ext.util.Format.substr(styearAccrual[1], 0, 3)
													}
									             if(objectfinace !=null){
										
													
													Ext.Msg.confirm('操作提示','是否要返回数据?',function(btn){
														if(btn=='yes'){
															 
															 objectfinace.getCmpByName("slSmallloanProject.projectMoney").setValue(projectMoney);
															 objectfinace.getCmpByName("slSmallloanProject.startDate").setValue(startDate);
															objectfinace.getCmpByName("slSmallloanProject.dayOfEveryPeriod").setValue(dayOfEveryPeriod);
															 objectfinace.getCmpByName("slSmallloanProject.payintentPerioDate").setValue(payintentPerioDate);
															objectfinace.getCmpByName("slSmallloanProject.payintentPeriod").setValue(payintentPeriod);
															objectfinace.getCmpByName("slSmallloanProject.accrual").setValue(accrual);
															objectfinace.getCmpByName("slSmallloanProject.intentDate").setValue(intentDate);
															objectfinace.getCmpByName("slSmallloanProject.managementConsultingOfRate").setValue(managementConsultingOfRate);
															objectfinace.getCmpByName("slSmallloanProject.overdueRate").setValue(dayAccrual);
															fillDatacalulate(objectfinace,'liucheng',projectId,projectMoney,payaccrualType,accrualtype,isStartDatePay)
														//	this1.destroy() ;
														}else{
														//	this1.destroy() ;
														}
													}) ;
									             }else{
									            	// this1.destroy() ;
									            	 
									            	 
									             }
				
				                           }
								      
								    }
								*/}

						});
			},// end of the constructor
			// 初始化组件
			
			initUIComponents : function() {
				var objectfinace=this.objectfinace;
				var projectInfoFinance= new FinanceCalulateFinancePanel({
					projectId:this.projectId,
					idDefinition:'calculate' ,
					 projectMoney:this.projectMoney,
					 startDate:this.startDate,
					 payaccrualType:this.payaccrualType,
					 dayOfEveryPeriod:this.dayOfEveryPeriod,
					 payintentPeriod:this.payintentPeriod,
					 isStartDatePay:this.isStartDatePay,
					 payintentPerioDate:this.payintentPerioDate,
					 intentDate:this.intentDate,
					 accrual:this.accrual,
					 accrualtype:this.accrualtype,
					 isPreposePayAccrual:this.isPreposePayAccrual,
					 isInterestByOneTime:this.isInterestByOneTime,
					 yearAccrualRate:this.yearAccrualRate,
					 dayAccrualRate:this.dayAccrualRate,
					 sumAccrualRate:this.sumAccrualRate,
					 dateMode:this.dateMode,
					 objectfinace:objectfinace,
					 idDefinition1:this.idDefinition1,
					 isHiddenbackBtn:this.isHiddenbackBtn
					 
					
				});
				var gridPanel = new FinanceCaluateIntentGrid({
					projId : this.projectId,
					object : projectInfoFinance,
					calcutype : 1 ,           //贷款
					businessType : 'Financing',
					isHidden:false
					
					
				});
				fillDataFinanceCalulate(projectInfoFinance,'calculate',this.projectId,this.projectMoney,this.payaccrualType,this.accrualtype,this.isStartDatePay,this.isPreposePayAccrual,this.isInterestByOneTime,this.dateMode);
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

