package com.zhiwei.credit.dao.creditFlow.finance.impl;


import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.customer.InvestPersonInfoDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.BpFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

@SuppressWarnings("unchecked")
public class BpFundIntentDaoImpl extends BaseDaoImpl<BpFundIntent> implements
		BpFundIntentDao {
	@Resource
	private InvestPersonInfoDao investPersonInfoDao;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	public BpFundIntentDaoImpl() {
		super(BpFundIntent.class);
	}

	@Override
	public List<BpFundIntent> getListBysql(HttpServletRequest request,Integer start ,Integer limit ) {
		// TODO Auto-generated method stub
		String sql="SELECT  "+
					"p.fundIntentId,"+
					"p.fundType," +
					"dic.itemValue as fundTypeName,"+
				    "p.intentDate,"+
				    "p.payMoney,"+
				    "p.incomeMoney,"+
				    "p.notMoney,"+
				    "p.afterMoney," +
				    "p.flatMoney,"+
				    "p.payintentPeriod,"+
				    "p.investPersonId,"+
				    "p.investPersonName,"+
					"p.bidPlanId as bidPlanId,"+
					"plan.proType as protype,"+
					"p.projectName  as projectName," +
					"p.fundResource," +

					//"p.status," +  数据库无此字段 

					"p.remark  "+
					"FROM  "+
					"bp_fund_intent AS p  " +
					"left join dictionary_independent as dic on dic.dicKey=p.fundType,"+
					"pl_bid_plan AS plan  "+
					" where p.isCheck=0 and p.isValid =0 " +
					" and p.bidPlanId=plan.bidId and p.fundType in('loanInterest','principalRepayment')";
		
		if(request!=null){
			String investPersonId=request.getParameter("investpersonId");
			String planId=request.getParameter("planId");
			if(investPersonId!=null&&null!=planId){
				String hql = "from InvestPersonInfo as ipi where ipi.investPersonId = ? and ipi.bidPlanId = ?";
				List<InvestPersonInfo> list = investPersonInfoDao.findByHql(hql, new Object[]{Long.parseLong(investPersonId),Long.parseLong(planId)});
				StringBuffer buff = new StringBuffer();
				if(list!=null){
					for(InvestPersonInfo info:list){
						if(null!=info){
							buff.append(info.getInvestPersonId()).append(",");
						}
					}
					String str = buff.toString();
					if(str.length()>0){
						str = str.substring(0, str.length()-1);
						sql=sql+" and p.investPersonId in ("+str+")";	
					}
				}
				//sql=sql+" and p.investPersonId="+Long.valueOf(investPersonId);
				//数据库表：bp_fund_intent表中investPersonId 对应的是invset_person_info中的investId
				//故需要读取invest_person_info表
			}
			
			
			if(planId!=null){
				sql=sql+" and p.bidPlanId="+Long.valueOf(planId);
			}
		}
		List list=null;
		if(start==null || limit ==null){
			list=this.getSession().createSQLQuery(sql).
			addScalar("fundIntentId", Hibernate.LONG).
			 addScalar("fundType", Hibernate.STRING).
			 addScalar("fundTypeName", Hibernate.STRING).
			 addScalar("intentDate", Hibernate.DATE).
			 addScalar("payMoney", Hibernate.BIG_DECIMAL).
			 addScalar("flatMoney", Hibernate.BIG_DECIMAL).
			 addScalar("incomeMoney", Hibernate.BIG_DECIMAL).
			 addScalar("notMoney", Hibernate.BIG_DECIMAL).
			 addScalar("afterMoney", Hibernate.BIG_DECIMAL).
			 addScalar("payintentPeriod", Hibernate.INTEGER).
			 addScalar("investPersonId", Hibernate.LONG).
			 addScalar("investPersonName", Hibernate.STRING).
			 addScalar("protype", Hibernate.STRING).
			 addScalar("bidPlanId", Hibernate.LONG).
			 addScalar("protype", Hibernate.STRING).
			 addScalar("projectName", Hibernate.STRING).
			 addScalar("fundResource", Hibernate.STRING).
			 addScalar("remark", Hibernate.STRING).
			 setResultTransformer(Transformers.aliasToBean(BpFundIntent.class)).list();
		}else{
			list=this.getSession().createSQLQuery(sql).
				 addScalar("fundIntentId", Hibernate.LONG).
				 addScalar("fundType", Hibernate.STRING).
				 addScalar("fundTypeName", Hibernate.STRING).
				 addScalar("intentDate", Hibernate.DATE).
				 addScalar("payMoney", Hibernate.BIG_DECIMAL).
				 addScalar("flatMoney", Hibernate.BIG_DECIMAL).
				 addScalar("incomeMoney", Hibernate.BIG_DECIMAL).
				 addScalar("notMoney", Hibernate.BIG_DECIMAL).
				 addScalar("afterMoney", Hibernate.BIG_DECIMAL).
				 addScalar("payintentPeriod", Hibernate.INTEGER).
				 addScalar("investPersonId", Hibernate.LONG).
				 addScalar("investPersonName", Hibernate.STRING).
				 addScalar("protype", Hibernate.STRING).
				 addScalar("bidPlanId", Hibernate.LONG).
				 addScalar("protype", Hibernate.STRING).
				 addScalar("projectName", Hibernate.STRING).
				 addScalar("projectName", Hibernate.STRING).
				 addScalar("fundResource", Hibernate.STRING).
				 setResultTransformer(Transformers.aliasToBean(BpFundIntent.class)).
				 setFirstResult(start).
				 setMaxResults(limit).
			     list();
		}
		return list;
	}

	@Override
	public List<BpFundIntent> getByPlanIdA(Long bidId, Long preceptId,
			Long investPersonId, String fundType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BpFundIntent> getByPlanIdAndPeriod(Long bidId,
			Integer payintentPeriod, String fundType, Long personId,String orderNum) {
		String hql1 = "from BpFundIntent as bf where bf.bidPlanId = ? and bf.payintentPeriod = ? and bf.fundType=? and bf.investPersonId=? and bf.orderNo=?";
		List<BpFundIntent> list = findByHql(hql1, new Object[]{bidId,payintentPeriod,fundType,personId,orderNum});
		return list;
	}

	@Override
	public List<BpFundIntent> listOfInverstPerson(String orderNo,String fundTypes) {
		// TODO Auto-generated method stub
		String hql1 = "from BpFundIntent as bf where bf.orderNo = ? ";
		if(!fundTypes.isEmpty()){
			hql1=hql1+" and bf.fundType in("+fundTypes+") ";
			List<BpFundIntent> list = findByHql(hql1, new Object[]{orderNo});
			return list;
		}else{
			
			List<BpFundIntent> list = findByHql(hql1, new Object[]{orderNo});
			return list;
		}
		
		
	}

	@Override
	public List<BpFundIntent> listbyLedger(String businessType,
			String fundType, String typetab, PagingBean pb,
			Map<String, String> map) {
		String hql="from BpFundIntent q where  1=1";
		/*		String strs=ContextUtil.getBranchIdsStr();//39,40
				if(null!=strs && !"".equals(strs)){
					hql+=" and q.companyId in ("+strs+")"; //
				}*/
				hql+=" and  q.isValid = 0 and q.isCheck = 0 and q.businessType='"+businessType+"' and q.fundType in "+fundType;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
				String str = simpleDateFormat.format(new Date());
				if(fundType.equals("principalLending")||fundType.equals("constBack")){
				    if(typetab.equals("should")){
			        	hql=hql+" and q.notMoney !=0";
					}
			       
			        if(typetab.equals("actual")){
			        	hql=hql+" and  q.notMoney =0";
					}
				}else{
			        if(typetab.equals("should")){
			        	hql=hql+" and  q.intentDate >= '"+str+"' and q.notMoney !=0"; //计划日期大于或等于今天
					}
			        if(typetab.equals("owe")){
			        	hql=hql+" and  q.intentDate < '"+str+"' and q.notMoney !=0";
					}
			        if(typetab.equals("actual")){
			        	hql=hql+" and  q.notMoney =0";
					}
		        }
				if(map.size()!=5){
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
			/*	if(pb == null) {
					return findByHql(hql.toString());
				} else {
					return findByHql(hql.toString(), null, pb);
				}*/
				return findByHql(hql.toString());
			}

	@Override
	public void saveFundIntent(String fundIntentJson, PlBidPlan plan,BpFundProject project,Short isCheck) {
		try{
			if (null != fundIntentJson && !"".equals(fundIntentJson)) {
				List<BpFundIntent> oldList = this.listByBidPlanId(plan.getBidId());
				for (BpFundIntent fi : oldList) {
					if (fi.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						this.remove(fi);
					}
				}
				String[] fundIntentJsonArr = fundIntentJson.split("@");
				for (int i = 0; i < fundIntentJsonArr.length; i++) {
					String fundIntentstr = fundIntentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(fundIntentstr));
			
					BpFundIntent bfi = (BpFundIntent) JSONMapper.toJava(parser.nextValue(), BpFundIntent.class);
					bfi.setProjectId(project.getProjectId());
					bfi.setProjectName(project.getProjectName());
					bfi.setProjectNumber(project.getProjectNumber());
					bfi.setPreceptId(project.getId());
					bfi.setBidPlanId(plan.getBidId());
					bfi.setBusinessType(project.getBusinessType());
					bfi.setCompanyId(project.getCompanyId());
					Short isvalid = 0;
					bfi.setIsValid(isvalid);
					bfi.setIsCheck(isCheck);
					if (null == bfi.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);
						if (bfi.getIncomeMoney().compareTo(lin) == 0) {
							bfi.setNotMoney(bfi.getPayMoney());
						} else {
							bfi.setNotMoney(bfi.getIncomeMoney());
						}
						bfi.setAfterMoney(new BigDecimal(0));
						bfi.setAccrualMoney(new BigDecimal(0));
						bfi.setFlatMoney(new BigDecimal(0));
						
						this.save(bfi);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (bfi.getIncomeMoney().compareTo(lin) == 0) {
							bfi.setNotMoney(bfi.getPayMoney());
						} else {
							bfi.setNotMoney(bfi.getIncomeMoney());
						}
						BpFundIntent bf = this.get(bfi.getFundIntentId());
						BeanUtil.copyNotNullProperties(bf,bfi);
						this.merge(bf);

					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public List<BpFundIntent> getByBidPlanId(Long bidPlanId) {
		String hql="from BpFundIntent as f where f.bidPlanId=?";
		return this.findByHql(hql,new Object[]{bidPlanId});
	}

	@Override
	public List<BpFundIntent> listByBidPlanId(Long bidPlanId) {
		String hql="from BpFundIntent as f where f.bidPlanId=? and f.isValid!=1";
		return this.findByHql(hql,new Object[]{bidPlanId});
	}

	@Override
	public BigDecimal getByPlanIdAndOtherRequest(String temp, String planId,
			String intentDate, String type) {
		String sql= "select SUM(bp.notMoney+IFNULL(bp.accrualMoney,0)) from BpFundIntent  as bp where bp.bidPlanId = ? " +
	    "and bp.orderNo=? and bp.isCheck=0 and bp.isValid=0 and bp.fundResource is not null and intentDate='"+intentDate+"'";
		if(type!=null){
		sql=sql+" and bp.fundType in "+type;
		}
		System.out.println("sql为"+sql);
		return (BigDecimal) this.getSession().createQuery(sql).setParameter(0, Long.valueOf(planId))
				.setParameter(1, temp).uniqueResult();
}

	@Override
	public List<BpFundIntent> listbyOwe(String businessType,
			List<String> fundtypelist, Date date) {
		/*String hql="from BpFundIntent";
		return this.getSession().createQuery(hql).list();*/
		String hql="from BpFundIntent q where  q.fundType in (:fundtypelist) and q.isValid = 0 and q.isCheck = 0 and q.businessType=:businessType and  q.intentDate < now() and q.notMoney !=0 ";
		System.out.println("计算的sql"+hql);
		return this.getSession().createQuery(hql).setParameterList("fundtypelist", fundtypelist).setParameter("businessType", businessType).list();
	}

	
	@Override
	public List<BpFundIntent> getByRequestNo(String requestNo) {
		String sql = "from BpFundIntent as fund where fund.requestNo=?";
		return this.getSession().createQuery(sql).setParameter(0, requestNo).list();
	}
	@Override
	public List<BpFundIntent> getCouponsIntent(String planId, String peridId,String requestNo,String fundType) {
		String sql = "";
		if(fundType!=null&&!fundType.equals("")&&requestNo!=null){
			 sql = " from BpFundIntent as fund where fund.bidPlanId=? and fund.payintentPeriod=? and fund.requestNo=? and fund.fundType=? and fund.factDate is  null and fund.isValid=0";
				return this.getSession().createQuery(sql).setParameter(0, Long.valueOf(planId)).setParameter(1, Integer.valueOf(peridId)).setParameter(2, requestNo).setParameter(3, fundType).list();
		}else if(fundType!=null&&!fundType.equals("")&&requestNo==null){
			if(fundType.equals("notCommoninterest")){
				sql = " from BpFundIntent as fund where fund.bidPlanId=? and fund.payintentPeriod=?  and fund.fundType in ('couponInterest','principalCoupons','subjoinInterest')";
				return this.getSession().createQuery(sql).setParameter(0, Long.valueOf(planId)).setParameter(1, Integer.valueOf(peridId)).list();

			}else{
				sql = " from BpFundIntent as fund where fund.bidPlanId=? and fund.payintentPeriod=?  and fund.fundType=? ";
				return this.getSession().createQuery(sql).setParameter(0, Long.valueOf(planId)).setParameter(1, Integer.valueOf(peridId)).setParameter(2, fundType).list();
			}
		}else{
			 sql = " from BpFundIntent as fund where fund.bidPlanId=? and fund.payintentPeriod=? and fund.requestNo=? and fund.factDate is  null ";
			return this.getSession().createQuery(sql).setParameter(0, Long.valueOf(planId)).setParameter(1, Integer.valueOf(peridId)).setParameter(2, requestNo).list();
		}
	
	}
	@Override
	public List<SlFundIntentPeriod> listByBidPlanIdAndpayintentPeriod( PagingBean pb,
			Map<String, String> map) {
		StringBuffer sql =new StringBuffer("SELECT ");
		sql.append(" intentperiod.intentDate AS intentDate, ");
		sql.append(" intentperiod.factDate AS factDate, ");
		sql.append(" intentperiod.bidPlanId AS planId, ");
		sql.append(" intentperiod.payintentPeriod AS payintentPeriod, ");
		sql.append(" intentperiod.repaySource AS repaySource, ");
		sql.append(" intentperiod.punishDays AS punishDays, ");
		sql.append(" pbp.bidProNumber AS bidPlanProjectNum, ");
		sql.append(" pbp.bidProName AS bidPlanName, ");
		sql.append(" bcm0.id AS receiverP2PId, ");
		sql.append(" bfp.receiverP2PAccountNumber AS receiverP2PName, ");
		sql.append(" bfp.receiverName AS receiverName, ");
		sql.append(" pbp.bidProName AS bidPlanName, ");
		sql.append(" IFNULL(cpp. NAME, cep.enterprisename) AS borrowName, ");
		sql.append(" IFNULL(cpp.id, cep.id) AS borrowId, ");
		sql.append(" IFNULL(bcm.id, bcm0.id) AS ptpborrowId, ");
		sql.append(" IFNULL(bcm.truename,bcm0.truename) AS ptpborrowName, ");
		sql.append(" pbp.authorizationStatus AS authorizationStatus, ");
		sql.append(" bfp.oppositeType AS oppositeType, ");
		sql.append(" intentperiod.loanerRepayMentStatus AS loanerRepayMentStatus FROM ");
		sql.append(" (SELECT * FROM bp_fund_intent intentperiod1 ");
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
		System.out.println("sql="+sql);
		/*sql.append(" GROUP BY"+
		"	intentperiod.bidPlanId,"+
		"	intentperiod.intentDate ) as a ");*/
		if(null==pb){
		/*	setObjectTotalItems(sql.toString(),null);
			return findObjectList(sql.toString(),null,null,SlFundIntentPeriod.class);*/
			
			List<SlFundIntentPeriod>	list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
			  return list;
		}else{
		//	setObjectTotalItems(sql.toString(),pb);
		//	return findObjectList(sql.toString(),null,pb,SlFundIntentPeriod.class);
			sql.append(" limit "+pb.getStart()+","+(pb.getPageSize()));
			System.out.println("sql1="+sql);
			//System.out.println("sqlsqlsqlsql===="+sql);
			List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
			  return list;
		}
	
	}
	class  rowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlFundIntentPeriod income = new SlFundIntentPeriod();
			income.setIntentDate(rs.getDate("intentDate"));
			income.setFactDate(rs.getDate("factDate"));
			income.setPlanId(rs.getLong("planId"));
			income.setPayintentPeriod(rs.getInt("payintentPeriod"));
			income.setPlanId(rs.getLong("planId"));
			income.setBidPlanName(rs.getString("bidPlanName"));
			income.setReceiverP2PId(rs.getLong("receiverP2PId"));
			income.setReceiverP2PName(rs.getString("receiverP2PName"));
			income.setReceiverName(rs.getString("receiverName"));
			income.setBidPlanProjectNum(rs.getString("bidPlanProjectNum"));
			income.setBorrowId(rs.getLong("borrowId"));
			income.setBorrowName(rs.getString("borrowName"));
			income.setPtpborrowId(rs.getLong("ptpborrowId"));
			income.setPtpborrowName(rs.getString("ptpborrowName"));
			income.setRepaySource(rs.getShort("repaySource"));
			income.setPunishDays(rs.getInt("punishDays"));
			income.setAuthorizationStatus(rs.getShort("authorizationStatus"));
			income.setOppositeType(rs.getString("oppositeType"));
			income.setLoanerRepayMentStatus(rs.getInt("loanerRepayMentStatus"));
			return income;
		}
		
	}
	
	@Override
	public List<SlFundIntentPeriod> getCouponsList( PagingBean pb,
			Map<String, String> map) {
		String fundType = map.get("fundType");
		if(fundType.equals("notCommoninterest")){
			fundType="('couponInterest','principalCoupons','subjoinInterest')";
		}else{
			fundType="('commoninterest')";
		}
		StringBuffer sql =new StringBuffer("select pbp.bidProName as bidProName,pbp.bidProNumber as bidProNumber," +
				" pbp.rebateType as rebateType,pbp.rebateWay as rebateWay,intentperiod.sumMoney as sumMoney," +
				" intentperiod.payintentPeriod as payPeriod,intentperiod.bidPlanId as bidPlanId," +
				" intentperiod.intentDate as intentDate,intentperiod.factDate as factDate,intentperiod.fundType as fundType,intentperiod.sumAfterMoney as sumAfterMoney" +
				" FROM(SELECT *,SUM(bp.incomeMoney) as sumMoney,SUM(bp.afterMoney)  as sumAfterMoney FROM bp_fund_intent bp WHERE bp.isValid = 0 AND bp.isCheck = 0 " +
				" AND bp.fundType  in "+fundType+"  GROUP BY bp.bidPlanId,bp.intentDate) AS intentperiod " +
				" LEFT JOIN pl_bid_plan pbp ON intentperiod.bidPlanId = pbp.bidId where 1=1 ");
	if(fundType.equals("('commoninterest')")){
		sql.append(" and pbp.addRate>0 ");
	}else{
		sql.append(" and pbp.coupon=1 ");
	}	
	String bidName=map.get("bidName");
	if(null !=bidName&&!bidName.equals("")){
		sql.append(" and pbp.bidProName like '%"+bidName+"%' ");
	}
	String bidProNumber=map.get("bidProNumber");
	if(null !=bidProNumber&&!bidProNumber.equals("")){
		sql.append(" and pbp.bidProNumber  like '%"+bidProNumber+"%' ");
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
	String rebateType=map.get("rebateType");
	if(null !=rebateType&&!rebateType.equals("")){
		sql.append(" and pbp.rebateType = ").append(rebateType);
	}
	String rebateWay=map.get("rebateWay");
	if(null !=rebateWay&&!rebateWay.equals("")){
		sql.append(" and pbp.rebateWay = ").append(rebateWay);
	}
	System.out.println(sql);
	if(null==pb){
		List<SlFundIntentPeriod>	list = this.jdbcTemplate.query(sql.toString(),new rowMapperCoupons());
		  return list;
	}else{
		sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize()));
		List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapperCoupons());
		  return list;
	}
	
	}
	class  rowMapperCoupons implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlFundIntentPeriod income = new SlFundIntentPeriod();
			income.setPlanId(rs.getLong("bidPlanId"));
			income.setIntentDate(rs.getDate("intentDate"));
			income.setFactDate(rs.getDate("factDate"));
		    income.setBidPlanName(rs.getString("bidProName"));
		    income.setBidPlanProjectNum(rs.getString("bidProNumber"));
		    income.setRebateType(rs.getString("rebateType"));
		    income.setRebateWay(rs.getString("rebateWay"));
		    income.setPayintentPeriod(rs.getInt("payPeriod"));
		    income.setFundType(rs.getString("fundType"));
		    income.setIncomeMoney(rs.getBigDecimal("sumMoney"));
		    income.setAfterMoney(rs.getBigDecimal("sumAfterMoney"));
			return income;
		}
		
	}
	@Override
	public List<SlFundIntentPeriod> getRaiseinterestList( PagingBean pb,
			Map<String, String> map) {
		String fundType = map.get("fundType");
		StringBuffer sql =new StringBuffer("select pbp.bidId as bidPlanId,pbp.bidProName as bidProName,pbp.bidProNumber as bidProNumber," +
				" bp.intentDate as intentDate,bp.factDate as factDate,bp.fundType,SUM(incomeMoney) sumMoney,pbp.raiseRate as raiseRate,SUM(bp.afterMoney)  as sumAfterMoney" +
				" FROM bp_fund_intent bp LEFT JOIN pl_bid_plan pbp ON bp.bidPlanId = pbp.bidId " +
				" left join pl_bid_info as pl on pl.orderNo=bp.orderNo where bp.isValid = 0		AND bp.isCheck = 0" +
				" AND bp.fundType  in('raiseinterest') ");
		String bidName=map.get("bidName");
		if(null !=bidName&&!bidName.equals("")){
			sql.append(" and pbp.bidProName like '%"+bidName+"%' ");
		}
		String bidProNumber=map.get("bidProNumber");
		if(null !=bidProNumber&&!bidProNumber.equals("")){
			sql.append(" and pbp.bidProNumber  like '%"+bidProNumber+"%' ");
		}
		String intentDateg=map.get("intentDateg");
		if(null !=intentDateg&&!intentDateg.equals("")){
			sql.append(" and bp.intentDate <= '").append(intentDateg).append("'");
		}
		String intentDatel=map.get("intentDatel");
		if(null !=intentDatel&&!intentDatel.equals("")){
			sql.append(" and bp.intentDate >= '").append(intentDatel).append("'");
		}
		String factDateg=map.get("factDateg");
		if(null !=factDateg&&!factDateg.equals("")){
			sql.append(" and bp.factDate <= '").append(factDateg).append(" 23:59:59'");
		}
		String factDatel=map.get("factDatel");
		if(null !=factDatel&&!factDatel.equals("")){
			sql.append(" and bp.factDate >= '").append(factDatel).append(" 00:00:00'");
		}
		sql.append(" GROUP BY pbp.bidId");
	//	System.out.println(sql.toString());
		if(null==pb){
			List<SlFundIntentPeriod>	list = this.jdbcTemplate.query(sql.toString(),new rowMapperRaise());
			return list;
		}else{
			sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize()));
			List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapperRaise());
			return list;
		}
		
	}
	class  rowMapperRaise implements RowMapper {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlFundIntentPeriod income = new SlFundIntentPeriod();
			income.setPlanId(rs.getLong("bidPlanId"));
			income.setIntentDate(rs.getDate("intentDate"));
			income.setFactDate(rs.getDate("factDate"));
			income.setBidPlanName(rs.getString("bidProName"));
			income.setBidPlanProjectNum(rs.getString("bidProNumber"));
			income.setFundType(rs.getString("fundType"));
			income.setIncomeMoney(rs.getBigDecimal("sumMoney"));
			income.setRaiseRate(rs.getString("raiseRate"));
			income.setAfterMoney(rs.getBigDecimal("sumAfterMoney"));
			return income;
		}
		
	}
	
	@Override
	public List<BpFundIntentPeriod> getRaiseBpfundIntent(Long bidId) {
		StringBuffer sql =new StringBuffer("select  bp.investpersonName as investpersonName,bp.incomeMoney as incomeMoney,bp.intentDate as intentDate,bp.factDate as factDate," +
				" bp.fundType as fundType,pl.bidtime as bidtime,pbp.startIntentDate as startIntentDate," +
				" TO_DAYS(pbp.startIntentDate) - TO_DAYS(pl.bidtime) AS days FROM bp_fund_intent bp " +
				" LEFT JOIN pl_bid_plan pbp ON bp.bidPlanId = pbp.bidId left join pl_bid_info as pl on pl.orderNo=bp.orderNo " +
				" where bp.isValid = 0 	AND bp.isCheck = 0 AND bp.fundType  in('raiseinterest') and pbp.bidId="+bidId+"");
			List<BpFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapperRaiseDetail());
			return list;
	}
	class  rowMapperRaiseDetail implements RowMapper {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlFundIntentPeriod income = new SlFundIntentPeriod();
			income.setIntentDate(rs.getDate("intentDate"));
			income.setFactDate(rs.getDate("factDate"));
			income.setFundType(rs.getString("fundType"));
			income.setIncomeMoney(rs.getBigDecimal("incomeMoney"));
			income.setBidtime(rs.getDate("bidtime"));
			income.setStartTime(rs.getDate("startIntentDate"));
			income.setDays(rs.getString("days"));
			income.setReceiverP2PName(rs.getString("investpersonName"));
			return income;
		}
		
	}
	@Override
	public List<SlFundIntentPeriod> getCouponsIncome(PagingBean pb) {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql =new StringBuffer("SELECT	member.truename as truename,p.investpersonName as investpersonName," +
				" sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.afterMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.afterMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.afterMoney ELSE 0 END ) AS 'couponsIncome', " +
				" sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.afterMoney ELSE 0 END ) AS 'addrateIncome', " +
				" sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.afterMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.afterMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.afterMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.afterMoney ELSE 0 END) AS 'sumIncome'," +
				" sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.notMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.notMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.notMoney ELSE 0 END ) + " +
				" sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.notMoney ELSE 0 END ) AS 'notIncome' " +
				" FROM `bp_fund_intent` AS p LEFT JOIN bp_cust_member as member on p.investPersonId=member.id " +
				" WHERE p.isCheck = 0 AND p.isValid = 0 " +
				" AND p.fundType in('couponInterest',	'principalCoupons',	'subjoinInterest','commoninterest') " +
				" ");
		String Q_loginname = request.getParameter("Q_loginname");
		String Q_truename = request.getParameter("Q_truename");
		if(Q_loginname!=null&&!Q_loginname.equals("")){
			sql.append(" and p.investpersonName like '%"+Q_loginname+"%'");
		}
		if(Q_truename!=null&&!Q_truename.equals("")){
			sql.append(" and member.truename like '%"+Q_truename+"%'");
		}
		sql.append(" GROUP BY	p.investPersonId");
		if(null==pb){
			List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapperCouponsIncome());
			return list;
		}else{
			sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize()));
			List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapperCouponsIncome());
			return list;
		}
		
	}
	class  rowMapperCouponsIncome implements RowMapper {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlFundIntentPeriod income = new SlFundIntentPeriod();
			income.setReceiverP2PName(rs.getString("investpersonName"));
			income.setReceiverName(rs.getString("truename"));
			income.setCouponsIncome(rs.getBigDecimal("couponsIncome"));
			income.setAddRateIncome(rs.getBigDecimal("addrateIncome"));
			income.setSumIncome(rs.getBigDecimal("sumIncome"));
			income.setNotIncome(rs.getBigDecimal("notIncome"));
			return income;
		}
		
	}
	public List<BpFundIntentPeriod> listByBidPlanIdAndpayintentPeriodAndorerNo( PagingBean pb,
			Map<String, String> map) {
		StringBuffer sql =new StringBuffer("select  *	from (SELECT ");
		sql.append("intentperiod.intentDate AS intentDate,");
		sql.append("intentperiod.factDate AS factDate,");
		sql.append("intentperiod.bidPlanId AS planId,");
		sql.append("intentperiod.payintentPeriod AS payintentPeriod,");
		sql.append("intentperiod.punishDays AS punishDays,");
		sql.append("intentperiod.orderNo AS orderNo,");
		sql.append("intentperiod.investPersonName AS investPersonName,");
		sql.append("info.fundResource AS fundResource,");
		sql.append("pbp.bidProNumber AS bidPlanProjectNum,");
		sql.append("pbp.bidProName AS bidPlanName,");
		sql.append("pbp.authorizationStatus AS authorizationStatus,");
		sql.append("bfp.oppositeType AS oppositeType,");
	    sql.append("IFNULL(cp. NAME ,ce.enterprisename) AS borrowName,");
		sql.append("IFNULL(cp.id ,ce.id) AS borrowId,");
		sql.append("IFNULL(	bcm.id ,bcm1.id) AS ptpborrowId,");
		sql.append("IFNULL(bcm.loginname ,bcm1.loginname) AS ptpborrowName");
/*	"	FROM"+
	"	bp_fund_intent intentperiod "
	+ "LEFT JOIN invest_person_info info on intentperiod.orderNo = info.orderNo 	 "
	+ "LEFT JOIN pl_bid_plan pbp on intentperiod.bidPlanId = pbp.bidId 	 "
	+ "left join bp_persion_dir_pro bpdp on bpdp.pdirProId = pbp.pDirProId and pbp.proType='pbp' "
	+ "	left join cs_person cp on cp.id=bpdp.persionId"
	+ "	left join bp_cust_relation bcr on bcr.offlineCusId=cp.id and bcr.offlineCustType='p_loan' "
	+ "  left join bp_cust_member bcm on bcm.id=bcr.p2pCustId  "
	+"	where intentperiod.isCheck=0 and  intentperiod.fundType !='principalLending'";*/
		
	sql.append(	"  FROM  ");
	sql.append(	"  bp_fund_intent intentperiod ");
	sql.append( "LEFT JOIN invest_person_info info on intentperiod.orderNo = info.orderNo 	 ");
	sql.append( "LEFT JOIN pl_bid_plan pbp on intentperiod.bidPlanId = pbp.bidId 	 ");
	sql.append( "LEFT JOIN bp_fund_project bfp on bfp.id = intentperiod.preceptId	 ");
	sql.append( "left join cs_person cp on cp.id=bfp.oppositeID and bfp.oppositeType='person_customer'");
	sql.append( "left join bp_cust_relation bcr on bcr.offlineCusId=cp.id and bcr.offlineCustType='p_loan' ");
	sql.append( " left join bp_cust_member bcm on bcm.id=bcr.p2pCustId  ");
		
	sql.append( "left join cs_enterprise ce on cp.id=bfp.oppositeID and bfp.oppositeType='company_customer'");
	sql.append( "left join bp_cust_relation bcr1 on bcr.offlineCusId=cp.id and bcr1.offlineCustType='b_loan' ");
	sql.append( "left join bp_cust_member bcm1 on bcm1.id=bcr.p2pCustId  ");
		
	sql.append("where intentperiod.isCheck=0  and intentperiod.isValid=0 and  intentperiod.fundType !='principalLending'");
		String planId=map.get("planId");
		if(null !=planId&&!planId.equals("")){
			sql.append(" and intentperiod.bidPlanId="+planId+"");
		}
		String intentDate=map.get("intentDate");
		if(null !=intentDate&&!intentDate.equals("")){
			sql.append(" and intentperiod.intentDate='"+intentDate+"'");
		}
		
		sql.append("	GROUP BY"+
	"	intentperiod.bidPlanId,"+
	"	intentperiod.intentDate," +
	"  intentperiod.orderNo) as a ");
	if(null==pb){
		List<BpFundIntentPeriod>	list = this.jdbcTemplate.query(sql.toString(),new rowMapperb());
		  return list;
	}else{
		sql.append(" limit "+pb.getStart()+","+pb.getPageSize());
		List<BpFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapperb());
		  return list;
	}
	
	}
	class  rowMapperb implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			BpFundIntentPeriod income = new BpFundIntentPeriod();
			income.setIntentDate(rs.getDate("intentDate"));
			income.setFactDate(rs.getDate("factDate"));
			income.setPayintentPeriod(rs.getInt("payintentPeriod"));
			income.setPlanId(rs.getLong("planId"));
			income.setBidPlanName(rs.getString("bidPlanName"));
			income.setBidPlanProjectNum(rs.getString("bidPlanProjectNum"));
			income.setPunishDays(rs.getInt("punishDays"));
			income.setOrderNo(rs.getString("orderNo"));
			income.setInvestPersonName(rs.getString("investPersonName"));
			income.setAuthorizationStatus(rs.getShort("authorizationStatus"));
			income.setOppositeType(rs.getString("oppositeType"));
			income.setBorrowId(rs.getLong("borrowId"));
			income.setBorrowName(rs.getString("borrowName"));
			income.setPtpborrowId(rs.getLong("ptpborrowId"));
			income.setPtpborrowName(rs.getString("ptpborrowName"));
			return income;
		}
		
	}
	@Override
	public List<BpFundIntent> getByBidPlanIdAndIntentDate(Long bidPlanId,
			Date intentDate,String orderNo) {
		if(null ==orderNo){
			String sql = " from BpFundIntent as fund where fund.bidPlanId=? and fund.intentDate=?  ";
			return this.getSession().createQuery(sql).setParameter(0, bidPlanId).setParameter(1, intentDate).list();
		}else{
			String sql = "from BpFundIntent as fund where fund.bidPlanId=? and fund.intentDate=? and fund.orderNo=?  ";
			return this.getSession().createQuery(sql).setParameter(0, bidPlanId).setParameter(1, intentDate).setParameter(2, orderNo).list();
			
		}
		
	}
	
	@Override
	public List<BpFundIntent> listByEarlyDate(Long bidPlanId, String orderNo,
			String earlyDate,String fundType) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String hql="from BpFundIntent as f where f.bidPlanId=? and f.orderNo=? and f.fundType=? and f.isValid=0 and f.isCheck=0 and f.interestStarTime<='"+earlyDate+"'";
		if(AppUtil.getInterest().equals("0")){
			hql=hql+" and f.interestEndTime>='"+sd.format(DateUtil.addDaysToDate(DateUtil.parseDate(earlyDate,"yyyy-MM-dd"), -1))+"'";
		}else{
			hql=hql+" and f.interestEndTime>='"+earlyDate+"'";
		}
		return this.findByHql(hql, new Object[]{bidPlanId,orderNo,fundType});
	}


	@Override
	public List<BpFundIntent> listbySlEarlyRepaymentId(Long bidPlanId,
			Long slEarlyRepaymentId) {
		String hql="from BpFundIntent as f where f.bidPlanId=? and f.slEarlyRepaymentId=? and f.isValid!=1";
		return this.findByHql(hql, new Object[]{bidPlanId,slEarlyRepaymentId});
	}

	@Override
	public void saveCommonFundIntent(String fundIntentJson, PlBidPlan plan,
			BpFundProject project, Short isCheck) {
		try{
			if (null != fundIntentJson && !"".equals(fundIntentJson)) {
				
				String[] fundIntentJsonArr = fundIntentJson.split("@");
				for (int i = 0; i < fundIntentJsonArr.length; i++) {
					String fundIntentstr = fundIntentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(fundIntentstr));
			
					BpFundIntent bfi = (BpFundIntent) JSONMapper.toJava(parser.nextValue(), BpFundIntent.class);
					bfi.setProjectId(project.getProjectId());
					bfi.setProjectName(project.getProjectName());
					bfi.setProjectNumber(project.getProjectNumber());
					bfi.setPreceptId(project.getId());
					bfi.setBidPlanId(plan.getBidId());
					bfi.setBusinessType(project.getBusinessType());
					bfi.setCompanyId(project.getCompanyId());
					Short isvalid = 0;
					bfi.setIsValid(isvalid);
					bfi.setIsCheck(isCheck);
					if (null == bfi.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);
						if (bfi.getIncomeMoney().compareTo(lin) == 0) {
							bfi.setNotMoney(bfi.getPayMoney());
						} else {
							bfi.setNotMoney(bfi.getIncomeMoney());
						}
						bfi.setAfterMoney(new BigDecimal(0));
						bfi.setAccrualMoney(new BigDecimal(0));
						bfi.setFlatMoney(new BigDecimal(0));
						
						this.save(bfi);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (bfi.getIncomeMoney().compareTo(lin) == 0) {
							bfi.setNotMoney(bfi.getPayMoney());
						} else {
							bfi.setNotMoney(bfi.getIncomeMoney());
						}
						BpFundIntent bf = this.get(bfi.getFundIntentId());
						BeanUtil.copyNotNullProperties(bf,bfi);
						this.merge(bf);

					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();

		}
		
	}

	@Override
	public List<BpFundIntent> listOfInvestAndEarlyId(String orderNo,
			String fundTypes, Long slEarlyRepaymentId) {
		// TODO Auto-generated method stub
		String hql1 = "from BpFundIntent as bf where bf.orderNo = ? and (bf.slEarlyRepaymentId is null or bf.slEarlyRepaymentId!=?)";
		if(!fundTypes.isEmpty()){
			hql1=hql1+" and bf.fundType in("+fundTypes+") ";
			List<BpFundIntent> list = findByHql(hql1, new Object[]{orderNo,slEarlyRepaymentId});
			return list;
		}else{
			
			List<BpFundIntent> list = findByHql(hql1, new Object[]{orderNo,slEarlyRepaymentId});
			return list;
		}
		
		
	}

	@Override
	public List<BpFundIntent> listByOrderNoAndEarlyId(String orderNo,
			String fundTypes, Long slEarlyRepaymentId) {
		String hql1 = "from BpFundIntent as bf where bf.orderNo = ? and bf.slEarlyRepaymentId=?";
		if(!fundTypes.isEmpty()){
			hql1=hql1+" and bf.fundType in("+fundTypes+") ";
			List<BpFundIntent> list = findByHql(hql1, new Object[]{orderNo,slEarlyRepaymentId});
			return list;
		}else{
			
			List<BpFundIntent> list = findByHql(hql1, new Object[]{orderNo,slEarlyRepaymentId});
			return list;
		}
	}

	@Override
	public BigDecimal getPrincipalMoney(Long bidPlanId, String date,String orderNo) {
		String hql="select sum(f.incomeMoney) from BpFundIntent as f where f.isValid=0 and f.isCheck=0 and f.fundType='principalRepayment' and f.bidPlanId=? and f.intentDate<='"+date+"'";
		if(null!=orderNo && !orderNo.equals("")){
			hql=hql+" and f.orderNo='"+orderNo+"'";
		}
		List list=getSession().createQuery(hql).setParameter(0, bidPlanId).list();
		BigDecimal money=new BigDecimal(0);
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				money=(BigDecimal) list.get(0);
			}
		}
		return money;
	}

	@Override
	public List<BpFundIntent> listByOrderNo(Long bidPlanId, String orderNo) {
		String hql="from BpFundIntent as f where f.bidPlanId=? and f.orderNo=? and f.isValid=0";
		return this.findByHql(hql, new Object[]{bidPlanId,orderNo});
	}
	@Override
	public List bidFundList(Long bidPlanId) {
		List<BpFundIntent> list = bpFundIntentDao.getByBidPlanId(bidPlanId);
		Boolean boo = false;
		for(BpFundIntent intent: list){
			if(intent.getSlEarlyRepaymentId()!=null){
				boo = true;
			}
		}
		StringBuffer buff=new StringBuffer("SELECT"
				+" f.payintentPeriod,"
				+"bjf.principal,"
				+"lxf.interest,"
				+"zxf.consultationMoney,"
				+"fwf.serviceMoney,"
				+"f.intentDate,"
				+"f.factDate,"
				+"fif.afterMoney,"
				+"fpf.interestPenalty,"
				+"faf.accrualMoney"
				+" FROM bp_fund_intent AS f");
				if(boo){
					buff.append(" left join (select sum(bj.incomeMoney) as principal,bj.payintentPeriod from bp_fund_intent as bj where bj.fundType='principalRepayment' and bj.isValid=0 and bj.bidPlanId="+bidPlanId+" group by bj.payintentPeriod) as bjf on bjf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(lx.incomeMoney) as interest,lx.payintentPeriod from bp_fund_intent as lx where lx.fundType='loanInterest' AND lx.isValid = 0 AND lx.isCheck = 0 and  lx.bidPlanId="+bidPlanId+" group by lx.payintentPeriod) as lxf on lxf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(zx.incomeMoney) as consultationMoney,zx.payintentPeriod from bp_fund_intent as zx where zx.fundType='consultationMoney'  AND zx.isValid = 0 AND zx.isCheck = 0 and zx.bidPlanId="+bidPlanId+" group by zx.payintentPeriod) as zxf on zxf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fw.incomeMoney) as serviceMoney,fw.payintentPeriod from bp_fund_intent as fw where fw.fundType='serviceMoney' AND fw.isValid = 0 AND fw.isCheck = 0 and fw.bidPlanId="+bidPlanId+" group by fw.payintentPeriod) as fwf on fwf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fa.accrualMoney) as accrualMoney,fa.payintentPeriod from bp_fund_intent as fa where fa.isValid = 0 and fa.bidPlanId="+bidPlanId+" group by fa.payintentPeriod) as faf on faf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fp.incomeMoney) as interestPenalty,fp.payintentPeriod from bp_fund_intent as fp where fp.fundType='interestPenalty' AND fp.isValid = 0 and fp.bidPlanId="+bidPlanId+" group by fp.payintentPeriod) as fpf on fpf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fi.afterMoney) as afterMoney,fi.payintentPeriod from bp_fund_intent as fi where fi.bidPlanId="+bidPlanId+"  and fi.fundType in ('principalRepayment','loanInterest','consultationMoney','serviceMoney') GROUP BY fi.payintentPeriod) as fif on fif.payintentPeriod=f.payintentPeriod"
							//+" WHERE f.bidPlanId = "+bidPlanId+" AND f.payintentPeriod !=0 AND f.isValid = 0 AND f.fundType != 'principalLending'  AND f.fundType != 'couponInterest' AND f.fundType != 'subjoinInterest'  GROUP BY f.payintentPeriod");
							+" WHERE f.bidPlanId = "+bidPlanId+"  AND f.isValid = 0 AND f.fundType != 'principalLending'  AND f.fundType != 'couponInterest' AND f.fundType != 'subjoinInterest'  GROUP BY f.payintentPeriod");
				}else{
					buff.append(" left join (select sum(bj.incomeMoney) as principal,bj.payintentPeriod from bp_fund_intent as bj where bj.fundType='principalRepayment' and bj.isValid=0 and bj.bidPlanId="+bidPlanId+" group by bj.payintentPeriod) as bjf on bjf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(lx.incomeMoney) as interest,lx.payintentPeriod from bp_fund_intent as lx where lx.fundType='loanInterest' AND lx.isValid = 0 and  lx.bidPlanId="+bidPlanId+" group by lx.payintentPeriod) as lxf on lxf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(zx.incomeMoney) as consultationMoney,zx.payintentPeriod from bp_fund_intent as zx where zx.fundType='consultationMoney'  AND zx.isValid = 0 and zx.bidPlanId="+bidPlanId+" group by zx.payintentPeriod) as zxf on zxf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fw.incomeMoney) as serviceMoney,fw.payintentPeriod from bp_fund_intent as fw where fw.fundType='serviceMoney' AND fw.isValid = 0 and fw.bidPlanId="+bidPlanId+" group by fw.payintentPeriod) as fwf on fwf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fa.accrualMoney) as accrualMoney,fa.payintentPeriod from bp_fund_intent as fa where fa.isValid = 0 and fa.bidPlanId="+bidPlanId+" group by fa.payintentPeriod) as faf on faf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fp.incomeMoney) as interestPenalty,fp.payintentPeriod from bp_fund_intent as fp where fp.fundType='interestPenalty' AND fp.isValid = 0 and fp.bidPlanId="+bidPlanId+" group by fp.payintentPeriod) as fpf on fpf.payintentPeriod=f.payintentPeriod"
							+" left join (select sum(fi.afterMoney) as afterMoney,fi.payintentPeriod from bp_fund_intent as fi where fi.bidPlanId="+bidPlanId+"  and fi.fundType in ('principalRepayment','loanInterest','consultationMoney','serviceMoney') GROUP BY fi.payintentPeriod) as fif on fif.payintentPeriod=f.payintentPeriod"
							//+" WHERE f.bidPlanId = "+bidPlanId+" AND f.payintentPeriod !=0 AND f.isValid = 0 AND f.fundType != 'principalLending'  AND f.fundType != 'couponInterest' AND f.fundType != 'subjoinInterest'  GROUP BY f.payintentPeriod");
					       +" WHERE f.bidPlanId = "+bidPlanId+" AND f.isValid = 0 AND f.fundType != 'principalLending'  AND f.fundType != 'couponInterest' AND f.fundType != 'subjoinInterest'  GROUP BY f.payintentPeriod");
				}
				
				
		System.out.println("bufferSql==="+buff.toString());			
		return this.getSession().createSQLQuery(buff.toString()).list();
				}

	@Override
	public void deleteFundIntent(Long bidPlanId) {
		final String sql="delete from bp_fund_intent  where bidPlanId="+bidPlanId;
		this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
			session.createSQLQuery(sql).executeUpdate();
			return null;
			}
			});
		
	}
	@Override
	public void deleteFundType(String fundType,Long bidPlanId) {
		if(fundType.equals("coupon")){
			final String sql="delete from bp_fund_intent  where bidPlanId ="+bidPlanId+" and fundType in('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest')";
			this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException,
				SQLException {
					session.createSQLQuery(sql).executeUpdate();
					return null;
				}
			});
		}
	}

	@Override
	public void saveFundIntent(List<BpFundIntent> list,Long bidPlanId) {
		 Session session =this.getSessionFactory().openSession();
		    //开始事务
	    Transaction tx = session.beginTransaction();
	    int count=0;
	    for(BpFundIntent f:list){
	    	f.setBidPlanId(bidPlanId);
	    	session.save(f);
	    	  if ( ++count % 500 == 0 )
	          {
	              session.flush();
	              session.clear();
	          }
	      }
	    tx.commit();
	    session.close();
	}

	@Override
	public BigDecimal getPrincipalAndInterest(Long bidPlanId) {
		String hql="select sum(f.incomeMoney) from BpFundIntent as f where f.bidPlanId=? and f.isValid=0 and f.fundType in('loanInterest','principalRepayment')";
		BigDecimal money=new BigDecimal(0);
		List list=this.getSession().createQuery(hql).setParameter(0, bidPlanId).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				money=(BigDecimal) list.get(0);
			}
		}
		return money;
	}
	
	@Override
	public List<BpFundIntent> getByprincipalRepayment(Long bidPlanId, String orderNo) {
		String hql="from BpFundIntent as s where s.bidPlanId=? and s.orderNo=?  and s.fundType=? and s.afterMoney=0 order by s.intentDate asc";
		return getSession().createQuery(hql).setParameter(0, bidPlanId).setParameter(1, orderNo).setParameter(2,"principalRepayment").list();
	}

	@Override
	public List bidFundListByOrderNo(Long bidPlanId, String orderNo) {
		StringBuffer buff=new StringBuffer("SELECT"
				+" f.payintentPeriod,"
				+"bjf.principal,"
				+"lxf.interest,"
				+"f.intentDate,"
				+"f.factDate,"
				+"fif.afterMoney,"
				+"jlf.reward,"
				+"bcf.interestPenaltyMoney,"
				+"fxf.accrualMoney"
				+" FROM bp_fund_intent AS f"
				+" left join (select sum(bj.incomeMoney) as principal,bj.payintentPeriod from bp_fund_intent as bj where bj.fundType='principalRepayment' and bj.isValid=0 and bj.bidPlanId="+bidPlanId+" and bj.orderNo='"+orderNo+"' group by bj.payintentPeriod) as bjf on bjf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(lx.incomeMoney) as interest,lx.payintentPeriod from bp_fund_intent as lx where lx.fundType in ('loanInterest') and lx.bidPlanId="+bidPlanId+" and lx.orderNo='"+orderNo+"' group by lx.payintentPeriod) as lxf on lxf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(jl.incomeMoney) as reward,jl.payintentPeriod from bp_fund_intent as jl where jl.fundType in (	'couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest') and jl.bidPlanId="+bidPlanId+" and jl.orderNo='"+orderNo+"' group by jl.payintentPeriod) as jlf on jlf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(fi.afterMoney) as afterMoney,fi.payintentPeriod from bp_fund_intent as fi where fi.bidPlanId="+bidPlanId+" and fi.orderNo='"+orderNo+"' and fi.fundType in ('principalRepayment','loanInterest','couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest','interestPenalty') GROUP BY fi.payintentPeriod) as fif on fif.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(bc.incomeMoney) as interestPenaltyMoney,bc.payintentPeriod from bp_fund_intent as bc where bc.bidPlanId="+bidPlanId+" and bc.orderNo='"+orderNo+"' and bc.fundType in ('interestPenalty') GROUP BY bc.payintentPeriod) as bcf on bcf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(fx.accrualMoney) as accrualMoney,fx.payintentPeriod from bp_fund_intent as fx where fx.bidPlanId="+bidPlanId+" and fx.orderNo='"+orderNo+"' group by fx.payintentPeriod) as fxf on fxf.payintentPeriod=f.payintentPeriod"
				+" WHERE f.bidPlanId = "+bidPlanId+" AND f.isValid = 0 AND f.fundType in ('principalRepayment','loanInterest','couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest') and f.orderNo='"+orderNo+"' GROUP BY f.payintentPeriod");
				//System.out.println(buff.toString());
		//System.out.println("提前还款测试"+buff.toString());
		return this.getSession().createSQLQuery(buff.toString()).list();
				}

	@Override
	public List<BpFundIntent> getdistributeMoney(Long planId,
			Long payintentPeriod, String fundType, String remak) {
		// TODO Auto-generated method stub
			String hql1="";
			if(fundType!=null&&!fundType.equals("")&&fundType.equals("coupons")){
				 hql1 = "from BpFundIntent as bf where bf.bidPlanId = ? and bf.payintentPeriod=?  and bf.fundType in ('principalCoupons','couponInterest','subjoinInterest') and bf.factDate is null and bf.isValid=0 and bf.isCheck=0";
			}else{
				 hql1 = "from BpFundIntent as bf where bf.bidPlanId = ? and bf.payintentPeriod=?  and bf.fundType in ('"+fundType+"') and bf.factDate is null and bf.isValid=0 and bf.isCheck=0";
			}
			
			List<BpFundIntent> list = this.findByHql(hql1, new Object[]{planId,payintentPeriod.intValue()});
			return list;
	}
	
	@Override
	public BigDecimal getAfterMoney(String temp, String planId,
			String intentDate, String type) {
		String sql= "select SUM(bp.afterMoney) from BpFundIntent  as bp where bp.bidPlanId = ? " +
	    "and bp.orderNo=?and bp.isCheck=0 and bp.isValid=0  and intentDate='"+intentDate+"'";
		if(type!=null){
		sql=sql+" and bp.fundType in "+type;
		}
		return (BigDecimal) this.getSession().createQuery(sql).setParameter(0, Long.valueOf(planId))
				.setParameter(1, temp).uniqueResult();
	}

	/* 
	 * 预期项目管理
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao#listOverDueByBidPlanIdAndpayintentPeriod(com.zhiwei.core.web.paging.PagingBean, java.util.Map)
	 */
	@Override
	public List<SlFundIntentPeriod> listOverDueByBidPlanIdAndpayintentPeriod(
			PagingBean pb, Map<String, String> map) {

		StringBuffer sql =new StringBuffer("SELECT ");
		sql.append(" intentperiod.intentDate AS intentDate, ");
		sql.append(" intentperiod.factDate AS factDate, ");
		sql.append(" intentperiod.bidPlanId AS planId, ");
		sql.append(" intentperiod.loanerRepayMentStatus AS loanerRepayMentStatus,");
		sql.append(" intentperiod.payintentPeriod AS payintentPeriod, ");
		sql.append(" intentperiod.repaySource AS repaySource, ");
		sql.append(" intentperiod.punishDays AS punishDays, ");
		sql.append(" pbp.bidProNumber AS bidPlanProjectNum, ");
		sql.append(" pbp.bidProName AS bidPlanName, ");
		sql.append(" bcm0.id AS receiverP2PId, ");
		sql.append(" bfp.receiverP2PAccountNumber AS receiverP2PName, ");
		sql.append(" bfp.receiverName AS receiverName, ");
		sql.append(" pbp.bidProName AS bidPlanName, ");
		sql.append(" IFNULL(cpp. NAME, cep.enterprisename) AS borrowName, ");
		sql.append(" IFNULL(cpp.id, cep.id) AS borrowId, ");
		sql.append(" IFNULL(bcm.id, bcm1.id) AS ptpborrowId, ");
		sql.append(" IFNULL(bcm.loginname,bcm1.loginname) AS ptpborrowName, ");
		sql.append(" pbp.authorizationStatus AS authorizationStatus, ");
		sql.append(" bfp.oppositeType AS oppositeType FROM ");
		sql.append(" (SELECT * FROM bp_fund_intent intentperiod1 ");
		sql.append(" WHERE intentperiod1.payintentPeriod !=0 AND intentperiod1.isValid = 0 and intentperiod1.loanerRepayMentStatus is NULL  and intentperiod1.isCheck=0 	AND intentperiod1.fundType != 'principalLending' and intentperiod1.intentDate < DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 DAY),'%Y-%m-%d') and intentperiod1.notMoney>0   ");
		sql.append(" GROUP BY	intentperiod1.bidPlanId,	intentperiod1.intentDate) AS  intentperiod ");
		sql.append(" LEFT JOIN invest_person_info info ON intentperiod.orderNo = info.orderNo ");
		sql.append(" LEFT JOIN pl_bid_plan pbp ON intentperiod.bidPlanId = pbp.bidId ");
		sql.append(" LEFT JOIN bp_fund_project bfp ON bfp.id = intentperiod.preceptId ");
		sql.append(" LEFT JOIN bp_cust_member bcm0 ON bcm0.loginname = bfp.receiverP2PAccountNumber ");
		
		sql.append(" LEFT JOIN cs_person cpp ON (cpp.id = bfp.oppositeID AND bfp.oppositeType = 'person_customer')");
		sql.append("LEFT JOIN cs_enterprise cep ON (cep.id = bfp.oppositeID AND bfp.oppositeType = 'company_customer')");
		

		if(map.get("proType").equals("B_Dir") || map.get("proType").equals("P_Dir")){
			sql.append(" LEFT JOIN cs_person cp ON (cp.id = bfp.oppositeID AND bfp.oppositeType = 'person_customer')");
			sql.append(" LEFT JOIN bp_cust_relation bcr ON (bcr.offlineCusId = cp.id AND bcr.offlineCustType = 'p_loan')");
			sql.append(" LEFT JOIN bp_cust_member bcm ON bcm.id = bcr.p2pCustId ");
			sql.append(" LEFT JOIN cs_enterprise ce ON (ce.id = bfp.oppositeID AND bfp.oppositeType = 'company_customer')");
			sql.append(" LEFT JOIN bp_cust_relation bcr1 ON (bcr1.offlineCusId = ce.id AND bcr1.offlineCustType = 'b_loan')");
			sql.append(" LEFT JOIN bp_cust_member bcm1 ON bcm1.id = bcr1.p2pCustId where 1=1 ");
		}else if(map.get("proType").equals("B_Or") || map.get("proType").equals("P_Or")){
			sql.append(" LEFT JOIN cs_cooperation_person cp ON (cp.id = bfp.reciverId AND bfp.reciverType = 'person')");
			sql.append(" LEFT JOIN bp_cust_relation bcr ON (bcr.offlineCusId = cp.id AND bcr.offlineCustType = 'p_cooperation')");
			sql.append(" LEFT JOIN bp_cust_member bcm ON bcm.id = bcr.p2pCustId ");
			sql.append(" LEFT JOIN cs_cooperation_enterprise ce ON (ce.id = bfp.reciverId AND bfp.reciverType = 'enterprise')");
			sql.append(" LEFT JOIN bp_cust_relation bcr1 ON (bcr1.offlineCusId = ce.id AND bcr1.offlineCustType = 'b_cooperation')");
			sql.append(" LEFT JOIN bp_cust_member bcm1 ON bcm1.id = bcr1.p2pCustId where 1=1");
		}
		
		String subType=map.get("subType");
		if(null !=subType && "online".equals(subType)){
			sql.append(" and bfp.loanId is not null ");
		}else if(null !=subType && "underline".equals(subType)){
			sql.append(" and bfp.loanId is null ");
		}
		
		//按标的类型查询
		String proType=map.get("proType");
		if(null !=proType && !"".equals(proType)){
			sql.append(" and pbp.proType='"+proType+"'");
		}
		String bidPlanName=map.get("bidPlanName");
		if(null !=bidPlanName&&!bidPlanName.equals("")){
			sql.append(" and pbp.bidProName  like '%/"+bidPlanName+"%'  escape '/' ");
		}
		
		String punishDaysS=map.get("punishDaysS");
		if(null !=punishDaysS&&!punishDaysS.equals("")){
			sql.append(" and intentperiod.punishDays >= ").append(Integer.valueOf(punishDaysS)).append("");
		}
		String punishDaysE=map.get("punishDaysE");
		if(null !=punishDaysE&&!punishDaysE.equals("")){
			sql.append(" and intentperiod.punishDays <= ").append(Integer.valueOf(punishDaysE)).append("");
		}
		String borrowName=map.get("borrowName");
		if(null !=borrowName&&!borrowName.equals("")){
			sql.append(" and ce.enterprisename  like '%/"+borrowName+"%'  escape '/'  or cp. NAME like '%/"+borrowName+"%'  escape '/'");
		}
		if(null==pb){
			List<SlFundIntentPeriod>	list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
			return list;
		}else{
			sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize()));
			List<SlFundIntentPeriod> list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
			return list;
		}
	}

	/* 
	 * 获取项目当期代偿总金额
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao#sumAllCompensatoryMoney(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BigDecimal sumAllCompensatoryMoney(String planId, String peridId,String cardNo) {
		// TODO Auto-generated method stub
		String hql=" select sum(p.notMoney)+ sum(p.accrualMoney) from bp_fund_intent as p where p.bidPlanId="+Long.valueOf(planId)+" and p.intentDate='"+peridId+"'";
		return (BigDecimal) this.getSession().createSQLQuery(hql).uniqueResult(); 
	}
	@Override
	public List<BpFundIntent> getThirdBpFundIntentList(String bidId,
			Date intentDate) {
			String sql = "from BpFundIntent as fund where fund.bidPlanId=? and fund.intentDate=?  and fund.isValid=0  and fund.fundType not in('couponInterest','principalCoupons','subjoinInterest','raiseinterest','commoninterest')";
			return this.getSession().createQuery(sql).setParameter(0, Long.valueOf(bidId)).setParameter(1, intentDate).list();
	}

	@Override
	public BigDecimal getFinancialMoney(Long bidPlanId, String type) {
		String sql = "";
		if(type.equals("all")){
			sql = "select SUM(intent.incomeMoney) from bp_fund_intent as intent where intent.bidPlanId=?";
		}
		return (BigDecimal)this.getSession().createSQLQuery(sql).setParameter(0, bidPlanId).uniqueResult();
	}

	@Override
	public BigDecimal getMoneyPerPeriod(String bidId, String period) {
		// TODO Auto-generated method stub
		String sql ="select SUM(intent.notMoney+IFNULL(intent.accrualMoney,0)) from bp_fund_intent as intent where intent.bidPlanId = ? and intent.payintentPeriod = ? and intent.isCheck=0 and intent.isValid=0 and intent.fundType not in('consultationMoney','serviceMoney')";//and intent.intentDate < DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 1 DAY),'%Y-%m-%d') 'couponInterest','principalCoupons','subjoinInterest','commoninterest',
		return (BigDecimal)this.getSession().createSQLQuery(sql).setParameter(0, bidId).setParameter(1, period).uniqueResult();
	}
	@Override
	public List<BpFundIntent> getByRequestNoLoaner(String requestNo) {
		String sql = " from BpFundIntent as fund where fund.requestNOLoaner=? and fund.fundType not in('couponInterest','principalCoupons','subjoinInterest','commoninterest')";
		return this.getSession().createQuery(sql).setParameter(0, requestNo).list();
	}

	@Override
	public List<BpFundIntent> getByBidIdandPeriod(String bidId, String period) {
		
		String sql ="from BpFundIntent as intent where intent.bidPlanId = ? and intent.payintentPeriod = ? and intent.isCheck=0 and intent.isValid=0";
		System.out.println("slq++"+sql);
		return (List<BpFundIntent>) this.getSession().createQuery(sql).setParameter(0, Long.valueOf(bidId)).setParameter(1, Integer.valueOf(period)).list();
	}

	@Override
	public BigDecimal getPlateFeeByPlanIdandPeriod(String bidId, String periodId) {
		// TODO Auto-generated method stu
		String sql = "select sum(intent.incomeMoney) from BpFundIntent as intent where intent.bidPlanId=? and intent.payintentPeriod=? and intent.isCheck=0 and intent.isValid=0 and intent.notMoney is not null and intent.factDate is null and  intent.fundType in('consultationMoney','serviceMoney')";
		System.out.println("平台手续费对用的sql"+sql);
		return (BigDecimal)this.getSession().createQuery(sql).setParameter(0, Long.valueOf(bidId)).setParameter(1, Integer.valueOf(periodId)).uniqueResult();
	}

	@Override
	public List<BpFundIntent> bidFundList2(long bidPlanId) {
		StringBuffer buff=new StringBuffer("SELECT"
				+" f.payintentPeriod,"
				+"IFNULL(bjf.principal,0) as principal,"
				+"IFNULL(lxf.interest,0) as interest,"
				+"IFNULL(zxf.consultationMoney,0) as consultationMoney,"
				+"IFNULL(fwf.serviceMoney,0) as serviceMoney,"
				+"f.intentDate,"
				+"f.factDate,"
				+"fif.afterMoney"
				+" FROM bp_fund_intent AS f"
				+" left join (select sum(bj.incomeMoney) as principal,bj.payintentPeriod from bp_fund_intent as bj where bj.fundType='principalRepayment' and bj.bidPlanId="+bidPlanId+" group by bj.payintentPeriod) as bjf on bjf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(lx.incomeMoney) as interest,lx.payintentPeriod from bp_fund_intent as lx where lx.fundType='loanInterest' and lx.bidPlanId="+bidPlanId+" group by lx.payintentPeriod) as lxf on lxf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(zx.incomeMoney) as consultationMoney,zx.payintentPeriod from bp_fund_intent as zx where zx.fundType='consultationMoney' and zx.bidPlanId="+bidPlanId+" group by zx.payintentPeriod) as zxf on zxf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(fw.incomeMoney) as serviceMoney,fw.payintentPeriod from bp_fund_intent as fw where fw.fundType='serviceMoney' and fw.bidPlanId="+bidPlanId+" group by fw.payintentPeriod) as fwf on fwf.payintentPeriod=f.payintentPeriod"
				+" left join (select sum(fi.afterMoney) as afterMoney,fi.payintentPeriod from bp_fund_intent as fi where fi.bidPlanId="+bidPlanId+" and fi.fundType in ('principalRepayment','loanInterest','consultationMoney','serviceMoney') GROUP BY fi.payintentPeriod) as fif on fif.payintentPeriod=f.payintentPeriod"
				+" WHERE f.bidPlanId = "+bidPlanId+" AND f.payintentPeriod !=0 AND f.isValid = 0 AND f.fundType != 'principalLending'  AND f.fundType != 'couponInterest' AND f.fundType != 'subjoinInterest'  GROUP BY f.payintentPeriod");
			
			return this.getSession().createSQLQuery(buff.toString())
				.addScalar("payintentPeriod",Hibernate.INTEGER)
				.addScalar("principal", Hibernate.BIG_DECIMAL)
				.addScalar("interest", Hibernate.BIG_DECIMAL)
				.addScalar("consultationMoney", Hibernate.BIG_DECIMAL)
				.addScalar("serviceMoney", Hibernate.BIG_DECIMAL)
				.addScalar("afterMoney", Hibernate.BIG_DECIMAL)
				.addScalar("factDate", Hibernate.DATE)
				.addScalar("intentDate", Hibernate.DATE)
				.setResultTransformer(Transformers.aliasToBean(BpFundIntent.class))
				.list();
		}

	@Override
	public BigDecimal getPrincipalAndInterest(Long bidPlanId, String orderNo) {
		String hql="select sum(f.incomeMoney) from BpFundIntent as f where f.bidPlanId=? and f.orderNo=? and f.isValid=0 and f.fundType in('loanInterest','principalRepayment')";
		BigDecimal money=new BigDecimal(0);
		List list=this.getSession().createQuery(hql).setParameter(0,bidPlanId).setParameter(1,orderNo).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				money=(BigDecimal) list.get(0);
			}
		}
		return money;
	}

	@Override
	public BigDecimal sumAllCompensatoryMoney(String bidPlanId, String period) {
		// TODO Auto-generated method stub
		//String hql=" select sum(p.incomeMoney)+ sum(p.accrualMoney) from bp_fund_intent as p where p.bidPlanId="+Long.valueOf(bidPlanId)+" and p.payintentPeriod='"+period+"'";
		String hql=" select sum(p.notMoney)+ sum(p.accrualMoney) from bp_fund_intent as p where p.isCheck=0 and p.isValid=0 and p.bidPlanId="+Long.valueOf(bidPlanId)+" and p.payintentPeriod='"+period+"'";
		return (BigDecimal) this.getSession().createSQLQuery(hql).uniqueResult(); 
	}

	@Override
	public List<BpFundIntent> getOverList(String bidId) {
		// TODO Auto-generated method stub
		String hql="from BpFundIntent as f where f.bidPlanId=? and f.factDate is null and f.isValid=0 and f.isCheck=0 ";
		return this.findByHql(hql, new Object[]{Long.valueOf(bidId)});
	}

	@Override
	public List<BpFundIntent> getOverdueFundIntent(String day) {
		StringBuffer buff=new StringBuffer("SELECT");
		buff.append(" plan.bidProName as projectName,plan.bidProNumber as projectNumber, bpf.bidPlanId,bpf.payintentPeriod,SUM(bpf.notMoney) as notMoney,DATE_FORMAT(bpf.intentDate,'%Y-%m-%d') as intentDate,member.loginname as oppositeName, member.telphone as opposittelephone");
		buff.append(" from bp_fund_intent as bpf");
		buff.append(" LEFT JOIN pl_bid_plan as plan on bpf.bidPlanId=plan.bidId");
		buff.append(" left JOIN bp_cust_member as member on plan.receiverP2PAccountNumber=member.loginname");
		buff.append(" where");
		if(day.equals("0")){//当天逾期的项目
			buff.append(" TO_DAYS(date_format(now(),'%Y-%m-%d')) - TO_DAYS(date_format(bpf.intentDate,'%Y-%m-%d')) =0");
		}else if(day.equals("1")){//明天的数据
			buff.append(" TO_DAYS(date_format(now(),'%Y-%m-%d')) - TO_DAYS(date_format(bpf.intentDate,'%Y-%m-%d')) =-1");
		}else if(day.equals("5")){//后5天的数据
			buff.append(" TO_DAYS(date_format(now(),'%Y-%m-%d')) - TO_DAYS(date_format(bpf.intentDate,'%Y-%m-%d')) =-5");
		}
		buff.append(" and bpf.factDate is null and bpf.notMoney>0 and bpf.isCheck=0 and bpf.isValid=0 GROUP BY bpf.bidPlanId,bpf.payintentPeriod");
		System.out.println("getOverdueFundIntent="+buff);
		return this.getSession().createSQLQuery(buff.toString())
		.addScalar("payintentPeriod",Hibernate.INTEGER)
		.addScalar("projectName", Hibernate.STRING)
		.addScalar("projectNumber", Hibernate.STRING)
		.addScalar("oppositeName", Hibernate.STRING)
		.addScalar("notMoney", Hibernate.BIG_DECIMAL)
		.addScalar("intentDate", Hibernate.DATE)
		.addScalar("opposittelephone", Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(BpFundIntent.class))
		.list();
	}

	@Override
	public List<SlFundIntent> getOverdueSlIntent(String day) {
		StringBuffer buff=new StringBuffer("SELECT");
		buff.append(" slf.payintentPeriod,slf.projectName,slf.projectNumber,SUM(notMoney) as notMoney,project.oppositeType,project.oppositeID  ");
		buff.append(" from sl_fund_intent as slf ");
		buff.append(" LEFT JOIN bp_fund_project as project on slf.preceptId=project.id ");
		buff.append(" where ");
		buff.append(" slf.factDate is null  and slf.isCheck=0 and slf.isValid=0");
		buff.append(" and TO_DAYS(date_format(now(),'%Y-%m-%d')) - TO_DAYS(date_format(slf.intentDate,'%Y-%m-%d')) =1");
		buff.append("  GROUP BY  slf.projectId,slf.payintentPeriod ");
		System.out.println("getOverdueSlIntent="+buff);
		return this.getSession().createSQLQuery(buff.toString())
		.addScalar("payintentPeriod",Hibernate.INTEGER)
		.addScalar("projectName", Hibernate.STRING)
		.addScalar("projectNumber", Hibernate.STRING)
		.addScalar("oppositeType", Hibernate.STRING)
		.addScalar("notMoney", Hibernate.BIG_DECIMAL)
		.addScalar("oppositeID", Hibernate.LONG)
		.setResultTransformer(Transformers.aliasToBean(SlFundIntent.class))
		.list();
	}
	/**
	 * 查询投资人所有未还本金记录
	 * @param investId
	 * @return
	 */
	@Override
	public List<BpFundIntent> listPrincipalByInvestId(Long investId,String yestoday){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = new Date();
		try {
			newDate = DateUtil.addDaysToDate(sf.parse(yestoday),1);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer buff = new StringBuffer("select f.fundIntentId,f.bidPlanId,b.bidProName as bidProName,f.investPersonId,f.investpersonName,SUM(f.incomeMoney) as sumMoney,f.orderNo as orderNo from Bp_Fund_Intent as f LEFT JOIN pl_bid_plan AS b on b.bidId=f.bidPlanId ")
		 .append("where fundType='principalRepayment' and isCheck = 0 and isValid=0 and b.startIntentDate<'"+sf.format(newDate)+"' ")
		.append(" and notMoney>0 and investPersonId="+investId+" GROUP BY f.bidPlanId ");
		return this.getSession().createSQLQuery(buff.toString())
		.addScalar("fundIntentId", Hibernate.LONG)
		.addScalar("bidPlanId", Hibernate.LONG)
		.addScalar("bidProName", Hibernate.STRING)
		.addScalar("orderNo", Hibernate.STRING)
		.addScalar("investpersonName", Hibernate.STRING)
		.addScalar("sumMoney", Hibernate.BIG_DECIMAL)
		.addScalar("investPersonId", Hibernate.LONG)
		.setResultTransformer(Transformers.aliasToBean(BpFundIntent.class))
		.list();
	}
	/**
	 * 根据标ID查询所有未还本金记录
	 * @param investId
	 * @return
	 */
	@Override
	public List<BpFundIntent> listPrincipalByBidId(Long bidId,String yestoday){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = new Date();
		try {
			newDate = DateUtil.addDaysToDate(sf.parse(yestoday),1);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer buff = new StringBuffer("select f.* from Bp_Fund_Intent as f LEFT JOIN pl_bid_plan AS b on b.bidId=f.bidPlanId ")
		.append("where f.fundType='principalRepayment' and f.isCheck = 0 and f.isValid=0 and b.startIntentDate<'"+sf.format(newDate)+"' ")
		.append(" and f.notMoney>0 and f.bidPlanId="+bidId);
		return this.getSession().createSQLQuery(buff.toString()).addEntity(BpFundIntent.class).list();
	}

	@Override
	public List<BpFundIntent> findByType(Long personId, Long bidId) {
		StringBuffer buff = new StringBuffer("select * from Bp_Fund_Intent as f ")
				.append(" where f.investPersonId= "+personId)
				.append(" and f.bidPlanId = "+ bidId )
				.append(" and f.fundType = 'raiseinterest' ");
		System.out.println("查询的sql语句: "+buff);
		return this.getSession().createSQLQuery(buff.toString()).addEntity(BpFundIntent.class).list();
	}

}
