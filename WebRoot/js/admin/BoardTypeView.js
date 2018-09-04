Ext.ns("BoardTypeView");

/**
 * @description 会议类型信息展示
 * @author YHZ
 * @data 2010-9-25 PM
 */
BoardTypeView = Ext.extend(Ext.Panel, {
	// 搜索
	searchPanel : null,
	// 数据展示gridPanel
	gridPanel : null,
	// GridPanel的Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 组件初始化
		this.initUIComponent();
		BoardTypeView.superclass.constructor.call(this, {
			id : 'BoardTypeView',
			title : '会议类型管理',
			iconCls : 'menu-confernece_boardType',
			layout : 'border',
			region : 'center',
			bodyStyle : 'padding : 5px 5px 5px 5px',
			items : [this.searchPanel, this.gridPanel ],
			buttonAlign : 'center',
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search.createCallback(this),
				scope : this
			}
		});
	},
	// end of this constructor
	initUIComponent : function() {
		this.searchPanel = new Ext.FormPanel({
			id : 'boardTypeSearchPanel',
			layout : 'form',
			region : 'north',
			width : '100%',
			height : 40,
			items : [{
				xtype : 'container',
				layout : 'column',
				style : 'margin-top:8px;',
				border : false,
				layoutConfig : {
					padding : '5px',
					align : 'middle'
				},
				defaults : {
					xtype : 'label',
					border : false,
					height : 25
				},
				items : [{
					style : 'margin:5px 5px 5px 5px;',
					text : '会议类型名称'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_typeName_S_EQ',
					maxLength : 128
				}, {
					style : 'margin:5px 5px 5px 5px;',
					text : '会议类型描述'
				}, {
					columnWidth : .2,
					xtype : 'textfield',
					name : 'Q_typeDesc_S_LK',
					maxLength : 4000
				}, {
					style : 'margin-left:5px;',
					xtype : 'button',
					text : '搜索',
					iconCls : 'btn-search',
					handler : this.search.createCallback(this)
				}, {
					xtype : 'button',
					text : '清空',
					iconCls : 'reset',
					handler : this.reset
				}]
			}]
		}); // end of this searchPanel
		
		// 创建store
		this.store = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : __ctxPath + '/admin/listBoardType.do'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [ {
					name : 'typeId',
					type : 'int'
				}, 'typeName', 'typeDesc' ]
			}),
			remoteSort : false
		});
		this.store.setDefaultSort('typeId', 'DESC');
		this.store.load( {
			params : {
				start : 0,
				limit : 25
			}
		});
		// 多选
		var sm = new Ext.grid.CheckboxSelectionModel();
		// 添加操作列
		this.rowActions = new Ext.ux.grid.RowActions( {
			header : '管理',
			width : 80,
			actions : [ {
				iconCls : 'btn-del',
				qtip : '删除',
				style : 'margin : 0 3px 0 3px'
			}, {
				iconCls : 'btn-edit',
				qtip : '编辑',
				style : 'margin : 0 3px 0 3px'
			} ]
		});
		// 列模型
		var cm = new Ext.grid.ColumnModel( {
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'typeId',
				dataIndex : 'typeId',
				hidden : true
			}, {
				header : '会议类型名称',
				dataIndex : 'typeName'
			}, {
				header : '会议类型描述',
				dataIndex : 'typeDesc'
			}, this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			}
		});
		// end of this cm

		this.topbar = new Ext.Toolbar( {
			id : 'BoardTypeToolBar',
			height : 30,
			bodyStyle : 'text-align:center,align:right',
			menuAlign : 'center',
			items : [{
				iconCls : 'btn-add',
				text : '新增',
				handler : function() {
					new BoardTypeForm().show();
				}
			}, '-', {
				iconCls : 'btn-edit',
				text : '编辑',
				handler : function() {
					var typeIds = Ext.getCmp('BoardTypeGridPanel')
							.getSelectionModel().getSelections();
					if (typeIds.length > 0) {
						BoardTypeView.edit(typeIds[0].data.typeId);
					} else {
						Ext.ux.Toast.msg('操作提示', '对不起，请选择操作数据！');
					}
				}
			}, '-', {
				iconCls : 'btn-del',
				text : '删除',
				handler : function() {
					var selectedTypeIds = Ext.getCmp('BoardTypeGridPanel')
							.getSelectionModel().getSelections();
					var typeIds = new Array();
					for ( var i = 0; i < selectedTypeIds.length; i++) {
						typeIds.push(selectedTypeIds[i].data.typeId);
					}
					BoardTypeView.del(typeIds);
				}
			}]
		});
		// end of this topbar

		this.gridPanel = new Ext.grid.GridPanel( {
			id : 'BoardTypeGridPanel',
			region : 'center',
			layout : 'form',
			bodyStyle : 'padding : 5px 5px 5px 5px',
			tbar : this.topbar,
			loadMask : true,
			store : this.store,
			plugins : this.rowActions,
			cm : cm,
			sm : sm,
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			bbar : new Ext.PagingToolbar( {
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示从{0}至{1}，共有{2}条记录',
				emptyMsg : '对不起，当前没有数据！'
			})
		});
		// end of this gridPanel
		// 添加GridPanel行双击事件
		this.gridPanel.addListener('rowdblclick', function(grid, rowIndex, e) {
			grid.getSelectionModel().each(function(exc) {
				BoardTypeView.edit(exc.data.typeId);
			});
		});
		this.rowActions.on('action', this.onRowActions, this);
	},// end of this initUIComponent

	onRowActions : function(gridPanel, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.delById(record.data.typeId);
			break;
		case 'btn-edit':
			this.editById(record.data.typeId);
			break;
		}
	},
	/**
	 * 根据typeId删除操作
	 */
	delById : function(typeId) {
		Ext.MessageBox.confirm('操作提示', '您真的要删除该数据吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request( {
					url : __ctxPath + '/admin/delBoardType.do',
					params : {
						typeId : typeId
					},
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					success : function() {
						Ext.ux.Toast.msg('操作提示', '恭喜您，数据删除成功！');
						Ext.getCmp('BoardTypeGridPanel').getStore().reload( {
							params : {
								start : 0,
								limit : 25
							}
						});
					},
					failure : function() {
						Ext.ux.Toast.msg('操作提示', '对不起，数据删除失败！');
					}
				});
			}
		});
	},
	/**
	 * 根据typeId编辑
	 */
	editById : function(typeId) {
		new BoardTypeForm( {
			typeId : typeId
		}).show();
	},
	
	/**
	 * 重置
	 */
	reset : function(){
		Ext.getCmp('boardTypeSearchPanel').getForm().reset();
	},
	
	/**
	 * 搜索
	 */
	search : function(self){
		if(self.searchPanel.getForm().isValid()){
			self.searchPanel.getForm().submit({
				url : __ctxPath + '/admin/listBoardType.do',
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
 * @description 编辑
 * @param typeId
 */
BoardTypeView.edit = function(typeId) {
	new BoardTypeForm( {
		typeId : typeId
	}).show();
};

/**
 * @description 判断GridPanel是否被选中
 * @return boolean
 */
BoardTypeView.validateSelected = function() {
	var grid = Ext.getCmp('BoardTypeGridPanel');
	var sm = grid.getSelectionModel().getSelections();
	if (sm.length > 0) {
		return true;
	} else {
		Ext.ux.Toast.msg('操作提示', '对不起，请选择操作数据！');
		return false;
	}
};

/**
 * @description 批量删除
 * @param typeIds
 *            typeId组成的字符串
 */
BoardTypeView.del = function(typeIds) {
	if (BoardTypeView.validateSelected()) {
		var gd = Ext.getCmp('BoardTypeGridPanel');
		Ext.MessageBox.confirm('删除提示', '您真的要删除您选中的数据吗？', function(btn) {
			if (btn == "yes") {
				Ext.Ajax.request( {
					url : __ctxPath + '/admin/multiDelBoardType.do',
					method : 'post',
					params : {
						typeIds : typeIds
					},
					success : function(form, action) {
						Ext.ux.Toast.msg('操作提示', '恭喜您，操作成功！');
						gd.getStore().reload( {
							params : {
								start : 0,
								limit : 25
							}
						});
						Ext.getCmp('BoardTypeView').close();
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('操作提示', '对不起，您提示的数据失败！');
						Ext.getCmp('BoardTypeView').close();
					}
				});
			} else {
				return;
			}
		});
	}
};
