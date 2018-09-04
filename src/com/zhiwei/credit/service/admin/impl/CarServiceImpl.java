package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.CarDao;
import com.zhiwei.credit.model.admin.Car;
import com.zhiwei.credit.service.admin.CarService;

public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService{
	private CarDao dao;
	
	public CarServiceImpl(CarDao dao) {
		super(dao);
		this.dao=dao;
	}

}