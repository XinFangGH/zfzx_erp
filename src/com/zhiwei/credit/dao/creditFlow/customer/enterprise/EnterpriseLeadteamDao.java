package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseLeadteam;

public interface EnterpriseLeadteamDao extends BaseDao<EnterpriseLeadteam>{
	public EnterpriseLeadteam getById(Integer id);
	public List<EnterpriseLeadteam> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}