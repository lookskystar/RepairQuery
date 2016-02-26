<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
<title>油水化验记录</title>
<base href="<%=basePath%>" />
<!--框架必需start-->
<script type="text/javascript" src="js/jquery-1.4.js"></script>
<script type="text/javascript" src="js/framework.js"></script>
<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
<!--框架必需end-->

<!--截取文字start-->
<script type="text/javascript" src="js/text/text-overflow.js"></script>
<!--截取文字end-->

<!--修正IE6支持透明PNG图start-->
<script type="text/javascript" src="js/method/pngFix/supersleight.js"></script>
<!--修正IE6支持透明PNG图end-->
<script type="text/javascript">
</script>
</head>
<body>
<div id="showImage">
              <center><h2>${datePlanPri.jcType }-${datePlanPri.jcnum}油水化验记录</h2></center>
	    	 	 <table width="100%" class="tableStyle">
	    	 	   <c:forEach var="map" items="${datas}">
	    	 	      <tr><th colspan="7" style="font-size: 20px;color: blue;" align="center">${map.key}</th></tr>
					  <tr>
						<th class="ver01" width="40%" align="center">项目</th>
						<th class="ver01"  width="10%" align="center">标准(最小值)</th>
						<th class="ver01" width="10%" align="center">标准(最大值)</th>
						<th class="ver01" width="5%" align="center">填报值</th>
						<th class="ver01" width="10%" align="center">合格情况</th>
						<th class="ver01" width="25%" align="center">检验人</th>
					 </tr>
					 <c:forEach items="${map.value}" var="entity">
					   <tr>
					    <td class="ver01" align="center">${entity.subitemtitle}</td>
						<td class="ver01" align="center">${entity.minVal}</td>
						<td class="ver01" align="center">${entity.maxVal}</td>
						<td class="ver01" align="center">${entity.realdeteval}</td>
						<td class="ver01" align="center">
						  <c:if test="${entity.quagrade==0}">优秀</c:if>
						  <c:if test="${entity.quagrade==1}">良好</c:if>
						  <c:if test="${entity.quagrade==2}">合格</c:if>
						  <c:if test="${entity.quagrade==3}">不合格</c:if>
						</td>
						<td class="ver01" align="center">${entity.receiptpeo}&nbsp;${entity.fintime}</td>
					  </tr>
					</c:forEach>
	    	 	   </c:forEach>
				</table>
	    	  </div>
</body>
</html>