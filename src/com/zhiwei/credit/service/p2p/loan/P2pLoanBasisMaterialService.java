package com.zhiwei.credit.service.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.loan.P2pLoanBasisMaterial;

/**
 * 
 * @author 
 *
 */
public interface P2pLoanBasisMaterialService extends BaseService<P2pLoanBasisMaterial>{
	public List<P2pLoanBasisMaterial> findBasisList(Long productId,String operationType);
}


