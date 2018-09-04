<%@ page pageEncoding="UTF-8" import="com.zhiwei.credit.service.flow.FormDefService"%>
<%@ page import="com.zhiwei.credit.model.flow.FormDef,com.zhiwei.core.util.AppUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//description:表单详细信息阅览
	//author：YHZ
	//data：2010-12-29PM
%>
<%
	Long formDefId = Long.valueOf(request.getParameter("formDefId"));
	FormDefService formDefService = (FormDefService)AppUtil.getBean("formDefService");
	FormDef formDef = formDefService.get(formDefId);
	request.setAttribute("formDef" , formDef);
%>
<table class="table-info" cellpadding="0" cellspacing="1" width="98%" height="98%"
	align="center">
	<tr>
		<th width="20%">表单标题：</th>
		<td>${formDef.formTitle }</td>
	</tr>
	<tr>
		<th width="20%">表单描述：</th>
		<td>${formDef.formDesp }</td>
	</tr>
	<tr>
		<th width="20%">定义HTML：</th>
		<td>${formDef.defHtml}</td>
	</tr>
	<tr>
		<th width="20%">表单状态:</th>
		<td><c:if test="${formDef.status == 0 }">草稿</c:if> <c:if
			test="${formDef.status == 1 }">正式</c:if></td>
	</tr>
	<tr>
		<th width="20%">表单类型：</th>
		<td><c:if test="${formDef.formType == 1}">单表</c:if> <c:if
			test="${formDef.formType == 2}">主从表</c:if> <c:if
			test="${formDef.formType == 3}">多表</c:if></td>
	</tr>
	<tr>
		<th width="20%">是否缺省：</th>
		<td><c:if test="${formDef.isDefault == 0}">否</c:if> <c:if
			test="${formDef.isDefault == 1}">是</c:if></td>
	</tr>
	<tr>
		<th width="20%">是否已经生成实体：</th>
		<td><c:if test="${formDef.isGen == 1}">是</c:if> <c:if
			test="${formDef.isGen == 0 }">否</c:if></td>
	</tr>
</table>