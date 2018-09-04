<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>表单示例</title>
	</head>
	<body>
		<form>
			<table cellpadding="0" cellspacing="1" border="1" width="90%">
				<tr>
					<td>订单号</td>
					<td>
						<input name="orderNo" type="text" width="200"/>
					</td>
				</tr>
				<tr>
					<td>客户号</td>
					<td><input name="cusNo" type="text" width="200"/></td>
				</tr>
				<tr>
					<td>
						描述
					</td>
					<td>
						<textarea name="descp" rows="5" cols="50"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>