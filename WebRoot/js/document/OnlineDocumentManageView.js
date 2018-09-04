/**
 * @author YungLocke
 * @class KnowledgeManageView
 * @extends Ext.Panel
 */
Ext.ns('OnlineDocumentManageView');
OnlineDocumentManageView=Ext.extend(Ext.Panel,{
    searchFormPanel:null,
    showPanel:null,
    gridPanel:null,
    treePanel:null,
    fileId:0,
    fileName:null,
    store:null,
    selectNode:null,
    isSearching:false,
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    OnlineDocumentManageView.superclass.constructor.call(this,{
	    	 id:'OnlineDocumentManageView',
	         layout:'border',
	         title:'在线文档管理',
	         iconCls:'menu-onlinedoc',
	         region:'center',
	         items:[this.showPanel]
	      
	    });
	},
	initUI:function(){
	   this.searchFormPanel=new Ext.FormPanel({
			    height : 35,
				region : 'north',
				frame : false,
				border : false,
				layout : 'hbox',
				layoutConfig : {
					padding : '5',
					align : 'middle'
				},
				defaults : {
					style : 'padding:0px 5px 0px 5px;',
					border : false,
					anchor : '98%,98%',
					labelWidth : 75,
					xtype : 'label'
				},
			   items:[
			   {
			   	 text:'文件名：'
			   },{
			   	xtype:'textfield',name:'fileName'
			   }
//			   ,{
//			   	 text:'作者：'
//			   },{
//			   	xtype:'textfield',name:'author'
//			   },{
//			   	 text:'关键字：'
//			   },{
//			   	xtype:'textfield',name:'keywords'
//			   }
			   ,{
				   xtype:'button',
				   text:'查询',
				   scope:this,
				   iconCls:'btn-search',
				   handler:this.search
			   },{
				   xtype:'button',
				   text:'重置',
				   scope:this,
				   iconCls:'btn-reset',
				   handler:function(){
				      this.searchFormPanel.getForm().reset();
				   }
			   }]
	   }); 
	   
	   this.treePanel = new Ext.tree.TreePanel({
				region : 'west',
				id : 'OnlineTreePanel',
				title : '知识目录',
				collapsible : true,
				split : true,
				autoScroll:true,
				width : 200,
				height : 800,
				tbar:new Ext.Toolbar({items:[{
						xtype:'button',
						iconCls:'btn-refresh',
						text:'刷新',
						scope:this,
						handler:function(){
							this.treePanel.root.reload();
						}
					},
					{
						xtype:'button',
						text:'展开',
						scope:this,
						iconCls:'btn-expand',
						handler:function(){
							this.treePanel.expandAll();
						}
					},
					{
						xtype:'button',
						text:'收起',
						scope:this,
						iconCls:'btn-collapse1',
						handler:function(){
							this.treePanel.collapseAll();
						}
					}
					]}),
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/document/onlineTreeDocFolder.do',
							listeners:{
							   scope:this,
							   'load':function(){
							   	   this.store.load({
								        folderId:0
								   });
							   }
							}
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					scope:this,
					'click' : this.clickNode,
					'contextmenu':this.treeContextMenu
				}
			});
	   
	    var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [
//			sm, 
			new Ext.grid.RowNumberer(), 
				{
						header : 'fileId',
						dataIndex : 'fileId',
						hidden : true
					}, {
						header : '文件名',
						dataIndex : 'fileName',
						width : 120,
						renderer:function(value, metadata, record) {
							var isFolder=record.data.isFolder;
							var docType=record.data.fileType;
							if(isFolder==1){
							   return '<img width="16" height="16" src="'+__ctxPath+'/images/flag/document/folder.png"/>'+value+'</img>';
							}else{
							   return '<img width="16" height="16" src="'+__ctxPath+'/images/flag/document/'+docType+'.png"/>'+value+'.'+docType+'</img>';
							}
						}
					},{
					    header:'类型',
					    dataIndex:'fileType',
					    width:60
					},{
					    header:'文件大小',
					    dataIndex:'fileSize',
					    renderer:function(value){
					       return '<span ext:qtip="文件大小：'+value+'">'+value+'</span>';
					    }
					},{
					    header:'作者',
					    dataIndex:'author',
					    width:80
					},
//						{
//					    header:'关键字',
//					    dataIndex:'keywords'
//					}
//					, 
						{
						header : '更新时间',
						dataIndex : 'updateTime'
					}, {
						header : '管理',
						dataIndex : 'docId',
						width : 120,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var editId = record.data.fileId;
							var isFolder=record.data.isFolder;
							var rightMod=record.data.rightMod;
							var rightDel=record.data.rightDel;
							var name=record.data.fileName;
							var fileType=record.data.fileType;
							var str = '<a title="属性" value="" href="#" onclick="OnlineDocumentManageView.pro('
									+ editId +','+isFolder+ ')">属性</a>';
							if(rightMod==1&&isFolder==0){
								str += '<a title="编辑" value="" class="" href="#" onclick="OnlineDocumentManageView.edit('
										+ editId +','+isFolder+ ',\''+fileType+'\')">&nbsp;编辑</a>';
							}
							if(rightDel==1&&isFolder==0){
								str += '<a title="删除" value="" class="" href="#" onclick="OnlineDocumentManageView.deleteDocument('
										+ editId +')">&nbsp;删除</a>';
							}
							if (isFolder==1) {
								str += '<a title="编辑" value="" class="" href="#" onclick="OnlineDocumentManageView.edit('
										+ editId +','+isFolder+ ')">&nbsp;编辑</a>';
								str += '<a title="删除" value="" class="" href="#" onclick="OnlineDocumentManageView.deleteFolder('
										+ editId +')">&nbsp;删除</a>';
								str += '<a title="授权" value="" class="" href="#" onclick="OnlineDocumentManageView.privilege('
										+ editId +',\''+name+ '\')">&nbsp;授权</a>';
							}
							if(isFolder==0){
								str += '<a title="查看" value="" class="" href="#" onclick="OnlineDocumentManageView.detail('
										+ editId + ')">&nbsp;查看</a>';
							}
							return str;
	
						}
					}
					],
			defaults : {
				menuDisabled : true,
				width : 100
			}
		});
	
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ '/document/onlineListDocFolder.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								id : 'id',
								fields : ['fileId', 'fileName', 'fileSize',
										'fileType', 'isFolder', {name:'parentId',type:'int'},'parentName', 'isShared','rightRead',
										'rightMod','rightDel','author','keywords','updateTime']
							}),
					remoteSort : true
					,
					listeners : {
						scope : this,
						'load' : this.loadAction
					}
				});
//		this.store.setDefaultSort('isFolder', 'desc');
	  
	    
	    this.toolbar = new Ext.Toolbar({
	    	        border:false,
					items : [{
								xtype : 'button',
								text : '新建文件夹',
								scope : this,
								iconCls : 'btn-add',
								id:'newOnlineFolderButton',
								handler : this.newFolder
							}, {
								xtype : 'button',
								text : '新建DOC文档',
								id:'newOnlineDocumentButton',
								scope : this,
								iconCls : 'btn-add'
								,
								handler : function() {
									if(this.fileId!=0&&this.fileName!=''){
										new OnlineDocumentForm({
										   docType:'doc',
										   folderId:this.fileId,
										   folderName:this.fileName
										}).show();
									}else{
									   new OnlineDocumentForm({docType:'doc'}).show();
									}
								}
							}, {
								xtype : 'button',
								text : '新建EXL文档',
								id:'newOnlineEXCELButton',
								scope : this,
								iconCls : 'btn-add'
								,
								handler : function() {
									if(this.fileId!=0&&this.fileName!=''){
										new OnlineDocumentForm({
										   docType:'xls',
										   folderId:this.fileId,
										   folderName:this.fileName
										}).show();
									}else{
									   new OnlineDocumentForm({docType:'xls'}).show();
									}
								}
							}, {
								xtype : 'button',
								text : '新建PPT文档',
								id:'newOnlinePPTButton',
								scope : this,
								iconCls : 'btn-add'
								,
								handler : function() {
									if(this.fileId!=0&&this.fileName!=''){
										new OnlineDocumentForm({
										   docType:'ppt',
										   folderId:this.fileId,
										   folderName:this.fileName
										}).show();
									}else{
									   new OnlineDocumentForm({docType:'ppt'}).show();
									}
								}
							}, {
								xtype : 'button',
								text : '上一层',
								id:'upOnlineLevelButton',
								iconCls : 'btn-up',
								scope : this,
								handler : this.upLevel
							},{
								xtype : 'button',
								text : '目录',
								iconCls : 'btn-up',
								scope : this,
								handler : this.firstLevel
							}, {
								xtype : 'button',
								text : '刷新',
								iconCls : 'btn-refresh',
								id:'refreshOnlineButton',
								scope : this,
								handler : function() {
									this.store.reload();
									this.selectNode=this.treePanel.getNodeById(this.fileId);
									this.selectNode.select();
									this.changPath();
								}
							}]
				});
		
	    
		this.gridPanel = new Ext.grid.GridPanel({
//			        title:'知识列表',
			        region:'center',
			        tbar:this.toolbar,
					id : 'OnlineDocumentGrid',
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					}
//					,
//					bbar : new HT.PagingBar({store : this.store})
				});
	
		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
								if(rec.data.isFolder==1){
									this.fileId=rec.data.fileId;
									this.fileName=rec.data.fileName;
									var store=grid.getStore();
								    store.baseParams={folderId:rec.data.fileId};
					    	        store.reload();
					    	        this.getTitle().setTitle('['+rec.data.fileName+']在线文档列表');
					    	        this.selectNode=this.treePanel.getNodeById(this.fileId);
					    	        this.selectNode.select();
					    	        this.changPath();//更改路径
					    	        this.searchEnable();
								}else{
									new OnlineDocumentDetail({docId:rec.data.fileId,docType:rec.data.fileType}).show();
								
								}
							},this);
				},this);
				
	   this.showPanel=new Ext.Panel({
	   	   region:'center',
	   	   border:false,
	       layout:'border',
	       items:[this.treePanel
	       ,{
	       	layout:'border',
	       	border:false,
	       	region:'center',
	       	items:[{
								xtype : 'panel',
								height : 28,
								region : 'north',
								layout : 'fit',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											style:'padding-left:15px;',
											cls : 'text-file',
											name : 'FilePathDisplayField',
											id : 'OnlineDocumentPathDisplayField',
											value : '/在线文档目录'
										}]
							}
			,{
			layout:'border',
			id:'TitleOnlineShowPanel',
			region:'center',
			title:'在线文档列表',
			border:false,
			items:[
			this.searchFormPanel,
			this.gridPanel]}]}]
	   });
	},
	treeContextMenu:function(node,e){
		this.clickNode(node);
		var menuItems=new Array();
		menuItems.push({
							text : '新建',
							scope : this,
							iconCls : 'btn-add',
							handler : this.newFolder
						});
		if(node.id!=0){
			menuItems.push({
								text : '修改',
								scope : this,
								iconCls : 'btn-edit',
								handler : this.editFolder
							});
			
			menuItems.push({
						text : '删除',
						scope : this,
						iconCls : 'btn-delete',
						handler : this.delFolder
					});
		
	       menuItems.push({
					text : '属性',
					scope : this,
					iconCls : 'btn-detail',
					handler : this.proFolder
				});
		}
		var menus = new Ext.menu.Menu({
					items : menuItems
				});
		menus.showAt(e.getXY());
	},
	search:function(){
		this.isSearching=true;
		this.searchDisable();
//	    this.store.baseParams={folderId:2,isSearch:true,fileName:'知识'};
//		
		var searchPanel = this.searchFormPanel;
		if (searchPanel.getForm().isValid()) {
			var baseParam = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
			var deParams = Ext.urlDecode(baseParam);
			deParams.isSearch=true;
			this.store.baseParams = deParams;
			this.store.reload();
		}
	},
	searchDisable:function(){
	    Ext.getCmp("upOnlineLevelButton").disable();
	    Ext.getCmp('newOnlineFolderButton').disable();
	    Ext.getCmp('newOnlineDocumentButton').disable();
	    Ext.getCmp('newOnlineEXCELButton').disable();
	    Ext.getCmp('newOnlinePPTButton').disable();
	    Ext.getCmp('refreshOnlineButton').disable();
	    this.treePanel.getNodeById(0).select();
	    Ext.getCmp('OnlineDocumentPathDisplayField').setValue('/在线文档目录');
	},
	searchEnable:function(){
		this.isSearching=false;
	    Ext.getCmp("upOnlineLevelButton").enable();
	    Ext.getCmp('newOnlineFolderButton').enable();
	    Ext.getCmp('newOnlineDocumentButton').enable();
	    Ext.getCmp('newOnlineEXCELButton').enable();
	    Ext.getCmp('newOnlinePPTButton').enable();
	    Ext.getCmp('refreshOnlineButton').enable();
	},
	newFolder:function(){
		var treePanel=this.treePanel;
	    new DocFolderForm({
					folderId : null,
					parentId : this.fileId,
					isShared : 2,
					callback:function(){
					   treePanel.root.reload();
					}
		}).show();// 表示增加公共文件夹
	},
	editFolder:function(){
		var treePanel=this.treePanel;
	    new DocFolderForm({folderId:this.fileId,callback:function(){
   		     treePanel.root.reload();
   		 }}).show();
	},
	delFolder:function(){
		var treePanel=this.treePanel;
		var fileId=this.fileId;
		var self=this;
		var node=this.selectNode.parentNode;
		Ext.Msg.confirm('删除操作', '你确定删除该目录吗?', function(btn) {
			if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/document/removeDocFolder.do',
								params : {
									folderId : fileId
								},
								method : 'post',
								success : function(result, request) {
									var res = Ext.util.JSON.decode(result.responseText);
									if(res.success==false){
									  Ext.ux.Toast.msg('操作信息', res.message);
									}else{
										Ext.ux.Toast.msg('操作信息', '成功删除目录！');
										treePanel.root.reload();
										self.clickNode(node);
									}
								},
			
								failure : function(result, request) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
								}
			
							});
						}});
	},
	getTitle:function(){
		var comp=Ext.getCmp('TitleOnlineShowPanel');
		if(comp!=null){
		   return comp;
		}
		return null;
	},
	loadAction:function(store){
		if(!this.isSearching){
		    var record = store.getAt(0);
			if (record != null) {
				var isFolder=record.get('isFolder');
				if(isFolder==1){
					var parentId = record.get('parentId');
					var parentName=record.get('parentName');
					if(parentId==0){
						this.getTitle().setTitle('在线文档列表');
					}else{
						this.fileName=parentName;
						this.getTitle().setTitle('['+parentName+']在线文档列表');
					}
					this.fileId = parentId;
					
				}
			}
			var node11=this.treePanel.getNodeById(this.fileId);
			if(node11){
			   this.selectNode=node11;
			   this.selectNode.select();
			   this.changPath();
			   if(node11.id==0){
					this.getTitle().setTitle('在线文档列表');
				}else{
					this.fileId=node11.id;
					this.fileName=node11.text;
					this.getTitle().setTitle('['+node11.text+']在线文档列表');
				}
			}
			
			
		}else{
		    this.getTitle().setTitle('查询结果列表');
		}
	},
	clickNode:function(node){
		if (node != null) {
			this.selectNode=node;
			this.changPath();
			var documentGrid =this.gridPanel;
			if(node.id==0){
				this.getTitle().setTitle('在线文档列表');
			}else{
				this.fileName=node.text;
				this.getTitle().setTitle('['+node.text+']在线文档列表');
			}
	    	this.fileId=node.id;
	    	var store=documentGrid.getStore();
	    	store.baseParams={folderId:node.id};
	    	store.reload();
	    	this.searchEnable();
	    }
	},
	changPath:function(){
	     var node=this.selectNode;
		 var path='';
		 while(node!=null&&node.text!=undefined){
		   path='/'+node.text+path;
		   node=node.parentNode;
		 }
		 Ext.getCmp('OnlineDocumentPathDisplayField').setValue(path);
	},
	upLevel:function(){
		if (this.fileId == 0||this.fileId==null) {
			Ext.ux.Toast.msg('提示信息', '已是最顶层!');
			return;
		}
	    this.store.baseParams={folderId:this.fileId,isUp:true};
		this.store.reload();
		
	},
	firstLevel:function(){
//		if (this.fileId == 0||this.fileId==null) {
//			Ext.ux.Toast.msg('提示信息', '已是最顶层!');
//			return;
//		}
	    this.store.baseParams={folderId:0};
		this.store.reload();
		this.getTitle().setTitle('在线文档列表');
		this.searchEnable();
	}
});
OnlineDocumentManageView.pro=function(fileId,isFolder){
	var path=Ext.getCmp('OnlineDocumentPathDisplayField').getValue();
	if(isFolder==1){
   		 new FileDetailShowWin({
   		   isOnline:true,
	       fileId:fileId,
	       filePath:path,
	       isFolder:true
	    }).show();
	}else{
	     new FileDetailShowWin({
	       isOnline:true,
	       fileId:fileId,
	       filePath:path,
	       isFolder:false
	    }).show();
	}
};

OnlineDocumentManageView.edit=function(fileId,isFolder,fileType){
	
	if(isFolder==1){
		var treePanel=Ext.getCmp('OnlineTreePanel');
   		 new DocFolderForm({folderId:fileId,callback:function(){
   		     treePanel.root.reload();
   		 }}).show();
	}else{
	     new OnlineDocumentForm({docId:fileId,docType:fileType}).show();
	}
};

OnlineDocumentManageView.detail=function(fileId){
   new OnlineDocumentDetail({docId:fileId}).show();

};
OnlineDocumentManageView.privilege=function(fileId,fileName){
   new KnowledgePrivilegeWin({folderId:fileId,folderName:fileName}).show();
};
OnlineDocumentManageView.deleteFolder=function(fileId){
   Ext.Msg.confirm('删除操作', '你确定删除该目录吗?', function(btn) {
			if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/document/removeDocFolder.do',
								params : {
									folderId : fileId
								},
								method : 'post',
								success : function(result, request) {
									var res = Ext.util.JSON.decode(result.responseText);
									if(res.success==false){
									  Ext.ux.Toast.msg('操作信息', res.message);
									}else{
										Ext.ux.Toast.msg('操作信息', '成功删除目录！');
										var treePanel=Ext.getCmp('OnlineTreePanel');
										treePanel.root.reload();
									}
								},
			
								failure : function(result, request) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
								}
			
							});
						}});
};
OnlineDocumentManageView.deleteDocument=function(fileId){
   Ext.Msg.confirm('信息确认', '您确认要删除该文档吗？', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath
											+ '/document/multiDelDocument.do',
									params : {
										ids : fileId
									},
									method : 'post',
									success : function() {
										var grid = Ext
												.getCmp('OnlineDocumentGrid');
										grid.getStore().reload();
									}
								});
							}
						});
};