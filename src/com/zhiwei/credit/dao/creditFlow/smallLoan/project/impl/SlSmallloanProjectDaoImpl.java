package com.zhiwei.credit.dao.creditFlow.smallLoan.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.model.creditFlow.finance.ProYearRate;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlSmallloanProjectDaoImpl extends BaseDaoImpl<SlSmallloanProject> implements SlSmallloanProjectDao{
	
	
	public SlSmallloanProjectDaoImpl() {
		super(SlSmallloanProject.class);
	}
	@Resource
	private SlFundIntentDao slFundIntentDao;
	
	
	public List<SlSmallloanProject> getProjectById(Long projectId){
		String hql ="from SlSmallloanProject sl where sl.projectId="+projectId;
		return super.findByHql(hql);
	}
	public List<SlSmallloanProject> getProjectByCompanyId(Long companyId){
		String hql ="from SlSmallloanProject sl where sl.projectStatus=1 and sl.companyId="+companyId;
		return super.findByHql(hql);
	}

	@Override
	public List<ProYearRate> getProYearRate() {
		String hql = "select case when a.operationType= 6575 then (a.accrualMoney-a.commisionMoney-a.elseMoney) else (a.accrualMoney+a.commisionMoney+a.elseMoney) end as yearRate,b.title as operationTypeName,a.projectName,a.projectMoney, a.commisionMoney,a.elseMoney,a.accrualMoney"
				+ " from SlSmallloanProject a ,CsDicAreaDynam b,SVEnterprisePerson c where a.operationType=b.id and a.oppositeID=c.id and a.oppositeType=c.type";

		// String
		// hql="select a.operationType,b.title as operationTypeName,a.projectName, a.projectNumber, a.oppositeType,c.name as oppositeName, a.oppositeID ,a.accrualtype, a.accrual, a.startDate, a.intentDate, a.projectMoney, a.accrualMoney, a.payProjectMoney, a.payAccrualMoney, (a.projectMoney-a.payProjectMoney) as nopayProjectMoney,(a.accrualMoney-a.payAccrualMoney) as nopayAccrualMoney, a.flatMoney"+
		// "  from sl_smallloan_project a left join cs_dic_area_dynam b on a.operationType=b.id left join s_v_enterprise_person c on a.oppositeID=c.id and a.oppositeType=c.type";

		return getSession().createQuery(hql).list();
		// return getSession().createSQLQuery(hql).list();
	}

	@Override
	public List<SlSmallloanProject> getProDetail(Map<String, String> map) {
		Short ot;
		BigDecimal pmg;
		BigDecimal pml;
		Short at;
		BigDecimal a;
		BigDecimal factor;
		if (map.get("Q_operationType_SN_EQ").equals("")) {
			ot = null;
		} else {
			ot = Short.valueOf(map.get("Q_operationType_SN_EQ"));
		}
		if (map.get("Q_projectMoney_BD_GE").equals("")) {
			pmg = null;
		} else {
			double pm1 = Long.valueOf(map.get("Q_projectMoney_BD_GE"));
			pmg = BigDecimal.valueOf(pm1);
		}
		if (map.get("Q_projectMoney_BD_LE").equals("")) {
			pml = null;
		} else {
			double pm1 = Long.valueOf(map.get("Q_projectMoney_BD_LE"));
			pml = BigDecimal.valueOf(pm1);
		}
		String pn = map.get("Q_projectName_S_LK");
		if (map.get("Q_accrualtype_SN_EQ").equals("")) {
			at = null;
		} else {
			at = Short.valueOf(map.get("Q_accrualtype_SN_EQ"));
		}
		if (map.get("Q_accrual_BD_EQ").equals("")) {
			a = null;
		} else {
			double a1 = Long.valueOf(map.get("Q_accrual_BD_EQ"));
			a = BigDecimal.valueOf(a1);
		}
		if (map.get("Q_payProjectMoney_BD_EQ").equals("")) {
			factor = null;
		} else {
			double a1 = Long.valueOf(map.get("Q_payProjectMoney_BD_EQ"));
			factor = BigDecimal.valueOf(a1);
		}

		String hql = "from SlSmallloanProject sl where sl.annualNetProfit = '0'";
		// String
		// hql="from SlSmallloanProject sl where sl.operationType=? and sl.projectMoney >= ? and sl.projectMoney <= ? "+
		// "and sl.projectName like ? and sl.accrualtype=? and sl.accrual =?";
		if (ot != null) {
			hql = hql + " and sl.operationType = " + ot;
		}
		if (pmg != null) {
			hql = hql + " and sl.projectMoney >= " + pmg;
		}
		if (pml != null) {
			hql = hql + " and sl.projectMoney <= " + pml;
		}
		if (pn != null) {
			hql = hql + " and sl.projectName like '%" + pn + "%'";
		}
		if (at != null) {
			hql = hql + " and sl.accrualtype= " + at;
		}
		if (a != null) {
			hql = hql + " and sl.accrual =" + a;
		}
		if (factor != null) {

			hql = hql
					+ " and (sl.projectMoney-sl.payProjectMoney)/sl.projectMoney >= "
					+ factor;
		}

		Query query = getSession().createQuery(hql);

		return query.list();
	}

	@Override
	public List<SlSmallloanProject> getList(short operationType, Date startTime,
			Date endTime) {
		String hql="from SlSmallloanProject as sl where sl.operationType=? and sl.startDate between ? and ?";
		return getSession().createQuery(hql).setParameter(0, operationType).setParameter(1, startTime).setParameter(2, endTime).list();
	}

	@Override
	public List<SlSmallloanProject> getListByProjectStatus(short operationType,
			Date startTime, Date endTime, short projectStatus) {
		String hql="from SlSmallloanProject as sl where sl.operationType=? and sl.startDate between ? and ? and sl.projectStatus=?";
		return getSession().createQuery(hql).setParameter(0, operationType).setParameter(1, startTime).setParameter(2, endTime).setParameter(3, projectStatus).list();
	}

	@Override
	public List<SlSmallloanProject> getTodayProjectList(Date date) {
		String strs=ContextUtil.getBranchIdsStr();
		String hql="from SlSmallloanProject as sl where  date_format(sl.createDate,'%Y-%m-%d') = date_format(?,'%Y-%m-%d')";
		if(null!=strs && !strs.equals("")){
			hql=hql+" and sl.companyId in ("+strs+")";
		}
		return getSession().createQuery(hql).setParameter(0, date).list();
	}

	@Override
	public String getList(String customerType, Long customerId) {
		String shql="from SlSmallloanProject as sl where sl.oppositeType=? and sl.oppositeID=?";
		List<SlSmallloanProject> slist=getSession().createQuery(shql).setParameter(0, customerType).setParameter(1, customerId).list();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(SlSmallloanProject sl:slist){
			buff.append("{\"projectId\":");
			buff.append(sl.getProjectId());
			buff.append(",\"businessType\":'");
			buff.append(sl.getBusinessType());
			buff.append("',\"projectName\":'");
			buff.append(sl.getProjectName());
			buff.append("',\"projectMoney\":");
			buff.append(sl.getProjectMoney());
			buff.append(",\"startDate\":'");
			if(sl.getStartDate()!=null){
				buff.append(df.format(sl.getStartDate()));
			}
			buff.append("',\"endDate\":'");
			if(sl.getIntentDate()!=null){
				buff.append(df.format(sl.getIntentDate()));
			}
			buff.append("',\"principalMoney\":");
			BigDecimal principalMoney=slFundIntentDao.getSumMoney(sl.getProjectId(), "SmallLoan", "principalRepayment");
			if(null!=principalMoney){
				buff.append(principalMoney);
			}
			buff.append(",\"interestMoney\":");
			BigDecimal interestMoney=slFundIntentDao.getSumMoney(sl.getProjectId(), "SmallLoan", "loanInterest");
			if(null!=interestMoney){
				buff.append(interestMoney);
			}
			buff.append(",\"status\":'");
			Date date=new Date();
			if(sl.getIntentDate()!=null){
				if(date.getTime() > sl.getIntentDate().getTime()){
					buff.append("已结束'},");
				}else{
					buff.append("进行中'},");
				}
			}else{
				buff.append("'},");
			}
			
		}
		String ghql="from GLGuaranteeloanProject as gl where gl.oppositeType=? and gl.oppositeID=?";
		List<GLGuaranteeloanProject> glist=getSession().createQuery(ghql).setParameter(0, customerType).setParameter(1, customerId).list();
		for(GLGuaranteeloanProject gl:glist){
			buff.append("{\"projectId\":");
			buff.append(gl.getProjectId());
			buff.append(",\"businessType\":'");
			buff.append(gl.getBusinessType());
			buff.append("',\"projectName\":'");
			buff.append(gl.getProjectName());
			buff.append("',\"projectMoney\":");
			buff.append(gl.getProjectMoney());
			buff.append(",\"startDate\":'");
			if(gl.getAcceptDate()!=null){
				buff.append(df.format(gl.getAcceptDate()));
			}
			buff.append("',\"endDate\":'");
			if(gl.getIntentDate()!=null){
				buff.append(df.format(gl.getIntentDate()));
			}
			buff.append("',\"principalMoney\":");
			BigDecimal principalMoney=slFundIntentDao.getSumMoney(gl.getProjectId(), "Guarantee", "principalRepayment");
			if(null!=principalMoney){
				buff.append(principalMoney);
			}
			buff.append(",\"interestMoney\":");
			BigDecimal interestMoney=slFundIntentDao.getSumMoney(gl.getProjectId(), "Guarantee", "loanInterest");
			if(null!=interestMoney){
				buff.append(interestMoney);
			}
			buff.append(",\"status\":'");
			Date date=new Date();
			if(gl.getIntentDate()!=null){
				if(date.getTime() > gl.getIntentDate().getTime()){
					buff.append("已结束'},");
				}else{
					buff.append("进行中'},");
				}
			}else{
				buff.append("'},");
			}
		}
		String fhql="from FlFinancingProject as fl where fl.oppositeType=? and fl.oppositeID=?";
		List<FlFinancingProject> flist=getSession().createQuery(fhql).setParameter(0, customerType).setParameter(1,customerId).list();
		for(FlFinancingProject fl:flist){
			buff.append("{\"projectId\":");
			buff.append(fl.getProjectId());
			buff.append(",\"businessType\":'");
			buff.append(fl.getBusinessType());
			buff.append("',\"projectName\":'");
			buff.append(fl.getProjectName());
			buff.append("',\"projectMoney\":");
			buff.append(fl.getProjectMoney());
			buff.append(",\"startDate\":'");
			if(fl.getStartDate()!=null){
				buff.append(df.format(fl.getStartDate()));
			}
			buff.append("',\"endDate\":'");
			if(fl.getIntentDate()!=null){
				buff.append(df.format(fl.getIntentDate()));
			}
			buff.append("',\"principalMoney\":");
			BigDecimal principalMoney=slFundIntentDao.getSumMoney(fl.getProjectId(), "Financing", "FinancingRepay");
			if(null!=principalMoney){
				buff.append(principalMoney);
			}
			buff.append(",\"interestMoney\":");
			BigDecimal interestMoney=slFundIntentDao.getSumMoney(fl.getProjectId(), "Financing", "FinancingInterest");
			if(null!=interestMoney){
				buff.append(interestMoney);
			}
			buff.append(",\"status\":'");
			Date date=new Date();
			if(fl.getIntentDate()!=null){
				if(date.getTime() > fl.getIntentDate().getTime()){
					buff.append("已结束'},");
				}else{
					buff.append("进行中'},");
				}
			}else{
				buff.append("'},");
			}
		}
		String phql="from PlPawnProject as fl where fl.oppositeType=? and fl.oppositeID=?";
		List<PlPawnProject> plist=getSession().createQuery(phql).setParameter(0, customerType).setParameter(1,customerId).list();
		for(PlPawnProject fl:plist){
			buff.append("{\"projectId\":");
			buff.append(fl.getProjectId());
			buff.append(",\"businessType\":'");
			buff.append(fl.getBusinessType());
			buff.append("',\"projectName\":'");
			buff.append(fl.getProjectName());
			buff.append("',\"projectMoney\":");
			buff.append(fl.getProjectMoney());
			buff.append(",\"startDate\":'");
			if(fl.getStartDate()!=null){
				buff.append(df.format(fl.getStartDate()));
			}
			buff.append("',\"endDate\":'");
			if(fl.getIntentDate()!=null){
				buff.append(df.format(fl.getIntentDate()));
			}
			buff.append("',\"principalMoney\":");
			BigDecimal principalMoney=slFundIntentDao.getSumMoney(fl.getProjectId(), "Financing", "pawnPrincipalRepayment");
			if(null!=principalMoney){
				buff.append(principalMoney);
			}
			buff.append(",\"interestMoney\":");
			BigDecimal interestMoney=slFundIntentDao.getSumMoney(fl.getProjectId(), "Financing", "pawnLoanInterest");
			if(null!=interestMoney){
				buff.append(interestMoney);
			}
			buff.append(",\"status\":'");
			Date date=new Date();
			if(fl.getIntentDate()!=null){
				if(date.getTime() > fl.getIntentDate().getTime()){
					buff.append("已结束'},");
				}else{
					buff.append("进行中'},");
				}
			}else{
				buff.append("'},");
			}
		}
		String ffhql="from FlLeaseFinanceProject as fl where fl.oppositeType=? and fl.oppositeID=?";
		List<FlLeaseFinanceProject> fflist=getSession().createQuery(ffhql).setParameter(0, customerType).setParameter(1,customerId).list();
		for(FlLeaseFinanceProject fl:fflist){
			buff.append("{\"projectId\":");
			buff.append(fl.getProjectId());
			buff.append(",\"businessType\":'");
			buff.append(fl.getBusinessType());
			buff.append("',\"projectName\":'");
			buff.append(fl.getProjectName());
			buff.append("',\"projectMoney\":");
			buff.append(fl.getProjectMoney());
			buff.append(",\"startDate\":'");
			if(fl.getStartDate()!=null){
				buff.append(df.format(fl.getStartDate()));
			}
			buff.append("',\"endDate\":'");
			if(fl.getIntentDate()!=null){
				buff.append(df.format(fl.getIntentDate()));
			}
			buff.append("',\"principalMoney\":");
			BigDecimal principalMoney=slFundIntentDao.getSumMoney(fl.getProjectId(), "Financing", "leasePrincipalRepayment");
			if(null!=principalMoney){
				buff.append(principalMoney);
			}
			buff.append(",\"interestMoney\":");
			BigDecimal interestMoney=slFundIntentDao.getSumMoney(fl.getProjectId(), "Financing", "leaseFeeCharging");
			if(null!=interestMoney){
				buff.append(interestMoney);
			}
			buff.append(",\"status\":'");
			Date date=new Date();
			if(fl.getIntentDate()!=null){
				if(date.getTime() > fl.getIntentDate().getTime()){
					buff.append("已结束'},");
				}else{
					buff.append("进行中'},");
				}
			}else{
				buff.append("'},");
			}
		}
		buff.deleteCharAt(buff.length()-1);
		buff.append("]}");
		return buff.toString();
	}

	@Override
	public SlSmallloanProject getListByoperationType(Long projectId,
			String operationType) {
		String hql="from SlSmallloanProject as s where s.projectId=? and s.operationType=?";
		List<SlSmallloanProject> list=getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, operationType).list();
		SlSmallloanProject s=null;
		if(null!=list && list.size()>0){
			s=list.get(0);
		}
		return s;
	}
//用于根据项目编号查询项目
	@Override
	public SlSmallloanProject findByprojectNumber(String projectName) {
		// TODO Auto-generated method stub
		String hql ="from SlSmallloanProject as s where s.projectNumber=? ";
		List<SlSmallloanProject> list=getSession().createQuery(hql).setParameter(0, projectName).list();
		SlSmallloanProject sl=null;
		if(null!=list && list.size()>0){
			sl=list.get(0);
		}
		return sl;
	}

	@Override
	public List<SlSmallloanProject> getListOfCustomer(String oppositeType,
			Long oppositeID) {
		String hql="from SlSmallloanProject as s where s.oppositeType=? and s.oppositeID=?";
		return getSession().createQuery(hql).setParameter(0, oppositeType).setParameter(1, oppositeID).list();
	}

	@Override
	public List getprojectList(Long companyId,
			String startDate, String endDate, String status) {
		StringBuffer hql=new StringBuffer("select count(s.projectId),sum(s.projectMoney) from SlSmallloanProject as s where s.projectStatus!=0 and s.companyId=? and s.startDate>='"+startDate+"' and s.startDate<='"+endDate+"'");
		if(null!=status && status.equals("1")){
			hql.append(" and s.projectMoney>=10000000");
		}else if(null!=status && status.equals("2")){
			hql.append(" and s.projectMoney>=5000000 and s.projectMoney<10000000");
		}else if(null!=status && status.equals("3")){
			hql.append(" and s.projectMoney>=1000000 and s.projectMoney<5000000");
		}else if(null!=status && status.equals("4")){
			hql.append(" and s.projectMoney>500000 and s.projectMoney<1000000");
			
		}else if(null!=status && status.equals("5")){
			hql.append(" and s.projectMoney<=500000");
		}
		return getSession().createQuery(hql.toString()).setParameter(0, companyId).list();
	}
	
	@Override
	public SlSmallloanProject getProjectNumber(String projectNumberKey){
		String hql="from SlSmallloanProject as gl where gl.projectNumber like '%"+projectNumberKey+"%'order by gl.projectId DESC";
		List<SlSmallloanProject> list=super.findByHql(hql);
		SlSmallloanProject slSmallloanProject=null;
		if(null!=list && list.size()>0){
			slSmallloanProject=list.get(0);
			return slSmallloanProject;
		}
		return null;
	}
	@Override
	public List<SlSmallloanProject> findList(String projectName,
			String projectNumber, String minMoneyStr, String maxMoneyStr,
			String projectStatus, PagingBean pb) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hqlbuf = new StringBuffer("from SlSmallloanProject where 1=1 "); 
		if(projectName != null && !projectName.trim().equals("")){
			params.add("%"+projectName.trim()+"%");
			hqlbuf.append("and projectName like ? ");
		}
		if(projectNumber != null && !projectNumber.trim().equals("")){
			params.add("%"+projectNumber.trim()+"%");
			hqlbuf.append("and projectNumber like ? ");
		}
		if(minMoneyStr != null && !minMoneyStr.trim().equals("")){
			BigDecimal mixMoney = new BigDecimal(minMoneyStr.trim());
			params.add(mixMoney);
			hqlbuf.append("and projectMoney >= ? ");
		}
		if(maxMoneyStr != null && !maxMoneyStr.trim().equals("")){
			BigDecimal maxMoney = new BigDecimal(maxMoneyStr.trim());
			params.add(maxMoney);
			hqlbuf.append("and projectMoney <= ? ");
		}
		if(projectStatus != null && !projectStatus.equals("")){
			params.add(Short.valueOf(projectStatus));
			hqlbuf.append("and projectStatus = ? ");
		} else{
			hqlbuf.append("and projectStatus between 1 and 4 ");
		}
		return findByHql(hqlbuf.toString(), params.toArray(), pb);
	}
	@Override
	public void approveProjectList(PageBean<SlSmallloanProject> pageBean,
			Short projectStatus,String userIdsStr) {
		 	HttpServletRequest request = pageBean.getRequest();
			String businessManager = request.getParameter("businessManager");
			String oppositeTypeValue = request.getParameter("oppositeTypeValue");
			String departId = request.getParameter("departId");
			String projectMoneyMax = request.getParameter("projectMoneyMax");
			String projectMoneyMin = request.getParameter("projectMoneyMin");
			String projectName = request.getParameter("projectName");
			String projectNumber = request.getParameter("projectNumber");
			String customerName = request.getParameter("customerName");
			String archivesBelonging=request.getParameter("archivesBelonging");
		
			StringBuffer sb = new StringBuffer ( ""+
			"select * from ( "+
			"SELECT "+
			"p.projectId as projectId, "+
			"p.appUserId as appUserId, "+
			"p.appUserName as appUserName, "+
			"p.businessType as businessType, "+
			"p.companyId as companyId, "+
			"p.createDate as createDate, "+
			"p.endDate as endDate, "+
			"p.startDate as startDate, "+
			"p.intentDate as intentDate, "+
			"p.operationType as operationType, "+
			"p.oppositeID as oppositeID, "+
			"p.oppositeType as oppositeType, "+
			"p.projectMoney as projectMoney, "+
			"p.projectName as projectName, "+
			"p.projectNumber as projectNumber, "+
			"p.projectStatus as projectStatus, "+
			"p.departId as departId, "+
			"p.departMentName as departMentName, "+
			"run.runId as runId, "+
			"o.org_name as orgName, "+
			"pf.taskId as taskId, "+
			"pf.activityName as activityName,"+
			"u.fullname as executor , "+
			"( case  "+
			"		   when p.oppositeType = 'person_customer' then  "+
			"		  (select  cp.`name` from cs_person as cp where cp.id = p.oppositeID ) "+
			"		   when p.oppositeType = 'company_customer' then    "+
			"		  (select enterprisename from cs_enterprise where id = p.oppositeID)  "+
			"		   else '' "+
			"		   end) as customerName, "+
			
			"p.archivesBelonging as archivesBelonging "+
			"FROM "+
			"	sl_smallloan_project AS p "+
			"LEFT JOIN  "+
			"	(select pr.runId,pr.projectId  from process_run as pr left join sl_smallloan_project as ss on pr.projectId = ss.projectId GROUP by pr.projectId ) "+
			" AS run ON run.projectId = p.projectId "+
			"LEFT JOIN organization AS o ON p.departId = o.org_id "+
			
			"left join  "+
			"(  "+
			"	SELECT  "+
			"		pf.*,  "+
			"	  pr.projectId  "+
			"	FROM  "+
			"	 ( select formId,runId,activityName,taskId from process_form order by formId desc ) as pf   "+
			"	 left join  "+
			"    process_run as pr  "+
			"  on pf.runId = pr.runId  "+
			"	group by pr.projectId   "+
			" )  "+ 
			" as pf on pf.projectId = p.projectId  "+ 
			" left join jbpm4_task as task on task.DBID_=pf.taskId  "+ 
			" left join app_user as u on u.userId=task.ASSIGNEE_  "+
			
			
			"WHERE "+
			"p.projectStatus = "+projectStatus+
			" ) as a " +
			" where 1=1  ");
			
			/*--------查询总条数---------*/
			StringBuffer totalCounts = new StringBuffer ("select count(*) from ( ");
			if(null!=userIdsStr && !userIdsStr.equals("")){
				sb.append(" and fn_check_repeat(a.appUserId,'"+userIdsStr+"') = 1");
			}
			
			if(null!=projectNumber&&!"".equals(projectNumber)){
				sb.append(" and a.projectNumber like '%"+projectNumber+"%' ");
			}
			if(null!=projectName&&!"".equals(projectName)){
				sb.append(" and a.projectName like '%"+projectName+"%' ");
			}
			if(null!=businessManager&&!"".equals(businessManager)){
				sb.append(" and a.appUserId like '%"+businessManager+"%' ");
			}
			if(null!=departId&&!"".equals(departId)){
				sb.append(" and a.departId like '%"+departId+"%' ");
			}
			if(null!=customerName&&!"".equals(customerName)){
				sb.append(" and a.customerName like '%"+customerName+"%' ");
			}
			if(null!=oppositeTypeValue&&!"".equals(oppositeTypeValue)){
				sb.append(" and a.oppositeType = '"+oppositeTypeValue+"' ");
			}
			if(null!=projectMoneyMin&&!"".equals(projectMoneyMin)){
				sb.append(" and a.projectMoney >= "+projectMoneyMin+" ");
			}
			if(null!=projectMoneyMax&&!"".equals(projectMoneyMax)){
				sb.append(" and a.projectMoney <= "+projectMoneyMax+" ");
			}
			if(null!=archivesBelonging && !"".equals(archivesBelonging)){
				sb.append(" and a.archivesBelonging="+archivesBelonging);
			}
			sb.append(" order by a.projectId desc ");
			totalCounts.append(sb).append(") as b");
			List list = this.getSession().createSQLQuery(sb.toString())
			.addScalar("projectId",Hibernate.LONG)
			.addScalar("appUserId", Hibernate.STRING)
			.addScalar("appUserName", Hibernate.STRING)
			.addScalar("businessType", Hibernate.STRING)
			.addScalar("createDate", Hibernate.DATE)
			.addScalar("endDate", Hibernate.DATE)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("operationType", Hibernate.STRING)
			.addScalar("oppositeID", Hibernate.LONG)
			.addScalar("oppositeType", Hibernate.STRING)
			.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("projectNumber", Hibernate.STRING)
			.addScalar("projectStatus", Hibernate.SHORT)
			.addScalar("departId", Hibernate.LONG)
			.addScalar("departMentName", Hibernate.STRING)
			.addScalar("runId", Hibernate.LONG)
			.addScalar("orgName", Hibernate.STRING)
			.addScalar("taskId", Hibernate.STRING)
			.addScalar("activityName",Hibernate.STRING)
			.addScalar("executor",Hibernate.STRING)
			.addScalar("customerName",Hibernate.STRING)
			
			.setResultTransformer(Transformers.aliasToBean(SlSmallloanProject.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
			pageBean.setResult(list);
			
			BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
			pageBean.setTotalCounts(total.intValue());
		
	}
	
	@Override
	public void getprojectByCustomerId(Integer personId,PageBean pageBean) {
		
		String sql =" select * from (select * from sl_smallloan_project as sp  where sp.oppositeID = "+personId+
					" order by sp.startDate desc ) as a GROUP BY a.oppositeID,a.oppositeType";
		List list = this.getSession().createSQLQuery(sql.toString())
		.addScalar("projectId",Hibernate.LONG)
		.addScalar("appUserId", Hibernate.STRING)
		.addScalar("appUserName", Hibernate.STRING)
		.addScalar("businessType", Hibernate.STRING)
		.addScalar("createDate", Hibernate.DATE)
		.addScalar("endDate", Hibernate.DATE)
		.addScalar("startDate", Hibernate.DATE)
		.addScalar("intentDate", Hibernate.DATE)
		.addScalar("operationType", Hibernate.STRING)
		.addScalar("oppositeID", Hibernate.LONG)
		.addScalar("oppositeType", Hibernate.STRING)
		.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
		.addScalar("projectName", Hibernate.STRING)
		.addScalar("projectNumber", Hibernate.STRING)
		.addScalar("projectStatus", Hibernate.SHORT)
		.addScalar("departId", Hibernate.LONG)
		.addScalar("departMentName", Hibernate.STRING)
		.addScalar("isOtherFlow", Hibernate.SHORT)
		.addScalar("productId",Hibernate.LONG)
		.setResultTransformer(Transformers.aliasToBean(SlSmallloanProject.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
		pageBean.setResult(list);
		
		String totalCountsSql ="select count(*) from (SELECT * from (select * from sl_smallloan_project as sp  where  sp.oppositeID = "+personId+" order by sp.startDate desc ) as b GROUP BY b.oppositeID,b.oppositeType) as a ";
		BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCountsSql.toString()).uniqueResult();
		if(null==total){
			pageBean.setTotalCounts(0);
		}else{
		    pageBean.setTotalCounts(total.intValue());
		}
	   
			
	}
	}
