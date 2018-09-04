package com.zhiwei.credit.dao.creditFlow.financingAgency.business;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;

/**
 * 
 * @author 
 *
 */
public interface BpBusinessOrProDao extends BaseDao<BpBusinessOrPro>{
	BpBusinessOrPro getBpBusinessOrProByMoneyPlanId(Long moneyPlanId);
	/**
	 * 
	 * 展期企业债权标查询
	 */
	public List<BpBusinessOrPro> bpBusinessOrProList(PagingBean pb,HttpServletRequest request);
	/**
	 * 
	 * 展期企业债权标总数查询
	 */
	public Long bpBusinessOrProCount(HttpServletRequest request);
}