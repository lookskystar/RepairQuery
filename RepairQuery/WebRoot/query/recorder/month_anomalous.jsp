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
	<title>机车检修记录</title>
	<!--框架必需start-->
	<script type="text/javascript" src="js/jquery-1.4.js"></script>
	<script type="text/javascript" src="js/framework.js  "></script>
	<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
	<!--框架必需end-->
	<script type="text/javascript" src="js/form/loadmask.js"></script>
	<!--报表插件start -->
	<script type="text/javascript" src="fusioncharts/js/fusioncharts.js"></script>
	<!--报表插件end-->
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
	<script type="text/javascript">
	$(document).ready(function(){
		//获取浏览器窗口大小
		var w=window.innerWidth
		|| document.documentElement.clientWidth
		|| document.body.clientWidth;
		
		var h=window.innerHeight
		|| document.documentElement.clientHeight
		|| document.body.clientHeight;
		// Create a new instance of FusionCharts for rendering inside an HTML
	    var myChart = new FusionCharts({
	    	width: w * 0.98,
	    	height: h * 0.7,
	        type: 'MSArea',
	        renderAt: 'msAreaId',
	        dataFormat: 'json',
	        dataSource: ${jsonData}
	    });
	    // Render the chart.
	    myChart.render();
	})
	</script>
</head>
<body>
	<div class="box4" >
		<form action="<%=basePath%>recorderAction!createMonthMSArea2DCharts.action" method="post">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">开始时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM'}))" name="st" value="${st }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">结束时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM'}))" name="et" value="${et }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" /></div>
		</form>
	</div>
	<div id="msAreaId" align="center" style="margin-top: 10px;"></div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>