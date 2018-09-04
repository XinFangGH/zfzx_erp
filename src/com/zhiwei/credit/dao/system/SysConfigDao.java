package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.SysConfig;

/**
 * 
 * @author 
 *
 */
public interface SysConfigDao extends BaseDao<SysConfig>{
	
	public SysConfig findByKey(String key);
	
	public List<SysConfig> findConfigByTypeKey(String typeKey);
	
	public List findTypeKeys();
	
}