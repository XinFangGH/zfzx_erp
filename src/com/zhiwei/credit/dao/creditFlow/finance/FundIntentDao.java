package com.zhiwei.credit.dao.creditFlow.finance;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;

public interface FundIntentDao extends BaseDao<FundIntent> {
	List<FundIntent> getList(String pname,String[] names,Object[] values);
	public List<FundIntent> listNoEarlyId(Long bidPlanId,Long slEarlyRepaymentId);
}
