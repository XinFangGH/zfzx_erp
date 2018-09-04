package com.zhiwei.credit.service.personal.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.personal.DutySectionDao;
import com.zhiwei.credit.model.personal.DutySection;
import com.zhiwei.credit.service.personal.DutySectionService;

public class DutySectionServiceImpl extends BaseServiceImpl<DutySection> implements DutySectionService{
	private DutySectionDao dao;
	
	public DutySectionServiceImpl(DutySectionDao dao) {
		super(dao);
		this.dao=dao;
	}

}