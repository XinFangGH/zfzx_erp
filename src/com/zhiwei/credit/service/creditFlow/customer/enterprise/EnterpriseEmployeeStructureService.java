package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseEmployeeStructure;

public interface EnterpriseEmployeeStructureService extends BaseService<EnterpriseEmployeeStructure>{
	public List<EnterpriseEmployeeStructure> getListByEnterpriseId(Integer enterpriseId);
}