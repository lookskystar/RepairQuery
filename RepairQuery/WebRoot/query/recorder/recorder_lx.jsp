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
<title>${flowval }检修记录
</title>
<link href="<%=basePath %>css/test.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>css/linkcss.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>js/tree/dtree/dtree.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>js/test.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.floatDiv.js"></script>
<script type="text/javascript" src="<%=basePath %>js/menu.js"></script>
<!-- 打印插件 -->
<script type="text/javascript" src="<%=basePath %>js/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>  
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed> 
</object> 
<!-- 打印end -->
<style type="text/css"> 
	#nav, #nav ul{
	margin:0;
	padding:0;
	list-style-type:none;
	list-style-position:outside;
	position:relative;
	line-height:1.5em; 
	}
	
	#nav a{
	display:block;
	padding:0px 5px;
	border:1px solid #333;
	color:#fff;
	font-weight:bold;
	text-decoration:none;
	background-color:#328aa4;
	}
	
	#nav a:link{
	background-color:#328aa4;
	}
	
    /*#nav a:visited{
	background-color:#fff;
	color:blue;
	}*/
	
	#nav a:hover{
	background-color:#fff;
	color:red;
	}
	
	#nav li{
	float:left;
	position:relative;
	}
	
	#nav ul {
	position:absolute;
	display:none;
	width:12em;
	top:1.5em;
	}
	
	#nav li ul a{
	width:12em;
	height:auto;
	float:left;
	}
	
	#nav ul ul{
	top:auto;
	}	
	
	#nav li ul ul {
	left:12em;
	margin:0px 0 0 10px;
	}
	
	#nav li:hover ul ul, #nav li:hover ul ul ul, #nav li:hover ul ul ul ul{
	display:none;
	}
	#nav li:hover ul, #nav li li:hover ul, #nav li li li:hover ul, #nav li li li li:hover ul{
	display:block;
	}
</style>
<script type="text/javascript">
  	$(function(){
		$('#scoll_div_id').floatdiv("middletop");
	});
</script>
</head>

<body bgcolor="#f8f7f7">
<div id="scoll_div_id" style="background:#328aa4;width:864px;height:23px;">
<ul id="nav">
    <li><a href="<%=basePath%>recorderAction!getInfoByJC.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}"  style="background-color:#fff;color:blue;">◇整车记录 </a></li>
    <li><a href="javascript:void(0);">◇检修班组</a>
       <ul>
           <c:forEach items="${bzs}" var="bz" varStatus="index">
            <li><a href="<%=basePath%>recorderAction!getInfoByBZ.action?rjhmId=${datePlan.rjhmId }&teamId=${bz.proteamid }&jwdCode=${jwdCode}">${bz.proteamname }</a></li>
           </c:forEach>
      </ul>
    </li>
	<li><a href="<%=basePath%>recorderAction!searchJcRec.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇交车试验</a></li>
    <li><a href="<%=basePath %>recorderAction!getAllInfoPre.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇报活记录</a></li>
    <li><a href="javascript:void(0);">◇记录导出</a></li>
    <li><a href="<%=basePath%>recorderAction!searchJCjungong.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇交车竣工 </a></li>
    <li><a href="javascript:void(0);">◇打印 </a>
        <ul>
            <li><a href="#">直接打印</a></li>
            <li><a href="#">打印设置</a></li>
            <li><a href="#">打印预览</a></li>
      </ul>
    </li>
</ul>
</div>
<br><br>
<!-- 浮动导航菜单end -->
<table width="800" border="0" align="center" cellspacing="0" vspace="0">
<tr>
<td colspan="5" align="center"><h2 align="center"><font>
<b>${flowval }&nbsp;&nbsp;
检修记录</b></h2></td>
</tr>
<tr>
<td  align="left" colspan="4">修程：${datePlan.fixFreque }</td>
<td  align="right"><a href="recorderAction!findXXJCPJ.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" style="color:blue;" target="_blank">查看配件检修记录</a></td>
</tr>
<tr><td colspan="6">
<table width="800" border="0" align="center" cellspacing="0" vspace="0">
 <tr>
	<td align="center" colspan="3" class="tbCELL3">
  <table width="800" border="0" align="center" cellspacing="0" vspace="0">
    <tr style="background-color: #328aa4;font-weight: bolder;line-height:40px;height: 40px;">
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">机车型号</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">报活部位</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">报活情况</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">报活人</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">审批人</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">接收人</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">检修人</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">检修情况</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">工长</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">交车工长</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">质检员</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">技术员</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">验收员</td>
      <td class="tbCELL1" align="center" nowrap="nowrap" style="color:#ffffff;">故障备注</td>
    </tr>
    <c:forEach var="rec" items="${preDictRecs }">
    <tr>
      <td class="tbCELL1" align="center">${datePlan.jcType } ${datePlan.jcnum }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.repPosi }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.repsituation }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.repemp }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.verifier }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.receiptPeo }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.fixEmp }&nbsp;</td>
      <td class="tbCELL1" align="center">${rec.dealSituation }&nbsp;
          <c:if test="${!empty rec.upPjNum}">
              <br/>
              <c:forTokens items="${rec.upPjNum }" delims="," var="num">
					<a href="recorderAction!findPjRecordByPjNum.action?rjhmId=${datePlan.rjhmId}&pjNum=${num}&jwdCode=${jwdCode}" target="_blank" style="color:blue;">${num }</a>&nbsp;
				</c:forTokens>
          </c:if>
      </td>
      <td class="tbCELL1" align="center">${rec.lead }<br><c:if test="${!empty rec.ldAffirmTime}">${fn:substring(rec.ldAffirmTime, 5, 16) }</c:if></td>
	  <td class="tbCELL1" align="center">${rec.commitLd }<br><c:if test="${!empty rec.comLdAffiTime}">${fn:substring(rec.comLdAffiTime, 5, 16) }</c:if></td>
	  <td class="tbCELL1" align="center">${rec.qi }<br><c:if test="${!empty rec.qiAffiTime}">${fn:substring(rec.qiAffiTime, 5, 16) }</c:if></td>
	  <td class="tbCELL1" align="center">${rec.technician }<br><c:if test="${!empty rec.techAffiTime}">${fn:substring(rec.techAffiTime, 5, 16) }</c:if></td>
	  <td class="tbCELL1" align="center">${rec.accepter }<br><c:if test="${!empty rec.acceTime}">${fn:substring(rec.acceTime, 5, 16) }</c:if></td>
	  <td class="tbCELL1" align="center">${rec.failNum}&nbsp;</td>
    </tr>
    </c:forEach>
  </td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
</body>
</html>