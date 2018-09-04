package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseRelationPerson;

public interface EnterpriseRelationPersonDao extends BaseDao<EnterpriseRelationPerson>{
	public List<EnterpriseRelationPerson> getListByEnterpriseId(Integer enterpriseId);
	public EnterpriseRelationPerson getById(int id);
	public List<EnterpriseRelationPerson> getList(Integer enterpriseId,PagingBean pb);
}