package com.zhiwei.credit.service.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.task.Appointment;

public interface AppointmentService extends BaseService<Appointment>{
	public List showAppointmentByUserId(Long userId, PagingBean pb);
}


