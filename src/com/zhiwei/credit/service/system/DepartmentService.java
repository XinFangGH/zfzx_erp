package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.Department;

public interface DepartmentService extends BaseService<Department> {

	public List<Department> findByParentId(Long parentId);
	public List<Department> findByDepName(String depName);
	public List<Department> findByPath(String path);
	
	/**
	 * 删除某个部门及其下属部门
	 * @param depId
	 */
	public void delCascade(Long depId);
}
