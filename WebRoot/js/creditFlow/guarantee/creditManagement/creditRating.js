

function selectCustomer(obj) {
	if (obj.enterprisename) {
		// Ext.getDom('creditRating.customerName').value = obj.enterprisename;
		Ext.getCmp('customerName').setValue(obj.enterprisename);
	} else if (obj.name) {
		// Ext.getCmp('creditRating.customerName').setValue(obj.name);
		// Ext.getDom('creditRating.customerName').value = obj.name;
		Ext.getCmp('customerName').setValue(obj.name);
	}
	Ext.getCmp('customerId').setValue(obj.id);

}

var task = "";
function runtime() {
	var arr = new Array();
	arr[0] = 0;
	arr[1] = 0;
	arr[2] = ':';
	arr[3] = 0;
	arr[4] = 0;
	arr[5] = ':';
	arr[6] = 0;
	arr[7] = 0;

	var str = '';
	task = {

		run : function() {
			if (arr[7] == 9) {
				arr[7] = 0;
				if (arr[6] == 5) {
					arr[6] = 0;
					if (arr[4] == 9) {
						arr[4] = 0;
						if (arr[3] == 5) {
							arr[3] = 0;
							if (arr[1] == 9) {
								arr[1] = 0;
								if (arr[0] == 5) {
									arr[0] = 0;
								} else {
									arr[0] = arr[0] + 1;
								}
							} else {
								arr[1] = arr[1] + 1;
							}
						} else {
							arr[3] = arr[3] + 1;
						}
					} else {
						arr[4] = arr[4] + 1;
					}
				} else {
					arr[6] = arr[6] + 1;
				}
			} else {
				arr[7] = arr[7] + 1;
			}

			for (var i = 0; i < arr.length; i++) {
				str = str + arr[i];
			}

			if (true) {
				Ext.getCmp("pgtime_new").setText("<b>评估时间：</b>" + str, false);
				Ext.getCmp("pgtime").setValue(str);
				str = '';
			}

		},
		interval : 1000

	}
	Ext.TaskMgr.start(task)

};
var addCreditRatingEB = function(jStore_creditRating) {
	var pars = {
		ptype : "dx",
		ctype : "企业"
	};
	var _store = new Ext.data.JsonStore({
				id : "_store",
				autoLoad : false,
				url : __ctxPath
						+ '/creditFlow/creditmanagement/rtListRatingTemplate.do',
				root : 'topics',
				baseParams :pars,
				fields : [{
							name : 'id'
						}, {
							name : 'templateName'
						}, {
							name : 'ptype'
						}, {
							name : 'customerType'
						}]
			});
	_store.load();
	var formPanel = new Ext.FormPanel({
		id : "cr_form",
		labelAlign : 'right',
		buttonAlign : 'center',
		// bodyStyle : 'padding:25px 25px 25px',
		labelWidth : 110,
		frame : true,
		waitMsgTarget : true,
		monitorValid : true,
		width : 500,
		items : [{
					xtype : 'label',
					html : '<font >注:未填写数据将自动获取最低分</font>'
				}, {
					id : 'ptype',
					xtype : 'radiogroup',
					fieldLabel : '评估类型',
					name : 'creditRating.ptype',
					items : [{
								boxLabel : '定性',
								name : 'ptype',
								inputValue : 'dx',
								checked : true
							}, {
								boxLabel : '定量',
								name : 'ptype',
								inputValue : 'dl'
							}],
					listeners : {
						change : function(checkbox, checked) {// 这事件是当radiogroup的值发生改变时进入
							Ext.getCmp("customerName").setValue("");
							Ext.getCmp("creditTemplate").setValue("");
							var _cv = Ext.getCmp("creditRating.customerType1")
									.getValue().inputValue;
							var _pv = checked.inputValue;
							var baseParam={ptype : _pv,ctype : _cv}
							var store=Ext.getCmp("creditTemplate").getStore();
							store.on('beforeload', function(store, o) {
								
								Ext.apply(o.params, baseParam);
							});
							store.reload();
						}
					}
				}, {
					id : 'creditRating.customerType1',
					xtype : 'radiogroup',
					fieldLabel : '客户类型',
					name : 'creditRating.customerType1',
					items : [{
								boxLabel : '企业',
								name : 'customerType',
								inputValue : '企业',
								checked : true
							}, {
								boxLabel : '个人',
								name : 'customerType',
								inputValue : '个人'
							}],
					listeners : {
						change : function(checkbox, checked) {// 这事件是当radiogroup的值发生改变时进入
							Ext.getCmp("customerName").setValue("");
							Ext.getCmp("creditTemplate").setValue("");

							var _pv = Ext.getCmp("ptype").getValue().inputValue;
							var _cv = checked.inputValue;
							var baseParam={ptype : _pv,ctype : _cv}
							var store=Ext.getCmp("creditTemplate").getStore();
							store.on('beforeload', function(store, o) {
								
								Ext.apply(o.params, baseParam);
							});
							store.reload();
						}

					}
				}, {
					id : 'customerName',
					xtype : 'combo',
					triggerClass : 'x-form-search-trigger',
					fieldLabel : '客户名称',
					name : 'customerName',
					width : 150,
					// readOnly : true,
					allowBlank : false,
					blankText : '必填信息',
					resizable : true,
					onTriggerClick : function() {
						newValue = formPanel.getForm().getValues()["customerType"];
						if (newValue == '企业') {
							selectEnterprise(selectCustomer);

						} else if (newValue == '个人') {
							selectPWName(selectCustomer);
						}
					}
				}, {
					xtype : 'hidden',
					id : 'customerId'

				}, {
					id : 'creditTemplate',
					xtype : 'combo',
					fieldLabel : '<font color=red>*</font>资信评估模板 ',
					name : 'creditTemplate',
					width : 150,
					mode : 'romote',
					allowBlank : false,
					blankText : '必填信息',
					store : _store,
					displayField : 'templateName',
					valueField : 'id',
					triggerAction : 'all',
					listeners : {
						expand : function(checkbox, checked) {// 这事件是当radiogroup的值发生改变时进入

						}
					}
				}],
		buttons : [{
			text : '下一步',
			formBind : true,
			handler : function() {
				var creditTemplateId = Ext.getCmp('creditTemplate').getValue();

				var creditTemplateName = formPanel.getForm().getValues()["creditTemplate"];
				var customerType = newValue = formPanel.getForm().getValues()["customerType"];
				var ptype = newValue = formPanel.getForm().getValues()["ptype"];
				var customerId = Ext.getCmp("customerId").getValue();
				var customerName = Ext.getCmp("customerName").getValue();
				if (ptype == "dx") {
					Ext.Ajax.request({
						url : __ctxPath
								+ '/creditFlow/creditmanagement/getCurrentUserCreditRating.do?cid='
								+ customerId + "&mid=" + creditTemplateId,
						method : 'POST',
						success : function(response, request) {
							var obj = Ext.util.JSON
									.decode(response.responseText);
							var username = obj.username
							runtime()
							creditRatingSubEB(customerId, customerName,
									creditTemplateId, creditTemplateName,
									customerType, jStore_creditRating, username,ptype);
						}
					});
				} else {
					Ext.MessageBox.confirm('确认', '是否确认进行评估，定量评估中客户资料中未填写数据系统将自动获取最低分', function(btn) {
							if (btn == 'yes') {
									Ext.Ajax.request({
									url : __ctxPath+ '/creditFlow/creditmanagement/autoAddSubCreditRating.do',
									params : {
										creditTemplateId :creditTemplateId,
										customerId:customerId,
										customerName:customerName,
										customerType:customerType,
										creditTemplateName:creditTemplateName,
										ptype:ptype
									},
									method : 'POST',
									success : function() {
												Ext.ux.Toast.msg('操作信息',
														'评估成功!');
												win.destroy();
												jStore_creditRating.load();
											},
									failure : function(form, action) {
												Ext.ux.Toast.msg('操作信息',
														'提交失败!');
											}
								});
							}
						});
					
				}
				 window.destroy();

				// Ext.Ajax.request({
				// url : __ctxPath+'/credit/creditmanagement/getCurrentUser.do',
				// method : 'POST',
				// success : function(response, request) {
				// var obj=Ext.util.JSON.decode(response.responseText);
				// var username=obj.username
				// runtime()
				// creditRatingSubEB(customerId,customerName, creditTemplateId,
				// creditTemplateName, customerType,
				// jStore_creditRating,username);
				// }
				// });

			}
		}, {
			text : '重置',
			handler : function() {
				Ext.getCmp("creditTemplate").setValue("");
				// formPanel.getForm().reset();
			}
		}]
	});

	
	var window = new Ext.Window({
				layout : 'fit',
				width : 420,
				height : 240,
				closable : true,
				resizable : false,
				buttonAlign : 'center',
				plain : true,
				border : false,
				modal : true,
				items : [formPanel],
				title : '新建资信评估',
				collapsible : true
			});
	window.show();
}

var getElementsByName = function(tag, name) {
	var returns = document.getElementsByName(name);
	if (returns.length > 0)
		return returns;
	returns = new Array();
	var e = document.getElementsByTagName(tag);
	for (var i = 0; i < e.length; i++) {
		if (e[i].getAttribute("name") == name) {
			returns[returns.length] = e[i];
		}
	}
	return returns;
}
function isIE() { // ie?
	if (window.navigator.userAgent.toLowerCase().indexOf("msie") >= 1)
		return true;
	else
		return false;
}
function check(score, quanzhong, maxStore, id_score, id_maxStore, opid_score,
		id_quanzhong) {

	document.getElementById(id_score).innerHTML = score;
	document.getElementById(id_quanzhong).innerHTML = quanzhong;
	document.getElementById(id_maxStore).innerHTML = maxStore;

	var x = getElementsByName("label", "opt" + id_score)

	if (!isIE()) { // firefox innerText define
		HTMLElement.prototype.__defineGetter__("innerText", function() {
					var anyString = "";
					var childS = this.childNodes;
					for (var i = 0; i < childS.length; i++) {
						if (childS[i].nodeType == 1)
							anyString += childS[i].tagName == "BR"
									? '\n'
									: childS[i].innerText;
						else if (childS[i].nodeType == 3)
							anyString += childS[i].nodeValue;
					}
					return anyString;
				});
		HTMLElement.prototype.__defineSetter__("innerText", function(sText) {
					this.textContent = sText;
				});
	}
	// var x=document.getElementsByName("opt"+id_score)
	for (var i = 0; i < x.length; i++) {

		x[i].innerHTML = x[i].innerText
	}
	document.getElementById(opid_score).innerHTML = "<font color='red'><strong>"
			+ score + "</strong></font>";

}

var countResult = function(sub, win, jStore_creditRating) {

	var arr_id = new Array;
	var arr_score = new Array;

	var score = 0;
	var maxScore = 0;
	var CompDom =Ext.getCmp("gPanel_ent").getEl().dom
	var getCK = Ext.query("input",CompDom);

	for (var i = 0; i < getCK.length; i++) {

		var whichObj = getCK[i];

		if (whichObj.type == "radio"
				&& whichObj.name.indexOf("jumpPath_") == -1
				&& whichObj.name.indexOf("customerType") == -1&& whichObj.name.indexOf("ptype") == -1) {

			var radioName = whichObj.name;

			var obj = document.getElementsByName(radioName);

			if (obj != null) {
				for (var j = 0; j < obj.length; j++) {

					if (obj[j].checked) {
						break;
					}

					if (j == obj.length - 1 && !obj[j].checked) {
						var index = radioName.lastIndexOf("s");
						var k = radioName
								.substring(index + 1, radioName.length)

						Ext.ux.Toast.msg('操作信息', '第' + k + '个指标评估未完成，请继续评估!');
						return;
					}
				}
			}

		}

		if (whichObj.type == "radio" && whichObj.checked == true
				&& whichObj.name.indexOf("jumpPath_") == -1
				&& whichObj.name.indexOf("customerType") == -1&& whichObj.name.indexOf("ptype") == -1) {
			var radioName = whichObj.name
			var index = radioName.lastIndexOf("s");
			radioName = radioName.substring(0, index)
			arr_id.push(radioName);
			arr_score.push(Ext.getDom(radioName + 'score').innerHTML);

			var v = Ext.getDom(radioName + 'score').innerHTML;
			var q = Ext.getDom(radioName + 'quanzhong').innerHTML
			var m = Ext.getDom(radioName + 'maxStore').innerHTML
			score = parseFloat(score) + parseFloat(v) * parseFloat(q);
			maxScore = parseFloat(maxScore) + parseFloat(m) * parseFloat(q);
		}
	}

	Ext.getCmp('arr_id').setValue(arr_id.toString());
	Ext.getCmp('arr_score').setValue(arr_score.toString());
	Ext.getCmp('ratingScore').setValue(Math.round((score / maxScore * 100)* Math.pow(10, 2))/ Math.pow(10, 2));

	Ext.Ajax.request({
		url : __ctxPath
				+ '/creditFlow/creditmanagement/getScoreGradeCreditRating.do?creditRating.ratingScore='
				+ score / maxScore * 100 + "&creditTemplateId="
				+ Ext.getCmp("creditTemplateId").getValue(),
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp('creditRegister').setValue(obj.data.grandname);
			Ext.getCmp('advise_sb').setValue(obj.data.hanyi);
			if (sub == 1) {
				Ext.MessageBox.confirm('确认', '是否确认提交本次评估', function(btn) {
							if (btn == 'yes') {
								Ext.getCmp('addCreditRatingSub').getForm()
										.submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											timeout : 120000,
											success : function() {
												Ext.ux.Toast.msg('操作信息',
														'评估成功!');
												win.destroy();
												jStore_creditRating.load();
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('操作信息',
														'提交失败!');
											}
										});
							}
						});
			}
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('操作信息', '服务器未响应，失败!');
		}
	});

};

var creditRatingSubEB = function(customerId, customerName, creditTemplateId,
		creditTemplateName, customerType, jStore_creditRating, username,ptype) {

	var fPanel_search = new Ext.form.FormPanel({
				id : 'addCreditRatingSub',
				url : __ctxPath
						+ '/creditFlow/creditmanagement/addSubCreditRating.do',
				labelAlign : 'right',
				buttonAlign : 'center',
				bodyStyle : 'padding:5px;',
				height : 100,
				width : Ext.getBody().getWidth(),
				frame : true,
				labelWidth : 80,
				monitorValid : true,
				items : [{
					layout : 'column',
					border : false,
					labelSeparator : ':',
					defaults : {
						layout : 'form',
						border : false,
						columnWidth : .33
					},
					items : [{
								items : [{
											id : 'arr_id',
											xtype : 'hidden',
											name : 'creditRating.arr_id'
										}, {
											id : 'arr_score',
											xtype : 'hidden',
											name : 'creditRating.arr_score'
										}, {
											id : 'ratingScore',
											xtype : 'hidden',
											name : 'creditRating.ratingScore'
										}, {
											id : 'customerId',
											xtype : 'hidden',
											name : 'creditRating.customerId',
											value : customerId
										}, {
											id : 'creditTemplateId',
											xtype : 'hidden',
											name : 'creditRating.creditTemplateId',
											value : creditTemplateId
										}, {
											id : 'creditRegister',
											xtype : 'hidden',
											name : 'creditRating.creditRegister'
										}, {
											id : 'advise_sb',
											xtype : 'hidden',
											name : 'creditRating.advise_sb'
										}, {
											id : 'templateScore',
											xtype : 'hidden',
											name : 'creditRating.templateScore'// 模版总分值
										}, {
											xtype : 'hidden',
											name : 'creditRating.customerType',
											value : customerType
										}, {
											xtype : 'hidden',
											name : 'creditRating.customerName',
											value : customerName
										}, {
											xtype : 'hidden',
											name : 'creditRating.creditTemplate',
											value : creditTemplateName
										},{
											xtype : 'hidden',
											name : 'creditRating.ptype',
											value : ptype
										}, {
											id : 'pgtime',
											xtype : 'hidden',
											name : 'creditRating.pgtime'
										}, {
											html : "<b>客户类型：</b>"
													+ customerType
										}, {
											html : '<br>'
										}, {

											html : "<b>评估人：</b>" + username
										}]
							}, {
								items : [{
											html : "<b>客户名称：</b>"
													+ customerName
										}, {
											html : '<br>'
										}, {

											xtype : 'label',
											// fieldLabel : '评估时间',
											id : 'pgtime_new',
											anchor : '90%',
											readOnly : true
										}]
							}, {
								items : [{

									html : "<b>资信评估模板：</b>"
											+ creditTemplateName
								}]
							}]
						// items
				}],
				buttons : [{
							text : '提交评估',
							tooltip : '提交评估',
							iconCls : 'searchIcon',
							scope : this,
							handler : function() {

								countResult(1, win1, jStore_creditRating);
							}
						}]
			});
	var sreader = new Ext.data.JsonReader({
				totalProperty : 'totalCounts',
				root : 'result'
			}, [{
						name : 'id'
					}, {
						name : 'indicatorId'
					}, {
						name : 'indicatorName'
					}, {
						name : 'optionName'
					}, {
						name : 'score'
					}, {
						name : 'defen'
					}, {
						name : 'xuhao'
					}]);

	jStore_enterprise = new Ext.data.GroupingStore({
				url : __ctxPath
						+ '/creditFlow/creditmanagement/addJsonCreditRating.do',
				reader : sreader,
				baseParams : {
					creditTemplateId : creditTemplateId
				},
				groupField : 'indicatorId'
			});

	jStore_enterprise.load();
	var incName = function(data, cellmeta, record) {

		return record.get("indicatorName");
	}
	var cModel_enterprise = new Ext.grid.ColumnModel([{
				header : '',
				width : 10,
				dataIndex : 'xuhao'
			}, {
				header : '',
				width : 200,
				dataIndex : 'indicatorId',
				hidden : true,
				renderer : incName
			}, {
				header : "选项",
				width : 300,
				dataIndex : 'optionName'
			}, {
				header : "分值",
				width : 100,
				dataIndex : 'score'
			}, {
				header : "得分",
				width : 100,
				sortable : true,
				dataIndex : 'defen',
				hidden : true
			}]);

	var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});

	var gPanel_enterprise = new Ext.grid.GridPanel({
				id : 'gPanel_ent',
				store : jStore_enterprise,
				width : Ext.getBody().getWidth() - 15,
				height : Ext.getBody().getHeight() - 125,
				view : new Ext.grid.GroupingView({
							forceFit : true,
							groupTextTpl : '{text}'

						}),
				autoScroll : true,
				colModel : cModel_enterprise,
				// autoExpandColumn : 6,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,

				// tbar : [button_add,button_see,button_update,button_delete],
				listeners : {
					'rowdblclick' : function(grid, index, e) {
						var id = grid.getSelectionModel().getSelected()
								.get('id');
						// seeEnterprise(id);
					}
				}
			});

	var panel_enterprise = new Ext.Panel({
				autoHeight : true,
				autoScroll : true,
				items : [fPanel_search, gPanel_enterprise]
			});

	var win1 = new Ext.Window({

				layout : 'fit',
				width : Ext.getBody().getWidth(),
				height : Ext.getBody().getHeight(),
				closable : true,
				resizable : false,
				buttonAlign : 'center',
				plain : true,
				border : false,
				modal : true,
				items : [panel_enterprise],
				title : '新建资信评估',
				collapsible : true
			});

	win1.show();
	win1.addListener({
				'destroy' : function() {
					Ext.TaskMgr.stop(task)
				}
			});
};
