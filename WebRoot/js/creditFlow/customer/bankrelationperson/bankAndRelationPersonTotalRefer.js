var anchor = '97%';
var selectBankRelationPersonTotal = function(funName , bankLable){
	var bankIdVal ;
	Ext.onReady(function(){
		var nodeValue ,currentNodeValue, isLeaf ,textValue ,idValue ,parentNode; var objArray = new Array();
		var bankTreeJsonStore = new Ext.data.JsonStore( {
			url : __ctxPath + '/creditFlow/customer/bankRelationPerson/queryBankWinCustomerBankRelationPerson.do',
			totalProperty : 'totalProperty',
			root : 'topics',
			fields : [ {name : 'id'}, {name : 'sexvalue'},{name :'fenbankvalue'},{name : 'name'}, {name : 'number'}, {name : 'duty'}, {name : 'blmtelephone'}, {name : 'marriage'}, {name : 'marriagename'}, {name : 'birthday'}, {name : 'fenbankid'}, {name : 'bankname'},{name : 'email'}, {name : 'address'}, {name : 'blmphone'},{name : 'bankaddress'} ]
		});
		var bankTreeColumnModel = new Ext.grid.ColumnModel(
			[/*
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),
				 {
					header : "联系人姓名",
					width : 100,
					sortable : true,
					dataIndex : 'name'
				},{
					header : "性别",
					width : 100,
					sortable : true,
					dataIndex : 'sex'
				}, {
					header : "办公电话",
					width : 100,
					sortable : true,
					dataIndex : 'blmtelephone'
				},{
					header : "职务",
					width : 80,
					sortable : true,
					dataIndex : 'duty'
				}*/
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),
				{
					header : "银行名称",
					width : 140,
					sortable : true,
					dataIndex : 'bankname'
				},{
					header : "支行名称",
					width : 140,
					sortable : true,
					dataIndex : 'fenbankvalue'
				},{
					header : "联系人姓名",
					width : 100,
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "性别",
					width : 60,
					sortable : true,
					dataIndex : 'sexvalue'
				},{
					header : "职务",
					width : 90,
					sortable : true,
					dataIndex : 'duty'
				},{
					header : "办公电话",
					width : 100,
					sortable : true,
					dataIndex : 'blmtelephone'
				},{
					header : "手机",
					width : 100,
					sortable : true,
					dataIndex : 'blmphone'
				},{
					header : "电子邮箱",
					width : 160,
					sortable : true,
					dataIndex : 'email'
				} ]);
		var bankTreeGrid = new Ext.grid.GridPanel( {
			id : 'bankTreeGrid',
			store : bankTreeJsonStore,
			colModel : bankTreeColumnModel,
			selModel : new Ext.grid.RowSelectionModel(),
			border : false,
			stripeRows : true,
			loadMask : new Ext.LoadMask(Ext.getBody(), {msg : "加载数据中······,请稍后······"}),
			autoWidth : true,
			height : Ext.getBody().getHeight()-100,
			listeners : {
				'rowdblclick' : function(grid, index, e) {
					var selected = grid.getStore().getAt(index) ;
					callbackFun(selected,funName);
					bankWin.close();
				},
				'rowcontextmenu' : function(grid,rowIndex, e) {
					setGridCatalogMenuFun(grid,e);
				}
			}
		});
	var button_add = new Ext.Button({
		text : '新增联系人',
		tooltip : '增加一条银行联系人信息',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			if(typeof(currentNodeValue) != "undefined" && currentNodeValue != null){
				addWin(objArray);
			}else{
				Ext.ux.Toast.msg('状态', '请先选择一条银行记录!');
			}
		}
	});
	var button_update = new Ext.Button({
		text : '修改联系人',
		tooltip : '修改选中的银行联系人信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			updateWin(bankTreeGrid);
		}
	});
	var button_see = new Ext.Button({
		text : '查看联系人',
		tooltip : '查看选中的银行联系人信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			seeWin(bankTreeGrid);
		}
	}); 
	
		var bankWin = new Ext.Window({
			id : 'bankWin',
			title : '银行联系人列表',
			width : 545,
			height : 480,
			modal : true ,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			resizable : true,
			minHeight : 480,
			minWidth : 545,
			items : [
				new Ext.Panel({
					id :'layoutPanel',
					layout : 'column',
					autoHeight : true,
					border : false ,
					autoWidth : true,
					autoScroll : true ,
					tbar:[{text:'查找', icons : 'btn-search',id : 'search'},'-',new Ext.form.TextField({id:'text',width:160}),button_add,button_update,button_see],
					items : [{
						layout : 'fit',
						columnWidth:0.4,
						border : false,
						id : 'leftTreePanel',
						items : [
							new Ext.tree.TreePanel({
								id :'bankTreePanel',
								border : false,
								frame : true ,
								iconCls : 'icon-nav',
								rootVisible : false,
								height : 412,
								autoScroll : true,
								loader : new Ext.tree.TreeLoader( {
				    				dataUrl : __ctxPath + '/creditFlow/customer/bankRelationPerson/bankAndRelationPersonTreeCustomerBankRelationPerson.do?lable='+bankLable
				    			}),
								root : new Ext.tree.AsyncTreeNode( {id : '0',text : '根结点'}),
								listeners : {
									'click' : function(node,e) {
										currentNodeValue = node ;
										idValue = node.id ;
										isLeaf = node.leaf;
										textValue = node.text ;
										bankTreeGrid.setTitle("查看"+node.text+"下的联系人") ;
										bankTreeJsonStore.baseParams.bankIdValue = idValue;
										bankTreeJsonStore.load({
											params : {}
										})
										bankWin.findById('rightTreePanel').doLayout() ;
										bankWin.doLayout();
										var n = node;
										for(i=0;;i++){
											objArray[i] = n;
											n = n.parentNode;
											if(n.id == '0')
												break;
										};
										parentNode = objArray[(objArray.length)-2];
										nodeValue = objArray[0];
									},
									'contextmenu' : function(node,e) {
										setCatalogMenuFun(node,e);
									},
									'dblclick' : function( node, e ){
										
										/*Ext.Ajax.request({
											url : 'isExistBankRelationPerson.action',
											method : 'POST',
											success : function(response, request) {
												obj = Ext.util.JSON.decode(response.responseText);
												if(obj.exsit == false){
													callbackFunTwo(objArray,funName);
													bankWin.close();
												}
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态', '操作失败，请重试');
											},
											params : {
												bankId : node.id
											}
										});*/
										
									}
								}
							})
						]
					},{
						layout : 'fit',
						columnWidth:0.6,
						border : false,
						id : 'rightTreePanel',
						items :[bankTreeGrid]
					}]
				})
			]
		});
		bankWin.show();
	function setCatalogMenuFun(node,e){
		var setCatalogMenu; 
		var nodeId = node.parentNode;
		var objArrayb = new Array();
		var n = node;
		for(i=0;;i++){
			objArrayb[i] = n;
			n = n.parentNode;
			if(n.id == '0')
				break;
		};
		setCatalogMenu = new Ext.menu.Menu({
			items :[{
				iconCls : 'btn-add',
				text :'增加联系人',
				handler : function (){
					addWin(objArrayb);
				}
			},"-",{
				iconCls : 'selectIcon',
				text : '显示所有联系人',
				handler : function (){
					bankTreeGrid.setTitle("查看"+node.text+"下的联系人") ;
					bankTreeJsonStore.baseParams.bankIdValue = node.id;
					bankTreeJsonStore.load({
						params : {}
					})
					bankWin.doLayout();
					/*if(isLeaf == true){}else{
						return ;
					}*/
				}
			}]
		})
		/*if(node.isLeaf()){}else{
			return ;
		}*/
		setCatalogMenu.showAt(e.getPoint());
	}
	function setGridCatalogMenuFun(grid,e){
		e.preventDefault();
		var setCatalogMenu = new Ext.menu.Menu({
			items :[{
				iconCls : 'updateIcon',
				text :'修改信息',
				handler : function (){
					updateWin(grid);
				}
			},"-",{
				iconCls : 'seeIcon',
				text : '查看详细信息',
				handler : function (){
					seeWin(grid);
				}
			}]
		})
		setCatalogMenu.showAt(e.getPoint());
	}
	function addWin(objArray){
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
								name : 'bankRelationPerson.name'
								//allowBlank : false,
							    //blankText : '必填信息',
							    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
								//regexText : '只能输入中文'
							},{
								id : 'addbanknamereferbar',
								xtype : 'textfield',
								fieldLabel : '所在银行',
								//emptyText:'请选择您所在银行',
								//allowBlank : false,
								name : 'bankname',
								value : objArray[(objArray.length)-2].text,
								readOnly : true
							},{
								xtype : 'textfield',
								fieldLabel : '电话号码',
								name : 'bankRelationPerson.blmtelephone'
							},{
								xtype : 'textfield',
								fieldLabel : '手机号码',
								name : 'bankRelationPerson.blmphone'
							},{
								xtype : "dickeycombo",
					nodeKey :'8',
								fieldLabel : '婚姻状况',
								hiddenName : 'bankRelationPerson.marriage',
								//mode : 'romote',
								width : 80 ,
								editable : false,
							    emptyText:'请选择婚姻状况',
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
							    emptyText:'请选性别',
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
								anchor:'100%',
								allowBlank : false,
								value : objArray[0].text
							},{
								xtype : 'textfield',
								fieldLabel : '职务',
								name : 'bankRelationPerson.duty',
								regex : /^[\u4e00-\u9fa5]{1,10}$/,
								regexText : '只能输入中文'
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
						name : 'bankRelationPerson.bankid',
						value : objArray[(objArray.length)-2].id
					},{
						id : 'addzhibankidreferbar',
						xtype : 'hidden',
						name : 'bankRelationPerson.fenbankid',
						value : objArray[0].id
					}], 
					buttons : [{
						id : 'submit',
						text : '保存',
						iconCls : 'submitIcon',
						formBind : true,
						handler : function() {
							addBankRelationPersonWinRefer.findById('addBankRelationPersonPanel').getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form ,action) {
									Ext.ux.Toast.msg('状态', '添加成功!');
											//bankTreeJsonStore.reload();
											//bankTreeJsonStore.baseParams.bankIdValue = idValue;
											bankTreeJsonStore.load({
												params : {bankIdValue : bankIdVal}
											});
											addBankRelationPersonWinRefer.destroy();
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
												//allowBlank : false,
											    //blankText : '必填信息',
											    //regex : /^[\u4e00-\u9fa5]{1,10}$/,
												//regexText : '只能输入中文',
												value : bankRelationPersonData.name
											},{
												id : 'upbanknamereferbar',
												xtype : 'textfield',
												fieldLabel : '所在银行',
												emptyText:'请选择您所在银行',
												//allowBlank : false,
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
											    emptyText:'请选择婚姻状况',
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
												nodeKey : 'sex_key',
												fieldLabel : "性别",
												hiddenName : 'bankRelationPerson.sex',
												
												value : bankRelationPersonData.sex,
												valueNotFoundText : bankRelationPersonData.sexvalue
											},{
												id : 'upzhibanknamereferbar',
												xtype : 'trigger',
												triggerClass :'x-form-search-trigger',
												fieldLabel : '银行支行',
												onTriggerClick : function(){
													selectDictionary('bank',getObjArrayReferUpbar);
												},
												editable : false,
												anchor:'100%',
												allowBlank : false ,
												value : bankRelationPersonData.fenbankvalue
											},{
												xtype : 'textfield',
												fieldLabel : '职务',
												name : 'bankRelationPerson.duty',
												regex : /^[\u4e00-\u9fa5]{1,10}$/,
												regexText : '只能输入中文',
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
										id : 'upzhibankidreferbar',
										xtype : 'hidden',
										name : 'bankRelationPerson.id',
										value : bankRelationPersonData.id
									},{
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
															bankTreeJsonStore.load({
																params : {bankIdValue : bankIdVal}
															})
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
										},{
											xtype : 'textfield',
											fieldLabel : '籍贯',
											regex : /^[\u4e00-\u9fa5]{1,10}$/,
											regexText : '只能输入中文',
											value : bankRelationPersonData.nativeplace,
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
	}
		Ext.getCmp('search').on('click',function(b , e){/*
			var text = Ext.get('text').dom.value;
			var tree = bankWin.findById('bankTreePanel');
			//var searchTreeLoader = new Ext.tree.TreeLoader( {
			//	 dataUrl : 'bankAndRelationPersonTreeAction.action?lable=no'
			//});
			//tree.loader = searchTreeLoader ;
			var root = tree.getRootNode();
			//searchTreeLoader.baseParams = {text : text};
			//searchTreeLoader.load(root);
			Ext.tree.TreeFilter.filter('text',root);
			tree.expandAll();
			bankWin.doLayout();
		*/});
		
		
	var callbackFun = function(selected,funName){
		bankRelationPersonJsonObj = {
			id : selected.get('id'),
			name : selected.get('name'),
			duty : selected.get('duty'),
			email : selected.get('email'),
			
			nodeText  : nodeValue.text,
			nodeId : nodeValue.id ,
			parentNodeText: parentNode.text,
			parentNodeId : parentNode.id,
			objArray : objArray,
			
			bankFullName : parentNode.text +'-'+nodeValue.text,
			
			blmtelephone : selected.get('blmtelephone'),
			blmphone : selected.get('blmphone')
		}
		funName(bankRelationPersonJsonObj);
	}
	
	var callbackFunTwo = function(arrayObj,funName){
		bankRelationPersonJsonObj = {
			arrayList : arrayObj
		}
		funName(bankRelationPersonJsonObj);
	}
	});
	
	var getObjArrayReferbar = function(objArray){
		Ext.getCmp('addbanknamereferbar').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('addbankidreferbar').setValue(objArray[(objArray.length)-1].id);
		bankIdVal = objArray[0].id ;
		alert(bankIdVal);
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