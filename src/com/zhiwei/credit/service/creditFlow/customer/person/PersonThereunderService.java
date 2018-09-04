package com.zhiwei.credit.service.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonThereunder;

public interface PersonThereunderService extends BaseService<PersonThereunder>{
	public List<PersonThereunder> getListByPersonId(Integer personId,PagingBean pb);
	public PersonThereunder getById(Integer id);
	public void addPerson(PersonThereunder personThereunder)throws Exception;
}