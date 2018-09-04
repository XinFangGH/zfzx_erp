function fillData(mainObj, alarm_fields, idDefinition) {
	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = alarm_fields.data.isInterestByOneTime;
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj){
			isInterestByOneTimeObj.setValue(true);
		}
	} else {
		if (null != isInterestByOneTimeObj){
			isInterestByOneTimeObj.setValue(false);
		}
	}
	var isPreposePayConsultingCheckObj = mainObj.getCmpByName('isPreposePayAccrualCheck');
	var isPreposePayConsultingCheck = alarm_fields.data.isPreposePayAccrual;
	if (isPreposePayConsultingCheck == 1) {
		if (null != isPreposePayConsultingCheckObj)
		{
			isPreposePayConsultingCheckObj.setValue(true);
		}
	} else {
		if (null != isPreposePayConsultingCheckObj){
			isPreposePayConsultingCheckObj.setValue(false);
		}
	}
	var accrualtype = alarm_fields.data.accrualtype;
	if (accrualtype == "sameprincipal") {
		Ext.getCmp("jixifs1").setValue(true);
		Ext.getCmp("jixifs3").setValue(false);

		Ext.getCmp("jixizq1").setDisabled(true);
		Ext.getCmp("jixizq2").setDisabled(true);
		Ext.getCmp("jixizq3").setDisabled(true);
		Ext.getCmp("jixizq4").setDisabled(true);
	} else if (accrualtype == "sameprincipalandInterest") {
		Ext.getCmp("jixifs2").setValue(true);
		Ext.getCmp("jixifs3").setValue(false);

		Ext.getCmp("jixizq1").setDisabled(true);
		Ext.getCmp("jixizq2").setDisabled(true);
		Ext.getCmp("jixizq3").setDisabled(true);
		Ext.getCmp("jixizq4").setDisabled(true);
		
	}else if (accrualtype == "sameprincipalsameInterest") {
		if(Ext.getCmp("jixifs5" )!=null){
			Ext.getCmp("jixifs5" ).setValue(true);
		}
		if(Ext.getCmp("jixifs3" )!=null){
			Ext.getCmp("jixifs3" ).setValue(false);
		}
	} else if (accrualtype == "singleInterest") {
		Ext.getCmp("jixifs3" ).setValue(true);

	} else if (accrualtype == "ontTimeAccrual") {
		Ext.getCmp("jixifs4").setValue(true);
		Ext.getCmp("jixifs3").setValue(false);

		Ext.getCmp("jixizq1").setDisabled(true);
		Ext.getCmp("jixizq2").setDisabled(true);
		Ext.getCmp("jixizq3").setDisabled(true);
		Ext.getCmp("jixizq4").setDisabled(true);
	} else if (accrualtype == "otherMothod") {
		Ext.getCmp("jixifs6").setValue(true);
		Ext.getCmp("jixifs3").setValue(false);
	}
	
	var payaccrualType = alarm_fields.data.payaccrualType;
	if(mainObj.getCmpByName('bpProductParameter.dayOfEveryPeriod')){
		mainObj.getCmpByName('bpProductParameter.dayOfEveryPeriod').setDisabled(true);// 默认为不可用
	}
	if (payaccrualType == "dayPay") {
		Ext.getCmp("jixizq1").setValue(true);

	} else if (payaccrualType == "monthPay") {
		Ext.getCmp("jixizq2").setValue(true);
	} else if (payaccrualType == "seasonPay") {
		Ext.getCmp("jixizq3").setValue(true);
	} else if (payaccrualType == "yearPay") {
		Ext.getCmp("jixizq4").setValue(true);
	} else if (payaccrualType == "owerPay") {
		Ext.getCmp("jixizq6").setValue(true);
	}
	
	var isStartDatePay = alarm_fields.data.isStartDatePay;
	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1").setValue(true);
		Ext.getCmp("meiqihkrq2").setValue(false);
		Ext.getCmp("meiqihkrq1").setDisabled(false);
		Ext.getCmp("meiqihkrq1").setDisabled(true);
		mainObj.getCmpByName('bpProductParameter.isStartDatePay').setValue("1");
	} else {
		Ext.getCmp("meiqihkrq1").setDisabled(true);
		Ext.getCmp("meiqihkrq2").setValue(true);
		Ext.getCmp("meiqihkrq1").setDisabled(false);
		if(mainObj.getCmpByName('bpProductParameter.payintentPerioDate')!=null){
			mainObj.getCmpByName('bpProductParameter.payintentPerioDate').setDisabled(true);
		}
		if(mainObj.getCmpByName('bpProductParameter.isStartDatePay')!=null){
			mainObj.getCmpByName('bpProductParameter.isStartDatePay').setValue("2");
		}
	}
}
