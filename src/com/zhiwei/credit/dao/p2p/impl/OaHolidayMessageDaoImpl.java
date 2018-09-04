package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.OaHolidayMessageDao;
import com.zhiwei.credit.model.p2p.OaHolidayMessage;
import org.hibernate.transform.Transformers;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class OaHolidayMessageDaoImpl extends BaseDaoImpl<OaHolidayMessage> implements OaHolidayMessageDao{

	public OaHolidayMessageDaoImpl() {
		super(OaHolidayMessage.class);
	}

	@Override
	public List<OaHolidayMessage> getMessageByType(Integer type) {
		String hql = "from OaHolidayMessage where type = ?";
		List<OaHolidayMessage> list = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setParameter(0, type).list();
		return list;
	}

	@Override
	public List<OaHolidayMessage> getMessageByDate(Date date,Integer type) {
		List<OaHolidayMessage> list;

		if (null == type) {
			type = 1;
		}

		if (null == date) {
			String sql = " from OaHolidayMessage where  type = ? and state = 1 order by id desc " ;
			list = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql).setParameter(0, type).list();
		}else {
			String sql = " from OaHolidayMessage where TO_DAYS(?) = TO_DAYS(holidayDate) and type = ? and state = 1 order by id desc ";
			list = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql).setParameter(0, date).setParameter(1, type).list();
		}

		return list;
	}
}