loadJs = function() {
	return {
		doLoadJs : function(json) {
			// 判断js
			var s = "";
			for (var i = 0; i < json.length - 1; i++) {
				s = s + json[i] + ",";// 防止意外
			}
			var arr = [];
			if (s.match("ah_a"))
				arr.push(__ctxPath + '/js/ah_ext/factory/getFilest.js');
			if (s.match("ah_b"))
				arr.push(__ctxPath + '/js/ah_ext/factory/ahinput.js');
			jsArr = [
					__ctxPath + '/js/ah_ext/factory/getFilest.js',// 封装方法
					__ctxPath + '/js/ah_ext/factory/ahinput.js',// 封装方法
					__ctxPath + '/js/ah_ext/getPanel.js',// 创建工厂
					__ctxPath + '/js/ah_ext/doLoad.js',// 封装方法
					__ctxPath + '/js/commonFlow/ExtUD.Ext.js',// 客户信息 项目基本信息
					__ctxPath
							+ '/js/creditFlow/smallLoan/project/loadDataCommon.js',// 加载数据JS
					__ctxPath + '/js/selector/UserDialog.js',
					__ctxPath
							+ '/js/creditFlow/common/EnterpriseShareequity.js',// 股东信息
					__ctxPath
							+ '/js/creditFlow/smallLoan/materials/SlProcreditMaterialsView.js',// 贷款材料
					__ctxPath
							+ '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 贷款准入原则
					__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
					__ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 风险调查报告
					__ctxPath + '/js/creditFlow/finance/calculateFundIntent.js',
					__ctxPath
							+ '/js/creditFlow/finance/calulateFinancePanel.js',
					__ctxPath + '/js/creditFlow/finance/caluateIntentGrid.js',
					__ctxPath
							+ '/js/creditFlow/finance/calulateloadDataCommon.js',
					__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
					__ctxPath + '/js/creditFlow/mortgage/AddDzyMortgageWin.js',
					__ctxPath
							+ '/js/creditFlow/mortgage/business/BusinessForm.js',
					__ctxPath
							+ '/js/creditFlow/smallLoan/finance/BorrowerInfo.js',
					__ctxPath
							+ '/js/creditFlow/repaymentSource/RepaymentSource.js', // 第一还款来源
					__ctxPath
							+ '/js/creditFlow/guarantee/meeting/CensorMeetingCollectivityDecisionConfirm.js',
					__ctxPath
							+ '/js/creditFlow/smallLoan/meeting/MeetingSummaryForm.js',
					__ctxPath
							+ '/js/creditFlow/smallLoan/meeting/MeetingSummaryUpload.js'];

			$ImportSimpleJs(jsArr, this.constructPanel, this);
		}
	}
}()
