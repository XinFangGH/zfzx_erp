package com.zhiwei.credit.dao.creditFlow.smallLoan.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/*
 * @author刘程源
 * */
public class MySelfDao extends HibernateDaoSupport {
	public Object get(String name, String propertyName, Object value) {
		String hql = "from " + name + " as model where model." + propertyName
				+ " = ?";
		return getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}

	public List getList(String pcalss) {
		String hql = "from " + pcalss + " as model ";
		return getSession().createQuery(hql).list();
	}

	public Object[] getList(String pcalss, Integer start, Integer limit) {
		String hql = "from " + pcalss + " as model ";
		List l;
		if (start != null && limit != null) {
			l = getSession().createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
		} else {
			l = getSession().createQuery(hql).list();
		}
		hql = "select  count(*) from " + pcalss + " as model ";
		long count = ((Long) getHibernateTemplate().iterate(hql).next())
				.intValue();
		int i = (int) count;
		return new Object[] { l, i };
	}

	public Object saveOrUpdate(Object o) {
		return null;
	}

	public List getList(String pcalss, Object[] pars, Object[] vals) {
		String s;
		StringBuffer hql = new StringBuffer("from ").append(pcalss).append(
				" as model ");
		if ((pars != null) && (vals != null) && (pars.length == vals.length)) {
			for (int i = 0; i < pars.length; i++) {
				s = (i == 0) ? "where model." + pars[i] + "=?" : "and  model."
						+ pars[i] + "=?";
				hql.append(s);
			}
		}
		Query queryObject = getSession().createQuery(hql.toString());
		for (int i = 0; i < vals.length; i++) {
			queryObject.setParameter(i, vals[i]);
		}
		List l = queryObject.list();
		return l;
	}

	public List getList(String pcalss, Map<String, Object> m) {
		String s;
		StringBuffer hql = new StringBuffer("from ").append(pcalss).append(
				" as model ");
		String par;
		Object val;
		List vals = new ArrayList();
		if (m != null) {
			for (Map.Entry<String, Object> entry : m.entrySet()) {
				par = entry.getKey();
				val = entry.getValue();
				s = (vals.size() == 0) ? "where model." + par + "=?"
						: "and  model." + par + "=?";
				hql.append(s);
				vals.add(val);
			}
		}
		Query queryObject = getSession().createQuery(hql.toString());
		for (int i = 0; i < vals.size(); i++) {
			queryObject.setParameter(i, vals.get(i));
		}
		List l = queryObject.list();
		return l;
	}

	public Object[] getList(String pcalss, String propertyName, Object value,
			Integer start, Integer limit) {

		String hql = "from " + pcalss + " as model where model."+propertyName+"=?";
		
		List l;
		
		if (start != null && limit != null) {
			l = getSession().createQuery(hql).setParameter(0,value).setFirstResult(start)
					.setMaxResults(limit).list();
		} else {
			l = getSession().createQuery(hql).list();
		}
		hql = "select  count(*) from " + pcalss + "as model where model."+propertyName+"=?";
		long count = ((Long) getHibernateTemplate().iterate(hql).next())
				.intValue();
		int i = (int) count;
		return new Object[] { l, i };
	}
	
	public void delete(Object object){
		if(null==object) return;
		getSession().delete(object);
	}
}
