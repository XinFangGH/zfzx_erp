package com.zhiwei.credit.service.creditFlow.financingAgency.persion;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;

/**
 * 
 * @author 
 *
 */
public interface BpPersionOrProService extends BaseService<BpPersionOrPro>{
	/**
	 * 统计项目的发标情况  （发标金额 ，剩余金额，发标数量 ，发标占比）
	 * @param pack
	 * @return
	 */
	public BpPersionOrPro residueMoneyMeth(BpPersionOrPro pack);
	/**
	 * 
	 * 展期个人债权标查询
	 */
	public List<BpPersionOrPro> bpPersionOrProList(PagingBean pb,HttpServletRequest request);
	/**
	 * 
	 * 展期个人债权标总数查询
	 */
	public Long bpPersionOrProCount(HttpServletRequest request);
}


