package com.zhiwei.credit.dao.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.ConfAttendDao;
import com.zhiwei.credit.model.admin.ConfAttend;

/**
 * @description ConfAttendDaoImpl
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
@SuppressWarnings("unchecked")
public class ConfAttendDaoImpl extends BaseDaoImpl<ConfAttend> implements
		ConfAttendDao {

	public ConfAttendDaoImpl() {
		super(ConfAttend.class);
	}

}