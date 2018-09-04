function outassureListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_outassure = new CS.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseOutassure.do',
				fields : [ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'assureobject'
				}, {
					name : 'money'
				}, {
					name : 'startdate'
				}, {
					name : 'enddate'
				},{
					name : 'assureterm'
				},{
					name : 'objectstatus'
				},{
					name : 'dutyrate'
				},{
					name : 'dutymoney'
				},{
					name : 'remarks'
				},{name :　'assureway'},
				{name : 'dbfs'},
				{name :'pretotalearn'},{
					name : 'assurewayValue'
				}],
				baseParams : {
					id : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_enterpriseOutassure.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add_debt = new Ext.Button({
				text : '增加',
				tooltip : '增加一条新的企业关联公司信息',
				iconCls : 'addIcon',
				scope : this,
				handler : function() {
					var fPanel_addOutassure = new CS.form.FormPanel({
						id : 'fPanel_addOutassure',
						url:__ctxPath + '/creditFlow/customer/enterprise/addEnterpriseOutassure.do',
						monitorValid : true,
						labelWidth : 90,
						bodyStyle:'padding:10px',
						width : 488,
						height : 328,
						items : [ {
				            layout:'column',
				            items : [{
				            	columnWidth:1,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'textfield',
				                    fieldLabel: '担保对象',
				                    name: 'enterpriseOutassure.assureobject'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype : "dickeycombo",
															nodeKey :'9',
				                    fieldLabel: '担保方式',
				                    width : 100 ,
									hiddenName : 'enterpriseOutassure.assureway',
									//dicId : assuremodeid
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
				                    xtype:'numberfield',
				                    fieldLabel: '担保金额(万)',
				                    name: 'enterpriseOutassure.money'
				                },{
				                	xtype:'numberfield',
				                    fieldLabel: '担保期限(月)',
				                    name: 'enterpriseOutassure.assureterm'
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '责任比例(%)',
				                    name: 'enterpriseOutassure.dutyrate'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				             		xtype:'textfield',
				                    fieldLabel: '担保内容',
				                    name: 'enterpriseOutassure.assurecontent'
				                },{
				                	xtype:'datefield',
				                    format : 'Y-m-d',
				                    fieldLabel: '担保起始日期',
				                    name: 'enterpriseOutassure.startdate'
				                },{
				                	xtype:'datefield',
				                    format : 'Y-m-d',
				                    fieldLabel: '担保截止日期',
				                    name: 'enterpriseOutassure.enddate'
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '担保解除条件',
				                    name: 'enterpriseOutassure.assurecondition'
				                }]
				            },{
				            	columnWidth:.41,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                    xtype:'numberfield',
				                    fieldLabel: '责任余额(万)',
				                    name: 'enterpriseOutassure.dutymoney'
				                }]
				            },{
				            	columnWidth:.59,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                labelWidth : 190,
				                items :[{
				                	xtype:'numberfield',
				                    fieldLabel: '预计担保期内支付的担保金额(万)',
				                    name: 'enterpriseOutassure.pretotalearn'
				                }]
				            },{
				            	columnWidth:1,
				                layout: 'form',
				                labelWidth : 140,
				                defaults : {anchor:anchor},
				            	items :[{
				            		xtype:'textfield',
				                    fieldLabel: '被担保方目前运营情况',
				                    name: 'enterpriseOutassure.objectstatus'
				            	}]
				            },{
				            		columnWidth : 1,
				            		layout: 'form',
									items :[{
										xtype : 'textarea',
				            			anchor : anchor,
										fieldLabel : '备注',
										name : 'enterpriseOutassure.remarks'
									}]
				            },{
				            	xtype : 'hidden',
				            	name : 'enterpriseOutassure.enterpriseid',
				            	value : enterpriseId
				            }]
				        } ],
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
						buttons : [ {
							text : '保存',
							iconCls : 'submitIcon',
							handler : function() {
								fPanel_addOutassure.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.ux.Toast.msg('状态', '添加成功!');
														jStore_outassure.reload();
														Ext.getCmp('win_addOutassure').destroy();
									},
									failure : function(form, action) {
										if(action.response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(action.response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','添加失败!');	
										}
									}
								});
							}
						 }]
					});
					
					var win_add_company = new Ext.Window({
						id : 'win_addOutassure',
						title: '新增<font color=red><'+obj.data.shortname+'></font>企业对外担保信息',
						layout : 'fit',
						width : 500,
						height : 360,
						closable : true,
						resizable : false,
						constrainHeader : true ,
						collapsible : true, 
						autoScroll : true,
						plain : true,
						border : false,
						modal : true,
						maximizable : true,
						bodyStyle : 'overflowX:hidden',
						buttonAlign: 'center',
						items:[fPanel_addOutassure],listeners : {
							'beforeclose' : function(){
								var formPanelObj = Ext.getCmp('fPanel_addOutassure');
								if(formPanelObj != null){
									if(formPanelObj.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												formPanelObj.buttons[0].handler.call() ;
											}else{
												formPanelObj.getForm().reset() ;
												win_add_company.destroy() ;
											}
										}) ;
										return false ;
									}
								}
							}
						}
					});
						win_add_company.show();
				}
			});
			var button_update_company = new CS.button.UButton({
				handler : function() {
					var selected = gPanel_enterpriseOutassure.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseOutassure.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.decode(response.responseText);
								var outassureData = obj.data ;
								var fPanel_update = new CS.form.FormPanel({
									id :'fPanel_updateOutassure',
									//url:'updateOutassure.do',
									url:__ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseOutassure.do',
									monitorValid : true,
									labelWidth : 90,
									bodyStyle:'padding:10px',
									width : 488,
									height : 328,
									items : [ {
							            layout:'column',
							            items : [{
							            	columnWidth:1,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype:'textfield',
							                    fieldLabel: '担保对象',
							                    name: 'enterpriseOutassure.assureobject',
							                    value : outassureData.assureobject
							                }]
							            },{
							            	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype : "dickeycombo",
															nodeKey :'9',
							                    fieldLabel: '担保方式',
							                    width : 100 ,
												hiddenName : 'enterpriseOutassure.assureway',
												//dicId : assuremodeid,
												value : outassureData.assureway,
												//valueNotFoundText : outassureData.dbfs
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
							                    xtype:'numberfield',
							                    fieldLabel: '担保金额(万)',
							                    name: 'enterpriseOutassure.money',
							                    value : outassureData.money
							                },{
							                	xtype:'numberfield',
							                    fieldLabel: '担保期限(月)',
							                    name: 'enterpriseOutassure.assureterm',
							                    value : outassureData.assureterm
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '责任比例(%)',
							                    name: 'enterpriseOutassure.dutyrate',
							                    value : outassureData.dutyrate
							                }]
							            },{
							            	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							             		xtype:'textfield',
							                    fieldLabel: '担保内容',
							                    name: 'enterpriseOutassure.assurecontent',
							                    value : outassureData.assurecontent
							                },{
							                	xtype:'datefield',
							                    format : 'Y-m-d',
							                    fieldLabel: '担保起始日期',
							                    name: 'enterpriseOutassure.startdate',
							                    value : outassureData.startdate
							                },{
							                	xtype:'datefield',
							                    format : 'Y-m-d',
							                    fieldLabel: '担保截止日期',
							                    name: 'enterpriseOutassure.enddate',
							                    value : outassureData.enddate
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '担保解除条件',
							                    name: 'enterpriseOutassure.assurecondition',
							                    value : outassureData.assurecondition
							                }]
							            },{
							            	columnWidth:.41,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                    xtype:'numberfield',
							                    fieldLabel: '责任余额(万)',
							                    name: 'enterpriseOutassure.dutymoney',
							                    value : outassureData.dutymoney
							                }]
							            },{
							            	columnWidth:.59,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                labelWidth : 190,
							                items :[{
							                	xtype:'numberfield',
							                    fieldLabel: '预计担保期内支付的担保金额(万)',
							                    name: 'enterpriseOutassure.pretotalearn',
							                    value : outassureData.pretotalearn
							                }]
							            },{
							            	columnWidth:1,
							                layout: 'form',
							                labelWidth : 140,
							                defaults : {anchor:anchor},
							            	items :[{
							            		xtype:'textfield',
							                    fieldLabel: '被担保方目前运营情况',
							                    name: 'enterpriseOutassure.objectstatus',
							                    value : outassureData.objectstatus
							            	}]
							            },{
							            		columnWidth : 1,
							            		layout: 'form',
												items :[{
													xtype : 'textarea',
							            			anchor : anchor,
													fieldLabel : '备注',
													name : 'enterpriseOutassure.remarks',
													value : outassureData.remarks
												}]
							            },{
							            	xtype : 'hidden',
							            	name : 'enterpriseOutassure.enterpriseid',
							            	value : outassureData.enterpriseid
							            },{
							            	xtype : 'hidden',
							            	name : 'enterpriseOutassure.id',
							            	value : outassureData.id
							            }]
							        } ],
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
									buttons : [ {
										text : '保存',
										iconCls : 'submitIcon',
										handler : function() {
											fPanel_update.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.ux.Toast.msg('状态', '保存成功!');
																	jStore_outassure.reload();
																	Ext.getCmp('win_updateOutassure').destroy();
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
									id : 'win_updateOutassure',
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业对外担保信息',
									layout : 'fit',
									width : 500,
									height : 360,
									closable : true,
									resizable : false,
									constrainHeader : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									buttonAlign: 'center',
									bodyStyle : 'overflowX:hidden',
									items:[fPanel_update],listeners : {
										'beforeclose' : function(){
											var formPanelObj = Ext.getCmp('fPanel_updateOutassure');
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
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
						});	
					}
				}
			});
			var button_delete = new CS.button.DButton({
				handler : function() {
					var selected = gPanel_enterpriseOutassure.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseOutassure.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_outassure.reload() ;
									},
									failure : function(result, action) {
										if(response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','删除失败!');
										}
									},
									params: { id: id }
								});
							}
						});
					}
				}
			});
			var button_see = new CS.button.SButton({
				handler : function() {
					var selected = gPanel_enterpriseOutassure.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seeEnterpriseCompany(id);
					}
				}
			});
			var cModel_enterpriseCompany = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer({header:'序'}),
							{
								header : "担保对象",
								width : 100,
								sortable : true,
								dataIndex : 'assureobject'
							}, {
								header : "担保方式",
								width : 100,
								sortable : true,
								dataIndex : 'assurewayValue'
							},{
								header : "担保金额(万)",
								width : 80,
								sortable : true,
								dataIndex : 'money',
								renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
							}, {
								header : "担保截止日期",
								width : 90,
								sortable : true,
								dataIndex : 'enddate'
							},{
								header : "责任比例(%)",
								width : 80,
								sortable : true,
								dataIndex : 'dutyrate',
								renderer : function(v){if(v == 0){return "" ;}else{return v+'%';}}
							}, {
								header : "预计担保期内支付的担保金额(万)",
								width : 190,
								sortable : true,
								dataIndex : 'pretotalearn',
								renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
							}]);
		
			var gPanel_enterpriseOutassure = new Ext.grid.GridPanel( {
				pageSize : pageSize,
				store : jStore_outassure,
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
					store : jStore_outassure
				}),
				height:360,
				colModel : cModel_enterpriseCompany,
				tbar :isReadOnly?[button_see]:[button_add_debt,button_see,button_update_company,button_delete],
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seeEnterpriseCompany(id);
					}
				}
			});
			var seeEnterpriseCompany = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseOutassure.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.decode(response.responseText);
						var outassureData = obj.data ;
						var panel_see = new CS.form.FormPanel({
							labelWidth : 100,
							bodyStyle:'padding:10px',
							width : 488,
							height : 298,
							items : [ {
					            layout:'column',
					            items : [{
					            	columnWidth:1,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'textfield',
					                    fieldLabel: '担保对象',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.assureobject
					                }]
					            },{
					            	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype : "dickeycombo",
										nodeKey :'9',
					                    fieldLabel: '担保方式',
					                    width : 100 ,
										value : outassureData.assureway,
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
					                },{
					                    xtype:'numberfield',
					                    fieldLabel: '担保金额(万)',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.money
					                },{
					                	xtype:'numberfield',
					                    fieldLabel: '担保期限(月)',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.assureterm
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '责任比例(%)',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.dutyrate
					                }]
					            },{
					            	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					             		xtype:'textfield',
					                    fieldLabel: '担保内容',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.assurecontent
					                },{
					                	xtype:'datefield',
					                    format : 'Y-m-d',
					                    fieldLabel: '担保起始日期',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.startdate
					                },{
					                	xtype:'datefield',
					                    format : 'Y-m-d',
					                    fieldLabel: '担保截止日期',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.enddate
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '担保解除条件',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.assurecondition
					                }]
					            },{
					            	columnWidth:.41,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                    xtype:'numberfield',
					                    fieldLabel: '责任余额(万)',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.dutymoney
					                }]
					            },{
					            	columnWidth:.59,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                labelWidth : 190,
					                items :[{
					                	xtype:'numberfield',
					                    fieldLabel: '预计担保期内支付的担保金额(万)',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.pretotalearn
					                }]
					            },{
					            	columnWidth:1,
					                layout: 'form',
					                labelWidth : 140,
					                defaults : {anchor:anchor},
					            	items :[{
					            		xtype:'textfield',
					                    fieldLabel: '被担保方目前运营情况',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : outassureData.objectstatus
					            	}]
					            },{
					            		columnWidth : 1,
					            		layout: 'form',
										items :[{
											xtype : 'textarea',
					            			anchor : anchor,
											fieldLabel : '备注',
											readOnly  : true,
											cls : 'readOnlyClass',
											value : outassureData.remarks
										}]
					            }]
					        } ],
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
							}
					    });
						var window_see = new Ext.Window({
							id : 'win_seeOutassure',
							title: '查看<font color=red><'+obj.data.shortname+'></font>对外担保信息',
							layout : 'fit',
							width : 500,
							height : 330,
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							buttonAlign: 'right',
							bodyStyle : 'overflowX:hidden',
							items:[panel_see]
						});
						window_see.show();			
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
			}
			jStore_outassure.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			var win_listEnterpriseOutassure = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业对外担保信息',
				width : 580,
				height : 410,
				autoScroll : true ,
				border : true,
				buttonAlign : 'center',
				layout : 'fit',
				modal : true,
				maximizable : true,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_enterpriseOutassure]
			}).show();
    	},
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}