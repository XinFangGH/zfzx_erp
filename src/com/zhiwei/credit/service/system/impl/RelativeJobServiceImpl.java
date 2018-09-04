package com.zhiwei.credit.service.system.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.RelativeJobDao;
import com.zhiwei.credit.model.system.RelativeJob;
import com.zhiwei.credit.service.system.RelativeJobService;

/**
 * @description 相对岗位管理
 * @author 智维软件
 * @company www.credit-software.com
 * @data 2010-12-13PM
 * 
 */
public class RelativeJobServiceImpl extends BaseServiceImpl<RelativeJob>
		implements RelativeJobService {

	private RelativeJobDao dao;

	public RelativeJobServiceImpl(RelativeJobDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * 根据parentId查询对应的父节点信息
	 */
	@Override
	public List<RelativeJob> findByParentId(Long parentId) {
		return dao.findByParentId(parentId);
	}

}