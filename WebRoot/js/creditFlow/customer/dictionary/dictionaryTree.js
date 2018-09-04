//加载完成后执行

var selectDictionary=function(val,funName){
	Ext.onReady(function() {
	var anchor = '96.5%';
	Ext.BLANK_IMAGE_URL = __ctxPath+ '/ext/resources/images/default/s.gif';

	Ext.QuickTips.init();
	var treeLoader = new Ext.tree.TreeLoader({
		dataUrl :__ctxPath +'/creditFlow/multiLevelDic/getDictionaryTreeWindowAreaDic.do',
		baseParams : {lable : val}
	});
	var businessTree = new Ext.tree.TreePanel({
		border : false,
		iconCls : 'icon-nav',
		rootVisible : false,
		autoScroll : true,
		loader : treeLoader,
		root : new Ext.tree.AsyncTreeNode({
			id: '-1',
			text:'根'})
	});
	
	var ondblclicktree = function(n){
		var objArray = new Array();
		var node = n;
		if(val=='hangye'||val=='area'){
			if(n.isLeaf()){
				for(i=0;;i++){
					objArray[i] = node;
					node = node.parentNode;
					if(node.id == '-1')
						break;
				}
				funName(objArray);
				dictionaryWindow.close();
			}else{
				//Ext.Msg.alert("提示信息","该节点不能被选择！")//注释by gao ,需求不要求弹出这句话.....
			}
		}else{
			for(i=0;;i++){
				objArray[i] = node;
				node = node.parentNode;
				if(node.id == '-1')
					break;
			}
			funName(objArray);
			dictionaryWindow.destroy();
		}
		
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
