package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.UserOrg;

public interface UserOrgService extends BaseService<UserOrg>{
	/**
	 * 取某一用户所有的部门
	 * @param userId
	 * @return
	 */
	public List<UserOrg> getDepOrgsByUserId(Long userId);
	
	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delUserOrg(Long orgId);
	/**
	 * 
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public List<UserOrg> getByUserIdOrgId(Long userId,Long orgId);
	
	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delCascade(Long userId, Long orgId);
	
	/**
	 * 获取用户及部门Id
	 * @param orgId
	 * @return
	 */
	public List<AppUser> getUsersByOrgId(Long orgId);
	
	public List<UserOrg> listByUserId(Long userId);
}


