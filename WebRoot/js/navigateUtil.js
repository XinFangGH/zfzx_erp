Ext.ns("navigateUtil");
//@DHZCB
//----------系统菜单调整新增-------------
//开始
function showNewMenus(el,flag){
	changeHeaderClass(el);
	var menu =null;
	if('fa'==flag){//互联网金融
		menu= new Ext.menu.Menu();
		//此处的id要与menu-all.xml文件中module的name相同
		if(isSystemType('Mod_fa_online')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_online',text:'信用借款业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_p2p')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_p2p',text:'P2P中介业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_p2c')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_p2c',text:'C2P中介业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_pa2p')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_pa2p',text:'PA2P中介业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_ca2p')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_ca2p',text:'CA2P中介业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_secondtransaction')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_secondtransaction',text:'债权交易业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_dplan')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_dplan',text:'D债权转让业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_uplan')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_uplan',text:'U债权转让业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_fa_bao')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'fa_bao',text:'活期理财业务',handler:manger,iconCls:'btn-team30'});
		}
		
		//menu.add({id: 'fa_base',text:'基础功能管理',handler:manger,iconCls:'btn-tree-team56'});
		
	}else if('loan'==flag){
		menu= new Ext.menu.Menu();
		if(isSystemType('Mod_team')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'team',text:'贷款业务',handler:manger,iconCls:'btn-team30'});
		}
		if(isSystemType('Mod_investcstmanger')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'investcstmanger',text:'财富业务',handler:manger,iconCls:'btn-team30'});
		}
		/*if(isSystemType('Mod_yun')){//此处的参数要和菜单xml文件的Menus的id相同
			menu.add({id: 'yun',text:'一元云购',handler:manger,iconCls:'btn-team30'});
		}*/
	}
	menu.show(el);
}

function manger(){
	if(!isSystemType('Mod_'+this.id)){//此处的参数要和菜单xml文件的Menus的id相同
		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
		return;
	}
 	SystemType=this.id;
 	changeSysType(SystemType);
 	//切换个人桌面
	changeDesk();
 	changeNewWestTitle(this.text);
 	loadWestMenu(false);
}

function changeNewWestTitle(text){
	Ext.getCmp("west-panel").setTitle("导航-"+text);
}

/**
 * 点击当前登录用户名出现修改信息页面
 */
function changeCurrentUser(){
	if(!isSystemType('Mod_erp')){
		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
		return;
	}
 	SystemType='erp';
 	changeSysType(SystemType);
 	changeNewWestTitle('系统管理');
 	loadWestMenu(false);
	
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem('ProfileForm');
	if (tabItem == null) {
		$ImportJs('ProfileForm', function(view) {
			tabItem = tabs.add(view);
			tabs.activate(tabItem);
		},null);
	}else {
		tabs.activate(tabItem);
	}
}
//结束
//----------系统菜单调整新增-------------
