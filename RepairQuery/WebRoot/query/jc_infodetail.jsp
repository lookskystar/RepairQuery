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
	<style>
	#infotable,#bzFinishiInfo{
		border-collapse: collapse;
	}
	
	#infotable th,#infotable td,#bzFinishiInfo th,#bzFinishiInfo td{
		border:1px solid #82A8F9;
		padding-left:2px;
		padding-right:2px;
		height:25px;
		line-height:25px;
	}
	
	#jcinfo a{
		font-size:12px;
		border:1px solid #999;
		display:block;
		float:left;
		width:60px;
		text-decoration: none;
		height:20px;
		line-height: 20px;
		text-align: center;
		margin-right:5px;
		font-weight: bold;
		background-color:#900;
		color:#fff;
	}
	</style>
</head>
<body>
	<center>
		<table class="tableStyle" style="width:568px;" id="infotable">
			<tr>
				<th align="center">车型</th>
				<th align="center">车号</th>
				<th align="center">修程</th>
				<th align="center">扣车时间</th>
				<th align="center">计划起机时间</th>
				<th align="center">计划交车时间</th>
				<th align="center">检修状态</th>
			</tr>
			<tr>
				<td>${datePlan.jcType }</td>
				<td>${datePlan.jcnum }</td>
				<td>${datePlan.fixFreque }</td>
				<td>${datePlan.kcsj }</td>
				<td>${datePlan.jhqjsj }</td>
				<td>${datePlan.jhjcsj }</td>
				<td>
					<c:if test="${datePlan.planStatue==-1 }">接车</c:if>
					<c:if test="${datePlan.planStatue==0 }">检修作业</c:if>
					<c:if test="${datePlan.planStatue==1 }">待验</c:if>
					<c:if test="${datePlan.planStatue==2 }">已交验</c:if>
					<c:if test="${datePlan.planStatue==3 }">机车转出</c:if>
				</td>
			</tr>
		</table>
		<table class="tableStyle" style="width:568px;" id="jcinfo">
			<tr>
				<td>
				<c:choose>
					<c:when test="${datePlan.fixFreque=='LX' || datePlan.fixFreque=='JG'|| datePlan.fixFreque=='ZZ'}">
						<a href="<%=basePath %>recorderAction!getInfoByJC.action?jcStype=${datePlan.jcType }&jcNum=${datePlan.jcnum }&xcxc=${datePlan.fixFreque }&rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">机车</a>
					</c:when>
					<c:when test="${datePlan.fixFreque=='QZ' || datePlan.fixFreque=='CJ'}">
						<a href="<%=basePath %>recorderAction!queryRecorder.action?jcStype=${datePlan.jcType }&jcNum=${datePlan.jcnum }&xcxc=${datePlan.fixFreque }&rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">机车</a>
					</c:when>
					<c:otherwise>
						<c:if test="${datePlan.projectType==0}">
						  <a href="<%=basePath %>recorderAction!queryRecorder.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">专业</a>
						  <a href="<%=basePath %>recorderAction!queryInfoByBz.action?workFlag=1&rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">班组</a>
						  <a href="<%=basePath %>recorderAction!getInfoByJC.action?rjhmId=${datePlan.rjhmId }&pageSize=100&jwdCode=${jwdCode}" target="_blank">机车</a>
						</c:if> 
						<c:if test="${datePlan.projectType==1}">
						  <a href="<%=basePath %>recorderAction!queryZxRecorder.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">专业</a>
						  <a href="<%=basePath %>recorderAction!getZxInfoByBZ.action?workFlag=1&rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}" target="_blank">班组</a>
						  <a href="<%=basePath %>recorderAction!getZxInfoByJC.action?rjhmId=${datePlan.rjhmId}&pageSize=100&jwdCode=${jwdCode}" target="_blank">机车</a>
						</c:if>
					</c:otherwise>
				</c:choose>
				<a href="<%=basePath %>recorderAction!showHistory.action?jcStype=${datePlan.jcType }&jcNum=${datePlan.jcnum }&rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">历史</a>
				<a href="<%=basePath %>recorderAction!getAllInfoPre.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">报活</a>
				<a href="<%=basePath %>recorderAction!searchJCjungong.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">竣工</a>
				<c:if test="${datePlan.projectType==0}">
					<a href="<%=basePath %>recorderAction!searchJcRec.action?rjhmId=${datePlan.rjhmId }&jcStype=${datePlan.jcType }&jwdCode=${jwdCode}" target="_blank">交车</a>
				</c:if>
				<c:if test="${datePlan.projectType==1}">
					<a href="<%=basePath %>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=5&jwdCode=${jwdCode}" target="_blank">交车</a>
				</c:if>
				<a href="<%=basePath %>recorderAction!searchOilAssayRecorderDaily.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">化验单</a>
				</td>
			</tr>
		</table>
		<table id="bzFinishiInfo" class="tableStyle" style="width:568px;">
			<tr><th align="center">检修班组</th><th align="center">完成时间</th><th align="center">检修情况</th><th align="center">所处节点</th></tr>
			<c:forEach items="${flowRecs}" var="rec">
				<tr>
					<td align="center">${rec.proTeamName }</td>
					<td align="center">${rec.finishTime }</td>
					<td align="center">
					  <c:if test="${datePlan.projectType==0}">
					     <c:choose>
							<c:when test="${rec.finishStatus==0}">
								<a  style="color: #f00" href="<%=basePath %>recorderAction!queryInfoByBz.action?workFlag=1&teamId=${rec.proTeam }&rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">未完成</a>
							</c:when>
							<c:otherwise>
								<a href="<%=basePath %>recorderAction!queryInfoByBz.action?workFlag=1&teamId=${rec.proTeam }&rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" target="_blank">完成</a>
							</c:otherwise>
						</c:choose>
					  </c:if>
					  <c:if test="${datePlan.projectType==1}">
					    <c:choose>
							<c:when test="${rec.finishStatus==0}">
								<a  style="color: #f00" href="<%=basePath %>recorderAction!getZxInfoByBZ.action?rjhmId=${datePlan.rjhmId}&workFlag=1&teamId=${rec.proTeam }&nodeId=${rec.fixflow}&jwdCode=${jwdCode}" target="_blank">未完成</a>
							</c:when>
							<c:otherwise>
								<a href="<%=basePath %>recorderAction!getZxInfoByBZ.action?rjhmId=${datePlan.rjhmId}&workFlag=1&teamId=${rec.proTeam }&nodeId=${rec.fixflow}&jwdCode=${jwdCode}" target="_blank">完成</a>
							</c:otherwise>
						</c:choose>
					  </c:if>
					</td>
					<td align="center">
						<c:choose>
					    	<c:when test="${rec.fixflow==100}">机车分解</c:when>
					    	<c:when test="${rec.fixflow==101}">车上组装</c:when>
					    	<c:otherwise>小辅修</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${datePlan.projectType==0&&datePlan.planStatue!=-1}">
		<br/>
		<table id="bzFinishiInfo" class="tableStyle" style="width:550px;">
			<tr>
		    	<th align="center">试验项目</th>
		      	<th align="center">工人</th>
		      	<th align="center">工长</th>
		      	<th align="center">质检员</th>
		      	<th align="center">技术员</th>
		      	<th align="center">交车工长</th>
		      	<th align="center">验收员</th>
		        <c:if test="${!empty map}">
		        	<tr>
		            	<td>${map.item}</td>
		            	<td>
		              		<c:if test="${map.grFinished==0}">完成</c:if>
		              		<c:if test="${map.grFinished!=0}"><font style="color: #f00">未签完</font></c:if>
		            	</td>
		            	<td>
		              		<c:if test="${map.gzFinished==0}">完成</c:if>
		              		<c:if test="${map.gzFinished!=0}"><font style="color: #f00">未签完</font></c:if>
		            	</td>
		            	<td>
		              		<c:if test="${map.zjFinished==0}">完成</c:if>
		              		<c:if test="${map.zjFinished!=0}"><font style="color: #f00">未签完</font></c:if>
		            	</td>
		            	<td>
		              		<c:if test="${map.jsFinished==0}">完成</c:if>
		              		<c:if test="${map.jsFinished!=0}"><font style="color: #f00">未签完</font></c:if>
		            	</td>
		            	<td>
		              		<c:if test="${map.jcgzFinished==0}">完成</c:if>
		              		<c:if test="${map.jcgzFinished!=0}"><font style="color: #f00">未签完</font></c:if>
		            	</td>
		            	<td>
		              		<c:if test="${map.ysFinished==0}">完成</c:if>
		              		<c:if test="${map.ysFinished!=0}"><font style="color: #f00">未签完</font></c:if>
		            	</td>
		           	</tr>
		      	</c:if>
		 	</tr>
		</table>
		</c:if>
	</center>
</body>
</html>
