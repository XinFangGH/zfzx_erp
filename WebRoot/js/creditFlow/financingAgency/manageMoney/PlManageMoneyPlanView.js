/**
 * @author
 * @class PlManageMoneyPlanView
 * @extends Ext.Panel
 * @description [PlManageMoneyPlan]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlManageMoneyPlanView.superclass.constructor.call(this, {
					id : 'PlManageMoneyPlanView',
					title : '理财计划制定',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'processModuleSearchPanel',
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor : '95%'

						},
						items : [{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel : '理财计划名称',
										name : 'Q_mmName_S_LK',
										xtype : 'textfield'
										

									}]
						}				, {
										columnWidth : .07,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										}]},	
											{columnWidth : .07,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '重置',
											scope : this,
											iconCls : 'btn-reset',
											handler : this.reset
										}]
									}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			singleSelect:true,
			// 使用RowActions
			id : 'PlManageMoneyPlanGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlan.do?Q_keystr_S_EQ=mmplan",
			fields : [{
						name : 'mmplanId',
						type : 'int'
					}, 'mmName', 'mmNumber', 'manageMoneyTypeId', 'keystr',
					'investScope', 'benefitWay', 'buyStartTime', 'buyEndTime',
					'startMoney', 'riseMoney', 'limitMoney',
					'startinInterestCondition', 'expireRedemptionWay',
					'chargeGetway', 'guaranteeWay', 'yeaRate', 'investlimit',
					'sumMoney', 'state', 'startinInterestTime',
					'endinInterestTime', 'investedMoney', 'bidRemark',
					'htmlPath', 'createtime', 'updatetime'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						hidden : true
					}/*, {
						header : 'manageMoneyTypeId',
						dataIndex : 'manageMoneyTypeId'
					}*/, {
						header : '理财计划名称',
						dataIndex : 'mmName'
					}, {
						header : '理财计划编号',
						dataIndex : 'mmNumber'
					}, {
						header : '招标金额',
						dataIndex : 'sumMoney',
					    align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
						
					}, {
						header : '已购买总金额',
						dataIndex : 'investedMoney',
					    align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
						
					}, {
						header : '年化收益率',
						dataIndex : 'yeaRate',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.000')+"%";
						}
					}, {
						header : '投资期限',
						dataIndex : 'investlimit',
						align : 'right',
						renderer:function(v){
							return v+"个月";
						}
					},  {
						header : '购买放开时间',
						dataIndex : 'buyStartTime'
					}, {
						header : '购买截止时间',
						dataIndex : 'buyEndTime'
					}, {
						header : '创建时间',
						dataIndex : 'createtime'
					}]
				// end of columns
			});


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
					new PlManageMoneyPlanForm({
								mmplanId : rec.data.mmplanId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlManageMoneyPlanForm().show({isAllReadOnly:false});
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/multiDelPlManageMoneyPlan.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if(parseFloat(s[0].data.investedMoney)==0){
		  $delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/multiDelPlManageMoneyPlan.do',
			grid : this.gridPanel,
			idName : 'mmplanId'
		});
		}else{
		Ext.ux.Toast.msg('操作信息', '已被投标不能删除！');
		}
		
	},
	// 编辑Rs
	editRs : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			if(s[0].data.state>0){
			 alert("该计划已经发布不能进行编辑！只能查看。");
			 new PlManageMoneyPlanForm({
						mmplanId : s[0].data.mmplanId,
						isAllReadOnly:true,
						isHidden:true,
						seeHidden :true
		 }).show();
			}else{
				new PlManageMoneyPlanForm({
						mmplanId : s[0].data.mmplanId,
						isAllReadOnly:false,
						readOnly:false,
						isHidden:false
		 }).show();
			}
			
		}
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.mmplanId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
