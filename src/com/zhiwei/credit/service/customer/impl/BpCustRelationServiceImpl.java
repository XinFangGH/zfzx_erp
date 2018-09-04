package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.BpCustRelationDao;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.service.customer.BpCustRelationService;

/**
 * 
 * @author 
 *
 */
public class BpCustRelationServiceImpl extends BaseServiceImpl<BpCustRelation> implements BpCustRelationService{
	@SuppressWarnings("unused")
	private BpCustRelationDao dao;
	
	public BpCustRelationServiceImpl(BpCustRelationDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpCustRelation> getByCustIdAndCustType(Long custId,
			String custType) {
		String hql = "from BpCustRelation br where br.offlineCusId=? and br.offlineCustType= ?";
		return dao.findByHql(hql, new Object[]{custId,custType});
	}
	
	@Override
	public BpCustRelation getByLoanTypeAndId(String loanUserType,
			Long loanUserId) {
		
		return dao.getByLoanTypeAndId(loanUserType,loanUserId);
	}
	
	@Override
	public BpCustRelation getP2pCustById(Long custId) {
		// TODO Auto-generated method stub
		return dao.getP2pCustById(custId);
	}

	@Override
	public BpCustRelation getByTypeAndP2pCustId(Long p2pCustId,
			String offlineCustType) {
		
		return dao.getByTypeAndP2pCustId(p2pCustId, offlineCustType);
	}

}