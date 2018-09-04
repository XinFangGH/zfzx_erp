/**
 * @author
 * @class PlOpbankAccountView
 * @extends Ext.Panel
 * @description [PlOpbankAccount]管理
 * @company 智维软件
 * @createtime:
 */
CreditTreeDictionaryView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				

				CreditTreeDictionaryView.superclass.constructor.call(this, {
							id : 'CreditTreeDictionaryView',
							title : '多级数据字典',
							region : 'center',
							layout:'border',
							iconCls : 'menu-dictionarylist',
							items : [this.dic_GPanel,this.dic_Panel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.dic_JStore = new Ext.data.JsonStore({
				url :__ctxPath + '/creditFlow/multiLevelDic/getAllDicTypeAreaDic.do',
				root : 'topics',
				totalProperty : 'totalProperty',
				baseParams : {
					parentId : '0'
				},
				fields : [{
							id : 'id',
							name : 'id'
						}, {
							id : 'text',
							name : 'text'
						}, {
							id : 'lable',
							name : 'lable'
						}, {
							id : 'remarks',
							name : 'remarks'
						},{
							id : 'orderid',
							name : 'orderid',
							defaultSortable :true
						}]
			});
	this.dic_JStore.load();

	var dic_CModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						header : '序号',
						width : 20
					}), {
				header : "字典类型",
				width : 160,
				sortable : true,
				dataIndex : 'text'
			}, {
				header : '字典ID号',
				width : 80,
				dataIndex : 'id'
			}, {
				header : "唯一标识",
				width : 80,
				sortable : true,
				dataIndex : 'lable'
			}, {
				header : "备注",
				width : 100,
				// hidden : true,
				dataIndex : 'remarks'
			}]);
	var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});

	// 同步请求方法
	var Synchronize = function(url) {
		function createXhrObject() {
			var http;
			var activeX = ['MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP',
					'Microsoft.XMLHTTP'];
			try {
				http = new XMLHttpRequest();
			} catch (e) {
				for (var i = 0; i < activeX.length; ++i) {
					try {
						http = new ActiveXObject(activeX[i]);
						break;
					} catch (e) {
					}
				}
			} finally {
				return http;
			}
		};
		var conn = createXhrObject();
		conn.open("GET", url, false);
		conn.send(null);
		if (conn.responseText != '') {
			return Ext.decode(conn.responseText).recordnum;
		} else {
			return null;
		}
	};
		
	this.dic_GPanel = new Ext.grid.GridPanel({
		id : 'dic_GPanel',
		border : false,
		region:'west',
	 	layout:'anchor',
	 	collapsible : true,
		split : true,
		width : 443,
		stateful: true,
        stateId: 'dic_GPanel', 
		tbar : [new Ext.Button({
			text : '添加字典',
			scope : this,
			tooltip : '添加字典类型',
			iconCls : 'btn-add',
			hidden : isGranted('_addzd')?false:true,
			handler : function() {
	          this.adddic();
			}
		}), new Ext.Button({
			text : '修改字典',
			tooltip : '修改选中的字典类别',
			iconCls : 'btn-edit',
			scope : this,
			hidden : isGranted('_editzd')?false:true,
			handler : function() {
				this.updatedic();
			}
		}), new Ext.Button({
			text : '删除字典',
			tooltip : '从数据库中完全删除选中字典类别',
			iconCls : 'btn-del',
			scope : this,
			hidden : isGranted('_removezd')?false:true,
			handler : function() {
               this.deletedic();
			}
		})],
		height : 560,
		store : this.dic_JStore,
		frame : false,
		iconCls : 'icon-grid',
		colModel : dic_CModel,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		listeners : {
			scope :this,
			'rowclick' : function(grid, index, e) {
				var gid = grid.getSelectionModel().getSelected().get('id');
				
				var gtext = grid.getSelectionModel().getSelected().get('text');
				Ext.getCmp("gselectid").value = gid;
				Ext.getCmp("gselecttext").value = gtext;
				Ext.getCmp("gselecttype").value = '0';
				
				var gridtreecmp = Ext.getCmp("gridtreeid");
				var treeload = new Ext.tree.TreeLoader({
							dataUrl : __ctxPath + '/creditFlow/multiLevelDic/getDicTypeTreeAreaDic.do'
						});
				var tree_Root = new Ext.tree.AsyncTreeNode({
							id : gid,
							lable : '字典',
							imgUrl : '',
							parentId : '1921',
							text : '',
							lable : '',
							number : 0,
							remarks : '',
							// leaf : false,
							orderid : '0',
							checked : true,
							isOld : '0'
						});

				var add_button_c = new Ext.Button({
					text : '添加字典项',
					scope : this,
					tooltip : '添加选中字典的字典项',
					iconCls : 'btn-add',
					hidden : isGranted('_adddjzdx')?false:true,
					handler : function() {
					  this.add_button_c();

					}
				});
				var update_button_c = new Ext.Button({
					text : '修改字典项',
					tooltip : '修改选中的字典项',
					iconCls : 'btn-edit',
					scope : this,
					hidden : isGranted('_editdjzdx')?false:true,
					handler : function() {
					this.update_button_c();

					}
				});

				var delete_button_c = new Ext.Button({
					text : '删除字典项',
					tooltip : '从数据库中完全删除选中字典项',
					iconCls : 'btn-del',
					scope : this,
					hidden : isGranted('_removedjzdx')?false:true,
					handler : function() {
						this.delete_button_c();

					}
				});

				var see_button_c = new Ext.Button({
							text : '展开所有结点',
							tooltip : '展开树中所有结点',
							iconCls : 'seeIcon',
							scope : this,
							handler : function() {
								this.tree.expandAll();
							}
						});
				var collapse_button_c = new Ext.Button({
							text : '收起所有结点',
							tooltip : '收起树中所有结点',
							iconCls : 'closeIcon',
							scope : this,
							handler : function() {
								this.tree.collapseAll();
							}
						});
				gridtreecmp.removeAll();

				this.tree = new Ext.ux.tree.TreeGrid({
					id : 'tree_child',
					border : false,
					width : 720,
					tbar : [add_button_c, update_button_c, delete_button_c,
							see_button_c,collapse_button_c],
					height : 560,
					layout : 'fit',
					loader : treeload,
					root : tree_Root,
					animCollapse :true,
					animate : true, 
					loadMask : myMask,
					columns : [{
								header : '数据字典项',
								dataIndex : 'text',
								width : 240
							}, {
								header : '字典ID号',
								width : 80,
								dataIndex : 'id'
							}, {
								header : '子节点个数',
								width : 120,
								dataIndex : 'id',
								tpl : new Ext.XTemplate(
										'{id:this.getChildNum}', {
											getChildNum : function(v) {
												var url = __ctxPath + '/creditFlow/multiLevelDic/getRecordCountByParentIDAreaDic.do?parentId='
														+ v;
												var obj = Synchronize(url);
												return obj;

											}
										})
							}, {
								header : "备注",
								dataIndex : 'remarks',
								width : 280
							}/*
								 * , { header : '显示顺序', width : 80, sortable :
								 * true, dataIndex : 'orderid', hidden : true }
								 */],
					listeners : {
						click : function(n) {
							Ext.getCmp("gselectid").value = n.attributes.id;
							Ext.getCmp("gselecttext").value = n.attributes.text;
							Ext.getCmp("gselecttype").value = '1';
							// Ext.getCmp("dic_GPanel").clearSelections();
						}
					}
				});

				gridtreecmp.add(this.tree);

				this.tree.doLayout();
				gridtreecmp.doLayout();
				this.dic_Panel.doLayout();
			}
		}

	});

	var add_button_o = new Ext.Button({
				text : '添加字典项',
				tooltip : '添加选中字典的字典项',
				iconCls : 'btn-add'
			});
	var update_button_o = new Ext.Button({
				text : '修改字典项',
				tooltip : '修改选中的字典项',
				iconCls : 'btn-edit'
			});

	var delete_button_o = new Ext.Button({
				text : '删除字典项',
				tooltip : '从数据库中完全删除选中字典项',
				iconCls : 'btn-del'
			});
	var see_button_o = new Ext.Button({
				text : '展开所有结点',
				tooltip : '展开树中所有结点',
				iconCls : 'seeIcon'
			});

	var collapse_button_o = new Ext.Button({
				text : '收起所有结点',
				tooltip : '收起树中所有结点',
				iconCls : 'closeIcon'
			});
	this.tree_GPanel = new Ext.ux.tree.TreeGrid({
				tbar : [add_button_o, update_button_o, delete_button_o,see_button_o, collapse_button_o],
				width : 710,
				height : 560,
				layout : 'fit',
				border : false,
				columns : [{
							header : '数据字典项',
							dataIndex : 'text',
							width : 240
						}, {
							header : '字典ID号',
							width : 80,
							dataIndex : 'id'
						}, {
							header : '子节点个数',
							width : 120,
							dataIndex : 'number'
						}, {
							header : "备注",
							dataIndex : 'remarks',
							width : 280
						}/*
							 * , { header : '显示顺序', width : 80, dataIndex :
							 * 'orderid', hidden : true }
							 */]
			});

	this.dic_Panel = new Ext.Panel({
				id : 'dic_Panel',
				region : "center",
				Width : 1210,
				title : '多级数据字典管理',
				autoHeight : true,
		//		autoWidth : true,
				autoScroll : true,
				border : false,
				loadMask : myMask,
				items : [ {
							id : 'gridtreeid',
							items : [this.tree_GPanel]
						}, {
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
			},// end of the initComponents()
		  
	deletedic:function(a){
		     var dic_JStore=this.dic_JStore;
		     var dic_GPanel=this.dic_GPanel;
				var selected = dic_GPanel.getSelectionModel().getSelected();// 选中当前行
				if (null == selected) {
					Ext.MessageBox.alert('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id'); // 获得当前行的ID值

					var deletfuntion = function(btn) {
						if (btn != 'yes')
							return;
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/multiLevelDic/deleteDicTypeAreaDic.do',
							method : 'POST',
							params : {
								id : id
							},
							success : function(response, request) {
								var msg = response.responseText;
								var re = Ext.util.JSON.decode(msg).data;
								if (re == 0) {
									Ext.MessageBox
											.alert("操作提示",
													"字典类型删除失败，只有该字典类型下所有子数据类型被删除后，该数据字典时才能被完全删除！");
								} else {
									Ext.MessageBox.alert("操作提示",
											"删除成功，该数据字典已被完全删除！");
									dic_JStore.removeAll();
									dic_JStore.reload();
								}
							}
						});
					};

					var deleteconfirm1 = function(btn) {
						if (btn != 'yes') {
							return;
						}
						Ext.MessageBox.confirm('系统提示',
								'删除选中数据字典后，该数据字典的数据都将会删除，你确定要删除吗？',
								deleteconfirm2);
					}
					var deleteconfirm2 = function(btn) {
						if (btn != 'yes') {
							return;
						}
						Ext.MessageBox
								.confirm(
										'系统提示',
										'如果将这些字典删除，可能会影响到项目中所有引用了该数据字典的功能模块！您确认要执行这些操作吗?',
										deleteconfirm3);
					}
					var deleteconfirm3 = function(btn) {
						if (btn != 'yes') {
							return;
						}
						Ext.MessageBox.confirm('系统提示',
								'此删除为完全删除,请最后一次确认您的操作，删除后该记录将无法还原!',
								deletfuntion);
					}
					Ext.MessageBox
							.confirm('系统提示',
									'你确定要删除选中的数据字典内容吗？删除后可能给系统带来严重的后果！',
									deleteconfirm1);
				}
		
		},		
				
				
		updatedic:function(){
			var dic_JStore=this.dic_JStore;
			var dic_GPanel=this.dic_GPanel;
		  			var selected = dic_GPanel.getSelectionModel().getSelected();// 选中当前行
				if (null == selected) {
					Ext.MessageBox.alert('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id'); // 获得当前行的ID值
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/multiLevelDic/getDicTypeIdAreaDic.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							var _update_panel = new Ext.FormPanel({
								id : 'multupdatepanel',
								url : __ctxPath + '/creditFlow/multiLevelDic/updateDicTypeAreaDic.do',
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
									fieldLabel : '字典类型',
									name : 'text',
									value : obj.data.text,
									width : 280,
									maxLength : 127,
									allowBlank : false,
									blankText : '必填信息'
								}, {
									xtype : 'textfield',
									fieldLabel : '唯一标识',
									name : 'lable',
									width : 280,
									maxLength : 50,
									allowBlank : false,
									blankText : '必填信息',
									value : obj.data.lable
								}, {
									xtype : 'textfield',
									fieldLabel : '排序号',
									name : 'orderid',
									width : 280,
									maxLength : 50,
									allowBlank : false,
									blankText : '必填信息',
									regex : /^[0-9]+$/,
									regexText : '请输入数字',
									value : obj.data.orderid
								}, {
									name : 'id',
									xtype : 'hidden',
									value : obj.data.id
								}, {
									xtype : 'textarea',
									fieldLabel : '备注 ',
									name : 'remarks',
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
												Ext.Msg.alert('状态', '修改成功!',
														function(btn, text) {
															dic_JStore.reload();
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
							})
							var UTwin = new Ext.Window({
										id : 'win',
										layout : 'fit',
										title : '编辑字典类型（根字典类型）',
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
							id : id
						}
					})
				}

			},
			listeners : {
				'click' : function() {
				}
		
		},
		adddic:function(){
			var dic_JStore=this.dic_JStore;
					var formPanel = new Ext.FormPanel({
							id : 'multdicform',
							labelAlign : 'right',
							buttonAlign : 'center',
							url : __ctxPath + '/creditFlow/multiLevelDic/saveDicTypeAreaDic.do',
							bodyStyle : 'padding:10px 25px 25px',
							labelWidth : 110,
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							// title : '增加字典类型',
							width : 500,
							items : [{
								xtype : 'textfield',
								fieldLabel : '字典类型 ',
								name : 'text',
								width : 280,
								maxLength : 127,
								allowBlank : false,
								blankText : '必填信息'
									// anchor : '80%'
								}, {
								xtype : 'textfield',
								fieldLabel : '唯一标识 ',
								name : 'lable',
								width : 280,
								maxLength : 50,
								allowBlank : false,
								blankText : '必填信息'
							}, {
								xtype : 'textfield',
								fieldLabel : '排序号',
								name : 'orderid',
								width : 280,
								maxLength : 50,
								allowBlank : false,
								blankText : '必填信息',
								regex : /^[0-9]+$/,
								regexText : '请输入数字'
							}, {
								xtype : 'textarea',
								fieldLabel : '备注 ',
								name : 'remarks',
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
											Ext.Msg.alert('状态', '添加成功!',
													function(btn, text) {
														if (btn == 'ok') {
															dic_JStore.reload();
															adwin.destroy();
														}
													});
										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('状态', '添加失败,有重复项!');
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
							id : 'uwin',
							layout : 'fit',
							title : '增加字典类型（根字典项）',
							width : 500,
							height : 200,
							minimizable : true,
							modal : true,
							items : [formPanel]
						});
				adwin.show();
		},
		add_button_c:function(){
			var dic_Panel=this.dic_Panel;
			var tree=this.tree;
			var gselectid = Ext.getCmp("gselectid").value;
			var gselecttext = Ext.getCmp("gselecttext").value;

						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要添加的子选项的父节点！');
							return;
						}
						var formPanel = new Ext.FormPanel({
							id : 'multdicform',
							labelAlign : 'right',
							buttonAlign : 'center',
							url : __ctxPath + '/creditFlow/multiLevelDic/addAreaDic.do',
							bodyStyle : 'padding:10px 25px 25px',
							labelWidth : 110,
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							width : 500,
							items : [{
										xtype : 'label',
										fieldLabel : '父字典项 ',
										text : '[' + gselecttext + ']'
									}, {
										xtype : 'hidden',
										fieldLabel : '',
										name : 'parentId',
										value : gselectid
									}, {
										xtype : 'textfield',
										fieldLabel : '数据字典项 ',
										name : 'text',
										width : 280,
										maxLength : 127,
										allowBlank : false,
										blankText : '必填信息'
									}, {
										xtype : 'textfield',
										fieldLabel : '排序号',
										name : 'orderid',
										width : 280,
										maxLength : 50,
										allowBlank : false,
										blankText : '必填信息',
										regex : /^[0-9]+$/,
										regexText : '请输入数字'
									}, {
										xtype : 'textarea',
										fieldLabel : '备注 ',
										name : 'remarks',
										width : 280,
										maxLength : 1000
									},{
										xtype : 'textarea',
										fieldLabel : '备注1 ',
										name : 'remarks1',
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
											Ext.Msg.alert('操作提示', '添加成功!',
													function(btn, text) {
														if (btn == 'ok') {
															tree.getRootNode()
																	.reload();
															// tree.getNodeById('2074').expand();
															// tree.doLayout();
															// dic_JStore.reload();
															adwin.destroy();
														}
													});
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
									id : 'uwin',
									layout : 'fit',
									title : '添加字典项（子字典项）',
									width : 500,
									height : 300,
									minimizable : true,
									modal : true,
									items : [formPanel]
								});
						adwin.show();
		},
		update_button_c:function(){
				var dic_Panel=this.dic_Panel;
			var tree=this.tree;
			var gselecttype = Ext.getCmp("gselecttype").value;
						var gselectid = Ext.getCmp("gselectid").value;
						var gselecttext = Ext.getCmp("gselecttext").value;
						if (gselecttype == '0') {
							return;
						}
						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要修改的字典项！');
							return;
						}
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
							method : 'POST',
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var pid = obj.data.parentId;
								
								Ext.Ajax.request({
									url :__ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
									method : 'POST',
									params : {
										id : pid
									},
									success : function(response, request) {
									
										var objc = Ext.util.JSON.decode(response.responseText);
										var ptext = objc.data.text;
										var _update_panel = new Ext.FormPanel({
											id : 'multupdatepanel',
											url : __ctxPath + '/creditFlow/multiLevelDic/updateDicAreaDic.do',
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
														name : 'id',
														value : gselectid
													}, {
														xtype : 'hidden',
														fieldLabel : '',
														name : 'sourcepid',
														value : pid
													}, {
														fieldLabel : '父字典ID',
														layout : 'column',
														items : [{
															id : 'parentId',
															xtype : 'textfield',
															name : 'parentId',
															width : 50,
															value : pid,
															blankText : '必填信息',
															regex : /^[0-9]+$/,
															regexText : '请输入数字',
															listeners : {
																'change' : function(a, b, c) {
																	if (b == gselectid) {
																		Ext.getCmp("parentId").setValue(c);
																		Ext.Msg.alert('操作提示','请办输入正确的字典ID号!');
																		return;

																	}
																	Ext.Ajax.request({
																						url : __ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
																						method : 'POST',
																						params : {
																							id : b
																						},
																						success : function(response,request) {
																							var objs = Ext.util.JSON.decode(response.responseText);
																							if (objs.data == null) {
																								Ext.getCmp("parentId").setValue(c);
																								Ext.Msg.alert('操作提示','请办输入正确的字典ID号!');
																								return;
																							} else {
																								var fatext = objs.data.text;
																								Ext.getCmp("fatext").setValue(fatext);
																							}

																						},
																						failure : function(form,action) {
																							Ext.Msg.alert('状态','失败!');
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
															value : ptext
														}]

													}, {
														xtype : 'textfield',
														fieldLabel : '数据字典项 ',
														name : 'text',
														width : 280,
														maxLength : 127,
														allowBlank : false,
														blankText : '必填信息',
														value : obj.data.text
													}, {
														xtype : 'textfield',
														fieldLabel : '排序号',
														name : 'orderid',
														width : 280,
														maxLength : 50,
														allowBlank : false,
														blankText : '必填信息',
														regex : /^[0-9]+$/,
														regexText : '请输入数字',
														value : obj.data.orderid
													}, {
														xtype : 'textarea',
														fieldLabel : '备注',
														name : 'remarks',
														width : 280,
														maxLength : 1000,
														value : obj.data.remarks
													},{
														xtype : 'textarea',
														fieldLabel : '备注1 ',
														name : 'remarks1',
														width : 280,
														maxLength : 1000,
														value : obj.data.remarks1
													}],
											buttons : [{
												text : '提交',
												formBind : true,
												handler : function() {
													_update_panel.getForm()
															.submit({
																method : 'POST',
																waitTitle : '连接',
																waitMsg : '消息发送中...',
																success : function() {
																	Ext.Msg.alert('状态','修改成功!',
																					function(btn,text) {
																						if (tree.getRootNode() != null) {
																							tree.getRootNode().reload();
																							UTwin.destroy();
																						} else {
																							dic_Panel.reload();
																							UTwin.destroy();
																						}
																					});
																},
																failure : function(
																		form,
																		action) {
																	Ext.Msg
																			.alert(
																					'操作提示',
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
													title : '编辑数据字典项（子数据字典项）',
													width : 500,
													height : 300,
													maximizable : true,
													modal : true,
													items : [_update_panel]
												});
										UTwin.show();
									}
								});
							},
							params : {
								id : gselectid
							}
						})
		},
		delete_button_c:function(){
			var tree=this.tree;
			var gselectid = Ext.getCmp("gselectid").value;
						var gselecttext = Ext.getCmp("gselecttext").value;
						var gselecttype = Ext.getCmp("gselecttype").value;
						if (gselecttype == '0') {
							return;
						}
						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要修改的字典项！');
							return;
						}
						var deletfuntion = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.Ajax.request({
								url : __ctxPath + '/creditFlow/multiLevelDic/deleteDicTypeAreaDic.do',
								method : 'POST',
								params : {
									id : gselectid
								},
								success : function(response, request) {
									var msg = response.responseText;
									var re = Ext.util.JSON.decode(msg).data;
									if (re == 0) {
										Ext.MessageBox
												.alert("操作提示",
														"字典类型删除失败，只有该字典类型下所有子数据类型被删除后，该数据字典时才能被完全删除！");
									} else {
										Ext.MessageBox.alert("操作提示",
												"删除成功，该数据字典已被完全删除！");
										tree.getRootNode().reload();

									}
								}
							});
						};

						var deleteconfirm1 = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.MessageBox.confirm('系统提示','删除选中数据字典后，该数据字典的数据都将会删除，你确定要删除吗？',deleteconfirm2);
						}
						var deleteconfirm2 = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.MessageBox.confirm('系统提示','如果将这些字典删除，可能会影响到项目中所有引用了该数据字典的功能模块！您确认要执行这些操作吗?',deleteconfirm3);
						}
						var deleteconfirm3 = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.MessageBox.confirm('系统提示','此删除为完全删除,请最后一次确认您的操作，删除后该记录将无法还原!',deletfuntion);
						}
						Ext.MessageBox.confirm('系统提示','你确定要删除选中的数据字典内容吗？删除后可能给系统带来严重的后果！',deleteconfirm1);
		}
});
