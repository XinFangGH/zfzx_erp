Ext.ns("zhiwei");
Ext.ns("zhiwei.ux");
/**
 * @company 智维软件有限公司
 * @createtime 2010-01-02
 * @author csx
 * @class TreePanelEditor
 * @extends Ext.tree.TreePanel
 * @description 树菜单编辑器,可带右键操作菜单，使用方式示例见ArchiveTypeTempView.js
 */
zhiwei.ux.TreePanelEditor=Ext.extend(Ext.tree.TreePanel,{
	/**
	 * 是否显示上下文菜单
	 * @type Boolean
	 */
	showContextMenu:true,
	/**
	 * URL
	 * @type 
	 */
	url:null,
	/**
	 * 右键菜单
	 * @type 
	 */
	contextMenu:null,
	/**
	 * 右键菜单项
	 * @type 
	 */
	contextMenuItems:null,
	/**
	 * 选择树节点
	 * @type 
	 */
	selectedNode:null,
	/*
	 * 过滤
	 */
	filter : true,
	/**
	 * 构造函数
	 */
	constructor:function(_cfg){
		if(!_cfg){
			_cfg={};
		}
		Ext.apply(this,_cfg);
		//从父类中拷贝构造
		zhiwei.ux.TreePanelEditor.superclass.constructor.call(this,{
			tbar: this.filter?new Ext.Toolbar({items:['Filter:', {
				 	 xtype:'trigger' ,
				 	 triggerClass:'x-form-clear-trigger',
					 scope:this,
					 onTriggerClick:function() {
						 this.setValue('');
						 fil.clear();
					 } ,
					 width :100,
					 id:'filter',
					 enableKeyEvents:true,
					 listeners:{
					 keyup:{buffer:150, fn:function(field, e) {
						 if(Ext.EventObject.ESC == e.getKey()) {
						    field.onTriggerClick();
						 }
						 else {
							 var val = this.getRawValue();
							 var re = new RegExp('.*' + val + '.*', 'i');
							 fil.clear();
							 fil.filter(re, 'text');
					     }
					 }}
					 }
					 },'-','-',
					{
						xtype : 'button',
						iconCls : 'btn-refresh',
						text : '刷新',
						scope:this,
						handler : function() {
							this.root.reload();
						}
					},'-',{
						xtype : 'button',
						text : '展开',
						iconCls : 'btn-expand',
						scope:this,
						handler : function() {
							this.expandAll();
						}
					},'-', {
						xtype : 'button',
						text : '收起',
						iconCls : 'btn-collapse1',
						scope:this,
						handler : function() {
							this.collapseAll();
						}
					}
					]}):null,
			loader : new Ext.tree.TreeLoader({
					url:this.url
			}),
			root : new Ext.tree.AsyncTreeNode({
				    expanded : true
			}),
			listeners : {
					scope:this,
					'click' : function(node){
						if(_cfg.onclick){
							this.selectedNode=node;
							var scope=this.scope?this.scope:this;
							_cfg.onclick.call(scope,node);
						}
					},
					'dblclick':function(node,e){
						if(_cfg.dblclick){
							this.selectedNode=node;
							var scope=this.scope?this.scope:this;
							_cfg.dblclick.call(scope,node);
						}
					}
			},
			rootVisible : false
		});
		if(this.contextMenuItems){
			//初始化右键的菜单
			this.initContextMenu();
		}
		if(true == this.filter) {

			var Filter = Ext.ux.tree.TreeFilterX ? Ext.ux.tree.TreeFilterX : Ext.tree.TreeFilter;
			var fil = new Filter(this, {autoClear:true});
		}
		
	},//end of constructor

	/**
	 * 右键菜单
	 */
	initContextMenu:function(){
	
		if(this.showContextMenu){
			this.contextMenu= new Ext.menu.Menu({});
			if(this.contextMenuItems!=null){
				this.contextMenu.add(this.contextMenuItems);
			}
			//树的右键菜单的
			this.on('contextmenu', this.contextHandler, this);
		}
	},
	contextHandler:function contextmenu(node, e) {
		if(this.treeType=='org'){
			this.selectedNode = new Ext.tree.TreeNode({
				id : node.id,
				text : node.text,
				orgType: node.attributes.orgType,
				orgDem: node.attributes.orgDem
			});
		}else if(this.treeType=='groupCompany'){
			  
			if(typeof(node.parentNode.text)=="undefined"){
			
				this.contextMenu.showAt(e.getXY());
			}
			else{
				
			    return false;
			}
		}else{
			this.selectedNode = new Ext.tree.TreeNode({
				id : node.id,
				text : node.text
			});
		}
		this.contextMenu.showAt(e.getXY());
	}
	
});
Ext.reg("treePanelEditor",zhiwei.ux.TreePanelEditor);