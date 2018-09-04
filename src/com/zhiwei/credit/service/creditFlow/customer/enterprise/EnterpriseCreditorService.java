package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseCreditor;

public interface EnterpriseCreditorService extends BaseService<EnterpriseCreditor>{
	public EnterpriseCreditor getById(Integer id);
	public List<EnterpriseCreditor> getListByEnterpriseId(Integer enterpriseId,PagingBean pb);
}