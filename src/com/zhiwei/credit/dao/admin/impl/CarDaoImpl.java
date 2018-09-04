package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.CarDao;
import com.zhiwei.credit.model.admin.Car;

public class CarDaoImpl extends BaseDaoImpl<Car> implements CarDao{

	public CarDaoImpl() {
		super(Car.class);
	}

}