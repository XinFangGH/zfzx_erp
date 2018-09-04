package com.zhiwei.credit.service.creditFlow.creditmanagement.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.service.creditFlow.creditmanagement.ElementService;

/**
 * 
 * @author credit004
 *
 */
public class ElementServiceImpl implements ElementService {

	private final Log log = LogFactory.getLog(getClass());
	
	private CreditBaseDao baseDao;
	
	public CreditBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(CreditBaseDao baseDao) {
		this.baseDao = baseDao;
	}


//	public List<ProblibElement> getAllElement(int start, int limit) {
//		
//		String hql = "from ProblibElement p order by p.updateTime asc";
//		
//		try {
//			List<ProblibElement> list = baseDao.queryHql(hql,start,limit);
//			
//			return list;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("error 043");
//		}
//		
//		return null;
//	}


	public int getAllElementAmount() {
		
		String hql = "select count(*) from ProblibElement p order by p.updateTime asc";
		
		try {
			List list = baseDao.queryHql(hql);
			
			if(list != null && list.size() != 0){
				return ((Long)list.get(0)).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error 062");
		}
		
		return 0;
	}

}
