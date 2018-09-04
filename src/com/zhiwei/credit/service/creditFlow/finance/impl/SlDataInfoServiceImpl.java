package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlDataInfoDao;
import com.zhiwei.credit.model.creditFlow.finance.SlDataInfo;
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;
import com.zhiwei.credit.service.creditFlow.finance.SlDataInfoService;

/**
 * 
 * @author 
 *
 */
public class SlDataInfoServiceImpl extends BaseServiceImpl<SlDataInfo> implements SlDataInfoService{
	@SuppressWarnings("unused")
	private SlDataInfoDao dao;
	
	public SlDataInfoServiceImpl(SlDataInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlDataInfo> getListByDataId(Long dataId) {
		
		return dao.getListByDataId(dataId);
	}

}