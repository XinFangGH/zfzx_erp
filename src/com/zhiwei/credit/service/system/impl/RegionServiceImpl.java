package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.RegionDao;
import com.zhiwei.credit.model.system.Region;
import com.zhiwei.credit.service.system.RegionService;

public class RegionServiceImpl extends BaseServiceImpl<Region> implements RegionService{
	private RegionDao dao;
	
	public RegionServiceImpl(RegionDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<Region> getProvince() {
		return dao.getProvince();
	}

	@Override
	public List<Region> getCity(Long regionId) {
		return dao.getCity(regionId);
	}

}