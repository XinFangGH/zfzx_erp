package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.TemplateOptionDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;

public class TemplateOptionDaoImpl extends BaseDaoImpl<TemplateOptions> implements TemplateOptionDao {

	public TemplateOptionDaoImpl() {
		super(TemplateOptions.class);
		// TODO Auto-generated constructor stub
	}


	@Resource
	   private CreditBaseDao creditBaseDao;
	public List getOptionListByIndicatorId(int templateIndicatorId) {
		List list = null;
		String hql = "from TemplateOptions o where o.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,templateIndicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public boolean addTemplateOption(TemplateOptions templateOption) {
		boolean b = false;
		
		try {
			creditBaseDao.saveDatas(templateOption);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public boolean deleteTemplateOption(int id) {
		boolean b = false;
		
		try {
			Object o = getHibernateTemplate().get(TemplateOptions.class, id);
			creditBaseDao.deleteDatas(o);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public boolean updateTemplateOption(TemplateOptions templateOption) {
		boolean b = false;
		
		try {
			creditBaseDao.updateDatas(templateOption);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	
}
