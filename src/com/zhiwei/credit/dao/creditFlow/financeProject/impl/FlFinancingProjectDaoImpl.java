package com.zhiwei.credit.dao.creditFlow.financeProject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.financeProject.FlFinancingProjectDao;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class FlFinancingProjectDaoImpl extends BaseDaoImpl<FlFinancingProject> implements FlFinancingProjectDao{

	public FlFinancingProjectDaoImpl() {
		super(FlFinancingProject.class);
	}

	@Override
	public List<FlFinancingProject> getTodayProjectList(Date date) {
		Long companyId = ContextUtil.getLoginCompanyId();
		if(companyId == null) {
			companyId = Long.valueOf("1");
		}
		String hql="from FlFinancingProject as sl  where  date_format(sl.createDate,'%Y-%m-%d') = date_format(?,'%Y-%m-%d') and sl.companyId = ?";
		return getSession().createQuery(hql).setParameter(0, date).setParameter(1, companyId).list();
	}
	
	/**
	 * 生成项目编号
	 * @param projectNumberKey
	 * return
	 */
	@Override
	public FlFinancingProject getProjectNumber(String projectNumberKey){
		String hql="from FlFinancingProject as fl where fl.projectNumber like '%"+projectNumberKey+"%'order by fl.projectId DESC";
		List<FlFinancingProject> list = super.findByHql(hql);
		FlFinancingProject flFinancingProject=null;
		if(null!=list && list.size()>0){
			flFinancingProject = list.get(0);
			return flFinancingProject;
		}
		return null;
	}
}