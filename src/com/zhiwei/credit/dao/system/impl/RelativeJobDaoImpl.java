package com.zhiwei.credit.dao.system.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.RelativeJobDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.RelativeJob;

/**
 * @description 相对岗位管理
 * @author 智维软件
 * @company www.credit-software.com
 * @data 2010-12-13AM
 */
@SuppressWarnings("unchecked")
public class RelativeJobDaoImpl extends BaseDaoImpl<RelativeJob> implements RelativeJobDao {

	public RelativeJobDaoImpl() {
		super(RelativeJob.class);
	}

	/**
	 * 方法重写，添加：根据id查询
	 */
	@Override
	public void remove(Long id) {
		String hql = "delete from RelativeJob r where r.path like ? ";
		Query query = getSession().createQuery(hql);
		query.setString(0, get(id).getPath() + "%");
		logger.debug(hql);
		query.executeUpdate();
	}

	/**
	 * 根据parentId查询对应的父节点信息
	 */
	@Override
	public List<RelativeJob> findByParentId(Long parentId) {
		String hql = "select r from RelativeJob r where r.parent = ? ";
		Object[] paramList = { parentId};
		return findByHql(hql, paramList);
	}
	
}