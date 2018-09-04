<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.util.List,com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator,com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%List list = (List) request.getAttribute("list"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--jiang --%>
<title>指标管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/total.css" />
		<script type="text/javascript" src="<%=basePath%>ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-all.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-lang-zh_CN.js" charset="utf-8"></script>
		<script language="javascript"  src="<%=basePath%>js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script language="javascript"  src="<%=basePath%>js/global.js" type="text/javascript"></script>
		<script language="javascript"  src="<%=basePath%>js/globalDicProperty.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/creditManagement/CreditRatingResult.js" charset="utf-8"></script>

	<%--<script type="text/javascript" src="<%=basePath%>credit/creditManagement/creditRatingManage.js" charset="utf-8"></script>
--%></head>

<body>
	<div id="firstPanel" class="x-hide-display">
    	<table border="0" cellpadding="0" cellspacing="0" width="100%"
				class="table_content">
		<tr>
		<td class="content_center_center" align="center">
						<!-- 表单内容 -->
						<table border="0" cellpadding="5" cellspacing="7" width="95%">
						<tr>
							<td class="content-left" >
								客户类型：
							</td>
							<td class="content-right">
								${creditRating.customerType}
							</td>
							<td class="content-left">
								客户名称：
							</td>
							<td class="content-right">
								${creditRating.customerName}
							</td>
							<td class="content-left">
								资信评估模板：
							</td>
							<td class="content-right">
								${creditRating.creditTemplate}
							</td>
						</tr>
						<tr>
							<td class="content-left">
								财务报表文件：
							</td>
							<td class="content-right">
								${creditRating.financeFile}
							</td>
							<td class="content-left">
								得分情况：
							</td>
							<td class="content-right">
								<font color=red>
									${creditRating.ratingScore}
								</font>
							</td>
						</tr>
						<tr>
							<td class="content-left" >
								资信等级：
							</td>
							<td class="content-right">
								${creditRating.creditRegister}
							</td>
							<td class="content-left">
								建议内容：
							</td>
							<td class="content-right">
								${creditRating.advise_sb}
							</td>
							<td class="content-left">
								贷款上限：
							</td>
							<td class="content-right">
								1000万元
							</td>
						</tr>
						
						</table>
		</td>
		</tr>
		</table>
	</div>
	<div id="firstPanel1" class="x-hide-display">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
				class="table_content">
		<tr>
		<td class="content_center_center" align="center">
						<!-- 表单内容 -->
			<table border="1" align="center" width="760px">
				<tr>
			<td>
				<small>序号</small>
			</td>
			<td>
				<small>指标</small>
			</td>
			<td>
				<small>评价等级</small>
			</td>
			<td>
				<small>得分</small>
			</td>
			<td>
				<small>指标评价</small>
			</td>
		</tr>
		<% for (int i=0; i<list.size(); i++) {
			TemplateIndicator ti = (TemplateIndicator) list.get(i);
		%>
			<tr>
				<td>
					<small>
					<%=i + 1 %>
					</small>
				</td>
				<td>
					<small>
					<%=ti.getIndicatorName() %>
					</small>
				</td>
				<td>
					<small>
					<%=ti.getOptionResult() %>
					</small>
					
				</td>
				<td>
					<small>
					<%=ti.getScoreResult() %>
					</small>
				</td>
				<td>
					<small>
					<%=ti.getIndicatorAssess() %>
					</small>
				</td>
			</tr>
		<%} %>
			</table>
		</td>
		</tr>
		</table>								
	</div>					
  </body>
</html>
