package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.ProcessModuleDao;
import com.zhiwei.credit.model.flow.ProcessModule;

@SuppressWarnings("unchecked")
public class ProcessModuleDaoImpl extends BaseDaoImpl<ProcessModule> implements ProcessModuleDao{

	public ProcessModuleDaoImpl() {
		super(ProcessModule.class);
	}

	@Override
	public ProcessModule getByKey(String string) {
		String hql = "from ProcessModule pm where pm.modulekey=?";
		List<ProcessModule> list = findByHql(hql, new Object[]{string});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}