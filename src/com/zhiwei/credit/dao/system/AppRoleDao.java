package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 用户
 * @author 
 *
 */
public interface AppRoleDao extends BaseDao<AppRole>{
	public AppRole getByRoleName(String roleName);
	
	/**
	 * 获取安全认证的数据源
	 * @return
	 */
	public HashMap<String,Set<String>>getSecurityDataSource();
	
	public List<AppRole> getListByRoleType(String roleName,String roleDesc,int start,int limit);
	
	public Long getCount(String roleName,String roleDesc);
	
	public List<AppRole> getListByOrgId(long orgId,String roleName,String roleDesc,int start,int limit);
	
	public Long getCountByOrgId(long orgId,String roleName,String roleDesc);
	public AppRole getCountByOrgId(long orgId,String roleName);
	
	public String getControlCompanyId(AppUser appUser);
	
	public List<AppRole> getListForOne(String roleName,String roleDesc,int start,int limit);
	
	public Long getCountForOne(String roleName,String roleDesc);
	
	public List<AppRole> getRoleListByOrgId(long orgId);
	
	public List<AppRole> getListByRoleType(String roleType);
	
	public boolean isOnlyHaveControlRole(AppUser appUser);
}
