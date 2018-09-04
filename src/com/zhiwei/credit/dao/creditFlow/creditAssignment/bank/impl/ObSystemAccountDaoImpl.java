package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ObSystemAccountDaoImpl extends BaseDaoImpl<ObSystemAccount> implements ObSystemAccountDao{

	public ObSystemAccountDaoImpl() {
		super(ObSystemAccount.class);
	}
	

	//根据投资人id查看系统虚拟账户
	@Override
	public ObSystemAccount getByInvrstPersonId(Long investMentPersonId) {
		// TODO Auto-generated method stub
		String  hql ="from ObSystemAccount as bank where  1=1 and bank.investmentPersonId="+investMentPersonId;
		List<ObSystemAccount> list =this.getSession().createQuery(hql).list();
		ObSystemAccount obSystemAccount =null;
		if(list!=null&&list.size()>0){
			obSystemAccount=list.get(0);
		}
		return obSystemAccount;
	}

	@Override
	public BigDecimal getBalance(String investPersonId) {
		String  sql ="select totalMoney from ObSystemAccount where  investmentPersonId="+investPersonId;
		List list =this.getSession().createQuery(sql).list();
		BigDecimal  obSystemAccount =null;
		if(list!=null&&list.size()>0){
			obSystemAccount=(BigDecimal) list.get(0);
		}
		return obSystemAccount;
	}
	@Override
	public ObSystemAccount getByInvrstPersonIdAndType(Long investPersionId,
			Short investPsersionType) {
		String hql="from ObSystemAccount as bank where  1=1 and bank.investmentPersonId=? and bank.investPersionType=?";
		return (ObSystemAccount)findUnique(hql, new Object[]{investPersionId,investPsersionType});
	}
	@Override
	public List<ObSystemAccount> findAccountList(String investName,String accountType, HttpServletRequest request, Integer start,Integer limit) {
			String  hql ="select " +
						 "bank.id," +
						 "bank.accountName," +
						 "bank.accountNumber," +
						 "bank.investmentPersonId," +
						 "m.truename," +
						 "m.loginname," +
						 "bank.investPersionType," +
						 "bank.totalMoney," +
						 "bank.accountStatus," +
						 "bank.companyId " +
						 "from ob_system_account as bank  LEFT JOIN bp_cust_member m on bank.investmentPersonId=m.id " +
						 "where  m.thirdPayFlagId is not null ";
		
		if(investName!=null&&!"".equals(investName)&&!"null".equals(investName)){
			hql=hql+"   and m.truename  like '%"+investName+"%' or m.loginname = '"+investName+"'";
		}
		if(accountType!=null&&!"".equals(accountType)&&!"null".equals(accountType)){
			hql=hql+"   and bank.investPersionType = "+Short.valueOf(accountType);
		}
		List list=null;
		if(start==null||limit==null){
			list=this.getSession().createSQLQuery(hql).list();;
		}else{
			list=this.getSession().createSQLQuery(hql)
		     .addScalar("id", Hibernate.LONG)
		     .addScalar("accountName", Hibernate.STRING)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("investmentPersonId", Hibernate.LONG)
		     .addScalar("truename", Hibernate.STRING)
		     .addScalar("loginname", Hibernate.STRING)
		     .addScalar("investPersionType", Hibernate.SHORT)
		     .addScalar("totalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("accountStatus", Hibernate.SHORT)
		     .addScalar("companyId", Hibernate.LONG)
		     .setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class)).
			  setFirstResult(start).setMaxResults(limit).
			  list();;
		}
		return list;
	}


	@Override
	public List<ObSystemAccount> getByAccountType(String plateFromFinanceType) {
		String hql="from ObSystemAccount as bank where  1=1 and bank.accountType=?";
	List list=this.getSession().createQuery(hql).setParameter(0, plateFromFinanceType).list();
		return this.getSession().createQuery(hql).setParameter(0, plateFromFinanceType).list();
	}

	 /**
     * 获取系统账户基本信息和第三方支付信息新的方法
     * @param request
     * @return
     */
	@Override
	public List<ObSystemAccount> getNewSystemAccountList(HttpServletRequest request, Integer start,Integer limit) {
		String sql="SELECT "+
					   "ob.id, "+
					   "ob.accountName, "+
					   "ob.accountNumber, "+
					   "ob.investmentPersonId, "+
					   "ob.investPersionType, "+
					   "ob.isException, "+
					   "ob.accountStatus, "+
					   "cust.loginname, "+
					   "bp.thirdPayFlagId as thirdPayConfigId , "+
					   "ob.totalMoney, "+
					   "IFNULL(sum(infodeal.payMoney),0.00) as freezeMoney, "+
					   "ob.totalMoney-(IFNULL(sum(infodeal.payMoney),0.00)) as availableInvestMoney, "+
					   "ob.thirdTotalMoney , "+
					   "ob.thirdFreezyMoney, "+
					   "ob.thirdAviableMoney, "+
					   "ob.checkDate "+
				   "FROM "+
						"ob_system_account AS ob "+
				   "LEFT JOIN bp_cust_member AS bp ON ( "+
					    "ob.investmentPersonId = bp.id "+
					    "AND ob.investPersionType = 0 "+
				   ") "+
				   "LEFT JOIN ob_account_deal_info as infodeal ON (" +
					    "infodeal.accountId=ob.id  " +
					    "and infodeal.dealRecordStatus=7" +
				   ") " +
				   "LEFT JOIN bp_cust_member AS cust ON ob.investmentPersonId=cust.id" +
				   " WHERE ob.investPersionType = 0 and cust.thirdPayFlagId is not null ";
	   if(request!=null){
		  //系统账户账号
		  String accountNumber =request.getParameter("accountNumber");
		  if(accountNumber!=null&&!"".equals(accountNumber)){
			  sql=sql+" and ob.accountNumber like '%"+accountNumber+"%' ";
		  }
		  String loginname =request.getParameter("loginname");
		  if(loginname!=null&&!"".equals(loginname)){
			  sql=sql+" and cust.loginname like '%"+loginname+"%' ";
		  }
		  //投资客户姓名
		  String accountName=request.getParameter("accountName");
		  if(accountName!=null&&!"".equals(accountName)){
			  sql=sql+" and ob.accountName like '%"+accountName+"%' ";
		  }
		  //第三方支付账号
		  String thirdPayFlagId=request.getParameter("thirdPayFlagId");
		  if(thirdPayFlagId!=null&&!"".equals(thirdPayFlagId)){
			  sql=sql+" and bp.thirdPayFlagId like '%"+thirdPayFlagId+"%' ";
		  }
		  //是否异常
		  String isException = request.getParameter("isException");
		  if(isException!=null&&!isException.equals("")){
			  sql=sql+" and ob.isException ="+isException;
		  }
	   }	   
	   sql=sql+ " group by ob.id";
	   System.out.println(sql);
	   List list=null;
		if(start==null||limit==null){
			list=this.getSession().createSQLQuery(sql)
			 .addScalar("id", Hibernate.LONG)
		     .addScalar("accountName", Hibernate.STRING)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("loginname", Hibernate.STRING)
		     .addScalar("investmentPersonId", Hibernate.LONG)
		     .addScalar("investPersionType", Hibernate.SHORT)
		     .addScalar("accountStatus", Hibernate.SHORT)
		     .addScalar("isException", Hibernate.INTEGER)
		     .addScalar("thirdPayConfigId", Hibernate.STRING)
		     .addScalar("totalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("checkDate", Hibernate.TIMESTAMP)
		     .setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class))
			 .list();
		}else{
			list=this.getSession().createSQLQuery(sql)
		     .addScalar("id", Hibernate.LONG)
		     .addScalar("accountName", Hibernate.STRING)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("loginname", Hibernate.STRING)
		     .addScalar("investmentPersonId", Hibernate.LONG)
		     .addScalar("investPersionType", Hibernate.SHORT)
		     .addScalar("accountStatus", Hibernate.SHORT)
		     .addScalar("isException", Hibernate.INTEGER)
		     .addScalar("thirdPayConfigId", Hibernate.STRING)
		     .addScalar("totalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("checkDate", Hibernate.TIMESTAMP)
		     .setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class)).
			  setFirstResult(start).setMaxResults(limit).
			  list();
		}
		return list;
	}


	@Override
	public List<Object> getNewSystemAccountList() {
		String sql="SELECT "+
					   "ob.id, "+
					   "ob.accountName, "+
					   "ob.accountNumber, "+
					   "ob.investmentPersonId, "+
					   "ob.investPersionType, "+
					   "ob.accountStatus, "+
					   "bp.thirdPayFlagId as thirdPayConfigId , "+
					   "ob.totalMoney, "+
					   "IFNULL(sum(infodeal.payMoney),0.00) as freezeMoney, "+
					   "ob.totalMoney-(IFNULL(sum(infodeal.payMoney),0.00)) as availableInvestMoney, "+
					   "ob.thirdTotalMoney , "+
					   "ob.thirdFreezyMoney, "+
					   "ob.thirdAviableMoney, "+
					   "ob.checkDate "+
				   "FROM "+
						"ob_system_account AS ob "+
				   "LEFT JOIN bp_cust_member AS bp ON ( "+
					    "ob.investmentPersonId = bp.id "+
					    "AND ob.investPersionType = 0 "+
				   ") "+
				   "LEFT JOIN ob_account_deal_info as infodeal ON (" +
					    "infodeal.accountId=ob.id  " +
					    "and infodeal.dealRecordStatus=7" +
				   ") " +
				   "WHERE 1=1  and bp.thirdPayFlagId is not null ";
		   
		sql=sql+ " group by ob.id";
		System.out.println("所有="+sql);
		List<Object> list=this.getSession().createSQLQuery(sql)
		.addScalar("id", Hibernate.LONG)
		.addScalar("accountName", Hibernate.STRING)
		.addScalar("accountNumber", Hibernate.STRING)
		.addScalar("investmentPersonId", Hibernate.LONG)
		.addScalar("investPersionType", Hibernate.SHORT)
		.addScalar("accountStatus", Hibernate.SHORT)
		.addScalar("thirdPayConfigId", Hibernate.STRING)
		.addScalar("totalMoney", Hibernate.BIG_DECIMAL)
		.addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
		.addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
		.addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
		.addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
		.addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
		.addScalar("checkDate", Hibernate.TIMESTAMP)
		.setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class))
		.list();
		return list;
	}


	@Override
	public List cooperationAccountList(Map<String, String> map) {
		String sql = " SELECT " +
					" o.id, " +
					" o.accountName, " +
					" o.accountNumber, " +
					" m.truename,"+
					" o.totalMoney, " +
					" b.offlineCustType " +
					" FROM " +
					" ob_system_account o " +
					" LEFT JOIN bp_cust_member m on o.investmentPersonId=m.id"+
					" LEFT JOIN bp_cust_relation AS b ON o.investmentPersonId = b.p2pCustId " +
					" WHERE " +
					" b.offlineCustType LIKE '%cooperation%' " +
					" AND o.investPersionType = 0 " ;
		if(map.get("accountName")!=null&&!"".equals(map.get("accountName"))){
			sql += " and m.truename like  '%"+map.get("accountName")+"%' ";
		}
		if(map.get("offlineCustType")!=null&&!"".equals(map.get("offlineCustType"))){
			sql += " and b.offlineCustType = '"+map.get("offlineCustType")+"' ";
		}
		//System.out.println("--->"+sql.toString());
		List<Object> list=this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG)
				.addScalar("accountName", Hibernate.STRING)
				.addScalar("truename", Hibernate.STRING)
				.addScalar("accountNumber", Hibernate.STRING)
				.addScalar("totalMoney", Hibernate.BIG_DECIMAL)
				.addScalar("offlineCustType", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class))
				.setFirstResult(Integer.valueOf(map.get("start")))
				.setMaxResults(Integer.valueOf(map.get("limit")))
				.list();
				return list;
	}


	@Override
	public Long cooperationAccountListCount(Map<String, String> map) {
		String sql = " SELECT " +
				" count(*) " +
				" FROM " +
				" ob_system_account o " +
				" LEFT JOIN bp_cust_member m on o.investmentPersonId=m.id"+
				" LEFT JOIN bp_cust_relation AS b ON o.investmentPersonId = b.p2pCustId " +
				" WHERE " +
				" b.offlineCustType LIKE '%cooperation%' " +
				" AND o.investPersionType = 0 " ;
		
		if(map.get("accountName")!=null&&!"".equals(map.get("accountName"))){
			sql += " and m.truename like  '%"+map.get("accountName")+"%' ";
		}
		if(map.get("offlineCustType")!=null&&!"".equals(map.get("offlineCustType"))){
			sql += " and b.offlineCustType = '"+map.get("offlineCustType")+"' ";
		}
		
		Object object = this.getSession().createSQLQuery(sql)
				.uniqueResult();
		return ((BigInteger)object).longValue();
	
}


	@Override
	public List<ObSystemAccount> findDownAccount(HttpServletRequest request,
			Integer start, Integer limit) {
		String  hql ="select " +
		 "bank.id," +
		 "bank.accountName," +
		 "bank.accountNumber," +
		 "bank.investmentPersonId," +
		 "bank.investPersonName," +
		 "bank.investPersionType," +
		 "bank.totalMoney," +
		 "bank.accountStatus," +
		 "p.investName as truename,"+
		 "bank.companyId " +
		 "from ob_system_account as bank " +
		 "LEFT JOIN cs_investmentperson as p ON p.investId=bank.investmentPersonId "+
		 "where  1=1 and bank.investPersionType = 1";

		if(null !=request){
			Object ids=request.getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(request,ids);
			String companyId= map.get("companyId");
			String userIds= map.get("userId");
			String shopId = map.get("shopId");
			
			if(null!=userIds && !"".equals(userIds)){
				hql=hql+" and  fn_check_repeat(p.belongedId,'"+userIds+"') = 1 ";
			}
			
			//按门店分离数据
			if(null !=shopId && !"".equals(shopId)){
				hql=hql+" and p.shopId="+shopId+" or p.belongedShopId in("+shopId+")";
			}
			
			if(null!=companyId &&!"".equals(companyId)){
				hql=hql+" and p.companyId in("+companyId+") ";
			}
			
			String investName=request.getParameter("investPersonName");
			if(null !=investName && !"".equals(investName)){
				hql=hql+"   and p.investName like '%"+investName+"%'";
			}
		}
		List list=null;
		System.out.println("-->"+hql);
		if(start==null||limit==null){
			list=this.getSession().createSQLQuery(hql)
			.addScalar("id", Hibernate.LONG)
			.addScalar("accountName", Hibernate.STRING)
			.addScalar("accountNumber", Hibernate.STRING)
			.addScalar("investmentPersonId", Hibernate.LONG)
			.addScalar("investPersonName", Hibernate.STRING)
			.addScalar("truename", Hibernate.STRING)
			.addScalar("investPersionType", Hibernate.SHORT)
			.addScalar("totalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("accountStatus", Hibernate.SHORT)
			.addScalar("companyId", Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class))
			.list();
		}else{
			list=this.getSession().createSQLQuery(hql)
			.addScalar("id", Hibernate.LONG)
			.addScalar("accountName", Hibernate.STRING)
			.addScalar("accountNumber", Hibernate.STRING)
			.addScalar("investmentPersonId", Hibernate.LONG)
			.addScalar("investPersonName", Hibernate.STRING)
			.addScalar("truename", Hibernate.STRING)
			.addScalar("investPersionType", Hibernate.SHORT)
			.addScalar("totalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("accountStatus", Hibernate.SHORT)
			.addScalar("companyId", Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class)).
			setFirstResult(start).setMaxResults(limit).
			list();;
		}
			return list;
		}
	
	@Override
	public List<ObSystemAccount> rechargeReconciliationList(HttpServletRequest request, Integer start,Integer limit) {
		String sql="SELECT "+
					   "ob.id, "+
					   "ob.accountName, "+
					   "bp.telphone, "+
					   "infodeal.createDate," +
					   "ob.accountNumber, "+
					   "ob.investmentPersonId, "+
					   "ob.investPersionType, "+
					   "ob.accountStatus, "+
					   "bp.thirdPayFlagId as thirdPayConfigId , "+
					   "ob.totalMoney, "+
					   "IFNULL(sum(infodeal.payMoney),0.00) as freezeMoney, "+
					   "ob.totalMoney-(IFNULL(sum(infodeal.payMoney),0.00)) as availableInvestMoney, "+
					   "ob.thirdTotalMoney , "+
					   "ob.thirdFreezyMoney, "+
					   "ob.thirdAviableMoney, "+
					   "ob.checkDate "+
				   "FROM "+
						"ob_system_account AS ob "+
				   "LEFT JOIN bp_cust_member AS bp ON ( "+
					    "ob.investmentPersonId = bp.id "+
					    "AND ob.investPersionType = 0 "+
				   ") "+
				   "LEFT JOIN ob_account_deal_info as infodeal ON (" +
					    "infodeal.accountId=ob.id" +
					 
				   ") " +
				   "WHERE 1=1 ";
	   if(request!=null){
		  //系统账户账号
		  String accountNumber =request.getParameter("accountNumber");
		  if(accountNumber!=null&&!"".equals(accountNumber)){
			  sql=sql+" and ob.accountNumber like '%"+accountNumber+"%' ";
		  }
		  //投资客户姓名
		  String accountName=request.getParameter("accountName");
		  if(accountName!=null&&!"".equals(accountName)){
			  sql=sql+" and ob.accountName like '%"+accountName+"%' ";
		  }
		  //第三方支付账号
		  String thirdPayFlagId=request.getParameter("thirdPayFlagId");
		  if(thirdPayFlagId!=null&&!"".equals(thirdPayFlagId)){
			  sql=sql+" and bp.thirdPayFlagId like '%"+thirdPayFlagId+"%' ";
		  }
	   }	   
	   sql=sql+ " group by ob.id";
	   List list=null;
		if(start==null||limit==null){
			list=this.getSession().createSQLQuery(sql)
			 .addScalar("id", Hibernate.LONG)
		     .addScalar("accountName", Hibernate.STRING)
		     .addScalar("telphone",Hibernate.STRING)
		     .addScalar("createDate", Hibernate.DATE)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("investmentPersonId", Hibernate.LONG)
		     .addScalar("investPersionType", Hibernate.SHORT)
		     .addScalar("accountStatus", Hibernate.SHORT)
		     .addScalar("thirdPayConfigId", Hibernate.STRING)
		     .addScalar("totalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("checkDate", Hibernate.TIMESTAMP)
		     .setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class))
			 .list();
		}else{
			list=this.getSession().createSQLQuery(sql)
		     .addScalar("id", Hibernate.LONG)
		     .addScalar("accountName", Hibernate.STRING)
		     .addScalar("telphone",Hibernate.STRING)
		     .addScalar("createDate", Hibernate.DATE)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("investmentPersonId", Hibernate.LONG)
		     .addScalar("investPersionType", Hibernate.SHORT)
		     .addScalar("accountStatus", Hibernate.SHORT)
		     .addScalar("thirdPayConfigId", Hibernate.STRING)
		     .addScalar("totalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
		     .addScalar("checkDate", Hibernate.TIMESTAMP)
		     .setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class)).
			  setFirstResult(start).setMaxResults(limit).
			  list();
		}
		return list;
	}
	
	@Override
	public List<ObSystemAccount> standardReconciliationTransferLinst(HttpServletRequest request, Integer start, Integer limit) {
		String sql="SELECT "+
		   "ob.id, "+
		   "ob.accountName, "+
		   "bp.telphone, "+
		   "infodeal.createDate," +
		   "ob.accountNumber, "+
		   "ob.investmentPersonId, "+
		   "ob.investPersionType, "+
		   "ob.accountStatus, "+
		   "bp.thirdPayFlagId as thirdPayConfigId , "+
		   "ob.totalMoney, "+
		   "IFNULL(sum(infodeal.payMoney),0.00) as freezeMoney, "+
		   "ob.totalMoney-(IFNULL(sum(infodeal.payMoney),0.00)) as availableInvestMoney, "+
		   "ob.thirdTotalMoney , "+
		   "ob.thirdFreezyMoney, "+
		   "ob.thirdAviableMoney, "+
		   "ob.checkDate "+
	   "FROM "+
			"ob_system_account AS ob "+
	   "LEFT JOIN bp_cust_member AS bp ON ( "+
		    "ob.investmentPersonId = bp.id "+
		    "AND ob.investPersionType = 0 "+
	   ") "+
	   "LEFT JOIN ob_account_deal_info as infodeal ON (" +
		    "infodeal.accountId=ob.id" +
		 
	   ") " +
	   "WHERE 1=1 ";
		if(request!=null){
		//系统账户账号
			String accountNumber =request.getParameter("accountNumber");
			if(accountNumber!=null&&!"".equals(accountNumber)){
			sql=sql+" and ob.accountNumber like '%"+accountNumber+"%' ";
		}
		//投资客户姓名
		String accountName=request.getParameter("accountName");
		if(accountName!=null&&!"".equals(accountName)){
		    sql=sql+" and ob.accountName like '%"+accountName+"%' ";
		}
		//第三方支付账号
		String thirdPayFlagId=request.getParameter("thirdPayFlagId");
		if(thirdPayFlagId!=null&&!"".equals(thirdPayFlagId)){
			sql=sql+" and bp.thirdPayFlagId like '%"+thirdPayFlagId+"%' ";
		}
		}	   
		sql=sql+ " group by ob.id";
		List list=null;
		if(start==null||limit==null){
			list=this.getSession().createSQLQuery(sql)
			.addScalar("id", Hibernate.LONG)
			.addScalar("accountName", Hibernate.STRING)
			.addScalar("telphone",Hibernate.STRING)
			.addScalar("createDate", Hibernate.DATE)
			.addScalar("accountNumber", Hibernate.STRING)
			.addScalar("investmentPersonId", Hibernate.LONG)
			.addScalar("investPersionType", Hibernate.SHORT)
			.addScalar("accountStatus", Hibernate.SHORT)
			.addScalar("thirdPayConfigId", Hibernate.STRING)
			.addScalar("totalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
			.addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
			.addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
			.addScalar("checkDate", Hibernate.TIMESTAMP)
			.setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class))
			.list();
		}else{
			list=this.getSession().createSQLQuery(sql)
			.addScalar("id", Hibernate.LONG)
			.addScalar("accountName", Hibernate.STRING)
			.addScalar("telphone",Hibernate.STRING)
			.addScalar("createDate", Hibernate.DATE)
			.addScalar("accountNumber", Hibernate.STRING)
			.addScalar("investmentPersonId", Hibernate.LONG)
			.addScalar("investPersionType", Hibernate.SHORT)
			.addScalar("accountStatus", Hibernate.SHORT)
			.addScalar("thirdPayConfigId", Hibernate.STRING)
			.addScalar("totalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("freezeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("availableInvestMoney", Hibernate.BIG_DECIMAL)
			.addScalar("thirdTotalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("thirdFreezyMoney", Hibernate.BIG_DECIMAL)
			.addScalar("thirdAviableMoney", Hibernate.BIG_DECIMAL)
			.addScalar("checkDate", Hibernate.TIMESTAMP)
			.setResultTransformer(Transformers.aliasToBean(ObSystemAccount.class)).
			setFirstResult(start).setMaxResults(limit).
			list();
		}
		return null;
	}

	@Override
	public List<Object> getSystemAccountMoneyList(String accountId) {
		String sql = "SELECT account.totalMoney AS totalMoney, " +
				"		sum( case  when deal.dealRecordStatus=7 then deal.payMoney else 0 END ) as freezeMoney," +
				"		account.totalMoney - sum( case  when deal.dealRecordStatus=7 then deal.payMoney else 0 END ) AS availableInvestMoney FROM " +
				"		ob_system_account AS account LEFT JOIN 	ob_account_deal_info AS deal ON deal.accountId = account.id " +
				"		WHERE deal.accountId =?";
		List<Object> list = this.getSession().createSQLQuery(sql).setParameter(0, accountId).list();
		return list;
	}

}