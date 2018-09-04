package com.zhiwei.credit.service.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.ConfAttendDao;
import com.zhiwei.credit.model.admin.ConfAttend;
import com.zhiwei.credit.service.admin.ConfAttendService;

/**
 * @description ConfAttendServiceImpl
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public class ConfAttendServiceImpl extends BaseServiceImpl<ConfAttend>
		implements ConfAttendService {
	@SuppressWarnings("unused")
	private ConfAttendDao dao;

	public ConfAttendServiceImpl(ConfAttendDao dao) {
		super(dao);
		this.dao = dao;
	}

}