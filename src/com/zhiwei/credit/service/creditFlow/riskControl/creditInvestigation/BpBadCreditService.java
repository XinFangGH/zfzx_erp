package com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;

/**
 * 
 * @author 
 *
 */
public interface BpBadCreditService extends BaseService<BpBadCredit>{
	public List<BpBadCredit> bpBadCreditList(PagingBean pb,HttpServletRequest request);
	public Long bpBadCreditCount(HttpServletRequest request);
	public BpBadCredit getByMoneyIdType(Long moneyId,String moneyType);
}


