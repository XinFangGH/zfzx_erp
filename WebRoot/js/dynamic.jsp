<%@ page contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
var __ctxPath="<%=request.getContextPath() %>";
var __p2pPath="<%=AppUtil.getP2pUrl()%>";
var __fullPath="<%=request.getScheme() + "://" + request.getHeader("host") +  request.getContextPath()%>";
var __fileURL="<%=AppUtil.configMap.get("fileURL")==null?"":AppUtil.configMap.get("fileURL").toString()%>";  