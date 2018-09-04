package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.DepartmentDao;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.service.system.DepartmentService;

public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements
		DepartmentService {

	private DepartmentDao dao;
	public DepartmentServiceImpl(DepartmentDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<Department> findByParentId(Long parentId) {
		return dao.findByParentId(parentId);
	}
	@Override
	public List<Department> findByDepName(String depName) {
		return dao.findByDepName(depName);
	}
	@Override
	public List<Department> findByPath(String path) {
		return dao.findByPath(path);
	}

	/**
	 * 删除某个部门及其下属部门
	 * @param depId
	 */
	public void delCascade(Long depId){
		Department dep=get(depId);
		evict(dep);
		List<Department> listDeps=findByPath(dep.getPath());
		for(Department o:listDeps){
			remove(o);
		}
	}
	
}
