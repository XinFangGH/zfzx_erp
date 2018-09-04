/**
 * 词条管理
 * @class SystemWordView
 * @extends Ext.Panel
 */
SystemWordView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		SystemWordView.superclass.constructor.call(this,{
			id:'SystemWordView',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'词条管理',
			iconCls:'menu-globalType',
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
		 this.leftPanel=new Ext.Panel(
			{
			 	region:'west',
			 	title:'分类管理',
			 	layout:'anchor',
			 	collapsible : true,
				split : true,
				width : 200,
			 	items:[	
			 		{  
			             xtype:'treePanelEditor',
			             id:'wordTypeTree',
			             title:'分类树', 
			             split : true, 
			             border:false,
			             height:380,
			             autoScroll:true,
			             url:__ctxPath+'/system/treeSystemWord.do?wordKey='+this.wordKey,
			             onclick:function(node){
			             	var parentId=node.id;
			             	var grid=Ext.getCmp('systemWordGrid');
			             	if(grid!=null){
			             		var store=grid.getStore();
			             		store.url = __ctxPath + "/system/subSystemWord.do?wordKey="+this.wordKey;
			             		
			             		store.baseParams={parentId:parentId};
								store.reload();
			             	}
			             },
			             contextMenuItems:[  
			                         {  
			                             text : '新建分类',  
			                             scope : this,  
			                             iconCls:'btn-add',  
			                             handler : function(){
			                             	var wordTypeTree=Ext.getCmp('wordTypeTree');
			                             	var parentId=wordTypeTree.selectedNode.id;

			                             	var systemWordTypeForm=new SystemWordTypeForm({
			                             		parentId:parentId,
			                             		callback:function(){
			                             			Ext.getCmp('wordTypeTree').root.reload();
			                             			Ext.getCmp('systemWordGrid').getStore().reload();
			                             		}
			                             	});
			                             	systemWordTypeForm.show();
			                               
			                             }  
			                         },{
			                         	text:'修改分类',
			                         	scope:this,
			                         	iconCls:'btn-edit',
			                         	handler:function(){
			                         		var wordTypeTree=Ext.getCmp('wordTypeTree');
			                             	var wordTypeId=wordTypeTree.selectedNode.id;
			                             	
			                             	var systemWordTypeForm=new SystemWordTypeForm({
			                             		wordId:wordTypeId,
			                             		callback:function(){
			                             			Ext.getCmp('wordTypeTree').root.reload();
			                             			Ext.getCmp('systemWordGrid').getStore().reload();
			                             		}
			                             	});
			                             	systemWordTypeForm.show();
			                         	}
			                         } 
			                         ]
					 }
			 	]
			 }
			 
		 );
					
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/system/subSystemWord.do",
					baseParams:{parentId:0},
					root : 'result',
					remoteSort : true,
					fields : [{
								name : 'wordId',
								type : 'int'
							}, 'wordName', 'parentId', 'wordKey',  'wordDescription']
				});
		//this.store.setDefaultSort('proTypeId', 'desc');
		
		this.store.load();
//		this.rowActions = new Ext.ux.grid.RowActions({
//			header : '管理',
//			width : 80,
//			actions : [{
//						iconCls : 'btn-last',
//						qtip : '向下',
//						style : 'margin:0 3px 0 3px'
//					}, {
//						iconCls : 'btn-up',
//						qtip : '向上',
//						style : 'margin:0 3px 0 3px'
//					}]
//		});
//		this.rowActions.on('action', this.onRowAction, this);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'wordId',
								dataIndex : 'wordId',
								hidden : true
							}, {
								header : '名称',
								dataIndex : 'wordName'
							}, {
								header : '词条Key',
								dataIndex : 'wordKey'
							},{
								header : '词条描述',
								width : 730,
								dataIndex : 'wordDescription'
							}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
		});
	
		var tbar=new Ext.Toolbar({
			items:[
			{
				text:'添加',
				iconCls:'btn-add',
				handler:function(){
					var wordTypeTree=Ext.getCmp('wordTypeTree');
					var selectedNode=wordTypeTree.selectedNode;
                 	
                 	if(!selectedNode){
                 		Ext.ux.Toast.msg('操作信息','请选择左树中的分类！');
                 		return;
                 	}
					if(selectedNode.hasChildNodes()) {
						Ext.ux.Toast.msg('操作信息','请选择最后一级节点分类！');
                 		return;
					}
                 	var systemWordForm=new SystemWordForm({
                 		isReadOnly : false,
                 		parentId:selectedNode.id,
                 		callback:function(){
                 			Ext.getCmp('systemWordGrid').getStore().reload();
                 		}
                 	});
                 	systemWordForm.show();
				}
			},'-',{
				xtype:'button',
				text:'编辑',
				iconCls:'btn-save',
				scope:this,
				handler:function(){
					var selRs = this.centerPanel.getSelectionModel().getSelections();
					if(selRs.length==0){
					   Ext.ux.Toast.msg('操作信息','请选择一条记录！');
					   return;
					}
					if(selRs.length>1){
					   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
					   return;
					}
					var systemWordForm=new SystemWordForm({
                 		isReadOnly : true,
                 		wordId : this.centerPanel.getSelectionModel().getSelected().data.wordId,
                 		callback : function(){
                 			Ext.getCmp('systemWordGrid').getStore().reload();
                 		}
                 	});
                 	systemWordForm.show();
				}
			},{
				text:'删除',
				iconCls:'btn-del',
				scope:this,
				handler:function(){
					var gridPanel = this.centerPanel;
					var selectRecords = gridPanel.getSelectionModel().getSelections();
					if (selectRecords.length == 0) {
						Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
						return;
					}
					var ids = Array();
					for (var i = 0; i < selectRecords.length; i++) {
						ids.push(selectRecords[i].data.wordId);
					}
					
					Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : __ctxPath + '/system/multiDelSystemWord.do',
										params : {
											ids : ids
										},
										method : 'POST',
										success : function(response, options) {
											Ext.ux.Toast.msg('操作信息','删除成功！');
											gridPanel.getStore().reload();
										},
										failure : function(response, options) {
											Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
										}
									});
						}
					});
				
				}
			}
			]
		});
		
		this.centerPanel= new Ext.grid.EditorGridPanel({
			region:'center',
			title:'词条列表',
			tbar:tbar,
			clicksToEdit: 1,
			id:'systemWordGrid',
	        store: this.store,
//	        plugins : this.rowActions,
	        sm:sm,
	        cm: cm,
	        height:450
		});
		
	},//end of initUIComponents
	
	onRowAction : function(gridPanel, record, action, row, col) {
		var grid=Ext.getCmp('globalTypeCenGrid');
		var store=grid.getStore();
		switch (action) {
			case 'btn-up' :
				if(row==0){
					Ext.ux.Toast.msg('操作信息','已经为第一条!');
					return ;
				}
				
				var rd1=store.getAt(row-1);
				var rd2=store.getAt(row);
				store.removeAt(row);
				store.removeAt(row-1);
				store.insert(row-1,rd2);
				store.insert(row,rd1);
				break;
			case 'btn-last' :
				if(row==store.getCount()-1){
					Ext.ux.Toast.msg('操作信息','已经为最后一条!');
					return ;
				}
				var rd1=store.getAt(row);
				var rd2=store.getAt(row+1);
				
				store.removeAt(row+1);
				store.removeAt(row);
			
				store.insert(row,rd2);
				store.insert(row+1,rd1);

				break;
			default :
				break;
		}
	}
});