package com.zhiwei.credit.service.creditFlow.smallLoan.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.ProjectPropertyClassificationDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.ProjectPropertyClassification;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.ProjectPropertyClassificationService;

/**
 * 
 * @author 
 *
 */
public class ProjectPropertyClassificationServiceImpl extends BaseServiceImpl<ProjectPropertyClassification> implements ProjectPropertyClassificationService{
	@SuppressWarnings("unused")
	private ProjectPropertyClassificationDao dao;
	
	public ProjectPropertyClassificationServiceImpl(ProjectPropertyClassificationDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public ProjectPropertyClassification getByProjectId(Long projectId,
			String businessType) {
		
		return dao.getByProjectId(projectId, businessType);
	}

	@Override
	public void savePropertyInfo(ProjectPropertyClassification projectPropertyClassification) {
		dao.savePropertyInfo(projectPropertyClassification);
		
	}
}