package com.zhiwei.credit.dao.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.Organization;

/**
 * @description 用户操作
 * @class AppUserDao
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-27AM
 */
public interface AppUserDao extends BaseDao<AppUser> {
	public AppUser findByUserName(String username);

	
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

	public List<AppUser> findByDepartment(String path);

	public List<AppUser> findByDepartment(Department department);

	public List<AppUser> findByRole(Long roleId);

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

	/**
	 * 查找某个部门下的所有用户
	 * 
	 * @param depId
	 * @return
	 */
	public List<AppUser> findByDepId(Long depId);

	/**
	 * 查找某组角色列表下所有的用户
	 * 
	 * @param roleIds
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
	 * 
	 * @param roleId
	 * @return
	 */
	public List<AppUser> getUsersByRoleId(Long roleId);

	/**
	 * 按部门取得用户列表
	 * 
	 * @param orgPath
	 * @return
	 */
	public List<AppUser> getDepUsers(String orgPath, PagingBean pb, Map map);

	/**
	 * 取得想对岗位用户列表
	 * 
	 * @param reJobId
	 * @return
	 */
	public List<AppUser> getReLevelUser(String reJobId,Long startUserId);

	/**
	 * 取得组织主要负责人
	 * 
	 * @param userOrg
	 * @return
	 */
	public List<AppUser> getChargeOrgUsers(Set userOrgs);

	/**
	 * 判断用户是否为超级管理员，或者管理员，或者有所有权限
	 * 
	 * @param userId
	 *            用户id
	 * @return 超级管理员,管理员,或者所有权限true
	 */
	Boolean isSuperMan(Long userId);
	
	public Set<AppUser> getAppUserByStr(String[] str);
	
	
	public AppUser findByUserNameAndConmpany(String userName,String path);
	
	
    public List<AppUser> getOrganizationUsers(Organization o);
     
     
    /**
     * 根据用户名获取集团用户
     * @param userName
     * @return
     */
    public AppUser findByUserNameAndOrganization(String userName,List<Organization> os);
    
    public List<AppUser> getUserList(Long orgId,String orgType,String userName,int start,int limit);
    
    public long getUserCount(Long orgId,String orgType,String userName);
     
    public List<AppUser> getAllUserList(Long orgId,String orgType,String userName,int start,int limit);
    
    public long getAllUserCount(Long orgId,String orgType,String userName);
    
    public List<AppUser> getDepartmentUsers(Long orgId,PagingBean pb);
    
    /**
     * 首先查询总公司,或者分公司下的所有部门 包括二级部门
     * 然后分页
     * @param os 所有的部门列表
     * @param pb 分页
     * @param map 查询条件
     * @return
     */
    public List<AppUser> getUsersByCompany(List<Organization> os,PagingBean pb, Map map);
    
    /**
     *  根据用户编号查询该编号是否存在
     * @param userNumber 用户编号
     * @return
     */
    public boolean existUserNumber(String userNumber);
    public boolean existUserNumber(String userNumber,long userId);


	public void getUserList(PageBean<AppUser> pageBean);


	public void userPerformanceList(PageBean<AppUser> pageBean);


	public void findUserList(PageBean<AppUser> pageBean);
	public String findMaxMember();


	public List<AppUser> findLower(List<Long> idList, PagingBean pb,HttpServletRequest request);
	
	public List<AppUser> findByUserNameAndAt(String userName);

	List<AppUser>  getByDepId(Long orgId);

    AppUser getAllById(Long userId);

    BigDecimal getLowerMoney(Long id, String startDate, String endDate);

	Integer getAllInvent(Long id, String startDate, String endDate);

	Integer allMoneyInvent(Long id, String startDate, String endDate);

    String getRelativeJobName(String upId, Long lowId);
}
