package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/


import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.RunData;

public interface RunDataService extends BaseService<RunData>{
	
	/**
	 * 取得某个运行实例中某个字段的详细信息
	 * @param runId
	 * @param fieldName
	 * @return
	 */
	public RunData getByRunIdFieldName(Long runId,String fieldName);
	
	/**
	 * 保存流程实例对应的变量
	 * @param runId
	 * @param vars
	 */
	public void saveFlowVars(Long runId,Map<String,Object> vars);
	/**
	 * 取得某个流程对应的所有参数Map
	 * @param runId
	 * @return
	 */
	public Map<String,Object> getMapByRunId(Long runId);

	/**
	 * 取得某个流程对应的参数数据列表
	 * @param runId
	 * @return
	 * 删除一个任务的时候需要删除run_data表中所有关联数据
	 */
	public List<RunData> getByRunId(Long runId);
}


