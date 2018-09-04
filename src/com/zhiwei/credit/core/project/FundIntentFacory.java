package com.zhiwei.credit.core.project;

import java.util.List;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;

public interface FundIntentFacory<T1,T2> {
	List<FundIntent> createInvestorFundList(T1 project,T2 investor);//投资人
	List<FundIntent> createBorrowerFundList(T1 project);//借款人
}
