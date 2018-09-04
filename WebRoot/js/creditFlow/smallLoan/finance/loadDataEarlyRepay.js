function fillDataEarlyRepay(mainObj,alarm_fields,idDefinition){
//	      if(alarm_fields==null){
//			     
//			    	mainObj.getCmpByName('slEarlyRepaymentRecord.payintentPerioDate').setDisabled(true);
//			    	mainObj.getCmpByName('slEarlyRepaymentRecord.isStartDatePay').setValue("2");
//			   
//			          mainObj.getCmpByName('mqhkri1').hide();
//			    
//	      
//	      }else {
//			    
//			    var isPreposePayAccrualObj = mainObj.getCmpByName('isPreposePayAccrualCheck');
//				var isPreposePayAccrual= alarm_fields.isPreposePayAccrual;
//			    if(isPreposePayAccrual==1){
//			    	 if(null!=isPreposePayAccrualObj)
//				     isPreposePayAccrualObj.setValue(true);
//				}
//				else
//				{    if(null!=isPreposePayAccrualObj) 
//				     isPreposePayAccrualObj.setValue(false);
//				}
//				
//				
//				var projectId=alarm_fields.projectId;
//				  var accrualtype= alarm_fields.accrualtype;
//				  idDefinition=projectId+idDefinition;
//				  if(accrualtype=="sameprincipal"){
//				     	 Ext.getCmp("jixifs1"+idDefinition).setValue(true);
//				     	  Ext.getCmp("jixifs3"+idDefinition).setValue(false);
//				     	  
//				     	  
//				     	  Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
//					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
//					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
//					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
//					          Ext.getCmp("jixizq5"+ idDefinition).setDisabled(true);
//				  }else if(accrualtype=="sameprincipalandInterest"){
//				        Ext.getCmp("jixifs2"+idDefinition).setValue(true);
//				        Ext.getCmp("jixifs3"+idDefinition).setValue(false);
//				        
//				        Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
//					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
//					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
//					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
//					          Ext.getCmp("jixizq5"+ idDefinition).setDisabled(true);
//				  }else if(accrualtype=="singleInterest"){
//				        Ext.getCmp("jixifs3"+idDefinition).setValue(true);
//				        
//				        
//				  }
//				var  payaccrualType=alarm_fields.payaccrualType;
//				   if(payaccrualType=="dayPay"){
//				     	Ext.getCmp("jixizq1"+idDefinition).setValue(true);
//				     	
//				  }else if(payaccrualType=="monthPay"){
//				        Ext.getCmp("jixizq2"+idDefinition).setValue(true);
//				 }else if(payaccrualType=="seasonPay"){
//				        Ext.getCmp("jixizq3"+idDefinition).setValue(true);
//				  }else if(payaccrualType=="yearPay"){
//				        Ext.getCmp("jixizq4"+idDefinition).setValue(true);
//				  }else if(payaccrualType=="ontTimeAccrual"){
//				       Ext.getCmp("jixizq5"+idDefinition).setValue(true);
//				        Ext.getCmp("jixizq2"+idDefinition).setValue(false);
//				  }
//				   var  isStartDatePay=alarm_fields.isStartDatePay;
//				
//			    if(isStartDatePay=="1"){
//			         Ext.getCmp("meiqihkrq1"+idDefinition).setValue(true);
//			         mainObj.getCmpByName('slEarlyRepaymentRecord.isStartDatePay').setValue("1");
//			      
//			    }else{
//			     Ext.getCmp("meiqihkrq2"+idDefinition).setValue(true);
//			    	mainObj.getCmpByName('slEarlyRepaymentRecord.payintentPerioDate').setDisabled(true);
//			    	mainObj.getCmpByName('slEarlyRepaymentRecord.isStartDatePay').setValue("2");
//			    }
//			   if(payaccrualType=="ontTimeAccrual"){
//			            mainObj.getCmpByName('mqhkri').hide();
//			    }else{
//			   
//			          mainObj.getCmpByName('mqhkri1').hide();
//			    
//			    }
//			
//			
//	      }
	
	  if(this.accrualtype=="ontTimeAccrual"){
		  mainObj.getCmpByName('bgks').hide();
    }else{
   
    	mainObj.getCmpByName('bgks1').hide();
    
    }  
}
