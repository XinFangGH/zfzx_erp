function fillDataExtension(mainObj,alarm_fields,idDefinition){
	   if(alarm_fields==null){
			    	mainObj.getCmpByName('slSuperviseRecord.payintentPerioDate').setDisabled(true);
			    	mainObj.getCmpByName('slSuperviseRecord.isStartDatePay').setValue("2");
			   
			          mainObj.getCmpByName('mqhkri1').hide();
			    
	      
	      }else {
	      		var isPreposePayAccrualsuperviseObj=mainObj.getCmpByName('isPreposePayAccrualsupervise')
	      		var isPreposePayAccrualsupervise=alarm_fields.data.slSuperviseRecord.isPreposePayConsultingCheck;
	      		if(isPreposePayAccrualsupervise==1){
	      			if(null!=isPreposePayAccrualsuperviseObj){
	      				isPreposePayAccrualsuperviseObj.setValue(true)
	      			}
	      		}else{
	      			if(null!=isPreposePayAccrualsuperviseObj){
	      				isPreposePayAccrualsuperviseObj.setValue(false)
	      			}
	      		}
	      		var isInterestByOneTimeObj=mainObj.getCmpByName('isInterestByOneTimeCheck')
	      		var isInterestByOneTime=alarm_fields.data.slSuperviseRecord.isInterestByOneTime;
	      		if(isInterestByOneTime==1){
	      			if(null!=isInterestByOneTimeObj){
	      				isInterestByOneTimeObj.setValue(true)
	      			}
	      		}else{
	      			if(null!=isInterestByOneTimeObj){
	      				isInterestByOneTimeObj.setValue(false)
	      			}
	      		}
			   // var isPreposePayConsultingCheckObj = mainObj.getCmpByName('isPreposePayConsultingCheck');
			    
				//var isPreposePayConsultingCheck= alarm_fields.data.slSuperviseRecord.isPreposePayConsultingCheck;
					
			   /* if(isPreposePayConsultingCheck==1){
			    	 if(null!=isPreposePayConsultingCheckObj)
				     isPreposePayConsultingCheckObj.setValue(true);
				}
				else
				{    if(null!=isPreposePayConsultingCheckObj) 
				     isPreposePayConsultingCheckObj.setValue(false);
				}*/
	      	
	      	var projectId =null;
	      	var accrualtype =null;
	      	 var  payaccrualType=null;
	      	var  isStartDatePay=null;
	      	
                if(idDefinition=="xiangmubianjiextenstion"){
                	projectId=alarm_fields.data.projectId;
                	accrualtype= alarm_fields.data.accrualtype;
                	 payaccrualType=alarm_fields.data.payaccrualType;
                	 isStartDatePay=alarm_fields.data.isStartDatePay;
                }else{
				   projectId=alarm_fields.data.slSuperviseRecord.id;
				   accrualtype= alarm_fields.data.slSuperviseRecord.accrualtype;
				   payaccrualType=alarm_fields.data.slSuperviseRecord.payaccrualType;
				   isStartDatePay=alarm_fields.data.slSuperviseRecord.isStartDatePay;
				     }
				     
				 idDefinition=projectId+idDefinition;
				  if(accrualtype=="sameprincipal"){
				     	 Ext.getCmp("jixifs1"+idDefinition).setValue(true);
				     	  Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				     	  
				     	  
				     	  Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
					          Ext.getCmp("jixizq6"+ idDefinition).setDisabled(true);
				  }else if(accrualtype=="sameprincipalandInterest"){
				        Ext.getCmp("jixifs2"+idDefinition).setValue(true);
				        Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				        
				  }else if(accrualtype=="sameprincipalsameInterest"){
				        Ext.getCmp("jixifs5"+idDefinition).setValue(true);
				         Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				        
				        
				  }else if(accrualtype=="otherMothod"){
				        Ext.getCmp("jixifs6"+idDefinition).setValue(true);
				         Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				        
				        
				  }else if (accrualtype=="ontTimeAccrual"){
			          Ext.getCmp("jixifs4"+idDefinition).setValue(true);
			     	  Ext.getCmp("jixifs3"+idDefinition).setValue(false);
			     	  
			     	  Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
				      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
				      Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
				      Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
				  }else if(accrualtype=="singleInterest"){
				        Ext.getCmp("jixifs3"+idDefinition).setValue(true);
				  }

				
				mainObj.getCmpByName('slSuperviseRecord.dayOfEveryPeriod').setDisabled(true);//默认为不可用
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
				  }/*else if(payaccrualType=="ontTimeAccrual"){
				       Ext.getCmp("jixizq5"+idDefinition).setValue(true);
				       Ext.getCmp("jixizq2"+idDefinition).setValue(false);
				       
				  }*/
				   
				
			    if(isStartDatePay=="1"){
			         Ext.getCmp("meiqihkrq1"+idDefinition).setValue(true);
			          Ext.getCmp("meiqihkrq2"+idDefinition).setValue(false);
			         mainObj.getCmpByName('slSuperviseRecord.isStartDatePay').setValue("1");
			      
			    }else{
			     Ext.getCmp("meiqihkrq2"+idDefinition).setValue(true);
			      Ext.getCmp("meiqihkrq1"+idDefinition).setValue(false);
			    	mainObj.getCmpByName('slSuperviseRecord.payintentPerioDate').setDisabled(true);
			    	mainObj.getCmpByName('slSuperviseRecord.isStartDatePay').setValue("2");
			    }
			    if(null!=alarm_fields.data.slSuperviseRecord.dateMode){
					var dateModelCom=mainObj.getCmpByName('slSuperviseRecord.dateMode');
					var st=dateModelCom.getStore();
					st.on('load',function(){
						dateModelCom.setValue(alarm_fields.data.slSuperviseRecord.dateMode)
					})
				}
			  /* if(accrualtype=="ontTimeAccrual"){
			            mainObj.getCmpByName('mqhkri').hide();
			            mainObj.getCmpByName('mqhkri1').show();
			    }else{
			        mainObj.getCmpByName('mqhkri1').hide();
		    		mainObj.getCmpByName('mqhkri').show()
			    }*/
			
	      }
	
}
