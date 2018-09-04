
var TreeCombo=Ext.extend(Ext.form.ComboBox,{
	constructor:function(_cfg){
		 var config={
				store : new Ext.data.SimpleStore({
							fields : [],
							data : [[]]
				}),
				editable : false,
				mode : 'local',
				emptyText : "请选择",
				triggerAction : 'all',
				maxHeight : 200,
				tpl : "<tpl for='.'><div style='height:200px'><div id='"+_cfg.id+"tree'></div></div></tpl>",
				onSelect : Ext.emptyFn
			} || _cfg;
			
		Ext.apply(this,config)
		
		TreeCombo.superclass.constructor.call(this,config);
		
		var tree = new Ext.tree.TreePanel({
			id:_cfg.id+'Tree',
			height:200,
			autoScroll: true,
			split: true,
			loader: new Ext.tree.TreeLoader({url:_cfg.url}),
		    root:
		    	new Ext.tree.AsyncTreeNode({
		        expanded: true
		    }),
		    rootVisible: false
		});
		var curCombo=this;
		//用于解决下拉存在二级菜单的时候，点击自动收起的功能
		curCombo.on('collapse',function(combo){
			var selectedNode = tree.getSelectionModel().getSelectedNode();
			if(selectedNode==null){
				curCombo.expand();
				return;
			}else{
				tree.getSelectionModel().clearSelections();
			}
		},this);
		tree.on('click', function(node) {
					var editField = Ext.getCmp(_cfg.valId);//根据要修改的域的ID取得该域
					if(node.id!=null&&node.id!=''){
						curCombo.setValue(node.text);
						curCombo.id=node.id;
						curCombo.collapse();
						if(editField !=null){
							editField.setValue(node.id); //把树结点的值赋给要修改的域
						}
					}
		});
		this.on('expand', function() {
					tree.render(_cfg.id+'tree');
		});
		
	}
});


Ext.reg('treecombo',TreeCombo);
