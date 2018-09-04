Ext.ns('DocFolderView');
/**
 * TODO: add class/table comments列表
 */
var DocFolderView = function() {
	return new Ext.Panel({
		id:'DocFolderView',
		title:'公共文件夹列表',
		layout:'border',
		region:'center',
		autoScroll:true,
		items:[
				new Ext.FormPanel({
				region:'north',
				height:35,
				frame:true,
				id:'DocFolderSearchForm',
				layout:'column',
				defaults:{xtype:'label'},
				items:[{text:'请输入查询条件:'}
	,{
		text : '主键'
	}, {
		xtype : 'textfield',
		name : 'Q_userId_S_LK'
	}
	,{
		text : '目录名称'
	}, {
		xtype : 'textfield',
		name : 'Q_folderName_S_LK'
	}
	,{
		text : '父目录'
	}, {
		xtype : 'textfield',
		name : 'Q_parentId_S_LK'
	}
	,{
		text : ''
	}, {
		xtype : 'textfield',
		name : 'Q_isShared_S_LK'
	}
						,{
							xtype:'button',
							text:'查询',
							iconCls:'search',
							handler:function(){
								var searchPanel=Ext.getCmp('DocFolderSearchForm');
								var gridPanel=Ext.getCmp('DocFolderGrid');
								if(searchPanel.getForm().isValid()){
					    			$search({
										searchPanel :searchPanel,
										gridPanel : gridPanel
									});
					    		}
								
							}
						}
				]
			}),
			this.setup()
		]
	});
};
/**
 * 建立视图
 */
DocFolderView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
DocFolderView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'folderId',
					dataIndex : 'folderId',
					hidden : true
				},
				{
				header : '文件夹名称',	
				dataIndex : 'folderName'
					}
				,{
				header : '父目录',	
				dataIndex : 'parentId'
		    	}
				,{
					header : '管理',
					dataIndex : 'folderId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.folderId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="DocFolderView.remove('
								+ editId + ')">&nbsp;</button>';
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DocFolderView.edit('
								+ editId + ')">&nbsp;</button>';
						str+= '&nbsp;<button title="授权" value=" " class="btn-shared" onclick="DocFolderView.right('+editId+')">&nbsp;</button>';
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'DocFolderGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});
			
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					DocFolderView.edit(rec.data.folderId);
				});
	});  
	return grid;

};

/**
 * 初始化数据
 */
DocFolderView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/document/shareDocFolder.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'folderId',
										type : 'int'
									}
									
									,'userId'
									,'folderName'
									,'parentId'
									,'path'
									,'isShared'
									]
						}),
				remoteSort : true
			});
	store.setDefaultSort('folderId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
DocFolderView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'DocFolderFootBar',
				height : 30,
				bodyStyle:'text-align:left',
				items : [
						{
							iconCls : 'btn-add',
							text : '添加DocFolder',
							xtype : 'button',
							handler : function() {
								new DocFolderForm(null,null,1);
						    }
						}, {
							iconCls : 'btn-del',
							text : '删除DocFolder',
							xtype : 'button',
							handler : function() {
								
								var grid=Ext.getCmp("DocFolderGrid");
								
								var selectRecords=grid.getSelectionModel().getSelections();
								
								if(selectRecords.length==0){
									Ext.ux.Toast.msg("信息","请选择要删除的记录！");
									return;
								}
								var ids=Array();
								for(var i=0;i<selectRecords.length;i++){
									ids.push(selectRecords[i].data.folderId);
								}
								
								DocFolderView.remove(ids);
							}
						}
				]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
DocFolderView.remove=function(id){
	var grid=Ext.getCmp("DocFolderGrid");
	Ext.Msg.confirm('信息确认','您确认要删除该记录吗？',function(btn){
			if(btn=='yes'){
				Ext.Ajax.request({
					url:__ctxPath+'/document/multiDelDocFolder.do',
					params:{
						ids:id
					},
					method:'post',
					success:function(){
						Ext.ux.Toast.msg("信息提示","成功删除所选记录！");
						grid.getStore().reload({params:{
							start : 0,
							limit : 25
						}});
					}
				});
		 }
	});
};

/**
 * 
 */
DocFolderView.edit=function(id){
	new DocFolderForm(id);
}

DocFolderView.right=function(id){
	new DocFolderSharedForm(id).getView().show();
}
