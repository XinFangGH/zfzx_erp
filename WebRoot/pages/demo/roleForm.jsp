<%@page import="com.zhiwei.credit.service.system.AppRoleService"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.credit.model.system.AppRole"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/admin.css"/>
		<title>角色表单示例</title>
		<%
			AppRoleService appRoleService=(AppRoleService)AppUtil.getBean("appRoleService");
			//用于提交时进行获取表单参数
			AppRole appRole=new AppRole();
			String roleId=request.getParameter("roleId");
			//若原来已经存在，则加载
			if(StringUtils.isNotEmpty(roleId)){
				appRole=appRoleService.get(new Long(roleId));
			}
			
			if("POST".equals(request.getMethod())){//保存提交值
				String roleName=request.getParameter("roleName");
				String roleDesc=request.getParameter("roleDesc");
				String status=request.getParameter("status");
				appRole.setRoleName(roleName);
				appRole.setRoleDesc(roleDesc);
				appRole.setStatus(new Short(status));
				appRole.setIsDefaultIn((short)0);
				appRoleService.save(appRole);
			}
			
			request.setAttribute("appRole",appRole);

			String defId=request.getParameter("defId");
			String taskId=request.getParameter("taskId");
			
		%>
		<script type="text/javascript">
			window.onload=function(){
				//保存后，把业务实体的id保存至外窗口流程实例中
				<%
					if("POST".equals(request.getMethod())){
						if(defId!=null){
				%>
							parent.document.getElementById('defParams<%=defId%>').value="roleId:<%=appRole.getRoleId()%>";
				<%	
						}else{
				%>
							parent.document.getElementById('taskParams<%=taskId%>').value="roleId:<%=appRole.getRoleId()%>";
				<%
						}
					}
				%>
			};
		</script>
	</head>
	<body>
		<form action="roleForm.jsp" method="post">
			<table class="table-info" cellpadding="0" cellspacing="1" width="98%">
				<tr>
					<th align="center" colspan="2">角色详细信息</th>
				</tr>
				<tr>
					<th>角色名字</th>
					<td>
						<input type="hidden" name="defId" value="<%=defId%>"/>
						<input type="hidden" name="taskId" value="<%=taskId%>"/>
						<input type="hidden" name="roleId" value="${appRole.roleId}"/>
						<input name="roleName" type="text" width="400" value="${appRole.roleName}"/>
					</td>
				</tr>
				<tr>
					<th>角色状态</th>
					<td>
						<select name="status">
							<option value="1" <c:if test="${appRole.status==1}">selected</c:if>>激活</option>
							<option value="0" <c:if test="${appRole.status==0}">selected</c:if>>禁用</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						描述
					</th>
					<td>
						<textarea name="roleDesc" rows="5" cols="50">${appRole.roleDesc}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="保 存 "/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>