package com.zhiwei.credit.dao.creditFlow.guarantee.project.impl;


import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.GLGuaranteeloanProjectDao;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;

@SuppressWarnings("unchecked")
public class GLGuaranteeloanProjectDaoImpl  extends BaseDaoImpl<GLGuaranteeloanProject> implements GLGuaranteeloanProjectDao {

	public GLGuaranteeloanProjectDaoImpl()
	{
		super(GLGuaranteeloanProject.class);
	}

	@Override
	public void startCreditFlow(FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GLGuaranteeloanProject> getTodayProjectList(Date date) {
		
		String hql="from GLGuaranteeloanProject as sl where date_format(sl.createDate,'%Y-%m-%d')=date_format(?,'%Y-%m-%d')";
		return getSession().createQuery(hql).setParameter(0, date).list();
	}

	@Override
	public GLGuaranteeloanProject getProjcetById(Long projectId) {
		String hql="from GLGuaranteeloanProject as sl where  sl.projectId =?";
		List<GLGuaranteeloanProject> list=getSession().createQuery(hql).setParameter(0, projectId).list();
		if(null!=list && list.size()>0){
			return  list.get(0);
		}
		return null;
	}
	
	@Override
	public GLGuaranteeloanProject getProjectNumber(String projectNumberKey){
		String hql="from GLGuaranteeloanProject as gl where gl.projectNumber like '%"+projectNumberKey+"%'order by gl.projectId DESC";
		List<GLGuaranteeloanProject> list=super.findByHql(hql);
		GLGuaranteeloanProject gLGuaranteeloanProject=null;
		if(null!=list && list.size()>0){
			gLGuaranteeloanProject=list.get(0);
			return gLGuaranteeloanProject;
		}
		return null;
	}
}
