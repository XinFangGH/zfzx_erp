/**
 * @author lisl
 * @class SlEarlyrepaymentDetailView
 * @description 提前还款详情
 * @extends Ext.Window
 */
SlEarlyrepaymentDetailView = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlEarlyrepaymentDetailView.superclass.constructor.call(this, {
			title : '提前还款详情',
			border : false,
			width : 966,
			height : 418,
			modal : true,
			iconCls : '',
			autoScroll : true,
			maximizable : true,
			layout : 'fit',
			items : []

		});
	},
	initUIComponents : function() {
		var jsArr = [__ctxPath
				+ '/js/creditFlow/smallLoan/finance/SmallLoanEarlyRepaymentEditView.js'];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.smallLoanEarlyRepaymentEditView = new SmallLoanEarlyRepaymentEditView({
			surplusnotMoney : this.surplusnotMoney,
			intentDate : this.intentDate,
			payintentPeriod : this.payintentPeriod,
			businessType : "SmallLoan",
			projectId : this.projectId,
			record : this.record,
			isAllReadOnly : true,
			isHidden : true
		});
		this.add(this.smallLoanEarlyRepaymentEditView);
		this.doLayout();
	}
});