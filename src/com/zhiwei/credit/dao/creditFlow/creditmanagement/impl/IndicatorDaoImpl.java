package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;

public class IndicatorDaoImpl extends BaseDaoImpl<Indicator> implements IndicatorDao{

	public IndicatorDaoImpl() {
		super(Indicator.class);
	}

	@Resource
	private CreditBaseDao creditBaseDao;


	public List getIndicatorList(final int indicatorTypeId,
			final String indicatorType, final String indicatorName,
			final String creater, final int start, final int limit,final String type) {
		List list = null;
		final String hql = "from Indicator i where i.indicatorTypeId=? and i.ptype=?";
		/*
		 * where (i.indicatorType like ?) and (i.indicatorName like ?) and
		 * (i.creater like ?)
		 */
		/*list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				Query q = session.createQuery(hql);
				q.setInteger(0, indicatorTypeId);
				q.setString(1, "%" + type + "%");
				
				 * q.setString(0, "%" + indicatorType + "%"); q.setString(1, "%"
				 * + indicatorName + "%"); q.setString(2, "%" + creater + "%");
				 
				q.setFirstResult(start);
				q.setMaxResults(limit);
				return q.list();
			}
		});*/
		list=getSession().createQuery(hql).setParameter(0, indicatorTypeId).setParameter(1, type).setFirstResult(start)
		.setMaxResults(limit).list();
		return list;
	}

	public int getIndicatorListNum(final int indicatorTypeId,
			final String indicatorType, final String indicatorName,
			final String creater, final String type) {
		List list = null;
		final String hql = "from Indicator i where i.indicatorTypeId=? and i.ptype=?";
		list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				Query q = session.createQuery(hql);
				q.setInteger(0, indicatorTypeId);
				q.setString(1, "%" + type + "%");
				/*
				 * q.setString(0, "%" + indicatorType + "%"); q.setString(1, "%"
				 * + indicatorName + "%"); q.setString(2, "%" + creater + "%");
				 */

				return q.list();
			}
		});

		return list.size();
	}

	public boolean addIndicator(Indicator indicator) {
		boolean b = false;

		try {
			creditBaseDao.saveDatas(indicator);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<Indicator> getIndicatorList(int parseInt ,String type) {
		List<Indicator> list = null;
		String hql = "from Indicator i where i.indicatorTypeId=? and i.ptype=?";
		try {
			list = getSession().createQuery(hql).setParameter(0, parseInt).setParameter(1, type).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<IndicatorStore> getIndicatorStoreList(int parseInt) {
		List<IndicatorStore> list = null;
		String hql = "from IndicatorStore ss where ss.parentId=?";
		try {
			list = getHibernateTemplate().find(hql, parseInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<IndicatorStore> getIndicatorStoreList(int parseInt, String type) {
		List<IndicatorStore> list = null;
		if (!"".equals(type)) {
			String hql = "from IndicatorStore ss where ss.parentId=? and ss.ptype=?";
			try {
				list = getHibernateTemplate().find(hql,
						new Object[] { parseInt, type });
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String hql = "from IndicatorStore ss where ss.parentId=?";
			try {
				list = getHibernateTemplate().find(hql, parseInt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Indicator getIndicator(int id) {
		Indicator indicator = null;
		List<Indicator> list = null;
		String hql = "from Indicator i where i.id=?";
		try {
			list = getHibernateTemplate().find(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null!=list && list.size()>0){
			indicator= list.get(0);
		}
		return indicator;
	}

	public boolean updateIndicator(Indicator i) {
		boolean b = false;

		try {
			creditBaseDao.updateDatas(i);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean deleteIndicator(int id) {
		boolean b = false;
		String hql = "from Options o where o.indicatorId=?";
		List list = null;

		try {
			Object o = getHibernateTemplate().get(Indicator.class, id);
			creditBaseDao.deleteDatas(o);
			list = getHibernateTemplate().find(hql, id);
			getHibernateTemplate().deleteAll(list);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List getOptionsList(int indicatorId) {
		List list = null;
		String hql = "from Options o where o.indicatorId=? ";
		try {
			list = getHibernateTemplate().find(hql, indicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getOptionsListOrder(int indicatorId, String s) {
		List list = null;
		String hql = "from Options o where o.indicatorId=? order by o.sortNo "
				+ s;
		try {
			list = getHibernateTemplate().find(hql, indicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getTemplateIndicator(int templateId) {
		List list = null;
		String hql = "from TemplateIndicator ti where ti.templateId=?";
		try {
			list = getHibernateTemplate().find(hql, templateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Indicator> getAllIndicator(int start, int limit,String type) {
		String hql = "from Indicator as i where i.ptype=?";
		return getSession().createQuery(hql).setParameter(0, type).setFirstResult(start)
				.setMaxResults(limit).list();
	}

	public List<Indicator> getIndicatorByType(int start, int limit, String type) {
		String hql = "from Indicator i where i.indicatorTypeId in (select s.id from IndicatorStore s where s.ptype=?)";
		return getSession().createQuery(hql).setParameter(0, type)
				.setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public int getAllIndicatorNum(String type) {
		String hql = "from Indicator as i where i.ptype=?";
		List list = getSession().createQuery(hql).setParameter(0, type).list();
		int count = 0;
		if (null != list && list.size() > 0) {
			count = list.size();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Indicator> getIndicatorByType(int parentId, int start, int limit,String type) {
		String hql = "from Indicator i where i.indicatorTypeId in (select s.id from IndicatorStore s where s.parentId=? and s.ptype=? ) and i.ptype=? ";
		return getSession().createQuery(hql).setParameter(0, parentId).setParameter(1, type).setParameter(2, type)
				.setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public int getIndicatorNum(int parentId,String type) {
		String hql = "from Indicator i where i.indicatorTypeId in (select s.id from IndicatorStore s where s.parentId=? and s.ptype=?) and i.ptype=?";
		List list = getSession().createQuery(hql).setParameter(0, parentId).setParameter(1, type).setParameter(2, type)
				.list();
		int count = 0;
		if (null != list && list.size() > 0) {
			count = list.size();
		}
		return count;
	}

	@Override
	public int getMaxStore(int indicatorTypeId) {
		String hql = "select i.id from Indicator i where i.indicatorTypeId=?";
		List list = getSession().createQuery(hql).setParameter(0,
				indicatorTypeId).list();
		int maxScore = 0;
		for (int i = 0; i < list.size(); i++) {
			int indicatorId = (Integer) list.get(i);
			String ohql = "select max(o.score) from Options o where o.indicatorId=?";
			List olist = getSession().createQuery(ohql).setParameter(0,
					indicatorId).list();
			int score = 0;
			if (null != olist && olist.get(0) != null && olist.size() > 0) {
				score = (Integer) olist.get(0);
			}
			maxScore = maxScore + score;
		}

		return maxScore;
	}

	public int getMaxStore(int indicatorTypeId, String type) {
		String hql = "select i.id from Indicator i where i.indicatorTypeId=? and  i.ptype=? ";
		List list = getSession().createQuery(hql).setParameter(0,
				indicatorTypeId).setParameter(1, type).list();
		int maxScore = 0;
		for (int i = 0; i < list.size(); i++) {
			int indicatorId = (Integer) list.get(i);
			String ohql = "select max(o.score) from Options o where o.indicatorId=? and o.ptype=?";
			List olist = getSession().createQuery(ohql).setParameter(0,
					indicatorId).setParameter(1, type).list();
			int score = 0;
			if (null != olist && olist.size() > 0) {
				score = (Integer) olist.get(0);
			}
			maxScore = maxScore + score;
		}

		return maxScore;
	}

	@Override
	public int getMinStore(int indicatorTypeId) {
		String hql = "select i.id from Indicator i where i.indicatorTypeId=?";
		List list = getSession().createQuery(hql).setParameter(0,
				indicatorTypeId).list();
		int minScore = 0;
		for (int i = 0; i < list.size(); i++) {
			int indicatorId = (Integer) list.get(i);
			String ohql = "select min(o.score) from Options o where o.indicatorId=?";
			List olist = getSession().createQuery(ohql).setParameter(0,
					indicatorId).list();
			int score = 0;
			if (null != olist && olist.get(0) != null && olist.size() > 0) {
				score = (Integer) olist.get(0);
			}
			minScore = minScore + score;
		}
		return minScore;
	}

	@Override
	public int getMinStore(int indicatorTypeId, String type) {
		String hql = "select i.id from Indicator i where i.indicatorTypeId=?  and i.ptype=? ";
		List list = getSession().createQuery(hql).setParameter(0,
				indicatorTypeId).setParameter(1, type).list();
		int minScore = 0;
		for (int i = 0; i < list.size(); i++) {
			int indicatorId = (Integer) list.get(i);
			String ohql = "select min(o.score) from Options o where o.indicatorId=?  and o.ptype=?";
			List olist = getSession().createQuery(ohql).setParameter(0,
					indicatorId).setParameter(1, type).list();
			int score = 0;
			if (null != olist && olist.size() > 0) {
				score = (Integer) olist.get(0);
			}
			minScore = minScore + score;
		}
		return minScore;
	}

	@Override
	public int getMaxScore(int indicatorId) {
		String hql = "select max(o.score) from Options o where o.indicatorId=?";
		int maxScore = 0;
		List list = getSession().createQuery(hql).setParameter(0, indicatorId)
				.list();
		if (null != list && list.size() > 0) {
			maxScore = (Integer) list.get(0);
		}
		return maxScore;
	}

	@Override
	public int getMinScore(int indicatorId) {
		String hql = "select min(o.score) from Options o where o.indicatorId=?";
		int minScore = 0;
		List list = getSession().createQuery(hql).setParameter(0, indicatorId)
				.list();
		if (null != list && list.size() > 0) {
			minScore = (Integer) list.get(0);
		}
		return minScore;
	}

	// type：DL/dx，indicatorTypeId：parid
	public Object[] getIndicatorForLXP(String type, int indicatorTypeId,
			int start, int limit) {
		List l;
		if (indicatorTypeId != 0) {
			String hql = "from Indicator i where i.indicatorTypeId in (select s.id from IndicatorStore s where s.ptype=? and s.parentId=?)";
			l = getSession().createQuery(hql).setParameter(0, type)
					.setParameter(1, indicatorTypeId).setFirstResult(start)
					.setMaxResults(limit).list();
		} else {
			String hql = "from Indicator i where i.indicatorTypeId in (select s.id from IndicatorStore s where s.ptype=?)";
			l = getSession().createQuery(hql).setParameter(0, type)
					.setFirstResult(start).setMaxResults(limit).list();
		}
		int count = 0;
		if (null != l && l.size() > 0) {
			count = l.size();
		}
		Object[] o = new Object[2];
		o[0] = l;
		o[1] = count;
		return o;
	}

	public Object[] getIndicatorForLX(String type, int indicatorTypeId,
			int start, int limit) {
		String hql;
		List l;
		List list;
		if (indicatorTypeId != 0) {
			hql = "from Indicator i where i.indicatorTypeId=?";
			l = getSession().createQuery(hql).setParameter(0, indicatorTypeId)
					.setFirstResult(start).setMaxResults(limit).list();
			list = getSession().createQuery(hql).setParameter(0,
					indicatorTypeId).list();
		} else {
			hql = "from Indicator i where i.indicatorTypeId in (select s.id from IndicatorStore s where s.ptype=?)";
			l = getSession().createQuery(hql).setParameter(0, type)
					.setFirstResult(start).setMaxResults(limit).list();
			list = getSession().createQuery(hql).setParameter(0, type).list();
		}

		int count = 0;
		if (null != list && list.size() > 0) {
			count = list.size();
		}
		Object[] o = new Object[2];
		o[0] = l;
		o[1] = count;
		return o;
	}

	public Map getByIndicatorId(int indicatorId) {
		String hql = "from Indicator i where i.indicatorTypeId=?";
		List<Indicator> l = getSession().createQuery(hql).setParameter(0,
				indicatorId).list();
		Map m = new HashMap();
		for (Indicator i : l) {
			m.put(i.getIndicatorName(), i.getIndicatorName());
		}
		return m;

	}

	@Override
	public List<Indicator> getIndicatorListByType(int parseInt, String type,
			String operationType) {
		List<Indicator> list = null;
		String hql = "from Indicator i where i.indicatorTypeId=? and i.ptype=? and i.operationType=?";
		try {
			list = getSession().createQuery(hql).setParameter(0, parseInt).setParameter(1, type).setParameter(2, operationType).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getSystemCreditelementCode(int customerId,String customerType,String key){
		try {DecimalFormat myFormatter = new DecimalFormat("####.#");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		//CreditmanagementElementCode elementCode = new CreditmanagementElementCode();
		String value="";
		if(customerType.equals("Enterprise")){
			String hql = "from EnterpriseFinance e where enterpriseId=? and textFeildId=?";
			List<EnterpriseFinance> ss =  getSession().createQuery(hql).setParameter(0, customerId)
			.setParameter(1, key).list();;
			if(ss!=null&&ss.size()>0){
				EnterpriseFinance enterpriseFinance=ss.get(0);
		    	if(enterpriseFinance!=null){
		    		value=enterpriseFinance.getTextFeildText();
		    	}
			}
			
			
		}else if(customerType.equals("Person")){
			
		}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
		
		
	}

}
