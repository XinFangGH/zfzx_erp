package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseLeadteamDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseLeadteam;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseLeadteamService;

public class EnterpriseLeadteamServiceImpl extends BaseServiceImpl<EnterpriseLeadteam> implements EnterpriseLeadteamService{
	@SuppressWarnings("unused")
	private EnterpriseLeadteamDao dao;
	public EnterpriseLeadteamServiceImpl(EnterpriseLeadteamDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public EnterpriseLeadteam getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public List<EnterpriseLeadteam> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		
		return dao.getListByEnterpriseId(enterpriseId, pb);
	}
}