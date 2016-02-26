<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		function planFixRec(jwdCode, xcxc, st, et){
			var jwdName;
			if(jwdCode == '0801') {
				jwdName = "广州机务段";
			} else if (jwdCode == '0818'){
				jwdName = "龙川机务段";
			} else if (jwdCode == '0819') {
				jwdName = "三水机务段";
			} else if (jwdCode == '0805') {
				jwdName = "株洲机务段";
			} else if (jwdCode == '08052') {
				jwdName = "长沙机务段";
			} else if (jwdCode == '0809'){
				jwdName = "怀化机务段";
			}
			top.Dialog.open({URL:"<%=basePath%>recorderAction!findFixRecOnPlan.action?jwdCode="+ jwdCode +"&xcxc="+ xcxc +"&st="+ st +"&et="+ et,Width:"100",Height:"100",
			Title:"计划扣车列表",MessageTitle:"扣车计划",Message:"这是&nbsp;&nbsp;"+ jwdName +"&nbsp;&nbsp;在&nbsp;&nbsp;"+ st +"&nbsp;&nbsp;到&nbsp;&nbsp;"+ et +"&nbsp;&nbsp;时间段内计划扣修的机车！"});
		}
		
		function numFixRec(jwdCode, xcxc, st, et, type){
			var jwdName;
			if(jwdCode == '0801') {
				jwdName = "广州机务段";
			} else if (jwdCode == '0818'){
				jwdName = "龙川机务段";
			} else if (jwdCode == '0819') {
				jwdName = "三水机务段";
			} else if (jwdCode == '0805') {
				jwdName = "株洲机务段";
			} else if (jwdCode == '08052') {
				jwdName = "长沙机务段";
			} else if (jwdCode == '0809'){
				jwdName = "怀化机务段";
			}
			var title;
			var message;
			if(type == 1) {
				title = "实际检修机车";
				message = "这是&nbsp;&nbsp;"+ jwdName +"&nbsp;&nbsp;在&nbsp;&nbsp;"+ st +"&nbsp;&nbsp;到&nbsp;&nbsp;"+ et +"&nbsp;&nbsp;时间段内实际检修的机车！";
			} else {
				title = "未兑现机车";
				message = "这是&nbsp;&nbsp;"+ jwdName +"&nbsp;&nbsp;在&nbsp;&nbsp;"+ st +"&nbsp;&nbsp;到&nbsp;&nbsp;"+ et +"&nbsp;&nbsp;时间段内未兑现检修的机车！";
			}
			top.Dialog.open({URL:"<%=basePath%>recorderAction!findFixRecOnNum.action?jwdCode="+ jwdCode +"&type="+ type +"&xcxc="+ xcxc +"&st="+ st +"&et="+ et,Width:"100",Height:"100",
			Title:""+ title +"",MessageTitle:""+ message +""});
		}
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：在修机车>> 在修机车统计</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>locoAction!countLocoOnFix.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">开始时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="st" value="${st }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">结束时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="et" value="${et }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>
		</form>
	</div>
	<div id="scrollContent" class="box2" panelTitle="各段在修机车统计列表" style="height: 50px;">
		<table class="tableStyle" style="text-align: center;margin-top: 5px;">
			<tr>
				<th>段/修程/数量</th>
				<th>大修</th>
				<th>中修</th>
				<th>小辅修</th>
				<th>临修</th>
				<th>其它</th>
				<th>合计</th>
			</tr>
			<!-- 
			<tr>
				<td></td>
				<td>计划</td>
				<td>实际</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>实际</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>实际</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>实际</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>实际</td>
				<td>未兑现</td>
				<td>计划</td>
				<td>实际</td>
				<td>未兑现</td>
			</tr>
			 -->
			<c:forEach items="${countModels }" var="model">
				<tr>
					<td><a href="<%=basePath%>locoAction!findGdtInfo.action?jwdCode=${model.jwdCode }">${model.areaName }</a></td>
					<!-- 
					<td>${model.oh_plan }</td>
					-->
					<td>${model.oh_fact }</td>
					<!-- 
					<td>${model.oh_unfill }</td>
					<td>
						<c:choose>
							<c:when test="${model.mid_plan gt 0}"> 
								<a href="javascript:planFixRec('${model.jwdCode}','zx', '${st}', '${et}');">
									${model.mid_plan }
								</a>
							</c:when>
							<c:otherwise>
									${model.mid_plan }
							</c:otherwise>
						</c:choose>
					</td>
					 -->
					<td>
						<c:choose>
							<c:when test="${model.mid_fact gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','zx', '${st}', '${et}', '1');">
									${model.mid_fact }
								</a>
							</c:when>
							<c:otherwise>${model.mid_fact }</c:otherwise>
						</c:choose>
					</td>
					<!-- 
					<td>
						<c:choose>
							<c:when test="${model.mid_unfill gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','zx', '${st}', '${et}', '2');">
									<font color="red">${model.mid_unfill }</font>
								</a>
							</c:when>
							<c:otherwise>${model.mid_unfill }</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.sm_plan gt 0}"> 
								<a href="javascript:planFixRec('${model.jwdCode}','xf', '${st}', '${et}');">
									${model.sm_plan }
								</a>
							</c:when>
							<c:otherwise>${model.sm_plan }</c:otherwise>
						</c:choose>
					</td>
					 -->
					<td>
						<c:choose>
							<c:when test="${model.sm_fact gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','xf', '${st}', '${et}', '1');">
									${model.sm_fact }
								</a>
							</c:when>
							<c:otherwise>${model.sm_fact }</c:otherwise>
						</c:choose>
					</td>
					<!-- 
					<td>
						<c:choose>
							<c:when test="${model.sm_unfill gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','xf', '${st}', '${et}', '2');">
									<font color="red">${model.sm_unfill }</font>
								</a>
							</c:when>
							<c:otherwise>${model.sm_unfill }</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.temp_plan gt 0}"> 
								<a href="javascript:planFixRec('${model.jwdCode}','lg', '${st}', '${et}');">
									${model.temp_plan }
								</a>
							</c:when>
							<c:otherwise>${model.temp_plan }</c:otherwise>
						</c:choose>
					</td>
					 -->
					<td>
						<c:choose>
							<c:when test="${model.temp_fact gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','lg', '${st}', '${et}', '1');">
									${model.temp_fact }
								</a>
							</c:when>
							<c:otherwise>${model.temp_fact }</c:otherwise>
						</c:choose>
					</td>
					<!-- 
					<td>
						<c:choose>
							<c:when test="${model.temp_unfill gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','lg', '${st}', '${et}', '2');">
									<font color="red">${model.temp_unfill }</font>
								</a>
							</c:when>
							<c:otherwise>${model.temp_unfill }</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.ot_plan gt 0}"> 
								<a href="javascript:planFixRec('${model.jwdCode}','qt', '${st}', '${et}');">
									${model.ot_plan }
								</a>
							</c:when>
							<c:otherwise>${model.ot_plan }</c:otherwise>
						</c:choose>
					</td>
					 -->
					<td>
						<c:choose>
							<c:when test="${model.ot_fact gt 0}"> 
								<a href="javascript:numFixRec('${model.jwdCode}','qt', '${st}', '${et}', '1');">
									${model.ot_fact }
								</a>
							</c:when>
							<c:otherwise>${model.ot_fact }</c:otherwise>
						</c:choose>
					</td>
					<!-- 
					<td>
						<c:choose>
							<c:when test="${model.ot_unfill gt 0}">
								<a href="javascript:numFixRec('${model.jwdCode}','qt', '${st}', '${et}', '2');">
									<font color="red">${model.ot_unfill }</font>
								</a>
							</c:when>
							<c:otherwise>${model.ot_unfill }</c:otherwise>
						</c:choose>
					</td>
					<td>${model.to_plan }</td>
					 -->
					<td>${model.to_fact }</td>
					<!-- 
					<td>${model.to_unfill }</td>
					 -->
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="box2" panelTitle="注意" style="color: red;">
		<div><font style="margin-left: 5px;">1.点击统计栏中数字即可查看选中时间段内，当前机务段在修机车详细信息！</font></div>
		<div><font style="margin-left: 5px;">2.如果想查看完成检修工艺的机车检修记录，请进入记录查询页面！</font></div>
		<div><font style="margin-left: 5px;">&nbsp;&nbsp;</font></div>
		<div><font style="margin-left: 5px;">&nbsp;&nbsp;</font></div>
		<div><font style="margin-left: 5px;">&nbsp;&nbsp;</font></div>
	</div>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</body>
</html>
