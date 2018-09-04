package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.VPunishDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.VPunishDetail;
import com.zhiwei.credit.service.creditFlow.finance.VPunishDetailService;

/**
 * 
 * @author 
 *
 */
public class VPunishDetailServiceImpl extends BaseServiceImpl<VPunishDetail> implements VPunishDetailService{
	private VPunishDetailDao dao;
	
	public VPunishDetailServiceImpl(VPunishDetailDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<VPunishDetail> wslistbyPunish(String businessType,
			Long projectId, String factDate) {
		// TODO Auto-generated method stub
		return dao.wslistbyPunish(businessType, projectId, factDate);
	}

	@Override
	public List<VPunishDetail> search(Map<String, String> map) {
		return dao.search(map);
	}

	@Override
	public int searchsize(Map<String, String> map) {
		return dao.searchsize(map);
	}

}