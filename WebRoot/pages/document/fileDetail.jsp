<%@ page pageEncoding="UTF-8"%>
<%
	//作用：用于显示附加文件的详细信息
	//作者：csx
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table-info" cellpadding="0" cellspacing="1" width="98%">
	<tr>
		<th width="20%" nowrap="nowrap">
			文件名
		</th>
		<td>
		${fileAttach.fileName}
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">文件路径</th>
		<td>
			${fileAttach.filePath}
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">
			上传者
		</th>
		<td>
			${fileAttach.creator }
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">
			备注
		</th>
		<td>
			${fileAttach.note}
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">上传时间</th>
		<td>
			<fmt:formatDate value="${fileAttach.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
	<tr>
		<th nowrap="nowrap">操作</th>
		<td>
			<c:choose>
				<c:when test="${fileAttach.delFlag == 0}">
					<img src="<%=request.getContextPath()%>/images/system/download.png"/>
					<a href="<%=request.getContextPath()%>/file-download?fileId=${fileAttach.fileId}" target="_blank">下载</a>
				</c:when>
				<c:when test="${fileAttach.delFlag == 1}">
					<font color="red">&nbsp;对不起，该文件已经被删除！</font>
				</c:when>
			</c:choose>
		</td>
	</tr>
</table>