<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty position }">
	<table width="98%" class="table-detail" cellpadding="0" cellspacing="1">
		<tr>
			<th width="140">岗位名称</th>
			<td>${position.posName}</td>
		</tr>
		<tr>
			<th>岗位描述</th>
			<td>${position.posDesc }</td>
		</tr>
		<tr>
			<th>级别</th>
			<td>${position.depth }</td>
		</tr>
		<tr>
			<th>上级岗位</th>
			<td>
				<c:if test="${not empty supPos}">
					${supPos.posName}
				</c:if>
			</td>
		</tr>
		<tr>
			<th>拥有角色</th>
			<td>
				<c:forEach var="role" items="${position.roles}">
					${role.roleName}&nbsp;
					<input name="roleid" type="hidden" value="${role.roleId}">
					<input name="rolename" type="hidden" value="${role.roleName}">
					<input name="roledesc" type="hidden" value="${role.roleDesc}">
				</c:forEach>
			</td>
		</tr>
	</table>
</c:if>