/**
 * @author
 * @class PlOpbankAccountView
 * @extends Ext.Panel
 * @description [PlOpbankAccount]管理
 * @company 智维软件
 * @createtime:
 */
AreaManagement = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				

				AreaManagement.superclass.constructor.call(this, {
							id : 'AreaDicView'+this.treeId,
							title : '地区管理',
							region : 'center',
							layout:'border',
							iconCls : 'btn-team53',
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
						dataUrl :__ctxPath + '/creditFlow/common/listAreaManagement.do'
					});
			var tree_Root = new Ext.tree.AsyncTreeNode({
						id : 1,
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
										text : '添加地区',
										scope : this,
										tooltip : '添加地区',
										iconCls : 'btn-add',
										hidden : isGranted('_addArea')?false:true,
									    handler : function() {
											this.add_button_c();
									  	}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_addArea')?false:true
								}),{
										text : '查看地区详细信息',
										tooltip : '查看地区详细信息',
										iconCls : 'btn-detail',
										scope : this,
										hidden : isGranted('_seeArea')?false:true,
										handler : function() {
											this.see_button_c();
									 	}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_seeArea')?false:true
								}),{
										text : '修改地区详细信息',
										tooltip : '修改地区详细信息',
										iconCls : 'btn-edit',
										scope : this,
										hidden : isGranted('_editArea')?false:true,
										handler : function() {
											this.update_button_c();
									 	}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_editArea')?false:true
								}),{
										text : '删除地区',
										tooltip : '删除地区',
										iconCls : 'btn-del',
										scope : this,
										hidden : isGranted('_removeArea')?false:true,
										 handler : function() {
									    	
										 this.delete_button_c();
									  }
								},new Ext.Toolbar.Separator({
									hidden : (isGranted('_removeArea')?false:true)||(false)
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
								header : '地区名称',
								dataIndex : 'text',
								width : 240
							}/*, {
								header : '字典ID号',
								width : 80,
								dataIndex : 'id'
							}*/
//							, {
//								header : '字典ID号',
//								width : 80,
//							//	hidden : true,
//								dataIndex : 'remarks'
//							}
							, {
								header : '子节点个数',
								width : 120,
								dataIndex : 'id',
								tpl : new Ext.XTemplate(
										'{id:this.getChildNum}', {
											getChildNum : function(v) {
												var url = __ctxPath + '/creditFlow/common/getCountAreaManagement.do?parentId='
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
						//title : this.treeId ==1?'地区数据字典管理':(this.treeId ==89?'银行数据字典管理':'行业类别管理'),
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
							gselectid = 1;
							gselecttext ='地区';
						}
						var formPanel = new Ext.FormPanel({
							id : 'multdicform',
							labelAlign : 'right',
							buttonAlign : 'center',
							url : __ctxPath + '/creditFlow/common/saveAreaManagement.do',
							bodyStyle : 'padding:10px 25px 25px',
							labelWidth : 70,
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							width : 500,
							items : [{
										xtype : 'label',
										fieldLabel : '父地区名称',
										text : '[' + gselecttext + ']'
									}, {
										xtype : 'hidden',
										fieldLabel : '',
										name : 'areaManagement.parentId',
										value : gselectid
									}, {
										xtype : 'textfield',
										fieldLabel : '地区名称 ',
										name : 'areaManagement.text',
										anchor : '100%',
										maxLength : 127,
										allowBlank : false,
										blankText : '必填信息'
									},{
										xtype : 'textarea',
										fieldLabel : '备注 ',
										name : 'areaManagement.remarks1',
										anchor : '100%'
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
									title : '添加地区',
									width : 400,
									height : 250,
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
						/*if (gselecttype == '0') {
							return;
						}
						alert(gselectid)*/
						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要查看的地区！');
							return;
						}
						Ext.Ajax.request({
							url :__ctxPath + '/creditFlow/common/getAreaManagement.do',
							method : 'POST',
							params : {
										areaId : gselectid
									},
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var _see_panel = new Ext.FormPanel({
									labelAlign : 'right',
									buttonAlign : 'center',
									bodyStyle : 'padding:10px 25px 25px',
									//height : 380,
									frame : true,
									labelWidth : 70,
									monitorValid : true,
								//	width : 400,
									items : [{
										xtype : 'label',
										fieldLabel : '父地区名称',
										text : '[' + obj.data.parentArea.text + ']'
									}, {
										xtype : 'textfield',
										fieldLabel : '地区名称 ',
										name : 'areaManagement.text',
										readOnly : true,
										value :obj.data.areaManagement.text,
										anchor : '100%',
										allowBlank : false,
										blankText : '必填信息'
									},{
										xtype : 'textarea',
										fieldLabel : '备注 ',
										readOnly : true,
										name : 'areaManagement.remarks1',
										value :obj.data.areaManagement.remarks1,
										anchor : '100%'
									}],
											buttons : [{

												text : '关闭',
												handler : function() {
													UTwin.destroy();
												}

											}]
										});
										var UTwin = new Ext.Window({
													id : 'win',
													layout : 'fit',
													title : '查看地区详细信息',
													width : 400,
													height : 250,
													maximizable : true,
													modal : true,
													items : [_see_panel]
												});
										UTwin.show();
									
							}
						})

					}
				,
				
					update_button_c : function() {
						var tree_GPanel=this.tree_GPanel;
						var gselecttype = Ext.getCmp("gselecttype").value;
						var gselectid = Ext.getCmp("gselectid").value;
						var gselecttext = Ext.getCmp("gselecttext").value;
						/*if (gselecttype == '0') {
							return;
						}
						alert(gselectid)*/
						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要查看的地区！');
							return;
						}
						Ext.Ajax.request({
							url :__ctxPath + '/creditFlow/common/getAreaManagement.do',
							method : 'POST',
							params : {
										areaId : gselectid
									},
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var formPanel = new Ext.FormPanel({
									labelAlign : 'right',
									buttonAlign : 'center',
									bodyStyle : 'padding:10px 25px 25px',
									url : __ctxPath + '/creditFlow/common/saveAreaManagement.do',
									//height : 380,
									frame : true,
									labelWidth : 70,
									monitorValid : true,
								//	width : 400,
									items : [{
										xtype : 'label',
										fieldLabel : '父地区名称',
										text : '[' + obj.data.parentArea.text + ']'
									},{
										xtype : 'hidden',
										name : 'areaManagement.id',
										value :obj.data.areaManagement.id
									}, {
										xtype : 'textfield',
										fieldLabel : '地区名称 ',
										name : 'areaManagement.text',
										value :obj.data.areaManagement.text,
										anchor : '100%',
										allowBlank : false,
										blankText : '必填信息'
									},{
										xtype : 'textarea',
										fieldLabel : '备注 ',
										name : 'areaManagement.remarks1',
										value :obj.data.areaManagement.remarks1,
										anchor : '100%'
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
											UTwin.destroy();
										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('操作提示', '添加失败,有重复项!');
										}
									})
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
													title : '编辑地区详细信息',
													width : 400,
													height : 250,
													maximizable : true,
													modal : true,
													items : [formPanel]
												});
										UTwin.show();
									
							}
						})

					}
				,
					
				

					delete_button_c : function() {
							var tree_GPanel=this.tree_GPanel;
						var gselectid = Ext.getCmp("gselectid").value;
						var gselecttext = Ext.getCmp("gselecttext").value;
						var gselecttype = Ext.getCmp("gselecttype").value;
						if (gselectid == '0') {
							Ext.ux.Toast.msg('操作提示', '请选择您要删除的地区！');
							return;
						}
						var deletfuntion = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.Ajax.request({
								url : __ctxPath + '/creditFlow/common/multiDelAreaManagement.do',
								method : 'POST',
								params : {
									id : gselectid
								},
								success : function(response, request) {
									var msg = response.responseText;
									var re = Ext.util.JSON.decode(msg).data;
									if (re == 0) {
										Ext.ux.Toast.msg("操作提示",
														"删除失败");
									} else {
										Ext.ux.Toast.msg("操作提示",
												"删除成功！");
										tree_GPanel.getRootNode().reload();
									}
								}
							});
						};
/*
						var deleteconfirm1 = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.MessageBox.confirm('系统提示',
									'您确认要删除所选记录吗',
									deleteconfirm2);
						}
						var deleteconfirm2 = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.MessageBox
									.confirm(
											'系统提示',
											'您确认要删除所选记录吗',
											deleteconfirm3);
						}
						var deleteconfirm3 = function(btn) {
							if (btn != 'yes') {
								return;
							}
							Ext.MessageBox.confirm('系统提示',
									'您确认要删除所选记录吗',
									deletfuntion);
						}*/
						Ext.MessageBox.confirm('系统提示',
								'您确认要删除所选记录吗',
								deletfuntion);
					}
});
