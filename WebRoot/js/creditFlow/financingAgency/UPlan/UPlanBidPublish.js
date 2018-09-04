/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
UPlanBidPublish = Ext.extend(Ext.Panel, {
	// 构造函数
	state : '',
	keystr : '',
	isPresale : '',
	buttonType : '',
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.state)!="undefined"){
        	this.state=_cfg.state;
		}
		if(typeof(_cfg.keystr)!="undefined"){
        	this.keystr=_cfg.keystr;
		}
		if(typeof(_cfg.isPresale)!="undefined"){
        	this.isPresale=_cfg.isPresale;
		}
		if(typeof(_cfg.buttonType)!="undefined"){
        	this.buttonType=_cfg.buttonType;
		}
		
		// 必须先初始化组件
		this.initUIComponents();
		UPlanBidPublish.superclass.constructor.call(this, {
					id : 'UPlanBidPublish'+this.keystr+this.state+this.isPresale+this.buttonType,
					layout : 'border',
					items : [this.gridPanel,this.searchPanel],
					modal : true,
					height : 550, 
					autoWidth : true,
					boder:0,
					maximizable : true,
					iconCls : 'btn-tree-team30',
					//title : this.titlePrefix ,
					buttonAlign : 'center'

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
			        id:'UPlanBidPublishSearchPanel'+this.keystr+this.state+this.isPresale+this.buttonType,
					layout : 'form',
					region : 'north',
					border : false,
					height : 65,
					anchor : '70%',
						items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						items : [{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '计划编号',
								name : 'Q_mmNumber_S_LK',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '计划名称',
								name : 'Q_mmName_S_LK',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .1,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:50px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
						}]},{
							columnWidth : .1,
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
								iconCls : 'btn-xls',
								text : '导出列表',
								xtype : 'button',
								scope : this,
								handler : this.toExcelPlan
							}, '->',(this.state != 0) ? {} : {
								iconCls : 'btn-detail',
								name:"previewmmplan",
								text : '查看招标网页',
								xtype : 'button',
								scope : this,
								handler : this.preview
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			plugins : [summary],
			id : 'UPlanBidPublishgrid'+this.keystr+this.state+this.isPresale+this.buttonType,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlan.do?Q_keystr_S_EQ="+this.keystr+'&Q_state_N_EQ='+this.state+'&isPresale='+this.isPresale,
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
					'htmlPath', 'createtime', 'updatetime','authorityStatus','lockingLimit','moneyReceiver','receiverType'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						align:'center',
						hidden : true
					}, {
						header : this.keystr=='UPlan'? 'U计划编号':'D计划编号',
						dataIndex : 'mmNumber'
					}, {
						header :this.keystr=='UPlan'? 'U计划名称':'D计划名称',
						dataIndex : 'mmName'						
					},{
						header : '收款类型',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'receiverType',
						renderer:function(v){
							if(v=='pt'){
							  return "平台账户"
							}else return "注册用户"
						}
					},{
						header : '收款专户',
						dataIndex : 'moneyReceiver'
					}, {
						header : '派息授权状态',
						align:'center',
						dataIndex : 'authorityStatus',
						renderer:function(v){
							if(v==1){
							  return "是"
							}else return "否"
						}
					}, {
						header : '计划金额(元)',
						dataIndex : 'sumMoney',
					    align : 'right',
					    summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
						
					},{
						header : '已购买总金额（元）',
						dataIndex : 'investedMoney',
					    align : 'right',
					    summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
						
					}, {
						header : '预期年化收益率',
						dataIndex : 'yeaRate',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"%";
						}
					}, {
						header : '投资期限（个月）',
						dataIndex : 'investlimit',
						align:'center',
						renderer:function(v){
							return v;
						}
					},{
						header : '锁定期限（个月）',
						dataIndex : 'lockingLimit',
						align:'center',
						renderer:function(v){
							return v;
						}
					},  {
						header : '购买放开时间',
						align:'center',
						dataIndex : 'buyStartTime'
					}, {
						header : '购买截止时间',
						align:'center',
						dataIndex : 'buyEndTime'
					}, {
						header : '创建时间',
						align:'center',
						dataIndex : 'createtime',
						hidden:true
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
	

	// 预览
	preview : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			window.open(__p2pPath + "/creditFlow/financingAgency/getDetailPlManageMoneyPlan.do?mmplanId="+record.data.mmplanId,'_blank');
		}

	},



	

	//导出到Excel
	toExcelPlan : function(){
	    var keystr = this.keystr;
		var state = this.state;
		var isPresale = this.isPresale;
		var mmNumber = this.searchPanel.getCmpByName('Q_mmNumber_S_LK').getValue();
		var mmName = this.searchPanel.getCmpByName('Q_mmName_S_LK').getValue();
		window.open(__ctxPath + "/creditFlow/financingAgency/toExcelPlanListPlManageMoneyPlan.do?"
		        +"keystr="+keystr
		        +"&state="+state
		        +"&isPresale="+isPresale
				+"&mmNumber="+mmNumber
				+"&mmName="+mmName
		);
	}
});
