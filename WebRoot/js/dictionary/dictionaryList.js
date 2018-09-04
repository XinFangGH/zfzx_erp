Ext.onReady(function() {
	var jStore_dictionary = new Ext.data.JsonStore({
				url : 'getDictionaryList.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [{
							name : 'id'
						}, {
							name : 'type'
						}, {
							name : 'remarks'
						}, {
							name : 'isOld'
						}]
			});

	jStore_dictionary.load({
				params : {
					start : 0,
					limit : 2000
				}
			});

	var cModel_dictionary = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '字典类别',
				width : 170,
				dataIndex : 'type',
				sortable : true
			}, {
				header : '类别ID',
				width : 50,
				dataIndex : 'id',
				sortable : true
			}, {
				header : '备注',
				dataIndex : 'remarks',
				sortable : true
			}]);

	var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});

	var dictionaryGrid = new Ext.grid.GridPanel({
				id : 'dictionaryGrid',
				store : jStore_dictionary,
				bodyStyle : 'width:100%;',
				height : document.body.clientHeight - 60,
				colModel : cModel_dictionary,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				// 颜色配置
				viewConfig : {
					forceFit : true,
					getRowClass : function(record, rowIndex, rowParams, store) {
						if (record.data.isOld == '1') {
							return 'x-grid-record-line';
						}
						return '';
					}
				}
			});
	var dictionaryPannel = new Ext.Panel({
				id : 'dictionaryPannel',
				layout : 'fit',
				autoHeight : false,
				height : document.body.clientHeight - 60,
				autoWidth : true,
				autoScroll : false,
				frame : true,
				renderTo : 'dictionaryList',
				items : dictionaryGrid
			})

	var jStore_option = new Ext.data.JsonStore({
				url : 'getOptionList.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [{
							name : 'id'
						}, {
							name : 'value'
						}, {
							name : 'sortorder'
						}, {
							name : 'isOld'
						}, {
							name : 'remarks'
						}],
				baseParams : {
					dictionaryId : document.getElementById("dictionaryId").value
				}
			});
	var cModel_dictionaryOption = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '数据字典项',
				dataIndex : 'value',
				width : 120,
				sortable : true
			},{
				header : '字典ID号',
				dataIndex : 'id',
				width : 40,
				sortable : true
			},{
				header : '显示顺序',
				dataIndex : 'sortorder',
				width : 40,
				sortable : true

			}, {
				id : 'id_autoExpandColumn',
				header : '备注',
				width : 180,
				dataIndex : 'remarks'
				// hidden : true
		}]);

	var dictionaryOptionGrid = new Ext.grid.GridPanel({
				id : 'dictionaryOptionGrid',
				store : jStore_option,
				bodyStyle : 'width:100%;',
				height : document.body.clientHeight - 60,
				colModel : cModel_dictionaryOption,
				autoExpandColumn : 'id_autoExpandColumn',
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				// 颜色配置
				viewConfig : {
					forceFit : true,
					getRowClass : function(record, rowIndex, rowParams, store) {
						if (record.data.isOld == '1') {
							return 'x-grid-record-line';
						}
						return '';
					}
				}
			});

	var dictionaryOptionPannel = new Ext.Panel({
				id : 'dictionaryOptionPannel',
				layout : 'fit',
				autoHeight : false,
				height : document.body.clientHeight - 60,
				autoWidth : true,
				autoScroll : false,
				frame : true,
				renderTo : 'dictionaryOption',
				items : dictionaryOptionGrid
			})
	var dictionaryGridOnClick = function(dictionaryGrid, rowIndex, e) {
		var selectionModel = dictionaryGrid.getSelectionModel();
		var record = selectionModel.getSelected();
		var dictionaryId = record.get("id");
		document.getElementById("dictionaryId").value = dictionaryId;
		document.getElementById("dictionaryOptionId").value = "";
		jStore_option.baseParams.dictionaryId = dictionaryId;
		jStore_option.on('beforeload', function(store, options) {
					var new_params = {
						dictionaryId : dictionaryId
					};
					Ext.apply(options.params, new_params);
				});
		jStore_option.removeAll();
		jStore_option.reload();
	}

	dictionaryGrid.addListener('rowclick', dictionaryGridOnClick);
	var dictionaryGridOnDbClick = function(dictionaryGrid, rowIndex, e) {
		var selectionModel = dictionaryGrid.getSelectionModel();
		var record = selectionModel.getSelected();
		var dictionaryId = record.get("id");
		document.getElementById("dictionaryId").value = dictionaryId;
		document.getElementById("dictionaryOptionId").value = "";
		jStore_option.baseParams.dictionaryId = dictionaryId;
		jStore_option.on('beforeload', function(store, options) {
					var new_params = {
						dictionaryId : dictionaryId
					};
					Ext.apply(options.params, new_params);
				});
		jStore_option.removeAll();
		jStore_option.reload();
	}

	dictionaryGrid.addListener('rowdblclick', dictionaryGridOnDbClick);
	
	
	
	var dictionaryOptionGridOnClick = function(dictionaryOptionGrid, rowIndex,
			e) {
		var selectionModel = dictionaryOptionGrid.getSelectionModel();
		var record = selectionModel.getSelected();
		document.getElementById("dictionaryOptionId").value = record.get("id");
		document.getElementById("dictionaryOptionValue").value = record
				.get("value");
		document.getElementById("sortorder").value = record.get("sortorder");
		document.getElementById("dictionaryOptionRemarks").value = record
				.get("remarks");
	}

	dictionaryOptionGrid.addListener('rowclick', dictionaryOptionGridOnClick);
	function submitForm(as_ActionName) {
		document.all.form_post.action = as_ActionName;
		document.all.form_post.submit();
	}

	// 点鼠标左链出现
	var onContextMenu = function(e) {
		var dictionaryId = document.getElementById("dictionaryId").value;
		//window.event.returnValue = false;
		if (dictionaryId == "") {
			Ext.ux.Toast.msg('系统提示', '请先选择字典!');
			return;
		}
		var setCatalogMenu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '添加字典项',
								handler : function() {
									uf_addDictionaryOption();
								}
							}, "-", {
								iconCls : 'updateIcon',
								text : '修改字典项',
								handler : function() {
									uf_updateDictionaryOption();
								}
							}, "-", {
								iconCls : 'deleteIcon',
								text : '删除字典项',
								handler : function() {
									uf_deleteOrRestoreDictionaryOption();
								}
							}]
				})
		setCatalogMenu.showAt(e.getXY());
	}

	dictionaryOptionGrid.addListener('contextmenu', onContextMenu);

	function uf_addDictionaryOption() {
		var dictionaryId = document.getElementById("dictionaryId").value;
//		window.event.returnValue = false;
		if (dictionaryId == "") {
			Ext.ux.Toast.msg('系统提示', '请先选择字典!');
			return;
		}

		var formPanel = new Ext.FormPanel({
			id : 'formPanelTest',
			labelAlign : 'right',
			buttonAlign : 'center',
			url : 'addOption.do',
			bodyStyle : 'padding:10px 25px 10px',
			labelWidth : 110,
			frame : true,
			waitMsgTarget : true,
			monitorValid : true,
			width : 500,
			items : [{
						xtype : 'textfield',
						fieldLabel : '字典项 ',
						name : 'value',
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						maxLength : 55
					}, {
						xtype : 'textfield',
						fieldLabel : '显示顺序 ',
						name : 'sortorder',
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						regex : /^[0-9]+$/,
						regexText : '请输入数字',
						maxLength : 55
					}, {
						xtype : 'textarea',
						fieldLabel : '备注 ',
						name : 'remarks',
						width : 280,
						maxLength : 1000
					}, {
						xtype : 'hidden',
						fieldLabel : '字典ID ',
						name : 'dictionaryId',
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						value : dictionaryId,
						maxLength : 45
					}],
			buttons : [{
				text : '提交',
				formBind : true,
				iconCls : 'submitIcon',
				handler : function(form, action) {
					Ext.getCmp('formPanelTest').getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function() {
							Ext.Msg.alert('状态', '添加成功!', function(btn, text) {
										if (btn == 'ok') {
											jStore_option.reload();
											adwin.destroy();
										}
									});
						},
						failure : function(form, action) {
							Ext.Msg
									.alert(
											'状态',
											Ext
													.decode(action.response.responseText).msg);
						}
					})
				}
			}, {

				text : '取消',
				handler : function() {
					adwin.destroy();
				}

			}]
		})
		var adwin = new Ext.Window({
					id : 'uwin',
					layout : 'fit',
					title : '添加字典项',
					width : 500,
					height : 200,
					minimizable : true,
					modal : true,
					items : [formPanel]
				})
		adwin.show();
	}

	function uf_updateDictionaryOption() {
		var dictionaryId = document.getElementById("dictionaryId").value;
		//window.event.returnValue = false;
		if (dictionaryId == "") {
			Ext.ux.Toast.msg('系统提示', '请先选择字典!');
			return;
		}
		var dictionaryOptionId = document.getElementById("dictionaryOptionId").value;
		var dictionaryOptionValue = document
				.getElementById("dictionaryOptionValue").value;
		var sortorder = document.getElementById("sortorder").value;
		var dictionaryOptionRemarks = document
				.getElementById("dictionaryOptionRemarks").value;
		if (dictionaryOptionId == "" || dictionaryOptionValue == "") {
			Ext.ux.Toast.msg("系统提示", "请选择一条纪录．");
			return;
		}
		var formPanel = new Ext.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			url : 'updateOption.do',
			bodyStyle : 'padding:10px 25px 10px',
			labelWidth : 110,
			frame : true,
			waitMsgTarget : true,
			monitorValid : true,
			width : 500,
			items : [{
						xtype : 'textfield',
						fieldLabel : '字典项 ',
						name : 'value',
						value : dictionaryOptionValue,
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						maxLength : 55
					}, {
						xtype : 'textfield',
						fieldLabel : '显示顺序 ',
						name : 'sortorder',
						width : 280,
						value : sortorder,
						allowBlank : false,
						blankText : '必填信息',
						regex : /^[0-9]+$/,
						regexText : '请输入数字',
						maxLength : 55
					}, {
						xtype : 'textarea',
						fieldLabel : '备　　注 ',
						name : 'remarks',
						width : 280,
						value : dictionaryOptionRemarks,
						allowBlank : true
					}, {
						xtype : 'hidden',
						fieldLabel : '字典ID ',
						name : 'dictionaryId',
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						value : dictionaryId,
						maxLength : 45
					}, {
						xtype : 'hidden',
						fieldLabel : '字典项ID ',
						name : 'dictionaryOptionId',
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						value : dictionaryOptionId,
						maxLength : 45
					}],
			buttons : [{
				text : '提交',
				iconCls : 'submitIcon',
				formBind : true,
				handler : function() {
					formPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function() {
							Ext.Msg.alert('状态', '修改成功!', function(btn, text) {
								if (btn == 'ok') {
									jStore_option.reload();
									adwin.destroy();
									document
											.getElementById("dictionaryOptionId").value = "";
								}
							});
						},
						failure : function(form, action) {
							Ext.Msg
									.alert(
											'状态',
											Ext
													.decode(action.response.responseText).msg);
						}
					})
				}
			}, {

				text : '取消',
				handler : function() {
					adwin.destroy();
				}

			}]

		})
		var adwin = new Ext.Window({
					id : 'uwin',
					layout : 'fit',
					title : '修改字典项',
					width : 500,
					height : 200,
					minimizable : true,
					modal : true,
					items : [formPanel]
				})
		adwin.show();
	}

	function uf_addDic() {
		var formPanel = new Ext.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			url : 'addDic.do',
			bodyStyle : 'padding:10px 25px 10px',
			labelWidth : 110,
			frame : true,
			// title : '增加字典类型',
			waitMsgTarget : true,
			monitorValid : true,
			width : 500,
			items : [{
						xtype : 'textfield',
						fieldLabel : '字典类别 ',
						name : 'type',
						width : 280,
						allowBlank : false,
						blankText : '必填信息',
						maxLength : 22

					}, {
						xtype : 'textarea',
						fieldLabel : '备　　注 ',
						name : 'remarks',
						width : 280,
						allowBlank : true
					}],
			buttons : [{
				text : '提交',
				iconCls : 'submitIcon',
				formBind : true,
				handler : function() {
					formPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function() {
							Ext.Msg.alert('状态', '添加成功!', function(btn, text) {
										if (btn == 'ok') {
											jStore_dictionary.reload();
											adwin.destroy();
										}
									});
						},
						failure : function(form, action) {
							Ext.Msg
									.alert(
											'状态',
											Ext
													.decode(action.response.responseText).msg);
						}
					})
				}
			}, {

				text : '取消',
				handler : function() {
					adwin.destroy();
				}

			}]

		})
		var adwin = new Ext.Window({
					id : 'uwin',
					layout : 'fit',
					title : '添加字典类别',
					width : 500,
					height : 200,
					minimizable : true,
					modal : true,
					items : [formPanel]
				})
		adwin.show();
	}

	function uf_deleteDic() {
		var dictionaryId = document.getElementById("dictionaryId").value;
		var deletfuntion = function(btn) {
			if (btn != 'yes')
				return;
			Ext.Ajax.request({
						url : 'deleteDic.do',
						method : 'POST',
						params : {
							dictionaryId : dictionaryId
						},
						success : function(response, request) {
							var msg = response.responseText;
							document.getElementById("dictionaryId").value = "";
							document.getElementById("dictionaryOptionId").value = "";
							if (msg != "") {
								Ext.ux.Toast.msg("系统提示", response.responseText);
								return;
							}
							jStore_dictionary.removeAll();
							jStore_option.removeAll();
							jStore_dictionary.reload();
							jStore_option.reload();
						}
					});
		};
		if (dictionaryId == "" || dictionaryId == null) {
			Ext.ux.Toast.msg("系统提示", "请选择一条记录!");
			return;
		}
		var deleteconfirm1 = function(btn) {
			if (btn != 'yes') {
				return;
			}
			Ext.MessageBox.confirm('系统提示',
					'删除选中数据字典后，该数据字典及其下所有子字典的数据都将会删除，你确定要删除吗？', deleteconfirm2);
		}
		var deleteconfirm2 = function(btn) {
			if (btn != 'yes') {
				return;
			}
			Ext.MessageBox.confirm('系统提示',
					'如果将这些字典删除，可能会影响到项目中所有引用了该数据字典的功能模块！您确认要执行这些操作吗?',
					deleteconfirm3);
		}

		var deleteconfirm3 = function(btn) {
			if (btn != 'yes') {
				return;
			}
			Ext.MessageBox.confirm('系统提示', '此删除为完全删除,请最后一次确认您的操作，删除后该记录将无法还原!',
					deletfuntion);
		}

		Ext.MessageBox.confirm('系统提示', '你确定要删除选中的数据字典内容吗？删除后可能带来意想不到的后果！',
				deleteconfirm1);
	}

	function uf_reStoreDic() {
		var dictionaryId = document.getElementById("dictionaryId").value;
		if (dictionaryId == "" || dictionaryId == null) {
			alert("请选择一条记录。");
			return;
		}
		Ext.Ajax.request({
					url : 'reStoreDic.do',
					method : 'POST',
					params : {
						dictionaryId : dictionaryId
					},
					success : function(response, request) {
						var msg = response.responseText;
						document.getElementById("dictionaryId").value = "";
						document.getElementById("dictionaryOptionId").value = "";
						if (msg != "") {
							Ext.ux.Toast.msg("系统提示", response.responseText);
							return;
						}
						jStore_dictionary.removeAll();
						jStore_option.removeAll();
						jStore_dictionary.reload();
						jStore_option.reload();
					}
				});
	}

	// 删除数据字典
	function uf_deleteOrRestoreDictionaryOption() {
		var dictionaryId = document.getElementById("dictionaryId").value;
		
		var dictionaryOptionId = document.getElementById("dictionaryOptionId").value;
		//window.event.returnValue = false;
		if (dictionaryOptionId == "" || dictionaryOptionId == null) {
			Ext.ux.Toast.msg('系统提示', '请选择一条记录!');
			return;
		}
		
		var deletfuntion = function(btn) {
			if (btn != 'yes')
				return;
			Ext.Ajax.request({
						url : 'deleteOrRestoreDictionaryOption.do',
						method : 'POST',
						params : {
							delOrRestore : 1,
							dictionaryOptionId : dictionaryOptionId,
							dictionaryId : dictionaryId
						},
						success : function(response, request) {
							var msg = response.responseText;
							document.getElementById("dictionaryOptionId").value = "";
							if (msg != "") {
								Ext.ux.Toast.msg("错误提示", response.responseText);
								return;
							}
							jStore_dictionary.removeAll();
							jStore_option.removeAll();
							jStore_dictionary.reload();
							jStore_option.reload();
						}
					});
		}

		var deleteconfirm1 = function(btn) {
			if (btn != 'yes') {
				return;
			}
			Ext.MessageBox.confirm('系统提示', '此删除为完全删除,请最后一次确认您的操作，删除后该记录将无法还原',
					deletfuntion);
		}

		Ext.MessageBox.confirm('系统提示',
				'如果将此选项删除，可能会影响到项目中所有引用了该数据字典的功能模块！你确定要删除吗？', deleteconfirm1);
	}

	// 更新数据字典
	function uf_updateDic() {
		var dictionaryId = document.getElementById("dictionaryId").value;
		if (dictionaryId == "" || dictionaryId == null) {
			Ext.ux.Toast.msg("系统提示","请选择一个数据字典。");
			return;
		} else {
			Ext.Ajax.request({
				url : 'getDicById.do',
				method : 'POST',
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					var _update_panel = new Ext.FormPanel({
						url : 'updateDic.do',
						labelAlign : 'right',
						buttonAlign : 'center',
						bodyStyle : 'padding:10px 25px 10px',
						height : 200,
						frame : true,
						labelWidth : 110,
						monitorValid : true,
						width : 400,
						items : [{
									xtype : 'textfield',
									fieldLabel : '字典类别',
									name : 'type',
									value : obj.data.type,
									width : 280,
									allowBlank : false,
									maxLength : 22,
									blankText : '必填信息'
								}, {
									xtype : 'textarea',
									fieldLabel : '备　　注 ',
									name : 'remarks',
									width : 280,
									allowBlank : true,
									value : obj.data.remarks
								}, {
									xtype : 'hidden',
									fieldLabel : 'dictionaryId',
									name : 'dictionaryId',
									width : 150,
									allowBlank : true,
									value : obj.data.id
								}],
						buttons : [{
							text : '提交',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								_update_panel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form, action) {
										var msg = Ext
												.decode(action.response.responseText).msg;
										if (Ext
												.decode(action.response.responseText).success) {
											Ext.Msg.alert('系统提示', '修改成功!',
													function(btn, text) {
														jStore_dictionary
																.reload();
														UTwin.destroy();
													});
										}
									},
									failure : function(form, action) {
										var msg = Ext
												.decode(action.response.responseText).msg;
										Ext.ux.Toast.msg('系统提示', msg);
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
								title : '修改字典',
								width : 500,
								height : 200,
								minimizable : true,
								modal : true,
								items : [_update_panel]
							})
					UTwin.show();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					dictionaryId : dictionaryId
				}
			})
		}
	}

	// 查询
	function uf_search() {
		var type = document.getElementById("type").value;
		jStore_option.reload();
		Ext.Ajax.request({
					url : 'search.do',
					method : 'POST',
					params : {
						type : type
					},
					success : function(response, request) {
						jStore_dictionary.removeAll();
						jStore_dictionary.loadData(Ext
								.decode(response.responseText));
					}
				});
	}

	new Ext.Button({
				text : '添加字典',
				handler : uf_addDic,
				iconCls : 'addIcon'
			}).render(document.body, 'btn_add');

	new Ext.Button({
				text : '修改字典',
				handler : uf_updateDic,
				iconCls : 'updateIcon'
			}).render(document.body, 'btn_update');

	new Ext.Button({
				text : '删除字典',
				handler : uf_deleteDic,
				iconCls : 'deleteIcon'
			}).render(document.body, 'btn_delete');

	new Ext.Button({
				text : '查找',
				handler : uf_search,
				iconCls : 'searchIcon'
			}).render(document.body, 'btn_search');

	new Ext.Toolbar.Separator({}).render(document.body, 'btn_split1');

	new Ext.Toolbar.Separator({}).render(document.body, 'btn_split2');

	new Ext.Button({
				text : '添加数据字典项',
				handler : uf_addDictionaryOption,
				iconCls : 'addIcon'
			}).render(document.body, 'btn_chadd');

	new Ext.Button({
				text : '修改数据字典项',
				handler : uf_updateDictionaryOption,
				iconCls : 'updateIcon'
			}).render(document.body, 'btn_chedit');

	new Ext.Button({
				text : '删除数据字典项',
				handler : uf_deleteOrRestoreDictionaryOption,
				iconCls : 'deleteIcon'
			}).render(document.body, 'btn_chdelete');

});