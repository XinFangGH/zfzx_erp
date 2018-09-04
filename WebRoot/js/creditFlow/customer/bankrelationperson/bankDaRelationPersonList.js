var anchor = '97%';
var selectBankRelationPersonTotal = function(funName , bankLable){
	    var bankPersonObj;
	    jStoreBankRelationPerson = new Ext.data.JsonStore( {
			url : __ctxPath + '/creditFlow/customer/bankRelationPerson/queryBankRelationPersonCustomerBankRelationPerson.do?isAll='+isGranted('_detail_syyhkh'),
			totalProperty : 'totalProperty',
			root : 'topics',
			remoteSort: true,
			fields : [ {name : 'id'},{name : 'bankid'},{name : 'name'},{name : 'sexvalue'}, {name : 'duty'}, {name : 'blmtelephone'}, {name : 'marriage'}, {name : 'marriagename'}, {name : 'birthday'}, {name : 'bankname'}, {name : 'email'}, {name : 'address'},  {name : 'fenbankid'}, {name : 'blmphone'},{name : 'fenbankvalue'},{name : 'bankaddress'} ]
		});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
		   msg : "加载数据中······,请稍后······"
		});
		jStoreBankRelationPerson.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cModelBankRelationPerson = new Ext.grid.ColumnModel(
		[sm,
			new Ext.grid.RowNumberer( {
				header : '序号',
				width : 35
			}),
			 {
				header : "联系人姓名",
				width : 120,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "性别",
				width : 60,
				sortable : true,
				dataIndex : 'sexvalue'
			}, {
				header : "婚姻状况",
				width : 80,
				sortable : true,
				dataIndex : 'marriagename'
			}, {
				header : "办公电话",
				width : 80,
				sortable : true,
				hidden :true,
				dataIndex : 'blmtelephone'
			}, {
				header : "手机号码",
				width : 100,
				hidden :true,
				sortable : true,
				dataIndex : 'blmphone'
			} ,{
				header : "电子邮件",
				width : 130,
				sortable : true,
				dataIndex : 'email'
			},{
				header : "银行名称",
				width : 170,
				sortable : true,
				dataIndex : 'bankname'
			},{
				header : "支行名称",
				width : 150,
				sortable : true,
				dataIndex : 'fenbankvalue'
			},{
				header : "职务",
				width : 120,
				sortable : true,
				dataIndex : 'duty'
			}]);
		var button_add = new Ext.Button({
			text : '新增',
			tooltip : '增加一条银行联系人信息',
			iconCls : 'btn-add',
			scope : this,
			handler : function() {
				//addWin();
				new BankRelationPersonWindow({listPanel :gPanelBankRelationPerson,type :'add',url :__ctxPath + '/creditFlow/customer/bankrelationperson/addBankRelationPersonCustomerBankRelationPerson.do'}).show();
			}
		});
		var button_update = new Ext.Button({
			text : '编辑',
			tooltip : '修改选中的银行联系人信息',
			iconCls : 'btn-edit',
			scope : this,
			handler : function() {
				var rows = gPanelBankRelationPerson.getSelectionModel().getSelections();
						if(rows.length==0){
							Ext.ux.Toast.msg('操作信息','请选择记录!');
							return;
						}else if(rows.length>1){
							Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
							return;
						}else{
							Ext.Ajax.request({
								url : __ctxPath+'/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
								method : 'POST',
								scope : this,
								success : function(response,request){
									var obj = Ext.util.JSON.decode(response.responseText);
									var bankRelationPersonData = obj.data;	
									new BankRelationPersonWindow({listPanel :gPanelBankRelationPerson,type :'edit',url :__ctxPath+'/creditFlow/customer/bankrelationperson/updateCustomerBankRelationPerson.do',bankRelationPersonData : bankRelationPersonData}).show();
								},
								failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
								},
								params: { id: rows[0].data.id }
							});	
						}
				
				//updateWin(gPanelBankRelationPerson);
			}
		});
		var button_see = new Ext.Button({
			text : '查看',
			tooltip : '查看选中的银行联系人信息',
			iconCls : 'btn-detail',
			scope : this,
			handler : function() {
				//seeWin(gPanelBankRelationPerson);
				var rows = gPanelBankRelationPerson.getSelectionModel().getSelections();
			if(rows.length==0){
				Ext.ux.Toast.msg('操作信息','请选择记录!');
				return;
			}else if(rows.length>1){
				Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
				return;
			}else{
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/customer/bankrelationperson/seeBankRelationPersonCustomerBankRelationPerson.do',
					method : 'POST',
					scope : this,
					success : function(response,request){
						var obj = Ext.util.JSON.decode(response.responseText);
						var bankRelationPersonData = obj.data;	
						new BankRelationPersonWindow({isRead : true,type :'read',bankRelationPersonData : bankRelationPersonData}).show();		
					},
					failure : function(response) {					
						Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: rows[0].data.id }
				});	
			}
			}
		}); 
		var pagingBar = new Ext.PagingToolbar( {
			pageSize : 15,
			store : jStoreBankRelationPerson,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
	 	var gPanelBankRelationPerson = new Ext.grid.GridPanel( {
	 		border : false,
			region : 'center',
			loadMask : new Ext.LoadMask(Ext.getBody(), {msg : "加载数据中······,请稍后······"}),
			tbar:[button_see,'-',button_add,'-',button_update,'-',new Ext.form.Label({text : '联系人姓名：'}),new Ext.form.TextField({id:'paramCnNameBank',width:100}), new Ext.form.Label({text : '银行：'}),new Ext.form.TextField({id:'paramBankDutyBank',width:100}), new Ext.form.Label({text : '职务：'}),new Ext.form.TextField({id:'paramCnDutyBank',width:100}),{text:'查找',iconCls : 'btn-search'}],
			store : jStoreBankRelationPerson,
			colModel : cModelBankRelationPerson,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			autoExpandColumn : 8,
			loadMask : myMask,
			autoWidth : true,
			bbar : pagingBar,
			listeners : {
				'rowdblclick' : function(grid,index,e){
					var selected = grid.getStore().getAt(index);
					callbackFun(selected, funName);
					bankWin.destroy();
				}
			}
		});
    	var bankWin = new Ext.Window({
			title : '银行联系人列表',
			width : 950,
			height : 380,
			modal : true ,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			resizable : true,
			minHeight : 370,
			minWidth : 900,
			items : [
			   gPanelBankRelationPerson
			]});
		    bankWin.show();
		    
	   function addWin(){
		var addBankRelationPersonWinRefer = new Ext.Window({
			id : 'addBankRelationPersonWinRefer',
			title : '新增银行联系人',
			layout : 'fit',
			width : (screen.width-180)*0.7 - 100,
			height : 410,
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
			minHeight : 410,
			minWidth : 535,
			items :[
			    new Ext.form.FormPanel({
					id :'addBankRelationPersonPanel',
					url : __ctxPath + '/creditFlow/customer/bankRelationPerson/addBankRelationPersonCustomerBankRelationPerson.do',
					//monitorValid : true,
					bodyStyle:'padding:5px',
					autoScroll : true ,
					labelAlign : 'right',
					buttonAlign : 'center',
					frame : true ,
					items : [{
						layout : 'column',
						xtype:'fieldset',
			            title: '基本信息',
			            anchor: '97%',
			            collapsible: true,
			            autoHeight:true,
			            items : [{
			            	columnWidth : .5,
							layout : 'form',
							labelWidth : 80,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'textfield',
								fieldLabel : '客户姓名',
								name : 'bankRelationPerson.name',
								allowBlank : false,
							    blankText : '必填信息'
							    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
								//regexText : '只能输入中文'
							},{
								id : 'addbanknamereferbar',
								xtype : 'textfield',
								fieldLabel : '所在银行',
								//emptyText:'请选择您所在银行',
								allowBlank : false,
								name : 'bankname',
								//value : objArray[(objArray.length)-2].text,
								readOnly : true
							},{
								xtype : 'textfield',
								fieldLabel : '电话号码',
								allowBlank : false,
								name : 'bankRelationPerson.blmtelephone'
							},{
								xtype : 'textfield',
								fieldLabel : '手机号码',
								allowBlank : false,
								name : 'bankRelationPerson.blmphone'
							},{
								xtype : "dickeycombo",
					nodeKey :'8',
								fieldLabel : '婚姻状况',
								hiddenName : 'bankRelationPerson.marriage',
								//mode : 'romote',
								width : 80 ,
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
							},{
								xtype : 'textfield',
								fieldLabel : '孩子1姓名',
								name : 'bankRelationPerson.childname1'
							},{
								xtype : 'textfield',
								fieldLabel : '孩子2姓名',
								name : 'bankRelationPerson.childname2'
							},{
								xtype : 'textfield',
								fieldLabel : '电子邮件',
								name : 'bankRelationPerson.email',
								allowBlank : false,
								regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
								regexText : '电子邮件格式不正确或无效的电子邮件地址'
							},{
								xtype : 'textfield',
								fieldLabel : '籍贯',
								name : 'bankRelationPerson.nativeplace',
								regex : /^[\u4e00-\u9fa5]{1,10}$/,
								regexText : '只能输入中文'
							}]
			            },{
			            	columnWidth : .5,
							layout : 'form',
							labelWidth : 80,
							defaults : {anchor : anchor},
							items : [{
								xtype : "dickeycombo",
								nodeKey :'sex_key',
								fieldLabel : '性别',
								hiddenName : 'bankRelationPerson.sex',
								width : 80 ,
								//mode : 'romote',
								editable : false,
							    //emptyText:'请选性别',
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
								id : 'addzhibanknamereferbar',
								xtype : 'trigger',
								triggerClass :'x-form-search-trigger',
								fieldLabel : '银行支行',
								onTriggerClick : function(){
									selectDictionary('bank',getObjArrayReferbar);
								},
								editable : false,
								anchor:anchor,
								allowBlank : false
								//value : objArray[0].text
							},{
								xtype : 'textfield',
								fieldLabel : '职务',
								name : 'bankRelationPerson.duty',
								allowBlank : false
								//regex : /^[\u4e00-\u9fa5]{1,10}$/,
								//regexText : '只能输入中文'
							},{
								xtype : 'textfield',
								fieldLabel : '传真号码',
								name : 'bankRelationPerson.fax'
							},{
								xtype : 'datefield',
								fieldLabel : '联系人生日',
								name : 'bankRelationPerson.birthday',
								format : 'Y-m-d'
							},{
								xtype : 'datefield',
								fieldLabel : '婚姻纪念日',
								name : 'bankRelationPerson.marriagedate',
								format : 'Y-m-d'
							},{
								xtype : 'datefield',
								fieldLabel : '孩子1生日',
								name : 'bankRelationPerson.childbirthday1',
								format : 'Y-m-d'
							},{
								xtype : 'datefield',
								fieldLabel : '孩子2生日',
								name : 'bankRelationPerson.childbirthday2',
								format : 'Y-m-d'
							}]
			            },{
			            	columnWidth : 1,
							layout : 'form',
							labelWidth : 80,
							defaults : {anchor : '98.5%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '家庭住址',
								name : 'bankRelationPerson.homeaddress'
							},{
								xtype : 'textfield',
								fieldLabel : '户口所在地',
								name : 'bankRelationPerson.hkszd'
							},{
								xtype : 'textfield',
								fieldLabel : '配偶信息',
								name : 'bankRelationPerson.mate'
							},{
								xtype : 'textfield',
								fieldLabel : '父母信息',
								name : 'bankRelationPerson.parents'
							}]
			            }]
					},{
						layout : 'column',
						xtype:'fieldset',
			            title: '兴趣爱好',
			            collapsible: true,
			            autoHeight:true,
			            anchor: '97%',
			            items : [{
			            	columnWidth : 1,
							layout : 'form',
							labelWidth : 80,
							defaults : {anchor : '98.5%'},
							items :[{
								xtype : 'textarea',
								fieldLabel : '嗜好与兴趣',
								height : 40,
								name : 'bankRelationPerson.interest1'
							},{
								xtype : 'textarea',
								fieldLabel : '喝酒或抽烟',
								height : 40,
								name : 'bankRelationPerson.interest2'
							},{
								xtype : 'textarea',
								fieldLabel : '就餐口味地点',
								height : 40,
								name : 'bankRelationPerson.interest3'
							},{
								xtype : 'textarea',
								fieldLabel : '聊天话题',
								height : 40,
								name : 'bankRelationPerson.interest4'
							}]
						}]
					},{
						id :'addbankidreferbar',
						xtype : 'hidden',
						name : 'bankRelationPerson.bankid'
						//value : objArray[(objArray.length)-2].id
					},{
						id : 'addzhibankidreferbar',
						xtype : 'hidden',
						name : 'bankRelationPerson.fenbankid'
						//value : objArray[0].id
					}], 
					buttons : [{
						id : 'submit',
						text : '保存',
						iconCls : 'submitIcon',
						formBind : true,
						scope : this,
						handler : function() {
							addBankRelationPersonWinRefer.findById('addBankRelationPersonPanel').getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form ,action) {
									Ext.ux.Toast.msg('状态', '添加成功!');
									jStoreBankRelationPerson.reload();
									addBankRelationPersonWinRefer.destroy();
								},
								
								failure : function(form, action) {
									/*if(action.response.status==0){
										Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
									}else if(action.response.status==-1){
										Ext.ux.Toast.msg('状态','连接超时，请重试!');
									}else{*/
										Ext.ux.Toast.msg('状态','添加失败，数据可能没有填写完整!');		
									//}
								}
							});
						}
					}]
				})
			]
		}).show();
	}	
	function updateWin(grid){
		var selected = grid.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条银行联系人记录!');
		}else{
			var id = selected.get('id');
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
				method : 'POST',
				success : function(response,request) {
					obj = Ext.util.JSON.decode(response.responseText);
						bankRelationPersonData = obj.data;
						var updateBankRelationPersonWinRefer = new Ext.Window({
							id : 'updateBankRelationPersonWinRefer',
							title: '修改银行联系人信息',
							layout : 'fit',
							width : (screen.width-180)*0.7 - 100,
							height : 410,
							constrainHeader : true ,
							closable : true,
							resizable : true,
							plain : true,
							border : false,
							modal : true,
							minHeight : 410,
							minWidth : 535,
							buttonAlign: 'right',
							bodyStyle:'overflowX:hidden',
					       	items :[
					       		new Ext.form.FormPanel({
									id :'updateBankRelationPersonPanel',
									url : __ctxPath + '/creditFlow/customer/bankRelationPerson/updateCustomerBankRelationPerson.do',
									//monitorValid : true,
									bodyStyle:'padding:5px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									items : [{
										layout : 'column',
										xtype:'fieldset',
							            title: '基本信息',
							            collapsible: true,
							            autoHeight:true,
							            anchor: '97%',
							            items : [{
							            	columnWidth : .5,
											layout : 'form',
											labelWidth : 80,
											defaults : {anchor : anchor},
											items : [{
												xtype : 'textfield',
												fieldLabel : '客户姓名',
												name : 'bankRelationPerson.name',
												allowBlank : false,
											    //blankText : '必填信息',
											    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
												//regexText : '只能输入中文',
												value : bankRelationPersonData.name
											},{
												id : 'upbanknamereferbar',
												xtype : 'textfield',
												fieldLabel : '所在银行',
												//emptyText:'请选择您所在银行',
												allowBlank : false,
											    //blankText : '必填信息',
												name : 'bankname',
												value : bankRelationPersonData.bankname,
												readOnly : true
											},{
												xtype : 'textfield',
												fieldLabel : '电话号码',
												name : 'bankRelationPerson.blmtelephone',
												value : bankRelationPersonData.blmtelephone
											},{
												xtype : 'textfield',
												fieldLabel : '手机号码',
												name : 'bankRelationPerson.blmphone',
												value : bankRelationPersonData.blmphone
											},{
												xtype : "dickeycombo",
					nodeKey :'8',
												fieldLabel : '婚姻状况',
												hiddenName : 'bankRelationPerson.marriage',
												//mode : 'romote',
												width : 80 ,
												editable : false,
											    //emptyText:'请选择婚姻状况',
												value : bankRelationPersonData.marriage,
												/*valueNotFoundText : bankRelationPersonData.marriagename*/
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
												xtype : 'textfield',
												fieldLabel : '孩子1姓名',
												name : 'bankRelationPerson.childname1',
												value : bankRelationPersonData.childname1
											},{
												xtype : 'textfield',
												fieldLabel : '孩子2姓名',
												name : 'bankRelationPerson.childname2',
												value : bankRelationPersonData.childname2
											},{
												xtype : 'textfield',
												fieldLabel : '电子邮件',
												name : 'bankRelationPerson.email',
												regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
												regexText : '电子邮件格式不正确或无效的电子邮件地址',
												value : bankRelationPersonData.email
											},{
												xtype : 'textfield',
												fieldLabel : '籍贯',
												name : 'bankRelationPerson.nativeplace',
												regex : /^[\u4e00-\u9fa5]{1,10}$/,
												regexText : '只能输入中文',
												value : bankRelationPersonData.nativeplace
											}]
							            },{
							            	columnWidth : .5,
											layout : 'form',
											labelWidth : 80,
											defaults : {anchor : anchor},
											items : [{
												xtype : "dickeycombo",
												nodeKey :'sex_key',
												fieldLabel : '性别',
												hiddenName : 'bankRelationPerson.sex',
												width : 80 ,
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
												},
											
												value : bankRelationPersonData.sex
											},{
												id : 'upzhibanknamereferbar',
												xtype : 'trigger',
												triggerClass :'x-form-search-trigger',
												fieldLabel : '银行支行',
												onTriggerClick : function(){
													selectDictionary('bank',getObjArrayReferUpbar);
												},
												editable : false,
												anchor:anchor,
												allowBlank : false ,
												value : bankRelationPersonData.fenbankvalue
											},{
												xtype : 'textfield',
												fieldLabel : '职务',
												name : 'bankRelationPerson.duty',
												//regex : /^[\u4e00-\u9fa5]{1,10}$/,
												//regexText : '只能输入中文',
												value : bankRelationPersonData.duty
											},{
												xtype : 'textfield',
												fieldLabel : '传真号码',
												name : 'bankRelationPerson.fax',
												value : bankRelationPersonData.fax
											},{
												xtype : 'datefield',
												fieldLabel : '联系人生日',
												name : 'bankRelationPerson.birthday',
												format : 'Y-m-d',
												value : bankRelationPersonData.birthday
											},{
												xtype : 'datefield',
												fieldLabel : '婚姻纪念日',
												name : 'bankRelationPerson.marriagedate',
												format : 'Y-m-d',
												value : bankRelationPersonData.marriagedate
											},{
												xtype : 'datefield',
												fieldLabel : '孩子1生日',
												name : 'bankRelationPerson.childbirthday1',
												format : 'Y-m-d',
												value : bankRelationPersonData.childbirthday1
											},{
												xtype : 'datefield',
												fieldLabel : '孩子2生日',
												name : 'bankRelationPerson.childbirthday2',
												format : 'Y-m-d',
												value : bankRelationPersonData.childbirthday2
											}]
							            },{
							            	columnWidth : 1,
											layout : 'form',
											labelWidth : 80,
											defaults : {anchor : '98.5%'},
											items :[{
												xtype : 'textfield',
												fieldLabel : '家庭住址',
												name : 'bankRelationPerson.homeaddress',
												value : bankRelationPersonData.homeaddress
											},{
												xtype : 'textfield',
												fieldLabel : '户口所在地',
												name : 'bankRelationPerson.hkszd',
												value : bankRelationPersonData.hkszd
											},{
												xtype : 'textfield',
												fieldLabel : '配偶信息',
												name : 'bankRelationPerson.mate',
												value : bankRelationPersonData.mate
											},{
												xtype : 'textfield',
												fieldLabel : '父母信息',
												name : 'bankRelationPerson.parents',
												value : bankRelationPersonData.parents
											}]
							            }]
									},{
										layout : 'column',
										xtype:'fieldset',
							            title: '兴趣爱好',
							            collapsible: true,
							            autoHeight:true,
							           	anchor: '97%',
							            items : [{
							            	columnWidth : 1,
											layout : 'form',
											labelWidth : 80,
											defaults : {anchor : '98.5%'},
											items :[{
												xtype : 'textarea',
												fieldLabel : '嗜好与兴趣',
												height : 40,
												name : 'bankRelationPerson.interest1',
												value : bankRelationPersonData.interest1
											},{
												xtype : 'textarea',
												fieldLabel : '喝酒或抽烟',
												height : 40,
												name : 'bankRelationPerson.interest2',
												value : bankRelationPersonData.interest2
											},{
												xtype : 'textarea',
												fieldLabel : '就餐口味地点',
												height : 40,
												name : 'bankRelationPerson.interest3',
												value : bankRelationPersonData.interest3
											},{
												xtype : 'textarea',
												fieldLabel : '聊天话题',
												height : 40,
												name : 'bankRelationPerson.interest4',
												value : bankRelationPersonData.interest4
											}]
										}]
									},{
										id : 'upbankidreferbar',
										xtype : 'hidden',
										name : 'bankRelationPerson.bankid',
										value : bankRelationPersonData.bankid
									},{
										//id : 'upzhibankidreferbar',
										xtype : 'hidden',
										name : 'bankRelationPerson.id',
										value : bankRelationPersonData.id
									},{
										id : 'upzhibankidreferbar',
										xtype : 'hidden',
										name : 'bankRelationPerson.fenbankid',
										value : bankRelationPersonData.fenbankid
									}], 
									buttons : [{
										id : 'submit',
										text : '保存',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											updateBankRelationPersonWinRefer.findById('updateBankRelationPersonPanel').getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													Ext.ux.Toast.msg('状态', '修改成功!');
													jStoreBankRelationPerson.reload();
													updateBankRelationPersonWinRefer.destroy();
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
					       	]
						}).show();			
				},
				failure : function(response) {					
						Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { id: id }
			});	
		}
	}
	function seeWin(grid){
		var selected = grid.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条银行联系人记录!');
		}else{
			var id = selected.get('id');
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
				method : 'POST',
				success : function(response,request){
					obj = Ext.util.JSON.decode(response.responseText);
					bankRelationPersonData = obj.data;	
					var seeBankRelationPersonWinRefer = new Ext.Window({
						id : 'seeBankRelationPersonWinRefer',
						title: '查看银行联系人信息',
						layout : 'fit',
						width : (screen.width-180)*0.7 - 100,
						height : 410,
						closable : true,
						collapsible : true,
						resizable : true,
						plain : true,
						border : false,
						autoScroll : true ,
						modal : true,
						buttonAlign: 'right',
						minHeight : 410,
						minWidth : 535,
						bodyStyle:'overflowX:hidden',
						items :[
							new Ext.form.FormPanel({
								id :'seeBankRelationPersonPanel',
								bodyStyle:'padding:5px',
								autoScroll : true ,
								labelAlign : 'right',
								buttonAlign : 'center',
								frame : true ,
								items : [{
									layout : 'column',
									xtype:'fieldset',
						            title: '基本信息',
						            collapsible: true,
						            autoHeight:true,
						            anchor: '97%',
						            items : [{
						            	columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										defaults : {anchor : anchor},
										items : [{
											xtype : 'textfield',
											fieldLabel : '客户姓名',
											value : bankRelationPersonData.name,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '所在银行',
											readOnly : true,
											value : bankRelationPersonData.bankname
										},{
											xtype : 'textfield',
											fieldLabel : '电话号码',
											value : bankRelationPersonData.blmtelephone,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '手机号码',
											value : bankRelationPersonData.blmphone,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '婚姻状况',
											value : bankRelationPersonData.marriagename,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '孩子1姓名',
											value : bankRelationPersonData.childname1,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '孩子2姓名',
											value : bankRelationPersonData.childname2,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '电子邮件',
											readOnly : true,
											value : bankRelationPersonData.email
										},{
											xtype : 'textfield',
											fieldLabel : '籍贯',
											/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
											regexText : '只能输入中文',*/
											value : bankRelationPersonData.nativeplace,
											readOnly : true
										}]
						            },{
						            	columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										defaults : {anchor : anchor},
										items : [{
											xtype : 'textfield',
											fieldLabel : '性别',
											value : bankRelationPersonData.sexvalue,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '银行支行',
											value : bankRelationPersonData.fenbankvalue,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '职务',
											value : bankRelationPersonData.duty,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '传真号码',
											value : bankRelationPersonData.fax,
											readOnly : true
										},{
											xtype : 'datefield',
											fieldLabel : '联系人生日',
											format : 'Y-m-d',
											value : bankRelationPersonData.birthday,
											readOnly : true
										},{
											xtype : 'datefield',
											fieldLabel : '婚姻纪念日',
											format : 'Y-m-d',
											value : bankRelationPersonData.marriagedate,
											readOnly : true
										},{
											xtype : 'datefield',
											fieldLabel : '孩子1生日',
											format : 'Y-m-d',
											value : bankRelationPersonData.childbirthday1,
											readOnly : true
										},{
											xtype : 'datefield',
											fieldLabel : '孩子2生日',
											format : 'Y-m-d',
											value : bankRelationPersonData.childbirthday2,
											readOnly : true
										}]
						            },{
						            	columnWidth : 1,
										layout : 'form',
										labelWidth : 80,
										defaults : {anchor : '98.5%'},
										items :[{
											xtype : 'textfield',
											fieldLabel : '家庭住址',
											value : bankRelationPersonData.homeaddress,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '户口所在地',
											value : bankRelationPersonData.hkszd,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '配偶信息',
											value : bankRelationPersonData.mate,
											readOnly : true
										},{
											xtype : 'textfield',
											fieldLabel : '父母信息',
											value : bankRelationPersonData.parents,
											readOnly : true
										}]
						            }]
								},{
									layout : 'column',
									xtype:'fieldset',
						            title: '兴趣爱好',
						            collapsible: true,
						            autoHeight:true,
						           	anchor: '97%',
						            items : [{
						            	columnWidth : 1,
										layout : 'form',
										labelWidth : 80,
										defaults : {anchor : '98.5%'},
										items :[{
											xtype : 'textarea',
											fieldLabel : '嗜好与兴趣',
											height : 40,
											value : bankRelationPersonData.interest1,
											readOnly : true
										},{
											xtype : 'textarea',
											fieldLabel : '喝酒或抽烟',
											height : 40,
											value : bankRelationPersonData.interest2,
											readOnly : true
										},{
											xtype : 'textarea',
											fieldLabel : '就餐口味地点',
											height : 40,
											value : bankRelationPersonData.interest3,
											readOnly : true
										},{
											xtype : 'textarea',
											fieldLabel : '聊天话题',
											height : 40,
											value : bankRelationPersonData.interest4,
											readOnly : true
										}]
									}]
								}]
							})
						]
					}).show();
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
			});	
		}
	};
	Ext.getCmp('paramCnNameBank').on('blur',function(){
		var nameValue = Ext.get('paramCnNameBank').dom.value;
		jStoreBankRelationPerson.baseParams.name = nameValue ;
		jStoreBankRelationPerson.load({
			params : {
				start : 0,
				limit : 15
			}
		});
	});
	Ext.getCmp('paramCnDutyBank').on('blur',function(){
		var dutyValue = Ext.get('paramCnDutyBank').dom.value;
		jStoreBankRelationPerson.baseParams.duty = dutyValue ;
		jStoreBankRelationPerson.load({
			params : {
				start : 0,
				limit : 15
			}
		});
	});
	Ext.getCmp('paramBankDutyBank').on('blur',function(){
		var banknameValue = Ext.get('paramBankDutyBank').dom.value;
		jStoreBankRelationPerson.baseParams.bankname = banknameValue ;
		jStoreBankRelationPerson.load({
			params : {
				start : 0,
				limit : 15
			}
		});
	});
	   var callbackFun = function(selected, funName) {
	      bankPersonObj = {
			 id : selected.get('id'),
			 email : selected.get('email'),
			 duty : selected.get('duty'),
			 name : selected.get('name'),
			 blmphone : selected.get('blmphone'),
			 blmtelephone : selected.get('blmtelephone'),
			 bankname : selected.get('bankname'),
			 fenbankvalue : selected.get('fenbankvalue'),
			 fenbankid : selected.get('fenbankid'),
			 bankid : selected.get('bankid')
	   	  }
	      funName(bankPersonObj);
	   }
	var getObjArrayReferbar = function(objArray){
		Ext.getCmp('addbanknamereferbar').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('addbankidreferbar').setValue(objArray[(objArray.length)-1].id);
		bankIdVal = objArray[0].id ;
		Ext.getCmp('addzhibanknamereferbar').setValue(objArray[0].text);
		Ext.getCmp('addzhibankidreferbar').setValue(objArray[0].id);
		Ext.getCmp('addzhibanknamereferbar').readOnly = true
	}
	var getObjArrayReferUpbar = function(objArray){
		Ext.getCmp('upbanknamereferbar').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('upbankidreferbar').setValue(objArray[(objArray.length)-1].id);
		bankIdVal = objArray[0].id ;
		Ext.getCmp('upzhibanknamereferbar').setValue(objArray[0].text);
		Ext.getCmp('upzhibankidreferbar').setValue(objArray[0].id);
		Ext.getCmp('upzhibanknamereferbar').readOnly = true
	}
}