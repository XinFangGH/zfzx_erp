package com.zhiwei.credit.core.project.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.credit.core.project.FundIntentFacory;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

public class FundIntentP2PFacory implements FundIntentFacory<BpFundProject,InvestPersonInfo> {

	@Resource
	private FundIntentP2PInvestList fundIntentP2PInvestList;
	@Resource
	private FundIntentP2PBorrowerList fundIntentP2PBorrowerList;
	@Override
	public List<FundIntent> createBorrowerFundList(BpFundProject project) {
		
		return fundIntentP2PBorrowerList.createList(project, null);
	}

	@Override
	public List<FundIntent> createInvestorFundList(BpFundProject project,
			InvestPersonInfo investor) {
		
		return fundIntentP2PInvestList.createList(project, investor);
	}

}
