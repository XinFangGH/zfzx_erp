<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String basePath=request.getContextPath();
%>
<script type="text/javascript" src="<%=basePath%>/js/system/DiaryDetail.js"></script>
<div class="contentDiv">
<table class="newsList" cellpadding="0" cellspacing="0" rules="rows">
	<c:forEach var="accountList" items="${accountList}">
		<tr onMouseOver="this.className='over';" onMouseOut="this.className='out';">
			<td><a href="#"	onclick="eval(new DiaryDetail(${diary.diaryId}))"><%--用户名：<c:out value="${accountList.loginname}"/> &nbsp;&nbsp;
							--%>真实姓名：<c:out value="${accountList.accountName}"/>&nbsp;&nbsp;
							第三方账号：<c:out value="${accountList.thirdPayConfigId}"/>&nbsp;&nbsp;
							对账日期：<c:out value="${accountList.checkDate}"/>
							
			<%--<br></br>平台资金：账户余额：<c:out value="${accountList.totalMoney}"/>&nbsp;&nbsp;&nbsp;&nbsp;
									   冻结金额：<c:out value="${accountList.freezeMoney}"/>&nbsp;&nbsp;&nbsp;&nbsp;
									   可用余额：<c:out value="${accountList.availableInvestMoney}"/>
			<br></br>第三方资金：账户余额：<c:out value="${accountList.thirdTotalMoney}"/>&nbsp;&nbsp;&nbsp;&nbsp;
									   冻结金额：<c:out value="${accountList.thirdFreezyMoney}"/>&nbsp;&nbsp;&nbsp;&nbsp;
									   可用余额：<c:out value="${accountList.thirdAviableMoney}"/>
									   <br></br>
									   --%></a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<div class="moreDiv"><span><a href="#"
	onclick="App.clickTopTab('SystemAccountIsException')">更多...</a></span></div>