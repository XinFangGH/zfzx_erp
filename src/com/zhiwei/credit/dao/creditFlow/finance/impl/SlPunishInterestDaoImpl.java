package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlPunishInterestDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishInterest;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlPunishInterestDaoImpl extends BaseDaoImpl<SlPunishInterest> implements SlPunishInterestDao{

	public SlPunishInterestDaoImpl() {
		super(SlPunishInterest.class);
	}

	@Override
	public List<SlPunishInterest> listbyisInitialorId(Long fundIntentId) {
		String hql="from SlPunishInterest q  where  q.fundIntentId="+fundIntentId;
		return findByHql(hql);
	}

}