package com.zhiwei.credit.service.creditFlow.common.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.common.AreaManagementDao;
import com.zhiwei.credit.model.creditFlow.common.AreaManagement;
import com.zhiwei.credit.service.creditFlow.common.AreaManagementService;

/**
 * 
 * @author 
 *
 */
public class AreaManagementServiceImpl extends BaseServiceImpl<AreaManagement> implements AreaManagementService{
	@SuppressWarnings("unused")
	private AreaManagementDao dao;
	
	public AreaManagementServiceImpl(AreaManagementDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<AreaManagement> getListByParentId(Long parentId) {
		
		return dao.getListByParentId(parentId);
	}

}