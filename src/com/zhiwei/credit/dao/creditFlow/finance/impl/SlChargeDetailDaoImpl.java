package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlChargeDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlChargeDetailDaoImpl extends BaseDaoImpl<SlChargeDetail> implements SlChargeDetailDao{

	public SlChargeDetailDaoImpl() {
		super(SlChargeDetail.class);
	}

	@Override
	public List<SlChargeDetail> getlistbyActualChargeId(Long actualChargeId) {
		String hql = "from SlChargeDetail s where s.iscancel = null and  s.slActualToCharge.actualChargeId="+actualChargeId;
		return findByHql(hql);
	}

	@Override
	public List<SlChargeDetail> getallbycompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	

}