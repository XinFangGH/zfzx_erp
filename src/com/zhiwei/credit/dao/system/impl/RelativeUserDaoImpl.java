package com.zhiwei.credit.dao.system.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.RelativeUserDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.RelativeUser;

/**
 * @description 相对岗位人员管理
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-13AM
 */
@SuppressWarnings("unchecked")
public class RelativeUserDaoImpl extends BaseDaoImpl<RelativeUser> implements
		RelativeUserDao {

	public RelativeUserDaoImpl() {
		super(RelativeUser.class);
	}

	/**
	 * 根据userId和jobUserId查询对应数据的总条数，返回:对应的总数据条数
	 */
	@Override
	public AppUser judge(Long userId, Long jobUserId) {
		String hql = "select r from RelativeUser r where r.appUser.userId = ? and r.jobUser.userId = ? ";
		Object[] paramList = { userId, jobUserId };
		List<RelativeUser> list = findByHql(hql, paramList);
		logger.debug(hql);
		return (list != null && list.size() > 0) ? list.get(0).getJobUser()
				: null;
	}

	/**
	 * 返回某个用户相对岗位的所有用户（如他主管的所有人）
	 * 
	 * @param userId
	 * @param reJobId
	 * @return
	 */
	public List<AppUser> findByUserIdReJobId(Long userId, Long reJobId) {
		String hql = "select ru.jobUser from RelativeUser ru where ru.appUser.userId=? and ru.relativeJob.reJobId=? ";
		Object result = findByHql(hql, new Object[] { userId, reJobId });
		return (List<AppUser>) result;
	}
	
	@Override
	public List<RelativeUser> findByUserIdAndJobUId(Long userId, Long jobUserId, Short isSuper) {
		String hql = "select r from RelativeUser r where r.appUser.userId = ? and r.jobUser.userId = ? and r.isSuper = ? ";
		Object[] paramList = {userId, jobUserId, isSuper};
		return findByHql(hql, paramList);
	}

	@Override
	public List<RelativeUser> list(Long appUserId, Long reJobId, PagingBean pb) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"select r from RelativeUser r where 1=1 ");
		if (appUserId != null && appUserId > 0) {
			hql.append("and r.appUser.userId = ? "); // set of this userId
			paramList.add(appUserId);
		}
		if (reJobId != null && reJobId > 0) { // set of this reJobId
			hql.append("and r.relativeJob.reJobId = ? ");
			paramList.add(reJobId);
		}
		logger.debug("自定义：RelativeUserDaoImpl:" + hql.toString());
		return (ArrayList<RelativeUser>) findByHql(hql.toString(), paramList
				.toArray(), pb);
	}

	/**
	 * 取得某个用户的相对岗位人员
	 * 
	 * @param userId
	 *            用户ID
	 * @param reJobId
	 *            相对岗位ID
	 * @return
	 */
	public List<Long> getReJobUserIds(Long userId, Long reJobId) {
		String hql = "select jobUser.userId from RelativeUser ru where ru.appUser.userId=? and relativeJob.reJobId=? ";
		List list = findByHql(hql, new Object[] { userId, reJobId });
		return list;
	}
	
	/**
	 * 取得某个用户的上级(如：启动项目的主管的上级为自己。)
	 * add by lu 2012.05.31
	 */
	public List<Long> getReJobSameUserId(Long userId, Long reJobId){
		String hql = "select jobUser.userId from RelativeUser ru where ru.appUser.userId=? and jobUser.userId=? and relativeJob.reJobId=?";
		List list = findByHql(hql, new Object[] { userId,userId, reJobId });
		return list;
	}
	
	/**
	 * 取得某个用户的下级
	 */
	@Override
	public List<Long> findLower(String userId){
		String hql = "select jobUser.userId from RelativeUser ru where ru.appUser.userId=? and ru.isSuper=0";
		List list = findByHql(hql, new Object[] {Long.valueOf(userId)});
		return list;
	}

	/**
	 * 取得某个用户的上级
	 */
	public Set<AppUser> getUpUser(Long userId){
		//分两种情况 
		//1:自己添加上级 
		String hql1 = "select  ru.jobUser from RelativeUser ru where ru.appUser.userId =? and ru.isSuper =?";
		List list1 = findByHql(hql1, new Object[]{userId,RelativeUser.SUPER_FLAG_TRUE});
		
		//2:别人把自己加为下级
		String hql2 = "select  ru.appUser from RelativeUser ru where  ru.jobUser.userId = ? and ru.isSuper = ?";
		List list2 = findByHql(hql2, new Object[]{userId,RelativeUser.SUPER_FLAG_FALSE});
		
		Set<AppUser> result = new HashSet<AppUser>();
		result.addAll(list1);
		result.addAll(list2);
		
		return result;
	}

	@Override
	public List<RelativeUser> findSubordinate(Long userId) {
		String hql = "from RelativeUser as r where r.appUser.userId = ? and r.isSuper = 0";
		return this.findByHql(hql, new Object[]{userId});
	}
	
}