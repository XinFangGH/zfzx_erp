/**
 * @author:
 * @class ArchFondView
 * @extends Ext.Panel
 * @description 全宗管理
 * @company 北京互融时代软件有限公司
 * @createTime:
 */
FlowFormQueryForms = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		FlowFormQueryForms.superclass.constructor.call(this, {
			id : 'FlowFormQueryForms',
			region : 'center',
			border : false,
			layout : {
				type : 'vbox',
				align : 'stretch'
			},
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			flex : 1,
			frame : false,
			border : false,
			region : 'north',
			layout : 'hbox',
			layoutConfig : {
				padding : '5'
			},
			defaults : {
				margins : '4 4 4 4'
			},
			items : [ {
				text : '表名',
				xtype : 'label'
			}, {
				fieldLabel : '',
				name : 'Q_tableName_S_LK',
				xtype : 'textfield'
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'search',
				scope : this,
				handler : this.search
			} ]
		});

		this.store = new Ext.data.JsonStore( {
			url : __ctxPath + "/flow/queryFormsFlowFormQuery.do",
			root : 'result',
			totalProperty : 'totalCounts',
			baseParams : {},
			remoteSort : true,
			fields : [ {
				name : 'tableId',
				type : 'int'
			}, 'formDefId', 'tableName', 'tableKey', 'formDef' ]
		});

		this.store.on('beforeload', function(store, options) {
				
				var baseParams = {};
				if (this.searchPanel.getForm().getEl()) {
					var formParam = Ext.Ajax.serializeForm(this.searchPanel
							.getForm().getEl());
					baseParams = Ext.urlDecode(formParam);

				}
				
				baseParams.start = 0;
				if (store.baseParams.limit)
					baseParams.limit = store.baseParams.limit;
				else
					baseParams.limit = 5;

				store.baseParams = baseParams;
			

		}, this);

		this.store.load();

		this.rowActions = new Ext.ux.grid.RowActions( {
			header : '管理',
			width : 100,
			actions : [ {
				iconCls : 'btn-showDetail',
				qtip : '详细',
				style : 'margin:0 5px 0 5px'
			} ]
		});

		var cm = new Ext.grid.ColumnModel( {
			columns : [ {
				header : 'tableId',
				sortable : false,
				dataIndex : 'tableId',
				hidden : true
			}, {
				header : '所属表单',
				sortable : false,
				dataIndex : 'formDef',
				renderer : function(formDef) {
					if (formDef)
						return formDef.formTitle;
				}
			}, {
				header : '表名',
				sortable : false,
				dataIndex : 'tableName'
			}, {
				header : '表号',
				sortable : false,
				dataIndex : 'tableKey'
			}, this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel( {
			flex : 12,
			region : 'center',
			store : this.store,
			cm : cm,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			bbar : new HT.PagingBar( {
				store : this.store
			})

		});
	},

	// 按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
});
