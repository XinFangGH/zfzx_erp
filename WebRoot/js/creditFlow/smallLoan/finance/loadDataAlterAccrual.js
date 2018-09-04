function fillDataAlterAccrual(mainObj,alarm_fields,idDefinition){
	        if(alarm_fields==null){
			    	mainObj.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setDisabled(true);
			    	mainObj.getCmpByName('slAlterAccrualRecord.isStartDatePay').setValue("2");
			   
			          mainObj.getCmpByName('mqhkri1').hide();
			    
	      
	      }else {
		/*	    var isPreposePayAccrualObj = mainObj.getCmpByName('isPreposePayAccrualCheck');
				var isPreposePayAccrual= alarm_fields.isPreposePayAccrual;
			    if(isPreposePayAccrual==1){
			    	 if(null!=isPreposePayAccrualObj)
				     isPreposePayAccrualObj.setValue(true);
				}
				else
				{    if(null!=isPreposePayAccrualObj) 
				     isPreposePayAccrualObj.setValue(false);
				}*/
				
	    		var payintentPeriodObj = mainObj.getCmpByName('alterpayintentPeriod');
	    	     payintentPeriodObj.setValue(alarm_fields.payintentPeriod);
				var projectId=alarm_fields.projectId;
				  var accrualtype= alarm_fields.accrualtype;
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
				  }else if(accrualtype=="singleInterest"){
				        Ext.getCmp("jixifs3"+idDefinition).setValue(true);
				        
				        
				  }else if(accrualtype=="ontTimeAccrual"){
				         Ext.getCmp("jixifs4"+idDefinition).setValue(true);
				     	  Ext.getCmp("jixifs3"+idDefinition).setValue(false);
				     	  
				     	  Ext.getCmp("jixizq1"+ idDefinition).setDisabled(true);
					      Ext.getCmp("jixizq2"+ idDefinition).setDisabled(true);
					        Ext.getCmp("jixizq3"+ idDefinition).setDisabled(true);
					          Ext.getCmp("jixizq4"+ idDefinition).setDisabled(true);  
				     	  
				    
							                   
				  	
				  }
				var  payaccrualType=alarm_fields.payaccrualType;
				var dayOfEveryPeriod=mainObj.getCmpByName('slAlterAccrualRecord.dayOfEveryPeriod')
				if(null!=dayOfEveryPeriod){
					dayOfEveryPeriod.setDisabled(true);//默认为不可用
				}
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
				   var  isStartDatePay=alarm_fields.isStartDatePay;
			    if(isStartDatePay=="1"){
			         Ext.getCmp("meiqihkrq1"+idDefinition).setValue(true);
			         mainObj.getCmpByName('slAlterAccrualRecord.isStartDatePay').setValue("1");
			      
			    }else{
			    	var temp= Ext.getCmp("meiqihkrq2"+idDefinition)
			    	if(null!=temp){
			    		temp.setValue(true);
			    	}
			    	if(null!=mainObj.getCmpByName('slAlterAccrualRecord.payintentPerioDate')){
			    		mainObj.getCmpByName('slAlterAccrualRecord.payintentPerioDate').setDisabled(true);
			    	}
			    	if(null!=mainObj.getCmpByName('slAlterAccrualRecord.isStartDatePay')){
			    		mainObj.getCmpByName('slAlterAccrualRecord.isStartDatePay').setValue("2");
			    	}
			    }
			    var mqhkri=mainObj.getCmpByName('mqhkri');
			    var mqhkri1=mainObj.getCmpByName('mqhkri1');
			   if(payaccrualType=="ontTimeAccrual"){
			   		if(null!=mqhkri){
			   			mqhkri.hide();
			   		}
			    }else{
			         if(null!=mqhkri1){
			   			mqhkri1.hide();
			   		}
			    }
	      }
}
