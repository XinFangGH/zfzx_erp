/**
 * 词条管理
 * @class SystemWordView
 * @extends Ext.Panel
 */
AccountBankManage=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		AccountBankManage.superclass.constructor.call(this,{
			id:'AccountBankManage',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'银行保证金账户管理',
			iconCls:'menu-accountbankmanage',
			items:[
				this.accountBankPanel,
				this.gPanel_manageCautionAccount
			]
		});
	},
	initUIComponents:function(){
		this.isNotController = false;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isNotController = true;
		}		
		
		var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var autoHight = Ext.getBody().getHeight();
	var autoWidth = Ext.getBody().getWidth();
	var start = 0 ;
	var pageSize = 15 ;
	var root = 'result' ;
	var totalProperty = 'totalCounts' ;
	
	
	var tbar=new Ext.Toolbar({
			items:[
			{
			text : '添加总行保证金',
			tooltip : '添加保证金账户信息',
			iconCls : 'btn-add',
			scope : this,
			hidden : isGranted('_addzhbzj_q')?false:true,
			handler : function() {
				this.addbank();
			}
			},'-',{
			text : '添加支行保证金账户',
			tooltip : '添加保证金账户信息',
			iconCls : 'addIcon',
			scope : this,
			hidden : isGranted('_addzhbzjzh_q')?false:true,
			handler : function() {
				
				var id = Ext.getCmp('selectid').value;
				var leaf = Ext.getCmp('selectleaf').value;
				var bankParentId =Ext.getCmp('selectbankParentId').value;
				if(leaf==false){
					this.addCautionAccount(bankParentId,id);
				}else{
				
						Ext.Msg.alert('状态', '请先选择总行!');
				}
	              
			}
			
			
			
			},'-',{
				text : '编辑',
				tooltip : '编辑',
				iconCls : 'btn-edit',
				scope : this,
				hidden : isGranted('_editzhbzjzh_q')?false:true,
				handler : function() {
					var id = Ext.getCmp('selectid').value;
					var leaf = Ext.getCmp('selectleaf').value;
					if(leaf==false){
						this.updatebank(id,leaf)
					}else{
					this.updateCautionAccount(id);
					}
		
		
		}
			},'-',{
				text : '删除',
				tooltip : '删除',
				scope : this,
				iconCls : 'btn-del',
				hidden : isGranted('_removezhbzjzh_q')?false:true,
				handler : function() {
						var id = Ext.getCmp('selectid').value;
					var leaf = Ext.getCmp('selectleaf').value;
					var bankParentId =Ext.getCmp('selectbankParentId').value;
					if(leaf==false){
						this.deletebank(id);
					}else{
					this.deleteCautionAccount(id);
				}
		}
			},'-',{
			text : '查看',
			tooltip : '查看',
			iconCls : 'btn-detail',
			scope : this,
			hidden : isGranted('_seezhbzjzh_q')?false:true,
			handler : function() {
				var id = Ext.getCmp('selectid').value;
				var leaf = Ext.getCmp('selectleaf').value;
				if(leaf==false){
					this.seebank(id,leaf)
				}else{
				this.seeCautionAccount(id,leaf);
				}
		
		}
			},'-',
			{
			iconCls : 'btn-refresh',
			scope : this,
			text : '刷新',
			hidden : isGranted('_save_zxpz_'+this.businessType)?false:true,
			handler : this.refresh
							}
			]
		});
		var accountBankTreeLoad = new Ext.tree.TreeLoader({
			dataUrl : __ctxPath + '/creditFlow/guarantee/guaranteefinance/getAccountBankTreeGlAccountBank.do',
			baseParams:{isNotController:this.isNotController}
		})
		this.accountBankTreePanel = new Ext.ux.tree.TreeGrid({
	    tbar:tbar,
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
			scope :this,
			click : function(node){
				var gPanel_manageCautionAccount1=this.gPanel_manageCautionAccount;
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
					gPanel_manageCautionAccount1.setTitle(node.text+"(保证金账户记录)");
					
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
						url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
						method : 'POST',
						success : function(response, request) {
							objAccountBank = Ext.util.JSON.decode(response.responseText);
							accountBankCautionMoney = objAccountBank.data;
							if(objAccountBank.success==true){
								bankWholeName = accountBankCautionMoney.bankBranchName;
								accountName = accountBankCautionMoney.accountname;
								gPanel_manageCautionAccount1.setTitle(bankWholeName+"-"+accountName+"(保证金账户记录)");
						
								
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
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
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
	this.accountBankTreePanel.expandAll() ;
	this.accountBankPanel = new Ext.Panel({
    	region:'west',
	 	layout:'anchor',
	 	collapsible : true,
	 	border : false,
		split : true,
		width : 500,
		id : 'accountBankPanel',
		title : '银行保证金管理',
		loadMask : myMask,
		items : [{
			layout : 'fit',
			columnWidth : 1,
			items : [this.accountBankTreePanel]
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
	
		
		var jStore_manageCautionAccount = new Ext.data.JsonStore( {
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
						{
						header : "银行名称",
						width : 250,
						sortable : true,
						dataIndex : 'bankname',
						renderer : function(v, metadata, record,
				                    rowIndex, columnIndex, store) {
				                    	 metadata.attr = ' ext:qtip="' + v + '"';
                                return v;				                    	 
				          }
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
						dataIndex : 'remark',
						renderer : function(v, metadata, record,
				                    rowIndex, columnIndex, store) {
				                    	 metadata.attr = ' ext:qtip="' + v + '"';
                                return v;				                    	 
				          }
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

	var tbar1=new Ext.Toolbar({
			items:[
			{
			text : '增加',
			tooltip : '增加保证金账户记录',
			iconCls : 'btn-add',
			//disabled : bool,
			scope : this,
			hidden : isGranted('_addbzjzhjl_q')?false:true,
			handler : function() {
				  var accountBankTreePanel1=this.accountBankTreePanel;
				var id=Ext.getCmp('selectid').value;
				var leaf =Ext.getCmp('selectleaf').value;
				var bankParentId=Ext.getCmp('selectbankParentId').value;
				if(leaf==false || leaf==null || leaf==""){
					Ext.Msg.alert('状态' ,"请先选择要的支行账户");
				}else{
			    Ext.Ajax.request({
			   url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
				method : 'POST',
				success : function(response, request){
					objAccountDataAdd = Ext.util.JSON.decode(response.responseText);
					//alert(objAccountDataAdd.data.bankBranchName+' -- '+objAccountDataAdd.data.text);
					var addManageCautionAccountWin = new Ext.Window({
						id : 'addManageCautionAccountWin',
						layout : 'fit',
						title : '新增保证金账户记录',
						width : (screen.width-180)*0.55,
						height : 235,
						border : false,
						modal : true,
						buttonAlign : 'center',
						buttons : [
								{
									text : '保存',
									iconCls : 'btn-save',
									scope : this,
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
											 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountRecord.do',
											success : function(form ,action) {
												objSubmit = Ext.util.JSON.decode(action.response.responseText);
												if(objSubmit.exsit == false){
													Ext.Msg.alert('状态' ,objSubmit.msg);
												}else{
														Ext.ux.Toast.msg('状态','添加成功!');
														accountBankTreePanel1.getRootNode().reload();
														accountBankTreePanel1.expandAll() ;
														jStore_manageCautionAccount.reload();
														addManageCautionAccountWin.destroy();
												}
											},
											failure : function(form ,action) {
												objSubmit = Ext.util.JSON.decode(action.response.responseText);
												Ext.Msg.alert('状态', objSubmit.msg);
											}
										})
									}
									}
								}, {
									text : '取消',
									iconCls : 'btn-cancel',
									scope : this,
									handler : function(){
									
									addManageCautionAccountWin.destroy();
									
									}
								}
					         ],
						items : [new Ext.form.FormPanel({
							id : 'addManageCautionAccountForm',
							labelAlign : 'right',
							buttonAlign : 'center',
					//		url : 'addManageCautionAccount.action',
							bodyStyle : 'padding:10px 25px 25px',
						    layout : 'column',
							frame : true,
							border : false,
							waitMsgTarget : true,
							monitorValid : true,
							autoWidth : true,
							autoHight : true ,
							items :[{
								columnWidth : 1,
								layout : 'form',
								id : 'addManageCautionAccountColumn',
								labelWidth : 65,
								defaults : {anchor : '100%'},
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
								}]},{
								columnWidth : .43,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : '100%'},
								items :[{
									id : 'totalMoney',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户余额',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : objAccountDataAdd.data.surplusMoney
								}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},
									{columnWidth : .5,
									layout : 'form',
									labelWidth : 70,
								    defaults : {anchor : '100%'},
									items :[{
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
								}]},
								{
								   columnWidth : .43,
									layout : 'form',
									labelWidth : 65,
								    defaults : {anchor : '100%'},
									items :[{
									id : 'oprateMoney',
									xtype : 'numberfield',
									fieldLabel : '操作金额',
									name : 'glAccountRecord.oprateMoney',
									fieldClass:'field-align',
									maxLength : 23,
									maxLengthText : '最大输入长度23',
						//			regex 	   : /^\d+(\.\d+)?$/,
									regexText  : '只能输入正整数或小数点!',
									allowBlank : false,
									blankText : '必填信息'
								}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
								   columnWidth : .5,
									layout : 'form',
									labelWidth : 70,
								defaults : {anchor : '100%'},
									items :[{
									id : 'oprateDate',
									xtype : 'datefield',
									fieldLabel : '经办日期 ',
									name : 'glAccountRecord.oprateDate',
									format : 'Y-m-d',
									allowBlank : false,
									blankText : '必填信息'
								}]},{
								columnWidth : 1,
									layout : 'form',
									labelWidth : 65,
							    	defaults : {anchor : '100%'},
									items :[{
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
							}]
						})],
						listeners : {

						}
					}).show();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					glAccountBankCautionmoneyId : id
				}
			})
			}
	}
			},'-',{
			text : '编辑',
			tooltip : '编辑选中记录信息',
			iconCls : 'btn-edit',
			//disabled : bool,
			scope : this,
			hidden : isGranted('_editbzjzhjl_q')?false:true,
			handler : function() {
		 var accountBankTreePanel1=this.accountBankTreePanel;
			var selected = this.gPanel_manageCautionAccount.getSelectionModel().getSelected();
			if (null == selected) {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('glAccountRecordId');
				Ext.Ajax.request({
				   url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountRecord.do',
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
							width : (screen.width-180)*0.55,
							border : false,
							height : 260,
							modal : true,
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
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
													 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountRecord.do',
													success : function(form ,action) {
													//	objt = Ext.util.JSON.decode(action.response.responseText);
														 Ext.ux.Toast.msg('状态',"编辑成功");
																	accountBankTreePanel1.getRootNode().reload();
																	accountBankTreePanel1.expandAll() ;
																	jStore_manageCautionAccount.reload();
																	updateManageCautionAccountWin.destroy();
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
											iconCls : 'btn-cancel',
											scope : this,
											handler : function(){
									
									updateManageCautionAccountWin.destroy();
									
									}
										}
							         ],
							items :[
								new Ext.form.FormPanel({
									id : 'updateManageCautionAccountForm',
									labelAlign : 'right',
									buttonAlign : 'center',
								//	url : 'updateManageCautionAccount.action',
									bodyStyle : 'padding:10px 25px 25px',
								    layout : 'column',
									frame : true,
									border : false,
									waitMsgTarget : true,
									monitorValid : true,
									autoWidth : true,
									autoHight : true ,
									items :[{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 70,
										defaults : {anchor : '98%'},
										items :[{
									id : 'cautionAccountNameAddupade',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户名称',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : objAccountData.data.bankBranchName
				            	
								}]},{
								columnWidth : .43,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : '100%'},
								items :[{
									id : 'totalMoneyUpdate',
				            		xtype : 'textfield',
				            		//allowBlank : false,
				            		fieldLabel : '账户余额',
				            		fieldClass:'field-align',
				            		readOnly : true,
									cls : 'readOnlyClass',
				            		value : sumsurplusMoney
								}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
								columnWidth : .5,
								layout : 'form',
								labelWidth : 90,
								defaults : {anchor : '96.2%'},
								items :[{
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
									
								}]},{ 	
								columnWidth : .43,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : '100%'},
								items :[{
									id : 'oprateMoneyUpdate',
									xtype : 'numberfield',
									fieldLabel : '操作金额',
									fieldClass:'field-align',
									name : 'glAccountRecord.oprateMoney',
									maxLength : 23,
									maxLengthText : '最大输入长度23',
								//	regex 	   : /^\d+(\.\d+)?$/,
									regexText  : '只能输入正整数或小数点!',
									allowBlank : false,
									blankText : '必填信息',
									value : objAccountData.data.oprateMoney
								}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
								columnWidth : .5,
								layout : 'form',
								labelWidth : 90,
								defaults : {anchor : '96.2%'},
								items :[{
											xtype : 'datefield',
											fieldLabel : '经办时间',
											format : 'Y-m-d',
											value : objAccountData.data.oprateDate
										}]},{
							   columnWidth : 1,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : '98%'},
								items :[{
											xtype : 'textfield',
											fieldLabel : '经办人',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountData.data.handlePerson
										}]},{	
								columnWidth : 1,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : '98%'},
								items :[{
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
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						glAccountRecordId : id
					}
				})
			}
		}
			
			
			
			},'-',{
			text : '删除',
		tooltip : '删除选中记录信息',
		//disabled : bool,
		iconCls : 'btn-del',
		scope : this,
		hidden : isGranted('_removebzjzhjl_q')?false:true,
		handler : function() {
			  var accountBankTreePanel1=this.accountBankTreePanel;
			var selected = this.gPanel_manageCautionAccount.getSelectionModel().getSelected();
			if (null == selected) {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('glAccountRecordId');
				Ext.MessageBox.confirm('确认删除', '是否确认删除该条记录？ ', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/deleteGlAccountRecord.do',
							method : 'POST',
							success : function(response, request) {
								objDeleteData = Ext.util.JSON.decode(response.responseText);
									 Ext.ux.Toast.msg('状态', '删除成功!');
											accountBankTreePanel1.getRootNode().reload();
											accountBankTreePanel1.expandAll() ;
											jStore_manageCautionAccount.reload();
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
			},'-',{
			text : '查看',
		tooltip : '查看选中记录信息',
		iconCls : 'btn-detail',
		scope : this,
		hidden : isGranted('_seebzjzhjl_q')?false:true,
		handler : function() {
			  var accountBankTreePanel1=this.accountBankTreePanel;
			var selected = this.gPanel_manageCautionAccount.getSelectionModel().getSelected();
			if (null == selected) {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('glAccountRecordId');
				Ext.Ajax.request({
					 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountRecord.do',
					method : 'POST',
					success : function(response, request){
						seeAccountData = Ext.util.JSON.decode(response.responseText);
						var seeManageCautionAccountWin = new Ext.Window({
							id : 'seeManageCautionAccountWin',
							layout : 'fit',
							title : '查看保证金账户信息',
							border : false,
							width : (screen.width-180)*0.55,
							height : 270,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'seeManageCautionAccountForm',
									labelAlign : 'right',
									buttonAlign : 'center',
									//url : 'updateManageCautionAccount.action',
						  		bodyStyle : 'padding:25px 0px 0px',
								    layout : 'column',
									frame : true,
									border : false,
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
									     	value : seeAccountData.data.bankBranchName
										}]},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '98%'},
										items :[{
											fieldLabel : '资金类型',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.capitalTypeValue
										}]},{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '操作金额',
											fieldClass:'field-align',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.oprateMoney
										}]},{
										 columnWidth : .07, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :30,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : "100%"
										
									 }]},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '98%'},
										items :[{
											fieldLabel : '经办日期',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.oprateDate
										}]},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '96%'},
										items :[{
											fieldLabel : '经办人',
											readOnly : true,
											cls : 'readOnlyClass',
											value : seeAccountData.data.handlePerson
										}]},{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '98%'},
										items :[{
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
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						glAccountRecordId : id 
					}
				})
			}
		}
			},"->",{
				     boxLabel : '存入支出记录',
					inputValue : true,
					xtype : 'checkbox',
					id : "incomepaycheckbox",
					checked : true,
					scope : this,
					handler : function() {
						var incomepaycheckbox=Ext.getCmp("incomepaycheckbox");
										var freezecheckbox=Ext.getCmp("freezecheckbox");
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == true){
										this.related("all",jStore_manageCautionAccount);
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == true){
										this.related("freezecheckbox",jStore_manageCautionAccount);
										}
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == false){
										this.related("incomepaycheckbox",jStore_manageCautionAccount);
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == false){
										this.related("none",jStore_manageCautionAccount);
										}
					}
					}
					,{
					 id :"freezecheckbox",
					boxLabel : '解冻冻结记录',
					inputValue : true,
					xtype : 'checkbox',
					checked : true,
					scope : this,
					handler : function() {
							var incomepaycheckbox=Ext.getCmp("incomepaycheckbox");
										var freezecheckbox=Ext.getCmp("freezecheckbox");
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == true){
										this.related("all",jStore_manageCautionAccount);
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == true){
										this.related("freezecheckbox",jStore_manageCautionAccount);
										}
										if(incomepaycheckbox.getValue() == true && freezecheckbox.getValue() == false){
										this.related("incomepaycheckbox",jStore_manageCautionAccount);
										}
										if(incomepaycheckbox.getValue() == false && freezecheckbox.getValue() == false){
										this.related("none",jStore_manageCautionAccount);
										}
					}
					}
			]
		});	
		
		this.gPanel_manageCautionAccount = new Ext.grid.GridPanel( {
		region:'center',
		title : '保证金账户记录',
		id : 'gPanel_manageCautionAccount',
		store : jStore_manageCautionAccount,
		height : 500,
		colModel : cModel_manageCautionAccount,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		bbar : pagingBar,
		tbar : tbar1//,button_update
	});
	},//end of initUIComponents
	addbank : function(){
		var accountBankTreePanel1=this.accountBankTreePanel;
   				var addAccountBankWin = new Ext.Window({
				id : 'addAccountBankWin',
				layout : 'fit',
				title : '总行保证金信息',
				//width : 500,
				width : (screen.width-180)*0.47,
				height : 238,
				border : false,
				modal : true,
				buttonAlign : 'center',
				buttons : [
						{
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						handler : function() {
								var bankName =Ext.get('bankName').dom.value;
								var authorizationMoney =Ext.get('authorizationMoney').dom.value;
							//	var serviceTypeBank =Ext.get('serviceTypeBank').dom.value;
								//alert("银行名称="+bankName+"   授信额度="+authorizationMoney+"   业务种类="+serviceTypeBank);
								if(bankName == "<----请选择---->" || authorizationMoney == "" ){
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
												 Ext.ux.Toast.msg('状态', '添加成功!');
													accountBankTreePanel1.getRootNode().reload();
													accountBankTreePanel1.expandAll() ;
													addAccountBankWin.destroy();
													
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
					}
//					, {
//						text : '重置',
//						iconCls : 'btn-reset',
//						scope : this,
//						handler : function() {
//							addAccountBankWin.findById('addAccountBankForm').getForm().reset();
//							}
//						
//					}
					, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : function() {
								addAccountBankWin.destroy();
							}
					}
		         ],
				items : [new Ext.form.FormPanel({
					id : 'addAccountBankForm',
					labelAlign : 'right',
					buttonAlign : 'center',
				//	url : 'addAccountBank.action',
					bodyStyle : 'padding:10px 25px 25px',
				    layout : 'column',
					frame : true,
					border : false,
					waitMsgTarget : true,
					monitorValid : true,
					autoWidth : true,
					autoHight : true ,
					items :[{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 85,
						defaults : {anchor : '100%'},
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
							
						}]},{	
							columnWidth : .4,
							layout : 'form',
							labelWidth : 85,
							defaults : {anchor : '100%'},
							items :[{
							id : 'authorizationMoney',
							xtype : 'numberfield',
							fieldLabel : '总额度',
							fieldClass:'field-align',
							name : 'glAccountBank.authorizationMoney',
							maxLength : 23,
							maxLengthText : '最大输入长度23',
							regex 	   : /^\d+(\.\d+)?$/,
							regexText  : '只能输入正整数或小数点!',
							readOnly :true,
							blankText : '必填信息',
							value :0
						}]},{
								 columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								}]
							 },{
							columnWidth : .4,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : '100%'},
							items :[{
							id : 'authorizationMone',
							xtype : 'numberfield',
							fieldLabel : '可用额度',
							fieldClass:'field-align',
							name : 'glAccountBank.surplusMoney',
							maxLength : 23,
							maxLengthText : '最大输入长度23',
							regex 	   : /^\d+(\.\d+)?$/,
							regexText  : '只能输入正整数或小数点!',
							readOnly :true,
							blankText : '必填信息',
							value :0
						}]},{
								 columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								}]
							 },{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 85,
							defaults : {anchor : '100%'},
							items :[{
							id : 'serviceTypeBank',
							xtype : 'textfield',
							fieldLabel : '适用业务种类',
							name : 'glAccountBank.serviceTypeBank',
							maxLength : 100,
							maxLengthText : '最大输入长度100'
						},{
							xtype : 'textarea',
							fieldLabel : '备&nbsp;&nbsp;注 ',
							labelWidth : 85,
							name : 'glAccountBank.remark',
							maxLength : 255
						}]
					}]
						
					
				})],
				listeners : {

				}
			}).show();
   },
  updatebank: function (id,leaf){
          var accountBankTreePanel1=this.accountBankTreePanel;
			if(id == '0') {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id=id;
				Ext.Ajax.request({
					url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBank.do',
					method : 'POST',
					success : function(response, request){
						objAccount = Ext.util.JSON.decode(response.responseText);
						var updateAccountBankWin = new Ext.Window({
							id : 'updateAccountBankWin',
							layout : 'fit',
							title : '编辑总行保证金信息',
							width : (screen.width-180)*0.47,
							height : 250,
							minimizable : true,
							modal : true,
							border : false,
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
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
																	 Ext.ux.Toast.msg('状态', '修改成功!');
																	accountBankTreePanel1.getRootNode().reload();
																	accountBankTreePanel1.expandAll() ;
																	updateAccountBankWin.destroy();
													},
													failure : function(form ,action) {
														objt = Ext.util.JSON.decode(action.response.responseText);
														Ext.Msg.alert('状态', objt.msg);
													}
												});
											}
										}
									
										}
//										, {
//											text : '重置',
//											iconCls : 'btn-reset',
//											scope : this,
//											handler : function() {
//											updateAccountBankWin.findById('updateAccountBankForm').getForm().reset();
//												
//											}
//										}
										, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : function() {
											updateAccountBankWin.destroy();
										}
										}
							         ],
							items :[
								new Ext.form.FormPanel({
									id : 'updateAccountBankForm',
									labelAlign : 'right',
									buttonAlign : 'center',
									border : false,
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
										}]},{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											id : 'authorizationMoneyUpdate',
											xtype : 'numberfield',
											fieldLabel : '总额度',
											fieldClass:'field-align',
											name : 'glAccountBank.authorizationMoney',
											allowBlank : false,
											blankText : '必填信息',
											maxLength : 23,
											maxLengthText : '最大输入长度23',
											regex 	   : /^\d+(\.\d+)?$/,
											regexText  : '只能输入正整数或小数点!',
											value : objAccount.data.authorizationMoney,
											readOnly :true
										}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											}]
										 },{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											id : 'authorizationMoneUpdate',
											xtype : 'numberfield',
											fieldLabel : '可用额度',
											fieldClass:'field-align',
											name : 'glAccountBank.surplusMoney',
											maxLength : 23,
											maxLengthText : '最大输入长度23',
											regex 	   : /^\d+(\.\d+)?$/,
											regexText  : '只能输入正整数或小数点!',
											allowBlank : false,
											blankText : '必填信息',
											value : objAccount.data.surplusMoney,
											readOnly :true
										}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											}]
										 },{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textfield',
											fieldLabel : '适用的业务种类',
											name : 'glAccountBank.serviceTypeBank',
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
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						glAccountBankId : id
					}
				})
			}
   
   },
  deletebank : function(){
   	var accountBankTreePanel1=this.accountBankTreePanel;
   	var gPanel_manageCautionAccount1=this.gPanel_manageCautionAccount;
   	var id = Ext.getCmp('selectid').value;
			var leaf = Ext.getCmp('selectleaf').value;
			if(id == '0') {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				Ext.MessageBox.confirm('确认删除', '该记录在附表可能同时存在相应记录,您确认要一并删除???', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
								url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/deleteGlAccountBank.do',
							method : 'POST',
							success : function(response, request) {
								objDelete = Ext.util.JSON.decode(response.responseText);
									 Ext.ux.Toast.msg('状态', '删除成功!');
											accountBankTreePanel1.getRootNode().reload();
											accountBankTreePanel1.expandAll() ;
										//	jStore_manageCautionAccount.reload();
											gPanel_manageCautionAccount1.getStore().reload();
							},
							failure : function(result, action) {
								var msg = Ext.decode(action.response.responseText);
								 Ext.ux.Toast.msg('状态', '删除失败!');
							},
							params: { glAccountBankId: id }
						});
					}
				});
			}
   	
   },
  seebank : function(id,leaf){
			if(id == '0') {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				Ext.Ajax.request({
					url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBank.do',
					method : 'POST',
					success : function(response, request){
						objAccountSee = Ext.util.JSON.decode(response.responseText);
						var seeAccountBankWin = new Ext.Window({
							id : 'seeAccountBankWin',
							layout : 'fit',
							title : '查看总行保证金信息',
							border : false,
							width : (screen.width-180)*0.47,
							height : 230,
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
									border : false,
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
										}]},{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 100,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '总额度',
											fieldClass:'field-align',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.authorizationMoney
										}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											}]
										 },{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 100,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '可用额度',
											fieldClass:'field-align',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.surplusMoney
										}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											}]
										 },{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 100,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
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
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						glAccountBankId : id
					}
				})
			}
   	
   },
   //新增保证金账户信息——右键事件调用
 addCautionAccount:function (bankParentId,id){
	//var selected = gPanel_cautionMoneyProj.getSelectionModel().getSelected();
	//var bankParentId = selected.get('bankParentId');
 	 	var accountBankTreePanel1=this.accountBankTreePanel;
	var addCautionAccountWin = new Ext.Window({
		id : 'addCautionAccountWin',
		layout : 'fit',
		title : '添加支行保证金账户',
		width : (screen.width-180)*0.5,
		height : 295,
		border : false,
		modal : true,
		buttonAlign : 'center',
		buttons : [
						{
							text : '保存',
							iconCls : 'btn-save',
							scope : this,
							handler : function() {
										var accountId =Ext.get('accountId').dom.value;
									//	var accountName =Ext.get('accountName').dom.value;
										var bankBranchName =Ext.get('bankBranchName').dom.value;
										var bankBranchId =Ext.get('bankBranchId').dom.value;
										
										var authorizationMoney =Ext.get('glAccountBankCautionmoney_authorizationMoney_add').dom.value;
										var surplusMoney =Ext.get('glAccountBankCautionmoney_surplusMoney_add').dom.value;
										if(bankBranchName == "" || bankBranchId == "" || accountId == "" ){
											Ext.Msg.alert('状态','请填写数据完整后再提交!');
										}else if(parseFloat(authorizationMoney) <parseFloat(surplusMoney)){
											Ext.Msg.alert('状态','初始可用金额不能大于初始总金额!');
										}else{
											addCautionAccountWin.findById('addCautionAccountForm').getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountBankCautionmoney.do',
												success : function() {
													Ext.ux.Toast.msg('操作信息','添加成功!');
																accountBankTreePanel1.getRootNode().reload();
																accountBankTreePanel1.expandAll() ;
																addCautionAccountWin.destroy();
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态', '操作失败，请重试');
												}
											})
										}
									}
								
						}, {
							text : '重置',
							iconCls : 'btn-reset',
							scope : this,
							handler : function() {
								addCautionAccountWin.findById('addCautionAccountForm').getForm().reset();
							}
						}, {
							text : '取消',
							iconCls : 'btn-cancel',
							scope : this,
							handler : function() {
								addCautionAccountWin.destroy();
							}
						}
			         ],
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
				labelWidth : 85,
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
            		editable : false,
            		fieldLabel : '开户银行 ',
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
				
				columnWidth : .45,
				layout : 'form',
				labelWidth : 85,
				defaults : {anchor : '100%'},
				items :[{
					id : 'accountIdname',
					xtype : 'textfield',
					fieldLabel : '账户名称',
					readOnly : true
				}]},{
				
				columnWidth : .05,
				layout : 'form',
				labelWidth : 5,
				defaults : {anchor : '97%'},
				items :[{
					fieldLabel : "<span style='margin-left:1px'>-</span> ",
									labelSeparator : '',
									anchor : "100%"
				}]},{
				
				columnWidth : .5,
				layout : 'form',
				labelWidth : 5,
				defaults : {anchor : '94.3%'},
				items :[{
					id : 'accountId',
					xtype : 'textfield',
					allowBlank : false,
					blakText : '必填信息',
					maxLength : 40,
					maxLengthText : '最大输入长度40',
					name : 'glAccountBankCautionmoney.accountname'
				}]},
				{  
				columnWidth : 1,
				layout : 'form',
				labelWidth : 85,
				defaults : {anchor : '97%'},
				items :[{
					id : 'accountName',
					xtype : 'textfield',
					fieldLabel : '银行地址',
					maxLength : 255,
					maxLengthText : '最大输入长度255',
					name : 'glAccountBankCautionmoney.bankAddress'
				}]},{  
				columnWidth : .4,
				layout : 'form',
				labelWidth : 85,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'numberfield',
					fieldLabel : '初始总金额',
					fieldClass:'field-align',
					allowBlank : false,
					blankText : '必填信息',
					fieldClass:'field-align',
					maxLength : 100,
					maxLengthText : '最大输入长度100',
					name : 'glAccountBankCautionmoney.rawauthorizationMoney',
					id :'glAccountBankCautionmoney_authorizationMoney_add',
					value :0
				
				}]},{
					 columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth :30,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>万元</span> ",
						labelSeparator : '',
						anchor : "100%"
					
				 }]},{  
				columnWidth : .4,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'numberfield',
					fieldLabel : '初始可用金额',
					fieldClass:'field-align',
					allowBlank : false,
					fieldClass:'field-align',
					blankText : '必填信息',
					maxLength : 100,
					maxLengthText : '最大输入长度100',
					name : 'glAccountBankCautionmoney.rawsurplusMoney',
					id :'glAccountBankCautionmoney_surplusMoney_add',
					value:0
					
				}]},{
					 columnWidth : .1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth :30,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>万元</span> ",
						labelSeparator : '',
						anchor : "100%"
					
				 }]},
				{
				columnWidth : .45,
				layout : 'form',
				labelWidth : 85,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'datefield',
					fieldLabel : '开户日期',
					name : 'glAccountBankCautionmoney.createDate',
					format:'Y-m-d'
				}]},{
				columnWidth : .55,
				layout : 'form',
				labelWidth : 118,
				defaults : {anchor : '94.1%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : '适用的业务种类',
					name : 'glAccountBankCautionmoney.serviceTypeAccount',
					blankText : '必填信息',
					maxLength : 100,
					maxLengthText : '最大输入长度100'
				}]},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 85,
				defaults : {anchor : '97%'},
				items :[{
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
},
//修改保证金账户信息——右键事件调用
updateCautionAccount :function (id){
 	var accountBankTreePanel1=this.accountBankTreePanel;
	if(id == '0') {
				 Ext.ux.Toast.msg('状态', '请选择一条记录!');
  }else{
	  Ext.Ajax.request({
	   url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
		method : 'POST',
		success : function(response, request){
			obj = Ext.util.JSON.decode(response.responseText);
			var updateCautionAccountWin = new Ext.Window({
				id : 'updateCautionAccountWin',
				layout : 'fit',
				title : '编辑保证金账户',
				width : (screen.width-180)*0.5,
				border : false,
				height : 330,
				minimizable : true,
				modal : true,
				buttonAlign : 'center',
			    buttons : [
							{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : function() {
								var bankBranchNameUpdate =Ext.get('bankBranchNameUpdate').dom.value;
								var bankBranchIdUpdate =Ext.get('bankBranchIdUpdate').dom.value;
								var accountIdUpdate =Ext.get('accountIdUpdate').dom.value;
								var accountNameUpdate =Ext.get('accountNameUpdate').dom.value;
								var authorizationMoney =Ext.get('glAccountBankCautionmoney_authorizationMoney_update').dom.value;
				            	var surplusMoney =Ext.get('glAccountBankCautionmoney_surplusMoney_update').dom.value;
				            	var rawauthorizationMoney =Ext.get('glAccountBankCautionmoney_rawauthorizationMoney_update').dom.value;
				            	var rawsurplusMoney =Ext.get('glAccountBankCautionmoney_rawsurplusMoney_update').dom.value;
								if(bankBranchNameUpdate == "" || bankBranchIdUpdate == "" ||accountIdUpdate == "" || accountNameUpdate == ""){
									Ext.Msg.alert('状态','请填写数据完整后再提交!');
								}
							if(parseFloat(authorizationMoney) < parseFloat(surplusMoney)){
								Ext.Msg.alert('状态','可用金额不能大于总金额!');
							}else if(parseFloat(rawauthorizationMoney) < parseFloat(rawsurplusMoney)){
								Ext.Msg.alert('状态','初始可用金额不能大于初始总金额!');
							}
							else{
									updateCautionAccountWin.findById('updateCautionAccountForm').getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/saveGlAccountBankCautionmoney.do',
										success : function(form ,action) {
											objt = Ext.util.JSON.decode(action.response.responseText);
												 Ext.ux.Toast.msg('状态', '修改成功!');
														accountBankTreePanel1.getRootNode().reload();
														accountBankTreePanel1.expandAll() ;
														updateCautionAccountWin.destroy();
										},
										failure : function(form ,action) {
											objt = Ext.util.JSON.decode(action.response.responseText);
											Ext.Msg.alert('状态', objt.msg);
										}
									});
								}
							}
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : function() {
									updateCautionAccountWin.findById('updateCautionAccountForm').getForm().reset();
							}
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : function() {
								updateCautionAccountWin.destroy();
							}
							}
				         ],
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
						border : false,
						autoWidth : true,
						autoHight : true ,
						items :[{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 120,
							defaults : {anchor : '100%'},
							items :[{
								id: 'bankBranchNameUpdate',
								xtype : 'trigger',
			            		triggerClass :'x-form-search-trigger',
			            		allowBlank : false,
			            		editable : false,
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
							labelWidth : 120,
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
								labelWidth : 120,
								defaults : {anchor : '100%'},
								items :[{
		            	 		id : 'accountNameUpdate',
								xtype : 'textfield',
								fieldLabel : '银行地址',
								maxLength : 255,
								maxLengthText : '最大输入长度255',
								name : 'glAccountBankCautionmoney.bankAddress',
								value : obj.data.bankAddress
							}]},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : '100%'},
								items :[{
								xtype : 'numberfield',
								fieldLabel : '初始总金额',
								fieldClass:'field-align',
								allowBlank : false,
								fieldClass:'field-align',
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								name : 'glAccountBankCautionmoney.rawauthorizationMoney',
								id :'glAccountBankCautionmoney_rawauthorizationMoney_update',
								value : obj.data.rawauthorizationMoney
							
							},{
								xtype : 'numberfield',
								fieldLabel : '总金额',
								fieldClass:'field-align',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								name : 'glAccountBankCautionmoney.authorizationMoney',
								id:'glAccountBankCautionmoney_authorizationMoney_update',
								value : obj.data.authorizationMoney
							}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 },{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
								columnWidth : .42,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : '100%'},
								items :[{
								xtype : 'numberfield',
								fieldLabel : '初始可用金额',
								fieldClass:'field-align',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								name : 'glAccountBankCautionmoney.rawsurplusMoney',
								id :'glAccountBankCautionmoney_rawsurplusMoney_update',
								value : obj.data.rawsurplusMoney
								
							},{
								xtype : 'numberfield',
								fieldLabel : '可用金额',
								fieldClass:'field-align',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								name : 'glAccountBankCautionmoney.surplusMoney',
								id :'glAccountBankCautionmoney_surplusMoney_update',
								value : obj.data.surplusMoney
							}]},{
								 columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 },{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
								columnWidth : 0.52,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'datefield',
									fieldLabel : '开户日期',
									name : 'glAccountBankCautionmoney.createDate',
									format:'Y-m-d',
									value : obj.data.createDate
							}]},{
							    columnWidth : .48,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : '100%'},
								items :[{
								xtype : 'textfield',
								fieldLabel : '适用的业务种类',
								name : 'glAccountBankCautionmoney.serviceTypeAccount',
								blankText : '必填信息',
								maxLength : 100,
								maxLengthText : '最大输入长度100',
								value : obj.data.serviceTypeAccount
							}]},{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : '100%'},
								items :[{
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
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			glAccountBankCautionmoneyId : id
		}
	})
				}
},
//删除保证金账户信息——右键事件调用
 deleteCautionAccount :function (id){
 	 	var accountBankTreePanel1=this.accountBankTreePanel;
 	 	var gPanel_manageCautionAccount=this.gPanel_manageCautionAccount;
	if(id == '0') {
	 Ext.ux.Toast.msg('状态', '请选择一条记录!');
	}else{
	  Ext.MessageBox.confirm('确认删除', '是否确认删除选中的记录???', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/deteleGlAccountBankCautionmoney.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					 Ext.ux.Toast.msg('状态', '删除成功!');
							accountBankTreePanel1.getRootNode().reload();
							accountBankTreePanel1.expandAll() ;
							gPanel_manageCautionAccount.getStore().reload();
				},
				failure : function(result, action) {
					var msg = Ext.decode(action.response.responseText);
					 Ext.ux.Toast.msg('状态', '删除失败!');
				},
				params: { glAccountBankCautionmoneyId: id }
			});
		}
	});
	}
},

refresh :function (){
	var accountBankTreePanel1=this.accountBankTreePanel;
	accountBankTreePanel1.getRootNode().reload();
	accountBankTreePanel1.expandAll() ;
},
//查看保证金账户信息
seeCautionAccount :function (id,leaf){
	if(id == '0') {
	 Ext.ux.Toast.msg('状态', '请选择一条记录!');
   }else{
	
	Ext.Ajax.request({
		 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
		
		method : 'POST',
		success : function(response, request){
			objSee = Ext.util.JSON.decode(response.responseText);
			//alert("冻结金额==="+objSee.data.accountFrozenMoney+"       解冻金额==="+objSee.data.accountUnfreezeMoney);
			var seeCautionAccountWin = new Ext.Window({
				id : 'seeCautionAccountWin',
				layout : 'fit',
				title : '查看保证金账户',
				border : false,
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
						border : false,
						autoWidth : true,
						autoHight : true ,
						items :[{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 120,
							defaults : {xtype : 'textfield',anchor : '100%'},
							items :[{
								fieldLabel : '账户开户银行 ',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankBranchName
							}]},{
				
							columnWidth : .5,
							layout : 'form',
							labelWidth : 120,
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
						labelWidth : 120,
						defaults : {anchor : '100%'},
						items :[{
								fieldLabel : '银行地址',
								xtype : 'textfield',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankAddress
							}]},{
						columnWidth : .45,
						layout : 'form',
						labelWidth : 120,
						defaults : {anchor : '100%'},
						items :[{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.rawauthorizationMoney',
								fieldClass:'field-align',
								fieldLabel : '初始总金额',								
								value : objSee.data.rawauthorizationMoney
							},{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.authorizationMoney',
								fieldClass:'field-align',
								fieldLabel : '总金额',							
								value : objSee.data.authorizationMoney
							}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 },{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
							columnWidth : .42,
							layout : 'form',
							labelWidth : 120,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.rawsurplusMoney',
								fieldClass:'field-align',
								fieldLabel : '初始可用金额',								
								value : objSee.data.rawsurplusMoney
							},{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.surplusMoney',
								fieldClass:'field-align',
								fieldLabel : '可用金额',								
								value : objSee.data.surplusMoney
							}]},{
								 columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 },{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{	
							columnWidth : .52,
							layout : 'form',
							labelWidth : 120,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'datefield',
								fieldLabel : '开户日期',
								name : 'glAccountBankCautionmoney.createDate',
								format:'Y-m-d',
								readOnly : true,
								value : objSee.data.createDate
							}]},{	
							columnWidth : .48,
							layout : 'form',
							labelWidth : 120,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '适用的业务种类',
								name : 'glAccountBankCautionmoney.serviceTypeAccount',
								readOnly : true,
								value : objSee.data.serviceTypeAccount
							}]},{
					   columnWidth : 1,
						layout : 'form',
						labelWidth : 120,
						defaults : {anchor : '100%'},
						items :[{
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
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			glAccountBankCautionmoneyId : id
		}
	})
	}
	},
	related :function(flag,jStore_manageCautionAccount){
	jStore_manageCautionAccount.baseParams.flag = flag;
	jStore_manageCautionAccount.load({
										params : {
											start : 0,
											limit : 15
										}
									});
	
	}
});
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