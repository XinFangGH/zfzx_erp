package com.zhiwei.credit.service.creditFlow.creditmanagement.impl;

import javax.annotation.Resource;

import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorDao;
import com.zhiwei.credit.service.creditFlow.creditmanagement.IndicatorService;


public class IndicatorServiceImpl  implements IndicatorService{
	@Resource
	private IndicatorDao dao;
	

	
	
	 @Override
	public Object[] getListByLastId(String type, int indicatorTypeId,
			int start, int limit) {
		return null;
	}

	@Override
	public Object[] getListByMidId(String type, int indicatorTypeId, int start,
			int limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
