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
		List<News> newList=new ArrayList<News>();
		for(News news:list){
			String content=StringUtil.html2Text(news.getContent());
			if(content.length()>250){
				content=content.substring(0, 250);
			}
			news.setContent(content);
			newList.add(news);
		}
		request.setAttribute("imageNewsList",newList);
%>

<%@page import="com.zhiwei.core.web.paging.PagingBean"%>
<%@page import="com.zhiwei.credit.service.info.NewsService"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.zhiwei.credit.model.info.News"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.zhiwei.core.util.StringUtil"%><script>
	var $ = function(id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	};

	var Class = {
		create : function() {
			return function() {
				this.initialize.apply(this, arguments);
			}
		}
	}

	Object.extend = function(destination, source) {
		for ( var property in source) {
			destination[property] = source[property];
		}
		return destination;
	}

	var TransformView = Class.create();
	TransformView.prototype = {
		// 容器对象,滑动对象,切换参数,切换数量
		initialize : function(container, slider, parameter, count, options) {
			if (parameter <= 0 || count <= 0)
				return;
			var oContainer = $(container), oSlider = $(slider), oThis = this;

			this.Index = 0;// 当前索引

			this._timer = null;// 定时器
			this._slider = oSlider;// 滑动对象
			this._parameter = parameter;// 切换参数
			this._count = count || 0;// 切换数量
			this._target = 0;// 目标参数

			this.SetOptions(options);

			this.Up = !!this.options.Up;
			this.Step = Math.abs(this.options.Step);
			this.Time = Math.abs(this.options.Time);
			this.Auto = !!this.options.Auto;
			this.Pause = Math.abs(this.options.Pause);
			this.onStart = this.options.onStart;
			this.onFinish = this.options.onFinish;

			oContainer.style.overflow = "hidden";
			oContainer.style.position = "relative";

			oSlider.style.position = "absolute";
			oSlider.style.top = oSlider.style.left = 0;
		},
		// 设置默认属性
		SetOptions : function(options) {
			this.options = {// 默认值
				Up : true,// 是否向上(否则向左)
				Step : 5,// 滑动变化率
				Time : 10,// 滑动延时
				Auto : true,// 是否自动转换
				Pause : 4000,// 停顿时间(Auto为true时有效)
				onStart : function() {
				},// 开始转换时执行
				onFinish : function() {
				}// 完成转换时执行
			};
			Object.extend(this.options, options || {});
		},
		// 开始切换设置
		Start : function() {
			if (this.Index < 0) {
				this.Index = this._count - 1;
			} else if (this.Index >= this._count) {
				this.Index = 0;
			}

			this._target = -1 * this._parameter * this.Index;
			this.onStart();
			this.Move();
		},
		// 移动
		Move : function() {
			clearTimeout(this._timer);
			var oThis = this, style = this.Up ? "top" : "left", iNow = parseInt(this._slider.style[style]) || 0, iStep = this
					.GetStep(this._target, iNow);

			if (iStep != 0) {
				this._slider.style[style] = (iNow + iStep) + "px";
				this._timer = setTimeout(function() {
					oThis.Move();
				}, this.Time);
			} else {
				this._slider.style[style] = this._target + "px";
				this.onFinish();
				if (this.Auto) {
					this._timer = setTimeout(function() {
						oThis.Index++;
						oThis.Start();
					}, this.Pause);
				}
			}
		},
		// 获取步长
		GetStep : function(iTarget, iNow) {
			var iStep = (iTarget - iNow) / this.Step;
			if (iStep == 0)
				return 0;
			if (Math.abs(iStep) < 1)
				return (iStep > 0 ? 1 : -1);
			return iStep;
		},
		// 停止
		Stop : function(iTarget, iNow) {
			clearTimeout(this._timer);
			this._slider.style[this.Up ? "top" : "left"] = this._target + "px";
		}
	};

	//window.onload=function() {
	function Each(list, fun) {
		for ( var i = 0, len = list.length; i < len; i++) {
			fun(list[i], i);
		}
	};

	var objs = $("idNum").getElementsByTagName("li");
	//图片的数量
	//var picCounts=5;
	var picCounts = objs.length;
	if(picCounts>0){
	var tv = new TransformView("idTransformView", "idSlider", 300, picCounts, {
		onStart : function() {
			Each(objs, function(o, i) {
				o.className = tv.Index == i ? "on" : "";
			})
		}// 按钮样式
		});

	tv.Start();
    
	Each(objs, function(o, i) {
		o.onmouseover = function() {
			o.className = "on";
			tv.Auto = false;
			tv.Index = i;
			tv.Start();
		}
		o.onmouseout = function() {
			o.className = "";
			tv.Auto = true;
			tv.Start();
		}
	})
	}
	//}
</script>

<div class="container" id="idTransformView">
<ul class="slider" id="idSlider">
	<c:forEach var="news" items="${imageNewsList}">
		<li class="newLi">
		<a href="#"
			onclick="App.MyDesktopClickTopTab('NewsDetail',${news.newsId})">
		<div class="newsdisplay" title="${news.subject}">
		<c:choose>
			<c:when test="${fn:length(news.subjectIcon)>0}">
				<img class="newsdisplayImg"
					src="<%=request.getContextPath()%>/attachFiles/${news.subjectIcon}"
					alt="${news.subject}"/>
			</c:when>
			<c:otherwise>
				<img class="newsdisplayImg"
					src="<%=request.getContextPath()%>/images/default_newsIcon.jpg"
					alt="${news.subject}" />
			</c:otherwise>
		</c:choose> 
		<div class="newsdisplayDiv">
		    <h2 align="center">${news.subject}</h2><br/>
		    <p style="line-height: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;${news.content}...</p>
		</div>
		</div>
		</a>
		</li>
	</c:forEach>
</ul>
<ul class="num" id="idNum">
	<c:forEach var="number" end="${fn:length(imageNewsList)}" begin="1"
		step="1">
		<li>${number}</li>
	</c:forEach>
</ul>
</div>

