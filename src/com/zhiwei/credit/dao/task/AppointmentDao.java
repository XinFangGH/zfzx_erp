package com.zhiwei.credit.dao.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.task.Appointment;

/**
 * 
 * @author 
 *
 */
public interface AppointmentDao extends BaseDao<Appointment>{
	//首页中根据当前登录用户显示约会列表
	public List showAppointmentByUserId(Long userId,PagingBean pb);
}