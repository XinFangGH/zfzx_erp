package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDebtDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseDebt;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseDebtService;

public class EnterpriseDebtServiceImpl extends BaseServiceImpl<EnterpriseDebt> implements EnterpriseDebtService{
	@SuppressWarnings("unused")
	private EnterpriseDebtDao dao;
	public EnterpriseDebtServiceImpl(EnterpriseDebtDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public EnterpriseDebt getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public List<EnterpriseDebt> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		
		return dao.getListByEnterpriseId(enterpriseId, pb);
	}
}