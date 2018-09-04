package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseCreditorDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseCreditor;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseCreditorService;

public class EnterpriseCreditorServiceImpl extends BaseServiceImpl<EnterpriseCreditor> implements EnterpriseCreditorService{
	@SuppressWarnings("unused")
	private EnterpriseCreditorDao dao;
	public EnterpriseCreditorServiceImpl(EnterpriseCreditorDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public EnterpriseCreditor getById(Integer id) {
		return dao.getById(id);
	}
	@Override
	public List<EnterpriseCreditor> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		
		return dao.getListByEnterpriseId(enterpriseId, pb);
	}
}