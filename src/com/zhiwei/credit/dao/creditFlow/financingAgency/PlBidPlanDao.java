package com.zhiwei.credit.dao.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;

/**
 * 
 * @author 
 *
 */
public interface PlBidPlanDao extends BaseDao<PlBidPlan>{

	String findLoanTotalMoneyBySQL(String pid);

	String findOrgMoneyBySQL(String pid, String flag);
	//散标贷后列表
	public List<PlBidPlan> allLoanedList(HttpServletRequest request,PagingBean pb);
	
	
	/**
	 * 散标贷后查询
	 */
	public List<PlBidPlan> findPlbidplanLoanAfter(HttpServletRequest request,
			PagingBean pb);
	
	public List<PlBidPlan> listByState(String state,String proType,Long proId);
    /**
     * 根据标的id获取标的一些信息
     * @param planId
     * @return
     */
	public PlBidPlan getAllInfoByplanId(Long planId);
	/**
	 * 散标贷后查询
	 */
	public List<PlBidPlan> getByStateList(HttpServletRequest request,PagingBean pb);
	
	public Integer countList(HttpServletRequest request,PagingBean pb);
	
	public List<PlBidPlan> listByTypeId(String proType,Long proId);
	
	/**
	 * 个人直投标查询
	 */
	public List<PlBidPlan> pdBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	

	/**
	 * 个人直投标列表数据查询
	 */

	public Long countPdBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	
	/**
	 * 企业直投标查询
	 */
	public List<PlBidPlan> bdBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	

	/**
	 * 企业直投标列表数据查询
	 */

	public Long countbdBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	
	
	/**
	 * 个人转让标查询
	 */
	public List<PlBidPlan> poBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	

	/**
	 * 个人转让标列表数据查询
	 */

	public Long countPoBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	
	/**
	 * 企业转让标查询
	 */
	public List<PlBidPlan> boBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);
	

	/**
	 * 企业转让标列表数据查询
	 */

	public Long countboBidPlanList(HttpServletRequest request,PagingBean pb,int [] bidStates);

	public List<PlBidPlan> getPlanByStatusList(Short valueOf, PagingBean pb, Map map);
	public List<PlBidPlan> getByProType(Map<String, String> map);

	List<PlBidPlan> listByBdirProId(Long bdirProId);

	List<PlBidPlan> listByPdirProId(Long pdirProId);

	/**
	 * 根据发标流水号查找标
	 * @param loanOrderNo
	 * @return
	 * 2017-9-5
	 * @tzw
	 */
	PlBidPlan getplanByLoanOrderNo(String loanOrderNo);

    PlBidPlan getByBdirProId(Long aLong);
}