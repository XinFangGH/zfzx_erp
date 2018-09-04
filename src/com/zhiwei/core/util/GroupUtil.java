package com.zhiwei.core.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



public class GroupUtil {
	
	/**
	  * 集团版数据分离
	  * 获得comPanyId和userId
	  * 方法说明：(1).可以按分公司Id进行分离 。(2).可以按创建人createrId
	  * 不放在登录方法中是因为需要改动的地方太多，并且每个功能的request参数不一样.
	  * 需要将userList在登录的时候就放到session中，否则每访问一次就要查询一次数据库
	  */
	 public static Map<String,String> separateFactor(HttpServletRequest request,Object userIds){
		 Map<String,String> map=new HashMap<String,String>();
		 String companyId=null;
		 Long currentUserId=ContextUtil.getCurrentUserId();
		 String roleType = ContextUtil.getRoleTypeSession(); //获得当前登录用户所处角色
		 Boolean flag = Boolean.valueOf(AppUtil.getSystemIsGroupVersion());    //判断当前是否为集团版本
		 boolean isAll=false;
		 
		 boolean isShop=false;
		 String departmentId=(String) request.getSession().getAttribute("shopId");
		 if(null!=request.getParameter("isAll")){
			 isAll=request.getParameter("isAll").equals("true")?true:false;//授权是否查看所有(该公司下)
		 }
		 if(null!=request.getParameter("isShop")){
			 isShop = request.getParameter("isShop").equals("true")?true:false;
		 }
		 //超级管理员可以看到所有的数据
		 if(1!=currentUserId){
			if (flag) {// 表示为集团版本
				if (roleType.equals("control")) {// 具有管控角色
					companyId=ContextUtil.getBranchIdsStr();
				}else{
					companyId=ContextUtil.getLoginCompanyId().toString();
				}
			}else{
				companyId=ContextUtil.getLoginCompanyId().toString();
			}
		 }
		 if(null!=request.getParameter("companyId") && !"".equals(request.getParameter("companyId"))){
			 companyId=request.getParameter("companyId");
		 }
		 map.put("companyId", companyId);
		 if(!isAll) {//如果用户不拥有查看所有项目信息的权限
			 if(!isShop){//判断是否按门店分离，如果没有授权按门店分离，默认按上下级分离
				 if(null!=userIds && !"1".equals(userIds.toString())){
					 map.put("userId", userIds.toString());
				 }
			 }else{//如果授权按门店分离，则按门店分离数据
				 map.put("shopId", departmentId);
			 }
		}
		return map;
	 }
}
