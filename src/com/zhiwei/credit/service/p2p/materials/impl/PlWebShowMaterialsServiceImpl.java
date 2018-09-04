package com.zhiwei.credit.service.p2p.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.materials.PlWebShowMaterialsDao;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.p2p.materials.PlWebShowMaterials;
import com.zhiwei.credit.service.p2p.materials.PlWebShowMaterialsService;

/**
 * 
 * @author 
 *
 */
public class PlWebShowMaterialsServiceImpl extends BaseServiceImpl<PlWebShowMaterials> implements PlWebShowMaterialsService{
	@SuppressWarnings("unused")
	private PlWebShowMaterialsDao dao;
	
	public PlWebShowMaterialsServiceImpl(PlWebShowMaterialsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlWebShowMaterials> getByProjIdAndShow(String projId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.getByProjIdAndShow(projId,businessType);
	}

	@Override
	public List<PlWebShowMaterials> getByProMaterialsId(String projId,
			SlProcreditMaterials slProcreditMaterials, String businessType,
			String operationType) {
		// TODO Auto-generated method stub
		return dao.getByProMaterialsId(projId,slProcreditMaterials,businessType,operationType);
	}

	@Override
	public List<PlWebShowMaterials> listByMaterialsIdAndOperationTypeKey(
			String projId, String businessType, String operationType,
			Long valueOf) {
		// TODO Auto-generated method stub
		return dao.listByMaterialsIdAndOperationTypeKey(projId,businessType,operationType,valueOf);
	}

	@Override
	public List<PlWebShowMaterials> listByMaterialsIdGroupById(String projId,
			String businessType, String operationType) {
		// TODO Auto-generated method stub
		return dao.listByMaterialsIdGroupById(projId,businessType,operationType);
	}

}