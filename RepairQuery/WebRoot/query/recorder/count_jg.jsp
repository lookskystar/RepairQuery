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
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 加改记录查询</span>
		</div>	
		</div>	
		</div>
	</div>
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countJgPredict.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机务段：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jwdCode">
					<c:forEach items="${areas }" var="area">
						<option value="${area.jwdcode }" <c:if test="${jwdCode == area.jwdcode }">selected="selected"</c:if> >${area.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" /></div>
		</form>
	</div>	
	<div  id="scrollContent">
		<table class="tableStyle" headFixMode="true">
			<tr>
				<th width="30%">车型/项目</th>
				<c:forEach items="${jcAcounts}" var="jc">
					<!-- 
					<th width="5%">${jc[0] }</th>
					 -->
					<th width="5%">${jc.jcType }</th>
			    </c:forEach>
				<th width="5%">合计</th>
			</tr>
			<tr>
				<th width="30%">配属台数</th>
				<c:set value="0" var="jcCount"/>
				<c:forEach items="${jcAcounts}" var="jc">
				    <c:set value="${jcCount+jc.jcTypeCount}" var="jcCount"/>
					<th width="5%">${jc.jcTypeCount }</th>
			    </c:forEach>
				<th width="5%">${jcCount }</th>
			</tr>
		</table>
		<table class="tableStyle" useColor="false" useHover="false" useClick="true">
			<c:forEach items="${result}" var="map">
				<c:set var="count" value="0"/>
				<tr align="center">
					<td rowspan="2" width="25%">${map.key}</td>
					<td width="5%">计划</td>
					<c:forEach items="${map.value}" var="plan">
						<c:set var="count" value="${count+plan.planNum}"/>
						<td	width="5%">${plan.planNum}</td>
					</c:forEach>
					<td width="5%">${count}</td>
				</tr>
				<tr align="center">
					<td	width="5%">实际</td>
					<c:set var="count" value="0"/>
					<c:forEach items="${map.value}" var="plan">
						<c:set var="count" value="${count+plan.sjNum}"/>
						<c:choose>
							<c:when test="${plan.sjNum<plan.planNum}">
								<td	width="5%"><a href="" style="color: #f00;font-weight: bold;">${plan.sjNum}</a></td>
							</c:when>
							<c:otherwise>
								<td	width="5%"><a href="" style="color: #00f;font-weight: bold;">${plan.sjNum}</a></td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<td width="5%"><a href="javascript:listJc('${map.key }','');" style="color: #00f;font-weight: bold;">${count}</a></td>
				</tr>
			</c:forEach>
			<tr><td colspan="8"></td></tr>
		</table>
	</div>
</body>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</html>
