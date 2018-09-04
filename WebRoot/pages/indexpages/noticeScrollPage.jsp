<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	PagingBean pb = new PagingBean(0,8);
	//查找发布的前五条新闻
	String sectionId = request.getParameter("sectionId");
	NewsService newsService = (NewsService)AppUtil.getBean("newsService");
	List<News> list = newsService.findImageNews(new Long(sectionId),pb);
	List<News> newList=new ArrayList<News>();
	for(News news:list){
		String content=StringUtil.html2Text(news.getContent());
		if(content.length()>250){
			content=content.substring(0, 250);
		}
		news.setContent(content);
		newList.add(news);
	}
	request.setAttribute("noticeList", newList);
%>


<%@page import="com.zhiwei.core.web.paging.PagingBean"%>
<%@page import="com.zhiwei.credit.service.info.NewsService"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.zhiwei.credit.model.info.News"%>
<%@page import="com.zhiwei.core.util.StringUtil"%>
<%@page import="java.util.ArrayList"%><div class="noticeDisplayDive">
	<div
		id="rollText">
		<div id="rollDiv" style="width:100%;">
		<c:forEach var="notice" items="${noticeList}">
			 <div class="noticeItem">
				<h1 align="center" class="noticeSubject">${notice.subject }</h1><br/>
				<p align="center" class="noticesubSubject"><span>发布人：${notice.author }</span><br/><span>有效时间：<fmt:formatDate
					value="${notice.createtime}" pattern="yyyy-MM-dd" />&nbsp;至&nbsp;<fmt:formatDate
					value="${notice.expTime }" pattern="yyyy-MM-dd" /></span></p>
				<div class="noticeContent"><p style="line-height: 20px;">${notice.content }</p></div>
			</div>
		</c:forEach>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/ScrollText.js"></script>
<div class="moreDiv"><span><a href="#"
	onclick="App.clickTopTab('SearchNotice')">更多...</a></span></div>

