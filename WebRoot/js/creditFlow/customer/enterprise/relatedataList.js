function relatedataListWin(orgVal){
	var pageSize = 10;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_relatedata = new CS.data.JsonStore({
				url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseRelatedata.do',
				fields : [ {
						name : 'id'
					},{
						name : 'enterpriseid'
					}, {
						name : 'recorddate'
					}, {
						name : 'energy'
					}, {
						name : 'receipts'
					}, {
						name : 'number'
					},{
						name : 'unit'
					},{
						name : 'money'
					},{
						name : 'remarks'
					},{
						name : 'sldw'
					},{
						name : 'sjlx'
					},{
						name : 'recordperiod'
					}],
				baseParams : {
					id : enterpriseId
				},
				listeners : {
					'load' : function() {
						gPanel_enterpriseRelatedata.getSelectionModel().selectRow(0);
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add_relatedata = new CS.button.AButton({
				handler : function() {
					var fPanel_add = new CS.form.FormPanel({
						url : __ctxPath + '/creditFlow/customer/enterprise/addEnterpriseRelatedata.do',
						monitorValid : true,
						labelWidth : 100,
						bodyStyle:'padding:10px',
						width : 488,
						height : 240,  
						items : [{
					        layout:'column',
					        items:[{
					        	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items : [{
					                xtype:'textfield',
					                fieldLabel: '单据名称及编号',
					                name: 'enterpriseRDate.receipts',
					                allowBlank : false ,
				                    blankText : '单据名称及编号不允许为空'
				                },{
				                	xtype:'datefield',
					                format : 'Y-m-d',
					                fieldLabel: '单据日期',
					                name: 'enterpriseRDate.recorddate',
					                allowBlank : false ,
				                    blankText : '数据时间不允许为空'
				                },{
				                	xtype:'numberfield',
					                fieldLabel: '数量',
					                name: 'enterpriseRDate.number'
				                },{
				                	xtype:'numberfield',
					                fieldLabel: '金额(元)',
					                name: 'enterpriseRDate.money',
					                allowBlank : false ,
				                    blankText : '金额不允许为空'
				                }]
					        },{
					        	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
					                xtype:'textfield',
					                fieldLabel: '单据期间',
					                name: 'enterpriseRDate.recordperiod'
				                },{
				                	xtype : "dickeycombo",
															nodeKey :'djlx',
									fieldLabel : '单据类型',
					                hiddenName: 'enterpriseRDate.energy',
									//dicId : DatatypeDicId,
									width : 80 ,
									allowBlank : false ,
				                    blankText : '金额不允许为空',
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
				                },{
					                xtype : 'csRemoteCombo',
									fieldLabel : '数量单位',
									width : 80 ,
					                hiddenName: 'enterpriseRDate.unit',
									dicId : unitofquantityDicId
				                }]
					        },{
					        	columnWidth:1,
				                layout: 'form',
				                items : [{
				                		xtype : 'textarea',
				            			anchor : '99.5%',
										fieldLabel : '备注',
										name : 'enterpriseRDate.remarks'
				                }]
					        },{
					        	xtype : 'hidden',
					            name : 'enterpriseRDate.enterpriseid',
					            value : enterpriseId
					        }]
					    }],
						buttons : [{
							text : '保存',
							formBind : true,
							iconCls : 'submitIcon',
							handler : function() {
								fPanel_add.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.ux.Toast.msg('状态', '添加成功!');
													jStore_relatedata.reload();
													Ext.getCmp('win_addRelatedata').destroy() ;
									},
									failure : function(form, action) {
										if(action.response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(action.response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','添加企业相关数据信息失败!');	
										}
									}
								});
							}
						}]
					});
					var win_add_debt = new Ext.Window({
						id : 'win_addRelatedata',
						title : '新增<font color=red><'+obj.data.shortname+'></font>企业相关数据',
						layout : 'fit',
						width :(screen.width-180)*0.5 - 20,
						height : 280,
						closable : true,
						resizable : true,
						constrainHeader : true ,
						collapsible : true, 
						plain : true,
						border : false,
						bodyStyle:'overflowX:hidden',
						modal : true,
						maximizable : true,
						buttonAlign : 'center',
						items :[fPanel_add],
						listeners : {
							'beforeclose' : function(){
								if(fPanel_add != null){
									if(fPanel_add.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												fPanel_add.buttons[0].handler.call() ;
											}else{
												fPanel_add.getForm().reset() ;
												win_add_debt.destroy() ;
											}
										}) ;
										return false ;
									}
								}
							}
						}
					}).show();
				}
			});
			var button_update_Debt = new CS.button.UButton({
				handler : function() {
					var selected = gPanel_enterpriseRelatedata.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseRelatedata.do',
							method : 'POST',
							success : function(response, request) {
								var obj = Ext.decode(response.responseText);
								var relateData = obj.data;
								var fPanel_update = new CS.form.FormPanel({
									url:__ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseRelatedata.do',
									labelWidth : 100,
									bodyStyle:'padding:10px',
									width : 488,
									height : 240,
									monitorValid : true,
									items : [{
								        layout:'column',
								        items:[{
								        	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items : [{
								                xtype:'textfield',
								                fieldLabel: '单据名称及编号',
								                name: 'enterpriseRDate.receipts',
								                allowBlank : false ,
							                    blankText : '单据名称及编号不允许为空',
							                    value : relateData.receipts
							                },{
							                	xtype:'datefield',
								                format : 'Y-m-d',
								                fieldLabel: '单据日期',
								                name: 'enterpriseRDate.recorddate',
								                allowBlank : false ,
							                    blankText : '数据时间不允许为空',
							                    value : relateData.recorddate
							                },{
							                	xtype:'numberfield',
								                fieldLabel: '数量',
								                name: 'enterpriseRDate.number',
								                value : relateData.number
							                },{
							                	xtype:'numberfield',
								                fieldLabel: '金额(元)',
								                name: 'enterpriseRDate.money',
								                allowBlank : false ,
							                    blankText : '金额不允许为空',
							                    value : relateData.money
							                }]
								        },{
								        	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
								                xtype:'textfield',
								                fieldLabel: '单据期间',
								                name: 'enterpriseRDate.recordperiod',
								                value : relateData.recordperiod
							                },{
							                	xtype : "dickeycombo",
															nodeKey :'djlx',
												fieldLabel : '数据类型',
								                hiddenName: 'enterpriseRDate.energy',
												//dicId : DatatypeDicId,
												allowBlank : false ,
												width : 80 ,
							                    blankText : '金额不允许为空',
							                    value : relateData.energy,
							                    //valueNotFoundText : relateData.sjlx
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
							                },{
								                xtype : "dickeycombo",
															nodeKey :'sldw',
												fieldLabel : '数量单位',
												width : 80 ,
								                hiddenName: 'enterpriseRDate.unit',
												//dicId : unitofquantityDicId,
												value : relateData.unit,
												//valueNotFoundText : relateData.sldw
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
							                items : [{
							                		xtype : 'textarea',
							            			anchor : '99.5%',
													fieldLabel : '备注',
													name : 'enterpriseRDate.remarks',
													value : relateData.remarks
							                }]
								        },{
								        	xtype : 'hidden',
								            name : 'enterpriseRDate.enterpriseid',
								            value : relateData.enterpriseid
								        },{
								        	xtype : 'hidden',
							            	name : 'enterpriseRDate.id',
							            	value : relateData.id
								        }]
								    }],
									buttons : [ {
										text : '保存',
										formBind : true,
										iconCls : 'submitIcon',
										handler : function() {
											fPanel_update.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.ux.Toast.msg('状态', '保存成功!');
																	jStore_relatedata.reload();
																	Ext.getCmp('win_updateRelatedata').destroy();
												},
												failure : function(form, action) {
													if(action.response.status==0){
														Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
													}else if(action.response.status==-1){
														Ext.ux.Toast.msg('状态','连接超时，请重试!');
													}else{
														Ext.ux.Toast.msg('状态','编辑失败!');								
													}
												}
											});
										}
									}]
								});
								var window_update = new Ext.Window({
									id : 'win_updateRelatedata',
									title : '编辑<font color=red><'+obj.data.shortname+'></font>企业相关数据',
									layout : 'fit',
									width :(screen.width-180)*0.5 - 20,
									height : 280,
									closable : true,
									resizable : false,
									constrainHeader : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									bodyStyle:'overflowX:hidden',
									items :[fPanel_update],
									listeners : {
										'beforeclose' : function(){
											if(fPanel_update != null){
												if(fPanel_update.getForm().isDirty()){
													Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
														if(btn=='yes'){
															fPanel_update.buttons[0].handler.call() ;
														}else{
															fPanel_update.getForm().reset() ;
															window_update.destroy() ;
														}
													}) ;
													return false ;
												}
											}
										}
									}
								}).show();
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
					var selected = gPanel_enterpriseRelatedata.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseRelatedata.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态','删除成功!');
										jStore_relatedata.reload();
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
					var selected = gPanel_enterpriseRelatedata.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var id = selected.get('id');
						seeEnterpriseDebt(id);
					}
				}
			});
			var cModel_enterpriseRelatedata = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : '序',
							width : 35
						}),{
							header : '单据期间',
							width : 100,
							sortable : true,
							dataIndex : 'recordperiod'
						}, {
					header : '单据日期',
					width : 80,
					sortable : true,
					dataIndex : 'recorddate'
				}, {
					header : '单据类型',
					width : 100,
					sortable : true,
					dataIndex : 'sjlx'
				}, {
					header : '单据名称及编号',
					width : 120,
					sortable : true,
					dataIndex : 'receipts'
				}, {
					header : '数量',
					width : 60,
					sortable : true,
					dataIndex : 'number'
				}, {
					header : '数量单位',
					width : 80,
					sortable : true,
					dataIndex : 'sldw'
				}, {
					header : '金额(元)',
					width : 80,
					sortable : true,
					dataIndex : 'money'
			}]);
			var gPanel_enterpriseRelatedata = new Ext.grid.GridPanel({
				pageSize : pageSize ,
				store : jStore_relatedata,
				autoWidth : true,
				border : false ,
				height:440,
				colModel : cModel_enterpriseRelatedata,
				autoExpandColumn : 5,
				loadMask : new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				}),
				bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					autoWidth : false ,
					style : '',
					displayInfo : true,
					displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
					emptyMsg : '没有符合条件的记录',
					store : jStore_relatedata
				}),
				tbar : [button_add_relatedata, button_see, button_update_Debt,
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
					url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseRelatedata.do',
					method : 'POST',
					success : function(response, request) {
						var obj = Ext.decode(response.responseText);
						var relateData = obj.data ;
						var panel_see = new CS.form.FormPanel({
							labelWidth : 100,
							bodyStyle:'padding:10px',
							width : 488,
							height : 240,
							items : [{
						        layout:'column',
						        items:[{
						        	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items : [{
						                xtype:'textfield',
						                fieldLabel: '单据名称及编号',
					                    value : relateData.receipts,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'textfield',
						                fieldLabel: '单据日期',
					                    value : relateData.recorddate,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'textfield',
						                fieldLabel: '数量',
						                value : relateData.number,
						                readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'textfield',
						                fieldLabel: '金额(元)',
					                    value : relateData.money,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                }]
						        },{
						        	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
						                xtype:'textfield',
						                fieldLabel: '单据期间',
						                value : relateData.recordperiod,
						                readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype : 'textfield',
										fieldLabel : '数据类型',
					                    value : relateData.sjlx,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
						                xtype : 'textfield',
										fieldLabel : '数量单位',
										value : relateData.sldw,
										readOnly  : true,
										cls : 'readOnlyClass'
					                }]
						        },{
						        	columnWidth:1,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items : [{
					                		xtype : 'textarea',
											fieldLabel : '备注',
											value : relateData.remarks,
											readOnly  : true,
											cls : 'readOnlyClass'
					                }]
						        }]
						    }]
						});
						var window_see = new Ext.Window({
							id : 'win_seeRelatedata',
							title : '查看<font color=red><'+obj.data.shortname+'></font>企业相关数据',
							layout : 'fit',
							width :(screen.width-180)*0.5 - 20,
							height : 280,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							modal : true,
							closable : true,
							border : false,
							bodyStyle:'overflowX:hidden',
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
			jStore_relatedata.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			var win_listEnterpriseRelatedata = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业相关数据',
				width :(screen.width-180)*0.5 + 30,
				height : 400,
				buttonAlign : 'center',
				layout : 'fit',
				modal : true,
				maximizable : true,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_enterpriseRelatedata]
			}).show();
    	}, 
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}