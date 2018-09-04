package com.zhiwei.credit.service.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.ProcessPersonCarReditregistries;

public interface ProcessPersonCarReditregistriesService extends BaseService<ProcessPersonCarReditregistries>{
	public List<ProcessPersonCarReditregistries> queryList(int personId,PagingBean pb);
	public ProcessPersonCarReditregistries getById(Integer id);
}