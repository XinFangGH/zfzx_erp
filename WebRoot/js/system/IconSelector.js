/**
 * @description 图标选择
 * @class IconSelector
 * @extend Window
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-2-16PM
 */
var IconSelector = {
	// 创建window对象
	getView : function(callback) {
		var iconTree = new Ext.tree.TreePanel( {
			id : 'iconSelectorTreePanel',
			region : 'west',
			border : false,
			width : 170,
			collapsible : true,
			autoScroll : true,
			split : true,
			title : '菜单列表',
			tbar : new Ext.Toolbar( {
				defaultType : 'button',
				items : [ {
					text : '刷新',
					iconCls : 'btn-refresh',
					handler : function() {
						Ext.getCmp('iconSelectorTreePanel').root.reload();
					}
				}, {
					text : '展开',
					iconCls : 'btn-expand',
					handler : function() {
						Ext.getCmp('iconSelectorTreePanel').expandAll();
					}
				}, {
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function() {
						Ext.getCmp('iconSelectorTreePanel').collapseAll();
					}
				} ]
			}),
			loader : new Ext.tree.TreeLoader( {
				url : __ctxPath + '/menu/loadTreeIcon.do'
			}),
			root : new Ext.tree.AsyncTreeNode( {
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'click' : function(node) {
					if (node != null) {
						var store = Ext.getCmp("iconSelectorDataView").getStore();
						store.reload( {
							params : {
								id : node.id
							}
						});
					}
				}
			}
		}); // end of this iconTree

		var imageStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : __ctxPath + '/menu/listIcon.do'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'icon',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [ 'text' , 'url']
			}),
			remoteSort : true
		});
		imageStore.load( {
			params : {
				start : 0,
				limit : 10
			}
		});
		var tpl = new Ext.XTemplate(
			'<tpl for=".">',
			'<div style="width:20px;height:20px;float:left;" class="thumb-wrap" id="{text}">',
			'<img align="middle" src="'+__ctxPath+'/images/{url}" title="{text}"/>', 
			'</div>', 
			'</tpl>');
		var dataView = new Ext.DataView( {
			id : 'iconSelectorDataView',
			layout : 'form',
			region : 'center',
			width : '100%',
			height : '100%',
			title : '图标展示',
			store : imageStore,
			tpl : tpl,
			multiSelect : true,
			overClass : 'x-view-over',
			itemSelector : 'div.thumb-wrap',
			bodyStyle : 'padding:4px',
			emptyText : '目前尚无记录',
			autoScroll : true,
			listeners : {
				'dblclick' : {
					fn : function(dv) {
						if (dv.getSelectedNodes() != null) {
							var node = dv.getSelectedNodes()[0];
							if (callback != null)
								callback.call(this, node.id);
							win.close();
						}
					}
				}
			}
		}); // end of this dataView
		var dataPanel = new Ext.Panel( {
			region : 'center',
			autoScroll : true,
			width : '100%',
			height : '100%',
			items : dataView
		}); // end of this dataPanel

		var win = new Ext.Window( {
			id : 'iconSelectorWin',
			title : '图标选择',
			iconCls : 'menu-icon',
			layout : 'border',
			region : 'center',
			maximizable : true,
			width : 600,
			height : 400,
			border : false,
			modal : true,
			buttonAlign : 'center',
			buttons : [ {
				text : '确定',
				iconCls : 'btn-ok',
				handler : function() {
					var dv = Ext.getCmp('iconSelectorDataView');
					var nodes = dv.getSelectedNodes();
					if (dv != null && nodes != null && nodes.length > 0) {
						var node = dv.getSelectedNodes()[0];
						if (callback != null)
							callback.call(this, node.id);
					} else
						callback.call(this, '');
					Ext.getCmp('iconSelectorWin').close();
				}
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : function() {
					Ext.getCmp('iconSelectorWin').close();
				}
			} ],
			items : [ iconTree, dataPanel ]
		}); // end of this win
		return win;
	}
};