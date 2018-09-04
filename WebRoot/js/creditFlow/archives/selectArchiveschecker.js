//加载完成后执行
var selectArchiveschecker=function(bankName,companyId){
	Ext.onReady(function() {
        Ext.QuickTips.init();
		Ext.BLANK_IMAGE_URL = __ctxPath + ' /ext/resources/images/default/s.gif';
		
		var accountBankTree = new Ext.Panel({
			width : 300,
			frame : true,
			titleCollapse : true,
			autoScroll : true,
			items : [{
		//		id : 'treePanelAccount',
				xtype : 'treepanel',
				border : false,
				iconCls : 'icon-nav',
				rootVisible : false,
				autoScroll : true,
				loader : new Ext.tree.TreeLoader({
					dataUrl : __ctxPath + '/creditFlow/archives/getCheckerTreePlArchives.do?companyId='+companyId
				}),
				root : new Ext.tree.AsyncTreeNode({
					id : '0',
					//expanded : false,
					text : '根结点'}),
				listeners : {
					'dblclick' : function(n) {
						if (n.leaf == true) {
							bankNameJsonObj = {
								id : n.id,
								text : n.text,
								pathname :n.attributes.pathname
							};
							bankName(bankNameJsonObj);
							accountBankWin.destroy();
						}
					}/*,'load' : function(n){
						alert(n.text);
					}*/
			    }
			}]
		});
		
		var accountBankWin = new Ext.Window({
			id : 'accountBanWin',
			title : '档案柜格子',
			iconCls : 'menu_manageIcon',
			width : (screen.width-180)*0.35,
			height : 400 ,
			layout : 'fit',
			collapsible : true,
			buttonAlign : 'center',
			modal : true,
			items : [accountBankTree]
		});
		//accountBankTree.findById('treePanelAccount').expandAll();
		accountBankWin.show();
		
		});
}