package com.zhiwei.core.jbpm;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.AppUserService;


public class UserSession implements IdentitySession{
	
	private AppUserService appUserService=(AppUserService)AppUtil.getBean("appUserService");
	
	private AppRoleService appRoleService=(AppRoleService)AppUtil.getBean("appRoleService");
	

	@Override
	public String createGroup(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMembership(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createUser(String arg0, String arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGroup(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMembership(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String arg0) {
		// TODO Auto-generated method stub
		
	}

	//---------------------------------methods above are not need overwrite--------------------------------------------
	
	@Override
	public Group findGroupById(String groupId) {
		return appRoleService.get(new Long(groupId));
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		AppUser user=appUserService.get(new Long(userId));
		List<Group> list=new ArrayList<Group>();
		Iterator it=user.getRoles().iterator();
		while(it.hasNext()){
			list.add((Group)it.next());
		}
		return list;
	}

	@Override
	public List<Group> findGroupsByUserAndGroupType(String userId, String groupType) {
		return findGroupsByUser(userId);
	}

	@Override
	public User findUserById(String userId) {
		return appUserService.get(new Long(userId));
	}

	@Override
	public List<User> findUsers() {
		List<AppUser> userList=appUserService.getAll();
		List<User> list=new ArrayList<User>();
		for(User user:userList){
			list.add((User)user);
		}
		return list;
	}

	@Override
	public List<User> findUsersByGroup(String groupId) {
		List<AppUser>userList=appUserService.findByRoleId(new Long(groupId));
		List<User> list=new ArrayList<User>();
		for(User user:userList){
			list.add((User)user);
		}
		return list;
	}

	@Override
	public List<User> findUsersById(String... userIds) {
		// TODO Auto-generated method stub
		return null;
	}

}
