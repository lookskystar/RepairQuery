<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改密码</title>
	<!--框架必需start-->
	<script type="text/javascript" src="js/jquery-1.4.js"></script>
	<script type="text/javascript" src="js/framework.js  "></script>
	<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
	<!--框架必需end-->
	<script src="js/form/validationEngine-cn.js" type="text/javascript"></script>
	<script src="js/form/validationEngine.js" type="text/javascript"></script>
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
</head>
<body>
	<div>
		<form id="updatepwd_form" action="userAction!updatePasswd.action" method="post" target="_parent">
			<table class="tableStyle" style="text-align: center;margin-top: 5px;">
				<tr>
					<td>姓名</td>
					<td>${xm }</td>
				</tr>
				<tr>
					<td>新密码</td>
					<td><input type="password" id="pwd" name="pwd" class=" validate[required]"/><span class="star">*</span></td>
				</tr>
				<tr>
					<td>确认新密码</td>
					<td><input type="password" id="cpwd" name="cpwd" class=" validate[required,confirm[pwd]]"><span class="star">*</span></td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="submit" value="提交" onclick='top.frmright.window.location.reload()'/>
						<input type="reset" value="重置"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>
