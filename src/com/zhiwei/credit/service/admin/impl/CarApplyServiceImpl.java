package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.CarApplyDao;
import com.zhiwei.credit.model.admin.CarApply;
import com.zhiwei.credit.service.admin.CarApplyService;

public class CarApplyServiceImpl extends BaseServiceImpl<CarApply> implements CarApplyService{
	private CarApplyDao dao;
	
	public CarApplyServiceImpl(CarApplyDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CarApply> findByCarIdAndStartEndTime(Long carId,
			Date startTime, Date endTime) {
		return dao.findByCarIdAndStartEndTime(carId, startTime, endTime);
	}

}