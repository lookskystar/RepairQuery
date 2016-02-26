<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp" %>
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
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 报活详细记录</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countReportDetail.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">开始时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="st" value="${st }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">结束时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="et" value="${et }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机&nbsp;&nbsp;务&nbsp;&nbsp;段：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jwdCode">
					<c:forEach items="${areas }" var="area">
						<option value="${area.jwdcode }" <c:if test="${jwdCode == area.jwdcode }">selected="selected"</c:if> >${area.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机车类型：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jcType">
					<option value="">--请选择--</option>
					<c:forEach items="${jcTypes }" var="type">
						<option value="${type.jcStypeValue }" <c:if test="${jcType == type.jcStypeValue }">selected="selected"</c:if> >${type.jcStypeValue }</option>
					</c:forEach>
				</select>
			</div>
			<br/>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机车编号：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="text" name="jcNum" value="${jcNum }" style="width: 150px;"/></div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">报&nbsp;&nbsp;活&nbsp;&nbsp;人：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="text" name="emp" value="${emp }" style="width: 150px;"/></div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">检&nbsp;&nbsp;修&nbsp;&nbsp;人：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="text" name="fixEmp" value="${fixEmp }" style="width: 150px;"/></div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>	
		</form>
	</div>
	<div id="scrollContent" >
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<tr>
			<td class="tbCELL1" align="center"  width="8%">机务段</td>
			<td class="tbCELL1" align="center"  width="8%">报活类别</td>
			<td class="tbCELL1" align="center"  width="8%">机车类型</td>
			<td class="tbCELL1" align="center"  width="8%">机车编号</td>
			<td class="tbCELL1" align="center"  width="10%">报活处所</td>
			<td class="tbCELL1" align="center"  width="6%">报活人</td>
			<td class="tbCELL1" align="center"  width="10%">报活时间</td>
			<td class="tbCELL1" align="center"  width="8%">检修情况</td>
			<td class="tbCELL1" align="center"  width="6%">检修人</td>
			<td class="tbCELL1" align="center"  width="6%">工长</td>
			<td class="tbCELL1" align="center"  width="6%">质检员</td>
			<td class="tbCELL1" align="center"  width="6%">技术员</td>
			<td class="tbCELL1" align="center"  width="6%">验收员</td>
		</tr>
		<c:forEach items="${preDictRecs}" var="preItem">
			<tr>
				<td class="tbCELL1" align="center"  name="preType">
					<c:if test="${preItem.jwdCode == 0801}">
						广州机务段
					</c:if>
					<c:if test="${preItem.jwdCode == 0818}">
						龙川机务段
					</c:if>
					<c:if test="${preItem.jwdCode == 0819}">
						三水机务段
					</c:if>
					<c:if test="${preItem.jwdCode == 0805}">
						株洲机务段
					</c:if>
					<c:if test="${preItem.jwdCode == 08052}">
						长沙机务段
					</c:if>
					<c:if test="${preItem.jwdCode == 0809}">
						怀化机务段
					</c:if>
				</td>
				<td class="tbCELL1" align="center"  name="preType">
					<c:if test="${preItem.type==0}">
						JT28报活
					</c:if>
					<c:if test="${preItem.type==1}">
						复检报活
					</c:if>
					<c:if test="${preItem.type==2}">
						检修过程报活
					</c:if>
					<c:if test="${preItem.type==6}">
						零公里检查
					</c:if>
				</td>
				<td class="tbCELL1" align="center">${preItem.jcType }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.jcNum }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.repsituation }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.repemp}&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.repTime }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.dealSituation }&nbsp;</td>
				<td class="tbCELL1" align="center">
					<c:if test="${empty preItem.fixEmp && empty preItem.dealFixEmp}"><font style="color: red;font-weight: bold;">(未派工)</font></c:if>
					<c:if test="${!empty preItem.fixEmp && empty preItem.dealFixEmp}">
						${preItem.fixEmp}<font style="color: red;font-weight: bold;">(未签认)</font>
					</c:if>
					<c:if test="${!empty preItem.fixEmp && !empty preItem.dealFixEmp}">${preItem.fixEmp}<br>${fn:substring(preItem.fixEndTime, 5, 16) }&nbsp;</c:if>
					<c:if test="${empty preItem.fixEmp && !empty preItem.dealFixEmp}">${fn:replace(preItem.dealFixEmp,',','')}<br>${fn:substring(preItem.fixEndTime, 5, 16) }&nbsp;</c:if>
				</td>
				<td class="tbCELL1" align="center">
					<c:if test="${empty preItem.lead}"><font style="color: red;font-weight: bold;">(未签认)</font></c:if>
					<c:if test="${!empty preItem.lead}">${preItem.lead}<br>${fn:substring(preItem.ldAffirmTime, 5, 16) }&nbsp;</c:if>
				</td>
				<td class="tbCELL1" align="center">${preItem.qi }<br>${fn:substring(preItem.qiAffiTime, 5, 16) }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.technician }<br>${fn:substring(preItem.techAffiTime, 5, 16) }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.accepter }<br>${fn:substring(preItem.acceTime, 5, 16) }&nbsp;</td>
			</tr>
		</c:forEach>
		<c:if test="${empty preDictRecs }">
				<tr>
					<td colspan="13" style="text-align: center;">没有相应的记录!</td>
				</tr>
			</c:if>
		<tr><td colspan="13"></td></tr>	
		</table>
	</div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>
