package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.FunUrl;

/**
 * 
 * @author 
 *
 */
public interface FunUrlDao extends BaseDao<FunUrl>{
	/**
	 * 按path及functionId查找
	 * @param path
	 * @param funId
	 * @return
	 */
	public FunUrl getByPathFunId(String path,Long funId);
}