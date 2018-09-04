package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.FunUrlDao;
import com.zhiwei.credit.model.system.FunUrl;

public class FunUrlDaoImpl extends BaseDaoImpl<FunUrl> implements FunUrlDao{

	public FunUrlDaoImpl() {
		super(FunUrl.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.system.FunUrlDao#getByPathFunId(java.lang.String, java.lang.Long)
	 */
	public FunUrl getByPathFunId(String path,Long funId){
		String hql="from FunUrl fu where fu.urlPath=? and fu.appFunction.functionId=? ";
		return (FunUrl)findUnique(hql, new Object[]{path,funId});
	}

}