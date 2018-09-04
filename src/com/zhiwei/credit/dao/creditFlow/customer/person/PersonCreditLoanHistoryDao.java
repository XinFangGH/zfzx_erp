package com.zhiwei.credit.dao.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonCreditLoanHistory;

public interface PersonCreditLoanHistoryDao extends BaseDao<PersonCreditLoanHistory>{
	public List<PersonCreditLoanHistory> getListByPersonId(Integer personId,
			PagingBean pb) ;
}