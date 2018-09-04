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
				height:76
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
								if (curUserInfo.username.indexOf('admin') !=-1) {
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
	
	  south : null,
	 
	/**
	 * 构造函数
	 */
	constructor : function(_cfg) {
		if(typeof(_cfg.systemName)!="undefined"){
			this.systemName=_cfg.systemName;
		}
		if(typeof(_cfg.userName)!="undefined"){
			this.userName=_cfg.userName;
		}
						
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		//this.initUIComponents();
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
							if (tabPanel.items.length > 9) {
								// Ext.Msg.alert("温馨提示", "为保证系统操作迅速流畅。
								// 请关掉您不需要的操作页面，再行打开此页面！");
//								Ext.ux.Toast.msg('温馨提示：','为保证系统操作迅速流畅。 请关掉您不需要的操作页面，再行打开此页面！');
								Ext.ux.Toast.msg('温馨提示：','系统限制最多同时打开10个页面,当前已达上限,请关闭一些闲置页面,再打开新页面.');
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
			/**
	 * 南部导航
	 */
	
	  this.south=new Ext.Panel({
		border : false,
		region : 'south',
		tbar : [new Ext.Toolbar.TextItem('您好: <a  href="#" style=" color:#000;text-decoration: underline;"  onclick="changeCurrentUser()">'+this.userName+'</a>')
				,'-',{
					text : '退出系统',
					iconCls : 'btn-logout',
					handler : function() {
						App.Logout();
					}
				},'-',{
					text : '在线用户',
					iconCls : 'btn-onlineUser',
					handler : function() {
						OnlineUserSelector.getView().show();
					}
				},'-',{
					text : '系统管理',
					iconCls : 'btn-system',
					handler : function() {
						ERPManger();
					}
				}/*,'-',{
					text : '工作桌面',
					iconCls : 'btn-desk',
					handler : function(v) {
						changeShowDesk(v.btnEl.dom);
					}
				}*/,'->',{
					xtype : 'tbseparator'
				},new Ext.Toolbar.TextItem('技术支持: <a href="http://www.zxzbol.com"; target="_blank"; style=" color:#000;text-decoration: underline;">升升投</a>'),
				 '-', {
					xtype : 'combo',
					mode : 'local',
					editable : false,
					value : '切换皮肤',
					width : 100,
					triggerAction : 'all',
					store : [['ext-all-css04', '缺省灰白'], ['ext-all', '浅蓝主题'],
							['ext-all-css05', '浅绿主题'],
							['ext-all-css03', '粉红主题'], ['xtheme-tp', '灰色主题'],
							['xtheme-default2', '灰蓝主题'],
							['xtheme-default16', '灰绿主题'],
							['xtheme-access', 'Access风格']],
					listeners : {
						scope : this,
						'select' : function(combo, record, index) {
							if (combo.value != '') {
								var expires = new Date();
								expires.setDate(expires.getDate() + 300);
								setCookie("theme", combo.value, expires,__ctxPath);
								Ext.util.CSS.swapStyleSheet("theme", __ctxPath
												+ "/ext3/resources/css/"
												+ combo.value + ".css");
							}
						}
					}
				}]
	}),

		IndexPage.superclass.constructor.call(this, {
					border : false,
					layout : "border", // 指定布局为border布局
					items : [this.top, this.west, this.center,this.south
					]
				});

		 //加载左侧菜单
				
		// loadWestMenu();
	}
});

function loadWestMenu(isReload) {
	if (!isReload) {
		isReload = '';
	}
		var westPanel = Ext.getCmp('west-panel');
		var topMenuId = SystemType;
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
function loadAppHome(homeid) {
	App.clickTopTab(homeid);
}
