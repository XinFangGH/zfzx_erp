package com.zhiwei.credit.dao.creditFlow.finance.compensatory.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.compensatory.PlBidCompensatoryDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlBidCompensatoryDaoImpl extends BaseDaoImpl<PlBidCompensatory> implements PlBidCompensatoryDao{

	public PlBidCompensatoryDaoImpl() {
		super(PlBidCompensatory.class);
	}

	/* 
	 * 查询代偿台账列表
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.creditFlow.finance.compensatory.PlBidCompensatoryDao#compensatoryList(com.zhiwei.core.web.paging.PagingBean, java.util.Map)
	 */
	@Override
	public List<PlBidCompensatory> compensatoryList(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		
		String sql="select p.id as id," +
				   " p.loanerp2pId as loanerp2pId, " +
				   " p.custmerId  as custmerId, " +
				   " p.planId as planId, " +
				   " p.compensatoryMoney as compensatoryMoney, " +
				   " p.compensatoryType as compensatoryType, " +
				   " p.compensatoryName as compensatoryName, " +
				   " p.compensatoryP2PId as compensatoryP2PId, " +
				   " p.payintentPeriod as payintentPeriod, " +
				   " p.requestNo as requestNo, " +
				   " p.compensatoryDate as compensatoryDate, " +
				   " p.compensatoryDays as compensatoryDays, " +
				   " IFNULL(p.punishRate,0) as punishRate, " +
				   " IFNULL(p.plateMoney,0) as plateMoney, " +
				   " IFNULL(p.punishMoney,0) as punishMoney, " +
				   " IFNULL(p.backPunishMoney,0) as backPunishMoney, " +
				   " IFNULL(p.backCompensatoryMoney,0) as backCompensatoryMoney, " +
				   " p.backDate as backDate, " +
				   " p.backStatus as backStatus, " +
				   " (p.compensatoryMoney+IFNULL(p.punishMoney,0)-IFNULL(p.backPunishMoney,0)-IFNULL(p.backCompensatoryMoney,0)-IFNULL(p.plateMoney,0)) as totalMoney, " +
				   " IFNULL(IFNULL(cse.enterprisename,csp.`name`),IFNULL(cscoe.`name`,cscop.`name`)) as custmerName, " +
				   " plan.bidProName as bidPlanname, " +
				   " plan.bidProNumber as bidPlanNumber " +
				   "from pl_bid_compensatory as p " +
				   "left join pl_bid_plan as plan on (p.planId=plan.bidId) " +
				   "left join cs_enterprise as cse on (cse.id=p.custmerId and p.custmerType='b_loan')" +
				   "left join cs_person as csp on (csp.id=p.custmerId and p.custmerType='p_loan') " +
				   "left join cs_cooperation_enterprise as cscoe on (cscoe.id=p.custmerId and p.custmerType='e_cooperation') " +
				   "left join cs_cooperation_person as cscop on (cscop.id=p.custmerId and p.custmerType='p_cooperation') where 1=1 ";
		if(map!=null){
			//招标项目代偿类型
			String planType=map.get("planType");
			if(null !=planType&&planType.equals("P_Dir")){
				sql=sql+" and plan.proType ='P_Dir'";
			}else if(null !=planType&&planType.equals("B_Dir")){
				sql=sql+" and plan.proType ='B_Dir'";
			}else if(null !=planType&&planType.equals("P_Or")){
				sql=sql+" and plan.proType ='P_Or'";
			}else if(null !=planType&&planType.equals("B_Or")){
				sql=sql+" and plan.proType ='B_Or'";
			}
			String status=map.get("status");
			if(null !=status&&status.equals("2")){
				sql=sql+" and p.backStatus =2";
			}else{
				sql=sql+" and p.backStatus <2";
			}
			
			String bidPlanName=map.get("bidPlanname");
			if(null !=bidPlanName&&!bidPlanName.equals("")){
				sql=sql+" and plan.bidProName like '%/"+bidPlanName+"%'  escape '/' ";
			}
			String bidPlanNum=map.get("bidPlanNum");
			if(null !=bidPlanNum&&!bidPlanNum.equals("")){
				sql=sql+" and plan.bidProNumber  like '%/"+bidPlanNum+"%'  escape '/' ";
			}
			
			String compensatoryDaysS=map.get("compensatoryDaysS");
			if(null !=compensatoryDaysS&&!compensatoryDaysS.equals("")){
				sql=sql+"  and p.compensatoryDays >= "+Integer.valueOf(compensatoryDaysS)+"";
			}
			String compensatoryDaysE=map.get("compensatoryDaysE");
			if(null !=compensatoryDaysE&&!compensatoryDaysE.equals("")){
				sql=sql+"  and p.compensatoryDays <= "+Integer.valueOf(compensatoryDaysE)+"";;
			}
			
			String compensatoryMoneyS=map.get("compensatoryMoneyS");
			if(null !=compensatoryMoneyS&&!compensatoryMoneyS.equals("")){
				sql=sql+"  and p.compensatoryMoney >= "+Integer.valueOf(compensatoryMoneyS)+"";
			}
			String compensatoryMoneyE=map.get("compensatoryMoneyE");
			if(null !=compensatoryMoneyE&&!compensatoryMoneyE.equals("")){
				sql=sql+"  and p.compensatoryMoney <= "+Integer.valueOf(compensatoryMoneyE)+"";;
			}
			
			String custmerName=map.get("custmerName");
			if(null !=custmerName&&!custmerName.equals("")){
				sql=sql+" and (cse.enterprisename like '%/"+custmerName+"%'  escape '/'  or csp.`name` like '%/"+custmerName+"%'  escape '/'  or cscoe.`name` like '%/"+custmerName+"%'  escape '/'  or cscop.`name` like '%/"+custmerName+"%'  escape '/' )";
			}
		}
		
		
		
		System.out.println("overDuesql="+sql);
		
		List<PlBidCompensatory>  list=this.getSession().createSQLQuery(sql).
		  addScalar("id",Hibernate.LONG).
		  addScalar("loanerp2pId",Hibernate.LONG).
		  addScalar("custmerId",Hibernate.LONG).
		  addScalar("planId",Hibernate.LONG).
		  addScalar("compensatoryMoney",Hibernate.BIG_DECIMAL).
		  addScalar("compensatoryType",Hibernate.STRING).
		  addScalar("compensatoryName",Hibernate.STRING).
		  addScalar("compensatoryP2PId",Hibernate.LONG).
		  addScalar("payintentPeriod",Hibernate.INTEGER).
		  addScalar("requestNo",Hibernate.STRING).
		  addScalar("compensatoryDate",Hibernate.TIMESTAMP).
		  addScalar("compensatoryDays",Hibernate.INTEGER).
		  addScalar("punishRate",Hibernate.BIG_DECIMAL).
		  addScalar("plateMoney",Hibernate.BIG_DECIMAL).
		  addScalar("punishMoney",Hibernate.BIG_DECIMAL).
		  addScalar("backPunishMoney",Hibernate.BIG_DECIMAL).
		  addScalar("backCompensatoryMoney",Hibernate.BIG_DECIMAL).
		  addScalar("backDate",Hibernate.TIMESTAMP).
		  addScalar("backStatus",Hibernate.INTEGER).
		  addScalar("totalMoney",Hibernate.BIG_DECIMAL).
		  addScalar("custmerName",Hibernate.STRING).
		  addScalar("bidPlanname",Hibernate.STRING).
		  addScalar("bidPlanNumber",Hibernate.STRING).
		  setResultTransformer(Transformers.aliasToBean(PlBidCompensatory.class)).
		  list();
		
		if(pb!=null&&pb.getStart()!=null &&pb.getPageSize()!=null){
			pb.setTotalItems(list!=null?list.size():0);
			List<PlBidCompensatory>  list1=this.getSession().createSQLQuery(sql).
			  addScalar("id",Hibernate.LONG).
			  addScalar("loanerp2pId",Hibernate.LONG).
			  addScalar("custmerId",Hibernate.LONG).
			  addScalar("planId",Hibernate.LONG).
			  addScalar("compensatoryMoney",Hibernate.BIG_DECIMAL).
			  addScalar("compensatoryType",Hibernate.STRING).
			  addScalar("compensatoryName",Hibernate.STRING).
			  addScalar("compensatoryP2PId",Hibernate.LONG).
			  addScalar("payintentPeriod",Hibernate.INTEGER).
			  addScalar("requestNo",Hibernate.STRING).
			  addScalar("compensatoryDate",Hibernate.TIMESTAMP).
			  addScalar("compensatoryDays",Hibernate.INTEGER).
			  addScalar("punishRate",Hibernate.BIG_DECIMAL).
			  addScalar("plateMoney",Hibernate.BIG_DECIMAL).
			  addScalar("punishMoney",Hibernate.BIG_DECIMAL).
			  addScalar("backPunishMoney",Hibernate.BIG_DECIMAL).
			  addScalar("backCompensatoryMoney",Hibernate.BIG_DECIMAL).
			  addScalar("backDate",Hibernate.TIMESTAMP).
			  addScalar("backStatus",Hibernate.INTEGER).
			  addScalar("totalMoney",Hibernate.BIG_DECIMAL).
			  addScalar("custmerName",Hibernate.STRING).
			  addScalar("bidPlanname",Hibernate.STRING).
			  addScalar("bidPlanNumber",Hibernate.STRING).
			  setResultTransformer(Transformers.aliasToBean(PlBidCompensatory.class)).
			  setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).
			  list();
			return list1;
		}else{
			return list;
		}
		
		
		
	}

	/* (non-Javadoc)
	 * @see com.zhiwei.credit.dao.creditFlow.finance.compensatory.PlBidCompensatoryDao#getOneList(java.lang.Long)
	 */
	@Override
	public PlBidCompensatory getOneList(Long id) {
		String sql="select p.id as id," +
		   " p.loanerp2pId as loanerp2pId, " +
		   " p.custmerId  as custmerId, " +
		   " p.planId as planId, " +
		   " p.compensatoryMoney as compensatoryMoney, " +
		   " p.compensatoryType as compensatoryType, " +
		   " p.compensatoryName as compensatoryName, " +
		   " p.compensatoryP2PId as compensatoryP2PId, " +
		   " p.payintentPeriod as payintentPeriod, " +
		   " p.requestNo as requestNo, " +
		   " p.compensatoryDate as compensatoryDate, " +
		   " p.compensatoryDays as compensatoryDays, " +
		   " p.punishRate as punishRate, " +
		   " p.plateMoney as plateMoney, " +
		   " p.punishMoney as punishMoney, " +
		   " p.backPunishMoney as backPunishMoney, " +
		   " p.backCompensatoryMoney as backCompensatoryMoney, " +
		   " p.backDate as backDate, " +
		   " p.backStatus as backStatus, " +
		   " (p.compensatoryMoney+p.punishMoney-p.backPunishMoney-p.backCompensatoryMoney-p.plateMoney) as totalMoney, " +
		   " (p.compensatoryMoney-p.backCompensatoryMoney) as unBackCompensatoryMoney, " +
		   " (p.punishMoney-p.backPunishMoney) as unBackPunishMoney, " +
		   " plan.bidProName as bidPlanname, " +
		   " plan.bidProNumber as bidPlanNumber " +
		   "from pl_bid_compensatory as p " +
		   "left join pl_bid_plan as plan on (p.planId=plan.bidId) " +
		   " where p.id= "+	id;
		
		return (PlBidCompensatory) this.getSession().createSQLQuery(sql).
		  addScalar("id",Hibernate.LONG).
		  addScalar("loanerp2pId",Hibernate.LONG).
		  addScalar("custmerId",Hibernate.LONG).
		  addScalar("planId",Hibernate.LONG).
		  addScalar("compensatoryMoney",Hibernate.BIG_DECIMAL).
		  addScalar("compensatoryType",Hibernate.STRING).
		  addScalar("compensatoryName",Hibernate.STRING).
		  addScalar("compensatoryP2PId",Hibernate.LONG).
		  addScalar("payintentPeriod",Hibernate.INTEGER).
		  addScalar("requestNo",Hibernate.STRING).
		  addScalar("compensatoryDate",Hibernate.TIMESTAMP).
		  addScalar("compensatoryDays",Hibernate.INTEGER).
		  addScalar("punishRate",Hibernate.BIG_DECIMAL).
		  addScalar("plateMoney",Hibernate.BIG_DECIMAL).
		  addScalar("punishMoney",Hibernate.BIG_DECIMAL).
		  addScalar("backPunishMoney",Hibernate.BIG_DECIMAL).
		  addScalar("backCompensatoryMoney",Hibernate.BIG_DECIMAL).
		  addScalar("backDate",Hibernate.TIMESTAMP).
		  addScalar("backStatus",Hibernate.INTEGER).
		  addScalar("totalMoney",Hibernate.BIG_DECIMAL).
		  addScalar("unBackCompensatoryMoney",Hibernate.BIG_DECIMAL).
		  addScalar("unBackPunishMoney",Hibernate.BIG_DECIMAL).
		  addScalar("bidPlanname",Hibernate.STRING).
		  addScalar("bidPlanNumber",Hibernate.STRING).
		  setResultTransformer(Transformers.aliasToBean(PlBidCompensatory.class)).uniqueResult();
	}

}