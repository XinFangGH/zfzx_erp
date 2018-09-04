<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.zhiwei.core.util.AppUtil" %>
<%@ page import="com.zhiwei.core.util.XmlUtil" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.zhiwei.credit.model.system.GlobalType,com.zhiwei.credit.service.system.GlobalTypeService" %>
<%@ page import="com.zhiwei.credit.model.flow.ProDefinition,com.zhiwei.credit.service.flow.ProDefinitionService" %>
<%@ page import="com.zhiwei.core.jbpm.jpdl.JpdlConverter" %>
<%@ page import="org.dom4j.Element" %>
<%@ page import="com.zhiwei.credit.service.bpm.ILog.factory.BpmFactory" %>
<%
	String basePath = request.getContextPath();
	String uId = request.getParameter("uId");
%>

<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	// 判断是保存,还是发布
	boolean deploy = Boolean.parseBoolean(request.getParameter("deploy"));
	// start 属性设置
	ProDefinition proDefinition = new ProDefinition();
	String proTId = request.getParameter("proTypeId");
	if(StringUtils.isNotEmpty(proTId)) // 流程类型编号
		proDefinition.setProTypeId(Long.valueOf(proTId));
	String name = request.getParameter("name");
	if(StringUtils.isNotEmpty(name)) { // 流程名称
		String nameStr = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		proDefinition.setName(nameStr); 
	}
	String processName = request.getParameter("processName");
	if(StringUtils.isNotEmpty(processName)) // JBPM流程key值
		proDefinition.setProcessName(processName);
	String status = request.getParameter("status");
	if(StringUtils.isNotEmpty(status)) // 流程状态
		proDefinition.setStatus(Short.valueOf(status));
	String description = request.getParameter("description");
	if(StringUtils.isNotEmpty(description)) { // 流程描述
		String des = new String(description.getBytes("ISO-8859-1"),"UTF-8");
		proDefinition.setDescription(des);
	}
	String drawDefXml = request.getParameter("drawDefXml"); // xml
	if(StringUtils.isNotEmpty(drawDefXml)) {
		String xmlStr = new String(drawDefXml.getBytes("ISO-8859-1"),"UTF-8");
		proDefinition.setDrawDefXml(xmlStr);
	}
	ProDefinitionService proDefinitionService = (ProDefinitionService)AppUtil.getBean("proDefinitionService");
	// end 属性设置
	
	String msg = proDefinitionService.defSave(proDefinition,deploy); // 操作描述信息
	response.getWriter().println(msg);
%>