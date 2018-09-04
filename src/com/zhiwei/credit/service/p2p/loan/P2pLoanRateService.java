package com.zhiwei.credit.service.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.p2p.loan.P2pLoanRate;

/**
 * 
 * @author 
 *
 */
public interface P2pLoanRateService extends BaseService<P2pLoanRate>{
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


