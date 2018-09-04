package com.zhiwei.credit.service.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.task.AppointmentDao;
import com.zhiwei.credit.model.task.Appointment;
import com.zhiwei.credit.service.task.AppointmentService;

public class AppointmentServiceImpl extends BaseServiceImpl<Appointment> implements AppointmentService{
	private AppointmentDao dao;
	
	public AppointmentServiceImpl(AppointmentDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List showAppointmentByUserId(Long userId, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.showAppointmentByUserId(userId, pb);
	}

}