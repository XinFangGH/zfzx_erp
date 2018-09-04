package com.zhiwei.credit.dao.system.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.model.system.Dictionary;

public class DictionaryDaoImpl extends BaseDaoImpl<Dictionary> implements
		DictionaryDao {

	public DictionaryDaoImpl() {
		super(Dictionary.class);
	}

	@Override
	public List<String> getAllItems() {
		final String hql = "select itemName from Dictionary group by itemName";
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
		final String hql = "select itemValue from Dictionary where itemName=?";
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
	public List<Dictionary> getByItemName(final String itemName) {
		final String hql = " from Dictionary where itemName=? and status = 0";
		return (List<Dictionary>) getHibernateTemplate().execute(
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
	public List<Dictionary> getByProTypeId(long proTypeId) {
		String hql="from Dictionary where proTypeId=? and status=0";
		return getSession().createQuery(hql).setParameter(0, proTypeId).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getByDicKey(String dicKey) {
		String hql="from Dictionary where dicKey=?";
		return getSession().createQuery(hql).setParameter(0, dicKey).list();
	}

	@Override
	public String getDicKeyByDicId(long dicId) {
		String hql="select dicKey from Dictionary where dicId=?";
		return (String)getSession().createQuery(hql).setParameter(0, dicId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getByParentId(long parentId,int start,int limit) {
		String hql="from Dictionary as d where d.globalType.parentId=? and d.globalType.status=0 and d.status=0";
		return getSession().createQuery(hql).setParameter(0, parentId).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public long getDicNumByParentId(long parentId) {
		String hql="select count(*) from Dictionary as d where d.globalType.parentId=? and d.globalType.status=0 and d.status=0";
		List list=getSession().createQuery(hql).setParameter(0, parentId).list();
		long count=0;
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}

	@Override
	public long getDicNumByProTypeId(long proTypeId) {
		String hql="select count(*) from Dictionary where proTypeId=? and status=0";
		long count=0;
		List list=getSession().createQuery(hql).setParameter(0, proTypeId).list();
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getListByProTypeId(long proTypeId,int start,int limit) {
		String hql="from Dictionary where proTypeId=? and status=0";
		return getSession().createQuery(hql).setParameter(0, proTypeId).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public String queryDic(String dicId) {
		String sql = "select dic.itemValue from dictionary as dic where dic.dicId="+Long.valueOf(dicId)+" ";
		return (String)this.getSession().createSQLQuery(sql).uniqueResult();
	}

	@Override
	public Dictionary queryLoanUser(Long dicId) {
		String sql = "SELECT * FROM `dictionary` as dic where dic.proTypeId=1134 and dic.`status`=0 and dic.dicId=? ";
		return (Dictionary)this.getSession().createSQLQuery(sql).addEntity(Dictionary.class).setParameter(0, dicId).uniqueResult();
	}

}