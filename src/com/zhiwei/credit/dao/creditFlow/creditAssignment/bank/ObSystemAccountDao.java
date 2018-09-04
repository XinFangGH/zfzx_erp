package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;

/**
 * 
 * @author 
 *
 */
public interface ObSystemAccountDao extends BaseDao<ObSystemAccount>{

	//根据投资人id查看系统虚拟账户
	public ObSystemAccount getByInvrstPersonId(Long investMentPersonId);

	/**查询投资人账户剩余余额*/
	public BigDecimal getBalance(String investPersonId);

	public ObSystemAccount getByInvrstPersonIdAndType(Long investPersionId,
			Short investPsersionType);

	public List<ObSystemAccount> findAccountList(String investName,
			String accountType, HttpServletRequest request, Integer start,
			Integer limit);
    /**
     * 根据账户类型获取账户信息
     */
	public List<ObSystemAccount> getByAccountType(String plateFromFinanceType);
	 /**
     * 获取系统账户基本信息和第三方支付信息新的方法
     * @param request
     * @return
     */
	public List<ObSystemAccount> getNewSystemAccountList(
			HttpServletRequest request, Integer start,Integer limit);

	public List<Object> getNewSystemAccountList();

	/**
	 * 合作机构资金账户查询--数据
	 * @param map
	 */
	public List cooperationAccountList(Map<String, String> map);

	/**
	 * 合作机构资金账户查询--总记录数
	 * @param map
	 * @return
	 */
	public Long cooperationAccountListCount(Map<String, String> map);
	public List<ObSystemAccount> findDownAccount(HttpServletRequest request,
			Integer start, Integer limit);
	/**
	 * 充值对账
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ObSystemAccount> rechargeReconciliationList(HttpServletRequest request, Integer start,Integer limit);
	/**
	 * 标的转账
	 */
	List<ObSystemAccount> standardReconciliationTransferLinst(HttpServletRequest request, Integer start,Integer limit);
	public List<Object> getSystemAccountMoneyList(String accountId);
}