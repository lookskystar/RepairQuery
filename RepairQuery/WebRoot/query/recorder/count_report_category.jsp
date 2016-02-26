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
	<!--遮罩start -->
	<script type="text/javascript" src="js/form/loadmask.js"></script>
	<!--遮罩end -->
	<!--选项卡start-->
	<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
	<script type="text/javascript" src="js/nav/jquery.layout.js"></script>
	<script type="text/javascript" src="js/nav/layout.js"></script>
	<script type="text/javascript" src="js/nav/tab.js"></script>
	<!--选项卡end-->
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
	<!-- 选项卡start -->
	<script>
	 var tab;
	 var index=1;
	$( function() {
		 tab= new TabView( {
			containerId :'tab_menu',
			pageid :'page',
			cid :'tab1',
			position :"top"
		});
		tab.add( {
			id :'index0',
			title :"报活类别",
			url :"<%=basePath%>recorderAction!countReportCategoryType.action?jwdCode=${jwdCode }&st=${st }&et=${et }",
			isClosed :true
		});
	});
	function addTab(id,name,url){
		tab.add( {
			id :id,
			title :name,
			url :url
		});
		index++;
	}
	</script>
	<!-- 选项卡end -->
</head>
<body rel="layout">
	 <div class="ui-layout-west">
        <div class="header">分类统计</div>
	    <div class="content">
			<div class="list_menu1">
				<a href="javascript:addTab('index0','报活类别','<%=basePath%>recorderAction!countReportCategoryType.action?jwdCode=${jwdCode }&st=${st }&et=${et }')"><dt><span>报活类别</span></dt></a>
				<a href="javascript:addTab('index1','报活专业','')"><dt><span>报活专业</span></dt></a>
				<a href="javascript:addTab('index2','报活车型','<%=basePath%>recorderAction!countReportCategoryJctype.action?jwdCode=${jwdCode }&st=${st }&et=${et }')"><dt><span>报活车型</span></dt></a>
			</div>
        </div>
    </div>
    <div class="ui-layout-center">
		<div class="subhead" style="height:30px;">
			<div id="tab_menu"></div>
		</div>
        <div class="content">
			<div id="page" style="width:100%;height:100%;"></div>	
        </div>
    </div>
</body>
</html>
