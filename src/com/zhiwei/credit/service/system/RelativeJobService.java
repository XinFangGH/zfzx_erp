package com.zhiwei.credit.service.system;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.RelativeJob;

/**
 * @description 相对岗位管理
 * @author 智维软件
 * @company www.credit-software.com
 * @data 2010-12-13PM
 * 
 */
public interface RelativeJobService extends BaseService<RelativeJob> {

	/**
	 * @description 根据parentId查询子节点信息
	 * @param parentId
	 *            父节点id
	 * @return List<RelativeJob>
	 */
	List<RelativeJob> findByParentId(Long parentId);
	
}
