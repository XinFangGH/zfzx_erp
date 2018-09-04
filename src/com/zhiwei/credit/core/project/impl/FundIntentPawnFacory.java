package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentFacory;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestEnterprise;

public class FundIntentPawnFacory implements FundIntentFacory<BpFundProject, InvestEnterprise> {

	@Override
	public List<FundIntent> createBorrowerFundList(BpFundProject project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FundIntent> createInvestorFundList(BpFundProject project,
			InvestEnterprise investor) {
		// TODO Auto-generated method stub
		return null;
	}

}
