package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.customer.InvestPersonDao;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.service.customer.InvestPersonService;

/**
 * 
 * @author 
 *
 */
public class InvestPersonServiceImpl extends BaseServiceImpl<InvestPerson> implements InvestPersonService{
	@SuppressWarnings("unused")
	private InvestPersonDao dao;
	
	public InvestPersonServiceImpl(InvestPersonDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 获取投资客户列表
	 * @param userIdsStr
	 * @param pb
	 * @param request
	 * @return
	 */
	public List<InvestPerson> getList(String companyId,String userIdsStr,PagingBean pb,HttpServletRequest request,Map map){
		return dao.getList(companyId,userIdsStr, pb, request,map);
	}

	public InvestPerson getByCardNumber(String cardNumber) {
		return dao.getByCardNumber(cardNumber);
	}
	@Override
	public List<InvestPerson> getListByCustomerLevel(String customerLevel) {
		// TODO Auto-generated method stub
		String hql = "from InvestPerson ip where ip.customerLevel=?";
		return dao.findByHql(hql, new Object[]{customerLevel});
	}
}