<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table-info" cellpadding="0" cellspacing="1" width="98%"  align="center">
	<tr>
		<th style="height:27px">序号</th>
		<th>投票人</th>
		<th>投票时间</th>
		<th>投票意见</th>
	</tr>
	<c:forEach items="${signDataList}" var="data" varStatus="index">
		<tr>
			<td>${index.count}</td>
			<td>${data.voteName}</td>
			<td>
				<c:if test="${not empty data.voteTime}">
					<fmt:formatDate value="${data.voteTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</c:if>
			</td>
			<td>
				<c:choose>
					<c:when test="${empty data.isAgree}">
						<font color='gray'>尚未投票</font>
					</c:when>
					<c:when test="${data.isAgree==1}">
						<font color='gree'>同意</font>
					</c:when>
					<c:when test="${data.isAgree==2}">
						<font color='red'>拒绝</font>
					</c:when>
					<c:otherwise>
						<font color='gray'>弃权</font>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>	
</table>
