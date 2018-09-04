package com.zhiwei.credit.service.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonCreditLoanHistoryDao;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonCreditLoanHistory;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonCreditLoanHistoryService;

public class PersonCreditLoanHistoryServiceImpl extends BaseServiceImpl<PersonCreditLoanHistory> implements PersonCreditLoanHistoryService{
	@SuppressWarnings("unused")
	private PersonCreditLoanHistoryDao dao;
	
	public PersonCreditLoanHistoryServiceImpl(PersonCreditLoanHistoryDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<PersonCreditLoanHistory> getListByPersonId(Integer personId,
			PagingBean pb) {
		return dao.getListByPersonId(personId, pb);
	}
}