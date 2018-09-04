function debtListWin(orgVal,isReadOnly){
	var pageSize = 10;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_debt = new CS.data.JsonStore({
				url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseDebt.do',
				fields : [{
							name : 'id'
						}, {
							name : 'enterpriseid'
						}, {
							name : 'zwrpid'
						},{
							name : 'zwrname'
						} ,{
							name : 'creditmoney'
						}, {
							name : 'creditstartdate'
						}, {
							name : 'creditenddate'
						}, {
							name : 'repayment'
						}, {
							name : 'borrowemoney'
						}, {
							name : 'lastpaydate'
						}, {
							name : 'repaymentway'
						}, {
							name : 'voucherway'
						}, {
							name : 'assurecondition'
						}, {
							name : 'remarks'
						}, {
							name : 'chfs'
						}, {
							name : 'dbqk'
						}, {
							name : 'pzfs'
						},{
							name : 'repaymentwayValue'
						},{
							name : 'voucherwayValue'
						}],
				baseParams : {
					id : enterpriseId
				},
				listeners : {
					'load' : function() {
						gPanel_enterpriseDebt.getSelectionModel().selectRow(0);
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add_debt = new CS.button.AButton({
				handler : function() {
					var fPanel_add = new CS.form.FormPanel({
						id:'addDebtPanel',
						url : __ctxPath + '/creditFlow/customer/enterprise/addEnterpriseDebt.do',
						monitorValid : true,
						labelWidth : 100,
						bodyStyle:'padding:10px',
						layout:'column',
						width : 488,
						height : 328,
						items :[{
							columnWidth:.5,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	xtype : 'textfield',
								fieldLabel : '债权人',
								name : 'enterpriseDebt.zwrname'
				            }]
						},{
							columnWidth:.5,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	xtype : 'numberfield',
								fieldLabel : '借款金额(万)',
								name : 'enterpriseDebt.creditmoney'
				            }]
						},{
							columnWidth:1,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	xtype : 'textfield',
								fieldLabel : '债务说明',
								name : 'enterpriseDebt.debtexplain'
				            }]
						},{
							columnWidth:.5,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	xtype : 'datefield',
								fieldLabel : '借款起始日期',
								format : 'Y-m-d',
								name : 'enterpriseDebt.creditstartdate'
				            
				            },{
								xtype : 'numberfield',
								fieldLabel : '已还金额(万)',
								name : 'enterpriseDebt.repayment'
				            },{
				            	xtype : 'datefield',
								fieldLabel : '最后还款日期',
								format : 'Y-m-d',
								name : 'enterpriseDebt.lastpaydate'
				            }]
						},{
							columnWidth:.5,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
								xtype : 'datefield',
								fieldLabel : '借款截止日期',
								format : 'Y-m-d',
								name : 'enterpriseDebt.creditenddate'
				            },{
								xtype : 'numberfield',
								fieldLabel : '借款余额(万)',
								name : 'enterpriseDebt.borrowemoney'
				            },{
				            	xtype : "dickeycombo",
															nodeKey :'chfs',
								fieldLabel : '偿还方式',
								width : 80 ,
								listWidth :180,
								hiddenName : 'enterpriseDebt.repaymentway',
								//dicId : chfsDicId
								listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
								
				            }]
						},{
							columnWidth:1,
				            layout: 'form',
				            labelWidth : 180,
				            defaults : {anchor:anchor},
				            items :[{
				            	xtype : 'numberfield',
								fieldLabel : '预计担保期内支付的金额(万元)',
								name : 'enterpriseDebt.pretotalearn'
				            }]
						},{
							columnWidth:.5,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	
xtype : "dickeycombo",
															nodeKey :'pzfs',
								fieldLabel : '凭证方式',
								width : 80 ,
								listWidth :140,
								hiddenName : 'enterpriseDebt.voucherway',
								//dicId : voucherwayDicId
								listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
				            }]
						},{
							columnWidth:.5,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	
xtype : "dickeycombo",
															nodeKey :'dbqk',
								fieldLabel : '担保情况',
								width : 80 ,
								hiddenName : 'enterpriseDebt.assurecondition',
								//dicId : cdgDicId
								listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
				            }]
						},{
							columnWidth:1,
				            layout: 'form',
				            defaults : {anchor:anchor},
				            items :[{
				            	xtype : 'textarea',
								fieldLabel : '备注',
								name : 'enterpriseDebt.remarks'
				            }]
						},{
							xtype : 'hidden',
							name : 'enterpriseDebt.enterpriseid',
							value : enterpriseId
						}],
						listeners : {
							'render' : function(f){
								f.getForm().items.each(function(item,index,length){
									if(item.readOnly==true){
										item.on('specialkey',function(f,e){
											if(e.getKey()==Ext.EventObject.BACKSPACE){
												e.stopEvent();
											}
										}) ;
									}
								}) ;
							}
						},
						buttons : [{
							text : '保存',
							iconCls : 'submitIcon',
							handler : function() {
								fPanel_add.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.ux.Toast.msg('状态', '添加成功!');
													jStore_debt.removeAll();
													jStore_debt.reload();
													Ext.getCmp('win_addDebt').destroy() ;
									},
									failure : function(form, action) {
										if(action.response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(action.response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','添加企业债权信息失败!');	
										}
									}
								});
							}
						}]
					});
					var win_add_debt = new Ext.Window({
						id : 'win_addDebt',
						title : '新增<font color=red><'+obj.data.shortname+'></font>企业债务信息',
						layout : 'fit',
						width : 600,
						height : 360,
						closable : true,
						resizable : false,
						maximizable : true,
						constrainHeader : true ,
						collapsible : true, 
						modal : true,
						border : false,
						plain : true,
						bodyStyle : 'overflowX:hidden',
						buttonAlign : 'center',
						items :[fPanel_add],
						listeners : {
							'beforeclose' : function(){
								var formPanelObj = Ext.getCmp('addDebtPanel');
								if(formPanelObj != null){
									if(formPanelObj.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												formPanelObj.buttons[0].handler.call() ;
											}else{
												formPanelObj.getForm().reset() ;
												win_add_debt.destroy() ;
											}
										}) ;
										return false ;
									}
								}
							}
						}
					});
					win_add_debt.show();
				}
			});
			var button_update_Debt = new CS.button.UButton({
				handler : function() {
					var selected = gPanel_enterpriseDebt.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseDebt.do',
							method : 'POST',
							success : function(response, request) {
								var obj = Ext.decode(response.responseText);
								var debtData = obj.data;
								var fPanel_update = new CS.form.FormPanel({
									id : 'updateDebtPanel',
									url : __ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseDebt.do',
									monitorValid : true,
									labelWidth : 100,
									bodyStyle:'padding:10px',
									layout:'column',
									width : 488,
									height : 328,
									items :[{
										columnWidth:.5,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
							            	xtype : 'textfield',
											fieldLabel : '债权人',
											name : 'enterpriseDebt.zwrname',
											value : debtData.zwrname
							            }]
									},{
										columnWidth:.5,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
							            	xtype : 'numberfield',
											fieldLabel : '借款金额(万)',
											name : 'enterpriseDebt.creditmoney',
											value : debtData.creditmoney
							            }]
									},{
										columnWidth:1,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
							            	xtype : 'textfield',
											fieldLabel : '债务说明',
											name : 'enterpriseDebt.debtexplain',
											value : debtData.debtexplain
							            }]
									},{
										columnWidth:.5,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
							            	xtype : 'datefield',
											fieldLabel : '借款起始日期',
											format : 'Y-m-d',
											name : 'enterpriseDebt.creditstartdate',
											value : debtData.creditstartdate
							            
							            },{
											xtype : 'numberfield',
											fieldLabel : '已还金额(万)',
											name : 'enterpriseDebt.repayment',
											value : debtData.repayment
							            },{
							            	xtype : 'datefield',
											fieldLabel : '最后还款日期',
											format : 'Y-m-d',
											name : 'enterpriseDebt.lastpaydate',
											value : debtData.lastpaydate
							            }]
									},{
										columnWidth:.5,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
											xtype : 'datefield',
											fieldLabel : '借款截止日期',
											format : 'Y-m-d',
											name : 'enterpriseDebt.creditenddate',
											value : debtData.creditenddate
							            },{
											xtype : 'numberfield',
											fieldLabel : '借款余额(万)',
											name : 'enterpriseDebt.borrowemoney',
											value : debtData.borrowemoney
							            },{
							            	id : 'chfs',
											xtype : "dickeycombo",
											nodeKey :'chfs',
											fieldLabel : '偿还方式',
											hiddenName : 'enterpriseDebt.repaymentway',
											//dicId : chfsDicId,
											listWidth :180,
											width : 80 ,
											value : debtData.repaymentway,
											//valueNotFoundText : debtData.chfs
											listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
							            }]
									},{
										columnWidth:1,
							            layout: 'form',
							            labelWidth : 180,
							            defaults : {anchor:anchor},
							            items :[{
							            	xtype : 'numberfield',
											fieldLabel : '预计担保期内支付的金额(万元)',
											name : 'enterpriseDebt.pretotalearn',
											value : debtData.pretotalearn
							            }]
									},{
										columnWidth:.5,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
											id : 'pzfs',
							        		
											xtype : "dickeycombo",
											nodeKey :'pzfs',
											fieldLabel : '凭证方式',
											hiddenName : 'enterpriseDebt.voucherway',
											//dicId : voucherwayDicId,
											listWidth :140,
											width : 80 ,
											value : debtData.voucherway,
											//valueNotFoundText : debtData.pzfs
											listeners : {
												afterrender : function(combox) {
													var st = combox.getStore();
													st.on("load", function() {
														if(combox.getValue() == 0||combox.getValue()==1){
															combox.setValue("");
														}else{
															combox.setValue(combox
																.getValue());
														}
														combox.clearInvalid();
													})
												}
											}
							            }]
									},{
										columnWidth:.5,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
											id : 'dbqk',
							        		
											xtype : "dickeycombo",
											nodeKey :'dbqk',
											fieldLabel : '担保情况',
											hiddenName : 'enterpriseDebt.assurecondition',
											//dicId : cdgDicId,
											width : 80 ,
											value : debtData.assurecondition,
											//valueNotFoundText : debtData.dbqk
											listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
							            }]
									},{
										columnWidth:1,
							            layout: 'form',
							            defaults : {anchor:anchor},
							            items :[{
							            	xtype : 'textarea',
											fieldLabel : '备注',
											name : 'enterpriseDebt.remarks',
											value : debtData.remarks
							            }]
									},{
										xtype : 'hidden',
										name : 'enterpriseDebt.enterpriseid',
										value : debtData.enterpriseid
									},{
										xtype : 'hidden',
										name : 'enterpriseDebt.id',
										value : debtData.id
									}],
									listeners : {
										'render' : function(f){
											f.getForm().items.each(function(item,index,length){
												if(item.readOnly==true){
													item.on('specialkey',function(f,e){
														if(e.getKey()==Ext.EventObject.BACKSPACE){
															e.stopEvent();
														}
													}) ;
												}
											}) ;
										}
									},
									buttons : [{
										text : '保存',
										iconCls : 'submitIcon',
										handler : function() {
											fPanel_update.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.ux.Toast.msg('状态', '保存成功!');
																jStore_debt.reload();
																Ext.getCmp('win_updateDebt').destroy();
												},
												failure : function(form, action) {
													if(action.response.status==0){
														Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
													}else if(action.response.status==-1){
														Ext.ux.Toast.msg('状态','连接超时，请重试!');
													}else{
														Ext.ux.Toast.msg('状态', '保存失败!');
													}
												}
											});
										}
									}]
								});
								var window_update = new Ext.Window({
									id : 'win_updateDebt',
									title : '编辑<font color=red><'+obj.data.shortname+'></font>企业债务信息',
									layout : 'fit',
									width : 600,
									height : 360,
									closable : true,
									resizable : false,
									constrainHeader : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									bodyStyle : 'overflowX:hidden',
									buttonAlign : 'center',
									items :[fPanel_update],
									listeners : {
										'beforeclose' : function(){
											var formPanelObj = Ext.getCmp('updateDebtPanel');
											if(formPanelObj != null){
												if(formPanelObj.getForm().isDirty()){
													Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
														if(btn=='yes'){
															formPanelObj.buttons[0].handler.call() ;
														}else{
															formPanelObj.getForm().reset() ;
															window_update.destroy() ;
														}
													}) ;
													return false ;
												}
											}
										}
									}
								});
								window_update.show();
							},
							failure : function(response) {
								handleResponse(response,'编辑') ;
							},
							params : {
								id : id
							}
						});
					}
				}
			});
			var button_delete = new CS.button.DButton({
				handler : function() {
					var selected = gPanel_enterpriseDebt.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseDebt.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态','删除成功!');
										jStore_debt.reload();
									},
									failure : function(response) {
										handleResponse(response,'删除') ;
									},
									params : {
										id : id
									}
								});
							}
						});
					}
				}
			});
			var button_see = new CS.button.SButton({
				handler : function() {
					var selected = gPanel_enterpriseDebt.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('id');
						seeEnterpriseDebt(id);
					}
				}
			});
			var cModel_enterpriseDebt = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : '序',
							width : 35
						}), {
					header : "债权人",
					width : 85,
					sortable : true,
					dataIndex : 'zwrname'
				}, {
					header : "借款金额(万)",
					width : 85,
					sortable : true,
					dataIndex : 'creditmoney',
					renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
				},{
					header : "已还金额(万)",
					width : 85,
					sortable : true,
					dataIndex : 'repayment',
					renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
				},{
					header : "借款余额(万)",
					width : 85,
					sortable : true,
					dataIndex : 'borrowemoney',
					renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
				}, {
					header : "借款截止日期",
					width : 100,
					sortable : true,
					dataIndex : 'creditenddate'
				},{
					header : "偿还方式",
					width : 90,
					sortable : true,
					dataIndex : 'repaymentwayValue'
				},{
					header : "凭证方式",
					width : 90,
					sortable : true,
					dataIndex : 'voucherwayValue'
				}]);
			var gPanel_enterpriseDebt = new Ext.grid.GridPanel({
				pageSize : pageSize ,
				store : jStore_debt,
				autoWidth : true,
				border : false ,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				}),
				bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					autoWidth : false ,
					//width : 100 ,
					style : '',
					displayInfo : true,
					displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
					emptyMsg : '没有符合条件的记录',
					store : jStore_debt
				}),
				height:360,
				colModel : cModel_enterpriseDebt,
				//autoExpandColumn : 7,
				tbar : isReadOnly?[button_see]:[button_add_debt, button_see, button_update_Debt,
						button_delete],
				listeners : {
					'rowdblclick' : function(grid, index, e) {
						var id = grid.getSelectionModel().getSelected().get('id');
						seeEnterpriseDebt(id);
					}
				}
			});
			var seeEnterpriseDebt = function(id) {
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseDebt.do',
					method : 'POST',
					success : function(response, request) {
						var obj = Ext.decode(response.responseText);
						var debtData = obj.data ;
						var panel_see = new CS.form.FormPanel({
							labelWidth : 100,
							bodyStyle:'padding:10px',
							layout:'column',
							width : 488,
							height : 328,
							items :[{
								columnWidth:.5,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
					            	xtype : 'textfield',
									fieldLabel : '债权人',
									value : debtData.zwrname,
									readOnly  : true,
									cls : 'readOnlyClass'
					            }]
							},{
								columnWidth:.5,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
					            	xtype : 'numberfield',
									fieldLabel : '借款金额(万)',
									value : debtData.creditmoney,
									readOnly  : true,
									cls : 'readOnlyClass'
					            }]
							},{
								columnWidth:1,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
					            	xtype : 'textfield',
									fieldLabel : '债务说明',
									value : debtData.debtexplain,
									readOnly  : true,
									cls : 'readOnlyClass'
					            }]
							},{
								columnWidth:.5,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
					            	xtype : 'datefield',
									fieldLabel : '借款起始日期',
									format : 'Y-m-d',
									value : debtData.creditstartdate,
									readOnly  : true,
									cls : 'readOnlyClass'
					            
					            },{
									xtype : 'numberfield',
									fieldLabel : '已还金额(万)',
									value : debtData.repayment,
									readOnly  : true,
									cls : 'readOnlyClass'
					            },{
					            	xtype : 'datefield',
									fieldLabel : '最后还款日期',
									format : 'Y-m-d',
									value : debtData.lastpaydate,
									readOnly  : true,
									cls : 'readOnlyClass'
					            }]
							},{
								columnWidth:.5,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
									xtype : 'datefield',
									fieldLabel : '借款截止日期',
									format : 'Y-m-d',
									value : debtData.creditenddate,
									readOnly  : true,
									cls : 'readOnlyClass'
					            },{
									xtype : 'numberfield',
									fieldLabel : '借款余额(万)',
									value : debtData.borrowemoney,
									readOnly  : true,
									cls : 'readOnlyClass'
					            },{
					            	xtype : "dickeycombo",
									nodeKey :'chfs',
									fieldLabel : '偿还方式',
									listWidth :180,
									width : 80 ,
									value : debtData.repaymentway,
									readOnly : true,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if(combox.getValue() == 0||combox.getValue()==1){
													combox.setValue("");
												}else{
													combox.setValue(combox
														.getValue());
												}
												combox.clearInvalid();
											})
										}
									}}]
							},{
								columnWidth:1,
					            layout: 'form',
					            labelWidth : 180,
					            defaults : {anchor:anchor},
					            items :[{
					            	xtype : 'numberfield',
									fieldLabel : '预计担保期内支付的金额(万元)',
									value : debtData.pretotalearn,
									readOnly  : true,
									cls : 'readOnlyClass'
					            }]
							},{
								columnWidth:.5,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
										xtype : "dickeycombo",
										nodeKey :'pzfs',
										fieldLabel : '凭证方式',
										hiddenName : 'enterpriseDebt.voucherway',
										listWidth :140,
										width : 80 ,
										value : debtData.voucherway,
										readOnly : true,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if(combox.getValue() == 0||combox.getValue()==1){
														combox.setValue("");
													}else{
														combox.setValue(combox
															.getValue());
													}
													combox.clearInvalid();
												})
											}
										}
					            }]
							},{
								columnWidth:.5,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
									xtype : "dickeycombo",
									nodeKey :'dbqk',
									fieldLabel : '担保情况',
									width : 80 ,
									value : debtData.assurecondition,
									readOnly : true,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if(combox.getValue() == 0||combox.getValue()==1){
													combox.setValue("");
												}else{
													combox.setValue(combox
														.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
					            }]
							},{
								columnWidth:1,
					            layout: 'form',
					            defaults : {anchor:anchor},
					            items :[{
					            	xtype : 'textarea',
									fieldLabel : '备注',
									value : debtData.remarks,
									readOnly  : true,
									cls : 'readOnlyClass'
					            }]
							}]
						});
						var window_see = new Ext.Window({
							id : 'win_seeDebt',
							title : '查看<font color=red><'+obj.data.shortname+'></font>企业债务信息',
							layout : 'fit',
							width : 600,
							height : 360,
							resizable : true,
							maximizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							modal : true,
							border : false,
							minHeight : 350,
							minWidth : 330,
							buttonAlign : 'center',
							bodyStyle : 'overflowX:hidden',
							items :[panel_see]
						});
						window_see.show();
					},
					failure : function(response) {
						handleResponse(response,'查看') ;
					},
					params : {
						id : id
					}
				});
			}
			jStore_debt.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
        	var win_listEnterpriseDept = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业债务信息',
				width : 680,
				height : 410,
				buttonAlign : 'center',
				layout : 'fit',
				border : true,
				modal : true,
				maximizable : true,
				autoScroll : true ,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_enterpriseDebt]
			}).show();
    	},  
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}