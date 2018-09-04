package com.zhiwei.credit.dao.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonRelationDao;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;

@SuppressWarnings("unchecked")
public class PersonRelationDaoImpl extends BaseDaoImpl<PersonRelation> implements PersonRelationDao{

	public PersonRelationDaoImpl() {
		super(PersonRelation.class);
	}

	@Override
	public PersonRelation getById(Integer id) {
		String hql="from PersonRelation as p where p.id=?";
		return (PersonRelation) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<PersonRelation> getListByPersonId(Integer personId,
			PagingBean pb) {
		String hql="from PersonRelation as p where p.personId=?";
		List<PersonRelation> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{personId});
		}else{
			list=this.findByHql(hql, new Object[]{personId},pb);
		}
		return list;
	}

	@Override
	public List<PersonRelation> getByIdAndType(int personId, String personType) {
		String hql="from PersonRelation as p where p.personId="+personId+" and p.relationShip="+personType;
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<PersonRelation> getByNameAndFlag(String relationName,
			String flag,Integer personId) {
		String hql="from PersonRelation as p where p.personId=? and p.relationName=? and p.flag=?";
		return this.findByHql(hql,new Object[]{personId,relationName,flag});
	}
}