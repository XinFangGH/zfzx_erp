package com.zhiwei.credit.dao.creditFlow.smallLoan.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.VSmallloanProjectDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VSmallloanProjectDaoImpl extends BaseDaoImpl<VSmallloanProject> implements VSmallloanProjectDao{
	
	
	public VSmallloanProjectDaoImpl() {
		super(VSmallloanProject.class);
	}
	
	/**
	 * 得到小额贷款项目信息列表
	 * @param userIds 
	 * @param projectStatus
	 * @param pb
	 * @param request
	 * @return
	 * @author lisl
	 */
	public List<VSmallloanProject> getProjectList(String userIdsStr,Short []projectStatus,PagingBean pb,HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		if(null!=projectStatus && projectStatus.length > 0) {
			for(Short status : projectStatus) {
				sb.append(status);
				sb.append(",");
			}
		}
		String status = "";
		if(!"".equals(sb.toString())) {
			status = sb.toString().substring(0, sb.length()-1);
		}
		StringBuffer hql = new StringBuffer("from VSmallloanProject vp where ");
		if(!"".equals(status)) {
			hql.append(" vp.projectStatus in ("+status+")  and");
		}
		if (userIdsStr != "" && !"".equals(userIdsStr)) {
			hql.append(" (fn_check_repeat(vp.userId,'"+userIdsStr+"') = 1 or fn_check_repeat(vp.businessManager,'"+userIdsStr+"') = 1) and");
		}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			hql.append(" vp.companyId in ("+companyIdStr+") and ");
		}
		hql.append("  (vp.processName not like '%smallLoanPostponedFlow%' ");
		hql.append(" and vp.processName not like '%slDirectorOpinionFlow%' ");
		hql.append(" and vp.processName not like '%smallLoanSuperviseFlow%' ");
		hql.append(" and vp.processName not like '%microPostponedFlow%' ");
		hql.append(" and vp.processName not like '%repaymentAheadOfTimeFlow%' ");
		hql.append(" and vp.processName not like '%alterAccrualFlow%') ");
		String keyWord = request.getParameter("keyWord");
		if(!"".equals(keyWord)  && null != keyWord) {
			hql.append(" and (vp.projectName like '%"+keyWord+"%' or vp.projectNumber like '%/"+keyWord+"%'  escape '/')");
		}
		String orgUserId = request.getParameter("orgUserId");
		if(!"".equals(orgUserId) && null != orgUserId) {
			hql.append("and vp.orgUserId ="+Long.valueOf(orgUserId)+" ");
		}
		/*if(!"".equals(companyId) && null != companyId) {
			hql.append("and vp.companyId ="+Long.valueOf(companyId)+" ");
		}*/
		String superviseOpinion = request.getParameter("superviseOpinion");
		/*if(!"".equals(superviseOpinion) && null != superviseOpinion) {
			hql.append("and vp.projectId in (select si.projectId from  SlSuperviseIn si where si.superviseOpinion = (select d.dicId from Dictionary d where d.dicKey='"+superviseOpinion+"'))");
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
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			hql.append(" and fn_check_repeat(vp.businessManager,'"+businessManager+"') = 1)");
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
		/*String isCapitalUnexpired = request.getParameter("isCapitalUnexpired");
		if("true".equals(isCapitalUnexpired)) {
			hql.append(" and vp.expectedRepaymentDate >'" + new Date().toLocaleString()+"'");
		}*/
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
	
	/**
	 * 通过ProjectId获取VSmallloanProject
	 * @param projectId
	 * @return
	 */
	public VSmallloanProject getByProjectId(Long projectId) {
		VSmallloanProject vp = null;
		String hql = "from VSmallloanProject vp where vp.projectId = ?";
		List<VSmallloanProject> list = findByHql(hql,new Object[]{projectId});
		if (list != null&&list.size()!=0) {
			vp = list.get(0);
		}
		return vp;
	}
	
	@Override
	public List<VSmallloanProject> getSmallList(String projectNum,
			String projectName, int start, int limit,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		StringBuffer hql=new StringBuffer("from VSmallloanProject as sl where 1=1");
		if(null!=strs && !"".equals(strs)){
			hql.append(" and sl.companyId in ("+strs+")");
		}
		hql.append(" and sl.projectNumber like ? and sl.projectName like ? order by sl.createtime desc");
		return getSession().createQuery(hql.toString()).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public long getSmallNum(String projectNum, String projectName,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		StringBuffer hql=new StringBuffer("select count(*) from VSmallloanProject as sl where 1=1");
		if(null!=strs && !"".equals(strs)){
			hql.append(" and sl.companyId in ("+strs+")");
		}
		hql.append(" and sl.projectNumber like ? and sl.projectName like ? order by sl.createtime desc");
		long count=0;
		List list=getSession().createQuery(hql.toString()).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").list();
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}

	@Override
	public List<VSmallloanProject> getByPiIdAndDate(String piId,
			Date startDate, Date endDate) {
		String hql="from VSmallloanProject vp where vp.piId=? and vp.runStatus=?";
		if(null!=startDate && null!=endDate){
			hql=hql+" and vp.expectedRepaymentDate between ? and ?";
			return findByHql(hql, new Object[]{piId,Constants.PROJECT_STATUS_MIDDLE,startDate,endDate});
		}
		return findByHql(hql, new Object[]{piId,Constants.PROJECT_STATUS_MIDDLE});
	}

	@Override
	public VSmallloanProject getByRunIdAndDate(Long runId, Date startDate,
			Date endDate) {
		String hql="from VSmallloanProject vp where vp.runId=?";
		List<VSmallloanProject> list=null;
		if(null!=startDate && null!=endDate){
			hql=hql+" and vp.expectedRepaymentDate between ? and ?";
			list=findByHql(hql,new Object[]{runId,startDate,endDate});
		}else if(startDate==null && endDate==null){
			list=findByHql(hql,new Object[]{runId});
		}
		VSmallloanProject vs=null;
		if(null!=list && list.size()>0){
			vs=list.get(0);
		}
		return vs;
	}
}
