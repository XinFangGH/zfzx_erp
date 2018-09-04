<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String basePath=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all-notheme.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-patch.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Portal.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.UploadDialog.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/ux-all.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/caltask/calendar.css" />

<script language="javascript" >

var gpObj = window.opener.gpObj;
document.write("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><style type='text/css'>html,body{height:100%;}</style></head>");
document.write(gpObj.innerHTML);

var gpElems = new Array();
gpElems = mergeElems(gpElems,gpObj.getElementsByTagName("input"));
gpElems = mergeElems(gpElems,gpObj.getElementsByTagName("textarea"));

var curElems = new Array();
curElems = mergeElems(curElems,document.getElementsByTagName("input"));
curElems = mergeElems(curElems,document.getElementsByTagName("textarea"));

for(var index=0;index<curElems.length;index++){
	curElems[index].value = gpElems[index].value;
	curElems[index].readOnly=true;
}

function mergeElems(oldElems,newElems){
	for(var index=0;index<newElems.length;index++){
		oldElems.push(newElems[index]);
	}
	return oldElems;
}

var tags = document.getElementsByTagName("textarea");
for(var i=0;i<tags.length;i++){
	if(tags[i].style.height!=""){
		tags[i].style.height = tags[i].scrollHeight+"px";
	}
}

setTimeout("resizeBody()",1000);

function resizeBody(){
	if(window.opener.printFlag){
	
		var fckeditorIframe = window.opener.fckeditorIframe;
		
		if(fckeditorIframe!=null){
			var gpFckContentBody = fckeditorIframe.contentDocument.getElementsByTagName("iframe")[0].contentDocument.body;
			var curFckEditor = document.getElementById(fckeditorIframe.id);
			var curFckContentBody = curFckEditor.contentDocument.getElementsByTagName('iframe')[0].contentDocument.body;
			curFckContentBody.innerHTML = gpFckContentBody.innerHTML;
			curFckEditor.style.height = (curFckEditor.offsetHeight - curFckContentBody.clientHeight 
					+ curFckContentBody.scrollHeight) + "px";
		}
		window.opener.fckeditorIframe = null;
		
		var divs = document.getElementsByTagName("div");
		for(var i=0;i<divs.length;i++){
			if(divs[i].style.height!=""){
				divs[i].style.height = divs[i].scrollHeight+"px";
			}
		}
		
		var tmpIndex = 0;
		var exerObj;
		for(var i=0;i<divs.length;i++){
			if(divs[i].style.height!=""){
				if(tmpIndex==1){
					divs[i].style.height = "auto";
					exerObj = divs[i];
				}
				tmpIndex++;
			}
		}
		
		for(var i=0;i<divs.length;i++){
			if(divs[i].style.height!=""){
				divs[i].style.height = (divs[i].offsetHeight+exerObj.offsetHeight)+"px";
				break;
			}
		}
	
		window.opener.printFlag = false;
	}else{
		var divs = document.getElementsByTagName("div");
		for(var i=0;i<divs.length;i++){
			if(divs[i].style.height!=""){
				divs[i].style.height = "auto";
			}
		}
	}

	window.print();
}


</script>
