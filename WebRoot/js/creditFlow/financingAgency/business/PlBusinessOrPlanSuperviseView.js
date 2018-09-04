/**
 * @author
 * @class PlBusinessOrPlanSuperviseView
 * @extends Ext.Panel
 * @description [PlBusinessOrPlanSuperviseView]管理
 * @company 互融软件
 * @createtime:
 */
PlBusinessOrPlanSuperviseView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PlBusinessOrPlanSuperviseView.superclass.constructor.call(this, {
					id : 'PlBusinessOrPlanSuperviseView'+this.status,
					title : '企业展期债权项目',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [ this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel( {
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
							name : 'persionName',
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
							name : 'proName',
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
							name : 'proNumber',
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
				})

		this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'menu-flowWait',
						text : '展期债权提交',
						xtype : 'button',
						scope : this,
						handler : this.editRs
					}]
				});
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
//    		id : 'PlBusinessOrPlanSuperviseView'+this.status,
			url : __ctxPath
					+ "/creditFlow/financingAgency/business/bpSuperviseListBpBusinessOrPro.do?orginalType="+this.status,
			fields : [{
						name : 'borProId',
						type : 'int'
					}, 'proId', 'businessType', 
					'proName', 'proNumber', 'yearInterestRate',
					
					'bidMoney', 'loanStarTime', 'loanEndTime', 
					'moneyPlanId', 'superviseRecordId','startDate','endDate','continuationMoney'],
			columns : [{
						header : 'borProId',
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
						header : '借款金额(元)',
						dataIndex : 'bidMoney',
						align : 'right',
						width : 100,
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
						}
					}, {
						header : '借款开始时间',
						dataIndex : 'loanStarTime',
						width : 100,
						align : 'center',
						format:'Y-m-d'
					}, {
						header : '借款结束时间',
						dataIndex : 'loanEndTime',
						width : 100,
						align : 'center',
						format:'Y-m-d'
					},{
						header : '展期金额(元)',
						dataIndex : 'continuationMoney',
						align : 'right',
						summaryType : 'sum',
						width : 100,
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
						}
					},  {
						header : '展期开始时间',
						dataIndex : 'startDate',
						align : 'center',
						width : 100,
						format:'Y-m-d'
					}, {
						header : '展期结束时间',
						align : 'center',
						dataIndex : 'endDate',
						width : 100,
						format:'Y-m-d'
					}]
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
					new PlPersionorPlanForm({
								id : rec.data.id
							}).show();
				});
	},

	// 编辑Rs
	editRs : function(record) {
			var selected = this.gridPanel.getSelectionModel().getSelected();
			var objGridPanel =this.gridPanel
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

					var sprojectId = selected.get('proId');
					var fundProjectId=selected.get('moneyPlanId');
					var projectId=selected.get('superviseRecordId');
					var borProId=selected.get('borProId');
					var businessType=selected.get('businessType');
				  new PlPersionOrPlanSuperviseRightForm({sprojectId:sprojectId,fundProjectId:fundProjectId,projectId:projectId,borProId:borProId,businessType:businessType,objGridPanel:objGridPanel}).show()
				
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
s