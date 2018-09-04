package com.zhiwei.credit.service.creditFlow.common.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.service.creditFlow.common.EnterpriseShareequityService;


public class EnterpriseShareequityServiceImpl extends BaseServiceImpl<EnterpriseShareequity> implements EnterpriseShareequityService{
	private EnterpriseShareequityDao dao;
	public EnterpriseShareequityServiceImpl(EnterpriseShareequityDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<EnterpriseShareequity> findShareequityList(int enterpriseId) {
		
		return dao.findShareequityList(enterpriseId);
	}
	@Override
	public EnterpriseShareequity load(int id) {
		
		return dao.load(id);
	}
	@Override
	public List<EnterpriseShareequity> findList(int enterpriseId, int personid,
			String shareholdertype) {
		return dao.findList(enterpriseId, personid, shareholdertype);
	}
	@Override
	public List<EnterpriseShareequity> findByType(int personid,
			String shareholdertype) {
		
		return dao.findByType(personid, shareholdertype);
	}
}
