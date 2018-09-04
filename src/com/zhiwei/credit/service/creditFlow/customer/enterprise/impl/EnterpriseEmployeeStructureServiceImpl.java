package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseEmployeeStructureDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseEmployeeStructure;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseEmployeeStructureService;


public class EnterpriseEmployeeStructureServiceImpl extends BaseServiceImpl<EnterpriseEmployeeStructure> implements EnterpriseEmployeeStructureService{
	@SuppressWarnings("unused")
	private EnterpriseEmployeeStructureDao dao;
	public EnterpriseEmployeeStructureServiceImpl(EnterpriseEmployeeStructureDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<EnterpriseEmployeeStructure> getListByEnterpriseId(
			Integer enterpriseId) {
		return dao.getListByEnterpriseId(enterpriseId);
	}
}