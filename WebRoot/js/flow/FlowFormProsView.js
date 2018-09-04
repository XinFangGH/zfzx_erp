/**
 * @author:
 * @class ArchFondView
 * @extends Ext.Panel
 * @description 全宗管理
 * @company 北京互融时代软件有限公司
 * @createTime:
 */
FlowFormProsView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		FlowFormProsView.superclass.constructor.call(this, {
			id : 'FlowFormProsView',
			title : '流程表单查询',
			iconCls:'menu-biaodan',
			region : 'center',
			layout : 'border',
			items : [ this.leftPanel, this.proPanel ]
		});
	},// end of constructor
	// 初始化组件
	typeId:0,
	initUIComponents : function() {

		this.leftPanel = new Ext.Panel( {
			layout : 'fit',
			region : 'west',
			collapsible : true,
			split : true,
			width : 200,
			title : '流程分类树',
			items : [ new zhiwei.ux.TreePanelEditor( {
				border : false,
				autoScroll : true,
				url : __ctxPath + '/system/flowTreeGlobalType.do?catKey=FLOW',
				onclick : function(node) {
					this.typeId=node.id;
					var QueryForms = Ext.getCmp('FlowFormProsView').QueryForms;
					QueryForms.store.proxy.conn.url = __ctxPath
							+ '/flow/queryFormsFlowFormQuery.do?typeId=' + node.id;
					QueryForms.store.load( {
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			}) ]
		});


		

		this.QueryForms = new FlowFormQueryForms({}).show();
		this.QueryForms.gridPanel.addListener('rowdblclick',
				this.QueryForms_rowClick, this);
		this.QueryForms.rowActions.on('action',
				this.QueryForms_onRowAction, this);



		this.proPanel = new Ext.Panel( {
			title : '流程表单',
			layout : 'border',
			region : 'center',
			autoScroll : true,
			items : [this.QueryForms]
		});

	},



	showForms : function(record) {
		var centerPanel = Ext.getCmp('centerTabPanel');
		centerPanel.remove(this.QueryView,true);
		
		this.QueryView = centerPanel.add(new FlowFormQueryView( {
			defId : record.data.defId,
			name : record.data.name,
			description : record.data.description,
			deployId : record.data.deployId
		}));
		
		centerPanel.activate(this.QueryView);
		
		
	},
	
	activeEntity:function(record){
		var center = Ext.getCmp('centerTabPanel');
		center.remove(this.QueryEntity,true);
		this.QueryEntity = center.add(new FlowFormQueryEntity({
			tableKey:record.data.tableKey,
			tableId:record.data.tableId,
			title : record.data.tableName
		}));
		center.activate(this.QueryEntity);
	},



	QueryForms_rowClick : function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		this.activeEntity.call(this, record);
	},
	QueryForms_onRowAction : function(grid, record, action, row, col) {

		switch (action) {
			case 'btn-showDetail' :
				this.activeEntity.call(this, record);
				break;
			default :
				break;
		}

	}

});
