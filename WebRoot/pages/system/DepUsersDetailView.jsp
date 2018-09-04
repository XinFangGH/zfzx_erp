<%@ page pageEncoding="utf-8" %>
<%@ page import="com.zhiwei.core.util.AppUtil" %>
<%@ page import="java.util.List" %>
<!-- 
	description 根据userId查询该用户的所有部门信息
	author YHZ
	company www.credit-software.com
	createtime 2011-1-15PM
 -->
 <%
 	Long userId = new Long(request.getParameter("userId"));
 	//DepUsersService depUsersService = (DepUsersService)AppUtil.getBean("depUsersService");
 	//List<DepUsers> list = depUsersService.findByUserIdDep(userId);
 	String isMain = null; //主部门
 	String username = "";//用户名称
 	//if(list != null && list.size() > 0){
 	//	for(int i = 0; i < list.size(); i++){
 	//		DepUsers du = list.get(i);
	//		username = du.getAppUser().getUsername(); //用户名称
 	//		if(du.getIsMain() != null && du.getIsMain().equals(DepUsers.ISMAIN)){
 	//			isMain = du.getDepartment().getDepName(); //主部门
 	//			list.remove(i);
 	//		}
 	//	}
 	//	request.setAttribute("list",list);
 	//}else
 	//	request.setAttribute("list",null);

 	request.setAttribute("depName",isMain); //主部门
 	request.setAttribute("username",username);//用户名称
 %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table-info" align="center" cellpadding="0" cellspacing="1" width="98%">
	<tr>
		<th style="width:20%;">用户名称：</th>
		<td>${username }</td>
	</tr>
	<tr>
		<th style="width:20%;">主部门：</th>
		<c:if test="${empty depName}">
			<td style="color:red;">NULL</td>
		</c:if>
		<c:if test="${not empty depName}">
			<td>${depName }</td>
		</c:if>
	</tr>
	<tr>
		<th style="width:20%;">副部门：</th>
		<c:if test="${not empty list}" >
			<td>
				<c:forEach var="depUsers" items="${list}">
					${depUsers.department.depName }<br/>
				</c:forEach>
			</td>
		</c:if>
		<c:if test="${empty list}">
			<td style="color:red;">NULL</td>
		</c:if>
	</tr>
</table>