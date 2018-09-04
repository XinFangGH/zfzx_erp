package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseDebt;

public interface EnterpriseDebtService extends BaseService<EnterpriseDebt>{
	public EnterpriseDebt getById(Integer id);
	public List<EnterpriseDebt> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}