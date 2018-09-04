package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.log.LogServicesResource;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.CreditRatingDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.CreditRating;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingOption;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGrade;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;


public class CreditRatingDaoImpl extends BaseDaoImpl<CreditRating> implements CreditRatingDao {

	public CreditRatingDaoImpl() {
		super(CreditRating.class);
		// TODO Auto-generated constructor stub
	}


	@Resource
	private CreditBaseDao creditBaseDao;
	public List getIndicatorByTempId(int id) {
		List<Indicator> list = null;
		String hql = "from TemplateIndicator ti where ti.templateId=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	             
	public List getOptionsList(int indicatorId) {
		List<Indicator> list = null;
		String hql = "from TemplateOptions to where to.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,indicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
    
	public List getOptionsListOrder(int indicatorId) {
		List<Indicator> list = null;
		String hql = "from TemplateOptions to where to.indicatorId=? order by to.optionName+1 asc";
		try {
			list = getHibernateTemplate().find(hql,indicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public boolean addCreditRating(CreditRating creditRating) {
		boolean b = false;
		
		try {
			creditBaseDao.saveDatas(creditRating);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public List getCreditRatingList(final int start, final int limit) {
		List list = null;
		final String hql = "from CreditRating cr ";
		list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				Query q = session.createQuery(hql);
				/*q.setString(0, "%" + indicatorType + "%");
				q.setString(1, "%" + indicatorName + "%");
				q.setString(2, "%" + creater + "%");*/
				q.setFirstResult(start);
				q.setMaxResults(limit);
				return q.list();
			}
		});
		return list;
	}


	public int getCreditRatingListNum() {
		List list = null;
		final String hql = "from CreditRating cr ";
		list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				Query q = session.createQuery(hql);
				/*q.setString(0, "%" + indicatorType + "%");
				q.setString(1, "%" + indicatorName + "%");
				q.setString(2, "%" + creater + "%");*/
				return q.list();
			}
		});
		return list.size();
	}


	public CreditRating getCreditRating(int id) {
		IndicatorStore is = null;
		List list = null;
		String hql = "from CreditRating cr where cr.id=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (CreditRating) list.get(0);
	}

	@LogServicesResource(description = "删除资信评估")
	public boolean deleteCreditRating(int id) {
		boolean b = false;
		try {
			 Object o = getHibernateTemplate().get(CreditRating.class, id);
			if(o!=null){
				 creditBaseDao.deleteDatas(o);
			}
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public ScoreGrade getScoreGrade(float ratingScore) {
		ScoreGrade sg = null;
		List list = null;
		Float[] arr = {ratingScore, ratingScore};
		String hql = "from ScoreGrade sg where sg.minScore<=? and sg.maxScore>?";
		try {
			list = getHibernateTemplate().find(hql,arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ScoreGrade) list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<CreditRating> getCreditRatingList(final String customerType,
			final String customerName, final String creditTemplate, final String projectId, final int start, final int limit,String userId,String startScore,String endScore,String grandName,String companyId) {
		List list = null;
		String hql = "from CreditRating cr where cr.customerType like ? and cr.customerName like ? and cr.creditTemplate like ? and (cr.projectId like ? or (''=? and cr.projectId is null))";
		if(null!=userId && !"".equals(userId)){
			hql=hql+" and cr.userId="+Long.parseLong(userId);
		}
		if(null!=startScore && !"".equals(startScore) && null!=endScore && !"".equals(endScore)){
			hql=hql+" and cr.ratingScore between "+Float.parseFloat(startScore)+" and "+Float.parseFloat(endScore);
		}
		if(null!=grandName && !"".equals(grandName)){
        	hql=hql+" and cr.creditRegister='"+grandName+"'";
        }
		if(null!=companyId && !"".equals(companyId)){
			hql=hql+" and cr.companyId in ("+companyId+")";
		}
		return getSession().createQuery(hql).setParameter(0, "%" + customerType + "%").setParameter(1, "%" + customerName + "%").setParameter(2, "%" + creditTemplate + "%").setParameter(3, "%" + projectId + "%").setParameter(4, projectId).setFirstResult(start).setMaxResults(limit).list();
	}

	public int getCreditRatingListNum(final String customerType,
			final String customerName, final String creditTemplate, final String projectId,String userId,String startScore,String endScore,String grandName,String companyId) {

		String hql = "from CreditRating cr where cr.customerType like ? and cr.customerName like ? and cr.creditTemplate like ? and (cr.projectId like ? or (''=? and cr.projectId is null))";
		if(null!=userId && !"".equals(userId)){
			hql=hql+" and cr.userId="+Long.parseLong(userId);
		}
		if(null!=startScore && !"".equals(startScore) && null!=endScore && !"".equals(endScore)){
			hql=hql+" and cr.ratingScore between "+Float.parseFloat(startScore)+" and "+Float.parseFloat(endScore);
		}
	    if(null!=grandName && !"".equals(grandName)){
        	hql=hql+" and cr.creditRegister='"+grandName+"'";
        }
	    if(null!=companyId && !"".equals(companyId)){
	    	hql=hql+" and cr.creditRegister in ("+companyId+")";
        }
		List list= getSession().createQuery(hql).setParameter(0, "%" + customerType + "%").setParameter(1, "%" + customerName + "%").setParameter(2, "%" + creditTemplate + "%").setParameter(3, "%" + projectId + "%").setParameter(4, projectId).list();
		int count=0;
		if(null!=list && list.size()>0){
			count=list.size();
		}
		return count;
	}


	public TemplateIndicator findTemplateIndicator(int templateIndicatorId) {
		TemplateIndicator ti = null;
		List list = null;
		String hql = "from TemplateIndicator ti where ti.id=?";
		try {
			list = getHibernateTemplate().find(hql,templateIndicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (TemplateIndicator) list.get(0);
	}

	@LogServicesResource(description = "添加资信评估")
	public java.io.Serializable addRatingIndicator(RatingIndicator ratingIndicator) {
		boolean b = false;
		java.io.Serializable s = null;
		try {
	
			s =  getSession().save(ratingIndicator);

			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return s;
	}

	public List getTemplateOptionList(int id) {
		List<TemplateOptions> list = null;
		String hql = "from TemplateOptions to where to.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public boolean addTemplateOptions(RatingOption ro) {
		boolean b = false;
		try {

			 getSession().save(ro);

			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}


	public RatingIndicator findRatingIndicator(int ratingIndicatorId) {
		List list = null;
		String hql = "from RatingIndicator ri where ri.id=?";
		try {
			list = getHibernateTemplate().find(hql,ratingIndicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (RatingIndicator) list.get(0);
	}


	public List getRatingOptionList(int id) {
		List<RatingOption> list = null;
		String hql = "from RatingOption ro where ro.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List getRatingOptionListOrder(int id) {
		List<RatingOption> list = null;
		String hql = "from RatingOption ro where ro.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public int getCreditRatingListNumOfJD(String customerType, int customerId) {
        String hql = "from CreditRating cr where cr.customerType=? and cr.customerId=?";
		
		List list=getSession().createQuery(hql).setParameter(0, customerType).setParameter(1, customerId).list();
		int count=0;
		if(null!=list && list.size()>0){
		   count=list.size();
		}
		return count;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CreditRating> getCreditRatingListOfJD(String customerType,
			int customerId, int start, int limit) {

		
		String hql = "from CreditRating cr where cr.customerType=? and cr.customerId=?";
		
		return getSession().createQuery(hql).setParameter(0, customerType).setParameter(1, customerId).setFirstResult(start).setMaxResults(limit).list();

	}


	@Override
	public int getMaxStore(int indicatorId) {
		String hql="select max(o.score) from TemplateOptions o where o.indicatorId=?";
		List list=getSession().createQuery(hql).setParameter(0, indicatorId).list();
		int maxStore=0;
		if(null!=list && list.size()>0){
			maxStore=(Integer)list.get(0);
		}
		return maxStore;
	}
	
	
}
