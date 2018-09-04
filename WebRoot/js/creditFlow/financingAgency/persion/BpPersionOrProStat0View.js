BpPersionOrProStat0View = Ext.extend(Ext.Panel, {
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
		
		if (this.managerType == "bulkProduct") {
			this.titlePrefix = "未维护";

		} 
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		BpPersionOrProStat0View.superclass.constructor.call(this, {
					id : 'BpPersionOrProStat0View_' + this.managerType,
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
							name : 'Q_persionName_S_LK',
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
									hidden : isGranted('persionOrProKeep'+'_'+this.openType+'_'+this.orginalType)?false:true,
									handler : this.projectKeep
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
					return '总计（元）';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			 plugins : [summary],
			id : 'BpPersionOrProStat0ViewGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/persion/listBpPersionOrPro.do?Q_orginalType_SN_EQ="+this.orginalType+"&Q_keepStat_N_EQ="+this.State,
			fields : [{
						name : 'porProId',
						type : 'int'
					}, 'proId', 'businessType', 'persionId', 'persionName',
					'proName', 'proNumber', 'yearInterestRate',
					'monthInterestRate', 'dayInterestRate',
					'totalInterestRate', 'interestPeriod', 'payIntersetWay',
					'bidMoney',  'bidTime', 'createTime',
					'updateTime', 'keepStat', 'schemeStat','loanStarTime','loanEndTime',
					'sex', 'age',
					'education', 'marriage', 'userName', 'monthIncome',
					'address', 'companyIndustry', 'companyScale', 'position',
					'workTime', 'workCity', 'houseProperty', 'houseLoan',
					'vehicleProperty', 'vehicleLoan'],
			columns : [{
						header : 'porProId',
						align:'center',
						dataIndex : 'porProId',
						hidden : true
					}, {
						header : 'proId',
						align:'center',
						dataIndex : 'proId',
						hidden : true
					}, {
						header : '业务类别',
						dataIndex : 'businessType',
						width : 70,
						renderer : function(value){
							if(value=='SmallLoan'){
								return '贷款业务'
							}
						}
					}, {
						header : '姓名',
						width : 70,
						dataIndex : 'persionName'
					}, {
						header : '项目名称',
						width : 250,
						summaryRenderer : totalMoney,
						dataIndex : 'proName'
					}, {
						header : '项目编号',
						width : 150,
						dataIndex : 'proNumber'
					}, {
						header : '年化利率',
						dataIndex : 'yearInterestRate',
						align : 'right',
						renderer : function(v){
							return v+'%'
						}
					}, {
						header : '计息周期',
						align:'center',
						dataIndex : 'payAcctualType',
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
					}, {
						header : '付息方式',
						align:'center',
						dataIndex : 'payIntersetWay',
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
						header : '转让金额(元)',
						dataIndex : 'bidMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
						}
					}, {
						header : '项目开始时间',
						align:'center',
						dataIndex : 'loanStarTime',
						format:'Y-m-d'
					}, {
						header : '项目结束时间',
						align:'center',
						dataIndex : 'loanEndTime',
						format:'Y-m-d'
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
				    keep:true,
				    proType:"P_Or",
				    record:record,// 个人债权项目信息
				    proIdupload:record.data.porProId,
				    tablename:"bp_persion_or_pro"
				}).show();
		}

	}
});
