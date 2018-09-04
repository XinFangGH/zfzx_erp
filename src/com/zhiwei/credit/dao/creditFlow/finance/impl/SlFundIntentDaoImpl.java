package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlFundIntentDaoImpl extends BaseDaoImpl<SlFundIntent> implements SlFundIntentDao{

	public SlFundIntentDaoImpl() {
		super(SlFundIntent.class);
	}

	@Override
	public int updateOverdue(SlFundIntent s) {
				
//		String hql="UPDATE sl_fund_intent SET sl_fund_intent.isOverdue=?";
//		 Object[] params ={s.getIsOverdue()};
		 return getSession().createQuery("UPDATE SlFundIntent f SET f.isOverdue='"+s.getIsOverdue()+"' where f.fundIntentId='"+s.getFundIntentId()+"'").executeUpdate();
//		update(hql,params);
//		return 0;
}

	@Override
	public int updateFlatMoney(SlFundIntent s) {
		
		return getSession().createQuery("UPDATE SlFundIntent f SET f.flatMoney='"+s.getFlatMoney()+"',f.notMoney='"+s.getNotMoney()+"' where f.fundIntentId='"+s.getFundIntentId()+"'").executeUpdate();
	}
	@Override
	public int updateIntent(SlFundIntent s) {
		// TODO Auto-generated method stub
		  SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		 String factDatehql="";
		 String intentDate="";
		 if(s.getFactDate()!=null){
			 factDatehql= "',f.factDate='"+sd.format(s.getFactDate());
		 }
		 if(s.getIntentDate()!=null){
			 intentDate=sd.format(s.getIntentDate());
		 }
		 return getSession().createQuery("UPDATE SlFundIntent f SET f.fundType='"+s.getFundType()+
				 "',f.intentDate='"+intentDate
				 +factDatehql+
				 "',f.incomeMoney='"+s.getIncomeMoney()+
				 "',f.payMoney='"+s.getPayMoney()+
				 "' where f.fundIntentId='"+s.getFundIntentId()+"'").executeUpdate();
	}
	@Override
	public List<SlFundIntent> getByProjectId(Long projectId,String businessType) {
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.projectId="+projectId+" and s.businessType='"+businessType+"' and (s.isValid = 0 or s.isValid = 1) order by s.intentDate desc";
		return findByHql(hql);
	}
	@Override
	public List<SlFundIntent> getByProjectIdAsc(Long projectId,String businessType) {
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.projectId="+projectId+" and s.businessType='"+businessType+"' and (s.isValid = 0 or s.isValid = 1) order by s.intentDate asc";
		return findByHql(hql);
	}
	@Override
	public List<SlFundIntent> getListByBidPlanId(Long bidPlanId){
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.bidPlanId="+bidPlanId+" and (s.isValid = 0 or s.isValid = 1) order by s.intentDate asc";
		return findByHql(hql);
	}
	@Override
	public List<SlFundIntent> getByProjectId(Long projectId,String businessType,String fundResource) {
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.projectId="+projectId+" and s.businessType='"+businessType+"'" 
				   + " and s.fundResource='"+fundResource+"' and (s.isValid = 0 or s.isValid = 1) order by s.intentDate asc";
		return findByHql(hql);
	}
	@Override
	public List<SlFundIntent> getByProjectId1(Long projectId,String businessType) {
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.projectId="+projectId+" and s.isValid = 0  and s.businessType='"+businessType+"' order by s.intentDate asc";
		  
		List<SlFundIntent> findByHql = findByHql(hql);
		
		 return findByHql;
	}
	@Override
	public List<SlFundIntent> getByProjectId2(Long projectId,
			String businessType) {
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.projectId="+projectId+" and s.businessType='"+businessType+"' order by s.intentDate asc";
		return findByHql(hql);
	}
	@Override
	public List<SlFundIntent> getByProjectId3(Long projectId,
			String businessType,String fundType) {
		String hql = "from SlFundIntent s where s.projectId="+projectId+" and s.isValid = 0 and s.isCheck = 0 and s.businessType='"+businessType+"' and s.fundType='"+fundType+"' order by s.intentDate desc";
		return findByHql(hql);
	}

	public List<SlFundIntent> getByProjectIdAndFundType(Long projectId, Integer fundType) {
		String hql = "from SlFundIntent s where s.projectId="+projectId+" and s.fundType="+fundType;
		return findByHql(hql);
	}

	@Override
	public int searchsize(Map<String, String> map,String businessType) {
		String hql="";
		if(businessType.equals("all")){
			hql="from SlFundIntent  q where s.fundType !='principalDivert' and q.isValid = 0 and q.isCheck = 0 ";// and q.businessType='smallLoan'";
		}
		
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
		String projNum=map.get("Q_projNum_N_EQ");
		String projName=map.get("Q_proj_Name_N_EQ");
		String fundType=map.get("Q_fundType_N_EQ");
		String isOverdue=map.get("Q_isOverdue_S_E");
		String notMoneyle=map.get("Q_notMoney_BD_LE");
		String intentDatege=map.get("Q_intentDate_D_GE");
		String intentDatele=map.get("Q_intentDate_D_LE");
		String operationType=map.get("Q_operationType_N_EQ");
		
		if(!projNum.equals("")){
			hql=hql+" and q.projectNumber like '%/"+projNum.toString()+"%'  escape '/' ";
		}
		if(!projName.equals("")){
			hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
		}
		if(!operationType.equals("")){
			hql=hql+" and q.businessType  = '"+operationType+"'";
		}
		if(!fundType.equals("")){
			hql=hql+" and q.fundType = '"+fundType+"'";
		}
		if(!notMoneyle.equals("")&&notMoneyle.equals("0")){
			hql=hql+" and  q.notMoney = 0";
		}
		
		if(!notMoneyle.equals("")&&notMoneyle.equals("1")){
			hql=hql+" and  q.notMoney !=0 ";
		}
		if(!intentDatege.equals("")){
			hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
		}
		
		if(!intentDatele.equals("")){
			hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
		}
		if(!isOverdue.equals("")){
			hql=hql+" and q.isOverdue = '"+isOverdue+"'";
			hql=hql+" and q.fundType != '1748'";
		}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
			
		}
	    Query query = getSession().createQuery(hql);
		
		 return  query.list().size();
	}

	@Override
	public List<SlFundIntent> search(Map<String, String> map,String businessType) {
		// TODO Auto-generated method stub
		String hql="";
		if(businessType.equals("all")){
		 hql="from SlFundIntent q  where s.fundType !='principalDivert' and q.isValid = 0 and q.isCheck = 0 ";//and q.businessType='smallLoan' ";
		}
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
		String projNum=map.get("Q_projNum_N_EQ");
		String projName=map.get("Q_proj_Name_N_EQ");
		String fundType=map.get("Q_fundType_N_EQ");
		String isOverdue=map.get("Q_isOverdue_S_E");
		String notMoneyle=map.get("Q_notMoney_BD_LE");
		String intentDatege=map.get("Q_intentDate_D_GE");
		String intentDatele=map.get("Q_intentDate_D_LE");
		String operationType=map.get("Q_operationType_N_EQ");
		
		if(!projNum.equals("")){
			hql=hql+" and q.projectNumber like '%/"+projNum+"%'  escape '/' ";
		}
		if(!projName.equals("")){
			hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
		}
		if(!operationType.equals("")){
			hql=hql+" and q.businessType  = '"+operationType+"'";
		}
		if(!fundType.equals("")){
			hql=hql+" and q.fundType = '"+fundType+"'";
		}
		if(!notMoneyle.equals("")&&notMoneyle.equals("0")){
			hql=hql+" and  q.notMoney = 0";
		}
		
		if(!notMoneyle.equals("")&&notMoneyle.equals("1")){
			hql=hql+" and  q.notMoney !=0 ";
		}
		if(!intentDatege.equals("")){
			hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
		}
		
		if(!intentDatele.equals("")){
			hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
		}
		if(!isOverdue.equals("")){
			hql=hql+" and q.isOverdue = '"+isOverdue+"'";
			hql=hql+" and q.fundType != '1748'";
		}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
		hql=hql+" order by q.intentDate asc";
	    Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		
		 return  query.list();
	}

	@Override
	public Map<String, BigDecimal> saveProjectfiance(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> getlistbyslSuperviseRecordId(
			Long slSuperviseRecordId, String businessType,Long projectId) {
		String hql = "from SlFundIntent s where s.slSuperviseRecordId="+slSuperviseRecordId+" and s.businessType='"+businessType+"' and s.projectId="+projectId;
		return findByHql(hql);
	}

	@Override
	public List<SlFundIntent> getByisvalidAndaftercheck(Long projectId,
			String businessType) {
		String hql = "from SlFundIntent s where s.projectId="+projectId+" and s.businessType='"+businessType+"' and s.isValid = 1 and s.afterMoney !=0 ";
		return findByHql(hql);
	}

	@Override
	public List<SlFundIntent> getByaftercheck(Long projectId,
			String businessType) {
		String hql = "from SlFundIntent s where s.projectId="+projectId+" and s.businessType='"+businessType+"' and s.afterMoney !=0 ";
		return findByHql(hql);
	}

	@Override
	public int searchsizeurge(String projectIdStr,Map<String, String> map, String businessType) {
		String hql="from SlFundIntent q where  1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		if (projectIdStr !=null && !projectIdStr.equals("")) {
			hql+=" and q.projectId in ("+projectIdStr+") ";
		}else if(projectIdStr !=null && projectIdStr.equals("")){
			hql+="  and q.projectId in ('')";
		}
		/*if(businessType.equals("all")){
			hql+=" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney";//and q.businessType='smallLoan' ";
		}*/
		/*if(("SmallLoan".equals(businessType))){//最初版本
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney and q.businessType='"+businessType+"'";
		}
		if("Financing".equals(businessType)){
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
		}*/
		
		/*if("SmallLoan".equals(businessType)){//改动1  update by gao
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney and q.businessType='"+businessType+"'";
		}else if("Financing".equals(businessType)){
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
		}else if("LeaseFinance".equals(businessType)){
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
		}*/
		if(null!=businessType&&!"all".equals(businessType)){//改动2 update by gao
			if(businessType.equals("Financing")){
				hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
			}else{
				hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney and q.businessType='"+businessType+"'";
			}
		}
		
		String tabflag=map.get("tabflag");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		if(tabflag.equals("coming")){
			hql=hql+" and  q.intentDate >= '"+str+"'";
		}
        if(tabflag.equals("overdue")){
        	hql=hql+" and  q.intentDate < '"+str+"'";
		}
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=4){
		String projNum=map.get("Q_projNum_N_EQ");
		String projName=map.get("Q_proj_Name_N_EQ");
		String intentType=map.get("Q_intentType_SN_EQ");
		String oppositeName=map.get("Q_oppositeName_N_EQ");
		String intentDatege=map.get("Q_intentDate_D_GE");
		String intentDatele=map.get("Q_intentDate_D_LE");
		String incomemoneyge=map.get("Q_incomemoney_S_GE");
		String incomemoneyle=map.get("Q_incomemoney_D_LE");
		String companyId=map.get("companyId");
		
		
		if(null !=companyId&&!companyId.equals("")){
			hql=hql+" and q.companyId="+companyId;
		}
		if(null != projNum&&!projNum.equals("")){
			hql=hql+" and q.projectNumber like '%/"+projNum+"%'  escape '/' ";
		}
		if(null != projName&&!projName.equals("")){
			hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
		}
		if(null != oppositeName&&!oppositeName.equals("")){
			hql=hql+" and q.projectName like '%/"+oppositeName+"%'  escape '/' ";
		}
		if(null != intentType&&!intentType.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				if(intentType.equals("0")){
					hql=hql+" and  q.fundType !='principalLending'";
				}
				if(intentType.equals("1")){
					hql=hql+" and  q.fundType = 'principalRepayment'";
				}
				if(intentType.equals("2")){
					hql=hql+" and  q.fundType != 'principalRepayment' and q.fundType != 'principalLending' and q.fundType != 'principalDivert'";
				}
			}else if(null!=businessType && businessType.equals("Financing")){
				if(intentType.equals("0")){
					hql=hql+" and  q.fundType !='Financingborrow'";
				}
				if(intentType.equals("1")){
					hql=hql+" and  q.fundType = 'FinancingRepay'";
				}
				if(intentType.equals("2")){
					hql=hql+" and  q.fundType != 'Financingborrow' and q.fundType != 'FinancingRepay' and q.fundType != 'principalDivert'";
				}
			}else if(null!=businessType && businessType.equals("Pawn")){
				if(intentType.equals("0")){
					hql=hql+" and  q.fundType !='pawnPrincipalLending'";
				}
				if(intentType.equals("1")){
					hql=hql+" and  q.fundType = 'pawnPrincipalRepayment'";
				}
				if(intentType.equals("2")){
					hql=hql+" and  q.fundType != 'pawnPrincipalLending' and q.fundType != 'pawnPrincipalRepayment'";
				}
			}else if("LeaseFinance".equals(intentType)){
					hql=hql+" and  q.fundType = '" + intentType + "' ";
			}
		}
		
		if(null != intentDatege&&!intentDatege.equals("")){
			hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
		}
		
		if(null != intentDatele&&!intentDatele.equals("")){
			hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
		}
		if(null != incomemoneyge && !incomemoneyge.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				hql=hql+" and  q.incomeMoney >= "+incomemoneyge+"";
			}else if(null!=businessType && businessType.equals("Financing")){
				hql=hql+" and  q.payMoney >= "+incomemoneyge+"";
			}
		}
		
		if(null != incomemoneyle && !incomemoneyle.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				hql=hql+" and  q.incomeMoney <= "+incomemoneyle+"";
			}else if(null!=businessType && businessType.equals("Financing")){
				hql=hql+" and  q.payMoney <= "+incomemoneyle+"";
			}
		}
		}else{
			//hql=hql+" and q.payMoney is null";
		}
		hql=hql+" order by q.intentDate asc";
	    Query query = getSession().createQuery(hql);
		
		 return  query.list().size();
	}

	@Override
	public void searchurge(PageBean<SlFundIntent> pageBean,Map<String, String> map, String businessType) {
		/*--------查询总条数---------*/
		StringBuffer totalCounts = new StringBuffer ("select count(*) from (");
		StringBuffer hql=new StringBuffer("select " +
				" s.fundIntentId," +
				" s.projectName," +
				" s.projectNumber," +
				" s.incomeMoney," +
				" dic.itemValue as fundTypeName," +
				" s.intentDate," +
				" s.payMoney," +
				" pro.projectMoney as payInMoney," +
				" s.factDate," +
				" s.fundType," +
				" s.afterMoney," +
				" s.notMoney," +
				" s.flatMoney," +
				" s.isOverdue," +
				" IFNULL(pro.overdueRate,0) as overdueRate," +
				" s.accrualMoney," +
				" s.businessType," +
				" s.projectId," +
				" urge.urgeTime as lastslFundintentUrgeTime," +
				" ( case pro.oppositeType " +
				"	when 'company_customer' then (select e.enterprisename from cs_enterprise as e where e.id=pro.oppositeID)" +
				"   else (select p.name from cs_person as p where p.id=pro.oppositeID)" +
				"	end " +
				" )as oppositeName," +
				" ( case pro.oppositeType " +
				"	when 'company_customer' then (select e.telephone from cs_enterprise as e where e.id=pro.oppositeID)" +
				"   else (select p.cellphone from cs_person as p where p.id=pro.oppositeID)" +
				"	end " +
				" )as opposittelephone," +
				" pro.startDate as projectStartDate," +
				" org.org_name as orgName"+
				" from sl_fund_intent as s " +
				" left join dictionary_independent as dic on dic.dicKey=s.fundType" +
				" left join sl_smallloan_project as pro on pro.projectId=s.projectId" +
				" left join organization as org on org.org_id=s.companyId" +
				" left join sl_fundintent_urge as urge on urge.slFundintentUrgeId=s.lastslFundintentUrgeId");
		
		if(null!=businessType && !"all".equals(businessType)){//改动2 update by gao
			if(businessType.equals("Financing")){
				hql.append(" where  s.isValid = 0 and s.isCheck = 0 and s.incomeMoney =0 and s.afterMoney !=s.payMoney and s.businessType='"+businessType+"'");
			}else{
				hql.append(" where  s.isValid = 0 and s.isCheck = 0 and s.payMoney =0 and s.afterMoney !=s.incomeMoney and s.businessType='"+businessType+"'");
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		if(map.get("tabflag").equals("coming")){
			hql.append(" and  s.intentDate >= '"+str+"'");
		}
        if(map.get("tabflag").equals("overdue")){
        	hql.append(" and  s.intentDate < '"+str+"'");
		}
		if(map.size()!=4){
			if(null !=map.get("companyId")&&!map.get("companyId").equals("")){
				hql.append(" and s.companyId in("+map.get("companyId")+")");
			}
			if(null != map.get("Q_projNum_N_EQ")&&!map.get("Q_projNum_N_EQ").equals("")){
				hql.append(" and s.projectNumber like '%/"+map.get("Q_projNum_N_EQ")+"%'  escape '/' ");
			}
			if(null != map.get("Q_proj_Name_N_EQ")&&!map.get("Q_proj_Name_N_EQ").equals("")){
				hql.append(" and s.projectName like '%/"+map.get("Q_proj_Name_N_EQ")+"%'  escape '/' ");
			}
			if(null != map.get("Q_oppositeName_N_EQ")&&!map.get("Q_oppositeName_N_EQ").equals("")){
				hql.append(" and s.projectName like '%/"+map.get("Q_oppositeName_N_EQ")+"%'  escape '/' ");
			}
			if(null != map.get("Q_intentType_SN_EQ")&&!map.get("Q_intentType_SN_EQ").equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					if(map.get("Q_intentType_SN_EQ").equals("0")){
						hql.append(" and  s.fundType !='principalLending'");
					}
					if(map.get("Q_intentType_SN_EQ").equals("1")){
						hql.append(" and  s.fundType = 'principalRepayment'");
					}
					if(map.get("Q_intentType_SN_EQ").equals("2")){
						hql.append(" and  s.fundType != 'principalRepayment' and q.fundType != 'principalLending' and q.fundType != 'principalDivert'");
					}
				}else if(null!=businessType && businessType.equals("Financing")){
					if(map.get("Q_intentType_SN_EQ").equals("0")){
						hql.append(" and  s.fundType !='Financingborrow'");
					}
					if(map.get("Q_intentType_SN_EQ").equals("1")){
						hql.append(" and  s.fundType = 'FinancingRepay'");
					}
					if(map.get("Q_intentType_SN_EQ").equals("2")){
						hql.append(" and  s.fundType != 'Financingborrow' and q.fundType != 'FinancingRepay' and q.fundType != 'principalDivert'");
					}
				}else if(null!=businessType && businessType.equals("Pawn")){
					if(map.get("Q_intentType_SN_EQ").equals("0")){
						hql.append(" and  s.fundType !='pawnPrincipalLending'");
					}
					if(map.get("Q_intentType_SN_EQ").equals("1")){
						hql.append(" and  s.fundType = 'pawnPrincipalRepayment'");
					}
					if(map.get("Q_intentType_SN_EQ").equals("2")){
						hql.append(" and  s.fundType != 'pawnPrincipalLending' and q.fundType != 'pawnPrincipalRepayment'");
					}
				}else if("LeaseFinance".equals(map.get("Q_intentType_SN_EQ"))){
					hql.append(" and  s.fundType = '" + map.get("Q_intentType_SN_EQ") + "' ");
				}
			}
			
			if(null != map.get("Q_intentDate_D_GE")&&!map.get("Q_intentDate_D_GE").equals("")){
				hql.append(" and  s.intentDate >= '"+map.get("Q_intentDate_D_GE")+"'");
			}
			
			if(null != map.get("Q_intentDate_D_LE")&&!map.get("Q_intentDate_D_LE").equals("")){
				hql.append(" and  s.intentDate <= '"+map.get("Q_intentDate_D_LE")+"'");
			}
			if(null != map.get("Q_incomemoney_S_GE") && !map.get("Q_incomemoney_S_GE").equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					hql.append(" and  s.incomeMoney >= "+map.get("Q_incomemoney_S_GE"));
				}else if(null!=businessType && businessType.equals("Financing")){
					hql.append(" and  s.payMoney >= "+map.get("Q_incomemoney_S_GE"));
				}
			}
			if(null != map.get("Q_incomemoney_D_LE") && !map.get("Q_incomemoney_D_LE").equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					hql.append(" and  s.incomeMoney <= "+map.get("Q_incomemoney_D_LE"));
				}else if(null!=businessType && businessType.equals("Financing")){
					hql.append(" and  s.payMoney <= "+map.get("Q_incomemoney_D_LE")+"");
				}
			}
		}
		totalCounts.append(hql);
		totalCounts.append(")as b");
		List list=null;
		if(null!=pageBean.getStart() && null!=pageBean.getLimit()){
			list = this.getSession().createSQLQuery(hql.toString())
			.addScalar("fundIntentId",Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("projectNumber", Hibernate.STRING)
			.addScalar("incomeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("payMoney", Hibernate.BIG_DECIMAL)
			.addScalar("payInMoney", Hibernate.BIG_DECIMAL)
			.addScalar("afterMoney", Hibernate.BIG_DECIMAL)
			.addScalar("notMoney", Hibernate.BIG_DECIMAL)
			.addScalar("flatMoney", Hibernate.BIG_DECIMAL)
			.addScalar("overdueRate", Hibernate.BIG_DECIMAL)
			.addScalar("accrualMoney", Hibernate.BIG_DECIMAL)
			.addScalar("fundTypeName", Hibernate.STRING)
			.addScalar("isOverdue", Hibernate.STRING)
			.addScalar("fundType", Hibernate.STRING)
			.addScalar("businessType", Hibernate.STRING)
			.addScalar("oppositeName", Hibernate.STRING)
			.addScalar("opposittelephone", Hibernate.STRING)
			.addScalar("orgName", Hibernate.STRING)
			.addScalar("projectId", Hibernate.LONG)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("factDate", Hibernate.DATE)
			.addScalar("projectStartDate", Hibernate.DATE)
			.addScalar("lastslFundintentUrgeTime", Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(SlFundIntent.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
		}else{
			list = this.getSession().createSQLQuery(hql.toString())
			.addScalar("fundIntentId",Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("projectNumber", Hibernate.STRING)
			.addScalar("incomeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("payMoney", Hibernate.BIG_DECIMAL)
			.addScalar("payInMoney", Hibernate.BIG_DECIMAL)
			.addScalar("afterMoney", Hibernate.BIG_DECIMAL)
			.addScalar("notMoney", Hibernate.BIG_DECIMAL)
			.addScalar("flatMoney", Hibernate.BIG_DECIMAL)
			.addScalar("overdueRate", Hibernate.BIG_DECIMAL)
			.addScalar("accrualMoney", Hibernate.BIG_DECIMAL)
			.addScalar("fundTypeName", Hibernate.STRING)
			.addScalar("isOverdue", Hibernate.STRING)
			.addScalar("fundType", Hibernate.STRING)
			.addScalar("businessType", Hibernate.STRING)
			.addScalar("oppositeName", Hibernate.STRING)
			.addScalar("opposittelephone", Hibernate.STRING)
			.addScalar("orgName", Hibernate.STRING)
			.addScalar("projectId", Hibernate.LONG)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("factDate", Hibernate.DATE)
			.addScalar("projectStartDate", Hibernate.DATE)
			.addScalar("lastslFundintentUrgeTime", Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(SlFundIntent.class)).list();
		}
		pageBean.setResult(list);
		BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}

	@Override
	public List<SlFundIntent> getlistbyslEarlyRepaymentId(
			Long slEarlyRepaymentId, String businessType, Long projectId) {
			String hql = "from SlFundIntent s where s.slEarlyRepaymentId="+slEarlyRepaymentId+" and s.businessType='"+businessType+"' and s.projectId="+projectId;
			return findByHql(hql);
	}

	@Override
	public List<SlFundIntent> getlistbyslslAlteraccrualRecordId(
			Long slAlteraccrualRecordId, String businessType, Long projectId) {
			String hql = "from SlFundIntent s where s.slAlteraccrualRecordId="+slAlteraccrualRecordId+" and s.businessType='"+businessType+"' and s.projectId="+projectId;
			return findByHql(hql);
	}

	@Override
	public void listbyLedger(PageBean<SlFundIntent> pageBean,String businessType,String fundType,String typetab, PagingBean pb, Map<String, String> map) {
		/*--------查询总条数---------*/
		StringBuffer tempCount=new StringBuffer("SELECT count(*) FROM " +
				" ( SELECT q.projectId FROM sl_fund_intent AS q where 1=1");
		
		StringBuffer temp=new StringBuffer("select " +
				" q.fundIntentId," +
				" q.projectName," +
				" q.projectNumber," +
				" q.incomeMoney," +
				" q.intentDate," +
				" q.factDate," +
				" q.payMoney," +
				" q.afterMoney," +
				" q.notMoney," +
				" q.flatMoney," +
				" q.accrualMoney," +
				" q.fundType," +
				" q.businessType," +
				" q.projectId," +
				" q.graceDay," +
				" q.continueDay," +
				" q.faxiAfterMoney," +
				" q.interestStarTime," +
				" q.interestEndTime," +
				" q.payintentPeriod," +
				" q.companyId," +
				" q.remark," +
				" (IF(q.notMoney=0,'已放款项',IF(q.intentDate<NOW(),'欠放款项','应放款项'))) AS tabType " +
				" from sl_fund_intent as q where 1=1 " );
		temp.append(" and  q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and q.fundType in "+fundType);
		tempCount.append(" and  q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and q.fundType in "+fundType);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		if(fundType.equals("principalLending")||fundType.equals("constBack")){
		    if(typetab.equals("should")){
		    	temp.append(" and q.notMoney !=0");
	        	tempCount.append(" and q.notMoney !=0");
			}
	        if(typetab.equals("actual")){
	        	temp.append(" and  q.notMoney =0");
	        	tempCount.append(" and  q.notMoney =0");
			}
		}else{
	        if(typetab.equals("should")){
	        	temp.append(" and  q.intentDate >= '"+str+"' and q.notMoney !=0"); //计划日期大于或等于今天
	        	tempCount.append(" and  q.intentDate >= '"+str+"' and q.notMoney !=0"); //计划日期大于或等于今天
			}
	        if(typetab.equals("owe")){
	        	temp.append(" and  q.intentDate < '"+str+"' and q.notMoney !=0");
	        	tempCount.append(" and  q.intentDate < '"+str+"' and q.notMoney !=0");
			}
	        if(typetab.equals("actual")){
	        	temp.append(" and  q.notMoney =0");
	        	tempCount.append(" and  q.notMoney =0");
			}
        }
		if(map.size()!=5){
			String synthesize=map.get("synthesize");
			String projNum=map.get("Q_projNum_N_EQ");
			String projName=map.get("Q_proj_Name_N_EQ");
			String intentDatege=map.get("Q_intentDate_D_GE");
			String intentDatele=map.get("Q_intentDate_D_LE");
			String operationType=map.get("Q_operationType_N_EQ");
			String companyId=map.get("companyId");
			String projectProperties=map.get("properties");
			String startFactDate=map.get("startFactDate");
			String endFactDate=map.get("endFactDate");
			String flagMoney = map.get("flagMoney");
			if(flagMoney!=null&&!flagMoney.equals("")){
				if(flagMoney.equals("notMoney")){
					temp.append(" and q.notMoney !=0");
					tempCount.append(" and q.notMoney !=0");
				}else if(flagMoney.equals("isMoney")){
					temp.append(" and q.notMoney =0");
					tempCount.append(" and q.notMoney =0");
				}else if(flagMoney.equals("overdueMoney")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date d = new Date();
					temp.append(" and  q.intentDate < '"+sdf.format(d)+"'");
					tempCount.append(" and  q.intentDate < '"+sdf.format(d)+"'");
				}
			}
			
			if(null !=companyId&&!companyId.equals("")){
				temp.append(" and q.companyId="+companyId);
				tempCount.append(" and q.companyId="+companyId);
			}
			if(null!=projNum && !projNum.equals("")){
				temp.append(" and q.projectNumber like '%"+projNum+"%'");
				tempCount.append(" and q.projectNumber like '%"+projNum+"%'");
			}
			if(null!=projName && !projName.equals("")){
				temp.append(" and q.projectName like '%"+projName+"%'");
				tempCount.append(" and q.projectName like '%"+projName+"%'");
			}
			if(null!=operationType && !operationType.equals("")){
				temp.append(" and q.businessType  = '"+operationType+"'");
				tempCount.append(" and q.businessType  = '"+operationType+"'");
			}
			if(null!=intentDatege && !intentDatege.equals("")){
				temp.append(" and  q.intentDate >= '"+intentDatege+"'");
				tempCount.append(" and  q.intentDate >= '"+intentDatege+"'");
			}
			if(null!=intentDatele && !intentDatele.equals("")){
				temp.append(" and  q.intentDate <= '"+intentDatele+"'");
				tempCount.append(" and  q.intentDate <= '"+intentDatele+"'");
			}
			if(null!=startFactDate && !"".equals(startFactDate) && (null==endFactDate || "".equals(endFactDate))){
				temp.append(" and q.factDate>='"+startFactDate+"'");
				tempCount.append(" and q.factDate>='"+startFactDate+"'");
			}
			if(null!=endFactDate && !"".equals(endFactDate) && (null==startFactDate || "".equals(startFactDate))){
				temp.append(" and q.factDate<='"+endFactDate+"'");
				tempCount.append(" and q.factDate<='"+endFactDate+"'");
			}
			if(null!=startFactDate && !"".equals(startFactDate) && null!=endFactDate && !"".equals(endFactDate)){
				temp.append(" and q.factDate>='"+startFactDate+"' and q.factDate<='"+endFactDate+"'");
				tempCount.append(" and q.factDate>='"+startFactDate+"' and q.factDate<='"+endFactDate+"'");
			}
			if(null!=projectProperties && !projectProperties.equals("")){
				if(null!=businessType && !"SmallLoan".equals(businessType)){			
					temp.append(" and q.projectId in (select s.projectId from sl_smallloan_project as s where s.projectProperties in ("+projectProperties+"))");
					tempCount.append(" and q.projectId in (select s.projectId from sl_smallloan_project as s where s.projectProperties in ("+projectProperties+"))");
				}
			}
			if(synthesize!=null&&!synthesize.equals("")){
				temp.append(" ORDER BY q.fundIntentId");
			}
		}
		
		tempCount.append(") as b");
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select " +
				" q.*," +
				" sp1.notMoney AS fxnotMoney," +
				" o.org_name as orgName," +
				" di.itemValue as fundTypeName" +
				" from ( ");
		
		sb.append(temp);
		sb.append(" LIMIT "+pageBean.getStart()+","+pageBean.getLimit());
		sb.append(" ) as q");
		sb.append(" LEFT JOIN organization as o on q.companyId=o.org_id " +
				  " LEFT JOIN dictionary_independent as di on di.dicKey=q.fundType" +
				  " LEFT JOIN sl_punish_interest as sp1 on sp1.fundIntentId = q.fundIntentId");
		List list = this.getSession().createSQLQuery(sb.toString())
			.addScalar("fundIntentId",Hibernate.LONG)
			.addScalar("projectId",Hibernate.LONG)
			.addScalar("companyId",Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("projectNumber", Hibernate.STRING)
			.addScalar("fundTypeName", Hibernate.STRING)
			.addScalar("businessType", Hibernate.STRING)
			.addScalar("fundType", Hibernate.STRING)
			.addScalar("remark", Hibernate.STRING)
			.addScalar("graceDay", Hibernate.STRING)
			.addScalar("continueDay", Hibernate.STRING)
			.addScalar("incomeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("payMoney", Hibernate.BIG_DECIMAL)
			.addScalar("afterMoney", Hibernate.BIG_DECIMAL)
			.addScalar("notMoney", Hibernate.BIG_DECIMAL)
			.addScalar("flatMoney", Hibernate.BIG_DECIMAL)
			.addScalar("accrualMoney", Hibernate.BIG_DECIMAL)
			.addScalar("faxiAfterMoney", Hibernate.BIG_DECIMAL)
			.addScalar("fxnotMoney", Hibernate.BIG_DECIMAL)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("factDate", Hibernate.DATE)
			.addScalar("interestStarTime", Hibernate.DATE)
			.addScalar("interestEndTime", Hibernate.DATE)
			.addScalar("payintentPeriod", Hibernate.INTEGER)
			.addScalar("tabType", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(SlFundIntent.class)).list();
			pageBean.setResult(list);
			
			BigInteger total =   (BigInteger) this.getSession().createSQLQuery(tempCount.toString()).uniqueResult();
			pageBean.setTotalCounts(total.intValue());
	}
	
	@Override
	public Long sizebyLedger(String businessType, String fundType,
			String typetab, PagingBean pb, Map<String, String> map) {
		String hql="select count(*) from SlFundIntent q where  1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		hql+=" and  q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and q.fundType in "+fundType;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		if(fundType.equals("principalLending")){
		    if(typetab.equals("should")){
	        	hql=hql+" and q.notMoney !=0";
			}
	       
	        if(typetab.equals("actual")){
	        	hql=hql+" and  q.notMoney =0";
			}
		}else{
	        if(typetab.equals("should")){
	        	hql=hql+" and  q.intentDate >= '"+str+"' and q.notMoney !=0";
			}
	        if(typetab.equals("owe")){
	        	hql=hql+" and  q.intentDate < '"+str+"' and q.notMoney !=0";
			}
	        if(typetab.equals("actual")){
	        	hql=hql+" and  q.notMoney =0";
			}
        }
		String synthesize="";
		if(map.size()!=5){
			synthesize=map.get("synthesize");
			String projNum=map.get("Q_projNum_N_EQ");
			String projName=map.get("Q_proj_Name_N_EQ");
			String isOverdue=map.get("Q_isOverdue_S_E");
			String notMoneyle=map.get("Q_notMoney_BD_LE");
			String intentDatege=map.get("Q_intentDate_D_GE");
			String intentDatele=map.get("Q_intentDate_D_LE");
			String operationType=map.get("Q_operationType_N_EQ");
			String companyId=map.get("companyId");
			String projectProperties=map.get("properties");
			String startFactDate=map.get("startFactDate");
			String endFactDate=map.get("endFactDate");
			String flagMoney = map.get("flagMoney");
			
			if(flagMoney!=null&&!flagMoney.equals("")){
				if(flagMoney.equals("notMoney")){
					hql=hql+" and q.notMoney !=0";
				}else if(flagMoney.equals("isMoney")){
					hql=hql+" and q.notMoney =0";
				}else if(flagMoney.equals("overdueMoney")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date d = new Date();
					hql=hql+" and  q.intentDate < '"+sdf.format(d)+"'";
				}
			}
			if(null !=companyId&&!companyId.equals("")){
				hql=hql+" and q.companyId="+companyId;
			}
			if(null!=projNum && !projNum.equals("")){
				hql=hql+" and q.projectNumber like '%/"+projNum+"%'  escape '/' ";
			}
			if(null!=projName && !projName.equals("")){
				hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
			}
			if(null!=operationType && !operationType.equals("")){
				hql=hql+" and q.businessType  = '"+operationType+"'";
			}
			
			if(null!=intentDatege && !intentDatege.equals("")){
				hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
			}
			
			if(null!=intentDatele && !intentDatele.equals("")){
				hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
			}
			if(null!=startFactDate && !"".equals(startFactDate) && (null==endFactDate || "".equals(endFactDate))){
				hql=hql+" and q.factDate>='"+startFactDate+"'";
			}
			if(null!=endFactDate && !"".equals(endFactDate) && (null==startFactDate || "".equals(startFactDate))){
				hql=hql+" and q.factDate<='"+endFactDate+"'";
			}
			if(null!=startFactDate && !"".equals(startFactDate) && null!=endFactDate && !"".equals(endFactDate)){
				hql=hql+" and q.factDate>='"+startFactDate+"' and q.factDate<='"+endFactDate+"'";
			}
			if(null!=projectProperties && !projectProperties.equals("")){
				if(null!=businessType && !"SmallLoan".equals(businessType)){
					hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
				}
			}
		}else{
			//hql=hql+" and q.payMoney is null";
		}
		hql=hql+" order by q.intentDate asc";
		String sql="";
		if(synthesize!=null&&!synthesize.equals("")){
			sql="select COUNT(*) from ( select * from sl_fund_intent q where  1=1 and q.companyId in (1) and  q.isValid = 0 and q.isCheck = 0 " +
					" and q.businessType='SmallLoan' and q.fundType in ('loanInterest','consultationMoney','serviceMoney','principalRepayment')  " +
					" GROUP BY  payintentPeriod , projectId  ) as a";
			long count=0;
			Query query =  this.getSession().createSQLQuery(sql);
			List<Object> list = query.list();
			if(null!=list && list.size()>0){
				String num = list.get(0).toString();
				count=Long.valueOf(num).longValue();
			}
			return count;
		}else{
			long count=0;
			List list;
			if(pb == null) {
				list= findByHql(hql.toString());
			} else {
				list= findByHql(hql.toString(), null, pb);
			}
			if(null!=list && list.size()>0){
				count=(Long)list.get(0);
			}
			return count;
		}
		
	}
	@Override
	public List<SlFundIntent> listbyfundType(String businessType, Long projectId,String fundType, Long isInitialorId) {
		String hql="from SlFundIntent q  where q.isValid = 0 and  q.projectId="+projectId+" and q.businessType='"+businessType+"' and q.fundType='"+fundType+"'";
		if(null!=isInitialorId){
			hql=hql+" and isInitialorId="+isInitialorId;
		}
		return findByHql(hql.toString());
	}

	@Override
	public List<SlFundIntent> listbyOwe(String businessType,String fundType, Long isInitialorId) {
		String hql="from SlFundIntent q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		hql+=" and  q.fundType in "+fundType+" and q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and  q.intentDate < '"+str+"' and q.notMoney !=0 and isInitialorId="+isInitialorId;
		return findByHql(hql.toString());
	}
	@Override
	public List<SlFundIntent> listbyOweTiming(String businessType,String fundType, Long isInitialorId) {
		String hql="from SlFundIntent q where 1=1";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		hql+=" and  q.fundType in "+fundType+" and q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and  q.intentDate < '"+str+"' and q.notMoney !=0 and isInitialorId="+isInitialorId;
		return findByHql(hql.toString());
	}

	@Override
	public List<SlFundIntent> listbyisInitialorId(Long isInitialorId) {
		String hql="from SlFundIntent q  where  q.isInitialorId="+isInitialorId;
		return findByHql(hql.toString());
	}

	@Override
	public List<SlFundIntent> getallbycompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> wsgetByPrincipalLending(Long projectId,
			String businessType) {
		String hql="from SlFundIntent s where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		hql+=" and  s.fundType='principalLending' and s.projectId="+projectId+" and s.businessType='"+businessType+"' order by s.intentDate asc";
		return findByHql(hql);
	}

	@Override
	public List<SlFundIntent> wsgetByInterestAccrued(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> wsgetByInterestPlan(Long projectId,
			String businessType,String factDate,String fundType) {
		String hql="from SlFundIntent s where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		hql+=" and   s.isValid = 0 and s.isCheck = 0 and  s.fundType='"+fundType+"' and s.businessType='"+businessType+"' and s.projectId="+projectId+" and s.intentDate='"+factDate+"'";
		return findByHql(hql);
	}



	@Override
	public List<SlFundIntent> wsgetByPrincipalRepayOverdue(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> wsgetByRealInterest(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> wsgetByRealPunishInterest(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> wsgetByRealpPrincipalPepay(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundIntent> listbyOwe(String businessType, Long projectId,
			String fundType) {
		String hql="from SlFundIntent q where q.projectId="+projectId;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		hql+=" and  q.fundType in "+fundType+" and q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and  q.intentDate < '"+str+"' and q.notMoney !=0 order by q.intentDate asc";
		return findByHql(hql.toString());
	}

	@Override
	public List<SlFundIntent> getListByFundType(Long projectId,String businessType, String fundType,Long getListByFundType,Long preceptId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and (s.slSuperviseRecordId is null or s.slSuperviseRecordId!=?) and s.isValid = 0";
		if(null!=preceptId){
			hql=hql+" and s.preceptId="+preceptId;
		}
		hql=hql+" order by s.intentDate desc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).setParameter(3, getListByFundType).list();
	}

	@Override
	public List<SlFundIntent> getListByIntentDate(Long projectId,
			String businessType, String date,Long slSuperviseRecordId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.intentDate"+date+"'";
		if(null==slSuperviseRecordId){
			hql=hql+" and s.slSuperviseRecordId is null";
		}else{
			hql=hql+" and s.slSuperviseRecordId="+slSuperviseRecordId;
		}
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}
	//add by liny  2013-2-28   查找共有多少条本金偿还
	@Override
	public List<SlFundIntent> findLastPrincipal(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=?  and s.fundType='principalRepayment' order by s.intentDate asc";
		List<SlFundIntent> list =getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
		return list;
	}

	@Override
	public List<SlFundIntent> getByProjectId4(Long projectId,
			String businessType) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public List<SlFundIntent> getListOfSupervise(Long projectId,
			String businessType,Date intentDate, Long slSuperviseRecordId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.intentDate<=? and s.isValid=0";
		if(null!=slSuperviseRecordId){
			hql=hql+" and s.slSuperviseRecordId="+slSuperviseRecordId;
		}
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, intentDate).list();
	}

	@Override
	public List<SlFundIntent> getByFundType(Long projectId,
			String businessType, String fundType) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and s.slEarlyRepaymentId is null and s.slAlteraccrualRecordId is null";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).list();
	}

	@Override
	public List<SlFundIntent> getByIntentPeriod(Long projectId,
			String businessType, String fundType, Long slSuperviseRecordId,Integer payIntentPeriod) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and s.payintentPeriod=?";
		if(null!=slSuperviseRecordId){
			hql=hql+" and s.slSuperviseRecordId="+slSuperviseRecordId;
		}
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).setParameter(3, payIntentPeriod).list();
	}

	@Override
	public List<SlFundIntent> getOfEarly(Long projectId, String businessType,
			String fundType,Long slSuperviseRecordId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and s.slEarlyRepaymentId is not null";
		if(null!=slSuperviseRecordId){
			hql=hql+" and s.slSuperviseRecordId="+slSuperviseRecordId;
		}
		hql=hql+" order by s.intentDate desc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).list();
	}

	@Override
	public List<SlFundIntent> dynamicGetData(Long projectId,
			String businessType, String[] str) {
		StringBuffer hql=new StringBuffer("from SlFundIntent as s where s.projectId=? and s.businessType=?  and (");
		
		if(str!=null){
			for(int i=0;i<str.length;i++){
				if((i+1)!=str.length){
				hql.append("s.fundType='"+str[i]+"' or ");
				}else{
				hql.append("s.fundType='"+str[i]+"' ");
				}
			}
		}
		hql.append(") order by s.intentDate asc");
		List<SlFundIntent> list =getSession().createQuery(hql.toString()).setParameter(0, projectId).setParameter(1, businessType).list();
		return list;
	}

	public List<SlFundIntent> getListOfHistory(Long projectId,String businessType,Date intentDate) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.intentDate>? and s.isValid=0";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, intentDate).list();
	}
	public List<SlFundIntent> listByProjectId(Long projectId,String businessType) {
		String hql = "from SlFundIntent s where s.fundType !='principalDivert' and s.projectId="+projectId+" and s.businessType='"+businessType+"' order by s.intentDate asc";
		return findByHql(hql);
	}
	   //用来导出贷款催收的记录 add by  liny
	@Override
	public List<SlFundIntent> listOutExcel(String projectIdStr,Map<String, String> map,String businessType) {
		String hql="from SlFundIntent q where  1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		if (projectIdStr !=null && !projectIdStr.equals("")) {
			hql+=" and q.projectId in ("+projectIdStr+") ";
		}else if(projectIdStr !=null && projectIdStr.equals("")){
			hql+="  and q.projectId in ('')";
		}
		/*if(businessType.equals("all")){
			hql+=" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney";//and q.businessType='smallLoan' ";
		}*/
		/*if(("SmallLoan".equals(businessType))){//最初版本
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney and q.businessType='"+businessType+"'";
		}
		if("Financing".equals(businessType)){
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
		}*/
		
		/*if("SmallLoan".equals(businessType)){//改动1  update by gao
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney and q.businessType='"+businessType+"'";
		}else if("Financing".equals(businessType)){
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
		}else if("LeaseFinance".equals(businessType)){
			hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
		}*/
		if(null!=businessType&&!"all".equals(businessType)){//改动2 update by gao
			if(businessType.equals("Financing")){
				hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.incomeMoney =0 and q.afterMoney !=q.payMoney and q.businessType='"+businessType+"'";
			}else{
				hql=hql+" and  q.isValid = 0 and q.isCheck = 0 and q.payMoney =0 and q.afterMoney !=q.incomeMoney and q.businessType='"+businessType+"'";
			}
		}
		
		String tabflag=map.get("tabflag");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		if(tabflag.equals("coming")){
			hql=hql+" and  q.intentDate >= '"+str+"'";
		}
        if(tabflag.equals("overdue")){
        	hql=hql+" and  q.intentDate < '"+str+"'";
		}
		if(map.size()!=4){
		String projNum=map.get("projNum");
		String projName=map.get("projectName");
		String intentType=map.get("intentType");
		String oppositeName=map.get("oppositeName");
		String intentDatege=map.get("intentDate_S");
		String intentDatele=map.get("intentDate_D_LE");
		String incomemoneyge=map.get("incomemoney_S");
		String incomemoneyle=map.get("incomemoney_D");
		String companyId=map.get("companyId");
		
		
		if(null !=companyId&&!companyId.equals("")){
			hql=hql+" and q.companyId="+companyId;
		}
		if(null != projNum&&!projNum.equals("")){
			hql=hql+" and q.projectNumber like '%/"+projNum+"%'  escape '/' ";
		}
		if(null != projName&&!projName.equals("")){
			hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
		}
		if(null != oppositeName&&!oppositeName.equals("")){
			hql=hql+" and q.projectName like '%/"+oppositeName+"%'  escape '/' ";
		}
		if(null != intentType&&!intentType.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				if(intentType.equals("0")){
					hql=hql+" and  q.fundType !='principalLending'";
				}
				if(intentType.equals("1")){
					hql=hql+" and  q.fundType = 'principalRepayment'";
				}
				if(intentType.equals("2")){
					hql=hql+" and  q.fundType != 'principalRepayment' and q.fundType != 'principalLending' and q.fundType != 'principalDivert'";
				}
			}else if(null!=businessType && businessType.equals("Financing")){
				if(intentType.equals("0")){
					hql=hql+" and  q.fundType !='Financingborrow'";
				}
				if(intentType.equals("1")){
					hql=hql+" and  q.fundType = 'FinancingRepay'";
				}
				if(intentType.equals("2")){
					hql=hql+" and  q.fundType != 'Financingborrow' and q.fundType != 'FinancingRepay' and q.fundType != 'principalDivert'";
				}
			}else if(null!=businessType && businessType.equals("Pawn")){
				if(intentType.equals("0")){
					hql=hql+" and  q.fundType !='pawnPrincipalLending'";
				}
				if(intentType.equals("1")){
					hql=hql+" and  q.fundType = 'pawnPrincipalRepayment'";
				}
				if(intentType.equals("2")){
					hql=hql+" and  q.fundType != 'pawnPrincipalLending' and q.fundType != 'pawnPrincipalRepayment'";
				}
			}else if("LeaseFinance".equals(intentType)){
					hql=hql+" and  q.fundType = '" + intentType + "' ";
			}
		}
		
		if(null != intentDatege&&!intentDatege.equals("")){
			hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
		}
		
		if(null != intentDatele&&!intentDatele.equals("")){
			hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
		}
		if(null != incomemoneyge && !incomemoneyge.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				hql=hql+" and  q.incomeMoney >= "+incomemoneyge+"";
			}else if(null!=businessType && businessType.equals("Financing")){
				hql=hql+" and  q.payMoney >= "+incomemoneyge+"";
			}
		}
		
		if(null != incomemoneyle && !incomemoneyle.equals("")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				hql=hql+" and  q.incomeMoney <= "+incomemoneyle+"";
			}else if(null!=businessType && businessType.equals("Financing")){
				hql=hql+" and  q.payMoney <= "+incomemoneyle+"";
			}
		}
		}else{
			//hql=hql+" and q.payMoney is null";
		}
		hql=hql+" order by q.intentDate asc";
	    Query query = getSession().createQuery(hql);
		
		 return  query.list();
	}

	@Override
	public BigDecimal getSumMoney(Long projectId, String businessType,
			String fundType) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String hql=" from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and ((s.factDate is null and s.intentDate<'"+sd.format(new Date())+"') or (s.factDate is not null and s.intentDate<s.factDate)) and s.notMoney!=0";
		List<SlFundIntent> list=getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).list();
		BigDecimal money=new BigDecimal(0);
		for(SlFundIntent s:list){
			if(null!=s.getNotMoney()){
				money=money.add(s.getNotMoney());
			}
		}
		return money;
	}
	@Override
	public List<SlFundIntent> getListByOperateId(Long projectId,
			String businessType, Long operateId, String status) {
		String hql="from SlFundIntent as f where f.projectId=? and f.businessType=? and f.operateId=? and f.isValid"+status;
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, operateId).list();
	}

	@Override
	public List<SlFundIntent> getListByEarlyOperateId(Long projectId,
			String businessType, Long earlyOperateId, String status) {
		String hql="from SlFundIntent as f where f.projectId=? and f.businessType=? and f.earlyOperateId=? and f.isValid"+status;
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, earlyOperateId).list();
	}

	@Override
	public List<SlFundIntent> getListByAlteraccrualOperateId(Long projectId,
			String businessType, Long alteraccrualOperateId, String status) {
		String hql="from SlFundIntent as f where f.projectId=? and f.businessType=? and f.alteraccrualOperateId=? and f.isValid"+status;
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, alteraccrualOperateId).list();
	}

	@Override
	public List<SlFundIntent> getListByFundType(Long projectId,
			String businessType, String fundType) {
		String hql="from SlFundIntent as f where f.projectId=? and f.businessType=? and f.fundType=? order by f.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).list();
	}
	@Override
	public List<SlFundIntent> getListByTypeAndDate(Long projectId,
			String businessType, String fundType, String intentDate) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and s.isValid=0 and s.intentDate"+intentDate+" order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).list();
	}

	@Override
	public List<SlFundIntent> getyqList(Long projectId, String businessType,
			String intentDate) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.isValid=0 and (s.intentDate<'"+intentDate+"' or s.intentDate<s.factDate)";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public List<SlFundIntent> getListByProjectId(Long projectId,
			String businessType) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.isValid=0 order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public List<SlFundIntent> getList(String fundType, String intentDate,String companyId) {
		String hql="from SlFundIntent as s where s.fundType=? and s.isValid=0 and s.isCheck=0 and s.intentDate<'"+intentDate+"'";
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			hql=hql+" and s.companyId in ("+str+")";
		}
		return getSession().createQuery(hql).setParameter(0, fundType).list();
	}

	@Override
	public List<SlFundIntent> getListByType(Long projectId,	String businessType, String fundType, String intentDate) {
		if("synthesize".equals(fundType)){
			int payintentPeriod = new Long(businessType).intValue();  
			String hql="from SlFundIntent as s where s.projectId=?  and s.payintentPeriod=? and s.intentDate='"+intentDate+"'";
			return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, (payintentPeriod)).list();
		}else{
			
			String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundType=? and s.isValid=0 and s.intentDate"+intentDate+" order by s.intentDate asc";
			return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundType).list();
		}
	}

	@Override
	public List<SlFundIntent> getByProjectAsc(Long projectId,String businessType,Long preceptId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=?";
		if(null!=preceptId){
			hql=hql+" and s.preceptId="+preceptId;
		}
		hql=hql+"   order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}
	/*@Override
	public List<SlFundIntent> getByProjectAsc(Long projectId,String businessType,String fundResource) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.fundResource=? order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, fundResource).list();
	}*/
	//用来查询出来充值还是取现的台账
	@Override
	public List<SlFundIntent> findAccountFundIntent(Long accountId,Long investPersonId, Long dealInfoId, int i) {
		// TODO Auto-generated method stub
		String hql =" from SlFundIntent as sl where sl.investPersonId=? and sl.systemAccountId=? and sl.accountDealInfoId=? and sl.afterMoney=0";
		if(i==0){//充值
			hql=hql+" and sl.fundType like '%accountRecharge%'";
		}else if(i==1){//取现
			hql=hql+" and sl.fundType like '%accountEnchashment%'";
		}
		return  getSession().createQuery(hql).setParameter(0, investPersonId).setParameter(1, accountId).setParameter(2, dealInfoId).list();
		}
	//查出已经债权人购买债权没有对账的款项信息
	@Override
	public List<SlFundIntent> getListByTreeIdUn(Long obligationId,
			Long investMentPersonId, Long id) {
		// TODO Auto-generated method stub
		String hql =" from SlFundIntent as sl where (sl.afterMoney=0 or sl.afterMoney=null )";
		if(obligationId!=0&&obligationId!=null&&!"".equals(obligationId)){
			hql=hql+" and sl.obligationId ="+obligationId;
		}if(investMentPersonId!=0&&investMentPersonId!=null&&!"".equals(investMentPersonId)){
			hql=hql+" and sl.investPersonId ="+investMentPersonId;
		}if(id!=0&&id!=null&&!"".equals(id)){
			hql=hql+" and sl.obligationInfoId ="+id;
		}
		hql =hql+" order by sl.investPersonId asc";
		List<SlFundIntent> list=getSession().createQuery(hql).list();
		return  getSession().createQuery(hql).list();
	}
	//查出债权人已经对过账的利息收益
	@Override
	public List<SlFundIntent> getListByTreeIdUn(Long obligationId,
			Long investMentPersonId, Long id, String investaccrual) {
		// TODO Auto-generated method stub
		String hql =" from SlFundIntent as sl where (sl.notMoney=0 or sl.notMoney=null )  and sl.fundType='"+investaccrual+"'";
		if(obligationId!=0&&obligationId!=null&&!"".equals(obligationId)){
			hql=hql+" and sl.obligationId ="+obligationId;
		}if(investMentPersonId!=0&&investMentPersonId!=null&&!"".equals(investMentPersonId)){
			hql=hql+" and sl.investPersonId ="+investMentPersonId;
		}if(id!=0&&id!=null&&!"".equals(id)){
			hql=hql+" and sl.obligationInfoId ="+id;
		}
		hql =hql+" order by sl.investPersonId asc";
		List<SlFundIntent> list=getSession().createQuery(hql).list();
		return  getSession().createQuery(hql).list();
	}
	 //查出一个债权人购买一个债权记录产生的款项信息
	@Override
	public List<SlFundIntent> getListByTreeId(Long projectId,
			Long investMentPersonId, Long id) {
		// TODO Auto-generated method stub
		String hql =" from SlFundIntent as sl where 1=1 ";
		if(projectId!=null&&projectId!=0&&!"".equals(projectId)){
			hql=hql+" and sl.obligationId ="+projectId;
		}if(investMentPersonId!=0&&investMentPersonId!=null&&!"".equals(investMentPersonId)){
			hql=hql+" and sl.investPersonId ="+investMentPersonId;
		}if(id!=0&&id!=null&&!"".equals(id)){
			hql=hql+" and sl.obligationInfoId ="+id;
		}
		hql =hql+" order by sl.investPersonId asc";
		List<SlFundIntent> list=getSession().createQuery(hql).list();
		return  getSession().createQuery(hql).list();
	}

	@Override
	public List<SlFundIntent> listOfInverstPerson(Long inverstPersonId,Long projectId, String businessType) {
		if("synthesize".equals(businessType)){
			int payintentPeriod = new Long(inverstPersonId).intValue();  
			String hql="from SlFundIntent as s where s.projectId=?  and s.payintentPeriod=?";
			return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, (payintentPeriod)).list();
		}else{
			String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.investPersonId=?";
			return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, inverstPersonId).list();
		}
	}

	@Override
	public List<SlFundIntent> getbyPreceptId(Long preceptId) {
		String hql="from SlFundIntent as s where s.preceptId=?  order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0,preceptId).list();
	}

	@Override
	public List<SlFundIntent> getByprincipalRepayment(Long projectId,
			String businessType) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=?  and s.fundType=? order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2,"principalRepayment").list();
	}

	@Override
	public List<SlFundIntent> getListByIntentDate(Date itentDate,java.lang.Short isUrge) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*		principalRepayment  本金偿还
		loanInterest		利息偿还
		consultationMoney	咨询管理费
		serviceMoney		服务费
*/
		Date today = new Date();
		if(isUrge==null){
			String hql = "from SlFundIntent where intentDate >= '"+sdf.format(itentDate)+" 00:00:00' and intentDate <= '"+sdf.format(itentDate)+" 23:59:59' and afterMoney = 0";
			return super.findByHql(hql);
		}else if(isUrge==0){
			String hql = "from SlFundIntent where intentDate >= '"+sdf.format(today)+" 00:00:00' and intentDate <= '"+sdf.format(itentDate)+" 23:59:59' and afterMoney = 0 and (isUrge = "+isUrge+" or isUrge is null)  and fundType in ('principalRepayment','loanInterest','consultationMoney','serviceMoney')";
			return super.findByHql(hql);
		}else{
			String hql = "from SlFundIntent where intentDate >= '"+sdf.format(itentDate)+" 00:00:00' and intentDate <= '"+sdf.format(itentDate)+" 23:59:59' and afterMoney = 0 and isUrge = "+isUrge;
			return super.findByHql(hql);
		}
		
	}
	

	@Override
	public SlFundIntent getFundIntent(Long projectId, Integer payintentPeriod,
			String fundType, Long bidPlanId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.payintentPeriod=? and s.fundType=? and s.bidPlanId=?";
		Object[] params={projectId,payintentPeriod,fundType,bidPlanId};
		return (SlFundIntent)findUnique(hql, params);
	}
	@Override
	public List<SlFundIntent> listByDateAndEarlyId(Long projectId,
			String businessType, String earlyDate, Long slEarlyRepaymentId,Long preceptId) {
		String hql="from SlFundIntent as f where f.projectId=? and f.businessType=? and f.preceptId=? and (f.slEarlyRepaymentId is null or f.slEarlyRepaymentId!=?) and f.intentDate>'"+earlyDate+"'";
		return this.findByHql(hql, new Object[]{projectId,businessType,preceptId,slEarlyRepaymentId});
	}
	@Override
	public BigDecimal getPrincipalMoney(Long projectId, String businessType,
			String earlyDate,Long preceptId) {
		String hql="select sum(f.incomeMoney) from SlFundIntent as f where f.isValid=0 and f.isCheck=0 and f.fundType='principalRepayment' and f.projectId=? and f.businessType=? and f.preceptId=? and f.intentDate<='"+earlyDate+"'";
		List list=getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, preceptId).list();
		BigDecimal money=new BigDecimal(0);
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				money=(BigDecimal) list.get(0);
			}
		}
		return money;
	}
	@Override
	public List<SlFundIntent> listByEarlyDate(Long projectId,String businessType,
			String earlyDate,String fundType,Long preceptId) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String hql="from SlFundIntent as f where f.projectId=? and f.businessType=? and f.fundType=? and f.preceptId=? and f.isValid=0 and f.isCheck=0 and f.interestStarTime<='"+earlyDate+"'";
		if(AppUtil.getInterest().equals("0")){
			hql=hql+" and f.interestEndTime>='"+sd.format(DateUtil.addDaysToDate(DateUtil.parseDate(earlyDate,"yyyy-MM-dd"), -1))+"'";
		}else{
			hql=hql+" and f.interestEndTime>='"+earlyDate+"'";
		}
		return this.findByHql(hql, new Object[]{projectId,businessType,fundType,preceptId});
	}

	@Override
	public List<SlFundIntent> listByAlelrtDate(Long projectId,
			String businessType, String alelrtDate, String fundType,
			Long preceptId, Long slAlteraccrualRecordId) {
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=? and s.intentDate>'"+alelrtDate+"' and (s.slAlteraccrualRecordId is null or s.slAlteraccrualRecordId!=?) and s.fundType in"+fundType;
		if(null!=preceptId && !preceptId.equals("")){
			hql=hql+" and s.preceptId="+Long.valueOf(preceptId);
		}
		return this.findByHql(hql, new Object[]{projectId,businessType,slAlteraccrualRecordId});
	}
	@Override
	public List<SlFundIntent> listByCurrentDate(String currentDate) {
		String hql="from SlFundIntent as s where s.intentDate<'"+currentDate+"' and s.isValid=0 and s.isCheck=0 and s.notMoney!=0  and s.fundType!='principalLending'";
		return this.findByHql(hql);
	}

	@Override
	public List<SlFundIntent> listByPreceptIdAndDate(Long projectId,
			String businessType,Long preceptId, String date, String fundType) {
		String hql="from SlFundIntent as s where s.isValid=0 and s.isCheck=0 and s.projectId=? and s.businessType=? and s.preceptId=? and s.fundType=? and s.intentDate>='"+date+"' order by s.intentDate asc";
		return this.findByHql(hql, new Object[]{projectId,businessType,preceptId,fundType});
	}
	
	@Override
	public List<SlFundIntent> getOverdueProjectId(Long projectId,String businessType) {
		String hql="";
		if(businessType==null){
			 hql = "from SlFundIntent s where s.projectId="+projectId+"  and s.isValid=0 and s.isCheck=0 and s.notMoney>0  and s.fundType !='principalLending'";
		}else{
			 hql = "from SlFundIntent s where s.projectId="+projectId+" and s.businessType='"+businessType+"' and s.isValid=0 and s.isCheck=0 and s.notMoney>0 and s.intentDate<NOW() and s.fundType !='principalLending'";
		}
		return findByHql(hql);
	}

	@Override
	public List<SlFundIntent> findSlSuperviseByFundType(Long projectId,
			Long slSuperviseRecordId, String businessType, String fundType) {
		// TODO Auto-generated method stub
		String hql="from SlFundIntent as s where s.projectId=? and s.businessType=?  and s.fundType=?  and s.slSuperviseRecordId=? order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2,fundType).setParameter(3, slSuperviseRecordId).list();
	}

	@Override
	public void listbyLedgerNew(PageBean<SlFundIntent> pageBean,Map<String,String> map) {

		List list=null;
		
		StringBuffer tempCount=new StringBuffer("SELECT count(*) FROM " +
				" (" +
				" SELECT intent.projectId" +
				" FROM sl_fund_intent AS intent" +
				" where fundType !='principalLending' AND intent.isCheck = 0 AND intent.isValid = 0 ");
		
		StringBuffer temp=new StringBuffer("select DISTINCT" +
				" pro.projectName,pro.projectNumber,intent.projectId,intent.payintentPeriod,intent.intentDate,intent.factDate,intent.punishDays,intent.isOverdue," +
				" (intent.principalAfterMoney + intent.interestAfterMoney+ intent.serviceAfterMoney+ intent.consultationAfterMoney + intent.principalFaxiAfterMoney+ ifnull(intent.interestFaxiAfterMoney, 0)+ ifnull(intent.consultationFaxiAfterMoney, 0)+ ifnull(intent.serviceFaxiAfterMoney, 0)) AS synthesizeAfterMoney," +//合计已对账金额
				" (intent.principalRepayment+intent.loanInterest+intent.serviceMoney+intent.consultationMoney+intent.principalAccrualMoney+intent.interestAccrualMoney+intent.serviceAccrualMoney+intent.consultationAccrualMoney" +
				"   -(intent.principalFlatMoney+intent.interestFlatMoney+intent.serviceFlatMoney+intent.consultationFlatMoney+IFNULL(s1.flatMoney,0))" +
				"	-(intent.principalAfterMoney + intent.interestAfterMoney+ intent.serviceAfterMoney+ intent.consultationAfterMoney + intent.principalFaxiAfterMoney + intent.interestFaxiAfterMoney + intent.consultationFaxiAfterMoney + intent.serviceFaxiAfterMoney)" +
				"  ) AS notSynthesizeMoney," +//合计未对账金额
				" (intent.principalFlatMoney+intent.interestFlatMoney+intent.serviceFlatMoney+intent.consultationFlatMoney+IFNULL(s1.flatMoney,0)) as sumFlatMoney," +//平账金额
				" (intent.principalAccrualMoney+intent.interestAccrualMoney+intent.serviceAccrualMoney+intent.consultationAccrualMoney) AS punishMoney," +//罚息金额
				" intent.principalRepayment,"+//本金
				" intent.loanInterest," +//利息
				" intent.serviceMoney," +//服务费
				" intent.consultationMoney," +//咨询管理费
				" (intent.principalRepayment+intent.loanInterest+intent.serviceMoney+intent.consultationMoney+intent.principalAccrualMoney+intent.interestAccrualMoney+intent.serviceAccrualMoney+intent.consultationAccrualMoney) as synthesizeMoney," +//合计金额
//				" intent.principalNotMoney," +//本金未对账金额
//				" intent.interestNotMoney,"+//利息未对账金额
//				" intent.serviceNotMoney,"+//服务费未对账金额
//				" intent.consultationNotMoney,"+//咨询管理费未对账金额
				" intent.fundIntentId,intent.businessType" +
				" from " +
				"  (" +
				"	 SELECT " +
				"		intent.projectId,intent.payintentPeriod,intent.fundType,intent.businessType," +
				"		SUM(CASE WHEN intent.fundType = 'principalRepayment' THEN ifnull(intent.incomeMoney, 0)ELSE 0 END) as principalRepayment," +//本金金额
				"		SUM(CASE WHEN intent.fundType = 'loanInterest' THEN ifnull(intent.incomeMoney, 0)ELSE 0 END) as loanInterest," +//利息金额
				"		SUM(CASE WHEN intent.fundType = 'serviceMoney' THEN ifnull(intent.incomeMoney, 0)ELSE 0 END) as serviceMoney," +//服务费金额
				"		SUM(CASE WHEN intent.fundType = 'consultationMoney' THEN ifnull(intent.incomeMoney, 0)ELSE 0 END) as consultationMoney," +//咨询管理费金额
				
				"		SUM(CASE WHEN intent.fundType = 'principalRepayment' THEN ifnull(intent.afterMoney, 0)ELSE 0 END) as principalAfterMoney," +//本金已对账金额
				"		SUM(CASE WHEN intent.fundType = 'loanInterest' THEN ifnull(intent.afterMoney, 0)ELSE 0 END) as interestAfterMoney," +//利息已对账金额
				"		SUM(CASE WHEN intent.fundType = 'serviceMoney' THEN ifnull(intent.afterMoney, 0)ELSE 0 END) as serviceAfterMoney," +//服务费已对账金额
				"		SUM(CASE WHEN intent.fundType = 'consultationMoney' THEN ifnull(intent.afterMoney, 0)ELSE 0 END) as consultationAfterMoney," +//咨询管理费已对账金额
				
//				" 		SUM(CASE WHEN intent.fundType = 'principalRepayment' THEN ifnull(intent.notMoney, 0)ELSE 0 END) as principalNotMoney,"+//本金未对账金额
//				" 		SUM(CASE WHEN intent.fundType = 'loanInterest' THEN ifnull(intent.notMoney, 0)ELSE 0 END) as interestNotMoney,"+//利息未对账金额
//				" 		SUM(CASE WHEN intent.fundType = 'serviceMoney' THEN ifnull(intent.notMoney, 0)ELSE 0 END) as serviceNotMoney,"+//服务费未对账金额
//				" 		SUM(CASE WHEN intent.fundType = 'consultationMoney' THEN ifnull(intent.notMoney, 0)ELSE 0 END) as consultationNotMoney,"+//咨询管理费未对账金额
				
				" 		SUM(CASE WHEN intent.fundType = 'principalRepayment' THEN ifnull(intent.accrualMoney, 0)ELSE 0 END) as principalAccrualMoney,"+//本金罚息金额
				" 		SUM(CASE WHEN intent.fundType = 'loanInterest' THEN ifnull(intent.accrualMoney, 0)ELSE 0 END) as interestAccrualMoney,"+//利息罚息金额
				" 		SUM(CASE WHEN intent.fundType = 'serviceMoney' THEN ifnull(intent.accrualMoney, 0)ELSE 0 END) as serviceAccrualMoney,"+//服务费罚息金额
				" 		SUM(CASE WHEN intent.fundType = 'consultationMoney' THEN ifnull(intent.accrualMoney, 0)ELSE 0 END) as consultationAccrualMoney,"+//咨询管理费罚息金额
				
				"		SUM(CASE WHEN intent.fundType = 'principalRepayment' THEN ifnull(intent.faxiAfterMoney, 0)ELSE 0 END) as principalFaxiAfterMoney," +//本金罚息已对账金额
				"		SUM(CASE WHEN intent.fundType = 'loanInterest' THEN ifnull(intent.faxiAfterMoney, 0)ELSE 0 END) as interestFaxiAfterMoney," +//利息罚息已对账金额
				"		SUM(CASE WHEN intent.fundType = 'serviceMoney' THEN ifnull(intent.faxiAfterMoney, 0)ELSE 0 END) as serviceFaxiAfterMoney," +//服务费罚息已对账金额
				"		SUM(CASE WHEN intent.fundType = 'consultationMoney' THEN ifnull(intent.faxiAfterMoney, 0)ELSE 0 END) as consultationFaxiAfterMoney," +//咨询管理费罚息已对账金额
				
				"		SUM(CASE WHEN intent.fundType = 'principalRepayment' THEN ifnull(intent.flatMoney, 0)ELSE 0 END) as principalFlatMoney," +//本金平账金额
				"		SUM(CASE WHEN intent.fundType = 'loanInterest' THEN ifnull(intent.flatMoney, 0)ELSE 0 END) as interestFlatMoney," +//利息平账金额
				"		SUM(CASE WHEN intent.fundType = 'serviceMoney' THEN ifnull(intent.flatMoney, 0)ELSE 0 END) as serviceFlatMoney," +//服务平账金额
				"		SUM(CASE WHEN intent.fundType = 'consultationMoney' THEN ifnull(intent.flatMoney, 0)ELSE 0 END) as consultationFlatMoney," +//咨询管理费平账金额
				
				"		max(intent.punishDays) AS punishDays," +
				"		intent.intentDate," +
				"		GROUP_CONCAT(intent.fundIntentId) as fundIntentId,intent.factDate," +
				"		IF(intent.intentDate < NOW(),1,0) AS isOverdue	" +
				"		FROM sl_fund_intent AS intent" +
				"		where fundType !='principalLending' AND intent.isCheck = 0 AND intent.isValid = 0");
		
		if(map.get("Q_proj_Name_N_EQ")!=null &&!"".equals(map.get("Q_proj_Name_N_EQ").toString())){
			temp.append(" and intent.projectName like '%"+map.get("Q_proj_Name_N_EQ").toString()+"%'");
			tempCount.append(" and intent.projectName like '%"+map.get("Q_proj_Name_N_EQ").toString()+"%'");
		}
		if(map.get("Q_projNum_N_EQ")!=null && !"".equals(map.get("Q_projNum_N_EQ").toString())){
			temp.append(" and intent.projectNumber like '%"+map.get("Q_projNum_N_EQ").toString()+"%'");
			tempCount.append(" and intent.projectNumber like '%"+map.get("Q_projNum_N_EQ").toString()+"%'");
		}
		if(map.get("Q_intentDate_D_GE")!=null && !"".equals(map.get("Q_intentDate_D_GE").toString())){
			temp.append(" and  intent.intentDate >= '"+map.get("Q_intentDate_D_GE").toString()+"'");
			tempCount.append(" and  intent.intentDate >= '"+map.get("Q_intentDate_D_GE").toString()+"'");
		}
		if(map.get("Q_intentDate_D_LE")!=null && !"".equals(map.get("Q_intentDate_D_LE").toString())){
			temp.append(" and  intent.intentDate <= '"+map.get("Q_intentDate_D_LE").toString()+"'");
			tempCount.append(" and  intent.intentDate <= '"+map.get("Q_intentDate_D_LE").toString()+"'");
		}
		if(map.get("startFactDate")!=null && !"".equals(map.get("startFactDate").toString()) ){
			temp.append(" and  intent.factDate >= '"+map.get("startFactDate").toString()+"'");
			tempCount.append(" and  intent.factDate >= '"+map.get("startFactDate").toString()+"'");
		}
		if(map.get("endFactDate")!=null && !"".equals(map.get("endFactDate").toString())){
			temp.append(" and  intent.factDate <= '"+map.get("endFactDate").toString()+"'");
			tempCount.append(" and  intent.factDate <= '"+map.get("endFactDate").toString()+"'");
		}
		/*if(map.get("flagMoney")!=null &&!"".equals(map.get("flagMoney").toString())){
			if(map.get("flagMoney").toString().equals("isMoney")){//已还款
				temp.append(" and intent.factDate is not null and intent.synthesizeAfterMoney is not null ");
				tempCount.append(" and intent.factDate is not null and intent.synthesizeAfterMoney is not null ");
			}else if(map.get("flagMoney").toString().equals("notMoney")){//未还款
				temp.append(" and intent.factDate is null and intent.synthesizeAfterMoney is null ");
				tempCount.append(" and intent.factDate is null and intent.synthesizeAfterMoney is null ");
			}else if(map.get("flagMoney").toString().equals("overdueMoney")){//已逾期
				temp.append(" and intent.isOverdue = 1 ");
				tempCount.append(" and intent.isOverdue = 1 ");
			}
		}*/
		tempCount.append(" GROUP BY intent.projectId,intent.payintentPeriod ) as b");
		
		temp.append(" GROUP BY intent.projectId,intent.payintentPeriod ");
		temp.append(" order by intent.intentDate asc LIMIT "+pageBean.getStart()+","+pageBean.getLimit());
		temp.append(" ) as intent");
		temp.append(" LEFT JOIN bp_fund_project AS pro ON intent.projectId = pro.projectId " +
					" LEFT JOIN sl_punish_interest as s1 on s1.fundIntentId=intent.fundIntentId" +
				    " ORDER BY intent.projectId,intent.payintentPeriod ASC");
//		System.out.println(temp);
		list = this.getSession().createSQLQuery(temp.toString())
			.addScalar("fundIntentId", Hibernate.LONG)//款项id
			.addScalar("projectId", Hibernate.LONG)//项目id
			.addScalar("projectNumber", Hibernate.STRING)//项目编号
			.addScalar("projectName", Hibernate.STRING)//项目名称
			.addScalar("businessType", Hibernate.STRING)//业务类型
			.addScalar("payintentPeriod",Hibernate.INTEGER)//款项期数
			.addScalar("punishMoney",Hibernate.BIG_DECIMAL)//罚息金额
			.addScalar("sumFlatMoney",Hibernate.BIG_DECIMAL)//平账金额
			.addScalar("synthesizeAfterMoney",Hibernate.BIG_DECIMAL)//已到账金额
			.addScalar("sumFlatMoney",Hibernate.BIG_DECIMAL)//平账金额
			.addScalar("punishDays", Hibernate.INTEGER)//罚息天数
			.addScalar("notSynthesizeMoney", Hibernate.BIG_DECIMAL)//未对账金额
			.addScalar("intentDate",Hibernate.DATE)//计划还款日
			.addScalar("factDate", Hibernate.DATE)//实际到账日期
			.addScalar("principalRepayment", Hibernate.BIG_DECIMAL)//本金
			.addScalar("loanInterest", Hibernate.BIG_DECIMAL)//利息
			.addScalar("serviceMoney", Hibernate.BIG_DECIMAL)//服务费
			.addScalar("consultationMoney", Hibernate.BIG_DECIMAL)//咨询管理费
			.addScalar("synthesizeMoney", Hibernate.BIG_DECIMAL)//合计金额
			.setResultTransformer(Transformers.aliasToBean(SlFundIntent.class)).list();
		pageBean.setResult(list);
		BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(tempCount.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}

	@Override
	public List<SlFundIntent> getComplexList(String projectId,String payintentPeriod, String businessType) {
		String hql = "from SlFundIntent s where s.projectId="+projectId+" and s.businessType='"+businessType+"' and (s.isValid = 0 and s.isCheck = 0) and s.payintentPeriod="+payintentPeriod;
		return findByHql(hql);
	}

	@Override
	public List<SlFundIntentPeriod> listexcelRepayInfo(Map<String, String> map) {

		StringBuffer sql =new StringBuffer("SELECT ");
		sql.append(" period.*,(period.principal + period.interest + period.manageMoney) as allTotalMoney from ");
		sql.append(" (SELECT  *, ");
		sql.append(" (SELECT COUNT(1) from invest_person_info as infos where infos.bidPlanId  = result.planId) as investNum, ");
		sql.append(" (select intentN.intentDate from bp_fund_intent intentN where intentN.bidPlanId = result.planId  ");
		sql.append(" AND intentN.payintentPeriod = result.payintentPeriod+1 LIMIT 1) as nestPayBankDate, ");
		sql.append(" IFNULL((SELECT SUM(intentB.incomeMoney) from bp_fund_intent intentB where intentB.bidPlanId = result.planId ");
		sql.append("  AND intentB.payintentPeriod = result.payintentPeriod AND intentB.fundType = 'principalRepayment' AND intentB.intentDate = result.intentDate),0) ");
		sql.append("  AS principal, ");
		sql.append(" IFNULL((SELECT SUM(intentL.incomeMoney) from bp_fund_intent intentL where ");
		sql.append(" intentL.bidPlanId = result.planId AND intentL.payintentPeriod = result.payintentPeriod AND intentL.fundType = 'loanInterest' AND intentL.intentDate = result.intentDate),0) ");
		sql.append(" AS interest, ");
		sql.append(" IFNULL((SELECT SUM(intentL.incomeMoney) FROM bp_fund_intent intentL  WHERE intentL.bidPlanId = result.planId AND intentL.payintentPeriod = result.payintentPeriod ");
		sql.append(" AND intentL.fundType = 'consultationMoney' AND intentL.intentDate = result.intentDate ),0 ");
		sql.append(" ) AS manageMoney, ");
		sql.append(" CASE result.payaccrualType ");
		sql.append(" WHEN 'yearPay' THEN CONCAT(result.planPeriod,'年') ");
		sql.append(" WHEN 'seasonPay' THEN  CONCAT(result.planPeriod,'个季度')  ");
		sql.append(" WHEN 'monthPay' THEN CONCAT(result.planPeriod,'个月')  ");
		sql.append(" WHEN 'dayPay' THEN CONCAT(result.planPeriod,'天') ");
		sql.append(" ELSE CONCAT(result.planPeriod * result.dayOfEveryPeriod,'天') ");
		sql.append(" END as term ");
		sql.append(" FROM ");
		sql.append(" (SELECT ");
		sql.append(" intentperiod.intentDate AS intentDate, ");
		sql.append(" intentperiod.bidPlanId AS planId, ");
		sql.append(" intentperiod.payintentPeriod AS payintentPeriod, ");
		sql.append(" intentperiod.loanerRepayMentStatus AS loanerRepayMentStatus,");
		sql.append(" CONCAT('第',intentperiod.payintentPeriod,'期') AS payintentPeriodShow, ");
		sql.append(" pbp.bidProName AS bidPlanName, ");
		sql.append(" FORMAT(pbp.bidMoney,0) AS bidMoney, ");
		sql.append(" CONCAT(pbp.yearInterestRate,'%') AS yearInterestRate, ");
		sql.append(" pbp.endIntentDate as endIntentDate, ");
		sql.append(" IFNULL(cpp.`NAME`,cep.enterprisename) AS borrowName, ");
		sql.append(" IFNULL(cpp.id, cep.id) AS borrowId, ");
		sql.append(" IFNULL(bcm.id, bcm0.id) AS ptpborrowId, ");
		sql.append(" IFNULL(bcm.truename, bcm0.truename) AS ptpborrowName, ");
		sql.append(" bfp.payintentPeriod as planPeriod, ");
		sql.append(" bfp.payaccrualType as payaccrualType, ");
		sql.append(" bfp.dayOfEveryPeriod as dayOfEveryPeriod, ");
		sql.append(" bfp.oppositeType AS oppositeType ");
		sql.append("FROM  (SELECT * FROM bp_fund_intent intentperiod1 ");
		String loanerRepayment = map.get("loanerRepayStatus").toString();
		if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("1")){//已返款
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0  AND intentperiod1.factDate is not NULL AND intentperiod1.afterMoney is not NULL And  intentperiod1.loanerRepayMentStatus = 1  AND intentperiod1.fundType != 'principalLending'	 ");// AND intentperiod1.loanerRepayMentStatus = 1
		}else if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("0")){//未返款
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0 AND intentperiod1.factDate is  NULL  AND intentperiod1.loanerRepayMentStatus = 1 AND intentperiod1.notMoney is not null AND intentperiod1.fundType != 'principalLending'	 ");// AND intentperiod1.loanerRepayMentStatus = 1
		}else if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("2")){//全选map.put("loanerRepayStatus", "0");
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0 AND intentperiod1.loanerRepayMentStatus = 1 AND intentperiod1.fundType != 'principalLending'	AND intentperiod1.loanerRepayMentStatus = 1 ");// AND intentperiod1.loanerRepayMentStatus = 1
		}else if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("3")){
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0  AND intentperiod1.fundType != 'principalLending'	AND intentperiod1.loanerRepayMentStatus is null");// AND intentperiod1.loanerRepayMentStatus = 1
		}else if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("4")){
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0  AND intentperiod1.fundType != 'principalLending' ");// AND intentperiod1.loanerRepayMentStatus = 1
		}else if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("5")){
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0  AND intentperiod1.fundType != 'principalLending'");// AND intentperiod1.loanerRepayMentStatus = 1
		}else if(loanerRepayment!=null&&!"".equals(loanerRepayment)&&loanerRepayment.equals("6")){
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0  AND intentperiod1.factDate is not NULL AND intentperiod1.afterMoney is not NULL And  intentperiod1.loanerRepayMentStatus = 1  AND intentperiod1.fundType != 'principalLending'");// AND intentperiod1.loanerRepayMentStatus = 1
		}else{
			sql.append(" WHERE  intentperiod1.isValid = 0 and intentperiod1.isCheck=0 AND intentperiod1.loanerRepayMentStatus = 1 AND intentperiod1.fundType != 'principalLending'	 ");// AND intentperiod1.loanerRepayMentStatus = 1
		}
		sql.append(" GROUP BY	intentperiod1.bidPlanId,	intentperiod1.intentDate) AS  intentperiod ");
		sql.append(" LEFT JOIN invest_person_info info ON intentperiod.orderNo = info.orderNo ");
		sql.append(" LEFT JOIN pl_bid_plan pbp ON intentperiod.bidPlanId = pbp.bidId ");
		sql.append(" LEFT JOIN bp_fund_project bfp ON bfp.id = intentperiod.preceptId ");
		sql.append(" LEFT JOIN bp_cust_member bcm0 ON bcm0.loginname = bfp.receiverP2PAccountNumber ");
		sql.append(" LEFT JOIN cs_person cpp ON (cpp.id = bfp.oppositeID AND bfp.oppositeType = 'person_customer')");
		sql.append(" LEFT JOIN cs_enterprise cep ON (cep.id = bfp.oppositeID AND bfp.oppositeType = 'company_customer')");
		sql.append(" LEFT JOIN bp_cust_member bcm ON bcm.loginname = pbp.receiverP2PAccountNumber where 1=1 ");
		sql.append(" AND intentperiod.fundType not in('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest') ");


		String subType=map.get("subType");
		if(null !=subType && "online".equals(subType)){
			sql.append(" and bfp.loanId is not null ");
		}else if(null !=subType && "underline".equals(subType)){
			sql.append(" and bfp.loanId is null ");
		}

		String bidType=map.get("bidPlanType");
		if(null !=bidType&&!bidType.equals("")){
			if(bidType.equals("OR")){
				sql.append(" and pbp.proType in ('B_Or','P_Or') ");
			}else if(bidType.equals("DIR")){
				//按标的类型查询
				String proType=map.get("proType");
				if(null !=proType && !"".equals(proType)){
					sql.append(" and pbp.proType='"+proType+"'");
				}else{
					sql.append(" and pbp.proType in ('B_Dir','P_Dir') ");
				}
			}
		}
		String authorizationStatus = map.get("authorizationStatus");
		if(null != authorizationStatus && !"".equals(authorizationStatus)){
			sql.append(" and pbp.authorizationStatus="+authorizationStatus);
		}
		String projectName=map.get("projectName");
		if(null !=projectName&&!projectName.equals("")){
			sql.append(" and bfp.projectName like '%/"+projectName+"%'  escape '/' ");
		}
		String bidPlanProjectNum=map.get("bidPlanProjectNum");
		if(null !=bidPlanProjectNum&&!bidPlanProjectNum.equals("")){
			sql.append(" and pbp.bidProNumber  like '%/"+bidPlanProjectNum+"%'  escape '/' ");
		}
		String bidPlanName=map.get("bidPlanName");
		if(null !=bidPlanName&&!bidPlanName.equals("")){
			sql.append(" and pbp.bidProName  like '%/"+bidPlanName+"%'  escape '/' ");
		}

		String intentDateg=map.get("intentDateg");
		if(null !=intentDateg&&!intentDateg.equals("")){
			sql.append(" and intentperiod.intentDate <= '").append(intentDateg).append("'");
		}
		String intentDatel=map.get("intentDatel");
		if(null !=intentDatel&&!intentDatel.equals("")){
			sql.append(" and intentperiod.intentDate >= '").append(intentDatel).append("'");
		}
		String factDateg=map.get("factDateg");
		if(null !=factDateg&&!factDateg.equals("")){
			sql.append(" and intentperiod.factDate <= '").append(factDateg).append(" 23:59:59'");
		}
		String factDatel=map.get("factDatel");
		if(null !=factDatel&&!factDatel.equals("")){
			sql.append(" and intentperiod.factDate >= '").append(factDatel).append(" 00:00:00'");
		}
		String isPay=map.get("isPay");
		if(null ==isPay|| isPay.equals("")){
			sql.append(" and intentperiod.afterMoney =0 ");
		}

		if(null !=isPay&&!isPay.equals("")&&!isPay.equals("all")){
			if(isPay.equals("notPay")){
				sql.append(" and intentperiod.afterMoney =0 ");
			}
			if(isPay.equals("payed")){
				sql.append( " and intentperiod.afterMoney !=0 ");
			}
			if(isPay.equals("none")){
				sql.append(" and 1=2 ");
			}
		}
		sql.append(") as result) AS period ");
		System.out.println("sql="+sql.toString());
		List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapper());

		return list;
	}

	class  rowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlFundIntentPeriod income = new SlFundIntentPeriod();
			income.setIntentDate(rs.getDate("intentDate"));
			income.setPlanId(rs.getLong("planId"));
			income.setPayintentPeriod(rs.getInt("payintentPeriod"));
			income.setPayintentPeriodShow(rs.getString("payintentPeriodShow"));
			income.setBidPlanName(rs.getString("bidPlanName"));
			income.setBidMoney(rs.getString("bidMoney"));
			income.setYearInterestRate(rs.getString("yearInterestRate"));
			income.setEndIntentDate(rs.getDate("endIntentDate"));
			income.setBorrowId(rs.getLong("borrowId"));
			income.setBorrowName(rs.getString("borrowName"));
			income.setPtpborrowId(rs.getLong("ptpborrowId"));
			income.setPtpborrowName(rs.getString("ptpborrowName"));
			income.setPlanPeriod(rs.getInt("planPeriod"));
			income.setPayaccrualType(rs.getString("payaccrualType"));
			income.setOppositeType(rs.getString("oppositeType"));
			income.setTerm(rs.getString("term"));
			income.setDayOfEveryPeriod(rs.getInt("dayOfEveryPeriod"));
			income.setLoanerRepayMentStatus(rs.getInt("loanerRepayMentStatus"));
			income.setAllTotalMoney(rs.getBigDecimal("allTotalMoney"));
			income.setPrincipal(rs.getBigDecimal("principal"));
			income.setInterest(rs.getBigDecimal("interest"));
			income.setManageMoney(rs.getBigDecimal("manageMoney"));
			income.setInvestNum(rs.getInt("investNum"));
			income.setNestPayBankDate(rs.getDate("nestPayBankDate"));
			income.setLoanerRepayMentStatus(rs.getInt("loanerRepayMentStatus"));
			return income;
		}

	}
}

