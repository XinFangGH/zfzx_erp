package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.ProjectDao;
import com.zhiwei.credit.model.customer.Project;

public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao{

	public ProjectDaoImpl() {
		super(Project.class);
	}

	@Override
	public boolean checkProjectNo(final String projectNo) {
		final String hql = "select count(*) from Project p where p.projectNo = ?";
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, projectNo);
				return query.uniqueResult();
			}});
		if(count!=0){
			return false;
		}else{
			return true;
		}
	}

}