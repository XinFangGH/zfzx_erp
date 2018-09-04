function outinvestListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_outinvest = new CS.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseOutinvest.do',
				fields : [ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'investobject'
				}, {
					name : 'money'
				}, {
					name : 'startdate'
				}, {
					name : 'enddate'
				},{
					name : 'expectincome'
				},{
					name : 'factincome'
				},{
					name : 'investway'
				},{
					name : 'pretotalearn'
				},{
					name :　'investincome'
				},{
					name : 'remarks'
				},{
					name : 'investvalue'
				},{
					name : 'investwayValue'
				}],
				baseParams : {
					id : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_enterpriseOutinvest.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add_debt = new CS.button.AButton({
				handler : function() {
					var fPanel_add = new CS.form.FormPanel({
						id : 'fPanel_addOutinvest',
						url:__ctxPath + '/creditFlow/customer/enterprise/addEnterpriseOutinvest.do',
						monitorValid : true,
						labelWidth : 90,
						bodyStyle:'padding:10px',
						width : 488,
						height : 328,
						items : [ {
				            layout:'column',
				            items:[{
				            	columnWidth:1,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'textfield',
				                    fieldLabel: '投资对象',
				                    name: 'enterpriseOutinvest.investobject',
									blankText : '投资对象不允许为空'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'numberfield',
				                    fieldLabel: '投资金额(万)',
				                    blankText : '投资金额不允许为空',
				                    name: 'enterpriseOutinvest.money'
				                	
				                },{
				                	xtype:'numberfield',
				                    fieldLabel: '预计收益(万)',
				                    name: 'enterpriseOutinvest.expectincome'
				                },{
									xtype:'numberfield',
				                    fieldLabel: '实际收益(万)',
				                    name: 'enterpriseOutinvest.factincome'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
									xtype : "dickeycombo",
									nodeKey :'tzfs',
									value:null,
				                    fieldLabel: '投资方式',
				                    width : 100 ,
									hiddenName : 'enterpriseOutinvest.investway',
									dicId : 160,
									listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																	    combox.setValue(combox.getValue());
																		combox.clearInvalid();
																	})
																}
															}
				                },{
				                	xtype:'datefield',
				                    format : 'Y-m-d',
				                    fieldLabel: '投资起始日期',
				                    name: 'enterpriseOutinvest.startdate'
				                },{
				                	xtype:'datefield',
				                    format : 'Y-m-d',
				                    fieldLabel: '投资截止日期',
				                    name: 'enterpriseOutinvest.enddate'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                labelWidth : 140,
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'numberfield',
				                    fieldLabel: '上期预计投资收益.万元',
				                    name: 'enterpriseOutinvest.bpredictincome'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                labelWidth : 140,
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'numberfield',
				                    fieldLabel: '上期实际投资收益.万元',
				                    name: 'enterpriseOutinvest.bpracticeincome'
				                }]
				            },{
				            	columnWidth:1,
				                layout: 'form',
				                labelWidth : 240,
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'numberfield',
				                    fieldLabel: '预计担保期内企业支出的投资金额 (万)',
				                    name: 'enterpriseOutinvest.pretotalearn',
				                    blankText : '投资金额不允许为空'
				                },{
				                	xtype:'numberfield',
				                    fieldLabel: '预计担保期内企业的投资收益金额(万)',
				                    name: 'enterpriseOutinvest.investincome',
				                    blankText : '投资金额不允许为空'
				                }]
				            },{          	
									columnWidth : 1,
				            		layout: 'form',
									items :[{
										xtype : 'textarea',
				            			anchor : '99.5%',
										fieldLabel : '备注',
										name : 'enterpriseOutinvest.remarks'
									}]
				
				            },{
				            	xtype : 'hidden',
				            	name : 'enterpriseOutinvest.enterpriseid',
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
								fPanel_add.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.ux.Toast.msg('状态', '添加成功!');
													jStore_outinvest.reload();
													Ext.getCmp('win_addOutinvest').destroy();
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
						} ]
					});
					var win_add_company = new Ext.Window({
						id : 'win_addOutinvest',
						title: '新增<font color=red><'+obj.data.shortname+'></font>企业对外投资信息',
						layout : 'fit',
						width : 500,
						height : 360,
						closable : true,
						constrainHeader : true ,
						collapsible : true, 
						resizable : false,
						plain : true,
						border : false,
						modal : true,
						maximizable : true,
						buttonAlign: 'center',
						bodyStyle:'overflowX:hidden',
						items:[fPanel_add],listeners : {
							'beforeclose' : function(){
								var formPanelObj = Ext.getCmp('fPanel_addOutinvest');
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
					var selected = gPanel_enterpriseOutinvest.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseOutinvest.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.decode(response.responseText);
								var outinvestData = obj.data ;
								var fPanel_update = new CS.form.FormPanel({
									id :'fPanel_updateOutinvest',
									url:__ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseOutinvest.do',
									monitorValid : true,
									labelWidth : 90,
									bodyStyle:'padding:10px',
									width : 488,
									height : 328,
									items : [{
							            layout:'column',
							            items:[{
							            	columnWidth:1,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype:'textfield',
							                    fieldLabel: '投资对象',
							                    name: 'enterpriseOutinvest.investobject',
												value : outinvestData.investobject
							                }]
							            },{
							            	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype:'numberfield',
							                    fieldLabel: '投资金额(万)',
							                    name: 'enterpriseOutinvest.money',
							                    value : outinvestData.money
							                	
							                },{
							                	xtype:'numberfield',
							                    fieldLabel: '预计收益(万)',
							                    name: 'enterpriseOutinvest.expectincome',
							                    value : outinvestData.expectincome
							                },{
												xtype:'numberfield',
							                    fieldLabel: '实际收益(万)',
							                    name: 'enterpriseOutinvest.factincome',
							                    value : outinvestData.factincome
							                }]
							            },{
							            	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	/*xtype : 'textfield',
												fieldLabel : '投资方式',
												name : 'enterpriseOutinvest.investway',
												value : outinvestData.investway*/
												xtype : "dickeycombo",
															nodeKey :'tzfs',
							                    fieldLabel: '投资方式',
							                    width : 100 ,
												hiddenName : 'enterpriseOutinvest.investway',
												//dicId : 160,
												value : outinvestData.investway,
												//valueNotFoundText : outinvestData.investvalue
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
							                	xtype:'datefield',
							                    format : 'Y-m-d',
							                    fieldLabel: '投资起始日期',
							                    name: 'enterpriseOutinvest.startdate',
							                    value : outinvestData.startdate
							                },{
							                	xtype:'datefield',
							                    format : 'Y-m-d',
							                    fieldLabel: '投资截止日期',
							                    name: 'enterpriseOutinvest.enddate',
							                    value : outinvestData.enddate
							                }]
							            },{
								            	columnWidth:.5,
								                layout: 'form',
								                labelWidth : 140,
								                defaults : {anchor:anchor},
								                items :[{
								                	xtype:'numberfield',
								                    fieldLabel: '上期预计投资收益.万元',
								                    name: 'enterpriseOutinvest.bpredictincome',
								                    value : outinvestData.bpredictincome
								                }]
								            },{
								            	columnWidth:.5,
								                layout: 'form',
								                labelWidth : 140,
								                defaults : {anchor:anchor},
								                items :[{
								                	xtype:'numberfield',
								                    fieldLabel: '上期实际投资收益.万元',
								                    name: 'enterpriseOutinvest.bpracticeincome',
								                    value : outinvestData.bpracticeincome
								                }]
								            },{
							            	columnWidth:1,
							                layout: 'form',
							                labelWidth : 240,
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype:'numberfield',
							                    fieldLabel: '预计担保期内企业支出的投资金额 (万)',
							                    name: 'enterpriseOutinvest.pretotalearn',
							                    value : outinvestData.pretotalearn
							                },{
							                	xtype:'numberfield',
							                    fieldLabel: '预计担保期内企业的投资收益金额(万)',
							                    name: 'enterpriseOutinvest.investincome',
							                    value : outinvestData.investincome
							                }]
							            },{          	
												columnWidth : 1,
							            		layout: 'form',
												items :[{
													xtype : 'textarea',
							            			anchor : '100%',
													fieldLabel : '备注',
													name : 'enterpriseOutinvest.remarks',
													value : outinvestData.remarks
												}]
							
							            },{
							            	xtype : 'hidden',
							            	name : 'enterpriseOutinvest.enterpriseid',
							            	value : outinvestData.enterpriseid
							            },{
							            	xtype : 'hidden',
							            	name : 'enterpriseOutinvest.id',
							            	value : outinvestData.id
							            }]
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
															jStore_outinvest.reload();
															Ext.getCmp('win_updateOutinvest').destroy();
												},
												failure : function(form, action) {
													if(action.response.status==0){
														Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
													}else if(action.response.status==-1){
														Ext.ux.Toast.msg('状态','连接超时，请重试!');
													}else{
														Ext.ux.Toast.msg('状态','保存失败!');
													}
												}
											});
										}
									}]
								});
								var window_update = new Ext.Window({
									id : 'win_updateOutinvest',
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业对外投资信息',
									layout : 'fit',
									width : 500,
									height : 360,
									closable : true,
									resizable : false,
									constrainHeader : true ,
									collapsible : true, 
									modal : true,
									plain : true,
									border : false,
									buttonAlign: 'center',
									bodyStyle:'overflowX:hidden',
									items :[fPanel_update],listeners : {
										'beforeclose' : function(){
											var formPanelObj = Ext.getCmp('fPanel_updateOutinvest');
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
					var selected = gPanel_enterpriseOutinvest.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseOutinvest.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_outinvest.reload() ;
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
					var selected = gPanel_enterpriseOutinvest.getSelectionModel().getSelected();
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
					new Ext.grid.RowNumberer(),
					{
						header : "投资对象",
						width : 100,
						sortable : true,
						dataIndex : 'investobject'
					},{
						header : "投资方式",
						sortable : true,
						dataIndex : 'investwayValue'
					}, {
						header : "投资金额(万)",
						width : 100,
						sortable : true,
						dataIndex : 'money',
						renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
					}, {
						header : "投资起始日期",
						width : 100,
						sortable : true,
						dataIndex : 'startdate'
					}, {
						header : "投资截止日期",
						width : 100,
						sortable : true,
						dataIndex : 'enddate'
					}, {
						header : "预计担保期内企业支出的投资金额(万)",
						width : 210,
						sortable : true,
						dataIndex : 'pretotalearn',
						renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
					},{
						header : "计担保期内企业的投资收益金额(万)",
						width : 200,
						sortable : true,
						dataIndex : 'investincome',
						renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
					}]);
			var gPanel_enterpriseOutinvest = new Ext.grid.GridPanel( {
				pageSize : pageSize,
				store : jStore_outinvest,
				colModel : cModel_enterpriseCompany,
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
					store : jStore_outinvest
				}),
				autoWidth : true,
				height:360,
				tbar : isReadOnly?[button_see]:[button_add_debt,button_see,button_update_company,button_delete],
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seeEnterpriseCompany(id);
					}
				}
			});
			var seeEnterpriseCompany = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseOutinvest.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var outinvestData = obj.data ;
						var panel_see = new CS.form.FormPanel({
							labelWidth : 90,
							bodyStyle:'padding:10px',
							width : 488,
							height : 328,
							items : [{
					            layout:'column',
					            items:[{
					            	columnWidth:1,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'textfield',
					                    fieldLabel: '投资对象',
										value : outinvestData.investobject,
										readOnly  : true,
										cls : 'readOnlyClass'
					                }]
					            },{
					            	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'numberfield',
					                    fieldLabel: '投资金额(万)',
					                    value : outinvestData.money,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                	
					                },{
					                	xtype:'numberfield',
					                    fieldLabel: '预计收益(万)',
					                    value : outinvestData.expectincome,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					            		xtype : "dickeycombo",
										nodeKey :'tzfs',
					                    fieldLabel: '投资方式',
					                    width : 100 ,
										hiddenName : 'enterpriseOutinvest.investway',
										value : outinvestData.investway,
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
					                	xtype:'numberfield',
					                    fieldLabel: '实际收益(万)',
					                    value : outinvestData.factincome,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'datefield',
					                    format : 'Y-m-d',
					                    fieldLabel: '投资起始日期',
					                    value : outinvestData.startdate,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'datefield',
					                    format : 'Y-m-d',
					                    fieldLabel: '投资截止日期',
					                    value : outinvestData.enddate,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                }]
					            },{
					            	columnWidth:.5,
					                layout: 'form',
					                labelWidth : 140,
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'numberfield',
					                    fieldLabel: '上期预计投资收益.万元',
					                    readOnly :true,
					                    name: 'enterpriseOutinvest.bpredictincome',
					                    value : outinvestData.bpredictincome
					                }]
					            },{
					            	columnWidth:.5,
					                layout: 'form',
					                labelWidth : 140,
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'numberfield',
					                    fieldLabel: '上期实际投资收益.万元',
					                    readOnly :true,
					                    name: 'enterpriseOutinvest.bpracticeincome',
					                    value : outinvestData.bpracticeincome
					                }]
					            },{
					            	columnWidth:1,
					                layout: 'form',
					                labelWidth : 240,
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'numberfield',
					                    fieldLabel: '预计担保期内企业支出的投资金额 (万)',
					                    value : outinvestData.pretotalearn,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'numberfield',
					                    fieldLabel: '预计担保期内企业的投资收益金额(万)',
					                    value : outinvestData.investincome,
					                    readOnly  : true,
										cls : 'readOnlyClass'
					                }]
					            },{          	
										columnWidth : 1,
					            		layout: 'form',
										items :[{
											xtype : 'textarea',
					            			anchor : '99.5%',
											fieldLabel : '备注',
											value : outinvestData.remarks,
											readOnly  : true,
										cls : 'readOnlyClass'
										}]
					            }]
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
							}
						});
						
						var window_see = new Ext.Window({
							id : 'win_seeOutinvest',
							title: '查看<font color=red><'+obj.data.shortname+'></font>对外投资信息',
							layout : 'fit',
							width : 500,
							height : 360,
							closable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							buttonAlign: 'right',
							bodyStyle:'overflowX:hidden',
							items :[panel_see]
						});
						window_see.show();			
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
			}
			jStore_outinvest.load({
				params : {
					start : 0,
					limit : 10
				}
			});
			var win_listEnterpriseOutinvest = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业对外投资信息',
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
				items:[gPanel_enterpriseOutinvest]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}