var add_button_o = new Ext.Button({
		    text : '添加当物材料项',
		    tooltip : '添加选中当物材料的担保材料项',
		    iconCls : 'addIcon'
}); 
var update_button_o = new Ext.Button({
		    text : '修改当物材料项',
		    tooltip : '修改选中的当物材料项',
		    iconCls : 'updateIcon'
});
var delete_button_o = new Ext.Button({
			text : '删除当物材料项',
			tooltip : '从数据库中完全删除选中当物材料项',
			iconCls : 'deleteIcon'
});
var delete_button_l = new Ext.Button({
			      
			text : '删除当物材料',
			tooltip : '从数据库中完全删除选中当物材料类别',
			iconCls : 'deleteIcon',
			scope : this,
			handler : function() {
				var testObj=Ext.getCmp("enterperiseMaterials_dic");
			    var selected = testObj.getSelectionModel().getSelected();// 选中当前行
				
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					
				    Ext.MessageBox.confirm('系统提示', '确认删除？删除后无法恢复!', function(btn) {
				    	       if (btn == 'no') {
				    	       	    return false;
				    	       }
				    	       else {
				    	                var id = selected.get('materialsId'); // 获得当前行的ID值
										Ext.Ajax.request({
											url : __ctxPath+ '/materials/deleteOurProcreditMaterialsEnterprise.do',
											method : 'POST',
											params : {
												materialsId : id
											},
											success : function(response, request) {
													testObj.getStore().load();
													Ext.getCmp("enterperiseMaterials_dic_r").getStore().load();
											}
										});
				    	       }
				    })
				}
			}
})
var update_button_l = new Ext.Button({
				text : '修改当物材料',
				tooltip : '修改选中的当物材料类别',
				iconCls : 'updateIcon',
				scope : this,
				handler : function() {
					var testObj=Ext.getCmp("enterperiseMaterials_dic");
					var selected = testObj.getSelectionModel().getSelected();// 选中当前行
							
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('materialsId'); // 获得当前行的ID值
						Ext.Ajax.request({
							url : __ctxPath+ '/materials/getOurProcreditMaterialsEnterprise.do',
							method : 'POST',
							success : function(response, request) {
								obj = Ext.util.JSON.decode(response.responseText);
										
								var _update_panel = new Ext.FormPanel({
									id : 'multupdatepanel',
									url : __ctxPath+ '/materials/saveOurProcreditMaterialsEnterprise.do',
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
										fieldLabel : '担保材料类型',
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
						},{
										name : 'ourProcreditMaterialsEnterprise.materialsId',
										xtype : 'hidden',
										value : obj.data.materialsId
									}, {
							xtype : 'textfield',
									fieldLabel : '业务类别',	
	 								name:'btn',
	 								readOnly:true,
	 								value:'典当行',
	 								width : 280,
	 								maxLength: 255
 							    },{
									
									fieldLabel : "业务品种",
									xtype : "combo",
									allowBlank : false,
									displayField : 'itemName',
									store : new Ext.data.SimpleStore({
										baseParams : {
											nodeKey : 'Pawn'
										},
										autoLoad:true,
										url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
										fields : ['itemId','itemName']
									}),
									valueField : 'itemId',
									triggerAction : 'all',
									mode : 'remote',
									hiddenName : "ourProcreditMaterialsEnterprise.operationTypeKey",
									editable : false,
									blankText : "业务品种不能为空，请正确填写!",
									width : 280,
									listeners : {
										afterrender : function(combox) {
											    var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(obj.data.operationTypeKey);
												})
										}
									}
								},{
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
													Ext.ux.Toast.msg('状态','修改成功!');
													UTwin.destroy();
													testObj.getStore().load();
													
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态','修改失败!');
															
																	
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
											title : '编辑当物材料类型（根当物材料类型）',
											width : 500,
											height : 230,
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
var add_button_l = new Ext.Button({
					text : '添加当物材料',
					scope : this,
					tooltip : '添加当物材料类型',
					iconCls : 'addIcon',
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
							fieldLabel : '当物材料类型 ',
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
						},{
							xtype : 'textfield',
									fieldLabel : '业务类别',	
									width : 280,
	 								name:'btn',
	 								readOnly:true,
	 								value:'典当行',
	 								maxLength: 255
 							    },{
									fieldLabel : "业务品种",
									xtype : "combo",
									allowBlank : false,
									displayField : 'itemName',
									width : 280,
									store : new Ext.data.SimpleStore({
										baseParams : {
											nodeKey : 'Pawn'
										},
										autoLoad:false ,
										url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
										fields : ['itemId','itemName']
									}),
									valueField : 'itemId',
									triggerAction : 'all',
									mode : 'remote',
									hiddenName : "ourProcreditMaterialsEnterprise.operationTypeKey",
									editable : false,
									blankText : "业务品种不能为空，请正确填写!",
									//anchor : "100%",
									listeners : {
										afterrender : function(combox) {
											    var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
												})
										}
									}
								},
								{
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
										    Ext.ux.Toast.msg('状态','添加成功!');
										    //enterperiseMaterials_dic
											 adwin.destroy();
											 Ext.getCmp("enterperiseMaterials_dic").getStore().load();
									}, 
									failure : function(form, action) {
										Ext.ux.Toast.msg('状态','添加失败,有重复项');
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
								title : '增加当物材料类型（根当物材料项）',
								width : 500,
								height : 230,
								minimizable : true,
								modal : true,
								items : [formPanel]
							});
					adwin.show();

				}
			})
		
			
var add_button_r = new Ext.Button({
						text : '添加当物材料项',
						scope : this,
						tooltip : '添加选中当物材料的当物材料项',
						iconCls : 'addIcon',
						handler : function() {
							var testObj=Ext.getCmp("enterperiseMaterials_dic");
							var gselectid = testObj.getSelectionModel().getSelected().get('materialsId');
							var operationTypeKey=testObj.getSelectionModel().getSelected().get('operationTypeKey');
							var gselecttext =  testObj.getSelectionModel().getSelected().get('materialsName');;
							if (gselectid == '0') {
								Ext.ux.Toast.msg('操作提示', '请选择您要添加的子选项的父节点！');
								return;
							}
							var formPanel = new Ext.FormPanel({
								id : 'multdicform_m',
								labelAlign : 'right',
								buttonAlign : 'center',
								url : __ctxPath+ '/materials/saveOurProcreditMaterialsEnterprise.do',
								bodyStyle : 'padding:10px 25px 25px',
								labelWidth : 110,
								frame : true,
								waitMsgTarget : true,
								monitorValid : true,
								//width : 500,
								items : [{
											xtype : 'label',
											fieldLabel : '父当物材料项',
											text : '[' + gselecttext + ']'
										}, {
											xtype : 'hidden',
											fieldLabel : '',
											name : 'ourProcreditMaterialsEnterprise.parentId',
											value : gselectid
										}, {
									fieldLabel : "业务品种",
									xtype : "combo",
									allowBlank : false,
									displayField : 'itemName',
									store : new Ext.data.SimpleStore({
										baseParams : {
											nodeKey : 'Pawn'
										},
										autoLoad:true,
										url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
										fields : ['itemId','itemName']
									}),
									valueField : 'itemId',
									triggerAction : 'all',
									readOnly:true,
									mode : 'remote',
									width : 280,
									hiddenName : "ourProcreditMaterialsEnterprise.operationTypeKey",
									editable : false,
									blankText : "业务品种不能为空，请正确填写!",
									//anchor : "100%",
									listeners : {
										afterrender : function(combox) {
											    var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(operationTypeKey);
												})
										}
									}
								},{
											xtype : 'hidden',
											fieldLabel : '',
											name : 'ourProcreditMaterialsEnterprise.leaf',
											value : '1'
										}, {
											xtype : 'textfield',
											fieldLabel : '数据当物材料项 ',
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
											   //enterperiseMaterials_dic_r
											   Ext.getCmp("enterperiseMaterials_dic_r").getStore().load();
											   
											},
											failure : function(form, action) {
												 Ext.ux.Toast.msg('操作提示','添加失败,有重复项!');
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
										title : '添加当物材料项（子当物材料项）',
										width : 500,
										height : 300,
										minimizable : true,
										modal : true,
										items : [formPanel]
									});
							adwin.show();

						}
					});
var update_button_r = new Ext.Button({
						text : '修改当物材料项',
						tooltip : '修改选中的当物材料项',
						iconCls : 'updateIcon',
						scope : this,
						handler : function() {
						    var testObj=Ext.getCmp("enterperiseMaterials_dic_r");
					        var selected = testObj.getSelectionModel().getSelected();// 选中当前行
							
							var gselectid = selected.get('materialsId');
							var gselecttext = selected.get('materialsName');
							
							
							if (gselectid == '0') {
								Ext.ux.Toast.msg('操作提示', '请选择您要修改的当物材料项！');
								return;
							}
							Ext.Ajax.request({
								url : __ctxPath+ '/materials/getOurProcreditMaterialsEnterprise.do',
										
								method : 'POST',
								success : function(response, request) {
									obj = Ext.util.JSON.decode(response.responseText);
											
									var _update_panel = new Ext.FormPanel({
										id : 'multupdatepanel',
										url : __ctxPath+ '/materials/saveOurProcreditMaterialsEnterprise.do',
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
										}, {	xtype : 'hidden',
											fieldLabel : '',
											name : 'ourProcreditMaterialsEnterprise.leaf',
											value : '1'
										},{
											xtype : 'textfield',
											fieldLabel : '数据当物材料项 ',
											name : 'ourProcreditMaterialsEnterprise.materialsName',
											width : 280,
											maxLength : 127,
											allowBlank : false,
											blankText : '必填信息',
											value : obj.data.materialsName
										   }, {
														fieldLabel : '父当物材料ID',
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
																    	Ext.Ajax
																			.request(
																					{
																						url : __ctxPath+ '/materials/getOurProcreditMaterialsEnterprise.do',
																						method : 'POST',
																						params : {
																							materialsId : b
																						},
																						success : function(response,request)
																					    {
																							var objs = Ext.util.JSON.decode(response.responseText);
																									
																							if (objs.data == null) {
																								Ext.getCmp("parentId").setValue(c);
																								Ext.ux.Toast.msg('操作提示','请办输入正确的当物材料ID号!');
																								return;
																							} else {
																								var fatext = objs.data.materialsName;
																								Ext.getCmp("fatext").setValue(fatext);
																							}
																						},
																						failure : function(form,action)
																						 {
																							     Ext.ux.Toast.msg('状态','失败!');
																						 }
																					})
														   }}
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

													},{
											xtype : 'textarea',
											fieldLabel : '合规说明',
											name : 'ourProcreditMaterialsEnterprise.ruleExplain',
											width : 280,
											maxLength : 1000,
											value : obj.data.ruleExplain
										},{
											xtype : 'hidden',
											name : 'ourProcreditMaterialsEnterprise.operationTypeKey',
											value : obj.data.operationTypeKey
										},{
										
											xtype : 'hidden',
											name : 'ourProcreditMaterialsEnterprise.remarks',
											value : obj.data.remarks
										
										}],
										buttons : [{
											text : '提交',
											formBind : true,
											handler : function() {
												_update_panel.getForm().submit(
														{
															method : 'POST',
															waitTitle : '连接',
															waitMsg : '消息发送中...',
															success : function() {
																Ext.ux.Toast.msg('状态','修改成功!')
																UTwin.destroy();
																Ext.getCmp("enterperiseMaterials_dic_r").getStore().load();
															},
															failure : function(
																	form,
																	action) {
																Ext.ux.Toast.msg('操作提示','修改失败,有重复项！');
																				
																				
																		
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
												title : '编辑数据当物材料项（子数据当物材料项）',
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

var delete_button_r = new Ext.Button({
						text : '删除当物材料项',
						tooltip : '从数据库中完全删除选中当物材料项',
						iconCls : 'deleteIcon',
						scope : this,
						handler : function() {
						    var testObj=Ext.getCmp("enterperiseMaterials_dic_r");
					        var selected = testObj.getSelectionModel().getSelected();// 选中当前行
							var gselectid = selected.get('materialsId');
							var gselecttext = selected.get('materialsName');
							if (gselectid == '0') {
								Ext.ux.Toast.msg('操作提示', '请选择您要修改的当物材料项！');
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
								    Ext.getCmp("enterperiseMaterials_dic_r").getStore().load();
								}
							});

						}
					});
