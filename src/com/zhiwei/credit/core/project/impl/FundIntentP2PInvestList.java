package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentInvestorList;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;


public class FundIntentP2PInvestList implements FundIntentInvestorList<BpFundProject,InvestPersonInfo> {
//P2P投资人放款收息表

	@Override
	public List<FundIntent> createList(BpFundProject project,
			InvestPersonInfo investor) {
		
		return null;
	}
}
