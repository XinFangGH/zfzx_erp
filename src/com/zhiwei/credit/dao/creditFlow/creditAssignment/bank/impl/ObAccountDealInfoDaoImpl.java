package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObAccountDealInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormStatisticsDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ObAccountDealInfoDaoImpl extends BaseDaoImpl<ObAccountDealInfo> implements ObAccountDealInfoDao{

	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	public ObAccountDealInfoDaoImpl() {
		super(ObAccountDealInfo.class);
	}
	@Override
	public List<ObAccountDealInfo> getDealList(String investPersonName,
			String transferDate,String flag) {
		// TODO Auto-generated method stub
		try{
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
			String  hql="from ObAccountDealInfo as dealInfo where 1=1 ";
			if(investPersonName!=null&&!"".equals(investPersonName)&&!"null".equals(investPersonName)){
				hql=hql+"  and dealInfo.investPersonName like '%"+investPersonName+"%'";
			} 
			if(transferDate!=null&&!"".equals(transferDate)&&!"null".equals(transferDate)){
				hql=hql+"  and dealInfo.transferDate >=" +format1.parse(transferDate);
			}
			if("2".equals(flag)){
				hql=hql+" and dealInfo.transferType='2'";
			}else if("1".equals(flag)){
				hql=hql+" and dealInfo.transferType='1'";
			}
			hql =hql+" order by transferDate desc,createDate desc ,id desc";
			return this.getSession().createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	//查询充值审批确认页面
	@Override
	public List<ObAccountDealInfo> getRechargeDealList(String investPersonName,
			String seniorValidationRechargeStatus,
			String rechargeConfirmStatus, String flag,String rechargeLevel) {
		// TODO Auto-generated method stub
		try{
			String  hql="from ObAccountDealInfo as dealInfo where 1=1 ";
			if(investPersonName!=null&&!"".equals(investPersonName)&&!"null".equals(investPersonName)){
				hql=hql+"  and dealInfo.investPersonName like '%"+investPersonName+"%'";
			}
			if("3".equals(rechargeLevel)){
				hql =hql+"  and dealInfo.rechargeConfirmStatus =1 ";
			}else{
				hql =hql+"  and dealInfo.rechargeConfirmStatus =0 ";
			}
			if("2".equals(flag)){//用来确认是充值还是取现
				hql=hql+" and dealInfo.transferType='2' ";
			}else if("1".equals(flag)){
				hql=hql+" and dealInfo.transferType='1'";
			}
			hql =hql+" order by dealInfo.transferDate desc,dealInfo.createDate desc ,dealInfo.id desc";
			return this.getSession().createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getCreateNameByCreateId(Long createId) {
		String hql="select a.fullname as username from AppUser as a where a.userId="+createId;
		List list=this.getSession().createQuery(hql).list();
		return list.isEmpty()?"":list.get(0).toString();
	}
	
	
	//根据投资人id  投资人债权id  以及账户交易类型
	@Override
	public ObAccountDealInfo getDealInfo(Long investMentPersonId, Long id,
			String flag) {
		// TODO Auto-generated method stub
		ObAccountDealInfo info =null;
		String  hql =" from ObAccountDealInfo as dealInfo where dealInfo.investPersonId=? and dealInfo.investObligationInfoId =? and dealInfo.transferType =? order by dealInfo.transferDate desc,dealInfo.createDate desc ,dealInfo.id desc ";
		List<ObAccountDealInfo> list=this.getSession().createQuery(hql).setParameter(0, Long.valueOf(investMentPersonId)).setParameter(1,Long.valueOf(id)).setParameter(2, flag).list();
		if(list!=null&&list.size()>0){
			info=list.get(0);
		}
		return info;
	}
	@Override
	public List<ObAccountDealInfo> queyAccountInfoRecord(Long accountId,
			String transferType, Short dealRecordStatus,
			Short enchashmentSatus, HttpServletRequest request,Integer start, Integer limit) {
		try{
			String sql ="select " +
						"ob.id," +
						"ob.accountId," +
						"ob.incomMoney," +
					    "ob.payMoney," +
					    "ob.currentMoney," +
					    "ob.transferType," +
					    "ob.shopId,"+
					    "ob.shopName," +
					    "ob.dealType," +
					    "ob.transferDate," +
					    "ob.investPersonId," +
					    "ob.investPersonType," +
					    "ob.investPersonName," +
					    "ob.rechargeLevel," +
					    "ob.dealRecordStatus,"+
					    "ob.recordStatus,"+
					    "ob.runId, " +
					    "ob.bankId, "+
					    "p.fullname as createName"+
					    " from ob_account_deal_info as ob LEFT JOIN ob_system_account as account on ob.accountId = account.id left join app_user as p on (ob.createId = p.userId) where 1=1 and account.investPersionType =1 ";
			if(dealRecordStatus!=null){
				if(dealRecordStatus.compareTo(Short.valueOf("23"))==0){
					sql=sql+" and ob.dealRecordStatus in(2,3)";
				}else{
					sql=sql+" and ob.dealRecordStatus="+dealRecordStatus;
				}
			}
			if(enchashmentSatus!=null){//取现审批状态
				if(enchashmentSatus.equals(Short.valueOf("5"))){
					sql=sql+" and (ob.recordStatus="+enchashmentSatus +" or ob.recordStatus is null) ";
				}
				sql=sql+" and ob.recordStatus="+enchashmentSatus ;
				
			}
			if(transferType!=null){
				sql=sql+" and ob.transferType='"+transferType+"'";

				if(transferType.equals(ObAccountDealInfo.T_RECHARGE)){//充值查询数据有特殊要求
					//if(dealRecordStatus!=null&&dealRecordStatus.equals(ObAccountDealInfo.DEAL_STATUS_1)){//只有线下客户充值才会进入一次审核
						sql=sql+" and ob.investPersonType=1";
					//}
				}else if(transferType.equals(ObAccountDealInfo.T_ENCHASHMENT)){//取现数据有要求
					if(AppUtil.getThirdPayType().equals("0")){//托管账户取现审核
						if(dealRecordStatus!=null&&(dealRecordStatus.equals("2")||dealRecordStatus.equals("1")||dealRecordStatus.equals("4")||dealRecordStatus.equals("5"))){
							sql=sql+" and ob.investPersonType=1";
						}
					}

				}
			}
			if(accountId!=null){
				sql=sql+" and ob.accountId ="+accountId;
			}
			String investPersonName =request.getParameter("investPersonName");
			if(investPersonName!=null && !"".endsWith(investPersonName)){
				sql=sql+" and ob.investPersonName like'%"+investPersonName+"%'";
			}
			
			sql=sql+"  order by ob.transferDate desc,ob.createDate desc ,ob.id desc ";
			System.out.println("查询的数据"+sql);
			List list=null;
			if(start==null || limit ==null){
				list =this.getSession().createSQLQuery(sql).
				 addScalar("id",Hibernate.LONG).
				  addScalar("accountId",Hibernate.LONG).
				  addScalar("incomMoney",Hibernate.BIG_DECIMAL).
				  addScalar("payMoney",Hibernate.BIG_DECIMAL).
				  addScalar("currentMoney",Hibernate.BIG_DECIMAL).
				  addScalar("transferType",Hibernate.STRING).
				  addScalar("shopId",Hibernate.LONG).
				  addScalar("shopName",Hibernate.STRING).
				  addScalar("dealType",Hibernate.SHORT).
				  addScalar("transferDate",Hibernate.DATE).
				  addScalar("investPersonId",Hibernate.LONG).
				  addScalar("investPersonType",Hibernate.SHORT).
				  addScalar("investPersonName",Hibernate.STRING).
				  addScalar("rechargeLevel",Hibernate.SHORT).
				  addScalar("dealRecordStatus",Hibernate.SHORT).
				  addScalar("recordStatus",Hibernate.SHORT).
				  addScalar("runId",Hibernate.LONG).
				  addScalar("bankId",Hibernate.LONG).
				  addScalar("createName",Hibernate.STRING).
				  setResultTransformer(Transformers.aliasToBean(ObAccountDealInfo.class)).
				list();
			}else{
				list =this.getSession().createSQLQuery(sql).
				  addScalar("id",Hibernate.LONG).
				  addScalar("accountId",Hibernate.LONG).
				  addScalar("incomMoney",Hibernate.BIG_DECIMAL).
				  addScalar("payMoney",Hibernate.BIG_DECIMAL).
				  addScalar("currentMoney",Hibernate.BIG_DECIMAL).
				  addScalar("transferType",Hibernate.STRING).
				  addScalar("shopId",Hibernate.LONG).
				  addScalar("shopName",Hibernate.STRING).
				  addScalar("dealType",Hibernate.SHORT).
				  addScalar("transferDate",Hibernate.DATE).
				  addScalar("investPersonId",Hibernate.LONG).
				  addScalar("investPersonType",Hibernate.SHORT).
				  addScalar("investPersonName",Hibernate.STRING).
				  addScalar("rechargeLevel",Hibernate.SHORT).
				  addScalar("dealRecordStatus",Hibernate.SHORT).
				  addScalar("recordStatus",Hibernate.SHORT).
				  addScalar("runId",Hibernate.LONG).
				  addScalar("bankId",Hibernate.LONG).
				  addScalar("createName",Hibernate.STRING).
				  setResultTransformer(Transformers.aliasToBean(ObAccountDealInfo.class)).
				  setFirstResult(start).setMaxResults(limit).
				  list();
			}
			
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public BigDecimal prefreezMoney(Long accountId, String direction) {
		// TODO Auto-generated method stub
		String  hql =" select sum(ob.payMoney) from ObAccountDealInfo as ob where ob.dealRecordStatus=7 and ob.accountId="+accountId;
		if(direction!=null&&direction.equals(ObAccountDealInfo.T_ENCHASHMENT)){
			hql=hql+"and ob.transferType='2' ";
		}else if(direction!=null&&direction.equals(ObAccountDealInfo.T_INVEST)){
			hql=hql+" and ob.transferType='4' ";
		}
		
		hql=hql+"  order by ob.transferDate desc,ob.createDate desc ,ob.id desc ";
		return (BigDecimal) this.getSession().createQuery(hql).uniqueResult();
	}
	@Override
	public BigDecimal typeTotalMoney(Long accountId, String direction) {
		// TODO Auto-generated method stub
		String hql="";
		if(direction.equals(ObAccountDealInfo.T_RECHARGE) ||direction.equals(ObAccountDealInfo.T_PRINCIALBACK)||direction.equals(ObAccountDealInfo.T_PROFIT)){
			 hql =" select sum(ob.incomMoney) from ObAccountDealInfo as ob where ob.accountId="+accountId+" and ob.dealRecordStatus=2 and ob.transferType='"+direction+"'";
		}else{
			 hql =" select sum(ob.payMoney) from ObAccountDealInfo as ob where ob.accountId="+accountId+" and ob.dealRecordStatus=2 and ob.transferType='"+direction+"'";
		}
		
		hql=hql+"  order by ob.transferDate desc,ob.createDate desc,ob.id desc  ";
		return (BigDecimal) this.getSession().createQuery(hql).uniqueResult();
	}
	
	/**查询投资人账户总记录*/
	@Override
	public List<ObAccountDealInfo> getaccountListQuery(String accountId,
			HttpServletRequest request, Integer start, Integer limit){
			String sql ="select " +
			"ob.id," +
			"ob.accountId," +
			"ob.incomMoney," +
		    "ob.payMoney," +
		    "ob.currentMoney," +
		    "ob.transferType," +
		    "ob.shopName," +
		    "ob.dealType," +
		    "ob.transferDate," +
		    "ob.investPersonName," +
		    "ob.rechargeLevel," +
		    "ob.dealRecordStatus,"+
		    "ob.msg,"+
		    "ob.createDate," +
		    "ob.recordNumber,"+
		    "accountSetting.typeName as transferTypeName"+
		    " from ob_account_deal_info as ob  " +
		    " left join ob_systemaccount_setting as accountSetting on (ob.transferType=accountSetting.typeKey ) " +
		    " where  1=1 " ;
			List list=null;
			if(accountId!=null&&!"".equals(accountId)){
				sql=sql+ " and ob.accountId="+Long.valueOf(accountId);
				if(null!=request){
				String startDate = request.getParameter("startDate");
					if(null!=startDate&&!"".equals(startDate)){
						sql+=" and transferDate>="+"'"+startDate+" 00:00:00'";
					}
					String endDate = request.getParameter("endDate");
					if(null!=endDate&&!"".equals(endDate)){
						sql+=" and transferDate<="+"'"+endDate+" 59:59:59'";
					}
					String transferType = request.getParameter("transferType");
					if(null!=transferType&&!"".equals(transferType)){
						sql+=" and transferType="+"'"+transferType+"'";
					}
				}
				
				sql=sql+"  order by ob.transferDate desc,ob.createDate desc,ob.id desc ";
				if(start==null || limit ==null){
					list =this.getSession().createSQLQuery(sql).addScalar("id",Hibernate.LONG).
					  addScalar("accountId",Hibernate.LONG).
					  addScalar("incomMoney",Hibernate.BIG_DECIMAL).
					  addScalar("payMoney",Hibernate.BIG_DECIMAL).
					  addScalar("currentMoney",Hibernate.BIG_DECIMAL).
					  addScalar("transferType",Hibernate.STRING).
					  addScalar("shopName",Hibernate.STRING).
					  addScalar("dealType",Hibernate.SHORT).
					  addScalar("transferDate",Hibernate.DATE).
					  addScalar("investPersonName",Hibernate.STRING).
					  addScalar("rechargeLevel",Hibernate.SHORT).
					  addScalar("dealRecordStatus",Hibernate.SHORT).
					  addScalar("msg",Hibernate.STRING).
					  addScalar("createDate",Hibernate.DATE).
					  addScalar("recordNumber",Hibernate.STRING).
					  addScalar("transferTypeName",Hibernate.STRING).list();
				}else{
					list =this.getSession().createSQLQuery(sql).
					  addScalar("id",Hibernate.LONG).
					  addScalar("accountId",Hibernate.LONG).
					  addScalar("incomMoney",Hibernate.BIG_DECIMAL).
					  addScalar("payMoney",Hibernate.BIG_DECIMAL).
					  addScalar("currentMoney",Hibernate.BIG_DECIMAL).
					  addScalar("transferType",Hibernate.STRING).
					  addScalar("shopName",Hibernate.STRING).
					  addScalar("dealType",Hibernate.SHORT).
					  addScalar("transferDate",Hibernate.DATE).
					  addScalar("investPersonName",Hibernate.STRING).
					  addScalar("rechargeLevel",Hibernate.SHORT).
					  addScalar("dealRecordStatus",Hibernate.SHORT).
					  addScalar("msg",Hibernate.STRING).
					  addScalar("createDate",Hibernate.DATE).
					  addScalar("recordNumber",Hibernate.STRING).
					  addScalar("transferTypeName",Hibernate.STRING).
					  setResultTransformer(Transformers.aliasToBean(ObAccountDealInfo.class)).
					  setFirstResult(start).setMaxResults(limit).
					  list();
				}
			}
		return list;
	}
	/**
	 * 查询线上提现审核
	 */
	public List<ObAccountDealInfo> getWithdrawCheck(HttpServletRequest request, Integer start, Integer limit){
		String sql ="select " +
				"cust.loginname," +
				"cust.truename," +
				"cust.telphone," +
				"cust.cardcode," +
				"ob.payMoney," +
				"ob.id," +
				"ob.createDate," +
				"ob.recordNumber," +
				"ob.thirdPayRecordNumber," +
				"ob.dealRecordStatus, " +
				"ob.msg " +
				"from ob_account_deal_info as ob left join bp_cust_member as cust on ob.investPersonId=cust.id " +
				"where ob.transferType=2 " ;
		List list=null;
			if(null!=request){
				String dealRecordStatus = request.getParameter("dealRecordStatus");
				if(dealRecordStatus!=null&&!dealRecordStatus.equals("")){
					if(dealRecordStatus.equals("77")){
						sql+=" and ob.dealRecordStatus=7 and ob.thirdPayRecordNumber is not null ";
					}else if(dealRecordStatus.equals("7")){
						sql+=" and ob.dealRecordStatus=7 and ob.thirdPayRecordNumber is  null ";
					}else{
						sql+=" and ob.dealRecordStatus= "+dealRecordStatus;
					}
				}
				String loginname = request.getParameter("loginname");
				if(loginname!=null&&!loginname.equals("")){
					sql+=" and cust.loginname='"+loginname+"'";
				}
				String telphone = request.getParameter("telphone");
				if(telphone!=null&&!telphone.equals("")){
					sql+=" and cust.telphone='"+telphone+"'";
				}
				String cardcode = request.getParameter("cardcode");
				if(cardcode!=null&&!cardcode.equals("")){
					sql+=" and cust.cardcode  like '%"+cardcode+"%'";
				}
				String thirdPayRecordNumber = request.getParameter("thirdPayRecordNumber");
				if(thirdPayRecordNumber!=null&&!thirdPayRecordNumber.equals("")){
					sql+=" and ob.thirdPayRecordNumber  like '%"+thirdPayRecordNumber+"%'";
				}
				String truename = request.getParameter("truename");
				if(truename!=null&&!truename.equals("")){
					sql+=" and cust.truename='"+truename+"'";
				}
				String recordNumber = request.getParameter("recordNumber");
				if(recordNumber!=null&&!recordNumber.equals("")){
					sql+=" and ob.recordNumber like '%"+recordNumber+"%'";
				}
				String startDate = request.getParameter("startDate");
				if(null!=startDate&&!"".equals(startDate)){
					sql+=" and createDate>="+"'"+startDate+" 00:00:00'";
				}
				String endDate = request.getParameter("endDate");
				if(null!=endDate&&!"".equals(endDate)){
					sql+=" and createDate<="+"'"+endDate+" 59:59:59'";
				}
			}
			sql=sql+"  order by ob.id desc ";
			System.out.println(sql);
			if(start==null || limit ==null){
				list =this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).
				addScalar("loginname",Hibernate.STRING).
				addScalar("telphone",Hibernate.STRING).
				addScalar("truename",Hibernate.STRING).
				addScalar("cardcode",Hibernate.STRING).
				addScalar("thirdPayRecordNumber",Hibernate.STRING).
				addScalar("msg",Hibernate.STRING).
				addScalar("payMoney",Hibernate.BIG_DECIMAL).
				addScalar("createDate",Hibernate.TIMESTAMP).
				addScalar("recordNumber",Hibernate.STRING).
				addScalar("dealRecordStatus",Hibernate.SHORT).
				setResultTransformer(Transformers.aliasToBean(ObAccountDealInfo.class)).
				list();
			}else{
				list =this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).
				addScalar("loginname",Hibernate.STRING).
				addScalar("telphone",Hibernate.STRING).
				addScalar("truename",Hibernate.STRING).
				addScalar("cardcode",Hibernate.STRING).
				addScalar("thirdPayRecordNumber",Hibernate.STRING).
				addScalar("msg",Hibernate.STRING).
				addScalar("payMoney",Hibernate.BIG_DECIMAL).
				addScalar("createDate",Hibernate.TIMESTAMP).
				addScalar("recordNumber",Hibernate.STRING).
				addScalar("dealRecordStatus",Hibernate.SHORT).
				setResultTransformer(Transformers.aliasToBean(ObAccountDealInfo.class)).
				setFirstResult(start).setMaxResults(limit).
				list();
			}
		return list;
	}
	@Override
	public ObAccountDealInfo getDealinfo(String transferType,Long accountId, Long investPersonId,Short investPersonType,
			String recordNumber, Long accountInfoId) {
		// TODO Auto-generated method stub
		String  hql="from ObAccountDealInfo as info where info.accountId="+accountId;
		if(null!=investPersonId&&!"".equals(investPersonId)&&null!=investPersonType&&!"".equals(investPersonType)){
			hql+=" and info.investPersonId="+Long.valueOf(investPersonId)+" and info.investPersonType="+investPersonType;
		}
		if(null!=recordNumber&&!"".equals(recordNumber)){
			hql+=" and info.recordNumber='"+recordNumber+"'";
		}
		if(null!=accountInfoId){
			hql+=" and info.id="+accountInfoId;
		}
		//U计划加入费用
		if(null!=transferType && ObAccountDealInfo.T_JOIN.equals(transferType)){
			hql+=" and info.transferType="+transferType;
		}
		ObAccountDealInfo info =null;
		List<ObAccountDealInfo>  list =this.getSession().createQuery(hql).list();
		if(list!=null&&list.size()>0){
			info=list.get(0);
		}
		return info;
	}
	
	/**svn:songwj
	 * @param userId
	 * @param accountId
	 * @param direction
	 * @return
	 */
	public BigDecimal typeTotalMoney(Long userId,Long accountId, String direction) {
		String hql="";
		if(direction.equals(ObAccountDealInfo.T_RECHARGE) ||direction.equals(ObAccountDealInfo.T_PRINCIALBACK)||direction.equals(ObAccountDealInfo.T_PROFIT)){
			 hql =" select sum(ob.incomMoney) from ObAccountDealInfo as ob where ob.accountId="+accountId+" and ob.transferType='"+direction+"'";
		}else if(direction.equals(ObAccountDealInfo.T_INMONEY)){
			hql =" select sum(ob.incomMoney) from ObAccountDealInfo as ob where ob.accountId="+accountId+"  and ob.transferType='"+direction+"'";
		}else if(direction.equals(ObAccountDealInfo.T_LOANPAY)){
			hql =" select sum(ob.payMoney) from ObAccountDealInfo as ob where ob.accountId="+accountId+"  and ob.transferType='"+direction+"'";
		}else if(direction.equals(ObAccountDealInfo.T_N_REPAYMENT)){
			if(userId==null){
				return new BigDecimal(0); 
				
			}else{
				hql = " select sum(slf.notMoney) from SlFundIntent  slf where slf.projectId in (select bf.projectId from BpFundProject bf where   bf.oppositeID ="+userId+" group by bf.projectId) and (slf.fundType='serviceMoney' or slf.fundType='consultationMoney' or slf.fundType='loanInterest' or slf.fundType='principalRepayment')";
			}
			

		}else{
			 hql =" select sum(ob.payMoney) from ObAccountDealInfo as ob where ob.accountId="+accountId+"  and ob.transferType='"+direction+"'";
		}
		
//		System.out.println("hql--------------"+hql);
		return (BigDecimal) this.getSession().createQuery(hql).uniqueResult();
	}
	
	@Override
	public ObAccountDealInfo getByOrderNumber(String orderNo, String totalFee,
			String transferType, String type0) {
		String hql = "";
		if(!totalFee.equals("")&&!transferType.equals("")){
			 hql =" from ObAccountDealInfo as ob where ob.recordNumber=? and ob.investPersonType=? and ob.transferType=? ";
				return (ObAccountDealInfo) this.getSession().createQuery(hql).setParameter(0, orderNo).setParameter(1, Short.valueOf(type0)).setParameter(2, transferType).uniqueResult();
		}else{
			 hql =" from ObAccountDealInfo as ob where ob.recordNumber=?";
				return (ObAccountDealInfo) this.getSession().createQuery(hql).setParameter(0, orderNo).uniqueResult();
		}
	}
	
	/**
	 * 查出来平台每个账户的自己明细
	 */
	@Override
	public List<PlateFormStatisticsDetail> getOneTypePlateFormStaticsDatail(HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String accountType=request.getParameter("accountType");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		if(startDate==null ||endDate==null){
			startDate=sdf.format(new Date());
			endDate=sdf.format(new Date());
		}
		String sql="SELECT sum(p.incomMoney) as 'incomeMoney',sum(p.payMoney)  as 'payMoney',p.transferType as 'fundType' ,setting.typeName as fundTypeName ,'"+startDate+"'  as 'startDate','"+endDate+"' as 'endDate'  FROM `ob_account_deal_info`  as p   left join `ob_systemaccount_setting` as setting on (setting.typeKey=p.transferType) where p.accountId=(select acc.id  from ob_system_account as acc where acc.accountType='"+accountType+"')"+" and p.createDate >= '"+startDate +" 00:00:00'  and p.createDate <= '"+endDate+" 59:59:59' group by p.transferType";
		List list=null;
		if(start==null || limit ==null){
			list =this.getSession().createSQLQuery(sql).
			  addScalar("incomeMoney",Hibernate.BIG_DECIMAL).
			  addScalar("payMoney",Hibernate.BIG_DECIMAL).
			  addScalar("fundType",Hibernate.STRING).
			  addScalar("fundTypeName",Hibernate.STRING).
			  addScalar("startDate",Hibernate.DATE).
			  addScalar("endDate",Hibernate.DATE).
			  setResultTransformer(Transformers.aliasToBean(PlateFormStatisticsDetail.class)).list();
		}else{
			list =this.getSession().createSQLQuery(sql).
			  addScalar("incomeMoney",Hibernate.BIG_DECIMAL).
			  addScalar("payMoney",Hibernate.BIG_DECIMAL).
			  addScalar("fundType",Hibernate.STRING).
			  addScalar("fundTypeName",Hibernate.STRING).
			  addScalar("startDate",Hibernate.DATE).
			  addScalar("endDate",Hibernate.DATE).
			  setResultTransformer(Transformers.aliasToBean(PlateFormStatisticsDetail.class)).
			  setFirstResult(start).setMaxResults(limit).
			  list();
		}
	   return list;
	}
	@Override
	public List<ObAccountDealInfo> cooperationAccountInfoList(Map<String, String> map) {
		String sql = " SELECT " +
				" oi.id, " +
				" oi.createDate, " +
				" oi.recordNumber, " +
				" oi.transferDate, " +
				" oi.transferType, " +
				" oi.incomMoney, " +
				" oi.payMoney, " +
				" oi.currentMoney, " +
				" oi.dealRecordStatus, " +
				" oa.accountName, " +
				" m.truename,"+
				" s.typeName "+
				" FROM " +
				" ob_account_deal_info as oi " +
				" left join ob_system_account oa on oi.accountId = oa.id " +
				" LEFT JOIN bp_cust_member m ON oa.investmentPersonId = m.id"+
				" LEFT JOIN bp_cust_relation AS b ON oa.investmentPersonId = b.p2pCustId " +
				" LEFT JOIN ob_systemaccount_setting s on oi.transferType=s.typeKey "+
				" WHERE " +
				" b.offlineCustType LIKE '%cooperation%' " +
				" AND oa.investPersionType = 0 ";
				if(map.get("name")!=null&&!"".equals(map.get("name"))){
					sql += " and m.truename  like  '%"+map.get("name")+"%' ";
				}
				if(map.get("startDate")!=null&&!"".equals(map.get("startDate"))){
					sql += " and oi.transferDate >= '"+map.get("startDate")+"' ";
				}	
				if(map.get("endDate")!=null&&!"".equals(map.get("endDate"))){
					sql += " and oi.transferDate <= '"+map.get("endDate")+"' ";
				}
				if(map.get("transferType")!=null&&!"".equals(map.get("transferType"))){
					sql += " and oi.transferType = "+map.get("transferType")+" ";
				}
				List<ObAccountDealInfo> list=this.getSession().createSQLQuery(sql)
						.addScalar("id", Hibernate.LONG)
						.addScalar("createDate", Hibernate.DATE)
						.addScalar("recordNumber", Hibernate.STRING)
						.addScalar("transferDate", Hibernate.DATE)
						.addScalar("transferType", Hibernate.STRING)
						.addScalar("incomMoney", Hibernate.BIG_DECIMAL)
						.addScalar("payMoney", Hibernate.BIG_DECIMAL)
						.addScalar("currentMoney", Hibernate.BIG_DECIMAL)
						.addScalar("dealRecordStatus", Hibernate.SHORT)
						.addScalar("accountName", Hibernate.STRING)
						.addScalar("truename", Hibernate.STRING)
						.addScalar("typeName", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(ObAccountDealInfo.class))
						.setFirstResult(Integer.valueOf(map.get("start")))
						.setMaxResults(Integer.valueOf(map.get("limit")))
						.list();
				return list;
	}
	@Override
	public ObAccountDealInfo getBythirdNumber(String thirdPayRecordNumber) {
		String hql =" from ObAccountDealInfo as ob where ob.thirdPayRecordNumber=?";
			return (ObAccountDealInfo) this.getSession().createQuery(hql).setParameter(0, thirdPayRecordNumber).uniqueResult();
	}
	@Override
	public Long cooperationAccountInfoListCount(Map<String, String> map) {
		String sql = " SELECT " +
				" count(*) " +
				" FROM " +
				" ob_account_deal_info as oi " +
				" left join ob_system_account oa on oi.accountId = oa.id " +
				" LEFT JOIN bp_cust_member m ON oa.investmentPersonId = m.id"+
				" LEFT JOIN bp_cust_relation AS b ON oa.investmentPersonId = b.p2pCustId " +
				" WHERE " +
				" b.offlineCustType LIKE '%cooperation%' " +
				" AND oa.investPersionType = 0 ";
		
				if(map.get("name")!=null&&!"".equals(map.get("name"))){
					sql += " and m.truename  like  '%"+map.get("name")+"%' ";
				}
				if(map.get("startDate")!=null&&!"".equals(map.get("startDate"))){
					sql += " and oi.transferDate >= '"+map.get("startDate")+"' ";
				}	
				if(map.get("endDate")!=null&&!"".equals(map.get("endDate"))){
					sql += " and oi.transferDate <= '"+map.get("endDate")+"' ";
				}
				if(map.get("transferType")!=null&&!"".equals(map.get("transferType"))){
					sql += " and oi.transferType = "+map.get("transferType")+" ";
				}
				
				Object object = this.getSession().createSQLQuery(sql).uniqueResult();
				return ((BigInteger)object).longValue();
	
	}
	@Override
	public BigDecimal sumPersonMoney(String transferType, Long investPersonId,
			String startTime, String endTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String hql="";
		if(transferType.equals("1")){
			 hql =" select sum (ob.incomMoney) as sumMoney  from ObAccountDealInfo as ob " +
			 		" where ob.transferType=? and ob.dealRecordStatus=2 and ob.investPersonId=? " +
			 		" and transferDate >= ? and transferDate <= ?";
		}else{
			 hql =" select sum (ob.payMoney) as sumMoney  from ObAccountDealInfo as ob " +
			 		" where ob.transferType=? and  ob.dealRecordStatus=2  and ob.investPersonId=? " +
			 		" and transferDate >= ? and transferDate <= ?";
		}
		try {
			BigDecimal sumMoney = (BigDecimal)this.getSession().createQuery(hql).setParameter(0, transferType)
								.setParameter(1, investPersonId).setParameter(2, format.parse(startTime))
								.setParameter(3, format.parse(endTime)).uniqueResult();
			if(null!=sumMoney){
				return sumMoney;
			}else{
				return new BigDecimal("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public BigDecimal getAccountIdRechargeMoney(Long accoutId) {
		String sql="select sum(incomMoney) from ObAccountDealInfo where accountId=? and transferType=1";
	 	return (BigDecimal) this.getSession().createQuery(sql).setParameter(0, accoutId).uniqueResult();
	}
	@Override
	public List<ObAccountDealInfo> getAllThirdDealInfo(String day) {
		String hql ="";
		List<ObAccountDealInfo> list = null;
		if(day==null||day.equals("")){
			hql = "from ObAccountDealInfo where   dealRecordStatus=2 or dealRecordStatus=7 GROUP BY accountId";
			list = this.getSession().createQuery(hql).list();
		}else{
			hql = "select * from ob_account_deal_info where  DATE_SUB(CURDATE(), INTERVAL ?-1 DAY) <= date(createDate) and (dealRecordStatus=2 or dealRecordStatus=7) GROUP BY accountId";
			list = this.getSession().createSQLQuery(hql).addEntity(ObAccountDealInfo.class).setParameter(0, day).list();
		}
		return list;
	}
	
	
	@Override
	public BigDecimal sumMoney(String transferType, Long[] investPersonIds,
			String startTime, String endTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String hql="";
		if(transferType.equals("1")){
			 hql =" select sum (ob.incomMoney) as sumMoney  from ObAccountDealInfo as ob " +
			 		" where ob.transferType = :transferType and ob.dealRecordStatus=2 and ob.investPersonId in (:investPersonIds) " +
			 		" and transferDate >= :startTime and transferDate <= :endTime";
		}else{
			 hql =" select sum (ob.payMoney) as sumMoney  from ObAccountDealInfo as ob " +
			 		" where ob.transferType= :transferType and  ob.dealRecordStatus=2  and ob.investPersonId in (:investPersonIds) " +
			 		" and transferDate >= :startTime and transferDate <= :endTime";
		}
		try {
			BigDecimal sumMoney = (BigDecimal)this.getSession().createQuery(hql).setParameter("transferType", transferType)
								.setParameterList("investPersonIds",investPersonIds).setParameter("startTime", format.parse(startTime))
								.setParameter("endTime", format.parse(endTime)).uniqueResult();
			if(null!=sumMoney){
				return sumMoney;
			}else{
				return new BigDecimal("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<ObAccountDealInfo> getListByTransFerTypeAndState(
			String transferType, String dealRecordStatus,String searchHours) {
		StringBuffer hql = new StringBuffer("SELECT * from ob_account_deal_info where 1=1 ");
			if (StringUtils.isNotEmpty(dealRecordStatus)) {
				hql.append(" AND dealRecordStatus= ").append(dealRecordStatus);
			}
			if (StringUtils.isNotEmpty(transferType)) {
				hql.append(" AND transferType = ").append(transferType);
			}
			if (StringUtils.isNotEmpty(searchHours)) {
				hql.append(" AND createDate > NOW()-INTERVAL ").append(searchHours).append(" HOUR ");
			}

			List<ObAccountDealInfo> list = this.getSession().createSQLQuery(hql.toString()).list();
		return list;
	}

	@Override
	public List<ThirdPayRecord> getThirdPayRecord(String bpName, String startDate, String transferType) {
		StringBuffer sb = new StringBuffer("SELECT t.userId,t.loginName,b.truename,t.interfaceType,t.interfaceName,t.requestTime,t.recordNumber,t.`code`,t.codeMsg,t.dataPackage from third_pay_record t LEFT JOIN bp_cust_member b on b.id = t.userId");
				sb.append(" where  b.truename='"+bpName+"'")
				.append(" or b.telphone ='"+bpName+"'")
				.append(" or t.loginName ='"+bpName+"'");
		if (StringUtils.isNotBlank(startDate)){
			sb.append(" and t.requestTime between '"+startDate+" 00:00:00' and '"+startDate+" 23:59:59'");
		}
		if (StringUtils.isNotBlank(transferType)){
			sb.append(" and t.interfaceType ='"+transferType+"'");
		}

		sb.append(" order by t.id desc");

		List list = getSession().createSQLQuery(sb.toString()).addScalar("userId", Hibernate.LONG).
				addScalar("loginName", Hibernate.STRING).
				addScalar("truename", Hibernate.STRING).
				addScalar("interfaceType", Hibernate.STRING).
				addScalar("interfaceName", Hibernate.STRING).
				addScalar("requestTime", Hibernate.DATE).
				addScalar("recordNumber", Hibernate.STRING).
				addScalar("code", Hibernate.STRING).
				addScalar("codeMsg", Hibernate.STRING).
				addScalar("dataPackage", Hibernate.STRING).
				setResultTransformer(Transformers.aliasToBean(ThirdPayRecord.class)).list();

		return list;
	}


}