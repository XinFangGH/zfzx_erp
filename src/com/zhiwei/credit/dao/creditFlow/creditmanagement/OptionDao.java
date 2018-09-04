package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;


public interface OptionDao extends BaseDao<Options>{

	public List getOptionListByIndicatorId(int indicatorId);

	public boolean addOption(Options options);

	public boolean deleteOption(int id);

	public boolean updateOption(Options options);
	
}
