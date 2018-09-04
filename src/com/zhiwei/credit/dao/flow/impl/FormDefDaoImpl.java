package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.FormDefDao;
import com.zhiwei.credit.model.flow.FormDef;

public class FormDefDaoImpl extends BaseDaoImpl<FormDef> implements FormDefDao{

	public FormDefDaoImpl() {
		super(FormDef.class);
	}

	@Override
	public List<FormDef> getByDeployId(String deployId) {
		String hql="from FormDef fd where deployId=?";
		return findByHql(hql, new Object[]{deployId});
	}
	
	/**
	 * 
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public FormDef getByDeployIdActivityName(String deployId,String activityName){
		String hql="from FormDef fd where fd.deployId=? and fd.activityName=?";
		return (FormDef)findUnique(hql, new Object[]{deployId,activityName});
	}
}