package com.zhiwei.credit.dao.creditFlow.fund.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpFundProjectDaoImpl extends BaseDaoImpl<BpFundProject> implements BpFundProjectDao{
	
	public BpFundProjectDaoImpl() {
		super(BpFundProject.class);
	}

	@Override
	public BpFundProject getByProjectId(Long projectId, Short type) {
		String hql="from BpFundProject as s where s.projectId=? and s.flag=?";
		
		List<BpFundProject> list=super.findByHql(hql, new Object[]{projectId,type});
		super.flush();
		BpFundProject s=null;
		if(null!=list && list.size()>0){
			s=list.get(0);
		}
		return s;
	}

	@Override
	public List<BpFundProject> getByProjectId(Long projectId) {
		String hql="from BpFundProject as s where s.projectId="+projectId;
		
		List<BpFundProject> list=  super.findByHql(hql);
		return list;
	}
	@Override
	public void projectList(PageBean<BpFundProject> pageBean,Map<String,String> map) {
		HttpServletRequest request=pageBean.getRequest();
		/*--------查询总条数---------*/
		StringBuffer totalCounts = new StringBuffer ("select count(*) from (");
		StringBuffer sql=new StringBuffer("SELECT "
				+" f.id,"
				+" f.projectId,"
				+" f.productId,"
				+" f.operationType,"
				+" f.flowType,"
				+" f.oppositeType,"
				+" f.oppositeID,"
				+" f.projectName,"
				+" f.projectNumber,"
				+" f.projectMoney,"
				+" f.startDate,"
				+" f.intentDate,"
				+" f.projectStatus,"
				+" f.appUserId,"
				+" f.appUserName,"
				+" f.ownJointMoney,"
				+" run.runId,"
				+" pf.taskId,"
				+" pf.activityName,"
				+" o.org_name as orgName,"
				+" s.departId,"
				+" s.departMentName,"
				+" d.itemValue as oppositeTypeValue,"
				+" u.fullname as executor,"
				+" ss.endDate as supEndDate,"
				+" f.businessType,"
				+" f.isOtherFlow"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join dictionary_independent as d on d.dicKey=f.oppositeType"
				+" LEFT JOIN organization AS o ON f.companyId = o.org_id"
				+" left join (select s.projectId,s.endDate from sl_supervise_record as s left join bp_fund_project as ps ON s.projectId=ps.projectId) as ss on ss.projectId = f.projectId"
				+" left join process_run as run on run.projectId=f.projectId "
				+" left join (select f.formId,f.runId,f.taskId,f.`status`,f.activityName from (select ro.* from process_form as ro where ro.taskId IS NOT NULL AND `ro`.`status` IN(0, 5, 6) order by ro.formId DESC) as f  group by f.runId order by f.formId desc ) as pf on pf.runId=run.runId"
				+" left join jbpm4_task as task on task.DBID_=pf.taskId"
				+" left join app_user as u on u.userId=task.ASSIGNEE_"
				+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		//查询
		String queryType=map.get("managerType");
		if(queryType!=null&&!"".equals(queryType)&&(queryType.equals("13")||queryType.equals("2")||queryType.equals("3"))){
			sql=new StringBuffer("SELECT "
						+"f.id, "
						+"f.projectId, "
						+"f.productId, "
						+"f.operationType, "
						+"f.flowType, "
						+"f.oppositeType, "
						+"f.oppositeID, "
						+"f.projectName, "
						+"f.projectNumber, "
						+"f.projectMoney, "
						+"f.startDate, "
						+"f.intentDate, "
						+"f.projectStatus, "
						+"f.appUserId, "
						+"f.appUserName, "
						+"f.ownJointMoney, "
						+"f.businessType, "
						+"f.isOtherFlow, "
						+"o.org_name AS orgName, "
						+"s.departId, "
						+"s.departMentName, "
						+"d.itemValue as oppositeTypeValue  "
						+"FROM  "
						+"sl_fund_intent AS p  "
						+"left join bp_fund_project as f on (p.preceptId=f.id and p.businessType = 'SmallLoan') "
						+"left join dictionary_independent as d on d.dicKey=f.oppositeType "
						+"LEFT JOIN sl_smallloan_project AS s ON s.projectId = f.projectId "
						+"LEFT JOIN organization AS o ON f.companyId = o.org_id "
						+"WHERE "
						+"p.isCheck = 0 "
						+"AND p.isValid = 0 "
						+"AND p.notMoney > 0 "
						+"AND p.businessType = 'SmallLoan' "
						+"and p.fundType !='principalLending'");
		}
		
		String status=map.get("projectStatuses");
		if(null!=status && !"".equals(status)){
			sql.append(" and f.projectStatus in ("+status+")");
		}else{
			sql.append(" and f.projectStatus>0");
		}
		String userIds= map.get("userId");
		if(null!=userIds && !userIds.equals("")){
			sql.append(" and fn_check_repeat(f.appUserId,'"+userIds+"') = 1");
		}
		String companyId= map.get("companyId");
		if(null!=companyId && !"".equals(companyId)){
			sql.append(" and f.companyId in ("+companyId+")");
		}
		String projectNumber = request.getParameter("projectNumber");
		if(null != projectNumber && !"".equals(projectNumber)) {
			sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
		}
		String projectName = request.getParameter("projectName");
		if(null != projectName && !"".equals(projectName)) {
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("oppositeType");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String orgUserId = request.getParameter("orgUserId");
		if(null != orgUserId && !"".equals(orgUserId)) {
			sql.append("and s.departId ="+Long.valueOf(orgUserId));
		}
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
		}
		String archivesBelonging=request.getParameter("archivesBelonging");
		if(null!=archivesBelonging && !archivesBelonging.equals("")){
			sql.append(" and f.archivesBelonging="+archivesBelonging);
		}
		
		if(queryType!=null&&!"".equals(queryType)&&(queryType.equals("13")||queryType.equals("2")||queryType.equals("3"))){
			sql.append(" group by p.projectId  ");
		}
		
		String sort=request.getParameter("sort");
		String dir=request.getParameter("dir");
		if(null!=sort && !"".equals(sort)){
			sql.append(" order by f."+sort+" "+dir);
		}else{
			sql.append(" order by f.startDate asc");
		}
		
		System.out.println(sql);
		totalCounts.append(sql).append(") as b");
		
		if(queryType!=null&&!"".equals(queryType)&&(queryType.equals("13")||queryType.equals("2")||queryType.equals("3"))){
			List list = this.getSession().createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.LONG)
			.addScalar("projectId", Hibernate.LONG)
			.addScalar("productId", Hibernate.LONG)
			.addScalar("operationType", Hibernate.STRING)
			.addScalar("flowType", Hibernate.STRING)
			.addScalar("oppositeType", Hibernate.STRING)
			.addScalar("oppositeID", Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("projectNumber", Hibernate.STRING)
			.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("projectStatus", Hibernate.SHORT)
			.addScalar("appUserId", Hibernate.STRING)
			.addScalar("appUserName", Hibernate.STRING)
			.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
			.addScalar("businessType", Hibernate.STRING)
			.addScalar("isOtherFlow", Hibernate.SHORT)
			.addScalar("orgName", Hibernate.STRING)
			.addScalar("departId", Hibernate.LONG)
			.addScalar("departMentName", Hibernate.STRING)
			.addScalar("oppositeTypeValue", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
			pageBean.setResult(list);
			BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(totalCounts.toString()).uniqueResult();
			pageBean.setTotalCounts(total.intValue());
		}else{
			List list = this.getSession().createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.LONG)
			.addScalar("projectId", Hibernate.LONG)
			.addScalar("productId", Hibernate.LONG)
			.addScalar("operationType", Hibernate.STRING)
			.addScalar("flowType", Hibernate.STRING)
			.addScalar("oppositeType", Hibernate.STRING)
			.addScalar("oppositeID", Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("projectNumber", Hibernate.STRING)
			.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("projectStatus", Hibernate.SHORT)
			.addScalar("appUserId", Hibernate.STRING)
			.addScalar("appUserName", Hibernate.STRING)
			.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
			.addScalar("runId", Hibernate.LONG)
			.addScalar("taskId", Hibernate.LONG)
			.addScalar("activityName", Hibernate.STRING)
			.addScalar("orgName", Hibernate.STRING)
			.addScalar("departId", Hibernate.LONG)
			.addScalar("departMentName", Hibernate.STRING)
			.addScalar("oppositeTypeValue", Hibernate.STRING)
			.addScalar("executor", Hibernate.STRING)
			.addScalar("supEndDate", Hibernate.DATE)
			.addScalar("businessType", Hibernate.STRING)
			.addScalar("isOtherFlow", Hibernate.SHORT)
			.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
			pageBean.setResult(list);
			BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(totalCounts.toString()).uniqueResult();
			pageBean.setTotalCounts(total.intValue());
		}
		
	}
	@Override
	public Long projectCount(String userIdsStr, Short[] projectStatus,HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("select count(*) from (SELECT "
				+" f.id"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join process_run as run on run.projectId=f.projectId"
				+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
		if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}
		if(null!=userIdsStr && !userIdsStr.equals("")){
			sql.append(" and fn_check_repeat(f.appUserId,'"+userIdsStr+"') = 1");
		}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			sql.append(" and f.companyId in ("+companyIdStr+")");
		}
		String projectNumber = request.getParameter("projectNumber");
		if(null != projectNumber && !"".equals(projectNumber)) {
			sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
		}
		String projectName = request.getParameter("projectName");
		if(null != projectName && !"".equals(projectName)) {
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("oppositeType");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String orgUserId = request.getParameter("orgUserId");
		if(null != orgUserId && !"".equals(orgUserId)) {
			sql.append("and s.departId ="+Long.valueOf(orgUserId));
		}
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
		}
		String archivesBelonging=request.getParameter("archivesBelonging");
		if(null!=archivesBelonging && !archivesBelonging.equals("")){
			sql.append(" and f.archivesBelonging="+archivesBelonging);
		}
		sql.append(" order by f.startDate desc) as s");
		Long count=0l;
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> fundProjectList(String userIdsStr, int start,
			int limit, HttpServletRequest request) {
		StringBuffer hql=new StringBuffer("from BpFundProject as f where f.flag=0 and f.ownJointMoney>0 ");
		String companyId=request.getParameter("companyId");
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		if(null!=strs && !"".equals(strs)){
			hql.append(" and f.companyId in ("+strs+")");
		}
		if(null!=userIdsStr && !userIdsStr.equals("")){
			hql.append(" and fn_check_repeat(f.appUserId,'"+userIdsStr+"') = 1");
		}
		String projectNum=request.getParameter("projectNum");
		if(null!=projectNum && !projectNum.equals("")){
			hql.append(" and f.projectNumber like '%"+projectNum+"%'");
		}
		String projectName=request.getParameter("projectName");
		if(null!=projectName && !projectName.equals("")){
			hql.append(" and f.projectName like '%"+projectName+"%'");
		}
		hql.append(" order by f.createDate desc");
		return this.getSession().createQuery(hql.toString()).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public Long fundProjectCount(String userIdsStr, HttpServletRequest request) {
		StringBuffer hql=new StringBuffer("select count(*) from BpFundProject  f where f.flag=0 and f.ownJointMoney>0 ");
		String companyId=request.getParameter("companyId");
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		if(null!=strs && !"".equals(strs)){
			hql.append(" and f.companyId in ("+strs+")");
		}
		if(null!=userIdsStr && !userIdsStr.equals("")){
			hql.append(" and fn_check_repeat(f.appUserId,'"+userIdsStr+"') = 1");
		}
		String projectNum=request.getParameter("projectNum");
		if(null!=projectNum && !projectNum.equals("")){
			hql.append(" and f.projectNumber like '%"+projectNum+"%'");
		}
		String projectName=request.getParameter("projectName");
		if(null!=projectName && !projectName.equals("")){
			hql.append(" and f.projectName like '%"+projectName+"%'");
		}
		hql.append(" order by f.createDate desc");
		Long count=0l;
		List list=this.getSession().createQuery(hql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count= (Long) list.get(0);
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> getProject(Long projectId, String businessType) {
		StringBuffer sql=new StringBuffer("SELECT "
				+" f.id,"
				+" f.projectId,"
				+" f.productId,"
				+" f.operationType,"
				+" f.flowType,"
				+" f.oppositeType,"
				+" f.oppositeID,"
				+" f.projectName,"
				+" f.projectNumber,"
				+" f.projectMoney,"
				+" f.startDate,"
				+" f.intentDate,"
				+" f.projectStatus,"
				+" f.appUserId,"
				+" f.appUserName,"
				+" f.ownJointMoney,"
				+" run.runId,"
				+" pf.taskId,"
				+" pf.activityName,"
				+" o.org_name as orgName,"
				+" s.departId,"
				+" s.departMentName,"
				+" d.itemValue as oppositeTypeValue,"
				+" u.fullname as executor,"
				+" ss.endDate as supEndDate,"
				+" f.businessType,"
				+" f.isOtherFlow"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join dictionary_independent as d on d.dicKey=f.oppositeType"
				+" LEFT JOIN organization AS o ON f.companyId = o.org_id"
				+" left join (select s.projectId,s.endDate from sl_supervise_record as s left join bp_fund_project as ps ON s.projectId=ps.projectId) as ss on ss.projectId = f.projectId"
				+" left join process_run as run on run.projectId=f.projectId"
				+" left join (select f.formId,f.runId,f.taskId,f.`status`,f.activityName from (select ro.* from process_form as ro where ro.taskId IS NOT NULL AND `ro`.`status` IN(0, 5, 6) order by ro.formId DESC) as f  group by f.runId order by f.formId desc ) as pf on pf.runId=run.runId"
				+" left join jbpm4_task as task on task.DBID_=pf.taskId"
				+" left join app_user as u on u.userId=task.ASSIGNEE_"
				//+" where f.projectId="+projectId);
				//huanggh 修改  债权标查看借款详细信息出错，
				//页面传来的值为bp_fund_project表主键，不是projectId，
				//走标准流程，bp_fund_project表会有两条数据，故应该用bp_fund_project表主键查询
				+" where f.id="+projectId);
	
			return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("appUserId", Hibernate.STRING)
					.addScalar("appUserName", Hibernate.STRING)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("runId", Hibernate.LONG)
					.addScalar("taskId", Hibernate.LONG)
					.addScalar("activityName", Hibernate.STRING)
					.addScalar("orgName", Hibernate.STRING)
					.addScalar("departId", Hibernate.LONG)
					.addScalar("departMentName", Hibernate.STRING)
					.addScalar("oppositeTypeValue", Hibernate.STRING)
					.addScalar("executor", Hibernate.STRING)
					.addScalar("supEndDate", Hibernate.DATE)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("isOtherFlow", Hibernate.SHORT)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
		}
	
	@Override
	public List<BpFundProject> projectLoanList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("SELECT "
			+" f.id,"
			+" f.projectId,"
			+" f.productId,"
			+" f.operationType,"
			+" f.flowType,"
			+" f.oppositeType,"
			+" f.oppositeID,"
			+" f.projectName,"
			+" f.projectNumber,"
			+" f.projectMoney,"
			+" f.startDate,"
			+" f.intentDate,"
			+" f.projectStatus,"
			+" f.appUserId,"
			+" f.appUserName,"
			+" f.ownJointMoney,"
			+" run.runId,"
			+" pf.taskId,"
			+" pf.activityName,"
			+" o.org_name as orgName,"
			+" s.departId,"
			+" s.departMentName,"
			+" d.itemValue as oppositeTypeValue,"
			+" u.fullname as executor,"
			+" ss.endDate as supEndDate,"
			+" f.businessType,"
			+" f.isOtherFlow"
			+" FROM bp_fund_project AS f "
			+" left join sl_smallloan_project as s on s.projectId=f.projectId"
			+" left join dictionary_independent as d on d.dicKey=f.oppositeType"
			+" LEFT JOIN organization AS o ON f.companyId = o.org_id"
			+" left join (select s.projectId,s.endDate from sl_supervise_record as s left join bp_fund_project as ps ON s.projectId=ps.projectId) as ss on ss.projectId = f.projectId"
			+" left join process_run as run on run.projectId=f.projectId"
			+" left join (select f.formId,f.runId,f.taskId,f.`status`,f.activityName from (select ro.* from process_form as ro where ro.taskId IS NOT NULL AND `ro`.`status` IN(0, 5, 6) order by ro.formId DESC) as f  group by f.runId order by f.formId desc ) as pf on pf.runId=run.runId"
			+" left join jbpm4_task as task on task.DBID_=pf.taskId"
			+" left join app_user as u on u.userId=task.ASSIGNEE_"
			+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
			if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}else{
				sql.append(" and f.projectStatus>0");
			}
			if(null!=userIdsStr && !userIdsStr.equals("")){
				sql.append(" and fn_check_repeat(f.appUserId,'"+userIdsStr+"') = 1");
			}
			String companyIdStr = ContextUtil.getBranchIdsStr();
			String companyId = request.getParameter("companyId");
			if(!"".equals(companyId) && null != companyId) {
				companyIdStr=companyId;
			}
			if(null != companyIdStr && !"".equals(companyIdStr)){
				sql.append(" and f.companyId in ("+companyIdStr+")");
			}
			String projectNumber = request.getParameter("projectNumber");
			if(null != projectNumber && !"".equals(projectNumber)) {
				sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
			}
			String projectName = request.getParameter("projectName");
			if(null != projectName && !"".equals(projectName)) {
				sql.append(" and f.projectName like '%"+projectName+"%'");
			}
			String oppositeTypeValue = request.getParameter("oppositeType");
			if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
				sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
			}
			String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
			String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
			if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
			}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
				sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
			}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
			}
			String orgUserId = request.getParameter("orgUserId");
			if(null != orgUserId && !"".equals(orgUserId)) {
				sql.append("and s.departId ="+Long.valueOf(orgUserId));
			}
			String businessManager = request.getParameter("Q_businessManager_S_LK"); 
			if(null != businessManager && !"".equals(businessManager)) {
				sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
			}
			String archivesBelonging=request.getParameter("archivesBelonging");
			if(null!=archivesBelonging && !archivesBelonging.equals("")){
				sql.append(" and f.archivesBelonging="+archivesBelonging);
			}
			sql.append(" order by f.startDate desc");
			System.out.println(sql);
		return this.getSession().createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.LONG)
				.addScalar("projectId", Hibernate.LONG)
				.addScalar("productId", Hibernate.LONG)
				.addScalar("operationType", Hibernate.STRING)
				.addScalar("flowType", Hibernate.STRING)
				.addScalar("oppositeType", Hibernate.STRING)
				.addScalar("oppositeID", Hibernate.LONG)
				.addScalar("projectName", Hibernate.STRING)
				.addScalar("projectNumber", Hibernate.STRING)
				.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
				.addScalar("startDate", Hibernate.DATE)
				.addScalar("intentDate", Hibernate.DATE)
				.addScalar("projectStatus", Hibernate.SHORT)
				.addScalar("appUserId", Hibernate.STRING)
				.addScalar("appUserName", Hibernate.STRING)
				.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
				.addScalar("runId", Hibernate.LONG)
				.addScalar("taskId", Hibernate.LONG)
				.addScalar("activityName", Hibernate.STRING)
				.addScalar("orgName", Hibernate.STRING)
				.addScalar("departId", Hibernate.LONG)
				.addScalar("departMentName", Hibernate.STRING)
				.addScalar("oppositeTypeValue", Hibernate.STRING)
				.addScalar("executor", Hibernate.STRING)
				.addScalar("supEndDate", Hibernate.DATE)
				.addScalar("businessType", Hibernate.STRING)
				.addScalar("isOtherFlow", Hibernate.SHORT)
				.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
	}
	
	@Override
	public Long projectLoanCount(String userIdsStr, Short[] projectStatus,HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("select count(*) from (SELECT "
				+" f.id"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join process_run as run on run.projectId=f.projectId"
				+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
		if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}
		if(null!=userIdsStr && !userIdsStr.equals("")){
			sql.append(" and fn_check_repeat(f.appUserId,'"+userIdsStr+"') = 1");
		}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			sql.append(" and f.companyId in ("+companyIdStr+")");
		}
		String projectNumber = request.getParameter("projectNumber");
		if(null != projectNumber && !"".equals(projectNumber)) {
			sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
		}
		String projectName = request.getParameter("projectName");
		if(null != projectName && !"".equals(projectName)) {
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("oppositeType");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String orgUserId = request.getParameter("orgUserId");
		if(null != orgUserId && !"".equals(orgUserId)) {
			sql.append("and s.departId ="+Long.valueOf(orgUserId));
		}
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
		}
		String archivesBelonging=request.getParameter("archivesBelonging");
		if(null!=archivesBelonging && !archivesBelonging.equals("")){
			sql.append(" and f.archivesBelonging="+archivesBelonging);
		}
		sql.append(" order by f.startDate desc) as s");
		Long count=0l;
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}
	
	@Override
	public List<BpFundProject> bpProjectList(Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("SELECT aaa.* from(SELECT "
			+" f.id,"
			+" f.projectId,"
			+" f.productId,"
			+" f.operationType,"
			+" f.flowType,"
			+" f.oppositeType,"
			+" f.oppositeID,"
			+" f.projectName,"
			+" f.projectNumber,"
			+" f.projectMoney,"
			+" f.startDate,"
			+" f.intentDate,"
			+" f.projectStatus,"
			+" f.appUserId,"
			+" f.appUserName,"
			+" f.ownJointMoney,"
			+" run.runId,"
			+" pf.taskId,"
			+" pf.activityName,"
			+" o.org_name as orgName,"
			+" s.departId,"
			+" s.departMentName,"
			+" d.itemValue as oppositeTypeValue,"
			+" u.fullname as executor,"
			+" ss.endDate as supEndDate,"
			+" f.businessType,"
			+" f.isOtherFlow,"
			+" dn.itemValue as superviseManageOpinionValue"
			+" FROM bp_fund_project AS f "
			+" left join sl_smallloan_project as s on s.projectId=f.projectId"
			+" left join dictionary_independent as d on d.dicKey=f.oppositeType"
			+" LEFT JOIN organization AS o ON f.companyId = o.org_id"
			+" left join (select s.projectId,s.endDate from sl_supervise_record as s left join bp_fund_project as ps ON s.projectId=ps.projectId) as ss on ss.projectId = f.projectId"
			+" left join process_run as run on run.projectId=f.projectId"
			+" left join (select f.formId,f.runId,f.taskId,f.`status`,f.activityName from (select ro.* from process_form as ro where ro.taskId IS NOT NULL AND `ro`.`status` IN(0, 5, 6) order by ro.formId DESC) as f  group by f.runId order by f.formId desc ) as pf on pf.runId=run.runId"
			+" left join jbpm4_task as task on task.DBID_=pf.taskId"
			+" left join app_user as u on u.userId=task.ASSIGNEE_"
			+" LEFT JOIN global_supervisemanage AS gs ON gs.projectId=f.id"
			+" LEFT JOIN dictionary AS dn ON dn.dicId=gs.superviseManageOpinion"
			+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
			if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}else{
				sql.append(" and f.projectStatus>0");
			}
			String companyIdStr = ContextUtil.getBranchIdsStr();
			String companyId = request.getParameter("companyId");
			if(!"".equals(companyId) && null != companyId) {
				companyIdStr=companyId;
			}
			if(null != companyIdStr && !"".equals(companyIdStr)){
				sql.append(" and f.companyId in ("+companyIdStr+")");
			}
			String projectNumber = request.getParameter("projectNumber");
			if(null != projectNumber && !"".equals(projectNumber)) {
				sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
			}
			String projectName = request.getParameter("projectName");
			if(null != projectName && !"".equals(projectName)) {
				sql.append(" and f.projectName like '%"+projectName+"%'");
			}
			String oppositeTypeValue = request.getParameter("oppositeType");
			if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
				sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
			}
			String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
			String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
			if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
			}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
				sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
			}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
			}
			String orgUserId = request.getParameter("orgUserId");
			if(null != orgUserId && !"".equals(orgUserId)) {
				sql.append("and s.departId ="+Long.valueOf(orgUserId));
			}
			String businessManager = request.getParameter("Q_businessManager_S_LK"); 
			if(null != businessManager && !"".equals(businessManager)) {
				sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
			}
			String archivesBelonging=request.getParameter("archivesBelonging");
			if(null!=archivesBelonging && !archivesBelonging.equals("")){
				sql.append(" and f.archivesBelonging="+archivesBelonging);
			}
			String superviseManageOpinion=request.getParameter("superviseManageOpinion");
			if(null !=superviseManageOpinion && !"".equals(superviseManageOpinion) && !"null".equals(superviseManageOpinion)){
				if(superviseManageOpinion.equals("422")){//没有执行过监管计划的项目都默认为正常贷款
					sql.append(" and (gs.superviseManageOpinion=422 or gs.superviseManageOpinion is NULL)");
				}else{
					sql.append(" and gs.superviseManageOpinion="+superviseManageOpinion);
				}
			}
			String startDate1=request.getParameter("startDate1");
			if(null !=startDate1 && !"".equals(startDate1)){
				sql.append(" and f.startDate>='"+startDate1+"'");
			}
			String startDate2=request.getParameter("startDate2");
			if(null !=startDate2 && !"".equals(startDate2)){
				sql.append(" and f.startDate<='"+startDate2+"'");
			}
			sql.append(" order by gs.superviseManageId DESC) as aaa GROUP BY aaa.projectId ORDER BY aaa.startDate DESC");
			System.out.println(sql);
		return this.getSession().createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.LONG)
				.addScalar("projectId", Hibernate.LONG)
				.addScalar("productId", Hibernate.LONG)
				.addScalar("operationType", Hibernate.STRING)
				.addScalar("flowType", Hibernate.STRING)
				.addScalar("oppositeType", Hibernate.STRING)
				.addScalar("oppositeID", Hibernate.LONG)
				.addScalar("projectName", Hibernate.STRING)
				.addScalar("projectNumber", Hibernate.STRING)
				.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
				.addScalar("startDate", Hibernate.DATE)
				.addScalar("intentDate", Hibernate.DATE)
				.addScalar("projectStatus", Hibernate.SHORT)
				.addScalar("appUserId", Hibernate.STRING)
				.addScalar("appUserName", Hibernate.STRING)
				.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
				.addScalar("runId", Hibernate.LONG)
				.addScalar("taskId", Hibernate.LONG)
				.addScalar("activityName", Hibernate.STRING)
				.addScalar("orgName", Hibernate.STRING)
				.addScalar("departId", Hibernate.LONG)
				.addScalar("departMentName", Hibernate.STRING)
				.addScalar("oppositeTypeValue", Hibernate.STRING)
				.addScalar("executor", Hibernate.STRING)
				.addScalar("supEndDate", Hibernate.DATE)
				.addScalar("businessType", Hibernate.STRING)
				.addScalar("isOtherFlow", Hibernate.SHORT)
				.addScalar("superviseManageOpinionValue", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
	}
	
	@Override
	public Long bpProjectCount(Short[] projectStatus,HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("SELECT COUNT(*) FROM(select count(*) from (SELECT "
				+" f.id,"
				+" f.projectId"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join process_run as run on run.projectId=f.projectId"
				+" LEFT JOIN global_supervisemanage AS gs ON gs.projectId = f.id"
				+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
		if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			sql.append(" and f.companyId in ("+companyIdStr+")");
		}
		String projectNumber = request.getParameter("projectNumber");
		if(null != projectNumber && !"".equals(projectNumber)) {
			sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
		}
		String projectName = request.getParameter("projectName");
		if(null != projectName && !"".equals(projectName)) {
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("oppositeType");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String orgUserId = request.getParameter("orgUserId");
		if(null != orgUserId && !"".equals(orgUserId)) {
			sql.append("and s.departId ="+Long.valueOf(orgUserId));
		}
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
		}
		String archivesBelonging=request.getParameter("archivesBelonging");
		if(null!=archivesBelonging && !archivesBelonging.equals("")){
			sql.append(" and f.archivesBelonging="+archivesBelonging);
		}
		
		String superviseManageOpinion=request.getParameter("superviseManageOpinion");
		if(null !=superviseManageOpinion && !"".equals(superviseManageOpinion)){
			if(superviseManageOpinion.equals("422")){//没有执行过监管计划的项目都默认为正常贷款
				sql.append(" and (gs.superviseManageOpinion=422 or gs.superviseManageOpinion is NULL)");
			}else{
				sql.append(" and gs.superviseManageOpinion="+superviseManageOpinion);
			}
		}
		String startDate1=request.getParameter("startDate1");
		if(null !=startDate1 && !"".equals(startDate1)){
			sql.append(" and f.startDate>='"+startDate1+"'");
		}
		String startDate2=request.getParameter("startDate2");
		if(null !=startDate2 && !"".equals(startDate2)){
			sql.append(" and f.startDate<='"+startDate2+"'");
		}
		
		sql.append(" order by gs.superviseManageId DESC) as s GROUP BY s.projectId )AS AAA");
	//	System.out.println(sql);
		Long count=0l;
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}
	
	@Override
	public List<BpFundProject> overdueProjectList(Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("SELECT "
			+" f.id,"
			+" f.projectId,"
			+" f.productId,"
			+" f.operationType,"
			+" f.flowType,"
			+" f.oppositeType,"
			+" f.oppositeID,"
			+" f.projectName,"
			+" f.projectNumber,"
			+" f.projectMoney,"
			+" f.startDate,"
			+" f.intentDate,"
			+" f.projectStatus,"
			+" f.appUserId,"
			+" f.appUserName,"
			+" f.ownJointMoney,"
			+" run.runId,"
			+" pf.taskId,"
			+" pf.activityName,"
			+" o.org_name as orgName,"
			+" s.departId,"
			+" s.departMentName,"
			+" d.itemValue as oppositeTypeValue,"
			+" u.fullname as executor,"
			+" ss.endDate as supEndDate,"
			+" f.businessType,"
			+" f.isOtherFlow"
			+" FROM bp_fund_project AS f "
			+" left join sl_smallloan_project as s on s.projectId=f.projectId"
			+" left join dictionary_independent as d on d.dicKey=f.oppositeType"
			+" LEFT JOIN organization AS o ON f.companyId = o.org_id"
			+" left join (select s.projectId,s.endDate from sl_supervise_record as s left join bp_fund_project as ps ON s.projectId=ps.projectId) as ss on ss.projectId = f.projectId"
			+" left join process_run as run on run.projectId=f.projectId"
			+" left join (select f.formId,f.runId,f.taskId,f.`status`,f.activityName from (select ro.* from process_form as ro where ro.taskId IS NOT NULL AND `ro`.`status` IN(0, 5, 6) order by ro.formId DESC) as f  group by f.runId order by f.formId desc ) as pf on pf.runId=run.runId"
			+" left join jbpm4_task as task on task.DBID_=pf.taskId"
			+" left join app_user as u on u.userId=task.ASSIGNEE_"
			+" LEFT JOIN sl_fund_intent AS sfi ON sfi.projectId=f.projectId"
			+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType"
			+" and sfi.isValid=0 and sfi.isCheck=0 and sfi.notMoney>0 and sfi.intentDate<NOW() and sfi.fundType !='principalLending'");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
			if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}else{
				sql.append(" and f.projectStatus>0");
			}
			String companyIdStr = ContextUtil.getBranchIdsStr();
			String companyId = request.getParameter("companyId");
			if(!"".equals(companyId) && null != companyId) {
				companyIdStr=companyId;
			}
			if(null != companyIdStr && !"".equals(companyIdStr)){
				sql.append(" and f.companyId in ("+companyIdStr+")");
			}
			String projectNumber = request.getParameter("projectNumber");
			if(null != projectNumber && !"".equals(projectNumber)) {
				sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
			}
			String projectName = request.getParameter("projectName");
			if(null != projectName && !"".equals(projectName)) {
				sql.append(" and f.projectName like '%"+projectName+"%'");
			}
			String oppositeTypeValue = request.getParameter("oppositeType");
			if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
				sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
			}
			String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
			String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
			if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
			}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
				sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
			}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
			}
			String orgUserId = request.getParameter("orgUserId");
			if(null != orgUserId && !"".equals(orgUserId)) {
				sql.append("and s.departId ="+Long.valueOf(orgUserId));
			}
			String businessManager = request.getParameter("Q_businessManager_S_LK"); 
			if(null != businessManager && !"".equals(businessManager)) {
				sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
			}
			String archivesBelonging=request.getParameter("archivesBelonging");
			if(null!=archivesBelonging && !archivesBelonging.equals("")){
				sql.append(" and f.archivesBelonging="+archivesBelonging);
			}
			sql.append("  GROUP BY f.projectId order by f.startDate desc");
		return this.getSession().createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.LONG)
				.addScalar("projectId", Hibernate.LONG)
				.addScalar("productId", Hibernate.LONG)
				.addScalar("operationType", Hibernate.STRING)
				.addScalar("flowType", Hibernate.STRING)
				.addScalar("oppositeType", Hibernate.STRING)
				.addScalar("oppositeID", Hibernate.LONG)
				.addScalar("projectName", Hibernate.STRING)
				.addScalar("projectNumber", Hibernate.STRING)
				.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
				.addScalar("startDate", Hibernate.DATE)
				.addScalar("intentDate", Hibernate.DATE)
				.addScalar("projectStatus", Hibernate.SHORT)
				.addScalar("appUserId", Hibernate.STRING)
				.addScalar("appUserName", Hibernate.STRING)
				.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
				.addScalar("runId", Hibernate.LONG)
				.addScalar("taskId", Hibernate.LONG)
				.addScalar("activityName", Hibernate.STRING)
				.addScalar("orgName", Hibernate.STRING)
				.addScalar("departId", Hibernate.LONG)
				.addScalar("departMentName", Hibernate.STRING)
				.addScalar("oppositeTypeValue", Hibernate.STRING)
				.addScalar("executor", Hibernate.STRING)
				.addScalar("supEndDate", Hibernate.DATE)
				.addScalar("businessType", Hibernate.STRING)
				.addScalar("isOtherFlow", Hibernate.SHORT)
				.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
	}
	
	@Override
	public Long overdueProjectCount(Short[] projectStatus,HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("select count(*) from (SELECT "
				+" f.id"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join process_run as run on run.projectId=f.projectId"
				+" LEFT JOIN sl_fund_intent AS sfi ON sfi.projectId = f.projectId"
				+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType"
				+" AND sfi.isValid = 0 AND sfi.isCheck = 0 AND sfi.notMoney > 0 AND sfi.intentDate < NOW() AND sfi.fundType != 'principalLending'"
				
		);
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
		if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			sql.append(" and f.companyId in ("+companyIdStr+")");
		}
		String projectNumber = request.getParameter("projectNumber");
		if(null != projectNumber && !"".equals(projectNumber)) {
			sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
		}
		String projectName = request.getParameter("projectName");
		if(null != projectName && !"".equals(projectName)) {
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("oppositeType");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String orgUserId = request.getParameter("orgUserId");
		if(null != orgUserId && !"".equals(orgUserId)) {
			sql.append("and s.departId ="+Long.valueOf(orgUserId));
		}
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
		}
		String archivesBelonging=request.getParameter("archivesBelonging");
		if(null!=archivesBelonging && !archivesBelonging.equals("")){
			sql.append(" and f.archivesBelonging="+archivesBelonging);
		}
		sql.append(" GROUP BY f.projectId order by f.startDate desc) as s");
		Long count=0l;
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}
	
	@Override
	public List<BpFundProject> IndustryProjectList(Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("SELECT aaa.* from(SELECT "
			+" f.id,"
			+" f.projectId,"
			+" f.productId,"
			+" f.operationType,"
			+" f.flowType,"
			+" f.oppositeType,"
			+" f.oppositeID,"
			+" f.projectName,"
			+" f.projectNumber,"
			+" f.projectMoney,"
			+" f.startDate,"
			+" f.intentDate,"
			+" f.projectStatus,"
			+" f.appUserId,"
			+" f.appUserName,"
			+" f.ownJointMoney,"
			+" run.runId,"
			+" pf.taskId,"
			+" pf.activityName,"
			+" o.org_name as orgName,"
			+" s.departId,"
			+" s.departMentName,"
			+" d.itemValue as oppositeTypeValue,"
			+" u.fullname as executor,"
			+" ss.endDate as supEndDate,"
			+" f.businessType,"
			+" f.isOtherFlow,"
			+" dn.itemValue as superviseManageOpinionValue,"
			+" csdad.title as hangyeTypeValue"
			+" FROM bp_fund_project AS f "
			+" left join sl_smallloan_project as s on s.projectId=f.projectId"
			+" left join dictionary_independent as d on d.dicKey=f.oppositeType"
			+" LEFT JOIN organization AS o ON f.companyId = o.org_id"
			+" left join (select s.projectId,s.endDate from sl_supervise_record as s left join bp_fund_project as ps ON s.projectId=ps.projectId) as ss on ss.projectId = f.projectId"
			+" left join process_run as run on run.projectId=f.projectId"
			+" left join (select f.formId,f.runId,f.taskId,f.`status`,f.activityName from (select ro.* from process_form as ro where ro.taskId IS NOT NULL AND `ro`.`status` IN(0, 5, 6) order by ro.formId DESC) as f  group by f.runId order by f.formId desc ) as pf on pf.runId=run.runId"
			+" left join jbpm4_task as task on task.DBID_=pf.taskId"
			+" left join app_user as u on u.userId=task.ASSIGNEE_"
			+" LEFT JOIN global_supervisemanage AS gs ON gs.projectId=f.id"
			+" LEFT JOIN dictionary AS dn ON dn.dicId=gs.superviseManageOpinion"
			+" LEFT JOIN cs_enterprise AS cse ON cse.id=f.oppositeID"
			+" LEFT JOIN cs_person AS csp ON csp.id=f.oppositeID"
			+" LEFT JOIN cs_dic_area_dynam AS csdad ON (csdad.id=cse.hangyetype  or csdad.id=csp.hangyetype)"
			+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
			if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}else{
				sql.append(" and f.projectStatus>0");
			}
			String companyIdStr = ContextUtil.getBranchIdsStr();
			String companyId = request.getParameter("companyId");
			if(!"".equals(companyId) && null != companyId) {
				companyIdStr=companyId;
			}
			if(null != companyIdStr && !"".equals(companyIdStr)){
				sql.append(" and f.companyId in ("+companyIdStr+")");
			}
			String projectNumber = request.getParameter("projectNumber");
			if(null != projectNumber && !"".equals(projectNumber)) {
				sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
			}
			String projectName = request.getParameter("projectName");
			if(null != projectName && !"".equals(projectName)) {
				sql.append(" and f.projectName like '%"+projectName+"%'");
			}
			String oppositeTypeValue = request.getParameter("oppositeType");
			if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
				sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
			}
			String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
			String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
			if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
			}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
				sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
			}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
				sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
			}
			String orgUserId = request.getParameter("orgUserId");
			if(null != orgUserId && !"".equals(orgUserId)) {
				sql.append("and s.departId ="+Long.valueOf(orgUserId));
			}
			String businessManager = request.getParameter("Q_businessManager_S_LK"); 
			if(null != businessManager && !"".equals(businessManager)) {
				sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
			}
			String archivesBelonging=request.getParameter("archivesBelonging");
			if(null!=archivesBelonging && !archivesBelonging.equals("")){
				sql.append(" and f.archivesBelonging="+archivesBelonging);
			}
			String superviseManageOpinion=request.getParameter("superviseManageOpinion");
			if(null !=superviseManageOpinion && !"".equals(superviseManageOpinion) && !"null".equals(superviseManageOpinion)){
				if(superviseManageOpinion.equals("422")){//没有执行过监管计划的项目都默认为正常贷款
					sql.append(" and (gs.superviseManageOpinion=422 or gs.superviseManageOpinion is NULL)");
				}else{
					sql.append(" and gs.superviseManageOpinion="+superviseManageOpinion);
				}
			}
			String startDate1=request.getParameter("startDate1");
			if(null !=startDate1 && !"".equals(startDate1)){
				sql.append(" and f.startDate>='"+startDate1+"'");
			}
			String startDate2=request.getParameter("startDate2");
			if(null !=startDate2 && !"".equals(startDate2)){
				sql.append(" and f.startDate<='"+startDate2+"'");
			}
			String hangyeName=request.getParameter("hangyeName");
			if(null !=hangyeName && !"".equals(hangyeName)){
				sql.append(" AND csdad.title='"+hangyeName+"'");
			}
			sql.append(" order by gs.superviseManageId DESC) as aaa GROUP BY aaa.projectId ORDER BY aaa.startDate DESC");
			System.out.println(sql);
		return this.getSession().createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.LONG)
				.addScalar("projectId", Hibernate.LONG)
				.addScalar("productId", Hibernate.LONG)
				.addScalar("operationType", Hibernate.STRING)
				.addScalar("flowType", Hibernate.STRING)
				.addScalar("oppositeType", Hibernate.STRING)
				.addScalar("oppositeID", Hibernate.LONG)
				.addScalar("projectName", Hibernate.STRING)
				.addScalar("projectNumber", Hibernate.STRING)
				.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
				.addScalar("startDate", Hibernate.DATE)
				.addScalar("intentDate", Hibernate.DATE)
				.addScalar("projectStatus", Hibernate.SHORT)
				.addScalar("appUserId", Hibernate.STRING)
				.addScalar("appUserName", Hibernate.STRING)
				.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
				.addScalar("runId", Hibernate.LONG)
				.addScalar("taskId", Hibernate.LONG)
				.addScalar("activityName", Hibernate.STRING)
				.addScalar("orgName", Hibernate.STRING)
				.addScalar("departId", Hibernate.LONG)
				.addScalar("departMentName", Hibernate.STRING)
				.addScalar("oppositeTypeValue", Hibernate.STRING)
				.addScalar("executor", Hibernate.STRING)
				.addScalar("supEndDate", Hibernate.DATE)
				.addScalar("businessType", Hibernate.STRING)
				.addScalar("isOtherFlow", Hibernate.SHORT)
				.addScalar("superviseManageOpinionValue", Hibernate.STRING)
				.addScalar("hangyeTypeValue", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
	}
	
	@Override
	public Long IndustryProjectCount(Short[] projectStatus,HttpServletRequest request) {
		StringBuffer sql=new StringBuffer("SELECT COUNT(*) FROM(select count(*) from (SELECT "
				+" f.id,"
				+" f.projectId"
				+" FROM bp_fund_project AS f "
				+" left join sl_smallloan_project as s on s.projectId=f.projectId"
				+" left join process_run as run on run.projectId=f.projectId"
				+" LEFT JOIN global_supervisemanage AS gs ON gs.projectId = f.id"
				+" LEFT JOIN cs_enterprise AS cse ON cse.id=f.oppositeID"
				+" LEFT JOIN cs_person AS csp ON csp.id=f.oppositeID"
				+" LEFT JOIN cs_dic_area_dynam AS csdad ON (csdad.id=cse.hangyetype  or csdad.id=csp.hangyetype)"
				+" where f.flag=0 and f.ownJointMoney>0  and run.processName=f.flowType");
		StringBuffer sb = new StringBuffer();
		if(projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}	
		if(!status.equals("")){
				sql.append(" and f.projectStatus in ("+status+")");
			}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			sql.append(" and f.companyId in ("+companyIdStr+")");
		}
		String projectNumber = request.getParameter("projectNumber");
		if(null != projectNumber && !"".equals(projectNumber)) {
			sql.append(" and f.projectNumber like '%"+projectNumber+"%' ");
		}
		String projectName = request.getParameter("projectName");
		if(null != projectName && !"".equals(projectName)) {
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("oppositeType");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			sql.append(" and f.oppositeType = '"+oppositeTypeValue+"'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			sql.append(" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			sql.append(" and f.ownJointMoney >= "+Float.valueOf(smallProjectMoney)+" and f.ownJointMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String orgUserId = request.getParameter("orgUserId");
		if(null != orgUserId && !"".equals(orgUserId)) {
			sql.append("and s.departId ="+Long.valueOf(orgUserId));
		}
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			sql.append(" and fn_check_repeat(f.appUserId,'"+businessManager+"') = 1");
		}
		String archivesBelonging=request.getParameter("archivesBelonging");
		if(null!=archivesBelonging && !archivesBelonging.equals("")){
			sql.append(" and f.archivesBelonging="+archivesBelonging);
		}
		
		String superviseManageOpinion=request.getParameter("superviseManageOpinion");
		if(null !=superviseManageOpinion && !"".equals(superviseManageOpinion)){
			if(superviseManageOpinion.equals("422")){//没有执行过监管计划的项目都默认为正常贷款
				sql.append(" and (gs.superviseManageOpinion=422 or gs.superviseManageOpinion is NULL)");
			}else{
				sql.append(" and gs.superviseManageOpinion="+superviseManageOpinion);
			}
		}
		String startDate1=request.getParameter("startDate1");
		if(null !=startDate1 && !"".equals(startDate1)){
			sql.append(" and f.startDate>='"+startDate1+"'");
		}
		String startDate2=request.getParameter("startDate2");
		if(null !=startDate2 && !"".equals(startDate2)){
			sql.append(" and f.startDate<='"+startDate2+"'");
		}
		String hangyeName=request.getParameter("hangyeName");
		if(null !=hangyeName && !"".equals(hangyeName)){
			sql.append(" AND csdad.title='"+hangyeName+"'");
		}
		sql.append(" order by gs.superviseManageId DESC) as s GROUP BY s.projectId )AS AAA");
		Long count=0l;
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public Long personBrowerProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		StringBuffer sql=new StringBuffer(" select count(*) from (SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" where b.flag=0 ");
		    	StringBuffer sb = new StringBuffer();
			
				String oppositeTypeValue = request.getParameter("oppositeType");
				if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
					sql.append(" and b.oppositeType = '"+oppositeTypeValue+"'");
				}
		
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and b.oppositeID ="+Long.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ) as aa");
     		//	System.out.println("---->"+sql);
		
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> personBrowerProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.ownJointMoney,"
				+" 	b.ownJointMoney  as jointMoney ,"
				+" b.projectMoney,"
				+" b.startDate,"
				+" b.intentDate,"
				+" b.projectStatus,"
				+" b.projectName,"
				+" b.projectNumber,"
				+" IFNULL(ps.noMoney,0) as overduePrincipalRepayment,"
				+" (IFNULL(pd.sumDay,0)+IFNULL(pd1.sumDay1,0)) as overduePrincipalRepaymentDays,"
				+" IFNULL(ls.noMoney,0) as overdueLoanInterest,"
				+" (IFNULL(ld.sumDay,0)+IFNULL(ld1.sumDay1,0)) as overdueLoanInterestDays "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" where b.flag=0 ");
		    	StringBuffer sb = new StringBuffer();
			
				String oppositeTypeValue = request.getParameter("oppositeType");
				if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
					sql.append(" and b.oppositeType = '"+oppositeTypeValue+"'");
				}
		
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and b.oppositeID ="+Long.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ");
			//	System.out.println("---->"+sql);
				if(null!=pb){
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
				}else{
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
					
					
				}
	}

	@Override
	public Long personBrowerOnlineProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer(" select count(*) from (SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" where b.flag=1 ");
		    	StringBuffer sb = new StringBuffer();
			
				String oppositeTypeValue = request.getParameter("oppositeType");
				if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
					sql.append(" and b.oppositeType = '"+oppositeTypeValue+"'");
				}
		
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and b.oppositeID ="+Long.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ) as aa");
     		//	System.out.println("---->"+sql);
		
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> personBrowerOnlineProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.platFormJointMoney,"
				+" 	b.platFormJointMoney as jointMoney,"
				+" b.projectMoney,"
				+" b.startDate,"
				+" b.intentDate,"
				+" b.projectStatus,"
				+" b.projectName,"
				+" b.projectNumber,"
				+" IFNULL(ps.noMoney,0) as overduePrincipalRepayment,"
				+" (IFNULL(pd.sumDay,0)+IFNULL(pd1.sumDay1,0)) as overduePrincipalRepaymentDays,"
				+" IFNULL(ls.noMoney,0) as overdueLoanInterest,"
				+" (IFNULL(ld.sumDay,0)+IFNULL(ld1.sumDay1,0)) as overdueLoanInterestDays "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" where b.flag=1 ");
		    	StringBuffer sb = new StringBuffer();
			
				String oppositeTypeValue = request.getParameter("oppositeType");
				if( null != oppositeTypeValue && !"".equals(oppositeTypeValue)) {
					sql.append(" and b.oppositeType = '"+oppositeTypeValue+"'");
				}
		
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and b.oppositeID ="+Long.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ");
			//	System.out.println("---->"+sql);
				if(null!=pb){
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("platFormJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
				}else{
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("platFormJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
				}
	}

	@Override
	public Long personAssureOnlineProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer(" select count(*) from ( SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.platFormJointMoney,"
				+" 	b.platFormJointMoney as jointMoney "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_procredit_mortgage m on m.projid=b.projectId and m.businessType=b.businessType and m.assuretypeid=606"
				+" LEFT JOIN"
				+" (select p.`name` as `name`,p.id,p.cardnumber as cardnumber ,603 as custype from cs_person p "
				+"  UNION"
				+" SELECT e.enterprisename as NAME,e.id,e.organizecode as cardnumber ,602 as custype from cs_enterprise e "
				+" ) cus on cus.custype=m.personType and cus.id=m.assureofname"
				+" where b.flag=1 and m.id is not null ");
		    	StringBuffer sb = new StringBuffer();
			
		    	String oppositeTypeValue = request.getParameter("oppositeType");
				if( null != oppositeTypeValue && "person_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=603");
				}
				if( null != oppositeTypeValue && "company_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=602");
				}
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append(" and m.assureofname ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" group by b.id order by b.createDate desc ) as aa");
			//	System.out.println("---->"+sql);
		
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> personAssureOnlineProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.platFormJointMoney,"
				+" 	b.platFormJointMoney as jointMoney,"
				+" b.projectMoney,"
				+" b.startDate,"
				+" b.intentDate,"
				+" b.projectStatus,"
				+" b.projectName,"
				+" b.projectNumber,"
				+" m.mortgagepersontypeforvalue,"
				+" cus.`name`,"
				+" cus.cardnumber,"
				+" IFNULL(ps.noMoney,0) as overduePrincipalRepayment,"
				+" (IFNULL(pd.sumDay,0)+IFNULL(pd1.sumDay1,0)) as overduePrincipalRepaymentDays,"
				+" IFNULL(ls.noMoney,0) as overdueLoanInterest,"
				+" (IFNULL(ld.sumDay,0)+IFNULL(ld1.sumDay1,0)) as overdueLoanInterestDays "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_procredit_mortgage m on m.projid=b.projectId and m.businessType=b.businessType and m.assuretypeid=606"
				+" LEFT JOIN"
				+" (select p.`name` as `name`,p.id,p.cardnumber as cardnumber ,603 as custype from cs_person p "
				+"  UNION"
				+" SELECT e.enterprisename as NAME,e.id,e.organizecode as cardnumber ,602 as custype from cs_enterprise e "
				+" ) cus on cus.custype=m.personType and cus.id=m.assureofname"
				+" where b.flag=1 and m.id is not null ");
		    	StringBuffer sb = new StringBuffer();
			
		    	String oppositeTypeValue = request.getParameter("oppositeType");
				if( null != oppositeTypeValue && "person_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=603");
				}
				if( null != oppositeTypeValue && "company_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=602");
				}
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append(" and m.assureofname ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" group by b.id order by b.createDate desc ");
			//	System.out.println("---->"+sql);
				if(null!=pb){
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("mortgagepersontypeforvalue", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("platFormJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
				}else{
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("mortgagepersontypeforvalue", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("platFormJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
				}
	}

	@Override
	public Long personAssureProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer(" select count(*) from (SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_procredit_mortgage m on m.projid=b.projectId and m.businessType=b.businessType and m.assuretypeid=606"
				+" LEFT JOIN"
				+" (select p.`name` as `name`,p.id,p.cardnumber as cardnumber ,603 as custype from cs_person p "
				+"  UNION"
				+" SELECT e.enterprisename as NAME,e.id,e.organizecode as cardnumber ,602 as custype from cs_enterprise e "
				+" ) cus on cus.custype=m.personType and cus.id=m.assureofname"
				+" where b.flag=0 and m.id is not null ");
		    	StringBuffer sb = new StringBuffer();
			
				String oppositeTypeValue = request.getParameter("oppositeType");
				if( null != oppositeTypeValue && "person_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=603");
				}
				if( null != oppositeTypeValue && "company_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=602");
				}
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append(" and m.assureofname ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" group by b.id  order by b.createDate desc ) as aa");
     		//	System.out.println("---->"+sql);
		
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> personAssureProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.ownJointMoney,"
				+" 	b.ownJointMoney  as jointMoney ,"
				+" b.projectMoney,"
				+" b.startDate,"
				+" b.intentDate,"
				+" b.projectStatus,"
				+" b.projectName,"
				+" b.projectNumber,"
				+" m.mortgagepersontypeforvalue,"
				+" cus.`name`,"
				+" cus.cardnumber,"
				+" IFNULL(ps.noMoney,0) as overduePrincipalRepayment,"
				+" (IFNULL(pd.sumDay,0)+IFNULL(pd1.sumDay1,0)) as overduePrincipalRepaymentDays,"
				+" IFNULL(ls.noMoney,0) as overdueLoanInterest,"
				+" (IFNULL(ld.sumDay,0)+IFNULL(ld1.sumDay1,0)) as overdueLoanInterestDays "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_procredit_mortgage m on m.projid=b.projectId and m.businessType=b.businessType and m.assuretypeid=606"
				+" LEFT JOIN"
				+" (select p.`name` as `name`,p.id,p.cardnumber as cardnumber ,603 as custype from cs_person p "
				+"  UNION"
				+" SELECT e.enterprisename as NAME,e.id,e.organizecode as cardnumber ,602 as custype from cs_enterprise e "
				+" ) cus on cus.custype=m.personType and cus.id=m.assureofname"
				+" where b.flag=0 and m.id is not null ");
		    	StringBuffer sb = new StringBuffer();
			
				String oppositeTypeValue = request.getParameter("oppositeType");
				if( null != oppositeTypeValue && "person_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=603");
				}
				if( null != oppositeTypeValue && "company_customer".equals(oppositeTypeValue)) {
					sql.append(" and m.personType=602");
				}
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append(" and m.assureofname ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" group by b.id order by b.createDate desc ");
			//	System.out.println("---->"+sql);
				if(null!=pb){
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("mortgagepersontypeforvalue", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
				}else{
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("mortgagepersontypeforvalue", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
				}
	}

	@Override
	public Long personLegalOnlineProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer(" select count(*) from (SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_enterprise c on c.id=b.oppositeID "
				+" LEFT JOIN"
				+" cs_person p on c.legalpersonid=p.id"
				+" where b.flag=1 AND b.oppositeType = 'company_customer' ");
		    	StringBuffer sb = new StringBuffer();
			
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and c.legalpersonid ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ) as aa");
     		//	System.out.println("---->"+sql);
		
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> personLegalOnlineProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.platFormJointMoney,"
				+" 	b.platFormJointMoney as jointMoney,"
				+" b.projectMoney,"
				+" b.startDate,"
				+" b.intentDate,"
				+" b.projectStatus,"
				+" b.projectName,"
				+" b.projectNumber,"
				+" p.`name`,"
				+" p.cardnumber,"
				+" IFNULL(ps.noMoney,0) as overduePrincipalRepayment,"
				+" (IFNULL(pd.sumDay,0)+IFNULL(pd1.sumDay1,0)) as overduePrincipalRepaymentDays,"
				+" IFNULL(ls.noMoney,0) as overdueLoanInterest,"
				+" (IFNULL(ld.sumDay,0)+IFNULL(ld1.sumDay1,0)) as overdueLoanInterestDays "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from bp_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from bp_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_enterprise c on c.id=b.oppositeID "
				+" LEFT JOIN"
				+" cs_person p on c.legalpersonid=p.id"
				+" where b.flag=1 AND b.oppositeType = 'company_customer' ");
		    	StringBuffer sb = new StringBuffer();
		
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and c.legalpersonid ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ");
			//	System.out.println("---->"+sql);
				if(null!=pb){
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("platFormJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
				}else{
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("platFormJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
				}
	}

	@Override
	public Long personLegalProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer(" select count(*) from (SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_enterprise c on c.id=b.oppositeID "
				+" LEFT JOIN"
				+" cs_person p on c.legalpersonid=p.id"
				+" where b.flag=0 AND b.oppositeType = 'company_customer' ");
		    	StringBuffer sb = new StringBuffer();
			
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and c.legalpersonid ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ) as aa");
     		//	System.out.println("---->"+sql);
		
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(sql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

	@Override
	public List<BpFundProject> personLegalProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("SELECT "
				+" b.oppositeID,"
				+" b.oppositeType,"
				+" b.productId,"
				+" b.id,"
				+" b.projectId,"
				+" b.operationType,"
				+" b.flowType,"
				+"  b.flag,"
				+" b.businessType,"
				+" b.ownJointMoney,"
				+" 	b.ownJointMoney  as jointMoney ,"
				+" b.projectMoney,"
				+" b.startDate,"
				+" b.intentDate,"
				+" b.projectStatus,"
				+" b.projectName,"
				+" b.projectNumber,"
				+" p.`name`,"
				+" p.cardnumber,"
				+" IFNULL(ps.noMoney,0) as overduePrincipalRepayment,"
				+" (IFNULL(pd.sumDay,0)+IFNULL(pd1.sumDay1,0)) as overduePrincipalRepaymentDays,"
				+" IFNULL(ls.noMoney,0) as overdueLoanInterest,"
				+" (IFNULL(ld.sumDay,0)+IFNULL(ld1.sumDay1,0)) as overdueLoanInterestDays "
				+" FROM bp_fund_project AS b "
				+" LEFT JOIN"
				+"(select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='principalRepayment' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ps  on b.id=ps.preceptId and b.businessType=ps.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as pd"
				+" on b.id=pd.preceptId and b.businessType=pd.businessType"
				+" left JOIN"
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='principalRepayment' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as pd1 "
				+" on b.id=pd1.preceptId and b.businessType=pd1.businessType"
				+" LEFT JOIN"
				+" (select SUM(s.notMoney) as noMoney,s.preceptId,s.businessType,s.intentDate from sl_fund_intent s where s.fundType='loanInterest' and (( s.factDate is null and s.intentDate <= NOW()) or (s.factDate is not null and s.intentDate<s.factDate))"
				+" and s.notMoney<>0   GROUP BY s.preceptId) as ls  on b.id=ls.preceptId and b.businessType=ls.businessType"
				+" LEFT JOIN"
				+" (select SUM(TO_DAYS(NOW())-to_days(s.intentDate)) as sumDay,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest'  and s.factDate is null and s.intentDate <= NOW() and s.notMoney<>0 GROUP BY s.preceptId) as ld"
				+" on b.id=ld.preceptId and b.businessType=ld.businessType"
				+" left JOIN "
				+" (select SUM(TO_DAYS(s.factDate)-to_days(s.intentDate)) as sumDay1 ,s.preceptId,s.businessType from sl_fund_intent s where s.fundType='loanInterest' AND s.factDate is not null and s.intentDate<s.factDate and s.notMoney<>0 GROUP BY s.preceptId) as ld1"
				+" on b.id=ld1.preceptId and b.businessType=ld1.businessType"
				+" LEFT JOIN"
				+" cs_enterprise c on c.id=b.oppositeID "
				+" LEFT JOIN"
				+" cs_person p on c.legalpersonid=p.id"
				+" where b.flag=0 AND b.oppositeType = 'company_customer' ");
		    	StringBuffer sb = new StringBuffer();
			
				String customerId = request.getParameter("customerId");
				if(null != customerId && !"".equals(customerId)) {
					sql.append("and c.legalpersonid ="+Integer.valueOf(customerId));
				}
				String startDate = request.getParameter("startDate");
				if(!"".equals(startDate) && null != startDate) {
					sql.append(" and b.startDate >= '"+startDate+"'");
				}
				String endDate = request.getParameter("endDate");
				if(!"".equals(endDate) && null != endDate) {
					sql.append(" and b.startDate <= '"+endDate+"'");
				}
				sql.append(" order by b.createDate desc ");
			//	System.out.println("---->"+sql);
				if(null!=pb){
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
				}else{
					return this.getSession().createSQLQuery(sql.toString())
					.addScalar("id", Hibernate.LONG)
					.addScalar("projectId", Hibernate.LONG)
					.addScalar("productId", Hibernate.LONG)
					.addScalar("operationType", Hibernate.STRING)
					.addScalar("flowType", Hibernate.STRING)
					.addScalar("flag", Hibernate.SHORT)
					.addScalar("oppositeType", Hibernate.STRING)
					.addScalar("oppositeID", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("projectNumber", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("projectMoney", Hibernate.BIG_DECIMAL)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("ownJointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("jointMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepayment", Hibernate.BIG_DECIMAL)
					.addScalar("overduePrincipalRepaymentDays", Hibernate.LONG)
					.addScalar("overdueLoanInterest", Hibernate.BIG_DECIMAL)
					.addScalar("overdueLoanInterestDays", Hibernate.LONG)
					.setResultTransformer(Transformers.aliasToBean(BpFundProject.class)).list();
				}
	}
}
