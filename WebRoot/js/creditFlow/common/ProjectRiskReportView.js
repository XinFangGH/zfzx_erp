/**
 * @author lisl
 * @class RiskReportView
 * @description 风险部综合分析报告
 * @extends Ext.Window
 */
ProjectRiskReportView = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ProjectRiskReportView.superclass.constructor.call(this, {
			title : '风险部综合分析报告',
			width : 733,
			height : 430,
			modal : true,
			border : false,
			iconCls : '',
			autoScroll : true,
			maximizable : true,
			layout : 'fit',
			items : [],
			scope : this,
			listeners : {
				beforeclose : function(win) {
					this.slRiskReportView.grid_RiskReportPanel.stopEditing();
				}
			}
		});
	},
	initUIComponents : function() {
		var jsArr = [__ctxPath + '/js/creditFlow/report/SlRiskReportView.js'// 风险分析综合报告
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},// 初始化组件
	constructPanel : function() {
		this.slRiskReportView = new SlRiskReportView({
			projectId : this.projectId,
			businessType : this.businessType,
			isHidden_riskReport : this.isHidden_riskReport,
			isgdEdit_riskReport : this.isgdEdit_riskReport,
			isHiddenAffrim_riskReport : false
		});
		this.add(this.slRiskReportView);
		this.doLayout();
	}
});