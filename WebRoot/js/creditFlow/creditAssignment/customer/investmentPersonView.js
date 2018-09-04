/**
 * 个人客户列表
 * 
 * @class PersonView
 * @extends Ext.Panel
 */
investmentPersonView = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		investmentPersonView.superclass.constructor.call(this, {
					id : 'investmentPersonView',
					height : 450,
					autoScroll : true,
					layout : 'border',
					title : typeof(this.isHiddenSearchPanel)=="undefined"?'投资客户管理':null,
					iconCls : 'btn-tree-team23',
					items : typeof(this.isHiddenSearchPanel)=="undefined"?[this.searchPanel, this.centerPanel]:[this.centerPanel]
				});
	},
	initUIComponents : function() {
		var isShow = false;
		var rightValue =  isGranted('_investmentPerson_see_All');
		var isShop = isGranted('_investmentPerson_see_shop');
		if (RoleType == "control") {
			isShow = true;

		}
		this.pageSize = 25;

		this.store = new Ext.data.JsonStore({
			url : __ctxPath
					+ '/creditFlow/creditAssignment/customer/queryInvestmentPersonCsInvestmentperson.do?isAll='
					+ rightValue+"&isShop="+isShop,
			totalProperty : 'totalProperty',
			root : 'topics',
			remoteSort : true,
			fields : [{
						name : 'investId'
					}, {
						name : 'investName'
					}, {
						name : 'sexvalue'
					}, {
						name : 'cardtypevalue'
					}, {
						name : 'cardnumber'
					}, {
						name : 'cellphone'
					}, {
						name : 'shopId'
					}, {
						name : 'companyId'
					}, {
						name : 'shopName'
					},  {
						name : 'orgName'
					}, {
						name : 'accountNumber'
					},  {
						name : 'contractStatus'
					}, {
						name : 'changeCardStatus'
					}, {
						name : 'birthDay'
					},{
						name : 'selfemail'
					}]
		});
		var person_store = this.store;
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});

		/*
		 * var importButton = new Ext.Button({ text : '导入个人数据', iconCls :
		 * 'addIcon', tooltip : '导入个人原始记录', scope : this, disabled : false,
		 * handler : function() { new Ext.Window({ id : 'importEnterpriseWin',
		 * title : '导入数据', layout : 'fit', width : (screen.width - 180) * 0.6 -
		 * 150, height : 130, closable : true, resizable : true, plain : false,
		 * bodyBorder : false, border : false, modal : true, constrainHeader :
		 * true, bodyStyle : 'overflowX:hidden', buttonAlign : 'right', items :
		 * [new Ext.form.FormPanel({ id : 'importEnterpriseFrom', monitorValid :
		 * true, labelAlign : 'right', url : __ctxPath
		 * +'/credit/customer/person/batchImportPerson.do', buttonAlign :
		 * 'center', enctype : 'multipart/form-data', fileUpload : true, layout :
		 * 'column', frame : true, items : [{ columnWidth : 1, layout : 'form',
		 * labelWidth : 90, defaults : { anchor : '95%' }, items : [{}, { xtype :
		 * 'textfield', fieldLabel : '请选择文件', allowBlank : false, blankText :
		 * '文件不能为空', inputType : 'file', id : 'fileBatch', name : 'fileBatch' }]
		 * }], buttons : [{ text : '导入', iconCls : 'uploadIcon', formBind :
		 * true, handler : function() {
		 * Ext.getCmp('importEnterpriseFrom').getForm() .submit({ method :
		 * 'POST', waitTitle : '连接', waitMsg : '消息发送中...', success :
		 * function(form, action) { Ext.ux.Toast.msg('状态', '导入成功!');
		 * Ext.getCmp('importEnterpriseWin').destroy(); person_store.reload(); },
		 * failure : function(form, action) { Ext.ux.Toast.msg('状态', '导入失败!'); }
		 * }); } }] })] }).show(); } })
		 */
		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
			height : 50,
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
				items : [{
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'investName',
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
				columnWidth : 0.1,
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

				columnWidth : 0.1,
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
		var hbar = new Ext.Toolbar({
			items:[{
				text : '确认选择',
				iconCls : 'btn-select',
				scope : this,
				handler : function() {
					var selects = this.centerPanel.getSelectionModel().getSelections();
					
					if(selects==null){
						Ext.ux.Toast.msg('提示', '请选择一条记录');
					}else if(selects.length==0){
						Ext.ux.Toast.msg('提示', '请选择一条记录');	
					}else if(selects.length>1){
						Ext.ux.Toast.msg('提示', '只能选择一条记录');
					}else{
						this.callbackFun(selects[0]);
					}
				}
			}]
		});
		var tbar = new Ext.Toolbar({
			items : [{
				text : '增加',
				iconCls : 'btn-add',
				hidden : isGranted('_create_investP') ? false : true,
				scope : this,
				handler : function() {
					var randomId = rand(100000);
					var id = "add_person" + randomId;
					var url = __ctxPath
							+ '/creditFlow/creditAssignment/customer/addPersonCsInvestmentperson.do';
					var window_add = new Ext.Window({
								title : '新增投资客户详细信息',
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
								items : [new investmentObj({
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
														
												formSaveinvestPersonObj(
														panel_add, window_add,
														personStore);
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
				hidden : isGranted('_create_investP') ? false : true
					}), {
				text : '查看',
				iconCls : 'btn-readdocument',
				hidden : isGranted('_create_grkh') ? false : true,
				scope : this,
				hidden: !isGranted('_See_investP')?true:false,
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
									+ '/creditFlow/creditAssignment/customer/seePersonCsInvestmentperson.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								var obj = Ext.util.JSON
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
											items : [new investmentObj({
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
								investId : rows[0].data.investId
							}
						});
					}
				}
			}, new Ext.Toolbar.Separator({
				hidden: !isGranted('_See_investP')?true:false
					}), {
				text : '编辑',
				iconCls : 'btn-edit',
				hidden : isGranted('_edit_investP') ? false : true,
				scope : this,
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
									+ '/creditFlow/creditAssignment/customer/seePersonCsInvestmentperson.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								obj = Ext.util.JSON
										.decode(response.responseText);
								var personData = obj.data;
								var randomId = rand(100000);
								var id = "update_person" + randomId;
								var url = __ctxPath
										+ '/creditFlow/creditAssignment/customer/updatePersonCsInvestmentperson.do';
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
									items : [new investmentObj({
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
											formSaveinvestPersonObj(panel_add,
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
								investId : rows[0].data.investId
							}
						});
					}
				}
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_edit_investP') ? false : true
					}), {
				text : '删除',
				iconCls : 'btn-del',
				hidden : isGranted('_remove_investP') ? false : true,
				scope : this,
				handler : function() {
									  var selected =this.centerPanel.getSelectionModel().getSelections();
									  var thisPanel = this.centerPanel; 
									  var len = selected.length; var list = "";
									  for (var j = 0; j < len; j++) {
									  	if (j == (len - 1)) { 
									  		list += selected[j].id; 
									  	}else {
									  		list += selected[j].id + ",";}
									  }
									  if (0 == len) {
									 	 Ext.MessageBox.alert('状态','请选择一条记录!'); 
									  } else {
									  	 Ext.MessageBox.confirm('确认删除','是否确认删除 选中的<font color=red>' + len + '</font>条记录',
									  		function(btn) { if (btn == 'yes') {
									  			Ext.Ajax.request({ url :__ctxPath +'/investment/deletePersonCsInvestmentPerson.do',
									  			method : 'POST', scope : this,
									  			success : function(response,request) {
													  var object=Ext.util.JSON.decode(response.responseText)
													  if(object.flag=='false'){
													  	Ext.ux.Toast.msg('状态',object.msg)
													  }else{ 
													  	Ext.ux.Toast.msg('状态','删除成功!'); 
													  }
													  thisPanel.getStore().reload(); 
												},
									 			failure : function(result, action) {
									 					Ext.ux.Toast.msg('状态', '删除失败!'); 
									 			},
												params : { listId : list } 
												}); 
											 } 
											}); 
										 }
										 
				}
			}, /*new Ext.Toolbar.Separator({
				hidden : isGranted('_remove_investP') ? false : true
			}), {
				iconCls : 'btn-edit',
				text : '申请制作合同',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_apply_contract') ? false : true,
				handler : function() {
					// 跳转到投资人合同制作流程
					var s = this.centerPanel.getSelectionModel()
							.getSelections();
					if (s <= 0) {
						Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
						return false;
					} else if (s.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
						return false;
					} else {
						var  panel =this;
						var record = s[0];
							var window_see = new Ext.Window({
									title : '制作合同',
									layout : 'fit',
									width : (screen.width - 100) * 0.7 + 160,
									maximizable : true,
									height : 460,
									closable : true,
									modal : true,
									plain : true,
									border : false,
									items : [new SlContractView({
												projectId : this.investmentId,
												isHiddenAddBtn : false,
												isHiddenDelBtn : false,
												isHiddenEdiBtn : false,
												isSignHidden : false,
												isHiddenAffrim : true,
												isHidden : true,
												issignEdit : true,
												isHiuploadScan : true,
												countersign:false,
												htType : 'investContract',
												isHiddenBZ:true,
												isHiddenDZY : true,
												businessType : this.businessType,
												isqsEdit : true,
												isHiddenisLegalCheck : false,
												isHiddenfileCount : false,
												isHiddenissign : false,
												investmentId : record.data.investId,
												businessType : 'Assignment',
												projId:record.data.investId
											})],
									listeners : {
										'beforeclose' : function(panel) {
											window_see.destroy();
										}
									}
								});
						window_see.show();
					}
				}

			},*/new Ext.Toolbar.Separator({
				hidden : isGranted('_apply_contract') ? false : true
					}), {
				iconCls : 'btn-card',
				text : '申请换卡',
				xtype : 'button',
				hidden : isGranted('_apply_changeCard') ? false : true,
				scope : this,
				handler : function() {

					// 跳转到换卡流程
					var s = this.centerPanel.getSelectionModel()
							.getSelections();
					if (s <= 0) {
						Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
						return false;
					} else if (s.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
						return false;
					} else {
						var panel =this;
						var record = s[0];							
						new bank({
							investId : record.data.investId,
							companyId: record.data.companyId,
							gridPanel : this.centerPanel
						}).show()
					}
				}
			}/*, new Ext.Toolbar.Separator({
				hidden : isGranted('_apply_changeCard') ? false : true
				}), {
				iconCls : 'btn-detail',
				text : '查看合同详情',
				xtype : 'button',
				hidden : isGranted('_see_contract') ? false : true,
				scope : this,
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
						// Ext.Ajax.request({
						// url : __ctxPath +
						// '/investment/seePersonCsInvestmentPerson.do',
						// method : 'POST',
						// scope : this,
						// success : function(response, request) {
						// var obj = Ext.util.JSON
						// .decode(response.responseText);
						// var personData = obj.data;
						// var randomId = rand(100000);
						// var id = "see_person" + randomId;
						// var anchor = '100%';
						var window_see = new Ext.Window({
									title : '查看合同详情',
									layout : 'fit',
									width : (screen.width - 100) * 0.7 + 160,
									maximizable : true,
									height : 460,
									closable : true,
									modal : true,
									plain : true,
									border : false,
									items : [new SlContractView({
												projectId : this.investmentId,
												isHiddenAddBtn : true,
												isHiddenDelBtn : true,
												isHiddenEdiBtn : true,
												isSignHidden : false,
												isHiddenAffrim : true,
												isHidden : true,
												issignEdit : false,
												isHiuploadScan : true,
												countersign:false,
												htType : 'investContract',
												businessType : this.businessType,
												isqsEdit : false,
												isHiddenisLegalCheck : false,
												isHiddenfileCount : false,
												isHiddenissign : false,
												investmentId : rows[0].data.investId,
												businessType : 'Assignment',
												projId:rows[0].data.investId
											})],
									listeners : {
										'beforeclose' : function(panel) {
											window_see.destroy();
										}
									}
								});
						window_see.show();
						// },
						// failure : function(response) {
						// Ext.ux.Toast.msg('状态', '操作失败，请重试');
						// },
						// params : {
						// investId : rows[0].data.investId
						// }
						// });
					}
				}
			}*/, new Ext.Toolbar.Separator({
				hidden : isGranted('_see_contract') ? false : true
				}), {
				text : '导出到Excel',
				iconCls : 'btn-xls',
				scope : this,
				hidden : isGranted('_export_investP') ? false : true,
				handler : function() {
					var companyId=1;
					if(isShow==true){
						companyId=this.getCmpByName('companyId').getValue()
					}
					var investName=this.getCmpByName('investName').getValue()
					var sex=this.getCmpByName('sex').getValue()
					var cardnumber=this.getCmpByName('cardnumber').getValue()
					var cardtype=this.getCmpByName('cardtype').getValue()
					window.open(__ctxPath + '/creditFlow/creditAssignment/customer/outputExcelCsInvestmentperson.do?isAll='+rightValue+'&companyId='+companyId+'&investName='+encodeURIComponent(encodeURIComponent(investName))
					+'&sex='+sex+'&cardnumber='+cardnumber+'&cardtype='+cardtype,'_blank');
				}
			}/*, new Ext.Toolbar.Separator({
				hidden : isGranted('_see_contract') ? false : true
				}),{
				iconCls : 'btn-add',
				text : '开通p2p账户',
				xtype : 'button',
				hidden : isGranted('_add_p2p_persion_user') ? false : true,
				ptype:'1',
				scope:this,
				handler: this.addP2pUser
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_see_contract') ? false : true
				}),{
				iconCls : 'btn-add',
				text : '开通第三方支付账户',
				xtype : 'button',
				hidden : isGranted('_add_ThirdPay_persion_user') ? false : true,
				scope:this,
				handler: this.addThirdPayCount
			}*/]
		});
		this.centerPanel = new HT.GridPanel({
					region : 'center',
					tbar : typeof(this.isHiddenSearchPanel)=="undefined"?tbar:hbar,
					clicksToEdit : 1,
					store : this.store,
					loadMask : this.myMask,
					columns : [{
								header : "所属分公司",
								width : 160,
								sortable : true,
								hidden : RoleType == "control" ? false : true,
								dataIndex : 'orgName'
							}, {
								header : "姓名",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'investName'
							}, {
								header : "性别",
								width : 50,
								align:'center',
								sortable : true,
								dataIndex : 'sexvalue'
							}, {
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
								header : "出生日期",
								width : 120,
								align:'center',
								sortable : true,
								dataIndex : 'birthDay'
							},{
								header : "手机号码",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'cellphone'
							},  {
								header : "登记门店",
								width : 100,	
								sortable : true,
								dataIndex : 'shopName'
							}/*, {
								header : "银行账户号码",
								width : 100,
								sortable : true,
								dataIndex : 'accountNumber'
							}*//*
								 * , { header : "客户授权人", width : 110, sortable :
								 * true, dataIndex : 'belongedName' }
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
	},// end of initUIComponents //查询
	searchByCondition : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.centerPanel
		});
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 添加p2p账户
	addP2pUser : function(obj,e) {
		var selections = this.centerPanel.getSelectionModel().getSelections();
		var grid=this.centerPanel;
		var bStr=obj.ptype;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var personId = selections[0].data.investId;
		var cellphone = selections[0].data.cellphone;
		var selfemail = selections[0].data.selfemail;
		var cardnumber = selections[0].data.cardnumber;
		new BpCustRelationForm({
			type:"p_invest",
			bStr:bStr,
			pType:2,
			userId:personId,
			cellphone:cellphone,
			selfemail:selfemail,
			cardnumber:cardnumber
		}).show();
	},
	//开通第三方支付的账号
	addThirdPayCount:function(){
		var selections = this.centerPanel.getSelectionModel().getSelections();
		var grid=this.centerPanel;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var personId = selections[0].data.investId;
		var cardtypevalue= selections[0].data.cardtypevalue;
		if(cardtypevalue=="身份证"){
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/creditAssignment/customer/addThirdPayCountCsInvestmentperson.do',
				method : 'POST',
				scope:this,
				success : function(response, request) {
					var object=Ext.util.JSON.decode(response.responseText);
					var msg=object.msg;
					Ext.ux.Toast.msg('状态', msg);
					return;
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('状态', '出错了，稍后再试');
					return;
				},
				params : {
					type:"0",
					personId:personId
				}
			});
		}else{
			Ext.ux.Toast.msg('状态', '开通第三方支付账户必须选择证件类型为身份证');
			return;
		}
	}
});