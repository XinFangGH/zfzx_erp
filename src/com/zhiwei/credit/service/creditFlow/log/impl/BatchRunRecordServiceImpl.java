package com.zhiwei.credit.service.creditFlow.log.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.log.BatchRunRecordDao;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;

/**
 * 
 * @author 
 *
 */
public class BatchRunRecordServiceImpl extends BaseServiceImpl<BatchRunRecord> implements BatchRunRecordService{
	@SuppressWarnings("unused")
	private BatchRunRecordDao dao;
	
	public BatchRunRecordServiceImpl(BatchRunRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

}