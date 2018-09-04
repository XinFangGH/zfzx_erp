package com.zhiwei.credit.service.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanConditionOrMaterialDao;
import com.zhiwei.credit.model.p2p.loan.P2pLoanConditionOrMaterial;
import com.zhiwei.credit.service.p2p.loan.P2pLoanConditionOrMaterialService;

/**
 * 
 * @author 
 *
 */
public class P2pLoanConditionOrMaterialServiceImpl extends BaseServiceImpl<P2pLoanConditionOrMaterial> implements P2pLoanConditionOrMaterialService{
	@SuppressWarnings("unused")
	private P2pLoanConditionOrMaterialDao dao;
	
	public P2pLoanConditionOrMaterialServiceImpl(P2pLoanConditionOrMaterialDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<P2pLoanConditionOrMaterial> getByProductId(Long productId,
			Long conditionType) {
		// TODO Auto-generated method stub
		return dao.getByProductId(productId, conditionType);
	}

}