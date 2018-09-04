package com.zhiwei.credit.dao.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.hrm.EmpProfileDao;
import com.zhiwei.credit.model.hrm.EmpProfile;

public class EmpProfileDaoImpl extends BaseDaoImpl<EmpProfile> implements EmpProfileDao{

	public EmpProfileDaoImpl() {
		super(EmpProfile.class);
	}

	@Override
	public boolean checkProfileNo(final String profileNo) {
		final String hql = "select count(*) from EmpProfile ep where ep.profileNo = ?";
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, profileNo);
				return query.uniqueResult();
			}});
		if(count!=0){
			return false;
		}else{
			return true;
		}
	}

}