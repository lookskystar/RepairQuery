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
	<script type="text/javascript" src="js/table/treeTable.js"></script>
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
			<span>当前位置：记录查询>> 检修计划查询</span>
		</div>	
		</div>	
		</div>
	</div>
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countPlan.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机务段：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jwdCode">
					<c:forEach items="${areas }" var="area">
						<option value="${area.jwdcode }" <c:if test="${jwdCode == area.jwdcode }">selected="selected"</c:if> >${area.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM'}))" name="yearMonth" value="${yearMonth }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">周次：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="weekCount">
					<option value="1" <c:if test="${weekCount == '1' }">selected="selected"</c:if> >第一周</option>
					<option value="2" <c:if test="${weekCount == '2' }">selected="selected"</c:if> >第二周</option>
					<option value="3" <c:if test="${weekCount == '3' }">selected="selected"</c:if> >第三周</option>
					<option value="4" <c:if test="${weekCount == '4' }">selected="selected"</c:if> >第四周</option>
					<option value="5" <c:if test="${weekCount == '5' }">selected="selected"</c:if> >第五周</option>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>
		</form>
	</div>	
	<div  id="scrollContent">
		<table class="tableStyle">
			<tr>
				<th colspan="8" style="font-weight: bold;font-size: 16px">检修周计划</th>
			</tr>
			<tr>
				<td colspan="8" align="center">
					制表日期:${planModel.makePlanTime }&nbsp;&nbsp;&nbsp;&nbsp;
					制表人:${planModel.makePlanPerson }
				</td>
			</tr>
			<tr>
				<th width="30px">顺序</th>
				<th width="10%">日期</th>
				<th width="10%">机型</th>
				<th width="10%">车号</th>
				<th width="10%">修程</th>
				<th width="10%">公里</th>
				<th width="10%">扣修地点</th>
				<th>备注</th>
			</tr>
			<c:forEach items="${planModel.detailList }" var="plan" varStatus="index">
				<tr align="center">
					<td>${index + 1 }</td>
					<td>${plan.planTime }<br/></td>
					<td>${plan.jcType }</td>
					<td>${plan.jcNum }</td>
					<td>${plan.xcxc }</td>
					<td>${plan.kilometre }</td>
					<td>${plan.kcArea }</td>
					<td>${plan.comments }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty planModel.detailList }">
				<tr>
					<td colspan="8" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="8"></td></tr>
		</table>
	</div>
</body>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</html>
