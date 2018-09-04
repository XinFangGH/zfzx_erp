package com.zhiwei.credit.dao.creditFlow.customer.person;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;

public interface SpouseDao extends BaseDao<Spouse>{
	public Spouse getByPersonId(Integer personId) ;
}