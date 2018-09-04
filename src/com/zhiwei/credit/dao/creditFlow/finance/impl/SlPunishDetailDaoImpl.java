package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlPunishDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlPunishDetailDaoImpl extends BaseDaoImpl<SlPunishDetail> implements SlPunishDetailDao{

	public SlPunishDetailDaoImpl() {
		super(SlPunishDetail.class);
	}

	@Override
	public List<SlPunishDetail> getlistbyActualChargeId(Long punishInterestId) {
		String hql = "from SlPunishDetail s where s.iscancel = null and  s.punishInterestId="+punishInterestId;
		return findByHql(hql);
	}

}