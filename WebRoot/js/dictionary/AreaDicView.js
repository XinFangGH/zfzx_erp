/**
 * @author
 * @class PlOpbankAccountView
 * @extends Ext.Panel
 * @description [PlOpbankAccount]管理
 * @company 智维软件
 * @createtime:
 */
AreaDicView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				

				AreaDicView.superclass.constructor.call(this, {
							id : 'AreaDicView'+this.treeId,
							title : this.treeId ==1?'地区数据字典':(this.treeId ==89?'银行管理':'行业类别字典'),
							region : 'center',
							layout:'border',
							iconCls : this.treeId ==1?'menu-flowManager':'menu-flowManager',
							items : [this.dic_Panel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var Synchronize= function(url) {
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
				var treeload = new Ext.tree.TreeLoader({
						dataUrl :__ctxPath + '/creditFlow/multiLevelDic/getDicTypeTreeAreaDic.do'
					});
			var tree_Root = new Ext.tree.AsyncTreeNode({
						id : this.treeId,
						lable : '字典',
						imgUrl : '',
						parentId : '0',
						text : '',
						lable : '',
						number : 0,
						remarks : '',
						// leaf : false,
						orderid : '0',
						checked : true,
						isOld : '0'
					});
			var myMask = new Ext.LoadMask(Ext.getBody(), {
						msg : "加载数据中······,请稍后······"
					});
		  	this.topbar = new Ext.Toolbar({
						items : [{
										text : '添加字典项',
										scope : this,
										tooltip : '添加选中字典的字典项',
										iconCls : 'btn-add',
										hidden : isGranted('_addzdx')?false:true,
									    handler : function() {
											this.add_button_c();
									  	}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_addzdx')?false:true
								}),{
										text : '查看字典项',
										tooltip : '查看选中的字典项',
										iconCls : 'btn-detail',
										scope : this,
										hidden : isGranted('_seezdx')?false:true,
										handler : function() {
											this.see_button_c();
									 	}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_editzdx')?false:true
								}),{
										text : '修改字典项',
										tooltip : '修改选中的字典项',
										iconCls : 'btn-edit',
										scope : this,
										hidden : isGranted('_editzdx')?false:true,
										handler : function() {
											this.update_button_c();
									 	}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_editzdx')?false:true
								}),{
										text : '删除字典项',
										tooltip : '从数据库中完全删除选中字典项',
										iconCls : 'btn-del',
										scope : this,
										hidden : isGranted('_removezdx')?false:true,
										 handler : function() {
									    	
										 this.delete_button_c();
									  }
								},new Ext.Toolbar.Separator({
									hidden : (isGranted('_removezdx')?false:true)||(false)
								}),{
									text : '展开所有结点',
									tooltip : '展开树中所有结点',
									iconCls : 'seeIcon',
									scope : this,
									handler : function() {
										this.tree_GPanel.expandAll();
									}
								},new Ext.Toolbar.Separator({
									//hidden : isGranted('_removezdx')?false:true
								}), {
										text : '收起所有结点',
										tooltip : '收起树中所有结点',
										iconCls : 'closeIcon',
										scope : this,
										handler : function() {
											this.tree_GPanel.collapseAll();
										}
										
								}
									]
				});
				this.tree_GPanel = new Ext.ux.tree.TreeGrid({
					id : 'tree_child',
					border : false,
					//autoWidth : true,
					//autoHeight :true,
					height : Ext.getBody().getViewSize().height-116,
					tbar : this.topbar,
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
								align:'center',
								width : 80,
								dataIndex : 'id'
							}
//							, {
//								header : '字典ID号',
//								width : 80,
//							//	hidden : true,
//								dataIndex : 'remarks'
//							}
							, {
								header : '子节点个数',
								width : 120,
								align:'center',
								alin:'center',
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
								header : "系统备注",
								dataIndex : 'remarks',
								width : 280
							}, {
								header : "手动备注",
								dataIndex : 'remarks1',
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
							Ext.getCmp("gselectremarks").value = n.attributes.remarks;
							// Ext.getCmp("dic_GPanel").clearSelections();
						}
					}
				});
			this.dic_Panel = new Ext.Panel({
						region : "center",
						id : 'dic_Panel',
						layout : 'column',
						title : this.treeId ==1?'地区数据字典管理':(this.treeId ==89?'银行数据字典管理':'行业类别字典'),
			//			autoHeight : true,
						Width : 1000,
						
						autoScroll : true,
						border : false,
						loadMask : myMask,
						items : [{
									id : 'gridtreeid',
									layout : 'fit',

									columnWidth : 1,

									items : [this.tree_GPanel]
								}, {
									id : 'gselectid',
									xtype : 'hidden',
									name : 'gselectid',
									value : '0'
								}, {
									id : 'gselectremarks',
									xtype : 'hidden',
									name : 'gselectremarks',
									value : ''
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
		  
				
					add_button_c : function() {
						var tree_GPanel=this.tree_GPanel;
						var gselectid = Ext.getCmp("gselectid").value;
						var gselecttext = Ext.getCmp("gselecttext").value;
						var gselectremarks = Ext.getCmp("gselectremarks").value;
						
						if (gselectid == '0') {
							/*Ext.ux.Toast.msg('操作提示', '请选择您要添加的子选项的父节点！');
							return;*/
							gselectid = this.treeId;
							gselecttext = this.treeId == 1?'地区':(this.treeId == 89?'银行':'行业类别');
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
										fieldLabel : '',
										name : 'remarks',
										width : 280,
										maxLength : 1000,
										hidden : true,
										value :gselectremarks
									},{
										xtype : 'textarea',
										fieldLabel : '备注 ',
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
											Ext.ux.Toast.msg('操作提示', '添加成功!');
											tree_GPanel.getRootNode().reload();
											adwin.destroy();
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

					}
				,
				//查看数据字典
				see_button_c : function() {
					
						var tree_GPanel=this.tree_GPanel;
						var gselecttype = Ext.getCmp("gselecttype").value;
						var gselectid = Ext.getCmp("gselectid").value;
						var gselecttext = Ext.getCmp("gselecttext").value;
						if (gselecttype == '0') {
							return;
						}
						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要查看的字典项！');
							return;
						}
						Ext.Ajax.request({
							url :__ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
							method : 'POST',
							success : function(response, request) {
								var obj = Ext.util.JSON
										.decode(response.responseText);
								var pid = obj.data.parentId;
								
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
									method : 'POST',
									params : {
										id : pid
									},
									success : function(response, request) {
										
										var objc = Ext.util.JSON
												.decode(response.responseText);
												
										var ptext = objc.data.text;
										var _see_panel = new Ext.FormPanel({
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
														readonly:true,
														layout : 'column',
														items : [{
															id : 'parentId',
															xtype : 'textfield',
															name : 'parentId',
															width : 50,
															value : pid,
															readOnly:true,
															blankText : '必填信息',
															regex : /^[0-9]+$/,
															regexText : '请输入数字',
															listeners : {
																'change' : function(
																		a, b, c) {
																	if (b == gselectid) {

																		Ext
																				.getCmp("parentId")
																				.setValue(c);
																		Ext.ux.Toast.msg('操作提示','请再输入正确的字典ID号!');
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
																								Ext.ux.Toast.msg('操作提示','请再输入正确的字典ID号!');
																								return;
																							} else {
																								var fatext = objs.data.text;
																								Ext.getCmp("fatext").setValue(fatext);
																							}

																						},
																						failure : function(form,action) {
																							Ext.ux.Toast.msg('状态','失败!');
																						}
																					})
																}
															}
														}, {
															xtype : 'label',
															width : 15,
															readOnly:true,
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
														readOnly:true,
														width : 280,
														maxLength : 127,
														allowBlank : false,
														blankText : '必填信息',
														value : obj.data.text
													}, {
														xtype : 'textfield',
														fieldLabel : '排序号',
														name : 'orderid',
														readOnly:true,
														width : 280,
														maxLength : 50,
														allowBlank : false,
														blankText : '必填信息',
														regex : /^[0-9]+$/,
														regexText : '请输入数字',
														value : obj.data.orderid
													}, {
														xtype : 'textarea',
														fieldLabel : '',
														name : 'remarks',
														readOnly:true,
														width : 280,
														maxLength : 1000,
														hidden : true,
														value : obj.data.remarks
													},{
														xtype : 'textarea',
														fieldLabel : '备注 ',
														name : 'remarks1',
														readOnly:true,
														width : 280,
														maxLength : 1000,
														value : obj.data.remarks1
													}],
											buttons : [{
												text : '提交',
												formBind : true,
												hidden:true,
												handler : function() {
													_update_panel.getForm()
															.submit({
																method : 'POST',
																waitTitle : '连接',
																waitMsg : '消息发送中...',
																success : function() {
																	Ext.ux.Toast.msg('状态','修改成功!');
																	if (tree_GPanel.getRootNode() != null) {

																		tree_GPanel.getRootNode().reload();
																			UTwin.destroy();
																		} else {
																			dic_Panel.reload();
																			UTwin.destroy();
																		}
																},
																failure : function(
																		form,
																		action) {
																	Ext.ux.Toast.msg(
																					'操作提示',
																					'修改失败,有重复项！');
																}
															});
												}

											}, {

												text : '取消',
												hidden:true,
												handler : function() {
													UTwin.destroy();
												}

											}]
										});
										var UTwin = new Ext.Window({
													id : 'win',
													layout : 'fit',
													title : '查看数据字典项（子数据字典项）',
													width : 500,
													height : 300,
													maximizable : true,
													modal : true,
													items : [_see_panel]
												});
										UTwin.show();
									}
								});
							},
							params : {
								id : gselectid
							}
						})

					}
				,
				
					update_button_c : function() {
						var tree_GPanel=this.tree_GPanel;
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
							url :__ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
							method : 'POST',
							success : function(response, request) {
								var obj = Ext.util.JSON
										.decode(response.responseText);
								var pid = obj.data.parentId;
								
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/multiLevelDic/getAreaIdAreaDic.do',
									method : 'POST',
									params : {
										id : pid
									},
									success : function(response, request) {
										
										var objc = Ext.util.JSON
												.decode(response.responseText);
												
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
																'change' : function(
																		a, b, c) {
																	if (b == gselectid) {

																		Ext
																				.getCmp("parentId")
																				.setValue(c);
																		Ext.ux.Toast.msg('操作提示','请再输入正确的字典ID号!');
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
																								Ext.ux.Toast.msg('操作提示','请再输入正确的字典ID号!');
																								return;
																							} else {
																								var fatext = objs.data.text;
																								Ext.getCmp("fatext").setValue(fatext);
																							}

																						},
																						failure : function(form,action) {
																							Ext.ux.Toast.msg('状态','失败!');
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
														fieldLabel : '',
														name : 'remarks',
														width : 280,
														maxLength : 1000,
														hidden : true,
														value : obj.data.remarks
													},{
														xtype : 'textarea',
														fieldLabel : '备注 ',
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
																	Ext.ux.Toast.msg('状态','修改成功!');
																	if (tree_GPanel.getRootNode() != null) {

																		tree_GPanel.getRootNode().reload();
																			UTwin.destroy();
																		} else {
																			dic_Panel.reload();
																			UTwin.destroy();
																		}
																},
																failure : function(
																		form,
																		action) {
																	Ext.ux.Toast.msg(
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

					}
				,
					
				

					delete_button_c : function() {
							var tree_GPanel=this.tree_GPanel;
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
										Ext.ux.Toast.msg("操作提示",
														"字典类型删除失败，只有该字典类型下所有子数据类型被删除后，该数据字典时才能被完全删除！");
									} else {
										Ext.ux.Toast.msg("操作提示",
												"删除成功，该数据字典已被完全删除！");
										tree_GPanel.getRootNode().reload();
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
						Ext.MessageBox.confirm('系统提示',
								'你确定要删除选中的数据字典内容吗？删除后可能给系统带来严重的后果！',
								deleteconfirm1);
					}
});
