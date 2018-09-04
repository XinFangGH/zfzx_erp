function fillData(mainObj, alarm_fields, idDefinition) {
	/**
	 * 
	 * @type
	 * 
	 * var recommendUsers= alarm_fields.data.recommendUsers; var
	 * recommendUserId= alarm_fields.data.slSmallloanProject.recommendUserId;
	 * if(""!=recommendUserId && null!=recommendUserId){
	 * 
	 * mainObj.getCmpByName('slSmallloanProject.recommendUserId').setValue(recommendUserId);
	 * mainObj.getCmpByName('slSmallloanProject.recommendUserId').setRawValue(recommendUsers);
	 * mainObj.getCmpByName('slSmallloanProject.recommendUserId').nextSibling().setValue(recommendUserId); }
	 */

	/*
	 * var payintentPeriodObj = mainObj.getCmpByName('payintentPeriod1');
	 * payintentPeriodObj.setValue(alarm_fields.data.slSmallloanProject.payintentPeriod);
	 */
	var managementConsultingMineTypeVal = alarm_fields.data.slSmallloanProject.managementConsultingMineType;
	var financeServiceMineTypeVal = alarm_fields.data.slSmallloanProject.financeServiceMineType;
	var mineTypeVal = alarm_fields.data.slSmallloanProject.mineType;

	var isInterestByOneTimeObj = mainObj.getCmpByName('isInterestByOneTimeCheck');
	var isInterestByOneTime = alarm_fields.data.slSmallloanProject.isInterestByOneTime;
	if (isInterestByOneTime == 1) {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(true);
	} else {
		if (null != isInterestByOneTimeObj)
			isInterestByOneTimeObj.setValue(false);
	}
	var isPreposePayConsultingCheckObj = mainObj
			.getCmpByName('isPreposePayAccrualCheck');
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
	mainObj.getCmpByName('slSmallloanProject.dayOfEveryPeriod')
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
	var isStartDatePay = alarm_fields.data.slSmallloanProject.isStartDatePay;

	if (isStartDatePay == "1") {
		Ext.getCmp("meiqihkrq1" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(false);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		mainObj.getCmpByName('slSmallloanProject.isStartDatePay').setValue("1");

	} else {
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setValue(true);
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(false);
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

	var zijinkxinfoobj = mainObj.getCmpByName('financeInfoFieldset').items
			.get(0); // 只读，this.isAllReadOnly为true
	if (zijinkxinfoobj.isAllReadOnly == true) {
		Ext.getCmp("meiqihkrq1" + idDefinition).setDisabled(true);
		Ext.getCmp("meiqihkrq2" + idDefinition).setDisabled(true);

		mainObj.getCmpByName('slSmallloanProject.payintentPeriod')
				.setDisabled(false);
		mainObj.getCmpByName('slSmallloanProject.payintentPerioDate')
				.setDisabled(false);
	}

	var breachRate = alarm_fields.data.slSmallloanProject.breachRate;
	if (breachRate == 0) {
		if (null != mainObj.getCmpByName('slSmallloanProject.breachRate'))
			mainObj.getCmpByName('slSmallloanProject.breachRate').setValue("");
	}
	if (zijinkxinfoobj.isAllReadOnly == true) {
		if (alarm_fields.data.slSmallloanProject.financeServiceMineId == null
				|| alarm_fields.data.slSmallloanProject.financeServiceMineId == "") {
			var typeObj = mainObj
					.getCmpByName('slSmallloanProject.financeServiceMineType');
			var typeM = mainObj
					.getCmpByName('slSmallloanProject.financeServiceMineId');
			Ext.apply(typeObj, {
						allowBlank : true
					});
			Ext.apply(typeM, {
						allowBlank : true
					});
			typeObj.emptyText = '';
			typeM.emptyText = '';
			typeObj.reset();
			typeM.reset();
			typeObj.clearValue();
			typeM.clearValue();
		}

		if (alarm_fields.data.slSmallloanProject.managementConsultingMineId == null
				|| alarm_fields.data.slSmallloanProject.managementConsultingMineId == "") {
			var typeObj = mainObj
					.getCmpByName('slSmallloanProject.managementConsultingMineType');
			var typeM = mainObj
					.getCmpByName('slSmallloanProject.managementConsultingMineId');
			Ext.apply(typeObj, {
						allowBlank : true
					});
			Ext.apply(typeM, {
						allowBlank : true
					});
			typeObj.emptyText = '';
			typeM.emptyText = '';
			typeObj.reset();
			typeM.reset();
			typeObj.clearValue();
			typeM.clearValue();
		}
	}
	var url = '';
	var store = null;
	var financeServiceMineTypeUrl = '';
	var financeServiceMineTypeStore = null;
	var combo = mainObj
			.getCmpByName('slSmallloanProject.managementConsultingMineId');
	var combo1 = mainObj
			.getCmpByName('slSmallloanProject.financeServiceMineId');

			var combo2 = mainObj
			.getCmpByName('slSmallloanProject.mineId');
//			var combo2 = Ext.getCmp("slsmallmineid");
	if (null != managementConsultingMineTypeVal
			&& "" != managementConsultingMineTypeVal) {

		if (managementConsultingMineTypeVal == "company_ourmain")// 企业
		{
			url = __ctxPath + '/creditFlow/ourmain/listSlCompanyMain.do';
			store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : url
								}),
						reader : new Ext.data.JsonReader({
									root : 'result'
								}, [{
											name : 'itemName',
											mapping : 'corName'
										}, {
											name : 'itemValue',
											mapping : 'companyMainId'
										}])
					})

		} else if (managementConsultingMineTypeVal == "person_ourmain") {

			url = __ctxPath + '/creditFlow/ourmain/listSlPersonMain.do';
			store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : url
								}),
						reader : new Ext.data.JsonReader({
									root : 'result'
								}, [{
											name : 'itemName',
											mapping : 'name'
										}, {
											name : 'itemValue',
											mapping : 'personMainId'
										}])
					})
		}
		if(combo){
			combo.store = store;
			combo.valueField = "itemValue";
			store.load({
						"callback" : function() {
							var v = combo.getValue();
							combo.setValue(v);
						}
					});
			if (combo.view) { // 刷新视图,避免视图值与实际值不相符
				combo.view.setStore(combo.store);
			}
		}
	};
	if (null != financeServiceMineTypeVal && "" != financeServiceMineTypeVal) {

		if (financeServiceMineTypeVal == "company_ourmain")// 企业
		{
			financeServiceMineTypeUrl = __ctxPath
					+ '/creditFlow/ourmain/listSlCompanyMain.do';
			financeServiceMineTypeStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : financeServiceMineTypeUrl
								}),
						reader : new Ext.data.JsonReader({
									root : 'result'
								}, [{
											name : 'itemName',
											mapping : 'corName'
										}, {
											name : 'itemValue',
											mapping : 'companyMainId'
										}])
					})

		} else if (financeServiceMineTypeVal == "person_ourmain") {

			financeServiceMineTypeUrl = __ctxPath
					+ '/creditFlow/ourmain/listSlPersonMain.do';
			financeServiceMineTypeStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : financeServiceMineTypeUrl
								}),
						reader : new Ext.data.JsonReader({
									root : 'result'
								}, [{
											name : 'itemName',
											mapping : 'name'
										}, {
											name : 'itemValue',
											mapping : 'personMainId'
										}])
					})
		}
		if(combo1){
			combo1.store = financeServiceMineTypeStore;
			combo1.valueField = "itemValue";
			financeServiceMineTypeStore.load({
						"callback" : function() {
							var v1 = combo1.getValue();
							combo1.setValue(v1);
						}
					});
			if (combo1.view) {
				combo1.view.setStore(combo1.store);
			}
		}
	}
		if (null != mineTypeVal && "" != mineTypeVal) {

		if (mineTypeVal == "company_ourmain")// 企业
		{
			url = __ctxPath + '/creditFlow/ourmain/listSlCompanyMain.do';
			store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : url
								}),
						reader : new Ext.data.JsonReader({
									root : 'result'
								}, [{
											name : 'itemName',
											mapping : 'corName'
										}, {
											name : 'itemValue',
											mapping : 'companyMainId'
										}])
					})

		} else if (mineTypeVal == "person_ourmain") {

			url = __ctxPath + '/creditFlow/ourmain/listSlPersonMain.do';
			store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : url
								}),
						reader : new Ext.data.JsonReader({
									root : 'result'
								}, [{
											name : 'itemName',
											mapping : 'name'
										}, {
											name : 'itemValue',
											mapping : 'personMainId'
										}])
					})
		}
		if(combo2){
			combo2.store = store;
			combo2.valueField = "itemValue";
			store.load({
						"callback" : function() {
							var v = combo2.getValue();
							combo2.setValue(v);
						}
					});
			if (combo2.view) { // 刷新视图,避免视图值与实际值不相符
				combo2.view.setStore(combo2.store);
			}
		}
	};
	var currency = alarm_fields.data.slSmallloanProject.currency;
	if (currency == "" || currency == null) {
		var currencyObj = mainObj.getCmpByName('slSmallloanProject.currency');
		if (null != currencyObj) {
			var st = currencyObj.getStore();
			st.load({
						"callback" : function() {
							if (st.getCount() > 0) {
								var record = st.getAt(0);
								var v = record.data.itemId;
								currencyObj.setValue(v);
							}
						}
					});
		}

	};
	var purposeType = alarm_fields.data.slSmallloanProject.purposeType;
	if (purposeType == "" || purposeType == null) {
		var purposeTypeObj = mainObj
				.getCmpByName('slSmallloanProject.purposeType');
		if (null != purposeTypeObj) {
			var st1 = purposeTypeObj.getStore();
			st1.load({
						"callback" : function() {
							if (st1.getCount() > 0) {
								var record = st1.getAt(0);
								var v = record.data.itemId;
								purposeTypeObj.setValue(v);
							}
						}
					});
		}

	};
	/*
	 * var dateMode= alarm_fields.data.slSmallloanProject.dateMode;
	 * if(dateMode=="" || dateMode==null){ var
	 * dateModeObj=mainObj.getCmpByName('slSmallloanProject.dateMode')
	 * if(null!=dateModeObj){ var dateModeSt=dateModeObj.getStore();
	 * dateModeSt.on("load", function() { var record = dateModeSt.getAt(0); var
	 * v = record.data.dicKey; dateModeObj.setValue(v); }) }
	 *  };
	 */
	// 是否允许提前还款去掉
	/*
	 * var isAhead = mainObj.getCmpByName('isAheadPay'); var operateObj =
	 * mainObj.getCmpByName('slSmallloanProject.aheadDayNum'); if(null!=isAhead &&
	 * null!=operateObj){ if (isAhead.getValue()==1) { Ext.apply(operateObj, {
	 * allowBlank : false, blankText : "提前还款通知天数不能为空!" });
	 * operateObj.setDisabled(false) } else { operateObj.setValue("");
	 * Ext.apply(operateObj, { allowBlank : true }); operateObj.clearInvalid();
	 * operateObj.setDisabled(true); }
	 *  }
	 */

}
