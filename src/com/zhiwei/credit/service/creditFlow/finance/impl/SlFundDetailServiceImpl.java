package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.service.creditFlow.finance.SlFundDetailService;

/**
 * 
 * @author 
 *
 */
public class SlFundDetailServiceImpl extends BaseServiceImpl<SlFundDetail> implements SlFundDetailService{
	private SlFundDetailDao dao;
	
	public SlFundDetailServiceImpl(SlFundDetailDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlFundDetail> getlistbySlFundIntentId(Long slFundIntentId, String type) {
		// TODO Auto-generated method stub
		return dao.getlistbySlFundIntentId(slFundIntentId,type);
	}

	@Override
	public List<SlFundDetail> getallbycompanyId() {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId();
	}

	@Override
	public List<SlFundDetail> getAllRecordList(String projectId, String period) {
		return dao.getAllRecordList(projectId,period);
	}
}