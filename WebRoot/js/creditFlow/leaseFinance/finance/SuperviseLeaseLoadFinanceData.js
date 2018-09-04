function fillSuperviseLeaseData(mainObj, alarm_fields, idDefinition) {

	mainObj.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.slSuperviseRecord.continuationMoney, '0,000.00'));
	
	
	var projectId = alarm_fields.data.slSuperviseRecord.id;
	var accrualtype = alarm_fields.data.slSuperviseRecord.accrualtype;
	idDefinition = projectId + idDefinition;
	if (accrualtype == "sameprincipal") {
		Ext.getCmp("jixifs1" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);

	} else if (accrualtype == "sameprincipalandInterest") {
		Ext.getCmp("jixifs2" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);

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
	}else if (accrualtype == "matchingRepayment") {
		Ext.getCmp("jixifs7" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);
	}else if (accrualtype == "equalAnnuity") {
		Ext.getCmp("jixifs8" + idDefinition).setValue(true);
		Ext.getCmp("jixifs3" + idDefinition).setValue(false);
	}
	var payaccrualType = alarm_fields.data.slSuperviseRecord.payaccrualType;
	mainObj.getCmpByName('slSuperviseRecord.dayOfEveryPeriod')
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
	var isStartDatePay = alarm_fields.data.slSuperviseRecord.isStartDatePay;
	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		//Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		mainObj.getCmpByName('slSuperviseRecord.isStartDatePay').setValue("1");

	} else {
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		//Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		mainObj.getCmpByName('slSuperviseRecord.payintentPerioDate').setDisabled(true);
		mainObj.getCmpByName('slSuperviseRecord.isStartDatePay').setValue("2");
	}
var feePayaccrualType = alarm_fields.data.slSuperviseRecord.feePayaccrualType;
	if (feePayaccrualType == "yearPay") {
		Ext.getCmp("zljixizq1" + idDefinition).setValue(true);

	} else if (feePayaccrualType == "onetime") {
		Ext.getCmp("zljixizq2" + idDefinition).setValue(true);
	} else if (feePayaccrualType == "withRentalFee") {
		Ext.getCmp("zljixizq3" + idDefinition).setValue(true);
	}
	

	

}
