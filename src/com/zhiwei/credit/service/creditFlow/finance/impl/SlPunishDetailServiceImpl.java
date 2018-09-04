package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlPunishDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishDetail;
import com.zhiwei.credit.service.creditFlow.finance.SlPunishDetailService;

/**
 * 
 * @author 
 *
 */
public class SlPunishDetailServiceImpl extends BaseServiceImpl<SlPunishDetail> implements SlPunishDetailService{
	@SuppressWarnings("unused")
	private SlPunishDetailDao dao;
	
	public SlPunishDetailServiceImpl(SlPunishDetailDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlPunishDetail> getlistbyActualChargeId(Long punishInterestId) {
		// TODO Auto-generated method stub
		return dao.getlistbyActualChargeId(punishInterestId);
	}

}