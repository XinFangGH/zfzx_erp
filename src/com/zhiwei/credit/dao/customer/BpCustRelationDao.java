package com.zhiwei.credit.dao.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.customer.BpCustRelation;

/**
 * 
 * @author 
 *
 */
public interface BpCustRelationDao extends BaseDao<BpCustRelation>{

	public BpCustRelation getByLoanTypeAndId(String loanUserType, Long loanUserId);

	//svn:songwj
		 
		public BpCustRelation getP2pCustById(Long custId);  
		public List<BpCustRelation> getP2pCustListById(Long custId); 
		public BpCustRelation getByTypeAndP2pCustId(Long p2pCustId,String offlineCustType);
		
		public BpCustRelation getByOfflineCusIdAndOfflineCustType(Long p2pCustId,String offlineCustType);
 
}