package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.system.AppRoleDao;
import com.zhiwei.credit.model.system.AppFunction;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FunUrl;

public class AppRoleDaoImpl extends BaseDaoImpl<AppRole> implements AppRoleDao{

	public AppRoleDaoImpl() {
		super(AppRole.class);
	}
	
	public AppRole getByRoleName(String roleName){
		String hql="from AppRole ar where ar.roleName=?";
		return (AppRole)findUnique(hql, new Object[]{roleName});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.system.AppRoleDao#getSecurityDataSource()
	 */
	public HashMap<String,Set<String>> getSecurityDataSource() {
		final HashMap<String,Set<String>> source=new HashMap<String, Set<String>>();
		
		//TODO status must be handler
		
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
					String hql="from AppRole";
					Query query=session.createQuery(hql);
					List<AppRole> roleList=query.list();
					
					for(AppRole role:roleList){
						TreeSet<String> urlSet=new TreeSet<String>();
						//取得某个角色的所有URL,TODO
						Iterator<AppFunction> functions=role.getFunctions().iterator();
						//Iterator<AppFunction> functions=role.getControlFunctions().iterator();
						while(functions.hasNext()){
							AppFunction fun=functions.next();
							Iterator<FunUrl> urlIt=fun.getFunUrls().iterator();
							while(urlIt.hasNext()){
								urlSet.add(urlIt.next().getUrlPath());
							}
						}
						
						source.put(role.getName(), urlSet);
					}
					return null;
			}
		});
		return source;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRole> getListByRoleType(String roleName,String roleDesc,int start,int limit) {
		String hql="from AppRole ar where ar.roleType='control' and ar.roleName like ? and ar.roleDesc like ?";
		return getSession().createQuery(hql).setParameter(0, "%"+roleName+"%").setParameter(1, "%"+roleDesc+"%").setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public Long getCount(String roleName,String roleDesc) {
		String hql="select count(*) from AppRole ar where ar.roleType='control' and ar.roleName like ? and ar.roleDesc like ?";
		long totalCount=0;
		List list=getSession().createQuery(hql).setParameter(0, "%"+roleName+"%").setParameter(1, "%"+roleDesc+"%").list();
		if(null!=list && list.size()>0){
			totalCount=(Long)list.get(0);
		}
		return totalCount;
	}

	@Override
	public Long getCountByOrgId(long orgId,String roleName,String roleDesc) {
		String hql="select count(*) from AppRole ar where ar.orgId=? and ar.roleType='business'";
		if(null!=roleName){
			hql=hql+" and ar.roleName like '%"+roleName+"%'";
		}
		if(null!=roleDesc){
			hql=hql+" and ar.roleDesc like '%"+roleDesc+"%'";
		}
		long count=0;
		List list=getSession().createQuery(hql).setParameter(0, orgId).list();
		if(null!=list && list.size()>0){
			count=(Long) list.get(0);
		}
		return count;
	}
	@Override
	public AppRole getCountByOrgId(long orgId,String roleName) {
		if(orgId==0){
			String hql="from AppRole ar where ar.roleType='control' and ar.roleName=? ";
			return (AppRole)findUnique(hql, new Object[]{roleName});
		}else{
		   String hql="from AppRole ar where ar.orgId=? and ar.roleName=? ";
		   return (AppRole)findUnique(hql, new Object[]{orgId,roleName});
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRole> getListByOrgId(long orgId,String roleName,String roleDesc, int start, int limit) {
		String hql="from AppRole ar where ar.orgId=? and ar.roleType='business'";
		if(null!=roleName){
			hql=hql+" and ar.roleName like '%"+roleName+"%'";
		}
		if(null!=roleDesc){
			hql=hql+" and ar.roleDesc like '%"+roleDesc+"%'";
		}
		if(start==0 && limit==0){
			hql="from AppRole ar where ar.orgId=?";
			return getSession().createQuery(hql).setParameter(0, orgId).list();
		}
		return getSession().createQuery(hql).setParameter(0, orgId).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public String getControlCompanyId(AppUser appUser) {
		
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		if(!flag){
			return "";
		}
		
		String hql="select controlCompanyId  from AppRole where roleId IN (:alist) and roleType='control'";
		Set<AppRole> roles=appUser.getRoles();
		List<Long> ids=new ArrayList<Long>();
		for(AppRole a:roles){
			ids.add(a.getRoleId());
		}
		List list=getSession().createQuery(hql).setParameterList("alist", ids).list();
		String returnIds="";
		if(null!=list && list.size()>0){
			returnIds=(String)list.get(0);
			return returnIds;
		}
		return returnIds;
	}

	@Override
	public Long getCountForOne(String roleName,String roleDesc) {
		String strs=ContextUtil.getBranchIdsStr();
		String hql="select count(*) from AppRole as role where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and role.orgId in ("+strs+")";
		}
		hql=hql+" and role.roleName like ? and role.roleDesc like ?";
		List list=getSession().createQuery(hql).setParameter(0, "%"+roleName+"%").setParameter(1, "%"+roleDesc+"%").list();
		long count=0;
		if(null!=list && list.size()>0){
			count=(Long) list.get(0);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRole> getListForOne(String roleName,String roleDesc,int start, int limit) {
		Long cId=ContextUtil.getLoginCompanyId();
		String hql="from AppRole as role where 1=1";
		if(null!=cId && !"".equals(cId)){
			hql=hql+" and role.orgId in ("+cId+")";
		}
		hql=hql+" and role.roleName like ? and role.roleDesc like ?";
		return getSession().createQuery(hql).setParameter(0, "%"+roleName+"%").setParameter(1, "%"+roleDesc+"%").setFirstResult(start).setMaxResults(limit).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRole> getRoleListByOrgId(long orgId) {
		String hql="from AppRole as role where role.orgId=?";
		return getSession().createQuery(hql).setParameter(0, orgId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRole> getListByRoleType(String roleType) {
		String hql="from AppRole as role where role.roleType=?";
		return getSession().createQuery(hql).setParameter(0, roleType).list();
	}

	@Override
	public boolean isOnlyHaveControlRole(AppUser appUser) {
		
		String sql="from AppRole as role where role.roleType!='control' and role.roleId in (:alist)";
		List<Long> ids=new ArrayList<Long>();
		for(AppRole a:appUser.getRoles()){
			ids.add(a.getRoleId());
		}
		List list=getSession().createQuery(sql).setParameterList("alist", ids).list();
		if(list.size()<=0){
			return true;
		}
		return false;
	}

}
