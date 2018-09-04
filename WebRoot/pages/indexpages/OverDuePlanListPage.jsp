<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="contentDiv">
<table class="newsList" cellpadding="0" cellspacing="0" rules="rows">
	<c:forEach var="overDue" items="${overDuePlanList}">
		<tr onMouseOver="this.className='over';" onMouseOut="this.className='out';">
			<td width="26">
				<img src="<%=request.getContextPath()%>/images/menus/flow/task.png"/>
			</td>
			<td>
				${overDue.bidPlanname}
			</td>
			<td width="80" nowrap="nowrap">${overDue.custmerName}</td>
			<td width="80" nowrap="nowrap">${overDue.compensatoryMoney}元</td>
			<td width="80" nowrap="nowrap"><a><fmt:formatDate
				value="${overDue.compensatoryDate}" pattern="yyyy-MM-dd" /></a></td>
		</tr>
	</c:forEach>
</table>
</div>
<div class="moreDiv"><span><a href="#"
	onclick="App.clickTopTab('compensatoryFianceAll')">更多...</a></span></div>