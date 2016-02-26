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
	<title>机车检修记录</title>
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
			<span>当前位置：记录查询>> 机车检修记录</span>
		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="综合条件查询">
		<form action="<%=basePath%>recorderAction!countFixRec.action" method="post" target="frmright">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">开始时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="st" value="${st }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">结束时间：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" class="Wdate" onclick="WdatePicker(({dateFmt:'yyyy-MM-dd'}))" name="et" value="${et }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机务段：</div>	
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="jwdCode">
					<c:forEach items="${areas }" var="area">
						<option value="${area.jwdcode }" <c:if test="${jwdCode == area.jwdcode }">selected="selected"</c:if> >${area.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">修程修次：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select class="dafult" name="xcxc">
					<option value="">-请选择-</option>
					<option value="xx" <c:if test="${xcxc == 'xx' }">selected="selected"</c:if> >小修</option>
					<option value="fx" <c:if test="${xcxc == 'fx' }">selected="selected"</c:if> >辅修</option>
					<option value="cj" <c:if test="${xcxc == 'cj' }">selected="selected"</c:if> >春鉴</option>
					<option value="qz" <c:if test="${xcxc == 'qz' }">selected="selected"</c:if> >秋整</option>
					<option value="lx" <c:if test="${xcxc == 'lx' }">selected="selected"</c:if> >临修</option>
					<option value="jg" <c:if test="${xcxc == 'jg' }">selected="selected"</c:if> >加改</option>
					<option value="zx" <c:if test="${xcxc == 'zx' }">selected="selected"</c:if> >中修</option>
					<option value="jj" <c:if test="${xcxc == 'jj' }">selected="selected"</c:if> >季检</option>
					<option value="yj" <c:if test="${xcxc == 'yj' }">selected="selected"</c:if> >月检</option>
					<option value="bnj" <c:if test="${xcxc == 'bnj' }">selected="selected"</c:if> >半年检</option>
					<option value="nj" <c:if test="${xcxc == 'nj' }">selected="selected"</c:if> >年检</option>
					<option value="zz" <c:if test="${xcxc == 'zz' }">selected="selected"</c:if> >重点整治</option>
					<option value="zqzz" <c:if test="${xcxc == 'zqzz' }">selected="selected"</c:if> >周期整治</option>
				</select>
			</div>
			</br>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">机车编号：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="text" name="jcNum" value="${jcNum }" style="width: 150px;"/></div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">项目名称：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="text" name="itemName" value="${itemName }" style="width: 400px;"/></div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;"><input type="submit" value="查  询" id="process"/></div>
		</form>
	</div>
	<div>
		<table class="tableStyle" headFixMode="true" style="text-align: center;">
			<tr>
				<th width="11.1%">机务段</th>
				<th width="11.09%">机车类型</th>
				<th width="7.8%">机车号</th>
				<th width="7.7%">修程</th>
				<th width="7.8%">股道</th>
				<th width="7.8%">台位</th>
				<th width="11.05%">实际扣车时间</th>
				<th width="11.11%">实际交车时间</th>
				<th width="7.78%">交车工长</th>
				<th width="7.78%">状态</th>
				<th width="8%">记录查看</th>
			</tr>
		</table>
	</div>
	<div id="scrollContent" panelTitle="各段机车检修记录列表">
		<table class="tableStyle" style="text-align: center;">
			<c:forEach items="${planPris }" var="plan">
				<tr>
					<td width="10%">
						<c:forEach items="${areas }" var="area">
							<c:if test="${area.jwdcode == plan.jwdcode }">
								${area.name }
							</c:if>
						</c:forEach>
					</td>
					<td width="10%">${plan.jcType }</td>
					<td width="7%">${plan.jcnum }</td>
					<td width="7%">${plan.fixFreque }</td>
					<td width="7%">${plan.gdh }</td>
					<td width="7%">${plan.twh }</td>
					<td width="10%">${plan.kcsj }</td>
					<td width="10%">${plan.sjjcsj }</td>
					<td width="7%">${plan.gongZhang }</td>
					<td width="7%">
						<c:if test="${plan.planStatue == -1}">新建</c:if>
						<c:if test="${plan.planStatue == 0}">在修</c:if>
						<c:if test="${plan.planStatue == 1}">待验</c:if>
						<c:if test="${plan.planStatue == 2}">交车</c:if>
						<c:if test="${plan.planStatue == 3}">转出</c:if>
					</td>
					<td width="7%"><a href="<%=basePath%>recorderAction!queryRecorder.action?rjhmId=${plan.rjhmId }&jwdCode=${plan.jwdcode}" target="_blank">记录详情</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty planPris }">
				<tr>
					<td colspan="11">没有相应的记录!</td>
				</tr>
			</c:if>
			<tr><td colspan="11"></td></tr>
		</table>
	</div>
</body>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
</html>
