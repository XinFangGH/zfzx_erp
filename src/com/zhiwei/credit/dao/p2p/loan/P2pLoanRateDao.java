package com.zhiwei.credit.dao.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;
import com.zhiwei.credit.model.p2p.loan.P2pLoanRate;

/**
 * 
 * @author 
 *
 */
public interface P2pLoanRateDao extends BaseDao<P2pLoanRate>{
	/**
	 * 
	 * @param pb
	 * @param productId
	 * @return 根据产品id查询利率列表
	 */
	public List<P2pLoanRate> p2pLoanRateList(PagingBean pb,Long productId);
	/**
	 * 查询总数
	 */
	public Long p2pLoanRateListCount(Long productId);
	
}