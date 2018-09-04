function fillPawnContinuedData(mainObj, alarm_fields, idDefinition) {

	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = alarm_fields.data.isInterestByOneTime;
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(true);
	} else {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(false);
	}
	var isPreposePayConsultingCheckObj = mainObj
			.getCmpByName('isPreposePayAccrualCheck');
	var isPreposePayConsultingCheck = alarm_fields.data.isPreposePayAccrual;
	if (isPreposePayConsultingCheck == 1) {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(true);
	} else {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(false);
	}

	var projectId = alarm_fields.data.projectId;
	idDefinition = projectId + idDefinition;
	
	var payaccrualType = alarm_fields.data.payaccrualType;
	mainObj.getCmpByName('pawnContinuedManagment.dayOfEveryPeriod')
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
	var isStartDatePay = alarm_fields.data.isStartDatePay;

	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
		mainObj.getCmpByName('pawnContinuedManagment.isStartDatePay').setValue("1");

	} else {
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		mainObj.getCmpByName('pawnContinuedManagment.payintentPerioDate')
				.setDisabled(true);
		mainObj.getCmpByName('pawnContinuedManagment.isStartDatePay').setValue("2");
	}


}
