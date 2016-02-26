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
	<script type="text/javascript" src="js/form/loadmask.js"></script>
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
	<script type="text/javascript">
		$(document).ready(function(){
			$("#process").bind("click", function () {
				$("#scrollContent").mask("正在查询请稍后...");
			});
		});
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 报活记录统计</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countReport.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">开始时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="st" value="${st }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">结束时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="et" value="${et }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>
		</form>
	</div>
	<div>
		<table class="tableStyle" headFixMode="true" style="text-align: center;">
			<tr>
				<th width="21.97%">日期/机务段</th>
				<th width="10.97%"><a href="<%=basePath%>recorderAction!countReportCategoryShow.action?jwdCode=0801&st=${st }&et=${et }">广州机务段</a></th>
				<th width="11%"><a href="<%=basePath%>recorderAction!countReportCategoryShow.action?jwdCode=0818&st=${st }&et=${et }">龙川机务段</a></th>
				<th width="10.97%"><a href="<%=basePath%>recorderAction!countReportCategoryShow.action?jwdCode=0819&st=${st }&et=${et }">三水机务段</a></th>
				<th width="10.90%"><a href="<%=basePath%>recorderAction!countReportCategoryShow.action?jwdCode=0805&st=${st }&et=${et }">株洲机务段</a></th>
				<th width="11%"><a href="<%=basePath%>recorderAction!countReportCategoryShow.action?jwdCode=08052&st=${st }&et=${et }">长沙机务段</a></th>
				<th width="10.99%"><a href="<%=basePath%>recorderAction!countReportCategoryShow.action?jwdCode=0809&st=${st }&et=${et }">怀化机务段</a></th>
				<th width="11%">小计</th>
			</tr>
		</table>
	</div>
	<div id="scrollContent">
		<table class="tableStyle" style="text-align: center;">
			<c:forEach items="${reports }" var="report">
				<tr>
					<td width="20%">${report.dayOfWeek }</td>
					<td width="10%"><a href="<%=basePath%>recorderAction!countReportDetail.action?st=${report.dayOfWeek}&et=${report.dayOfWeek}&jwdCode=0801" target="frmright">${report.gz}</a></td>
					<td width="10%"><a href="<%=basePath%>recorderAction!countReportDetail.action?st=${report.dayOfWeek}&et=${report.dayOfWeek}&jwdCode=0818" target="frmright">${report.lc }</a></td>
					<td width="10%"><a href="<%=basePath%>recorderAction!countReportDetail.action?st=${report.dayOfWeek}&et=${report.dayOfWeek}&jwdCode=0819" target="frmright">${report.ss}</a></td>
					<td width="10%"><a href="<%=basePath%>recorderAction!countReportDetail.action?st=${report.dayOfWeek}&et=${report.dayOfWeek}&jwdCode=0805" target="frmright">${report.zz}</a></td>
					<td width="10%"><a href="<%=basePath%>recorderAction!countReportDetail.action?st=${report.dayOfWeek}&et=${report.dayOfWeek}&jwdCode=08052" target="frmright">${report.cs }</a></td>
					<td width="10%"><a href="<%=basePath%>recorderAction!countReportDetail.action?st=${report.dayOfWeek}&et=${report.dayOfWeek}&jwdCode=0809" target="frmright">${report.hh }</a></td>
					<td width="10%">${report.total }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty reports }">
				<tr>
					<td colspan="8" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="box2" panelTitle="注意" style="color: red;">
		<div><font style="margin-left: 5px;">1.点击统计栏中数字即可查看选中时间段内，当前机务段报活记录详细信息！</font></div>
		<div><font style="margin-left: 5px;">2.如果想查看各段按报活类别、专业、车型统计信息请点击各机务段名称！</font></div>
		<div><font style="margin-left: 5px;">&nbsp;&nbsp;</font></div>
	</div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>
