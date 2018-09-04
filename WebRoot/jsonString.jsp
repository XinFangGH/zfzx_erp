<%@ page language="java" pageEncoding="UTF-8" 
import="java.util.*,java.io.OutputStream,com.zhiwei.core.util.ExportUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="#request.isExport==null || #request.isExport==false" >
	<s:property value="jsonString" escape="false" />
</s:if>
<s:else>
	<% 
		String exportType = (String)request.getAttribute("exportType");
		String fileName = (String)request.getAttribute("fileName");
		List<Object> list = (List<Object>)request.getAttribute("__exportList");
		OutputStream os = response.getOutputStream();
		String colId = (String)request.getAttribute("colId");
		String colName = (String)request.getAttribute("colName");

		if("xls".equals(exportType)){		
			response.setContentType("application/x-msdownload");
			response.setHeader("content-disposition","attachment; filename="+fileName+".xls");
			ExportUtil.ExportXls(list,os,colId,colName);
		}else{
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition","attachment;filename="+fileName+".pdf");
			ExportUtil.ExportPdf(list,os,colId,colName);
		}
		os.close();
        out.clear();
        out=pageContext.pushBody();
	%>
</s:else>