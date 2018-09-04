package com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project.VLawsuitguarantProjectDao;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.VLawsuitguarantProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")                                  
public class VLawsuitguarantProjectDaoImpl extends BaseDaoImpl<VLawsuitguarantProject> implements VLawsuitguarantProjectDao{

	public VLawsuitguarantProjectDaoImpl() {
		super(VLawsuitguarantProject.class);
	}

	@Override
	public List<VLawsuitguarantProject> getProjectList(String userIdsStr,
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
		StringBuffer hql = new StringBuffer("from VLawsuitguarantProject vp where 1=1 ");
		if(!"".equals(status)) {
			hql.append("and vp.projectStatus in ("+status+")");
		}
		if (userIdsStr != "") {
			hql.append("and (vp.userId in ("+userIdsStr+") or vp.businessManager in ("+userIdsStr+")) ");
		}
		
		String projectNumber = request.getParameter("Q_projectNumber_S_LK");
		if(!"".equals(projectNumber) && null != projectNumber) {
			hql.append(" vp.projectNumber like '%/"+projectNumber+"%'  escape '/' ");
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
			hql.append(" and vp.businessManager like '%"+businessManager+"%'");
		}
		if(pb == null) {
			return findByHql(hql.toString());
		} else {
			return findByHql(hql.toString(), null, pb);
		}
	}
   
	public VLawsuitguarantProject getByProjectId(Long projectId) {
		VLawsuitguarantProject vp = null;
		String hql = "from VLawsuitguarantProject vp where vp.projectId = " + projectId;
		List<VLawsuitguarantProject> list = findByHql(hql);
		if(list.size() > 0) {
			vp = list.get(0);
		}
		return vp;
	}
}