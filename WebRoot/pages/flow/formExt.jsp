<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//为表单定义模板可以加上的其他变量或参数
%>
<c:set var="areaName" value=""/>
<c:choose>
	<c:when test="${not empty taskId}">
		<c:set var="areaName" value="formTaskExt${taskId}"/>
	</c:when>
	<c:otherwise>
		<c:set var="areaName" value="formExt${defId}"/>
	</c:otherwise>
</c:choose>
<textarea cols="40" rows="10" id="${areaName}" style="display:none;">
${formUiJs}
</textarea>