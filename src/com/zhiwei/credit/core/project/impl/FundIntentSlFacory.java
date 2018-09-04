package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentFacory;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

public class FundIntentSlFacory implements FundIntentFacory<SlSmallloanProject,BpFundIntent> {

	@Override
	public List<FundIntent> createBorrowerFundList(SlSmallloanProject project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FundIntent> createInvestorFundList(SlSmallloanProject project,
			BpFundIntent investor) {
		// TODO Auto-generated method stub
		return null;
	}

}
