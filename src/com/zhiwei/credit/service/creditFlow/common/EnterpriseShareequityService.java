package com.zhiwei.credit.service.creditFlow.common;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;

public interface EnterpriseShareequityService extends BaseService<EnterpriseShareequity> {
	public List<EnterpriseShareequity> findShareequityList(int enterpriseId);
	public EnterpriseShareequity load(int id);
	public List<EnterpriseShareequity> findList(int enterpriseId,int personid,String shareholdertype);
	
	public List<EnterpriseShareequity> findByType(int personid,String shareholdertype);
}
