package com.zhiwei.credit.service.creditFlow.customer.person.impl;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.SpouseDao;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;

public class SpouseServiceImpl extends BaseServiceImpl<Spouse> implements SpouseService{
	@SuppressWarnings("unused")
	private SpouseDao dao;
	
	public SpouseServiceImpl(SpouseDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Spouse getByPersonId(Integer personId) {
		
		return dao.getByPersonId(personId);
	}
}