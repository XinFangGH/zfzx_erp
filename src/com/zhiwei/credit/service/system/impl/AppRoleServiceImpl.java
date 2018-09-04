package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.AppRoleDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.AppRoleService;
@WebService
public class AppRoleServiceImpl extends BaseServiceImpl<AppRole> implements AppRoleService{
	private AppRoleDao dao;
	
	public AppRoleServiceImpl(AppRoleDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public AppRole getByRoleName(String roleName){
		return dao.getByRoleName(roleName);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.system.AppRoleService#getSecurityDataSource()
	 */
	public HashMap<String,Set<String>>getSecurityDataSource(){
		return dao.getSecurityDataSource();
	}
	
	
	public AppRole getById(Long roleId){
		return get(roleId);
	}

	@Override
	public List<AppRole> getListByRoleType(String roleName,String roleDesc,int start,int limit) {
		
		return dao.getListByRoleType(roleName,roleDesc,start,limit);
	}

	@Override
	public Long getCount(String roleName,String roleDesc) {
		
		return dao.getCount(roleName,roleDesc);
	}
	
	@Override
	
	public Long getCountByOrgId(long orgId,String roleName,String roleDesc) {
		
		return dao.getCountByOrgId(orgId,roleName,roleDesc);
	}
	@Override
	public AppRole getCountByOrgId(long orgId,String roleName) {
		
		return dao.getCountByOrgId(orgId,roleName);
	}
	@Override
	public List<AppRole> getListByOrgId(long orgId,String roleName,String roleDesc, int start, int limit) {
		
		return dao.getListByOrgId(orgId,roleName,roleDesc, start, limit);
	}

	@Override
	public String getControlCompanyId(AppUser appUser) {
		return dao.getControlCompanyId(appUser);
	}

	@Override
	public Long getCountForOne(String roleName, String roleDesc) {
		
		return dao.getCountForOne(roleName, roleDesc);
	}

	@Override
	public List<AppRole> getListForOne(String roleName, String roleDesc,
			int start, int limit) {
		
		return dao.getListForOne(roleName, roleDesc, start, limit);
	}

	@Override
	public List<AppRole> getRoleListByOrgId(long orgId) {
		
		return dao.getRoleListByOrgId(orgId);
	}

	@Override
	public List<AppRole> getListByRoleType(String roleType) {
		
		return dao.getListByRoleType(roleType);
	}
	@Override
	public boolean isOnlyHaveControlRole(AppUser appUser) {
		// TODO Auto-generated method stub
		return dao.isOnlyHaveControlRole(appUser);
	}

}
