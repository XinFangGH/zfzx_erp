package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterprisePrize;

public interface EnterprisePrizeService extends BaseService<EnterprisePrize>{
	public EnterprisePrize getById(Integer id);
	public List<EnterprisePrize> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}