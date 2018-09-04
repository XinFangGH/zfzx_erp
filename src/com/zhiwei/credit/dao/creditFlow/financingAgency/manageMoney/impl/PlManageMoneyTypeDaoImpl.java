package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlManageMoneyTypeDaoImpl extends BaseDaoImpl<PlManageMoneyType> implements PlManageMoneyTypeDao{

	public PlManageMoneyTypeDaoImpl() {
		super(PlManageMoneyType.class);
	}


	@Override
	public List<PlManageMoneyType> getListbykeystr(String keystr) {
		String hql="from PlManageMoneyType q where q.state=0 and q.keystr=?";
		return getSession().createQuery(hql).setParameter(0, keystr).list();
	}


	@Override
	public PlManageMoneyPlan yesOrNo(Long manageMoneyTypeId) {
		PlManageMoneyPlan plManageMoneyPlan=null;
		String hql="from PlManageMoneyPlan AS plmp WHERE plmp.manageMoneyTypeId=?";
		List<PlManageMoneyPlan> plist=getSession().createQuery(hql).setParameter(0, manageMoneyTypeId).list();
		if(plist.size()>0){
			plManageMoneyPlan=plist.get(0);
		}
		return plManageMoneyPlan;
	}

}