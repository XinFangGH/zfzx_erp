package com.zhiwei.credit.service.creditFlow.common;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.common.ProjectCommentsHistory;

public interface ProjectCommentsHistoryService extends BaseService<ProjectCommentsHistory> {
	 public List<ProjectCommentsHistory> getList(Long projectId,String businessType,String componentKey);
}
