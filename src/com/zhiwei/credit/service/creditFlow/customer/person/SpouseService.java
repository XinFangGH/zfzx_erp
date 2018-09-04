package com.zhiwei.credit.service.creditFlow.customer.person;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;

public interface SpouseService extends BaseService<Spouse>{
	public Spouse getByPersonId(Integer personId) ;
}