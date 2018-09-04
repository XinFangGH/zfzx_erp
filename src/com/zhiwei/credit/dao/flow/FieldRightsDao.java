package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.FieldRights;

/**
 * 
 * @author 
 *
 */
public interface FieldRightsDao extends BaseDao<FieldRights>{
	/**
	 * 根据映射字段和节点来查找权限
	 * @param mappingId
	 * @param fieldId
	 * @param taskName
	 * @return
	 */
	public List<FieldRights> getByMappingFieldTaskName(Long mappingId,Long fieldId,String taskName);
	/**
	 * 根据映射和任务节点来查找表单的权限列表
	 * @param mappingId
	 * @param taskName
	 * @return
	 */
	public List<FieldRights> getByMappingIdAndTaskName(Long mappingId,String taskName);
	/**
	 * 根据映射ID来查找权限
	 * @param mappingId
	 * @return
	 */
	public List<FieldRights> getByMappingId(Long mappingId);
}