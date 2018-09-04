/**
 * @description 菜单管理
 * @class MenuView
 * @extends Panel
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-1-26PM
 */
MenuView = Ext.extend(Ext.Panel, {

	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponent();
		MenuView.superclass.constructor.call(this, {
			id : 'menuView',
			title : '菜单管理',
			iconCls : 'menu-m',
			layout : 'border',
			region : 'center',
			items : [this.treePanel]
		});
	},
	//实例化组件
	initUIComponent : function() {
		// treePanel
		this.treePanel = new Ext.tree.TreePanel({
			id : 'menuViewTreePanel',
			layout : 'form',
			region : 'center',
			anchor : '98% 98%',
			autoScroll : true,
			collapsible : true,
			loadMask : true,
			split : true,
			width : '98%',
			title : '菜单列表',
			tbar : new Ext.Toolbar({
				defaultType : 'button',
				items : [{
					text : '刷新',
					iconCls : 'btn-refresh',
					handler : function(){
						Ext.getCmp('menuViewTreePanel').root.reload();
					}
				},{
					text : '展开',
					iconCls : 'btn-expand',
					handler : function(){
						Ext.getCmp('menuViewTreePanel').expandAll();
					}
				},{
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function(){
						Ext.getCmp('menuViewTreePanel').collapseAll();
					}
				}]
			}),
			loader : new Ext.tree.TreeLoader({
				url : __ctxPath + '/menu/listMenu.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'dblclick' : function(node){}
			}
		});// end of this treePanel
		// 树的右键菜单的
		this.treePanel.on('contextmenu', contextmenu, this.treePanel);
		// 创建右键菜单 menu
		var treeMenu = new Ext.menu.Menu({
			id : 'MenuViewTreeMenu',
			items : [{
				text : '新建菜单',
				iconCls : 'btn-add',
				scope : this,
				handler : add
			},{
				text : '编辑菜单',
				iconCls : 'btn-edit',
				scope : this,
				handler : edit
			},{
				text : '删除菜单',
				iconCls : 'btn-del',
				scope : this,
				handler : del
			}]
		});
		//新增
		function add(){
			var node = selectedNode;
			var fm = Ext.getCmp('MenuForm'); // window
			if(fm == null || fm == 'undefined')
				fm = new MenuForm({
					parentId : node != null ? node.id : ''
				});
			fm.show();
		}
		//修改
		function edit(){
			var node = Ext.getCmp('menuViewTreePanel').getSelectionModel().getSelectedNode();
			if(node == null || node == 'undefined'){
				Ext.ux.Toast.msg('操作提示','请选择，菜单项！');
				return;
			}
			if(node.id == '0'){
				Ext.ux.Toast.msg('操作提示','对不起，公司名称不能修改！');
				return;
			}
			var fm = Ext.getCmp('MenuForm');
			if(fm == null || fm == 'undefined')
				fm = new MenuForm({
					id : node.id,
					text : node.text,
					isChild : !node.hasChildNodes()
				});
			fm.show();
		}
		//删除
		function del(){
			var node = selectedNode;
			if(node == null || node == 'undefined'){
				Ext.ux.Toast.msg('操作提示','请选择，菜单项！');
				return;
			}
			if(node.id == '0'){
				Ext.ux.Toast.msg('操作提示','对不起，公司名称不能修改！');
				return;
			}
			Ext.Msg.confirm('操作提示','你真的要删除菜单['+node.text+']吗？',function(btn){
				if(btn == 'yes'){
					Ext.Ajax.request({
						url : __ctxPath + '/menu/deleteMenu.do',
						params : {
							id : node.id
						},
						method : 'post',
						waitMsg : '数据正在提交，请稍后...',
						success : function(){
							Ext.ux.Toast.msg('操作提示','菜单删除成功！');
							Ext.getCmp('menuViewTreePanel').root.reload();
							if(curUserInfo.username=='admin')
								loadWestMenu('true');
							else
								Ext.ux.Toast.msg('操作提示','仅对开发用户开放刷新菜单功能!');
						}
					});
				}
			});
		}
		
		function contextmenu(node, e){
			selectedNode = new Ext.tree.TreeNode({
				id : node.id,
				text : node.text
			});
			treeMenu.showAt(e.getXY());
		}
		
	} // end of this initUIComponent
});