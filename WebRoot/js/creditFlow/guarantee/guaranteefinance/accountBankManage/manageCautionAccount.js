var start = 0 ;
var pageSize = 15 ;
var root = 'result' ;
var totalProperty = 'totalCounts' ;
//var manageCautionAccount = function(cautionAccountData){
	//alert("支行名称："+cautionAccountData.bankBranchName+"      保证金账户名称："+cautionAccountData.text+"    账户id："+cautionAccountData.id);
	
	 jStore_manageCautionAccount = new Ext.data.JsonStore( {
		id : 'jStore_manageCautionAccount',
		url : __ctxPath + '/creditFlow/guarantee/guaranteefinance/listGlAccountRecord.do',
		totalProperty : totalProperty,
		root : root ,
		fields : [ {
			name : 'glAccountRecordId'
		},{
			name : 'cautionAccountId'
		}, {
			name : 'capitalTypeId'
		}, {
			name : 'oprateMoney'
		}, {
			name:  'oprateDate'
		},{
			name : 'handlePerson'
		}, {
			name : 'capitalTypeValue'
		}, {
			name : 'bankBranchName'
		}, {
			name : 'bankname'
		}, {
			name : 'serviceTypeAccount'
		}, {
			name : 'remark'
		}, {
			name : 'accountId'
		}]
	});
	jStore_manageCautionAccount.load({
		params : {
	//		cautionAccountId : cautionAccountData.id,
			start : start,
			limit : pageSize
		}
	});
	
	
var cModel_manageCautionAccount = new Ext.grid.ColumnModel(
			[
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 35
					}),
//					{
//						header : "保证金账户号码",
//						width : 120,
//						sortable : true,
//						dataIndex : 'accountId'
//					},
//						{
//						header : "保证金账户名称",
//						width : 120,
//						sortable : true,
//						dataIndex : 'accountName'
//					}, 
//						{
//						header : "银行名称",
//						width : 120,
//						sortable : true,
//						dataIndex : 'bankBranchName'
//					},
						{
						header : "银行名称",
						width : 250,
						sortable : true,
						dataIndex : 'bankname'
					},
					{
						header : "资金类型",
						width : 80,
						sortable : true,
						dataIndex : 'capitalTypeValue'
					},{
						header : "金额",
						width : 80,
						sortable : true,
						align : 'right',
						dataIndex : 'oprateMoney',
						renderer :function(v){
						return v+'万元';
						}
					},{
						header : "经办日期",
						width : 80,
						sortable : true,
						dataIndex : 'oprateDate'
					}, {
						header : "经办人",
						width : 80,
						sortable : true,
						dataIndex : 'handlePerson'
					}, /*{
						header : "适用的业务种类",
						width : 100,
						sortable : true,
						dataIndex : 'serviceTypeAccount'
					}, */{
						header : "备注",
						width : 130,
						sortable : true,
						dataIndex : 'remark'
					}, {
						header : "",
						width : 0,
						sortable : true,
						dataIndex : 'glAccountRecordId',
						hidden:true
					} ]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : pageSize,
		store : jStore_manageCautionAccount,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	//增加记录
	var button_add = new Ext.Button({
		text : '增加保证金记录',
		tooltip : '增加一条新记录',
		iconCls : 'addIcon',
		//disabled : bool,
		scope : this,
		handler : function() {
				var id=Ext.getCmp('selectid').value;
				var leaf =Ext.getCmp('selectleaf').value;
				var bankParentId=Ext.getCmp('selectbankParentId').value;
				if(leaf==false || leaf==null || leaf==""){
					Ext.Msg.alert('状态' ,"请先选择要的支行账户");
				}else{
			    Ext.Ajax.request({
			   url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
				method : 'POST',
				success : function(response, request){
					objAccountDataAdd = Ext.util.JSON.decode(response.responseText);
					//alert(objAccountDataAdd.data.bankBranchName+' -- '+objAccountDataAdd.data.text);
					var addManageCautionAccountWin = new Ext.Window({
						id : 'addManageCautionAccountWin',
						layout : 'fit',
						title : '新增保证金账户记录',
						width : (screen.width-180)*0.55,
						height : 280,
						iconCls : 'newIcon',
						minimizable : true,
						modal : true,
						items : [new Ext.form.FormPanel({
							id : 'addManageCautionAccountForm',
							labelAlign : 'right',
							buttonAlign : 'center',
					//		url : 'addManageCautionAccount.action',
							bodyStyle : 'padding:10px 25px 25px',
						    layout : 'column',
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							autoWidth : true,
							autoHight : true ,
							items :[{
								columnWidth : 1,
								layout : 'form',
								id : 'addManageCautionAccountColumn',
								labelWidth : 90,
								defaults : {anchor : '97%'},
								items :[{
									id : 'cautionAccountNameAdd',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户名称',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : objAccountDataAdd.data.bankBranchName+"-"+objAccountDataAdd.data.accountname
				            		//name : 'enterpriseProject.cautionAccountId',
				            		/*onTriggerClick : function(){
										selectAccountBankName(getCautionAccountName);
									}*/
								},{
									id : 'totalMoney',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户余额.万元',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : objAccountDataAdd.data.surplusMoney
								},{
									/*id: 'capitalTypeA',
									xtype : 'csRemoteCombo',
									fieldLabel : '资金类型',
									hiddenName : 'accountRecord.capitalType',
									emptyText : '<----请选择---->',
									width : 75,
									dicId : capitalTypeDic*/
									id: 'capitalTypeA',
									xtype : 'combo',
									fieldLabel : '资金类型',
									hiddenName : 'glAccountRecord.capitalType',
									name : 'glAccountRecord.capitalType',
									emptyText : '<----请选择---->',
									width : 75,
									allowBlank : false,
									blankText : '必填信息',
									mode : 'local',
							        store : new Ext.data.SimpleStore({
											        fields : ["value", "id"],
										            data : [["存入", "1"],
													     	["支出", "2"],
													     	["冻结", "3"],
													     	["解结", "4"]]
									              	}),
									displayField : 'value',
									valueField : 'id',
									triggerAction : 'all'
								},{
									id : 'oprateMoney',
									xtype : 'numberfield',
									fieldLabel : '金额.万元',
									name : 'glAccountRecord.oprateMoney',
									maxLength : 23,
									maxLengthText : '最大输入长度23',
									regex 	   : /^\d+(\.\d+)?$/,
									regexText  : '只能输入正整数或小数点!',
									allowBlank : false,
									blankText : '必填信息'
								},{
									xtype : 'textarea',
									fieldLabel : '备&nbsp;&nbsp;注 ',
									name : 'glAccountRecord.remark',
									maxLength : 255
								},{
									id : 'cautionAccountIdAdd',
					            	name : 'glAccountRecord.cautionAccountId',
					            	xtype : 'hidden',
					            	value : id
								}]
							}],
							tbar : [{
								text : '保存',
								formBind : true,
								iconCls : 'saveIcon',
								handler : function() {
									var capitalType = Ext.getCmp('capitalTypeA').getValue();
									var oprateMoney = Ext.getCmp('oprateMoney').getValue();
									var totalMoney = Ext.getCmp('totalMoney').getValue();
									//alert("资金类型=="+capitalType+"   金额=="+oprateMoney+"   可用额度=="+totalMoney);
									if(capitalType == "<----请选择---->" || oprateMoney == ""){
										Ext.Msg.alert('状态','请填写数据完整后再提交!');
									}if((capitalType == 3 || capitalType == 2) && (oprateMoney>totalMoney)){
										Ext.Msg.alert('状态','支取,或冻结金额不能大于该账户可用金额，请重新输入!');
									}else{
										addManageCautionAccountWin.findById('addManageCautionAccountForm').getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											 url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/saveGlAccountRecord.do',
											success : function(form ,action) {
												objSubmit = Ext.util.JSON.decode(action.response.responseText);
												if(objSubmit.exsit == false){
													Ext.Msg.alert('状态' ,objSubmit.msg);
												}else{
													Ext.Msg.alert('状态', "添加成功",
													function(btn, text) {
														accountBankTreePanel.getRootNode().reload();
														accountBankTreePanel.expandAll() ;
														jStore_manageCautionAccount.reload();
														addManageCautionAccountWin.destroy();
													});
												}
											},
											failure : function(form ,action) {
												objSubmit = Ext.util.JSON.decode(action.response.responseText);
												Ext.Msg.alert('状态', objSubmit.msg);
											}
										})
									}
								}
							}]
						})],
						listeners : {
//							'beforeclose' : function(){
//								if(addManageCautionAccountWin.findById('addManageCautionAccountForm').getForm().isDirty()){
//									Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
//										if(btn=='yes'){
//											addManageCautionAccountWin.findById('addManageCautionAccountForm').getTopToolbar().items.itemAt(0).handler.call() ;
//										}else{
//											addManageCautionAccountWin.findById('addManageCautionAccountForm').getForm().reset() ;
//											addManageCautionAccountWin.destroy() ;
//										}
//									}) ;
//									return false ;
//								}
//							}
						}
					}).show();
				},
				failure : function(response) {
					Ext.Msg.alert('状态', '操作失败，请重试');
				},
				params : {
					glAccountBankCautionmoneyId : id
				}
			})
			}
	}
	});
	
	
	var button_update = new Ext.Button({
		text : '编辑保证金记录',
		tooltip : '编辑选 中记录信息',
		iconCls : 'updateIcon',
		//disabled : bool,
		scope : this,
		handler : function() {
			var selected = gPanel_manageCautionAccount.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('glAccountRecordId');
				Ext.Ajax.request({
				   url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountRecord.do',
					method : 'POST',
					success : function(response, request){
						objAccountData = Ext.util.JSON.decode(response.responseText);
						var sumsurplusMoney;
						if(objAccountData.data.capitalType==2 || objAccountData.data.capitalType==3){
						   sumsurplusMoney=objAccountData.data.surplusMoney+objAccountData.data.oprateMoney;
						}else{
							sumsurplusMoney=objAccountData.data.surplusMoney;
						}
						var updateManageCautionAccountWin = new Ext.Window({
							id : 'updateManageCautionAccountWin',
							layout : 'fit',
							title : '编辑保证金账户信息',
							iconCls : 'editorIcon',
							width : (screen.width-180)*0.55,
							height : 320,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'updateManageCautionAccountForm',
									labelAlign : 'right',
									buttonAlign : 'center',
								//	url : 'updateManageCautionAccount.action',
									bodyStyle : 'padding:10px 25px 25px',
								    layout : 'column',
									frame : true,
									waitMsgTarget : true,
									monitorValid : true,
									autoWidth : true,
									autoHight : true ,
									items :[{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 90,
										defaults : {anchor : '98%'},
										items :[{
									id : 'cautionAccountNameAddupade',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户名称',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : objAccountData.data.bankBranchName
				            	
								},{
									id : 'totalMoneyUpdate',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户余额.万元',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : sumsurplusMoney
								},{
									/*id: 'capitalTypeA',
									xtype : 'csRemoteCombo',
									fieldLabel : '资金类型',
									hiddenName : 'accountRecord.capitalType',
									emptyText : '<----请选择---->',
									width : 75,
									dicId : capitalTypeDic*/
									id: 'capitalTypeUpdate',
									xtype : 'combo',
									fieldLabel : '资金类型',
									hiddenName : 'glAccountRecord.capitalType',
									name:'glAccountRecord.capitalType',
									width : 75,
									allowBlank : false,
									blankText : '必填信息',
									mode : 'local',
							        store : new Ext.data.SimpleStore({
											        fields : ["value", "id"],
										            data : [["存入","1"],
													     	["支出","2"],
													     	["冻结","3"],
													     	["解结","4"]]
									              	}),
									displayField : 'value',
									valueField : 'id',
									triggerAction : 'all',
									value : objAccountData.data.capitalType
									
								},{ 
											id : 'oprateMoneyUpdate',
											xtype : 'numberfield',
											fieldLabel : '金额.万元',
											name : 'glAccountRecord.oprateMoney',
											maxLength : 23,
											maxLengthText : '最大输入长度23',
											regex 	   : /^\d+(\.\d+)?$/,
											regexText  : '只能输入正整数或小数点!',
											allowBlank : false,
											blankText : '必填信息',
											value : objAccountData.data.oprateMoney
										},{
											xtype : 'textfield',
											fieldLabel : '经办时间',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountData.data.oprateDate
										},{
											xtype : 'textfield',
											fieldLabel : '经办人',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountData.data.handlePerson
										},{
											xtype : 'textarea',
											fieldLabel : '备&nbsp;&nbsp;注 ',
											name : 'accountRecord.remark',
											maxLength : 255,
											value : objAccountData.data.remark
										},/*{
											xtype : 'hidden',
											name : 'accountRecord.id',
											value : objAccountData.data.id
										},*/{
											xtype : 'hidden',
							        		name : 'glAccountRecord.glAccountRecordId',
							        		value : objAccountData.data.glAccountRecordId
										}]
									}],
									tbar : [{
										text : '保存',
										formBind : true,
										iconCls : 'saveIcon',
										handler : function() {
											var capitalTypeUpdate = Ext.getCmp('capitalTypeUpdate').getValue();
											var oprateMoneyUpdate = Ext.getCmp('oprateMoneyUpdate').getValue();
											var totalMoneyUpdate = Ext.getCmp('totalMoneyUpdate').getValue();
											var totalM = parseFloat(totalMoneyUpdate)+parseFloat(objAccountData.data.oprateMoney);
											//alert("余额+取出金额=="+parseFloat(totalM)+typeof(totalM)+"   当前输入的金额=="+parseFloat(oprateMoneyUpdate)+typeof(oprateMoneyUpdate)+"   可用余额=="+totalMoneyUpdate+typeof(totalMoneyUpdate));
//											if(capitalTypeUpdate == "" || oprateMoneyUpdate == ""){
//												Ext.Msg.alert('状态','请填写数据完整后再提交!');
//											}else 
											if((capitalTypeUpdate == 2 || capitalTypeUpdate == 3) && (parseFloat(oprateMoneyUpdate)>parseFloat(totalM))){
												Ext.Msg.alert('状态','支取,冻结金额不能大于该账户可用金额，请重新输入!');
											}else{
												updateManageCautionAccountWin.findById('updateManageCautionAccountForm').getForm().submit({
													method : 'POST',
													waitTitle : '连接',
													waitMsg : '消息发送中...',
													 url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/saveGlAccountRecord.do',
													success : function(form ,action) {
														objt = Ext.util.JSON.decode(action.response.responseText);
														Ext.Msg.alert('状态', objt.msg,
																function(btn, text) {
														//			accountBankTreePanel.getRootNode().reload();
															//		accountBankTreePanel.expandAll() ;
																	jStore_manageCautionAccount.reload();
																	updateManageCautionAccountWin.destroy();
																});
													},
													failure : function(form ,action) {
														objt = Ext.util.JSON.decode(action.response.responseText);
														Ext.Msg.alert('状态', objt.msg);
													}
												});
											}
										}
									}]
								})
							],
							listeners : {
//								'beforeclose' : function(){
//									if(updateManageCautionAccountWin.findById('updateManageCautionAccountForm').getForm().isDirty()){
//										Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
//											if(btn=='yes'){
//												updateManageCautionAccountWin.findById('updateManageCautionAccountForm').getTopToolbar().items.itemAt(0).handler.call() ;
//											}else{
//												updateManageCautionAccountWin.findById('updateManageCautionAccountForm').getForm().reset() ;
//												updateManageCautionAccountWin.destroy() ;
//											}
//										}) ;
//										return false ;
//									}
//								}
							}
						}).show();
					},
					failure : function(response) {
						Ext.Msg.alert('状态', '操作失败，请重试');
					},
					params : {
						glAccountRecordId : id
					}
				})
			}
		}
	});
	var button_delete = new Ext.Button({
		text : '删除保证金记录',
		tooltip : '删除选中的记录信息',
		//disabled : bool,
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_manageCautionAccount.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('glAccountRecordId');
				Ext.MessageBox.confirm('确认删除', '是否确认删除该条记录？ ', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							 url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/deleteGlAccountRecord.do',
							method : 'POST',
							success : function(response, request) {
								objDeleteData = Ext.util.JSON.decode(response.responseText);
									Ext.Msg.alert('状态', "删除成功",
										function(btn, text) {
											accountBankTreePanel.getRootNode().reload();
											accountBankTreePanel.expandAll() ;
											jStore_manageCautionAccount.reload();
										});
							},
							failure : function(result, action) {
								var msg = Ext.decode(action.response.responseText);
								Ext.Msg.alert('状态',msg);
							},
							params: { glAccountRecordId: id }
							/*url : 'deleteManageCautionAccount.action',
							method : 'POST',
							success : function() {
								jStore_manageCautionAccount.reload();
								Ext.Msg.alert('状态', '删除成功!');
							},
							failure : function(result, action) {
								Ext.Msg.alert('状态','删除失败!');
							},
							params: { 
								id: id
							}*/
						});
					}
				});
			}
		}
	});
	
	var button_see = new Ext.Button({
		text : '查看保证金记录',
		tooltip : '查看选中的记录信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_manageCautionAccount.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('glAccountRecordId');
				Ext.Ajax.request({
					 url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountRecord.do',
					method : 'POST',
					success : function(response, request){
						seeAccountData = Ext.util.JSON.decode(response.responseText);
						var seeManageCautionAccountWin = new Ext.Window({
							id : 'seeManageCautionAccountWin',
							layout : 'fit',
							title : '查看保证金账户信息',
							iconCls : 'lookIcon',
							width : (screen.width-180)*0.55,
							height : 300,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'seeManageCautionAccountForm',
									labelAlign : 'right',
									buttonAlign : 'center',
									//url : 'updateManageCautionAccount.action',
									bodyStyle : 'padding:10px 25px 25px',
								    layout : 'column',
									frame : true,
									waitMsgTarget : true,
									monitorValid : true,
									autoWidth : true,
									autoHight : true ,
									items :[{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '98%'},
										items :[{
											fieldLabel : '账户名称',
											readOnly : true,
											cls : 'readOnlyClass',
									     	value : objAccountData.data.bankBranchName
										},{
											fieldLabel : '资金类型',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.capitalTypeValue
										},{
											fieldLabel : '金额.万元',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.oprateMoney
										},{
											fieldLabel : '创建时间',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.oprateDate
										},{
											fieldLabel : '经办人',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.handlePerson
										},{
											xtype : 'textarea',
											fieldLabel : '备&nbsp;&nbsp;注 ',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.remark
										}]
									}]
								})
							]
						}).show();
					},
					failure : function(response) {
						Ext.Msg.alert('状态', '操作失败，请重试');
					},
					params : {
						glAccountRecordId : id 
					}
				})
			}
		}
	});
	
		
	var incomepay_checkbox =new Ext.form.Checkbox({
	                              boxLabel : '存入支出记录',
									inputValue : true,
									id : "incomepaycheckbox",
									checked : true,
									scope : this,
									handler : function(){
									var incomepaycheckbox=Ext.getCmp("incomepaycheckbox");
										var freezecheckbox=Ext.getCmp("freezecheckbox");
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == true){
										this.related("all");
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == true){
										this.related("freezecheckbox");
										}
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == false){
										this.related("incomepaycheckbox");
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == false){
										this.related("none");
										}
									}
	
	
	})
	var freeze_checkbox =new Ext.form.Checkbox({
	
	                               id :"freezecheckbox",
									boxLabel : '解冻冻结记录',
									inputValue : true,
									scope : this,
									checked : true,
									handler : function() {
										var incomepaycheckbox=Ext.getCmp("incomepaycheckbox");
										var freezecheckbox=Ext.getCmp("freezecheckbox");
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == true){
										this.related("all");
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == true){
										this.related("freezecheckbox");
										}
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == false){
										this.related("incomepaycheckbox");
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == false){
										this.related("none");
										}
										}
	
	})

	
	var gPanel_manageCautionAccount = new Ext.grid.GridPanel( {
		title : '保证金账户记录',
		id : 'gPanel_manageCautionAccount',
		store : jStore_manageCautionAccount,
		height : 500,
		colModel : cModel_manageCautionAccount,
		//autoExpandColumn : 5,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
	//	loadMask : myMask,
		bbar : pagingBar,
		tbar : [button_add,button_see,button_delete,"->",incomepay_checkbox,freeze_checkbox],//,button_update
		listeners : {}
	});
	

var related=function(flag){
jStore_manageCautionAccount.baseParams.flag = flag;
jStore_manageCautionAccount.load({
									params : {
										start : start,
										limit : pageSize
									}
								});

}

