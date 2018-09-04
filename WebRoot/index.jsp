<%@page import="com.zhiwei.credit.service.system.AppUserService"%>
<%@page import="com.zhiwei.credit.service.system.AppRoleService"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.core.util.ContextUtil"%>
<%@page import="java.util.Map"%>

<%
	String basePath=request.getContextPath();
	//登录成功后，需要把该用户显示至在线用户
	AppUtil.addOnlineUser(request.getSession().getId(), ContextUtil.getCurrentUser());
	AppUserService appUserService=(AppUserService)AppUtil.getBean("appUserService");
	AppRoleService appRoleService=(AppRoleService)AppUtil.getBean("appRoleService");
    String vids=appRoleService.getControlCompanyId(ContextUtil.getCurrentUser());
	String vroleType=ContextUtil.getRoleTypeSession();
    //String systemType=AppUtil.getSystemIsOAVersion(); 
	//String OAorTeam=ContextUtil.getSystemOAorTeam(); 
	String p2pUrl=AppUtil.getP2pUrl();
	String loginOutInfo=ContextUtil.loginOutInfo;
	String CurrentUserName=ContextUtil.getUsLoginSession();
	String shopId=(String)request.getSession().getAttribute("shopId");
	String shopName=(String)request.getSession().getAttribute("shopName");
	Map<?,?> configMap=AppUtil.getConfigMap();
	String systemName=AppUtil.getSystemName();
%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
<% if(null!=configMap && null!=configMap.get("iconFile")){
	%>
		<link rel="shortcut icon" href="<%=configMap.get("iconFile")%>" />
	<%	
}else{
	%>
		<link rel="shortcut icon" href="favicon.ico" />
	<%	
} %>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="msthemecompatible" content="no">
		<title><%=AppUtil.getSystemName()%></title>
        <!--不能延迟加载-->
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all.css"/>				
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/ux-all.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/layout.css"/>
		<!-- 首页样式加载 -->
    	<link href="<%=basePath%>/css/desktop.css" rel="stylesheet" type="text/css" />	
    	
    	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/ueditor/ueditor.config.js"></script>
     	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/ueditor/ueditor.all.min.js"> </script>
    	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
     	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/ueditor/lang/zh-cn/zh-cn.js"></script>
    	
		<!-- load the extjs libary -->
		<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
		<!-- Ext 核心JS -->
		<!-- 生产环境使用以下两个js -->
		<script type="text/javascript" src="<%=basePath%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-all.js"></script>
		<!-- 开发环境使用以下两个js -->
		<!--<script type="text/javascript" src="<%=basePath%>/ext3/adapter/ext/ext-base-debug.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-all-debug.js"></script>  -->
		
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-basex.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-lang-zh_CN.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/RowExpander.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/TreeFilterX.js"></script>
		<!--使用iframe加载的依赖JS  -->
		<script type="text/javascript" src="<%=basePath%>/ext3/miframe.js"></script>
		
		<!-- ExtJS总计组件 css和js -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.grid.GridSummary.css"/>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.GridSummary.js"></script>
		<!-- ExtJS进度条组件css和js-->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.grid.ProgressColumn.css"/>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.ProgressColumn.js"></script>
				
		<!-- 需要的 JS-->
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/financingAgency/PlBidPlanView.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/App.import.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/XmlTreeLoader.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/TabCloseMenu.js"></script>		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/DateTimeField.js"></script>
	    <!-- 登录信息,工具JS -->
		<script type="text/javascript" src="<%=basePath%>/js/team/App.Init.Team.js"></script>	
		<script type="text/javascript" src="<%=basePath%>/js/App.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/navigateUtil.js"></script>
		
	     <!-- 个人首页JS -->
	    <script type="text/javascript" src="<%=basePath%>/js/team/IndexPage.Team.js"></script>	
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/common/CreditFlowCommon.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/common/ValidateIdCard.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/document/CreditDocumentManagerZW.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/contract/SlContractView.js"></script>
	        
	     <script type="text/javascript" src="<%=basePath%>/js/jsloader.js"></script>	
	    <script type="text/javascript" src="<%=basePath%>/highchart/jQuery/jquery-1.8.2-min.js"></script>
	    <script type="text/JavaScript" src="<%=basePath%>/highchart/jQuery/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/highchart/highcharts.js"></script>
		<%--<script type="text/javascript" src="<%=basePath%>/highchart/modules/exporting.js"></script>--%>
		
		<script type="text/javascript" src="<%=basePath%>/highchart/highcharts-3d.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/highchart/css/deskIndex.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/highchart/css/deskLess.css"/>
		
		
		<%--<script type="text/javascript" src="<%=basePath%>/highchart/ownChart/Calendar3.js"></script>--%>
		
		<script>window.onload = function() {  setTimeout(function(){ importJs(); }, 1000);  }</script> 
	 
	</head>
	<body oncontextmenu="return false">
        <div class="header" id="app-header">
			<div class="header_left">
				<div class="logo">
					<% if(null!=configMap && null!=configMap.get("index_logoFile")){
						%>
							<img src="<%=configMap.get("index_logoFile") %>" />
						<%	
					}else{
						%>
							<img src="images/imagesteam/logo.png" />
						<%	
					} %>

				</div>
				<div class="systemname">
					<%=AppUtil.getSystemName()%>
				</div>
			</div>
			<div class="header_right">
        		<!--<div class="welcome">
        			<span class="org-clor" onclick="App.ClickTaskCount()" ><em class="word-icon"></em>待办<i id="taskCount">(0)</i></span>
	        		<span>您好，<a href="#" class="top_ctrl"  onclick="changeCurrentUser()" ><security:authentication property="principal.fullname"/>!</a></span>&nbsp;&nbsp;
	        		<span onclick = "App.Logout()" class="exit"></span>
	        		<span  style=" cursor:pointer; text-decoration:underline;color:red;" onclick = "App.MyDesktopClickTopTab('PlBidPlanView')" >已齐标项目(<span id="bidSum"></span>)件</span>&nbsp;&nbsp;
	        		<a  style=" cursor:pointer; text-decoration:underline" onclick = "changeShowDesk(this)" >工作桌面</a>
	        	</div>-->
				<div class="header_rit">
					<% if (vids!="") {%>
					<div class="operating"  <%if ( !vroleType.equals("") && vroleType.equals("business")) {%> style="display:block"  <%}else{%> style="display:none" <%}%> id="control_div">
					       <a href="#" class="top_ctrl"  onclick="changeRoleType('control')"  >管控管理</a>
					</div>
					<div class="operating"  <%if ( !vroleType.equals("") && vroleType.equals("control")) {%> style="display:block"   <%}else{%> style="display:none" <%}%> id="business_div">
					       <a href="#" class="top_business"  onclick="changeRoleType('business')">业务管理</a>
					</div>
					<%}%>
					
					<!--<div class="operating">
						<a  href="#" onclick = "changeShowDesk(this)" >工作桌面</a>
					</div>
					--><div class="operating" id="customerManger">
						<a href="#" class="customerManger"  onclick="customerManger(this)" >客户中心</a>
					</div>
					<!--<div class="operating" id="marketingManger">
						<a href="#" class="customerManger"  onclick="MarketingManger(this)" >营销中心</a>
					</div>-->
					<!-- <div class="operating" id="smallHandler">
						<a href="#" class="smallHandler"  onclick="showNewMenus(this,'loan')" >业务中心</a>
					</div> -->
					
					<div class="operating" id="faManger">
						<a href="#" class="faManger"  onclick="showNewMenus(this,'fa')">网贷中心</a> 
					</div>
					<div class="operating" id="p2pManger" >
						<a href="#" class="p2pManger"  onclick="p2pManger(this)" >运营中心</a>
					</div>
					<!-- <div class="operating" id="controlManger" >
						<a href="#" class="controlManger"  onclick="controlManger(this)" >风控中心</a>
					</div>  -->
					<div class="operating" id="reportManger" >
						<a href="#" class="reportManger"  onclick="reportManger(this)" >台账报表</a>
					</div>
					<!--<div class="operating" id="ERPManger">
						<a href="#" class="ERPManger"  onclick="ERPManger(this)" >系统管理</a>
					</div>
					--><div class="operating">
						<a href="#"  class="myFlow"  onclick="App.ClickTaskCount(this)" >待办任务<em class="do"  id="taskCount">0</em></a>
					</div>
					<!--
					<div class="operating">
						<a href="#" class="reset_password" onclick="showMenus(this)">切换系统</a>
					</div> 

					<div class="operating"  >
					    <a href="#" class="loan_application"  onclick="creditAssignmentHandler()">债权转让</a>
					</div>
					
					<div class="operating"  >
					    <a href="#" class="loan_application"  onclick="financingHandler()">投资管理</a>
					</div>
					<div class="operating">
						<a href="#" class="loan_application"  onclick="interentFinanceHandler()"  >互联网金融</a>
					</div>				
					-->
					<div class="away">
						<img src="images/imagesteam/header_tb05.gif" title="收起头部" onclick="App.TopcollapseTeam(this)" />
					</div>
				</div>
			</div>
		</div>
	</body>
			
	<script  src="<%=basePath%>/js/importIndex.js"></script>
	
	<script type="text/javascript">
		 var p2pUrl='<%=p2pUrl%>'
	     function changeRoleType(roleType){
              var url='<%=request.getContextPath()%>'+"/system/changeRoletypeAppRole.do";
  			  Ext.Ajax.request({
					url:url,
					method:"post",
					success:function(response,opts){
						 var obj = Ext.decode(response.responseText);
						 App.ReLoadHomeTeam (obj.userInfo)
						 if(roleType=="business"){
						     RoleType="business";
				             document.getElementById("business_div").style.display= "none"; //隐藏
				             document.getElementById("control_div").style.display= "block"; //显示
						 }else if(roleType="control"){
						     RoleType="control";
				             document.getElementById("control_div").style.display= "none"; //隐藏
				             document.getElementById("business_div").style.display= "block"; //显示
						 }
					},
					params:{
						roleType:roleType
					} 
			 })
	     }
	     
	     function showMessage(){
			new MessageWin().show();
	     }

	     function alertFince(fid){
	         	var saveMx= new Ext.Button({
		     		text : '保存',
		     		iconCls : 'saveIcon',
		     		handler: function(){
			     		var v=Ext.getCmp('MXFince').getValue();
			    		document.getElementById(fid.id).value=v;
			     		MXW.close();
			   	 	}
			 	});
			 
	 		 	var formPanel = new Ext.FormPanel({
					layout : 'column',
					border : false,
					region : 'north',
					height : 150,
					anchor : '100%',
					frame : true,
					items : [{
						xtype : 'textarea',
						width:270,
						anchor : '100%',
						name : "MXFince",
						id:"MXFince"
					}]
			  	})
			  
			  	var MXW=new Ext.Window({
		      		title : '明细',
		        	width: 300,
		        	modal : true, 
		        	height : 150,
		        	items:[formPanel],
		        	"close":function(){ 
             			var v=Ext.getCmp('MXFince').getValue();
		     			document.getElementById(fid.id).value=v;
		    			 MXW.hide();
					}
				});
				MXW.show();
				var fv=document.getElementById(fid.id).value;
		    	if(fv!=''){
		     		Ext.getCmp('MXFince').setValue(fv);
		    	}
	   	 }
	
	     var hurong_flowType = "<%=AppUtil.getFlowType()%>";
	     var __companyName="<%=AppUtil.getCompanyName()%>";
	     var systemName="<%=AppUtil.getSystemName()%>";
	     var userInfo="<%=appUserService.getCurUserInfo()%>";
	     var currentUserId = "<%=ContextUtil.getCurrentUser().getUserId()%>";
	     var currentUserFullName = "<%=ContextUtil.getCurrentUser().getFullname()%>";
	     var ids="<%=appRoleService.getControlCompanyId(ContextUtil.getCurrentUser())%>";
	     var RoleType="<%=ContextUtil.getRoleTypeSession()%>";
	     var SystemType="<%=ContextUtil.getSysTypeSession()%>";
	     var isGroup = "<%=AppUtil.getSystemIsGroupVersion()%>";
       
	     var initDeskMenus="<%=AppUtil.getDeskMenus()%>"; //menu-all.xml中定义的菜单集合 @desk
	    	 
	     var shopId='<%=shopId%>';
	     var shopName='<%=shopName%>';
		  		     
	     var window_select=null;
	     var form1 =new Ext.form.FormPanel({
       			labelAlign:'right',
        		labelWidth:80,
        		frame:true,
       			items:[{
	          		xtype:"combo",
	           		id:'temp1',
	                fieldLabel: '角色',
	                name: 'combo',
	                value:null,
	                triggerAction : 'all',
	                store: new Ext.data.SimpleStore({
	                     fields: ['value', 'text'],
	                     data: [
	                         ['control', '管控角色'],
	                         ['business', '业务角色']
	                     ]
	                 }),
	                 displayField: 'text',
	                 valueField: 'value',
	                 mode: 'local',
	                 emptyText:'---请选择---'
	       	     },{
					 xtype:'label',value:'daff',tpl:'daffffffffffffffff'
				 }]
       	 });
       	 
	     if(null!=ids && ""!=ids){
		       if(RoleType==""){
             	    window_select = new Ext.Window({
						title : '选择操作角色',
						height : 102,
						constrainHeader : true,
						frame : true,
						closable : false,
						border : false,
						buttonAlign : 'center',
						width : 352,
						resizable : true,
						modal : true,
						items:[
     						form1
						],
						buttons:[{
						 	iconCls : 'btn-ok',
	            		 	text:'确认',
	           			 	handler:function(){
	           			    	var s=Ext.getCmp('temp1').getValue();
	           			    	if(null==s || ""==s){  
	           			    		Ext.ux.Toast.msg('操作信息', '请选择角色类型！');
	           			       		return false;
	           			    	}
	           			    	RoleType=s;
	           			   	 	var url='<%=request.getContextPath()%>'+"/system/setAuthAppRole.do";
	           			    	Ext.Ajax.request({
       								url:url,
         							method:"post",
         							success:function(response,opts){
         							     var data = Ext.decode(response.responseText);
         							     var isFalg=data.success;
         							     if(isFalg){
         							          window_select.hide();
         							          var obj = Ext.decode(response.responseText);
         							   		  App.ReLoadHomeTeam(obj.userInfo)
         							          if(s=="business"){
         							             document.getElementById("business_div").style.display= "none"; //隐藏
         							             document.getElementById("control_div").style.display= "block"; //显示
         							          }else if(s="control"){
         							             document.getElementById("control_div").style.display= "none"; //隐藏
         							             document.getElementById("business_div").style.display= "block"; //显示
         							          }
         							     }
         							},
         							params:{
           							 	roleType:s
         							} 
	          					})
	            			}
	        			}]
				  	});
           		}
		   }
		   Ext.onReady(function(){
			   //待办项目定时更新开始...
			   var taskMgr = new Ext.util.TaskRunner();
			   var task1 = {
   					run : function(){
				        ZW.refreshTaskPanelView();
				        var obj=document.getElementById("taskCount");
				        ZW.refreshTaskCount(obj);
				    },
				    interval : 600000
				};  
			    taskMgr.start(task1); 
			    if(null!=window_select){
			       window_select.show();
			    }
			    //代办项目定时更新结束。。。
		    });
</script>
</html>