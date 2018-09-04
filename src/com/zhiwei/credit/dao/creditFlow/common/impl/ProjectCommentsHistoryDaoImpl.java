/**
 * 
 */
package com.zhiwei.credit.dao.creditFlow.common.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.common.ProjectCommentsHistoryDao;
import com.zhiwei.credit.model.creditFlow.common.ProjectCommentsHistory;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;

/**
 * @author gao
 *
 */
public class ProjectCommentsHistoryDaoImpl extends BaseDaoImpl<ProjectCommentsHistory> implements ProjectCommentsHistoryDao{

	public ProjectCommentsHistoryDaoImpl() {
		super(ProjectCommentsHistory.class);
	}

	@Override
	public List<ProjectCommentsHistory> getList(Long projectId,
			String businessType, String componentKey) {
		String hql = "from ProjectCommentsHistory pch where pch.projectId=? and pch.businessType=? and pch.componentKey=?";
		Object[] objs={projectId,businessType,componentKey};
		return this.findByHql(hql, objs);
	}
}
