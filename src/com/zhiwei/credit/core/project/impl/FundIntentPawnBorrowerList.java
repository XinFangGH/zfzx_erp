package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentBorrowerList;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;

public class FundIntentPawnBorrowerList implements FundIntentBorrowerList<BpFundProject,Enterprise> {
//典当行(第三方)放款收息表

	@Override
	public List<FundIntent> createList(BpFundProject project,
			Enterprise borrower) {
		// TODO Auto-generated method stub
		return null;
	}
}
