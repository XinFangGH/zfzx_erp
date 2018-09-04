package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterprisePrizeDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterprisePrize;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterprisePrizeService;

public class EnterprisePrizeServiceImpl extends BaseServiceImpl<EnterprisePrize> implements EnterprisePrizeService{
	@SuppressWarnings("unused")
	private EnterprisePrizeDao dao;
	public EnterprisePrizeServiceImpl(EnterprisePrizeDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public EnterprisePrize getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public List<EnterprisePrize> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		
		return dao.getListByEnterpriseId(enterpriseId, pb);
	}
}