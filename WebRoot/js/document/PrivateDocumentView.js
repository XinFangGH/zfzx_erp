
/**
 * @author YungLocke
 * @class PrivateDocumentView
 * @extends Ext.Panel
 */
PrivateDocumentView = Ext.extend(Ext.Panel, {
	searchPanel : null,
	showPanel : null,
	store : null,
	dataView : null,
	selectNode:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUI();
		PrivateDocumentView.superclass.constructor.call(this, {
					title : '我的文档',
					iconCls : 'menu-personal-doc',
					layout : 'border',
					region : 'center',
					id : 'PrivateDocumentView',
					height : 800,
					width : 800,
					items : [this.treePanel,{
						region:'center',
						layout:'border',
						items:[this.searchPanel,this.showPanel]
					}]

				});
	},
	initUI : function() {
		this.searchPanel = new Ext.FormPanel({
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
			   },{
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
				      this.searchPanel.getForm().reset();
				   }
			   }]
			});
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ '/document/folderDocFolder.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								id : 'id',
								fields : ['fileId', 'fileName', 'fileSize',
										'fileType', 'isFolder', {name:'parentId',type:'int'},'parentName', 'isShared']
							}),
					remoteSort : true
				});
		this.store.setDefaultSort('isFolder', 'desc');

		this.store.load();

		this.dataView = new Ext.DataView({
			region : 'center',
			border : false,
			store : this.store,
			tpl : new Ext.XTemplate(
					'<ul>',
					'<tpl for=".">',
					'<li class="phone" id="{fileId}" name="{fileName}" type="{isFolder}" ext:qtip="<div>{fileName}</div><div>{fileType}</div>" onselect= "document.selection.empty()">',
						'<img width="64" height="64" src="'
								+ __ctxPath
								+ '/images/flag/document/{[(values.isFolder==0?(values.isShared==0?"file.png":"share.png"):"folder.png")]}" />',
						'<strong style="font-size:15px;overflow:hidden;">{fileName}</strong>',
//						'{[values.isFolder == 0 ? "<span><a href=#>下载</a></span>" : ""]}',
					'</li>', '</tpl>', '</ul>'),
			id : 'phones',
			itemSelector : 'li.phone',
			overClass : 'phone-hover',
			multiSelect : true,
			autoScroll : true,
			listeners : {
				'render':{ 
			        fn:this.bodyRender,
			        scope:this
				},
				'dblclick' : {
					fn : this.openFile,
					scope : this
				},
				'contextmenu' : {
					fn : this.dbclickNode,
					scope : this
				}
			}
		});

		this.toolbar = new Ext.Toolbar({
					items : [{
								xtype : 'button',
								text : '新建文件夹',
								scope : this,
								iconCls : 'btn-add',
								id:'NewPrivateFolderButton',
								handler : this.newFolder
							}, {
								xtype : 'button',
								text : '新建文档',
								scope : this,
								id:'NewPrivateDocumentButton',
								iconCls : 'btn-add',
								handler : this.newDocument
							}, {
								xtype : 'button',
								text : '向上',
								iconCls : 'btn-up',
								id:'UpPrivateFolderButton',
								scope : this,
								handler : this.upLevel
							}, {
								xtype : 'button',
								text : '刷新',
								iconCls : 'btn-refresh',
								scope : this,
								handler : this.refresh
							}]
				});
				
				
		this.treePanel = new Ext.tree.TreePanel({
				region : 'west',
//				id : 'leftTreePanel',
				title : '目录',
				collapsible : true,
				split : true,
				autoScroll:true,
				width : 200,
				height : 800,
				animate: true,
                enableDD: true,
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
							url : __ctxPath + '/document/listDocFolder.do'
							,
							listeners:{
							   scope:this,
							   'load':function(){
							   	   if(!this.selectNode){
							   	  	 this.selectNode=this.treePanel.getNodeById(0);
							   	  	 this.selectNode.select();
							   	   }else{
							   	   	  this.selectNodeMethod(this.selectNode);
							   	   }
							   	   this.changePath();
							   }
							}
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true,
							draggable:true
						}),
				rootVisible : false,
				listeners : {
					scope:this,
					'click' : this.clickNode,
					'contextmenu':this.treeContextMenu,
					'beforemovenode':this.moveNode
				}
			});
		this.showPanel = new Ext.Panel({
					region : 'center',
					tbar : this.toolbar,
					layout : 'border',
					items : [{
								xtype : 'panel',
								height : 28,
//								border:false,
								region : 'north',
								layout : 'fit',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											style:'padding-left:15px;',
											cls : 'text-file',
											name : 'FilePathDisplayField',
											id : 'FilePathDisplayField',
											value : '/我的文件夹'
										}]
							}, this.dataView]

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
								handler : this.editFile.createCallback(this,true)
							});
			
			menuItems.push({
						text : '删除',
						scope : this,
						iconCls : 'btn-delete',
						handler : this.delFolder.createCallback(this,this.dataView,
									this.selectNode.id,this.treePanel,true)
					});
		}
		menuItems.push({
					text : '属性',
					scope : this,
					iconCls : 'btn-detail',
					handler : this.folderDetail
				});

		var menus = new Ext.menu.Menu({
					items : menuItems
				});
		menus.showAt(e.getXY());
	},
	newDocument:function(){
	     if(this.selectNode&&this.selectNode.id!=0){
			new DocumentForm({
			   folderId:this.selectNode.id,
			   folderName:this.selectNode.text
			}).show();
		}else{
		   new DocumentForm().show();
		}
	},
	moveNode:function(tree,node,oldParent,newParent,index){
		if(oldParent.id==newParent.id){
//			this.clickNode(node);
            return false;		
		}
		var dataView=this.dataView;
		var self=this;
	    Ext.Msg.confirm('操作提示', '你确定移动该目录吗?', function(btn) {
					if (btn == 'yes') {
					    Ext.Ajax.request({
					       url:__ctxPath + '/document/moveDocFolder.do',
					       params:{folderIdOld:node.id,folderIdNew:newParent.id},
					       method : 'post',
					       success:function(result, request){
					       	   tree.root.reload();
					       	   dataView.getStore().reload();
					       },
					       failure : function(result, request){
					          
					       }
					    });
					    
					    return;
					}
	    });
	    
	    return false;
	    
	},
	refresh:function(){
	    var store = this.dataView.getStore();
		this.treePanel.root.reload();
		store.reload();
	},
	folderDetail:function(){
	    if(!this.selectNode){
	          return ;
	    }
	    new FileDetailShowWin({
	       isPersonal:true,
	       fileId:this.selectNode.id,
	       isFolder:true
	    }).show();
	},
	bodyRender:function(p){
	    p.getEl().on('contextmenu',this.bodyContextClick,this);
	},
	bodyContextClick:function(e,t,o){
		var target=t;
		if(target.id!='phones'){
			return;
		}
		var menuItems = new Array();
		menuItems.push({
					text : '上一目录',
					scope : this,
					disabled:this.isSearching?true:false,
					iconCls : 'btn-up',
					handler : this.upLevel
				});
		menuItems.push({
					text : '新建文件',
					scope : this,
					iconCls : 'btn-add',
					disabled:this.isSearching?true:false,
					handler : this.newFolder
				});
		menuItems.push({
					text : '新建文档',
					scope : this,
					iconCls : 'btn-add',
					disabled:this.isSearching?true:false,
					handler : this.newDocument
				});
//		menuItems.push({
//					text : '粘贴',
//					scope : this,
//					disabled:this.cuting&&!this.isSearching?false:true,
//					iconCls : 'btn-add',
//					handler : this.moveFile
//				});
		menuItems.push({
					text : '刷新',
					scope : this,
					iconCls : 'btn-refresh',
					handler : this.refresh
				});
		menuItems.push({
					text : '属性',
					scope : this,
					iconCls : 'btn-detail',
					disabled:this.isSearching?true:false,
					handler : this.folderDetail
				});

		var menus = new Ext.menu.Menu({
					items : menuItems
				});
		menus.showAt(e.getXY());
	},
	search:function(){
	    this.isSearching=true;
		this.searchDisable();
		var searchPanel = this.searchPanel;
		if (searchPanel.getForm().isValid()) {
			var baseParam = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
			var deParams = Ext.urlDecode(baseParam);
			deParams.isSearch=true;
			deParams.limit=10000;
			this.store.baseParams = deParams;
			this.store.reload();
		}
	},
	searchDisable:function(){
	    Ext.getCmp('NewPrivateFolderButton').disable();
	    Ext.getCmp('NewPrivateDocumentButton').disable();
	    Ext.getCmp('UpPrivateFolderButton').disable();
	    this.isSearching=true;
	},
	searchEnable:function(){
	    Ext.getCmp('NewPrivateFolderButton').enable();
	    Ext.getCmp('NewPrivateDocumentButton').enable();
	    Ext.getCmp('UpPrivateFolderButton').enable();
	    this.isSearching=false;
	},
	changePath:function(){
		 var node=this.selectNode;
		 var path='';
		 while(node!=null&&node.text!=undefined){
		   path='/'+node.text+path;
		   node=node.parentNode;
		 }
		 Ext.getCmp('FilePathDisplayField').setValue(path);
	},
	newFolder:function() {
		var parentId =0;
		var node=this.selectNode;
		if (node) {
			parentId =this.selectNode.id;
		}
		var treePanel=this.treePanel;
		new DocFolderForm({
					folderId : null,
					parentId : parentId,
					isShared : null,
					callback:function(){
                        treePanel.root.reload();	
					}
				}).show();
	},
	upLevel:function(){
		this.isSearching=false;
		var node=this.selectNode;
		var parentNode=node.parentNode;
		if(node&&node.id==0){
		    Ext.ux.Toast.msg('提示信息', '已是最顶层!');
			return;
		}
		var store = this.dataView.getStore();
		store.baseParams.isSearch=false;
		store.reload({
					params : {
//						isSearch:false,
						folderId :parentNode.id
					}
		});
		this.selectNode=parentNode;
		this.selectNodeMethod(parentNode);
	},
	selectNodeMethod:function(){
	    if(this.selectNode){
	        this.selectNode=this.treePanel.getNodeById(this.selectNode.id);
	        this.selectNode.select();
	    }
	},
	dbclickNode : function(dataview, index, node, e) {
		dataview.all.each(function(el) {
					dataview.deselect(el);
				});
		dataview.select(index, true);
		var treePanel=this.treePanel;
		var nodes = dataview.getSelectedNodes();
		if (nodes != '' && nodes != null && nodes != 'undefined') {
			var menuItems = new Array();
			var type = nodes[0].type;
			var fileId = nodes[0].id;
			menuItems.push({
						text : '打开',
						scope : this,
						iconCls : 'btn-add',
						handler : this.openFile
					});
			if (type == 1) {
				menuItems.push({
							text : '修改',
							scope : this,
							iconCls : 'btn-edit',
							handler : this.editFile.createCallback(this)
						});
//				menuItems.push({
//							text : '剪切',
//							scope : this,
//							disabled:this.isSearching?true:false,
//							iconCls : 'btn-edit',
//							handler : this.cutFile
//						});
				menuItems.push({
							text : '删除',
							scope : this,
							iconCls : 'btn-delete',
							handler : this.delFolder.createCallback(this,dataview,
									fileId,treePanel,false)
						});
			} else {
				menuItems.push({
							text : '共享',
							scope : this,
							iconCls : 'btn-shared',
							handler : this.shareDocument
						});
				menuItems.push({
							text : '删除',
							scope : this,
							iconCls : 'btn-delete',
							handler : this.delDocument.createCallback(dataview,
									fileId)
						});
			}
			menuItems.push({
						text : '属性',
						scope : this,
						iconCls : 'btn-detail',
						handler : this.fileDetail
					});

			var menus = new Ext.menu.Menu({
						items : menuItems
					});
			menus.showAt(e.getXY());
		}
	},
	cutFile:function(){
	   this.cuting=true;
	},
	moveFile:function(){
	   this.cuting=false;
	},
	fileDetail:function(){
	    var view = this.dataView;
		var nodes = view.getSelectedNodes();
	    var node=nodes[0];
	    var field = Ext.getCmp('FilePathDisplayField');
	    var type = nodes[0].type;
	    var isFolder=false;
	    if(type==1){
	        isFolder=true;
	    }
	    new FileDetailShowWin({
	       isPersonal:true,
	       fileId:node.id,
	       filePath:field.getValue(),
	       isFolder:isFolder
	    }).show();
	    
	},
	openFile : function() {
		var view = this.dataView;
		var nodes = view.getSelectedNodes();
		if (nodes[0].type == 1) {// 文件夹
			var store = view.getStore();
			store.baseParams.isSearch=false;
			store.reload({
						params : {
							folderId : nodes[0].id
						}
					});
			var node=this.treePanel.getNodeById(nodes[0].id);
			node.select();
			this.selectNode=node;
			this.changePath();		
			this.searchEnable();
		} else {
			new DocumentForm({
						docId : nodes[0].id
					}).show();
		}
		
		

	},
	clickNode:function(node){
		 this.selectNode=node;
		 this.changePath();
	     var view=this.dataView;
	     var store = view.getStore();
	     store.baseParams.isSearch=false;
		 store.reload({
					params : {
						folderId : node.id
					}
				});
		  this.searchEnable();
	},
	editFile : function(self,isTreePanel) {
		var view = self.dataView;
		var nodes = view.getSelectedNodes();
		if ((nodes[0]&&nodes[0].type == 1)||isTreePanel==true) {// 文件夹
			var treePanel=self.treePanel;
			var vid;
			if(isTreePanel){
				vid=self.selectNode.id;
			}else{
			    vid=nodes[0].id;
			}
			new DocFolderForm({
						folderId : vid,
						parentId : null,
						isShared : null,
						callback:function(){
						   treePanel.root.reload();
						}
					}).show();
		} else {
			new DocumentForm({
						docId : nodes[0].id
					}).show();
		}
	},
	shareDocument : function() {
		var view = this.dataView;
		var nodes = view.getSelectedNodes();
		if (nodes[0].type != 1) {
			new DocumentSharedForm({docId:nodes[0].id}).show();
		}
	},
	delFolder : function(self,dataview, fileId,treePanel,isTreePanel) {
		var selNode=self.selectNode;
		Ext.Msg.confirm('删除操作', '你确定删除该目录吗?', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/document/removeDocFolder.do',
									params : {
										folderId : fileId
									},
									method : 'post',
									success : function(result, request) {
										var res = Ext.util.JSON
												.decode(result.responseText);
										if (res.success == false) {
											Ext.ux.Toast.msg('操作信息',
													res.message);
										} else {
//											dataview.getStore().reload();
											treePanel.root.reload();
											if(isTreePanel&&selNode.parentNode){
											    self.clickNode(selNode.parentNode);
											}else{
											    self.clickNode(selNode);
											}
											Ext.ux.Toast.msg('操作信息', '成功删除目录！');
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
					}
				});
	},
	delDocument : function(dataview, fileId) {
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
										dataview.getStore().reload({
											   params : {
//													isSearch:false
												}
											});
										Ext.ux.Toast.msg("信息提示", "成功删除所选文档！");
									}
								});
					}
				});
	}
});