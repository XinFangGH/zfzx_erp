package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;

public interface EnterpriseFinanceDao extends BaseDao<EnterpriseFinance>{
	public List<EnterpriseFinance> getListByEnterpriseId(Integer enterpriseId);
	public List<EnterpriseFinance> getList(Integer enterpriseId,String textFeildId);
}