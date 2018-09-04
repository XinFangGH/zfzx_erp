function selectEnterprise(funName) {
	var
		jStore_enterpriseForSelect = new Ext.data.JsonStore({
				url : __ctxPath
						+ '/creditFlow/customer/enterprise/entListEnterprise.do?isAll='+isGranted('_seeAll_qykh'),
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [{
							name : 'id'
						}, {
							name : 'surplusMoney'
						}, {
							name : 'shortname'
						}, {
							name : 'tradetype'
						}, {
							name : 'ownership'
						}, {
							name : 'registermoney'
						}, {
							name : 'enterprisename'
						}, {
							name : 'linkman'
						}, {
							name : 'legalperson'
						}, {
							name : 'controlpersonname'
						}, {
							name : 'controlpersonid'
						}, {
							name : 'legalpersonid'
						}, {
							name : 'linkmampersonid'
						}, {
							name : 'area'
						}, {
							name : 'linkperson'
						}, {
							name : 'legalperson'
						}, {
							name : 'controlperson'
						}, {
							name : 'linkpersonjob'
						}, {
							name : 'linkpersonmobile'
						}, {
							name : 'linkpersontel'
						}, {
							name : 'tradetypev'
						}, {
							name : 'ownershipv'
						}, {
							name : 'cciaa'
						}, {
							name : 'address'
						}, {
							name : 'organizecode'
						}, {
							name : 'telephone'
						}, {
							name : 'postcoding'
						}, {//
							name : 'opendate'
						}, {
							name : 'hangyeType'
						}, {
							name : 'hangyeName'
						},{
							name : 'documentType'
						},{
						   name : 'taxnum'
						}, {
							name : 'isCheckCard'
						}],
				// 服务器端排序 by chencc
				// sortInfo : {field: "enterprisename", direction: "DESC"}
				remoteSort : true
			});
	var seeRegistermoney = function(val) {
		if (val != "0" || val != null) {
			return val + '万元';
		} else {
			return '';
		}
	}

	// function selectEnterprise(funName) {
	var anchor = '100%';
	var pageSize = 15;
	var listWindowWidth = 650;
	var listWindowHeight = 465;
	var detailWindowWidth = 580;
	var detailWindowHeight = 370;
	var defaultLabelWidth = 110;// 默认标签的宽度
	var defaultTextfieldWidth = 135;// 默认文本输入域宽度
	var enterpriseJsonObj;
	jStore_enterpriseForSelect.addListener('load', function() {
				gPanel_enterpriseSelect.getSelectionModel().selectFirstRow();
			}, this);
	jStore_enterpriseForSelect.addListener('loadexception', function(proxy,
					options, response, err) {
				Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
			}, this);
	//var sm = new Ext.grid.CheckboxSelectionModel();
	var cModel_enterprise = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				id : 'enterpriseName',
				header : "企业名称",
				width : 100,
				sortable : true,
				dataIndex : 'enterprisename'
			}, {
            id : 'enterpriseName',
            header : "法人",
            width : 90,
            sortable : true,
            dataIndex : 'linkman'
        },{
				header : "企业简称",
				width : 100,
				sortable : true,
				dataIndex : 'shortname'
			}, {
				header : "证件号码",
				width : 105,
				sortable : true,
				dataIndex : 'organizecode'
			}/*, {
				header : "营业执照号码",
				width : 105,
				sortable : true,
				dataIndex : 'cciaa'
			}*//*, {
				header : "所有制性质",
				width : 105,
				sortable : true,
				dataIndex : 'ownershipv'
			}*/, {
				header : "注册资金",
				width : 105,
				sortable : true,
				dataIndex : 'registermoney',
				renderer : seeRegistermoney
			}, {
				header : "企业成立日期",
				width : 90,
				sortable : true,
				dataIndex : 'opendate'
			}]);
	var pagingBar_Enterprise = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : jStore_enterpriseForSelect,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
			});

	var gPanel_enterpriseSelect = new Ext.grid.GridPanel({
		id : 'gPanel_enterpriseSelect',
		store : jStore_enterpriseForSelect,
		colModel : cModel_enterprise,
		autoExpandColumn : 'enterpriseName',
		//selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : true,
		bbar : pagingBar_Enterprise,
		tbar : [{
			text : '查看',
			iconCls : 'btn-detail',
			handler : function(btn, e) {
				var selected = gPanel_enterpriseSelect.getSelectionModel().getSelected();
				var len = selected.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
				} else {
					enterpriseId = selected.get('id');
					Ext.Ajax.request({
						url : __ctxPath+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							var enterpriseData = obj.data.enterprise;
				            var personData = obj.data.person;
							if (obj.success == false || obj.success == 'false') {
								Ext.ux.Toast.msg('错误提示', '查询出错');
								return;
							}
							var window_see = new Ext.Window({
										id : 'win_seeEnterprise',
										title : '查看<' + enterpriseData.shortname + '>信息',
										layout : 'fit',
										iconCls : 'lookIcon',
										width : (screen.width - 180) * 0.7+ 160,
										constrainHeader : true,
										collapsible : true,
										autoScroll : true,
										height : 460,
										closable : true,
										resizable : false,
										modal : true,
										plain : true,
										border : false,
										items : [new enterpriseObj({enterpriseId:enterpriseData.id,enterprise:enterpriseData,person:personData,isReadOnly:true})]
									}).show();
						},
						failure : function(response, options) {
							var responseMsg = Ext.util.JSON.decode(response.responseText);
							if (response.status == -1) {
								Ext.ux.Toast.msg('错误原因', '请求超时！');
								return;
							}
							Ext.ux.Toast.msg('错误原因', responseMsg.data? responseMsg.data	: '服务器处理出错');
						},
						params : {
							id : enterpriseId
						}
					});
				}
			}
		},'-', {
			text : '新增',
			iconCls : 'btn-add',
			handler : function(btn, e) {
			    var random=rand(1000000);
	            var id="add_enterprise"+random;
	            var jStore_enterprise=gPanel_enterpriseSelect.getStore();
				var window_addEnterprise = new Ext.Window({
					title : '新增企业信息',
				    height : 460,
					constrainHeader : true,
					collapsible : true,
					frame : true,
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
					tbar : [new Ext.Button({
						text : '保存',
						tooltip : '保存企业基本信息',
						iconCls : 'submitIcon',
						hideMode : 'offsets',
						handler : function() {
							var vDates="";
							var panel_add=window_addEnterprise.get(0);
						  /*  var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
					        vDates=getGridDate(edGrid);
					        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					        gudonginfo_hidden.setValue(vDates);*/
					        formSavePersonObj(panel_add,window_addEnterprise,jStore_enterprise);
									
						}
					})],
					items : [new enterpriseObj({winId:id})]
				}).show();
			}
		}, '-', {
			text : '编辑',
			iconCls : 'btn-edit',
			handler : function(btn, e) {
				var selected = gPanel_enterpriseSelect.getSelectionModel().getSelected();
				var jStore_enterprise=gPanel_enterpriseSelect.getStore();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id');
					var isCheckCard=selected.get('isCheckCard');
					Ext.Ajax.request({
						url : __ctxPath	+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							var enterpriseData = obj.data.enterprise;
				            var personData = obj.data.person;
							var enterpriseName = enterpriseData.shortname;
							var enterName = enterpriseData.enterprisename;
							var random=rand(1000000);
	                        var id="update_enterprise"+random;
	                        if(isCheckCard){
								enterpriseData.isCardcodeReadOnly=true;
							}else{
								enterpriseData.isCardcodeReadOnly=false;
							}
							var window_update = new Ext.Window({
								id : 'window_update',
								title : '编辑企业<<' + enterName + '>>信息',
								layout : 'fit',
								height : 460,
								collapsible : true,
								width : (screen.width - 180) * 0.7 + 160,
								iconCls : 'upIcon',
								constrain : true,
								closable : true,
								resizable : true,
								plain : true,
								border : false,
								modal : true,
								buttonAlign : 'right',
								autoScroll : true,
								bodyStyle : 'overflowX:hidden',
								items : [new enterpriseObj({enterpriseId:enterpriseData.id,enterprise:enterpriseData,person:personData,winId:id})],
								tbar : [new Ext.Button({
											text : '更新',
											tooltip : '更新企业信息',
											iconCls : 'btn-refresh',
											hideMode : 'offsets',
											handler : function() {
												var vDates="";
							                    var panel_add=window_update.get(0);
							                    if(panel_add.getCmpByName('gudong_store')){
												    var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
											        vDates=getGridDate(edGrid);
							                    }
											        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					   								gudonginfo_hidden.setValue(vDates);
												    formSavePersonObj(panel_add,window_update,jStore_enterprise);
											}
										})],
								listeners : {/*
									'beforeclose' : function(panel) {
										var  fPanel_updateEnterprise=panel.get(0);
										if (fPanel_updateEnterprise != null) {
											if (fPanel_updateEnterprise
													.getForm().isDirty()) {
												Ext.Msg.confirm('操作提示',
														'数据被修改过,是否保存',
														function(btn) {
															if (btn == 'yes') {
																window_update.getTopToolbar().items.items[0].handler.call();
																		
															} else {
															    window_update.destroy();		
															}
														});
												return false;
											}
										}
									}
								*/}
							});
							window_update.show();
						},
						failure : function(result, action) {
							Ext.ux.Toast.msg('状态', '请求失败');
						},
						params : {
							id : id
						}
					});
				}
			}
		}, '-', new Ext.form.Label({
					text : '企业名称：'
				}), new Ext.form.TextField({
					id : 'enterprisenameRefer',
					width : 110
				}), {
			text : '查找',
			iconCls : 'btn-search'
		}],
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window_EnterpriseForSelect.destroy();
				myMask_EnterpriseSelect.hide();
			}
		}
	});
	Ext.getCmp('enterprisenameRefer').on('blur', function() {
				var value = Ext.get('enterprisenameRefer').dom.value;
				jStore_enterpriseForSelect.baseParams.enterprisename = value;
				jStore_enterpriseForSelect.load({
				params : {
								start : 0,
								limit : 15
							}
				});
			});
	var window_EnterpriseForSelect = new Ext.Window({
				title : '企业列表',
				border : false,
				width : (screen.width - 180) * 0.7,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [gPanel_enterpriseSelect],
				layout : 'fit',
				buttonAlign : 'center'
			});
	window_EnterpriseForSelect.show();
	jStore_enterpriseForSelect.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});

	var formSave = function(formPanelId, winObj, storeObj) {
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
					method : 'POST',
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					success : function(form, action) {
						Ext.ux.Toast.msg('状态', '保存成功!');
						storeObj.reload();
						winObj.destroy();
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('状态', '保存失败!可能数据没有填写完整');
					}
				})
	}

	var callbackFun = function(selected, funName) {
		enterpriseJsonObj = {
			id : selected.get('id'),
            surplusMoney : selected.get('surplusMoney'),
			shortname : selected.get('shortname'),
			hangyetype : selected.get('hangyetype'),
			ownership : selected.get('ownership'),
			registermoney : selected.get('registermoney'),
			enterprisename : selected.get('enterprisename'),
			controlpersonid : selected.get('controlpersonid'),
			legalpersonid : selected.get('legalpersonid'),
			linkmampersonid : selected.get('linkmampersonid'),
			area : selected.get('area'),
			legalperson : selected.get('legalperson'),
			linkperson : selected.get('linkperson'),
			controlperson : selected.get('controlperson'),
			linkpersonjob : selected.get('linkpersonjob'),
			linkpersonmobile : selected.get('linkpersonmobile'),
			linkpersontel : selected.get('linkpersontel'),
			hangyetypevalue : selected.get('hangyeName'),
			organizecode : selected.get('organizecode'),
			cciaa : selected.get('cciaa'),
			telephone : selected.get('telephone'),
			postcoding : selected.get('postcoding'),
			hangyetype : selected.get('hangyeType'),
			address : selected.get('address'),
			registerstartdate : selected.get('registerstartdate'),
			documentType:selected.get('documentType'),
			taxnum:selected.get('taxnum'),
			isCheckCard : selected.get('isCheckCard')
		}
		funName(enterpriseJsonObj);
	}

	var selectLinkman = function(obj) {
		Ext.getCmp('linkman').setValue(obj.name);
		Ext.getCmp('linkmanId').setValue(obj.id);
	};
	var selectLegalperson = function(obj) {
		Ext.getCmp('legalpersonName').setValue(obj.name);
		Ext.getCmp('legalpersonId').setValue(obj.id);
	}
	var selectControlperson = function(obj) {
		Ext.getCmp('controlpersonName').setValue(obj.name);
		Ext.getCmp('controlpersonId').setValue(obj.id);
	}
	var upselectLegalperson = function(obj) {
		Ext.getCmp('uplegalpersonName').setValue(obj.name);
		Ext.getCmp('uplegalpersonId').setValue(obj.id);
	}
	var upselectControlperson = function(obj) {
		Ext.getCmp('upcontrolpersonName').setValue(obj.name);
		Ext.getCmp('upcontrolpersonId').setValue(obj.id);
	}
	var getEnterObjArray = function(objArray) {
		Ext.getCmp('canentergslname')
				.setValue(objArray[(objArray.length) - 1].text + "_"
						+ objArray[(objArray.length) - 2].text);
		Ext.getCmp('canentergslnameid')
				.setValue(objArray[(objArray.length) - 1].id + ","
						+ objArray[(objArray.length) - 2].id);
	}
	var getEnterAreaObjArray = function(objArray) {
		Ext.getCmp('canentermanagecity').setValue(objArray[(objArray.length)
				- 1].text
				+ "_"
				+ objArray[(objArray.length) - 2].text
				+ "_"
				+ objArray[0].text);
		Ext.getCmp('canentermanagecityid').setValue(objArray[(objArray.length)
				- 1].id
				+ ","
				+ objArray[(objArray.length) - 2].id
				+ ","
				+ objArray[0].id);
	}
	var getEnterAreaObjArrayUp = function(objArray) {
		Ext.getCmp('canupentermanagecity').setValue(objArray[(objArray.length)
				- 1].text
				+ "_"
				+ objArray[(objArray.length) - 2].text
				+ "_"
				+ objArray[0].text);
		Ext.getCmp('canupentermanagecityid')
				.setValue(objArray[(objArray.length) - 1].id + ","
						+ objArray[(objArray.length) - 2].id + ","
						+ objArray[0].id);
	}
	var getEnterObjArrayUp = function(objArray) {
		Ext.getCmp('canupentergslname')
				.setValue(objArray[(objArray.length) - 1].text + "_"
						+ objArray[(objArray.length) - 2].text);
		Ext.getCmp('canupentergslnameid').setValue(objArray[(objArray.length)
				- 1].id
				+ "," + objArray[(objArray.length) - 2].id);
	}
}