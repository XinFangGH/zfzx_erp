package com.zhiwei.credit.service.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.customer.BpCustRelation;

/**
 * 
 * @author 
 *
 */
public interface BpCustRelationService extends BaseService<BpCustRelation>{
	List<BpCustRelation> getByCustIdAndCustType(Long custId,String custType);
	
	/**
     * 获取关系 通过类别和ID
     * @param loanUserType
     * @param loanUserId
     * @return
     */
	BpCustRelation getByLoanTypeAndId(String loanUserType, Long loanUserId);
	
	
	public BpCustRelation getP2pCustById(Long custId);
	
	public BpCustRelation getByTypeAndP2pCustId(Long p2pCustId,String offlineCustType);
}


