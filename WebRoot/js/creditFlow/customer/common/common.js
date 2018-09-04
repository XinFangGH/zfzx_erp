/** 保存 保存成功后不关闭窗口，
 * 返回 返回提示保存数据，
 * 取消 取消不提示保存数据，
 * 提交， 提交成功后关闭窗口 
 * 是否为空 保存调用查看是否为空*/

    function rand(number)
	{
	    rnd.today=new Date();
	    rnd.seed=rnd.today.getTime();
	    function rnd()
	    {
	        rnd.seed = (rnd.seed*9301+49297) % 233280;
	        return rnd.seed/(233280.0);
	    };
	    return Math.ceil(rnd()*number);
	};

	/**客户管理模块 ，公共方法， 当表单填写数据后， 客户点击取消按钮以后，判断是否为空， */
	var isEmpty = function(formPanelId , winObj , storeObj){
		var formObj = Ext.getCmp(formPanelId);
		var textfieldArray = formObj.findByType('textfield');
		var datefieldArray = formObj.findByType('datefield');
		var numberfieldArray = formObj.findByType('numberfield');
		var radiogroupArray = formObj.findByType('radiogroup');
		var comboArray = formObj.findByType('combo');
		var csRemoteComboArray = formObj.findByType('csRemoteCombo');
		for(var i = 0 ; i < textfieldArray.length ; i ++){
			var textfieldObj = textfieldArray[i] ;
			var val = textfieldObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < datefieldArray.length ; i ++){
			var datefieldObj = datefieldArray[i] ;
			var val = datefieldObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < numberfieldArray.length ; i ++){
			var numberfieldObj = numberfieldArray[i] ;
			var val = numberfieldObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < radiogroupArray.length ; i ++){
			var radiogroupObj = radiogroupArray[i] ;
			var val = radiogroupObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < comboArray.length ; i ++){
			var comboObj = comboArray[i] ;
			var val = comboObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < csRemoteComboArray.length ; i ++){
			var csRemoteComboObj = csRemoteComboArray[i] ;
			var val = csRemoteComboObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}
	}
	
	/**客户管理模块 ，个人信息保存数据方法 ， 提供个人管理公共调用*/
	var formSavePerson = function(winObj, storeObj ,objId){
		var formObj = winObj.get(0);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			clientValidation : true,
			success : function(form ,action) {
				obj = Ext.util.JSON.decode(action.response.responseText);
				if(obj.exsit == false){
					Ext.ux.Toast.msg('状态' ,obj.msg );
				}else{
					Ext.ux.Toast.msg('状态' ,'保存成功' );
					if(typeof(storeObj) != 'undefined') {
						storeObj.reload();
					}
					if(null != winObj){
						winObj.destroy();
					}
					formObj.doLayout();
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
			}
		})
	}
	     var formSavePersonObj = function(formPanel ,winObj, storeObj,formObj){
	    if(formPanel.getForm().isValid()){
	    if(formPanel.getCmpByName('person.cardtype').getValue()==309){
	    	if(validateIdCard(formPanel.getCmpByName('person.cardnumber').getValue())==1){
				Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
				return;
			}else if(validateIdCard(formPanel.getCmpByName('person.cardnumber').getValue())==2){
				Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
				return;
			}else if(validateIdCard(formPanel.getCmpByName('person.cardnumber').getValue())==3){
				Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
				return;
			}
	    }
		formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			clientValidation : true,
			success : function(form ,action) {
				obj = Ext.util.JSON.decode(action.response.responseText);
				if(obj.exsit == false){
					Ext.Msg.alert('提示',obj.msg)
					return;
					/*Ext.Msg.confirm('信息确认', obj.msg, function(btn){
						if(btn=="yes") {
							winObj.destroy();
						if(storeObj != null && typeof(storeObj) != 'undefined') {
								storeObj.reload();
							}
						}
					})*/
					//Ext.ux.Toast.msg('状态' ,'保存成功' );
				}else{
					Ext.ux.Toast.msg('状态' ,'保存成功' );
					if(formObj != null) {
						formObj.getCmpByName('enterprise.enterprisename').setValue("");
						formObj.getCmpByName('enterprise.id').setValue("");
						formObj.getCmpByName('enterprise.shortname').setValue("");
						formObj.getCmpByName('enterprise.area').setValue("");
						formObj.getCmpByName('enterprise.cciaa').setValue("");
						formObj.getCmpByName('enterprise.organizecode').setValue("");
						formObj.getCmpByName('enterprise.telephone').setValue("");
						formObj.getCmpByName('enterprise.postcoding').setValue("");
						formObj.getCmpByName('enterprise.hangyeType').setValue("");
						formObj.getCmpByName('enterprise.hangyeName').setValue("");
						formObj.getCmpByName('person.id').setValue("");
					    formObj.getCmpByName('person.name').setValue("");
					    formObj.getCmpByName('person.sex').setValue("");
						formObj.getCmpByName('person.cardtype').setValue("");
						formObj.getCmpByName('person.cardnumber').setValue("");
						formObj.getCmpByName('person.cellphone').setValue("");
						formObj.getCmpByName('person.selfemail').setValue("");
						var enterpriseId = formPanel.getCmpByName('enterprise.id').getValue();
						var enterpriseName = formPanel.getCmpByName('enterprise.enterprisename').getValue();
						var shortname = formPanel.getCmpByName('enterprise.shortname').getValue();
						var area = formPanel.getCmpByName('enterprise.area').getValue();
						var cciaa = formPanel.getCmpByName('enterprise.cciaa').getValue();
						var organizecode = formPanel.getCmpByName('enterprise.organizecode').getValue();
						var telephone = formPanel.getCmpByName('enterprise.telephone').getValue();
						var postcoding = formPanel.getCmpByName('enterprise.postcoding').getValue();
						var hangyeType = formPanel.getCmpByName('enterprise.hangyeType').getValue();
						var hangyeName = formPanel.getCmpByName('enterprise.hangyeName').getValue();
						if(enterpriseName!= null && enterpriseName != "")	
						formObj.getCmpByName('enterprise.enterprisename').setValue(enterpriseName);
						if(enterpriseId != null && enterpriseId != "")	
						formObj.getCmpByName('enterprise.id').setValue(enterpriseId);
						if(shortname != null && shortname != "")	
						formObj.getCmpByName('enterprise.shortname').setValue(shortname);
						if(area != null && area != "")	
						formObj.getCmpByName('enterprise.area').setValue(area);
						if(cciaa != null && cciaa != "")	
						formObj.getCmpByName('enterprise.cciaa').setValue(cciaa);
						if(organizecode != null && organizecode != "")	
						formObj.getCmpByName('enterprise.organizecode').setValue(organizecode);
						if(telephone != null && telephone != "")
						formObj.getCmpByName('enterprise.telephone').setValue(telephone);
						if(postcoding != null && postcoding != "")
						formObj.getCmpByName('enterprise.postcoding').setValue(postcoding);
						if(hangyeType != null && hangyeType != "") {
						   formObj.getCmpByName('enterprise.hangyeType').setValue(hangyeType);
						   formObj.getCmpByName('enterprise.hangyeName').setValue(hangyeName);
						}
						var legalpersonid = formPanel.getCmpByName('enterprise.legalpersonid').getValue();
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/person/seePersonseeInfo.do',
							method : "post",
							params : {
								id : legalpersonid
							},
							success : function(d) {
								var c = Ext.util.JSON.decode(d.responseText);
								var id = c.data.id;
								var name = c.data.name;
								var sex = c.data.sex;
								var cardtype = c.data.cardtype;
								var cardnumber = c.data.cardnumber;
								var cellphone = c.data.cellphone;
								var selfemail = c.data.selfemail;
								if(id!=0 && id!="")
								formObj.getCmpByName('person.id').setValue(id);
								if(name!=0 && name!="")
								formObj.getCmpByName('person.name').setValue(name);
								if(sex!=0 && sex!="")
								formObj.getCmpByName('person.sex').setValue(sex);
								if(cardtype!=0 && cardtype!="")
								formObj.getCmpByName('person.cardtype').setValue(cardtype);
								if(cardnumber!=0 && cardnumber!="")
								formObj.getCmpByName('person.cardnumber').setValue(cardnumber);
								if(cellphone!=0 && cellphone!="")
								formObj.getCmpByName('person.cellphone').setValue(cellphone);
								if(selfemail!=0 && selfemail!="")
								formObj.getCmpByName('person.selfemail').setValue(selfemail);
							}
						})
						var edGrid = formObj.getCmpByName('gudong_store').get(0).get(1);
						var store = edGrid.getStore();
						var url = __ctxPath + '/creditFlow/common/getShareequity.do?enterpriseId='+ enterpriseId;
						store.proxy.conn.url = url;
						store.load();
						formObj.doLayout();
					}else{
						if(typeof(formPanel.getCmpByName('enterprise.id'))!="undefined" && null!=formPanel.getCmpByName('enterprise.id')){
							formPanel.getCmpByName('enterprise.id').setValue(obj.enterpriseId)
						}
						if(typeof(formPanel.getCmpByName('person.id'))!="undefined" && null!=formPanel.getCmpByName('person.id')){
							formPanel.getCmpByName('person.id').setValue(obj.newId)
						}
					}
					if(storeObj != null && typeof(storeObj) != 'undefined') {
						storeObj.reload();
					}
					
				}
				//winObj.close();//noted by gao 需求要求保存更新 不自动关闭窗口
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
				//winObj.close();
			}
		})
			return true;
			winObj.close();
		}else {
			return false;
			winObj.close();
		}
	}
   var formSavePersonObj1 = function(formPanel ,winObj, storeObj,formObj){
	    if(formPanel.getForm().isValid()){
	    	if(formPanel.getCmpByName('person.cardtype').getValue()==309){
	    	if(validateIdCard(formPanel.getCmpByName('person.cardnumber').getValue())==1){
				Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
				return;
			}else if(validateIdCard(formPanel.getCmpByName('person.cardnumber').getValue())==2){
				Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
				return;
			}else if(validateIdCard(formPanel.getCmpByName('person.cardnumber').getValue())==3){
				Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
				return;
			}
	    }
		formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			clientValidation : true,
			success : function(form ,action) {
				obj = Ext.util.JSON.decode(action.response.responseText);
				if(obj.exsit == false){
					Ext.ux.Toast.msg('状态' ,obj.msg );
					
				}else{
					Ext.ux.Toast.msg('状态' ,'保存成功' );
					if(formObj != null) {
						formObj.getCmpByName('person.id').setValue("");
					    formObj.getCmpByName('person.name').setValue("");
					    formObj.getCmpByName('person.sex').setValue("");
						formObj.getCmpByName('person.cardtype').setValue("");
						formObj.getCmpByName('person.cardnumber').setValue("");
						formObj.getCmpByName('person.marry').setValue("");
						formObj.getCmpByName('person.cellphone').setValue("");
						formObj.getCmpByName('person.birthday').setValue("");
						formObj.getCmpByName('person.postcode').setValue("");
					//formObj.getCmpByName('person.postaddress').setValue("");
						//formObj.getCmpByName('person.currentcompany').setValue("");
						/*formObj.getCmpByName('person.unitaddress').setValue("");
						formObj.getCmpByName("person.familyaddress").setValue("");
						formObj.getCmpByName('person.hukou').setValue("");*/
						var personId = formPanel.getCmpByName('person.id').getValue();
						var personName = formPanel.getCmpByName('person.name').getValue();
						var sex = formPanel.getCmpByName('person.sex').getValue();
						var cardtype = formPanel.getCmpByName('person.cardtype').getValue();
						var cardnumber = formPanel.getCmpByName('person.cardnumber').getValue();
						var marry = formPanel.getCmpByName('person.marry').getValue();
						var cellphone = formPanel.getCmpByName('person.cellphone').getValue();
						var birthday = formPanel.getCmpByName('person.birthday').getValue();
						var postcode = formPanel.getCmpByName('person.postcode').getValue();
						//var postaddress = formPanel.getCmpByName('person.postaddress').getValue();
						//var currentcompany = formPanel.getCmpByName('person.currentcompany').getValue();
						/*var unitaddress = formPanel.getCmpByName('person.unitaddress').getValue();
						var familyaddress = formPanel.getCmpByName('person.familyaddress').getValue();
						var hukou = formPanel.getCmpByName('person.hukou').getValue();*/
						formObj.getCmpByName('person.id').setValue(personId);
					    formObj.getCmpByName('person.name').setValue(personName);
					    formObj.getCmpByName('person.sex').setValue(sex);
						formObj.getCmpByName('person.cardtype').setValue(cardtype);
						formObj.getCmpByName('person.cardnumber').setValue(cardnumber);
						formObj.getCmpByName('person.marry').setValue(marry);
						formObj.getCmpByName('person.cellphone').setValue(cellphone);
						formObj.getCmpByName('person.birthday').setValue(birthday);
						formObj.getCmpByName('person.postcode').setValue(postcode);
						fillData(formObj,obj,null);
					//	formObj.getCmpByName('person.postaddress').setValue(postaddress);
						//formObj.getCmpByName('person.currentcompany').setValue(currentcompany);
						/*formObj.getCmpByName('person.unitaddress').setValue(unitaddress);
						formObj.getCmpByName("person.familyaddress").setValue(familyaddress);
						formObj.getCmpByName('person.hukou').setValue(hukou);*/
					}
					if(storeObj != null && typeof(storeObj) != 'undefined') {
						storeObj.reload();
					}
					//winObj.close();
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
			}
		})
		}
	}
	 var formSaveObj = function(formPanel ,winObj, storeObj,formObj){
	    if(formPanel.getForm().isValid()){
		formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			clientValidation : true,
			success : function(form ,action) {
				obj = Ext.util.JSON.decode(action.response.responseText);
				if(obj.exsit == false){
					Ext.ux.Toast.msg('状态' ,obj.msg );
				}else{
					Ext.ux.Toast.msg('状态' ,'保存成功' );
					if(formObj != null) {
						formObj.getCmpByName('person.id').setValue("");
					    formObj.getCmpByName('person.name').setValue("");
					    formObj.getCmpByName('person.sex').setValue("");
						formObj.getCmpByName('person.cardtype').setValue("");
						formObj.getCmpByName('person.cardnumber').setValue("");
						formObj.getCmpByName('person.marry').setValue("");
						formObj.getCmpByName('person.cellphone').setValue("");
						formObj.getCmpByName('person.selfemail').setValue("");
						formObj.getCmpByName('person.postcode').setValue("");
						formObj.getCmpByName('person.postaddress').setValue("");
						
						var personId = formPanel.getCmpByName('person.id').getValue();
						var personName = formPanel.getCmpByName('person.name').getValue();
						var sex = formPanel.getCmpByName('person.sex').getValue();
						var cardtype = formPanel.getCmpByName('person.cardtype').getValue();
						var cardnumber = formPanel.getCmpByName('person.cardnumber').getValue();
						var marry = formPanel.getCmpByName('person.marry').getValue();
						var cellphone = formPanel.getCmpByName('person.cellphone').getValue();
						var selfemail = formPanel.getCmpByName('person.selfemail').getValue();
						var postcode = formPanel.getCmpByName('person.postcode').getValue();
						var postaddress = formPanel.getCmpByName('person.postaddress').getValue();
				
						formObj.getCmpByName('person.id').setValue(personId);
					    formObj.getCmpByName('person.name').setValue(personName);
					    formObj.getCmpByName('person.sex').setValue(sex);
						formObj.getCmpByName('person.cardtype').setValue(cardtype);
						formObj.getCmpByName('person.cardnumber').setValue(cardnumber);
						formObj.getCmpByName('person.marry').setValue(marry);
						formObj.getCmpByName('person.cellphone').setValue(cellphone);
						formObj.getCmpByName('person.selfemail').setValue(selfemail);
						formObj.getCmpByName('person.postcode').setValue(postcode);
						formObj.getCmpByName('person.postaddress').setValue(postaddress);
				
					}
					if(storeObj != null && typeof(storeObj) != 'undefined') {
						storeObj.reload();
					}
					winObj.close();
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
			}
		})
		}
	}
	 var formSaveinvestPersonObj = function(formPanel ,winObj, storeObj,formObj){
	    if(formPanel.getForm().isValid()){
		    if(formPanel.getCmpByName('csInvestmentperson.cardtype').getValue()==309){
		    	if(validateIdCard(formPanel.getCmpByName('csInvestmentperson.cardnumber').getValue())==1){
					Ext.Msg.alert('身份证号码验证','投资客户证件号码不正确,请仔细核对')
					return;
				}else if(validateIdCard(formPanel.getCmpByName('csInvestmentperson.cardnumber').getValue())==2){
					Ext.Msg.alert('身份证号码验证','投资客户证件号码地区不正确,请仔细核对')
					return;
				}else if(validateIdCard(formPanel.getCmpByName('csInvestmentperson.cardnumber').getValue())==3){
					Ext.Msg.alert('身份证号码验证','投资客户证件号码生日日期不正确,请仔细核对')
					return;
				}
		    }
			formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				formBind : true,
				clientValidation : true,
				success : function(form ,action) {
					obj = Ext.util.JSON.decode(action.response.responseText);
					if(obj.exsit == false){
						Ext.Msg.confirm('信息确认', obj.msg, function(btn){
							if(btn=="yes") {
								winObj.destroy();
							if(storeObj != null && typeof(storeObj) != 'undefined') {
									storeObj.reload();
								}
							}
						})
						//Ext.ux.Toast.msg('状态' ,'保存成功' );
					}else{
						//Ext.ux.Toast.msg('状态' ,'保存成功' );
						if(formObj != null) {}
						if(storeObj != null && typeof(storeObj) != 'undefined') {
							storeObj.reload();
						}
						winObj.close();
					};
				},
				failure : function(form, action) {
					//Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
					winObj.close();
				}
			})
			return true;
		}else{
			return false;
		}
	}
	/**客户管理模块 ，公共方法， 当表单填写数据后， 客户点击保存按钮以后调用的方法。 */
	var formSave = function(formPanelId ,winObj ,storeObj,formObj){
		
		var  formPanel = Ext.getCmp(formPanelId);

		formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			success : function(form ,action) {
			Ext.ux.Toast.msg('状态' ,'保存成功' );
				if(typeof(storeObj) != 'undefined' && storeObj != null) {
					storeObj.reload();
				}
				if(null != winObj){
					winObj.destroy();
				}
				if(formObj != null) {
					alert(formObj.getCmpByName('person.id'))
					formObj.getCmpByName('person.id').setValue("");
				    formObj.getCmpByName('person.name').setValue("");
				    formObj.getCmpByName('person.sex').setValue("");
					formObj.getCmpByName('person.cardtype').setValue("");
					formObj.getCmpByName('person.cardnumber').setValue("");
					formObj.getCmpByName('person.marry').setValue("");
					formObj.getCmpByName('person.cellphone').setValue("");
					formObj.getCmpByName('person.selfemail').setValue("");
					formObj.getCmpByName('person.postcode').setValue("");
					formObj.getCmpByName('person.postaddress').setValue("");
					alert(formPanel.getCmpByName('person.id'))
					var personId = formPanel.getCmpByName('person.id').getValue();
					var personName = formPanel.getCmpByName('person.name').getValue();
					var sex = formPanel.getCmpByName('person.sex').getValue();
					var cardtype = formPanel.getCmpByName('person.cardtype').getValue();
					var cardnumber = formPanel.getCmpByName('person.cardnumber').getValue();
					var marry = formPanel.getCmpByName('person.marry').getValue();
					var cellphone = formPanel.getCmpByName('person.cellphone').getValue();
					var selfemail = formPanel.getCmpByName('person.selfemail').getValue();
					var postcode = formPanel.getCmpByName('person.postcode').getValue();
					var postaddress = formPanel.getCmpByName('person.postaddress').getValue();
					
					if(personId != null && personId != "")	
					formObj.getCmpByName('person.id').setValue(personId);
					if(personName != null && personName != "")	
					formObj.getCmpByName('person.name').setValue(personName);
					if(sex != null && sex != "")	
					formObj.getCmpByName('person.sex').setValue(sex);
					if(cardtype != null && cardtype != "")	
					formObj.getCmpByName('person.cardtype').setValue(cardtype);
					if(cardnumber != null && cardnumber != "")	
					formObj.getCmpByName('person.cardnumber').setValue(cardnumber);
					if(marry != null && marry != "")	
					formObj.getCmpByName('person.marry').setValue(marry);
					if(cellphone != null && cellphone != "")	
					formObj.getCmpByName('person.cellphone').setValue(cellphone);
					if(selfemail != null && selfemail != "")	
					formObj.getCmpByName('person.selfemail').setValue(selfemail);
					if(postcode != null && postcode != "")	
					formObj.getCmpByName('person.postcode').setValue(postcode);
					if(postaddress != null && postaddress != "")	
					formObj.getCmpByName('person.postaddress').setValue(postaddress);
				}
				formPanel.doLayout();
				
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整');
			}
		})
	}
	
	/**客户管理模块 ，公共方法， 当表单填写数据后， 客户点击返回或取消按钮以后调用的方法。*/
	var formBack = function(formPanelId,winObj , storeObj){
		isEmpty(formPanelId,winObj , storeObj);
	}
	/**编辑企业客户信息*/
	var editEnterpriseInfo = function(enterpriseId,jStore_enterprise,formObj) {
		Ext.Ajax.request({
			url :  __ctxPath+'/creditFlow/customer/enterprise/loadInfoEnterprise.do',
			method : 'POST',
			success : function(response,request) {
				var obj = Ext.decode(response.responseText);
				enterpriseData = obj.data.enterprise;
				var personData = obj.data.person;
				if(enterpriseData.isCheckCard){
					enterpriseData.isCardcodeReadOnly=true;
				}else{
					enterpriseData.isCardcodeReadOnly=false;
				}
				updateEnterprise(enterpriseData,personData,jStore_enterprise,formObj);
			},
			failure : function(response) {
				if(response.status==0){
					Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
				}else if(response.status==-1){
					Ext.ux.Toast.msg('状态','连接超时，请重试!');
				}else{
					Ext.ux.Toast.msg('状态','编辑失败!');	
				}
			},
			params: {id: enterpriseId }
		});	
	}
	/**编辑企业客户信息updateEnterprise1*/
	var editEnterpriseInfo1 = function(enterpriseId,jStore_enterprise,formObj,legalId,legalHidden) {
		Ext.Ajax.request({
			url :  __ctxPath+'/creditFlow/customer/enterprise/loadInfoEnterprise.do',
			method : 'POST',
			success : function(response,request) {
				var obj = Ext.decode(response.responseText);
				enterpriseData = obj.data.enterprise;
				var personDate = obj.data.person;
				updateEnterprise1(enterpriseData,personDate,jStore_enterprise,formObj,legalId,legalHidden);
			},
			failure : function(response) {
				if(response.status==0){
					Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
				}else if(response.status==-1){
					Ext.ux.Toast.msg('状态','连接超时，请重试!');
				}else{
					Ext.ux.Toast.msg('状态','编辑失败!');	
				}
			},
			params: {id: enterpriseId }
		});	
	}
	/**编辑个人客户信息*/
	var editPersonInfo = function(personId,formObj) {
		Ext.Ajax.request({
			url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
			method : 'POST',
			success : function(response,request) {			
				obj = Ext.util.JSON.decode(response.responseText);
				var personData = obj.data;
				var randomId=rand(100000);
				var id="update_person"+randomId;
	            var url= __ctxPath	+ '/creditFlow/customer/person/updateInfoPerson.do';
	            var window_update = new Ext.Window({
				title : '编辑个人客户详细信息',
				height : 460,
				constrainHeader : true,
				collapsible : true,
				frame : true,
				iconCls : 'btn-edit',
				border : false,
				bodyStyle : 'overflowX:hidden',
				buttonAlign : 'right',
				iconCls : 'newIcon',
				width : (screen.width - 180) * 0.7 + 160,
				resizable : true,
				layout : 'fit',
				autoScroll : false,
				constrain : true,
				closable : true,
				modal : true,
				items : [new personObj({
							personData : personData,
							url:url,
							id:id
						})],
				tbar : [new Ext.Button({
							text : '更新',
							tooltip : '更新基本信息',
							iconCls : 'btn-refresh',
							hideMode : 'offsets',
							handler : function() {
								var panel_add = window_update.get(0);								
								formSaveObj(panel_add,window_update,null,formObj);
							}
						})],
				listeners : {
					'beforeclose' : function(panel) {
						window_update.destroy();
					}
				}
			});
			window_update.show();
	},
			failure : function(response) {					
					Ext.ux.Toast.msg('状态','操作失败，请重试');		
			},
			params: { id: personId }
		});	
}
/**
 * 删除个人照片 个人身份证正反面照片
 * @param {} hiddenId  附件ID 隐藏域的name
 * @param {} displayId  照片显示的的name
 * @param {} winID      生成的动态panelID
 * @return {Boolean}
 */
var delePhotoFile_new=function(hiddenId,displayId,winID){
    var  winobj=Ext.getCmp(winID);
	var panelObj=winobj.get(0);
	var adV=panelObj.getCmpByName(hiddenId).getValue();
	if(null==adV || ""==adV || adV==0){
	   alert('请先上传图片');return false}
     Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+adV,
		method : 'POST',
		scope : this,
		success : function() {
			Ext.ux.Toast.msg('状态', '删除成功！');
			var display = panelObj.getCmpByName(displayId);
			if(hiddenId=="personPhotoId"){
			    var personsex = panelObj.getCmpByName('person.sex');
				 if (personsex.getValue == '313') {// 312
						display.body.update('<img src="' + __ctxPath+ '/images/default_image_female.jpg" />');
				} else {
						display.body.update('<img src="' + __ctxPath+ '/images/default_image_male.jpg" />');
				}
				panelObj.getCmpByName(hiddenId).setValue(null);
				return false;
			}
			display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80/></div>',false);
			panelObj.getCmpByName(hiddenId).setValue(null);
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('状态', '删除失败，请重试！');
		}
	});
   
};
var deleFtpPhotoFile=function(hiddenId,displayId,winID){
    var  winobj=Ext.getCmp(winID);
	var panelObj=winobj.get(0);
	var adV=panelObj.getCmpByName(hiddenId).getValue();
	if(null==adV || ""==adV || adV==0){
	   alert('请先上传图片');return false}
     Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/removeImgFileForm.do?fileid='+adV,
		method : 'POST',
		scope : this,
		success : function() {
			Ext.ux.Toast.msg('状态', '删除成功！');
			var display = panelObj.getCmpByName(displayId);
			display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80/></div>',false);
			panelObj.getCmpByName(hiddenId).setValue(null);
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('状态', '删除失败，请重试！');
		}
	});
   
};
var showPdf=function(fileid,src){
	new PdfTemplateView(fileid,src,null,null);
	return false;
}
/**
 * 上传个人照片 个人身份证正反面照片
 * @param {} title
 * @param {} mark
 * @param {} displayProfilePhoto 
 * @param {} personPId
 * @param {} winID
 */
var  uploadPhotoBtnPerson_new= function(title,mark,displayProfilePhoto,personPId,winID) {
	        var  winobj=Ext.getCmp(winID);
	        var panelObj=winobj.get(0);
	        new Ext.Window({
			title : title,
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true ,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items:[new Ext.form.FormPanel({
				url : getRootPath()+'/contract/uploadPhotoProduceHelper.do',
				monitorValid : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				enctype : 'multipart/form-data',
				fileUpload: true, 
				layout : 'column',
				frame : true ,
				items : [{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 140,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : title,
						allowBlank : false,
						blankText : title+'不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
	    				inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'mark',
						value :mark
					}]
				}],
				scope:this,
				buttons : [{
					text : '上传',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
						
					       var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/  
						   var url = 'file://'+ Ext.get('fileUpload').dom.value;  
	                       var furl=Ext.get('fileUpload').dom.value;
						   var extendname=furl.substring(furl.lastIndexOf("."),furl.length);
						   if (!img_reg.test(url) && extendname!=".pdf") {  
	                      	 alert('您选择的文件格式不正确,请重新选择!');
	                         return false;
	                       };
						    var pwindow=this.ownerCt.ownerCt.ownerCt;
						    var collection = this.ownerCt.ownerCt.ownerCt.items; 
						    var formPanel=collection.first();
						    formPanel.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							params:{extendname:extendname},
							success : function(form ,action) {
								Ext.ux.Toast.msg('提示',title+'上传成功！',
								pwindow.destroy(),
									function(btn, text) {
								});
								var fileid = action.result.fileid;
								var webPath = action.result.webPath;
								var display =panelObj.getCmpByName(displayProfilePhoto);
								if (displayProfilePhoto == 'displayProfilePhoto') {
									if(extendname==".jpg" ||extendname==".JPG"|| extendname==".jpeg"){
										display.body.update('<img src="' + __ctxPath+ '/' + webPath+ '" ondblclick=showPic("' + webPath	+ '") width="100%" height="100%"/>');
									}else if(extendname==".pdf"){
										display.body.update('<img src="' + __ctxPath+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('+fileid+',"'+webPath+'") width="100%" height="100%"/>');
									}
									panelObj.getCmpByName(personPId).setValue(fileid);	
									return false;
								}
								if(extendname.toLowerCase()==".jpg"  ||extendname.toLowerCase()==".jpeg"){
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ getRootPath()+"/"+webPath+'" ondblclick=showPic("'+webPath+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+fileid+',"'+webPath+'") width =140 height=80  /></div>',false);	
								}
								panelObj.getCmpByName(personPId).setValue(fileid);
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示',title+'上传失败！');		
							}
						});
					}
				}]
			})]
		}).show();
};

var showPic = function(src){
	var pic_panel;
	var pic_win;
	pic_panel = new Ext.Panel({
        frame:true,
        autoHeight : true,
        width: 374,
        layout:'fit',
        items:[{
        	html: '<div align="center"><img src="'+src+'"  title="将鼠标置于图片上，滚动鼠标滑轮可以把图片放大缩小哦！" onmousewheel="return bbimg(this)" style="cursor:pointer;"   ondblclick="downloadPic(\''+src+'\'); "/></div>'
        }]
	});

	pic_win = new Ext.Window({
		id : 'id_pic_win',
		title: "原图片预览",
		autoScroll : true,
		layout : 'fit',
		width : 550,
		height :405,
		maximizable : true,
		closable : true,
		resizable : true,
		plain : true,
		modal : true,
		buttonAlign: 'center',
		bodyStyle : 'padding: 0',
		items : [pic_panel],
		listeners : {
			scope : this,
			maximize : function() {
				pic_panel.setWidth(window.screen.width - 26);
			}
		}
	});
	
	pic_win.show();
	
};
function bbimg(o){
	var zoom=parseInt(o.style.zoom, 10)||100;zoom+=event.wheelDelta/12;if (zoom>0) o.style.zoom=zoom+'%';
	return false;
};
//下载图片
function downloadPic(imgURL){
	
	var oPop;
	
	if(Ext.isIE){
	  	oPop = window.open(imgURL,"","width=0,height=0,top=5000,left=5000");   
	}else{
		oPop = window.open(imgURL,"","width=0,height=0,top=9,left=9");
	}
	  for(;oPop.document.readyState != "complete";)   
	  { 
	    if(oPop.document.readyState == "complete")break; 
	  } 
	  oPop.document.execCommand("SaveAs"); 
	  oPop.close();
};