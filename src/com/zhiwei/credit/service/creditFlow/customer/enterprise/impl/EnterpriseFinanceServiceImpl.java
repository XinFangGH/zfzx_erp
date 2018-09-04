package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseFinanceDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseFinanceService;

public class EnterpriseFinanceServiceImpl extends BaseServiceImpl<EnterpriseFinance> implements EnterpriseFinanceService{
	@SuppressWarnings("unused")
	private EnterpriseFinanceDao dao;
	public EnterpriseFinanceServiceImpl(EnterpriseFinanceDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<EnterpriseFinance> getListByEnterpriseId(Integer enterpriseId) {
		return dao.getListByEnterpriseId(enterpriseId);
	}
	@Override
	public List<EnterpriseFinance> getList(Integer enterpriseId,
			String textFeildId) {
		return dao.getList(enterpriseId, textFeildId);
	}
}