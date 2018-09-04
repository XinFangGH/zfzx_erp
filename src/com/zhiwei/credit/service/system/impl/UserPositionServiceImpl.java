package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.UserPositionDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.UserPosition;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.UserOrgService;
import com.zhiwei.credit.service.system.UserPositionService;

public class UserPositionServiceImpl extends BaseServiceImpl<UserPosition> implements UserPositionService{
	@SuppressWarnings("unused")
	private UserPositionDao dao;
	@Resource
	private AppUserService appUserService;
	@Resource
	private UserOrgService userOrgService;
	
	public UserPositionServiceImpl(UserPositionDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 查找某一用户的所有职位
	 * @param userId
	 * @return
	 */
	public List<UserPosition> getByUserPos(Long userId){
		return dao.getByUserPos(userId);
	}

	/**
	 * 取得某个岗位的所有的人员
	 * 
	 * @param posId
	 * @return
	 */
//	public List<Long> getUserIdsByPosId(Long posId) {
//		return dao.getUserIdsByPosId(posId);
//	}
	public List<Long> getUserIdsByPosId(Long posId) {
		List<UserPosition> list= dao.getByPosId(posId);
		List<Long> userIds=new ArrayList<Long>();
		for(UserPosition up:list){
			userIds.add(up.getAppUser().getUserId());
		}
		return userIds;
	}
	
	/**
	 * 删除UserPosition对象
	 * @param userId
	 * @param posId
	 * @return
	 */
	public void delCascade(Long userId, Long posId){
		dao.delCascade(userId,posId);
	}
	

	/**
	 * 获取某个用户所有部门拥有某个岗位的员工列表 
	 * @param userId 指定的用户
	 * @param posId  指定的岗位
	 * @return
	 */
	public Set<AppUser> getSameDepUsersByUserIdPosId(Long userId,Long posId){
		Set<AppUser> users=new HashSet<AppUser>();
		AppUser curUser=appUserService.get(userId);
		//获取该部门所在主部门
		if(curUser.getDepartment().getDepId()!=null){//获取至当前部门
			//取得该岗位的所有人员
			List<AppUser> list=dao.getUsersByPosId(posId);
			HashSet<AppUser> tempSet=new HashSet<AppUser>();
			tempSet.addAll(list);
			//取得该部门的所有人员
			List<AppUser> depUsers=userOrgService.getUsersByOrgId(curUser.getDepartment().getDepId());
			
			for(AppUser us:depUsers){
				if(tempSet.contains(us)){
					users.add(us);
				}
			}
		}else{//若当前没有获取到主部门,即把该岗位对应的所有的人员获取出来
			List<AppUser> list=dao.getUsersByPosId(posId);
			users.addAll(list);
		}
		
		return users;
	}
	
}