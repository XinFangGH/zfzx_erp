package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseOutinvestDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutinvest;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseOutinvestService;

public class EnterpriseOutinvestServiceImpl extends BaseServiceImpl<EnterpriseOutinvest> implements EnterpriseOutinvestService{
	@SuppressWarnings("unused")
	private EnterpriseOutinvestDao dao;
	public EnterpriseOutinvestServiceImpl(EnterpriseOutinvestDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public EnterpriseOutinvest getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public List<EnterpriseOutinvest> getListByEnterpriseId(
			Integer enterpriseId, PagingBean pb) {
		
		return dao.getListByEnterpriseId(enterpriseId, pb);
	}
}