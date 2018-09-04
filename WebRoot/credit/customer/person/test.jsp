<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>银行列表</title>
		<link rel="stylesheet" type="text/css"
			href="../../../ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="../../../css/icons.css" />
		<script type="text/javascript"
			src="../../../ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../../ext/ext-all.js"></script>
		<script type="text/javascript" src="../../../ext/ext-lang-zh_CN.js"
			charset="utf-8"></script>
		<script type="text/javascript"
			src="../../../js/customer/person/personWindowObjList.js" charset="utf-8"></script>
		<script type="text/javascript">
		   function funName(obj){
			 
			   document.getElementById('job').value = obj.job;
			   document.getElementById('name').value = obj.name;
			   document.getElementById('cellphone').value = obj.cellphone;
			   document.getElementById('telphone').value = obj.telphone;
			   document.getElementById('wagebank').value = obj.wagebank;
			   document.getElementById('wageperson').value = obj.wageperson;
			   document.getElementById('wageaccount').value = obj.wageaccount;
		   }
		</script>
	</head>
	<body>
		<center>
			<table style="width: 100%; height: 100%">
			
				<tr>
					<td>
						人员选择
						<input id="name" type="text" name="name" />
						<input type="button" value="sct" onclick="selectPWName(funName)">
					</td>
				</tr>
				<tr>
					<td>
						联系人职务
						<input id="job" type="text" name="job">
					</td>
					<td>
						联系人手机
						<input  id="cellphone" type="text" name="cellphone">
					</td>
					<td>
						联系人固定电话
						<input id="telphone" type="text" name="telphone">
					</td>
				</tr>
				<tr>
					<td>
						工资开户行
						<input id="wagebank" type="text" name="wagebank">
					</td>
					<td>
						工资开户人
						<input id="wageperson" type="text" name="wageperson">
					</td>
					<td>
						工资开户账号
						<input id="wageaccount" type="text" name="wageaccount">
					</td>
				</tr>
			</table>
		</center>

	</body>
</html>
