package com.zhiwei.credit.service.creditFlow.financingAgency.business;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;

/**
 * 
 * @author 
 *
 */
public interface BpBusinessOrProService extends BaseService<BpBusinessOrPro>{
	/**
	 * 统计项目的发标情况  （发标金额 ，剩余金额，发标数量 ，发标占比）
	 * @param pack
	 * @return
	 */
	public BpBusinessOrPro residueMoneyMeth(BpBusinessOrPro pack);
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


