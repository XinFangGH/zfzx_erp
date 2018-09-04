package com.zhiwei.credit.dao.creditFlow.common;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.common.ProjectCommentsHistory;

public interface ProjectCommentsHistoryDao extends BaseDao<ProjectCommentsHistory>{
	public List<ProjectCommentsHistory> getList(Long projectId,String businessType,String componentKey);
}
