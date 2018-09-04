package com.zhiwei.credit.core.project;

import java.util.List;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;

public interface FundIntentInvestorList<T1,T2> extends FundIntentList {

	public List<FundIntent> createList(T1 project,T2 investor);

}
