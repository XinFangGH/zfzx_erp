package com.zhiwei.credit.core.project;

import java.util.List;

import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

public interface FundIntent {
	List<SlFundIntent> createList(BpFundProject project,InvestPersonInfo invester);
}
