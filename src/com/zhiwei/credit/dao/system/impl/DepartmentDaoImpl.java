package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.DepartmentDao;
import com.zhiwei.credit.model.system.Department;

public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

	public DepartmentDaoImpl() {
		super(Department.class);
	}

	@Override
	public List<Department> findByParentId(Long parentId) {
		final String hql = "from Department d where d.parentId=?";
		Object[] params ={parentId};
		return findByHql(hql, params);
	}

	@Override
	public List<Department> findByVo(Department department, PagingBean pb) {
		ArrayList paramList=new ArrayList();
		String hql="from Department vo where 1=1";
		if(StringUtils.isNotEmpty(department.getDepName())){
			hql+=" and vo.depName like ?";
			paramList.add("%"+department.getDepName()+"%");
		}
		if(StringUtils.isNotEmpty(department.getDepDesc())){
			hql+=" and vo.depDesc=?";
			paramList.add("%"+department.getDepDesc()+"%");
		}
		return findByHql(hql, paramList.toArray(), pb);
	}

	@Override
	public List<Department> findByDepName(String depName) {
		String hql="from Department vo where vo.depName=?";		
		String[] param={depName};
		return findByHql(hql,param);
	}

	@Override
	public List<Department> findByPath(String path) {
		String hql="from Department vo where vo.path like ?";
		String[] param={path+"%"};
		return findByHql(hql,param);
	}

	
}
