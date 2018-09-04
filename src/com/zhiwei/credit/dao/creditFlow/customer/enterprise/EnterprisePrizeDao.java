package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterprisePrize;

public interface EnterprisePrizeDao extends BaseDao<EnterprisePrize>{
	public EnterprisePrize getById(Integer id);
	public List<EnterprisePrize> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}