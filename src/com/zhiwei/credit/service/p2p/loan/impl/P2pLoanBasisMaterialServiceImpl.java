package com.zhiwei.credit.service.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanBasisMaterialDao;
import com.zhiwei.credit.model.p2p.loan.P2pLoanBasisMaterial;
import com.zhiwei.credit.service.p2p.loan.P2pLoanBasisMaterialService;

/**
 * 
 * @author 
 *
 */
public class P2pLoanBasisMaterialServiceImpl extends BaseServiceImpl<P2pLoanBasisMaterial> implements P2pLoanBasisMaterialService{
	@SuppressWarnings("unused")
	private P2pLoanBasisMaterialDao dao;
	
	public P2pLoanBasisMaterialServiceImpl(P2pLoanBasisMaterialDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<P2pLoanBasisMaterial> findBasisList(Long productId,String operationType) {
		// TODO Auto-generated method stub
		return dao.findBasisList(productId,operationType);
	}

}