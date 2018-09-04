package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.CarApplyDao;
import com.zhiwei.credit.model.admin.CarApply;

@SuppressWarnings("unchecked")
public class CarApplyDaoImpl extends BaseDaoImpl<CarApply> implements CarApplyDao{

	public CarApplyDaoImpl() {
		super(CarApply.class);
	}

	@Override
	public List<CarApply> findByCarIdAndStartEndTime(Long carId,
			Date startTime, Date endTime) {
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select vo from CarApply vo ");
		hql.append("where vo.car.carId = ? ");
		params.add(carId);
	    hql.append("and ((vo.startTime < ? and vo.endTime > ?) "); 
	    hql.append("or (vo.startTime < ? and vo.endTime > ?) ");
	    hql.append("or (vo.startTime > ? and vo.endTime <?))");
		params.add(startTime);
		params.add(startTime);
		params.add(endTime);
		params.add(endTime);
		params.add(startTime);
		params.add(endTime);
		return findByHql(hql.toString(), params.toArray());
	}

}