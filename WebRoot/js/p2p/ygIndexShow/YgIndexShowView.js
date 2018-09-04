/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
YgIndexShowView = Ext.extend(Ext.Panel, {
	// 构造函数
	type : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(_cfg.type != 'undefined'){
			this.type=_cfg.type
		}
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		YgIndexShowView.superclass.constructor.call(this, {
					id : 'YgIndexShowView'+this.type,
					region : 'center',
					layout : 'border',
					iconCls:"menu-flowManager",
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-edit',
								text : '修改详情',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'YgIndexShowViewGrid'+this.type,
			url : __ctxPath + "/p2p/listIndexShow.do?Q_type_S_EQ="+this.type,
			fields : [{
						name : 'id',
						type : 'int'
					},
						'oneLevelType'
					    ,'twoLevelType'
						,'description'
						,'updateTime'
	                    ,'insertTime'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '一级类型',
						width:30,
						dataIndex : 'oneLevelType'
					},{
						header : '类型',
						dataIndex : 'twoLevelType',
						width:30
					},{
						header : '修改日期',
						dataIndex : 'updateTime',
						width:30
					},{
						header : '内容描述',
						dataIndex : 'description'
					}]
			});


	},

	editRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record=selectRs[0];
			var id=record.data.id;
		    new YgIndexShowForm({
				id : id
			}).show();
		}
	}

});
