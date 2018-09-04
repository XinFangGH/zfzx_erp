Ext.namespace("Ext.ah"); // 自定义一个命名空间
Ext.ah.DoLoad = Ext.emptyFn; // 在命名空间上自定义一个类,全部为静态方法
// 组件赋值，获取，后台调用
Ext.ah.DoLoad._setDate = function(a, b) {
	var obj = Ext.getCmp("form1").getCmpByName(a);
	if (obj != null)
		obj.setValue(b);
}
Ext.ah.DoLoad._getDate = function(a) {
	var obj = Ext.getCmp(a);
	if (obj != null) {
		if (a == "ext_borrowerInfo") {
			return getBorrowerInfoData(obj.get(0));
		}
		if (a == "ext_slActualToCharge") {
			return obj.getGridDate();
		}
		if (a == "ext_repaymentSource") {
			return getSourceGridDate(obj.get(0));
		} else {
			return obj.get(0);
		}
	}
}

Ext.ah.DoLoad._setValueFromJs = function(data, name) {
	if (name == "projectMoney1") {
		var projectM = this.getCmpByName('projectMoney1');
		if (projectM != null) {
			projectM.setValue(Ext.util.Format.number(
					data.slSmallloanProject.projectMoney, '0,000.00'));
		}

	} else if (name == "enterpriseBank") {
		if (typeof(data.enterpriseBank) != 'undefined'
				&& this.getCmpByName('enterpriseBank.areaName') != null) {
			this.getCmpByName('enterpriseBank.areaName')
					.setValue(data.enterpriseBank.areaName);
		}
	} else if (name == "comments") {
		var obj = data[name];
		if (obj != null) {
			if(this.ownerCt.ownerCt.getCmpByName("comments")){
				this.ownerCt.ownerCt.getCmpByName("comments").setValue(obj);
			}
		}
	} else if (name = "degree") {
		var appUsers = data.slSmallloanProject.appUsers;
		var appUserId = data.slSmallloanProject.appUserId;
		if ("" != appUserId && null != appUserId) {
			this.getCmpByName('degree').setValue(appUserId);
			this.getCmpByName('degree').setRawValue(appUsers);
			this.getCmpByName('degree').nextSibling().setValue(appUserId);
		}

	} else if (name == "ahgl") {
		Ext.getCmp("ahgl").hiddenValue = data.slSmallloanProject.managementConsultingMineType;
	}

}

Ext.ah.DoLoad._getPerOrEnt = function() {
	var type = Ext.getCmp("form1").oppositeType;
	if (type == "company_customer") {
		var eg = Ext.getCmp("ext_perMain").getCmpByName('gudong_store').get(0)
				.get(1);
		vDates = getGridDate(eg);
		/*if (vDates != "") {
			var arrStr = vDates.split("@");
			for (var i = 0; i < arrStr.length; i++) {
				var str = arrStr[i];
				var object = Ext.util.JSON.decode(str)
				if (object.personid == "") {
					Ext.ux.Toast.msg('操作信息', '股东名称不能为空，请选择股东名称');
					return false;
				}
				if (object.shareholdertype == "") {
					Ext.ux.Toast.msg('操作信息', '股东类别不能为空，请选择股东类别');
					return false;
				}
			}
		}*/
		return vDates;
	}
}
// 数据初始化辅助
Ext.ah.DoLoad.baseInfo = function(projectId, taskId) {
	return {
		url : __ctxPath + '/project/getInfoSlSmallloanProject.do?slProjectId='
				+ projectId + '&slTaskId=' + taskId,
		method : "POST",
		preName : ['enterprise', 'person', 'slSmallloanProject',
				"businessType", "enterpriseBank", "spouse", 'isOnline'],
		root : 'data',
		success : function(response, options) {
			var respText = response.responseText;
			var alarm_fields = Ext.util.JSON.decode(respText);
			var data = alarm_fields.data;
			// 备注
			Ext.ah.DoLoad._setValueFromJs.call(this, data, "comments");
			//
			Ext.ah.DoLoad._setValueFromJs.call(this, data, "ahgl");
			//
			Ext.ah.DoLoad._setValueFromJs.call(this, data, "projectMoney1");
			//
			Ext.ah.DoLoad._setValueFromJs.call(this, data, "enterpriseBank");
			// 项目经理
			Ext.ah.DoLoad._setValueFromJs.call(this, data, "degree");
			// 其他信息加载
			fillData(this, alarm_fields, 'Smallliucheng' + this.taskId);
		}
	}
};
// 快速保存
Ext.ah.DoLoad.baseSave = function(formPanel, fun) {
	// 可以与提交下一步合并
	var type = Ext.getCmp("form1").oppositeType;
	var vDates=""
	if (type == "company_customer") {
		var eg = Ext.getCmp("ext_perMain").getCmpByName('gudong_store').get(0)
				.get(1);
		vDates = getGridDate(eg);
	}
	// 手续费
	var slActualToChargeJsonData = Ext.ah.DoLoad
			._getDate("ext_slActualToCharge");
	// 还款方式
	var repaymentSource = Ext.ah.DoLoad._getDate("ext_repaymentSource");
	// 合伙人
	var borrowerInfo="";
	if(typeof(Ext.getCmp("ext_borrowerInfo"))!="undefined" && Ext.getCmp("ext_borrowerInfo")!=null){
 		 borrowerInfo = getBorrowerInfoData(Ext.getCmp("ext_borrowerInfo")
			.get(0));
	}
	var breachRate = this.getCmpByName('slSmallloanProject.breachRate');
	if (typeof(breachRate)!="undefined" && breachRate!=null && breachRate.getValue() == "") {
		breachRate.setValue(0);
	}
	var SlFundIntentViewVM_panel=this.getCmpByName('SlFundIntentViewVM_panel');
	var fundIntentJsonData=""
	if(typeof(SlFundIntentViewVM_panel)!="undefined" &&null!=SlFundIntentViewVM_panel){
	 fundIntentJsonData=SlFundIntentViewVM_panel.getGridDate()
	}
	var slActualToCharge=Ext.getCmp('ext_slActualToCharge')
	var repaymentSourcePanel=Ext.getCmp('ext_repaymentSource')
	var borrowerInfoPanel=Ext.getCmp('ext_borrowerInfo')
	var propertyIdObj=this.getCmpByName('projectPropertyClassification.id')
	var conforenceIdObj=this.getCmpByName('slConferenceRecord.conforenceId')
	formPanel.getForm().submit({
				clientValidation : false,
				url : __ctxPath + '/project/updateSlSmallloanProject.do',
				params : {
					"gudongInfo" : vDates,
					"repaymentSource" : repaymentSource,
					"borrowerInfo" : borrowerInfo,
					"slActualToChargeJsonData" : slActualToChargeJsonData,
					'fundIntentJsonData':fundIntentJsonData,
					"isDeleteAllFundIntent" : 1,
					'comments' : formPanel.comments
				},
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				scope : this,
				success : function(fp, action) {
					var object = Ext.util.JSON.decode(action.response.responseText)
					Ext.ux.Toast.msg('操作信息', '保存信息成功!');
					if(typeof(SlFundIntentViewVM_panel)!="undefined" &&null!=SlFundIntentViewVM_panel){
						SlFundIntentViewVM_panel.save()
					}
					if(typeof(slActualToCharge)!="undefined" && null!=slActualToCharge){
						slActualToCharge.savereload()
					}
					if(typeof(repaymentSourcePanel)!="undefined" && null!=repaymentSourcePanel){
						repaymentSourcePanel.grid_RepaymentSource.getStore().reload()
					}
					if(typeof(borrowerInfoPanel)!="undefined" && null!=borrowerInfoPanel){
						borrowerInfoPanel.grid_BorrowerInfo.getStore().reload()
					}
					if(typeof(propertyIdObj)!="undefined" && null!=propertyIdObj){
						propertyIdObj.setValue(object.propertyId)
					}
					if(typeof(conforenceIdObj)!="undefined" && null!=conforenceIdObj){
						conforenceIdObj.setValue(object.conforenceId)
					}
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
				}
			});
}

// 提交下一步

Ext.ah.DoLoad.baseNext = function(rootObj, outpanel) {
	 var breachRate= rootObj.getCmpByName('slSmallloanProject.breachRate');
	 if(breachRate){
          if(breachRate.getValue()=="") {
           breachRate.setValue(0);
          }
	 }
	
	Ext.ah.DoLoad.makeDate();
}

// 数据处理
Ext.ah.DoLoad.makeDate = function() {
	var vDates=""
	var type = Ext.getCmp("form1").oppositeType;
	if (type == "company_customer") {
		var eg = Ext.getCmp("ext_perMain").getCmpByName('gudong_store').get(0)
				.get(1);
		vDates = getGridDate(eg);
	}

	var breachRate = Ext.getCmp("form1")
			.getCmpByName('slSmallloanProject.breachRate');
	if (breachRate.getValue() == "")
		breachRate.setValue(0);
	// 后台调用赋值
	var borData = Ext.ah.DoLoad._getDate("ext_borrowerInfo");
	var repData = Ext.ah.DoLoad._getDate("ext_repaymentSource");
	var slaData = Ext.ah.DoLoad._getDate("ext_slActualToCharge");
	var SlFundIntentViewVM_panel=Ext.getCmp("form1").getCmpByName('SlFundIntentViewVM_panel');
	var fundIntentJsonData="";
	if(typeof(SlFundIntentViewVM_panel)!="undefined" &&null!=SlFundIntentViewVM_panel){
	 fundIntentJsonData=SlFundIntentViewVM_panel.getGridDate()
	}
	// 基础信息,需要维护，传递信息，后台保存
	// 合伙人
	Ext.ah.DoLoad._setDate('gudongInfo', vDates); // 股东信息
	Ext.ah.DoLoad._setDate('borrowerInfo', borData); // 共同借款人
	Ext.ah.DoLoad._setDate('repaymentSource', repData); // 第一还款来源
	Ext.ah.DoLoad._setDate('slActualToChargeJson', slaData); // 手续费
	Ext.ah.DoLoad._setDate('fundIntentJsonData', fundIntentJsonData); // 手续费
}

// doLoad = function() {
// return {
// },
// baseInfo2 : function(projectId, taskId) {
// return {
// url : __ctxPath
// + '/project/getInfoSlSmallloanProject.do?slProjectId='
// + projectId + '&slTaskId=' + taskId,
// method : "POST",
// preName : ['enterprise', 'person', 'slSmallloanProject',
// "businessType", "enterpriseBank", "spouse"],
// root : 'data',
// success : function(response, options) {
// var respText = response.responseText;
// var alarm_fields = Ext.util.JSON.decode(respText);
// if (!alarm_fields.data.isOnline) {
// var form = new Ext.form.FieldSet({
// collapsible : true,
// autoHeight : true,
// title : '会议纪要',
// items : [this.meetingSummaryForm]
// });
// this.formPanel.add(form);
// this.formPanel.doLayout();
// Ext.getCmp("ahgl").hiddenValue =
// alarm_fields.data.slSmallloanProject.managementConsultingMineType;
// this
// .getCmpByName('projectMoney1')
// .setValue(Ext.util.Format
// .number(
// alarm_fields.data.slSmallloanProject.projectMoney,
// '0,000.00'));
// if (typeof(alarm_fields.data.enterpriseBank) != 'undefined'
// && this.getCmpByName('enterpriseBank.areaName') != null) {
// this
// .getCmpByName('enterpriseBank.areaName')
// .setValue(alarm_fields.data.enterpriseBank.areaName);
// }
// var appUsers = alarm_fields.data.slSmallloanProject.appUsers;
// var appUserId = alarm_fields.data.slSmallloanProject.appUserId;
// if ("" != appUserId && null != appUserId) {
// this.getCmpByName('degree').setValue(appUserId);
// this.getCmpByName('degree').setRawValue(appUsers);
// this.getCmpByName('degree').nextSibling()
// .setValue(appUserId);
// }
// fillData(this, alarm_fields, 'Smallliucheng'
// + this.taskId);
// }
// }
// }
// },
//
// baseSave : function(formPanel, fun) {
// var vDates = "";
// var slActualToChargeJsonData = Ext.getCmp("ext_slActualToCharge")
// .getGridDate(); // 手续费
// var borrowerInfo = getBorrowerInfoData(Ext
// .getCmp("ext_borrowerInfo").get(0));
// var borrowerInfoGrid = Ext.getCmp("ext_borrowerInfo").get(0); // 合伙人
// // var gridPanel =this.gridPanel.get(1);
// var slActualToCharge = Ext.getCmp("ext_slActualToCharge");
// var oppType = this.oppositeType;
// var op = this.getCmpByName('person.id')
// var enterpriseBank = this.getCmpByName("enterpriseBank.id");
// if (this.oppositeType == "company_customer") {
// var eg = Ext.getCmp("ext_perMain").getCmpByName('gudong_store')
// .get(0).get(1);
// vDates = getGridDate(eg);
// if (vDates != "") {
// var arrStr = vDates.split("@");
// for (var i = 0; i < arrStr.length; i++) {
// var str = arrStr[i];
// var object = Ext.util.JSON.decode(str)
// if (object.personid == "") {
// Ext.ux.Toast.msg('操作信息', '股东名称不能为空，请选择股东名称');
// return;
// }
// if (object.shareholdertype == "") {
// Ext.ux.Toast.msg('操作信息', '股东类别不能为空，请选择股东类别');
// return;
// }
// }
// }
// } else {
// var personMarry = this.getCmpByName('person.marry').getValue();
// if (personMarry != null && personMarry != ""
// && personMarry == 317) {
// var spousePanel = this.getCmpByName('spouse.spouseId')
// }
// }
//
// var rs_sou = Ext.getCmp("ext_repaymentSource");
// if (rs_sou != null) {
// var repaymentSource = getSourceGridDate(rs_sou.get(0));
//
// } else {
// var repaymentSource = "";
// }
// var breachRate = this.getCmpByName('slSmallloanProject.breachRate');
// if (breachRate.getValue() == "") {
// breachRate.setValue(0);
// }
//
// formPanel.getForm().submit({
// clientValidation : false,
// url : __ctxPath + '/project/updateSlSmallloanProject.do',
// params : {
// "gudongInfo" : vDates,
// "repaymentSource" : repaymentSource,
// "borrowerInfo" : borrowerInfo,
// "slActualToChargeJsonData" : slActualToChargeJsonData,
// "isDeleteAllFundIntent" : 1,
// 'comments' : formPanel.comments
// },
// method : 'post',
// waitMsg : '数据正在提交，请稍后...',
// scope : this,
// success : function(fp, action) {
// var object = Ext.util.JSON
// .decode(action.response.responseText)
// Ext.getCmp("ext_repaymentSource").grid_RepaymentSource
// .getStore().reload();
// Ext.ux.Toast.msg('操作信息', '保存信息成功!');
// if (oppType == "company_customer") {
// eg.getStore().reload();
// op.setValue(object.legalpersonid)
// } else {
// if (personMarry != null && personMarry != ""
// && personMarry == 317) {
// spousePanel.setValue(object.spouseId)
// }
// }
// enterpriseBank.setValue(object.enterpriseBankId);
// borrowerInfoGrid.getStore().reload();
// slActualToCharge.savereload();
// if (fun) {
// fun.call(this.ownerCt.ownerCt)
// }
// },
// failure : function(fp, action) {
// Ext.MessageBox.show({
// title : '操作信息',
// msg : '信息保存出错，请联系管理员！',
// buttons : Ext.MessageBox.OK,
// icon : 'ext-mb-error'
// });
// }
// });
// },
// basesave2 : function(formPanel, fun, pid) {// 汇总审贷会意见
// var slActualToChargeJsonData = Ext.getCmp("ext_slActualToCharge")
// .getGridDate(); // 手续费
// var slActualToCharge = Ext.getCmp("ext_slActualToCharge");
// formPanel.getForm().submit({
// clientValidation : false,
// url : __ctxPath
// + '/project/updateSlSmallloanProject.do',
// params : {
// comments : formPanel.comments,
// "slActualToChargeJsonData" : slActualToChargeJsonData,
// "isDeleteAllFundIntent" : 1,
// "projectId" : pid,
// "tag" : 1
// },
// method : 'post',
// waitMsg : '数据正在提交，请稍后...',
// success : function(fp, action) {
// slActualToCharge.savereload();
// Ext.ux.Toast.msg('操作信息', '保存信息成功!');
// },
// failure : function(fp, action) {
// Ext.MessageBox.show({
// title : '操作信息',
// msg : '信息保存出错，请联系管理员！',
// buttons : Ext.MessageBox.OK,
// icon : 'ext-mb-error'
// });
// }
// });
// },
// basesave3 : function(formPanel, fun, pid) {// 普通的mark保存 可依靠元数据合并
// formPanel.getForm().submit({
// clientValidation : false,
// url : __ctxPath
// + '/project/updateSlSmallloanProject.do',
// params : {
// comments : formPanel.comments
// },
// method : 'post',
// waitMsg : '数据正在提交，请稍后...',
// success : function(fp, action) {
// Ext.ux.Toast.msg('操作信息', '保存信息成功!');
// },
// failure : function(fp, action) {
// Ext.MessageBox.show({
// title : '操作信息',
// msg : '信息保存出错，请联系管理员！',
// buttons : Ext.MessageBox.OK,
// icon : 'ext-mb-error'
// });
// }
// });
// },

//
// }()
//

