package com.zhiwei.credit.dao.system.impl;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.system.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.AppUserDao;

/**
 * @description 用户信息管理
 * @class AppUserDaoImpl
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-27AM
 */
@SuppressWarnings("unchecked")
public class AppUserDaoImpl extends BaseDaoImpl<AppUser> implements AppUserDao,
		UserDetailsService {

	public AppUserDaoImpl() {
		super(AppUser.class);
	}

	@Override
	public AppUser findByUserName(String username) {
		String hql = "from AppUser au where au.username=?";
		Object[] params = {username};
		List<AppUser> list = findByHql(hql, params);
		AppUser user = null;
		if (list.size() != 0) {
			user = list.get(0);
			
		}
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
		AppUser user = (AppUser) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						String hql = "from AppUser ap where ap.username=? and ap.delFlag = ?";
						Query query = session.createQuery(hql);
						query.setString(0, username);
						query.setShort(1, Constants.FLAG_UNDELETED);
						AppUser user = null;
						user = (AppUser) query.uniqueResult();

						if (user != null) {
							Hibernate.initialize(user.getRoles());
							Hibernate.initialize(user.getDepartment());
							//user.init();
						}
						return user;
					}
		});
		//初始化其对应的公司
		if(user.getDepartment()!=null){
			String path=user.getDepartment().getPath();
			if(path!=null){
				String[]ids=path.split("[.]");
				for(String id : ids){
					if(!"0".equals(id) && StringUtils.isNotEmpty(id)){
						Organization org=(Organization)findUnique("from Organization org where org.orgId=?", new Object[]{new Long(id)});
						if(Organization.ORG_TYPE_COMPANY.equals(org.getOrgType())){
							user.setCompany(org);
							break;
						}
					}
				}
			}
		}
		
		//若用户当前不属于任何公司，则其为公共部门下的。
		if(user.getCompany()==null){
			Organization org=(Organization)findUnique("from Organization org where org.orgId=?", new Object[]{new Long(Organization.PUBLIC_COMPANY_ID)});
			user.setCompany(org);
		}
		
		return user;
	}

	/**
	 * 根据部门PATH属性查找
	 */
	@Override
	public List findByDepartment(String path, PagingBean pb) {
		List list = new ArrayList();
		String hql = new String();
		if ("0.".equals(path)) {
			hql = "from AppUser vo2 where vo2.delFlag = ?";
			list.add(Constants.FLAG_UNDELETED);
		} else {
			//TODO
			hql="select distinct au from AppUser au where au.department.path like ? and au.delFlag=? ";
//			hql = "select DISTINCT vo2 from Department vo1,AppUser vo2,DepUsers vo3 where 1=1"
//					+ " and vo3.appUser=vo2"
//					+ " and vo3.department=vo1"
//					+ " and vo1.path like ? and vo2.delFlag = ? order by vo3.sn";
			list.add(path + "%");
			list.add(Constants.FLAG_UNDELETED);
		}
		return findByHql(hql, list.toArray(), pb);
	}

	@Override
	public List findByDepartment(Department department) {
		String hql = "select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department and vo1.path like ? and vo2.delFlag = ?";
		Object[] params = { department.getPath() + "%",
				Constants.FLAG_UNDELETED };
		return findByHql(hql, params);
	}

	@Override
	public List findByRole(Long roleId) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		return findByHql(hql, objs);
	}

	@Override
	public List findByRole(Long roleId, PagingBean pb) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		return findByHql(hql, objs, pb);
	}

	@Override
	public List<AppUser> findByDepartment(String path) {
		String hql = "select vo2 from Department vo1,AppUser vo2 where vo1.depId=vo2.depId and vo1.path like ? and vo2.delFlag =?";
		Object[] params = { path + "%", Constants.FLAG_UNDELETED };
		return findByHql(hql, params);
	}

	public List findByRoleId(Long roleId) {
		String hql = "select vo from AppUser vo join vo.roles as roles where roles.roleId=? and vo.delFlag =?";
		return findByHql(hql, new Object[] { roleId, Constants.FLAG_UNDELETED });
	}

	public List findByUserIds(Long... userIds) {
		String hql = "select vo from AppUser vo where vo.delFlag=? ";

		if (userIds == null || userIds.length == 0)
			return null;
		hql += " where vo.userId in (";
		int i = 0;
		for (@SuppressWarnings("unused") Long userId : userIds) {
			if (i++ > 0) {
				hql += ",";
			}
			hql += "?";
		}
		hql += " )";

		return findByHql(hql,
				new Object[] { Constants.FLAG_UNDELETED, userIds });
	}

	@Override
	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb) {
		String st = "";
		if (userIds.size() > 0) {
			Iterator<Long> it = userIds.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(it.next().toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			st = sb.toString();
		}

		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		if (path != null) {
			hql
					.append("select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department ");
			hql.append(" and vo1.path like ?");
			list.add(path + "%");
		} else {
			hql.append("from AppUser vo2 where 1=1 ");
		}
		if (st != "") {
			hql.append(" and vo2.userId not in (" + st + ")");
		}
		hql.append(" and vo2.delFlag = ?");
		list.add(Constants.FLAG_UNDELETED);
		return findByHql(hql.toString(), list.toArray(), pb);
	}

	@Override
	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb) {
		String st = "";
		if (userIds.size() > 0) {
			Iterator<Long> it = userIds.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(it.next().toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			st = sb.toString();
		}
		StringBuffer hql = new StringBuffer(
				"select vo from AppUser vo join vo.roles roles where roles.roleId=?");
		List list = new ArrayList();
		list.add(roleId);
		if (st != "") {
			hql.append(" and vo.userId not in (" + st + ")");
		}
		hql.append(" and vo.delFlag =?");
		list.add(Constants.FLAG_UNDELETED);
		return findByHql(hql.toString(), list.toArray(), pb);
	}

	@Override
	public List<AppUser> findByDepId(Long depId) {
		String hql = "from AppUser vo where vo.delFlag=0 and vo.department.depId=?";
		Object[] objs = { depId };
		return findByHql(hql, objs);
	}

	/**
	 * 查找某组角色列表下所有的用户
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<AppUser> findUsersByRoleIds(String roleIds) {
		if (StringUtils.isEmpty(roleIds)) {
			return new ArrayList();
		}
		String hql = "select distinct au from AppUser as au join au.roles as roles where roles.roleId in ("
				+ roleIds + ") and au.delFlag =?";

		return findByHql(hql, new Object[] { Constants.FLAG_UNDELETED });
	}

	/**
	 * @description 根据当前用户岗位取得下属岗位的用户
	 * @return List<AppUser>
	 */
	public List<AppUser> findRelativeUsersByUserId(Long userId) {
		// 按Position取下属
		StringBuffer sb = new StringBuffer("select up from UserPosition up where up.appUser.userId = ? ");
		Query query = getSession().createQuery(sb.toString());
		query.setLong(0, ContextUtil.getCurrentUserId());
		List<UserPosition> uplist = query.list();
		List<Position> plist = new ArrayList();
		for(UserPosition up:uplist){
			List<Position> tlist = (List)findByHql("select p from Position p where p.posId = "+up.getPosition().getPosId());
			Set<Position> mainPosSub = tlist.get(0)==null?null:tlist.get(0).getMainPositionSubs();
			Set<Position> subPosSub = tlist.get(0)==null?null:tlist.get(0).getSubPositionSubs();
			if(mainPosSub!=null&&subPosSub!=null){
				tlist.addAll(mainPosSub);
				tlist.addAll(subPosSub);
			}
			for(Position pos:tlist){
				if(!plist.contains(pos)){
					plist.add(pos);
				}
			}
		}
		
		String paths = "";
		if(plist.size()>0){
			for(Position pos:plist){
				if(paths.length()>0)
					paths += ",";
				paths += pos.getPath();
			}
		}
		
		// 取得路径对应posId
		sb = new StringBuffer("select p from Position p ");
		String[] pths = paths.split(",");
		for(int index=0;index<pths.length;index++){
			if(index==0){
				sb.append("where (p.path like '"+pths[0]+"%' and p.path <> '"+pths[0]+"') ");
			}else{
				sb.append("or (p.path like '"+pths[index]+"%' and p.path <> '"+pths[index]+"') ");
			}
		}
		
		// 得到对应用户
		String pathHql = sb.toString();
		sb = new StringBuffer("select distinct au from AppUser au,UserPosition up " +
				"where au.userId = up.appUser.userId " +
				"and up.position.posId in ("+pathHql+") " +
				"and au.delFlag = 0");
		List<AppUser> users=findByHql(sb.toString());
		
		// 按Relative_job取下属
		sb = new StringBuffer("select distinct au0 from AppUser au0 where au0.userId " +
				"in (select ru.jobUser.userId from AppUser au, RelativeUser ru " +
				"where au.userId = ? and au.delFlag = 0 and ru.isSuper=0" +//update by lisl 2012-06-26 isSuper=0表示找下属
				"and au.userId = ru.appUser.userId) ");
		users.addAll(findByHql(sb.toString(),new Object[]{userId}));
		return users;
	}

	@Override
	public List<AppUser> findByDepartment(String path, String userIds,
			PagingBean pb) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		if ("0.".equals(path)) {
			hql.append("from AppUser vo2 where vo2.delFlag = ? ");
			list.add(Constants.FLAG_UNDELETED);
		} else {
			hql.append("select distinct au from AppUser au where au.department.path like ? and au.delFlag=? ");
			list.add(path + "%");
			list.add(Constants.FLAG_UNDELETED);
		}
		//删除userIds中的数据
		if(userIds != null && !userIds.equals("")){
			hql.append("and vo2.userId in (?) ");
			list.add(userIds);
		}
		hql.append("order by vo3.sn "); //排序
		logger.debug("自定义AppUserDaoImpl : " + hql.toString());
		return findByHql(hql.toString(), list.toArray(), pb);
	}
	
	/**
	 * 按角色取得用户列表
	 * @param roleId
	 * @return
	 */
	public List<AppUser> getUsersByRoleId(Long roleId){
		String hql="from AppUser au join au.roles as role where role.roleId=?";
		return (List<AppUser>)findByHql(hql, new Object[]{roleId});
	}
	
	/**
	 * 按部门取得用户列表
	 * @param orgPath
	 * @return
	 */
	@Override
	public List<AppUser> getDepUsers(String orgPath,PagingBean pb,Map map) {

		String hql = "from AppUser au where au.delFlag=0 ";
		if(!"0.".equals(orgPath)){
			hql="select distinct uo.appUser from UserOrg uo " +
				"where uo.organization.path like ? and uo.appUser.delFlag = 0 ";
			
			if(map!=null){
				if(StringUtils.isNotEmpty(map.get("username").toString())){
					hql += "and uo.appUser.username like '%"+map.get("username").toString().trim()+"%' ";
				}
				if(StringUtils.isNotEmpty(map.get("fullname").toString())){
					hql += "and uo.appUser.fullname like '%"+map.get("fullname").toString().trim()+"%' ";
				}
			}
			if(pb!=null){
				return findByHql(hql, new Object[]{orgPath+"%"}, pb);
			}else{
				return findByHql(hql, new Object[]{orgPath+"%"});
			}
		}
		
		if(map!=null){
			if(StringUtils.isNotEmpty(map.get("username").toString())){
				hql += "and au.username like '%"+map.get("username").toString().trim()+"%' ";
			}
			if(StringUtils.isNotEmpty(map.get("fullname").toString())){
				hql += "and au.fullname like '%"+map.get("fullname").toString().trim()+"%' ";
			}
		}
		if(pb!=null){
			return findByHql(hql, new Object[]{}, pb);
		}else{
			return findByHql(hql, new Object[]{});
		}
	}
	/**
	 * 按组织结构获取用户列表
	 */
	public List<AppUser> getOrganizationUsers(Organization o) {
		
		String hql = "From AppUser a where a.userId in ( select uo.appUser.userId from UserOrg  uo where uo.organization.orgId="+o.getOrgId()+") and a.delFlag=0 ";
		return findByHql(hql);
	}
	

	/**
	 * 取得相对岗位用户列表
	 * @param reJobId
	 * @param startUserId 流程发起人
	 * @return
	 */
	public List<AppUser> getReLevelUser(String reJobId,Long startUserId){
		
		// 取得当前用户所有岗位
//		Long userId = ContextUtil.getCurrentUserId();
		
		List<Position> plist = new ArrayList<Position>();
		
		String hql = "select distinct p from UserPosition up, Position p " +
			"where up.position.posId = p.posId " +
			"and up.appUser.userId = ? ";
		
		Query query = getSession().createQuery(hql);
		query.setLong(0, startUserId);
		List<Position> plist3 = query.list();
		
		if(plist3!=null&&plist3.size()>0){
			plist.addAll(plist3);
		}
		
		// 如果取下x级岗位，则把已关联的同级岗位也计算
		if(Integer.parseInt(reJobId)<0){
			if(plist!=null&&plist.size()>0){
				for(Position p:plist3){
					
					List<Position> tlist = (List)findByHql("select p from Position p where p.posId = "+p.getPosId());
					
					Set<Position> mainPosSub = tlist.get(0)==null?null:tlist.get(0).getMainPositionSubs();
					Set<Position> subPosSub = tlist.get(0)==null?null:tlist.get(0).getSubPositionSubs();
					if(mainPosSub!=null&&subPosSub!=null){
						mainPosSub.addAll(subPosSub);
					}
					
					if(mainPosSub!=null&&mainPosSub.size()>0){
						for(Position pos:mainPosSub){
							if(!plist.contains(pos)){
								plist.add(pos);
							}
						}
					}
					
				}
			}
		}
		
		// 拼接路径
		int tmpReJobId = Integer.parseInt(reJobId)*(-1);
		String rePath = "";
		int ltFlag = 0;
		if(plist!=null&&plist.size()>0){
			for(int index=0;index<plist.size();index++){
				String path = ((Position)plist.get(index)).getPath();
				
				String[] curLvArr = path.substring(0, path.length()-1).split("\\.");
				int curLevel = curLvArr.length;
				int aftLevel = curLevel + tmpReJobId;
				if(aftLevel<curLevel){
					for(int index0=0;index0<aftLevel;index0++){
						rePath += curLvArr[index0] + ".";
					}
				}else if(aftLevel>curLevel){
					rePath += path;
					for(int index0=0;index0<aftLevel-curLevel;index0++){
						rePath += "%.";
					}
				}else{
					rePath += path;
				}
				if(index==0){ltFlag = aftLevel-curLevel;}
				rePath += ",";
			}
		}
		
		// 取得路径对应posId
		StringBuffer sb = null;
		String[] pths = rePath.length()==0?new String[0]:rePath.substring(0, rePath.length()-1).split(",");
		if(ltFlag>0){
			
			sb = new StringBuffer("select p from Position p ");
			for(int index=0;index<pths.length;index++){
				query = getSession().createQuery(
						"select p from Position p where p.path like '"+pths[index]+"' ");
				plist = query.list();
				int matchFlag = pths[index].split("\\.").length;
				if(plist!=null&&plist.size()>0){
					for(int index1=0;index1<plist.size();index1++){
						Position p = plist.get(index1);
						if(matchFlag==p.getPath().split("\\.").length){
							if(sb.toString().indexOf("where")==-1){
								sb.append("where p.path = '"+p.getPath()+"' ");
							}else{
								sb.append("or p.path = '"+p.getPath()+"' ");
							}
						} 
					}
				}
			}
		}else{
			sb = new StringBuffer("select p from Position p where p.posSupId in " +
				"(select p0.posSupId from Position p0 ");
			for(int index=0;index<pths.length;index++){
				if(index==0){
					sb.append("where p0.path = '"+pths[0]+"' ");
				}else{
					sb.append("or p0.path = '"+pths[index]+"' ");
				}
			}
			sb.append(")");
		}
		
		List<Position> tplist = (List)findByHql(sb.toString());
		if(!"0".equals(reJobId)){
			plist.clear();
			for(Position pos:tplist){
				plist.add(pos);
			}
			for(Position pos:plist){
				tplist.addAll(pos.getMainPositionSubs());
				tplist.addAll(pos.getSubPositionSubs());
				boolean flag = false;
				if(pos.getMainPositionSubs().size()==0&&pos.getSubPositionSubs().size()==0){
					for(int index=0;index<pths.length;index++){
						if(pos.getPath().indexOf(pths[index].split("%")[0])!=-1){
							flag = true;
							break;
						}
					}
					if(!flag){
						tplist.remove(pos);
					}
				}
			}
		}

		List<AppUser> list = new ArrayList<AppUser>();
		for(Position pos:tplist){
			Set<UserPosition> upSet = pos.getUserPositions();
			Iterator<UserPosition> upIt =upSet.iterator();
			while(upIt.hasNext()){
				UserPosition up = upIt.next();
				AppUser au = up.getAppUser();
				if(!list.contains(au))
					list.add(au);
			}
		}
		
		if("0".equals(reJobId)&&list.size()==0){
			list.add(ContextUtil.getCurrentUser());
		}
		
		return list;
	}
	
	/**
	 * 取得组织主要负责人
	 * @param userOrg
	 * @return
	 */
	@Override
	public List<AppUser> getChargeOrgUsers(Set userOrgs) {
		String isChargeUser = "";
		Iterator<UserOrg> it = userOrgs.iterator();
		while(it.hasNext()){
			UserOrg userOrg = it.next();
			if(userOrg.getIsCharge()!=null&&userOrg.getIsCharge()==1){
				isChargeUser += userOrg.getUserid()+",";
			}
		}
		isChargeUser = isChargeUser.length()==0?"0":isChargeUser.substring(0, isChargeUser.length()-1);
		String hql = "from AppUser au where au.userId in ("+isChargeUser+") and au.delFlag=0";
		return findByHql(hql);
	}

	/**
	 * 判断是否为超级管理员
	 * @param userId 用户id
	 * @return 超级管理员true
	 */
	@Override
	public Boolean isSuperMan(Long userId) {
		AppUser appUser = get(userId);
		if (appUser != null && appUser.getRoles() != null && appUser.getRoles().size() > 0) {
			for(AppRole role : appUser.getRoles()){
				if(role != null && (role.getRoleName().equals("超级管理员") || role.getRoleName().equals("管理员") || role.getRights().equals("__ALL"))){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Set<AppUser> getAppUserByStr(String[] str) {
		
		Set<AppUser> list =new HashSet<AppUser>();
		for(int i=0;i<str.length;i++){
			if(!str[i].equals("")){
				AppUser appUser = get(Long.valueOf(str[i]));
				if(null!=appUser){
					list.add(appUser);
				}
			}
		}
		return list;
	}

	@Override
	public AppUser findByUserNameAndConmpany(String userName, String path) {
		String hql="select distinct uo.appUser from UserOrg uo " +
		"where uo.organization.path like ? and uo.appUser.delFlag = 0";
		List<AppUser> users=findByHql(hql, new Object[]{path+"%"});
	    for(int i=0;i<users.size();i++){
	    	AppUser au=users.get(i);
	    	if(au.getUsername().equals(userName)){
	    		return au;
	    	}
	    }
		return null;
	}

	@Override
	public AppUser findByUserNameAndOrganization(String userName,List<Organization> os) {
		
		String ids="";
		if(null!=os && os.size()>0){
			 for(int i=0;i<os.size();i++){
				 ids+=os.get(i).getOrgId()+",";
			 }
		}
	    if(os.size()>0){
	    	ids=ids.substring(0, ids.length()-1);
	    }
		String hql = "From AppUser a where a.userId in ( select uo.appUser.userId from UserOrg  uo where uo.organization.orgId in ("+ids+") ) and a.delFlag=0 and a.username='"+userName+"'";
		List<AppUser> list= findByHql(hql);
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public long getUserCount(Long orgId,String orgType,String userName) {
		StringBuffer hql=new StringBuffer("select count(*) from AppUser as u where u.delFlag=0");
		if(null!=userName){
			hql.append(" and u.fullname like '%"+userName+"%'");
		}
		if(null!=orgId && null!=orgType && orgType.equals("0")){
			hql.append(" and u.department.depId  in (select r.orgId from Organization as r where r.path like '%"+orgId+"%' and r.orgType!=1 and r.orgSupId="+orgId+")");
		}else{
			hql.append(" and u.department.depId in (select r.orgId from Organization as r where r.path like '%"+orgId+"%')");
		}
		List list=getSession().createQuery(hql.toString()).list();
		long count=0;
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}

	@Override
	public List<AppUser> getUserList(Long orgId,String orgType,String userName, int start, int limit) {
		StringBuffer hql=new StringBuffer("from AppUser as u where u.delFlag=0");
		if(null!=userName){
			hql.append(" and u.fullname like '%"+userName+"%'");
		}
		if(null!=orgId && null!=orgType && orgType.equals("0")){
			hql.append(" and u.department.depId  in (select r.orgId from Organization as r where r.path like '%"+orgId+"%' and r.orgType!=1 and r.orgSupId="+orgId+")");
		}else{
			hql.append(" and u.department.depId in (select r.orgId from Organization as r where r.path like '%"+orgId+"%')");
		}
		return getSession().createQuery(hql.toString()).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public long getAllUserCount(Long orgId, String orgType,String userName) {
		StringBuffer hql=new StringBuffer("select count(*) from AppUser as u where u.delFlag=0");
		if(null!=userName){
			hql.append(" and u.fullname like '%"+userName+"%'");
		}
		if(null!=orgId && null!=orgType && orgType.equals("0")){
			//去一个条件and r.orgSupId="+orgId+"，原来的无法按名字模糊查询
			hql.append(" and u.department.depId  in (select r.orgId from Organization as r where r.path like '%"+orgId+"%' and r.orgType!=1 )");
		}else{
			hql.append(" and u.department.depId in (select r.orgId from Organization as r where r.path like '%"+orgId+"%')");
		}
		long count=0;
		List list=getSession().createQuery(hql.toString()).list();
		if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return count;
	}

	@Override
	public List<AppUser> getAllUserList(Long orgId, String orgType, String userName,int start,
			int limit) {
		StringBuffer hql=new StringBuffer("from AppUser as u where u.delFlag=0");
		if(null!=userName){
			hql.append(" and u.fullname like '%"+userName+"%'");
		}
		if(null!=orgId && null!=orgType && orgType.equals("0")){
			//去一个条件and r.orgSupId="+orgId+"，原来的无法按名字模糊查询
			hql.append(" and u.department.depId  in (select r.orgId from Organization as r where r.path like '%"+orgId+"%' and r.orgType!=1 )");
		}else{
			hql.append(" and u.department.depId in (select r.orgId from Organization as r where r.path like '%"+orgId+"%')");
		}
		return getSession().createQuery(hql.toString()).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public List<AppUser> getDepartmentUsers(Long orgId,PagingBean pb) {
		String hql="from AppUser as u where u.delFlag=0 and u.department.depId  in (select r.orgId from Organization as r where r.path like ? and r.orgType!=1)";
		return findByHql(hql, new Object[]{"%"+orgId+"%"}, pb);
	}

	@Override
	public List<AppUser> getUsersByCompany(List<Organization> os,
			PagingBean pb,Map map) {
		//update by lu 2012.12.05 分公司组织结构查询问题
		String ids="";
		if(null!=os && os.size()>0){
			 for(int i=0;i<os.size();i++){
				 ids+=os.get(i).getOrgId()+",";
			 }
		}
	    if(os.size()>0){
	    	ids=ids.substring(0, ids.length()-1);
	    }
	    String hql = "From  AppUser a where a.userId in ( select  distinct uo.appUser.userId from UserOrg  uo where 1=1";
	    
	    if(map!=null){
			if(StringUtils.isNotEmpty(map.get("username").toString())){
				hql += " and uo.appUser.username like '%"+map.get("username").toString().trim()+"%' ";
			}
			if(StringUtils.isNotEmpty(map.get("fullname").toString())){
				hql += " and uo.appUser.fullname like '%"+map.get("fullname").toString().trim()+"%' ";
			}
			if(StringUtils.isNotEmpty(map.get("userId").toString())){
				hql += " and uo.appUser.userId >1 ";
			}
		}
	    
	    if(ids!=null&&!"".equals(ids)){
	    	hql += " and uo.organization.orgId in ("+ids+") )";
	    }else{
	    	hql += ")";
	    }
		if(pb == null) {
			return findByHql(hql.toString());
		} else {
			return findByHql(hql.toString(), null, pb);
		}
	}

	@Override
	public boolean existUserNumber(String userNumber) {
		boolean fsg=true;
		String  hql="from AppUser a where a.userNumber=?";
		Object[] params = {userNumber};
		List<AppUser> list = findByHql(hql, params);
		if (list.size() > 0) {
			fsg=false;
		}
		return fsg;
	}
	@Override
	public boolean existUserNumber(String userNumber,long userId) {
		boolean fsg=true;
		String  hql="from AppUser a where a.userNumber=? and a.userId !=?";
		Object[] params = {userNumber,userId};
		List<AppUser> list = findByHql(hql, params);
		if (list.size() > 0) {
			fsg=false;
		}
		return fsg;
	}

	@Override
	public void getUserList(PageBean<AppUser> pageBean) {
		HttpServletRequest request = pageBean.getRequest();
		String companyId = request.getParameter("companyId");//公司id
		String fullname = request.getParameter("fullname");//姓名
		String plainpassword = request.getParameter("plainpassword");//推荐码
		String mobile = request.getParameter("mobile");//手机号
		String registrationDateGE = request.getParameter("registrationDateGE");//查询时间
		String registrationDateLE = request.getParameter("registrationDateLE");//查询时间
		
		StringBuffer tempSql = new StringBuffer(
				" select " +
				" u.userId," +
				" u.username," +
				" u.fullname," +
				" u.mobile," +
				" u.email," +
				" u.userNumber," +
				" bm.loginname as p2pAccount," +
				" bm.plainpassword," +
				" u.status," +
				" bm.isForbidden," +
				" bm.id as bpCustMemberId," +
				" u.org_id as companyId," +
				" o.org_name as companyName," +
				" DATE_FORMAT(bm.registrationDate,'%Y-%m-%d') as registrationDate," +
				" ( select COUNT(bcm.id) from bp_cust_member as bcm where bcm.recommandPerson=bm.plainpassword ) as recommandNum," +
				" ( select COUNT(bcm.id) from bp_cust_member as bcm where bcm.secondRecommandPerson=bm.plainpassword ) as secondRecommandNum," +
				" ( SELECT IFNULL(sum(info.investMoney),0) AS sumMoney FROM invest_person_info AS info " +
				"   LEFT JOIN bp_cust_member AS bcm ON bcm.id = info.investPersonId" +
				"   WHERE bcm.recommandPerson = bm.plainpassword" +
				" )as infoMoneyone, " +
				" ( SELECT IFNULL(SUM(buy.buyMoney),0) AS sumMoney FROM pl_managemoneyplan_buyinfo AS buy " +
				"   LEFT JOIN bp_cust_member AS bcm ON bcm.id = buy.investPersonId and buy.state>=1" +
				"   and (buy.keystr='UPlan' or buy.keystr='mmplan') WHERE bcm.recommandPerson = bm.plainpassword" +
				" )as mmplanMoneyone, " +
				" ( SELECT IFNULL(sum(info.investMoney),0) AS sumMoney FROM invest_person_info AS info " +
				"   LEFT JOIN bp_cust_member AS bcm ON bcm.id = info.investPersonId" +
				"   WHERE bcm.secondRecommandPerson = bm.plainpassword" +
				" )as infoMoneytwo, " +
				" ( SELECT IFNULL(SUM(buy.buyMoney),0) AS sumMoney FROM pl_managemoneyplan_buyinfo AS buy " +
				"   LEFT JOIN bp_cust_member AS bcm ON bcm.id = buy.investPersonId and buy.state >=1" +
				"   and (buy.keystr='UPlan' or buy.keystr='mmplan') WHERE bcm.secondRecommandPerson = bm.plainpassword" +
				" )as mmplanMoneytwo " +
				" from app_user as u" +
				" LEFT JOIN bp_cust_relation as br on u.userId=br.offlineCusId and br.offlineCustType='p_staff'" +
				" LEFT JOIN bp_cust_member as bm on br.p2pCustId=bm.id" +
				" LEFT JOIN organization as o on o.org_id=u.org_id");
		StringBuffer totalCounts = new StringBuffer("select count(*) from (");
		StringBuffer sb = new StringBuffer ("select * from (");
		sb.append(tempSql);
		sb.append(" ) as u where u.username <> 'system' and u.plainpassword is not NULL ");
		totalCounts.append(tempSql);
		totalCounts.append(" ) as u where u.username <> 'system' and u.plainpassword is not NULL ");
		if(null!=companyId && !companyId.equals("")){
			sb.append(" and fn_check_repeat(u.companyId,'"+companyId+"') = 1");
			totalCounts.append(" and fn_check_repeat(u.companyId,'"+companyId+"') = 1");
		}
		//TODO 紧急判断
//			AppUser users = (AppUser) request.getSession().getAttribute("users");
//		if(null!=fullname&&!"".equals(fullname)){
//			sb.append(" and u.fullname like '%"+fullname+"%' ");
//			totalCounts.append(" and u.fullname like '%"+fullname+"%' ");
//		}else if (users != null && !users.getFullname().equals( "超级管理员")){
//			fullname = users.getFullname();
//			sb.append(" and u.fullname like '%"+fullname+"%' ");
//			totalCounts.append(" and u.fullname like '%"+fullname+"%' ");
//		}


		if(null!=plainpassword&&!"".equals(plainpassword)){
			sb.append(" and u.plainpassword like '%"+plainpassword+"%' ");
			totalCounts.append(" and u.plainpassword like '%"+plainpassword+"%' ");
		}
		if(null!=mobile&&!"".equals(mobile)){
			sb.append(" and u.mobile like '%"+mobile+"%' ");
			totalCounts.append(" and u.mobile like '%"+mobile+"%' ");
		}
		if(null!=registrationDateGE&&!"".equals(registrationDateGE)){
			sb.append(" and  u.registrationDate >= '"+registrationDateGE.split("T")[0]+"' ");
			totalCounts.append(" and  u.registrationDate >= '"+registrationDateGE.split("T")[0]+"' ");
		}
		if(null!=registrationDateLE&&!"".equals(registrationDateLE)){
			sb.append(" and  u.registrationDate <= '"+registrationDateLE.split("T")[0]+"' ");
			totalCounts.append(" and  u.registrationDate <= '"+registrationDateLE.split("T")[0]+"' ");
		}
		List list = null;
		Query sqlQuery=this.getSession().createSQLQuery(sb.toString())
			.addScalar("userId",Hibernate.LONG)
			.addScalar("bpCustMemberId",Hibernate.LONG)
			.addScalar("companyId",Hibernate.LONG)
			.addScalar("username", Hibernate.STRING)
			.addScalar("fullname", Hibernate.STRING)
			.addScalar("mobile", Hibernate.STRING)
			.addScalar("email", Hibernate.STRING)
			.addScalar("userNumber", Hibernate.STRING)
			.addScalar("p2pAccount", Hibernate.STRING)
			.addScalar("plainpassword", Hibernate.STRING)
			.addScalar("companyName", Hibernate.STRING)
			.addScalar("registrationDate", Hibernate.STRING)
			.addScalar("recommandNum", Hibernate.INTEGER)
			.addScalar("secondRecommandNum", Hibernate.INTEGER)
			.addScalar("status", Hibernate.SHORT)
			.addScalar("infoMoneyone", Hibernate.BIG_DECIMAL)
			.addScalar("infoMoneytwo", Hibernate.BIG_DECIMAL)
			.addScalar("mmplanMoneyone", Hibernate.BIG_DECIMAL)
			.addScalar("mmplanMoneytwo", Hibernate.BIG_DECIMAL)
			.addScalar("isForbidden", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(AppUser.class));
			if(null!=pageBean.getStart()){
				list=sqlQuery.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
			}else{
				list=sqlQuery.list();
			}
		pageBean.setResult(list);
		BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}

	/*@Override
	public void userPerformanceList(PageBean<AppUser> pageBean) {
		HttpServletRequest request = pageBean.getRequest();
		String type = request.getParameter("type");//代表查询类型
		String companyId = request.getParameter("companyId");//公司id
		String fullname = request.getParameter("fullname");//姓名
		String plainpassword = request.getParameter("plainpassword");//推荐码
		String registrationDateGE = request.getParameter("registrationDateGE");//查询时间
		String registrationDateLE = request.getParameter("registrationDateLE");//查询时间
		
		StringBuffer tempSql = new StringBuffer("select a.* ,o.org_name AS oneDept,o1.org_name AS secDept,b.roleName,u.org_id as companyId " +
				" from " +
				"  (" +
				"   select " +
				"	br.offlineCusId," +
				"   bm1.truename," +
				"	bm1.cardcode," +
				"	COUNT(info.investId) AS investCount," +
				"	sum(info.investMoney) AS sumInvestMoney," +
				"	'bidPlan' AS keystr," +
				"	DATE_FORMAT(pbi.bidtime, '%Y-%m-%d') AS investDate," +
				"	pbp.bidProName AS investProjectName," +
				"	pbp.payMoneyTime AS investlimit,");
				if("one".equals(type)){
					tempSql.append(" bm1.recommandPerson AS plainpassword,");
				}else{
					tempSql.append(" bm1.secondRecommandPerson AS plainpassword,");
				}
				tempSql.append("( SELECT bcm.truename FROM bp_cust_member AS bcm" );
				if("one".equals(type)){
					tempSql.append(" WHERE bcm.plainpassword = bm1.recommandPerson ) AS recommended");
				}else{
					tempSql.append(" WHERE bcm.plainpassword = bm1.secondRecommandPerson ) AS recommended");
				}
				tempSql.append(" from bp_cust_relation AS br" +
				"	LEFT JOIN bp_cust_member as bm on br.p2pCustId=bm.id");
				if("one".equals(type)){
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.recommandPerson");
				}else{
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.secondRecommandPerson");
				}
				tempSql.append(" LEFT JOIN invest_person_info AS info ON info.investPersonId = bm1.id" +
				"	LEFT JOIN pl_bid_plan AS pbp ON info.bidPlanId = pbp.bidId" +
				"	LEFT JOIN pl_bid_info AS pbi ON info.orderNo=pbi.orderNo" +
				"	where br.offlineCustType = 'p_staff' and bm1.truename is not null " +
				"	GROUP BY info.bidPlanId,DATE_FORMAT(pbi.bidtime, '%Y-%m-%d')" +
				"	UNION" +
				"	select" +
				"	br.offlineCusId," +
				"	bm1.truename," +
				"	bm1.cardcode," +
				"	COUNT(buy.orderId) AS investCount," +
				"	sum(buy.buyMoney) AS sumInvestMoney," +
				"	buy.keystr," +
				"	DATE_FORMAT(buy.buyDatetime, '%Y-%m-%d') AS investDate," +
				"	buy.mmName AS investProjectName," +
				"	plan.investlimit,");
				if("one".equals(type)){
					tempSql.append(" bm1.recommandPerson AS plainpassword,");
				}else{
					tempSql.append(" bm1.secondRecommandPerson AS plainpassword,");
				}
				tempSql.append("( SELECT bcm.truename FROM bp_cust_member AS bcm" );
				if("one".equals(type)){
					tempSql.append(" WHERE bcm.plainpassword = bm1.recommandPerson ) AS recommended");
				}else{
					tempSql.append(" WHERE bcm.plainpassword = bm1.secondRecommandPerson ) AS recommended");
				}
				tempSql.append(" from bp_cust_relation AS br" +
				"	LEFT JOIN bp_cust_member as bm on br.p2pCustId=bm.id");
				if("one".equals(type)){
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.recommandPerson");
				}else{
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.secondRecommandPerson");
				}
				tempSql.append(" LEFT JOIN pl_managemoneyplan_buyinfo AS buy ON buy.investPersonId = bm1.id AND buy.state >= 1" +
				"	LEFT JOIN pl_managemoney_plan AS plan ON buy.mmplanId = plan.mmplanId" +
				"	where br.offlineCustType = 'p_staff' AND (buy.keystr = 'mmplan' OR buy.keystr = 'UPlan')" +
				"	GROUP BY buy.mmplanId,DATE_FORMAT(buy.buyDatetime, '%Y-%m-%d')" +
				"  ) as a" +
				"  LEFT JOIN app_user AS u ON u.userId = a.offlineCusId" +
				"  LEFT JOIN user_org uo ON a.offlineCusId = uo.userId" +
				"  LEFT JOIN organization o ON o.org_id = uo.org_id" +
				"  LEFT JOIN organization o1 ON o1.org_id = o.org_sup_id AND o.org_sup_id != 1" +
				"  LEFT JOIN (" +
				"   SELECT a.userId,GROUP_CONCAT(ar.roleName) AS roleName" +
				"   FROM app_user a" +
				"   LEFT JOIN user_role AS ur ON ur.userId = a.userId" +
				"   LEFT JOIN app_role AS ar ON ar.roleId = ur.roleId" +
				"	GROUP BY ur.userId" +
				"   ) AS b ON u.userId = b.userId");
		StringBuffer totalCounts = new StringBuffer("select count(*) from (");
		StringBuffer sb = new StringBuffer ("select * from (");
		sb.append(tempSql);
		sb.append(" ) as u where u.offlineCusId <> -1 ");
		totalCounts.append(tempSql);
		totalCounts.append(" ) as u where u.offlineCusId <> -1 ");
		if(null!=companyId && !companyId.equals("")){
			sb.append(" and fn_check_repeat(u.companyId,'"+companyId+"') = 1");
			totalCounts.append(" and fn_check_repeat(u.companyId,'"+companyId+"') = 1");
		}
		if(null!=fullname&&!"".equals(fullname)){
			sb.append(" and u.truename like '%"+fullname+"%' ");
			totalCounts.append(" and u.truename like '%"+fullname+"%' ");
		}
		if(null!=plainpassword&&!"".equals(plainpassword)){
			sb.append(" and u.plainpassword like '%"+plainpassword+"%' ");
			totalCounts.append(" and u.plainpassword like '%"+plainpassword+"%' ");
		}
		if(null!=registrationDateGE&&!"".equals(registrationDateGE)){
			sb.append(" and  u.investDate >= '"+registrationDateGE.split("T")[0]+"' ");
			totalCounts.append(" and  u.investDate >= '"+registrationDateGE.split("T")[0]+"' ");
		}
		if(null!=registrationDateLE&&!"".equals(registrationDateLE)){
			sb.append(" and  u.investDate <= '"+registrationDateLE.split("T")[0]+"' ");
			totalCounts.append(" and  u.investDate <= '"+registrationDateLE.split("T")[0]+"' ");
		}
		sb.append(" order by investDate desc ");
		List list = null;
		Query sqlQuery=this.getSession().createSQLQuery(sb.toString())
			.addScalar("offlineCusId",Hibernate.LONG)
			.addScalar("truename", Hibernate.STRING)
			.addScalar("cardcode", Hibernate.STRING)
			.addScalar("keystr", Hibernate.STRING)
			.addScalar("investDate", Hibernate.STRING)
			.addScalar("investlimit", Hibernate.STRING)
			.addScalar("recommended", Hibernate.STRING)
			.addScalar("oneDept", Hibernate.STRING)
			.addScalar("secDept", Hibernate.STRING)
			.addScalar("roleName", Hibernate.STRING)
			.addScalar("plainpassword", Hibernate.STRING)
			.addScalar("investProjectName", Hibernate.STRING)
			.addScalar("investCount", Hibernate.INTEGER)
			.addScalar("sumInvestMoney", Hibernate.BIG_DECIMAL).setResultTransformer(Transformers.aliasToBean(AppUser.class));
			if(null!=pageBean.getStart()){
				list=sqlQuery.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
			}else{
				list=sqlQuery.list();
			}
		pageBean.setResult(list);
		BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}
*/
	@Override
	public void userPerformanceList(PageBean<AppUser> pageBean) {
		HttpServletRequest request = pageBean.getRequest();
		String type = request.getParameter("type");//代表查询类型
		String companyId = request.getParameter("companyId");//公司id
		String fullname = request.getParameter("fullname");//姓名
		String plainpassword = request.getParameter("plainpassword");//推荐码
		String registrationDateGE = request.getParameter("registrationDateGE");//查询时间
		String registrationDateLE = request.getParameter("registrationDateLE");//查询时间
		
		StringBuffer tempSql = new StringBuffer("select a.* ,o.org_name AS oneDept,o1.org_name AS secDept,b.roleName,u.org_id as companyId " +
				" from " +
				"  (" +
				"   select " +
				"	br.offlineCusId," +
				"   bm1.truename," +
				"	bm1.cardcode," +
				//"	COUNT(info.investId) AS investCount," +
				"   bm.truename as recommended,"+
				"	info.investMoney AS sumInvestMoney," +
				"	'bidPlan' AS keystr," +
				"	DATE_FORMAT(pbi.bidtime, '%Y-%m-%d') AS investDate," +
				"	pbp.bidProName AS investProjectName," +
				"	pbp.payMoneyTime AS investlimit," +
				"   pbp.payMoneyTimeType as payMoneyTimeType, ");
				if("one".equals(type)){
					tempSql.append(" bm1.recommandPerson AS plainpassword");
				}else{
					tempSql.append(" bm1.secondRecommandPerson AS plainpassword");
				}
//				tempSql.append("( SELECT bcm.truename FROM bp_cust_member AS bcm" );
//				if("one".equals(type)){
//					tempSql.append(" WHERE bcm.plainpassword = bm1.recommandPerson ) AS recommended");
//				}else{
//					tempSql.append(" WHERE bcm.plainpassword = bm1.secondRecommandPerson ) AS recommended");
//				}
				tempSql.append(" from bp_cust_relation AS br" +
				"	LEFT JOIN bp_cust_member as bm on br.p2pCustId=bm.id");
				if("one".equals(type)){
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.recommandPerson");
				}else{
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.secondRecommandPerson");
				}
				tempSql.append(" LEFT JOIN invest_person_info AS info ON info.investPersonId = bm1.id" +
				"	LEFT JOIN pl_bid_plan AS pbp ON info.bidPlanId = pbp.bidId" +
				"	LEFT JOIN pl_bid_info AS pbi ON info.orderNo=pbi.orderNo" +
				"	where br.offlineCustType = 'p_staff' and bm1.truename is not null " +
				"	GROUP BY info.bidPlanId,DATE_FORMAT(pbi.bidtime, '%Y-%m-%d')" +
				"	UNION" +
				"	select" +
				"	br.offlineCusId," +
				"	bm1.truename," +
				"	bm1.cardcode," +
				//"	COUNT(buy.orderId) AS investCount," +
				"   bm.truename as recommended,"+
				"	buy.buyMoney AS sumInvestMoney," +
				"	buy.keystr," +
				"	DATE_FORMAT(buy.buyDatetime, '%Y-%m-%d') AS investDate," +
				"	buy.mmName AS investProjectName," +
				"	plan.investlimit," +
				"   'monthPay' as payMoneyTimeType,");
				if("one".equals(type)){
					tempSql.append(" bm1.recommandPerson AS plainpassword");
				}else{
					tempSql.append(" bm1.secondRecommandPerson AS plainpassword");
				}
//				tempSql.append("( SELECT bcm.truename FROM bp_cust_member AS bcm" );
//				if("one".equals(type)){
//					tempSql.append(" WHERE bcm.plainpassword = bm1.recommandPerson ) AS recommended");
//				}else{
//					tempSql.append(" WHERE bcm.plainpassword = bm1.secondRecommandPerson ) AS recommended");
//				}
				tempSql.append(" from bp_cust_relation AS br" +
				"	LEFT JOIN bp_cust_member as bm on br.p2pCustId=bm.id");
				if("one".equals(type)){
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.recommandPerson");
				}else{
					tempSql.append(" LEFT JOIN bp_cust_member as bm1 on bm.plainpassword=bm1.secondRecommandPerson");
				}
				tempSql.append(" LEFT JOIN pl_managemoneyplan_buyinfo AS buy ON buy.investPersonId = bm1.id AND buy.state >= 1" +
				"	LEFT JOIN pl_managemoney_plan AS plan ON buy.mmplanId = plan.mmplanId" +
				"	where br.offlineCustType = 'p_staff' AND (buy.keystr = 'mmplan' OR buy.keystr = 'UPlan')" +
				"	GROUP BY buy.mmplanId,DATE_FORMAT(buy.buyDatetime, '%Y-%m-%d')" +
				"  ) as a" +
				"  LEFT JOIN app_user AS u ON u.userId = a.offlineCusId" +
				"  LEFT JOIN user_org uo ON a.offlineCusId = uo.userId" +
				"  LEFT JOIN organization o ON o.org_id = uo.org_id" +
				"  LEFT JOIN organization o1 ON o1.org_id = o.org_sup_id AND o.org_sup_id != 1" +
				"  LEFT JOIN (" +
				"   SELECT a.userId,GROUP_CONCAT(ar.roleName) AS roleName" +
				"   FROM app_user a" +
				"   LEFT JOIN user_role AS ur ON ur.userId = a.userId" +
				"   LEFT JOIN app_role AS ar ON ar.roleId = ur.roleId" +
				"	GROUP BY ur.userId" +
				"   ) AS b ON u.userId = b.userId");
		StringBuffer totalCounts = new StringBuffer("select count(1) from (");
		StringBuffer sb = new StringBuffer ("select * from (");
		sb.append(tempSql);
		sb.append(" ) as u where u.offlineCusId <> -1 ");
		totalCounts.append(tempSql);
		totalCounts.append(" ) as u where u.offlineCusId <> -1 ");
		if(null!=companyId && !companyId.equals("")){
			sb.append(" and fn_check_repeat(u.companyId,'"+companyId+"') = 1");
			totalCounts.append(" and fn_check_repeat(u.companyId,'"+companyId+"') = 1");
		}

		//TODO 紧急修改业绩
		AppUser users = (AppUser) request.getSession().getAttribute("users");
		if(null!=fullname&&!"".equals(fullname)){//由客户姓名修改为推荐人姓名
//			sb.append(" and u.truename like '%"+fullname+"%' ");
//			totalCounts.append(" and u.truename like '%"+fullname+"%' ");
			sb.append(" and recommended like '%"+fullname+"%' ");
			totalCounts.append(" and recommended like '%"+fullname+"%' ");
		}else if (users != null && !users.getFullname().equals( "超级管理员")){
			fullname = users.getFullname();
			sb.append(" and recommended like '%"+fullname+"%' ");
			totalCounts.append(" and recommended like '%"+fullname+"%' ");
	}





		if(null!=plainpassword&&!"".equals(plainpassword)){
			sb.append(" and u.plainpassword like '%"+plainpassword+"%' ");
			totalCounts.append(" and u.plainpassword like '%"+plainpassword+"%' ");
		}
		if(null!=registrationDateGE&&!"".equals(registrationDateGE)){
			sb.append(" and  u.investDate >= '"+registrationDateGE.split("T")[0]+"' ");
			totalCounts.append(" and  u.investDate >= '"+registrationDateGE.split("T")[0]+"' ");
		}
		if(null!=registrationDateLE&&!"".equals(registrationDateLE)){
			sb.append(" and  u.investDate <= '"+registrationDateLE.split("T")[0]+"' ");
			totalCounts.append(" and  u.investDate <= '"+registrationDateLE.split("T")[0]+"' ");
		}
		sb.append(" order by investDate desc ");
		List list = null;
		System.out.println("sql=="+sb.toString());
		Query sqlQuery=this.getSession().createSQLQuery(sb.toString())
			.addScalar("offlineCusId",Hibernate.LONG)
			.addScalar("truename", Hibernate.STRING)
			.addScalar("cardcode", Hibernate.STRING)
			.addScalar("keystr", Hibernate.STRING)
			.addScalar("investDate", Hibernate.STRING)
			.addScalar("investlimit", Hibernate.STRING)
			.addScalar("payMoneyTimeType", Hibernate.STRING)
			.addScalar("recommended", Hibernate.STRING)
			.addScalar("oneDept", Hibernate.STRING)
			.addScalar("secDept", Hibernate.STRING)
			.addScalar("roleName", Hibernate.STRING)
			.addScalar("plainpassword", Hibernate.STRING)
			.addScalar("investProjectName", Hibernate.STRING)
			//.addScalar("investCount", Hibernate.INTEGER)
			.addScalar("sumInvestMoney", Hibernate.BIG_DECIMAL).setResultTransformer(Transformers.aliasToBean(AppUser.class));
			if(null!=pageBean.getStart()){
				list=sqlQuery.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
			}else{
				list=sqlQuery.list();
			}
		pageBean.setResult(list);
		BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}

	

	@Override
	public void findUserList(PageBean<AppUser> pageBean) {
		StringBuffer totalCounts = new StringBuffer("select count(*) from (");
		StringBuffer sb = new StringBuffer(" select a.userId,a.fullname,a.username,a.mobile,a.email " +
				" from app_user as a" +
				" left join bp_cust_relation as br on br.offlineCusId=a.userId and br.offlineCustType='p_staff'" +
				" LEFT JOIN bp_cust_member as bm on br.p2pCustId=bm.id" +
				" where a.userId<> -1 and a.delFlag=0 and bm.plainpassword  is NULL ");
		List list = null;
		totalCounts.append(sb).append(" ) as u");
		Query sqlQuery=this.getSession().createSQLQuery(sb.toString())
			.addScalar("userId",Hibernate.LONG)
			.addScalar("username", Hibernate.STRING)
			.addScalar("fullname", Hibernate.STRING)
			.addScalar("mobile", Hibernate.STRING)
			.addScalar("email", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(AppUser.class));
		if(null!=pageBean.getStart()){
			list=sqlQuery.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
		}else{
			list=sqlQuery.list();
		}
		pageBean.setResult(list);
		BigInteger total =   (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}
	
	@Override
	public String findMaxMember() {
		Query q = this.getSession().createQuery("select max(userNumber) from AppUser where userNumber is not NULL");
		String maxNumber=(String)q.uniqueResult();
		return maxNumber;
	}

	@Override
	public List<AppUser> findLower(List<Long> idList, PagingBean pb,HttpServletRequest request) {
		String fullname=request.getParameter("fullname");
		StringBuffer hql = new StringBuffer();
		if (null==idList) {//超级管理员
			hql.append("from AppUser au where au.delFlag = 0");
		} else {//普通用户
//			idList.add(ContextUtil.getCurrentUserId());
			hql.append("select distinct au from AppUser au where au.userId in (:alist) and au.delFlag=0 ");
		}
		if(null!=fullname && !"".equals(fullname)){
			hql.append(" and au.fullname like '%"+fullname+"%' ");
		}
		Query query = getSession().createQuery(hql.toString());
		Query query2 = getSession().createQuery(hql.toString());
		if (null!=idList) {//超级管理员
			query.setParameterList("alist",idList);
			query2.setParameterList("alist",idList);
		}
		query.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize());
		pb.setTotalItems(query2.list().size());


		return query.list();
	}
	
	@Override
	public List<AppUser> findByUserNameAndAt(String userName) {
		String hql = "From AppUser a where  a.delFlag=0 and a.username like '%"+userName+"@%'";
		List<AppUser> list= findByHql(hql);
		return list;
	}

	@Override
	public List<AppUser> getByDepId(Long orgId) {
		String sql = "SELECT * from app_user  where depId = "+orgId;
		List<AppUser> appUser = getSession().createSQLQuery(sql)
                .addScalar("userId",Hibernate.LONG)
                .addScalar("username", Hibernate.STRING)
                .addScalar("fullname", Hibernate.STRING)
                .addScalar("mobile", Hibernate.STRING)
                .addScalar("email", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(AppUser.class)).list();

		return appUser;
	}

	@Override
	public AppUser getAllById(Long userId) {

		String sql  = "SELECT a.userId,a.fullname,a.userNumber,a.username,a.mobile,m.loginname as p2pAccount ,m.plainpassword as plainpassword from app_user a " +
				"LEFT JOIN bp_cust_relation b on a.userId=b.offlineCusId LEFT JOIN " +
				"bp_cust_member m on m.id=b.p2pCustId  where a.userId="+userId;

		AppUser appUser = (AppUser) this.getSession().createSQLQuery(sql)
				.addScalar("userId", Hibernate.LONG)
				.addScalar("username", Hibernate.STRING)
				.addScalar("userNumber", Hibernate.STRING)
				.addScalar("fullname", Hibernate.STRING)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("p2pAccount", Hibernate.STRING)
				.addScalar("plainpassword", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(AppUser.class)).list().get(0);

		return appUser;
	}

	@Override
	public BigDecimal getLowerMoney(Long id, String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SUM(payMoney) from (SELECT b.id,b.truename  from (SELECT b.id,b.truename,b.plainpassword  from  bp_cust_relation r LEFT JOIN bp_cust_member b on b.id = r.p2pCustId where r.offlineCusId=")
				.append( id+" ) as c LEFT JOIN bp_cust_member b  on c.plainpassword=b.recommandPerson)as a ,ob_account_deal_info o where a.id= o.investPersonId and transferType=4 and  dealRecordStatus = 2 ");
		if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
			sb.append("and createDate BETWEEN '"+startDate+" 00:00:00'  AND '"+endDate+" 23:59:59'");
		}else {
			sb.append("and createDate BETWEEN '"+ DateUtil.getFirstDay()+" 00:00:00'  AND '"+ DateUtil.getLastDay()+" 23:59:59'");
		}

		BigDecimal money = (BigDecimal) getSession().createSQLQuery(sb.toString()).uniqueResult();

		if (money == null || money.compareTo(BigDecimal.ZERO) == 0){
			money = BigDecimal.ZERO;
		}

		return money;
	}

	@Override
	public Integer getAllInvent(Long id, String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(*)  from (SELECT b.id,b.truename,b.plainpassword  from  bp_cust_relation r LEFT JOIN bp_cust_member b on b.id = r.p2pCustId where r.offlineCusId=");
				sb.append(id+") as c LEFT JOIN bp_cust_member b  on c.plainpassword=b.recommandPerson ");

		if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
			sb.append("  where b.registrationDate BETWEEN '"+startDate+" 00:00:00'  AND '"+endDate+" 23:59:59'");
		}else {
            sb.append(" where b.registrationDate BETWEEN '"+ DateUtil.getFirstDay()+" 00:00:00'  AND '"+ DateUtil.getLastDay()+" 23:59:59'");
        }

		BigInteger count = (BigInteger) getSession().createSQLQuery(sb.toString()).uniqueResult();

		return count.intValue();
	}

	@Override
	public Integer allMoneyInvent(Long id, String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(*) from (SELECT a.id from (SELECT b.id,b.truename  from (SELECT b.id,b.truename,b.plainpassword  from  bp_cust_relation r LEFT JOIN bp_cust_member b on b.id = r.p2pCustId where r.offlineCusId=");
		sb.append(id+") as c LEFT JOIN bp_cust_member b  on c.plainpassword=b.recommandPerson ");
		if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
			sb.append("  where b.registrationDate BETWEEN '"+startDate+" 00:00:00'  AND '"+endDate+" 23:59:59'");
		}else {
            sb.append(" where b.registrationDate BETWEEN '"+ DateUtil.getFirstDay()+" 00:00:00'  AND '"+ DateUtil.getLastDay()+" 23:59:59'");
        }
		sb.append(" )as a ,ob_account_deal_info o where a.id= o.investPersonId and transferType=4 GROUP BY o.investPersonId ) as a ");

		BigInteger integer = (BigInteger) getSession().createSQLQuery(sb.toString()).uniqueResult();

		return integer.intValue();
	}

	@Override
	public String getRelativeJobName(String upId, Long lowId) {
		String sql = "SELECT r.jobName  from relative_job r LEFT JOIN relative_user u on r.reJobId = u.reJobId  where u.userId ="+lowId+" and u.jobUserId="+upId;
		String companyName = (String) getSession().createSQLQuery(sql).uniqueResult();

		return companyName;
	}
}