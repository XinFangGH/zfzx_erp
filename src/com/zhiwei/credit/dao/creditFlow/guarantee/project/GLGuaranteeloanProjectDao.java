package com.zhiwei.credit.dao.creditFlow.guarantee.project;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;

public interface GLGuaranteeloanProjectDao extends BaseDao<GLGuaranteeloanProject>{
	
	public void startCreditFlow(FlowRunInfo flowRunInfo);
	
	public List<GLGuaranteeloanProject> getTodayProjectList(Date date);
	
	public  GLGuaranteeloanProject getProjcetById(Long projectId);

	/**
	 * 获取项目编号-预生成
	 * @param projectNumberKey
	 * @return
	 */
	public GLGuaranteeloanProject getProjectNumber(String projectNumberKey);
}
