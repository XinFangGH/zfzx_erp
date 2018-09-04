package com.zhiwei.credit.dao.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanRateDao;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonThereunder;
import com.zhiwei.credit.model.p2p.loan.P2pLoanRate;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class P2pLoanRateDaoImpl extends BaseDaoImpl<P2pLoanRate> implements P2pLoanRateDao{

	public P2pLoanRateDaoImpl() {
		super(P2pLoanRate.class);
	}

	@Override
	public List<P2pLoanRate> p2pLoanRateList(PagingBean pb,
			Long productId) {
		// TODO Auto-generated method stub
		String hql="from P2pLoanRate as p where p.productId=?";
		List<P2pLoanRate> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{productId});
		}else{
			list=this.findByHql(hql, new Object[]{productId},pb);
		}
		return list;
	}

	@Override
	public Long p2pLoanRateListCount(Long productId) {
		// TODO Auto-generated method stub
		Long count=new Long("0");
		String hql="from P2pLoanRate as p where p.productId=?";
		List<P2pLoanRate> list=this.findByHql(hql, new Object[]{productId});
		if(null!=list && list.size()>0){
			count=Long.valueOf(list.size());
		}
		return count;
	}

}