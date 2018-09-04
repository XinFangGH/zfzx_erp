OurFinanceProductMaterialsView = Ext.extend(Ext.Panel, {
	dic_GPanel : null,
	tree_GPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		businessType = this.businessType;// 给listener 传值 add by gaoqingrui
		this.initUIComponents();
		OurFinanceProductMaterialsView.superclass.constructor.call(this, {
					id : 'OurFinanceProductMaterialsView',
					layout : 'border',
					iconCls : 'menu-onlinedoc',
					title : '融资材料管理',
					/*
					 * autoHeight : true, autoWidth : true, autoScroll : true,
					 */
					border : false,
					items : [this.dic_GPanel, this.tree_GPanel/*
																 * { layout :
																 * 'fit',
																 * columnWidth :
																 * 0.42, items :
																 * [this.dic_GPanel] }, {
																 * id :
																 * 'gridtreeid',
																 * layout :
																 * 'fit',
																 * columnWidth :
																 * 0.52, items :
																 * [this.tree_GPanel] }
																 */,	{
								id : 'gselectid',
								xtype : 'hidden',
								name : 'gselectid',
								value : '0'
							}, {
								id : 'gselecttext',
								xtype : 'hidden',
								name : 'gselecttext',
								value : '0'
							}, {
								id : 'gselecttype',
								xtype : 'hidden',
								name : 'gselecttype',
								value : '0'
							}]
				});
	},
	initUIComponents : function() {
		// update by gao

		this.add_button_o = new Ext.Button({
					text : '添加融资材料项',
					tooltip : '添加选中租赁材料的融资材料项',
					hidden : isGranted('_create_dcx') ? false : true,
					iconCls : 'addIcon'
				});
		this.update_button_o = new Ext.Button({
					text : '修改融资材料项',
					tooltip : '修改选中的融资材料项',
					hidden : isGranted('_edit_dcx') ? false : true,
					iconCls : 'updateIcon'
				});
		this.delete_button_o = new Ext.Button({
					text : '删除融资材料项',
					tooltip : '从数据库中完全删除选中融资材料项',
					hidden : isGranted('_remove_dcx') ? false : true,
					iconCls : 'deleteIcon'
				});

		this.delete_button_l = new Ext.Button({

			text : '删除融资材料',
			tooltip : '从数据库中完全删除选中融资材料类别',
			iconCls : 'deleteIcon',
			hidden : isGranted('_remove_dc') ? false : true,
			scope : this,
			handler : function() {
				var testObj = Ext.getCmp("enterperiseMaterials_dic");
				var selected = testObj.getSelectionModel().getSelected();// 选中当前行

				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {

					Ext.MessageBox.confirm('系统提示', '确认删除？删除后无法恢复!', function(
							btn) {
						if (btn == 'no') {
							return false;
						} else {
							var id = selected.get('materialsId'); // 获得当前行的ID值
							Ext.Ajax.request({
								url : __ctxPath
										+ '/materials/deleteOurProcreditMaterialsEnterprise.do',
								method : 'POST',
								params : {
									materialsId : id
								},
								success : function(response, request) {
									testObj.getStore().load();
									Ext.getCmp("enterperiseMaterials_dic_r")
											.getStore().load();
								}
							});
						}
					})
				}
			}
		})
		this.update_button_l = new Ext.Button({
			text : '修改融资材料',
			tooltip : '修改选中的融资材料类别',
			iconCls : 'updateIcon',
			hidden : isGranted('_edit_dc') ? false : true,
			scope : this,
			handler : function() {
				var testObj = Ext.getCmp("enterperiseMaterials_dic");
				var selected = testObj.getSelectionModel().getSelected();// 选中当前行

				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('materialsId'); // 获得当前行的ID值
					Ext.Ajax.request({
						url : __ctxPath
								+ '/materials/getOurProcreditMaterialsEnterprise.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);

							var _update_panel = new Ext.FormPanel({
								id : 'multupdatepanel',
								url : __ctxPath
										+ '/materials/saveOurProcreditMaterialsEnterprise.do',
								labelAlign : 'right',
								buttonAlign : 'center',
								bodyStyle : 'padding:10px 25px 25px',
								height : 300,
								frame : true,
								labelWidth : 110,
								monitorValid : true,
								width : 400,
								items : [{
									xtype : 'textfield',
									fieldLabel : '融资材料类型',
									name : 'ourProcreditMaterialsEnterprise.materialsName',
									value : obj.data.materialsName,
									width : 280,
									maxLength : 127,
									allowBlank : false,
									blankText : '必填信息'
								}, {
									xtype : 'hidden',
									fieldLabel : '',
									name : 'ourProcreditMaterialsEnterprise.leaf',
									value : '0'
								}, {
									xtype : 'hidden',
									name : 'ourProcreditMaterialsEnterprise.parentId',
									value : '0'
								}, {
									name : 'ourProcreditMaterialsEnterprise.materialsId',
									xtype : 'hidden',
									value : obj.data.materialsId
								}/*, {
									xtype : 'textfield',
									fieldLabel : '业务类别',
									name : 'btn',
									readOnly : true,
									value : '小额融资',
									width : 280,
									maxLength : 255
								}, {

									fieldLabel : "融资类型",
									xtype : "combo",
									allowBlank : false,
									displayField : 'itemName',
									store : new Ext.data.SimpleStore({
										baseParams : {
											nodeKey : 'SmallLoan'
										},
										autoLoad : true,
										url : __ctxPath
												+ '/system/getTypeJsonByNodeKeyGlobalType.do',
										fields : ['itemId', 'itemName']
									}),
									valueField : 'itemId',
									triggerAction : 'all',
									mode : 'remote',
									hiddenName : "ourProcreditMaterialsEnterprise.operationTypeKey",
									editable : false,
									blankText : "融资类型不能为空，请正确填写!",
									width : 280,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox
														.setValue(obj.data.operationTypeKey);
											})
										}
									}
								}, {
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									width : 280,
									store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["企业", "company"],
														["个人", "person"]]
											}),
									triggerAction : "all",
									hiddenName : 'ourProcreditMaterialsEnterprise.customerType',
									value : obj.data.customerType,
									allowBlank : false,
									editable : false,
									fieldLabel : '客户类型'
								}*/, {
									xtype : 'textarea',
									fieldLabel : '备注 ',
									name : 'ourProcreditMaterialsEnterprise.remarks',
									width : 280,
									maxLength : 1000,
									value : obj.data.remarks
								}],
								buttons : [{
									text : '提交',
									formBind : true,
									handler : function() {
										_update_panel.getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function() {
												Ext.ux.Toast.msg('状态', '修改成功!');
												UTwin.destroy();
												testObj.getStore().load();

											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态', '修改失败!');

											}
										});
									}

								}, {

									text : '取消',
									handler : function() {
										UTwin.destroy();
									}

								}]
							})
							var UTwin = new Ext.Window({
										id : 'win',
										layout : 'fit',
										title : '编辑融资材料类型（根融资材料类型）',
										width : 500,
										height : 200,
										maximizable : true,
										modal : true,
										items : [_update_panel]
									})
							UTwin.show();
						},
						failure : function(response) {
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
						},
						params : {
							materialsId : id
						}
					})
				}

			}
		})
		this.add_button_l = new Ext.Button({
			text : '添加融资材料',
			scope : this,
			tooltip : '添加融资材料类型',
			iconCls : 'addIcon',
			hidden : isGranted('_create_dc') ? false : true,
			handler : function() {
				var formPanel = new Ext.FormPanel({
					labelAlign : 'right',
					buttonAlign : 'center',
					url : __ctxPath
							+ '/materials/saveOurProcreditMaterialsEnterprise.do',
					bodyStyle : 'padding:10px 25px 25px',
					labelWidth : 110,
					frame : true,
					waitMsgTarget : true,
					monitorValid : true,
					width : 500,
					items : [{
								xtype : 'textfield',
								fieldLabel : '融资材料类型 ',
								name : 'ourProcreditMaterialsEnterprise.materialsName',
								width : 280,
								maxLength : 127,
								allowBlank : false,
								blankText : '必填信息'
							}, {
								xtype : 'hidden',
								fieldLabel : '',
								name : 'ourProcreditMaterialsEnterprise.leaf',
								value : '0'
							}, {
								xtype : 'hidden',
								name : 'ourProcreditMaterialsEnterprise.parentId',
								value : '0'
							} ,{
								xtype : 'hidden',
								name : 'ourProcreditMaterialsEnterprise.operationTypeKey',
								value : 'FinanceProduct'
							}, {
								xtype : 'textarea',
								fieldLabel : '备注 ',
								name : 'ourProcreditMaterialsEnterprise.remarks',
								width : 280,
								maxLength : 1000
							}],
					buttons : [{
						text : '提交',
						formBind : true,
						handler : function() {
							formPanel.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function() {
									Ext.ux.Toast.msg('状态', '添加成功!');
									// enterperiseMaterials_dic
									adwin.destroy();
									Ext.getCmp("enterperiseMaterials_dic")
											.getStore().load();
								},
								failure : function(form, action) {
									Ext.ux.Toast.msg('状态', '添加失败,有重复项');
								}
							})
						}
					}, {

						text : '取消',
						handler : function() {
							adwin.destroy();
						}

					}]

				});
				var adwin = new Ext.Window({
							layout : 'fit',
							title : '增加融资材料类型（根融资材料项）',
							width : 500,
							height : 200,
							minimizable : true,
							modal : true,
							items : [formPanel]
						});
				adwin.show();

			}
		})

		this.add_button_r = new Ext.Button({
			text : '添加融资材料项',
			scope : this,
			tooltip : '添加选中融资材料的融资材料项',
			hidden : isGranted('_create_dcx') ? false : true,
			iconCls : 'addIcon',
			handler : function() {
				var testObj = Ext.getCmp("enterperiseMaterials_dic");
				var gselectid = testObj.getSelectionModel().getSelected()
						.get('materialsId');
				var operationTypeKey = testObj.getSelectionModel()
						.getSelected().get('operationTypeKey');
				var gselecttext = testObj.getSelectionModel().getSelected()
						.get('materialsName');
				var customerType = testObj.getSelectionModel().getSelected()
						.get('customerType');
				if (gselectid == '0') {
					Ext.ux.Toast.msg('操作提示', '请选择您要添加的子选项的父节点！');
					return;
				}
				var formPanel = new Ext.FormPanel({
					id : 'multdicform_m',
					labelAlign : 'right',
					buttonAlign : 'center',
					url : __ctxPath
							+ '/materials/saveOurProcreditMaterialsEnterprise.do',
					bodyStyle : 'padding:10px 25px 25px',
					labelWidth : 110,
					frame : true,
					waitMsgTarget : true,
					monitorValid : true,
					// width : 500,
					items : [{
								xtype : 'label',
								fieldLabel : '父融资材料项',
								text : '[' + gselecttext + ']'
							}, {
								xtype : 'hidden',
								fieldLabel : '',
								name : 'ourProcreditMaterialsEnterprise.parentId',
								value : gselectid
							}, {
								xtype : 'hidden',
								fieldLabel : '',
								name : 'ourProcreditMaterialsEnterprise.customerType',
								value : customerType
							},{
								xtype : 'hidden',
								name : 'ourProcreditMaterialsEnterprise.operationTypeKey',
								value : 'FinanceProduct'
							}/*, {
								fieldLabel : "融资类型",
								xtype : "combo",
								allowBlank : false,
								displayField : 'itemName',
								store : new Ext.data.SimpleStore({
									baseParams : {
										nodeKey : 'SmallLoan'//
									},
									autoLoad : true,
									url : __ctxPath
											+ '/system/getTypeJsonByNodeKeyGlobalType.do',
									fields : ['itemId', 'itemName']
								}),
								valueField : 'itemId',
								triggerAction : 'all',
								readOnly : true,
								mode : 'remote',
								width : 280,
								hiddenName : "ourProcreditMaterialsEnterprise.operationTypeKey",
								editable : false,
								blankText : "融资类型不能为空，请正确填写!",
								// anchor : "100%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox
															.setValue(operationTypeKey);
												})
									}
								}
							}*/, {
								xtype : 'hidden',
								fieldLabel : '',
								name : 'ourProcreditMaterialsEnterprise.leaf',
								value : '1'
							}, {
								xtype : 'textfield',
								fieldLabel : '数据融资材料项 ',
								name : 'ourProcreditMaterialsEnterprise.materialsName',
								width : 280,
								maxLength : 127,
								allowBlank : false,
								blankText : '必填信息'
							}, {
								xtype : 'textarea',
								fieldLabel : '合规说明 ',
								name : 'ourProcreditMaterialsEnterprise.ruleExplain',
								width : 280,
								maxLength : 1000
							}],

					buttons : [{
						text : '提交',
						formBind : true,
						handler : function() {
							formPanel.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function() {
									Ext.ux.Toast.msg('操作提示', '添加成功!');
									adwin.destroy();
									// enterperiseMaterials_dic_r
									Ext.getCmp("enterperiseMaterials_dic_r")
											.getStore().load();

								},
								failure : function(form, action) {
									Ext.ux.Toast.msg('操作提示', '添加失败,有重复项!');
								}
							})
						}
					}, {

						text : '取消',
						handler : function() {
							adwin.destroy();
						}

					}]

				});
				var adwin = new Ext.Window({
							layout : 'fit',
							title : '添加融资材料项（子融资材料项）',
							width : 500,
							height : 230,
							minimizable : true,
							modal : true,
							items : [formPanel]
						});
				adwin.show();

			}
		});
		this.update_button_r = new Ext.Button({
			text : '修改融资材料项',
			tooltip : '修改选中的融资材料项',
			iconCls : 'updateIcon',
			hidden : isGranted('_edit_dcx') ? false : true,
			scope : this,
			handler : function() {
				var testObjr = Ext.getCmp("enterperiseMaterials_dic");
				var testObj = Ext.getCmp("enterperiseMaterials_dic_r");
				var selected = testObj.getSelectionModel().getSelected();// 选中当前行
				var customerType = testObjr.getSelectionModel().getSelected()
						.get('customerType');

				var gselectid = selected.get('materialsId');
				var gselecttext = selected.get('materialsName');

				if (gselectid == '0') {
					Ext.ux.Toast.msg('操作提示', '请选择您要修改的融资材料项！');
					return;
				}
				Ext.Ajax.request({
					url : __ctxPath
							+ '/materials/getOurProcreditMaterialsEnterprise.do',

					method : 'POST',
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);

						var _update_panel = new Ext.FormPanel({
							id : 'multupdatepanel',
							url : __ctxPath
									+ '/materials/saveOurProcreditMaterialsEnterprise.do',
							labelAlign : 'right',
							buttonAlign : 'center',
							bodyStyle : 'padding:10px 25px 25px',
							height : 380,
							frame : true,
							labelWidth : 110,
							monitorValid : true,
							width : 400,
							items : [{
								xtype : 'hidden',
								fieldLabel : '',
								name : 'ourProcreditMaterialsEnterprise.materialsId',
								value : gselectid
							}, {
								xtype : 'hidden',
								fieldLabel : '',
								name : 'ourProcreditMaterialsEnterprise.leaf',
								value : '1'
							}, {
								xtype : 'hidden',
								name : 'ourProcreditMaterialsEnterprise.customerType',
								value : customerType
							}, {
								xtype : 'textfield',
								fieldLabel : '数据融资材料项 ',
								name : 'ourProcreditMaterialsEnterprise.materialsName',
								width : 280,
								maxLength : 127,
								allowBlank : false,
								blankText : '必填信息',
								value : obj.data.materialsName
							}, {
								fieldLabel : '父融资材料ID',
								layout : 'column',
								items : [{
									id : 'parentId',
									xtype : 'textfield',
									name : 'ourProcreditMaterialsEnterprise.parentId',
									width : 50,
									value : obj.data.parentId,
									blankText : '必填信息',
									regex : /^[0-9]+$/,
									regexText : '请输入数字',
									listeners : {
										'change' : function(a, b, c) {
											Ext.Ajax.request({
												url : __ctxPath
														+ '/materials/getOurProcreditMaterialsEnterprise.do',
												method : 'POST',
												params : {
													materialsId : b
												},
												success : function(response,
														request) {
													var objs = Ext.util.JSON
															.decode(response.responseText);

													if (objs.data == null) {
														Ext.getCmp("parentId")
																.setValue(c);
														Ext.ux.Toast
																.msg('操作提示',
																		'请办输入正确的租赁材料ID号!');
														return;
													} else {
														var fatext = objs.data.materialsName;
														Ext
																.getCmp("fatext")
																.setValue(fatext);
													}
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态',
															'失败!');
												}
											})
										}
									}
								}, {
									xtype : 'label',
									width : 15,
									html : '&nbsp;--'
								}, {
									id : 'fatext',
									xtype : 'textfield',
									readOnly : true,
									name : 'fatext',
									width : 210,
									value : obj.data.parentText
								}]

							}, {
								xtype : 'textarea',
								fieldLabel : '合规说明',
								name : 'ourProcreditMaterialsEnterprise.ruleExplain',
								width : 280,
								maxLength : 1000,
								value : obj.data.ruleExplain
							}, {
								xtype : 'hidden',
								name : 'ourProcreditMaterialsEnterprise.operationTypeKey',
								value : obj.data.operationTypeKey
							}, {

								xtype : 'hidden',
								name : 'ourProcreditMaterialsEnterprise.remarks',
								value : obj.data.remarks

							}],
							buttons : [{
								text : '提交',
								formBind : true,
								handler : function() {
									_update_panel.getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function() {
											Ext.ux.Toast.msg('状态', '修改成功!')
											UTwin.destroy();
											Ext
													.getCmp("enterperiseMaterials_dic_r")
													.getStore().load();
										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('操作提示',
													'修改失败,有重复项！');

										}
									});
								}

							}, {

								text : '取消',
								handler : function() {
									UTwin.destroy();
								}

							}]
						});
						var UTwin = new Ext.Window({
									id : 'win',
									layout : 'fit',
									title : '编辑数据融资材料项（子数据融资材料项）',
									width : 500,
									height : 300,
									maximizable : true,
									modal : true,
									items : [_update_panel]
								});
						UTwin.show();

					},
					params : {
						materialsId : gselectid
					}
				})

			}
		});

		this.delete_button_r = new Ext.Button({
			text : '删除融资款料项',
			tooltip : '从数据库中完全删除选中融资材料项',
			hidden : isGranted('_remove_dcx') ? false : true,
			iconCls : 'deleteIcon',
			scope : this,
			handler : function() {
				var testObj = Ext.getCmp("enterperiseMaterials_dic_r");
				var selected = testObj.getSelectionModel().getSelected();// 选中当前行
				var gselectid = selected.get('materialsId');
				var gselecttext = selected.get('materialsName');
				if (gselectid == '0') {
					Ext.ux.Toast.msg('操作提示', '请选择您要修改的融资材料项！');
					return;
				}
				Ext.Ajax.request({
					url : __ctxPath
							+ '/materials/deleteOurProcreditMaterialsEnterprise.do',
					method : 'POST',
					params : {
						materialsId : gselectid
					},
					success : function(response, request) {
						Ext.ux.Toast.msg('操作提示', '删除成功!');
						Ext.getCmp("enterperiseMaterials_dic_r").getStore()
								.load();
					}
				});

			}
		});
		// update by gao end

		this.tree_GPanel = new HT.EditorGridPanel({
					region : 'center',
					height : 800,
					width : 300,
					autoScroll : true,
					id : 'enterperiseMaterials_dic_r',
					frame : false,
					border : false,
					isautoLoad : false,
					iconCls : 'icon-grid',
					stripeRows : true,
					tbar : [this.add_button_r, this.update_button_r,
							this.delete_button_r],
					columns : [{
								header : '融资材料项',
								dataIndex : 'materialsName',
								width : 240
							}, {
								dataIndex : 'materialsId',
								hidden : true
							}, {
								header : '父节点名称',
								width : 100,
								dataIndex : 'parentText'
							}, {
								header : "合规说明",
								dataIndex : 'ruleExplain',
								width : 280
							}]
				});
		this.tree_GPanel.doLayout();
		var tree_GPanel = this.tree_GPanel
		this.dic_GPanel = new HT.EditorGridPanel({
			id : 'enterperiseMaterials_dic',
			region : 'west',
			border : false,
			height : Ext.getBody().getViewSize().height - 115,
			width : 300,
			split : true,
			autoScroll : true,
			url : __ctxPath
					+ '/materials/listByOperationTypeKeyOurProcreditMaterialsEnterprise.do?operationTypeKey=FinanceProduct',// 融资租赁项目，默认以Guarantee_CompanyBusiness
																																			// 为operationTypeKey作为反担保措施对应key，如果修改，请修改vm中抵质押物材料
			fields : [{
						id : 'id',
						name : 'materialsId'
					}, {
						id : 'text',
						name : 'materialsName'
					}, {
						id : 'lable',
						name : 'lable'
					}, {
						id : 'remarks',
						name : 'remarks'
					}, {
						id : 'remarks',
						name : 'remarks'
					}, {
						id : 'operationTypeName',
						name : 'operationTypeName'
					}, {
						id : 'operationTypeKey',
						name : 'operationTypeKey'
					}, {
						id : 'customerType',
						name : 'customerType'
					}],
			columns : [{
						header : "融资材料类型",
						width : 160,
						sortable : true,
						dataIndex : 'materialsName'
					}/*, {
						header : '融资类型',
						width : 100,
						dataIndex : 'operationTypeName'
					}*/, {
						header : "编号",
						width : 100,
						dataIndex : 'materialsId'
					}, {
						hidden : true,
						dataIndex : 'customerType'
					}],

			frame : false,
			iconCls : 'icon-grid',
			stripeRows : true,
			tbar : [this.add_button_l, this.update_button_l,
					this.delete_button_l],
			scope : this,
			listeners : {
				'rowclick' : function(grid, index, e) {
					var gtext = grid.getSelectionModel().getSelected()
							.get('materialsName');
					var gid = grid.getSelectionModel().getSelected()
							.get('materialsId');
					var rightStore = tree_GPanel.getStore();
					var rightUrl = __ctxPath
							+ '/materials/listChildrenOurProcreditMaterialsEnterprise.do?node='
							+ gid;
					rightStore.proxy = new Ext.data.HttpProxy({
								url : rightUrl
							})
					rightStore.reader = new Ext.data.JsonReader({
								root : 'result'
							}, [{
										name : 'materialsId'
									}, {
										name : 'materialsName'
									}, {
										name : 'parentText'
									}, {
										name : 'ruleExplain'
									}]);
					rightStore.load();
				}
			}
		});
		this.dic_GPanel.doLayout();
	}
});