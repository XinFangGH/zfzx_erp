package com.zhiwei.credit.service.creditFlow.assuretenet.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.assuretenet.OurProcreditAssuretenetDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.service.creditFlow.assuretenet.OurProcreditAssuretenetService;

/**
 * 
 * @author 
 *
 */
public class OurProcreditAssuretenetServiceImpl extends BaseServiceImpl<OurProcreditAssuretenet> implements OurProcreditAssuretenetService{
	private OurProcreditAssuretenetDao dao;
	
	public OurProcreditAssuretenetServiceImpl(OurProcreditAssuretenetDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<OurProcreditAssuretenet> getListByBussinessType(
			String businessTypeKey,PagingBean pb) {

		return dao.getListByBussinessType(businessTypeKey,pb);
	}

	@Override
	public void deleteByProductId(String id) {
		dao.deleteByProductId(id);
	}

	@Override
	public List<OurProcreditAssuretenet> getByProjectId(Long projectId) {
		return dao.getByProjectId(projectId);
	}

	@Override
	public List<OurProcreditAssuretenet> getByProductId(Long productId) {
		return dao.getByProductId(productId);
	}

	@Override
	public List<OurProcreditAssuretenet> getAssuretenetTree(String customerType) {
		return dao.getAssuretenetTree(customerType);
	}

	@Override
	public List<OurProcreditAssuretenet> getByPProductIdAndOperationType(
			String productId, String businessType) {
		// TODO Auto-generated method stub
		return dao.getByPProductIdAndOperationType(productId, businessType);
	}

	@Override
	public List<OurProcreditAssuretenet> getListByType(String businessType,
			String operationType) {
		// TODO Auto-generated method stub
		return dao.getListByType(businessType,operationType);
	}

	@Override
	public List<OurProcreditAssuretenet> checkIsExit(String productId,
			Long assuretenetId, String businessType) {
		// TODO Auto-generated method stub
		return dao.checkIsExit(productId, assuretenetId, businessType);
	}

}