package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseRelationPerson;

public interface EnterpriseRelationPersonService extends BaseService<EnterpriseRelationPerson>{
	public List<EnterpriseRelationPerson> getListByEnterpriseId(Integer enterpriseId);
	public void add(EnterpriseRelationPerson relationPerson);
	public void update(EnterpriseRelationPerson relationPerson);
	public EnterpriseRelationPerson getById(int id);
	public List<EnterpriseRelationPerson> getList(Integer enterpriseId,PagingBean pb);
}