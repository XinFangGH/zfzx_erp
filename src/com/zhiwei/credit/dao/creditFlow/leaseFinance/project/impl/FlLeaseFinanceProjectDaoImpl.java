package com.zhiwei.credit.dao.creditFlow.leaseFinance.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.FlLeaseFinanceProjectDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class FlLeaseFinanceProjectDaoImpl extends BaseDaoImpl<FlLeaseFinanceProject> implements FlLeaseFinanceProjectDao{
	public FlLeaseFinanceProjectDaoImpl() {
		super(FlLeaseFinanceProject.class);
	}
	
	public List<FlLeaseFinanceProject> getProjectById(Long projectId){
		String hql ="from FlLeaseFinanceProject fl where fl.projectId="+projectId;
		return super.findByHql(hql);
	}
	
	
	@Override
	public FlLeaseFinanceProject getProjectNumber(String projectNumberKey){
		String hql="from FlLeaseFinanceProject as fl where fl.projectNumber like '%"+projectNumberKey+"%'order by fl.projectId DESC";
		List<FlLeaseFinanceProject> list=super.findByHql(hql);
		FlLeaseFinanceProject flLeaseFinanceProject=null;
		if(null!=list && list.size()>0){
			flLeaseFinanceProject=list.get(0);
			return flLeaseFinanceProject;
		}
		return null;
	}
	
	public VLeaseFinanceProject getViewByProjectId(Long projectId){
		String hql="from VLeaseFinanceProject as p where p.projectId=?";
		return (VLeaseFinanceProject) getSession().createQuery(hql).setParameter(0, projectId).uniqueResult();
	}
	
}