
Ext.DataView.DragSelector = function(cfg){
    cfg = cfg || {};
    var view, proxy, tracker;
    var rs, bodyRegion, dragRegion = new Ext.lib.Region(0,0,0,0);
    var dragSafe = cfg.dragSafe === true;

    this.init = function(dataView){
        view = dataView;
        view.on('render', onRender);
    };

    function fillRegions(){
        rs = [];
        view.all.each(function(el){
            rs[rs.length] = el.getRegion();
        });
        bodyRegion = view.el.getRegion();
    }

    function cancelClick(){
        return false;
    }

    function onBeforeStart(e){
        return !dragSafe || e.target == view.el.dom;
    }

    function onStart(e){
        view.on('containerclick', cancelClick, view, {single:true});
        if(!proxy){
            proxy = view.el.createChild({cls:'x-view-selector'});
        }else{
            if(proxy.dom.parentNode !== view.el.dom){
                view.el.dom.appendChild(proxy.dom);
            }
            proxy.setDisplayed('block');
        }
        fillRegions();
        view.clearSelections();
    }

    function onDrag(e){
        var startXY = tracker.startXY;
        var xy = tracker.getXY();

        var x = Math.min(startXY[0], xy[0]);
        var y = Math.min(startXY[1], xy[1]);
        var w = Math.abs(startXY[0] - xy[0]);
        var h = Math.abs(startXY[1] - xy[1]);
        dragRegion.left = x;
        dragRegion.top = y;
        dragRegion.right = x+w;
        dragRegion.bottom = y+h;
        dragRegion.constrainTo(bodyRegion);
        proxy.setRegion(dragRegion);
        
        for(var i = 0, len = rs.length; i < len; i++){
            var r = rs[i];
            var sel = dragRegion.intersect(r);
            if(sel && !r.selected){  
                r.selected = true;
                view.select(i, true);
            }else if(!sel && r.selected){
                r.selected = false;
                view.deselect(i);
            }
        }
    }

    function onEnd(e){
        if (!Ext.isIE) {
            view.un('containerclick', cancelClick, view);    
        }        
        if(proxy){
            proxy.setDisplayed(false);
        }
    }

    function onRender(view){
        tracker = new Ext.dd.DragTracker({
            onBeforeStart: onBeforeStart,
            onStart: onStart,
            onDrag: onDrag,
            onEnd: onEnd
        });
        tracker.initEl(view.el);
    }
};

/**
 * @author lyy
 * @description 2010年4月14日
 * @class PhoneBookView
 * @extends Ext.Panel
 */
PhoneBookView = Ext.extend(Ext.Panel, {
	searchPanel : null,
	phoneBookPanel : null,
	store : null,
	dataView : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		PhoneBookView.superclass.constructor.call(this, {
					id : 'PhoneBookView',
					title : '联系人列表',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.phoneBookPanel]
				});
	},
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
					id : 'PhoneBookSearchForm',
					height : 40,
					region : 'north',
					frame : false,
					border : false,
					layout : 'hbox',
					layoutConfig : {
						padding : '5',
						align : 'middle'
					},
					defaults : {
						xtype : 'label',
						margins : {
							top : 0,
							right : 4,
							bottom : 4,
							left : 4
						}
					},
					items : [{
								text : '请输入查询条件:'
							}, {
								text : '姓名'
							}, {
								xtype : 'textfield',
								name : 'Q_fullname_S_LK'
							}, {
								text : '称谓'
							}, {
								xtype : 'textfield',
								name : 'Q_title_S_LK',
								xtype : 'combo',
								anchor : '95%',
								mode : 'local',
								triggerAction : 'all',
								store : ['先生', '女士']
							}, {
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : this.search.createCallback(this)
							},{
							    xtype:'hidden',
								name:'Q_appUser.userId_L_EQ',
								value:curUserInfo.userId
							},{
							    xtype:'hidden',
							    name:'Q_phoneGroup.isPublic_SN_EQ',
							    value:0
							}]
				});
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ '/communicate/listPhoneBook.do'
							}),
					baseParams:{
					       'Q_appUser.userId_L_EQ':curUserInfo.userId,
					       'Q_phoneGroup.isPublic_SN_EQ':0
					},
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								id : 'id',
								fields : [{
											name : 'phoneId',
											type : 'int'
										}

										, 'fullname', 'title', 'birthday',
										'nickName', 'duty', 'spouse', 'childs',
										'companyName', 'companyAddress',
										'companyPhone', 'companyFax',
										'homeAddress', 'homeZip', 'mobile',
										'phone', 'email', 'qqNumber', 'msn',
										'note', 'userId', 'groupId', 'isShared']
							}),
					remoteSort : true
				});
		this.store.setDefaultSort('phoneId', 'desc');
		this.store.load({
					params : {
						start : 0,
						limit : 15
					}
				});

		this.dataView = new Ext.DataView({
			store : this.store,
			tpl : new Ext.XTemplate(
					'<tpl for=".">',
					'<div class="thumb-wrap" id="{phoneId}">',
					'<span><font>姓名&nbsp;：</font>{fullname}</span>',
					'<span><font>手机&nbsp;：</font>{mobile}</span>',
					'<span><font>电话&nbsp;：</font>{phone}</span>',
					'<span><font>Email：</font>{email}</span>',
					'<span><font>QQ&nbsp;&nbsp;&nbsp;：</font>{qqNumber}</span>',
					'<span><font>MSN&nbsp;&nbsp;：</font>{msn}</span>',
					'</div>', '</tpl>'),
//			height:326,
			autoScroll:true,
			multiSelect : true,
			overClass : 'x-view-over',
			itemSelector : 'div.thumb-wrap',
			emptyText : '目前尚无记录',
			plugins:[
			   new Ext.DataView.DragSelector()
			],
			listeners : {
				'dblclick' : {
					fn : this.editRecord.createCallback(this),
					scope : this
				},
				'contextmenu' : {
					fn : this.showMenu,
					scope : this
				}
			}
		});

		this.toolbar = new Ext.Toolbar({
					id : 'PhoneBookFootBar',
					height : 28,
					bodyStyle : 'text-align:left',
					items : [{
						iconCls : 'btn-add',
						xtype : 'button',
						text : '添加',
						handler : this.createRecord
					}, '-', {
					     iconCls:'btn-del',
					     xtype:'button',
					     text:'删除',
					     handler:this.deleteRecord.createCallback(this)
					}]
				});

		this.phoneBookPanel = new Ext.Panel({
					region : 'center',
					tbar : this.toolbar,
					layout : 'fit',
//					autoHeight : true,
//					autoScroll:true,
					defaults:{
					   anchor : '96%,96%'
					},
					items : this.dataView,
					bbar : new Ext.PagingToolbar({
							pageSize : 15,
							store : this.store,
							displayInfo : true,
							displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
							emptyMsg : "当前没有记录"
						})
				});

		
	},// 初始化结束
	search : function(self) {
		var searchPanel = self.searchPanel;
		if (searchPanel.getForm().isValid()) {
			var store = self.dataView.getStore();
			var baseParam = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
			var deParams = Ext.urlDecode(baseParam);
			deParams.start = 0;
			deParams.limit = 15;
			store.baseParams = deParams;
			self.phoneBookPanel.getBottomToolbar().moveFirst();
		}
	},// 查询
	showMenu : function(view, index, node, e) {
		var nodes = view.getSelectedNodes();
		if(nodes.length<2){
			view.all.each(function(el) {
						view.deselect(el);
					});
			view.select(index, true);
		}
		nodes = view.getSelectedNodes();
		if (nodes != '' && nodes != null && nodes != 'undefined') {
			var menuItems=new Array();
			if(nodes.length==1){
			menuItems.push({
							text : '修改',
							scope : this,
							iconCls : 'btn-edit',
							handler : this.editRecord.createCallback(this)
						});
			}
			menuItems.push({
								text : '删除',
								scope : this,
								iconCls : 'btn-delete',
								handler : this.deleteRecord
										.createCallback(this)
							});
			var menus = new Ext.menu.Menu({
					items : menuItems
				});
			menus.showAt(e.getXY());
		}

	},// 显示菜单
	createRecord : function() {
		new PhoneBookForm().show();
	},// 创建新记录
	editRecord : function(self) {
		var nodes = self.dataView.getSelectedNodes();
		if (nodes != '' && nodes != null && nodes != 'undefined') {
			var phoneId = nodes[0].id;
			new PhoneBookForm({phoneId:phoneId}).show();
		}
	},// 编辑记录
	deleteRecord : function(self) {
		var nodes = self.dataView.getSelectedNodes();
		var store = self.dataView.getStore();
		if (nodes != '' && nodes != null && nodes != 'undefined') {
			var ids=new Array();
			for(var i=0;i<nodes.length;i++){
			   ids.push(nodes[i].id);
			}
			Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : __ctxPath
												+ '/communicate/multiDelPhoneBook.do',
										params : {
											ids : ids
										},
										method : 'post',
										success : function() {
											Ext.ux.Toast.msg("信息提示",
													"成功删除所选记录！");
											store.reload({
														params : {
															start : 0,
															limit : 15
														}
													});
										}
									});
						}
					});
		}else{
		    Ext.ux.Toast.msg("信息提示","请选择删除记录！");
		}
	}// 删除记录
});
