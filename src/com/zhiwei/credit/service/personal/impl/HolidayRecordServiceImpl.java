package com.zhiwei.credit.service.personal.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.personal.HolidayRecordDao;
import com.zhiwei.credit.model.personal.HolidayRecord;
import com.zhiwei.credit.service.personal.HolidayRecordService;

public class HolidayRecordServiceImpl extends BaseServiceImpl<HolidayRecord> implements HolidayRecordService{
	private HolidayRecordDao dao;
	
	public HolidayRecordServiceImpl(HolidayRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

}