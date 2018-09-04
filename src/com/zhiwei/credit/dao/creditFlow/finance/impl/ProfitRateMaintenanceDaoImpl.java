package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.ProfitRateMaintenanceDao;
import com.zhiwei.credit.model.creditFlow.finance.ProfitRateMaintenance;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ProfitRateMaintenanceDaoImpl extends BaseDaoImpl<ProfitRateMaintenance> implements ProfitRateMaintenanceDao{

	public ProfitRateMaintenanceDaoImpl() {
		super(ProfitRateMaintenance.class);
	}

	@Override
	public List getList(String startDate, String endDate, String strs) {
		String sql="SELECT s.projectId,s.intentDate,s.accrual,s.projectMoney,s.startDate,(select MAX(p.adjustDate) from profit_rate_maintenance as p where p.adjustDate<=s.startDate) as adjustDate from sl_smallloan_project as s where s.projectStatus!=0  and s.companyId in ("+strs+")";
		if(null!=startDate && !"".equals(startDate) && (null==endDate || "".equals(endDate))){
			sql=sql+" and s.startDate>='"+startDate+"'";
		}else if((null==startDate || "".equals(startDate)) && null!=endDate && !"".equals(endDate)){
			sql=sql+" and s.startDate<='"+endDate+"'";
		}else if(null!=startDate && !"".equals(startDate) && null!=endDate && !"".equals(endDate)){
			sql=sql+" and s.startDate>='"+startDate+"' and s.startDate<='"+endDate+"'";
		}
		return getSession().createSQLQuery(sql).list();
	}

	@Override
	public List<ProfitRateMaintenance> getRateList(Date adjustDate) {
		String hql="from ProfitRateMaintenance as p where p.adjustDate=?";
		return getSession().createQuery(hql).setParameter(0, adjustDate).list();
	}

}