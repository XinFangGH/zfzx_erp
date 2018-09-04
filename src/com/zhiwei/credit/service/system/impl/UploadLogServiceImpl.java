package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
 
import com.zhiwei.credit.dao.system.UploadLogDao;
import com.zhiwei.credit.model.system.UploadLog;
import com.zhiwei.credit.service.system.UploadLogService;

/**
 * 
 * @author 
 *
 */
public class UploadLogServiceImpl extends BaseServiceImpl<UploadLog> implements UploadLogService{
	@SuppressWarnings("unused")
	private UploadLogDao dao;
	
	public UploadLogServiceImpl(UploadLogDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<UploadLog> listBybBidId(String bidId) {
		
		return dao.listBybBidId(bidId);
	}

}