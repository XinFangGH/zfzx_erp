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
import com.zhiwei.credit.dao.customer.CusLinkmanDao;
import com.zhiwei.credit.model.customer.CusLinkman;

public class CusLinkmanDaoImpl extends BaseDaoImpl<CusLinkman> implements CusLinkmanDao{

	public CusLinkmanDaoImpl() {
		super(CusLinkman.class);
	}

	@Override
	public boolean checkMainCusLinkman(final Long customerId,final Long linkmanId) {
		final StringBuffer hql = new StringBuffer("select count(*) from CusLinkman  cl where cl.isPrimary = 1 and cl.customer.customerId =? ");
		if(linkmanId!=null){
			hql.append("and cl.linkmanId != ? ");
		}
		Long count = (Long)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql.toString());
				query.setLong(0, customerId);
				if(linkmanId != null){
					query.setLong(1,linkmanId);
				}
				return query.uniqueResult();
			}
		});
		if(count!=0){
			return false;
		}else{
			return true;
		}
		
	}

}