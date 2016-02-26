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
		
		function monthRate(st, et){
			top.Dialog.open({URL:"<%=basePath%>recorderAction!createMonthMSArea2DCharts.action?st="+ st +"&et="+ et,Width:"100",Height:"100",
			Title:"兑现率统计",MessageTitle:"月兑现率统计"});
		}
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 检修计划统计</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countPlans.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">开始时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="st" value="${st }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">结束时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="et" value="${et }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><button onclick="monthRate('${st }', '${et }');"/>月兑现率</div>
		</form>
	</div>
	<div>
		<table class="tableStyle" headFixMode="true" style="text-align: center;">
			<tr>
				<td>日期/机务段</td>
				<td colspan="3">广州机务段</td>
				<td colspan="3">龙川机务段</td>
				<td colspan="3">三水机务段</td>
				<td colspan="3">株洲机务段</td>
				<td colspan="3">长沙机务段</td>
				<td colspan="3">怀化机务段</td>
			</tr>
			<tr>
				<td></td>
				<td>计划</td>
				<td>兑现</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>兑现</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>兑现</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>兑现</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>兑现</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>兑现</td>
				<td>未兑现</td>
			</tr>
		</table>
	</div>
	<div id="scrollContent">
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<c:forEach items="${plans }" var="plan">
				<tr>
					<td width="128">${plan.dayOfWeek }</td>
					<td width="55"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0801&st=${plan.st}&et=${plan.et}&isCash=" target="frmright">${plan.gz_plan}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0801&st=${plan.st}&et=${plan.et}&isCash=1" target="frmright">${plan.gz_actual }</a></td>
					<td width="75"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0801&st=${plan.st}&et=${plan.et}&isCash=0" target="frmright">${plan.gz_unfill}</a></td>
					<td width="54"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0818&st=${plan.st}&et=${plan.et}&isCash=" target="frmright">${plan.lc_plan}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0818&st=${plan.st}&et=${plan.et}&isCash=1" target="frmright">${plan.lc_actual }</a></td>
					<td width="75"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0818&st=${plan.st}&et=${plan.et}&isCash=0" target="frmright">${plan.lc_unfill}</a></td>
					<td width="54"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0819&st=${plan.st}&et=${plan.et}&isCash=" target="frmright">${plan.ss_plan}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0819&st=${plan.st}&et=${plan.et}&isCash=1" target="frmright">${plan.ss_actual }</a></td>
					<td width="75"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0819&st=${plan.st}&et=${plan.et}&isCash=0" target="frmright">${plan.ss_unfill}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0805&st=${plan.st}&et=${plan.et}&isCash=" target="frmright">${plan.zz_plan}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0805&st=${plan.st}&et=${plan.et}&isCash=1" target="frmright">${plan.zz_actual }</a></td>
					<td width="76"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0805&st=${plan.st}&et=${plan.et}&isCash=0" target="frmright">${plan.zz_unfill}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=08052&st=${plan.st}&et=${plan.et}&isCash=" target="frmright">${plan.cs_plan}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=08052&st=${plan.st}&et=${plan.et}&isCash=1" target="frmright">${plan.cs_actual }</a></td>
					<td width="77"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=08052&st=${plan.st}&et=${plan.et}&isCash=0" target="frmright">${plan.cs_unfill}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0809&st=${plan.st}&et=${plan.et}&isCash=" target="frmright">${plan.hh_plan}</a></td>
					<td width="53"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0809&st=${plan.st}&et=${plan.et}&isCash=1" target="frmright">${plan.hh_actual }</a></td>
					<td width="58"><a href="<%=basePath%>recorderAction!countPlansDetail.action?jwdCode=0809&st=${plan.st}&et=${plan.et}&isCash=0" target="frmright">${plan.hh_unfill}</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty plans }">
				<tr>
					<td colspan="19" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="19"></td></tr>	
		</table>
	</div>
	<div class="box2" panelTitle="注意" style="color: red;">
		<div><font style="margin-left: 5px;">1.点击统计栏中数字即可查看选中时间段内，当前机务段检修计划统计信息！</font></div>
		<div><font style="margin-left: 5px;">2.如果想查看各段月兑现率统计信息请直接点击月兑现率进入图表统计页面！</font></div>
	</div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>
