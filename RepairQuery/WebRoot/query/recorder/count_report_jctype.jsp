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
			<span>当前位置：记录查询>> 报活记录统计>> 按机车类型统计>> ${st }&nbsp;&nbsp;至&nbsp;&nbsp;${et }&nbsp;&nbsp;时间段内按报活机车类型统计情况</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div id="scrollContent">
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<tr>
				<th>日期/机车类型</td>
				<th>DF4</th>
				<th>DF5</th>
				<th>DF7</th>
				<th>DF7C</th>
				<th>DF11C</th>
				<th>DF12</th>
				<th>SS3</th>
				<th>SS3B</th>
				<th>SS6</th>
				<th>SS6B</th>
				<th>SS8</th>
				<th>SS9</th>
				<th>HXE1C</th>
				<th>HXD3C</th>
				<th>小计</th>
			</tr>
			<c:forEach items="${reports }" var="map">
				<tr>
					<td>${map.dayOfWeek }</td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=DF4&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.DF4 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=DF5&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.DF5 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=DF7&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.DF7 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=DF7C&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.DF7C }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=DF11G&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.DF11G }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=DF12&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.DF12 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=SS3&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.SS3 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=SS3B&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.SS3B }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=SS6&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.SS6 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=SS6B&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.SS6B }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=SS8&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.SS8 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=SS9&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.SS9 }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=HXD1C&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.HXD1C }</a></td>
					<td><a href="<%=basePath%>recorderAction!countReportDetail.action?jcType=HXD3C&jwdCode=${jwdCode }&st=${map.st }&et=${map.et }" target="frmright">${map.HXD3C }</a></td>
					<td>${map.total }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty reports }">
				<tr>
					<td colspan="16" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>
