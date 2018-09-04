<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List,com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator,com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%List list = (List) request.getAttribute("list");


 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--jiang --%>
<title>新建资信评估</title>
	
	<link rel="stylesheet" type="text/css"
			href="<%=basePath%>ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/icons.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/total.css" />
			 <script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    </script>
		<script type="text/javascript"
			src="<%=basePath%>ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>ext/ext-lang-zh_CN.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>js/global.js"
			charset="utf-8"></script>
		
		<script language="javascript"
			src="<%=basePath%>js/globalDicProperty.js" type="text/javascript"></script>			
		<script type="text/javascript" src="<%=basePath%>js/globalDicProperty.js"
			charset="utf-8"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/creditFlow/customer/person/personWindowObjList.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/creditFlow/customer/enterprise/selectEnterprise.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
	<script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
	<script type="text/javascript">
		   Ext.onReady(function(){
			   	  var storeTheme=getCookie('theme');
			   	  if(storeTheme==null || storeTheme==''){
				   	  storeTheme='ext-all-css04';
			   	  }
			      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
		    });
  </script>
		<script type="text/javascript">
			var customerType = '${creditRating.customerType}';
			var customerId = '${creditRating.customerId}';
			var customerName = '${creditRating.customerName}';
			var creditTemplate = '${creditRating.creditTemplate}';
			var creditTemplateId = '${creditRating.creditTemplateId}';
			var financeFile = '${creditRating.financeFile}';
		</script>
		<script type="text/javascript">
			function check(score,id_score) {
				//Ext.getDom(id_score).innerText = score;
				document.getElementById(id_score).innerText = score;
			}

			var size = <%=list.size() %>;
	
			function test() {
				var arr_id = new Array;
				var arr_score = new Array;
				var score = 0;
				
				for (var i=0; i<size; i++) {
					
				}
				
			}
			
			function countResult(sub) {
				var arr_id = new Array;
				var arr_score = new Array;
				var score = 0;
				
				<% for (int i=0; i<list.size(); i++) {
					TemplateIndicator ti = (TemplateIndicator) list.get(i);
				%>
					var obj=document.getElementsByName('<%=ti.getId()%>');
					if(obj!=null){
        				var i;
        				for(i=0;i<obj.length;i++){
            				if(obj[i].checked){
                				break;            
            				}
            				if(i == obj.length-1 && !obj[i].checked){
            					alert('指标评估未完成，请继续评估！');
            					return;
            				}
        				}
    				}
					
						
					arr_id.push(<%=ti.getId()%>);
					arr_score.push(Ext.getDom('<%=ti.getId() + "score"%>').innerHTML);
					var v = Ext.getDom('<%=ti.getId() + "score"%>').innerHTML;
					
					score = parseFloat(score) + parseFloat(v);
				<%} %>
				//Ext.getCmp('scoreResult').innerHTML = score;
				
				Ext.getDom('creditRating.arr_id').value = arr_id.toString();
				Ext.getDom('creditRating.arr_score').value = arr_score.toString();
				Ext.getDom('creditRating.ratingScore').value = score;
				Ext.getCmp('arr_id').setValue(arr_id.toString());
				Ext.getCmp('arr_score').setValue(arr_score.toString());
				Ext.getCmp('ratingScore').setValue(score);
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/creditmanagement/getScoreGradeCreditRating.do?creditRating.ratingScore=' + score,
					method : 'POST',
					success : function(response,request) {
							obj = Ext.util.JSON.decode(response.responseText);
							Ext.getDom('creditRating.creditRegister').value = obj.data.grandName;
							Ext.getDom('creditRating.advise_sb').value = obj.data.advise;
							Ext.getCmp('creditRegister').setValue(obj.data.grandName);
							Ext.getCmp('advise_sb').setValue(obj.data.advise);
							//Ext.getDom('creditRegister').innerHTML = obj.data.grandName;
							//Ext.getDom('advise_sb').innerHTML = obj.data.advise;
					},
					failure : function(result, action) {
						Ext.Msg.alert('状态','服务器未响应，失败!');
					}
				});
				if (sub == 1) {
					Ext.MessageBox.confirm('确认', '是否确认提交本次评估', function(btn) {
					if (btn == 'yes') {
						//document.getElementById('addCreditRatingSub').submit();
						Ext.Ajax.request({
							url : __ctxPath+'/credit/creditmanagement/addCreditRatingSub.do',
							method : 'POST',
							form : 'addCreditRatingSub',
							success : function(response) {
								if (response.responseText == '{success:true}') {
									Ext.Msg.alert('状态', '成功!');
									var redirect = "<%=basePath%>creditManagement/creditRatingManage.jsp";
									window.location = redirect;
								} else if(response.responseText == '{success:false}') {
									Ext.Msg.alert('状态','失败!');
								} else {
									Ext.Msg.alert('状态','失败!');
								}
							},
							failure : function(result, action) {
								Ext.Msg.alert('状态','服务器未响应，失败!');
							}
						});
					}
					});
				}
				
			}
		</script>
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/creditManagement/creditRatingSub.js" charset="utf-8"></script>
		<style type="text/css">
.key {
	background: url(../images/icon_table.gif) no-repeat 0px 0px;
}

.key {
	background-color: #FFFFFF;
	padding-left: 20px;

}
</style>
	
</head>

<body>
		<form id="addCreditRatingSub" action="<%=basePath%>creditFlow/creditmanagement/addSubCreditRating.do" method="post">
			
			<input id="creditRating.customerType" type="hidden" name="creditRating.customerType" value="${creditRating.customerType}" />
			<input id="creditRating.customerId" type="hidden" name="creditRating.customerId" value="${creditRating.customerId}" />
			<input id="creditRating.customerName" type="hidden" name="creditRating.customerName" value="${creditRating.customerName}" />
			
			<input id="creditRating.creditTemplate" type="hidden" name="creditRating.creditTemplate" value="${creditRating.creditTemplate}" />
			<input id="creditRating.creditTemplateId" type="hidden" name="creditRating.creditTemplateId" value="${creditRating.creditTemplateId}" />
			<input id="creditRating.templateScore" type="hidden" name="creditRating.templateScore" value="${creditRating.templateScore}" />
			<input id="creditRating.financeFile" type="hidden" name="creditRating.financeFile" value="${creditRating.financeFile}" />
			
			<input id="creditRating.arr_id" type="hidden" name="creditRating.arr_id" />
			<input id="creditRating.arr_score" type="hidden" name="creditRating.arr_score"  />
			<input id="creditRating.ratingScore" type="hidden" name="creditRating.ratingScore" />
			<input id="creditRating.creditRegister" type="hidden" name="creditRating.creditRegister" />
			<input id="creditRating.advise_sb" type="hidden" name="creditRating.advise_sb" />
		</form>
		
	
	
</body>
</html>
