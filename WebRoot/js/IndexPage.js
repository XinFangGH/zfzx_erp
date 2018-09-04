/**
 * @author csx
 * @version 1.0
 * @date 2009-12-25
 * @class IndexPage
 * @extends Ext.Viewport
 * @description 程序的主页
 */
var IndexPage = Ext.extend(Ext.Viewport, {
	/**
	 * 头部导航
	 */
	top : new Ext.Panel({
				region : 'north',
				id : '__nortPanel',
				contentEl : 'app-header',
				height : 58
			}),
	/**
	 * 中间内容部分
	 */
	center : null,
	/**
	 * 西部菜单导航Panel
	 */
	west : new Ext.Panel({
				region : 'west',
				id : 'west-panel', //
				title : '导航',
				iconCls : 'menu-navigation',
				split : true,
				width : 180,
				// autoScroll : true,
				layout : 'accordion',
				collapsible : true,
				tools : [{
							id : 'refresh',
							handler : function() {
								if (curUserInfo.username.indexOf('admin')!=-1 ) {
									loadWestMenu('true');
								} else {
									Ext.ux.Toast.msg('操作提示', '仅对开发用户开放刷新菜单功能!');
								}
							}
						}],
				items : []
			}),

	/**
	 * 南部导航
	 */
	/*
	 * south: new Ext.Panel({ border:false, region : 'south', tbar : [{ text :
	 * '退出系统', iconCls : 'btn-logout', handler : function() { App.Logout(); }
	 * },'-',{ text : '在线用户', iconCls : 'btn-onlineUser', handler : function() {
	 * OnlineUserSelector.getView().show(); } },'-',{ text : '意见箱', iconCls :
	 * 'btn-suggest-box', handler : function(){
	 * App.clickTopTab('SuggestBoxView',{title:'我的意见箱',userId:curUserInfo.userId}); }
	 * },'-', { id : 'messageTip', xtype:'button', hidden : true, width : 50,
	 * height : 20, handler : function() { var megBtn =
	 * Ext.getCmp('messageTip'); var megWin = Ext.getCmp('win'); if (megWin ==
	 * null) { new MessageWin().show(); } megBtn.hide(); } }, '->', { xtype :
	 * "tbfill" }, { xtype:'tbtext', text:__companyName, //text:'智维民间金融ERP',
	 * id:'toolbarCompanyName' }, { xtype : 'tbseparator' }, new
	 * Ext.Toolbar.TextItem('技术支持 <a href=http://www.zhiweitime.com
	 * target="_blank">北京互融时代软件有限公司</a>'), { xtype : 'tbseparator' },{ pressed :
	 * false, text : '便签', iconCls:'tipsTile', handler:function(){
	 * App.clickTopTab('PersonalTipsView'); } }, { xtype : 'tbseparator' }, {
	 * pressed : false, text : '与我们联系', handler:function(){
	 * Ext.ux.Toast.msg("联系我们","电话：020-62652355<br/>网址：http://www.zhiweitime.com"); }
	 * },'-',{ text:'收展', iconCls:'btn-expand', handler:function(){ var
	 * panel=Ext.getCmp("__nortPanel"); if(panel.collapsed){ panel.expand(true);
	 * }else{ panel.collapse(true); } } },'-',{ xtype:'combo', mode : 'local',
	 * editable : false, value:'切换皮肤', width:100, triggerAction : 'all', store
	 * :[['ext-all-css04','缺省灰白'],['ext-all','浅蓝主题'],['ext-all-css05','绿色主题'],['ext-all-css03','粉红主题'],['xtheme-tp','灰色主题'],['xtheme-default2','灰蓝主题'],['xtheme-default16','绿色主题'],['xtheme-access','Access风格']],
	 * listeners:{ scope: this, 'select':function(combo,record, index){
	 * if(combo.value!=''){ var expires = new Date();
	 * expires.setDate(expires.getDate() + 300);
	 * setCookie("theme",combo.value,expires, __ctxPath);
	 * Ext.util.CSS.swapStyleSheet("theme", __ctxPath + "/ext3/resources/css/" +
	 * combo.value + ".css"); } } } }] }),
	 */
	/**
	 * 构造函数
	 */
	constructor : function() {
		Ext.QuickTips.init();// 调用此方法，tabTip才有效果 add by lisl 2012-05-11
		this.center = new Ext.TabPanel({
					id : 'centerTabPanel',
					region : 'center',
					deferredRender : true,
					enableTabScroll : true,
					activeTab : 0, // first tab initially active,
					resizeTabs : true,
					tabWidth : 195,
					defaults : {
						autoScroll : true,
						closable : true
					},
					items : [],
					plugins : new Ext.ux.TabCloseMenu(),
					listeners : {
				/*		'add' : function(tabPanel, comp, index) {

							if (tabPanel.items.length > 8) {
								tabPanel.remove(tabPanel.items.get(0));
								tabPanel.doLayout();
							}
						},*/
						'beforeadd' : function(tabPanel, comp, index) {
							if (tabPanel.items.length > 10) {
								// Ext.Msg.alert("温馨提示", "为保证系统操作迅速流畅。
								// 请关掉您不需要的操作页面，再行打开此页面！");
								Ext.ux.Toast.msg('温馨提示：',
										'为保证系统操作迅速流畅。 请关掉您不需要的操作页面，再行打开此页面！');
								return false;
							}
						},
						'tabchange' : function(tabPanel, panel) {
							if (panel) {
								panel.setTabTip(panel.title);// 为tabPanel动态设置qtip
							}
						}
					}
				});

		IndexPage.superclass.constructor.call(this, {
					border : false,
					layout : "border", // 指定布局为border布局
					items : [this.top, this.west, this.center
					// this.south
					]
				});

		// 加上首页的导航菜单
		var activeTab = getCookie('_topNavId');
		if (activeTab == null || activeTab == undefined)
			activeTab = 0;
		this.navTab = new Ext.TabPanel({
					width : 800,
					id : 'appNavTabPanel',
					deferredRender : true,
					enableTabScroll : true,
					activeTab : activeTab,
					frame : false,
					border : false,
					plain : true,
					height : 0,
					renderTo : 'header-nav',
					tabMargin : 20,
					defaults : {
						autoScroll : false,
						closable : false,
						bodyStyle : 'padding-bottom: 12px;'
					},
					listeners : {
						scope : this,
						'tabchange' : function(tabPanel, tab) {
							var expires = new Date();
							expires.setDate(expires.getDate() + 300);
							setCookie("_topNavId", tab.getId(), expires,
									__ctxPath);
							// 切换左菜单
							loadWestMenu();
						}
					},
					items : []
				});
		// 设置日历、声音提示、首页
		this.afterPropertySet();
		// 加载菜单
		// loadWestMenu();
	},
	/**
	 * 设置日历、声音提示、首页
	 */
	afterPropertySet : function() {
		var centerPanel = this.center;
		// 显示信息条数按钮
		var addBtn = function(count) {
			var megBtn = Ext.getCmp('messageTip');
			var megWin = Ext.getCmp('win');
			var reMegWin = Ext.getCmp('wind');
			var megSpr = Ext.getCmp('messageTipSeparator');
			if (count > 0 && megWin == null && reMegWin == null) {

				if (typeof(megBtn) != "undefined") {
					megBtn
							.setText('<div style="height:25px;"><img src="'
									+ __ctxPath
									+ '/images/newpm.gif" style="height:12px;"/>你有<strong style="color: red;">'
									+ count + '</strong>信息</div>');
					megBtn.show();
				}
			} else {
				megBtn.hide();
				megSpr.hide();
			}
		};

		var addBtnFunction = function() {
			Ext.Ajax.request({
						url : __ctxPath + '/info/countInMessage.do',
						method : 'POST',
						success : function(response, options) {
							var result = Ext.util.JSON
									.decode(response.responseText);
							count = result.count;
							addBtn(count);
							setTimeout(addBtnFunction, 1000 * 60); // 设60秒响应一次
						},
						failure : function(response, options) {
						},
						scope : this
					});
		};

		var navTab = this.navTab;
		navTab.add(curUserInfo.topModules);
		var removeitem=navTab.findById("Mod_credit");
		if(removeitem){
			removeitem.hide();
			navTab.remove(removeitem);
			navTab.doLayout();
				
		}
		setTimeout(function() {
					// 激活
					var actTabId = getCookie('_topNavId');
					// 激活上一次点击的Panel
					if (actTabId) {
						navTab.activate(actTabId);
					}
					if (navTab.getActiveTab() == null) {
						// 激活第一个
						navTab.activate(navTab.items.get(0));
					}
					// 显示当前日历
					addBtnFunction();
				}, 1200);
		// Ext.getCmp('SearchForm').render('searchFormDisplay');
	}
});

function loadWestMenu(isReload) {
	if (!isReload) {
		isReload = '';
	}
		var westPanel = Ext.getCmp('west-panel');
		var topMenuId = "";
	if (getCookie("_topNavId") != '') {
		var iconCls=getCookie("_topNavId");
		topMenuId = iconCls.split('_')[1];
	} else {
	
		var iconCls = Ext.getCmp('appNavTabPanel').getActiveTab().iconCls;
		 topMenuId = iconCls.split('-')[1];
	}
	// alert(getCookie("_topNavId"))
	Ext.Ajax.request({
				url : __ctxPath + '/panelTreeMenu.do?topMenuId=' + topMenuId
						+ '&isReload=' + isReload,
				success : function(response, options) {
					var arr = eval(response.responseText);
					var __activedPanelId = getCookie("__activedPanelId");
					westPanel.removeAll();
					westPanel.doLayout();

					for (var i = 0; i < arr.length; i++) {
						var doc = strToDom(arr[i].subXml);
						var root = doc.documentElement || doc;
						var panel = new Ext.tree.TreePanel({
									id : arr[i].id,
									title : arr[i].text,
									iconCls : arr[i].iconCls,
									layout : 'fit',
									animate : true,
									border : false,
									autoScroll : true,
									loader : new zhiwei.ux.TreeXmlLoader({
												preloadChildren : true
											}),
									root : new Ext.tree.AsyncTreeNode({
												text : root.tagName,
												xmlNode : root
											}),
									listeners : {
										'click' : App.clickNode
									},
									rootVisible : false
								});
						westPanel.add(panel);
						panel.on('expand', function(p) {
									// 记住上次点激的panel
									var expires = new Date();
									expires.setDate(expires.getDate() + 30);
									setCookie("__activedPanelId", p.id,
											expires, __ctxPath);
								});
						// 激活上次点击的panel
						if (arr[i].id == __activedPanelId) {
							westPanel.layout.activeItem = panel;
						}
					}
					westPanel.doLayout();
				}
			});
}// end of the loadWestMenu function
