<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="msthemecompatible" content="no">
		<style type="text/css">
		
		.bigOrange{
			color :#FF7400;
			font-size :14px;
			font-weight: bold;
		}
		tr{
			padding-bottom: 3px;
			padding-top: 3px;
			
		}
		.oncurrenttd
		{
			background-color: #FF7400;
		}
		</style>
		</head>
	<body>
		<table border="0" cellspacing="3" cellpadding="3" style="font-size: 12px;font-family: '宋体'; width : 800px; height:auto; margin:5px 5px 5px 5px">
			<tbody>
				${htmlLocation}
				<tr class="bottomLine">
					<td colspan="4">
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>