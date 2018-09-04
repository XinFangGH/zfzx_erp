<%@ page language="java"  pageEncoding="utf-8" import="java.util.*,java.text.*" %>
<%@page import="com.zhiwei.credit.model.creditFlow.creditAssignment.bank.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>取现审核清单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
		<%--<img src="<%=basePath%>/images/login/login.jpg">--%>
	</td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="40" colspan="2" align="center" ></td>
  </tr>
</table>  

<table cellspacing="0" align="Center" rules="all" bordercolor="Black" border="1" style="border-color:Black;border-width:1px;border-style:solid;width:100%;border-collapse:collapse;">
    <tr> 
      <td width="3%" height="18" rowspan="2" align="center" bgcolor="#D4D0C8">序号</td>
      <td width="5%" rowspan="2" align="center" bgcolor="#D4D0C8">投资人姓名</td>
      <td width="5%" rowspan="2" align="center" bgcolor="#D4D0C8">取现金额</td>
      <td width="5%" rowspan="2" align="center" bgcolor="#D4D0C8">取现时间</td>
       <td width="5%" rowspan="2" align="center" bgcolor="#D4D0C8">取现账户</td>
      <td width="8%" rowspan="2"  align="center" bgcolor="#D4D0C8">取现账号</td>
      <td width="8%" rowspan="2"  align="center" bgcolor="#D4D0C8">开户行</td>
      <td width="8%" rowspan="2"  align="center" bgcolor="#D4D0C8">门店</td>
      <td width="8%" rowspan="2"  align="center" bgcolor="#D4D0C8">操作人</td>
   </tr>
   <tr>
       
   </tr>
     <%
       List<ObAccountDealInfo> list=(List<ObAccountDealInfo>)request.getAttribute("ptList");
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      	int size = list.size();
      	for (int i=0 ;i<size;i++) {
      		ObAccountDealInfo ss=  list.get(i);
     %>
    <tr bgcolor="#FFFFFF" onMouseOver="this.bgColor='#00FF00';" onMouseOut="this.bgColor='FFFFFF';"> 
      <td height="18" align="center"><%=i+1 %></td>
      <td align="center"><%=ss.getInvestPersonName() %></td>
      <td align="center"><%=ss.getPayMoney() %>元</td>
      <td align="center"><%=sdf.format(ss.getCreateDate()) %></td>
      <td align="center"><%=ss.getAccountName()%></td>
      <td align="center"><%=ss.getAccountNumber()%></td>
      <td align="center"><%=ss.getKhname()%></td>
      <%--<td align="center"><%=ss.getShopName() %></td>--%>
      <td align="center"><%=ss.getShopId()%></td>
      <td align="center"><%=ss.getCreateName() %></td>
    </tr>
	<%}
	%>
     </table>
  </body>
</html>