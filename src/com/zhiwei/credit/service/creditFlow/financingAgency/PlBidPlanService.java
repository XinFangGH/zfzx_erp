package com.zhiwei.credit.service.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.p2p.BpCustMember;

/**
 * 
 * @author 
 *
 */
public interface PlBidPlanService extends BaseService<PlBidPlan>{
	/**
	 * 获取标的动态信息
	 * 如 进度 投标人数 投标金额
	 * @return
	 */
	public PlBidPlan bidDynamic(PlBidPlan plBidPlan);
	/**
	 * 获取借款总额
	 * @param pid 项目id
	 * @return
	 */
	public String findLoanTotalMoneyBySQL(String pid);
	/**
	 * 获取合作机构 金额
	 * @param pid 项目id
	 * @param flag 1 合作机构 0 平台自有资金
	 * @return
	 */
	public String findOrgMoneyBySQL(String pid,String flag);
	/*
	 * 修改放款状态
	 * 启动办理流程中是否点击贷款发放按钮
	 * **/
	public void loanMoney(Long plBidPlanId);
	/**
	 * 获取标的信息
	 * @param bidplan
	 * @return
	 */
	public PlBidPlan getPlan(PlBidPlan bidplan) ;
	/**
	 * 判断是否已经还清 并修改状态
	 */
	public void bidComplete(Long planId,HttpServletRequest req);
	/**
	 * 
	 *散标贷后列表
	 */
	public List<PlBidPlan> allLoanedList(HttpServletRequest request,PagingBean pb);
	/**
	 * 启动散标的提前还款流程
	 */
	 public String startBidPrePaymentProcess(Long bidId);
	 
	 
	/**
	 * 散标贷后查询
	 */
	public List<PlBidPlan> findPlbidplanLoanAfter(HttpServletRequest request,PagingBean pb);
	
	public List<PlBidPlan> listByState(String state, String proType, Long proId);
	
	/**
	 * 通过标获取借款人 在p2p 注册的账号
	 * @param plBidInfo
	 * @return
	 */
	public BpCustMember getLoanMember(PlBidInfo plBidInfo);
	
	public  BpCustMember getLoanMember(PlBidPlan bidplan);
	
	/**
	 * 散标贷后查询
	 */
	public List<PlBidPlan> getByStateList(HttpServletRequest request,PagingBean pb);
	
	public Integer countList(HttpServletRequest request,PagingBean pb);
	/**
	 * 查询出来招标项目的原始项目
	 * @param plBidPlan
	 * @return
	 */
	public BpFundProject getBpFundProject(PlBidPlan plBidPlan);
	/**
	 * 查询出来招标项目的担保机构
	 * @param plBidPlan
	 * @return
	 */
	public BpCustMember getGuraneeMember(PlBidPlan plBidPlan);

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
	/**
	 * 工作桌面展示招标项目
	 * @param valueOf
	 * @param pb
	 * @param map
	 * @return
	 */
	public List<PlBidPlan> getPlanByStatusList(Short valueOf, PagingBean pb,Map map);
	/**
	 * 更新标的状态 通过判断 金额是否已满
	 * bidId 标id 
	 * currMoney 投标金额
	 * @return
	 * 0 满标  1超标  -1 未满标
	 */
	public int updateStatByMoney(Long bidId,BigDecimal currMoney);
	public BigDecimal bidedMoney(Long bidId);
	public List<PlBidPlan> getByProType(Map<String, String> map);
	/**
	 * 根据发标的流水号查找标
	 * @param loanOrderNo
	 * @return
	 * 2017-9-5
	 * @tzw
	 */
	public PlBidPlan getplanByLoanOrderNo(String loanOrderNo);

	/**
	 *根据bp_business_dir_pro查询标的
	 *
	 * @auther: XinFang
	 * @date: 2018/8/28 15:09
	 */
	PlBidPlan getByBdirProId(Long aLong);
}
   

