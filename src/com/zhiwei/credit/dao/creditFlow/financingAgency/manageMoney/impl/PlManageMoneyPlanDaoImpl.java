package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlManageMoneyPlanDaoImpl extends BaseDaoImpl<PlManageMoneyPlan> implements PlManageMoneyPlanDao{

	public PlManageMoneyPlanDaoImpl() {
		super(PlManageMoneyPlan.class);
	}

	@Override
	public PlManageMoneyPlan getByRequestNo(String requestNo) {
		// TODO Auto-generated method stub
		String hql="from PlManageMoneyPlan as p where p.requestNo=?";
		return (PlManageMoneyPlan) this.getSession().createQuery(hql).setParameter(0, requestNo).uniqueResult();
	}

	@Override
	public List<PlManageMoneyPlan> getAllPlbuyInfo(PagingBean pb,
			Map<String, String> map) {
		StringBuffer sql = new StringBuffer("SELECT" +
				" plmp.mmplanId," +
				"plmp.mmNumber," +
				"plmp.mmName," +
				"plmp.startinInterestTime," +
				"plmoa.intentDate," +
				"sum(case when plmoa.fundType='loanInterest' then plmoa.incomeMoney else 0 end ) as sumIncomeMoney," +
				"plmoa.factDate" +
				" from pl_mm_order_assigninterest AS plmoa" +
				" LEFT JOIN pl_managemoney_plan AS plmp ON plmoa.mmplanId=plmp.mmplanId where 1=1");
		
		String keystr=map.get("keystr");
		if(null !=keystr && !"".equals(keystr) && !keystr.equals("undefined")){
			sql.append(" and plmp.keystr='"+keystr+"'"); 
		}else{
			return null;
		}
		String state=map.get("state");
		if(null !=state && !"".equals(state) && !state.equals("undefined")){
			sql.append(" AND plmp.state in("+state+")"); 
		}else{
			return null;
		}
		
		String mmNumber=map.get("mmNumber");
		if(null !=mmNumber && !"".equals(mmNumber)){
			sql.append(" and plmp.mmNumber like '%"+mmNumber+"%'");
		}
		
		String mmName=map.get("mmName");
		if(null !=mmName && !"".equals(mmName)){
			sql.append(" and plmp.mmName like'%"+mmName+"%'");
		}
		
		String intentDate_GE=map.get("intentDate_GE");
		if(null !=intentDate_GE && !"".equals(intentDate_GE)){
			sql.append(" and plmoa.intentDate >='"+intentDate_GE+"'");
		}
		
		String intentDate_LE=map.get("intentDate_LE");
		if(null !=intentDate_LE && !"".equals(intentDate_LE)){
			sql.append(" and plmoa.intentDate <='"+intentDate_LE+" 23:59:59'");
		}
		
		String isPayExperience=map.get("isPayExperience");
		if(null !=isPayExperience && !"".equals(isPayExperience)){
			if(isPayExperience.equals("all")){//查询全部
				
			}else if(isPayExperience.equals("notPayExperience")){//未派息
				sql.append(" and plmoa.factDate is null");
			}else if(isPayExperience.equals("payedExperience")){//已派息
				sql.append(" and plmoa.factDate is not null");
			}else if(isPayExperience.equals("none")){//一个都不查
				sql.append(" and plmp.keystr='KKKKKKKK'");
			}
		}else{//默认查询未派息
			sql.append(" and plmoa.factDate is null");
		}
		
		sql.append(" group  by plmp.mmplanId ");
		System.out.println(sql);
		if(null==pb){
			return this.getSession().createSQLQuery(sql.toString())
			.addScalar("mmplanId",Hibernate.LONG)
			.addScalar("mmNumber",Hibernate.STRING)
			.addScalar("mmName",Hibernate.STRING)
			.addScalar("startinInterestTime",Hibernate.DATE)
			.addScalar("intentDate",Hibernate.DATE)
			.addScalar("sumIncomeMoney",Hibernate.BIG_DECIMAL)
			.addScalar("factDate",Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(PlManageMoneyPlan.class)).list();
		}else{
			return this.getSession().createSQLQuery(sql.toString())
			.addScalar("mmplanId",Hibernate.LONG)
			.addScalar("mmNumber",Hibernate.STRING)
			.addScalar("mmName",Hibernate.STRING)
			.addScalar("startinInterestTime",Hibernate.DATE)
			.addScalar("intentDate",Hibernate.DATE)
			.addScalar("sumIncomeMoney",Hibernate.BIG_DECIMAL)
			.addScalar("factDate",Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(PlManageMoneyPlan.class))
			       .setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}
	}

	@Override
	public List<PlManageMoneyPlan> getByStateAndCondition(Map<String, String> map) {
		String state=map.get("state");
		String startinInterestCondition=map.get("startinInterestCondition");
		String sql="select * from pl_managemoney_plan as p where p.state in ("+state+") and p.startinInterestCondition in ("+startinInterestCondition+")";
		return this.getSession().createSQLQuery(sql).addEntity(PlManageMoneyPlan.class)
		.list();
	}

	@Override
	public BigDecimal findBidMoneyByAccount(String account,String keystr) {
		String childType=keystr;
		if("mmplanOr".equals(keystr)){
			childType="mmplan";
		}else if("UPlanOr".equals(keystr)){
			childType="UPlan";
		}
		/*String hql="select sum(investedMoney) from pl_managemoney_plan where moneyReceiver=? and state in(0,1) and keystr='"+childType+"'";*/
		String hql="SELECT SUM(sumMoney) from pl_managemoney_plan where state in(0,1,2,4,7) and  moneyReceiver=? and keystr=?";
		return (BigDecimal)this.getSession().createSQLQuery(hql).setParameter(0,account).setParameter(1, childType).uniqueResult();
	}

	@Override
	public BigDecimal findMatchingMoneyByAccount(String account, String keystr) {
		String childType=keystr;
		if("mmplanOr".equals(keystr)){
			childType="mmplan";
		}else if("UPlanOr".equals(keystr)){
			childType="UPlan";
		}
		String hql="SELECT SUM(buyinfo.buyMoney)-SUM(buyinfo.currentMatchingMoney) from"
					+" pl_managemoneyplan_buyinfo as buyinfo where buyinfo.mmplanId in"
					+" (SELECT SUM(sumMoney) from pl_managemoney_plan where state in(0,1,2,4,7) "
					+" and  moneyReceiver=? and keystr=?) and buyinfo.state in(1,2,7)";
		return (BigDecimal)this.getSession().createSQLQuery(hql).setParameter(0,account).setParameter(1, childType).uniqueResult();
	}
	
}