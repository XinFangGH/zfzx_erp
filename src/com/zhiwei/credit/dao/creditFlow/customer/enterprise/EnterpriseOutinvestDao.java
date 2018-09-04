package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutinvest;

public interface EnterpriseOutinvestDao extends BaseDao<EnterpriseOutinvest>{
	public EnterpriseOutinvest getById(Integer id);
	public List<EnterpriseOutinvest> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}