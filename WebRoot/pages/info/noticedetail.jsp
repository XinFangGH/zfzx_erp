<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.*"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.core.web.paging.PagingBean"%>
<%@page import="com.zhiwei.core.command.QueryFilter"%>
<%
%>
<table width="98%" cellpadding="0" cellspacing="1" style="border: 5px 5px 5px 5px;">
	<tr>
		<td align="center" style="font:2.0em 宋体  ;color:black;font-weight: bold;padding:10px 0px 10px 0px; ">
			${notice.noticeTitle}
			<input type="hidden" value="${__haveNextNoticeFlag}" id="__haveNextNoticeFlag"/>
			<input type="hidden" value="${notice.noticeId }" id="__curNoticeId"/>
		</td>
	</tr>
	
	<tr>
		<td align="center" style="padding:0px 0px 10px 0px;">
				发布人:
			<font color="green">
				${notice.postName}
			</font> 
				&nbsp;生效日期:
			<font color="red">
				<fmt:formatDate value="${notice.effectiveDate}"pattern="yyyy-MM-dd" />
			</font>
				&nbsp;——
			<font color="red">
				<fmt:formatDate value="${notice.expirationDate}"pattern="yyyy-MM-dd" />
			</font>
		</td>
	</tr>
	<tr>
		<td style="border-top:dashed 1px #ccc;" height="28">
		</td>
	</tr>
	<tr >
		<td style="font:13px 宋体;color: black;line-height:24px;">
			${notice.noticeContent}
		</td>
	</tr>
</table>