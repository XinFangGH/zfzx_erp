package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.CsHolidayDao;
import com.zhiwei.credit.model.system.CsHoliday;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsHolidayDaoImpl extends BaseDaoImpl<CsHoliday> implements CsHolidayDao{

	public CsHolidayDaoImpl() {
		super(CsHoliday.class);
	}

	@Override
	public List<CsHoliday> checkRepeatByDate(Date d) {
		final String hql = "from CsHoliday  ch where ch.dateStr=?";
		Object[] params ={d};
		return findByHql(hql, params);
	}

	@Override
	public CsHoliday getMaxDeferDay(Date intentDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer hql=new StringBuffer("select " +
				" ch.id," +
				" ch.dateStr," +
				" ch.yearStr," +
				" ch.dicId " +
				" from cs_holiday as ch" +
				" where DATE_FORMAT('"+sdf.format(intentDate)+"','%Y-%m-%d') " +
				" BETWEEN DATE_FORMAT(ch.dateStr,'%Y-%m-%d')" +
				" AND DATE_FORMAT(ch.yearStr,'%Y-%m-%d') ORDER BY ch.yearStr DESC");
		Session s=this.getSession();
		List list = s.createSQLQuery(hql.toString())
			   .addScalar("id",Hibernate.LONG)
			   .addScalar("dicId",Hibernate.LONG)
			   .addScalar("dateStr",Hibernate.DATE)
			   .addScalar("yearStr",Hibernate.DATE)
			   .setResultTransformer(Transformers.aliasToBean(CsHoliday.class)).list();
		this.releaseSession(s);
		if(null!=list && list.size()>0){
			return (CsHoliday)list.get(0);
		}else{
			return null;
		}
	}

}