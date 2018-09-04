package com.thirdPayInterface.ThirdPayLog.dao.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.thirdPayInterface.ThirdPayLog.dao.ThirdPayRecordDao;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ThirdPayRecordDaoImpl extends BaseDaoImpl<ThirdPayRecord> implements ThirdPayRecordDao{

	public ThirdPayRecordDaoImpl() {
		super(ThirdPayRecord.class);
	}

	@Override
	public ThirdPayRecord getByUniqueId(String uniqueId) {
		String sql = "from ThirdPayRecord as tpr where tpr.uniqueId=?";
		List<ThirdPayRecord> list = this.getSession().createQuery(sql).setParameter(0, uniqueId).list();
		return 	list.size()>0?list.get(0):null;
	}

	@Override
	public ThirdPayRecord getByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		String hql = "from ThirdPayRecord record where record.recordNumber = "+"'"+orderNo.trim()+"'";
        List<ThirdPayRecord> list =  this.getHibernateTemplate().find(hql);
		return 	list.size()>0?list.get(0):null;
	}

	@Override
	public List getBpCustmember(String thirdPayConfigId) {
		Object[] ret=new Object[2];
		String sql = "select cust.id,cust.loginname from bp_cust_member as cust where cust.thirdPayFlagId=?";
		List list = this.getSession().createSQLQuery(sql).setParameter(0, thirdPayConfigId).list();
		return list;
	}
	
	@Override
	public List<ThirdPayRecord> getByIdAndType(String thirdPayConfigId,
			String interfaceType) {
		String hql = "from ThirdPayRecord as tr where tr.thirdPayFlagId=? and tr.interfaceType=? ORDER BY tr.requestTime DESC ";
		return this.getSession().createQuery(hql).setParameter(0, thirdPayConfigId).setParameter(1, interfaceType).list();
	}

}