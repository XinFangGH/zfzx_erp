package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.UserOrgDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.UserOrg;

@SuppressWarnings("unchecked")
public class UserOrgDaoImpl extends BaseDaoImpl<UserOrg> implements UserOrgDao{

	public UserOrgDaoImpl() {
		super(UserOrg.class);
	}
	/**
	 * 取某一用户所有的部门
	 * @param userId
	 * @return
	 */
	public List<UserOrg> getDepOrgsByUserId(Long userId){
		String hql="from UserOrg uo where uo.appUser.userId=? and (uo.organization.orgType=? or uo.organization.orgType=?)";
		return findByHql(hql,new Object[]{userId,Organization.ORG_TYPE_COMPANY,Organization.ORG_TYPE_DEPARTMENT});
	}
	
	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delUserOrg(Long orgId) {
		String hql = "delete UserOrg uo where uo.organization.orgId=?";
		getSession().createQuery(hql);
	}
	
	public List<UserOrg> getByUserIdOrgId(Long userId,Long orgId){
		String hql="from UserOrg uo where uo.appUser.userId=? and uo.organization.orgId=?";
		return(List<UserOrg>) findByHql(hql,new Object[]{userId,orgId});
	}
	
	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delCascade(Long userId, Long orgId){
		String hql = "delete UserOrg uo where uo.appUser.userId = "+userId;
		if(orgId!=0){
			hql+=" and uo.organization.orgId="+ orgId;
		}
		getSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * 获取用户及部门Id
	 * @param orgId
	 * @return
	 */
	public List<AppUser> getUsersByOrgId(Long orgId){
		String hql="select distinct uo.appUser from UserOrg uo where uo.organization.orgId=?";
		return(List)findByHql(hql,new Object[]{orgId});
	}
	@Override
	public List<UserOrg> listByUserId(Long userId) {
		String hql="from UserOrg as uo where uo.appUser.userId=?";
		return this.findByHql(hql, new Object[]{userId});
	}

}