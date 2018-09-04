var start = 0;
var pageSize = 15;
var bodyWidth = Ext.getBody().getWidth();
var bodyHeight = Ext.getBody().getHeight();
var fieldWidth = 150;
var root = 'topics';
var totalProperty = 'totalProperty';
var selectPWName = function() {
	Ext.onReady(function() {
		var windowGroup = new Ext.WindowGroup();
		var jStore_enterprise = new Ext.data.JsonStore({
					url : __ctxPath+'/creditFlow/customer/person/queryListPerson.do?isAll='+isGranted('_detail_sygrkh'),
					totalProperty : totalProperty,
					root : root,
					fields : [{
								name : 'id'
							}, {
								name : 'name'
							}, {
								name : 'sex'
							}, {
								name : 'job'
							}, {
								name : 'cardtype'
							}, {
								name : 'cardnumber'
							}, {
								name : 'cellphone'
							}, {
								name : 'birthday'
							}],
							remoteSort: true//服务器端排序 by chencc
				});
		jStore_enterprise.load({
					params : {
						start : start,
						limit : pageSize
					}
				});
		var cModel_enterprise = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "姓名",
					width : 80,
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "性别",
					width : 40,
					sortable : true,
					dataIndex : 'sex'
				}, {
					header : "职务",
					width : 80,
					sortable : true,
					dataIndex : 'job'
				}, {
					header : "证件类型",
					width : 80,
					sortable : true,
					dataIndex : 'cardtype'
				}, {
					header : "证件号码",
					width : 60,
					sortable : true,
					dataIndex : 'cardnumber'
				}, {
					header : "手机号码",
					width : 60,
					sortable : true,
					dataIndex : 'cellphone'

				}, {
					header : "出生日期",
					width : 80,
					sortable : true,
					dataIndex : 'birthday'
				}]);
		var pagingBar = new Ext.PagingToolbar({
					pageSize : pageSize,
					store : jStore_enterprise,
					autoWidth : true,
					hideBorders : true,
					displayInfo : true,
					displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
					emptyMsg : "没有符合条件的记录······"
				});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});

		var button_add = new Ext.Button({
			text : '增加',
			tooltip : '增加一条新的个人信息',
			iconCls : 'addIcon',
			scope : this,
			handler : function() {
				var fPanel_add = new Ext.FormPanel({
							url : __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
							labelAlign : 'left',
							buttonAlign : 'center',
							bodyStyle : 'padding:5px;',
							height : 100,
							frame : true,
							labelWidth : 100,
							monitorValid : true,
							items : [{
								layout : 'column',
								border : false,
								labelSeparator : ':',
								defaults : {
									layout : 'form',
									border : false,
									columnWidth : .5
								},
								items : [{
											items : [{
														xtype : 'textfield',
														fieldLabel : '姓名',
														name : 'person.name',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '性别',
														name : 'person.sex',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '证件类型',
														name : 'person.cardtype',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '证件号码',
														name : 'person.cardnumber',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '固定电话',
														name : 'person.telphone',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '手机号码',
														name : 'person.cellphone',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '传真',
														name : 'person.fax',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '通信地址',
														name : 'person.postaddress',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '邮政编码',
														regex : /^[0-9]{6}$/,
														regexText : '邮政编码格式不正确',
														name : 'person.postcode',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '学历',
														name : 'person.dgree',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '婚姻状况',
														name : 'person.marry',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '公民类型',
														name : 'person.peopletype',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'datefield',
														fieldLabel : '出生日期',
														emptyText : '请选择',
														name : 'person.birthday',
														format : 'Y-m-d',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '民族',
														name : 'person.nationality',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '固定地址',
														name : 'person.address',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '户口',
														name : 'person.hukou',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '籍贯',
														name : 'person.homeland',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '先工作单位',
														name : 'person.currentcompany',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'combo',
														fieldLabel : '技术职称',
														name : 'person.techpersonnel',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '职务',
														name : 'person.job',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textarea',
														fieldLabel : '备注',
														name : 'person.remarks',
														anchor : '90%'
													}]
										}]
									// items
							}],
							buttons : [{
								text : '提交',
								formBind : true,
								handler : function() {
									fPanel_add.getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function() {
											Ext.ux.Toast.msg('状态', '添加个人信息成功!');
														if (btn == 'ok') {
															jStore_enterprise
																	.removeAll();
															jStore_enterprise
																	.reload();
															window_add
																	.destroy();
														}
										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('状态', '添加个人信息失败!');
											// top.getForm().reset();
										}
									});
								}
							}, {
								text : '取消',
								handler : function() {
									window_add.destroy();
								}
							}]
						});

				var window_add = new Ext.Window({
							id : 'w_add',
							title : '新增个人信息',
							layout : 'fit',
							width : 600,
							height : 400,
							closable : true,
							constrainHeader : true ,
							resizable : true,
							plain : true,
							border : false,
							modal : true,
							buttonAlign : 'right',
							minHeight : 250, // resizable为true有效

							minWidth : 530,// resizable为true有效
							bodyStyle : 'padding: 5',
							items : [fPanel_add],
							// buttons: [{}],
							listeners : {
								'show' : function() {
									// alert();
								}
							},
							manager : new Ext.WindowGroup()
						});
				window_add.show();
				/** 调用一个函数，使页面跳转到新的window，进入新增页面-----结束 */
				// window.location.href = 'addEnterprise.jsp' ;
				//
			}
		});

		var button_update = new Ext.Button({
			text : '修改',
			tooltip : '修改选中的个人信息',
			iconCls : 'updateIcon',
			scope : this,
			handler : function() {
				var selected = gPanel_enterprise.getSelectionModel()
						.getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id');
					Ext.Ajax.request({
						url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							/** 修改企业信息弹出开始 */
							var fPanel_update = new Ext.FormPanel({
								url : __ctxPath+'/creditFlow/customer/person/updateInfoPerson.do',
								labelAlign : 'left',
								buttonAlign : 'center',
								bodyStyle : 'padding:5px;',
								height : 100,
								frame : true,
								labelWidth : 100,
								monitorValid : true,
								items : [{
									layout : 'column',
									border : false,
									labelSeparator : ':',
									defaults : {
										layout : 'form',
										border : false,
										columnWidth : .5
									},
									items : [{
												items : [{
															xtype : 'textfield',
															fieldLabel : '姓名',
															name : 'person.name',
															value : obj.data.name,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'datefield',
															fieldLabel : '出生日期',
															name : 'person.birthday',
															value : obj.data.birthday,
															format : 'Y-m-d H:i:s',
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'combo',
															fieldLabel : '证件类型',
															name : 'person.cardtype',
															value : obj.data.cardtype,
															anchor : '90%'
														}]
											}, {
												items : [{
													xtype : 'textfield',
													fieldLabel : '证件号码',
													name : 'person.cardnumber',
													value : obj.data.cardnumber,
													anchor : '90%'
												}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '固定电话',
															name : 'person.telphone ',
															value : obj.data.telphone,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '手机号码',
															name : 'person.cellphone',
															value : obj.data.cellphone,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '传真',
															name : 'person.fax',
															value : obj.data.fax,
															anchor : '90%'
														}]
											}, {
												items : [{
													xtype : 'textfield',
													fieldLabel : '通信地址',
													name : 'person.postaddress',
													value : obj.data.postaddress,
													anchor : '90%'
												}]
											}, {
												items : [{
															xtype : 'combo',
															fieldLabel : '婚姻状况',
															name : 'person.marry',
															value : obj.data.marry,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '固定地址',
															name : 'person.address',
															value : obj.data.address,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '户口',
															name : 'person.hukou',
															value : obj.data.hukou,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '籍贯',
															name : 'person.homeland',
															value : obj.data.homeland,
															anchor : '90%'
														}]
											}, {
												items : [{
													xtype : 'textfield',
													fieldLabel : '现工作单位',
													name : 'person.currentcompany',
													value : obj.data.currentcompany,
													anchor : '90%'
												}]
											}, {
												items : [{
															xtype : 'textfield',
															fieldLabel : '职务',
															name : 'person.job',
															value : obj.data.job,
															anchor : '90%'
														}]
											}, {
												items : [{
													xtype : 'combo',
													fieldLabel : '技术职称',
													name : 'person.techpersonnel',
													value : obj.data.techpersonnel,
													anchor : '90%'
												}]
											}, {
												items : [{
													xtype : 'combo',
													fieldLabel : '民族',
													name : 'person.nationality',
													value : obj.data.nationality,
													anchor : '90%'
												}]
											}, {
												items : [{
															xtype : 'textarea',
															fieldLabel : '备注',
															name : 'person.remarks',
															value : obj.data.remarks,
															anchor : '90%'
														}]
											}, {
												items : [{
															xtype : 'hidden',
															hideLabel : true,
															name : 'person.id',
															value : obj.data.id,
															anchor : '90%'
														}]
											}]
										// items
								}],
								buttons : [{
									text : '提交',
									formBind : true,
									handler : function() {
										fPanel_update.getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function() {
												Ext.ux.Toast.msg('状态', '修改成功!',
														function(btn, text) {
															if (btn == 'ok') {
																jStore_enterprise
																		.removeAll();
																jStore_enterprise
																		.reload();
																window_update
																		.destroy();
															}
														});
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态', '修改失败!');
												// top.getForm().reset();
											}
										});
									}
								}, {
									text : '取消',
									handler : function() {
										window_update.destroy();
									}
								}]
							});

							var window_update = new Ext.Window({
										id : 'window_update',
										title : '修改个人信息',
										layout : 'fit',
										width : 600,
										height : 350,
										constrainHeader : true ,
										closable : true,
										resizable : true,
										plain : true,
										border : false,
										modal : true,
										buttonAlign : 'right',
										minHeight : 250, // resizable为true有效
										minWidth : 530,// resizable为true有效
										bodyStyle : 'padding: 5',
										items : [fPanel_update],
										// buttons: [{}],
										listeners : {
											'show' : function() {
												// alert();
											}
										},
										manager : new Ext.WindowGroup()
									});
							window_update.show();
							/** 修改企业信息弹出结束 */
						},
						failure : function(response) {
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
						},
						params : {
							id : id
						}
					});
				}
			}
		});
		var button_delete = new Ext.Button({
			text : '删除',
			tooltip : '删除选中的企业信息',
			iconCls : 'selectIcon',
			scope : this,
			handler : function() {
				//
				var selected = gPanel_enterprise.getSelectionModel()
						.getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id');
					Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
												url : __ctxPath+'/creditFlow/customer/person/deletePerson.do',
												method : 'POST',
												success : function() {
													Ext.ux.Toast.msg('状态','删除成功!');
													// jStore_enterprise.removeAll();
													// jStore_enterprise.reload();
													searchByCondition();
												},
												failure : function(result,
														action) {
													Ext.ux.Toast.msg('状态','删除失败!');
												},
												params : {
													id : id
												}
											});
								}
							});
				}
				//
			}
		});

		var button_see = new Ext.Button({
					text : '查看',
					tooltip : '查看选中的人员信息',
					iconCls : 'seeIcon',
					scope : this,
					handler : function() {
						var selected = gPanel_enterprise.getSelectionModel()
								.getSelected();
						if (null == selected) {
							Ext.ux.Toast.msg('状态', '请选择一条记录!');
						} else {
							var id = selected.get('id');
							searchIndexMessage(id);
						}
						//
					}
				});

		/** 查看某一行信息 */
		var searchIndexMessage = function(id) {
			Ext.Ajax.request({
				url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					/** 查看信息 */
					var panel_see = new Ext.Panel({

								buttonAlign : 'center',
								bodyStyle : 'padding:5px;',
								height : 500,
								width : 600,
								title : '信息查看',
								frame : true,
								items : [{
									layout : 'column',
									border : false,
									defaults : {
										layout : 'form',
										border : false,
										columnWidth : .25,
										height : 30
									},
									items : [{
												html : '姓名： ' + obj.data.name
											}, {
												html : '性别： ' + obj.data.sex
											}, {
												html : '民族：'
														+ obj.data.nationality
											}, {
												html : '证件类型：'
														+ obj.data.cardtype
											}, {
												html : '证件号码：'
														+ obj.data.cardnumber
											}, {
												html : '固定电话：'
														+ obj.data.telphone
											}, {
												html : '手机号码：'
														+ obj.data.cellphone
											}, {
												html : '传真：' + obj.data.fax
											}, {
												html : '学历：' + obj.data.dgree
											}, {
												html : '出生日期：'
														+ obj.data.birthday
											}, {
												html : '通信地址：'
														+ obj.data.postaddress
											}, {
												html : '民族：'
														+ obj.data.nationality
											}, {
												html : '固定地址：'
														+ obj.data.address
											}, {
												html : '户口：' + obj.data.hukou
											}, {
												html : '籍贯：'
														+ obj.data.homeland
											}, {
												html : '现工作单位：'
														+ obj.data.currentcompany
											}, {
												html : '职务：' + obj.data.job
											}, {
												html : '技术职称：'
														+ obj.data.techpersonnel
											}, {
												html : '婚姻状况：' + obj.data.marry
											}, {
												html : '备注：' + obj.data.remarks
											}]
										// items
								}],
								buttons : [{
											text : '关闭',
											handler : function() {
												window_see.destroy();
											}
										}]
							});

					var window_see = new Ext.Window({
								id : 'window_update',
								title : '新增个人信息',
								layout : 'fit',
								width : 600,
								height : 350,
								closable : true,
								constrainHeader : true ,
								resizable : true,
								plain : true,
								border : false,
								modal : true,
								buttonAlign : 'right',
								minHeight : 250, // resizable为true有效
								minWidth : 530,// resizable为true有效
								bodyStyle : 'padding: 5',
								items : [panel_see],
								listeners : {
									'show' : function() {
										// alert();
									}
								},
								manager : new Ext.WindowGroup()
							});
					window_see.show();
					/** 查看企业信息弹出结束 */
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					id : id
				}
			});
		}
		var gPanel_enterprise = new Ext.grid.GridPanel({
			id : 'gPanel_enterprise',
			store : jStore_enterprise,
			width : 600,
			height : 300,
			colModel : cModel_enterprise,
			autoExpandColumn : 7,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : myMask,
			bbar : pagingBar,
			tbar : [{
				text : '选择',
				iconCls : 'selectIcon',
				handler : function() {
					var selected = gPanel_enterprise.getSelectionModel()
							.getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						id = selected.get('id');
						document.getElementById('name').value = selected
								.get('name');
						document.getElementById('job').value = selected
								.get('job');
						document.getElementById('cellphone').value = selected
								.get('cellphone');
						document.getElementById('telphone').value = selected
								.get('telphone');
						window.destroy();
					}
				}
			}, button_add, button_see, button_update, button_delete],
			listeners : {
				'rowdblclick' : function(grid, index, e) {
					var id = grid.getSelectionModel().getSelected().get('id');
					var name = grid.getSelectionModel().getSelected()
							.get('name');
					var duty = grid.getSelectionModel().getSelected()
							.get('duty');
					var blmtelephone = grid.getSelectionModel().getSelected()
							.get('blmtelephone');
					var blmphone = grid.getSelectionModel().getSelected()
							.get('blmphone');
					document.getElementById('name').value = name;
					document.getElementById('job').value = job;
					document.getElementById('cellphone').value = cellphone;
					document.getElementById('telphone').value = telphone;
					window.destroy();
				}
			}
		});

		var window = new Ext.Window({
					title : '人员列表',
					border : false,
					width : 600,
					height : 450,
					constrainHeader : true ,
					layout : 'fit',
					buttonAlign : 'center',
					items : [gPanel_enterprise]
				});
		window.show();

		var searchByCondition = function() {
			jStore_enterprise.load({
						params : {
							start : 0,
							limit : 15
						}
					});
		}

	});
}
