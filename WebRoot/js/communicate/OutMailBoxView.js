Ext.ns("OutMailFolder");
/**
 *　个人文档目录视图
 */
var OutMailBoxView = function() {
	var selectedNode;
	
	var outMailView=new OutMailView();
	
	var treePanel = new Ext.tree.TreePanel({
				id : 'leftOutMailBoxTree',
				border:false,
				region:'center',
				split : true,
				layout:'fit',
				tbar:new Ext.Toolbar({items:[{
						xtype:'button',
						iconCls:'btn-refresh',
						text:'刷新',
						handler:function(){
							treePanel.root.reload();
						}
					}, '-', {
						xtype:'button',
						text:'展开',
						iconCls:'btn-expand',
						handler:function(){
							treePanel.expandAll();
						}
					}, '-', {
						xtype:'button',
						text:'收起',
						iconCls:'btn-collapse1',
						handler:function(){
							treePanel.collapseAll();
						}
					}
					]}),
				loader : new Ext.tree.TreeLoader({
							url : __ctxPath + '/communicate/listOutMailFolder_.do'
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				rootVisible : false,
				listeners : {
					'click' : function(node){
						if (node != null) {
							
							outMailView.setFolderId(node.id);
							var mailCenterView = Ext.getCmp('OutMailCenterView');
							var mail = Ext.getCmp('OutMailView');
							mail.folderId=node.id;
							Ext.getCmp('OutMailSearchForm').getForm().findField("Q_outMailFolder.folderId_L_EQ").setValue(node.id);;
							var showMailDetail = Ext.getCmp('ShowOutMailDetail');
							if(showMailDetail!=null){
								mailCenterView.remove('ShowOutMailDetail')
								mail.show();
								mailCenterView.doLayout();
							}
							if (node.id != 0) {
								
								Ext.getCmp('OutMailView').setTitle('[' + node.text + ']');

								var mailGrid = Ext.getCmp('OutMailGrid');
								var store = mailGrid.getStore();

								store.url = __ctxPath+ '/communicate/listOutMail_.do';
								store.baseParams = {
									folderId : node.id
								};
								store.reload({
											params : {
												start : 0,
												limit : 25
											}
										});
							}
					    }
					}
				}
			});

	function contextmenu(node, e) {
		selectedNode = new Ext.tree.TreeNode({
					id : node.id,
					text : node.text
		});
		treeMenu.showAt(e.getXY());
	}
	//树的右键菜单的
	treePanel.on('contextmenu', contextmenu, treePanel);

	// 创建右键菜单
	var treeMenu = new Ext.menu.Menu({
				id : 'OutMailFolderTreeMenu',
				items : [{
							text : '新建目录',
							scope : this,
							iconCls:'btn-add',
							handler : createNode
						}, {
							text : '修改目录',
							scope : this,
							iconCls:'btn-edit',
							handler : editNode
						}, {
							text : '删除目录',
							scope : this,
							iconCls:'btn-delete',
							handler : deleteNode
						}]
			});

	//新建目录
	function createNode(nodeId) {
		var parentId=selectedNode.id;
		
		new OutMailFolderForm(null,parentId);
		
	};
	//编辑目录
	function editNode() {
		var folderId=selectedNode.id;
		if(folderId > 4){//禁止用户对默认文件夹进行修改操作
			new OutMailFolderForm(folderId);
		}
		
	};
	//删除目录，子目录也一并删除
	function deleteNode() {
		var folderId=selectedNode.id;
		if(folderId > 4){//禁止用户对默认文件夹进行删除操作
			Ext.Ajax.request({
				url:__ctxPath+'/communicate/countOutMailFolder_.do',
				params:{folderId:folderId},
				method:'post',
				success:function(result,request){
					var res = Ext.util.JSON.decode(result.responseText);
					if (res.count == 0) {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/communicate/removeOutMailFolder_.do',
									params : {
										folderId : folderId
									},
									method : 'post',
									success : function(result, request) {
										Ext.ux.Toast.msg('操作信息', '成功删除目录！');
										treePanel.root.reload();
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
					}//if count == 0
					//文件夹中存在邮件
					else{
						Ext.Msg.confirm('警告信息','该文件夹及其子目录下还有'+res.count+'封邮件,确定要删除吗?',function(btn){
							if(btn == 'yes'){
								Ext.Ajax.request({
									url : __ctxPath
											+ '/communicate/removeOutMailFolder_.do',
									params : {
										folderId : folderId,
										count : res.count
									},
									method : 'post',
									success : function(result, request) {
										Ext.ux.Toast.msg('操作信息', '成功删除目录！');
										treePanel.root.reload();
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
						
					}
				},
				failure:function(result,request){
					Ext.MessageBox.show({
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
				}
				
			});
		}
	};
	var ltopbar = new Ext.Toolbar({
		id : 'leftMailBoxToolbar',
		height : 25,
		items : [{
			text:'收邮件',
			iconCls : 'btn-mail_receive',
			handler:function(){//这里把列表显示为收件箱
			
			 Ext.MessageBox.show({  
                    msg : '邮件收取中，请稍后...',  
                    width : 300,  
                    wait : true,  
                    progress : true,  
                    closable : true,  
                    waitConfig : {  
                        interval : 200  
                    },  
                    icon : Ext.Msg.INFO  
                });  
			  
			Ext.Ajax.request({
				   url: __ctxPath+ '/communicate/fetchOutMail_.do?sid='+new Date(),
				   timeout:12000000,
				   success: function(response, opts){
						
						Ext.MessageBox.hide();
				
						outMailView.setFolderId(1);//收件箱folderId==1
						Ext.getCmp('OutMailView').setTitle('[收件箱]');
						var mailGrid = Ext.getCmp('OutMailGrid');
						var store = mailGrid.getStore();
						store.url = __ctxPath+ '/communicate/listOutMail_.do';
						store.baseParams = {folderId : 1,isFetch:'Y'};
						store.reload({
							params : {start : 0,limit : 25}
						});
				    },
				    failure: function(response, opts) {
				    	Ext.MessageBox.updateText('邮件收取出错!!!...');
				    	Ext.MessageBox.hide();
				   }
				});			
			}
		}, '-', {
			text:'发邮件',
			iconCls : 'btn-mail_send',
			handler:function(){//发邮件功能
				var tab = Ext.getCmp('centerTabPanel');
				var outMailForm = Ext.getCmp('OutMailForm');
				if(outMailForm==null){
					outMailForm = new OutMailForm('','','');
					tab.add(outMailForm);
					tab.activate(outMailForm);
				}else{
					tab.remove(outMailForm);
					outMailForm = new OutMailForm('','','');
					tab.add(outMailForm);
					tab.activate(outMailForm);
				}
			}
		}]
	});
	var leftMailBoxPanel = new Ext.Panel({
		collapsible : true,
		region : 'west',
		layout:'border',
		title : '外部邮箱目录',
		width:160,
		autoScroll : false,
		tbar : ltopbar,
		split : true,
		items :[treePanel]
	});
	var centerPanel = new Ext.Panel({
		id : 'OutMailCenterView',
		region:'center',
		autoScroll : true,layout : 'fit',
		items:[outMailView.getView()]
	});
	var panel = new Ext.Panel({
				title : '外部邮箱',
				iconCls:'menu-mail_box',
				layout : 'border',
				id:'OutMailBoxView',
				autoScroll : false,
				items : [leftMailBoxPanel,centerPanel]
	});
	
	return panel;
};


