package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.log.LogServicesResource;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.RatingTemplateDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingTemplate;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;

public class RatingTemplateDaoImpl extends BaseDaoImpl<RatingTemplate> implements RatingTemplateDao {
	public RatingTemplateDaoImpl() {
		super(RatingTemplate.class);
	}

	@Resource
	   private CreditBaseDao creditBaseDao;
	public List<RatingTemplate> getRatingTemplateList() {
		List list = null;
		String hql = "from RatingTemplate rt ";
		try {
			list = getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<RatingTemplate> getRatingTemplateList(String p,String c) {
		List list = null;
		String hql = "from RatingTemplate rt  where 1=1 ";
		if(p!=null&&!"".equals(p)){
			hql+=" and rt.ptype= '"+p+"' ";
		}if(c!=null&&!"".equals(c)){
			hql+=" and rt.customerType= '"+c+"' ";
		}
		try {
			list = this.getSession().createQuery(hql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getRatingTemplateNum() {
		List list = null;
		String hql = "from RatingTemplate rt ";
		try {
			list = getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size();
	}
	@LogServicesResource(description = "增加或修改评估模板")
	public void addRatingTemplate(RatingTemplate ratingTemplate) {
		boolean b = false;
		java.io.Serializable s = null;
		try {
			s = creditBaseDao.saveDatas(ratingTemplate);
			
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		//return (Integer) s;
	}

	public List<IndicatorStore> getTemplateIndicatorStoreList(int parseInt, int templateId) {
		List<IndicatorStore> list = null;
		String hql = "from IndicatorStore ss where ss.parentId=?";
		try {
			list = getHibernateTemplate().find(hql,parseInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<TemplateIndicator> getTemplateIndicatorList(int parseInt, int templateId) {
		List<TemplateIndicator> list = null;
		Object[] obj = {parseInt, templateId};
		String hql = "from TemplateIndicator ti where ti.indicatorTypeId=? and ti.templateId=?";
		try {
			list = getHibernateTemplate().find(hql,obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Indicator getIndicator(int parseInt) {
		List list = null;
		Indicator indicator = null;
		String hql = "from Indicator i where i.id=?";
		
		try {
			list = getHibernateTemplate().find(hql,parseInt);
			indicator = (Indicator) list.get(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return indicator;
	}
	@LogServicesResource(description = "增加评估模板指标")
	public java.io.Serializable addTemplateIndicator(TemplateIndicator ti) {
		boolean b = false;
		java.io.Serializable s = null;
		try {
			s = getHibernateTemplate().save(ti);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return s;
	}

	public List getOptionList(int parseInt) {
		List<Options> list = null;
		String hql = "from Options o where o.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,parseInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean addTemplateOptions(TemplateOptions o) {
		boolean b = false;
		try {
			creditBaseDao.saveDatas(o);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}

	public RatingTemplate getRatingTemplate(int id) {
		List list = null;
		Indicator indicator = null;
		String hql = "from RatingTemplate rt where rt.id=?";
		
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (RatingTemplate) list.get(0);
	}

	public boolean updateRatingTemplate(RatingTemplate rt) {
		boolean b = false;
		
		try {
			creditBaseDao.updateDatas(rt);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	@LogServicesResource(description = "删除评估模板")
	public boolean deleteRatingTemplate(int id) {
		boolean b = false;
		
		try {
			String hql="from TemplateIndicator ti where ti.templateId=?";
			List<TemplateIndicator> ilist=getHibernateTemplate().find(hql, id);
			for(TemplateIndicator ti:ilist){
				String ohql="from TemplateOptions to where to.indicatorId=?";
				List<TemplateOptions> olist=getHibernateTemplate().find(ohql, ti.getId());
				creditBaseDao.deleteAll(olist);
			}
			creditBaseDao.deleteAll(ilist);
			Object o = getHibernateTemplate().get(RatingTemplate.class, id);
			creditBaseDao.deleteDatas(o);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean subTemplateIndicatorSuccess(int id) {
		boolean b = false;
		
		try {
			RatingTemplate o = (RatingTemplate) getHibernateTemplate().get(RatingTemplate.class, id);
			o.setSubTemplateIndicator(1);
			creditBaseDao.updateDatas(o);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public int countTemplateScore(int id) {
		List list = null;
		String hql = "select sum(t.score) from TemplateOptions t where t.indicatorId in (select ti.id from TemplateIndicator ti where ti.templateId=?) and t.sortNo=1";
		
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ((Long) list.get(0)).intValue();
	}
	@LogServicesResource(description = "删除评估模板某项指标")
	public boolean deleteTemplateIndicator(int indicatorId) {
		boolean b = false;
		String hql = "from TemplateOptions to where to.indicatorId=?";
		List list = null;
		try {
			Object o = getHibernateTemplate().get(TemplateIndicator.class, indicatorId);
			creditBaseDao.deleteDatas(o);
			list = getHibernateTemplate().find(hql, indicatorId);
			creditBaseDao.deleteAll(list);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<TemplateIndicator> templateContentList(int id) {
		List<TemplateIndicator> list = null;
		String hql = "from TemplateIndicator ti where ti.templateId=? order by ti.indicatorTypeId desc";
		try {
			list = getHibernateTemplate().find(hql, id);
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

	public List<TemplateIndicator> getTemplateIndicator(int id) {
		List list = null;
		String hql = "from TemplateIndicator ti where ti.templateId=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getNewTemplateId() {
		String hql="select max(id) from RatingTemplate";
		List list=getHibernateTemplate().find(hql);
		int templateId=0;
		if(null!=list && list.size()>0){
			templateId=Integer.parseInt(list.get(0).toString());
		}
		return templateId;
	}

	@Override
	public boolean updateTemplateIndicator(TemplateIndicator ti) {
		boolean b=false;
		try {
			creditBaseDao.updateDatas(ti);
			b=true;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public int getMaxScore(int templateId) {
		List<TemplateIndicator> tlist=getTemplateIndicator(templateId);
		int maxScore=0;
		for(TemplateIndicator ti:tlist ){
			String hql="select max(score) from TemplateOptions to where to.indicatorId=?";
			List list=getSession().createQuery(hql).setParameter(0, ti.getId()).list();
			int score=0;
			if(null!=list && list.size()>0){
				score=(Integer)list.get(0);
				maxScore=maxScore+score;
			}
		}
		return maxScore;
	}

	@Override
	public int getMinScore(int templateId) {
		List<TemplateIndicator> tlist=getTemplateIndicator(templateId);
		int minScore=0;
		for(TemplateIndicator ti:tlist ){
			String hql="select min(score) from TemplateOptions to where to.indicatorId=?";
			List list=getSession().createQuery(hql).setParameter(0, ti.getId()).list();
			int score=0;
			if(null!=list && list.size()>0){
				score=(Integer)list.get(0);
				minScore=minScore+score;
			}
		}
		return minScore;
	}

	@Override
	public int getMaxScoreOfOption(int indicatorId) {
		String hql="select max(score) from TemplateOptions to where to.indicatorId=?";
		List list=getSession().createQuery(hql).setParameter(0,indicatorId).list();
		int score=0;
		if(null!=list && list.size()>0){
			score=(Integer)list.get(0);
		}
		return score;
	}

	@Override
	public int getMinScoreOfOption(int indicatorId) {
		String hql="select min(score) from TemplateOptions to where to.indicatorId=?";
		List list=getSession().createQuery(hql).setParameter(0, indicatorId).list();
		int score=0;
		if(null!=list && list.size()>0){
			score=(Integer)list.get(0);
		}
		return score;
	}

	@Override
	public int getIndicatorTypeNum(int templateId) {
		String hql="select ti.indicatorTypeId from TemplateIndicator ti where ti.templateId=? group by ti.indicatorTypeId";
		List list=getSession().createQuery(hql).setParameter(0, templateId).list();
		int count=0;
		if(null!=list && list.size()>0){
			count=list.size();
		}
		return count;
	}
	
	
}
