package com.zhiwei.credit.service.creditFlow.finance;

import java.math.BigDecimal;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

public interface FundIntentService extends BaseService<FundIntent>{

	String savejson(String slFundIentJson, Long projectId,String businessType,Short flag,Long companyId,Long preceptId,String fundResource,Long bidPlanId);
	
	List<FundIntent> getListByPreceptId(Long preceptId);
	
	List<FundIntent> getListByBidPlanId(Long bidPlanId);
	
	List<FundIntent> createList(BpFundProject bpProject,SlSmallloanProject slProject,List<InvestPersonInfo> personInfos,List<InvestEnterprise> enterpriseInfos);
	public List<FundIntent> listNoEarlyId(Long bidPlanId,Long slEarlyRepaymentId);
}
