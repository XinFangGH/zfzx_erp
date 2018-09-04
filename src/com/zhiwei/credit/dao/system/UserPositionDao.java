package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.UserPosition;

/**
 * 
 * @author 
 *
 */
public interface UserPositionDao extends BaseDao<UserPosition>{
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
//	public List<Long> getUserIdsByPosId(Long posId);
	
	/**
	 * 删除UserPosition对象
	 * @param userId
	 * @param posId
	 * @return
	 */
	public void delCascade(Long userId, Long posId);
	
	public List getByPosId(Long posId);
	
	public List<AppUser> getUsersByPosId(Long posId);
}