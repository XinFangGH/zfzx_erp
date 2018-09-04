<%@page import="com.zhiwei.core.util.RequestUtil"%>
<%//显示流程图片
	String defId=request.getParameter("defId");
	String fullUrl=request.getScheme() + "://" + request.getHeader("host") +  request.getContextPath()+"/jbpmImage?defId="+defId+"&genMap=true";
%>
<%@page pageEncoding="UTF-8"%>
<img src="<%=request.getContextPath()%>/jbpmImage?defId=<%=defId%>" usemap="#Map<%=defId%>">
	<map name="Map<%=defId%>">
		<%=RequestUtil.getHtml(fullUrl) %>
   	</map>
</img>
	
	