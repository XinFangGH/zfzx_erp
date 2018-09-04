package com.zhiwei.credit.dao.creditFlow.guarantee.project.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.VGuaranteeloanProjectDao;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;
@SuppressWarnings("unchecked")
public class VGuaranteeloanProjectDaoImpl extends BaseDaoImpl<VGuaranteeloanProject> implements VGuaranteeloanProjectDao{

	public VGuaranteeloanProjectDaoImpl() {
		super(VGuaranteeloanProject.class);
	}
	
	public Long getProjectIdByRunId(Long runId) {
		String hql = "from VGuaranteeloanProject v where v.runId="+runId;
		return findByHql(hql).get(0).getProjectId();
	}
	
	public List<VGuaranteeloanProject> getByTaskIds(String taskIds,PagingBean pb){
		String hql = "from VGuaranteeloanProject vp where vp.taskId in("+taskIds+") order by vp.taskCreateTime desc";
		return findByHql(hql, null, pb);
	}
	
	public List<VGuaranteeloanProject> getByPiId(String piId){
		String hql = "from VGuaranteeloanProject vp where vp.piId=? and vp.runStatus=?";
		return findByHql(hql, new Object[]{piId,Constants.PROJECT_STATUS_MIDDLE});
	}
	
	public List<VGuaranteeloanProject> getByTaskId(String taskId){
		String hql = "from VGuaranteeloanProject vp where vp.taskId=?";
		return findByHql(hql,new Object[]{taskId});
	}

	@Override
	public List<VGuaranteeloanProject> getByProjectId(Long projectId) {
		String hql = "from VGuaranteeloanProject v where v.projectId="+projectId;
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
	public List<VGuaranteeloanProject> getProjectList(String userIdsStr,Short projectStatus,Short bmStatus,PagingBean pb,HttpServletRequest request) {
		StringBuffer hql = new StringBuffer("from VGuaranteeloanProject vp where vp.projectStatus ="+projectStatus+" and vp.bmStatus ="+bmStatus+" ");
		String companyId=request.getParameter("companyId");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			hql.append(" and vp.companyId in ("+str+")");
		}
		if (userIdsStr != "") {
			hql.append("and (fn_check_repeat(vp.userId,'"+userIdsStr+"') = 1 or fn_check_repeat(vp.appUserIdOfA,'"+userIdsStr+"') = 1)");
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
		String guaranteeBusinessManager = request.getParameter("Q_appUserIdOfA_S_LK"); 
		if(!"".equals(guaranteeBusinessManager) && null != guaranteeBusinessManager) {
			hql.append(" and fn_check_repeat(vp.appUserIdOfA,'"+guaranteeBusinessManager+"') = 1)");
		}
		hql.append("  and (vp.processName not like '%GuaranteeSuperviseFlow%') ");
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
	public List<VGuaranteeloanProject> getGuaranteeList(String projectNum,
			String projectName, int start, int limit,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="from VGuaranteeloanProject as vp where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and vp.companyId in ("+strs+")";
		}
		hql=hql+" and vp.projectNumber like ? and vp.projectName like ? order by vp.createtime desc";
		return getSession().createQuery(hql).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public long getGuaranteeNum(String projectNum, String projectName,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="select count(*) from VGuaranteeloanProject as vp where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and vp.companyId in ("+strs+")";
		}
		hql=hql+" and vp.projectNumber like ? and vp.projectName like ? order by vp.createtime desc";
		long count=0;
		List list=getSession().createQuery(hql).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").list();
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}
}
