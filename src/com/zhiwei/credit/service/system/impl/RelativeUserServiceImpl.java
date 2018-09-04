package com.zhiwei.credit.service.system.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;
import java.util.Set;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.RelativeUserDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.RelativeUser;
import com.zhiwei.credit.service.system.RelativeUserService;

/**
 * @description 相对岗位人员管理
 * @author 智维软件
 * @company www.credit-software.com
 * @data 2010-12-13PM
 * 
 */
public class RelativeUserServiceImpl extends BaseServiceImpl<RelativeUser>
		implements RelativeUserService {

	private RelativeUserDao dao;

	public RelativeUserServiceImpl(RelativeUserDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * 根据userId和jobUserId查询对应数据的总行数
	 */
	@Override
	public AppUser judge(Long userId, Long jobUserId) {
		return dao.judge(userId, jobUserId);
	}

	@Override
	public List<AppUser> findByUserIdReJobId(Long userId, Long reJobId) {
		return dao.findByUserIdReJobId(userId, reJobId);
	}

	@Override
	public List<RelativeUser> list(Long appUserId, Long reJobId, PagingBean pb) {
		return dao.list(appUserId, reJobId, pb);
	}
	
	/**
	 * 取得某个用户的相对岗位人员
	 * @param userId 用户ID
	 * @param reJobId  相对岗位ID
	 * @return
	 */
	public List<Long> getReJobUserIds(Long userId,Long reJobId){
		return dao.getReJobUserIds(userId, reJobId);
	}

	/**
	 * 取得某个用户的上级
	 */
	@Override
	public Set<AppUser> getUpUser(Long userId) {
		return dao.getUpUser(userId);
	}

	@Override
	public List<RelativeUser> findByUIdAndJobUId(Long userId, Long reJobId,
			Short isSuper) {
		return dao.findByUserIdAndJobUId(userId, reJobId, isSuper);
	}
	
	/**
	 * 取得某个用户的上级(如：启动项目的主管的上级为自己。)
	 * add by lu 2012.05.31
	 */
	public List<Long> getReJobSameUserId(Long userId, Long reJobId){
		return dao.getReJobSameUserId(userId, reJobId);
	}
	@Override
	public List<RelativeUser> findSubordinate(Long userId) {
		return dao.findSubordinate(userId);
	}

	@Override
	public List<Long> findLower(String userId) {
		return dao.findLower(userId);
	}
}