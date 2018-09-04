function fillLeaseData(mainObj, alarm_fields, idDefinition) {

	mainObj.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.projectMoney, '0,000.00'));
	mainObj.getCmpByName('allMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.allMoney, '0,000.00'));
	mainObj.getCmpByName('leaseDepositMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.leaseDepositMoney, '0,000.00'));
	mainObj.getCmpByName('rentalFeeMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.rentalFeeMoney, '0,000.00'));
	mainObj.getCmpByName('leaseRetentionFeeMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.leaseRetentionFeeMoney, '0,000.00'));
	mainObj.getCmpByName('rentalMoney1').setValue(Ext.util.Format.number(alarm_fields.data.flLeaseFinanceProject.rentalMoney, '0,000.00'));
	
	var projectId = alarm_fields.data.flLeaseFinanceProject.projectId;
	var accrualtype = alarm_fields.data.flLeaseFinanceProject.accrualtype;
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
	var payaccrualType = alarm_fields.data.flLeaseFinanceProject.payaccrualType;
	mainObj.getCmpByName('flLeaseFinanceProject.dayOfEveryPeriod')
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
	var isStartDatePay = alarm_fields.data.flLeaseFinanceProject.isStartDatePay;

	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		//Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		mainObj.getCmpByName('flLeaseFinanceProject.isStartDatePay').setValue("1");

	} else {
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		//Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		mainObj.getCmpByName('flLeaseFinanceProject.payintentPerioDate').setDisabled(true);
		mainObj.getCmpByName('flLeaseFinanceProject.isStartDatePay').setValue("2");
	}
var feePayaccrualType = alarm_fields.data.flLeaseFinanceProject.feePayaccrualType;
	if (feePayaccrualType == "yearPay") {
		Ext.getCmp("zljixizq1" + idDefinition).setValue(true);

	} else if (feePayaccrualType == "onetime") {
		Ext.getCmp("zljixizq2" + idDefinition).setValue(true);
	} else if (feePayaccrualType == "withRentalFee") {
		Ext.getCmp("zljixizq3" + idDefinition).setValue(true);
	}
	

	

}
