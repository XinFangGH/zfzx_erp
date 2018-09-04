var autoHight = Ext.getBody().getHeight();
var autoWidth = Ext.getBody().getWidth();
var start = 0 ;
var pageSize = 20 ;
var root = 'topics' ;
var totalProperty = 'totalProperty' ;
var accountBankTreePanel;
var jStore_cautionMoneyProj;
var gPanel_cautionMoneyProj;

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var accountBankTreeLoad = new Ext.tree.TreeLoader({
		dataUrl : __ctxPath + '/creditFlow/guarantee/guaranteefinance/getAccountBankTreeGlAccountBank.do'
	})
	   function seebank(id,leaf){
			if(id == '0') {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				Ext.Ajax.request({
					url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBank.do',
					method : 'POST',
					success : function(response, request){
						objAccountSee = Ext.util.JSON.decode(response.responseText);
						var seeAccountBankWin = new Ext.Window({
							id : 'seeAccountBankWinProj',
							layout : 'fit',
							title : '查看总行保证金信息',
							iconCls : 'lookIcon',
							width : (screen.width-180)*0.4,
							height : 300,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'seeAccountBankFormProj',
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
   //查看保证金账户信息
function seeCautionAccount(id,leaf){
	if(id == '0') {
	  Ext.MessageBox.alert('状态', '请选择一条记录!');
   }else{
	
	Ext.Ajax.request({
		 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
		
		method : 'POST',
		success : function(response, request){
			objSee = Ext.util.JSON.decode(response.responseText);
			//alert("冻结金额==="+objSee.data.accountFrozenMoney+"       解冻金额==="+objSee.data.accountUnfreezeMoney);
			var seeCautionAccountWin = new Ext.Window({
				id : 'seeCautionAccountWinProj',
				layout : 'fit',
				title : '查看保证金账户',
				iconCls : 'lookIcon',
				//width : 500,
				width : (screen.width-180)*0.5,
				height : 350,
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
							},{
								fieldLabel : '账户名称',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.accountname
							},{
								fieldLabel : '银行地址',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankAddress
							},{
								fieldLabel : '冻结金额.万元',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.accountFrozenMoney
							},{
								fieldLabel : '可用金额.万元',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.surplusMoney
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
//查看授信银行——按钮调用
	var see_button_acc = new Ext.Button({
		text : '查看详情',
		tooltip : '查看保证金账户信息',
		iconCls : 'seeIcon',
		handler : function() {
			var id = Ext.getCmp('selectidProj').value;
			var leaf = Ext.getCmp('selectleafProj').value;
			if(leaf==false){
				seebank(id,leaf)
			}else{
			seeCautionAccount(id,leaf);
			}
		
		}
	});
	//树列表
	accountBankTreePanel = new Ext.ux.tree.TreeGrid({
		tbar : [see_button_acc],
		id : 'accountBankTreePanelProj' ,
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
							return v+'万元';
						}else{
							return '<font color=green>'+v+'</font>';
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
								return '<font color=red>'+v+'</font>'+'万元';;
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
				Ext.getCmp('selectidProj').value = node.id;
				Ext.getCmp('selectleafProj').value = node.leaf;
				jStore_cautionMoneyProj.removeAll();
				var nodeId;
				if(node.leaf == false){
					var nodeRootId = node.id;
					jStore_cautionMoneyProj.baseParams.accountBankId = nodeRootId;
					jStore_cautionMoneyProj.baseParams.nodeId = 0;
					gPanel_cautionMoneyProj.setTitle(node.text+"(保证金账户记录)");
					jStore_cautionMoneyProj.load({
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
						url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
						method : 'POST',
						success : function(response, request) {
							objAccountBank = Ext.util.JSON.decode(response.responseText);
							accountBankCautionMoney = objAccountBank.data;
							if(objAccountBank.success==true){
								bankWholeName = accountBankCautionMoney.bankBranchName;
								accountName = accountBankCautionMoney.accountname;
								gPanel_cautionMoneyProj.setTitle(bankWholeName+"-"+accountName+"(保证金账户记录)");
								jStore_cautionMoneyProj.baseParams.nodeId = nodeId;
								jStore_cautionMoneyProj.baseParams.accountBankId = "";
								jStore_cautionMoneyProj.load({
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
		id : 'accountBankPanelProj',
		layout : 'column',
		title : '银行保证金账户',
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
			id : 'selectidProj',
			xtype : 'hidden',
			name : 'id',
			value : '0'
		},{
			id : 'selectleafProj',
			xtype : 'hidden',
			name : 'leaf'
		},{
			id : 'selectbankParentIdProj',
			xtype : 'hidden',
			name : 'bankParentId'
		}]
	});
	
	
	
	var accountBankManagePanel = new Ext.Panel({
		id :'dic_PanelProj',
		iconCls :'menu-accountbankmanageproj',
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
			items : [gPanel_cautionMoneyProj]
		}]
	});	
	
	var accountBankViewport = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			iconCls :'menu-accountbankmanageproj',
			items : [accountBankManagePanel]
		}]
	});

	
	
})








