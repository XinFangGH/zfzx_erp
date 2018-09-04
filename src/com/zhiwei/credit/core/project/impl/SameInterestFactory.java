package com.zhiwei.credit.core.project.impl;

import com.zhiwei.credit.core.project.FundIntent;
import com.zhiwei.credit.core.project.FundIntentFactory;

public class SameInterestFactory implements FundIntentFactory{

	@Override
	public FundIntent createFund() {
		return new FundInfoSameInterest();
	}

}
