package com.zhiwei.credit.dao.creditFlow.financeProject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financeProject.VFinancingProjectDao;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.service.customer.InvestPersonService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VFinancingProjectDaoImpl extends BaseDaoImpl<VFinancingProject> implements VFinancingProjectDao{

	@Resource
	private InvestPersonService investPersonService;
	
	public VFinancingProjectDaoImpl() {
		super(VFinancingProject.class);
	}
	/**
	 * 得到融资项目信息列表
	 * @param userIdsStr 
	 * @param pb
	 * @param request
	 * @return
	 * @author lisl
	 */
	public List<VFinancingProject> getProjectList(String userIdsStr,PagingBean pb,HttpServletRequest request,Map map) {
		String projectStatus = request.getParameter("projectStatus");
		StringBuffer hql = new StringBuffer("from VFinancingProject vp where 1=1");
		if(projectStatus != "" && projectStatus != null) {
			hql.append(" and vp.projectStatus  in ("+projectStatus+")");
		}
		if (userIdsStr != "") {
			hql.append(" and (fn_check_repeat(vp.userId,'"+userIdsStr+"') = 1 or fn_check_repeat(vp.businessManager,'"+userIdsStr+"') = 1) ");
		}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			hql.append(" and vp.companyId in ("+companyIdStr+")");
		}		
		
		String keyWord = request.getParameter("keyWord");
		if(!"".equals(keyWord)  && null != keyWord) {
			hql.append(" and vp.projectNumber like '%/"+keyWord+"%'  escape '/'");
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
		
		String smallEndDate = request.getParameter("Q_endDate_D_GE");
		String bigEndDate = request.getParameter("Q_endDate_D_LE");
		if(!"".equals(smallEndDate) && null != smallEndDate  && (null == bigEndDate || "".equals(bigEndDate))) {
			hql.append(" and vp.endDate >= '"+smallEndDate+"'");
		}else if (!"".equals(bigEndDate) && null != bigEndDate  && (null == smallEndDate || "".equals(smallEndDate))) {
			hql.append(" and vp.endDate <= '"+bigEndDate+"'");
		}else if (!"".equals(bigEndDate) && null != smallEndDate && null != bigEndDate && !"".equals(smallEndDate)) {
			hql.append(" and vp.endDate >= '"+smallEndDate+"' and vp.endDate <='"+bigEndDate+"'");
		}
		
		String businessManager = request.getParameter("Q_businessManager_S_LK"); 
		if(null != businessManager && !"".equals(businessManager)) {
			
			hql.append(" and fn_check_repeat(vp.businessManager,'"+businessManager+"') = 1)");
		}
		hql.append(" and vp.processName <> 'smallLoanPostponedFlow'");
		hql.append(" and vp.processName <> 'slDirectorOpinionFlow'");
		
		String  customerLevel = request.getParameter("customerLevel");
		List<InvestPerson> listIP = new ArrayList<InvestPerson>();
		if(null !=customerLevel && !"".equals(customerLevel)){
			listIP = investPersonService.getListByCustomerLevel(customerLevel);
			String ids = "";
			for(InvestPerson ip : listIP){
				ids+=ip.getPerId()+",";
			}
			if(!ids.equals("")){
				ids = ids.substring(0,ids.length()-1);	
				hql.append(" and vp.oppositeId in ("+ids+")");
			}else{
				hql.append(" and vp.oppositeId in ('')");
			}
		}
		//根据部门获取项目信息
		if(map != null && map.size() != 0){
			String department = map.get("departmentId").toString();
			if(null != department && !department.equals("")){
				hql.append(" and vp.businessManager in ("+department+")");
			}else if (department.equals("")){
				hql.append(" and vp.businessManager = 0");
			}
		}		
		String sort = request.getParameter("dir");
		if(Constants.PROJECT_STATUS_STOP.toString().equals(projectStatus)  || Constants.PROJECT_STATUS_COMPLETE.toString().equals(projectStatus)) {
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
	 * 通过ProjectId获取VFinancingProject
	 * @param projectId
	 * @return
	 */
	public VFinancingProject getByProjectId(Long projectId) {
		VFinancingProject vp = null;
		String hql = "from VFinancingProject vp where vp.projectId = ?";
		List<VFinancingProject> list = findByHql(hql,new Object[]{projectId});
		if (list != null) {
			vp = list.get(0);
		}
		return vp;
	}
	@Override
	public List<VFinancingProject> getFinancingList(String projectNum,
			String projectName, int start, int limit,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="from VFinancingProject as vp where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and vp.companyId in ("+strs+")";
		}
		hql=hql+" and vp.projectNumber like ? and vp.projectName like ? order by vp.createtime desc";
		return getSession().createQuery(hql).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").setFirstResult(start).setMaxResults(limit).list();
	}
	@Override
	public long getFinancingNum(String projectNum, String projectName,String companyId) {
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="select count(*) from VFinancingProject as vp where 1=1";
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