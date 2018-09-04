package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.BpCustRelationDao;
import com.zhiwei.credit.model.customer.BpCustRelation;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustRelationDaoImpl extends BaseDaoImpl<BpCustRelation> implements BpCustRelationDao{

	public BpCustRelationDaoImpl() {
		super(BpCustRelation.class);
	}

	@Override
	public BpCustRelation getByLoanTypeAndId(String loanUserType,
			Long loanUserId) {
		String hql  = "from BpCustRelation bp where bp.offlineCustType= ? and bp.offlineCusId=? ";
		
		return (BpCustRelation) findUnique(hql, new Object[]{loanUserType,loanUserId});
	}
	
	//svn:songwj
	@Override
	public BpCustRelation getP2pCustById(Long custId) {
		String hql  = "from BpCustRelation bp where  bp.p2pCustId=? ";
		
		return (BpCustRelation) findUnique(hql, new Object[]{custId});
	}

	@Override
	public BpCustRelation getByTypeAndP2pCustId(Long p2pCustId,
			String offlineCustType) {
		String hql="from BpCustRelation as bp where bp.p2pCustId=? and bp.offlineCustType=?";
		return (BpCustRelation) this.findUnique(hql, new Object[]{p2pCustId,offlineCustType});
	}
	
	@Override
	public BpCustRelation getByOfflineCusIdAndOfflineCustType(Long offlineCusId,String offlineCustType) {
		String hql="from BpCustRelation as bp where bp.offlineCusId=? and bp.offlineCustType=?";
		return (BpCustRelation) this.findUnique(hql, new Object[]{offlineCusId,offlineCustType});
	}

	@Override
	public List<BpCustRelation> getP2pCustListById(Long custId) {
		// TODO Auto-generated method stub
		String hql  = "select * from bp_cust_relation bp where  bp.p2pCustId=?";
		return this.getSession().createSQLQuery(hql).addEntity(BpCustRelation.class).setParameter(0, custId).list();
	}
	
}