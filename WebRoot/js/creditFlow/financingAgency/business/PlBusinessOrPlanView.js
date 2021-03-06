/**
 * @author
 * @class PlBusinessDirPlanView
 * @extends Ext.Panel
 * @description [PlBusinessOrPlan]管理
 * @company 智维软件
 * @createtime:
 */
PlBusinessOrPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
        
		// 调用父类构造
		PlBusinessOrPlanView.superclass.constructor.call(this, {
					id : 'PlBusinessOrPlanView'+this.status,
					title : '企业债权项目',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
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
						hidden:this.status==1?false:true,
						scope : this,
					    handler : this.seeSelRs
						},{
						iconCls : 'btn-detail',
						text : '查看借款项目详细信息',
						xtype : 'button',
						scope : this,
						handler : this.editRs
						}]
				});
		var growthColumn = new Ext.ux.grid.ProgressColumn({
					header : "计划占比",
					dataIndex : 'rate',
					width : 100,
					align:'center',
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
			id : 'PlBusinessOrPlanViewGrid'+this.status,
			url : __ctxPath
					+ "/creditFlow/financingAgency/business/listPublishBpBusinessOrPro.do?Q_orginalType_SN_EQ="+this.status,
			fields : [{
						name : 'borProId',
						type : 'int'
					}, 'proId', 'businessType', 'businessId', 'businessName','receiverName','receiverP2PAccountNumber',
					'proName', 'proNumber', 'yearInterestRate',
					'monthInterestRate', 'dayInterestRate',
					'totalInterestRate', 'interestPeriod', 'payIntersetWay',
					'bidMoney', 'loanStarTime', 'loanEndTime', 'createTime','keepStat',
					'updateTime', 'publishOrMoney', 'publishOrNum', 
					'rate','moneyPlanId','residueMoney','obligatoryPersion','payAcctualType','schemeStat'
					,'reciverId','reciverType'],
			columns : [{
						header : 'borProId',
						align:'center',
						dataIndex : 'borProId',
						hidden : true
					}, {
						header : 'proId',
						align:'center',
						dataIndex : 'proId',
						hidden : true
					}, {
						header : '业务类别',
						align:'center',
						dataIndex : 'businessType',
						width : 80,
						renderer : function(v){
							if(v=='SmallLoan'){
								return '贷款业务'
							}
						}
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
						width : 80,
						renderer : function(v){
							return v+'%'
						}
					}, {
						header : '借款金额',
						align : 'right',
						summaryType : 'sum',
						dataIndex : 'bidMoney',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+'元'
						}
					}, {
						header : '债权持有人名称',
						align : 'left',
						dataIndex : 'receiverName'
					}, {
						header : '债权持有人P2P账号',
						dataIndex : 'receiverP2PAccountNumber'
					}/*, {
						header : '借款开始时间',
						dataIndex : 'loanStarTime',
						format:'Y-m-d'
					}, {
						header : '借款结束时间',
						dataIndex : 'loanEndTime',
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
						width : 100,
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+'元'
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
					new PlBusinessorPlanForm({
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
				url : __ctxPath+ '/p2p/chechIsOpenThirdBpCustMember.do?customerId='+record.data.reciverId+"&customerType="+record.data.reciverType,
				method : 'post',
				success : function(response,request) {
					var obj= Ext.decode(response.responseText);
					if(obj.success){
						new PlOrBidListForm({
							borProId : record.data.borProId,
							isUnFinish:(record.data.rate!=100?false:true),
							proType:"B_Or",
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
	// 编辑Rs
	editRs : function(record) {
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

				
					var projectId = selected.get('moneyPlanId');
					var	idPrefix = "SmallLoanProjectInfo_";
					
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
						params : {
							businessType : "SmallLoan",
							projectId : projectId
						},
						method : 'post',
						success : function(resp, op) {
							var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
							showProjectInfoTab(record, idPrefix)
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
				
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
