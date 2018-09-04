BpBusinessDirProStat0View = Ext.extend(Ext.Panel, {
	/*
	 * rechargeLevel : 0, titlePrefix : "",
	 */
	hiddenInfo : true,

	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.managerType) != "undefined") {
			this.managerType = _cfg.managerType;
		}
		if (this.managerType == "bulkProduct") {
			this.titlePrefix = "未维护";

		} 
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		BpBusinessDirProStat0View.superclass.constructor.call(this, {
					id : 'BpBusinessDirProStat0View_' + this.managerType,
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
							name : 'Q_businessName_S_LK',
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
							name : 'Q_proName_S_LK',
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
							name : 'Q_proNumber_S_LK',
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
		if (this.managerType == "bulkProduct") {
			this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-protect',
									text : '公示信息维护',
									xtype : 'button',
									scope : this,
									hidden : isGranted('businessDirProKeep')?false:true,
									handler : this.proKeep
								}]
					});
		} else if (this.managerType == "successProduct") {
			this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-detail',
									text : '项目编辑',
									xtype : 'button',
									scope : this,
									
									handler : this.seeMappingObligationInfo
								}]
					});
		}
           var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			 plugins : [summary],
			id : 'BpBusinessDirProGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/business/listBpBusinessDirPro.do?Q_keepStat_N_EQ="+this.State,
			fields : [{
						name : 'bdirProId',
						type : 'int'
					}, 'proId', 'businessType', 'businessId', 'businessName',
					'proName', 'proNumber', 'yearInterestRate',
					'monthInterestRate', 'dayInterestRate',
					'totalInterestRate', 'interestPeriod', 'payIntersetWay',
					'bidMoney', 'loanLife', 'bidTime', 'createTime',
					'updateTime', 'keepStat', 'schemeStat','payAcctualType'],
			columns : [{
						header : 'bdirProId',
						align:'center',
						dataIndex : 'bdirProId',
						hidden : true
					}, {
						header : 'proId',
						align:'center',
						dataIndex : 'proId',
						hidden : true
					}, {
						header : '业务类别',
						dataIndex : 'businessType',
						width : 80,
						renderer : function(value){
							if(value=='SmallLoan'){
								return '贷款业务'
							}
						}
					}, {
						header : '企业名称',
						dataIndex : 'businessName',
						width : 120
					}, {
						header : '项目名称',
						 summaryRenderer : totalMoney,
						dataIndex : 'proName',
						width : 250
					}, {
						header : '项目编号',
						dataIndex : 'proNumber',
						width : 150
					}, {
						header : '年化利率',
						dataIndex : 'yearInterestRate',
						align : 'right',
						width : 70,
						renderer : function(v){
							return v+'%'
						}
					}, {
						header : '付息方式',
						align:'center',
						dataIndex : 'payIntersetWay',
						width : 110,
						renderer : function(value){
							if(value==1){
								return '等额本息'
							}else if(value==2){
								return '等额本金'
							}else if(value==3){
								return '等本等息'
							}else if(value==4){
								return '按期收息,到期还本'
							}else if(value==5){
								return '一次性支付全部利息'
							}
						}
					}, {
						header : '未招标金额(元)',
						dataIndex : 'bidMoney',
						align : 'right',
						 summaryType : 'sum',
						width : 100,
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
						}
					}, {
						header : '期望借款期限',
						align:'center',
						width : 70,
						dataIndex : 'loanLife'
					}, {
						header : '计息周期',
						dataIndex : 'payAcctualType',
						width : 70,
						align:'center',
						renderer : function(v){
							if(v=='dayPay'){
								return '日'
							}else if(v=='monthPay'){
								return  '月'
							}else if(v=='seasonPay'){
								return '季'
							}else if(v=='yearPay'){
								return '年'
							}else if(v=='owerPay'){
								return '自定义周期'
							}
						}
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

	// 项目维护
	proKeep : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			new PlBusinessDirProKeepForm({
				    gp:this.gridPanel,
				    keep:true,
				    proId:record.data.proId,
					bdirProId:record.data.bdirProId,
					proName:record.data.proName,
					proNumber:record.data.proNumber,
					businessName:record.data.businessName,
					proType:"B_Dir",
					proIdupload:record.data.bdirProId,
				    tablename:"bp_business_dir_pro"
				}).show();
		}

	}
});
