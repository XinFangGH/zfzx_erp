/**
 * @author
 * @class PlMmOrderAssignInterestView
 * @extends Ext.Panel
 * @description [PlMmOrderAssignInterest]管理
 * @company 智维软件
 * @createtime:
 */
PlMmOrderAssignInterestView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlMmOrderAssignInterestView.superclass.constructor.call(this, {
					id : 'PlMmOrderAssignInterestView',
					title : '收益清单',
					region : 'center',
					layout : 'border',
					height : 500,
					width : 1000,
					items : [ this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.topbar = new Ext.Toolbar({
			items : [/*	{
								iconCls : 'btn-print',
								text : '打印',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							},{
								iconCls : 'btn-user-sel',
								text : '派息',
								xtype : 'button',
								scope : this,
								handler : this.assignInterest
							},"->",*/{xtype:'label',html : '【<font style="line-height:20px" color=red>投资人：</font>'+this.investPersonName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp理财产品名称：</font>'+this.mmName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 购买金额：</font>'+Ext.util.Format.number(this.buyMoney,',000,000,000.00')+"元"},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp订单期限：</font>'+this.orderlimit+"天"},
						this.promisIncomeSum==null?'':{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 承诺总收益：</font>'+Ext.util.Format.number(this.promisIncomeSum,',000,000,000.00')+"元"}
						,this.currentGetedMoney==null?'':{xtype:'label',html : '&nbsp;&nbsp&nbsp;&nbsp<font color=red>当前已实现收益：</font>'+Ext.util.Format.number(this.currentGetedMoney,',000,000,000.00')+'元'}
						,{xtype:'label',html : ' 】'}
							]
		});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'PlMmOrderAssignInterestGrid',
	//		hiddenCm :true,
			singleSelect : true,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmOrderAssignInterest.do?Q_orderId_L_EQ="+this.orderId+"&Q_isValid_SN_EQ=0&Q_isCheck_SN_EQ=0",
			fields : [{
						name : 'assignInterestId',
						type : 'int'
					}, 'orderId', 'investPersonId', 'investPersonName',
					'mmplanId', 'mmName', 'fundType', 'incomeMoney','afterMoney','factDate','payMoney',
					'intentDate','periods','keystr'],
			columns : [{
						header : 'assignInterestId',
						dataIndex : 'assignInterestId',
						hidden : true
					}/*, {
						header : 'orderId',
						dataIndex : 'orderId'
					}, {
						header : 'investPersonId',
						dataIndex : 'investPersonId'
					}, {
						header : 'investPersonName',
						dataIndex : 'investPersonName'
					}, {
						header : 'mmplanId',
						dataIndex : 'mmplanId'
					}, {
						header : 'mmName',
						dataIndex : 'mmName'
					}*/, {
						header : '期数',
						dataIndex : 'periods',
						
						renderer:function(v){
							if(v==null){return "";}
							else{
							return "第"+v+"期"
							}
						}
					}, {
						header : '资金类型',
						dataIndex : 'fundType',
					
						renderer:function(v){
							if(v=="loanInterest"){
							   return "利息"
							}else if(v=="riskRate"){
							  return "风险金"
							}else if(v=="liquidatedDamages"){
							  return "提前赎回违约金"
							}else {
							
							  return "本金";
							
							}
						}
					}, {
						header : '收入金额',
						dataIndex : 'incomeMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '支出金额',
						dataIndex : 'payMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '计划派息日期',
						dataIndex : 'intentDate'
					}, {
						header : '已派金额',
						dataIndex : 'afterMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '实际派息日期',
						dataIndex : 'factDate'
					}]
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
					new PlMmOrderAssignInterestForm({
								assignInterestId : rec.data.assignInterestId
							}).show();
				});
	},
	// 创建记录
	assignInterest : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		Ext.Ajax.request({
			url : __ctxPath
					+ "/creditFlow/financingAgency/assignInterestPlMmOrderAssignInterest.do",
			method : 'post',
			success : function(response, request) {
				var obj=response.responseText;
				if(obj.failure==true){
					Ext.ux.Toast.msg('操作信息',obj.msg);
				}else{
					Ext.getCmp("PlMmOrderAssignInterestGrid").getStore().reload();
					Ext.ux.Toast.msg('操作信息', '派息成功');
				}
			},
			params : {
				assignInterestId : s[0].data.assignInterestId,
				investPersonId : s[0].data.investPersonId,
				keystr:s[0].data.keystr
			}
		});
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderAssignInterest.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderAssignInterest.do',
			grid : this.gridPanel,
			idName : 'assignInterestId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlMmOrderAssignInterestForm({
					assignInterestId : record.data.assignInterestId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.assignInterestId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
