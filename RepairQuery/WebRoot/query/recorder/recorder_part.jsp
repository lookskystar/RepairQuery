<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
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
<base href="<%=basePath%>">
<title>${currentTeam.proteamname }&nbsp;&nbsp;${flowval }
检修记录</title>
<link href="<%=basePath %>css/test.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>css/linkcss.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>js/tree/dtree/dtree.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>js/test.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=basePath %>js/tree/dtree/dtree.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/float.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/print.js"></script>
</head>

<body bgcolor="#f8f7f7">
<form id="form1" name="form1" method="post" action="">
<!--left box-->
<div style="width:870px;margin-left:-435px;left:50%;position:absolute;" id="content2">
<table width="864" border="0" align="center" cellspacing="0" vspace="0" style="padding-top:10px;">
<tr>
<td colspan="8" align="center" height="40"><h2 align="center"><font style="font-family:'隶书'">
<b>
${pjDy.pjName }&nbsp;&nbsp;
检修记录
</b></font></h2></td>
<td  align="right"></td>
</tr>
<tr>
<td width="80px" align="right">配件编号：</td>
<td width="80px">${pjDy.pjNum}</td>
<td width="65px" align="right">出厂编号：</td><td width="100px" align="left">${pjDy.factoryNum}</td>
<td width="80px" align="right">存储位置：</td><td width="100px" algin="left">
  	  <c:if test="${pjDy.storePosition==0}">中心库</c:if>
	  <c:if test="${pjDy.storePosition==1}">班组</c:if>
	  <c:if test="${pjDy.storePosition==2}">车上</c:if>
	  <c:if test="${pjDy.storePosition==3}">外地</c:if>
</td>
<td width="100px" align="right">配件状态：</td><td width="100px" algin="left">
  <c:if test="${pjDy.pjStatus==0}">合格</c:if>
  <c:if test="${pjDy.pjStatus==1}">待修</c:if>
  <c:if test="${pjDy.pjStatus==2}">报废</c:if>
  <c:if test="${pjDy.pjStatus==3}">检修中</c:if>
</td>
</tr>
<tr><td colspan="8">
<table width="864" border="0" align="center" cellspacing="0" vspace="0">
 <tr>
	<td align="center" colspan="3" class="tbCELL3">
	<table width="864" border="0" align="center" cellspacing="0" vspace="0" id="datatabel">
		<tr style="line-height:40px;height: 40px;background-color: lightblue;font-weight: bolder;">
			<td class="tbCELL1" align="center" style="white-space:nowrap; min-width" width="15%">配件名称</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap; min-width" width="15%">修次</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap; min-width" width="15%">检修时间</td>
			<!-- 
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="5%">计划标识</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="8%">计划制定者</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="15%">制定时间</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="5%">检修数量</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="10%">接件时间</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="10%">交件时间</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="10%">计划开工时间</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="10%">计划完工时间</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="5%">状态</td>
			 -->
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="10%">操作</td>
		</tr>
		<c:forEach items="${pjRecs}" var="pjRec" varStatus="s">
		<tr>
			<td class="tbCELL1" align="center" name="secondname">${pjRec.pjname}</td>
			<td class="tbCELL1" align="center" name="secondname">第${s.index+1}次检修</td>
			<td class="tbCELL1" align="center" name="secondname">
			  <fmt:formatDate value="${pjRec.accepttime }" pattern="yyyy-MM-dd HH:mm"/>
			</td>
			<td class="tbCELL1" align="center">
			    <c:choose>
			     <c:when test="${pjRec.type==2}">
			        <a href="javascript:void(0);" title="委修厂:${pjRec.fixemp }--备注:${pjRec.fixsituation}" style="color:blue;">委外检修</a>
			     </c:when>
			     <c:otherwise>
			       <a href="<%=basePath%>recorderAction!findPjDetailRecorder.action?jwdCode=${jwdCode}&pjId=${pjDy.pjdid}&recId=${pjRec.pjRecId}" target="_blank" style="color:blue;">查看详情</a>
			     </c:otherwise>
			    </c:choose>
			</td>
		</tr>
		</c:forEach>
		<c:if test="${empty pjRecs}"><tr><td class="tbCELL1" align="center" colspan="4">无相应检修记录!</td></tr></c:if>
	</table>
  </td>
  </tr>
  <tr>
	</tr>
  </table>
  </td>
  </tr>
  </table>
  </div>
</form>
</body>
</html>