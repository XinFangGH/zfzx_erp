package com.zhiwei.credit.dao.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonThereunder;

public interface PersonThereunderDao extends BaseDao<PersonThereunder>{
	public List<PersonThereunder> getListByPersonId(Integer personId,PagingBean pb);
	public PersonThereunder getById(Integer id);
}