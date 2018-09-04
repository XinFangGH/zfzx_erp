package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.DepreRecordDao;
import com.zhiwei.credit.model.admin.DepreRecord;
import com.zhiwei.credit.service.admin.DepreRecordService;

public class DepreRecordServiceImpl extends BaseServiceImpl<DepreRecord> implements DepreRecordService{
	private DepreRecordDao dao;
	
	public DepreRecordServiceImpl(DepreRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Date findMaxDate(Long assetsId) {
		return dao.findMaxDate(assetsId);
	}

}