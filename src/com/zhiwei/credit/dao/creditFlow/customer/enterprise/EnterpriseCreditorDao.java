package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseCreditor;

public interface EnterpriseCreditorDao extends BaseDao<EnterpriseCreditor>{
	public EnterpriseCreditor getById(Integer id);
	public List<EnterpriseCreditor> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}