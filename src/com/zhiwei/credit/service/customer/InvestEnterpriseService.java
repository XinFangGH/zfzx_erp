package com.zhiwei.credit.service.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.customer.InvestEnterprise;

/**
 * 
 * @author 
 *
 */
public interface InvestEnterpriseService extends BaseService<InvestEnterprise>{
	public List<InvestEnterprise> getList(HttpServletRequest request,PagingBean pb,String userIdStr);
	public List<InvestEnterprise> getExcelList(HttpServletRequest request,
			PagingBean pb);
	public InvestEnterprise getByOrganizecode(String organizecode);
	
	List<InvestEnterprise> getList(String businessType,String userIdStr);
}


