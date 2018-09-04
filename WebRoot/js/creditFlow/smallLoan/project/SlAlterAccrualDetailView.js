/**
 * @author lisl
 * @class SlAlterAccrualDetailView
 * @description 利率变更详情
 * @extends Ext.Window
 */
SlAlterAccrualDetailView = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlAlterAccrualDetailView.superclass.constructor.call(this, {
			title : '利率变更详情',
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
				+ '/js/creditFlow/smallLoan/finance/SmallLoanAlterAccrualEditView.js'];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.smallLoanAlterAccrualEditView = new SmallLoanAlterAccrualEditView({
			surplusnotMoney : this.surplusnotMoney,
			intentDate : this.intentDate,
			payintentPeriod : this.payintentPeriod,
			businessType : "SmallLoan",
			projectId : this.projectId,
			slAlterAccrualRecord : this.record
		});
		this.add(this.smallLoanAlterAccrualEditView);
		this.doLayout();
	}
});