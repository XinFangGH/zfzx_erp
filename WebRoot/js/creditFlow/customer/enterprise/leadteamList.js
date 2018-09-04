function leadteamListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_leadteam = new CS.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterpriseLeadteam.do',
				fields : [ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'personid'
				}, {
					name : 'sex'
				}, {
					name : 'cardtype'
				}, {
					name : 'cardnum'
				},{
					name : 'duty'
				},{
					name : 'techtitle'
				},{
					name : 'director'
				},{
					name : 'phone'
				},{
					name : 'name'
				},{
					name : 'remarks'
				},{
					name : 'jszc'		
				},{
					name : 'zjlx'
				},{
					name : 'cardtypeValue'
				},{
					name : 'techtitleValue'
				}],
				baseParams : {
					id : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_enterpriseTeam.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add_debt = new CS.button.AButton({
				handler : function() {
					var fPanel_addLeadteam = new Ext.form.FormPanel({
						url: __ctxPath + '/creditFlow/customer/enterprise/addEnterpriseLeadteam.do',
						monitorValid : true,
						autoScroll : false ,
						autoHeight : true,
						buttonAlign : 'center',
						border : false,
						frame : true,
						labelAlign : 'right',
						labelWidth : 80,
						bodyStyle:'padding:10px',
						items : [ {
				            layout:'column',
				            items:[{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                    xtype:'textfield',
				                    fieldLabel: '姓名',
				                    name: 'enterpriseLeadteam.name',
									allowBlank : false,
									blankText : '管理人姓名不能为空'
				                },{
				                	
xtype : "dickeycombo",
															nodeKey :'card_type_key',
									fieldLabel : '证件类型',
									hiddenName : 'enterpriseLeadteam.cardtype',
									//dicId : cardtypeDicId,
									width : 80,
									allowBlank : false,
									blankText : '证件类型不能为空',
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
				                	xtype:'textfield',
				                    fieldLabel: '固定电话',
				                    name: 'enterpriseLeadteam.phone'
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '手机号码',
				                    name: 'enterpriseLeadteam.cellphone'
				                },{
									xtype : "dickeycombo",
															nodeKey :'dgree',
									fieldLabel : '学历',
									width : 80,
									hiddenName : 'enterpriseLeadteam.dgree',
									listWidth : 80,
									//dicId : dgreeDicId
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
				                labelWidth : 70,
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
				                	allowBlank : false,
									blankText : '性别不能为空',
					                fieldLabel: '性别',
					                items: [
					                    {boxLabel: '男', name: 'enterpriseLeadteam.sex', inputValue: true},
					                    {boxLabel: '女', name: 'enterpriseLeadteam.sex', inputValue: false}
					                ]
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '证件号码',
				                    name: 'enterpriseLeadteam.cardnum',
				                    allowBlank : false,
									blankText : '职务不能为空'
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '传真号码',
				                    name: 'enterpriseLeadteam.fax'
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '年龄',
				                    name: 'enterpriseLeadteam.age'
				                },{
				                	xtype:'textfield',
				                    fieldLabel: '毕业院校',
				                    name: 'enterpriseLeadteam.gschool'
				                }]
				            },{
				            	columnWidth:1,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'textfield',
				                    fieldLabel: '现工作单位',
				                    name: 'enterpriseLeadteam.nowjobunit'
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype:'textfield',
				                    fieldLabel: '职务',
				                    name: 'enterpriseLeadteam.duty'
				                },{
				                	xtype: 'radiogroup',
					                fieldLabel: '是否董事成员',
					                items: [
					                    {boxLabel: '是', name: 'enterpriseLeadteam.director', inputValue: true},
					                    {boxLabel: '否', name: 'enterpriseLeadteam.director', inputValue: false}
					                ]
				                },{
				                	xtype : "dickeycombo",
															nodeKey :'techpersonnel',
									fieldLabel : '技术职称',
									hiddenName : 'enterpriseLeadteam.techtitle',
									//dicId : techpersonnelDicId,
									width : 80,
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
				                labelWidth : 70,
				                defaults : {anchor:anchor},
				            	items :[{
				            		xtype:'textfield',
				                    fieldLabel: '任职年限',
				                    name: 'enterpriseLeadteam.rzhitotal'
				            	},{
				            		xtype:'textfield',
				                    fieldLabel: '工作年限',
				                    name: 'enterpriseLeadteam.jobtotal'
				            	}]
				            },{
				            		columnWidth : 1,
				            		layout: 'form',
				            		defaults : {anchor:anchor},
									items :[{
					            		xtype:'textfield',
					                    fieldLabel: '行业经验',
					                    name: 'enterpriseLeadteam.tradeexper'
									},{
										xtype:'textfield',
					                    fieldLabel: '管理经验',
					                    name: 'enterpriseLeadteam.manageexper'
									},{
										xtype:'textfield',
					                    fieldLabel: '主要履历',
					                    name: 'enterpriseLeadteam.mainrecord'
									},{
										xtype : 'textfield',
										fieldLabel : '备注',
										name : 'enterpriseLeadteam.remarks'
									}]
				            
				            },{
				                	xtype : 'hidden',
				                	name : 'enterpriseLeadteam.enterpriseid',
				                	value : enterpriseId
				                }]
				        } ],
						buttons : [ {
							text : '保存',
							formBind : true,
							iconCls : 'submitIcon',
							handler : function() {
								fPanel_addLeadteam.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.ux.Toast.msg('状态', '保存成功!');
														jStore_leadteam.reload();
														Ext.getCmp('win_addLeadteam').destroy();
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
						id : 'win_addLeadteam',
						title: '新增<font color=red><'+obj.data.shortname+'></font>管理团队信息',
						layout : 'fit',
						width :(screen.width-180)*0.5 - 20,
						height : 320,
						closable : true,
						resizable : true,
						constrainHeader : true ,
						autoScroll : true ,
						collapsible : true, 
						plain : true,
						border : false,
						modal : true,
						maximizable : true,
						buttonAlign : 'right',
						bodyStyle : 'overflowX:hidden',
						items :[fPanel_addLeadteam],
						listeners : {
							'beforeclose' : function(){
								if(fPanel_addLeadteam != null){
									if(fPanel_addLeadteam.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												fPanel_addLeadteam.buttons[0].handler.call() ;
											}else{
												fPanel_addLeadteam.getForm().reset() ;
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
					var selected = gPanel_enterpriseTeam.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseLeadteam.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.decode(response.responseText);
								var leadteamData = obj.data ;
								var fPanel_update = new Ext.form.FormPanel({
									url:__ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseLeadteam.do',
									monitorValid : true,
									autoScroll : false ,
									autoHeight : true,
									buttonAlign : 'center',
									border : false,
									frame : true,
									labelAlign : 'right',
									labelWidth : 80,
									bodyStyle:'padding:10px',
									items : [ {
							            layout:'column',
							            items:[{
							            	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                    xtype:'textfield',
							                    fieldLabel: '姓名',
							                    name: 'enterpriseLeadteam.name',
												allowBlank : false,
												blankText : '管理人姓名不能为空',
												value : leadteamData.name
							                },{
							                	xtype : "dickeycombo",
															nodeKey :'card_type_key',
												fieldLabel : '证件类型',
												hiddenName : 'enterpriseLeadteam.cardtype',
												//dicId : cardtypeDicId,
												width : 80,
												allowBlank : false,
												blankText : '证件类型不能为空',
												value : leadteamData.cardtype,
												//valueNotFoundText : leadteamData.zjlx
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
							                	xtype:'textfield',
							                    fieldLabel: '固定电话',
							                    name: 'enterpriseLeadteam.phone',
							                    value : leadteamData.phone
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '手机号码',
							                    name: 'enterpriseLeadteam.cellphone',
							                    value : leadteamData.cellphone
							                },{
												xtype : "dickeycombo",
															nodeKey :'dgree',
												fieldLabel : '学历',
												width : 80,
												hiddenName : 'enterpriseLeadteam.dgree',
												listWidth : 80,
												//dicId : dgreeDicId,
												value : leadteamData.dgree,
												//valueNotFoundText : leadteamData.dgreevalue
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
							                labelWidth : 70,
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype: 'radiogroup',
							                	allowBlank : false,
												blankText : '性别不能为空',
								                fieldLabel: '性别',
								                items: [
								                    {boxLabel: '男', name: 'enterpriseLeadteam.sex', inputValue: true ,checked:leadteamData.sex},
								                    {boxLabel: '女', name: 'enterpriseLeadteam.sex', inputValue: false ,checked:!(leadteamData.sex)}
								                ]
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '证件号码',
							                    name: 'enterpriseLeadteam.cardnum',
							                    allowBlank : false,
												blankText : '职务不能为空',
												value : leadteamData.cardnum
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '传真号码',
							                    name: 'enterpriseLeadteam.fax',
							                    value : leadteamData.fax
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '年龄',
							                    name: 'enterpriseLeadteam.age',
							                    value : leadteamData.age
							                },{
							                	xtype:'textfield',
							                    fieldLabel: '毕业院校',
							                    name: 'enterpriseLeadteam.gschool',
							                    value : leadteamData.gschool
							                }]
							            },{
							            	columnWidth:1,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype:'textfield',
							                    fieldLabel: '现工作单位',
							                    name: 'enterpriseLeadteam.nowjobunit',
							                    value : leadteamData.nowjobunit
							                }]
							            },{
							            	columnWidth:.5,
							                layout: 'form',
							                defaults : {anchor:anchor},
							                items :[{
							                	xtype:'textfield',
							                    fieldLabel: '职务',
							                    name: 'enterpriseLeadteam.duty',
							                    value : leadteamData.duty
							                },{
							                	xtype: 'radiogroup',
								                fieldLabel: '是否董事成员',
								                items: [
								                    {boxLabel: '是', name: 'enterpriseLeadteam.director', inputValue: true,checked:leadteamData.director},
								                    {boxLabel: '否', name: 'enterpriseLeadteam.director', inputValue: false, checked:!(leadteamData.director)}
								                ]
							                },{
							                	xtype : "dickeycombo",
															nodeKey :'techpersonnel',
												fieldLabel : '技术职称',
												hiddenName : 'enterpriseLeadteam.techtitle',
												//dicId : techpersonnelDicId,
												value : leadteamData.techtitle,
												//valueNotFoundText : leadteamData.jszc,
												width : 80,
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
							                labelWidth : 70,
							                defaults : {anchor:anchor},
							            	items :[{
							            		xtype:'textfield',
							                    fieldLabel: '任职年限',
							                    name: 'enterpriseLeadteam.rzhitotal',
							                    value : leadteamData.rzhitotal
							            	},{
							            		xtype:'textfield',
							                    fieldLabel: '工作年限',
							                    name: 'enterpriseLeadteam.jobtotal',
							                    value : leadteamData.jobtotal
							            	}]
							            },{
							            		columnWidth : 1,
							            		layout: 'form',
							            		defaults : {anchor:anchor},
												items :[{
								            		xtype:'textfield',
								                    fieldLabel: '行业经验',
								                    name: 'enterpriseLeadteam.tradeexper',
								                    value : leadteamData.tradeexper
												},{
													xtype:'textfield',
								                    fieldLabel: '管理经验',
								                    name: 'enterpriseLeadteam.manageexper',
								                    value : leadteamData.manageexper
												},{
													xtype:'textfield',
								                    fieldLabel: '主要履历',
								                    name: 'enterpriseLeadteam.mainrecord',
								                    value : leadteamData.mainrecord
												},{
													xtype : 'textfield',
													fieldLabel : '备注',
													name : 'enterpriseLeadteam.remarks',
													value : leadteamData.remarks
												}]
							            
							            },{
							                	xtype : 'hidden',
							                	name : 'enterpriseLeadteam.enterpriseid',
							                	value : leadteamData.enterpriseid
							                },{
							                	xtype : 'hidden',
							                	name : 'enterpriseLeadteam.id',
							                	value : leadteamData.id
							                }]
							        } ],
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
																	jStore_leadteam.reload();
																	Ext.getCmp('win_updateLeadteam').destroy();
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
									id : 'win_updateLeadteam',
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业管理团队信息',
									layout : 'fit',
									width :(screen.width-180)*0.5 - 20,
									height : 320,
									closable : true,
									resizable : false,
									constrainHeader : true ,
									autoScroll : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									buttonAlign: 'center',
									bodyStyle : 'overflowX:hidden',
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
					var selected = gPanel_enterpriseTeam.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseLeadteam.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_leadteam.reload() ;
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
					var selected = gPanel_enterpriseTeam.getSelectionModel().getSelected();
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
								header : "姓名",
								width : 80,
								sortable : true,
								dataIndex : 'name'
							}, {
								header : "性别",
								width : 50,
								sortable : true,
								dataIndex : 'sex',
								renderer : function(v){
									if(!v){
										return '女' ;
									}else{
										return '男' ;
									}
								}
							}, {
								id : 'content',
								header : "证件类型",
								width : 100,
								sortable : true,
								dataIndex : 'cardtypeValue'
							}, {
								header : "证件号码",
								width : 100,
								sortable : true,
								dataIndex : 'cardnum'
							}, {
								header : "职务",
								width : 100,
								sortable : true,
								dataIndex : 'duty'
							}, {
								header : "技术职称",
								width : 150,
								sortable : true,
								dataIndex : 'techtitleValue'
							}, {
								header : "是否董事成员",
								width : 100,
								sortable : true,
								dataIndex : 'director',
								renderer : function(v){
									if(v){
										return '是';
									}else{
										return '否' ;
									}
								}
							}, {
								header : "联系电话",
								width : 100,
								sortable : true,
								dataIndex : 'phone'
							},{
								header : "备注",
								width : 80,
								sortable : true,
								dataIndex : 'remarks'
							}]);
			var gPanel_enterpriseTeam = new CS.grid.GridPanel( {
				id : 'gPanel_enterpriseTeam',
				pageSize : pageSize,
				store : jStore_leadteam,
				autoWidth : true,
				height:350,
				selModel : new Ext.grid.RowSelectionModel(),
				loadMask : new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				}),
				stripeRows : true,
				border : false ,
				bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					autoWidth : false ,
					style : '',
					displayInfo : true,
					displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
					emptyMsg : '没有符合条件的记录',
					store : jStore_leadteam
				}),
				autoExpandColumn : 9,
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
					url : __ctxPath + '/creditFlow/customer/enterprise/findEnterpriseLeadteam.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.decode(response.responseText);
						var leadteamData = obj.data ;
						var panel_seeLeadteam = new Ext.form.FormPanel({
							autoScroll : false ,
							autoHeight : true,
							buttonAlign : 'center',
							border : false,
							frame : true,
							labelAlign : 'right',
							labelWidth : 80,
							bodyStyle:'padding:10px',
							items : [ {
					            layout:'column',
					            items:[{
					            	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                    xtype:'textfield',
					                    fieldLabel: '姓名',
										readOnly  : true,
										cls : 'readOnlyClass',
										value : leadteamData.name
					                },{
					                	xtype : "dickeycombo",
										nodeKey :'card_type_key',
										fieldLabel : '证件类型',
										width : 80,
										allowBlank : false,
										blankText : '证件类型不能为空',
										value : leadteamData.cardtype,
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
					                	xtype:'textfield',
					                    fieldLabel: '固定电话',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.phone
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '手机号码',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.cellphone
					                },{
										xtype : "dickeycombo",
										nodeKey :'dgree',
										fieldLabel : '学历',
										width : 80,
										value : leadteamData.dgree,
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
					                labelWidth : 70,
					                defaults : {anchor:anchor},
					                items :[{
						                xtype: 'textfield',
										fieldLabel: '性别',
										value : leadteamData.sex == true ? '男' : '女' ,
										readOnly : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '证件号码',
					                    readOnly : true,
										cls : 'readOnlyClass',
										value : leadteamData.cardnum
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '传真号码',
					                    value : leadteamData.fax,
					                    readOnly : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '年龄',
					                    readOnly : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.age
					                },{
					                	xtype:'textfield',
					                    fieldLabel: '毕业院校',
					                    readOnly : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.gschool
					                }]
					            },{
					            	columnWidth:1,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'textfield',
					                    fieldLabel: '现工作单位',
					                    readOnly : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.nowjobunit
					                }]
					            },{
					            	columnWidth:.5,
					                layout: 'form',
					                defaults : {anchor:anchor},
					                items :[{
					                	xtype:'textfield',
					                    fieldLabel: '职务',
					                    readOnly : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.duty
					                },{
						                xtype: 'textfield',
										fieldLabel: '是否董事成员',
										value : leadteamData.director == true ? '是' : '否' ,
										readOnly : true,
										cls : 'readOnlyClass'
					                },{
					                	xtype : "dickeycombo",
													nodeKey :'techpersonnel',
										fieldLabel : '技术职称',
										value : leadteamData.techtitle,
										readOnly : true,
										width : 80,
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
					                labelWidth : 70,
					                defaults : {anchor:anchor},
					            	items :[{
					            		xtype:'textfield',
					                    fieldLabel: '任职年限',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.rzhitotal
					            	},{
					            		xtype:'textfield',
					                    fieldLabel: '工作年限',
					                    readOnly  : true,
										cls : 'readOnlyClass',
					                    value : leadteamData.jobtotal
					            	}]
					            },{
					            		columnWidth : 1,
					            		layout: 'form',
					            		defaults : {anchor:anchor},
										items :[{
						            		xtype:'textfield',
						                    fieldLabel: '行业经验',
						                    readOnly  : true,
											cls : 'readOnlyClass',
						                    value : leadteamData.tradeexper
										},{
											xtype:'textfield',
						                    fieldLabel: '管理经验',
						                    readOnly  : true,
											cls : 'readOnlyClass',
						                    value : leadteamData.manageexper
										},{
											xtype:'textfield',
						                    fieldLabel: '主要履历',
						                    readOnly  : true,
											cls : 'readOnlyClass',
						                    value : leadteamData.mainrecord
										},{
											xtype : 'textfield',
											fieldLabel : '备注',
											readOnly  : true,
											cls : 'readOnlyClass',
											value : leadteamData.remarks
										}]
					            }]
					        } ]
						});
						var window_see = new Ext.Window({
							id : 'win_seeLeadteam',
							title: '查看企业管理人信息',
							layout : 'fit',
							width :(screen.width-180)*0.5 - 20,
							height : 320,
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							autoScroll : true ,
							plain : true,
							border : false,
							modal : true,
							maximizable : true,
							buttonAlign: 'center',
							bodyStyle : 'overflowX:hidden',
							items :[panel_seeLeadteam]
						});
						window_see.show();			
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
			}
			jStore_leadteam.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			var win_listEnterpriseLeadteam = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>管理团队信息',
				width :(screen.width-180)*0.5 + 40,
				height : 400,
				buttonAlign : 'center',
				layout : 'fit',
				modal : true,
				maximizable : true,
				constrainHeader : true ,
				collapsible : true, 
				border : true,
				autoScroll : true ,
				items :[gPanel_enterpriseTeam]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}