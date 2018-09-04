/**
 * @author: YHZ
 * @class: RelativeUserView
 * @extends: Ext.Window
 * @description: 相对岗位人员管理
 * @company: 北京互融时代软件有限公司
 * @data: 2010-1-10AM
 */
RelativeUserView = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				RelativeUserView.superclass.constructor.call(this, {
					id : 'RelativeUserView',
					title : '该用户['+this.username+']添加上下级管理',
					iconCls : 'menu-relativeJob',
					region : 'center',
					layout : 'border',
					width : 850,
					height : 600,
					modal : true,
					maximizable : true,
					items : [this.treePanel, this.gridPanel]
				});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.topbar = new Ext.Toolbar({
					defaultType : 'button',
					items : [{
								iconCls : 'btn-superior',
								text : '添加上级',
								scope : this,
								handler : this.addRelativeUser.createCallback(1,this.userId)
							} , '-' , {
								iconCls : 'btn-sibling',
								text : '添加同级',
								scope : this,
								handler : this.addRelativeUser.createCallback(2,this.userId)
							} , '-' , {
								iconCls : 'btn-subordinate',
								text : '添加下级',
								scope : this,
								handler : this.addRelativeUser.createCallback(0,this.userId)
							} , '-' , {
								iconCls : 'btn-del',
								text : '删除',
								scope:this,
								handler : this.removeSelRs
							}]
				});
				
				// 实例化treePanel, 加载相对岗位人员管理
				this.treePanel = new Ext.tree.TreePanel({
					// TODO treePanel相对岗位人员管理
					id : 'RelativeUserViewTreePanel',
					region : 'west',
					collapsible : true,
					autoScroll : true,
					split : true,
					width : 150,
					title : '相对岗位列表',
					tbar : new Ext.Toolbar({
						items : [{
							xtype : 'button',
							iconCls : 'btn-refresh',
							text : '刷新',
							handler : function() {
								Ext.getCmp('RelativeUserViewTreePanel').root.reload();
							}
						}, {
							xtype : 'button',
							text : '展开',
							iconCls : 'btn-expand',
							handler : function() {
								Ext.getCmp('RelativeUserViewTreePanel').expandAll();
							}
						}, {
							xtype : 'button',
							text : '收起',
							iconCls : 'btn-collapse1',
							handler : function() {
								Ext.getCmp('RelativeUserViewTreePanel').collapseAll();
							}
						}]
					}),
					loader : new Ext.tree.TreeLoader({
						url : __ctxPath + '/system/treeLoadRelativeJob.do'
					}),
					root : new Ext.tree.AsyncTreeNode({
						expanded : true
					}),
					rootVisible : false,
					listeners : {
						'click' : function(nd){
							if(nd != null){
								var store = Ext.getCmp('RelativeUserView').gridPanel.getStore();
								store.baseParams = {
									reJobId : nd.id
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
				}); // end of this treePanel
				// 树的右键菜单的
				this.treePanel.on('contextmenu', contextmenu, this.treePanel);
				//treeMenu
				this.treeMenu = new Ext.menu.Menu({
					id : 'RelativeUserMenu',
					items : [{
						iconCls : 'btn-add',
						text : '新增岗位',
						scope : this,
						handler : addRelativeJob
					},{
						text : '修改岗位信息',
						iconCls : 'btn-edit',
						scope : this,
						handler : editRelativeJob
					},{
						text : '删除岗位信息',
						iconCls : 'btn-del',
						scope : this,
						handler : delRelativeJob
					}]
				}); // end of this treeMenu
				
				//菜单操作
				function contextmenu(node, e) {
					selected = new Ext.tree.TreeNode({
						id : node.id,
						text : node.text
					});
					Ext.getCmp('RelativeUserMenu').showAt(e.getXY());
				}
				//新增岗位信息
				function addRelativeJob(){
					var nodeId = selected.id;
					var relativeJobForm = Ext.getCmp('RelativeJobForm');
					if (relativeJobForm == null) {
						if (nodeId > 0) {
							new RelativeJobForm({
								nodeId : nodeId
							}).show();
						} else {
							new RelativeJobForm({
								nodeId : 0
							}).show();
						}
					}
				}
				//编辑岗位信息
				function editRelativeJob(){
					var nodeId = selected.id;
					if(nodeId > 0){
						var relativeJobForm = Ext.getCmp('RelativeJobForm');
						if(relativeJobForm == null){
							new RelativeJobForm().show();
							relativeJobForm = Ext.getCmp('RelativeJobForm');
						}
						relativeJobForm.form.load({
							url : __ctxPath + '/system/getRelativeJob.do',
							params : {
								reJobId : nodeId
							},
							method : 'post',
							deferredRender : true,
							layoutOnTabChange : true,
							failure : function(){
								Ext.ux.Toast.msg('操作提示', '载入岗位信息失败!');
							}
						});
					}else{
						Ext.ux.Toast.msg('操作提示','对不起，公司名称不能修改，请原谅！');
					}
				}
				//删除岗位信息
				function delRelativeJob(){
					var nodeId = selected.id;
					if(nodeId >0){
						Ext.Msg.confirm('操作提示','你真的要删除该岗位信息吗？',function(btn){
							if(btn == 'yes'){
								Ext.Ajax.request({
									url : __ctxPath + '/system/deleteRelativeJob.do?reJobId='+nodeId,
									method : 'post',
									success : function(result, request){
										var res = Ext.util.JSON.decode(result.responseText);
										if(res.success){
											Ext.ux.Toast.msg('操作提示','删除该岗位信息成功！');
											Ext.getCmp('RelativeUserViewTreePanel').root.reload();
										}else
											Ext.ux.Toast.msg('操作提示','对不起，删除该岗位信息失败！');
									},
									failure : function(){}
								});
							}
						});
					}else{
						Ext.ux.Toast.msg('操作提示','对不起，公司名称不能删除，请原谅！');
					}
				}
				
				
				this.gridPanel=new HT.GridPanel({
					// TODO gridPanel
					region : 'center',
					tbar : this.topbar,
					//使用RowActions
					rowActions : true,
					id : 'RelativeUserGrid',
					url : __ctxPath + "/system/listRelativeUser.do?userId="+this.userId,
					fields : [{
								name : 'relativeUserId',
								type : 'int'
							},'appUser','jobUser','isSuper','relativeJob'],
					columns : [
							{
								header : 'relativeUserId',
								dataIndex : 'relativeUserId',
								hidden : true
							} , { 
								header : '岗位员工',
								dataIndex : 'jobUser',
								renderer : function(value){
									return value.username;
								}
							} , {
								header : '上下级标识 ',	
								dataIndex : 'isSuper',
								renderer : function(value){
									if(value==0)
										return '<button class="btn-subordinate" title="下级"/>';
									else if(value == 1) 
										return '<button class="btn-superior" title="上级" />';
									else if(value == 2)
										return '<button class="btn-sibling" title="同级" />';
									else
										return '';
								}
							} , new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								actions : [{
										 iconCls:'btn-del',qtip:'删除',style:'margin:0 3px 0 3px'
									}
								],
								listeners:{
									scope : this,
									'action' : this.onRowAction
								}
							})
					]//end of columns
				});
			},// end of the initComponents()
			
			//创建记录
			createRs : function() {
				new RelativeUserForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/system/multiDelRelativeUser.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/system/multiDelRelativeUser.do',
					grid:this.gridPanel,
					idName:'relativeUserId'
				});
			},
			
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.relativeUserId);
						break;
					default :
						break;
				}
			},
			
			/**
			 * @description 添加用户级别[上级,下级,同级]
			 * @param type
			 * 			1.上级,0.下级,2.同级
			 * @param userId
			 * 			用户id[userId]
			 */
			addRelativeUser : function(type,userId){
				var typeMsg = '';
				if(type == 1)
					typeMsg = '上级';
				else if (type == 2)
					typeMsg = '同级';
				else 
					typeMsg = '下级';
				//判断是否选中相对岗位
				var node = Ext.getCmp('RelativeUserViewTreePanel').getSelectionModel().getSelectedNode();
				if(node != null && node.id > 0){
					// 获取已选用户userId
					var rStore = Ext.getCmp('RelativeUserView').gridPanel.getStore();
					var selArr = new Array();
					for(var i=0;i<rStore.getCount();i++){
						var ap = rStore.getAt(i).data.jobUser;
						selArr.push({userId:ap.userId,fullname:ap.fullname});
					}
					//弹出选择器
					UserSelector.getView(function(jobUserIds,fullNames){
						if(jobUserIds!=''){
							//向RelativeUser表中添加数据操作
							Ext.Ajax.request({
								url : __ctxPath + '/system/mutliAddRelativeUser.do',
								method : 'post',
								params : {
									'userId' : userId, //所属员工
									'reJobId' : node.id, // 对应的岗位 
									'jobUserIds' : jobUserIds, //岗位人员列表
									'relativeUser.isSuper' : type
								},
								success : function(response,op){
									var res = Ext.util.JSON.decode(response.responseText);
									Ext.ux.Toast.msg('操作提示',res.msg);
									rStore.reload();
								},
								failure : function(){
									Ext.ux.Toast.msg('操作提示','对不起，添加'+typeMsg+'用户信息失败！');
								}
							});
						}
					},false,false,selArr).show();
				}else{
					Ext.ux.Toast.msg('操作提示','请选择岗位名称！');
				}
			}
});
