package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.UserOrgDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.UserOrg;
import com.zhiwei.credit.service.system.UserOrgService;

public class UserOrgServiceImpl extends BaseServiceImpl<UserOrg> implements UserOrgService{
	@SuppressWarnings("unused")
	private UserOrgDao dao;
	
	public UserOrgServiceImpl(UserOrgDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取某一用户所有的部门
	 * @param userId
	 * @return
	 */
	public List<UserOrg> getDepOrgsByUserId(Long userId){
		return dao.getDepOrgsByUserId(userId);
	}

	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delUserOrg(Long orgId) {
		dao.delUserOrg(orgId);
	}
	
	public List<UserOrg> getByUserIdOrgId(Long userId,Long orgId){
		return dao.getByUserIdOrgId(userId,orgId);
	}

	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delCascade(Long userId, Long orgId) {
		dao.delCascade(userId,orgId);
	}

	/**
	 * 获取用户及部门Id
	 * @param orgId
	 * @return
	 */
	public List<AppUser> getUsersByOrgId(Long orgId) {
		return dao.getUsersByOrgId(orgId);
	}

	@Override
	public List<UserOrg> listByUserId(Long userId) {
		
		return dao.listByUserId(userId);
	}

}