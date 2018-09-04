package com.zhiwei.credit.service.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.RelativeUser;
import com.zhiwei.credit.model.system.UserOrg;

public interface AppUserService extends BaseService<AppUser> {

	public AppUser findByUserName(String username);
	
	public AppUser findByUserNameAndConmpany(String username,String path);
	
	
	public List<AppUser> findByDepartment(String path, PagingBean pb);
	
	/**
	 * @description 根据userIds查询不对应的数据
	 * @param path
	 *            路径
	 * @param userIds
	 *            userId组成的字符串
	 * @param pb
	 *            PagingBean
	 * @return List<AppUser>
	 */
	List<AppUser> findByDepartment(String path, String userIds, PagingBean pb);

	public List<AppUser> findByRole(Long roleId, PagingBean pb);

	public List<AppUser> findByRoleId(Long roleId);

	/**
	 * 根据部门查找不是上属的用户
	 */
	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb);

	/**
	 * 根据角色查找不是上属的用户
	 */
	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb);

	public List<AppUser> findByDepId(Long depId);

	/**
	 * 动态密码接口服务方法
	 * 
	 * @param input
	 *            参数数组
	 * @param function
	 *            接口名称
	 * @return ok=成功 fail=失败
	 */
	public String initDynamicPwd(HashMap<String, String> input, String function);

	/**
	 * 按角色ID查找用户
	 * 
	 * @param roleIds
	 *            角色Id，通过','分割
	 * @return
	 */
	public List<AppUser> findUsersByRoleIds(String roleIds);

	/**
	 * @description 根据当前用户岗位取得下属岗位的用户
	 * @return List<AppUser>
	 */
	public List<AppUser> findRelativeUsersByUserId(Long userId);
	

	/**
	 * 按角色取得用户列表
	 * @param roleId
	 * @return
	 */
	public List<AppUser> getUsersByRoleId(Long roleId);
	
	/**
	 * 返回当前用户的信息，以Json格式返回
	 * @return
	 */
	public String getCurUserInfo();
	
	/**
	 * 按部门取得用户列表
	 * @param orgPath
	 * @return
	 */
	public List<AppUser> getDepUsers(String orgPath,PagingBean pb, Map map);
	
	/**
	 * 取得相对岗位用户列表
	 * @param reJobId
	 * @return
	 */
	public List<AppUser> getReLevelUser(String reJobId,Long startUserId);
	
	/**
	 * 取得组织主要负责人
	 * @param userOrg
	 * @return
	 */
	public List<AppUser> getChargeOrgUsers(Set<UserOrg> userOrgs);
	
	/**
	 * 判断用户是否是超级管理员，或者管理员，或者具有所有权限
	 * @param userId 用户id
	 * @return 超级管理员，或者管理员，或者具有所有权限true
	 */
	Boolean isSuperMan(Long userId);
	
	public Set<AppUser> getAppUserByStr(String[] str);
	
	/**
	 * 得到当前用户或当前用户及其所有下属用户的ID字符串(各ID间以'，'号分隔开)
	 * @return
	 */
	public String getUsersStr();
	
	
    public List<AppUser> getOrganizationUsers(Organization o);
    
    
    public AppUser findByUserNameAndOrganization(String userName,List<Organization> os); 
    
    public List<AppUser> getUserList(Long orgId,String orgType,String userName,int start,int limit);
    
    public long getUserCount(Long orgId,String orgType,String userName);
    
    public List<AppUser> getAllUserList(Long orgId,String orgType,String userName,int start,int limit);
    
    public long getAllUserCount(Long orgId,String orgType,String userName);
    public List<AppUser> getDepartmentUsers(Long orgId,PagingBean pb);
    
    public List<AppUser> getUsersByCompany(List<Organization> os,PagingBean pb, Map map);
    
    /**
     * 根据传入的编号查询该编号是否已经存在
     * @param userNumber  用户的编号
     * @return true 表示不存在
     * @return false 表示存在
     */
    public boolean existUserNumber(String userNumber);
    public boolean existUserNumber(String userNumber,long userId);
	/**
	 * 根据用户id获取用户信息、
	 * @param 格式为 id,id,id,id
	 * 
	 * */
	public List<AppUser> findByUserId(String userIds);
	
	public List<AppUser> diguiRelativeUser(List<RelativeUser> findSubordinate,
			List<AppUser> appUserList);
	
	/**
	 * 根据流程启动人ＩＤ和角色ＩＤ返回　同一个门店的拥有这个角色的人
	 */
	public List<AppUser> separateByRoleId(Long flowStartUserId,Set<Long> roleSet);
	
	
	/**
	 * 根据角色ID  set返回所有User
	 */
	public List<AppUser> findByRoleSet(Set<Long> roleSet);

	/**
	 * 查询有推荐码的ERP账号
	 * @param pageBean
	 * @return
	 */
	public void getUserList(PageBean<AppUser> pageBean);

	/**
	 * 查询线下业绩
	 * @param pageBean
	 */
	public void userPerformanceList(PageBean<AppUser> pageBean);

	/**
	 * 查询系统用户列表
	 * 非禁用并且未生成推荐码的用户
	 * @param pageBean
	 */
	public void findUserList(PageBean<AppUser> pageBean);
	
	public String findMaxMember();

	/**
	 * 根据当前登录用户Id查询所有下级信息
	 * @param idList
	 * @param pb
	 * @param request 
	 * @return
	 */
	public List<AppUser> findLower(List<Long> idList, PagingBean pb, HttpServletRequest request);
	/**
	 * 根据角色ID  set返回所有User
	 */
	public List<AppUser> findByUserNameAndAt(String userName);
	
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public String getCurUserInfoOfmobile();

	List<AppUser> getByDepId(List<Organization> byOrgSupId);

    List<AppUser> getAllById(List<AppUser> list);

	AppUser getAllLowerMsg(AppUser user,Set<Long> allLowerId, String startDate, String endDate);

    String getRelativeJobName(String upId, Long lowId);
}
