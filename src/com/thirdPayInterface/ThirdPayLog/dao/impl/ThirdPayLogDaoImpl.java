package com.thirdPayInterface.ThirdPayLog.dao.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.thirdPayInterface.ThirdPayLog.dao.ThirdPayLogDao;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayLog;
import com.zhiwei.core.dao.impl.BaseDaoImpl;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ThirdPayLogDaoImpl extends BaseDaoImpl<ThirdPayLog> implements ThirdPayLogDao{

	public ThirdPayLogDaoImpl() {
		super(ThirdPayLog.class);
	}

	@Override
	public ThirdPayLog getByUniqueId(String uniqueId) {
		String sql = "from ThirdPayLog as tpr where tpr.uniqueId=?";
		List<ThirdPayLog> list = this.getSession().createQuery(sql).setParameter(0, uniqueId).list();
		return 	list.size()>0?list.get(0):null;
	}

	@Override
	public ThirdPayLog getByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		String hql = "from ThirdPayLog record where record.recordNumber = "+"'"+orderNo.trim()+"'";
        List<ThirdPayLog> list =  this.getHibernateTemplate().find(hql);
		return 	list.size()>0?list.get(0):null;
	}

}