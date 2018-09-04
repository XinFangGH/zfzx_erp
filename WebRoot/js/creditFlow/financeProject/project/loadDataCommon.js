function fillData(mainObj,alarm_fields,idDefinition){
	            /**
	             * 
	             * @type 
	             
	            var recommendUsers= alarm_fields.data.recommendUsers;
				var recommendUserId= alarm_fields.data.flFinancingProject.recommendUserId;
				if(""!=recommendUserId &&  null!=recommendUserId){
				
				   mainObj.getCmpByName('flFinancingProject.recommendUserId').setValue(recommendUserId);
				   mainObj.getCmpByName('flFinancingProject.recommendUserId').setRawValue(recommendUsers);
				   mainObj.getCmpByName('slSmallloanProject.recommendUserId').nextSibling().setValue(recommendUserId);
				}
				*/
  	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = alarm_fields.data.flFinancingProject.isInterestByOneTime;
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(true);
	} else {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(false);
	}
	var isPreposePayConsultingCheckObj = mainObj
			.getCmpByName('isPreposePayAccrualCheck');
	var isPreposePayConsultingCheck = alarm_fields.data.flFinancingProject.isPreposePayAccrual;
	if (isPreposePayConsultingCheck == 1) {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(true);
	} else {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(false);
	}

	var projectId = alarm_fields.data.flFinancingProject.projectId;
	var accrualtype = alarm_fields.data.flFinancingProject.accrualtype;
	idDefinition = projectId + idDefinition;
	if (accrualtype == "sameprincipal") {
		Ext.getCmp("jixifs1" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);

		Ext.getCmp("jixizq1" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq2" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq3" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq4" + idDefinition).setDisabled(true);
	} else if (accrualtype == "sameprincipalandInterest") {
		Ext.getCmp("jixifs2" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);

		Ext.getCmp("jixizq1" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq2" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq3" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq4" + idDefinition).setDisabled(true);
	} else if (accrualtype == "sameprincipalsameInterest") {
		Ext.getCmp("jixifs5" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);
	} else if (accrualtype == "singleInterest") {
		Ext.getCmp("jixifs3" + idDefinition).setValue(true);

	} else if (accrualtype == "ontTimeAccrual") {
		Ext.getCmp("jixifs4" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);

		Ext.getCmp("jixizq1" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq2" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq3" + idDefinition).setDisabled(true);
		Ext.getCmp("jixizq4" + idDefinition).setDisabled(true);
	} else if (accrualtype == "otherMothod") {
		Ext.getCmp("jixifs6" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);
	}
	var payaccrualType = alarm_fields.data.flFinancingProject.payaccrualType;
	mainObj.getCmpByName('flFinancingProject.dayOfEveryPeriod').setDisabled(true);// 默认为不可用
	if (payaccrualType == "dayPay") {
		Ext.getCmp("jixizq1" + idDefinition).setValue(true);

	} else if (payaccrualType == "monthPay") {
		Ext.getCmp("jixizq2" + idDefinition).setValue(true);
	} else if (payaccrualType == "seasonPay") {
		Ext.getCmp("jixizq3" + idDefinition).setValue(true);
	} else if (payaccrualType == "yearPay") {
		Ext.getCmp("jixizq4" + idDefinition).setValue(true);
	} else if (payaccrualType == "owerPay") {
		Ext.getCmp("jixizq6" + idDefinition).setValue(true);
		// mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	}
	var isStartDatePay = alarm_fields.data.flFinancingProject.isStartDatePay;

	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
		mainObj.getCmpByName('flFinancingProject.isStartDatePay').setValue("1");

	} else {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(false);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		mainObj.getCmpByName('flFinancingProject.payintentPerioDate')
				.setDisabled(true);
		mainObj.getCmpByName('flFinancingProject.isStartDatePay').setValue("2");
	}
	if(null!=alarm_fields.data.flFinancingProject.dateMode){
		var dateModelCom=mainObj.getCmpByName('flFinancingProject.dateMode');
		var st=dateModelCom.getStore();
		st.on('load',function(){
			dateModelCom.setValue(alarm_fields.data.flFinancingProject.dateMode)
		})
	}
    
    

	            var currency= alarm_fields.data.flFinancingProject.currency;
			    if(currency=="" || currency==null){
			          var currencyObj=mainObj.getCmpByName('flFinancingProject.currency');
			          if(null!=currencyObj){
			                      var st=currencyObj.getStore();
						          st.load({"callback":function(){
						          	    if(st.getCount()>0){
						                        var record = st.getAt(0);
								                var v = record.data.itemId;
								                currencyObj.setValue(v);
						                 }
						          }});
			          }
			    
			    };
			   
	
		
	
}
