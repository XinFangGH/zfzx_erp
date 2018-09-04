package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentInvestorList;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestEnterprise;

public class FundIntentSlInvestorList implements FundIntentInvestorList<SlSmallloanProject,InvestEnterprise> {

	@Override
	public List<FundIntent> createList(SlSmallloanProject project,
			InvestEnterprise investor) {
		return null;
	}
}
