package com.zhiwei.credit.service.system.impl;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;

import com.google.gson.Gson;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.dynamicPwd.HttpClient;
import com.zhiwei.credit.core.dynamicPwd.YooeResponse;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.IndexDisplay;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.PanelItem;
import com.zhiwei.credit.model.system.RelativeUser;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.model.system.UserOrg;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.IndexDisplayService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.RelativeUserService;
import com.zhiwei.credit.service.system.SysConfigService;

/**
 * @description 用户信息
 * @class AppUserServiceImpl
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-27AM
 */
public class AppUserServiceImpl extends BaseServiceImpl<AppUser> implements AppUserService {
	private AppUserDao dao;
	@Resource
	IndexDisplayService indexDisplayService;
	@Resource
	private RelativeUserService relativeUserService;
	public AppUserServiceImpl(AppUserDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Resource
	private SysConfigService sysConfigService;
	
	
	@Resource
	private AppRoleService appRoleService;
	@Resource
	private OrganizationService organizationService;
	

	@Override
	public AppUser findByUserName(String username) {
		return dao.findByUserName(username);
	}

	@Override
	public List<AppUser> findByDepartment(String path, PagingBean pb) {
		return dao.findByDepartment(path, pb);
	}

	@Override
	public List<AppUser> findByDepartment(String path, String userIds, PagingBean pb){
		return dao.findByDepartment(path, pb);
	}
	
	@Override
	public List<AppUser> findByRole(Long roleId, PagingBean pb) {
		return dao.findByRole(roleId, pb);
	}

	public List<AppUser> findByRoleId(Long roleId) {
		return dao.findByRole(roleId);
	}

	@Override
	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb) {
		return dao.findSubAppUser(path, userIds, pb);
	}

	@Override
	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb) {
		return dao.findSubAppUserByRole(roleId, userIds, pb);
	}

	@Override
	public List<AppUser> findByDepId(Long depId) {
		return dao.findByDepId(depId);
	}

	public String initDynamicPwd(HashMap<String, String> input, String function) {
		SysConfig dynamicPwdConfig = sysConfigService.findByKey("dynamicUri");
		URI base_uri = URI.create(dynamicPwdConfig.getDataValue());
		HttpClient client = new HttpClient(base_uri);
		try {

			YooeResponse response = client.call_api(function, input);

			String ret_cmd = response.getRetCmd();
			logger.debug("=============dynamicPwd status:" + ret_cmd);

			HashMap<String, String> output = response.getVarsDict();
			Iterator<String> i = output.keySet().iterator();
			String result = output.get("ret");

			while (i.hasNext()) {
				String name = i.next();
				String value = output.get(name);
				logger.debug("==============dynamicPwd info:" + name + "="
						+ value);
			}

			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return "\"" + function + "\"失败，异常：" + e.getMessage();
			// TODO Auto-generated catch block
		} catch (Exception e) {
			e.printStackTrace();
			return "\"" + function + "\"失败，异常：" + e.getMessage();
			// TODO Auto-generated catch block
		}
	}

	/**
	 * 按角色ID查找用户
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<AppUser> findUsersByRoleIds(String roleIds) {
		return dao.findUsersByRoleIds(roleIds);
	}

	/**
	 * @description 根据当前用户岗位取得下属岗位的用户
	 * @return List<AppUser>
	 */
	public List<AppUser> findRelativeUsersByUserId(Long userId) {
		return dao.findRelativeUsersByUserId(userId);
	}
	

	/**
	 * 按角色取得用户列表
	 * @param roleId
	 * @return
	 */
	public List<AppUser> getUsersByRoleId(Long roleId){
		return dao.getUsersByRoleId(roleId);
	}
	
	/**
	 * 返回当前用户的信息，以Json格式返回
	 * @return
	 */
	public String getCurUserInfo(){
	    AppUser user = ContextUtil.getCurrentUser();
		AppUser currentUser=dao.get(user.getUserId());
	    Hibernate.initialize(currentUser.getRoles());
	    currentUser.init();
		Department curDep = currentUser.getDepartment();
		if (curDep == null) {// 若所属部门为空，则设置一个缺省的部门 TODO
			curDep = new Department();
			curDep.setDepId(0l);
			curDep.setDepName(AppUtil.getCompanyName());
		}
		//去掉公共权限
		List<IndexDisplay> list = indexDisplayService.findByUser(currentUser.getUserId());
		List<PanelItem> items = new ArrayList<PanelItem>();
		for (IndexDisplay id : list) {
			PanelItem pi = new PanelItem();
			pi.setPanelId(id.getPortalId());
			pi.setColumn(id.getColNum());
			pi.setRow(id.getRowNum());
			items.add(pi);
		}
		StringBuffer sb = new StringBuffer();
		String depName=curDep.getDepName().trim();
		sb.append("{success:true,user:{userId:'").append(currentUser.getUserId())
				.append("',fullname:'").append(currentUser.getFullname())
				.append("',username:'").append(currentUser.getUsername())
				.append("',depId:'").append(curDep.getDepId())
				.append("',depName:'").append(depName)
				.append("',rights:'");
		sb.append(currentUser.getRights().toString().replace("[", "").replace("]",""));
		sb.append("',userDesk:'").append(null==currentUser.getDesks()?"":currentUser.getDesks().toString().replace("[", "").replace("]",""));//用户桌面权限
		sb.append("',deskRights:'").append(null==currentUser.getDeskRights()?"":currentUser.getDeskRights().toString().replaceAll(", ",",").replace("[","").replace("]",""));//角色桌面权限
		
		Gson gson = new Gson();
		sb.append("',topModules:");
		sb.append(gson.toJson(currentUser.getTopModules().values()));
		sb.append(",items:").append(gson.toJson(items).toString());
		sb.append("},sysConfigs:{");
		//系统配置也在此时加载dd
		/*List<SysConfig> sysConfigs = sysConfigService.getAll();
		for(SysConfig sysConfig : sysConfigs){
			sb.append("'")
			  .append(sysConfig.getConfigKey())
			  .append("':'")
			  .append(sysConfig.getDataValue())
			  .append("',");
		}
		if(sysConfigs.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}*/
		sb.append("}}");
		return sb.toString().replaceAll("\"", "'");
	}
	
	/**
	 * 按部门取得用户列表
	 * @param orgPath
	 * @return
	 */
	@Override
	public List<AppUser> getDepUsers(String orgPath,PagingBean pb,Map map) {
		return dao.getDepUsers(orgPath,pb,map);
	}
	
	/**
	 * 取得想对岗位用户列表
	 * @param reJobId
	 * @return
	 */
	@Override
	public List<AppUser> getReLevelUser(String reJobId,Long startUserId) {
		return dao.getReLevelUser(reJobId,startUserId);
	}
	
	/**
	 * 取得组织主要负责人
	 * @param userOrg
	 * @return
	 */
	@Override
	public List<AppUser> getChargeOrgUsers(Set<UserOrg> userOrgs) {
		return dao.getChargeOrgUsers(userOrgs);
	}


	@Override
	public Boolean isSuperMan(Long userId) {
		return dao.isSuperMan(userId);
	}

	@Override
	public Set<AppUser> getAppUserByStr(String[] str) {
		
		return this.dao.getAppUserByStr(str);
	}

	/**
	 * 得到当前用户或当前用户及其所有下属用户的ID字符串(各ID间以'，'号分隔开)
	 * @return
	 */
	public String getUsersStr() {
		String userIdsStr = "";
		StringBuffer userIdsStrBuffer = new StringBuffer();
		String currentUserId = ContextUtil.getCurrentUserId().toString();
//		List<AppUser> userList = dao.findRelativeUsersByUserId();
		List<AppUser> userList = dao.findRelativeUsersByUserId(ContextUtil.getCurrentUserId());
		if(userList.size() > 0) {
			for(AppUser appUser : userList) {
				userIdsStrBuffer.append(appUser.getUserId());
				userIdsStrBuffer.append(",");
			}
			userIdsStr = userIdsStrBuffer.toString() + currentUserId;
		}else {
			userIdsStr = currentUserId;
		}
		return userIdsStr;
	}

	@Override
	public AppUser findByUserNameAndConmpany(String userName, String path)
	{
		return this.dao.findByUserNameAndConmpany(userName, path);
	}

	@Override
	public List<AppUser> getOrganizationUsers(Organization o) 
	{
		return  this.dao.getOrganizationUsers(o);
	}

	@Override
	public AppUser findByUserNameAndOrganization(String userName,List<Organization> os) {
	    return this.dao.findByUserNameAndOrganization(userName,os);
	}

	@Override
	public long getUserCount(Long orgId,String orgType,String userName) {
		
		return dao.getUserCount(orgId,orgType,userName);
	}

	@Override
	public List<AppUser> getUserList(Long orgId,String orgType,String userName, int start, int limit) {
		
		return dao.getUserList(orgId,orgType,userName, start, limit);
	}

	@Override
	public long getAllUserCount(Long orgId, String orgType,String userName) {
	
		return dao.getAllUserCount(orgId, orgType,userName);
	}

	@Override
	public List<AppUser> getAllUserList(Long orgId, String orgType,String userName, int start,
			int limit) {
		
		return dao.getAllUserList(orgId, orgType,userName, start, limit);
	}

	@Override
	public List<AppUser> getDepartmentUsers(Long orgId, PagingBean pb) {
		
		return dao.getDepartmentUsers(orgId, pb);
	}

	@Override
	public List<AppUser> getUsersByCompany(List<Organization> os,
			PagingBean pb, Map map) {
		List<AppUser> list=dao.getUsersByCompany(os, pb, map);
		return list;
	}

	@Override
	public boolean existUserNumber(String userNumber) {
		return dao.existUserNumber(userNumber);
	}
	public boolean existUserNumber(String userNumber,long userId) {
		return dao.existUserNumber(userNumber, userId);
	}
	@Override
	public List<AppUser> findByUserId(String userIds) {
		List<AppUser> list = new ArrayList<AppUser>();
		String[] uids = userIds.split(",");
		for(int i = 0; i<uids.length;i++){
			if("".equals(uids[i])||null==uids[i]) continue;
			list.add(dao.get(Long.parseLong(uids[i])));
		}
		return list;
	}
	/**
	 * 递归方法
	 * @param list
	 * @param appUserList
	 * @return
	 */
	@Override
	public List<AppUser> diguiRelativeUser(List<RelativeUser> list,List<AppUser> appUserList){
		for(RelativeUser ru : list){
			AppUser jobUser = ru.getJobUser();
			appUserList.add(jobUser);
			List<RelativeUser> findSubordinate = relativeUserService.findSubordinate(jobUser.getUserId());
			diguiRelativeUser(findSubordinate,appUserList);
		}
		return appUserList;
	}
	
	@Override
	public List<AppUser> separateByRoleId(Long flowStartUserId,
			Set<Long> roleSet) {
	//	long start = System.currentTimeMillis();
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		
		//角色对应的appuser总集合
		HashSet<AppUser> appUserSet  = new HashSet<AppUser>();
		if(roleSet!=null&&roleSet.size()>0){
			Iterator<Long> iterator = roleSet.iterator();
			while(iterator.hasNext()){
				Long next = iterator.next();
				AppRole appRole = appRoleService.get(next);
				if(appRole!=null){
					if(appRole.getAppUsers()!=null&&appRole.getAppUsers().size()>0){
						appUserSet.addAll(appRole.getAppUsers());
					}
				}
			}
		}
		
		//流程发起人
		AppUser appUser  =null;
		if(flowStartUserId!=null){
			appUser = this.dao.get(flowStartUserId);
		}else{
			logger.error("流程发起人丢失----------------");
			return null;
		}
		//如果发起人没有门店，则返回流程发起人
		if(appUser.getDepartment()==null){
			appUserList.add(dao.get(Long.valueOf(flowStartUserId)));
			return appUserList;
		}
		
		Organization org = organizationService.get(appUser.getDepartment().getDepId());
//		System.out.println("发起人所在部门:"+org.getOrgName());
		//发起人所在门店
		Organization mendian = organizationService.recursionFindOrganiztion(org);
		//如果门店 为 NULL则直接返回流程发起人
		if(mendian==null){
			appUserList.add(dao.get(Long.valueOf(flowStartUserId)));
			return appUserList;
		}
//		System.out.println("发起人所在门店:"+mendian.getOrgName());
		
		//过滤比较角色人员库
		Iterator<AppUser> iteratorAppUser = appUserSet.iterator();
		while(iteratorAppUser.hasNext()){
			AppUser next = iteratorAppUser.next();
			Organization tempOrg = organizationService.get(next.getDepartment().getDepId());
//			System.out.println(next.getFullname()+"所在部门:"+tempOrg.getOrgName());
			Organization tempMD = organizationService.recursionFindOrganiztion(tempOrg);
//			System.out.println(next.getFullname()+"所在门店:"+tempMD.getOrgName());
			if(tempMD!=null&&tempMD.getOrgId().compareTo(mendian.getOrgId())==0){
			//	System.out.println(next.getFullname());
				appUserList.add(next);
			}
		}
	//	long end = System.currentTimeMillis();
	//	System.out.println("角色查询耗时:"+(end-start));
		
		if(appUserList.size()==0){   //如果对应的角色不存在，则返回发起人
			appUserList.add(appUser);
		}
		
		return appUserList;
	}

	@Override
	public List<AppUser> findByRoleSet(Set<Long> roleSet) {
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		
		//角色对应的appuser总集合
		HashSet<AppUser> appUserSet  = new HashSet<AppUser>();
		if(roleSet!=null&&roleSet.size()>0){
			Iterator<Long> iterator = roleSet.iterator();
			while(iterator.hasNext()){
				Long next = iterator.next();
				AppRole appRole = appRoleService.get(next);
				if(appRole!=null){
					if(appRole.getAppUsers()!=null&&appRole.getAppUsers().size()>0){
						appUserSet.addAll(appRole.getAppUsers());
					}
				}
			}
		}
		
		Iterator<AppUser> iterator = appUserSet.iterator();
		while (iterator.hasNext()) {
			AppUser next = iterator.next();
			appUserList.add(next);
		}
		
		return appUserList;
	}

	@Override
	public void getUserList(PageBean<AppUser> pageBean) {
		dao.getUserList(pageBean);
	}

	@Override
	public void userPerformanceList(PageBean<AppUser> pageBean) {
		dao.userPerformanceList(pageBean);
	}

	@Override
	public void findUserList(PageBean<AppUser> pageBean) {
		dao.findUserList(pageBean);
	}
	
	@Override
	public String findMaxMember() {
		return dao.findMaxMember();
	}

	@Override
	public List<AppUser> findLower(List<Long> idList, PagingBean pb,HttpServletRequest request) {
		return dao.findLower(idList,pb,request);
	}
	@Override
	public List<AppUser> findByUserNameAndAt(String userName) {
		return dao.findByUserNameAndAt(userName);
	}
	
	/**
	 * 我真的不知道这是干吗的
	 * @param orgPath
	 * @return
	 */
	
	@Override
	public String getCurUserInfoOfmobile(){
			

		    AppUser user = ContextUtil.getCurrentUser();
			AppUser currentUser=dao.get(user.getUserId());
		    Hibernate.initialize(currentUser.getRoles());
		    currentUser.init();
			Department curDep = currentUser.getDepartment();
			if (curDep == null) {// 若所属部门为空，则设置一个缺省的部门 TODO
				curDep = new Department();
				curDep.setDepId(0l);
				curDep.setDepName(AppUtil.getCompanyName());
			}
			//去掉公共权限
			List<IndexDisplay> list = indexDisplayService.findByUser(currentUser
					.getUserId());
			List<PanelItem> items = new ArrayList<PanelItem>();
			for (IndexDisplay id : list) {
				PanelItem pi = new PanelItem();
				pi.setPanelId(id.getPortalId());
				pi.setColumn(id.getColNum());
				pi.setRow(id.getRowNum());
				items.add(pi);
			}
			StringBuffer sb = new StringBuffer();
			String depName=curDep.getDepName().trim();
			sb.append("{\"success\":true,\"user\":{\"userId\":\"").append(
					currentUser.getUserId()).append("\",\"fullname\":\"").append(
					currentUser.getFullname()).append("\",\"username\":\"").append(
					currentUser.getUsername()).append("\",\"depId\":\"").append(
					curDep.getDepId()).append("\",\"depName\":\"").append(depName).append("\",\"rights\":\"");
			sb.append(currentUser.getRights().toString().replace("[", "").replace(
					"]", ""));
			
			Gson gson = new Gson();
			sb.append("\",\"topModules\":");
			sb.append(gson.toJson(currentUser.getTopModules().values()));
			sb.append(",\"items\":").append(gson.toJson(items).toString());
			sb.append("},\"sysConfigs\":{");
			//系统配置也在此时加载dd
			List<SysConfig> sysConfigs = sysConfigService.getAll();
			for(SysConfig sysConfig : sysConfigs){
				sb.append("\"")
				  .append(sysConfig.getConfigKey())
				  .append("\":\"")
				  .append(sysConfig.getDataValue())
				  .append("\",");
			}
			if(sysConfigs.size()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			sb.append("}}");
			return sb.toString().replaceAll("\"", "\"");
		
			
		}

	@Override
	public List<AppUser> getByDepId(List<Organization> byOrgSupId) {
		List<AppUser> list = new ArrayList<>();
		for (Organization organization : byOrgSupId) {
			List<AppUser> appUser = dao.getByDepId(organization.getOrgId());
			list.addAll(appUser);
		}

		return list;

	}

    @Override
    public List<AppUser> getAllById(List<AppUser> list) {
        List<AppUser> lists = new ArrayList<>();
        for (AppUser appUser : list) {
             AppUser a =    dao.getAllById(appUser.getUserId());
             lists.add(a);
        }
        return lists;
    }

	@Override
	public AppUser getAllLowerMsg(AppUser appUser,Set<Long> allLowerId, String startDate, String endDate) {
		BigDecimal money =new BigDecimal(0);
		Integer allCount = 0;
		Integer allMoneyCount =0;
		for (Long id : allLowerId) {
			money = dao.getLowerMoney(id,startDate,endDate).add(money);
			allCount += dao.getAllInvent(id,startDate,endDate);
			allMoneyCount += dao.allMoneyInvent(id,startDate,endDate);
		}
 		appUser.setSumInvestMoney(money);//投资总额
		appUser.setRecommandNum(allCount);//推荐人数
		appUser.setSecondRecommandNum(allMoneyCount);//投资成功推荐人数

		return appUser;
	}

	@Override
	public String getRelativeJobName(String upId, Long lowId) {

		return dao.getRelativeJobName(upId,lowId);
	}
}