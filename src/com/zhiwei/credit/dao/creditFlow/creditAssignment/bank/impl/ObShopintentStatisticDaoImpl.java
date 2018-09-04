package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObShopintentStatisticDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObShopintentStatistic;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ObShopintentStatisticDaoImpl extends BaseDaoImpl<ObShopintentStatistic> implements ObShopintentStatisticDao{

	public ObShopintentStatisticDaoImpl() {
		super(ObShopintentStatistic.class);
	}
	@Override
	public List<ObShopintentStatistic> listShopIntentStatistic(
			String shopName, String startDate, String endDate,Integer start, Integer limit) {
		// TODO Auto-generated method stub
		
		String hql=" from ObShopintentStatistic as ob where 1=1 ";
		if(shopName!=null &&!"".equals(shopName)){
			hql=hql+"  and  ob.shopName like '%"+shopName+"%'";
		}
		if(startDate!=null &&!"".equals(startDate)){
			hql=hql+"  and  ob.createDate >='"+startDate+"'";
		}  
		if(endDate!=null &&!"".equals(endDate)){
			hql=hql+"  and  ob.createDate <'"+endDate+"'";
		}
		return  this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public List<ObShopintentStatistic> listObShopIntentStatistic(Long shopId) {
		// TODO Auto-generated method stub
		String hql=" from ObShopintentStatistic as ob where ob.shopId=?";
		return this.getSession().createQuery(hql).setParameter(0, shopId).list();
	}

}