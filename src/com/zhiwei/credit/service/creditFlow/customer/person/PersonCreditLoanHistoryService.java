package com.zhiwei.credit.service.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonCreditLoanHistory;

public interface PersonCreditLoanHistoryService extends BaseService<PersonCreditLoanHistory>{
	public List<PersonCreditLoanHistory> getListByPersonId(Integer personId,PagingBean pb);
}