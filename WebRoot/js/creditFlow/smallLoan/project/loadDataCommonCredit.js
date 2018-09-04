function fillData(mainObj, alarm_fields, idDefinition) {

	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = alarm_fields.data.slSmallloanProject.isInterestByOneTime;
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(true);
	} else {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(false);
	}
	var isPreposePayConsultingCheckObj = mainObj.getCmpByName('isPreposePayAccrualCheck');
	var isPreposePayConsultingCheck = alarm_fields.data.slSmallloanProject.isPreposePayAccrual;
	if (isPreposePayConsultingCheck == 1) {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(true);
	} else {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(false);
	}

	var projectId = alarm_fields.data.slSmallloanProject.projectId;
	var accrualtype = alarm_fields.data.slSmallloanProject.accrualtype;
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
	var payaccrualType = alarm_fields.data.slSmallloanProject.payaccrualType;
	mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(true);// 默认为不可用
	if (payaccrualType == "dayPay") {
		Ext.getCmp("jixizq1" + idDefinition).setValue(true);
		mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue()
	} else if (payaccrualType == "monthPay") {
		Ext.getCmp("jixizq2" + idDefinition).setValue(true);
		mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue()
	} else if (payaccrualType == "seasonPay") {
		Ext.getCmp("jixizq3" + idDefinition).setValue(true);
		mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue()
	} else if (payaccrualType == "yearPay") {
		Ext.getCmp("jixizq4" + idDefinition).setValue(true);
		mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setValue()
	} else if (payaccrualType == "owerPay") {
		Ext.getCmp("jixizq6" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		// mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	}
	var isStartDatePay = alarm_fields.data.slSmallloanProject.isStartDatePay;

	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
	/*	Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);*/
		mainObj.getCmpByName('slSmallloanProject.isStartDatePay').setValue("1");

	} else {
		//Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(false);
		mainObj.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(true);
		mainObj.getCmpByName('slSmallloanProject.isStartDatePay').setValue("2");
	}

	if(null!=alarm_fields.data.slSmallloanProject.dateMode){
		var dateModelCom=mainObj.getCmpByName('slSmallloanProject.dateMode');
		var st=dateModelCom.getStore();
		st.on('load',function(){
			dateModelCom.setValue(alarm_fields.data.slSmallloanProject.dateMode)
		})
	}

	var zijinkxinfoobj = mainObj.getCmpByName('financeInfoFieldset').items.get(0); // 只读，this.isAllReadOnly为true
	if (zijinkxinfoobj.isAllReadOnly == true) {
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setDisabled(true);

		mainObj.getCmpByName('slSmallloanProject.payintentPeriod').setDisabled(false);
		mainObj.getCmpByName('slSmallloanProject.payintentPerioDate').setDisabled(false);
	}
}

function fillFundData(mainObj, alarm_fields, idDefinition) {
	var pclass=null;
	var zijinkxinfoobj=null;
	var temp=idDefinition;
	if(idDefinition.indexOf("tongyongliucheng1")!=-1){
		pclass='ownBpFundProject';
		zijinkxinfoobj=mainObj.getCmpByName('ownFinanceInfoFieldset').items.get(0);
	}else if(idDefinition.indexOf("tongyongliucheng2")!=-1){
		pclass='platFormBpFundProject';
		zijinkxinfoobj=mainObj.getCmpByName('platFormfinanceInfoFieldset').items.get(0);
	}
	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = null;
	if(idDefinition.indexOf("tongyongliucheng1")!=-1){
		isInterestByOneTime = alarm_fields.data.ownBpFundProject.isInterestByOneTime;
	}else if(idDefinition.indexOf("tongyongliucheng2")!=-1){
		isInterestByOneTime = alarm_fields.data.platFormBpFundProject.isInterestByOneTime;
	}
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(true);
	} else {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(false);
	}
	var isPreposePayConsultingCheckObj = mainObj.getCmpByName('isPreposePayAccrualCheck');
	var isPreposePayConsultingCheck = null;
	if(idDefinition.indexOf("tongyongliucheng1")!=-1){
		isPreposePayConsultingCheck = alarm_fields.data.ownBpFundProject.isPreposePayAccrual;
	}else if(idDefinition.indexOf("tongyongliucheng2")!=-1){
		isPreposePayConsultingCheck = alarm_fields.data.platFormBpFundProject.isPreposePayAccrual;
	}
	if (isPreposePayConsultingCheck == 1) {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(true);
	} else {
		if (null != isPreposePayConsultingCheckObj)
			isPreposePayConsultingCheckObj.setValue(false);
	}

	var projectId = null;
	var accrualtype = null;
	if(idDefinition.indexOf("tongyongliucheng1")!=-1){
		var projectId = alarm_fields.data.ownBpFundProject.projectId;
	var accrualtype = alarm_fields.data.ownBpFundProject.accrualtype;
	}else if(idDefinition.indexOf("tongyongliucheng2")!=-1){
		var projectId = alarm_fields.data.platFormBpFundProject.projectId;
	var accrualtype = alarm_fields.data.platFormBpFundProject.accrualtype;
	}
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
	var payaccrualType = null;
	if(temp.indexOf("tongyongliucheng1")!=-1){
		payaccrualType = alarm_fields.data.ownBpFundProject.payaccrualType;
	}else if(temp.indexOf("tongyongliucheng2")!=-1){
		payaccrualType = alarm_fields.data.platFormBpFundProject.payaccrualType;
	}
	mainObj.getCmpByName(pclass+'.dayOfEveryPeriod').setDisabled(true);// 默认为不可用
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
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		// mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod').setDisabled(false);
	}
	var isStartDatePay = null;
	if(temp.indexOf("tongyongliucheng1")!=-1){
		isStartDatePay=alarm_fields.data.ownBpFundProject.isStartDatePay;
	}else if(temp.indexOf("tongyongliucheng2")!=-1){
		isStartDatePay=alarm_fields.data.platFormBpFundProject.isStartDatePay;
	}
	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
	/*	Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);*/
		mainObj.getCmpByName(pclass+'.isStartDatePay').setValue("1");

	} else {
		//Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(false);
		mainObj.getCmpByName(pclass+'.payintentPerioDate').setDisabled(true);
		mainObj.getCmpByName(pclass+'.isStartDatePay').setValue("2");
	}
	
	var zijinkxinfoobj=null;
	if(mainObj.getCmpByName('financeInfoFieldset')){
		zijinkxinfoobj = mainObj.getCmpByName('financeInfoFieldset').items.get(0); // 只读，this.isAllReadOnly为true
		if (zijinkxinfoobj.isAllReadOnly == true) {
			Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
			Ext.getCmp("meiqihkrq2" + idDefinition).setDisabled(true);
	
			mainObj.getCmpByName(pclass+'.payintentPeriod').setDisabled(false);
			mainObj.getCmpByName(pclass+'.payintentPerioDate').setDisabled(false);
		}
		
		if (zijinkxinfoobj.isAllReadOnly == true) {
			Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
			Ext.getCmp("meiqihkrq2" + idDefinition).setDisabled(true);
	
			mainObj.getCmpByName(pclass+'.payintentPeriod').setDisabled(false);
			mainObj.getCmpByName(pclass+'.payintentPerioDate').setDisabled(false);
		}
	}

}
