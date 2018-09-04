<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty org }">
	<table width="98%" class="table-detail" cellpadding="0" cellspacing="1">
		<tr>
			<th width="140">组织名称</th>
			<td>${org.orgName}</td>
		</tr>
		<tr>
			<th>组织描述</th>
			<td>${org.orgDesc }</td>
		</tr>
		<tr>
			<th>主要负责人</th>
			<td>${chargeUser }</td>
		</tr>
		<!--<tr>
			<th>级别</th>
			<td>${org.depth }</td>
		</tr> -->
		<tr>
			<th>维度</th>
			<td>${demName }</td>
		</tr>
		<tr>
			<th>上级组织</th>
			<td>
				<c:if test="${not empty supOrg}">
					${supOrg.orgName}
				</c:if>
			</td>
		</tr>
		<tr>
			<th>建立人</th>
			<td>${org.creatorId }</td>
		</tr>
		<tr>
			<th>建立日期</th>
			<td><fmt:formatDate value="${org.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<th>修改人</th>
			<td>
				${org.updateId}
			</td>
		</tr>
		<tr>
			<th>修改日期</th>
			<td>
				<fmt:formatDate value="${org.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
	</table>
</c:if>