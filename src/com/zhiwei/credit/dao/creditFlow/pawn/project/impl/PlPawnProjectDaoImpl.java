package com.zhiwei.credit.dao.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PlPawnProjectDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlPawnProjectDaoImpl extends BaseDaoImpl<PlPawnProject> implements PlPawnProjectDao{

	public PlPawnProjectDaoImpl() {
		super(PlPawnProject.class);
	}
	@Override
	public PlPawnProject getProjectNumber(String projectNumberKey){
		String hql="from PlPawnProject as gl where gl.projectNumber like '%"+projectNumberKey+"%'order by gl.projectId DESC";
		List<PlPawnProject> list=super.findByHql(hql);
		PlPawnProject plPawnProject=null;
		if(null!=list && list.size()>0){
			plPawnProject=list.get(0);
			return plPawnProject;
		}
		return null;
	}
	@Override
	public List<VPawnProject> getProjectList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		StringBuffer sb=new StringBuffer();
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
		StringBuffer hql = new StringBuffer("from VPawnProject vp where 1=1");
		if(!"".equals(status)) {
			hql.append(" and vp.projectStatus in ("+status+")");
		}
		if (userIdsStr != "") {
			hql.append(" and (fn_check_repeat(vp.userId,'"+userIdsStr+"') = 1 or fn_check_repeat(vp.appUserId,'"+userIdsStr+"') = 1)");
		}
		String companyIdStr = ContextUtil.getBranchIdsStr();
		String companyId = request.getParameter("companyId");
		if(!"".equals(companyId) && null != companyId) {
			companyIdStr=companyId;
		}
		if(null != companyIdStr && !"".equals(companyIdStr)){
			hql.append(" and vp.companyId in ("+companyIdStr+") ");
		}
		//hql.append("  and vp.processName not like '%%' ");
		String phnumber=request.getParameter("phnumber");
		if(null!=phnumber){
			hql.append(" and vp.phnumber like '%"+phnumber+"%'");
		}
		String customerName=request.getParameter("customerName");
		if(null!=customerName){
			hql.append(" and vp.customerName like '%"+customerName+"%'");
		}
		String pawnProjectStatus=request.getParameter("pawnProjectStatus");
		if(null!=pawnProjectStatus && !"".equals(pawnProjectStatus)){
			hql.append(" and vp.projectStatus="+pawnProjectStatus);
		}
		if(null!=pb){
			return getSession().createQuery(hql.toString()).setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).list();
		}else{
			return getSession().createQuery(hql.toString()).list();

		}
	}
	@Override
	public VPawnProject getByProjectId(Long projectId) {
		String hql="from VPawnProject as p where p.projectId=?";
		return (VPawnProject) getSession().createQuery(hql).setParameter(0, projectId).uniqueResult();
	}
	@Override
	public List<VPawnProject> getPawnList(String projectNum,
			String projectName, int start, int limit, String companyId) {

		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="from VPawnProject as vp where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and vp.companyId in ("+strs+")";//mineId ●
		}
		hql=hql+" and vp.projectNumber like ? and vp.projectName like ? order by vp.createtime desc";
		return getSession().createQuery(hql).setParameter(0, "%"+projectNum+"%").setParameter(1, "%"+projectName+"%").setFirstResult(start).setMaxResults(limit).list();
	}
	@Override
	public long getLeaseFinanceNum(String projectNum, String projectName,
			String companyId) {

		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		String hql="select count(*) from VPawnProject as vp where 1=1";
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