package com.zhiwei.credit.dao.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.hrm.StandSalaryDao;
import com.zhiwei.credit.model.hrm.StandSalary;

public class StandSalaryDaoImpl extends BaseDaoImpl<StandSalary> implements StandSalaryDao{

	public StandSalaryDaoImpl() {
		super(StandSalary.class);
	}

	@Override
	public boolean checkStandNo(final String standardNo) {
		final String hql = "select count(*) from StandSalary ss where ss.standardNo = ?";
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, standardNo);
				return query.uniqueResult();
			}});
		if(count!=0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<StandSalary> findByPassCheck() {
	    String hql="from StandSalary vo where vo.status=?";
	    Object[] objs={Constants.FLAG_PASS};
		return findByHql(hql, objs);
	}

}