var selectPWNameTwo = function(funName) {
	
	var anchor = '100%';
	var jStorePersonWin = new Ext.data.JsonStore({
		url : __ctxPath+'/creditFlow/customer/person/queryListPerson.do?isAll='+isGranted('_detail_sygrkh'),
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
				name : 'id'
			}, {
				name : 'name'
			}, {
				name : 'sexvalue'
			}, {
				name : 'jobvalue'
			}, {
				name : 'cardtypevalue'
			}, {
				name : 'marryvalue'
			}, {
				name : 'cardnumber'
			}, {
				name : 'cellphone'
			}, {
				name : 'birthday'
			}, {
				name : 'mateid'
			}, {
				name : 'telphone'
			}, {
				name : 'creditbank'
			}, {
				name : 'creditperson'
			}, {
				name : 'creditaccount'
			}, {
				name : 'wagebank'
			}, {
				name : 'wageperson'
			}, {
				name : 'wageaccount'
			}, {
				name : 'cardtype'
			}, {
				name : 'job'
			}, {
				name : 'sex'
			}, {
				name : 'dgree'
			}, {
				name : 'dgreevalue'
			}, {
				name : 'currentcompany'
			}, {
				name : 'unitproperties'
			}, {
				name : 'unitpropertiesvalue'
			}, {
				name : 'techpersonnel'
			}, {
				name : 'sonnelvalue'
			}, {
				name : 'unitphone'
			}, {
				name : 'familyaddress'
			}, {
				name : 'selfemail'
			}, {
				name : 'pinyinname'
			}, {
				name : 'marry'
			}, {
				name : 'monthIncomes'
			}, {
				name : 'hukou'
			}, {
				name : 'beforeName'
			}, {
				name : 'steadyWage'
			},{name : 'age'}, {
				name : 'personSFZZId'
			}, {
				name : 'personSFZZUrl'
			}, {
				name : 'personSFZFId'
			}, {
				name : 'personSFZFUrl'
			}
			],
			remoteSort: true//服务器端排序 by chencc
		});
		
		var ageTransition = function(val){
			if(val != ""){
				return val+'岁';
			}else{
				return '' ;
			}
		}
		var cModelPersonWin = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "姓名",
					width : 80,
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "性别",
					width : 40,
					sortable : true,
					dataIndex : 'sexvalue'
				}, {
					header : "年龄",
					width : 40,
					sortable : true,
					dataIndex : 'age',
					renderer : ageTransition
				}, {
					header : "出生日期",
					width : 80,
					sortable : true,
					dataIndex : 'birthday'
				}, {
					header : "证件类型",
					width : 80,
					sortable : true,
					dataIndex : 'cardtypevalue'
				}, {
					header : "证件号码",
					width : 120,
					sortable : true,
					dataIndex : 'cardnumber'
				}, {
					header : "手机号码",
					width : 100,
					sortable : true,
					dataIndex : 'cellphone'
				}]);
		var pagingBar = new Ext.PagingToolbar({
			pageSize : 15,
			store : jStorePersonWin,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});
		var button_add = new Ext.Button({
			text : '增加',
			tooltip : '增加一条新的个人信息',
			iconCls : 'addIcon',
			scope : this,
			handler : function() {
				var addPersonPanel = new Ext.form.FormPanel({
					url :  __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
					monitorValid : true,
					autoWidth : true,
					bodyStyle:'padding:10px',
					autoScroll : true ,
					isflag : false ,
					labelAlign : 'right',
					buttonAlign : 'center',
					frame : true ,
					layout : 'column',
					items :[{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : anchor},
						items :[{
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'person.name',
							allowBlank : false,
							blankText : '姓名为必填内容'
						},{
							xtype : 'textfield',
							fieldLabel : '固定电话',
							name : 'person.telphone'
						},{
							xtype : "dickeycombo",
						nodeKey :'card_type_key',
							fieldLabel : '证件类型',
							width : 80,
							hiddenName : 'person.cardtype',
							allowBlank : false,
							blankText : '证件类型为必填内容',
							
							listeners : {
								afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							},
								'change' : function(field,newValue,oldValue){
									if(newValue == 628){
										Ext.getCmp('cardnumberadd').index = 628;
										Ext.getCmp('cardnumberadd').regex = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
										Ext.getCmp('cardnumberadd').regexText = '身份证号码无效';
									}else {
										Ext.getCmp('cardnumberadd').regex = "" ;
									}
								}
							}
						},{
							xtype : "dickeycombo",
						nodeKey :'gmlx',
							fieldLabel : '公民类型',
							width : 80,
							hiddenName : 'person.peopletype',
							listeners : {
								afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
						nodeKey :'nationality',
							fieldLabel : '民族',
							width : 80,
							hiddenName : 'person.nationality',
							listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
							xtype : 'textfield',
							fieldLabel : '固定住址',
							name : 'person.address'
						},{
							xtype : "dickeycombo",
					nodeKey :'dgree',
							fieldLabel : '学历',
							width : 80,
							hiddenName : 'person.dgree',
							listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
						columnWidth : .5,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : anchor},
						items :[{
							xtype : "dickeycombo",
						nodeKey :'sex_key',
							fieldLabel : '性别',
							width : 80,
							hiddenName : 'person.sex',
							allowBlank : false,
							blankText : '性别为必填内容',
							listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
							xtype : 'textfield',
							fieldLabel : '手机号码',
							name : 'person.cellphone'
						},{
							id : 'cardnumberadd',
							xtype : 'textfield',
							fieldLabel : '证件号码',
							name : 'person.cardnumber',
							allowBlank : false,
							blankText : '证件号码为必填内容',
							listeners : {
								'blur':function(f){
									ajaxUniquenessValidator(this,"ajaxValidatorCardPersonAjaxValidator","该人员已存在！");
								}
			                }
						},{
							xtype : 'datefield',
							fieldLabel : '出生日期',
							name : 'person.birthday',
							format : 'Y-m-d'
						},{
							xtype : 'textfield',
							fieldLabel : '籍贯',
							name : 'person.homeland'
						},{
							xtype : 'textfield',
							fieldLabel : '户口所在地',
							name : 'person.hukou'
						},{
							xtype : 'textfield',
							fieldLabel : '毕业院校',
							name : 'person.graduationunversity'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : anchor},
						items :[{
							xtype : 'textfield',
							fieldLabel : '现工作单位',
							name : 'person.currentcompany'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : anchor},
						items :[{
							xtype : "dickeycombo",
					nodeKey :'zhiwujob',
							fieldLabel : '职务',
							hiddenName : 'person.job',
							width : 80,
							listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
					nodeKey :'techpersonnel',
							fieldLabel : '技术职称',
							hiddenName : 'person.techpersonnel',
							width : 80,
							listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
						columnWidth : .5,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : anchor},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '任职年限',
							name : 'person.jobtotal'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : anchor},
						items :[{
							xtype : 'textarea',
							fieldLabel : '备注',
							height : 40 ,
							name : 'enPrize.remarks'
						}]
					}],
					buttons :[{
						text : '保存',
						iconCls : 'submitIcon',
						formBind : true,
						handler : function() {
							addPersonPanel.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form ,action) {
									obj = Ext.util.JSON.decode(action.response.responseText);
									if(obj.exsit == false){
										Ext.ux.Toast.msg('状态' ,obj.msg );
									}else{
										Ext.ux.Toast.msg('状态' ,'保存成功');
										jStorePersonWin.removeAll();
										jStorePersonWin.reload();
										window_add.destroy() ;
										
									}
								},
								failure : function(form, action) {
									Ext.ux.Toast.msg('状态','保存失败!');	
								}
							});
						}
					}]
				})	
				
				var window_add = new Ext.Window({
					title : '新增个人信息',
					layout : 'fit',
					width: (screen.width-180)*0.7-200,
					height : 420,
					closable : true,
					constrainHeader : true ,
					collapsible : true,
					resizable : true,
					plain : true,
					border : false,
					autoScroll : true ,
					modal : true,
					bodyStyle:'overflowX:hidden',
					buttonAlign : 'right',
					iconCls : 'newIcon',
					items :[addPersonPanel],
					listeners : {
						'beforeclose' : function(){
							if(addPersonPanel != null){
								if(addPersonPanel.getForm().isDirty()){
									Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
										if(btn=='yes'){
											addPersonPanel.buttons[0].handler.call() ;
										}else{
											addPersonPanel.getForm().reset() ;
											window_add.destroy() ;
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
		var button_fastadd = new Ext.Button({
			text : '快速新增',
			tooltip : '快速新增个人信息',
			iconCls : 'addIcon',
			scope : this,
			handler : function() {
				var addFastPersonPanel = new Ext.form.FormPanel({
					id : 'addFastPersonPanel',
					url :  __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
					monitorValid : true,
					bodyStyle:'padding:10px',
					autoScroll : true ,
					labelAlign : 'right',
					buttonAlign : 'center',
					height : 90,
					frame : true ,
					layout : 'column',
					items :[{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 65,
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'person.name',
							allowBlank : false,
							blankText : '姓名为必填内容'
						},{
							xtype : "dickeycombo",
						nodeKey :'card_type_key',
							fieldLabel : '证件类型',
							emptyText : '请选证件类型',
							hiddenName : 'person.cardtype',
							allowBlank : false,
							blankText : '证件类型为必填内容',
							listeners : {
								afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
										combox.setValue("");
									}else{
										combox.setValue(combox
											.getValue());
									}
									combox.clearInvalid();
								})
							},
								'change' : function(field,newValue,oldValue){
									if(newValue == 628){
										Ext.getCmp('cardnumberfast').index = 628;
										Ext.getCmp('cardnumberfast').regex = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
										Ext.getCmp('cardnumberfast').regexText = '身份证号码无效';
									}else {
										Ext.getCmp('cardnumberfast').regex = "" ;
									}
								}
							}
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 65,
						defaults : {anchor : '100%'},
						items :[{
							xtype : "dickeycombo",
						nodeKey :'sex_key',
							fieldLabel : '性别',
							emptyText : '请选性别',
							hiddenName : 'person.sex',
							allowBlank : false,
							blankText : '性别为必填内容',
							listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
							id : 'cardnumberfast',
							xtype : 'textfield',
							fieldLabel : '证件号码',
							name : 'person.cardnumber',
							allowBlank : false,
							blankText : '证件号码为必填内容',
							listeners : {
								'blur':function(f){
									ajaxUniquenessValidator(this,"ajaxValidatorCardPersonAjaxValidator","该人员已存在！");
								}
				            }
						}]
					},{
						xtype : 'hidden',
						name : 'person.job'
					},{
						xtype : 'hidden',
						name : 'person.cellphone'
					}],
					buttons :[{
						text : '保存',
						iconCls : 'submitIcon',
						formBind : true,
						handler : function() {
							addFastPersonPanel.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form ,action) {
									obj = Ext.util.JSON.decode(action.response.responseText);
									if(obj.exsit == false){
										Ext.ux.Toast.msg('状态' ,obj.msg );
									}else{
										Ext.ux.Toast.msg('状态' ,'保存成功' );
										jStorePersonWin.removeAll();
										jStorePersonWin.reload();
										window_add.destroy() ;
										
									}
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
				var window_add = new Ext.Window({
					id : 'w_fastadd',
					title : '快速录入个人信息',
					layout : 'fit',
					width: (screen.width-180)*0.7-200,
					height : 170,
					closable : true,
					constrainHeader : true ,
					collapsible : true,
					resizable : true,
					plain : true,
					border : false,
					autoScroll : true ,
					modal : true,
					bodyStyle:'overflowX:hidden',
					buttonAlign : 'right',
					iconCls : 'newIcon',
					items :[addFastPersonPanel],
					listeners : {
						'beforeclose' : function(){
							if(addFastPersonPanel != null){
								if(addFastPersonPanel.getForm().isDirty()){
									Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
										if(btn=='yes'){
											addFastPersonPanel.buttons[0].handler.call() ;
										}else{
											addFastPersonPanel.getForm().reset() ;
											window_add.destroy() ;
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
			tooltip : '编辑选中的个人信息',
			iconCls : 'updateIcon',
			scope : this,
			handler : function() {
			var selected = gPanelPersonWin.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url :  __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
					method : 'POST',
					success : function(response,request) {
						obj = Ext.util.JSON.decode(response.responseText);
						var personData = obj.data;
						var updatePersonPanel = new Ext.form.FormPanel({
							url : __ctxPath+'/creditFlow/customer/person/updateInfoPerson.do',
							monitorValid : true,
							bodyStyle:'padding:10px',
							autoScroll : true ,
							labelAlign : 'right',
							buttonAlign : 'center',
							frame : true ,
							autoWidth : true,
							isflag : false ,
							layout : 'column',
							items :[{
								columnWidth : .5,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '姓名',
									name : 'person.name',
									allowBlank : false,
									blankText : '姓名为必填内容',
									value : personData.name
								},{
									xtype : 'textfield',
									fieldLabel : '固定电话',
									name : 'person.telphone',
									value : personData.telphone
								},{
									xtype : "dickeycombo",
									nodeKey :'card_type_key',
									fieldLabel : '证件类型',
									hiddenName : 'person.cardtype',
									width : 80,
									//mode : 'remote',
									editable : false,
									allowBlank : false,
									//blankText : '证件类型为必选内容',
									value : personData.cardtype,
									/*valueNotFoundText : personData.cardtypevalue,*/
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
													combox.setValue("");
												}else{
													combox.setValue(combox
														.getValue());
												}
												combox.clearInvalid();
											})
										},
										'change' : function(field,newValue,oldValue){
											if(newValue == 628){
												Ext.getCmp('upcardnumber').regex = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
												Ext.getCmp('upcardnumber').regexText = '身份证号码无效';
											}else if(newValue == 629){
												Ext.getCmp('upcardnumber').regex ;
												Ext.getCmp('upcardnumber').regexText = '军官证号码无效';
											}else if(newValue == 630){
												Ext.getCmp('upcardnumber').regex ;
												Ext.getCmp('upcardnumber').regexText = '护照号码无效';
											}else {
												
											}
										}
									}
								},{
									xtype : "dickeycombo",
						nodeKey :'gmlx',
									fieldLabel : '公民类型',
									emptyText : '请选择公民类型',
									width : 80,
									hiddenName : 'person.peopletype',
									//mode : 'remote',
									editable : false,
									
									value : personData.peopletype,
									listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
						nodeKey :'nationality',
									fieldLabel : '民族',
									hiddenName : 'person.nationality',
									emptyText : '请选择民族',
									//mode : 'remote',
									width : 80,
									editable : false,
									
									value : personData.nationality,
									listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
									xtype : 'textfield',
									fieldLabel : '固定住址',
									name : 'person.address',
									value : personData.address
								},{
									xtype : "dickeycombo",
					nodeKey :'dgree',
									fieldLabel : '学历',
									hiddenName : 'person.dgree',
									emptyText : '请选择学历',
									width : 80,
									editable : false,
									
									value : personData.dgree,
									listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
								columnWidth : .5,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : anchor},
								items :[{
									xtype : "dickeycombo",
						nodeKey :'sex_key',
									fieldLabel : '性别',
									hiddenName : 'person.sex',
									width : 80,
									allowBlank : false,
									blankText : '必填信息',
									editable : false,
									
									value : personData.sex,
									listeners : {
							/*afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									//alert("combox.getValue()=="+combox.getValue());
									combox.setValue(combox.getValue());
									Ext.getCmp('xingbie_update').setValue(personData.sex) ;
									Ext.getCmp('xingbie_update').setRawValue(personData.sexvalue);
								})
							}*/
							
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
									xtype : 'textfield',
									fieldLabel : '手机号码',
									name : 'person.cellphone',
									value : personData.cellphone
								},{
									id : 'upcardnumber',
									xtype : 'textfield',
									fieldLabel : '证件号码',
									name : 'person.cardnumber',
									allowBlank : false,
									blankText : '证件号码为必填内容',
									value : personData.cardnumber,
									listeners : {
										'blur':function(f){
											ajaxUniquenessValidator(this,"ajaxValidatorCardPersonAjaxValidator","该人员已存在！");
										}
					                }
								},{
									xtype : 'datefield',
									fieldLabel : '出生日期',
									name : 'person.birthday',
									format : 'Y-m-d',
									value : personData.birthday
								},{
									xtype : 'textfield',
									fieldLabel : '籍贯',
									name : 'person.homeland',
									value : personData.homeland
								},{
									xtype : 'textfield',
									fieldLabel : '户口所在地',
									name : 'person.hukou',
									value : personData.hukou
								},{
									xtype : 'textfield',
									fieldLabel : '毕业院校',
									name : 'person.graduationunversity',
									value : personData.graduationunversity
								}]
							},{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '现工作单位',
									name : 'person.currentcompany',
									value : personData.currentcompany
								}]
							},{
								columnWidth : .5,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : anchor},
								items :[{
									xtype : "dickeycombo",
						nodeKey :'zhiwujob',
									fieldLabel : '职务',
									hiddenName : 'person.job',
									emptyText : '请选择职务',
									width : 80,
									editable : false,
									
									value : personData.job,
									listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
					nodeKey :'techpersonnel',
									fieldLabel : '技术职称',
									hiddenName : 'person.techpersonnel',
									emptyText : '请选择技术职称',
									width : 80,
									editable : false,
									
									value : personData.techpersonnel,
									listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
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
								columnWidth : .5,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'numberfield',
									fieldLabel : '任职年限',
									name : 'person.jobtotal',
									value : personData.jobtotal
								}]
							},{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textarea',
									fieldLabel : '备注',
									height : 40 ,
									name : 'person.remarks',
									value : personData.remarks
								}]
							},{
								xtype : 'hidden',
								name : 'person.id',
								value : personData.id
							}],
							buttons :[{
								text : '保存',
								iconCls : 'submitIcon',
								formBind : true,
								handler : function() {
									updatePersonPanel.getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function(form ,action) {
											obj = Ext.util.JSON.decode(action.response.responseText);
											if(obj.exsit == false){
												Ext.ux.Toast.msg('状态' ,obj.msg );
											}else{
												Ext.ux.Toast.msg('状态' ,'保存成功' );
												jStorePersonWin.removeAll();
												jStorePersonWin.reload();
												pwindow_update.destroy() ;
												
											}
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
						var pwindow_update = new Ext.Window({
							id : 'pwindow_update',
							title: '编辑个人基本信息',
							layout : 'fit',
							width: (screen.width-180)*0.7-200,
							height : 420,
							closable : true,
							collapsible : true,
							resizable : true,
							autoScroll : true ,
							plain : true,
							border : false,
							modal : true,
							buttonAlign: 'right',
							iconCls : 'upIcon',
							bodyStyle:'overflowX:hidden',
					        items :[updatePersonPanel],
					        listeners : {
								'beforeclose' : function(){
									if(updatePersonPanel != null){
										if(updatePersonPanel.getForm().isDirty()){
											Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
												if(btn=='yes'){
													updatePersonPanel.buttons[0].handler.call() ;
												}else{
													//updatePersonPanel.getForm().reset() ;
													pwindow_update.destroy() ;
													pwindow_update.close();
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
		var button_see = new Ext.Button({
			text : '查看',
			tooltip : '查看选中的人员信息',
			iconCls : 'seeIcon',
			scope : this,
			handler : function() {
				var selected = gPanelPersonWin.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id');
					Ext.Ajax.request({
						url :  __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
						method : 'POST',
						success : function(response,request){
							obj = Ext.util.JSON.decode(response.responseText);
							var personData = obj.data;	
							var seePersonPanel= new Ext.form.FormPanel({
								monitorValid : true,
								bodyStyle:'padding:10px',
								autoScroll : true ,
								labelAlign : 'right',
								buttonAlign : 'center',
								frame : true ,
								autoWidth : true,
								layout : 'column',
								items :[{
									columnWidth : .5,
									layout : 'form',
									labelWidth : 70,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '姓名',
										value : personData.name,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '固定电话',
										value : personData.telphone,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '证件类型',
										value : personData.cardtypevalue,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '公民类型',
										value : personData.peopletypevalue,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '民族',
										value : personData.nationalityvalue,
										readOnly : true,
						           	 	cls : 'readOnlyClass'
					
									},{
										xtype : 'textfield',
										fieldLabel : '固定住址',
										value : personData.address,
										readOnly : true,
						           	 	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '学历',
										value: personData.dgreevalue,
										readOnly : true,
							            cls : 'readOnlyClass'
									}]
								},{
									columnWidth : .5,
									layout : 'form',
									labelWidth : 70,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '性别',
										value : personData.sexvalue,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '手机号码',
										value : personData.cellphone,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '证件号码',
										value : personData.cardnumber,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '出生日期',
										value : personData.birthday,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '籍贯',
										value : personData.homeland,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '户口所在地',
										value : personData.hukou,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '毕业院校',
										value : personData.graduationunversity,
										readOnly : true,
						            	cls : 'readOnlyClass'
									}]
								},{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 70,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '现工作单位',
										value : personData.currentcompany,
										readOnly : true,
						            	cls : 'readOnlyClass'
									}]
								},{
									columnWidth : .5,
									layout : 'form',
									labelWidth : 70,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '职务',
										value : personData.jobvalue,
										readOnly : true,
						            	cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '技术职称',
										value : personData.sonnelvalue,
										readOnly : true,
							            cls : 'readOnlyClass'
									}]
								},{
									columnWidth : .5,
									layout : 'form',
									labelWidth : 70,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'numberfield',
										fieldLabel : '任职年限',
										value : personData.jobtotal,
										readOnly : true,
							            cls : 'readOnlyClass'
									}]
								},{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 70,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textarea',
										fieldLabel : '备注',
										height : 40 ,
										value : personData.remarks,
										readOnly : true,
							            cls : 'readOnlyClass'
									}]
								}]
							})
							var window_see = new Ext.Window({
								id : 'window_see',
								title: '查看个人信息',
								layout : 'fit',
								width: (screen.width-180)*0.7-200,
								height : 420,
								closable : true,
								collapsible : true,
								resizable : true,
								plain : true,
								border : false,
								autoScroll : true ,
								modal : true,
								buttonAlign: 'right',
								iconCls : 'lookIcon',
								bodyStyle:'overflowX:hidden',
							    items :[seePersonPanel]
							});
							window_see.show();			
							},
							failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
							});	
							}
						}
					});
		var gPanelPersonWin = new Ext.grid.GridPanel({
			id : 'gPanelPersonWin',
			store : jStorePersonWin,
			width : 500,
			height : 300,
			colModel : cModelPersonWin,
			autoExpandColumn : 7,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : true,
			bbar : pagingBar,
			tbar : [button_add/*,button_fastadd*/,button_see,button_update,'-' ,new Ext.form.Label({text : '姓名：'}),new Ext.form.TextField({id:'personNameRefer',width:80}), new Ext.form.Label({text : '性别：'}),{id:'personSexRefer',width:80,xtype : "dickeycombo",
						nodeKey :'sex_key'},{text:'查找',iconCls : 'searchIcon'}],//],
			listeners : {																																							
				'rowdblclick' : function(grid, index, e) {
					var selected = grid.getStore().getAt(index) ;
					callbackFun(selected,funName);
					personWin.destroy();
				}
			}
		});
		Ext.getCmp('personNameRefer').on('blur',function(field){
			var value = Ext.get('personNameRefer').dom.value;
			jStorePersonWin.baseParams.name = value ;
			jStorePersonWin.load({
				params : {
					start : 0,
					limit : 15
				}
			});
		});
		Ext.getCmp('personSexRefer').on('blur',function(field){
			var value = Ext.get('personSexRefer').dom.value;
			jStorePersonWin.baseParams.sexvalue = value ;
			jStorePersonWin.load({
				params : {
					start : 0,
					limit : 15
				}
			});
		});
		var personWin = new Ext.Window({
			title : '人员列表',
			border : false,
			width: (screen.width-180)*0.75 - 200,
			height : 440,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gPanelPersonWin],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
		  		xtype:'button',
			    text:'关闭',
			    iconCls:'close',
			    handler:function(){
//			    	window_EnterpriseForSelect.close();
					personWin.close();
			    }
	       	}]
		});
		//加载框开始就加载开始  by chencc
		personWin.show();
		
		jStorePersonWin.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		//加载框开始就加载结束  by chencc
		var searchByCondition = function() {
			jStorePersonWin.load({
				params : {
					start : 0,
					limit : 15
				}
			});
		}
		var callbackFun = function(selected,funName){
			personJsonObj = {
				id : selected.get('id'),
				name : selected.get('name'),
				mateid : selected.get('mateid'),
				cardtype : selected.get('cardtype'),
				cardnumber : selected.get('cardnumber'),
				cellphone : selected.get('cellphone'),
				telphone : selected.get('telphone'),
				job : selected.get('job'),
				creditbank : selected.get('creditbank'),
				creditperson : selected.get('creditperson'),
				creditaccount : selected.get('creditaccount'),
				wagebank : selected.get('wagebank'),
				wageperson : selected.get('wageperson'),
				wageaccount : selected.get('wageaccount'),
				sex : selected.get('sex'),
				birthday : selected.get('birthday'),
				dgree : selected.get('dgree'),
				currentcompany : selected.get('currentcompany'),
				unitproperties : selected.get('unitproperties'),
				techpersonnel : selected.get('techpersonnel'),
				unitphone : selected.get('unitphone'),
				familyaddress : selected.get('familyaddress'),
				selfemail : selected.get('selfemail'),
				pinyinname : selected.get('pinyinname'),
				marry : selected.get('marry'),
				monthIncomes : selected.get('monthIncomes'),
				sexvalue : selected.get('sexvalue'),
				marryvalue : selected.get('marryvalue'),
				cardtypevalue : selected.get('cardtypevalue'),
				jobvalue : selected.get('jobvalue'),
				dgreevalue : selected.get('dgreevalue'),
				sonnelvalue : selected.get('sonnelvalue'),
				unitpropertiesvalue : selected.get('unitpropertiesvalue'),
				hukou : selected.get('hukou'),
				beforeName : selected.get('beforeName'),
				steadyWage : selected.get('steadyWage'),
				postaddress : selected.get('postaddress'),
				isheadoffamily : selected.get('isheadoffamily'),
				familyshengname : selected.get('familyshengname'),
				familysheng : selected.get('familysheng'),
				familyshiname : selected.get('familyshiname'),
				familyshi : selected.get('familyshi'),
				familyxianname : selected.get('familyxianname'),
				familyxian : selected.get('familyxian'),
				roadname : selected.get('roadname'),
				roadnum : selected.get('roadnum'),
				communityname : selected.get('communityname'),
				doorplatenum : selected.get('doorplatenum'),
				familypostcode : selected.get('familypostcode'),
				employway : selected.get('employway'),
				homeshape : selected.get('homeshape'),
				housearea : selected.get('housearea'),
				homeincome : selected.get('homeincome'),
				homeotherincome : selected.get('homeotherincome'),
				communityname : selected.get('communityname'),
				household : selected.get('household'),
				homeexpend : selected.get('homeexpend'),
				homecreditexpend : selected.get('homecreditexpend'),
				homeshapevalue : selected.get('homeshapevalue'),
				employwayvalue : selected.get('employwayvalue'),
				personSFZZId : selected.get('personSFZZId'),
				personSFZZUrl : selected.get('personSFZZUrl'),
				personSFZFId : selected.get('personSFZFId'),
				personSFZFUrl : selected.get('personSFZFUrl')
			}
		funName(personJsonObj);
	}
}
//add by lisl 2012-2-17
/**查看个人客户信息*/
var seePersonCustomer=function(id){
	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
		method : 'POST',
		success : function(response,request){
			obj = Ext.util.JSON.decode(response.responseText);
			var personData = obj.data;
            var randomId=rand(100000);
			var id="see_person"+randomId;
			var anchor = '100%';
			var window_see = new Ext.Window({
						title : '查看个人客户详细信息',
						layout : 'fit',
						width : (screen.width - 180) * 0.7 + 160,
						maximizable:true,
						height : 460,
						closable : true,
						modal : true,
						plain : true,
						border : false,
						items : [new personObj({url:null,id:id,personData:personData,isReadOnly:true})],
						listeners : {
							'beforeclose' : function(panel) {
								window_see.destroy();
							}
						}
					});
			window_see.show();
									},
			failure : function(response) {					
					Ext.ux.Toast.msg('状态','操作失败，请重试');		
			},
			params: { id: id }
		});	
}
//end 