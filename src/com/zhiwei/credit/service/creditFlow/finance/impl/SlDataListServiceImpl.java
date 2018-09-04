package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlDataListDao;
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;
import com.zhiwei.credit.service.creditFlow.finance.SlDataListService;

/**
 * 
 * @author 
 *
 */
public class SlDataListServiceImpl extends BaseServiceImpl<SlDataList> implements SlDataListService{
	@SuppressWarnings("unused")
	private SlDataListDao dao;
	
	public SlDataListServiceImpl(SlDataListDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlDataList> getListByType(String type, String companyId,
			String startDate, String endDate, String sendPersonId, PagingBean pb) {
		
		return dao.getListByType(type, companyId, startDate, endDate, sendPersonId, pb);
	}

	@Override
	public Date getMaxDate(String type) {
		
		return dao.getMaxDate(type);
	}

}