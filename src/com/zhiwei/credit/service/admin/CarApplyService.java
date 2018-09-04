package com.zhiwei.credit.service.admin;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.admin.CarApply;

public interface CarApplyService extends BaseService<CarApply> {

	/**
	 * 根据车辆编号[carId],申请的开始时间[startTime],申请的结束时间[endTime]查询满足添加的数据
	 * 
	 * @param carId
	 *            车辆编号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return List<CarApply>
	 */
	List<CarApply> findByCarIdAndStartEndTime(Long carId, Date startTime,
			Date endTime);
}
