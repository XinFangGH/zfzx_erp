package com.zhiwei.credit.service.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.archives.OurArchivesMaterialsDao;
import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;
import com.zhiwei.credit.service.creditFlow.archives.OurArchivesMaterialsService;

/**
 * 
 * @author 
 *
 */
public class OurArchivesMaterialsServiceImpl extends BaseServiceImpl<OurArchivesMaterials> implements OurArchivesMaterialsService{
	@SuppressWarnings("unused")
	private OurArchivesMaterialsDao dao;
	
	public OurArchivesMaterialsServiceImpl(OurArchivesMaterialsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<OurArchivesMaterials> getByBusinessType(String businessType,PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getByBusinessType(businessType,pb);
	}
	
	@Override
	public List<OurArchivesMaterials> getbyoperationTypeKey(String operationTypeKey){
		// TODO Auto-generated method stub
		return dao.getbyoperationTypeKey(operationTypeKey);
	}

	@Override
	public List<OurArchivesMaterials> checkIsExit(String productId,
			Long materialsId, String businessType) {
		// TODO Auto-generated method stub
		return dao.checkIsExit(productId, materialsId, businessType);
	}

	@Override
	public List<OurArchivesMaterials> getByPProductIdAndOperationType(
			String productId, String businessType) {
		// TODO Auto-generated method stub
		return dao.getByPProductIdAndOperationType(productId, businessType);
	}

	@Override
	public List<OurArchivesMaterials> getByProductId(Long productId) {
		// TODO Auto-generated method stub
		return dao.getByProductId(productId);
	}

	@Override
	public List<OurArchivesMaterials> getListByType(String businessType,
			String operationType) {
		// TODO Auto-generated method stub
		return dao.getListByType(businessType,operationType);
	}

}