//加载完成后执行
var selectAccountBankName=function(bankName){
	Ext.onReady(function() {
        Ext.QuickTips.init();
		Ext.BLANK_IMAGE_URL = __ctxPath+ '/ext/resources/images/default/s.gif';
		
		var accountBankTree = new Ext.Panel({
			width : 300,
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
					dataUrl : __ctxPath + '/creditFlow/guarantee/guaranteefinance/getAccountBankTreeGlAccountBank.do'
					//dataUrl : 'getAccountBankTree.action'
				}),
				root : new Ext.tree.AsyncTreeNode({
					id : '0',
					//expanded : false,
					text : '根结点'}),
				listeners : {
					'dblclick' : function(n) {
						if (n.leaf == true) {
							
							//var bankIds = n.attributes.bankId;
							//var bankNames = n.attributes.bankName;
							///var bankNameEnd = bankNames.substring(bankNames.indexOf("("));//获取从'('开始至末位的值
		            		//var bankNameStart = bankNames.substring(0,bankNames.indexOf("("));//获取从0开始至第一次出现'('位置之间的值
		            		//return start+'<font color=green>'+len+'</font>';
		            		
							bankNameJsonObj = {
								bankName : n.attributes.bankBranchName,
								accountname : n.attributes.accountname,
								surplusMoney : n.attributes.surplusMoney,
								id : n.id,
								text : n.text
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
			title : '保证金账户参照',
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
//加载完成后执行
var selectAccountBankName1=function(bankName){
	Ext.onReady(function() {
        Ext.QuickTips.init();
		Ext.BLANK_IMAGE_URL = __ctxPath+ '/ext/resources/images/default/s.gif';
		
		var accountBankTreeLoad = new Ext.tree.TreeLoader({
		dataUrl : __ctxPath + '/creditFlow/guarantee/guaranteefinance/getAccountBankTreeGlAccountBank.do'
	    })
		var accountBankTree  = new Ext.ux.tree.TreeGrid({
		width: 500 ,
		tbar :new Ext.Toolbar({items:['关键字:', {
				 xtype:'trigger'
					 ,triggerClass:'x-form-clear-trigger'
					 ,
					 scope:this,
					 onTriggerClick:function() {
		
						 this.setValue('');
						 
						 fil1.clear();
					 }
					 ,id:'filter1'
					 ,enableKeyEvents:true,
					 width :241
					 ,listeners:{
					 keyup:{buffer:150, fn:function(field, e) {
						 if(Ext.EventObject.ESC == e.getKey()) {
						    field.onTriggerClick();
						 }
						 else {
							 var val = this.getRawValue();
							 var re = new RegExp('.*' + val + '.*', 'i');
							 fil1.clear();
							 fil1.filter(re, 'text');
					     }
					 }}
					 }
					 }]}),
		height : 550 ,
		filter :true,
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
						'dblclick' : function(n) {
						if (n.leaf == true) {
							bankNameJsonObj = {
								bankName : n.attributes.bankBranchName,
								accountname : n.attributes.accountname,
								surplusMoney : n.attributes.surplusMoney,
								id : n.id,
								text : n.text
							};
							bankName(bankNameJsonObj);
							accountBankWin.destroy();
						}
					}
			}
			
			
		
	});
			var Filter1 = Ext.ux.tree.TreeFilterX ? Ext.ux.tree.TreeFilterX : Ext.tree.TreeFilter;
			var fil1 = new Filter1(accountBankTree, {autoClear:true});		
	//		accountBankTree.expandAll() ;
		var accountBankWin = new Ext.Window({
			title : '保证金账户参照',
			iconCls : 'menu_manageIcon',
			width : (screen.width-180)*0.45,
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