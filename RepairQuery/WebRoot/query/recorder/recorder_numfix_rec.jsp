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
	<title>实际扣车</title>
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
	<div id="scrollContent" panelTitle="机车列表">
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<tr>
				<th>车型</th>
				<th>机车号</th>
				<th>修程</th>
				<th>股道</th>
				<th>台位</th>
				<th>实际扣车时间</th>
				<th>计划交车时间</th>
				<th>交车工长</th>
				<th>状态</th>
				<th>记录查看</th>
			</tr>
			<c:forEach items="${planPris }" var="plan">
				<tr>
					<td>${plan.jcType }</td>
					<td>${plan.jcnum }</td>
					<td>${plan.fixFreque }</td>
					<td>${plan.gdh }</td>
					<td>${plan.twh }</td>
					<td>${plan.kcsj }</td>
					<td>${plan.jhjcsj }</td>
					<td>${plan.gongZhang }</td>
					<td>
						<c:if test="${plan.planStatue == -1}">新建</c:if>
						<c:if test="${plan.planStatue == 0}">在修</c:if>
						<c:if test="${plan.planStatue == 1}">待验</c:if>
						<c:if test="${plan.planStatue == 2}">已交</c:if>
						<c:if test="${plan.planStatue == 3}">转出</c:if>
					</td>
					<td><a href="<%=basePath%>recorderAction!queryRecorder.action?rjhmId=${plan.rjhmId }&jwdCode=${plan.jwdcode}" target="_blank">记录详情</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty planPris }">
				<tr>
					<td colspan="10">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="10"></td></tr>
		</table>
	</div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>
