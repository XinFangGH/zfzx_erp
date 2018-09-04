GlobalTypeSelector=Ext.extend(Ext.Window,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		
		this.buttons=[{
			text:'选择',
			iconCls:'btn-save',
			scope:this,
			handler:function(){
				if(this.callback){
					if(!this.isSingle){
						var typeIds=Ext.getCmp('selectedIds').getValue();
						var typeNames=Ext.getCmp('selectedTypes').getValue();
						this.callback.call(this,typeIds,typeNames);
					}else{
							var rows = this.rightPanel.getSelectionModel().getSelections();
							if(rows.length>0){
								this.callback.call(this,rows[0].data.proTypeId,rows[0].data.typeName);
							}
					}
				}
				this.close();
			}
		},{
			text:'取消',
			iconCls:'btn-cancel',
			scope:this,
			handler:function(){
				this.close();
			}
		}];
		
		GlobalTypeSelector.superclass.constructor.call(this,{
			title:config.title?config.title:'分类选择',
			layout:'border',
			height:480,
			width:520,
			buttonAlign:'center'
		});
	},
	initUIComponents:function(){
		this.leftPanel=new Ext.tree.TreePanel({
			title : '分类',
			collapsible : true,
			autoScroll:true,
			split : true,
			region:'west',
			width:140,
			rootVisible : false,
			loader : new Ext.tree.TreeLoader({
					url:__ctxPath+'/system/treeGlobalType.do?catKey=' + this.catKey
			}),
			root : new Ext.tree.AsyncTreeNode({
				    expanded : true
			}),
			listeners : {
					'click' : function(node){
						this.selectedNode=node;
						var store=Ext.getCmp('proTypeCenGrid').getStore();
						store.baseParams={parentId:node.id};
						store.reload();
					}
					
			}
		});
		
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/system/subGlobalType.do?catKey=" + this.catKey,
					baseParams:{parentId:0},
					root : 'result',
					remoteSort : true,
					fields : [{
								name : 'proTypeId',
								type : 'int'
							}, 'typeName', 'nodeKey',  'sn']
		});
		
		this.store.load();
		
		var conf={singleSelect: this.isSingle?this.isSingle:false};
		if(!this.isSingle){
			Ext.apply(conf,{
				listeners:{
					'rowselect':function( selModel,rowIndex, rec){
							var selIds=Ext.getCmp('selectedIds');
							var selTypes=Ext.getCmp('selectedTypes');
							
							selIds.setValue(selIds.getValue() + rec.data.proTypeId + ';');
							selTypes.setValue(selTypes.getValue() + rec.data.typeName + ';');
					},
					'rowdeselect':function( selModel,rowIndex, rec){
							var selIds=Ext.getCmp('selectedIds');
							var selTypes=Ext.getCmp('selectedTypes');
							
							selIds.setValue(selIds.getValue().replace(rec.data.proTypeId + ';',''));
							selTypes.setValue(selTypes.getValue().replace(rec.data.typeName + ';',''));
					}
				}
			});
		}
		var sm = new Ext.grid.CheckboxSelectionModel(conf);
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'proTypeId',
								dataIndex : 'proTypeId',
								hidden : true
							}, {
								header : '名称',
								dataIndex : 'typeName',
								editor:new Ext.form.TextField({
									allowBlank:false
								})
							}],
					defaults : {
						sortable : true,
						menuDisabled : false
					}
		});
		
		this.rightPanel= new Ext.grid.GridPanel({
			region:'center',
			title:'分类列表',
			id:'proTypeCenGrid',
	        store: this.store,
	        sm:sm,
	        cm: cm,
	        height:450
		});
		this.items=[
				this.leftPanel,
				this.rightPanel
			]
			
		if(!this.isSingle){
			this.southPanel=new Ext.Panel({
				region:'south',
				height:38,
				bodyStyle:'padding:5px',
				border:false,
				layout:'form',
				items:[
					{
						xtype:'hidden',
						id:'selectedIds'
					},
					{
						xtype:'compositefield',
						fieldLabel:'选择的类型',
						items:[
							{
								xtype:'textfield',
								width:300,
								readOnly:true,
								id:'selectedTypes'
							},{
								xtype:'button',
								iconCls:'btn-del',
								text:'清除',
								handler:function(){
									Ext.getCmp('selectedIds').setValue('');
									Ext.getCmp('selectedTypes').setValue('');
								}
								
							}
						]
					}
				]
			});//end of southPanel
			
			this.items.push(this.southPanel);
		}
	}
});