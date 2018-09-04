package com.zhiwei.credit.dao.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.archives.PlProjectArchivesDao;
import com.zhiwei.credit.model.creditFlow.archives.PlProjectArchives;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlProjectArchivesDaoImpl extends BaseDaoImpl<PlProjectArchives> implements PlProjectArchivesDao{

	public PlProjectArchivesDaoImpl() {
		super(PlProjectArchives.class);
	}

	@Override
	public List searchproject(Map<String, String> map, String businessType) {
		String projectName=map.get("projectName");
		String projectNum=map.get("projectNum");
		String oppositeTypeValue=map.get("oppositeTypeValue");
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String hql ="";
		String objectName="";
		if(null!=businessType && businessType.equals("SmallLoan")){
			objectName="VProcessRunData";
			 hql="from "+objectName+" v where v.businessType = 'SmallLoan' ";
			
		}else if(null!=businessType && businessType.equals("Guarantee")){
			objectName="VGuaranteeloanProject";
			 hql="from "+objectName+" v where v.projectId > 0 ";
		}else if(null!=businessType && businessType.equals("Financing")){
			objectName="VProcessRunData";
			 hql="from "+objectName+" v where v.businessType = 'Financing' ";
		}else {
			  return null;
			}
	   
		if(null!=projectName && !projectName.equals("")){
				hql=hql+" and v.projectName like '%"+projectName+"%'"; 
		}
		if(null!=projectNum && !projectNum.equals("")){
	    	   hql=hql+" and v.projectNumber like '%"+projectNum+"%'";
			}
	     if(null!=oppositeTypeValue && !oppositeTypeValue.equals("")){
	  	   hql=hql+ " and v.oppositeTypeValue= '"+oppositeTypeValue+"'";
	      }
		 Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
			
		 return  query.list();
	}

	@Override
	public int searchprojectsize(Map<String, String> map, String businessType) {
		String projectName=map.get("projectName");
		String projectNum=map.get("projectNum");
		String oppositeTypeValue=map.get("oppositeTypeValue");
		String hql ="";
		String objectName="";
		if(null!=businessType && businessType.equals("SmallLoan")){
			objectName="VProcessRunData";
			 hql="from "+objectName+" v where v.businessType = 'SmallLoan' ";
			
		}else if(null!=businessType && businessType.equals("Guarantee")){
			objectName="VGuaranteeloanProject";
			 hql="from "+objectName+" v where v.projectId > 0 ";
		}else if(null!=businessType && businessType.equals("Financing")){
			objectName="VProcessRunData";
			 hql="from "+objectName+" v where v.businessType = 'Financing' ";
		}else{
		  return 0;
		}
		if(null!=projectName && !projectName.equals("")){
			hql=hql+" and v.projectName like '%"+projectName+"%'"; 
		}
       if(null!=projectNum && !projectNum.equals("")){
    	   hql=hql+" and v.projectNumber like '%"+projectNum+"%'";
		}
       if(null!=oppositeTypeValue && !oppositeTypeValue.equals("")){
    	   hql=hql+ " and v.oppositeTypeValue= '"+oppositeTypeValue+"'";
       }
		 Query query = getSession().createQuery(hql);
			
		 return  query.list().size();
	}

	@Override
	public List<PlProjectArchives> getbyproject(Long projectId,
			String businessType) {
		String hql = "from PlProjectArchives s where s.projectId = "+projectId+" and s.businessType ='"+businessType+"'";
		return findByHql(hql);
	}

	@Override
	public Long projectCount(String businessType, HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		
		if(null!=businessType && businessType.equals("SmallLoan")){
			sql.append("SELECT "
					+" count(f.id) "
					
					+" FROM bp_fund_project AS f"
					+" LEFT JOIN pl_project_archives AS a ON a.projectId = f.id AND a.businessType"
					+" WHERE f.flag = 0 AND f.projectStatus = 1");
		}else{
			String obj="gl_guaranteeloan_project";
			if(businessType.equals("Pawn")){
				obj="pl_pawn_project";
			}else if(businessType.equals("Financing")){
				obj="fl_financing_project";
			}else if(businessType.equals("LeaseFinance")){
				obj="fl_lease_finance_project";
			}
			 sql.append("SELECT "
					+" count(f.projectId)"
					
					+" FROM "+obj+" AS f"
					+" LEFT JOIN pl_project_archives AS a ON a.projectId = f.id AND a.businessType"
					+" WHERE f.projectStatus = 1");
		}
		String companyId=request.getParameter("companyId");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !companyId.equals("")){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			sql.append(" and f.companyId in ("+str+")");
		}
		String projectNum=request.getParameter("projectNum");
		if(null!=projectNum && projectNum.equals("projectNum")){
			sql.append(" and f.projectNumber like '%"+projectNum+"%'");
		}
		String projectName=request.getParameter("projectName");
		if(null!=projectName && !projectName.equals("")){
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String isArchives=request.getParameter("isArchives");
		if(null!=isArchives && isArchives.equals("1")){
			sql.append(" and a.isArchives=1");
		}else if(null!=isArchives && isArchives.equals("0")){
			sql.append(" and a.isArchives is null");
		}
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
	public List projectList(String businessType, HttpServletRequest request,int start,int limit) {
		StringBuffer sql=new StringBuffer();
		
		if(null!=businessType && businessType.equals("SmallLoan")){
			sql.append("SELECT "
					+" f.id,"
					+" f.projectId,"
					+" f.projectName,"
					+" f.projectNumber,"
					+" f.projectMoney,"
					+" f.appUserName,"
					+" f.createDate,"
					+" a.isArchives,"
					+" f.oppositeType,"
					+" a.projtoarchiveId,"
					+" f.businessType"
					+" FROM bp_fund_project AS f"
					+" LEFT JOIN pl_project_archives AS a ON a.projectId = f.id AND a.businessType=f.businessType"
					+" WHERE f.flag = 0 AND f.projectStatus = 1");
		}else{
			String obj="gl_guaranteeloan_project";
			if(businessType.equals("Pawn")){
				obj="pl_pawn_project";
			}else if(businessType.equals("Financing")){
				obj="fl_financing_project";
			}else if(businessType.equals("LeaseFinance")){
				obj="fl_lease_finance_project";
			}
			 sql.append("SELECT "
					+" f.projectId,"
					+" f.projectName,"
					+" f.projectNumber,"
					+" f.projectMoney,"
					+" f.appUserName,"
					+" f.createDate,"
					+" a.isArchives,"
					+" f.oppositeType,"
					+" a.projtoarchiveId,"
					+" f.businessType"
					+" FROM "+obj+" AS f"
					+" LEFT JOIN pl_project_archives AS a ON a.projectId = f.projectId AND a.businessType=f.businessType"
					+" WHERE f.projectStatus = 1");
		}
		String companyId=request.getParameter("companyId");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !companyId.equals("")){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			sql.append(" and f.companyId in ("+str+")");
		}
		String projectNum=request.getParameter("projectNum");
		if(null!=projectNum && !projectNum.equals("")){
			sql.append(" and f.projectNumber like '%"+projectNum+"%'");
		}
		String projectName=request.getParameter("projectName");
		if(null!=projectName && !projectName.equals("")){
			sql.append(" and f.projectName like '%"+projectName+"%'");
		}
		String isArchives=request.getParameter("isArchives");
		if(null!=isArchives && isArchives.equals("1")){
			sql.append(" and a.isArchives=1");
		}else if(null!=isArchives && isArchives.equals("0")){
			sql.append(" and a.isArchives is null");
		}
		return this.getSession().createSQLQuery(sql.toString()).setFirstResult(start).setMaxResults(limit).list();
	}

}