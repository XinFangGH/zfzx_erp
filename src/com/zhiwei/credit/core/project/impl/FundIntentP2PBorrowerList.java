package com.zhiwei.credit.core.project.impl;

import java.util.List;

import com.zhiwei.credit.core.project.FundIntentBorrowerList;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestEnterprise;

public class FundIntentP2PBorrowerList implements FundIntentBorrowerList<BpFundProject,InvestEnterprise> {
//P2P平台借款人放款收息表
	

	@Override
	public List<FundIntent> createList(BpFundProject project,
			InvestEnterprise borrower) {
		// TODO Auto-generated method stub
		return null;
	}

}
