package com.zhiwei.credit.dao.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.person.ProcessPersonCarReditregistriesDao;
import com.zhiwei.credit.model.creditFlow.customer.person.ProcessPersonCarReditregistries;

@SuppressWarnings("unchecked")
public class ProcessPersonCarReditregistriesDaoImpl extends BaseDaoImpl<ProcessPersonCarReditregistries> implements ProcessPersonCarReditregistriesDao{

	public ProcessPersonCarReditregistriesDaoImpl() {
		super(ProcessPersonCarReditregistries.class);
	}

	@Override
	public List<ProcessPersonCarReditregistries> queryList(int personId,
			PagingBean pb) {
		String hql="from ProcessPersonCarReditregistries AS pc where pc.mortgagorId=?";
		List<ProcessPersonCarReditregistries> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{personId});
		}else{
			list=this.findByHql(hql, new Object[]{personId},pb);
		}
		return list;
	}

	@Override
	public ProcessPersonCarReditregistries getById(Integer id) {
		String hql="from ProcessPersonCarReditregistries AS pc where pc.id=?";
		return (ProcessPersonCarReditregistries) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
}