<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
		PagingBean pb=new PagingBean(0,8);
		//查找发布的前五条新闻
		String sectionId = request.getParameter("sectionId");
		NewsService newsService = (NewsService)AppUtil.getBean("newsService");
		List<News> list = newsService.findImageNews(new Long(sectionId),pb);
		List<News> newsList=new ArrayList<News>();
		for(News news:list){
			String content=StringUtil.html2Text(news.getContent());
			if(content.length()>250){
				content=content.substring(0, 250);
			}
			news.setContent(content);
			newsList.add(news);
		}
		request.setAttribute("newsList",newsList);
%>

<%@page import="com.zhiwei.core.web.paging.PagingBean"%>
<%@page import="com.zhiwei.credit.service.info.NewsService"%>
<%@page import="java.util.List"%>
<%@page import="com.zhiwei.credit.model.info.News"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.zhiwei.core.util.StringUtil"%>
<%@page import="com.zhiwei.core.util.AppUtil"%><div class="contentDiv">
<table class="newsList" cellpadding="0" cellspacing="0" rules="rows">
	<c:forEach var="news" items="${newsList}">
		<tr onMouseOver="this.className='over';" onMouseOut="this.className='out';">
			<td width="26"><c:choose>
				<c:when test="${fn:length(news.subjectIcon)>0}">
					<img
						src="<%=request.getContextPath()%>/attachFiles/${news.subjectIcon}" />
				</c:when>
				<c:otherwise>
					<img
						src="<%=request.getContextPath()%>/images/default_newsIcon.jpg" />
				</c:otherwise>
			</c:choose></td>
			<td><a href="#"
				onclick="App.MyDesktopClickTopTab('NewsDetail',${news.newsId})">${news.subject}</a></td>
			<td width="80" nowrap="nowrap"><a>${news.author}</a></td>
			<td width="80" nowrap="nowrap"><a><fmt:formatDate
				value="${news.updateTime}" pattern="yyyy-MM-dd" /></a></td>
		</tr>
	</c:forEach>
</table>
</div>
<div class="moreDiv"><span><a href="#"
	onclick="App.clickTopTab('SearchNews')">更多...</a></span></div>