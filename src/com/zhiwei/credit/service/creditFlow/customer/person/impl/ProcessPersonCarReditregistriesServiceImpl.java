package com.zhiwei.credit.service.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.person.ProcessPersonCarReditregistriesDao;
import com.zhiwei.credit.model.creditFlow.customer.person.ProcessPersonCarReditregistries;
import com.zhiwei.credit.service.creditFlow.customer.person.ProcessPersonCarReditregistriesService;

public class ProcessPersonCarReditregistriesServiceImpl extends BaseServiceImpl<ProcessPersonCarReditregistries> implements ProcessPersonCarReditregistriesService{
	@SuppressWarnings("unused")
	private ProcessPersonCarReditregistriesDao dao;
	
	public ProcessPersonCarReditregistriesServiceImpl(ProcessPersonCarReditregistriesDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ProcessPersonCarReditregistries getById(Integer id) {
		
		return dao.getById(id);
	}

	@Override
	public List<ProcessPersonCarReditregistries> queryList(int personId,
			PagingBean pb) {
		
		return dao.queryList(personId, pb);
	}
}