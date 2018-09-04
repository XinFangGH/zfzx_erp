package com.zhiwei.credit.dao.creditFlow.customer.person;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.person.ProcessPersonCarReditregistries;

public interface ProcessPersonCarReditregistriesDao extends BaseDao<ProcessPersonCarReditregistries>{
	public List<ProcessPersonCarReditregistries> queryList(int personId,PagingBean pb);
	public ProcessPersonCarReditregistries getById(Integer id);
}