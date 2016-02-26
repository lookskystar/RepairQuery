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
<base href="<%=basePath%>report/">
<title>整车配件检修记录
</title>
<link href="<%=basePath %>css/test.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>css/linkcss.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>js/tree/dtree/dtree.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>js/test.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=basePath %>js/tree/dtree/dtree.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/float.js"></script>
</head>

<body bgcolor="#f8f7f7">
<form id="form1" name="form1" method="post" action="">
<div style="width:870px;margin-left:-435px;left:50%;position:absolute;" id="content2">
<table width="864" border="0" align="center" cellspacing="0" vspace="0" style="padding-top:10px;">
<tr>
<td colspan="6" align="center" height="40"><h2 align="center"><font style="font-family:'隶书'">
<b>整车配件检修记录
</b></font></h2></td>
<td  align="right"></td>
</tr>
<tr>
<td width="163" align="right">机车号：</td>
<td width="240">${datePlan.jcType }  ${datePlan.jcnum }</td>
<td width="55" align="left">修程：</td><td width="251">${datePlan.fixFreque}</td>
<td width="140" align="left">检修日期：</td><td width="195" algin="left">${datePlan.kcsj }</td>
</tr>
<tr><td colspan="6">
<table width="864" border="0" align="center" cellspacing="0" vspace="0">
 <tr>
	<td align="center" colspan="3" class="tbCELL3">
	<table width="864px" border="0" align="center" cellspacing="0" vspace="0" id="datatabel">
		<tr style="line-height:40px;height: 40px;background-color: #328aa4;font-weight: bolder;">
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff" width="10%">专业</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff" width="5%">序号</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff" width="20%">配件名称</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;word-break:break-all;word-wrap: break-word;" width="50%">上车配件编号</td>
		</tr>
		<c:if test="${!empty map}">
	 		<c:forEach var="map" items="${map}">
	 		  <c:set var="num" value="0"/>
	 		  <c:if test="${!empty map.value}"><c:set var="num" value="${fn:length(map.value)}"/></c:if>
	 		  <tr>
	 		  	<td rowspan="${num+1}" class="tbCELL1" align="center" style="white-space:nowrap" width="10%">${map.key}(${num})</td>
	 		  </tr>
	 		  <c:if test="${!empty map.value}">
	 		    <c:forEach var="pj" items="${map.value}" varStatus="s">
	 		      <tr><td class="tbCELL1" align="center" style="white-space:nowrap" width="5%">${s.index+1}</td>
	 		      <td class="tbCELL1" align="center" style="white-space:nowrap" width="20%">${pj.pjName}</td>
	 		      <td class="tbCELL1" align="center" style="word-break:normal;word-wrap: break-word;" width="50%">
	 		        <c:if test="${!empty pj.dynamicInfos}">
	 		          <c:forEach var="dy" items="${pj.dynamicInfos}">
	 		            <a href="query!findPjRecordByPjNum.do?rjhmId=${datePlan.rjhmId}&pjdid=${dy.pjdid}" target="_blank">${dy.pjNum}</a>&nbsp;&nbsp;
	 		          </c:forEach>
	 		        </c:if>
	 		      </td>
	 		      </tr>
	 		    </c:forEach>
	 		  </c:if>
	 		</c:forEach>
	 	</c:if>
	 	<c:if test="${empty map}">
	 	  <tr><td align="center" colspan="4">没有相应的配件记录!</td></tr>
	 	</c:if>
	</table>
  </td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  </div>
  <!-- endprint -->
</form>
</body>
</html>