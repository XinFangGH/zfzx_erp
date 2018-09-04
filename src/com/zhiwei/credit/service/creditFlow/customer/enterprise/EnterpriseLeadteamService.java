package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseLeadteam;

public interface EnterpriseLeadteamService extends BaseService<EnterpriseLeadteam>{
	public EnterpriseLeadteam getById(Integer id);
	public List<EnterpriseLeadteam> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}