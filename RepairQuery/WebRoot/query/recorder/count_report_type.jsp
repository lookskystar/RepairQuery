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
	<title>广州铁路(集团)公司</title>
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
	<div class="position" roller="false">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 报活记录统计>> 按报活类别统计>> ${st }&nbsp;&nbsp;至&nbsp;&nbsp;${et }&nbsp;&nbsp;时间段内按报活类别统计情况</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div id="scrollContent">
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<tr>
				<th>日期/报活类别</td>
				<th>JT28</th>
				<th>复检报活</th>
				<th>过程报活</th>
				<th>零公里报活</th>
				<th>小计</th>
			</tr>
			<c:forEach items="${reports }" var="map">
				<tr>
					<td>${map.dayOfWeek }</td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?type=0&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.JT28 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?type=1&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.FJ }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?type=2&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.GC }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?type=6&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.LGL }</a></td>
					<td>${map.total }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty reports }">
				<tr>
					<td colspan="6" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="6"></td></tr>	
		</table>
	</div>
</body>
</html>
