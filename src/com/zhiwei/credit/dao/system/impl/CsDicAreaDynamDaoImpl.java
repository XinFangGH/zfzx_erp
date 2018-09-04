package com.zhiwei.credit.dao.system.impl;

/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
 */

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;

import com.zhiwei.credit.dao.system.CsDicAreaDynamDao;
import com.zhiwei.credit.model.system.CsDicAreaDynam;


@SuppressWarnings("unchecked")
public class CsDicAreaDynamDaoImpl extends BaseDaoImpl<CsDicAreaDynam>
		implements CsDicAreaDynamDao {

	public CsDicAreaDynamDaoImpl() {
		super(CsDicAreaDynam.class);
	}

	/*
	 * 根据ID查找其一级子节点
	 */
	public List<CsDicAreaDynam> getAllItemById(final Long ID) {
		final String hql = " from CsDicAreaDynam where id=?";
		return (List<CsDicAreaDynam>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setLong(0, ID);
						return query.list();
					}
				});

	}

	/*
	 * 根据父ID为xx的所有节点
	 */
	public List<CsDicAreaDynam> getAllItemByParentId(final Long parentID) {
		final String hql = " from CsDicAreaDynam where parentId=?";
		return (List<CsDicAreaDynam>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setLong(0, parentID);
						return query.list();
					}
				});

	}

}