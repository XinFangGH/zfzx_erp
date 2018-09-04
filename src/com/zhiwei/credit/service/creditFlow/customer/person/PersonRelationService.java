package com.zhiwei.credit.service.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;

public interface PersonRelationService extends BaseService<PersonRelation>{
	public List<PersonRelation> getListByPersonId(Integer personId,PagingBean pb);
	public PersonRelation getById(Integer id);
	public List<PersonRelation> getByIdAndType(int personId, String personType);
	public List<PersonRelation> getByNameAndFlag(String relationName,String flag,Integer personId);
}