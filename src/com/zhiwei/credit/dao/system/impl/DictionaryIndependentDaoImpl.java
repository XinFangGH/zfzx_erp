package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.DictionaryIndependentDao;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.DictionaryIndependent;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class DictionaryIndependentDaoImpl extends BaseDaoImpl<DictionaryIndependent> implements DictionaryIndependentDao{

	public DictionaryIndependentDaoImpl() {
		super(DictionaryIndependent.class);
	}

	@Override
	public List<DictionaryIndependent> getListByProTypeId(long proTypeId) {
		String hql="from DictionaryIndependent where proTypeId=? and status=0 order by sn asc";
		return getSession().createQuery(hql).setParameter(0, proTypeId).list();
	}
	public List<String> getAllItems() {
		final String hql = "select itemName from DictionaryIndependent group by itemName";
		return (List<String>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						return query.list();
					}
				});
	}

	@Override
	public List<String> getAllByItemName(final String itemName) {
		final String hql = "select itemValue from DictionaryIndependent where itemName=?";
		return (List<String>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, itemName);
						return query.list();
					}
				});
	}

	@Override
	public List<DictionaryIndependent> getByItemName(final String itemName) {
		final String hql = " from DictionaryIndependent where itemName=?";
		return (List<DictionaryIndependent>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString(0, itemName);
						return query.list();
					}
				});
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryIndependent> getByDicKey(String dicKey) {
		String hql="from DictionaryIndependent where dicKey=?";
		return getSession().createQuery(hql).setParameter(0, dicKey).list();
	}

	@Override
	public String getDicKeyByDicId(long dicId) {
		String hql="select dicKey from DictionaryIndependent where dicId=?";
		return (String)getSession().createQuery(hql).setParameter(0, dicId).uniqueResult();
	}
}