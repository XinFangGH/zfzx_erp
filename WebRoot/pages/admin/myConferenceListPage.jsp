<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String basePath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="contentDiv">
<table class="newsList" cellpadding="0" cellspacing="0" rules="rows">
	<c:forEach var="cf" items="${myConferenceList}">
		<tr>
			<td width="26"><img src="<%=basePath%>/images/menus/admin/conference_myjoin.png"></td>
			<td><a href="#"
				onclick="eval(ConferenceDetailForm.show(${cf.confId }))">${cf.confTopic}</a></td>
			<td nowrap="nowrap">${cf.roomLocation }</td>
			<td nowrap="nowrap" style="width:80px;"><a>
			<fmt:formatDate value="${cf.startTime}" pattern="yyyy-MM-dd" /></a></td>
		</tr>
	</c:forEach>
</table>
</div>
<div class="moreDiv"><span><a href="#"
	onclick="App.clickTopTab('MyJoinConferenceView')">更多...</a></span></div>