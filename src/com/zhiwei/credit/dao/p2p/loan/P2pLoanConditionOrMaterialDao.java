package com.zhiwei.credit.dao.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.p2p.loan.P2pLoanConditionOrMaterial;

/**
 * 
 * @author 
 *
 */
public interface P2pLoanConditionOrMaterialDao extends BaseDao<P2pLoanConditionOrMaterial>{
	/**
	 * 
	 * @param productId
	 * @return根据productId，conditionType查询列表
	 */
	public List<P2pLoanConditionOrMaterial> getByProductId(Long productId,Long conditionType );
	
}