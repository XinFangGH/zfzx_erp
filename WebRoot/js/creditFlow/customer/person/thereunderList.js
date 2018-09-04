function thereunderListWin(cardnumber,isReadOnly){
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
   	 	method:'post',   
    	params:{perosnName:cardnumber},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var personIdValue = obj.data.id;
			var jStore_thereunder = new Ext.data.JsonStore( {
				url : __ctxPath+'/credit/customer/person/queryPersonThereunderMessage.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
					name : 'id'
				}, {
					name : 'companyname'
				}, {
					name : 'licensenum'
				}, {
					name : 'value'
				}, {
					name : 'registerdate'  
				}, {
					name : 'registercapital'  
				}, {
					name : 'address'  
				}, {
					name : 'lnpname'  
				}, {
					name : 'phone'
				},{
					name : 'shortname'
				},{
					name : 'name'
				} ],
				baseParams :{pid : personIdValue}
			});
			jStore_thereunder.load({
				params : {
					start : 0,
					limit : 20
				}
			});
			var button_add = new Ext.Button({
				text : '增加',
				tooltip : '增加一条新的个人旗下公司信息',
				iconCls : 'addIcon',
				scope : this,
				handler : function() {
					var addThereunderPanel = new Ext.form.FormPanel({
						id : 'addThereunderPanel',
						url : __ctxPath+'/credit/customer/person/addPersonThereunderMessage.do',
						monitorValid : true,
						bodyStyle:'padding:10px',
						//autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						height : 240,
						frame : true ,
						layout : 'column',
						items:[{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items :[{
				            	id : 'encompanyname',
				            	xtype : 'combo',
				                fieldLabel: '<font color=red>*</font>公司名称',
				                triggerClass :'x-form-search-trigger',
				                hiddenName : 'companyname',
				                onTriggerClick : function(){
									selectEnterGridWin(selectCompanyname);
								},
								resizable : true,
								mode : 'romote',
								editable : true,
								lazyInit : false,
								allowBlank : false,
								typeAhead : true,
								minChars : 1,
								store : new Ext.data.JsonStore({
									url : __ctxPath+'/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
									root : 'topics',
									autoLoad : true,
									fields : [{
												name : 'id'
											}, {
												name : 'name'
											}],
									listeners : {
										'load' : function(s,r,o){
											if(s.getCount()==0){
												Ext.getCmp('encompanyname').markInvalid('没有查找到匹配的记录') ;
											}
										}
									}
								}),
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								listeners : {
									'select' : function(combo,record,index){
										Ext.getCmp('encompanynameid').setValue(record.get('id'));
										Ext.getCmp('encompanyname').setValue(record.get('shortname'));
									},'blur' : function(f){
										if(f.getValue()!=null&&f.getValue()!=''){
											Ext.getCmp('encompanynameid').setValue(f.getValue());
										}
									}
								}
							},{
								id : 'encompanynameid',
								xtype : 'hidden',
								name : 'personThereunder.companyname'
							}]
						},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items :[{
								xtype : "dickeycombo",
															nodeKey :'guanxi',
								width : 70,
								fieldLabel : '<font color=red>*</font>关系',
								hiddenName : 'personThereunder.relate',
								//dicId : vwtcoaDicId,
								allowBlank : false,
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
							},
								{
					            	id : 'therepoxm',
					            	xtype : 'combo',
					                fieldLabel: '联系人',
					                triggerClass :'x-form-search-trigger',
					                hiddenName : 'lnpname',
					                onTriggerClick : function(){
										selectPWName(selectSpousePerson);
									},
									resizable : true,
									mode : 'romote',
									editable : true,
									lazyInit : false,
									allowBlank : false,
									typeAhead : true,
									minChars : 1,
									listWidth : 150,
									store : new Ext.data.JsonStore({
										url : __ctxPath+'/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
										root : 'topics',
										autoLoad : true,
										fields : [{
													name : 'id'
												}, {
													name : 'name'
												}],
										listeners : {
											'load' : function(s,r,o){
												if(s.getCount()==0){
													Ext.getCmp('poxm').markInvalid('没有查找到匹配的记录') ;
												}
											}
										}
									}),
									displayField : 'name',
									valueField : 'id',
									triggerAction : 'all',
									listeners : {
										'select' : function(combo,record,index){
											Ext.getCmp('therepoid').setValue(record.get('id'));
											Ext.getCmp('therepoxm').setValue(record.get('name'));
										},'blur' : function(f){
											if(f.getValue()!=null&&f.getValue()!=''){
												Ext.getCmp('therepoid').setValue(f.getValue());
											}
										}
									}
								}
							,{
								id :'therepoid',
								xtype : 'hidden',
								name : 'personThereunder.lnpid'
							}]
						},{
							columnWidth : .5,
							labelWidth : 90,
							layout : 'form',
							defaults : {anchor : '100%'},
							items :[{
								id : 'licensenum',
								xtype : 'textfield',
								fieldLabel : '营业执照号码',
								name : 'personThereunder.licensenum'
								//regex : /^[A-Za-z0-9]+$/,
								//regexText : '格式错误'
							},{
								xtype : 'textfield',
								fieldLabel : '联系人电话',
								name : 'personThereunder.phone'
								//regex : /^(\d{3,4})-(\d{7,8})/,
								//regexText : '电话格式错误或无效的电话号码'
							}]
						},{
							columnWidth : 0.5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items :[{
								xtype : 'datefield',
								fieldLabel : '注册时间',
								name : 'personThereunder.registerdate',
								format : 'Y-m-d'
							}]
						},{
							columnWidth : 0.5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : '100%'},
							items :[{
								id : 'registercapital',
								xtype : 'textfield',
								fieldLabel : '注册资本(万元)',
								name : 'personThereunder.registercapital',
								regex : /^\d+(\.\d+)?$/ ,
								regexText : '数据格式不对'
							}]
						},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : '100%'},
							items :[{
								id : 'address',
								xtype : 'textfield',
								fieldLabel : '经营地址',
								name : 'personThereunder.address'
							}]
						},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items :[{
								xtype : 'textarea',
								fieldLabel : '备注',
								name : 'personThereunder.remarks'
							}]
						},{
							xtype : 'hidden',
							name : 'personThereunder.personid',
							value : personIdValue
						}],
						buttons : [{
							text : '保存',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								addThereunderPanel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
											Ext.ux.Toast.msg('状态', '保存成功!');
													jStore_thereunder.reload();
													Ext.getCmp('addThereWindow').destroy();
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
					})
					var addThereWindow = new Ext.Window({
						id : 'addThereWindow',
						title: '新增个人旗下公司信息',
						layout : 'fit',
						closable : true,
						constrainHeader : true ,
						resizable : true,
						plain : true,
						border : false,
						modal : true,
						buttonAlign: 'right',
						iconCls : 'newIcon',
						height :300,
						width :(screen.width-180)*0.6,
						bodyStyle:'overflowX:hidden',
						items :[addThereunderPanel],
						listeners : {
							'beforeclose' : function(){
								if(addThereunderPanel != null){
									if(addThereunderPanel.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												addThereunderPanel.buttons[0].handler.call() ;
											}else{
												addThereunderPanel.getForm().reset() ;
												addThereWindow.destroy() ;
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
			var button_update = new Ext.Button({
				text : '编辑',
				tooltip : '编辑选中的个人旗下公司信息',
				iconCls : 'updateIcon',
				scope : this,
				handler : function() {
					var selected = gPanel_thereunder.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath+'/credit/customer/person/queryVThereunderByIDMessage.do',
							method : 'POST',
							success : function(response,request) {
								obj = Ext.util.JSON.decode(response.responseText);
								var	thereunderData = obj.data;
								var updateThereunderPanel = new Ext.form.FormPanel({
									id : 'updateThereunderPanel',
									url : __ctxPath+'/credit/customer/person/updatePersonThereunderMessage.do',
									monitorValid : true,
									bodyStyle:'padding:10px',
									//autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									height : 240,
									frame : true ,
									items :[{
										layout : 'column',
											items:[{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
									            	id : 'upencompanyname',
									            	xtype : 'combo',
									                fieldLabel: '<font color=red>*</font>公司名称',
									                triggerClass :'x-form-search-trigger',
									                hiddenName : 'companyname',
									                onTriggerClick : function(){
														selectEnterGridWin(upselectCompanyname);
													},
													resizable : true,
													mode : 'romote',
													editable : true,
													lazyInit : false,
													allowBlank : false,
													typeAhead : true,
													minChars : 1,
													store : new Ext.data.JsonStore({
														url : __ctxPath+'/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
														root : 'topics',
														autoLoad : true,
														fields : [{
																	name : 'id'
																}, {
																	name : 'name'
																}],
														listeners : {
															'load' : function(s,r,o){
																if(s.getCount()==0){
																	Ext.getCmp('upencompanyname').markInvalid('没有查找到匹配的记录') ;
																}
															}
														}
													}),
													displayField : 'name',
													valueField : 'id',
													triggerAction : 'all',
													value : thereunderData.shortname,
													listeners : {
														'select' : function(combo,record,index){
															Ext.getCmp('upencompanynameid').setValue(record.get('id'));
															Ext.getCmp('upencompanyname').setValue(record.get('shortname'));
														},'blur' : function(f){
															if(f.getValue()!=null&&f.getValue()!=''){
																Ext.getCmp('upencompanynameid').setValue(f.getValue());
															}
														}
													}
												},{
													id : 'upencompanynameid',
													xtype : 'hidden',
													name : 'personThereunder.companyname',
													value : thereunderData.companyname
												}]
											},{
												columnWidth : .5,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : "dickeycombo",
															nodeKey :'guanxi',
													fieldLabel : '<font color=red>*</font>关系',
													hiddenName : 'personThereunder.relate',
													//mode : 'remote',
													width : 80,
													allowBlank : false,
													blankText : '必填信息',
													editable : false,
													value : thereunderData.relate,
													/*valueNotFoundText : thereunderData.value*/
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
									            	id : 'thereuppoxm',
									            	xtype : 'combo',
									                fieldLabel: '联系人',
									                triggerClass :'x-form-search-trigger',
									                hiddenName : 'lnpname',
									                onTriggerClick : function(){
														selectPWName(upselectSpousePerson);
													},
													resizable : true,
													mode : 'romote',
													editable : true,
													lazyInit : false,
													allowBlank : false,
													typeAhead : true,
													minChars : 1,
													listWidth : 150,
													store : new Ext.data.JsonStore({
														url : __ctxPath+'/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
														root : 'topics',
														autoLoad : true,
														fields : [{
																	name : 'id'
																}, {
																	name : 'name'
																}],
														listeners : {
															'load' : function(s,r,o){
																if(s.getCount()==0){
																	Ext.getCmp('thereuppoxm').markInvalid('没有查找到匹配的记录') ;
																}
															}
														}
													}),
													displayField : 'name',
													valueField : 'id',
													triggerAction : 'all',
													value : thereunderData.name,
													listeners : {
														'select' : function(combo,record,index){
															Ext.getCmp('thereuppoid').setValue(record.get('id'));
															Ext.getCmp('thereuppoxm').setValue(record.get('name'));
														},'blur' : function(f){
															if(f.getValue()!=null&&f.getValue()!=''){
																Ext.getCmp('thereuppoid').setValue(f.getValue());
															}
														}
													}
											
												},{
													id :'thereuppoid',
													xtype : 'hidden',
													name : 'personThereunder.lnpid',
													value : thereunderData.lnpid
												}]
											},{
												columnWidth : .5,
												labelWidth : 90,
												layout : 'form',
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '营业执照号码',
													name : 'personThereunder.licensenum',
													value : thereunderData.licensenum
													//regex : /^[A-Za-z0-9]+$/,
													//regexText : '格式错误'
												},{
													xtype : 'textfield',
													fieldLabel : '联系人电话',
													name : 'personThereunder.phone',
													value : thereunderData.phone
													//regex : /^(\d{3,4})-(\d{7,8})/,
													//regexText : '电话格式错误或无效的电话号码'
												}]
											},{
												columnWidth : 0.5,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'datefield',
													fieldLabel : '注册时间',
													name : 'personThereunder.registerdate',
													format : 'Y-m-d',
													value : thereunderData.registerdate
												}]
											},{
												columnWidth : 0.5,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '注册资本(万元)',
													name : 'personThereunder.registercapital',
													regex : /^\d+(\.\d+)?$/ ,
													regexText : '数据格式不对',
													value : thereunderData.registercapital
												}]
											},{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '经营地址',
													name : 'personThereunder.address',
													value : thereunderData.address
												}]
											},{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textarea',
													fieldLabel : '备注',
													name : 'personThereunder.remarks',
													value : thereunderData.remarks
												}]
											},{
												xtype : 'hidden',
												name : 'personThereunder.id',
												value : thereunderData.id
											},{
												xtype : 'hidden',
												name : 'personThereunder.personid',
												value : thereunderData.personid
											}]
									}],
									buttons : [{
										text : '保存',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											updateThereunderPanel.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
														Ext.ux.Toast.msg('状态', '保存成功!');
																jStore_thereunder.reload();
																Ext.getCmp('updateThereWindow').destroy();
												},
												failure : function(form, action) {
													if(action.response.status==0){
														Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
													}else if(action.response.status==-1){
														Ext.ux.Toast.msg('状态','连接超时，请重试!');
													}else{
														Ext.ux.Toast.msg('状态','修改失败!');		
													}
												}
											});
										}
									}]
								})
									var updateThereWindow = new Ext.Window({
										id : 'updateThereWindow',
										title: '编辑个人旗下公司信息',
										layout : 'fit',
										iconCls : 'upIcon',
										height :300,
										width :(screen.width-180)*0.6,
										bodyStyle:'overflowX:hidden',
										constrainHeader : true ,
										closable : true,
										resizable : true,
										plain : true,
										border : false,
										modal : true,
										buttonAlign: 'right',
								       	items :[updateThereunderPanel],
								       	listeners : {
											'beforeclose' : function(){
												if(updateThereunderPanel != null){
													if(updateThereunderPanel.getForm().isDirty()){
														Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
															if(btn=='yes'){
																updateThereunderPanel.buttons[0].handler.call() ;
															}else{
																updateThereunderPanel.getForm().reset() ;
																updateThereWindow.destroy() ;
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
			
			var button_delete = new Ext.Button({
				text : '删除',
				tooltip : '删除选中的企业信息',
				iconCls : 'deleteIcon',
				scope : this,
				handler : function() {
					var selected = gPanel_thereunder.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath+'/credit/customer/person/deletePersonThereunderMessage.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										searchByCondition();
									},
									failure : function(result, action) {
										Ext.ux.Toast.msg('状态','删除失败!');
									},
									params: { id: id }
								});
							}
						});
					}
				}
			});
			var button_see = new Ext.Button({
				text : '查看',
				tooltip : '查看选中的个人旗下公司信息',
				iconCls : 'seeIcon',
				scope : this,
				handler : function() {
					var selected = gPanel_thereunder.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath+'/credit/customer/person/queryVThereunderByIDMessage.do',
							method : 'POST',
							success : function(response,request) {
								obj = Ext.util.JSON.decode(response.responseText);
								var	thereunderData = obj.data;
								var seeThereunderPanel = new Ext.form.FormPanel({
									bodyStyle:'padding:10px',
									//autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									height : 240,
									frame : true ,
									items :[{
										layout : 'column',
											items:[{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '公司名称',
													value : thereunderData.shortname,
													readOnly : true,
													cls : 'readOnlyClass'
												}]
											},{
												columnWidth : .5,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '关系',
													value : thereunderData.value,
													readOnly : true,
									             	cls : 'readOnlyClass'
												},{
													xtype : 'textfield',
													fieldLabel : '联系人',
													value : thereunderData.name,
													readOnly : true,
									            	cls : 'readOnlyClass'
												}]
											},{
												columnWidth : .5,
												labelWidth : 90,
												layout : 'form',
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '营业执照号码',
													value : thereunderData.licensenum,
													readOnly : true,
									            	cls : 'readOnlyClass'
												},{
													xtype : 'textfield',
													fieldLabel : '联系人电话',
													value : thereunderData.phone,
													readOnly : true,
									            	cls : 'readOnlyClass'
												}]
											},{
												columnWidth : 0.5,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '注册时间',
													value : thereunderData.registerdate,
													readOnly : true,
									            	cls : 'readOnlyClass'
												}]
											},{
												columnWidth : 0.5,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '注册资本(万元)',
													value : thereunderData.registercapital,
													readOnly : true,
									            	cls : 'readOnlyClass'
												}]
											},{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '经营地址',
													value : thereunderData.address,
													readOnly : true,
									            	cls : 'readOnlyClass'
												}]
											},{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textarea',
													fieldLabel : '备注',
													value : thereunderData.remarks,
													readOnly : true,
									            	cls : 'readOnlyClass'
												}]
											}]
									}]
								})
									var seeThereWindow = new Ext.Window({
										id : 'seeThereWindow',
										title: '查看个人旗下公司信息',
										layout : 'fit',
										iconCls : 'lookIcon',
										height :270,
										width :(screen.width-180)*0.6,
										bodyStyle:'overflowX:hidden',
										closable : true,
										resizable : true,
										constrainHeader : true ,
										plain : true,
										border : false,
										modal : true,
										buttonAlign: 'right',
								       	items :[seeThereunderPanel]
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
		
			var cModel_thereunder = new Ext.grid.ColumnModel(
					[
						new Ext.grid.RowNumberer( {
							header : '序号',
							width : 35
						}),
						 {
							header : "公司名称",
							width : 140,
							sortable : true,
							dataIndex : 'shortname'
							}, {
							header : "营业执照号码",
							width : 100,
							sortable : true,
							dataIndex : 'licensenum'
						}, {
							header : "关系",
							width : 90,
							sortable : true,
							dataIndex : 'value'
						},{
							header : "联系人",
							width : 90,
							sortable : true,
							dataIndex : 'name'
						},{
							header : "联系人电话",
							width : 100,
							sortable : true,
							dataIndex : 'phone'
						}, {
							header : "注册时间",
							width : 90,
							sortable : true,
							dataIndex : 'registerdate'					
						}, {
							id : 'registercapital',
							header : "注册资本",
							width : 80,
							sortable : true,
							dataIndex : 'registercapital'					
						}]);
		
			var pagingBar = new Ext.PagingToolbar( {
				pageSize : 20,
				store : jStore_thereunder,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});
		
			var gPanel_thereunder = new Ext.grid.GridPanel( {
				id : 'gPanel_thereunder',
				store : jStore_thereunder,
				colModel : cModel_thereunder,
				//autoExpandColumn : 'registercapital',
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				height : 380,
				autoWidth : true,
				bbar : pagingBar,
				tbar :isReadOnly?[button_see]:[button_add,button_see,button_update,button_delete]
			});
			var searchByCondition = function() {
				jStore_thereunder.load({
					params : {
						start : 0,
						limit : 20
					}
				});
			}
			var thereunderWin = new Ext.Window({
				title : '个人旗下公司信息',
				width : (screen.width-180)*0.7 - 50,
				height: 430,
				buttonAlign : 'center',
				border : false,
				layout : 'fit',
				modal : true,
				autoScroll : true ,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_thereunder]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	}); 
	var selectSpousePerson = function(obj){
		Ext.getCmp('therepoxm').setValue(obj.name);
		Ext.getCmp('therepoid').setValue(obj.id) ;
}
var selectCompanyname = function(obj){
		Ext.getCmp('encompanyname').setValue(obj.shortname);
		Ext.getCmp('encompanynameid').setValue(obj.id) ;
		Ext.getCmp('licensenum').setValue(obj.cciaa);
		Ext.getCmp('registercapital').setValue(obj.registermoney);
		Ext.getCmp('address').setValue(obj.factaddress);
}
var upselectSpousePerson = function(obj){
		Ext.getCmp('thereuppoxm').setValue(obj.name);
		Ext.getCmp('thereuppoid').setValue(obj.id) ;
}
var upselectCompanyname = function(obj){
		Ext.getCmp('upencompanyname').setValue(obj.shortname);
		Ext.getCmp('upencompanynameid').setValue(obj.id) ;
}

}
