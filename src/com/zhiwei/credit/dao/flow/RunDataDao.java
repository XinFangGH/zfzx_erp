package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.RunData;

/**
 * 
 * @author 
 *
 */
public interface RunDataDao extends BaseDao<RunData>{

	/**
	 * 取得某个运行实例中某个字段的详细信息
	 * @param runId
	 * @param fieldName
	 * @return
	 */
	public RunData getByRunIdFieldName(Long runId,String fieldName);
	
	/**
	 * 取得某个流程对应的参数数据列表
	 * @param runId
	 * @return
	 */
	public List<RunData> getByRunId(Long runId);
}