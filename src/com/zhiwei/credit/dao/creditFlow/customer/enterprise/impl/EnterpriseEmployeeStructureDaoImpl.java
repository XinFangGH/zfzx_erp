package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseEmployeeStructureDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseEmployeeStructure;

@SuppressWarnings("unchecked")
public class EnterpriseEmployeeStructureDaoImpl extends BaseDaoImpl<EnterpriseEmployeeStructure> implements EnterpriseEmployeeStructureDao{
	public EnterpriseEmployeeStructureDaoImpl() {
		super(EnterpriseEmployeeStructure.class);
	}

	@Override
	public List<EnterpriseEmployeeStructure> getListByEnterpriseId(
			Integer enterpriseId) {
		String hql = "from EnterpriseEmployeeStructure e where e.enterpriseId=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).list();
	}
}