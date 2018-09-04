package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorStoreDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;

public class IndicatorStoreDaoImpl extends BaseDaoImpl<IndicatorStore> implements IndicatorStoreDao {
    public IndicatorStoreDaoImpl() {
		super(IndicatorStore.class);
		// TODO Auto-generated constructor stub
	}

	@Resource
	private CreditBaseDao creditBaseDao;

	public List<IndicatorStore> getIndicatorStoreList(int parentId,String type) {
		List<IndicatorStore> list = null;
		String hql = "from IndicatorStore ss where ss.parentId=? and ss.ptype=?";
		try {
			list = this.getSession().createQuery(hql).setParameter(0, parentId).setParameter(1, type).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public boolean addIndicatorStrore(IndicatorStore indicatorStore) {
		boolean b = false;
		
		try {
			creditBaseDao.saveDatas(indicatorStore);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public IndicatorStore getIndicatorStore(int id) {
		IndicatorStore is = null;
		List list = null;
		String hql = "from IndicatorStore i where i.id=?";
		try {
			list = getHibernateTemplate().find(hql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null!=list && list.size()>0){
			is=(IndicatorStore) list.get(0);
		}
		return is;
	}


	public boolean updateIndicatorStore(IndicatorStore is) {
		boolean b = false;
		
		try {
			creditBaseDao.updateDatas(is);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public boolean deleteIndicatorStore(int id) {
		boolean b = false;
		
		try {
			String hql="from Indicator i where i.indicatorTypeId=?";
			List<Indicator> list=getSession().createQuery(hql).setParameter(0, id).list();
			for(Indicator indicator:list){
				String ohql="from Options o where o.indicatorId=?";
				List<Options> olist=getSession().createQuery(ohql).setParameter(0, indicator.getId()).list();
				creditBaseDao.deleteAll(olist);
			}
			creditBaseDao.deleteAll(list);
			Object o = getHibernateTemplate().get(IndicatorStore.class, id);
			creditBaseDao.deleteDatas(o);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public List<IndicatorStore> getIndicatorStoreList() {
		List<IndicatorStore> list = null;
		String hql = "from IndicatorStore ss where ss.isleaf=1";
		try {
			list = getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public List<IndicatorStore> getIndicatorStoreListDL(int parentId) {
		List<IndicatorStore> list = null;
		String hql = "from IndicatorStore ss where ss.parentId=? and ss.ptype='dl'";
		try {
			list = getHibernateTemplate().find(hql,parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<IndicatorStore> getIndicatorStoreListDX(int parentId) {
		List<IndicatorStore> list = null;
		String hql = "from IndicatorStore ss where ss.parentId=? and ss.ptype='dx'";
		try {
			list = getHibernateTemplate().find(hql,parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
