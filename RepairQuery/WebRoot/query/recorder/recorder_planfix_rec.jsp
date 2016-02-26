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
	<title>计划扣车列表</title>
	<!--框架必需start-->
	<script type="text/javascript" src="js/jquery-1.4.js"></script>
	<script type="text/javascript" src="js/framework.js  "></script>
	<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
	<!--框架必需end-->
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
</head>
<body>
	<div id="scrollContent" panelTitle="计划扣车列表">
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<tr>
				<th>车型</th>
				<th>机车号</th>
				<th>修程</th>
				<th>扣车时间</th>
				<th>机车计划走行公里</th>
				<th>机车实际走行公里</th>
				<th>扣修地点</th>
				<th>是否兑现</th>
				<th>是否兑现</th>
				<th>未兑现原因</th>
				<th>备注</th>
			</tr>
			<c:forEach items="${planPris }" var="plan">
				<tr>
					<td>${plan.jcType }</td>
					<td>${plan.jcNum }</td>
					<td>${plan.xcxc }</td>
					<td>${plan.kilometre }</td>
					<td>${plan.realKilometre }</td>
					<td>${plan.kcArea }</td>
					<td>${plan.isCash }</td>
					<td>
						<c:choose>
							<c:when test="${plan.cashReason eq 0}">
								未兑现
							</c:when>
							<c:when test="${plan.cashReason eq 1}">
								日兑现
							</c:when>
							<c:when test="${plan.cashReason eq 2}">
								周兑现
							</c:when>
						</c:choose>
					</td>
					<td>${plan.comments }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty planPris }">
				<tr>
					<td colspan="11">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="11"></td></tr>
		</table>
	</div>
</body>
</html>
