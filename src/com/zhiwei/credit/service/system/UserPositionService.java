package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;
import java.util.Set;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.UserPosition;

public interface UserPositionService extends BaseService<UserPosition>{
	/**
	 * 查找某一用户的所有职位
	 * @param userId
	 * @return
	 */
	public List<UserPosition> getByUserPos(Long userId);
	
	/**
	 * 取得某个岗位的所有的人员
	 * 
	 * @param posId
	 * @return
	 */
	public List<Long> getUserIdsByPosId(Long posId);
	
	/**
	 * 删除UserPosition对象
	 * @param userId
	 * @param posId
	 * @return
	 */
	public void delCascade(Long userId, Long posId);
	
	/**
	 * 获取某个用户所有部门拥有某个岗位的员工列表 
	 * @param userId 指定的用户
	 * @param posId  指定的岗位
	 * @return
	 */
	public Set<AppUser> getSameDepUsersByUserIdPosId(Long userId,Long posId);
}


