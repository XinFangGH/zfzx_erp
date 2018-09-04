package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.AppFunctionDao;
import com.zhiwei.credit.model.system.AppFunction;

public class AppFunctionDaoImpl extends BaseDaoImpl<AppFunction> implements AppFunctionDao{

	public AppFunctionDaoImpl() {
		super(AppFunction.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.system.AppFunctionDao#getByKey(java.lang.String)
	 */
	public AppFunction getByKey(String key){
		String hql="from AppFunction af where af.funKey=?";
		return (AppFunction)findUnique(hql, new String[]{key});
	}

}