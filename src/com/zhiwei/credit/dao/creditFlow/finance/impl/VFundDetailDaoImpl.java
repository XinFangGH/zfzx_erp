package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.creditFlow.finance.VFundDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VFundDetailDaoImpl extends BaseDaoImpl<VFundDetail> implements VFundDetailDao{

	public VFundDetailDaoImpl() {
		super(VFundDetailDao.class);
	}

	@Override
	public List<VFundDetail> search(Map<String, String> map) {
		String hql="from VFundDetail q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String businessType=map.get("businessType");
		if(null!=businessType && !businessType.equals("")){
			hql=hql+" and q.businessType='"+businessType+"'";
		}
		if(map.size()!=3){
//		String projNum=map.get("Q_projNum_N_EQ");
//		String projName=map.get("Q_proj_Name_N_EQ");
		String projNum=map.get("Q_projNum_N_EQ");	
		String projName=map.get("Q_proj_Name_N_EQ");
		String fundType=map.get("Q_fundType_S_EQ");
		String intentDatege1=map.get("Q_intentDate_D_GE");
		String intentDatege =intentDatege1+ " 00:00:00";
		String intentDatele1=map.get("Q_intentDate_D_LE");
		String intentDatele =intentDatele1 +" 23:59:59";
		String transactionType=map.get("Q_transactionType_S_LK");
		String operationType=map.get("Q_operationType_N_EQ");
		String companyId=map.get("companyId");
		 String projectProperties=map.get("properties");
		if(null !=companyId&&!companyId.equals("")){
			hql=hql+" and q.companyId="+companyId;
		}
		if(!projNum.equals("")){
			hql=hql+" and q.projectNumber like '%/"+projNum.toString()+"%'  escape '/' ";
		}
		if(!projName.equals("")){
			hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
		}
		/*if(!operationType.equals("")){
			hql=hql+" and q.businessType  = '"+operationType+"'";
		}*/
		if(!transactionType.equals("")){
			hql=hql+" and (q.transactionType  like '%"+transactionType+"%' or q.qlidetransactionType like '%" +transactionType+"%')";
		}
		if(!fundType.equals("")){
			hql=hql+" and q.funType = '"+fundType+"'";
		}
		if(!intentDatege1.equals("")){
			hql=hql+" and  q.operTime >= '"+intentDatege+"'";
		}
		
		if(!intentDatele1.equals("")){
			hql=hql+" and  q.operTime <= '"+intentDatele+"'";
		}
		if(null!=projectProperties && !projectProperties.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
			}
		}
		}else{
			
		}
		hql=hql+" order by q.operTime desc";
		 Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
			
		 return  query.list();
	}

	@Override
	public int searchsize(Map<String, String> map) {
		String hql="select count(*) from VFundDetail q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String businessType=map.get("businessType");
		if(null!=businessType && !businessType.equals("")){
			hql=hql+" and q.businessType='"+businessType+"'";
		}
		if(map.size()!=3){
//		String projNum=map.get("Q_projNum_N_EQ");
//		String projName=map.get("Q_proj_Name_N_EQ");
		String projNum=map.get("Q_projNum_N_EQ");
		String projName=map.get("Q_proj_Name_N_EQ");
		String fundType=map.get("Q_fundType_S_EQ");
		String intentDatege1=map.get("Q_intentDate_D_GE");
		String intentDatege =intentDatege1+ " 00:00:00";
		String intentDatele1=map.get("Q_intentDate_D_LE");
		String intentDatele =intentDatele1 +" 23:59:59";
		String transactionType=map.get("Q_transactionType_S_LK");
		 String projectProperties=map.get("properties");
	    String companyId=map.get("companyId");
		
	    if(null !=companyId&&!companyId.equals("")){
			hql=hql+" and q.companyId="+companyId;
		}
	    if(!projNum.equals("")){
			hql=hql+" and q.projectNumber like '%/"+projNum.toString()+"%'  escape '/' ";
		}
		if(!projName.equals("")){
			hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
		}
		/*if(!operationType.equals("")){
			hql=hql+" and q.businessType  = '"+operationType+"'";
		}*/
		if(!transactionType.equals("")){
			hql=hql+" and (q.transactionType  like '%"+transactionType+"%' or q.qlidetransactionType like '%" +transactionType+"%')";
		}
		if(!fundType.equals("")){
			hql=hql+" and q.funType = '"+fundType+"'";
		}
		if(!intentDatege1.equals("")){
			hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
		}
		
		if(!intentDatele1.equals("")){
			hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
		}
		if(null!=projectProperties && !projectProperties.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
			}
		}
		}else{
			
		}

		Long count=Long.valueOf("0");
		    Query query = getSession().createQuery(hql);
		    List list=query.list();
		    if(null!=list && list.size()>0){
				count=(Long)list.get(0);
			}
			 return  count.intValue();
	}

	@Override
	public List<VFundDetail> wslistbyCharge(String businessType,
			Long projectId, String factDate) {
		String hql="from VFundDetail s  where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String factDatel=sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(factDate, "yyyy-MM-dd"), +1));
		hql+=" and s.iscancel is null and s.funType='consultationMoney' and s.businessType='"+businessType+"' and s.projectId="+projectId+" " +
				"and s.factDate >= '"+factDate+"' and s.factDate <='"+factDatel+"'";
		return findByHql(hql);
	}

	@Override
	public List<VFundDetail> wslistbyPrincipalRepay(String businessType,
			Long projectId, String factDate) {
		String hql="from VFundDetail s  where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String factDatel=sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(factDate, "yyyy-MM-dd"), +1));
			hql+=" and  s.iscancel is null and s.funType='principalRepayment' and s.businessType='"+businessType+"' and s.projectId="+projectId+" " +
					"and s.factDate >= '"+factDate+"' and s.factDate <='"+factDatel+"'";
			return findByHql(hql);
		
	}

	@Override
	public List<VFundDetail> wslistbyinterest(String businessType,
			Long projectId, String factDate) {
		String hql="from VFundDetail s  where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String factDatel=sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(factDate, "yyyy-MM-dd"), +1));
			hql+=" and  s.iscancel is null and s.funType='loanInterest' and s.businessType='"+businessType+"' and s.projectId="+projectId+" " +
					"and s.factDate >= '"+factDate+"' and s.factDate <='"+factDatel+"'";
			return findByHql(hql);
		
	}

	@Override
	public List getListByFundType(String startDate, String endDate,
			Long companyId, String fundType) {
		String hql="select count(s.projectId),if(isNull(sum(v.afterMoney)),0,sum(v.afterMoney)) from v_fund_detail as v left join sl_smallloan_project as s on s.projectId=v.projectId where v.factDate>='"+startDate+"' and v.factDate<='"+endDate+"' and v.companyId=? and v.funType=?";
		return getSession().createSQLQuery(hql).setParameter(0, companyId).setParameter(1, fundType).list();
	}

	@Override
	public List getshCount(String startDate, String endDate, Long companyId,
			String fundType) {
		String sql="select count(s.projectId) from sl_smallloan_project as s where s.companyId="+companyId+" and s.projectId in (select v.projectId from v_fund_detail as v where v.funType='"+fundType+"' and v.companyId="+companyId+" and (select max(f.factDate) from v_fund_detail as f where f.projectId=v.projectId and f.companyId="+companyId+" and f.funType='"+fundType+"')>='"+startDate+"' and (select max(f.factDate) from v_fund_detail as f where f.projectId=v.projectId and f.companyId="+companyId+" and f.funType='"+fundType+"')<='"+endDate+"' and v.intentincomeMoney=(select sum(d.afterMoney) from v_fund_detail as d where d.funType='"+fundType+"' and d.projectId=v.projectId and d.companyId="+companyId+"))";
		return getSession().createSQLQuery(sql).list();
	}

	@Override
	public List getwjqList(String startDate, String endDate, Long companyId,
			String fundType) {
		String sql="select (select sum(s.projectMoney) from sl_smallloan_project as s where s.startDate<='"+endDate+"' and s.projectStatus!=0 and s.companyId="+companyId+")-(if(isNull(sum(v.afterMoney)),0,sum(v.afterMoney))) as monry,((select count(s.projectId) from sl_smallloan_project as s where s.startDate<='"+endDate+"' and s.projectStatus!=0 and s.companyId="+companyId+")-(select count(s.projectId) from sl_smallloan_project as s where s.companyId="+companyId+" and s.projectId in (select t.projectId from v_fund_detail as t where t.funType='"+fundType+"' and t.companyId="+companyId+" and (select max(f.factDate) from v_fund_detail as f where f.projectId=t.projectId and f.companyId="+companyId+" and f.funType='"+fundType+"')<='"+endDate+"' and t.intentincomeMoney=(select sum(if(isNull(d.afterMoney),0,d.afterMoney)) from v_fund_detail as d where d.funType='"+fundType+"' and d.projectId=t.projectId and d.companyId="+companyId+")))) as count from v_fund_detail as v where v.funType='"+fundType+"' and v.factDate<='"+endDate+"' and v.companyId="+companyId;
		return getSession().createSQLQuery(sql).list();
	}

	@Override
	public List getyqList(String endDate, Long companyId, String fundType) {
		String sql="select count(*),((select if(ISNULL(sum(f.incomeMoney)),0,sum(f.incomeMoney)) from sl_fund_intent as f where f.companyId="+companyId+" and f.intentDate<'"+endDate+"' and f.fundType='principalRepayment' and f.fundIntentId not in (select v.fundIntentId from v_fund_detail as v where v.factDate<='"+endDate+"' and v.intentDate<'"+endDate+"' and v.funType='principalRepayment' and v.companyId="+companyId+"))+(select if(ISNULL(sum(i.incomeMoney)),0,sum(i.incomeMoney)) from sl_fund_intent as i where i.companyId="+companyId+" and i.fundIntentId in (select fundIntentId from v_fund_detail as v where v.funType='principalRepayment' and v.companyId="+companyId+" and v.factDate<='"+endDate+"' and v.intentDate<'"+endDate+"' and v.intentincomeMoney>(select sum(f.afterMoney) from v_fund_detail as f where f.factDate<='"+endDate+"' and f.fundIntentId=v.fundIntentId) GROUP BY v.fundIntentId))-(select if(ISNULL(sum(v.afterMoney)),0,sum(v.afterMoney)) from v_fund_detail as v where v.companyId="+companyId+" and v.funType='principalRepayment' and v.factDate<='"+endDate+"' and v.intentDate<'"+endDate+"' and v.intentincomeMoney>(select sum(f.afterMoney) from v_fund_detail as f where f.factDate<='"+endDate+"' and f.fundIntentId=v.fundIntentId))) as bjmoney from sl_smallloan_project as s where s.projectStatus!=0 and s.startDate<='"+endDate+"' and s.companyId="+companyId+" and (s.projectId  in (select f.projectId from sl_fund_intent as f where f.fundType='principalRepayment' and f.intentDate<'"+endDate+"' and f.fundIntentId not in (select v.fundIntentId from v_fund_detail as v where v.factDate<='"+endDate+"' and v.intentDate<'"+endDate+"' and v.funType='principalRepayment' and v.companyId="+companyId+")) or s.projectId in (select v.projectId from v_fund_detail as v where v.funType='principalRepayment' and v.companyId="+companyId+" and v.factDate<='"+endDate+"' and v.intentDate<'"+endDate+"' and v.intentincomeMoney>(select sum(f.afterMoney) from v_fund_detail as f where f.factDate<='"+endDate+"' and f.fundIntentId=v.fundIntentId)))";
		return getSession().createSQLQuery(sql).list();
	}

	@Override
	public List<VFundDetail> listByFundType(String fundType, Long projectId,
			String businessType) {
		String hql="from VFundDetail as v where v.funType=? and v.projectId=? and v.businessType=? order by v.factDate desc";
		return getSession().createQuery(hql).setParameter(0, fundType).setParameter(1, projectId).setParameter(2, businessType).list();
	}

	@Override
	public List getListByFundType(String fundType, String companyId,String startDate,String intentDate) {
		String hql="select sum(v.afterMoney) from VFundDetail as v where v.iscancel is null";
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			hql=hql+" and v.companyId in ("+str+")";
		}
		if(null!=fundType && !"".equals(fundType)){
			hql=hql+" and v.funType='"+fundType+"'";
		}
		if(null!=startDate && !startDate.equals("")){
			hql=hql+" and v.factDate>='"+startDate+"'";
		}
		if(null!=intentDate && !intentDate.equals("")){
			hql=hql+" and v.factDate<'"+intentDate+"'";
		}
		hql=hql+" group by v.fundIntentId";
		return getSession().createQuery(hql).list();
	}


}