package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutassure;

public interface EnterpriseOutassureDao extends BaseDao<EnterpriseOutassure>{
	public EnterpriseOutassure getById(Integer id);
	public List<EnterpriseOutassure> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}