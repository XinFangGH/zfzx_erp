<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.zhiwei.credit.model.flow.FormTemplate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Long taskId=(Long)request.getAttribute("taskId");
	Long defId=(Long)request.getAttribute("defId");
	
	FormTemplate formTemplate=(FormTemplate)request.getAttribute("formTemplate");
	Map formVars=(Map)request.getAttribute("formVars");
	String url=formTemplate.getFormUrl();
	if(StringUtils.isNotEmpty(url)){
		if(!url.startsWith("http")){//采用相对路径，则表示引用为本系统中的表单
			url=request.getScheme() + "://" + request.getHeader("host") +  request.getContextPath()+url;
		}
	}
	if(url.indexOf("?")==-1){
		url+="?";
	}else{
		url+="&";
	}
	
	if(defId!=null){
		url+="defId="+defId;
%>
	<input type="hidden" name="defParams" id="defParams<%=defId%>" />
<%
	}else{
		url+="taskId="+taskId;
%>
	<input type="hidden" name="taskParams" id="taskParams<%=taskId%>" />
<%
	}
	if(formVars!=null){
		Iterator<String> it= formVars.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			Object value=(Object)formVars.get(key);
			System.out.println("key:" + key + " VALUE:"+ value);
			if(value!=null){
				url=url.replace("${"+key+"}", value.toString());
			}
		}
	}
%>

<iframe frameborder="0" width="100%" height="100%" src="<%=url%>" onload="this.height=this.contentWindow.document.body.scrollHeight"></iframe>