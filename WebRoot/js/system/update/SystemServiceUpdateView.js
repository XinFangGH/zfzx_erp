/**
 * @author
 * @class SystemServiceUpdateView
 * @extends Ext.Panel
 * @description [SystemServiceUpdate]管理
 * @company 智维软件
 * @createtime:
 */
SystemServiceUpdateView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SystemServiceUpdateView.superclass.constructor.call(this, {
			id : 'SystemServiceUpdateView',
			title : '服务器部署管理',
			region : 'center',
			layout : 'border',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel( {
			layout : 'form',
			region : 'north',
			colNums : 3,
			items : [ {
						fieldLabel : '项目名称',
						name : 'Q_projname_D_EQ',
						flex : .33,
						xtype : 'textfield'
					}],
			buttons : [ {
				text : '查询',
				scope : this,
				iconCls : 'btn-search',
				handler : this.search
			}, {
				text : '重置',
				scope : this,
				iconCls : 'btn-reset',
				handler : this.reset
			}]
		});// end of searchPanel

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '添加',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			},{
				iconCls : 'btn-add',
				text : '编辑',
				xtype : 'button',
				scope : this,
				handler : this.editRs
			},{
				
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
				
			},{
				iconCls : 'btn-add',
				text : '上传',
				xtype : 'button',
				scope : this,
				handler : this.upload
			} ,{
				iconCls : 'btn-add',
				text : '解压',
				xtype : 'button',
				scope : this,
				handler : this.jieya
			},{
				iconCls : 'btn-refresh',
				text : '更新',
				xtype : 'button',
				scope : this,
				handler : this.tihuan
			} ]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			id:'SystemServiceUpdateViewGrid',
			//使用RowActions
			rowActions : true,
			id : 'SystemServiceUpdateGrid',
			url : __ctxPath + "/p2p/listSystemServiceUpdate.do",
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'updatetime', 'updatesize', 'filepath', 'operatorname','disUpTime','disFilesize',
					'ifsuccess' ],
			columns : [ {
				header : '',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '更新时间',
				dataIndex : 'disUpTime'
			}, {
				header : '文件大小',
				dataIndex : 'disFilesize'
			}, {
				header : '上传路径',
				dataIndex : 'filepath'
			}, {
				header : '操作人',
				dataIndex : 'operatorname'
			}, {
				header : '是否成功',
				dataIndex : 'ifsuccess',
				renderer:function(v){
					if(v==1){
						return "成功";
					}else{
						return "失败";
					}
				}
			}, new Ext.ux.grid.RowActions( {
				header : '管理',
				width : 100,
				hidden:true,
				actions : [ {
					iconCls : 'btn-del',
					qtip : '删除',
					style : 'margin:0 3px 0 3px'
				}, {
					iconCls : 'btn-edit',
					qtip : '编辑',
					style : 'margin:0 3px 0 3px'
				} ],
				listeners : {
					scope : this,
					'action' : this.onRowAction
				}
			}) ]
		//end of columns
				});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			new SystemServiceUpdateForm( {
				id : rec.data.id
			}).show();
		});
	},
	//创建记录
	createRs : function() {
		new SystemServiceUpdateForm().show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel( {
			url : __ctxPath + '/p2p/multiDelSystemServiceUpdate.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/p2p/multiDelSystemServiceUpdate.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function( ) {
		 var selections = this.gridPanel.getSelectionModel().getSelections();
		 var len = selections.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var id = selections[0].data.id;
		new SystemServiceUpdateForm( {
			id :  id
		}).show();
	},tihuan:function(){
		
		var selections = this.gridPanel.getSelectionModel().getSelections();
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var id = selections[0].data.id;
		Ext.Ajax.request({
			url : __ctxPath + '/p2p/tihuanFileSystemServiceUpdate.do',
			method : 'POST',
			success : function(response, request) {
//					Ext.getCmp('SystemServiceUpdateViewGrid').getStore().reload();
					Ext.ux.Toast.msg('操作提示', '更新成功');
			},
			failure : function(response) {
				Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
			},
			params : {
				id:id
			}
		})
	},upload:function(){
		new SystemUploadWin().show();
	},jieya:function(){
		 var selections = this.gridPanel.getSelectionModel().getSelections();
		 var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var id = selections[0].data.id;
		Ext.Ajax.request({
			url : __ctxPath + '/p2p/jieyaFileSystemServiceUpdate.do',
			method : 'POST',
			success : function(response, request) {
					Ext.getCmp('SystemServiceUpdateViewGrid').getStore().reload();
					Ext.ux.Toast.msg('操作提示', '解压成功');
			},
			failure : function(response) {
				Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
			},
			params : {
				id:id
			}
	    }) 
	},
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.removeRs.call(this, record.data.id);
			break;
		case 'btn-edit':
			this.editRs.call(this, record);
			break;
		default:
			break;
		}
	}
});
