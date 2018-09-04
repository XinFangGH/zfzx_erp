package com.zhiwei.credit.dao.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanConditionOrMaterialDao;
import com.zhiwei.credit.model.p2p.loan.P2pLoanConditionOrMaterial;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class P2pLoanConditionOrMaterialDaoImpl extends BaseDaoImpl<P2pLoanConditionOrMaterial> implements P2pLoanConditionOrMaterialDao{

	public P2pLoanConditionOrMaterialDaoImpl() {
		super(P2pLoanConditionOrMaterial.class);
	}

	@Override
	public List<P2pLoanConditionOrMaterial> getByProductId(Long productId,Long conditionType) {
		// TODO Auto-generated method stub
		String hql=" from P2pLoanConditionOrMaterial p where p.productId=? and p.conditionType=?";
		return this.findByHql(hql,new Object[]{productId,conditionType});
	}

}