package com.zhiwei.credit.dao.creditFlow.creditAssignment.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.VInvestmentPerson;

/**
 * 
 * @author 
 *
 */
public interface CsInvestmentpersonDao extends BaseDao<CsInvestmentperson>{
	List<CsInvestmentperson> getPersonAndBank();
	public List<VInvestmentPerson> getList(HttpServletRequest request,String companyId,String userIds);
	public List<CsInvestmentperson> ajaxQueryinvestmentPerson(Map<String,String> map,
			Integer start, Integer limit, String hql,String[] str, Object[] obj, String dir);
	List<CsInvestmentperson> getAllList(HttpServletRequest request,
			Integer start, Integer limit);
	public List<CsInvestmentperson> listInvest(Integer start, Integer limit, HttpServletRequest request);
	
	public List<CsInvestmentperson> getDown(Map<String, String> map,
			Integer start, Integer limit);
}