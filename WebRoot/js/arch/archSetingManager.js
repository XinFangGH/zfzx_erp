/**
 * 分类管理
 * @class ProModalManager
 * @extends Ext.Panel
 */
archSetingManager=Ext.extend(Ext.Panel,{
	rootId:null,
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		archSetingManager.superclass.constructor.call(this,{
			id:'archSetingManager',
			iconCls : "menu-archSet",
			height:450,
			autoScroll:true,
			layout:'border',
			title:'档案系统设置',
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
		

		//查询根结点
		var rootId=null;
		Ext.Ajax.request({
					url : __ctxPath + '/system/listGlobalType.do',
					method : 'POST',
					async : false,
					success : function(response, opts) {
						var obj = Ext.decode(response.responseText);
						rootId=obj.result[0].proTypeId;
					},

					failure : function(response, opts) {

					},
					params : {
						Q_catKey_S_EQ:'DIC',
						Q_parentId_L_EQ:0,
						Q_nodeKey_S_EQ:'arch'
					}
				});
	
		this.rootId=rootId;
		

		 this.leftPanel=new zhiwei.ux.TreePanelEditor(
			{
			 			 region:'west', 
			             id:'archDicType',
			             title:'档案管理分类', 
			             collapsible : true,
			             split : true, 
			             height:450,
			             width:160,
			             autoScroll:true,
			             url:__ctxPath+'/arch/treeArchType.do?catKey=DIC&rootId='+this.rootId,
			             onclick:function(node){
							this.selectedNode=node;
			             	var parentId=node.id;
			             	var grid=Ext.getCmp('archDicGrid');
			             	if(grid!=null){
			             		if(parentId==0){
			             			grid.setTitle('所有设置');
			             		}else{
			             			grid.setTitle(node.text+'-设置');
			             		}
			             		var store=grid.getStore();
			             		store.url = __ctxPath + "/system/listDictionary.do";
			             		store.baseParams={parentId:parentId};

								store.reload();
			             	}
			             	
			             }
						
//						,
//			             contextMenuItems:[  
//			                         {  
//			                             text : '新建分类',  
//			                             scope : this,  
//			                             iconCls:'btn-add',  
//			                             handler : function(){
//			                             	var archDicType=Ext.getCmp('archDicType');
//			                             	var parentId=archDicType.selectedNode.id;
//
//			                             	var globalTypeForm=new GlobalTypeForm({
//			                             		parentId:parentId,
//			                             		catKey:'DIC',
//			                             		callback:function(){
//			                             			archDicType.root.reload();
//			                             		}
//			                             	});
//			                             	globalTypeForm.show();
//			                             }  
//			                         },{
//			                         	text:'修改分类',
//			                         	scope:this,
//			                         	iconCls:'btn-edit',
//			                         	handler:function(){
//			                         		var archDicType=Ext.getCmp('archDicType');
//			                             	var proTypeId=archDicType.selectedNode.id;
//			                             	
//			                             	var globalTypeForm = new GlobalTypeForm({
//			                             		proTypeId:proTypeId,
//			                             		callback:function(){
//			                             			Ext.getCmp('archDicType').root.reload();
//			                             		}
//			                             	});
//			                             	globalTypeForm.show();
//			                         	}
//			                         },{
//			                         	text:'添加设置项',
//			                         	scope:this,
//			                         	iconCls:'btn-add',
//			                         	handler:function(){
//			                         		var gridPanel=this.centerPanel;
//											var archDicType=Ext.getCmp('archDicType');
//											var selectedNode=archDicType.selectedNode;
//											var typeName=archDicType.selectedNode.text;
//											var parentId=0;
//								            if(selectedNode!=null){
//								            	 parentId=selectedNode.id;
//								            }
//								            if(parentId==0){
//								            	Ext.ux.Toast.msg('操作信息','请从左选择设置分类');
//								            	return;
//								            }
//											new DictionaryForm({
//												parentId:parentId,
//												typeName:typeName,
//												callback:function(){
//													gridPanel.getStore().reload();
//												}
//											}).show();
//			                         	}
//			                         } 
//			                   ]
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
							}, 'itemName', 'itemValue', 'descp','sn','globalType']
				});
		this.store.setDefaultSort('dicId', 'desc');
		this.store.baseParams={parentId:this.rootId};
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
							}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
		});
	
		var tbar=new Ext.Toolbar({
			items:[{
					text:'添加设置',
					iconCls:'btn-add',
					scope:this,
					handler:function(){
						var gridPanel=this.centerPanel;
						var archDicType=Ext.getCmp('archDicType');
						
						var selectedNode=archDicType.selectedNode;
						if(selectedNode == null){
							Ext.ux.Toast.msg('操作信息', '请在左边选择字典分类');
							return;
						}
						var typeName=archDicType.selectedNode.text;
						
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
						var grid=this.centerPanel;
						
						var store=grid.getStore();
						var params = [];
						
						for (var i = 0; i < store.getCount(); i += 1) {
							var record = store.getAt(i);
							params.push(record.data);
						}
	
						Ext.Ajax.request({
								method : 'post',
								url : __ctxPath + '/system/mulSaveDictionary.do',
								params : { data : Ext.encode(params)},
								success : function(request) {
									Ext.ux.Toast.msg('操作信息', '成功设置');
									store.reload();
									grid.getView().refresh();
								},
								failure : function(request) {
									Ext.ux.Toast.msg('操作信息', '设置出错，请联系管理员!');
								}
						});
	
					}
				},'-',{
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
				}
				
//				,'-',{
//					text:'转移类型',
//					iconCls:'',
//					handler:function(){
//							var grid = Ext.getCmp('archDicGrid');
//							var rows = grid.getSelectionModel().getSelections();
//							var dicIds = '';
//							if(rows.length==0){
//								Ext.ux.Toast.msg('操作信息','请选择记录!');
//								return;
//							}
//							for (var i = 0; i < rows.length; i++) {
//								if (i > 0) {
//									dicIds += ',';
//								}
//								dicIds += rows[i].data.dicId;
//							}
//							
//							new DicTypeChangeWin({dicIds:dicIds,callbal:function(){
//								grid.getStore().reload();
//							}}).show();
//					}
//				}
			]
		});
		
		this.centerPanel= new Ext.grid.EditorGridPanel({
			region:'center',
			title:'档案管理设置',
			tbar:tbar,
			clicksToEdit: 1,
			id:'archDicGrid',
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