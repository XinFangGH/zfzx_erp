//加载完成后执行

var areaTree=function(funName,extRootControl){//extControl add by gao  尽职调查页面 需求要求  只支持2 3 级双击返回值功能
	Ext.onReady(function() {
	
	var anchor = '96.5%';
	Ext.BLANK_IMAGE_URL = basepath()+'ext3/resources/images/default/s.gif';
	Ext.QuickTips.init();
	//Ext.form.Field.prototype.msgTarget = 'side';
	var treeLoader = new Ext.tree.TreeLoader({
		//dataUrl : 'js/permission/tree-data-manager.json'
		dataUrl :__ctxPath +'/creditFlow/common/getAreaTreeAreaManagement.do'
	});
	var businessTree = new Ext.tree.TreePanel({
		border : false,
		iconCls : 'icon-nav',
		rootVisible : false,
		autoScroll : true,
		loader : treeLoader,
		
		//root : new Ext.tree.AsyncTreeNode(),
		root : new Ext.tree.AsyncTreeNode({
			id: '-1',
			text:'根'})
	});
	
	var ondblclicktree = function(n){
		var objArray = new Array();
		var node = n;
		if(extRootControl&&node.parentNode.id == '-1'){//根节点
			return;
		}
		for(i=0;;i++){
			objArray[i] = node;
			node = node.parentNode;
			if(node.id == '-1')
				break;
		}
		
		funName(objArray);
		dictionaryWindow.destroy();
		
		/*if(n.isLeaf()){
			for(i=0;;i++){
				objArray[i] = node;
				node = node.parentNode;
				if(node.id == '-1')
					break;
			}
			funName(objArray);
			dictionaryWindow.close();
		}*/
	}
	
	businessTree.addListener('dblclick',ondblclicktree);
	/**行业类别参照树*/
	var permissionmanager = new Ext.Panel({
		id : 'permissionmanager',
		//width: (screen.width-180)*0.5 - 30,
		height : 400,//高度自by chencc
		//autoHeight :true,//高度自动 by chencc
		frame : true,
		autoScroll : true ,
		titleCollapse : true,
		expandDefaults: {
			duration:.85
			},
			collapseDefaults: {
			duration:.85
			},
			items : businessTree
	});

	var dictionaryWindow = new Ext.Window({
		width: (screen.width-180)*0.4,
		title : '数据字典',
		height : 450 ,//高度自by chencc
		//autoHeight :true,//高度自动 by chencc
		collapsible : true,
		layout : 'form',
		buttonAlign : 'center',
		modal : true,
		resizable : false,
		items : [permissionmanager]
	});
	dictionaryWindow.show();
});
}
