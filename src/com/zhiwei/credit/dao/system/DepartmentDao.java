package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.Department;

public interface DepartmentDao extends BaseDao<Department> {

	public List<Department> findByParentId(Long parentId);
	public List<Department> findByVo(Department department,PagingBean pb);
	public List<Department> findByDepName(String depName);
	public List<Department> findByPath(String path);
}
