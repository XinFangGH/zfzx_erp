package com.zhiwei.credit.dao.creditFlow.smallLoan.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.ProjectPropertyClassificationDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.ProjectPropertyClassification;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ProjectPropertyClassificationDaoImpl extends BaseDaoImpl<ProjectPropertyClassification> implements ProjectPropertyClassificationDao{

	public ProjectPropertyClassificationDaoImpl() {
		super(ProjectPropertyClassification.class);
	}

	@Override
	public ProjectPropertyClassification getByProjectId(Long projectId,
			String businessType) {
		String hql="from ProjectPropertyClassification as p where p.projectId=? and p.businessType=?";
		return (ProjectPropertyClassification) getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).uniqueResult();
	}

	@Override
	public void savePropertyInfo(
			ProjectPropertyClassification projectPropertyClassification) {
		try{
			if(projectPropertyClassification.getId()==null){
				this.save(projectPropertyClassification);
			}else{
				ProjectPropertyClassification ppc=this.get(projectPropertyClassification.getId());
				BeanUtil.copyNotNullProperties(ppc, projectPropertyClassification);
				this.save(ppc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}