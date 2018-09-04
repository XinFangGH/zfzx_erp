package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

public class MakeScoreDao extends HibernateDaoSupport {
	// 根据传递class ，返回class
	public Object get(Class c, String propertyName, Object value) {
		String hql = "from " + c.getName() + " as model where model."
				+ propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}
	
	
	public List getForList(Class c, String propertyName, Object value) {
		String hql = "from " + c.getName() + " as model where model."
				+ propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value)
				.list();
	}

}
