package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.RegionDao;
import com.zhiwei.credit.model.system.Region;

public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao{

	public RegionDaoImpl() {
		super(Region.class);
	}

	/**
	 * 查出所有的省份
	 */
	@Override
	public List<Region> getProvince() {
		Long parentId = 0l;
		String hql = "from Region r where r.parentId = ?";
		return findByHql(hql, new Object[]{parentId});
	}
	
	/**
	 * 根据省份的ID查出该省所有的市
	 */
	@Override
	public List<Region> getCity(Long regionId) {
		String hql = "from Region r where r.parentId = ?";
		return findByHql(hql, new Object[]{regionId});
	}


}