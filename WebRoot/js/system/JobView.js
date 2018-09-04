/**
 * @description 职位管理
 * @class JobView
 * @extends Ext.Panel
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2010-12-22PM
 */
JobView = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		JobView.superclass.constructor.call(this, {
			id : 'JobView',
			title : '职位人员管理',
			region : 'center',
			layout : 'border',
			border : false,
			iconCls : 'menu-job',
			items : [ this.gridPanel, this.searchPanel, this.jobTreePanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			defaultType : 'button',
			items : [{
				text : '添加',
				iconCls : 'add-user',
				scope : this,
				handler : function(){
					var node = Ext.getCmp('jobViewJobTreePanel').getSelectionModel().getSelectedNode();
					if(node != null && node.id > 0){
						new UserJobForm({
							jobId : node.id,
							jobName : node.text
						}).show();
					} else{
						new UserJobForm({}).show();
					}
				}
			} , '-' , {
				text : '删除',
				iconCls : 'btn-del',
				scope : this,
				handler : this.removeByIds
			}]
		});
		////////////////////searchPanel start////////////////////////////////
		this.searchPanel = new Ext.FormPanel({
			// TODO searchPanel用户信息搜索
			id : 'JobViewSearchPanel',
			region : 'north',
			layout : 'hbox',
			border : false,
			height : 40,
			frame : false,
			keys : {
				key : Ext.EventObject.ENTER,
				scope : this,
				fn : this.search
			},
			layoutConfig : {
				padding : '5px',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				border : false,
				margins : {top:0,left:5,right:5,bottom:0}
			},
			items : [{
				text : '用户账号：'
			}, {
				xtype : 'textfield',
				name : 'Q_appUser.username_S_LK',
				maxLength : 256
			}, {
				text : '用户名称：'
			}, {
				xtype : 'textfield',
				name : 'Q_appUser.fullname_S_LK',
				maxLength : 256
			}, {
				text : '主职位(是/否)：'
			}, {
				xtype : 'combo',
				hiddenName : 'Q_isMain_SN_EQ',
				mode : 'local',
				triggerAction : 'all',
				editable : false,
				store : [['0','否'],['1','是']],
				emptyText : '全部'
			}, {
				xtype : 'button',
				text : '搜索',
				iconCls : 'search',
				scope : this,
				handler : this.search
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'reset',
				scope : this,
				handler : this.reset
			}]
		});
		///////////////////searchPanel[用户列表数据搜索] end /////////////////////////////////
		
		/////////////////////gridPanel[加载对应职位下的人员信息]//////////////////////////
		this.gridPanel = new HT.GridPanel({
			// TODO girdPanel[加载对应职位下的人员信息]
			id : 'jobUserGrid',
			title : '职位人员列表',
			region : 'center',
			tbar : this.topbar,
			rowActions : true,
			url : __ctxPath + '/system/listUserJob.do',
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			fields : [{
				name : 'userJobId',
				type : 'int'
			},'job','appUser','isMain'],
			columns : [{
				header : 'userJobId',
				dataIndex : 'userJobId',
				hidden : true
			} , {
				header : '员工账号',
				dataIndex : 'appUser',
				renderer : function(value){
					return value.userId;
				}
			} , {
				header : '员工名称',
				dataIndex : 'appUser',
				renderer : function(value){
					return value.username;
				}
			} , {
				header : '职位',
				dataIndex : 'job',
				renderer : function(value){
					return value.jobName;
				}
			} , {
				header : '主职位(是/否)',
				dataIndex : 'isMain',
				renderer : function(value){
					return value == 1 ? '是' : '否';
				}
			} , new Ext.ux.grid.RowActions ({
				header : '管理',
				width : 100,
				actions : [{
					iconCls : 'btn-del',
					qtip : '删除',
					style : 'margin : 0 3px 0 3px'
				}, {
					iconCls : 'btn-edit',
					qtip : '编辑',
					style : 'margin : 0 3px 0 3px'
				}, {
					iconCls : 'btn-showDetail',
					qtip : '查看职位信息',
					style : 'margin : 0 3px 0 3px'
				}],
				listeners : {
					scope : this,
					'action' : this.onRowActions 
				}
			})]
		}); //end of this gridPanel
		this.gridPanel.addListener('rowdblclick',function(grid, rowIndex, e){
			grid.getSelectionModel().each(function(rec){
				new UserJobForm({
					userJobId : rec.data.userJobId,
					jobName : rec.data.job.jobName
				}).show();
			});
		});
		////////////////////end gridPanel[加载对应职位下的人员信息]////////////////////////
		
		///////////////////////////jobTreePanel[加载职位列表]////////////////////////////////////////////
		this.jobTreePanel = new Ext.tree.TreePanel({
			// TODO jobTreePanel
			id : 'jobViewJobTreePanel',
			region : 'west',
			border : false,
			width : 200,
			collapsible : false,
			autoScroll : true,
			split : true,
			title : '职位信息列表',
			tbar : new Ext.Toolbar({
				defaultType : 'button',
				items : [{
					text : '刷新',
					iconCls : 'btn-refresh',
					handler : function(){
						Ext.getCmp('jobViewJobTreePanel').root.reload();
					}
				},{
					text : '展开',
					iconCls : 'btn-expand',
					handler : function(){
						Ext.getCmp('jobViewJobTreePanel').expandAll();
					}
				},{
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function(){
						Ext.getCmp('jobViewJobTreePanel').collapseAll();
					}
				}]
			}),
			loader : new Ext.tree.TreeLoader({
				url : __ctxPath + '/hrm/treeLoadJob.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'click' : this.clickNode
			}
		}); // end of this jobTreePanel
		
		this.jobTreePanel.on('contextmenu',jobContextmenu,this.jobTreePanel);
		
		var jobTreeMenu = new Ext.menu.Menu({
			// TODO jobTreeMenu
			id : 'jobTreeMenu',
			items : [{
				text : '新增职位信息',
				iconCls : 'btn-add',
				scope : this,
				handler : jobAdd
			},{
				text : '修改职位信息',
				iconCls : 'btn-edit',
				scope : this,
				handler : jobEdit
			},{
				text : '删除职位信息',
				iconCls : 'btn-del',
				scope : this,
				handler : jobRemove
			}]
		}); // end of this treeMenu
		
		function jobContextmenu(node ,e ){
			jobSelected = new Ext.tree.TreeNode({
				id : node.id,
				text : node.text
			});
			jobTreeMenu.showAt(e.getXY());
		};
		
		//新增
		function jobAdd(){
			var nodeId = jobSelected.id;
			if(nodeId > 0){
				new JobForm({
					parentId : nodeId //父节点
				}).show();
			} else {
				new JobForm({
					parentId : 0
				}).show();
			}
		}
		
		//修改
		function jobEdit(){
			var nodeId = jobSelected.id;
			if(nodeId>0){
				new JobForm({
					jobId : nodeId //jobId
				}).show();
			}else{
				Ext.ux.Toast.msg('操作提示','对不起，公司名称不能修改！');
			}
		}
		
		//删除
		function jobRemove(){
			var nodeId = jobSelected.id;
			if(nodeId > 0){
				Ext.Msg.confirm('操作提示','你真的要删除该职位信息吗？',function(btn){
					if(btn == 'yes'){
						var jobTree = Ext.getCmp('jobViewJobTreePanel');
						Ext.Ajax.request({
							url : __ctxPath + '/hrm/deleteJob.do?jobId='+nodeId,
							method : 'post',
							waitMsg : '数据正在提交，请稍后...',
							success : function(result,request){
								var res = Ext.util.JSON.decode(result.responseText);
								if(res.success){
									Ext.ux.Toast.msg('操作提示','删除该职位信息操作成功！');
									jobTree.root.reload();
								}else{
									Ext.ux.Toast.msg('操作提示','对不起，删除该职位信息操作失败！');
								}
							}
						});
					}
				});
			}else
				Ext.ux.Toast.msg('操作提示','对不起，公司名称不能删除！');
		}
		
		////////////////////////////end of this jobTreePanel//////////////////////////////////////

	},// end of the initComponents()

	/**
	 * 管理
	 */
	onRowActions : function(grid, record, action, row, col){
		switch(action){
		case 'btn-showDetail':
			this.showDetail.call(this, record);
			break;
		case 'btn-edit' :
			this.editById.call(this, record);
			break;
		case 'btn-del' : 
			this.removeById.call(this, record.data.userJobId);
			break;
		default : 
				break;
		}
	},
	
	/**
	 * 清空
	 */
	reset : function() {
		var searchPanel = Ext.getCmp('JobViewSearchPanel');
		searchPanel.getForm().reset();
	},

	/**
	 * 搜索
	 */
	search : function() {
		var fm = Ext.getCmp('JobViewSearchPanel');
		// 从FormPanel中获取值
		var appUserId = fm.getCmpByName('Q_appUser.username_S_LK').getValue();
		var appUsername = fm.getCmpByName('Q_appUser.fullname_S_LK').getValue();
		var isMain = fm.getCmpByName('Q_isMain_SN_EQ').getValue();
		// end
		var gridPanel = Ext.getCmp('jobUserGrid');
		var tree = Ext.getCmp('jobViewJobTreePanel'); // tree
		var node = tree.getSelectionModel().getSelectedNode(); //获取选中的节点
		var store = gridPanel.getStore();
		if(node != null && node.id > 0){
			store.baseParams = {
				'Q_job.jobId_L_EQ' : node.id,
				'Q_appUser.username_S_LK' : appUserId,
				'Q_appUser.fullname_S_LK' : appUsername,
				'Q_isMain_SN_LK' : isMain 
			};
		}else{
			store.baseParams = {
				'Q_appUser.username_S_LK' : appUserId,
				'Q_appUser.fullname_S_LK' : appUsername,
				'Q_isMain_SN_EQ' : isMain
			};
		}
		if (fm.getForm().isValid()) {
			store.reload({
				params : {
					start : 0,
					limit : 25
				}
			});
		}
	},
	
	/**
	 * 编辑数据操作
	 */
	editById : function(record){
		new UserJobForm({
			userJobId : record.data.userJobId,
			jobName : record.data.job.jobName
		}).show();
	},
	
	/**
	 * 查看所有职位信息
	 */
	showDetail : function(record){
			var appUser = record.data.appUser;
			UserJobDetailForm.show(appUser.userId,appUser.username);
	},
	
	/**
	 * 根据userId删除数据
	 */
	removeById : function(userJobId){
		Ext.Msg.confirm('操作提示','你真的要删除所选数据吗？',function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelUserJob.do',
					params : {
						ids : userJobId
					},
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					success : function(req,op){
						Ext.ux.Toast.msg('操作提示','数据提交成功！');
						Ext.getCmp('jobUserGrid').getStore().reload();
					},
					failure : function(){
						Ext.ux.Toast.msg('操作提示','对不起，数据提交失败！');
					}
				});
			}
		});
	},
	
	/**
	 * 删除多条数据操作
	 */
	removeByIds : function(){
		var gp = Ext.getCmp('jobUserGrid');
		var record = gp.getSelectionModel().getSelections();
		if(record.length > 0){
			var ids = new Array();
			for(var i = 0; i < record.length; i++)
				ids.push(record[i].data.userJobId);
			this.removeById(ids);
		} else
			Ext.ux.Toast.msg('操作提示','对不起，请选择要删除的员工信息！');
	},
	
	/**
	 * 节点单击事件
	 */
	clickNode : function(node){
		if(node != null){
			var fm = Ext.getCmp('JobViewSearchPanel');
			// 从FormPanel中获取值
			var appUserId = fm.getCmpByName('Q_appUser.username_S_LK').getValue();
			var appUsername = fm.getCmpByName('Q_appUser.fullname_S_LK').getValue();
			var isMain = fm.getCmpByName('Q_isMain_SN_EQ').getValue();
			// end
			var store = Ext.getCmp('jobUserGrid').getStore();
			if(node != null && node.id > 0){
				store.baseParams = {
					'Q_job.jobId_L_EQ' : node.id,
					'Q_appUser.username_S_LK' : appUserId,
					'Q_appUser.fullname_S_LK' : appUsername,
					'Q_isMain_SN_EQ' : isMain
				};
			}else{
				store.baseParams = {
					'Q_appUser.username_S_LK' : appUserId,
					'Q_appUser.fullname_S_LK' : appUsername,
					'Q_isMain_SN_EQ' : isMain
				};
			}
			store.reload({
				params : {
					start : 0,
					limit : 25
				}
			});
		}
	}
	
});