package com.zhiwei.credit.dao.system;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;
import java.util.Set;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.RelativeUser;

/**
 * @description 相对岗位人员管理
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-13AM
 */
public interface RelativeUserDao extends BaseDao<RelativeUser> {

	/**
	 * @description 根据userId和jobUserId判断对应的数据是否存在,返回：对应数据的总条数
	 * @param userId
	 *            userId
	 * @param jobUserId
	 *            jobUserId
	 * @return AppUser 当前对象
	 */
	AppUser judge(Long userId, Long jobUserId);

	/**
	 * 返回某个用户相对岗位的所有用户（如他主管的所有人）
	 * 
	 * @param userId
	 *            用户ID
	 * @param reJobId
	 *            相对岗位ID
	 * @return
	 */
	List<AppUser> findByUserIdReJobId(Long userId, Long reJobId);

	/**
	 * 根据userId,jobUserId和级别判断对应的数据
	 * @param userId
	 * @param jobUserId
	 * @param isSuper
	 * @return
	 */
	List<RelativeUser> findByUserIdAndJobUId(Long userId, Long jobUserId, Short isSuper);
	
	/**
	 * @description 筛选数据
	 * @param appUserId
	 *            appUser对应的userId属性值
	 * @param reJobId
	 *            对应相对岗位编号
	 * @param pb
	 *            PagingBean对象
	 * @return List<RelativeUser>
	 */
	List<RelativeUser> list(Long appUserId, Long reJobId, PagingBean pb);
	
	/**
	 * 取得某个用户的相对岗位人员
	 * @param userId 用户ID
	 * @param reJobId  相对岗位ID
	 * @return
	 */
	public List<Long> getReJobUserIds(Long userId,Long reJobId);
	
	/**
	 * 取得某个用户的上级
	 */
	public Set<AppUser> getUpUser(Long userId);
	
	/**
	 * 取得某个用户的上级(如：启动项目的主管的上级为自己。)
	 * add by lu 2012.05.31
	 */
	public List<Long> getReJobSameUserId(Long userId, Long reJobId);
	
	List<RelativeUser> findSubordinate(Long userId);

	List<Long> findLower(String userId);
}