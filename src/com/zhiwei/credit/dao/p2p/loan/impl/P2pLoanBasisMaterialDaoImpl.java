package com.zhiwei.credit.dao.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanBasisMaterialDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.p2p.loan.P2pLoanBasisMaterial;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class P2pLoanBasisMaterialDaoImpl extends BaseDaoImpl<P2pLoanBasisMaterial> implements P2pLoanBasisMaterialDao{

	public P2pLoanBasisMaterialDaoImpl() {
		super(P2pLoanBasisMaterial.class);
	}

	@Override
	public List<P2pLoanBasisMaterial> findBasisList(Long productId,String operationType) {
		// TODO Auto-generated method stub
		String sql="SELECT * FROM p2p_loan_basismaterial p WHERE  p.materialState<>0  and   p.operationType like '%"+operationType+"%'  and   p.materialId NOT IN ( SELECT	c.projectId FROM p2p_loan_conditionormaterial c WHERE	c.productId="+productId+" and c.projectId is not null and c.conditionType=2)";
	//	System.out.println("-->"+sql);
		
		return this.getSession().createSQLQuery(sql)
		.addScalar("materialId",Hibernate.LONG)
		.addScalar("materialState",Hibernate.LONG)
		.addScalar("materialName",Hibernate.STRING)
		.addScalar("operationType",Hibernate.STRING)
		.addScalar("remark",Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(P2pLoanBasisMaterial.class)).list();
	}

}