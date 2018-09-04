/**
 * 个人客户列表
 * 
 * @class PersonView
 * @extends Ext.Panel
 */

PersonView = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		PersonView.superclass.constructor.call(this, {
					id : 'PersonView'+this.singleType,
					title : '客户档案管理',
					height : 450,
					autoScroll : true,
					layout : 'border',
					iconCls : 'btn-tree-team23',
					items : [this.searchPanel, this.centerPanel]
				});
	},
	initUIComponents : function() {
		var isShow = false;

		if (RoleType == "control") {
			isShow = true;

		}
		this.pageSize = 25;

		this.store = new Ext.data.JsonStore({
					url : __ctxPath
							+ '/creditFlow/customer/person/perQueryListPerson.do?isAll='
							+ isGranted('_detail_sygrkh'),
					totalProperty : 'totalProperty',
					root : 'topics',
					remoteSort : true,
					fields : [{
								name : 'id'
							}, {
								name : 'name'
							}, {
								name : 'sexvalue'
							}, {
								name : 'jobvalue'
							}, {
								name : 'cardtypevalue'
							}, {
								name : 'cardnumber'
							}, {
								name : 'cellphone'
							}, {
								name : 'birthday'
							}, {
								name : 'nationalityvalue'
							}, {
								name : 'peopletypevalue'
							}, {
								name : 'dgreevalue'
							}, {
								name : 'techpersonnel'
							}, {
								name : 'age'
							}, {
								name : 'marryvalue'
							}, {
								name : 'telphone'
							}, {
								name : 'englishname'
							}, {
								name : 'companyName'
							}, {
								name : 'shopId'
							}, {
								name : 'shopName'
							}, {
								name : 'isCheckCard'
							},{name:'selfemail'}]
				});
		var person_store = this.store;
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});

		var importButton = new Ext.Button({
			text : '导入个人数据',
			iconCls : 'addIcon',
			tooltip : '导入个人原始记录',
			scope : this,
			disabled : false,
			handler : function() {
				new Ext.Window({
					id : 'importEnterpriseWin',
					title : '导入数据',
					layout : 'fit',
					width : (screen.width - 180) * 0.6 - 150,
					height : 130,
					closable : true,
					resizable : true,
					plain : false,
					bodyBorder : false,
					border : false,
					modal : true,
					constrainHeader : true,
					bodyStyle : 'overflowX:hidden',
					buttonAlign : 'right',
					items : [new Ext.form.FormPanel({
						id : 'importEnterpriseFrom',
						monitorValid : true,
						labelAlign : 'right',
						url : __ctxPath
								+ '/creditFlow/customer/person/batchImportPBatchImportDatabase.do',
						buttonAlign : 'center',
						enctype : 'multipart/form-data',
						fileUpload : true,
						layout : 'column',
						frame : true,
						items : [{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 90,
									defaults : {
										anchor : '95%'
									},
									items : [{}, {
												xtype : 'textfield',
												fieldLabel : '请选择文件',
												allowBlank : false,
												blankText : '文件不能为空',
												inputType : 'file',
												id : 'fileBatch',
												name : 'fileBatch'
											}]
								}],
						buttons : [{
							text : '导入',
							iconCls : 'uploadIcon',
							formBind : true,
							handler : function() {
								Ext.getCmp('importEnterpriseFrom').getForm()
										.submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function(form, action) {
												Ext.ux.Toast.msg('状态', '导入成功!');
												Ext
														.getCmp('importEnterpriseWin')
														.destroy();
												person_store.reload();

											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态', '导入失败!');
											}
										});
							}
						}]
					})]
				}).show();
			}
		})
		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
			height : 75,
			// labelWidth : 55,
			region : "north",
			bodyStyle : 'padding:7px 0px 7px 10px',
			border : false,
			width : '100%',
			monitorValid : true,
			layout : 'column',
			defaults : {
				layout : 'form',
				border : false,
				bodyStyle : 'padding:5px 0px 0px 20px'
			},
			items : [isShow ? {
				columnWidth : 0.2,
				labelWidth : 65,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : "combo",
					anchor : "100%",
					fieldLabel : '所属分公司',
					hiddenName : "companyId",
					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath
										+ '/system/getControlNameOrganization.do',
								fields : ['companyId', 'companyName']
							})
				}]
			}
					: {
						columnWidth : 0.01
					}, {
				columnWidth : isShow ? 0.15 : 0.2,
				labelWidth : 40,
				// bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'name',
							anchor : '100%'
						}]
			}, /*
				 * { columnWidth : 0.14, labelWidth : 40, items : [{ xtype :
				 * 'csRemoteCombo', fieldLabel : '职务', hiddenName : 'job', dicId :
				 * positionDicId, anchor : '100%' }] },
				 */{
				columnWidth : isShow ? 0.12 : 0.14,
				labelWidth : 40,
				items : [{
							xtype : 'dickeycombo',
							fieldLabel : '性别',
							hiddenName : 'sex',
							nodeKey : 'sex_key',
							anchor : '100%'
						}]
			}, {
				columnWidth : isShow ? 0.15 : 0.17,
				labelWidth : 55,
				items : [{
							xtype : "dickeycombo",
							nodeKey : 'card_type_key',
							hiddenName : "cardtype",
							fieldLabel : "证件类型",
							anchor : '100%',
							editable : true,
							listeners : {
								scope : this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
												combox.setValue(combox
														.getValue());
												combox.clearInvalid();
											})
								}
							}
						}]

			}, {
				columnWidth : isShow ? 0.2 : 0.25,
				labelWidth : 55,
				items : [{
							xtype : 'textfield',
							fieldLabel : '证件号码',
							name : 'cardnumber',
							anchor : '100%'
						}]

			}, {
				columnWidth : 0.2,
				layout : 'form',
				labelWidth : 80,
				labelAlign : 'right',
				border : false,
				items : [{
					fieldLabel : '所属门店',
					xtype : "combo",
					anchor : "100%",
					hiddenName : "shopId",
					displayField : 'orgUserName',
					valueField : 'orgUserId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath
										+ '/system/getOrgUserNameOrganization.do',
								fields : ['orgUserId', 'orgUserName']
							})
				}]
			}, /*
				 * { columnWidth : 0.14, labelWidth : 55, items : [{ xtype :
				 * 'textfield', fieldLabel : '手机号码', name : 'cellphone', anchor :
				 * '100%' }] },
				 *//*
				 * { columnWidth : 0.14, labelWidth : 55, items : [{ xtype :
				 * "dickeycombo", hiddenName : "customerLevel", nodeKey :
				 * 'customerLevel', // xx代表分类名称 fieldLabel : "客户级别",
				 * anchor:'100%', listeners : { afterrender : function(combox) {
				 * var st = combox.getStore(); st.on("load", function() {
				 * 
				 * combox.setValue(combox.getValue()); combox.clearInvalid(); }) } } }] },
				 */{
				columnWidth : 0.07,
				items : [{
							id : 'searchButton',
							xtype : 'button',
							text : '查询',
							tooltip : '根据查询条件过滤',
							iconCls : 'btn-search',
							width : 60,
							formBind : true,
							scope : this,
							handler : function() {
								this.searchByCondition();
							}
						}]
			}, {

				columnWidth : 0.07,
				items : [{
							xtype : 'button',
							text : '重置',
							width : 60,
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]

			}]
		}); // 查询面板结束

		// this.store.setDefaultSort('name');
		// 加载数据
		this.store.load({
					scope : this,
					params : {
						start : 0,
						limit : this.pageSize,
						isAll : isGranted('_detail_sygrkh')
					}
				});
		var personStore = this.store;
		var tbar = new Ext.Toolbar({
			items : [{
				text : '建档',
				iconCls : 'btn-add',
				hidden : isGranted('_create_grkh') ? false : true,
				scope : this,
				handler : function() {
					var randomId = rand(100000);
					var id = "add_person" + randomId;
					var url = __ctxPath
							+ '/creditFlow/customer/person/addInfoPerson.do';
					var window_add = new Ext.Window({
								title : '新增个人客户详细信息',
								height : 460,
								constrainHeader : true,
								collapsible : true,
								frame : true,
								iconCls : 'btn-add',
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
											personData : null,
											url : url,
											id : id
										})],
								tbar : [new Ext.Button({
											text : '保存',
											tooltip : '保存基本信息',
											iconCls : 'submitIcon',
											hideMode : 'offsets',
											handler : function() {
												var vDates = "";
												var panel_add = window_add
														.get(0);
												formSavePersonObj(panel_add,
														window_add, personStore);
											}
										})],
								listeners : {
									'beforeclose' : function(panel) {
										window_add.destroy();
									}
								}
							});
					window_add.show();

				}
			}, new Ext.Toolbar.Separator({
						hidden : isGranted('_create_grkh') ? false : true
			}), {
				text : '查看',
				iconCls : 'btn-readdocument',
				scope : this,
				hidden : isGranted('_See_grkh') ? false : true,
				handler : function() {
					var rows = this.centerPanel.getSelectionModel()
							.getSelections();
					if (rows.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择记录!');
						return;
					} else if (rows.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
						return;
					} else {
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/customer/person/seeInfoPerson.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								obj = Ext.util.JSON
										.decode(response.responseText);
								var personData = obj.data;
								var randomId = rand(100000);
								var id = "see_person" + randomId;
								var anchor = '100%';
								var window_see = new Ext.Window({
											title : '查看个人客户详细信息',
											layout : 'fit',
											width : (screen.width - 180) * 0.7
													+ 160,
											maximizable : true,
											height : 460,
											closable : true,
											modal : true,
											plain : true,
											border : false,
											items : [new personObj({
														url : null,
														id : id,
														personData : personData,
														isReadOnly : true
													})],
											listeners : {
												'beforeclose' : function(panel) {
													window_see.destroy();
												}
											}
										});
								window_see.show();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : {
								id : rows[0].data.id
							}
						});
					}

				}
			}, new Ext.Toolbar.Separator({
						hidden : isGranted('_See_grkh') ? false : true
					}), {
				text : '编辑',
				iconCls : 'btn-edit',
				scope : this,
				hidden : isGranted('_edit_grkh') ? false : true,
				handler : function() {
					var rows = this.centerPanel.getSelectionModel()
							.getSelections();
					if (rows.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择记录!');
						return;
					} else if (rows.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
						return;
					} else {
						var isCheckCard=rows[0].data.isCheckCard
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/customer/person/seeInfoPerson.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								obj = Ext.util.JSON.decode(response.responseText);
								var personData = obj.data;
								if(isCheckCard){
									personData.isCardcodeReadOnly=true;
								}else{
									personData.isCardcodeReadOnly=false;
								}
								var randomId = rand(100000);
								var id = "update_person" + randomId;
								var url = __ctxPath+ '/creditFlow/customer/person/updateInfoPerson.do';
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
												url : url,
												id : id
											})],
									tbar : [new Ext.Button({
										text : '更新',
										tooltip : '更新基本信息',
										iconCls : 'btn-refresh',
										hideMode : 'offsets',
										handler : function() {
											var vDates = "";
											var panel_add = window_update
													.get(0);
											formSavePersonObj(panel_add,
													window_update, personStore);
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
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : {
								id : rows[0].data.id
							}
						});
					}

				}
			}, new Ext.Toolbar.Separator({
						hidden : isGranted('_edit_grkh') ? false : true
					}), {
				text : '删除',
				iconCls : 'btn-del',
				hidden : isGranted('_remove_grkh') ? false : true,
				scope : this,
				handler : function() {
					var selected = this.centerPanel.getSelectionModel()
							.getSelections();
					var thisPanel = this.centerPanel;
					var len = selected.length;
					var list = "";
					for (var j = 0; j < len; j++) {
						if (j == (len - 1)) {
							list += selected[j].id;
						} else
							list += selected[j].id + ",";
					}
					if (0 == len) {
						Ext.MessageBox.alert('状态', '请选择一条记录!');
					} else {
						Ext.MessageBox.confirm('确认删除', '您确认要删除所选记录吗？',
								function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/customer/person/deletePerson.do',
											method : 'POST',
											scope : this,
											success : function(response,
													request) {
												var object = Ext.util.JSON
														.decode(response.responseText)
												if (object.flag == 'false') {
													Ext.ux.Toast.msg('状态',
															object.msg)
												} else {
													Ext.ux.Toast.msg('状态',
															'删除成功!');
												}
												thisPanel.getStore().reload();
											},
											failure : function(result, action) {
												Ext.ux.Toast.msg('状态', '删除失败!');
											},
											params : {
												listId : list
											}
										});
									}
								});
					}

				}
			},
				  new Ext.Toolbar.Separator({ hidden :
				  (isGranted('_remove_grkh') ? false : true) ||
				  (isGranted('_detail_ywwl') ? false : true) }),{ iconCls :
				  'btn-detail', text : '业务往来', xtype : 'button', scope:this,
				  hidden : isGranted('_detail_ywwl') ? false : true, handler :
				  function() { var ss =
				 this.centerPanel.getSelectionModel().getSelections(); var len =
				  ss.length; if (len > 1) { Ext.ux.Toast.msg('状态', '只能选择一条记录'); }
				  else if (0 == len) { Ext.ux.Toast.msg('状态', '请选择一条记录'); }
				  else { var cardNum = ss[0].data.cardnumber; var personIdValue
				  =ss[0].data.id; new
				  PersonAll({customerType:'person_customer',customerId
				  :personIdValue,personType:603,shareequityType:'person_shareequity'}).show() } }
				   },
				 new Ext.Toolbar.Separator({
						hidden : (isGranted('_detail_ywwl') ? false : true)
								|| (isGranted('_export_grkh') ? false : true)
					}), {
				text : '导出到Excel',
				iconCls : 'btn-xls',
				scope : this,
				hidden : isGranted('_export_grkh') ? false : true,
				handler : function() {
					var companyId = (isShow==true?this.getCmpByName("companyId").getValue():null);//分公司id
					var sex=this.getCmpByName("sex").getValue();//性别
					var name = this.getCmpByName("name").getValue();//姓名
					var cardtype = this.getCmpByName("cardtype").getValue();//证件类型
					var cardnumber = this.getCmpByName("cardnumber").getValue();//证件号码
					var shopId = this.getCmpByName("shopId").getValue();//门店id
					window.open(__ctxPath+ '/creditFlow/customer/person/outputExcelPerson.do?name='+name+'&sex='+sex
								+'&cardtype='+cardtype+"&cardnumber="+cardnumber+"&shopId="+shopId+'&isAll='+isGranted('_detail_sygrkh'),'_blank');
				}
			},{
				iconCls : 'btn-add',
				text : '加入黑名单',
				xtype : 'button',
				hidden : isGranted('_addBlack_grkh') ? false : true,
				scope : this,
				ptype : "1",
				handler : this.addBlackList
			}/*, {
				scope : this,
				handler : this.addBlackList
			}, {

				iconCls : 'btn-add',
				text : '开通p2p账户',
				xtype : 'button',
				hidden : isGranted('_add_p2p_persion_user') ? false : true,
				scope : this,
				ptype : "1",
				handler : this.addP2pUser
			},{

				iconCls : 'btn-add',
				text : '绑定p2p账户',
				xtype : 'button',
				hidden : isGranted('_add_p2p_persion_user') ? false : true,
				scope : this,
				ptype : "2",
				handler : this.addP2pUser
			}*/]
		});

		this.centerPanel = new HT.GridPanel({
					region : 'center',
					// title:'个人客户信息',
					tbar : tbar,
					clicksToEdit : 1,
					store : this.store,
					loadMask : this.myMask,
					columns : [{
								header : "所属分公司",
								width : 160,
								sortable : true,
								hidden : RoleType == "control" ? false : true,
								dataIndex : 'companyName'
							}, {
								header : "所属门店",
								width : 100,
								sortable : true,
								dataIndex : 'shopName'
							}, {
								header : "姓名",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'name'
							}, {
								header : "性别",
								width : 50,
								align:'center',
								sortable : true,
								dataIndex : 'sexvalue'
							},/*
								 * { header : "年龄", width : 50, sortable : true,
								 * dataIndex : 'age' },
								 *//*
								 * { header : "职务", width : 100, sortable :
								 * true, dataIndex : 'jobvalue' }, { header :
								 * "婚姻状况", width : 100, sortable : true,
								 * dataIndex : 'marryvalue' },
								 */{
								header : "证件类型",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'cardtypevalue'
							}, {
								header : "证件号码",
								width : 120,
								align:'center',
								sortable : true,
								dataIndex : 'cardnumber'
							}, {
								header : "手机号码",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'cellphone'
							}, {
								header : "家庭电话",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'telphone'
							}/*
								 * , { header : "出生日期", width : 110, sortable :
								 * true, dataIndex : 'birthday' }
								 */],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					},
					height : 450,
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					listeners : {
						afteredit : function(e) {
						}
					}
				});
	},// end of initUIComponents

	// 查询
	searchByCondition : function() {
		this.store.baseParams.name = this.searchPanel.getForm()
				.findField('name').getValue();
		this.store.baseParams.sex = this.searchPanel.getForm().findField('sex')
				.getValue();
		// 修改查询按钮 添加了证件类型 证件号码 隐藏了职务 客户级别 电话号码
		this.store.baseParams.cardtype = this.searchPanel.getForm()
				.findField('cardtype').getValue();
		this.store.baseParams.cardnumber = this.searchPanel.getForm()
				.findField('cardnumber').getValue();
		/*
		 * this.store.baseParams.job =
		 * this.searchPanel.getForm().findField('job').getValue();
		 * this.store.baseParams.cellphone =
		 * this.searchPanel.getForm().findField('cellphone').getValue();
		 * this.store.baseParams.customerLevel =
		 * this.searchPanel.getForm().findField('customerLevel').getValue();
		 */
		if (null != this.searchPanel.getForm().findField('companyId')) {
			this.store.baseParams.companyId = this.searchPanel.getForm()
					.findField('companyId').getValue();
		}
		this.store.baseParams.shopId = this.searchPanel.getForm()
				.findField('shopId').getValue();
		this.store.load({
					scope : this,
					params : {
						start : 0,
						limit : this.pageSize
					}
				});
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 添加p2p账户
	addP2pUser : function(obj, e) {
		var selections = this.centerPanel.getSelectionModel().getSelections();
		var grid = this.centerPanel;
		var bStr = obj.ptype;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var personId = selections[0].data.id;
		var cellphone = selections[0].data.cellphone;
		var selfemail = selections[0].data.selfemail;
		var cardnumber = selections[0].data.cardnumber;
		new BpCustRelationForm({
			type : "p_loan",
			pType : '1',
			bStr : bStr,
			userId : personId,
			cellphone:cellphone,
			selfemail:selfemail,
			cardnumber:cardnumber
		}).show();
	},
	addBlackList : function() {
		var selections = this.centerPanel.getSelectionModel().getSelections();
		var grid = this.centerPanel;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var personId = selections[0].data.id;
		var fp = new Ext.FormPanel({
					frame : true,
					labelAlign : 'right',
					bodyStyle : 'padding:5px 5px 5px 5px',
					labelWidth : 60,
					border : false,
					url : __ctxPath
							+ '/creditFlow/customer/person/addBlackPerson.do?id='
							+ personId,
					items : [{
								xtype : 'textarea',
								fieldLabel : '原因说明',
								allowBlank : false,
								name : 'blackReason',
								anchor : '100%'
							}]
				})
		var window = new Ext.Window({
					title : '加入黑名单',
					width : 400,
					height : 150,
					modal : true,
					items : fp,
					buttonAlign : 'center',
					buttons : [{
								text : '提交',
								iconCls : 'btn-save',
								handler : function() {
									fp.getForm().submit({
												waitMsg : '正在提交...',
												method : 'post',
												success : function(form, action) {
													Ext.ux.Toast.msg('状态',
															'添加成功');
													window.close();
													grid.getStore().reload()
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态',
															'添加失败');
												}
											})
								}
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								handler : function() {
									window.destroy()
								}
							}]
				})
		window.show()
	}
});