package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;

public interface EnterpriseFinanceService extends BaseService<EnterpriseFinance>{
	public List<EnterpriseFinance> getListByEnterpriseId(Integer enterpriseId);
	public List<EnterpriseFinance> getList(Integer enterpriseId,String textFeildId);
}