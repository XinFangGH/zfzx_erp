<%@ page pageEncoding="UTF-8"%>
<%
	String basePath=request.getContextPath();
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.credit.service.archive.ArchivesService"%>
<%@page import="com.zhiwei.credit.model.archive.Archives"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
	String archivesId = request.getParameter("archivesId");
	ArchivesService arService = (ArchivesService)AppUtil.getBean("archivesService");
	Archives arch=new Archives();
	if(StringUtils.isNotEmpty(archivesId)){
		arch=arService.get(new Long(archivesId));
	}
	request.setAttribute("arch",arch);
%>



<h3 align="center" title="公文标题">
	<font style="font:1.9em 幼圆  ;color:blue;font-weight: bold;">
		${arch.subject}
	</font>
	<font style="font:1.9em 幼圆  ;color:green;font-weight: bold;">
				(${arch.status})
	</font>
</h3> 

<span style="float:right;padding-right:12%;">
	创建日期:
	<fmt:formatDate value="${arch.createtime}" pattern="yyyy-MM-dd"/>
</span>

<table class="table-info" cellpadding="0" cellspacing="1" width="96%" align="center">
  <c:if test="${fn:length(arch.archivesDocs)>0}">
	  <tr>
	  	<th width="15%" rowspan="${fn:length(arch.archivesDocs)+1}">
	  		公文附件
	  	</th>
	  	<c:forEach var="doc" items="${arch.archivesDocs}">
	  	 	<tr>
	  	 		<td colspan="3">
	  	 			<img src="<%=request.getContextPath()%>/images/flag/attachment.png"/>
	      			<!-- <a href="#" onclick="new ArchivesDocForm({docPath :'${doc.fileAttach.filePath}'}).show();return false;"  target="_blank">${doc.docName}</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
	      			<a href="<%=request.getContextPath()%>/file-download?fileId=${doc.fileAttach.fileId}"  target="_blank">${doc.docName}</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  			</td>
	  		</tr>
	  	</c:forEach>
	  </tr>
  </c:if>
  <tr>
     <th width="15%">来文文字号</th>
     <td>${arch.archivesNo}</td>
     <th width="15%">发文机关</th>
     <td>${arch.issueDep}</td>
  </tr>
  <tr>
   	<th width="15%">发文人</th>
     <td>${arch.issuer}</td>
     <th width="15%">来文类型</th>
     <td>${arch.archivesType.typeName}</td>
  </tr>
  <tr>
  	 <th width="15%">公文来源</th>
     <td>${arch.sources}</td>
     <th width="15%">紧急程度</th>
     <td>${arch.urgentLevel}</td>
  </tr>
   <tr>
     <th width="15%">主题词</th>
     <td>${arch.keywords}</td>
     <th width="15%">秘密等级</th>
     <td>${arch.privacyLevel}</td>
  </tr>
  <tr>
  	 <th width="15%">签收部门</th>
  	 <td colspan="4">${arch.recDepNames}</td>
  </tr>
  <tr>
     <th width="15%">内容</th>
     <td colspan="4">${arch.shortContent}</td>
  </tr>
</table>

