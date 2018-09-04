package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseEmployeeStructure;

public interface EnterpriseEmployeeStructureDao extends BaseDao<EnterpriseEmployeeStructure>{
	public List<EnterpriseEmployeeStructure> getListByEnterpriseId(Integer enterpriseId);
}