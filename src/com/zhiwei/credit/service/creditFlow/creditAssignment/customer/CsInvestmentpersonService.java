package com.zhiwei.credit.service.creditFlow.creditAssignment.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.VInvestmentPerson;;

/**
 * 
 * @author 
 *
 */
public interface CsInvestmentpersonService extends BaseService<CsInvestmentperson>{
	public void ajaxQueryinvestmentPerson(Map<String,String> map,Integer start,Integer limit, String hql, String[] str,
			Object[] obj, String sort, String dir);
	public void addInvestment(CsInvestmentperson csInvestmentperson);
	//查询单个投资客户的信息
	public VInvestmentPerson queryInvestmentPerson(Long investId);
	//更新单个投资客户信息
	public void updatePerson(CsInvestmentperson csInvestmentperson);
	public List<VInvestmentPerson> getList(HttpServletRequest request,String companyId,String userIds);
	/**查询投资人和银行卡详细信息*/
	public List<CsInvestmentperson> getPersonAndBank();
	public List<CsInvestmentperson> getAllList(HttpServletRequest request,
			Integer start, Integer limit);
	public List<CsInvestmentperson> listInvest(Integer start, Integer limit, HttpServletRequest request);
	public CsInvestmentperson queryPersonCardnumber(String cardNum);
	public List<CsInvestmentperson> getDown(Map<String,String> map,Integer start,Integer limit);
}


