package com.zhiwei.credit.service.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.customer.InvestPerson;

/**
 * 
 * @author 
 *
 */
public interface InvestPersonService extends BaseService<InvestPerson>{
	/**
	 * 获取投资客户列表
	 * @param userIdsStr
	 * @param pb
	 * @param request
	 * @return
	 */
	public List<InvestPerson> getList(String companyId,String userIdsStr,PagingBean pb,HttpServletRequest request, Map map);
	/**
	 * 
	 * @param cardNumber
	 * @return
	 */
	public InvestPerson getByCardNumber(String cardNumber);
	/**
	 * @param customerLevel
	 * 通过客户级别获取用户列表
	 * 
	 */
	public List<InvestPerson> getListByCustomerLevel(String customerLevel);
}


