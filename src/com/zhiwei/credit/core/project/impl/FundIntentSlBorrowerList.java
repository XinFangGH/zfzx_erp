package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentBorrowerList;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

public class FundIntentSlBorrowerList implements FundIntentBorrowerList<SlSmallloanProject,Enterprise> {

	@Override
	public List<FundIntent> createList(SlSmallloanProject project,
			Enterprise borrower) {
		// TODO Auto-generated method stub
		return null;
	}
//小贷借款人放款收息表
}
