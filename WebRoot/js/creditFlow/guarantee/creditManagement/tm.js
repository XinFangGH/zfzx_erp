/**
 * 分类管理
 * 
 * @class ProModalManager
 * @extends Ext.Panel
 */
TemplateManagement = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		TemplateManagement.superclass.constructor.call(this, {
					id : 'TemplateManagement',
					height : 450,
					autoScroll : true,
					layout : 'border',
					title : '评估模板管理',
					iconCls : "btn-team36",
					items : [this.leftPanel, this.centerPanel]
				});
	},
	initUIComponents : function() {

		var autoHight = Ext.getBody().getHeight();
		var autoWidth = Ext.getBody().getWidth();
		var dic_TreePanel;
		var nodeSelected;
		var dic_Root;

		var reader = new Ext.data.JsonReader({
					root : 'topics',
					totalProperty : 'totalProperty'
				}, [{
							name : 'id'
						}, {
							name : 'indicatorTypeId'
						}, {
							name : 'indicatorType'
						}, {
							name : 'indicatorName'
						}, {
							name : 'parentIndicatorId'

						}, {
							name : 'templateId'

						}, {
							name : 'quanzhong'
						}, {
							name : 'indicatorDesc'
						}, {
							name : 'creater'
						}, {
							name : 'maxScore'
						}, {
							name : 'minScore'
						}, {
							name : 'qxScore'
						}, {
							name : 'qnScore'
						}, {
							name : 'xuhao'
						}, {
							name : 'modifer'
						}, {
							name : 'typeNum'
						}, {
							name : 'indicatorNum'
						}]);

		this.store = new Ext.data.GroupingStore({
			url : __ctxPath + '/credit/creditmanagement/templateContentList.do',
			reader : reader,
			sortInfo : {
				field : 'indicatorTypeId',
				direction : "DESC"
			},
			groupField : 'indicatorTypeId'
		});

		var templateId = 0;
		var templateName = "";

		Ext.form.Field.prototype.msgTarget = 'side';

		if (typeof(AreaDicAjaxServ) == "undefined") {
			// Ext.Msg.alert("警告提示","请先设置DWR，并实例化AreaDicAjaxServ");
		} else {
			mgrInit.setMgr(AreaDicAjaxServ);
			// Ext.Msg.alert("AreaDicAjaxServ设置成功");
			// Ext.Msg.alert(mgr);
		}

		var dic_JStore = new Ext.data.JsonStore({
					url : __ctxPath
							+ '/creditFlow/creditmanagement/rtListRatingTemplate.do',
					root : 'topics',
					totalProperty : 'totalProperty',
					fields : [{
								id : 'id',
								name : 'id'
							}, {
								id : 'templateName',
								name : 'templateName'
							}, {
								id : 'customerType',
								name : 'customerType'
							}, {
								id : 'applyPoint',
								name : 'applyPoint'
							}, {
								id : 'careteTime',
								name : 'careteTime'
							}, {
								id : 'subTemplateIndicator',
								name : 'subTemplateIndicator'
							}, {
								name : 'className'
							}]
				});
		dic_JStore.load();

		var dic_CModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "模板名称",
					width : 100,
					sortable : true,
					dataIndex : 'templateName'
				}, {
					header : "客户类型",
					width : 60,
					sortable : true,
					dataIndex : 'customerType'
				}, {
					header : "适用特征",
					width : 100,
					sortable : true,
					dataIndex : 'applyPoint',
					renderer : function(data, metadata, record, rowIndex,
							columnIndex, store) {
						metadata.attr = ' ext:qtip="' + data + '"';
						return data;
					}
				}, {
					header : "信用评级标准",
					width : 100,
					sortable : true,
					dataIndex : 'className',
					renderer : function(data, metadata, record, rowIndex,
							columnIndex, store) {
						metadata.attr = ' ext:qtip="' + data + '"';
						return data;
					}
				}]);
		var myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});

		var button_count = new Ext.Button({
			text : '计算模板分数',
			tooltip : '为该模板添加相应指标',
			iconCls : 'searchIcon',
			scope : this,
			handler : function() {
				var selected = dic_GPanel.getSelectionModel().getSelected();

				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一种资信模版!');
				} else {
					var id = selected.get('id');
					var text = selected.get('templateName');
					Ext.Ajax.request({
						url : __ctxPath
								+ '/creditFlow/creditmanagement/countTemplateScoreRatingTemplate.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							Ext.getCmp('label_temp').setText('[' + text + ']'
									+ '分数：');
							Ext.getCmp('label_score')
									.setText('<font color=red>'
											+ obj.data.score + '</font>');
						},
						failure : function(result, action) {
							Ext.ux.Toast.msg('状态', '操作失败!');
						},
						params : {
							id : id
						}
					});

				}
			}

		});
		this.dic_tbar = new Ext.Toolbar({
					items : [{
								xtype : 'button',
								text : '增加',
								scope : this,
								tooltip : '增加模板',
								iconCls : 'addIcon',
								hidden : isGranted('_Template_add')
										? false
										: true,
								handler : this.addTemplate
							}, {
								text : '修改',
								tooltip : '修改选中的模板',
								iconCls : 'updateIcon',
								scope : this,
								hidden : isGranted('_Template_update')
										? false
										: true,
								handler : this.updateTemplate
							}, {
								text : '删除',
								tooltip : '删除选中模版',
								iconCls : 'deleteIcon',
								scope : this,
								hidden : isGranted('_Template_delete')
										? false
										: true,
								handler : this.deleteTemplate

							}, {
								text : '查看',
								tooltip : '查看选中模版',
								iconCls : 'deleteIcon',
								scope : this,
								hidden : isGranted('_Template_see')
										? false
										: true,
								handler : this.seeTemplate
							}]
				})

		this.tbar = new Ext.Toolbar({
			items : [{
				xtype : 'label',
				html : '<pre><font color="blue">模板名称：      权重前最高总分：       权重前最低总分：     权重后最高总分：       权重后最低总分:</font></pre>'
			}]
		});
		this.dic_GPanel = new Ext.grid.GridPanel({
			id : 'dic_GPanel',
			height : Ext.getBody().getHeight() - 30,
			store : dic_JStore,
			frame : false,
			iconCls : 'icon-grid',
			colModel : dic_CModel,
			autoExpandColumn : 3,
			tbar : this.dic_tbar,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			autoScroll : true,
			loadMask : myMask,
			listeners : {
				scope : this,
				'rowclick' : function(grid, index, e) {
					templateId = grid.getSelectionModel().getSelected()
							.get('id');
					templateName = grid.getSelectionModel().getSelected()
							.get('templateName')
					this.store.load({
								params : {
									id : templateId
								}
							});
					var tbar = this.getTopToolbar();
					var store = this.store
					this.store.on('load', function() {
						tbar.items
								.get(0)
								.setText(
										'<pre><font color="blue">模板名称：</font><font color="red">'
												+ templateName
												+ '</font>      <font color="blue">权重前最高总分：</font><font color="red">'
												+ store.getAt(0).data.maxScore
												+ '</font>     <font color="blue">权重前最低总分：</font><font color="red">'
												+ store.getAt(0).data.minScore
												+ '</font>    <font color="blue">权重后最高总分：</font><font color="red">'
												+ store.getAt(store
														.getTotalCount()
														- 1).data.qxScore
												+ '</font>     <font color="blue">权重后最低总分：</font><font color="red">'
												+ store.getAt(store
														.getTotalCount()
														- 1).data.qnScore
												+ '</font>    <font color="blue">指标类型数：</font><font color="red">'
												+ store.getAt(store
														.getTotalCount()
														- 1).data.typeNum
												+ '</font>     <font color="blue">指标数：</font><font color="red">'
												+ store.getAt(store
														.getTotalCount()
														- 1).data.indicatorNum
												+ '</font></pre>', false)
					})
				}
			} //

		});
		var indicatorType = function(data, cellmeta, record) {

			return record.get("indicatorType");

		}
		this.cm = new Ext.grid.ColumnModel([{
					header : '序号',
					width : 30,
					dataIndex : 'xuhao'
				}, {
					header : "要素名称",
					width : 120,
					dataIndex : 'indicatorTypeId',
					hidden : true,
					renderer : indicatorType
				}, {
					header : "指标名称",
					width : 150,
					sortable : true,
					dataIndex : 'indicatorName'
				}, {
					header : "指标选项",
					width : 260,
					sortable : true,
					dataIndex : 'indicatorDesc',
					renderer : function(data, metadata, record, rowIndex,
							columnIndex, store) {
						metadata.attr = ' ext:qtip="' + data + '"';
						return data;
					}
				}, {
					header : "分值",
					width : 40,
					sortable : true,
					align : 'right',
					dataIndex : 'creater'
				}, {
					header : "权重",
					width : 50,
					sortable : true,
					dataIndex : 'quanzhong',
					align : 'center',
					editor : {
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						width : 50,
						allowBlank : false,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["1", "1"], ["2", "2"], ["3", "3"],
											["4", "4"], ["5", "5"], ["6", "6"],
											["7", "7"], ["8", "8"], ["9", "9"],
											["10", "10"]]
								}),
						triggerAction : "all"

					}
				}, {
					header : "权重后的分值",
					width : 70,
					sortable : true,
					align : 'right',
					dataIndex : 'modifer'

				}]);

		var button_updateOptions = new Ext.Button({
					text : '修改指标项',
					tooltip : '修改指标项',
					iconCls : 'updateIcon',
					scope : this,
					handler : function() {
						var selected = dic_groupPan.getSelectionModel()
								.getSelected();// 选中当前行
						if (null == selected) {
							Ext.ux.Toast.msg('状态', '请选择一条记录!');
						} else {
							editCatalogType(selected.get('id'));
						}

					}
				});

		this.materialsItemListToolbar = new Ext.Toolbar({
					items : [{
								xtype : 'button',
								text : '增加指标',
								tooltip : '增加该模版指标项',
								iconCls : 'addIcon',
								scope : this,
								hidden : isGranted('_Indicator_add')
										? false
										: true,
								handler : function() {
									this.addTemplateIndicator(templateId)
								}
							}, {
								xtype : 'button',
								text : '删除指标',
								tooltip : '删除该模版下的指标',
								iconCls : 'deleteIcon',
								scope : this,
								hidden : isGranted('_Indicator_delete')
										? false
										: true,
								handler : function() {
									this.deleteTemplateIndicator(templateId)
								}
							}, {
								xtype : 'button',
								text : '保存',
								tooltip : '保存权重',
								iconCls : 'deleteIcon',
								scope : this,
								hidden : isGranted('_Indicator_save')
										? false
										: true,
								handler : function() {
									this.save(templateId)
								}
							}]
				});
		this.centerPanel = new Ext.grid.EditorGridPanel({
			id : 'materialsItemGrid',
			region : 'center',
			store : this.store,
			cm : this.cm,
			clicksToEdit : 1,
			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "项" : "项"]})'
			}),
			frame : false,
			selModel : new Ext.grid.RowSelectionModel(),
			height : Ext.getBody().getHeight() - 30,
			animCollapse : false,
			stripeRows : true,
			iconCls : 'icon-grid',
			autoScroll : true,
			tbar : this.materialsItemListToolbar
		});

		var afteredit = function(obj) {
			var id = obj.record.get("id");
			var value = obj.record.get("receive");
			hashMap.put(id, value);
		}
		this.centerPanel.addListener('afteredit', afteredit);

		/*
		 * var dic_Viewport = new Ext.Viewport({ enableTabScroll : true, layout :
		 * 'fit', items : [{ region : "center", layout : 'fit', items :
		 * [dic_Panel] }] });
		 */

		// 添加目录，添加叶子
		function editCatalogType(id) {
			// var id = node.id;
			// templateIndicatorId = id.substring(1);
			templateIndicatorId = id;
			jStore_option.removeAll();
			jStore_option.reload({
						params : {
							templateIndicatorId : id
						}
					});
			win_option.show();

		}

		this.leftPanel = new Ext.Panel({
					title : '模板分类',
					region : 'west',
					layout : 'anchor',
					collapsible : true,
					split : true,
					width : 300,
					items : [this.dic_GPanel]
				});

	},

	addTemplate : function() {
		var dic_GPanel = this.dic_GPanel;
		var mytree = new Ext.tree.TreePanel({
			id : 'mytree',
			animate : true,
			collapsible : true,
			rootVisible : false,
			autoScroll : true,
			height : 340,
			header : false,
			// width : 350,
			anchor : '100%',
			frame : true,
			
//				animate : true,
//						collapsible : true,
//						rootVisible : false,
//						autoScroll : true,
//						height : 380,
//						header : false,
//						anchor : '100%',
//						frame : true,
//						
			loader : new Ext.tree.TreeLoader({
						autoLoad : true,
						dataUrl : __ctxPath
								+ '/creditFlow/creditmanagement/getCheckTreeIndicator.do',
						listeners : {
							'beforeload' : function() {
								alert("ck")
							},
							'load' : function() {
								alert("ckx")
							}
						}

					}),
			root : new Ext.tree.AsyncTreeNode({
						id : '0',
						text : '根结点'
					}),
			lines : true

		});

		var formPanel = new Ext.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			url : __ctxPath + '/creditFlow/creditmanagement/addRatingTemplate.do',
			// bodyStyle:'padding:25px 25px 25px',
			labelWidth : 100,
			frame : true,
			waitMsgTarget : true,
			monitorValid : true,
			width : 800,
			layout : 'column',
			items : [{
				columnWidth : 1,
				layout : 'form',
				items : [{
							xtype : 'radiogroup',
							fieldLabel : '评估类型',
							name : 'ptype',
							items : [{
										boxLabel : '定性',
										name : 'ratingTemplate.ptype',
										inputValue : 'dx',
										checked : true
									}, {
										boxLabel : '定量',
										name : 'ratingTemplate.ptype',
										inputValue : 'dl'
									}],
							listeners : {
								change : function(checkbox, checked) {// 这事件是当radiogroup的值发生改变时进入
									var childs = Ext.getCmp("mytree").root.childNodes;
									var val = checked.inputValue;
									for (var i = 0; i < childs.length; i++) {
										if (childs[i].attributes.type == val) {
											childs[i].ui.show();
										} else {
											childs[i].ui.hide();
										}

									}

									var nodes = Ext.getCmp('mytree')
											.getChecked();
									if (nodes && nodes.length) {
										for (var i = 0; i < nodes.length; i++) {
											// 设置UI状态为未选中状态
											nodes[i].getUI().toggleCheck(false);
											// 设置节点属性为未选中状态
											nodes[i].attributes.checked = false;
										}
									}

								}
							}
						}]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [{
							xtype : 'radiogroup',
							fieldLabel : '客户类型',
							name : 'customerType',
							items : [{
										boxLabel : '企业',
										name : 'ratingTemplate.customerType',
										inputValue : '企业',
										checked : true
									}, {
										boxLabel : '个人',
										name : 'ratingTemplate.customerType',
										inputValue : '个人'
									}],
							listeners : {
								change : function(checkbox, checked) {// 这事件是当radiogroup的值发生改变时进入

									var nodes = Ext.getCmp('mytree')
											.getChecked();
									if (nodes && nodes.length) {
										for (var i = 0; i < nodes.length; i++) {
											// 设置UI状态为未选中状态
											nodes[i].getUI().toggleCheck(false);
											// 设置节点属性为未选中状态
											nodes[i].attributes.checked = false;
										}
									}

									var childs = Ext.getCmp("mytree").root.childNodes;
									if (checked.inputValue == "企业") {
										var val = "qy";
									} else {
										var val = "gr";
									}

									for (var i = 0; i < childs.length; i++) { // 大文件夹
										var childi = childs[i].childNodes;
										for (var j = 0; j < childi.length; j++) {
											var childk = childi[j].childNodes;

											for (var k = 0; k < childk.length; k++) { // 所有文件
												if (childk[k].attributes.type == val) {
													childk[k].ui.show();
												} else {
													childk[k].ui.hide();
												}

											}

										}
									}
								}
							}
						}]

			}, {
				columnWidth : 0.33,
				layout : 'form',
				items : [{
							xtype : 'textfield',
							fieldLabel : '模板名称 ',
							name : 'ratingTemplate.templateName',
							allowBlank : false,
							blankText : '必填信息',
							anchor : '100%'

						}]
			}, {
				columnWidth : 0.33,
				layout : 'form',
				items : [{

							xtype : 'textfield',
							fieldLabel : '适用特性 ',
							name : 'ratingTemplate.applyPoint',
							allowBlank : false,
							blankText : '必填信息',
							anchor : '100%'

						}, {
							id : 'idArray',
							xtype : 'hidden',
							name : 'idArray'
						}]
			}, {
				columnWidth : 0.33,
				layout : 'form',
				items : [{
							xtype : 'hidden',
							name : 'ratingTemplate.className'
						}, {
							xtype : 'combo',
							fieldLabel : '信用评级标准 ',
							hiddenName : 'ratingTemplate.classId',
							displayField : 'className',
							valueField : 'classId',
							store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath
										+ '/creditFlow/creditmanagement/getAllCLassTypeScoreGradeOfClass.do',
								fields : ['className', 'classId']
							}),
							triggerAction : "all",
							allowBlank : false,
							blankText : '必填信息',
							anchor : '100%',
							listeners : {
								'select' : function(combox, record, index) {
									combox.previousSibling().setValue(record
											.get("className"))
								}
							}

						}]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [{
					id : 'label_add',
					xtype : 'label',
					html : '<pre>        已选择指标类型：0   已选择指标：0   总计最高分：0   总计最低分：0</pre>'

				}]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [mytree]

			}],
			buttons : [{
						text : '提交',
						iconCls : 'submitIcon',
						formBind : true,
						handler : function() {
							var b = mytree.getChecked();
							var checkid = new Array;// 存放选中id的数组
							for (var i = 0; i < b.length; i++) {
								checkid.push(b[i].id);// 添加id到数组
							}
							Ext.getCmp('idArray').setValue(checkid.toString());
							formPanel.getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function() {
											Ext.ux.Toast.msg('状态', '添加成功!')
											dic_GPanel.getStore().reload();
											adwin.destroy();

										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('状态', '添加失败!');
										}
									})
						}
					}]

		});
		mytree.expandAll();

		mytree.on('checkchange', function(node, checked) {
					node.expand();
					node.attributes.checked = checked;
					node.eachChild(function(child) {
								child.ui.toggleCheck(checked);
								child.attributes.checked = checked;
								child.fireEvent('checkchange', child, checked);
							});
					var b = mytree.getChecked();
					var maxScore = 0;
					var minScore = 0;
					var arr = new Array()
					for (var i = 0; i < b.length; i++) {
						arr.push(b[i].parentNode);
						maxScore = parseFloat(maxScore)
								+ parseFloat(b[i].attributes.iconCls)
						minScore = parseFloat(minScore)
								+ parseFloat(b[i].attributes.model)
					}
					var obj = {};
					a = [];

					for (var i = 0; i < arr.length; i++) {
						if (!obj[arr[i]]) {

							obj[arr[i]] = 1;

							a.push(arr[i]);

						}
					}
					Ext.getCmp("label_add").setText(
							"<pre>        已选择指标类型：" + a.length + "   已选择指标："
									+ mytree.getChecked().length + "   总计最高分："
									+ maxScore + "   总计最低分：" + minScore
									+ "</pre>", false)
				}, mytree);

		var adwin = new Ext.Window({
					id : 'uwin',
					layout : 'fit',
					title : '增加模板',
					width : 630,
					height : 500,
					modal : true,
					collapsible : true,
					items : [formPanel]
				})
		adwin.show();
	},
	updateTemplate : function() {
		var selected = this.dic_GPanel.getSelectionModel().getSelected();// 选中当前行
		var dic_GPanel = this.dic_GPanel;
		var store = this.centerPanel.getStore()
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var id = selected.get('id'); // 获得当前行的ID值
			Ext.Ajax.request({
				url : __ctxPath
						+ '/creditFlow/creditmanagement/getInfoRatingTemplate.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);

					var mytree = new Ext.tree.TreePanel({
						animate : true,
						collapsible : true,
						rootVisible : false,
						autoScroll : true,
						height : 340,
						header : false,
						// width : 350,
						anchor : '100%',
						frame : true,
						loader : new Ext.tree.TreeLoader({
							dataUrl : __ctxPath
									+ '/creditFlow/creditmanagement/getCheckTreeIndicator.do?update=1&templateId='
									+ id
						}),
						root : new Ext.tree.AsyncTreeNode({
									id : '0',
									text : '根结点'
								}),
						lines : true,
						listeners : {
							'load' : function() {

								var b = mytree.getChecked();
								var maxScore = 0;
								var minScore = 0;
								var arr = new Array()
								for (var i = 0; i < b.length; i++) {
									arr.push(b[i].parentNode);
									maxScore = parseFloat(maxScore)
											+ parseFloat(b[i].attributes.iconCls)
									minScore = parseFloat(minScore)
											+ parseFloat(b[i].attributes.model)
								}
								var obj = {};
								a = [];

								for (var i = 0; i < arr.length; i++) {
									if (!obj[arr[i]]) {

										obj[arr[i]] = 1;

										a.push(arr[i]);

									}
								}
								Ext.getCmp("label_update").setText(
										"<pre>        已选择指标类型：" + a.length
												+ "   已选择指标："
												+ mytree.getChecked().length
												+ "   总计最高分：" + maxScore
												+ "   总计最低分：" + minScore
												+ "</pre>", false)
							}
						}
					});

					var _update_panel = new Ext.FormPanel({
						url : __ctxPath
								+ '/creditFlow/creditmanagement/updateRatingTemplate.do',
						labelAlign : 'right',
						buttonAlign : 'center',
						// bodyStyle:'padding:25px 25px 25px',
						height : 300,
						frame : true,
						labelWidth : 100,
						monitorValid : true,
						width : 400,
						layout : 'column',
						items : [{
							columnWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'hidden',
										name : 'ratingTemplate.id',
										width : 150,
										value : obj.data.id
									}, {
										xtype : 'radiogroup',
										fieldLabel : '客户类型',
										name : 'customerType',
										items : [{
											boxLabel : '企业',
											name : 'ratingTemplate.customerType',
											inputValue : '企业',
											checked : (obj.data.customerType == '企业')
													? true
													: false
										}, {
											boxLabel : '个人',
											name : 'ratingTemplate.customerType',
											inputValue : '个人',
											checked : (obj.data.customerType == '个人')
													? true
													: false
										}]
									}]
						}, {
							columnWidth : 0.33,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : '模板名称 ',
										name : 'ratingTemplate.templateName',
										allowBlank : false,
										blankText : '必填信息',
										anchor : '100%',
										value : obj.data.templateName

									}]
						}, {
							columnWidth : 0.33,
							layout : 'form',
							items : [{

										xtype : 'textfield',
										fieldLabel : '适用特性 ',
										name : 'ratingTemplate.applyPoint',
										allowBlank : false,
										blankText : '必填信息',
										anchor : '100%',
										value : obj.data.applyPoint

									}, {
										id : 'idArray',
										xtype : 'hidden',
										name : 'idArray'
									}]
						}, {

							columnWidth : 0.33,
							layout : 'form',
							items : [{
										xtype : 'hidden',
										name : 'ratingTemplate.className',
										value : obj.data.className
									}, {
										xtype : 'combo',
										fieldLabel : '信用评级标准 ',
										hiddenName : 'ratingTemplate.classId',
										displayField : 'className',
										valueField : 'classId',
										store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath
													+ '/creditFlow/creditmanagement/getAllCLassTypeScoreGradeOfClass.do',
											fields : ['className', 'classId']
										}),
										triggerAction : "all",
										allowBlank : false,
										blankText : '必填信息',
										anchor : '100%',

										listeners : {
											afterrender : function(combox) {
												combox.setValue()
												var st = combox.getStore();
												st.on("load", function() {

													combox
															.setValue(obj.data.classId);

												})
												combox.clearInvalid();
											},
											'select' : function(combox, record,
													index) {
												combox
														.previousSibling()
														.setValue(record
																.get("className"))
											}
										}

									}]

						}, {
							columnWidth : 1,
							layout : 'form',
							items : [{
								id : 'label_update',
								xtype : 'label',
								html : '<pre>        已选择指标类型：0   已选择指标：0   总计最高分：0   总计最低分：0</pre>'

							}]
						}, {
							columnWidth : 1,
							layout : 'form',
							items : [mytree]

						}],

						buttons : [{
							text : '提交',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								var b = mytree.getChecked();
								var checkid = new Array;// 存放选中id的数组
								for (var i = 0; i < b.length; i++) {
									checkid.push(b[i].id);// 添加id到数组
								}
								// alert(checkid.toString());
								Ext.getCmp('idArray').setValue(checkid
										.toString());
								_update_panel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.ux.Toast.msg('状态', '修改成功!')
										dic_GPanel.getStore().reload();
										store.load({
													params : {
														id : id
													}
												});
										UTwin.destroy();
										Ext.ux.Toast.msg('状态', '修改成功!',
												function(btn, text) {
													dic_GPanel.getStore()
															.reload();
													store.load({
																params : {
																	id : id
																}
															});
													UTwin.destroy();
												});
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
					});
					mytree.expandAll();

					mytree.on('checkchange', function(node, checked) {

								node.expand();
								node.attributes.checked = checked;
								var b = mytree.getChecked();
								var maxScore = 0;
								var minScore = 0;
								var arr = new Array()
								for (var i = 0; i < b.length; i++) {
									arr.push(b[i].parentNode);
									maxScore = parseFloat(maxScore)
											+ parseFloat(b[i].attributes.iconCls)
									minScore = parseFloat(minScore)
											+ parseFloat(b[i].attributes.model)
								}
								var obj = {};
								a = [];

								for (var i = 0; i < arr.length; i++) {
									if (!obj[arr[i]]) {

										obj[arr[i]] = 1;

										a.push(arr[i]);

									}
								}
								Ext.getCmp("label_update").setText(
										"<pre>        已选择指标类型：" + a.length
												+ "   已选择指标："
												+ mytree.getChecked().length
												+ "   总计最高分：" + maxScore
												+ "   总计最低分：" + minScore
												+ "</pre>", false)
								node.eachChild(function(child) {

											child.ui.toggleCheck(checked);
											child.attributes.checked = checked;
											child.fireEvent('checkchange',
													child, checked);

										});
							}, mytree);
					var UTwin = new Ext.Window({
								id : 'win',
								layout : 'fit',
								title : '编辑模板类型',
								width : 630,
								height : 500,
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
					id : id
				}
			})
		}
	},
	deleteTemplate : function() {
		var selected = this.dic_GPanel.getSelectionModel().getSelected();
		var dic_GPanel = this.dic_GPanel
		var centerPanel = this.centerPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var id = selected.get('id');
			Ext.MessageBox.confirm('确认删除', '是否确认执行删除 '
							+ selected.get('templateName'), function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath
										+ '/creditFlow/creditmanagement/deleteRsRatingTemplate.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('状态', '删除成功!');
									dic_GPanel.getStore().reload();
									centerPanel.getStore().load({
												params : {
													id : id
												}
											})
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('状态', '删除失败!');
								},
								params : {
									id : id
								}
							});
						}
					});

		}
	},
	seeTemplate : function() {

		var selected = this.dic_GPanel.getSelectionModel().getSelected();// 选中当前行
		var dic_GPanel = this.dic_GPanel;
		var store = this.centerPanel.getStore()
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var id = selected.get('id'); // 获得当前行的ID值
			Ext.Ajax.request({
				url : __ctxPath
						+ '/creditFlow/creditmanagement/getInfoRatingTemplate.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);

					var mytree = new Ext.tree.TreePanel({
						animate : true,
						collapsible : true,
						rootVisible : false,
						autoScroll : true,
						height : 380,
						header : false,
						anchor : '100%',
						frame : true,
						loader : new Ext.tree.TreeLoader({
							dataUrl : __ctxPath
									+ '/creditFlow/creditmanagement/getCheckTreeIndicator.do?update=1&templateId='
									+ id
						}),
						root : new Ext.tree.AsyncTreeNode({
									id : '0',
									text : '根结点'
								}),
						lines : true,
						listeners : {
							beforeload : function() {
								alert("ckxxx")
							},
							'load' : function() {
								var b = mytree.getChecked();
								var maxScore = 0;
								var minScore = 0;
								var arr = new Array()
								for (var i = 0; i < b.length; i++) {
									arr.push(b[i].parentNode);
									maxScore = parseFloat(maxScore)
											+ parseFloat(b[i].attributes.iconCls)
									minScore = parseFloat(minScore)
											+ parseFloat(b[i].attributes.model)
								}
								var obj = {};
								a = [];

								for (var i = 0; i < arr.length; i++) {
									if (!obj[arr[i]]) {

										obj[arr[i]] = 1;

										a.push(arr[i]);

									}
								}
								Ext.getCmp("label_see").setText(
										"<pre>        已选择指标类型：" + a.length
												+ "   已选择指标："
												+ mytree.getChecked().length
												+ "   总计最高分：" + maxScore
												+ "   总计最低分：" + minScore
												+ "</pre>", false)
							}
						}
					});

					var _update_panel = new Ext.FormPanel({

						labelAlign : 'right',
						buttonAlign : 'center',
						// bodyStyle:'padding:25px 25px 25px',
						height : 300,
						frame : true,
						labelWidth : 100,
						monitorValid : true,
						width : 400,
						layout : 'column',
						items : [{
							columnWidth : 1,
							layout : 'form',
							items : [{
										xtype : 'hidden',
										name : 'ratingTemplate.id',
										width : 150,
										value : obj.data.id
									}, {
										xtype : 'radiogroup',
										fieldLabel : '客户类型',
										name : 'customerType',
										disabled : true,
										items : [{
											boxLabel : '企业',
											name : 'ratingTemplate.customerType',
											inputValue : '企业',
											checked : (obj.data.customerType == '企业')
													? true
													: false
										}, {
											boxLabel : '个人',
											name : 'ratingTemplate.customerType',
											inputValue : '个人',
											checked : (obj.data.customerType == '个人')
													? true
													: false
										}]
									}]
						}, {
							columnWidth : 0.33,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : '模板名称 ',
										name : 'ratingTemplate.templateName',
										allowBlank : false,
										blankText : '必填信息',
										anchor : '100%',
										readOnly : true,
										value : obj.data.templateName

									}]
						}, {
							columnWidth : 0.33,
							layout : 'form',
							items : [{

										xtype : 'textfield',
										fieldLabel : '适用特性 ',
										name : 'ratingTemplate.applyPoint',
										allowBlank : false,
										blankText : '必填信息',
										anchor : '100%',
										readOnly : true,
										value : obj.data.applyPoint

									}, {
										id : 'idArray',
										xtype : 'hidden',
										name : 'idArray'
									}]
						}, {

							columnWidth : 0.33,
							layout : 'form',
							items : [{
										xtype : 'hidden',
										name : 'ratingTemplate.className',
										value : obj.data.className
									}, {
										xtype : 'combo',
										fieldLabel : '信用评级标准 ',
										hiddenName : 'ratingTemplate.classId',
										displayField : 'className',
										valueField : 'classId',
										store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath
													+ '/creditFlow/creditmanagement/getAllCLassTypeScoreGradeOfClass.do',
											fields : ['className', 'classId']
										}),
										triggerAction : "all",
										allowBlank : false,
										blankText : '必填信息',
										anchor : '100%',
										readOnly : true,
										listeners : {
											afterrender : function(combox) {
												combox.setValue()
												var st = combox.getStore();
												st.on("load", function() {

													combox
															.setValue(obj.data.classId);

												})
												combox.clearInvalid();
											},
											'select' : function(combox, record,
													index) {
												combox
														.previousSibling()
														.setValue(record
																.get("className"))
											}
										}

									}]

						}, {
							columnWidth : 1,
							layout : 'form',
							items : [{
								id : 'label_see',
								xtype : 'label',
								html : '<pre>         已选择指标类型：0   已选择指标：0   总计最高分：0   总计最低分：0</pre>'

							}]
						}, {
							columnWidth : 1,
							layout : 'form',
							items : [mytree]

						}]
					});
					mytree.expandAll();
					mytree.on('checkchange', function(node, checked) {
								node.expand();
								node.attributes.checked = checked;
								node.eachChild(function(child) {
											child.ui.toggleCheck(checked);
											child.attributes.checked = checked;
											child.fireEvent('checkchange',
													child, checked);
										});
							}, mytree);
					var UTwin = new Ext.Window({
								id : 'win',
								layout : 'fit',
								title : '查看模板类型',
								width : 630,
								height : 500,
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
					id : id
				}
			})
		}

	},
	addTemplateIndicator : function(templateId) {
		var store = this.centerPanel.getStore()
		if (templateId > 0) {
			var mytree = new Ext.tree.TreePanel({
				animate : true,
				collapsible : true,
				rootVisible : false,
				autoScroll : true,
				height : 265,
				anchor : '100%',
				header : false,
				// width : 350,
				height : 360,
				frame : true,
				loader : new Ext.tree.TreeLoader({
					dataUrl : __ctxPath
							+ '/creditFlow/creditmanagement/getCheckTreeIndicator.do?templateId='
							+ templateId
				}),
				root : new Ext.tree.AsyncTreeNode({
							id : '0',
							text : '根结点'
						}),
				lines : true

			});

			var formPanel = new Ext.FormPanel({
						labelAlign : 'right',
						buttonAlign : 'center',
						url : __ctxPath
								+ '/creditFlow/creditmanagement/addTemplateIndicatorRatingTemplate.do',
						// bodyStyle:'padding:25px 25px 25px',
						labelWidth : 110,
						frame : true,
						border : false,
						waitMsgTarget : true,
						monitorValid : true,
						width : 500,
						height : 410,
						items : [{
									id : 'idArray',
									xtype : 'hidden',
									name : 'idArray'
								}, {
									id : 'id',
									xtype : 'hidden',
									name : 'id',
									value : templateId
								}, mytree],
						buttons : [{
							text : '提交',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								var b = mytree.getChecked();
								var checkid = new Array;// 存放选中id的数组
								for (var i = 0; i < b.length; i++) {
									if (b[i].disabled == false) {
										checkid.push(b[i].id);// 添加id到数组
									}
								}
								// alert(checkid.toString());
								Ext.getCmp('idArray').setValue(checkid
										.toString());
								// alert(checkid.toString());
								if (checkid.toString() != null
										&& checkid.toString() != '') {
									formPanel.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.ux.Toast.msg('状态',
															'添加成功!')
													store.load({
																params : {
																	id : templateId
																}
															});
													adwin.destroy();

												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态',
															'添加失败!');
												}
											})
								} else {
									EExt.ux.Toast
											.msg('状态', '未选中任何指标，请重新选择或取消!');
								}

							}
						}]

					});
			mytree.expandAll();
			mytree.on('checkchange', function(node, checked) {
						node.expand();
						node.attributes.checked = checked;
						node.eachChild(function(child) {
									child.ui.toggleCheck(checked);
									child.attributes.checked = checked;
									child.fireEvent('checkchange', child,
											checked);
								});
					}, mytree);

			var adwin = new Ext.Window({
						id : 'uwin',
						layout : 'fit',
						title : '增加模版指标',
						width : 420,
						// height : 410,
						autoHeight : true,
						modal : true,
						collapsible : true,
						items : [formPanel]
					})
			adwin.show();
		} else {
			Ext.ux.Toast.msg('状态', '请先选择一种模版!');
		}

	},
	deleteTemplateIndicator : function(templateId) {

		var selected = this.centerPanel.getSelectionModel().getSelected();// 选中当前行
		var store = this.centerPanel.getStore()
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			// editCatalogType(selected.get('id'));
			var id = selected.get('id');
			Ext.MessageBox.confirm('确认删除', '是否确认执行删除 '
							+ selected.get('indicatorName'), function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath
										+ '/creditFlow/creditmanagement/deleteTemplateIndicatorRatingTemplate.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('状态', '删除成功!');
									store.load({
												params : {
													id : templateId
												}
											});
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('状态', '删除失败!');
								},
								params : {
									indicatorId : id
								}
							});
						}
					});
		}

	},
	save : function(templateId) {
		var getCenterGridDate = function(grid) {

			if (typeof(grid) == "undefined" || null == grid) {
				return "";
				return false;
			}
			var vRecords = grid.getStore().getRange(0,
					grid.getStore().getCount()); // 得到修改的数据（记录对象）

			var vCount = vRecords.length; // 得到记录长度

			var vDatas = '';
			if (vCount > 0) {
				// begin 将记录对象转换为字符串（json格式的字符串）
				for (var i = 0; i < vCount; i++) {
					var str = Ext.util.JSON.encode(vRecords[i].data);
					var index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					vDatas += str + '@';
				}

				vDatas = vDatas.substr(0, vDatas.length - 1);
			}
			return vDatas;
		}
		var vDatas = getCenterGridDate(this.centerPanel);
		var store = this.centerPanel.getStore()
		Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/creditmanagement/updateTemplateIndicatorRatingTemplate.do',
					method : 'POST',
					params : {
						indicatorStr : vDatas
					},
					success : function(response, request) {
						Ext.ux.Toast.msg('状态', '保存成功!');
						store.load({
									params : {
										id : templateId
									}
								});

					},
					failure : function(result, action) {
						Ext.ux.Toast.msg('状态', '保存失败!');
					}
				})
	}

});