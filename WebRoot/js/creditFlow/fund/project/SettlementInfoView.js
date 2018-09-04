/**
 * @author
 * @class SettlementReviewerPayView
 * @extends Ext.Panel
 * @description [SettlementReviewerPay]管理
 * @company 智维软件
 * @createtime:
 */
SettlementInfoView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SettlementInfoView.superclass.constructor.call(this, {
					id : 'SettlementInfoView',
					title : '每日结算详情',
					layout : 'fit',
					modal : true,
					height : 300,
					width : 550,
					maximizable : true,
					buttonAlign : 'center',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		this.topbar = new Ext.Toolbar({
		items : [{
				iconCls : 'btn-xls',
				text : '导出excel',
				xtype : 'button',
				scope : this,
				handler :function(){
						window.open(__ctxPath+ '/web/shipsExcelSettlementReviewerPay.do?infoId='+this.infoId,'_blank');
					}
			}]
		
		})
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			//tbar : this.topbar,
			//使用RowActions
			tbar :   this.topbar ,
			rowActions : false,
			id : 'SettlementInfoViewGrid',
			url : __ctxPath
					+ "/web/listByShipsSettlementReviewerPay.do?infoId="+this.infoId,
			fields : [{
						name : 'ownershipId',
						type : 'int'
					},'investId','investName','infoId','borrowerId','borrower','bidId','bidName','transferDate','reMainMoney','investTrueName','borrowerName'],
			columns : [{
						header : 'ownershipId',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '标的名称',
						dataIndex : 'bidName'
					}, {
						header : '日期',
						dataIndex : 'transferDate'
					}, {
						header : '保有量金额',
						dataIndex : 'reMainMoney'
					}, {
						header : '投资人账号',
						dataIndex : 'investName',
						hidden : this.type=='3'?true:false
					},{
						header : '投资人姓名',
						dataIndex : 'investTrueName',
						hidden : this.type=='3'?true:false
					}, {
						header : '借款人账号',
						dataIndex : 'borrower',
						hidden : (this.type=='1'||this.type=='2')?true:false
					},{
						header : '借款人姓名',
						dataIndex : 'borrowerName',
						hidden : (this.type=='1'||this.type=='2')?true:false
					}]
				//end of columns
			});

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new SettlementReviewerPayForm({
								id : rec.data.id
							}).show();
				});
	},
	//创建记录
	createRs : function() {
		new SettlementReviewerPayForm().show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.fund.project/multiDelSettlementReviewerPay.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.fund.project/multiDelSettlementReviewerPay.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function(record) {
		new SettlementReviewerPayForm({
					id : record.data.id
				}).show();
	},
	//行的Action
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
