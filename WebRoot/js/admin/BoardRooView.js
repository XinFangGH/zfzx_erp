Ext.ns('BoardRooView');

/**
 * @author:
 * @class BoardRooView
 * @extends Ext.Panel
 * @description 会议室管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
BoardRooView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BoardRooView.superclass.constructor.call(this, {
			id : 'BoardRooView',
			title : '会议室管理',
			iconCls : 'menu-conference_boardRoom',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel ],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search.createCallback(this),
				scope : this
			}
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'BoardRooSearchForm',
			layout : 'form',
			region : 'north',
			width : '100%',
			height : 40,
			items : [{
				xtyle : 'container',
				border : false,
				style : 'margin-top:10px;',
				layout : 'column',
				layoutConfig : {
					padding : '5',
					align : 'middle'
				},
				defaults : {
					xtype : 'label',
					border : false,
					height : 25
				},
				items : [{
					style : 'margin:5px 5px 5px 5px;',
					text : '会议室名称:'
				}, {
					columnWidth : .2,
					name : 'Q_roomName_S_LK',
					xtype : 'textfield',
					maxLength : 128
				}, {
					style : 'margin:5px 5px 5px 5px;',
					text : '描述:'
				}, {
					columnWidth : .2,
					name : 'Q_roomDesc_S_LK',
					xtype : 'textfield',
					maxLength : 4000
				}, {
					style : 'margin:5px 5px 5px 5px;',
					text : '容纳人数:'
				}, {
					columnWidth : .1,
					name : 'Q_containNum_L_GE',
					xtype : 'numberfield',
					maxLength : 6
				}, {
					style : 'margin:5px 5px 5px 5px;',
					text : ' - '
				}, {
					columnWidth : .1,
					name : 'Q_containNum_L_LE',
					xtype : 'numberfield',
					maxLength : 6
				}, {
					style : 'margin-left:5px;',
					xtype : 'button',
					text : '搜索',
					iconCls : 'btn-search',
					handler : this.search.createCallback(this)
				},{
					xtype : 'button',
					text : '清空',
					iconCls : 'reset',
					handler : this.reset
				} ]
			}]
		});// end of the searchPanel

		this.store = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : __ctxPath + '/admin/listBoardRoo.do'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [ {
					name : 'roomId',
					type : 'int'
				}, 'roomName', 'roomDesc', 'containNum' ]
			}),
			remoteSort : false
		});
		this.store.setDefaultSort('roomId', 'DESC');
		this.store.load( {
			params : {
				start : 0,
				limit : 25
			}
		});

		this.rowActions = new Ext.ux.grid.RowActions( {
			header : '管理',
			width : 80,
			actions : [ {
				iconCls : 'btn-del',
				qtip : '删除',
				style : 'margin:0 3px 0 3px'
			}, {
				iconCls : 'btn-edit',
				qtip : '编辑',
				style : 'margin:0 3px 0 3px'
			} ]
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel( {
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'roomId',
				dataIndex : 'roomId',
				hidden : true
			}, {
				header : '会议室名称',
				dataIndex : 'roomName'
			},  {
				header : '容纳人数(单位：个)',
				dataIndex : 'containNum'
			},
			{
				header : '描述',
				dataIndex : 'roomDesc'
			},this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			}
		});// end of the cm

		this.topbar = new Ext.Toolbar( {
			id : 'BoardRootopBar',
			height : 30,
			bodyStyle : 'text-align:right',
			menuAlign : 'center',
			items : [{
				iconCls : 'btn-add',
				text : '新增',
				handler : function() {
					new BoardRooForm().show();
				}
			}, '-', {
				iconCls : 'btn-edit',
				text : '编辑',
				handler : function() {
					var grid = Ext.getCmp('BoardRooGrid');
					var selectRecords = grid.getSelectionModel().getSelections();
					if (selectRecords.length == 0) {
						Ext.ux.Toast.msg('编辑提示', '请选择要修改的记录！');
						return;
					}
					new BoardRooForm( {
						roomId : selectRecords[0].data.roomId
					}).show();
				}
			}, '-', {
				iconCls : 'btn-del',
				text : '删除',
				handler : function() {
					var grid = Ext.getCmp("BoardRooGrid");
					var selectRecords = grid.getSelectionModel().getSelections();
					if (selectRecords.length == 0) {
						Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
						return;
					}
					var ids = new Array();
					for ( var i = 0; i < selectRecords.length; i++) {
						ids.push(selectRecords[i].data.roomId);
					}
					BoardRooView.remove(ids);
				}
			}]
		});
		// end of the topbar
		
		this.gridPanel = new Ext.grid.GridPanel( {
			id : 'BoardRooGrid',
			tbar : this.topbar,
			region : 'center',
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : cm,
			sm : sm,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			bbar : new Ext.PagingToolbar( {
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
				emptyMsg : "当前没有记录"
			})
		});
		// end of the gridPanel

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
				if (isGranted('_BoardRooEdit')) {
					new BoardRooForm( {
						roomId : rec.data.roomId
					}).show();
				}
			});
		});
		this.rowActions.on('action', this.onRowAction, this);
	},// end of the initUIComponents

	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.delByIds(record.data.roomId);
			break;
		case 'btn-edit':
			this.editRecord(record);
			break;
		default:
			break;
		}
	},
	/**
	 * 根据roomId删除数据
	 */
	delByIds : function(roomId) {
		var gd = Ext.getCmp("BoardRooGrid");
		Ext.MessageBox.confirm("操作提示", "您真的要删除这条数据吗？", function(btn) {
			if (btn == "yes") {
				Ext.Ajax.request( {
					url : __ctxPath + '/admin/delBoardRoo.do',
					method : 'post',
					params : {
						roomId : roomId
					},
					success : function() {
						Ext.ux.Toast.msg("操作提示", "成功删除所选记录！");
						gd.getStore().reload( {
							params : {
								start : 0,
								limit : 25
							}
						});
					},
					failure : function(){
						Ext.ux.Toast.msg('操作提示','对不起，删除数据操作失败！');
					}
				});
			}
		});
	},
	/**
	 * 根据record编辑数据
	 */
	editRecord : function(record) {
		new BoardRooForm({
			roomId : record.data.roomId
		}).show();
	},
	
	/**
	 * 重置
	 */
	reset : function(){
		Ext.getCmp('BoardRooSearchForm').getForm().reset();
	},
	
	/**
	 * 搜索
	 */
	search : function(self){
		if(self.searchPanel.getForm().isValid()){
			self.searchPanel.getForm().submit({
				url :  __ctxPath + '/admin/listBoardRoo.do',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				success : function(fm,action){
					var result = Ext.util.JSON.decode(action.response.responseText);
					self.gridPanel.getStore().loadData(result);
				},
				failure : function(){
					Ext.ux.Toast.msg('操作提示','对不起，数据提交失败！');
				}
			});
		}
	}
});

/**
 * @description 新增数据操作
 */
BoardRooView.add = function(roomId) {
	new BoardRooForm().show();
};

/**
 * @description 删除数据操作
 * @param roomId
 *            会议室编号
 */
BoardRooView.remove = function(roomId) {
	if (roomId != "") {
		var gd = Ext.getCmp("BoardRooGrid");
		Ext.MessageBox.confirm("操作提示", "您真的要删除这条数据吗？", function(btn) {
			if (btn == "yes") {
				Ext.Ajax.request( {
					url : __ctxPath + '/admin/multiDelBoardRoo.do',
					method : 'post',
					params : {
						ids : roomId
					},
					success : function() {
						Ext.ux.Toast.msg("操作提示", "数据删除操作成功！");
						gd.getStore().reload( {
							params : {
								start : 0,
								limit : 25
							}
						});
					},
					failure : function(){
						Ext.ux.Toast.msg('操作提示','对不起，数据删除失败！');
					}
				});
			}
		});
	} else {
		Ext.ux.Toast.msg("温馨提示", "对不起，请选择要删除的数据！");
	}
};