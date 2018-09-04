function selectContractTemplateWin(funName ,onlymark){
	var anchor = '96.5%';
	var treeLoader = new Ext.tree.TreeLoader({
		dataUrl :'getByOnlyMarkDocumentTempletCall.do?onlymark='+onlymark
	});
	//var sm = new Ext.grid.CheckboxSelectionModel();
	var templateTree = new Ext.tree.TreePanel({
		border : false,
		iconCls : 'icon-nav',
		rootVisible : false,
		autoScroll : true,
		loader : treeLoader,
		root : new Ext.tree.AsyncTreeNode({
			id: '0',
			text:'根'
		})
	});
	
	var ondblclicktree = function(n){
		var objArray = new Array();
		var node = n;
		for(i=0;;i++){
			objArray[i] = node;
			node = node.parentNode;
			if(node.id == '0')
				break;
		}
		funName(objArray);
		templateWindow.close();
	}
	
	templateTree.addListener('dblclick',ondblclicktree);
	
	var templatePanel = new Ext.Panel({
		id : 'templatePanel',
		width : 415,
		height : 320,
		frame : true,
		autoScroll : true ,
		titleCollapse : true,
		expandDefaults: {
			duration:.85
		},
		collapseDefaults: {
			duration:.85
		},
		items : templateTree
	});

	var templateWindow = new Ext.Window({
		width: 420,
		title : '数据字典',
		height : 351 ,
		collapsible : true,
		layout : 'form',
		buttonAlign : 'center',
		modal : true,
		resizable : false,
		items : [templatePanel]
	});
	templateWindow.show();

}