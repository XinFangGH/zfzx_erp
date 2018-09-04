/**
 * 个人通讯薄目录视图
 */
/**
 * @author YungLocke
 * @class PersonalPhoneBookView
 * @extends Ext.Panel
 */
Ext.ns("PersonalPhoneBookView");
PersonalPhoneBookView=Ext.extend(Ext.Panel,{
    treePanel:null,
    phoneBookView:null,
    selectedNode:null,
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   PersonalPhoneBookView.superclass.constructor.call(this,{
	        title : '我的通讯薄',
			iconCls : "menu-personal-phoneBook",
			layout : 'border',
			id : 'PersonalPhoneBookView',
			height : 800,
			items : [this.treePanel, this.phoneBookView]
	   });
	},
	initUI:function(){
		this.phoneBookView=new PhoneBookView();
		this.toolbar=new Ext.Toolbar({
			items : [{
						xtype : 'button',
						iconCls : 'btn-refresh',
						text : '刷新',
						scope:this,
						handler : function() {
							this.treePanel.root.reload();
						}
					}, '-', {
						xtype : 'button',
						text : '展开',
						iconCls : 'btn-expand',
						scope:this,
						handler : function() {
							this.treePanel.expandAll();
						}
					}, '-', {
						xtype : 'button',
						text : '收起',
						scope:this,
						iconCls : 'btn-collapse1',
						handler : function() {
							this.treePanel.collapseAll();
						}
					}]
		});
	    this.treePanel= new Ext.tree.TreePanel({
			region : 'west',
			id : 'leftBookPanel',
			title : '我的通讯分组',
			collapsible : true,
			split : true,
			width : 160,
			height : 800,
			tbar :this.toolbar ,
			loader : new Ext.tree.TreeLoader({
						url : __ctxPath + '/communicate/listPhoneGroup.do'
					}),
			root : new Ext.tree.AsyncTreeNode({
						expanded : true
					}),
			rootVisible : false,
			listeners : {
				scope:this,
				'click' : this.clickNode
			}
		});
		
		this.treePanel.on('contextmenu', this.contextmenu, this);
	 
	},
	clickNode:function(node) {
		if (node != null) {
			var bookView = this.phoneBookView;
			if (node.id == 0) {
				bookView.setTitle('所有联系人');
			} else {
				bookView.setTitle(node.text + '组列表');
			}
			var store = bookView.dataView.getStore();
			store.url = __ctxPath + '/communicate/listPhoneBook.do';
			store.baseParams = {
				'Q_phoneGroup.groupId_L_EQ' : node.id>0?node.id:'',
				'Q_appUser.userId_L_EQ':curUserInfo.userId,
				'Q_phoneGroup.isPublic_SN_EQ':0
			};
			store.reload({params : {start : 0,limit : 15}});
		}
	},
	contextmenu:function(node, e) {
		var treeMenu = new Ext.menu.Menu({
			items : [{
						text : '新建组',
						scope : this,
						iconCls : 'btn-add',
						handler : this.createNode
					}, {
						text : '修改组',
						scope : this,
						iconCls : 'btn-edit',
						handler : this.editNode
					}, {
						text : '删除组',
						scope : this,
						iconCls : 'btn-delete',
						handler : this.deleteNode
					}, {
						text : '上移',
						scope : this,
						iconCls : 'btn-up',
						handler : this.upMove
					}, {
						text : '下移',
						scope : this,
						iconCls : 'btn-last',
						handler : this.downMove
					}, {
						text : '置顶',
						scope : this,
						iconCls : 'btn-top',
						handler : this.topMove
					}, {
						text : '置底',
						scope : this,
						iconCls : 'btn-down',
						handler : this.underMove
					}]
		});
		this.selectedNode = new Ext.tree.TreeNode({
					id : node.id,
					text : node.text
				});
		treeMenu.showAt(e.getXY());
	},
	createNode:function(){
	    new PhoneGroupForm({isPublic:0}).show();
	},
	editNode:function(){
	    var groupId = this.selectedNode.id;
		if (groupId > 0) {
			new PhoneGroupForm({groupId:groupId,isPublic:0}).show();
		} else {
			Ext.MessageBox.show({
				title : '操作信息',
				msg : '该处不能被修改',
				buttons : Ext.MessageBox.OK,
				icon : 'ext-mb-error'
			});
		}
	},
	deleteNode:function(){
	    var groupId = this.selectedNode.id;
	    var treePanel=this.treePanel;
	    var phoneBookView=this.phoneBookView;
		Ext.Ajax.request({
			url : __ctxPath + '/communicate/countPhoneGroup.do',
			params : {
				'Q_phoneGroup.groupId_L_EQ' : groupId
			},
			method : 'post',
			success : function(result, request) {
				var count = Ext.util.JSON.decode(result.responseText).count;
				Ext.Msg.confirm('删除操作', '组里还有' + count + '个联系人，你确定删除目录组吗?',
						function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/communicate/multiDelPhoneGroup.do',
									params : {
										ids : groupId
									},
									method : 'post',
									success : function(result, request) {
										Ext.ux.Toast.msg('操作信息', '成功删除目录！');
										treePanel.root.reload();
										var store = phoneBookView.dataView.getStore();
										store.reload({params : {start : 0,limit : 15}});
									},
									failure : function(result, request) {
										Ext.MessageBox.show({title : '操作信息',msg : '信息保存出错，请联系管理员！',buttons : Ext.MessageBox.OK,icon : 'ext-mb-error'});
									}
								});

							}
						});
				
			},
			failure : function(result, request) {
			}
		});
	},
	upMove:function(){
		var groupId = this.selectedNode.id;
		this.moveTO(this.treePanel,groupId,1);

	},
	downMove:function(){
	   var groupId = this.selectedNode.id;
	   this.moveTO(this.treePanel,groupId,2);
		
	},
	topMove:function(){
	    var groupId = this.selectedNode.id;
	    this.moveTO(this.treePanel,groupId,3);
	},
	underMove:function(){
	    var groupId = this.selectedNode.id;
	    this.moveTO(this.treePanel,groupId,4);
	},
	moveTO:function(treePanel,groupId,optId){
	   Ext.Ajax.request({
					url : __ctxPath + '/communicate/movePhoneGroup.do',
					params : {
						groupId : groupId,
						optId : optId
					},
					method : 'post',
					success : function(result, request) {
						treePanel.root.reload();
					},
					failure : function(result, request) {
					}
				});
	}

});

