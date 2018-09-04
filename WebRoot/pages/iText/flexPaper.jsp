<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.zhiwei.credit.model.document.Document" %>
<%@ page import="com.zhiwei.credit.model.system.FileAttach" %>
<%
	String basePath = request.getContextPath();
	Document document = (Document)request.getAttribute("document");
%>

  <head>
  	<title>
  		在线文档阅览--${document.docName}
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
           var xiSwfUrlStr = "<%=basePath%>/pages/iText/playerProductInstall.swf";
           var flashvars = {};
           var params = {};
           params.quality = "high";
           params.bgcolor = "#ffffff";
           params.allowscriptaccess = "sameDomain";
           params.allowfullscreen = "true";

           var attributes = {};
           attributes.id = "flexpaper";
           attributes.name = "flexpaper";
           attributes.align = "middle";
           swfobject.embedSWF("<%=basePath%>/pages/iText/flexpaper.swf", "flashContent", 
               "100%", "100%", swfVersionStr, xiSwfUrlStr, flashvars, params, attributes);
		swfobject.createCSS("#flashContent", "display:block;text-align:left;");
		
		function getDocumentInfo(){
			var documentInfo = new Object();
			documentInfo["docName"] = document.getElementById("hd_docName").value;      // 文档名称
			documentInfo['creator'] = document.getElementById("hd_creator").value;      // 创建人
			documentInfo['createtime'] = document.getElementById("hd_createtime").value; // 创建时间
			documentInfo['updatetime'] = document.getElementById("hd_updatetime").value;  // 更新时间
			documentInfo["swfFile"] = document.getElementById("hd_swfPath").value;        // swf文件的路径
			return documentInfo;
		}
		 // 加载下拉框
		function getFileAttachList(){
			var arr = new Array();
			<%	
			int i = 0;
			for(FileAttach tp : document.getAttachFiles()){%>
				arr[<%=i%>] = {
						fileId : '<%=tp.getFileId()%>',
						url : '<%=request.getContextPath() + "/attachFiles/" + tp.getFilePath()%>'
						,label : '<%=tp.getFileName()%>'
					};
			<% i++; }%>
			return arr;
		}
		 
		// 文件下载
		function attachFileDownLoad(fileId){
			window.open(document.getElementById("hd_basePath").value + "/file-downLoad?fileId=" + fileId);
		}
  </script>
 <div>
 	<input type="hidden" id="hd_basePath" value="<%=basePath%>" />
	<input type="hidden" id="hd_docName" value="${document.docName}" />	
	<input type="hidden" id="hd_creator" value="${document.appUser.fullname}"/>
	<input type="hidden" id="hd_createtime" value="${createtime}"/>
	<input type="hidden" id="hd_updatetime" value="${updatetime}"/>
	<input type="hidden" id="hd_swfPath" value="${swfPath}"/>
	<input type="hidden" id="hd_attachFileList" value="${attachFileList}">
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
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="flexpaper">
        <param name="movie" value="<%=basePath%>/pages/iText/flexpaper.swf" />
        <param name="quality" value="high" />
        <param name="bgcolor" value="#ffffff" />
        <param name="allowScriptAccess" value="sameDomain" />
        <param name="allowFullScreen" value="true" />
        <object type="application/x-shockwave-flash" data="<%=basePath%>/pages/iText/flexpaper.swf" width="100%" height="100%">
            <param name="quality" value="high" />
            <param name="bgcolor" value="#ffffff" />
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="allowFullScreen" value="true" />
        	<p> 
        		Either scripts and active content are not permitted to run or Adobe Flash Player version
        		10.0.0 or greater is not installed.
        	</p>
            <a href="http://www.adobe.com/go/getflashplayer">
                <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
            </a>
        </object>
   </object>
</noscript>
