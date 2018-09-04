package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseOutassureDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutassure;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseOutassureService;

public class EnterpriseOutassureServiceImpl extends BaseServiceImpl<EnterpriseOutassure> implements EnterpriseOutassureService{
	@SuppressWarnings("unused")
	private EnterpriseOutassureDao dao;
	public EnterpriseOutassureServiceImpl(EnterpriseOutassureDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public EnterpriseOutassure getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public List<EnterpriseOutassure> getListByEnterpriseId(
			Integer enterpriseId, PagingBean pb) {
		
		return dao.getListByEnterpriseId(enterpriseId, pb);
	}
}