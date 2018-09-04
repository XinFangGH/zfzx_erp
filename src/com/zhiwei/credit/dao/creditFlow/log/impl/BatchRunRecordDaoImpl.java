package com.zhiwei.credit.dao.creditFlow.log.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.log.BatchRunRecordDao;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BatchRunRecordDaoImpl extends BaseDaoImpl<BatchRunRecord> implements BatchRunRecordDao{

	public BatchRunRecordDaoImpl() {
		super(BatchRunRecord.class);
	}

}