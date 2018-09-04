<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,com.zhiwei.core.util.AppUtil" %>
<%@ page import="com.zhiwei.credit.model.system.GlobalType" %>
<%
	String basePath = request.getContextPath();
	String uId = request.getAttribute("uId").toString();
	String flowType = request.getAttribute("flowType").toString();
	String postUrl = basePath + "/flow/flexDefSaveProDefinition.do?flowType="+flowType;
%>
<%
    // 获取流程类型,加载下拉框
    @SuppressWarnings("unchecked")
	List<GlobalType> record = (List<GlobalType>)request.getAttribute("record");
%>
<!-- 加载数据操作 -->

<%
	// 获取流程定义id
	String defId = "";
	String loadDateUrl = ""; // flex中获取加载数据的地址
	System.out.println(basePath);
	if(request.getAttribute("defId") != null) {
		defId = request.getAttribute("defId").toString(); // 流程id
		loadDateUrl = basePath + "/flow/flexGetProDefinition.do?defId=" + defId;
	}
%>

  <head>
  <link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
  	<title>
  		在线流程设计--${title}
  	</title>
  </head>
  <style type="text/css" media="screen"> 
	html, body	{ height:100%; }
	body { margin:0; padding:0; overflow:auto; text-align:center; 
	       background-color: #ffffff; }   
	#flashContent { display:none; }
  </style>
		   
       <script type="text/javascript" src="<%=basePath%>/pages/bpm/swfobject.js"></script>
       <script type="text/javascript">
           var swfVersionStr = "10.0.0";
           var xiSwfUrlStr = "<%=basePath%>/pages/bpm/playerProductInstall.swf";
           var flashvars = {};
           var params = {};
           params.quality = "high";
           params.bgcolor = "#ffffff";
           params.allowscriptaccess = "sameDomain";
           params.allowfullscreen = "true";
           var attributes = {};
           attributes.id = "bpmeditor";
           attributes.name = "bpmeditor";
           attributes.align = "middle";
           swfobject.embedSWF("<%=basePath%>/pages/bpm/bpmeditor.swf", "flashContent", 
               "100%", "100%", swfVersionStr, xiSwfUrlStr, flashvars, params, attributes);
		swfobject.createCSS("#flashContent", "display:block;text-align:left;");
		
    // 加载下拉框
	function loadData(){
		var str = new Array();
		str[0] = {id:'0',text:'总分类'};
		<%for(int i=0;i<record.size();i++){%>
			<%GlobalType tp = record.get(i);%>
			str[<%=i+1%>] = {id:'<%=tp.getProTypeId()%>',text:'<%=tp.getTypeName()%>'};
		<%}%>
		return str;
	}

	//供flex方向调用,获取提交数据的地址
	function getCtxPath(){
		return document.getElementById("postUrl").value; // 获取flex中提交数据请求的地址
	}
	
	// 供flex方向调用,关闭flex页面
	function closeFlexWindow(){
		window.close();
		opener.location.reload();
	}
	
	function getLoadDateUrl(){
		return document.getElementById("flexLoadDateUrl").value; // 获取flex中加载load数据请求地址
	}
  </script>
  <div>
  	<input id="postUrl" type="hidden" value="<%=postUrl%>" />
  	<input id="flexLoadDateUrl" type="hidden" value="<%=loadDateUrl%>" />
  </div>
  <div style="width:100%;height:50px;background-image:url('<%=basePath%>/images/f2.gif');padding-bottom:10px;">
  	<img alt="<%=AppUtil.getCompanyName()%>" src="<%=basePath+AppUtil.getCompanyLogo()%>" align="left"><h1 align="left" style="padding-top:10px;"> &nbsp;&nbsp;在线流程设计 </h1>
  </div>
  <div id="flashContent">
     	<p> To view this page ensure that Adobe Flash Player version 10.0.0 or greater is installed. </p>
		<script type="text/javascript"> 
		var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://"); 
		document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
						+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
		</script> 
   </div>
	   	
<noscript>
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="bpmeditor">
        <param name="movie" value="<%=basePath%>/pages/bpm/bpmeditor.swf" />
        <param name="quality" value="high" />
        <param name="bgcolor" value="#ffffff" />
        <param name="allowScriptAccess" value="sameDomain" />
        <param name="allowFullScreen" value="true" />
        <!--[if !IE]>-->
        <object type="application/x-shockwave-flash" data="<%=basePath%>/pages/bpm/bpmeditor.swf" width="100%" height="80%">
            <param name="quality" value="high" />
            <param name="bgcolor" value="#ffffff" />
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="allowFullScreen" value="true" />
        <!--<![endif]-->
        <!--[if gte IE 6]>-->
        	<p> 
        		Either scripts and active content are not permitted to run or Adobe Flash Player version
        		10.0.0 or greater is not installed.
        	</p>
        <!--<![endif]-->
            <a href="http://www.adobe.com/go/getflashplayer">
                <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
            </a>
        <!--[if !IE]>-->
        </object>
        <!--<![endif]-->
   </object>
</noscript>
