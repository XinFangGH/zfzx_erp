package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.ProcessModuleDao;
import com.zhiwei.credit.model.flow.ProcessModule;
import com.zhiwei.credit.service.flow.ProcessModuleService;

public class ProcessModuleServiceImpl extends BaseServiceImpl<ProcessModule> implements ProcessModuleService{
	@SuppressWarnings("unused")
	private ProcessModuleDao dao;
	
	public ProcessModuleServiceImpl(ProcessModuleDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ProcessModule getByKey(String string) {
		return dao.getByKey(string);
	}

}