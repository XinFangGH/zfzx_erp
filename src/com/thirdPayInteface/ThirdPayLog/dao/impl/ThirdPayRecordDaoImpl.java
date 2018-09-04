package com.thirdPayInteface.ThirdPayLog.dao.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.thirdPayInteface.ThirdPayLog.dao.ThirdPayRecordDao;
import com.thirdPayInteface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.dao.impl.BaseDaoImpl;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ThirdPayRecordDaoImpl extends BaseDaoImpl<ThirdPayRecord> implements ThirdPayRecordDao{

	public ThirdPayRecordDaoImpl() {
		super(ThirdPayRecord.class);
	}

}