//加载完成后执行
var selectBranchBank=function(node,bankBranchName){
	Ext.onReady(function() {
        Ext.QuickTips.init();
		Ext.BLANK_IMAGE_URL = __ctxPath+ '/ext/resources/images/default/s.gif';
		
		var accountBranchBankTree = new Ext.Panel({
			width : 300,
			/*frame : true,
			titleCollapse : true,
			autoScroll : true,*/
			frame : true,
			titleCollapse : true,
			autoScroll : true,
			items : [{
				id : 'treePanelAccount',
				xtype : 'treepanel',
				border : false,
				iconCls : 'icon-nav',
				//collapsed : false,
				//animate : true,
				rootVisible : false,
				autoScroll : true,
				loader : new Ext.tree.TreeLoader({
					dataUrl : __ctxPath+'/system/getAllParentBanksTreeDicAreaDynam.do'
				}),
				root : new Ext.tree.AsyncTreeNode({
					id : node,
					//expanded : false,
					text : '根结点'}),
				listeners : {
					'dblclick' : function(n) {
						//alert("n.id===="+n.id+"    d.text==="+n.text);
						if (n.leaf == true) {
							//document.getElementById('name').value = n.text ;
							//document.getElementById('id').value = n.id ;
							bankBranchNameJsonObj = {
								id : n.id,
								text : n.text,
								remarks :n.attributes.remarks
							};
							bankBranchName(bankBranchNameJsonObj);
							accountBranchBankWin.destroy();
						}
					}
			    }
			}]
		});
		
		var accountBranchBankWin = new Ext.Window({
			id : 'accountBranchBanWin',
			title : '账户开户银行',
			iconCls : 'menu_manageIcon',
			//width: 300,
			width : (screen.width-180)*0.35,
			height : 400 ,
			layout : 'fit',
			collapsible : true,
			buttonAlign : 'center',
			modal : true,
			items : [accountBranchBankTree]
		});
		//accountBankTree.findById('treePanelAccount').expandAll();
		accountBranchBankWin.show();
		
		});
/*	Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = '/creditBusiness1.0/ext/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
	*//**授信银行管理树*//*
	
	指标树start------------------------------------------------------------------------------
	var dic_TreeLoader = new Ext.tree.TreeLoader({
		dataUrl : 'indicatorStoreList.action'
	})
	var dic_Root = new Ext.tree.AsyncTreeNode({
		id : '0',
		text : "授信银行树"
	});
	//alert(id + text);
	var dic_TreePanel = new Ext.Panel({
		id : 'dic_TreePanel',
		frame : false,
		autoScroll : true,
		collapsible : false,
		titleCollapse : false,
		items : [{
				id :'tree_panel',
				xtype : 'treepanel',
				border : false,
				iconCls : 'icon-nav',
				rootVisible : true,
				autoScroll : true,
				loader : dic_TreeLoader,
				root : dic_Root,
				listeners : {
					'contextmenu' : function(node,e) {
						nodeSelected = node;
						nodeSelected.select();
						//setCatalogMenuFun(node,e);
					},
					listeners : {
        					'dblclick' : function(n) {	
        						if(n.isLeaf()){
        							document.getElementById('procredit.pbid').value = n.id ;
									document.getElementById('bankname').value = n.text ;									
//        							Ext.getCmp('szyh').setValue('aaaaa');
									window.close();
        						}
        					}
        				}
					'click' : function(node) {
						if (node.leaf == true) {
//							alert('a');
//							panel_enterprise.add(gPanel_enterprise);
							gPanel_enterprise.setTitle("指标类型：" + node.text);
							indicatorTypeId = node.id;
							indicatorType = node.text;
							jStore_enterprise.removeAll();
							jStore_enterprise.baseParams.indicatorTypeId = indicatorTypeId;
							jStore_enterprise.load({
								params : {
									start : start,
									limit : pageSize
								}
							});
						}
					}
				}
		}]
					
	});
	指标树end----------------------------------------------------------------------------
			
	var window = new Ext.Window({
		width: 350,
		title : '授信银行列表',
		height : 400 ,
		collapsible : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [dic_TreePanel]
	});
	window.show();
});*/

}
