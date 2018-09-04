function fillDataFinanceCalulate(mainObj,idDefinition,projectId,projectMoney,payaccrualType,accrualtype,isStartDatePay,isPreposePayAccrual,isInterestByOneTime,dateMode){

	mainObj.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(projectMoney,'0,000.00'));
				var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
				if (isInterestByOneTime == 1) {
					if (null != isInterestByOneTimeObj)
						isInterestByOneTimeObj.setValue(true);
				} else {
					if (null != isInterestByOneTimeObj)
						isInterestByOneTimeObj.setValue(false);
				}
				var isPreposePayConsultingCheckObj = mainObj
						.getCmpByName('isPreposePayAccrualCheck');
						
				var isPreposePayConsultingCheck = isPreposePayAccrual;
				if (isPreposePayConsultingCheck == 1) {
					if (null != isPreposePayConsultingCheckObj)
						isPreposePayConsultingCheckObj.setValue(true);
				} else {
					if (null != isPreposePayConsultingCheckObj)
						isPreposePayConsultingCheckObj.setValue(false);
				}
				var idDefinition1=idDefinition;
				  idDefinition=projectId+idDefinition;
				  if(accrualtype=="sameprincipal"){
				     	 Ext.getCmp("jixifs1"+idDefinition).setValue(true);
				     	  Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				     	  
				     	  
				     	  Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
				  }else if(accrualtype=="sameprincipalandInterest"){
				        Ext.getCmp("jixifs2"+idDefinition).setValue(true);
				        Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				        
				        Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
				  }else if(accrualtype=="sameprincipalsameInterest"){
				        Ext.getCmp("jixifs5"+idDefinition).setValue(true);
				        
				        
				        
				  }else if(accrualtype=="singleInterest"){
				        Ext.getCmp("jixifs3"+idDefinition).setValue(true);
				        
				        
				  } else if (accrualtype == "sameprincipalsameInterest") {
						Ext.getCmp("jixifs5" + idDefinition).setValue(true);
						Ext.getCmp("jixifs3" + idDefinition).setValue(false);
					}else if(accrualtype=="ontTimeAccrual"){
				         Ext.getCmp("jixifs4"+idDefinition).setValue(true);
				     	  Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				     	  
				     	  Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
				     	  
				    
							                   
				  	
				  } else if (accrualtype == "otherMothod") {
					Ext.getCmp("jixifs6" + idDefinition).setValue(true);
					Ext.getCmp("jixifs3" + idDefinition).setValue(false);
				  }
				mainObj.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);//默认为不可用
				   if(payaccrualType=="dayPay"){
				     	Ext.getCmp("jixizq1"+idDefinition).setValue(true);
				     	
				  }else if(payaccrualType=="monthPay"){
				        Ext.getCmp("jixizq2"+idDefinition).setValue(true);
				 }else if(payaccrualType=="seasonPay"){
				        Ext.getCmp("jixizq3"+idDefinition).setValue(true);
				  }else if(payaccrualType=="yearPay"){
				        Ext.getCmp("jixizq4"+idDefinition).setValue(true);
				  }else if(payaccrualType=="owerPay"){
				       Ext.getCmp("jixizq6"+idDefinition).setValue(true);
				    //   	mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
				  }
			    if(isStartDatePay=="1"){
			         Ext.getCmp("meiqihkrq1"+idDefinition).setValue(true);
			             Ext.getCmp("meiqihkrq2"+idDefinition).setValue(false);
			         mainObj.getCmpByName('flFinancingProject.isStartDatePay').setValue("1");
			      
			    }else{
			     Ext.getCmp("meiqihkrq2"+idDefinition).setValue(true);
			    	mainObj.getCmpByName('flFinancingProject.payintentPerioDate').setDisabled(true);
			    	mainObj.getCmpByName('flFinancingProject.isStartDatePay').setValue("2");
			    }
			    
				if(null!=dateMode){
					var dateModelCom=mainObj.getCmpByName('flFinancingProject.dateMode');
					var st=dateModelCom.getStore();
					if(idDefinition1=='calculate'){
						st.on('load',function(){
							dateModelCom.setValue(dateMode)
						})
					}else{
						dateModelCom.setValue(dateMode)
					}
				}
			 
			    
		
			       
			       
				
			   

                
               
			    
			 

				
	
}
