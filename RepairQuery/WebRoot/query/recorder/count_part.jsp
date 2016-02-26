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
	<script type="text/javascript" src="js/table/treeTable.js"></script>
	<!-- 提交 -->
	<script type="text/javascript" src="js/form/loadmask.js"></script>
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
	<script type="text/javascript">
		$(document).ready(function(){
			$("#process").bind("click", function () {
				$("#scrollContent").mask("正在查询请稍后...");
			});
		})
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 配件检修记录统计</span>
		</div>	
		</div>	
		</div>
	</div>
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countPartRate.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机务段：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jwdCode">
					<c:forEach items="${areas }" var="area">
						<option value="${area.jwdcode }" <c:if test="${jwdCode == area.jwdcode }">selected="selected"</c:if> >${area.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">车型：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jcType">
					<c:forEach items="${types }" var="type">
						<option value="${type.jcType }" <c:if test="${jcType == type.jcType }">selected="selected"</c:if> >${type.jcType }</option>
					</c:forEach>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>
		</form>
	</div>	
	<div  id="scrollContent">
		<table class="treeTable" initState="collapsed">
			<tr>
				<th width="180">专业</th>
				<th width="120">合格配件数</th>
				<th width="120">在修配件数</th>
				<th width="120">待修配件数</th>
				<th width="120">已装车</th>		
				<th width="120">总计</th>
				<th width="120">合格率</th>
					
			</tr>
			<c:forEach items="${pjCountMap }" var="unitMap">
				<tr id="node-${unitMap.key.id }">
					<td><span class="folder">${unitMap.key.name }</span></td>
					<td align="center">${unitMap.key.passCount }</td>
					<td align="center">${unitMap.key.fixCount }</td>
					<td align="center">${unitMap.key.waitCount }</td>
					<td align="center">${unitMap.key.onTrain}</td>
					<td align="center">${unitMap.key.totalCount }</td>	
					<td align="center">${unitMap.key.passRate }</td>			
				</tr>
				<c:forEach items="${unitMap.value }" var="part" varStatus="idx">
					<tr id="node-${unitMap.key.id }-${idx.index+1}" class="child-of-node-${unitMap.key.id }">
						<td ><a href="<%=basePath%>recorderAction!dyPJListInput.action?pjName=${part.name }&jwdCode=${jwdCode}"><span class="file">${part.name }</span></a></td>
						<td align="center"><a href="<%=basePath%>recorderAction!dyPJListInput.action?pjName=${part.name }&jwdCode=${jwdCode}&pjStatus=0">${part.passCount }</a></td>
						<td align="center"><a href="<%=basePath%>recorderAction!dyPJListInput.action?pjName=${part.name }&jwdCode=${jwdCode}&pjStatus=3">${part.fixCount }</a></td>
						<td align="center"><a href="<%=basePath%>recorderAction!dyPJListInput.action?pjName=${part.name }&jwdCode=${jwdCode}&pjStatus=1">${part.waitCount }</a></td>
						<td align="center"><a href="<%=basePath%>recorderAction!dyPJListInput.action?pjName=${part.name }&jwdCode=${jwdCode}&pjStatus=0&storePosition=2">${part.onTrain}</a></td>
						<td align="center">${part.totalCount }</td>
						<td align="center">${part.passRate }</td>
					</tr>
				</c:forEach>
			</c:forEach>	
			<c:if test="${empty pjCountMap }">
				<tr>
					<td colspan="7" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="7"></td></tr>	
		</table>                    
	</div>
</body>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</html>
