package com.zhiwei.credit.dao.system;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.RelativeJob;

/**
 * @description 相对岗位管理
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-13AM
 */
public interface RelativeJobDao extends BaseDao<RelativeJob> {

	/**
	 * @description 根据parentId查询对应的相对岗位信息
	 * @param parentId
	 *            父节点Id
	 * @return List<RelativeJob>
	 */
	List<RelativeJob> findByParentId(Long parentId);
}