package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.OptionDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;


public class OptionDaoImpl extends BaseDaoImpl<Options> implements OptionDao {
   public OptionDaoImpl() {
	   super(Options.class);
		// TODO Auto-generated constructor stub
	}



@Resource
   private CreditBaseDao creditBaseDao;
	public List getOptionListByIndicatorId(int indicatorId) {
		List list = null;
		String hql = "from Options o where o.indicatorId=?";
		try {
			list = getHibernateTemplate().find(hql,indicatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public boolean addOption(Options options) {
		boolean b = false;
		
		try {
			creditBaseDao.saveDatas(options);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public boolean deleteOption(int id) {
		boolean b = false;
		
		try {
			Object o = getHibernateTemplate().get(Options.class, id);
			creditBaseDao.deleteDatas(o);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}



	public boolean updateOption(Options options) {
		boolean b = false;
		
		try {
			creditBaseDao.updateDatas(options);
			b = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	
}	
