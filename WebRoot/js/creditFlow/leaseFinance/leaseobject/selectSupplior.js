function selectSupplior(funName) {
	var jStore_suppliorSelect = new Ext.data.JsonStore({
				url : __ctxPath	+ '/creditFlow/leaseFinance/project/listFlObjectSupplior.do',//?isGrantedSeeAllSuppliors='+isGranted('_seeAll_qykh'),   //暂时没加 权限... by gao
				totalProperty : 'totalCounts',
				root : 'result',
				fields : [{
							name : 'id'
						}, {
							name : 'legalPersonName'
						}, {
							name : 'companyPhoneNum'
						}, {
							name : 'Name'
						}, {
							name : 'connectorName'
						}, {
							name : 'connectorPhoneNum'
						}, {
							name : 'connectorPosition'
						}, {
							name : 'companyFax'
						}, {
							name : 'companyAddress'
						}, {
							name : 'companyComment'
						}],
				// 服务器端排序 by chencc
				// sortInfo : {field: "enterprisename", direction: "DESC"}
				remoteSort : true
			});
	var anchor = '100%';
	var pageSize = 15;
	var listWindowWidth = 650;
	var listWindowHeight = 465;
	var detailWindowWidth = 580;
	var detailWindowHeight = 370;
	var defaultLabelWidth = 110;// 默认标签的宽度
	var defaultTextfieldWidth = 135;// 默认文本输入域宽度
	var enterpriseJsonObj;
	jStore_suppliorSelect.addListener('load', function() {
				gPanel_suppliorSelect.getSelectionModel().selectFirstRow();
			}, this);
	jStore_suppliorSelect.addListener('loadexception', function(proxy,
					options, response, err) {
				Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
			}, this);
	var cModel_supplior = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				header : "供货方名称",
				width : 105,
				sortable : true,
				dataIndex : 'Name'
			},  {
				id : 'legalPersonName',
				header : "法人代表姓名",
				width : 190,
				sortable : true,
				dataIndex : 'legalPersonName'
			}, {
				header : "公司电话",
				width : 100,
				sortable : true,
				dataIndex : 'companyPhoneNum'
			},{
				header : "联系人姓名",
				width : 105,
				sortable : true,
				dataIndex : 'connectorName'
			}, {
				header : "联系人电话",
				width : 105,
				sortable : true,
				dataIndex : 'connectorPhoneNum'
			}, {
				header : "联系人职位",
				width : 105,
				sortable : true,
				dataIndex : 'connectorPosition'
//				renderer : seeRegistermoney
			}, {
				header : "公司传真",
				width : 90,
				sortable : true,
				dataIndex : 'companyFax'
			} ,{
				header : "公司地址",
				width : 90,
				sortable : true,
				dataIndex : 'companyAddress'
			},{
				header : "备注",
				width : 90,
				sortable : true,
				dataIndex : 'companyComment'
			}]);
	var pagingBar_Supplior = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : jStore_suppliorSelect,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	var myMask_SuppliorSelect = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
			});

	var gPanel_suppliorSelect = new Ext.grid.GridPanel({
		id : 'gPanel_suppliorSelect',
		store : jStore_suppliorSelect,
		colModel : cModel_supplior,
		autoExpandColumn : '', // fuck you 返回json字符串字段无前缀  就为''
		//selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : true,
		bbar : pagingBar_Supplior,
		tbar : [/*{
			text : '查看',
			iconCls : 'btn-detail',
			handler : function(btn, e) {
				var selected = gPanel_suppliorSelect.getSelectionModel().getSelected();
				var len = selected.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
				} else {
					enterpriseId = selected.get('id');
					Ext.Ajax.request({
						url : __ctxPath+ '/creditFlow/leaseFinance/project/getFlObjectSupplior.do',
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
										id : 'win_seeSupplior',
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
	            var id="add_supplior"+random;
	            var jStore_enterprise=gPanel_suppliorSelect.getStore();
				var window_addSupplior = new Ext.Window({
					title : '新增供货单位',
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
						tooltip : '保存供货单位信息',
						iconCls : 'submitIcon',
						hideMode : 'offsets',
						handler : function() {
							var vDates="";
							var panel_add=window_addSupplior.get(0);
						    var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
					        vDates=getGridDate(edGrid);
					        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					        gudonginfo_hidden.setValue(vDates);
					        formSavePersonObj(panel_add,window_addSupplior,jStore_enterprise);
									
						}
					})],
					items : [new enterpriseObj({winId:id})]
				}).show();
			}
		}, '-', {
			text : '编辑',
			iconCls : 'btn-edit',
			handler : function(btn, e) {
				var selected = gPanel_suppliorSelect.getSelectionModel().getSelected();
				var jStore_enterprise=gPanel_suppliorSelect.getStore();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id');
					Ext.Ajax.request({
						url : __ctxPath	+ '/credit/customer/enterprise/loadSuppliorInfo.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							var enterpriseData = obj.data.enterprise;
				            var personData = obj.data.person;
							var enterpriseName = enterpriseData.shortname;
							var enterName = enterpriseData.shortname;
							var random=rand(1000000);
	                        var id="update_enterprise"+random;
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
											iconCls : 'submitIcon',
											hideMode : 'offsets',
											handler : function() {
												var vDates="";
							                    var panel_add=window_update.get(0);
												    var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
											        vDates=getGridDate(edGrid);
											        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					   								gudonginfo_hidden.setValue(vDates);
												    formSavePersonObj(panel_add,window_update,jStore_enterprise);
											}
										})],
								listeners : {
									'beforeclose' : function(panel) {
										var  fPanel_updateSupplior=panel.get(0);
										if (fPanel_updateSupplior != null) {
											if (fPanel_updateSupplior
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
								}
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
		}, '-', */new Ext.form.Label({
					text : '供货单位名称：'
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
				window_SuppliorForSelect.destroy();
				myMask_SuppliorSelect.hide();
			}
		}
	});
	Ext.getCmp('enterprisenameRefer').on('blur', function() {
				var value = Ext.get('enterprisenameRefer').dom.value;
				jStore_suppliorSelect.baseParams.Name = value;
				jStore_suppliorSelect.load({
				params : {
								start : 0,
								limit : 15
							}
				});
			});
	var window_SuppliorForSelect = new Ext.Window({
				title : '供货单位列表',
				border : false,
				width : (screen.width - 180) * 0.7,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [gPanel_suppliorSelect],//
				layout : 'fit',
				buttonAlign : 'center'
			});
	window_SuppliorForSelect.show();
	jStore_suppliorSelect.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});

/*	var formSave = function(formPanelId, winObj, storeObj) {
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
	}*/
	//回填函数
	var callbackFun = function(selected, funName) {
		enterpriseJsonObj = {
			id : selected.get('id'),
			legalPersonName : selected.get('legalPersonName'),
			companyPhoneNum : selected.get('companyPhoneNum'),
			Name : selected.get('Name'),
			connectorName : selected.get('connectorName'),
			connectorPhoneNum : selected.get('connectorPhoneNum'),
			connectorPosition : selected.get('connectorPosition'),
			companyFax : selected.get('companyFax'),
			companyAddress : selected.get('companyAddress'),
			companyComment : selected.get('companyComment')
		}
		funName(enterpriseJsonObj);
	}
/*
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
	}*/
}