package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseDebt;

public interface EnterpriseDebtDao extends BaseDao<EnterpriseDebt>{
	public EnterpriseDebt getById(Integer id);
	public List<EnterpriseDebt> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}