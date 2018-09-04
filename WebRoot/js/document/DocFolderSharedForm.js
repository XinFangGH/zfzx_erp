
DocFolderSharedForm=Ext.extend(Ext.Window,{
     formPanel:null,
     constructor:function(_cfg){
         Ext.applyIf(this,_cfg);
         this.initUI();
         DocFolderSharedForm.superclass.constructor.call(this,{
                title : '文件夹授权',
				iconCls : 'menu-public-fol',
				width : 620,
				height : 420,
				modal : true,
				layout : 'fit',
				scope : this,
				buttonAlign : 'center',
				items : this.formPanel,
				buttons:this.buttons
         
         });         
     },
     initUI:function(){
     
         this.formPanel=new Ext.FormPanel({
        	 	layout : 'form',
 				bodyStyle : 'padding:10px',
 				border : false,
 				autoScroll : true,
				items : [{
							xtype : 'hidden',
							name : 'privilegeId'
						}, {
							xtype : 'hidden',
							name : 'folderId',
							value : this.folderId
						}, {
							xtype : 'fieldset',
							border : false,
							layout : 'column',
							items : [{
										xtype : 'label',
										text : '共享人员',
										width : 100
									}, {
										xtype : 'hidden',
										name : 'userIds',
										id : 'userIds'
									}, {
										xtype : 'textarea',
										name : 'userNames',
										id : 'userNames',
										width : 300
									}, {
										xtype : 'button',
										text : '选择',
										iconCls : 'btn-select',
										width : 80,
										handler : function() {
											// 显示选择器，并且设置用户
											UserSelector.getView(
													function(uIds, fnames) {
														var sharedUserIds = Ext
																.getCmp('userIds');
														var sharedUserNames = Ext
																.getCmp('userNames');
		
														if (sharedUserIds.getValue() == '') {// 若原没有值，则直接设置
															sharedUserIds
																	.setValue(uIds);
															sharedUserNames
																	.setValue(fnames);
															return;
														}
														// 去掉重复的人员
														var vIds = sharedUserIds
																.getValue().split(',');
														var fnms = sharedUserNames
																.getValue().split(',');
		
														sharedUserIds
																.setValue(uniqueArray(vIds
																		.concat(uIds
																				.split(','))));
														sharedUserNames
																.setValue(uniqueArray(fnms
																		.concat(fnames
																				.split(','))));
		
													}).show();
										}
									}, {
										xtype : 'button',
										iconCls : 'btn-clear',
										text : '重置',
										handler : function() {
											var sharedUserIds = Ext.getCmp('userIds');
											var sharedUserNames = Ext
													.getCmp('userNames');
		
											sharedUserIds.setValue('');
											sharedUserNames.setValue('');
										},
										width : 80
									}]
						}, {
							xtype : 'fieldset',
							border : false,
							layout : 'column',
							items : [{
										xtype : 'label',
										text : '共享部门',
										width : 100
									}, {
										name : 'depIds',
										id : 'depIds',
										xtype : 'hidden'
									}, {
										name : 'depNames',
										id : 'depNames',
										xtype : 'textarea',
										width : 300
									}, {
										xtype : 'button',
										text : '选择',
										iconCls : 'btn-select',
										width : 80,
										handler : function() {
											DepSelector.getView(function(ids, names) {
												var sharedDepIds = Ext.getCmp('depIds');
												var sharedDepNames = Ext
														.getCmp('depNames');
		
												if (sharedDepIds.getValue() == '') {// 若原没有值，则直接设置
													sharedDepIds.setValue(ids);
													sharedDepNames.setValue(names);
													return;
												}
												// 去掉重复的部门
												var vIds = sharedDepIds.getValue()
														.split(',');
												var fnms = sharedDepNames.getValue()
														.split(',');
		
												sharedDepIds.setValue(uniqueArray(vIds
														.concat(ids.split(','))));
												sharedDepNames
														.setValue(uniqueArray(fnms
																.concat(names
																		.split(','))));
		
											}).show();
										}
									}, {
										xtype : 'button',
										text : '重置',
										iconCls : 'btn-clear',
										handler : function() {
											var sharedDepIds = Ext.getCmp('depIds');
											var sharedDepNames = Ext.getCmp('depNames');
		
											sharedDepIds.setValue('');
											sharedDepNames.setValue('');
										},
										width : 80
									}]
						}, {
							xtype : 'fieldset',
							border : false,
							layout : 'column',
							items : [{
										xtype : 'label',
										text : '共享角色',
										width : 100
									}, {
										xtype : 'hidden',
										id : 'roleIds',
										name : 'roleIds'
									}, {
										name : 'roleNames',
										id : 'roleNames',
										xtype : 'textarea',
										width : 300
									}, {
										xtype : 'button',
										text : '选择',
										iconCls : 'btn-select',
										width : 80,
										handler : function() {
											RoleSelector.getView(function(ids, names) {
		
												var sharedRoleIds = Ext
														.getCmp('roleIds');
												var sharedRoleNames = Ext
														.getCmp('roleNames');
		
												if (sharedRoleIds.getValue() == '') {// 若原没有值，则直接设置
													sharedRoleIds.setValue(ids);
													sharedRoleNames.setValue(names);
													return;
												}
		
												// 去掉重复的部门
												var vIds = sharedRoleIds.getValue()
														.split(',');
												var fnms = sharedRoleNames.getValue()
														.split(',');
		
												sharedRoleIds.setValue(uniqueArray(vIds
														.concat(ids.split(','))));
												sharedRoleNames
														.setValue(uniqueArray(fnms
																.concat(names
																		.split(','))));
		
											}).show();
										}
									}, {
										xtype : 'button',
										text : '重置',
										iconCls : 'btn-clear',
										handler : function() {
											var sharedRoleIds = Ext.getCmp('roleIds');
											var sharedRoleNames = Ext
													.getCmp('roleNames');
		
											sharedRoleIds.setValue('');
											sharedRoleNames.setValue('');
										},
										width : 80
									}]
						}, {
							xtype : 'fieldset',
							border : false,
							layout : 'column',
							items : [{
										xtype : 'label',
										text : '权限选择：',
										width : 100
									}, {
										xtype : 'checkbox',
										name : 'rightR',
										id : 'rightR'
									}, {
										xtype : 'label',
										text : '可读',
										width : 60
									}, {
										xtype : 'checkbox',
										name : 'rightU',
										id : 'rightU',
										listeners:{
										   "check":function(){
										      var rightU=Ext.getCmp('rightU');
										      var rightD=Ext.getCmp('rightD');
										       var rightR=Ext.getCmp('rightR');
										      if(rightU.getValue()){
										         rightR.setValue(true);
										         rightR.disable();
										      }else if(!rightD.getValue()){
										         rightR.enable();
										      }
										   	
										   }
										}
									}, {
										xtype : 'label',
										text : '可修改',
										width : 60
									}, {
										xtype : 'checkbox',
										name : 'rightD',
										id : 'rightD',
										listeners:{
										   "check":function(){
										      var rightD=Ext.getCmp('rightD');
										      var rightU=Ext.getCmp('rightU');
										      var rightR=Ext.getCmp('rightR');
										      if(rightD.getValue()){
										         rightR.setValue(true);
										         rightR.disable();
										      }else if(!rightU.getValue()){
										         rightR.enable();
										      }
										   	
										   }
										}
									}, {
										xtype : 'label',
										text : '可删除',
										width : 60
									}]
		
						}]
			});
			
			
			this.buttons=[{
					xtype : 'button',
					text : '共享',
					scope:this,
					iconCls : 'btn-ok',
					handler : function() {
						var win=this;
						var userIds = Ext.getCmp('userIds').getValue();
						var depIds = Ext.getCmp('depIds').getValue();
						var roleIds = Ext.getCmp('roleIds').getValue();
						var rightR = Ext.getCmp('rightR').getValue();
						var rightU=Ext.getCmp('rightU').getValue();
					    var rightD=Ext.getCmp('rightD').getValue();
						if (userIds != '' || depIds != '' || roleIds != '') {
							if (rightR == true) {
								var rightRC = Ext.getCmp('rightR');
								rightRC.enable();
								var gridPanel=this.grid;
								this.formPanel.getForm().submit({
									url : __ctxPath
											+ '/document/addDocPrivilege.do',
									method : 'post',
									waitMsg : '正在提交...',
									success : function(fp, action) {
										Ext.ux.Toast.msg('提示', '保存成功！');
										gridPanel.getStore().reload();
										win.close();
									},
									failure : function(fp, action) {
										Ext.ux.Toast.msg('出错', '请与管理员联系！');
									}
								});
							} else {
								Ext.ux.Toast.msg('提示', '读权限为基本权限！');
							}
						} else {
							Ext.ux.Toast.msg('提示', '请选择！');
						}
					}
				}, {
					xtype : 'button',
					iconCls : 'close',
					text : '关闭',
					scope:this,
					handler : function() {
						this.close();
					}
				}];
     }
});

//var DocFolderSharedForm = function(privilegeId, folderId) {
//	this.folderId = folderId;
//	// var folderId = this.folderId;
//	this.privilegeId = privilegeId;
//	var checkRead=function(){
//	    alert('ffdf');
//	}
//	var formPanel = new Ext.FormPanel({
//		id : 'DocFolderSharedForm',
//		bodyStyle : 'padding:4px 10px 4px 10px',
//		items : [{
//					xtype : 'hidden',
//					name : 'privilegeId',
//					value : this.privilegeId
//
//				}, {
//					xtype : 'hidden',
//					name : 'folderId',
//					value : this.folderId
//				}, {
//					xtype : 'fieldset',
//					border : false,
//					layout : 'column',
//					items : [{
//								xtype : 'label',
//								text : '共享人员',
//								width : 100
//							}, {
//								xtype : 'hidden',
//								name : 'userIds',
//								id : 'userIds'
//							}, {
//								xtype : 'textarea',
//								name : 'userNames',
//								id : 'userNames',
//								width : 300
//							}, {
//								xtype : 'button',
//								text : '选择',
//								iconCls : 'btn-select',
//								width : 80,
//								handler : function() {
//									// 显示选择器，并且设置用户
//									UserSelector.getView(
//											function(uIds, fnames) {
//												var sharedUserIds = Ext
//														.getCmp('userIds');
//												var sharedUserNames = Ext
//														.getCmp('userNames');
//
//												if (sharedUserIds.getValue() == '') {// 若原没有值，则直接设置
//													sharedUserIds
//															.setValue(uIds);
//													sharedUserNames
//															.setValue(fnames);
//													return;
//												}
//												// 去掉重复的人员
//												var vIds = sharedUserIds
//														.getValue().split(',');
//												var fnms = sharedUserNames
//														.getValue().split(',');
//
//												sharedUserIds
//														.setValue(uniqueArray(vIds
//																.concat(uIds
//																		.split(','))));
//												sharedUserNames
//														.setValue(uniqueArray(fnms
//																.concat(fnames
//																		.split(','))));
//
//											}).show();
//								}
//							}, {
//								xtype : 'button',
//								iconCls : 'btn-clear',
//								text : '清空',
//								handler : function() {
//									var sharedUserIds = Ext.getCmp('userIds');
//									var sharedUserNames = Ext
//											.getCmp('userNames');
//
//									sharedUserIds.setValue('');
//									sharedUserNames.setValue('');
//								},
//								width : 80
//							}]
//				}, {
//					xtype : 'fieldset',
//					border : false,
//					layout : 'column',
//					items : [{
//								xtype : 'label',
//								text : '共享部门',
//								width : 100
//							}, {
//								name : 'depIds',
//								id : 'depIds',
//								xtype : 'hidden'
//							}, {
//								name : 'depNames',
//								id : 'depNames',
//								xtype : 'textarea',
//								width : 300
//							}, {
//								xtype : 'button',
//								text : '选择',
//								iconCls : 'btn-select',
//								width : 80,
//								handler : function() {
//									DepSelector.getView(function(ids, names) {
//										var sharedDepIds = Ext.getCmp('depIds');
//										var sharedDepNames = Ext
//												.getCmp('depNames');
//
//										if (sharedDepIds.getValue() == '') {// 若原没有值，则直接设置
//											sharedDepIds.setValue(ids);
//											sharedDepNames.setValue(names);
//											return;
//										}
//										// 去掉重复的部门
//										var vIds = sharedDepIds.getValue()
//												.split(',');
//										var fnms = sharedDepNames.getValue()
//												.split(',');
//
//										sharedDepIds.setValue(uniqueArray(vIds
//												.concat(ids.split(','))));
//										sharedDepNames
//												.setValue(uniqueArray(fnms
//														.concat(names
//																.split(','))));
//
//									}).show();
//								}
//							}, {
//								xtype : 'button',
//								text : '清空',
//								iconCls : 'btn-clear',
//								handler : function() {
//									var sharedDepIds = Ext.getCmp('depIds');
//									var sharedDepNames = Ext.getCmp('depNames');
//
//									sharedDepIds.setValue('');
//									sharedDepNames.setValue('');
//								},
//								width : 80
//							}]
//				}, {
//					xtype : 'fieldset',
//					border : false,
//					layout : 'column',
//					items : [{
//								xtype : 'label',
//								text : '共享角色',
//								width : 100
//							}, {
//								xtype : 'hidden',
//								id : 'roleIds',
//								name : 'roleIds'
//							}, {
//								name : 'roleNames',
//								id : 'roleNames',
//								xtype : 'textarea',
//								width : 300
//							}, {
//								xtype : 'button',
//								text : '选择',
//								iconCls : 'btn-select',
//								width : 80,
//								handler : function() {
//									RoleSelector.getView(function(ids, names) {
//
//										var sharedRoleIds = Ext
//												.getCmp('roleIds');
//										var sharedRoleNames = Ext
//												.getCmp('roleNames');
//
//										if (sharedRoleIds.getValue() == '') {// 若原没有值，则直接设置
//											sharedRoleIds.setValue(ids);
//											sharedRoleNames.setValue(names);
//											return;
//										}
//
//										// 去掉重复的部门
//										var vIds = sharedRoleIds.getValue()
//												.split(',');
//										var fnms = sharedRoleNames.getValue()
//												.split(',');
//
//										sharedRoleIds.setValue(uniqueArray(vIds
//												.concat(ids.split(','))));
//										sharedRoleNames
//												.setValue(uniqueArray(fnms
//														.concat(names
//																.split(','))));
//
//									}).show();
//								}
//							}, {
//								xtype : 'button',
//								text : '清空',
//								iconCls : 'btn-clear',
//								handler : function() {
//									var sharedRoleIds = Ext.getCmp('roleIds');
//									var sharedRoleNames = Ext
//											.getCmp('roleNames');
//
//									sharedRoleIds.setValue('');
//									sharedRoleNames.setValue('');
//								},
//								width : 80
//							}]
//				}, {
//					xtype : 'fieldset',
//					border : false,
//					layout : 'column',
//					items : [{
//								xtype : 'label',
//								text : '权限选择：',
//								width : 100
//							}, {
//								xtype : 'checkbox',
//								name : 'rightR',
//								id : 'rightR'
//							}, {
//								xtype : 'label',
//								text : '可读',
//								width : 60
//							}, {
//								xtype : 'checkbox',
//								name : 'rightU',
//								id : 'rightU',
//								listeners:{
//								   "check":function(){
//								      var rightU=Ext.getCmp('rightU');
//								      var rightD=Ext.getCmp('rightD');
//								       var rightR=Ext.getCmp('rightR');
//								      if(rightU.getValue()){
//								         rightR.setValue(true);
//								         rightR.disable();
//								      }else if(!rightD.getValue()){
//								         rightR.enable();
//								      }
//								   	
//								   }
//								}
//							}, {
//								xtype : 'label',
//								text : '可修改',
//								width : 60
//							}, {
//								xtype : 'checkbox',
//								name : 'rightD',
//								id : 'rightD',
//								listeners:{
//								   "check":function(){
//								      var rightD=Ext.getCmp('rightD');
//								      var rightU=Ext.getCmp('rightU');
//								      var rightR=Ext.getCmp('rightR');
//								      if(rightD.getValue()){
//								         rightR.setValue(true);
//								         rightR.disable();
//								      }else if(!rightU.getValue()){
//								         rightR.enable();
//								      }
//								   	
//								   }
//								}
//							}, {
//								xtype : 'label',
//								text : '可删除',
//								width : 60
//							}]
//
//				}]
//	});
// 
//	var win = new Ext.Window({
//				title : '文件夹授权',
//				iconCls : 'menu-public-fol',
//				width : 620,
//				height : 420,
//				modal : true,
//				layout : 'anchor',
//				plain : true,
//				bodyStyle : 'padding:5px;',
//				scope : this,
//				buttonAlign : 'center',
//				items : formPanel,
//				buttons : [{
//					xtype : 'button',
//					text : '共享',
//					iconCls : 'btn-ok',
//					handler : function() {
//						var userIds = Ext.getCmp('userIds').getValue();
//						var depIds = Ext.getCmp('depIds').getValue();
//						var roleIds = Ext.getCmp('roleIds').getValue();
//						var rightR = Ext.getCmp('rightR').getValue();
//						if (userIds != '' || depIds != '' || roleIds != '') {
//							if (rightR == true) {
//								var rightRC = Ext.getCmp('rightR');
//								rightRC.enable();
//								formPanel.getForm().submit({
//									url : __ctxPath
//											+ '/document/addDocPrivilege.do',
//									method : 'post',
//									waitMsg : '正在提交...',
//									success : function(fp, action) {
//										Ext.ux.Toast.msg('提示', '保存成功！');
//										Ext.getCmp('DocPrivilegeGrid')
//												.getStore().reload();
//										win.close();
//									},
//									failure : function(fp, action) {
//										Ext.ux.Toast.msg('出错', '请与管理员联系！');
//									}
//								});
//							} else {
//								Ext.ux.Toast.msg('提示', '读权限为基本权限！');
//							}
//						} else {
//							Ext.ux.Toast.msg('提示', '请选择！');
//						}
//					}
//				}, {
//					xtype : 'button',
//					iconCls : 'btn-cancel',
//					text : '关闭',
//					handler : function() {
//						win.close();
//					}
//				}]
//
//			});
//	win.show();
//};