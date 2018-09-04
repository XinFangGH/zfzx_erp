package com.zhiwei.credit.dao.creditFlow.leaseFinance.project.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.VLeaseFinanceProjectDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;
@SuppressWarnings("unchecked")
public class VLeaseFinanceProjectDaoImpl extends BaseDaoImpl<VLeaseFinanceProject> implements VLeaseFinanceProjectDao{

	public VLeaseFinanceProjectDaoImpl() {
		super(VLeaseFinanceProject.class);
	}
	
	public Long getProjectIdByRunId(Long runId) {
		String hql = "from VLeaseFinanceProject v where v.runId="+runId;
		return findByHql(hql).get(0).getProjectId();
	}
	
	public List<VLeaseFinanceProject> getByTaskIds(String taskIds,PagingBean pb){
		String hql = "from VLeaseFinanceProject vp where vp.taskId in("+taskIds+") order by vp.taskCreateTime desc";
		return findByHql(hql, null, pb);
	}
	
	public List<VLeaseFinanceProject> getByPiId(String piId){
		String hql = "from VLeaseFinanceProject vp where vp.piId=? and vp.runStatus=?";
		return findByHql(hql, new Object[]{piId,Constants.PROJECT_STATUS_MIDDLE});
	}
	
	public List<VLeaseFinanceProject> getByTaskId(String taskId){
		String hql = "from VLeaseFinanceProject vp where vp.taskId=?";
		return findByHql(hql,new Object[]{taskId});
	}

	@Override
	public List<VLeaseFinanceProject> getByProjectId(Long projectId) {
		String hql = "from VLeaseFinanceProject v where v.projectId="+projectId;
		return findByHql(hql);
	}
	
	/**
	 * 得到企业担保项目信息列表
	 * @param userIdsStr
	 * @param projectStatus
	 * @param bmStatus
	 * @param pb
	 * @param request
	 * @return
	 */
	public List<VLeaseFinanceProject> getProjectList(String userIdsStr,Short projectStatus,PagingBean pb,HttpServletRequest request) {
		StringBuffer hql = new StringBuffer();
		if(projectStatus == 100){//如果projectStatus=100则表示查询所有状态信息的项目,对于数据库 没有特殊意义
			hql.append("from VLeaseFinanceProject vp where 1=1 ");
		}else{
			hql.append("from VLeaseFinanceProject vp where vp.projectStatus ="+projectStatus+" ");
		}
		
		String companyId=request.getParameter("companyId");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			hql.append("and vp.companyId in ("+str+")");
		}
		if (userIdsStr != "") {
			hql.append("and (fn_check_repeat(vp.userId,'"+userIdsStr+"') = 1 or fn_check_repeat(vp.appUserId,'"+userIdsStr+"') = 1)");
		}
		String projectNumber = request.getParameter("Q_projectNumber_S_LK");
		if(!"".equals(projectNumber) && null != projectNumber) {
			hql.append(" and vp.projectNumber like '%/"+projectNumber+"%'  escape '/' ");
		}
		String projectName = request.getParameter("Q_projectName_S_LK");
		if(!"".equals(projectName)  && null != projectName) {
			hql.append(" and vp.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("Q_oppositeTypeValue_S_EQ");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			hql.append(" and vp.oppositeTypeValue = '"+oppositeTypeValue+"'");
		}
		String customerName = request.getParameter("Q_customerName_S_LK");
		if(!"".equals(customerName) && null != customerName) {
			hql.append(" and vp.customerName like '%"+customerName+"%'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			hql.append(" and vp.projectMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			hql.append(" and vp.projectMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			hql.append(" and vp.projectMoney >= "+Float.valueOf(smallProjectMoney)+" and vp.projectMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String smallStartDate = request.getParameter("Q_startDate_D_GE");
		String bigStartDate = request.getParameter("Q_startDate_D_LE");
		if(!"".equals(smallStartDate) && null != smallStartDate  && (null == bigStartDate || "".equals(bigStartDate))) {
			hql.append(" and vp.startDate >= '"+smallStartDate+"'");
		}else if (!"".equals(bigStartDate) && null != bigStartDate  && (null == smallStartDate || "".equals(smallStartDate))) {
			hql.append(" and vp.startDate <= '"+bigStartDate+"'");
		}else if (!"".equals(bigStartDate) && null != smallStartDate && null != bigStartDate && !"".equals(smallStartDate)) {
			hql.append(" and vp.startDate >= '"+smallStartDate+"' and vp.startDate <='"+bigStartDate+"'");
		}
		String guaranteeBusinessManager = request.getParameter("Q_appUserId_S_LK"); 
		if(!"".equals(guaranteeBusinessManager) && null != guaranteeBusinessManager) {
			hql.append(" and fn_check_repeat(vp.appUserId,'"+guaranteeBusinessManager+"') = 1)");
		}
		String sort = request.getParameter("dir");
		
		if(Constants.PROJECT_STATUS_STOP == projectStatus || Constants.PROJECT_STATUS_COMPLETE == projectStatus) {
			if(sort != null) {
				hql.append(" order by vp.endDate " + sort);
			}else {
				hql.append(" order by vp.endDate desc");
			}
		}else {
			if(sort != null) {
				hql.append(" order by vp.startDate " + sort);
			}else {
				hql.append(" order by vp.startDate desc");
			}
		}
		if(pb == null) {
			return findByHql(hql.toString());
		} else {
			return findByHql(hql.toString(), null, pb);
		}
	}

	@Override
	public List<VLeaseFinanceProject> getLeaseFinanceList(String projectNum,
		String projectName, int start, int limit,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="from VLeaseFinanceProject as vp where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and vp.companyId in ("+strs+")";//mineId ●
		}
		hql=hql+" and vp.projectNumber like ? and vp.projectName like ? order by vp.createtime desc";
		return getSession().createQuery(hql).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public long getLeaseFinanceNum(String projectNum, String projectName,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="select count(*) from VLeaseFinanceProject as vp where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and vp.companyId in ("+strs+")";//mineId 分公司Id
		}
		hql=hql+" and vp.projectNumber like ? and vp.projectName like ? order by vp.createtime desc";
		long count=0;
		List list=getSession().createQuery(hql).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").list();
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}
	
	@Override
	public List<VLeaseFinanceProject> getProjectList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
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
		StringBuffer hql = new StringBuffer("from VLeaseFinanceProject vp where ");
		if(!"".equals(status)) {
			hql.append(" vp.projectStatus in ("+status+")  and");
		}
		if (userIdsStr != "") {
			hql.append(" (fn_check_repeat(vp.userId,'"+userIdsStr+"') = 1 or fn_check_repeat(vp.businessManager,'"+userIdsStr+"') = 1) and");
		}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			hql.append(" vp.companyId in ("+companyIdStr+") ");
		}
		String keyWord = request.getParameter("keyWord");
		if(!"".equals(keyWord)  && null != keyWord) {
			hql.append(" and (vp.projectName like '%"+keyWord+"%' or vp.projectNumber like '%/"+keyWord+"%'  escape '/')");
		}
		
		/*if(!"".equals(companyId) && null != companyId) {
			hql.append("and vp.companyId ="+Long.valueOf(companyId)+" ");
		}*/
		String superviseOpinion = request.getParameter("superviseOpinion");
	/*	if(!"".equals(superviseOpinion) && null != superviseOpinion) {
			hql.append("and vp.projectId in (select si.projectId from  GlobalSuperviseIn si where si.superviseOpinion = (select d.dicId from Dictionary d where d.dicKey='"+superviseOpinion+"'))");
		}*/
		String projectNumber = request.getParameter("Q_projectNumber_S_LK");
		if(!"".equals(projectNumber) && null != projectNumber) {
			hql.append(" and vp.projectNumber like '%/"+projectNumber+"%'  escape '/' ");
		}
		String projectName = request.getParameter("Q_projectName_S_LK");
		if(!"".equals(projectName)  && null != projectName) {
			hql.append(" and vp.projectName like '%"+projectName+"%'");
		}
		String oppositeTypeValue = request.getParameter("Q_oppositeTypeValue_S_EQ");
		if(!"".equals(oppositeTypeValue) && null != oppositeTypeValue) {
			hql.append(" and vp.oppositeTypeValue = '"+oppositeTypeValue+"'");
		}
		String incomeMoneyGe = request.getParameter("Q_incomemoney_S_GE");
		String incomeMoneyLe = request.getParameter("Q_incomemoney_D_LE");
		if(!"".equals(incomeMoneyGe) && null != incomeMoneyGe  && (null == incomeMoneyLe || "".equals(incomeMoneyLe))) {
			hql.append(" and vp.projectMoney >= "+incomeMoneyGe+" ");
		}else if (!"".equals(incomeMoneyLe) && null != incomeMoneyLe  && (null == incomeMoneyGe || "".equals(incomeMoneyGe))) {
			hql.append(" and vp.projectMoney <= "+incomeMoneyGe+" ");
		}else if (!"".equals(incomeMoneyGe) && null != incomeMoneyGe && null != incomeMoneyLe && !"".equals(incomeMoneyLe)) {
			hql.append(" and vp.projectMoney >= "+incomeMoneyGe+" and vp.projectMoney <="+incomeMoneyLe+" ");
		}
		
		String customerName = request.getParameter("Q_customerName_S_LK");
		if(!"".equals(customerName) && null != customerName) {
			hql.append(" and vp.customerName like '%"+customerName+"%'");
		}
		String smallProjectMoney = request.getParameter("Q_projectMoney_BD_GE");
		String bigProjectMoney = request.getParameter("Q_projectMoney_BD_LE");
		if(!"".equals(smallProjectMoney) && null != smallProjectMoney && ("".equals(bigProjectMoney) || null == bigProjectMoney)) {
			hql.append(" and vp.projectMoney >= "+Float.valueOf(smallProjectMoney));
		}else if (!"".equals(bigProjectMoney) && null != bigProjectMoney && ("".equals(smallProjectMoney) || null == smallProjectMoney)) {
			hql.append(" and vp.projectMoney <="+Float.valueOf(bigProjectMoney));
		}else if (!"".equals(smallProjectMoney) && null != smallProjectMoney && !"".equals(bigProjectMoney) && null != bigProjectMoney) {
			hql.append(" and vp.projectMoney >= "+Float.valueOf(smallProjectMoney)+" and vp.projectMoney <="+Float.valueOf(bigProjectMoney));	
		}
		String smallStartDate = request.getParameter("Q_startDate_D_GE");
		String bigStartDate = request.getParameter("Q_startDate_D_LE");
		if(!"".equals(smallStartDate) && null != smallStartDate  && (null == bigStartDate || "".equals(bigStartDate))) {
			hql.append(" and vp.startDate >= '"+smallStartDate+"'");
		}else if (!"".equals(bigStartDate) && null != bigStartDate  && (null == smallStartDate || "".equals(smallStartDate))) {
			hql.append(" and vp.startDate <= '"+bigStartDate+"'");
		}else if (!"".equals(bigStartDate) && null != smallStartDate && null != bigStartDate && !"".equals(smallStartDate)) {
			hql.append(" and vp.startDate >= '"+smallStartDate+"' and vp.startDate <='"+bigStartDate+"'");
		}
		/*String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			hql.append(" and vp.businessManager like '%"+businessManager+"%'");
		}*/
		String appUserId = request.getParameter("Q_businessManager_S_LK"); 
		if(null != appUserId && !"".equals(appUserId)) {
			hql.append(" and vp.appUserId in ("+appUserId+") ");
		}
		String projectProperties=request.getParameter("projectProperties");
		String properties="";
		if(null!=projectProperties && !projectProperties.equals("")){
			String[] propertiesArr=projectProperties.split(",");
			for(int i=0;i<propertiesArr.length;i++){
				properties=properties+"'"+propertiesArr[i]+"',";
			}
			if(!properties.equals("")){
				properties=properties.substring(0,properties.length()-1);
			}
		}
        if(!properties.equals("")){
        	hql.append(" and vp.projectProperties in ("+properties+")");
        }
		String sort = request.getParameter("dir");
		if(Constants.PROJECT_STATUS_STOP.toString().equals(status) || Constants.PROJECT_STATUS_COMPLETE.toString().equals(status)) {
			if(sort != null) {
				hql.append(" order by vp.endDate " + sort);
			}else {
				hql.append(" order by vp.endDate desc");
			}
		}else {
			if(sort != null) {
				hql.append(" order by vp.startDate " + sort);
			}else {
				hql.append(" order by vp.startDate desc");
			}
		}
		if(pb == null) {
			return findByHql(hql.toString());
		} else {
			return findByHql(hql.toString(), null, pb);
		}
	}
}
