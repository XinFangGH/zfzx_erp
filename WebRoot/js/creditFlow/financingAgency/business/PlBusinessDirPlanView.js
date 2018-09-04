/**
 * @author
 * @class PlBusinessDirPlanView
 * @extends Ext.Panel
 * @description [PlBusinessDirPlan]管理
 * @company 智维软件
 * @createtime:
 */
PlBusinessDirPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PlBusinessDirPlanView.superclass.constructor.call(this, {
					id : 'PlBusinessDirPlanView',
					title : '企业直投招标计划制定',
					region : 'center',
					layout : 'border',
					iconCls : "btn-tree-team17",
					items : [ this.searchPanel, this.gridPanel]
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


		this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-detail',
						text : '查看及制定招标计划',
						xtype : 'button',
						scope : this,
					    handler : this.seeSelRs
						}/*,{
						iconCls : 'btn-edit',
						text : '手动关闭',
						xtype : 'button',
						scope : this,
					    handler : this.bidClose
						}*/]
				});
		var growthColumn = new Ext.ux.grid.ProgressColumn({
					header : "计划占比",
					align:'center',
					dataIndex : 'rate',
					width : 100,
					textPst : '%',
					colored : true
				
				});
         var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			 plugins : [summary],
			id : 'PlBusinessDirPlanGrid' + this.managerType,
			url : __ctxPath
					+ "/creditFlow/financingAgency/business/listPublishBpBusinessDirPro.do",
			fields : [{
						name : 'bdirProId',
						type : 'int'
					}, 'proId', 'businessType', 'businessId', 'businessName',
					'proName', 'proNumber', 'yearInterestRate',
					'monthInterestRate', 'dayInterestRate',
					'totalInterestRate', 'interestPeriod', 'payIntersetWay',
					'bidMoney', 'loanLife', 'bidTime', 'createTime',
					'updateTime', 'publishOrMoney', 'publishOrNum', 'rate','residueMoney','payAcctualType','schemeStat','keepStat','versionType'],
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
						width : 80,
						dataIndex : 'businessType',
						renderer : function(v){
							if(v=='SmallLoan'){
								return '贷款业务'
							}
						}
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
						width : 80,
						renderer : function(v){
							return v+'%'
						}
					}, {
						header : '期望借款金额',
						dataIndex : 'bidMoney',
						summaryType : 'sum',
						align : 'right',
						width : 100,
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+'元'
						}
					}, {
						header : '期望借款期限',
						align : 'center',
						width : 80,
						dataIndex : 'loanLife'
					}, {
						header : '计息周期',
						align : 'center',
						dataIndex : 'payAcctualType',
						width : 80,
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
					}/*, {
						header : '期望到位时间',
						dataIndex : 'bidTime',
						width : 150,
						format:'Y-m-d'
					}*/,growthColumn, {
						header : '计划数量',
						align : 'center',
						summaryType : 'sum',
						width : 80,
						dataIndex : 'publishOrNum'
					}, {
						header : '计划金额',
						dataIndex : 'publishOrMoney',
						align : 'right',
						summaryType : 'sum',
						width : 100,
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+'元'
						}
					},{
						header : '展示类型',
						align:'center',
						dataIndex : 'businessType',
						width : 80,
						renderer : function(v){
							if(v=='1'){
								return '版本1'
							}else if(v=='2') {
								return '版本2'
							}else {
								return '未区分版本前数据'
							}
						}
					},{
						header : '',
						width : 1,
						dataIndex : ''
					}]
				// end of columns
		});

	//	this.gridPanel.addListener('rowdblclick', this.rowClick);

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
					new PlBusinessDirPlanForm({
								id : rec.data.id
							}).show();
				});
	},

	// 查看方案
	seeSelRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			Ext.Ajax.request({
				url : __ctxPath+ '/p2p/chechIsOpenThirdBpCustMember.do?customerId='+record.data.businessId+"&customerType=b_loan",
				method : 'post',
				success : function(response,request) {
					var obj= Ext.decode(response.responseText);
					if(obj.success){
						new PlDirBidListForm({
							bdirProId : record.data.bdirProId,
							isUnFinish:(record.data.rate!=100?false:true),
							proType:"B_Dir",
							keepStat:record.data.keepStat
						}).show();
					}else{
						Ext.ux.Toast.msg('操作信息',obj.msg);
						return;
					}
				}
			});
		}

	},
	// 手动关闭
	bidClose : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
		}

	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.id);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
