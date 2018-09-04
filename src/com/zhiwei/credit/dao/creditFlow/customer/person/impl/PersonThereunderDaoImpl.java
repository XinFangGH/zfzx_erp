package com.zhiwei.credit.dao.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonThereunderDao;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonThereunder;

@SuppressWarnings("unchecked")
public class PersonThereunderDaoImpl extends BaseDaoImpl<PersonThereunder> implements PersonThereunderDao{

	public PersonThereunderDaoImpl() {
		super(PersonThereunder.class);
	}

	@Override
	public List<PersonThereunder> getListByPersonId(Integer personId,
			PagingBean pb) {
		String hql="from PersonThereunder as p where p.personid=?";
		List<PersonThereunder> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{personId});
		}else{
			list=this.findByHql(hql, new Object[]{personId},pb);
		}
		return list;
	}

	@Override
	public PersonThereunder getById(Integer id) {
		String hql="from PersonThereunder as p where p.id=?";
		return (PersonThereunder) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
}