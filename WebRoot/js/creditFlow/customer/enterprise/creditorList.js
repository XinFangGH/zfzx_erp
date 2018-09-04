function creditorListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_creditor = new Ext.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/getAllEnterpriseCreditor.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
						name : 'id'
					},{
						name : 'zqrpname' //债权人
					}, {
						name : 'creditmoney' //借款金额(万元) 
					}, {
						name : 'repayment' //已还金额(万元)
					}, {
						name : 'bowmoney'  //借款余额(万元)
					}, {
						name : 'davalue' //偿还方式	
					},{
						name : 'dbvalue'  //凭证方式 
					},{
						name : 'creditstartdate' // 借款起始日期
					},{
						name : 'creditenddate'//借款截止日期
					},{
						name : 'lastdate' //最后一次还款日期
					},{
						name : 'repaywayValue'
					},{
						name : 'voucherwayValue'
					}],
				baseParams : {
					eid : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_creditor.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add = new CS.button.AButton({
				handler : function() {
					var addCreditor = new Ext.form.FormPanel({
						id :'addCreditor',
						url : __ctxPath + '/creditFlow/customer/enterprise/addEnterpriseCreditor.do',
						//monitorValid : true,
						bodyStyle:'padding:10px',
						autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						layout:'column',
						width : 488,
						height : 328,
						frame : true ,
						items: [{
							columnWidth:.5,
					        layout: 'form',
					        labelWidth : 80,
							defaults : {anchor : anchor},
							items :[{
								xtype : 'textfield',
								fieldLabel : '债务人',
								name : 'enCreditor.zqrpname'
							}]
						},{
							columnWidth:.5,
					        layout: 'form',
					        labelWidth : 80,
							defaults : {anchor : anchor},
							items :[{
								xtype : 'textfield',
								fieldLabel : '已还金额(万)',
								name : 'enCreditor.repayment'
							}]
						},{
							columnWidth:1,
					        layout: 'form',
					        labelWidth : 80,
							defaults : {anchor : anchor},
							items :[{
								xtype : 'textfield',
								fieldLabel : '债权说明',
								name : 'enCreditor.creditexplain'
							}]
						},{
							columnWidth:.415,
					        layout: 'form',
					        labelWidth : 80,
							defaults : {anchor : anchor},
							items:[{
								xtype : 'numberfield',
								fieldLabel : '借款金额(万)',
								name : 'enCreditor.creditmoney'
							}]
						},{
							columnWidth:.585,
					        layout: 'form',
					        labelWidth : 120,
							defaults : {anchor : anchor},
							items:[{
								xtype : 'datefield',   
								fieldLabel : '最后一次还款日期',
								name : 'enCreditor.lastdate',
								format : 'Y-m-d'
							}]
						},{
							columnWidth:.5,
					        layout: 'form',
					        labelWidth : 80,
							defaults : {anchor : anchor},
							items:[{
									xtype : 'datefield',      
						  			fieldLabel : '借款起始日期',
						  			format : 'Y-m-d',
									name : 'enCreditor.creditstartdate'
							  },{
									xtype : 'datefield',      
									fieldLabel : '借款截止日期',
									format : 'Y-m-d',
									name : 'enCreditor.creditenddate'
							  }]
						},{
							columnWidth:.5,
					        layout: 'form',
					        labelWidth : 80,
							defaults : {anchor : anchor},
							items:[{
						 			xtype : 'textfield',
									fieldLabel : '借款余额(万)',
									name : 'enCreditor.bowmoney'
							 },{
									xtype : "dickeycombo",
									nodeKey :'chfs',
									fieldLabel : '偿还方式',
									hiddenName : 'enCreditor.repayway',
									//mode : 'remote',
									width : 100 ,
									//emptyText : '请选择偿还方式',
									editable : false,
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
					         labelWidth : 80,
							 defaults : {anchor : anchor},
							 items : [{
								/*xtype : 'textfield',
								fieldLabel : '担保情况',
								name : 'enCreditor.assurecondition'*/
								xtype : "dickeycombo",
									nodeKey :'dbqk',
								fieldLabel : '担保情况',
								width : 80 ,
								hiddenName : 'enCreditor.assurecondition',
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
							 columnWidth:.5,
					         layout: 'form',
					         labelWidth : 80,
							 defaults : {anchor : anchor},
							 items : [{
							 		xtype : "dickeycombo",
									nodeKey :'pzfs',
									fieldLabel : '凭证方式',
									hiddenName : 'enCreditor.voucherway',
									width : 100 ,
									//mode : 'remote',
									//emptyText : '请选择凭证方式',
									editable : false,
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
							columnWidth : 1,
							layout :'form',
							labelWidth : 180,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'numberfield',
								fieldLabel : '预计担保期内支付的金额(万)',
								name : 'enCreditor.pretotalearn'
							}]
						},{
							 columnWidth : 1,
					         layout: 'form',
					         labelWidth : 80,
							 defaults : {anchor : anchor},
							 items : [{
								xtype : 'textarea',
								fieldLabel : '备注',
								name : 'enCreditor.remarks'
							 }]
						},{
							xtype : 'hidden',
							name : 'enCreditor.enterpriseid',
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
							//formBind : true,
							handler : function() {
								addCreditor.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										Ext.ux.Toast.msg('状态', '保存成功!');
												jStore_creditor.reload();
												Ext.getCmp('win_add').destroy();
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
					})
					var win_add = new Ext.Window({
						id : 'win_add',
						title: '新增<font color=red><'+obj.data.shortname+'></font>企业债权情况信息',
						layout : 'fit',
						width : 500,
						height : 360,
						closable : true,
						constrainHeader : true ,
						collapsible : true,
						resizable : true,
						maximizable : true,
						plain : true,
						border : false,
						autoScroll : true ,
						modal : true,
						bodyStyle:'overflowX:hidden',
						buttonAlign : 'right',
						items :[addCreditor],
						listeners : {
							'beforeclose' : function(){
								var formPanelObj = Ext.getCmp('addCreditor');
								if(formPanelObj != null){
									if(formPanelObj.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												formPanelObj.buttons[0].handler.call() ;
											}else{
												formPanelObj.getForm().reset() ;
												win_add.destroy() ;
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
			var button_update = new CS.button.UButton({
				handler : function() {
					var selected = gPanel_creditor.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/findByIdEnterpriseCreditor.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var creditorData = obj.data ;
								var updateCreditor = new Ext.form.FormPanel({
									id :'updateCreditor',
									url : __ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseCreditor.do',
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									layout:'column',
									width : 488,
									height : 328,
									frame : true ,
									items: [{
										columnWidth:.5,
								        layout: 'form',
								        labelWidth : 80,
										defaults : {anchor : anchor},
										items :[{
											xtype : 'textfield',
											fieldLabel : '债务人',
											name : 'enCreditor.zqrpname',
											value : creditorData.zqrpname
										}]
									},{
										columnWidth:.5,
								        layout: 'form',
								        labelWidth : 80,
										defaults : {anchor : anchor},
										items :[{
											xtype : 'textfield',
											fieldLabel : '已还金额(万)',
											name : 'enCreditor.repayment',
											value : creditorData.repayment
										}]
									},{
										columnWidth:1,
								        layout: 'form',
								        labelWidth : 80,
										defaults : {anchor : anchor},
										items :[{
											xtype : 'textfield',
											fieldLabel : '债权说明',
											name : 'enCreditor.creditexplain',
											value : creditorData.creditexplain
										}]
									},{
										columnWidth:.415,
								        layout: 'form',
								        labelWidth : 80,
										defaults : {anchor : anchor},
										items:[{
											xtype : 'numberfield',
											fieldLabel : '借款金额(万)',
											name : 'enCreditor.creditmoney',
											value : creditorData.creditmoney
										}]
									},{
										columnWidth:.585,
								        layout: 'form',
								        labelWidth : 120,
										defaults : {anchor : anchor},
										items:[{
											xtype : 'datefield',   
											fieldLabel : '最后一次还款日期',
											name : 'enCreditor.lastdate',
											format : 'Y-m-d',
											value : creditorData.lastdate
										}]
									},{
										columnWidth:.5,
								        layout: 'form',
								        labelWidth : 80,
										defaults : {anchor : anchor},
										items:[{
												xtype : 'datefield',      
									  			fieldLabel : '借款起始日期',
									  			format : 'Y-m-d',
												name : 'enCreditor.creditstartdate',
												value : creditorData.creditstartdate
										  },{
												xtype : 'datefield',      
												fieldLabel : '借款截止日期',
												format : 'Y-m-d',
												name : 'enCreditor.creditenddate',
												value : creditorData.creditenddate
										  }]
									},{
										columnWidth:.5,
								        layout: 'form',
								        labelWidth : 80,
										defaults : {anchor : anchor},
										items:[{
									 			xtype : 'textfield',
												fieldLabel : '借款余额(万)',
												name : 'enCreditor.bowmoney',
												value : creditorData.bowmoney
										 },{
												xtype : "dickeycombo",
															nodeKey :'chfs',
												fieldLabel : '偿还方式',
												hiddenName : 'enCreditor.repayway',
												//mode : 'remote',
												width : 100 ,
												//emptyText : '请选择偿还方式',
												editable : false,
												value : creditorData.repayway,
												/*valueNotFoundText : creditorData.dbvalue*/
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
								         labelWidth : 80,
										 defaults : {anchor : anchor},
										 items : [{
											/*xtype : 'textfield',
											fieldLabel : '担保情况',
											name : 'enCreditor.assurecondition',
											value : creditorData.assurecondition*/
							        		xtype : "dickeycombo",
															nodeKey :'dbqk',
											fieldLabel : '担保情况',
											hiddenName : 'enCreditor.assurecondition',
											//dicId : cdgDicId,
											width : 80 ,
											value : creditorData.assurecondition,
											//valueNotFoundText : creditorData.assureconditionvalue
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
								         labelWidth : 80,
										 defaults : {anchor : anchor},
										 items : [{
										 		xtype : "dickeycombo",
															nodeKey :'pzfs',
												fieldLabel : '凭证方式',
												hiddenName : 'enCreditor.voucherway',
												width : 100 ,
												//mode : 'remote',
												//emptyText : '请选择凭证方式',
												editable : false,
												value : creditorData.voucherway,
												/*valueNotFoundText : creditorData.davalue*/
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
										columnWidth : 1,
										layout :'form',
										labelWidth : 180,
										defaults : {anchor : anchor},
										items : [{
											xtype : 'numberfield',
											fieldLabel : '预计担保期内支付的金额(万)',
											name : 'enCreditor.pretotalearn',
											value : creditorData.pretotalearn
										}]
									},{
										 columnWidth : 1,
								         layout: 'form',
								         labelWidth : 80,
										 defaults : {anchor : anchor},
										 items : [{
											xtype : 'textarea',
											fieldLabel : '备注',
											name : 'enCreditor.remarks',
											value : creditorData.remarks
										 }]
									},{
										xtype : 'hidden',
										name : 'enCreditor.enterpriseid',
										value : creditorData.enterpriseid
									},{
										xtype : 'hidden',
										name : 'enCreditor.id',
										value : creditorData.id
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
										formBind : true,
										handler : function() {
											updateCreditor.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													Ext.ux.Toast.msg('状态', '保存成功!');
															jStore_creditor.reload();
															Ext.getCmp('win_update').destroy();
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
								})
								
								var window_update = new Ext.Window({
									id : 'win_update',
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业债权情况信息',
									layout : 'fit',
									width : 500,
									height : 360,
									closable : true,
									resizable : true,
									constrainHeader : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									autoScroll : true ,
									bodyStyle:'overflowX:hidden',
									buttonAlign : 'right',
									items:[updateCreditor],
									listeners : {
										'beforeclose' : function(){
											var formPanelObj = Ext.getCmp('updateCreditor');
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
								}).show();
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
					var selected = gPanel_creditor.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseCreditor.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_creditor.reload() ;
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
					var selected = gPanel_creditor.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seeCreditorShow(id);
					}
			}
			});
				
			var cModel_creditor = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer( {
					header : '序号', 
					width : 35
				}),
				{
					header : "债务人",
					width : 85,
					sortable : true, 
					dataIndex : 'zqrpname'
				}, {
					header : "借款金额(万)",
					width : 85,
					sortable : true,
					dataIndex : 'creditmoney',
					renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
				}, {
					header : "已还金额(万)",
					width : 85,
					sortable : true,
					dataIndex : 'repayment',
					renderer : function(v){if(v == 0){return "" ;}else{return v+'万';}}
				}, {
					header : "借款余额(万)",
					width : 85,
					sortable : true,
					dataIndex : 'bowmoney',
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
					dataIndex : 'repaywayValue'
				},{
					header : "凭证方式",
					width : 90,
					sortable : true,
					dataIndex : 'voucherwayValue'
				}]);
			var gPanel_creditor = new Ext.grid.GridPanel( {
				pageSize : pageSize,
				store : jStore_creditor,
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
					store : jStore_creditor
				}),
				height:360,
				colModel : cModel_creditor,
				tbar : isReadOnly?[button_see]:[button_add,button_see,button_update,button_delete],
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seeCreditorShow(id);
					}
				}
			});
			var seeCreditorShow = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/findByIdEnterpriseCreditor.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var creditorData = obj.data ;
						var seeCreditor = new Ext.form.FormPanel({
							id :'seeCreditor',
							bodyStyle:'padding:10px',
							autoScroll : true ,
							labelAlign : 'right',
							buttonAlign : 'center',
							layout:'column',
							width : 488,
							height : 308,
							frame : true ,
							items: [{
								columnWidth:.5,
						        layout: 'form',
						        labelWidth : 80,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '债务人',
									value : creditorData.zqrpname,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
							},{
								columnWidth:.5,
						        layout: 'form',
						        labelWidth : 80,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '已还金额(万)',
									name : 'enCreditor.repayment',
									value : creditorData.repayment,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
							},{
								columnWidth:1,
						        layout: 'form',
						        labelWidth : 80,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '债权说明',
									name : 'enCreditor.creditexplain',
									value : creditorData.creditexplain,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
							},{
								columnWidth:.415,
						        layout: 'form',
						        labelWidth : 80,
								defaults : {anchor : anchor},
								items:[{
									xtype : 'numberfield',
									fieldLabel : '借款金额(万)',
									name : 'enCreditor.creditmoney',
									value : creditorData.creditmoney,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
							},{
								columnWidth:.585,
						        layout: 'form',
						        labelWidth : 120,
								defaults : {anchor : anchor},
								items:[{
									xtype : 'datefield',   
									fieldLabel : '最后一次还款日期',
									name : 'enCreditor.lastdate',
									format : 'Y-m-d',
									value : creditorData.lastdate,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
							},{
								columnWidth:.5,
						        layout: 'form',
						        labelWidth : 80,
								defaults : {anchor : anchor},
								items:[{
										xtype : 'datefield',      
							  			fieldLabel : '借款起始日期',
							  			format : 'Y-m-d',
										name : 'enCreditor.creditstartdate',
										value : creditorData.creditstartdate,
										readOnly  : true,
										cls : 'readOnlyClass'
								  },{
										xtype : 'datefield',      
										fieldLabel : '借款截止日期',
										format : 'Y-m-d',
										name : 'enCreditor.creditenddate',
										value : creditorData.creditenddate,
										readOnly  : true,
										cls : 'readOnlyClass'
								  }]
							},{
								columnWidth:.5,
						        layout: 'form',
						        labelWidth : 80,
								defaults : {anchor : anchor},
								items:[{
							 			xtype : 'textfield',
										fieldLabel : '借款余额(万)',
										name : 'enCreditor.bowmoney',
										value : creditorData.bowmoney,
										readOnly  : true,
										cls : 'readOnlyClass'
								 },{
								 	xtype : "dickeycombo",
									nodeKey :'chfs',
									fieldLabel : '偿还方式',
									width : 100 ,
									editable : false,
									value : creditorData.repayway,
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
						         labelWidth : 80,
								 defaults : {anchor : anchor},
								 items : [{
									xtype : "dickeycombo",
									nodeKey :'dbqk',
									fieldLabel : '担保情况',
									width : 80 ,
									value : creditorData.assurecondition,
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
						         labelWidth : 80,
								 defaults : {anchor : anchor},
								 items : [{
								 	xtype : "dickeycombo",
									nodeKey :'pzfs',
									fieldLabel : '凭证方式',
									hiddenName : 'enCreditor.voucherway',
									width : 100 ,
									editable : false,
									value : creditorData.voucherway,
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
								columnWidth : 1,
								layout :'form',
								labelWidth : 180,
								defaults : {anchor : anchor},
								items : [{
									xtype : 'numberfield',
									fieldLabel : '预计担保期内支付的金额(万)',
									name : 'enCreditor.pretotalearn',
									value : creditorData.pretotalearn,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
							},{
								 columnWidth : 1,
						         layout: 'form',
						         labelWidth : 80,
								 defaults : {anchor : anchor},
								 items : [{
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'enCreditor.remarks',
									value : creditorData.remarks,
									readOnly  : true,
									cls : 'readOnlyClass'
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
						})
						var window_see = new Ext.Window({
							title: '查看<font color=red><'+obj.data.shortname+'></font>企业债权情况',
							layout : 'fit',
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							width : 500,
							height : 340,
							autoScroll : true ,
							bodyStyle:'overflowX:hidden',
							buttonAlign : 'right',
							items :[seeCreditor]
						}).show();			
					},
					failure : function(response) {
							Ext.ux.Toast.msg('状态','操作失败，请重试');	
					},
					params: { id: id }
				});	
			}
			jStore_creditor.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			var win_listEnterpriseCreditor = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业债权情况 ',
				width : 580,
				height : 410,
				buttonAlign : 'center',
				layout : 'fit',
				border : true,
				modal : true,
				maximizable : true,
				autoScroll : true ,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_creditor]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}