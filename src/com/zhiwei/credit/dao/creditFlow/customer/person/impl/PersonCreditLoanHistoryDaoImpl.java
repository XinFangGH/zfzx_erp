package com.zhiwei.credit.dao.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonCreditLoanHistoryDao;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonCreditLoanHistory;

@SuppressWarnings("unchecked")
public class PersonCreditLoanHistoryDaoImpl extends BaseDaoImpl<PersonCreditLoanHistory> implements PersonCreditLoanHistoryDao{

	public PersonCreditLoanHistoryDaoImpl() {
		super(PersonCreditLoanHistory.class);
	}

	@Override
	public List<PersonCreditLoanHistory> getListByPersonId(Integer personId,
			PagingBean pb) {
		String hql="from PersonCreditLoanHistory as p where p.personId=?";
		List<PersonCreditLoanHistory> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{personId});
		}else{
			list=this.findByHql(hql, new Object[]{personId},pb);
		}
		return list;
	}
}