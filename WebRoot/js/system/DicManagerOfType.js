/**
 * 分类管理
 * @class ProModalManager
 * @extends Ext.Panel
 */
DicManagerOfType=Ext.extend(Ext.Panel,{
	nodeKey : "",
	constructor:function(config){
		if(config.nodeKey){
			this.nodeKey=config.nodeKey
		}
		Ext.applyIf(this,config);
		this.initUIComponents();
		DicManagerOfType.superclass.constructor.call(this,{
            id:'DicManagerOfType'+this.nodeKey,
			height:450,
			autoScroll:true,
			layout:'border',
			title:'数据字典分类管理',
			iconCls:"menu-dictionary",
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
		var nodeKey=this.nodeKey
        this.leftPanel = new Ext.Panel({
			title:'数据字典分类',
			region : 'west',
			layout : 'anchor',
			collapsible : true,
			split : true,
			width : 200,
			autoHeight:true,
			items : [

			{
				xtype : 'treePanelEditor',
				id:'dicType'+nodeKey,
				split : true,
				rootVisible : false,
				border : false,
				height : Ext.getBody().getViewSize().height-115,
				autoScroll : true,
				scope : this,
				url:__ctxPath+'/system/treeOfTypeGlobalType.do?catKey=DIC&nodeKey='+nodeKey,
				onclick : function(node) {
					this.selectedNode=node;
	             	var parentId=node.id;
	             	var grid=Ext.getCmp('dicGrid'+nodeKey);
	             	if(grid!=null){
	             		if(parentId==0){
	             			grid.setTitle('所有数据字典');
	             		}else{
	             			grid.setTitle(node.text+'-数据字典');
	             		}
	             		var store=grid.getStore();
	             		store.url = __ctxPath + "/system/listDictionary.do";
	             		store.baseParams={
	             		parentId:parentId,
	             		leaf:node.leaf};
						store.reload();
						grid.getBottomToolbar().moveFirst();
	             	}
    
				},
               contextMenuItems : [
    
                     {  
                         text : '新建分类',  
                         scope : this,  
                         iconCls:'btn-add',
                         hidden : isGranted('_DictionaryAddType'+nodeKey)?false:true,
                         handler : function(){
                         	var dicType=Ext.getCmp('dicType'+nodeKey);
                         	var parentId=dicType.selectedNode.id;

                         	var globalTypeForm=new GlobalTypeForm({
                         		parentId:parentId,
                         		catKey:'DIC',
                         		callback:function(){
                         			dicType.root.reload();
                         		}
                         	});
                         	globalTypeForm.show();
                         }  
                     },{
                     	text:'修改分类',
                     	scope:this,
                     	iconCls:'btn-edit',
                     	hidden : isGranted('_DictionaryEditType'+nodeKey)?false:true,
                     	handler:function(){
                     		var dicType=Ext.getCmp('dicType'+nodeKey);
                         	var proTypeId=dicType.selectedNode.id;
                         	
                         	var globalTypeForm = new GlobalTypeForm({
                         		proTypeId:proTypeId,
                         		callback:function(){
                         			Ext.getCmp('dicType'+nodeKey).root.reload();
                         		}
                         	});
                         	globalTypeForm.show();
                     	}
                     },{
                     	text:'添加字典项',
                     	scope:this,
                     	iconCls:'btn-add',
                     	hidden : isGranted('_DictionaryAdd'+nodeKey)?false:true,
                     	handler:function(){
                     		var gridPanel=this.centerPanel;
							var dicType=Ext.getCmp('dicType'+nodeKey);
							var selectedNode=dicType.selectedNode;
							var typeName=dicType.selectedNode.text;
							var parentId=0;
				            if(selectedNode!=null){
				            	 parentId=selectedNode.id;
				            }
				            if(parentId==0){
				            	Ext.ux.Toast.msg('操作信息','请从左选择字典分类');
				            	return;
				            }
							new DictionaryForm({
								parentId:parentId,
								typeName:typeName,
								callback:function(){
									gridPanel.getStore().reload();
								}
							}).show();
                     	}
                     },{

                         	text:'删除分类',
                         	scope:this,
                         	iconCls:'btn-del',
                         	hidden : isGranted('_DictionaryDelType'+nodeKey)?false:true,
                         	handler:function(){

                         		var dicType=Ext.getCmp('dicType'+nodeKey);
                             	var proTypeId=dicType.selectedNode.id;
                             	Ext.Msg.confirm("提示!",'确定要删除吗？',
                        		function(btn) {
                                    if (btn == "yes") {
		                             	Ext.Ajax.request({
							                   url:  __ctxPath + '/system/deleteGlobalType.do',
							                   method : 'POST',
							                   params : {
														proTypeId : proTypeId
													},
							                  success : function(response,request) {
				
													var xx=response.responseText.toString().trim();
												
				                            		if(xx=="{success:false}"){
				                            	
				                            			Ext.ux.Toast.msg('操作信息',"该分类下含有数据字典项，不能删除，请删除数据字典项，在删除分类");
				                            			return;
				                            		}else{
				                            			Ext.ux.Toast.msg('操作信息',"删除成功");
				                            			dicType.root.reload();
				                            		}
						                      }
				                             });  
                        			}
                             	});
                             /*	var globalTypeForm = new GlobalTypeForm({
                             		proTypeId:proTypeId,
                             		callback:function(){
                             			Ext.getCmp('dicType').root.reload();
                             		}
                             	});
                             	globalTypeForm.show();*/
                     	
                            }
                         
                     } 
               
                ]
			}]
		}

		);

					
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + '/system/listDictionary.do',
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'dicId',
								type : 'int'
							}, 'itemName', 'itemValue', 'descp','sn','globalType','dicKey']
				});
	
		this.store.setDefaultSort('sn');
		// 加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'ID',
								dataIndex : 'dicId',
								hidden : true
							}, {
								header : 'parentId',
								dataIndex : 'globalType',
								hidden : true,
								renderer:function(globalType){
								if(globalType)return globalType.parentId;
								else return 0;
							}
							},{
								header : 'proTypeId',
								dataIndex : 'globalType',
								hidden : true,
								renderer:function(globalType){
								if(globalType)return globalType.proTypeId;
								
							}
							},{
								header : '分类名称',
								dataIndex : 'itemName'
							},{
								header:'值',
								dataIndex:'itemValue',
								editor:new Ext.form.TextField({allowBlank:false})
							},{
								header:'描述',
								dataIndex:'descp',
								editor:new Ext.form.TextField()
							}, {
								header : '序号',
								dataIndex : 'sn',
								editor:new Ext.form.TextField()
							}, {
								header : 'Key',
								dataIndex : 'dicKey',
								
								editor:new Ext.form.TextField({readOnly:true})
							}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
		});
	
		var tbar=new Ext.Toolbar({
			items:[{
					text:'添加数据字典',
					iconCls:'btn-add',
					scope:this,
					hidden : isGranted('_DictionaryAdd'+nodeKey)?false:true,
					handler:function(){
						var gridPanel=this.centerPanel;
						var dicType=Ext.getCmp('dicType'+nodeKey);
						
						var selectedNode=dicType.selectedNode;
						var typeName=dicType.selectedNode.text;
						
						var parentId=0;
			            if(selectedNode!=null){
			            	 parentId=selectedNode.id;
			            }
			            	
			            if(parentId==0){
			            	Ext.ux.Toast.msg('操作信息','请从左选择字典分类');
			            	return;
			            }
						new DictionaryForm({
							parentId:parentId,
							typeName:typeName,
							callback:function(){
								gridPanel.getStore().reload();
							}
						}).show();
					}
				},
				'-',
				{
					xtype:'button',
					text:'保存',
					iconCls:'btn-save',
					scope:this,
					handler:function(){
						var params = [];
						var grid=this.centerPanel;
						var store=grid.getStore();
						
						for (var i = 0; i < store.getCount(); i += 1) {
							var record = store.getAt(i);
							if(record.dirty){
								params.push(record.data);
							}
						}
						
						if (params.length == 0) {
						        Ext.ux.Toast.msg('信息', '没有对数据进行任何更改');
						        return;
					    }
						Ext.Ajax.request({
								method : 'post',
								url : __ctxPath + '/system/mulSaveDictionary.do',
								params : { data : Ext.encode(params)},
								success : function(response) {
								
										Ext.ux.Toast.msg('操作信息', '成功设置');
										store.reload();
										grid.getView().refresh();
								
								},
								failure : function(request) {
									Ext.ux.Toast.msg('操作信息', '设置出错，请联系管理员!');
								}
						});
	
					}
				},new Ext.Toolbar.Separator({
					hidden : isGranted('_DictionaryDel'+nodeKey)?false:true
				}),{
					text:'删除',
					iconCls:'btn-del',
					scope:this,
					hidden : isGranted('_DictionaryDel'+nodeKey)?false:true,
					handler:function(){
						var gridPanel = this.centerPanel;
						var selectRecords = gridPanel.getSelectionModel().getSelections();
						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.dicId);
						}
						
						Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
											url : __ctxPath + '/system/multiDelDictionary.do',
											params : {
												ids : ids
											},
											method : 'POST',
											success : function(response, options) {
												Ext.ux.Toast.msg('操作信息','成功删除该数字字典！');
												gridPanel.getStore().reload();
											},
											failure : function(response, options) {
												Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
											}
										});
							}
						});
					}
				},new Ext.Toolbar.Separator({
					hidden : isGranted('_DictionaryChange'+nodeKey)?false:true
				}),{
					text:'转移类型',
					iconCls:'',
					hidden : isGranted('_DictionaryChange'+nodeKey)?false:true,
					handler:function(){
							var grid = Ext.getCmp('dicGrid'+nodeKey);
							var rows = grid.getSelectionModel().getSelections();
							var dicIds = '';
							if(rows.length==0){
								Ext.ux.Toast.msg('操作信息','请选择记录!');
								return;
							}
							for (var i = 0; i < rows.length; i++) {
								if (i > 0) {
									dicIds += ',';
								}
								dicIds += rows[i].data.dicId;
							}
							
							new DicTypeChangeWin(
								{dicIds:dicIds,callback:function(){
								grid.getStore().reload();
							}}
							).show();
					}
				},new Ext.Toolbar.Separator({
					hidden : isGranted('_DictionaryEdit'+nodeKey)?false:true
				}),{
					text:'编辑数据字典项',
					iconCls:'btn-edit',
					hidden : isGranted('_DictionaryEdit'+nodeKey)?false:true,
					handler:function(){
							var grid = Ext.getCmp('dicGrid'+nodeKey);
							var rows = grid.getSelectionModel().getSelections();
							var dicIds = '';
							if(rows.length==0){
								Ext.ux.Toast.msg('操作信息','请选择记录!');
								return;
							}else if(rows.length>1){
								Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
								return;
							}else{
								new DictionaryForm({
									dicId:rows[0].data.dicId,
									typeName:rows[0].data.itemName,
									callback:function(){
									grid.getStore().reload();
								    }
								}).show()
							}
						
					}
				}
			]
		});
		
		this.centerPanel= new Ext.grid.EditorGridPanel({
			region:'center',
			title:'数据字典管理',
			tbar:tbar,
			clicksToEdit: 1,
			id:'dicGrid'+nodeKey,
	        store: this.store,
	        sm:sm,
	        cm: cm,
	        height:450,
	        viewConfig : {
						forceFit : true,
						autoFill : true
					},
			bbar : new Ext.PagingToolbar({
						pageSize : 25,
						store : this.store,
						displayInfo : true,
						displayMsg : '当前页记录索引{0}-{1}， 共{2}条记录',
						emptyMsg : "当前没有记录"
			})
		});
		
	}//end of initUIComponents
	
});