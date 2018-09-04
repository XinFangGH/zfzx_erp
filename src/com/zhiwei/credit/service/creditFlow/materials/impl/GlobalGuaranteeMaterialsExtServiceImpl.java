package com.zhiwei.credit.service.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.materials.GlobalGuaranteeMaterialsExtDao;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsExt;
import com.zhiwei.credit.service.creditFlow.materials.GlobalGuaranteeMaterialsExtService;

/**
 * 
 * @author 
 *
 */
public class GlobalGuaranteeMaterialsExtServiceImpl extends BaseServiceImpl<GlobalGuaranteeMaterialsExt> implements GlobalGuaranteeMaterialsExtService{
	@SuppressWarnings("unused")
	private GlobalGuaranteeMaterialsExtDao dao;
	
	public GlobalGuaranteeMaterialsExtServiceImpl(GlobalGuaranteeMaterialsExtDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GlobalGuaranteeMaterialsExt> getListByParentId(
			Integer parentId) {
		// TODO Auto-generated method stub
		return this.dao.getListByParentId(parentId);
	}

	@Override
	public List<GlobalGuaranteeMaterialsExt> getListByParentIdAndType(
			Integer parentId, String operationTypeKey) {
		return dao.getListByParentIdAndType(parentId, operationTypeKey);
	}

}