function fillPawnData(mainObj, alarm_fields, idDefinition) {


	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = alarm_fields.data.plPawnProject.isInterestByOneTime;
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(true);
	} else {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(false);
	}
	var isPreposePayConsultingCheckObj = mainObj
			.getCmpByName('isPreposePayAccrualCheck');
	var isPreposePayConsultingCheck = alarm_fields.data.plPawnProject.isPreposePayAccrual;
	if (isPreposePayConsultingCheck == 1) {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(true);
	} else {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(false);
	}

	var projectId = alarm_fields.data.plPawnProject.projectId;
	idDefinition = projectId + idDefinition;
	
	var payaccrualType = alarm_fields.data.plPawnProject.payaccrualType;
	mainObj.getCmpByName('plPawnProject.dayOfEveryPeriod')
			.setDisabled(true);// 默认为不可用
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
	var isStartDatePay = alarm_fields.data.plPawnProject.isStartDatePay;

	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
		mainObj.getCmpByName('plPawnProject.isStartDatePay').setValue("1");

	} else {
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		mainObj.getCmpByName('plPawnProject.payintentPerioDate')
				.setDisabled(true);
		mainObj.getCmpByName('plPawnProject.isStartDatePay').setValue("2");
	}


}
