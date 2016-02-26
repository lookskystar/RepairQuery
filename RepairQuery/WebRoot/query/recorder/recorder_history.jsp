<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=basePath %>css/linkcss.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UFT-8">
<script type="text/javascript" src="<%=basePath %>js/My97DatePicker/WdatePicker.js">
</script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.js"></script>

<title>查询结果</title>
</head>
<body style="padding-top:20px">

<table width="764" border="0" align="center" cellspacing="0" vspace="0">
<tr>
	<td align="center" colspan="10">
		<h2>机车检修历史记录</h2>
	</td>
</tr>
<tr>
	<td align="left" colspan="10">
		机车型号:${jcStype }  机车编号:${jcNum }
	</td>
</tr>
 <tr>
	<td align="center" colspan="3" class="tbCELL3">
<table width="1000px" border="0" align="center" cellspacing="0" vspace="0" id="datatabel">
	<tr style="background-color: #328aa4;font-weight: bolder;">
		<th class="tbCELL1" style="color:#ffffff;">修程修次</th>
		<th class="tbCELL1" style="color:#ffffff;">股道</th>
		<th class="tbCELL1" style="color:#ffffff;">台位</th>
		<th class="tbCELL1" style="color:#ffffff;">扣车时间</th>
		<th class="tbCELL1" style="color:#ffffff;">计划起机时间<br>实际起机时间</th>
		<th class="tbCELL1" style="color:#ffffff;">计划交车时间<br>实际交车时间</th>
		<th class="tbCELL1" style="color:#ffffff;">交车工长</th>
		<th class="tbCELL1" style="color:#ffffff;">检修节点</th>
		<th class="tbCELL1" style="color:#ffffff;">状态</th>
		<th class="tbCELL1" style="color:#ffffff;">查看</th>
	</tr>

	<c:forEach var="dp" items="${list }">
		<tr>
			<td class="tbCELL1">${dp.fixFreque }</td>
			<td class="tbCELL1">${dp.gdh }</td>
			<td class="tbCELL1">${dp.twh }</td>
			<td class="tbCELL1">${dp.kcsj }</td>
			<td class="tbCELL1">${fn:substring(dp.jhqjsj,5,16) }<br>${fn:substring(dp.sjqjsj,5,16) }</td>
			<td class="tbCELL1">${fn:substring(dp.jhjcsj,5,16) }<br>${fn:substring(dp.sjjcsj,5,16) }</td>
			<td class="tbCELL1">${dp.gongZhang }</td>
			<td class="tbCELL1">${dp.nodeName }</td>
			<td class="tbCELL1">
				<c:if test="${dp.planStatue==-1 }">新建</c:if>
			<c:if test="${dp.planStatue==0 }">在修</c:if>
			<c:if test="${dp.planStatue==1 }">待验</c:if>
			<c:if test="${dp.planStatue==2 }">已交</c:if>
			<c:if test="${dp.planStatue==3 }">转出</c:if>
			</td>
			<th class="tbCELL1">
				<c:if test="${dp.projectType==0}">
					<a href="<%=basePath%>recorderAction!queryRecorder.action?rjhmId=${dp.rjhmId}&jwdCode=${jwdCode}" target="_blank">检修记录</a>
				</c:if>
				<c:if test="${dp.projectType==1}">
					<a href="<%=basePath%>recorderAction!queryZxRecorder.action?rjhmId${dp.rjhmId}&jwdCode=${jwdCode}" target="_blank">检修记录</a>
				</c:if>
				<a href="<%=basePath%>recorderAction!getAllInfoPre.action?rjhmId=${dp.rjhmId}&jwdCode=${jwdCode}" target="_blank">报活记录</a>
				<a href="<%=basePath%>recorderAction!searchJCjungong.action?rjhmId=${dp.rjhmId}&jwdCode=${jwdCode}" target="_blank">交车竣工单</a>
				<c:if test="${dp.projectType==0}">
					<a href="<%=basePath%>recorderAction!searchJcRec.action?rjhmId=${dp.rjhmId}&jcStype=${dp.jcType }&jwdCode=${jwdCode}" target="_blank">交车记录</a>
				</c:if>
				<c:if test="${dp.projectType==1}">
					<a href="<%=basePath %>recorderAction!viewExperiment.action?id=${dp.rjhmId}&jceiId=5&jwdCode=${jwdCode}" target="_blank">试验记录</a>
				</c:if>
				<a href="<%=basePath %>recorderAction!searchOilAssayRecorderDaily.action?rjhmId=${dp.rjhmId }&jwdCode=${jwdCode}" target="_blank">油水化验记录</a>
			</th>
		</tr>
	</c:forEach>
	
</table>
</td>
</tr>
</table>
</body>
</html>