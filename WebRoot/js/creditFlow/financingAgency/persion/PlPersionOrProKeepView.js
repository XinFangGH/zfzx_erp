PlPersionOrProKeepView = Ext.extend(Ext.Panel, {
	/*
	 * rechargeLevel : 0, titlePrefix : "",
	 */
	hiddenInfo : false,

	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.managerType) != "undefined") {
			this.managerType = _cfg.managerType;
		}
		
		if (typeof(_cfg.orginalType) != "undefined") {
			this.orginalType = _cfg.orginalType;
		}
		
		if (this.managerType == "successProduct") {
			this.titlePrefix = "已维护";
			this.hiddenInfo = true;

		}
		Ext.applyIf(this, _cfg);

		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PlPersionOrProKeepView.superclass.constructor.call(this, {
					id : 'PlPersionOrProKeepView' + this.managerType,
					title : this.titlePrefix,
					region : 'center',
					layout : 'border',
					iconCls : "btn-tree-team17",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					region : 'north',
					height :40,
					items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'Q_bpPersionOrPro.persionName_S_LK',
							anchor : '100%'
						}]
						
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '项目名称',
							name : 'Q_bpPersionOrPro.proName_S_LK',
							anchor : '100%'
						}]
						
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '项目编码',
							name : 'Q_bpPersionOrPro.proNumber_S_LK',
							anchor : '100%'
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						style : 'margin-left:20px',
						items : [{
							xtype : 'button',
							text : '查询',
							scope : this,
							iconCls : 'btn-search',
							handler : this.search
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						items : [{
							xtype : 'button',
							text : '重置',
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
									iconCls : 'btn-protect',
									text : '公示信息维护',
									xtype : 'button',
									scope : this,
									hidden : isGranted('persionOrProKeep'+'_'+this.openType+'_'+this.orginalType)?false:true,
									handler : this.projectKeep
								}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			//rowActions : true,
			id : 'PlOrProKeepViewGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/persion/listPlPersionDirProKeep.do?sort=updateDate&dir=desc&Q_proType_S_EQ="+this.type+"&Q_bpPersionOrPro.orginalType_SN_EQ="+this.orginalType,
			fields : [{
						name : 'keepId',
						type : 'int'
					}, 'pOrProId', {
						name : 'typeId',
						mapping : 'plKeepProtype.name'
					}, {
						name : 'proName',
						mapping : 'bpPersionOrPro.proName'
					}, {
						name : 'proNumber',
						mapping : 'bpPersionOrPro.proNumber'
					}, {
						name : 'proId',
						mapping : 'bpPersionOrPro.proId'
					}, {
						name : 'creditLevelId',
						mapping : 'plKeepCreditlevel.name'
					}, 'proDes', 'proUseWay', 'proPayMoneyWay',
					'proRiskCtr', 'proBusinessScope', 'proBusinessStatus', /*{
						name : 'proGtOrzId',
						mapping : 'plKeepGtorz.name'
					},*/ 'proGtOrjIdea', 'proLoanMaterShow',
					'proLoanLlimitsShow', 'proLoanLevelShow', 'createDate',
					'updateDate','bpPersionOrPro','guarantorsName'],
			columns : [{
						header : 'keepId',
						align:'center',
						dataIndex : 'keepId',
						hidden : true
					}, {
						header : '项目id',
						align:'center',
						dataIndex : 'proId',
						hidden : true
						
					}, {
						header : '缓存标项目id',
						align:'center',
						dataIndex : 'pOrProId',
						hidden : true
						
					}, {
						header : '项目名称',
						width : 250,
						dataIndex : 'proName'
						
					}, {
						header : '项目编号',
						width : 150,
						dataIndex : 'proNumber'
						
					}, {
						header : '借款项目类型',
						dataIndex : 'typeId'
					}, {
						header : '信用等级',
						align:'center',
						dataIndex : 'creditLevelId'
					}, {
						header : '担保机构',
						dataIndex : 'guarantorsName'
					},  {
						header : '创建时间',
						align:'center',
						dataIndex : 'createDate',
						format:'Y-m-d'
					}, {
						header : '修改时间',
						align:'center',
						dataIndex : 'updateDate',
						format:'Y-m-d'
					}/*, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								actions : [ {
											iconCls : 'btn-edit',
											qtip : '编辑',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})*/]
				// end of columns
		});
	this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlOrProKeepForm({
								keepId : rec.data.keepId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlOrProKeepForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/persion/multiDelPlPersionOrProKeep.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/persion/multiDelPlPersionOrProKeep.do',
			grid : this.gridPanel,
			idName : 'keepId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		    
		new PlPersionDirProKeepForm({
					keepId : record.data.keepId,
					gp:this.gridPanel,
					record:record
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.keepId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	},
	projectKeep : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			
			new PlPersionDirProKeepForm({
				    gp:this.gridPanel,
				    keepId : record.data.keepId,
				    keep:true,
				    proType:"P_Or",
				    record:record,// 个人债权项目信息
				    proIdupload:record.data.bpPersionOrPro.porProId,
				    tablename:"bp_persion_or_pro"
				}).show();
		}

	}
});
