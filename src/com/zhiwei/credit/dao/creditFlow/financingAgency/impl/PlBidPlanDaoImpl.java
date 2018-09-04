package com.zhiwei.credit.dao.creditFlow.financingAgency.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.r;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.TypedValue;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlBidPlanDaoImpl extends BaseDaoImpl<PlBidPlan> implements PlBidPlanDao{

	public PlBidPlanDaoImpl() {
		super(PlBidPlan.class);
	}

	@Override
	public String findLoanTotalMoneyBySQL(String pid) {
		String ret="";
		Map<String, String> params=new HashMap<String, String>();
		params.put("projectId", pid);
		List list=this.executeSqlFind("sl_smallloan_project","projectMoney",params);
		if(list!=null&&list.size()>0){
			ret= list.get(0).toString();
		}else{
			ret= "0";
		}
		return ret;
	}

	@Override
	public String findOrgMoneyBySQL(String pid, String flag) {
		String ret="";
		Map<String, String> params=new HashMap<String, String>();
		params.put("projectId", pid);
		params.put("flag", flag);
		List list=this.executeSqlFind("bp_fund_project","ownJointMoney",params);
		if(list!=null&&list.size()>0){
			ret= list.get(0).toString();
		}else{
			ret= "0";
		}
		return ret;
	}
	@Override
	public List<PlBidPlan> allLoanedList(HttpServletRequest request,
			PagingBean pb) {
		String hql="select p from PlBidPlan as p left join p.bpPersionOrPro as por left join p.bpBusinessDirPro as bdir left join p.bpPersionDirPro as pdir left join p.bpBusinessOrPro as bor where p.isLoan=1";
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			hql=hql+" and p.bidProName like '%"+bidProName+"%'";
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			hql=hql+" and p.bidProNumber like '%"+bidProNumber+"%'";
		}
		String startDate=request.getParameter("startDate");
		if(null!=startDate && !startDate.equals("")){
			hql=hql+" and p.endIntentDate>='"+startDate+"'";
		}
		String endDate=request.getParameter("endDate");
		if(null!=endDate && !endDate.equals("")){
			hql=hql+" and p.endIntentDate<='"+endDate+"'";
		}
		String proName=request.getParameter("proName");
		if(null!=proName && !proName.equals("")){
			hql=hql+" and ((por.proName like '%"+proName+"%') or (bdir.proName like '%"+proName+"%') or (pdir.proName like '%"+proName+"%') or (bor.proName like '%"+proName+"%'))";
		}
		return this.findByHql(hql, new Object[]{}, pb);
	}

	@Override
	public List<PlBidPlan> findPlbidplanLoanAfter(HttpServletRequest request,
			PagingBean pb) {
		
		String state = request.getParameter("state");
		String bidProName = request.getParameter("bidProName");
		String bidProNumber = request.getParameter("bidProNumber");
		String proName = request.getParameter("proName");
		String endIntentDateMin = request.getParameter("endIntentDateMin");
		String endIntentDateMax = request.getParameter("endIntentDateMax");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PlBidPlan.class).createAlias("bpPersionDirPro", "b");
		if(state!=null&&!"".equals(state)){
			detachedCriteria.add(Restrictions.eq("state", Integer.valueOf(state)));
		}
		if(bidProName!=null&&!"".equals(bidProName)){
			detachedCriteria.add(Restrictions.like("bidProName", "%"+bidProName+"%"));
		}
		if(bidProNumber!=null&&!"".equals(bidProNumber)){
			detachedCriteria.add(Restrictions.like("bidProNumber", "%"+bidProNumber+"%"));
		}
		if(proName!=null&&!"".equals(proName)){
			detachedCriteria.add(Restrictions.like("b.proName", "%"+proName+"%"));
		}
		if(endIntentDateMin!=null&&!"".equals(endIntentDateMin)){
			Date date = null;
			try {
				date = format.parse(endIntentDateMin);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			detachedCriteria.add(Restrictions.gt("endIntentDate", date));
		}
		if(endIntentDateMax!=null&&!"".equals(endIntentDateMax)){
			Date date = null;
			try {
				date = format.parse(endIntentDateMax);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			detachedCriteria.add(Restrictions.lt("endIntentDate",date));
		}
		
		DetachedCriteria detachedCriteriaT = DetachedCriteria.forClass(PlBidPlan.class).createAlias("bpPersionDirPro", "b");
		if(state!=null&&!"".equals(state)){
			detachedCriteriaT.add(Restrictions.eq("state", Integer.valueOf(state)));
		}
		if(detachedCriteriaT!=null&&!"".equals(bidProName)){
			detachedCriteria.add(Restrictions.like("bidProName", "%"+bidProName+"%"));
		}
		if(bidProNumber!=null&&!"".equals(bidProNumber)){
			detachedCriteriaT.add(Restrictions.like("bidProNumber", "%"+bidProNumber+"%"));
		}
		if(proName!=null&&!"".equals(proName)){
			detachedCriteriaT.add(Restrictions.like("b.proName", "%"+proName+"%"));
		}
		if(endIntentDateMin!=null&&!"".equals(endIntentDateMin)){
			Date date = null;
			try {
				date = format.parse(endIntentDateMin);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			detachedCriteriaT.add(Restrictions.gt("endIntentDate", date));
		}
		if(endIntentDateMax!=null&&!"".equals(endIntentDateMax)){
			Date date = null;
			try {
				date = format.parse(endIntentDateMax);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			detachedCriteriaT.add(Restrictions.lt("endIntentDate",date));
		}
		detachedCriteriaT.setProjection(Projections.rowCount());
		List list = this.getHibernateTemplate().findByCriteria(detachedCriteriaT);
		pb.setTotalItems((Integer)list.get(0));
		
		return this.getHibernateTemplate().findByCriteria(detachedCriteria, pb.getFirstResult(), pb.getPageSize());
	}

	@Override
	public List<PlBidPlan> listByState(String state, String proType, Long proId) {
		String hql="from PlBidPlan as p where p.state not in"+state;
		if(null!=proType && proType.equals("P_Dir")){
			hql=hql+" and p.bpPersionDirPro.pdirProId="+proId;
		}else if(null!=proType && proType.equals("P_Or")){
			hql=hql+" and p.bpPersionOrPro.porProId="+proId;
		}else if(null!=proType && proType.equals("B_Or")){
			hql=hql+" and p.bpBusinessOrPro.borProId="+proId;
		}else if(null!=proType && proType.equals("B_Dir")){
			hql=hql+" and p.bpBusinessDirPro.bdirProId="+proId;
		}
		return this.findByHql(hql);
	}

	@Override
	public PlBidPlan getAllInfoByplanId(Long planId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlBidPlan> getByStateList(HttpServletRequest request,
			PagingBean pb) {
		StringBuffer sb=new StringBuffer("SELECT "
				+" pbp.bidId,"
				+" pbp.bidProName,"
				+" pbp.bidProNumber,"
				+" sl.projectName,"
				+" pt.name,"
				+" pbp.bidMoney,"
				+" sl.yearAccrualRate,"
				+" pbp.startInterestType,"
				+"  pbp.startIntentDate,"
				+" pbp.endIntentDate,"
				+" pbp.proType,"
				+" sl.oppositeType,"
				+" sl.projectId,"
				+" sl.id"
				+" FROM pl_bid_plan AS pbp "
				+" LEFT JOIN bp_persion_dir_pro AS pDir ON pDir.pdirProId=pbp.pDirProId  and pbp.proType='P_Dir'"
				+" LEFT JOIN bp_business_dir_pro AS bDir ON bDir.bdirProId=pbp.bdirProId and pbp.proType='B_Dir'"
				+" LEFT JOIN bp_persion_or_pro AS pOr ON pOr.porProId=pbp.pOrProId and pbp.proType='P_Or'"
				+" LEFT JOIN bp_business_or_pro AS bOr ON bOr.borProId=pbp.borProId and pbp.proType='B_Or'"
				+" LEFT JOIN bp_fund_project AS sl ON (((sl.id = pDir.moneyPlanId) OR (sl.id = bDir.moneyPlanId) OR (sl.id = pOr.moneyPlanId) OR (sl.id = bOr.moneyPlanId)))"
				+" LEFT JOIN pl_bidding_type AS pt on pt.biddingTypeId=pbp.biddingTypeId"
				+" WHERE 1=1 ");
		String state=request.getParameter("state");
		if(null !=state && !"".equals(state)){
			sb.append(" and pbp.state="+state);
		}
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and pbp.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and pbp.bidProNumber like '%"+bidProNumber+"%'");
		}
		String proName=request.getParameter("proName");
		if(null!=proName && !proName.equals("")){
			sb.append(" and (bdir.proName like '%"+proName+"%' or bor.proName like '%"+proName+"%' or pdir.proName like '%"+proName+"%' or por.proName like '%"+proName+"%')");
		}
		String endIntentDateMin=request.getParameter("endIntentDateMin");
		if(null!=endIntentDateMin && !endIntentDateMin.equals("")){
			sb.append(" and pbp.endIntentDate>='"+endIntentDateMin+"'");
		}
		String endIntentDateMax=request.getParameter("endIntentDateMax");
		if(null!=endIntentDateMax && !endIntentDateMax.equals("")){
			sb.append(" and pbp.endIntentDate<='"+endIntentDateMax+"'");
		}
		if(null !=pb){
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("projectName",Hibernate.STRING)
			.addScalar("name",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("yearAccrualRate",Hibernate.BIG_DECIMAL)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("startIntentDate",Hibernate.DATE)
			.addScalar("endIntentDate",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("oppositeType",Hibernate.STRING)
			.addScalar("projectId",Hibernate.LONG)
			.addScalar("id",Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("projectName",Hibernate.STRING)
			.addScalar("name",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("yearAccrualRate",Hibernate.BIG_DECIMAL)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("startIntentDate",Hibernate.DATE)
			.addScalar("endIntentDate",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("oppositeType",Hibernate.STRING)
			.addScalar("projectId",Hibernate.LONG)
			.addScalar("id",Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class)).list();
		}
		
	}
	
	
	@Override
	public Integer countList(HttpServletRequest request,
			PagingBean pb) {
		StringBuffer sb=new StringBuffer("SELECT count(*) "
				+" FROM pl_bid_plan AS pbp "
				+" LEFT JOIN bp_persion_dir_pro AS pDir ON pDir.pdirProId=pbp.pDirProId  and pbp.proType='P_Dir'"
				+" LEFT JOIN bp_business_dir_pro AS bDir ON bDir.bdirProId=pbp.bdirProId and pbp.proType='B_Dir'"
				+" LEFT JOIN bp_persion_or_pro AS pOr ON pOr.porProId=pbp.pOrProId and pbp.proType='P_Or'"
				+" LEFT JOIN bp_business_or_pro AS bOr ON bOr.borProId=pbp.borProId and pbp.proType='B_Or'"
				+" LEFT JOIN bp_fund_project AS sl ON (((sl.id = pDir.moneyPlanId) OR (sl.id = bDir.moneyPlanId) OR (sl.id = pOr.moneyPlanId) OR (sl.id = bOr.moneyPlanId)))"
				+" LEFT JOIN pl_bidding_type AS pt on pt.biddingTypeId=pbp.biddingTypeId"
				+" WHERE 1=1 ");
		String state=request.getParameter("state");
		if(null !=state && !"".equals(state)){
			sb.append(" and pbp.state="+state);
		}
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and pbp.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and pbp.bidProNumber like '%"+bidProNumber+"%'");
		}
		String proName=request.getParameter("proName");
		if(null!=proName && !proName.equals("")){
			sb.append(" and (bdir.proName like '%"+proName+"%' or bor.proName like '%"+proName+"%' or pdir.proName like '%"+proName+"%' or por.proName like '%"+proName+"%')");
		}
		String endIntentDateMin=request.getParameter("endIntentDateMin");
		if(null!=endIntentDateMin && !endIntentDateMin.equals("")){
			sb.append(" and pbp.endIntentDate>='"+endIntentDateMin+"'");
		}
		String endIntentDateMax=request.getParameter("endIntentDateMax");
		if(null!=endIntentDateMax && !endIntentDateMax.equals("")){
			sb.append(" and pbp.endIntentDate<='"+endIntentDateMax+"'");
		}
		System.out.println(sb);
		Object object=this.getSession().createSQLQuery(sb.toString()).uniqueResult();
		return Integer.parseInt(object.toString());
	}

	@Override
	public List<PlBidPlan> listByTypeId(String proType, Long proId) {
		// TODO Auto-generated method stub
		String hql="from PlBidPlan as p where 1=1";
		if(null!=proType && proType.equals("P_Dir")){
			hql=hql+" and p.bpPersionDirPro.pdirProId="+proId;
		}else if(null!=proType && proType.equals("P_Or")){
			hql=hql+" and p.bpPersionOrPro.porProId="+proId;
		}else if(null!=proType && proType.equals("B_Or")){
			hql=hql+" and p.bpBusinessOrPro.borProId="+proId;
		}else if(null!=proType && proType.equals("B_Dir")){
			hql=hql+" and p.bpBusinessDirPro.bdirProId="+proId;
		}
		return this.findByHql(hql);
	}

	@Override
	public List<PlBidPlan> pdBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer("SELECT "
				+" p.bidId,"
				+" p.bidProName,"
				+" p.bidProNumber,"
				+" p.bidMoney,"
				+" p.bidMoneyScale,"
				+" p.startMoney,"
				+" p.riseMoney,"
				+" p.createtime,"
				+" p.updatetime,"
				+" p.state,"
				+" p.startInterestType,"
				+" p.bidStartTime,"
				+" p.prepareSellTime,"
				+" p.publishSingeTime,"
				+" p.bidEndTime,"
				+" p.proType,"
				+" p.isStart,"
				+" pd.proId,"
				+" pd.proName,"
				+" pd.moneyPlanId,"
				+" pd.yearInterestRate,"
				+" t.name as typeName"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_persion_dir_pro pd ON pd.pdirProId = p.pDirProId "
				+" LEFT JOIN pl_bidding_type t on t.biddingTypeId=p.biddingTypeId"
				+" WHERE  p.proType='P_Dir' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//  Date d =new Date();
		  String dd =format.format(new Date());
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String loanId=request.getParameter("loanId");
		if(null!=loanId && "underline".equals(loanId)){
			sb.append(" and pd.loanId is null ");
		}else if(null!=loanId && "online".equals(loanId)){
			sb.append(" and pd.loanId is not null ");
		}
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		sb.append(" ORDER BY p.createtime DESC ");
	//	System.out.println("-->"+sb.toString());
		if(null !=pb){
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class)).list();
		}
	}

	@Override
	public Long countPdBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select count(*) from   (SELECT "
				+" p.bidId"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_persion_dir_pro pd ON pd.pdirProId = p.pDirProId "
				+" WHERE  p.proType='P_Dir' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		
		sb.append(" ORDER BY p.createtime DESC ) as bb");
		//System.out.println("-->"+sb.toString());
		BigInteger count=new BigInteger("0");
		List list=this.getSession().createSQLQuery(sb.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count=(BigInteger) list.get(0);
			}
		}
		return count.longValue();
	}

	@Override
	public List<PlBidPlan> bdBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer("SELECT "
				+" p.bidId,"
				+" p.bidProName,"
				+" p.bidProNumber,"
				+" p.bidMoney,"
				+" p.bidMoneyScale,"
				+" p.startMoney,"
				+" p.riseMoney,"
				+" p.createtime,"
				+" p.updatetime,"
				+" p.state,"
				+" p.startInterestType,"
				+" p.bidStartTime,"
				+" p.prepareSellTime,"
				+" p.publishSingeTime,"
				+" p.bidEndTime,"
				+" p.proType,"
				+" p.isStart,"
				+" pd.proId,"
				+" pd.proName,"
				+" pd.moneyPlanId,"
				+" pd.yearInterestRate,"
				+" t.name as typeName"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_business_dir_pro pd ON pd.bdirProId = p.bdirProId "
				+" LEFT JOIN pl_bidding_type t on t.biddingTypeId=p.biddingTypeId"
				+" WHERE  p.proType='B_Dir' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		sb.append(" ORDER BY p.createtime DESC ");
		//System.out.println("-->"+sb.toString());
		if(null !=pb){
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class)).list();
		}
	}

	@Override
	public Long countbdBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select count(*) from   (SELECT "
				+" p.bidId"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_business_dir_pro pd ON pd.bdirProId = p.bdirProId"
				+" WHERE  p.proType='B_Dir' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		
		sb.append(" ORDER BY p.createtime DESC ) as bb");
	//	System.out.println("-->"+sb.toString());
		BigInteger count=new BigInteger("0");
		List list=this.getSession().createSQLQuery(sb.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count=(BigInteger) list.get(0);
			}
		}
		return count.longValue();
	}

	@Override
	public List<PlBidPlan> boBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer("SELECT "
				+" p.bidId,"
				+" p.bidProName,"
				+" p.bidProNumber,"
				+" p.bidMoney,"
				+" p.bidMoneyScale,"
				+" p.startMoney,"
				+" p.riseMoney,"
				+" p.createtime,"
				+" p.updatetime,"
				+" p.state,"
				+" p.startInterestType,"
				+" p.bidStartTime,"
				+" p.prepareSellTime,"
				+" p.publishSingeTime,"
				+" p.bidEndTime,"
				+" p.proType,"
				+" p.isStart,"
				+" pd.proId,"
				+" pd.proName,"
				+" pd.moneyPlanId,"
				+" pd.yearInterestRate,"
				+" t.name as typeName"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_business_or_pro pd ON pd.borProId= p.borProId"
				+" LEFT JOIN pl_bidding_type t on t.biddingTypeId=p.biddingTypeId"
				+" WHERE  p.proType='B_Or' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		sb.append(" ORDER BY p.createtime DESC ");
	//	System.out.println("-->"+sb.toString());
		if(null !=pb){
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class)).list();
		}
	}

	@Override
	public Long countboBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select count(*) from   (SELECT "
				+" p.bidId"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_business_or_pro pd ON pd.borProId= p.borProId "
				+" WHERE  p.proType='B_Or' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		
		sb.append(" ORDER BY p.createtime DESC ) as bb");
		//System.out.println("-->"+sb.toString());
		BigInteger count=new BigInteger("0");
		List list=this.getSession().createSQLQuery(sb.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count=(BigInteger) list.get(0);
			}
		}
		return count.longValue();
	}

	@Override
	public List<PlBidPlan> poBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer("SELECT "
				+" p.bidId,"
				+" p.bidProName,"
				+" p.bidProNumber,"
				+" p.bidMoney,"
				+" p.bidMoneyScale,"
				+" p.startMoney,"
				+" p.riseMoney,"
				+" p.createtime,"
				+" p.updatetime,"
				+" p.state,"
				+" p.startInterestType,"
				+" p.bidStartTime,"
				+" p.prepareSellTime,"
				+" p.publishSingeTime,"
				+" p.bidEndTime,"
				+" p.proType,"
				+" p.isStart,"
				+" pd.proId,"
				+" pd.proName,"
				+" pd.moneyPlanId,"
				+" pd.yearInterestRate,"
				+" t.name as typeName"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_persion_or_pro pd ON pd.porProId=p.pOrProId "
				+" LEFT JOIN pl_bidding_type t on t.biddingTypeId=p.biddingTypeId"
				+" WHERE  p.proType='P_Or' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		sb.append(" ORDER BY p.createtime DESC ");
		System.out.println("-->"+sb.toString());
		if(null !=pb){
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
			return this.getSession().createSQLQuery(sb.toString())
			.addScalar("bidId",Hibernate.LONG)
			.addScalar("bidProName",Hibernate.STRING)
			.addScalar("bidProNumber",Hibernate.STRING)
			.addScalar("bidMoney",Hibernate.BIG_DECIMAL)
			.addScalar("bidMoneyScale",Hibernate.DOUBLE)
			.addScalar("startMoney",Hibernate.BIG_DECIMAL)
			.addScalar("riseMoney",Hibernate.BIG_DECIMAL)
			.addScalar("createtime",Hibernate.DATE)
			.addScalar("updatetime",Hibernate.DATE)
			.addScalar("state",Hibernate.INTEGER)
			.addScalar("startInterestType",Hibernate.INTEGER)
			.addScalar("bidStartTime",Hibernate.INTEGER)
			.addScalar("prepareSellTime",Hibernate.DATE)
			.addScalar("publishSingeTime",Hibernate.DATE)
			.addScalar("bidEndTime",Hibernate.DATE)
			.addScalar("proType",Hibernate.STRING)
			.addScalar("proId",Hibernate.LONG)
			.addScalar("proName",Hibernate.STRING)
			.addScalar("moneyPlanId",Hibernate.LONG)
			.addScalar("yearInterestRate",Hibernate.BIG_DECIMAL)
			.addScalar("typeName",Hibernate.STRING)
			.addScalar("isStart",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(PlBidPlan.class)).list();
		}
	}
	@Override
	public Long countPoBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select count(*) from   (SELECT "
				+" p.bidId"
				+" from  pl_bid_plan p "
				+" LEFT JOIN bp_persion_or_pro pd ON pd.porProId=p.pOrProId"
				+" WHERE  p.proType='B_Or' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date d =new Date();
		  String dd =format.format(d);
		  sb.append(" and p.publishSingeTime<='"+dd+"'");
		StringBuffer sbs = new StringBuffer();
		if(bidStates.length > 0) {
			for(int status : bidStates) {
				sbs.append(status);
				sbs.append(",");
			}
		}
		String status = "";
		if(!"".equals(sbs.toString())) {
			status = sbs.toString().substring(0, sbs.length()-1);
		}	
			if(!status.equals("")){
				sb.append(" and p.state in ("+status+")");
			}else{
				sb.append(" and p.state>0");
			}
		
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		
		sb.append(" ORDER BY p.createtime DESC ) as bb");
	//	System.out.println("-->"+sb.toString());
		BigInteger count=new BigInteger("0");
		List list=this.getSession().createSQLQuery(sb.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count=(BigInteger) list.get(0);
			}
		}
		return count.longValue();
	}

	@Override
	public List<PlBidPlan> getPlanByStatusList(Short valueOf, PagingBean pb,
			Map map) {
		// TODO Auto-generated method stub
		String hql=" from PlBidPlan  as p where p.state="+valueOf;
		return this.getSession().createQuery(hql).setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).list();
	}
	
	@Override
	public List<PlBidPlan> getByProType(Map<String, String> map) {
		String hql="from PlBidPlan as p where p.state  in(7,10)";
		String proType = map.get("proType");
		String proId = map.get("proId");
		if(null!=proType && proType.equals("P_Dir")){
			hql=hql+" and p.bpPersionDirPro.pdirProId="+proId;
		}else if(null!=proType && proType.equals("P_Or")){
			hql=hql+" and p.bpPersionOrPro.porProId="+proId;
		}else if(null!=proType && proType.equals("B_Or")){
			hql=hql+" and p.bpBusinessOrPro.borProId="+proId;
		}else if(null!=proType && proType.equals("B_Dir")){
			hql=hql+" and p.bpBusinessDirPro.bdirProId="+proId;
		}
		return this.findByHql(hql);
	}
	@Override
	public List<PlBidPlan> listByBdirProId(Long bdirProId){
		String sql = "select * from pl_bid_plan where bdirProId="+bdirProId;
		return this.getSession().createSQLQuery(sql).addEntity(PlBidPlan.class).list();
	}
	@Override
	public List<PlBidPlan> listByPdirProId(Long pdirProId){
		String sql = "select * from pl_bid_plan where pdirProId="+pdirProId;
		return this.getSession().createSQLQuery(sql).addEntity(PlBidPlan.class).list();
	}

	@Override
	public PlBidPlan getplanByLoanOrderNo(String loanOrderNo) {
		String sql = "select * from pl_bid_plan where loanOrderNo="+loanOrderNo;
		 List<PlBidPlan> list = this.getSession().createSQLQuery(sql).addEntity(PlBidPlan.class).list();
		 if (list != null && list.size() > 0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public PlBidPlan getByBdirProId(Long aLong) {
		String sql = "SELECT * from pl_bid_plan where bdirProId= "+aLong;

		List<PlBidPlan> list = getSession().createSQLQuery(sql).addEntity(PlBidPlan.class).list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}else {
			return null;
		}
	}


}