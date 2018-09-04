var autoHight = Ext.getBody().getHeight();
var autoWidth = Ext.getBody().getWidth();
var start = 0 ;
var pageSize = 15 ;
var root = 'topics' ;
var totalProperty = 'totalProperty' ;
var accountBankTreePanel;
var jStore_cautionMoneyProj;

var selectid;
var selectleaf;
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var accountBankTreeLoad = new Ext.tree.TreeLoader({
		dataUrl : __ctxPath + '/creditFlow/guarantee/guaranteefinance/getAccountBankTreeGlAccountBank.do'
	})
	
	var chooseServiceTypeValue;
	var gPanel_cautionMoneyProj;
     function addbank(){
   				var addAccountBankWin = new Ext.Window({
				id : 'addAccountBankWin',
				layout : 'fit',
				title : '总行保证金信息',
				//width : 500,
				width : (screen.width-180)*0.4,
				height : 300,
				iconCls : 'newIcon',
				minimizable : true,
				modal : true,
				items : [new Ext.form.FormPanel({
					id : 'addAccountBankForm',
					labelAlign : 'right',
					buttonAlign : 'center',
				//	url : 'addAccountBank.action',
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
						id : 'addAccountBankColumn',
						labelWidth : 100,
						defaults : {anchor : '97%'},
						items :[{
								name : 'glAccountBank.id',
								xtype : 'hidden',
								value : null
							},{
							id: 'bankName',
							xtype : 'combo',
							fieldLabel : '银行名称',
							hiddenName : 'glAccountBank.bankParentId',
							emptyText : '<----请选择---->',
							width : 75,
							allowBlank : false,
							blankText : '必填信息',
							mode : 'remote',
							store : new Ext.data.JsonStore({
								//url : "/zhiwei/credit/system/getByParentId1CsDicAreaDynam.do",
								url : __ctxPath+'/system/getByParentId1DicAreaDynam.do',
								autoLoad :true,
								root : 'topics',
								fields : [{
									name : 'id'
								}, {
									name : 'parentId'
								},/* {
									name : 'sortorder'
								}, */{
									name : 'text'
								}],
								baseParams :{parentId : 89}
							}),
							displayField : 'text',
							valueField : 'id',
							triggerAction : 'all'
							
						},{
							id : 'authorizationMoney',
							xtype : 'numberfield',
							fieldLabel : '总额度(万元)',
							name : 'glAccountBank.authorizationMoney',
							maxLength : 23,
							maxLengthText : '最大输入长度23',
							regex 	   : /^\d+(\.\d+)?$/,
							regexText  : '只能输入正整数或小数点!',
							readOnly :true,
							blankText : '必填信息',
							value :0
						},{
							id : 'authorizationMone',
							xtype : 'numberfield',
							fieldLabel : '可用额度(万元)',
							name : 'glAccountBank.surplusMoney',
							maxLength : 23,
							maxLengthText : '最大输入长度23',
							regex 	   : /^\d+(\.\d+)?$/,
							regexText  : '只能输入正整数或小数点!',
							readOnly :true,
							blankText : '必填信息',
							value :0
						},{
							id : 'serviceTypeBank',
							xtype : 'textfield',
							fieldLabel : '适用的业务种类',
							name : 'glAccountBank.serviceTypeBank',
							maxLength : 100,
							maxLengthText : '最大输入长度100',
							allowBlank : false,
							blankText : '必填信息'
						},{
							xtype : 'textarea',
							fieldLabel : '备&nbsp;&nbsp;注 ',
							name : 'glAccountBank.remark',
							maxLength : 255
						}]
					}],
					tbar : [{
						text : '保存',
						formBind : true,
						iconCls : 'saveIcon',
						handler : function() {
							var bankName =Ext.get('bankName').dom.value;
							var authorizationMoney =Ext.get('authorizationMoney').dom.value;
							var serviceTypeBank =Ext.get('serviceTypeBank').dom.value;
							//alert("银行名称="+bankName+"   授信额度="+authorizationMoney+"   业务种类="+serviceTypeBank);
							if(bankName == "<----请选择---->" || authorizationMoney == "" || serviceTypeBank == ""){
								Ext.Msg.alert('状态','请填写数据完整后再提交!');
							}else{
								addAccountBankWin.findById('addAccountBankForm').getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountBank.do',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										objSubmit = Ext.util.JSON.decode(action.response.responseText);
										if(objSubmit.exsit == false){
											Ext.Msg.alert('状态' ,"重复添加");
											
										}else{
									          Ext.Msg.alert('状态', "添加成功",
											function(btn, text) {
												accountBankTreePanel.getRootNode().reload();
												accountBankTreePanel.expandAll() ;
												addAccountBankWin.destroy();
											});
												
											//		Ext.Msg.alert('状态' ,objSubmit.msg);
											
										}
									},
									failure : function(form ,action) {
									//	objSubmit = Ext.util.JSON.decode(action.response.responseText);
									//	Ext.Msg.alert('状态', objSubmit.msg);
									}
								})
							}
						}
					}, {
						text : '取消',
						iconCls : 'cancelIcon',
						handler : function() {
							addAccountBankWin.destroy();
						}
					}]
				})],
				listeners : {
//					'beforeclose' : function(){
//						if(addAccountBankWin.findById('addAccountBankForm').getForm().isDirty()){
//							Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
//								if(btn=='yes'){
//									addAccountBankWin.findById('addAccountBankForm').getTopToolbar().items.itemAt(0).handler.call() ;
//								}else{
//									addAccountBankWin.findById('addAccountBankForm').getForm().reset() ;
//									addAccountBankWin.close() ;
//								}
//							}) ;
//							return false ;
//						}
//					}
				}
			}).show();
   };
   function updatebank(id,leaf){
   
			if(id == '0') {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id=id;
				Ext.Ajax.request({
					url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountBank.do',
					method : 'POST',
					success : function(response, request){
						objAccount = Ext.util.JSON.decode(response.responseText);
						var updateAccountBankWin = new Ext.Window({
							id : 'updateAccountBankWin',
							layout : 'fit',
							title : '编辑总行保证金信息',
							iconCls : 'editorIcon',
							width : (screen.width-180)*0.4,
							height : 300,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'updateAccountBankForm',
									labelAlign : 'right',
									buttonAlign : 'center',
						//			url : 'updateAccountBank.action',
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
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textfield',
											fieldLabel : '银行名称',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccount.data.text
										},{
											id : 'authorizationMoneyUpdate',
											xtype : 'numberfield',
											fieldLabel : '总额度.万元',
											name : 'glAccountBank.authorizationMoney',
											allowBlank : false,
											blankText : '必填信息',
											maxLength : 23,
											maxLengthText : '最大输入长度23',
											regex 	   : /^\d+(\.\d+)?$/,
											regexText  : '只能输入正整数或小数点!',
											value : objAccount.data.authorizationMoney,
											readOnly :true
										},{
											id : 'authorizationMoneUpdate',
											xtype : 'numberfield',
											fieldLabel : '可用额度(万元)',
											name : 'glAccountBank.surplusMoney',
											maxLength : 23,
											maxLengthText : '最大输入长度23',
											regex 	   : /^\d+(\.\d+)?$/,
											regexText  : '只能输入正整数或小数点!',
											allowBlank : false,
											blankText : '必填信息',
											value : objAccount.data.surplusMoney,
											readOnly :true
										},{
											xtype : 'textfield',
											fieldLabel : '适用的业务种类',
											name : 'glAccountBank.serviceTypeBank',
											allowBlank : false,
											blankText : '必填信息',
											maxLength : 100,
											maxLengthText : '最大输入长度100',
											value : objAccount.data.serviceTypeBank
										}
//										,{
//											xtype : 'textfield',
//											fieldLabel : '创建时间',
//											readOnly : true,
//											cls : 'readOnlyClass',
//											value : objAccount.data.createDate
//										}
										,{
											xtype : 'textarea',
											fieldLabel : '备&nbsp;&nbsp;注 ',
											name : 'glAccountBank.remark',
											maxLength : 255,
											value : objAccount.data.remark
										},{
											xtype : 'hidden',
											name : 'glAccountBank.id',
											value : objAccount.data.id
										}]
									}],
									tbar : [{
										text : '保存',
										formBind : true,
										iconCls : 'saveIcon',
										handler : function() {
											var authorizationMoneyUpdate =Ext.get('authorizationMoneyUpdate').dom.value;
											if(authorizationMoneyUpdate == ""){
												Ext.Msg.alert('状态','请填写数据完整后再提交!');
											}else{
												updateAccountBankWin.findById('updateAccountBankForm').getForm().submit({
													method : 'POST',
													waitTitle : '连接',
													url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountBank.do',
													waitMsg : '消息发送中...',
													success : function(form ,action) {
														objt = Ext.util.JSON.decode(action.response.responseText);
														Ext.Msg.alert('状态', "修改成功",
																function(btn, text) {
																	accountBankTreePanel.getRootNode().reload();
																	accountBankTreePanel.expandAll() ;
																	updateAccountBankWin.destroy();
																});
													},
													failure : function(form ,action) {
														objt = Ext.util.JSON.decode(action.response.responseText);
														Ext.Msg.alert('状态', objt.msg);
													}
												});
											}
										}
									}, {

										text : '取消',
										iconCls : 'cancelIcon',
										handler : function() {
											updateAccountBankWin.destroy();
										}
									}]
								})
							],
							listeners : {
//								'beforeclose' : function(){
//									if(updateAccountBankWin.findById('updateAccountBankForm').getForm().isDirty()){
//										Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
//											if(btn=='yes'){
//												updateAccountBankWin.findById('updateAccountBankForm').getTopToolbar().items.itemAt(0).handler.call() ;
//											}else{
//												updateAccountBankWin.findById('updateAccountBankForm').getForm().reset() ;
//												updateAccountBankWin.close() ;
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
						glAccountBankId : id
					}
				})
			}
   
   }
   function deletebank(){
   	
   	var id = Ext.getCmp('selectid').value;
			var leaf = Ext.getCmp('selectleaf').value;
			if(id == '0') {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				Ext.MessageBox.confirm('确认删除', '该记录在附表可能同时存在相应记录,您确认要一并删除???', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
								url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/deleteGlAccountBank.do',
							method : 'POST',
							success : function(response, request) {
								objDelete = Ext.util.JSON.decode(response.responseText);
									Ext.Msg.alert('状态', '删除成功',
										function(btn, text) {
											accountBankTreePanel.getRootNode().reload();
											accountBankTreePanel.expandAll() ;
											jStore_manageCautionAccount.reload();
										});
							},
							failure : function(result, action) {
								var msg = Ext.decode(action.response.responseText);
								Ext.Msg.alert('状态',"删除失败");
							},
							params: { glAccountBankId: id }
						});
					}
				});
			}
   	
   };
   function seebank(id,leaf){
			if(id == '0') {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				Ext.Ajax.request({
					url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountBank.do',
					method : 'POST',
					success : function(response, request){
						objAccountSee = Ext.util.JSON.decode(response.responseText);
						var seeAccountBankWin = new Ext.Window({
							id : 'seeAccountBankWin',
							layout : 'fit',
							title : '查看总行保证金信息',
							iconCls : 'lookIcon',
							width : (screen.width-180)*0.4,
							height : 300,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'seeAccountBankForm',
									labelAlign : 'right',
									buttonAlign : 'center',
			//						url : 'updateAccountBank.action',
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
										labelWidth : 100,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '银行名称',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.text
										},{
											fieldLabel : '总额度.万元',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.authorizationMoney
										},{
											fieldLabel : '可用额度.万元',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.surplusMoney
										},{
											fieldLabel : '适用的业务种类',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.serviceTypeBank
										}
//										,{
//											fieldLabel : '创建时间',
//											readOnly : true,
//											cls : 'readOnlyClass',
//											value : objAccountSee.data.createDate
//										}
										,{
											xtype : 'textarea',
											fieldLabel : '备&nbsp;&nbsp;注 ',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.remark
										}]
									}]/*,

									tbar : [{
										text : '取消',
										iconCls : 'cancelIcon',
										handler : function() {
											seeAccountBankWin.destroy();
										}
									}]*/
								})
							]
						}).show();
					},
					failure : function(response) {
						Ext.Msg.alert('状态', '操作失败，请重试');
					},
					params : {
						glAccountBankId : id
					}
				})
			}
   	
   };
	//新增授信银行
	var add_button_acc = new Ext.Button({
		text : '添加支行证金账户',
		tooltip : '添加保证金账户信息',
		iconCls : 'addIcon',
		handler : function() {
			
			var id = Ext.getCmp('selectid').value;
			var leaf = Ext.getCmp('selectleaf').value;
			var bankParentId =Ext.getCmp('selectbankParentId').value;
			if(leaf==false){
				addCautionAccount(bankParentId,id);
			}else{
			
					Ext.Msg.alert('状态', '请先选择总行!');
			}
              
		}
	});
	var add_button_bank = new Ext.Button({
		text : '添加总行保证金',
		tooltip : '添加保证金账户信息',
		iconCls : 'addIcon',
		handler : function() {
			addbank();
		}
	});
	//编辑授信银行——按钮调用
	var update_button_acc = new Ext.Button({
		text : '编辑',
		tooltip : '编辑',
		iconCls : 'updateIcon',
		handler : function() {
			var id = Ext.getCmp('selectid').value;
			var leaf = Ext.getCmp('selectleaf').value;
			if(leaf==false){
				updatebank(id,leaf)
			}else{
			updateCautionAccount(id);
			}
		
		
		}
	});
	//删除授信银行——按钮调用
	var delete_button_acc = new Ext.Button({
		text : '删除',
		tooltip : '删除',
		iconCls : 'deleteIcon',
		handler : function() {
				var id = Ext.getCmp('selectid').value;
			var leaf = Ext.getCmp('selectleaf').value;
			var bankParentId =Ext.getCmp('selectbankParentId').value;
			if(leaf==false){
				deletebank(id);
			}else{
			deleteCautionAccount(id);
			}
		}
	});
	//查看授信银行——按钮调用
	var see_button_acc = new Ext.Button({
		text : '查看',
		tooltip : '查看',
		iconCls : 'seeIcon',
		handler : function() {
			var id = Ext.getCmp('selectid').value;
			var leaf = Ext.getCmp('selectleaf').value;
			if(leaf==false){
				seebank(id,leaf)
			}else{
			seeCautionAccount(id,leaf);
			}
		
		}
	});
	
	
	
	
	var setValueToNullWhenZero = function(v){
		if(v == 0){
			return '';
		}else{
			return v;
		}
	}

	

	
	//树列表
	accountBankTreePanel = new Ext.ux.tree.TreeGrid({
		tbar : [add_button_bank,add_button_acc,update_button_acc,see_button_acc,delete_button_acc],
		id : 'accountBankTreePanel' ,
		width: 500 ,
		height : 550 ,
		layout : 'fit',
		border : false,
		loader : accountBankTreeLoad ,
		root : new Ext.tree.AsyncTreeNode({
			text :'根节点',
			id : '0'
		}),
		columns : [
		{
			width : 320,
			header : '银行名称',
			dataIndex : 'text',
			tpl: new Ext.XTemplate('{text:this.formatText}', {
            formatText: function(v) {
	            	if(v.indexOf("(")=="-1"){//后台传的值不包含()
	            		return v;
	            	}else{
	            		var len = v.substring(v.indexOf("("));//获取从'('开始至末位的值
	            		var start = v.substring(0,v.indexOf("("));//获取从0开始至第一次出现'('位置之间的值
	            		return start+'<font color=green>'+len+'</font>';
	            	}
	            }
	        })
		},{
			width : 85,
			header : '总额度',
			align : 'right',
			dataIndex : 'authorizationMoney',
			tpl: new Ext.XTemplate('{authorizationMoney:this.formatType}', {
                formatType: function(v) {
					if(typeof(v) != "undefined"){
						if(typeof(v) != "string"){
							return v+''+'万元';
						}else{
							return '<font color=green>'+v+'</font>'+'万元';
						}
					}else{
						return '';
					}
                }
            })
		},{
			width : 85,
			header : '可用额度',
			dataIndex : 'surplusMoney',
			align : 'right',
			tpl: new Ext.XTemplate('{surplusMoney:this.formatMoney}', {
                formatMoney: function(v) {
                	if(typeof(v) != "undefined"){
						if(typeof(v) != "string"){
							if(v < 0){
								return '<font color=red>'+v+'</font>'+'万元';
							}else if(v >= 0){
								return v+'万元';
							}else{
								return '';
							}
						}else{
							return '<font color=green>'+v+'</font>'+'万元';
						}
					}else{
						return '';
					}
					/*if(v < 0){
						return '<font color=red>'+v+'&nbsp;&nbsp;&nbsp;&nbsp;</font>';
					}else if(v >= 0){
						return v+'&nbsp;&nbsp;&nbsp;&nbsp;';
					}else{
						return '';
					}*/
                }
            })
		},{
			width : 0,
			header : '',
			dataIndex : 'bankParentId',
			hidden : true
		}],
		listeners : {
			click : function(node){
			//'dblclick' : function(node){
				Ext.getCmp('selectid').value = node.id;
				Ext.getCmp('selectleaf').value = node.leaf;
					Ext.getCmp('selectbankParentId').value = node.attributes.bankParentId;
					
				jStore_manageCautionAccount.removeAll();
				var nodeId;
				if(node.leaf == false){
					var nodeRootId = node.id;
					jStore_manageCautionAccount.baseParams.accountBankId = nodeRootId;
					jStore_manageCautionAccount.baseParams.nodeId = 0;
					gPanel_manageCautionAccount.setTitle(node.text+"(保证金账户记录)");
					
					jStore_manageCautionAccount.load({
						params : {
							start : start,
							limit : pageSize
						}
					});
				}else if(node.leaf == true){
					var bankWholeName;
					var accountName;
					nodeId = node.id;
					Ext.Ajax.request({
				//		url : 'ajaxGetBankWholeName.action',
						url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
						method : 'POST',
						success : function(response, request) {
							objAccountBank = Ext.util.JSON.decode(response.responseText);
							accountBankCautionMoney = objAccountBank.data;
							if(objAccountBank.success==true){
								bankWholeName = accountBankCautionMoney.bankBranchName;
								accountName = accountBankCautionMoney.accountname;
								gPanel_manageCautionAccount.setTitle(bankWholeName+"-"+accountName+"(保证金账户记录)");
						
								
								jStore_manageCautionAccount.baseParams.nodeId = nodeId;
								jStore_manageCautionAccount.baseParams.accountBankId = "";
								jStore_manageCautionAccount.load({
									params : {
										start : start,
										limit : pageSize
									}
								});
							}else{
								Ext.Msg.alert('状态', objAccountBank.msg);
							}
						},
						failure : function(response) {
							Ext.Msg.alert('状态', '操作失败，请重试');
						},
						params : {
							glAccountBankCautionmoneyId : nodeId
						}
					});
				}
			},
			contextmenu : function(node,e) {
//				Ext.getCmp('selectid').value = node.id;
//				Ext.getCmp('selectleaf').value = node.leaf;
			//	setPopMenuFunction(node,e);
			}
		}
	});
	accountBankTreePanel.expandAll() ;
	var accountBankPanel = new Ext.Panel({
		id : 'accountBankPanel',
		layout : 'column',
		title : '银行保证金管理',
		autoHeight : true,
		autoWidth : true,
		autoScroll : true,
		border : false,
		loadMask : myMask,
		items : [{
			layout : 'fit',
			columnWidth : 1,
			items : [accountBankTreePanel]
		},{
			id : 'selectid',
			xtype : 'hidden',
			name : 'id',
			value : '0'
		},{
			id : 'selectleaf',
			xtype : 'hidden',
			name : 'leaf'
		},{
			id : 'selectbankParentId',
			xtype : 'hidden',
			name : 'bankParentId'
		}]
	});
	
	
	
	var accountBankManagePanel = new Ext.Panel({
		id :'dic_Panel',
		layout : 'column',
		autoHeight : true,
		autoWidth : true,
		autoScroll : true,
		items : [{
			layout : 'fit',
			columnWidth:0.42,
			items : [accountBankPanel]
		},{
			layout : 'fit',
			//height : Ext.getBody().getViewSize().height - 5,
			columnWidth:0.002
		},{
			layout : 'fit',
			columnWidth:0.578,
			items : [gPanel_manageCautionAccount]
		}]
	});	
	
	var accountBankViewport = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			items : [accountBankManagePanel]
		}]
	});
	
	
	
	/*var accountBankViewport = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			items : [accountBankPanel]
			//items : [account_bank_view_panel]
		}]
	});*/
	
	
})
//新增保证金账户信息——右键事件调用
function addCautionAccount(bankParentId,id){
	//var selected = gPanel_cautionMoneyProj.getSelectionModel().getSelected();
	//var bankParentId = selected.get('bankParentId');
	var addCautionAccountWin = new Ext.Window({
		id : 'addCautionAccountWin',
		layout : 'fit',
		title : '添加保证金账户',
		width : (screen.width-180)*0.5,
		height : 350,
		iconCls : 'newIcon',
		minimizable : true,
		modal : true,
		items : [new Ext.form.FormPanel({
			border : false,
			id : 'addCautionAccountForm',
			labelAlign : 'right',
			buttonAlign : 'center',
	//		url : 'addCautionAccount.action',
		//	bodyStyle : 'padding:10px 25px 25px',
			bodyStyle : 'padding:10px 2px 25px',
		    layout : 'column',
			frame : true,
			waitMsgTarget : true,
			monitorValid : true,
			autoWidth : true,
			autoHight : true ,
			items :[{
				columnWidth : 1,
				layout : 'form',
			//	id : 'addCautionAccountColumn',
				labelWidth : 100,
				defaults : {anchor : '97%'},
				items :[{
								name : 'glAccountBankCautionmoney.id',
								xtype : 'hidden',
								value : null
					},{
					id: 'bankBranchName',
					xtype : 'trigger',
            		triggerClass :'x-form-search-trigger',
            		allowBlank : false,
            		fieldLabel : '账户开户银行 ',
            		onTriggerClick : function(){
						selectBranchBank(bankParentId,getBranchBankName);
					}
					/*xtype : 'textfield',
					fieldLabel : '账户开户银行 ',
					allowBlank : false,
					blankText : '必填信息',
					cls : 'bankIcon',
					readOnly : true,
					//cls : 'readOnlyClass',
					listeners : {
						'focus' : function(){
							selectBranchBank(node.attributes.bankParentId,getBranchBankName);
						}
					}*/
				},{
					id: 'bankBranchId',
					xtype : 'hidden',
					name : 'glAccountBankCautionmoney.bankBranchId'
				}]},{
				
				columnWidth : .5,
				layout : 'form',
				labelWidth : 100,
				defaults : {anchor : '100%'},
				items :[{
					id : 'accountIdname',
					xtype : 'textfield',
					fieldLabel : '账户名称',
					readOnly : true
				}]},{
				
				columnWidth : .02,
				layout : 'form',
				labelWidth : 2,
				defaults : {anchor : '97%'},
				items :[{
					fieldLabel : "<span style='margin-left:1px'>-</span> ",
									labelSeparator : '',
									anchor : "100%"
				}]},{
				
				columnWidth : .48,
				layout : 'form',
				labelWidth : 3,
				defaults : {anchor : '95%'},
				items :[{
					id : 'accountId',
					xtype : 'textfield',
					allowBlank : false,
					blankText : '必填信息',
					maxLength : 40,
					maxLengthText : '最大输入长度40',
					name : 'glAccountBankCautionmoney.accountname'
				}]},
				{  
				columnWidth : 1,
				layout : 'form',
				labelWidth : 100,
				defaults : {anchor : '97%'},
				items :[{
					id : 'accountName',
					xtype : 'textfield',
					fieldLabel : '银行地址',
					allowBlank : false,
					blankText : '必填信息',
					maxLength : 255,
					maxLengthText : '最大输入长度255',
					name : 'glAccountBankCautionmoney.bankAddress'
				},{
					xtype : 'numberfield',
					fieldLabel : '总金额(万元)',
					allowBlank : false,
					blankText : '必填信息',
					maxLength : 100,
					maxLengthText : '最大输入长度100',
					name : 'glAccountBankCautionmoney.authorizationMoney',
					id :'glAccountBankCautionmoney_authorizationMoney_add'
				
				},{
					xtype : 'numberfield',
					fieldLabel : '可用金额(万元)',
					allowBlank : false,
					blankText : '必填信息',
					maxLength : 100,
					maxLengthText : '最大输入长度100',
					name : 'glAccountBankCautionmoney.surplusMoney',
					id :'glAccountBankCautionmoney_surplusMoney_add'
					
				},{
					xtype : 'textfield',
					fieldLabel : '适用的业务种类',
					name : 'glAccountBankCautionmoney.serviceTypeAccount',
					allowBlank : false,
					blankText : '必填信息',
					maxLength : 100,
					maxLengthText : '最大输入长度100'
				},{
					xtype : 'textarea',
					fieldLabel : '备&nbsp;&nbsp;注 ',
					name : 'glAccountBankCautionmoney.remark',
					maxLength : 255
				},{
					id: 'nodeid',
					xtype : 'hidden',
					name : 'glAccountBankCautionmoney.parentId',
					value : id
				}]
			}],
			tbar : [{
				text : '保存',
				formBind : true,
				iconCls : 'saveIcon',
				handler : function() {
					var accountId =Ext.get('accountId').dom.value;
					var accountName =Ext.get('accountName').dom.value;
					var bankBranchName =Ext.get('bankBranchName').dom.value;
					var bankBranchId =Ext.get('bankBranchId').dom.value;
					
					var authorizationMoney =Ext.get('glAccountBankCautionmoney_authorizationMoney_add').dom.value;
					var surplusMoney =Ext.get('glAccountBankCautionmoney_surplusMoney_add').dom.value;
					if(bankBranchName == "" || bankBranchId == "" || accountId == "" || accountName == ""){
						Ext.Msg.alert('状态','请填写数据完整后再提交!');
					}if(parseFloat(authorizationMoney) <parseFloat(surplusMoney)){
						Ext.Msg.alert('状态','可用金额不能大于总金额!');
					}else{
						addCautionAccountWin.findById('addCautionAccountForm').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountBankCautionmoney.do',
							success : function() {
								Ext.Msg.alert('状态', '添加<保证金账户>成功!',
									function(btn, text) {
										if (btn == 'ok') {
											accountBankTreePanel.getRootNode().reload();
											accountBankTreePanel.expandAll() ;
											addCautionAccountWin.destroy();
										}
									});
							},
							failure : function(form, action) {
								Ext.Msg.alert('状态', '操作失败，请重试!');
							}
						})
					}
				}
			}, {
				text : '取消',
				iconCls : 'cancelIcon',
				handler : function() {
					addCautionAccountWin.destroy();
				}
			}]
		})],
		listeners : {
//			'beforeclose' : function(){
//				if(addCautionAccountWin.findById('addCautionAccountForm').getForm().isDirty()){
//					Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
//						if(btn=='yes'){
//							addCautionAccountWin.findById('addCautionAccountForm').getTopToolbar().items.itemAt(0).handler.call() ;
//						}else{
//							addCautionAccountWin.findById('addCautionAccountForm').getForm().reset() ;
//							addCautionAccountWin.close() ;
//						}
//					}) ;
//					return false ;
//				}
//			}
		}
	});
	addCautionAccountWin.show();
}
//修改保证金账户信息——右键事件调用
function updateCautionAccount(id){

	if(id == '0') {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
  }else{
	  Ext.Ajax.request({
	   url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
		method : 'POST',
		success : function(response, request){
			obj = Ext.util.JSON.decode(response.responseText);
			var updateCautionAccountWin = new Ext.Window({
				id : 'updateCautionAccountWin',
				layout : 'fit',
				title : '编辑保证金账户',
				iconCls : 'editorIcon',
				width : (screen.width-180)*0.5,
				height : 330,
				minimizable : true,
				modal : true,
				items :[
					new Ext.form.FormPanel({
						id : 'updateCautionAccountForm',
						labelAlign : 'right',
						buttonAlign : 'center',
			//			url : 'updateCautionAccount.action',
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
							labelWidth : 100,
							defaults : {anchor : '100%'},
							items :[{
								id: 'bankBranchNameUpdate',
								xtype : 'trigger',
			            		triggerClass :'x-form-search-trigger',
			            		allowBlank : false,
			            		fieldLabel : '账户开户银行 ',
			            		value : obj.data.bankBranchName,
			            		//readOnly : true,
			            		onTriggerClick : function(){
									selectBranchBank(obj.data.bankParentId,getBranchBankNameUpdate);
								}
								/*xtype : 'textfield',
								fieldLabel : '账户开户银行 ',
								allowBlank : false,
								blankText : '必填信息',
								cls : 'bankIcon',
								readOnly : true,
								//cls : 'readOnlyClass',
								value : obj.data.bankBranchName,
								listeners : {
									'focus' : function(){
										selectBranchBank(obj.data.bankParentId,getBranchBankNameUpdate);
									}
								}*/
							},{
								id: 'bankBranchIdUpdate',
								xtype : 'hidden',
								name : 'glAccountBankCautionmoney.bankBranchId',
								value : obj.data.bankBranchId
							}]},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '账户名称',
								readOnly :true,
								id :'accountIdnameupde',
								value : obj.data.text
							}]},{
							
							columnWidth : .02,
							layout : 'form',
							labelWidth : 2,
							defaults : {anchor : '97%'},
							items :[{
								fieldLabel : "<span style='margin-left:1px'>-</span> ",
												labelSeparator : '',
												anchor : "100%"
							}]},{
							
							columnWidth : .48,
							layout : 'form',
							labelWidth : 3,
							defaults : {anchor : '100%'},
							items :[{
								id : 'accountIdUpdate',
								xtype : 'textfield',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 40,
								maxLengthText : '最大输入长度40',
								name : 'glAccountBankCautionmoney.accountname',
								value : obj.data.accountname
							}]},{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 100,
								defaults : {anchor : '100%'},
								items :[{
		            	 		id : 'accountNameUpdate',
								xtype : 'textfield',
								fieldLabel : '银行地址',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 255,
								maxLengthText : '最大输入长度255',
								name : 'glAccountBankCautionmoney.bankAddress',
								value : obj.data.bankAddress
							},{
								xtype : 'numberfield',
								fieldLabel : '总金额(万元)',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								name : 'glAccountBankCautionmoney.authorizationMoney',
								id:'glAccountBankCautionmoney_authorizationMoney_update',
								value : obj.data.authorizationMoney
							},{
								xtype : 'numberfield',
								fieldLabel : '可用金额(万元)',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								name : 'glAccountBankCautionmoney.surplusMoney',
								id :'glAccountBankCautionmoney_surplusMoney_update',
								value : obj.data.surplusMoney
							},{
								xtype : 'textfield',
								fieldLabel : '适用的业务种类',
								name : 'glAccountBankCautionmoney.serviceTypeAccount',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								value : obj.data.serviceTypeAccount
							},{
								xtype : 'textarea',
								fieldLabel : '备&nbsp;&nbsp;注 ',
								name : 'glAccountBankCautionmoney.remark',
								maxLength : 255,
								value : obj.data.remark
							},{
								name : 'glAccountBankCautionmoney.id',
				            	value : obj.data.id,
				            	xtype : 'hidden'
							}]
						}],
						tbar : [{
							text : '保存',
							formBind : true,
							iconCls : 'saveIcon',
							handler : function() {
								var bankBranchNameUpdate =Ext.get('bankBranchNameUpdate').dom.value;
								var bankBranchIdUpdate =Ext.get('bankBranchIdUpdate').dom.value;
								var accountIdUpdate =Ext.get('accountIdUpdate').dom.value;
								var accountNameUpdate =Ext.get('accountNameUpdate').dom.value;
								var authorizationMoney =Ext.get('glAccountBankCautionmoney_authorizationMoney_update').dom.value;
				            	var surplusMoney =Ext.get('glAccountBankCautionmoney_surplusMoney_update').dom.value;
								if(bankBranchNameUpdate == "" || bankBranchIdUpdate == "" ||accountIdUpdate == "" || accountNameUpdate == ""){
									Ext.Msg.alert('状态','请填写数据完整后再提交!');
								}
							if(parseFloat(authorizationMoney) < parseFloat(surplusMoney)){
								Ext.Msg.alert('状态','可用金额不能大于总金额!');
							}else{
									updateCautionAccountWin.findById('updateCautionAccountForm').getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountBankCautionmoney.do',
										success : function(form ,action) {
											objt = Ext.util.JSON.decode(action.response.responseText);
											Ext.Msg.alert('状态', "修改成功",
													function(btn, text) {
														accountBankTreePanel.getRootNode().reload();
														accountBankTreePanel.expandAll() ;
														updateCautionAccountWin.destroy();
													});
										},
										failure : function(form ,action) {
											objt = Ext.util.JSON.decode(action.response.responseText);
											Ext.Msg.alert('状态', objt.msg);
										}
									});
								}
							}

						}, {
							text : '取消',
							iconCls : 'cancelIcon',
							handler : function() {
								updateCautionAccountWin.destroy();
							}
						}]
					})
				],
				listeners : {
//					'beforeclose' : function(){
//						if(updateCautionAccountWin.findById('updateCautionAccountForm').getForm().isDirty()){
//							Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
//								if(btn=='yes'){
//									updateCautionAccountWin.findById('updateCautionAccountForm').getTopToolbar().items.itemAt(0).handler.call() ;
//								}else{
//									updateCautionAccountWin.findById('updateCautionAccountForm').getForm().reset() ;
//									updateCautionAccountWin.close() ;
//								}
//							}) ;
//							return false ;
//						}
//					}
				}
			});
			updateCautionAccountWin.show();
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
//删除保证金账户信息——右键事件调用
function deleteCautionAccount(id){
	if(id == '0') {
	Ext.MessageBox.alert('状态', '请选择一条记录!');
	}else{
	  Ext.MessageBox.confirm('确认删除', '是否确认删除选中的记录???', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/deteleGlAccountBankCautionmoney.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert('状态', "删除成功",
						function(btn, text) {
							accountBankTreePanel.getRootNode().reload();
							accountBankTreePanel.expandAll() ;
						});
				},
				failure : function(result, action) {
					var msg = Ext.decode(action.response.responseText);
					Ext.Msg.alert('状态',"删除失败");
				},
				params: { glAccountBankCautionmoneyId: id }
			});
		}
	});
	}
}


//查看保证金账户信息
function seeCautionAccount(id,leaf){
	if(id == '0') {
	  Ext.MessageBox.alert('状态', '请选择一条记录!');
   }else{
	
	Ext.Ajax.request({
		 url:__ctxPath + 'creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
		
		method : 'POST',
		success : function(response, request){
			objSee = Ext.util.JSON.decode(response.responseText);
			//alert("冻结金额==="+objSee.data.accountFrozenMoney+"       解冻金额==="+objSee.data.accountUnfreezeMoney);
			var seeCautionAccountWin = new Ext.Window({
				id : 'seeCautionAccountWin',
				layout : 'fit',
				title : '查看保证金账户',
				iconCls : 'lookIcon',
				//width : 500,
				width : (screen.width-180)*0.5,
				height : 300,
				minimizable : true,
				modal : true,
				items :[
					new Ext.form.FormPanel({
						labelAlign : 'right',
						buttonAlign : 'center',
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
							labelWidth : 100,
							defaults : {xtype : 'textfield',anchor : '100%'},
							items :[{
								fieldLabel : '账户开户银行 ',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankBranchName
							}]},{
				
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '账户名称',
								readOnly : true,
								value : objSee.data.text
								}]},{
								
								columnWidth : .02,
								layout : 'form',
								labelWidth : 2,
								defaults : {anchor : '97%'},
								items :[{
									fieldLabel : "<span style='margin-left:1px'>-</span> ",
													labelSeparator : '',
													anchor : "100%"
								}]},{
								
						columnWidth : .48,
						layout : 'form',
						labelWidth : 3,
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							allowBlank : false,
							readOnly :true,
							value : objSee.data.accountname
						}]},{
					
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						defaults : {anchor : '100%'},
						items :[{
								fieldLabel : '银行地址',
								xtype : 'textfield',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankAddress
							},{
								fieldLabel : '总金额(万元)',
								xtype : 'textfield',
								readOnly : true,
								cls : 'readOnlyClass',
								value : obj.data.authorizationMoney
							},{
								fieldLabel : '可用金额.万元',
								xtype : 'textfield',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.surplusMoney
							},{
								xtype : 'textfield',
								fieldLabel : '适用的业务种类',
								readOnly : true,
								value : objSee.data.serviceTypeAccount
							}
//							,{
//								fieldLabel : '创建时间',
//								readOnly : true,
//								cls : 'readOnlyClass',
//								value : objSee.data.createDate
//							}
							,{
								xtype : 'textarea',
								fieldLabel : '备&nbsp;&nbsp;注 ',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.remark
							}]
						}]/*,
						tbar : [{
							text : '取消',
							iconCls : 'cancelIcon',
							handler : function() {
								seeCautionAccountWin.destroy();
							}
						}]*/
					})
				]
			});
			seeCautionAccountWin.show();
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
var getCautionAccountName = function(obj){//保证金账户
	Ext.getCmp('cautionAccountId_Add').setValue(obj.id);
	Ext.getCmp('cautionAccountId_Choose').setValue(obj.text);
	Ext.getCmp('surplusMoney_Add').setValue(obj.surplusMoney);
}

var getCautionAccountNameUpdate = function(obj){//保证金账户--修改用
	Ext.getCmp('accountId_update').setValue(obj.id);
	Ext.getCmp('cautionAccountId_update').setValue(obj.text);
}

//选择企业贷项目带出的信息
var getEnterpriseProjectName = function(objEnter){
	Ext.getCmp('projectId_Add').setValue(objEnter.projid);
	Ext.getCmp('enterpriseProjectName_Add').setValue(objEnter.projname);
	/*Ext.getCmp('prepayBankMargin_Add').setValue(objEnter.prepaybankmargin);
	Ext.getCmp('bankMarginMoney_Add').setValue(objEnter.bankmargin);
	Ext.getCmp('bailPayoutDate_Add').setValue(objEnter.bailpayoutdate);
	Ext.getCmp('cautionmoneyPayRemarks_Add').setValue(objEnter.cautionmoneyPayRemarks);*/
}

//选择车贷项目带出的信息
var getCarProjectName = function(objCar){
	Ext.getCmp('projectId_Add').setValue(objCar.id);
	Ext.getCmp('carProjectName_Add').setValue(objCar.carProjName);
	//Ext.getCmp('prepayBankMargin_Add').setValue(objCar.bankYhbzjpzhm);
	Ext.getCmp('bankMarginMoney_Add').setValue(objCar.bankYhbzjkkje);
	Ext.getCmp('cautionCode_Add').setValue(objCar.bankYhbzjpzhm);
	Ext.getCmp('bailPayoutDate_Add').setValue(objCar.freezeDate);
	Ext.getCmp('cautionmoneyPayRemarks_Add').setValue(objCar.bankYhbzjsm);
}

var getBranchBankName = function(objArray){
	Ext.getCmp('bankBranchName').setValue(objArray.remarks);
	Ext.getCmp('bankBranchId').setValue(objArray.id);
	Ext.getCmp('accountIdname').setValue(objArray.text);
};

var getBranchBankNameUpdate = function(objArray){
	Ext.getCmp('bankBranchNameUpdate').setValue(objArray.remarks);
	Ext.getCmp('bankBranchIdUpdate').setValue(objArray.id);
	Ext.getCmp('accountIdnameupde').setValue(objArray.text);
};

