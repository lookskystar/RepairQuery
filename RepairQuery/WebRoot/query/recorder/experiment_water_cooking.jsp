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
<title>${unitType}&nbsp;&nbsp;${flowval }
水阻试验检修记录
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
	//合并表格的单元格
	function cellMege(table,col){
		var lines=$('#'+table+' tr').size();
		if(lines<=2) return ;
		var v,l=0,current='',index=1;
		for(var i=1;i<lines;i++){
			var tr=$('#'+table+' tr').eq(i);
			v=tr.find('td[name="'+col+'"]').html();
			//alert(tr.find('td[name="secondname"]').size());
			l++;
			//alert(v);
			if(v!=current||i==(lines-1)){//如果两个值不相同或者是最后一行
				if(i==(lines-1)) l++;
				if(l>1){//草果两行，合并
					var td=$('#'+table+' tr').eq(index).find('td[name="'+col+'"]');
					td.attr('rowspan',l);
					for(var j=1;j<l;j++){
						var td=$('#'+table+' tr').eq(index+j).find('td[name="'+col+'"]').remove();
					}
				}
				l=0;//从新计数
				current=v;
				index=i;
			}
		}
	}
	$(function(){
		cellMege('datatabel','firstname');
		cellMege('datatabel','secondname');
		cellMege('datatabel','fixitemname');
		$('#scoll_div_id').floatdiv("middletop");
	});

    function SaveAsFile(){ 
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));   
		LODOP.PRINT_INIT(""); 
		LODOP.ADD_PRINT_TABLE(100,20,500,80,document.getElementById("content").innerHTML); 
		LODOP.SET_SAVE_MODE("Orientation",1); //Excel文件的页面设置：横向打印   1-纵向,2-横向;
		LODOP.SET_SAVE_MODE("LINESTYLE",1);//导出后的Excel是否有边框
		LODOP.SAVE_TO_FILE("${datePlan.jcType }-${datePlan.jcnum }-${datePlan.fixFreque}水阻试验记录.xls"); 
	};	

	var LODOP; //声明为全局变量 
	function preview(){
		CreatePrintPage();
		LODOP.PREVIEW();
	}

	function setup(){
		CreatePrintPage();
		LODOP.PRINT_DESIGN();
	}

	function print(){
		CreatePrintPage();
		LODOP.PRINT();	
	}

	function CreatePrintPage(){
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		var strBodyStyle="<style>table,td{border:1 solid #000000;border-collapse:collapse}</style>";
		var strFormHtml=strBodyStyle+"<body>"+document.getElementById("content").innerHTML+"</body>";
		LODOP.PRINT_INITA(10,10,754,453,"打印控件操作");
		LODOP.SET_PRINT_PAGESIZE (2, 0, 0,"A4");
		LODOP.ADD_PRINT_TEXT(21,300,300,30,"${datePlan.jcType }-${datePlan.jcnum }水阻试验记录\n");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXT(40,60,350,30,"机车号:${datePlan.jcType } ${datePlan.jcnum }  修程:${datePlan.fixFreque}  检修日期:${datePlan.kcsj }");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.ADD_PRINT_HTM(63,38,684,330,strFormHtml);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.ADD_PRINT_LINE(414,23,413,725,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
		//LODOP.ADD_PRINT_TEXT(421,37,144,22,"左下脚的文本小标题");
		//LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
		LODOP.ADD_PRINT_TEXT(421,542,165,22,"第#页/共&页");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
	}
</script>
<style type="text/css">
	TABLE.bluetable {
		FONT: 100% Arial, Helvetica, sans-serif
	}
	TD {
		FONT: 100% Arial, Helvetica, sans-serif
	}
	TABLE.bluetable {
		MARGIN: 0.1em 0px; BORDER-COLLAPSE: collapse
	}
	TABLE.bluetable TH {
		BORDER-BOTTOM: #2b3b58 1px solid; TEXT-ALIGN: left; BORDER-LEFT: #2b3b58 1px solid; PADDING-BOTTOM: 0.5em; PADDING-LEFT: 0.5em; PADDING-RIGHT: 0.5em; BORDER-TOP: #2b3b58 1px solid; BORDER-RIGHT: #2b3b58 1px solid; PADDING-TOP: 0.5em
	}
	TABLE.bluetable TD {
		BORDER-BOTTOM: #2b3b58 1px solid; TEXT-ALIGN: center; BORDER-LEFT: #2b3b58 1px solid; PADDING-BOTTOM: 0.5em; PADDING-LEFT: 0.5em; PADDING-RIGHT: 0.5em; BORDER-TOP: #2b3b58 1px solid; BORDER-RIGHT: #2b3b58 1px solid; PADDING-TOP: 0.5em
	}
	TABLE.bluetable TH {
		BACKGROUND: url(images/tr_back.gif) #328aa4 repeat-x; COLOR: #fff
	}
	TABLE.bluetable TD {
		BACKGROUND: #FFFFFF
	}
	TABLE.bluetable TR.even TD {
		BACKGROUND: #e5f1f4
	}
	TABLE.bluetable TR.odd TD {
		BACKGROUND: #f8fbfc
	}
	TABLE.bluetable TH.over {
		BACKGROUND: #4a98af
	}
	TABLE.bluetable TR.even TH.over {
		BACKGROUND: #4a98af
	}
	TABLE.bluetable TR.odd TH.over {
		BACKGROUND: #4a98af
	}
	TABLE.bluetable TH.down {
		BACKGROUND: #bce774
	}
	TABLE.bluetable TR.even TH.down {
		BACKGROUND: #bce774
	}
	TABLE.bluetable TR.odd TH.down {
		BACKGROUND: #bce774
	}
	TABLE.bluetable TH.selected {
		
	}
	TABLE.bluetable TR.even TH.selected {
		
	}
	TABLE.bluetable TR.odd TH.selected {
		
	}
	TABLE.bluetable TD.over {
		BACKGROUND: #ecfbd4
	}
	TABLE.bluetable TR.even TD.over {
		BACKGROUND: #ecfbd4
	}
	TABLE.bluetable TR.odd TD.over {
		BACKGROUND: #ecfbd4
	}
	TABLE.bluetable TD.down {
		BACKGROUND: #bce774; COLOR: #fff
	}
	TABLE.bluetable TR.even TD.down {
		BACKGROUND: #bce774; COLOR: #fff
	}
	TABLE.bluetable TR.odd TD.down {
		BACKGROUND: #bce774; COLOR: #fff
	}
	TABLE.bluetable TD.selected {
		BACKGROUND: #bce774; COLOR: #555
	}
	TABLE.bluetable TR.even TD.selected {
		BACKGROUND: #bce774; COLOR: #555
	}
	TABLE.bluetable TR.odd TD.selected {
		BACKGROUND: #bce774; COLOR: #555
	}
	TABLE.bluetable TD.empty {
		BACKGROUND: #fff
	}
	TABLE.bluetable TR.odd TD.empty {
		BACKGROUND: #fff
	}
	TABLE.bluetable TR.even TD.empty {
		BACKGROUND: #fff
	}
</style>
</head>

<body bgcolor="#f8f7f7">
<form id="form1" name="form1" method="post" action="">
<!-- 浮动导航菜单start -->
<div id="scoll_div_id" style="background:#328aa4;width:864px;height:23px;">
<ul id="nav">
    <li><a href="javascript:void(0);">◇整车记录 </a>
       <ul>
         <li><a href="<%=basePath%>recorderAction!getZxInfoByJC.action?rjhmId=${datePlan.rjhmId}&psize=100&jwdCode=${jwdCode}">整车检修记录</a></li>
         <li><a href="<%=basePath%>recorderAction!findJCAll.action?rjhmId=${datePlan.rjhmId}&jcStype=${datePlan.jcType }&jwdCode=${jwdCode}">检修配件记录</a></li>
       </ul>
    </li>
    <li><a href="javascript:void(0);">◇机车部件</a>
      <ul>
            <c:forEach items="${units}" var="unit">
               <li><a href="<%=basePath%>recorderAction!queryZxRecorder.action?rjhmId=${datePlan.rjhmId}&fristUnit=${unit.firstunitid }&jwdCode=${jwdCode}">${unit.firstunitname }</a></li>
            </c:forEach>
      </ul>
    </li>
    <li><a href="javascript:void(0);">◇检修班组</a>
       <ul>
            <c:forEach items="${bzs}" var="bz" varStatus="index">
	            <li><a href="<%=basePath %>recorderAction!getZxInfoByBZ.action?rjhmId=${datePlan.rjhmId}&workFlag=1&teamId=${bz.proteamid}&jwdCode=${jwdCode}">${bz.proteamname }</a></li>
            </c:forEach>
      </ul>
    </li>
     <li><a href="javascript:void(0);" style="background-color:#fff;color:blue;">◇试验记录</a>
       <ul>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=2&jwdCode=${jwdCode}" style="background-color:#fff;color:blue;">水阻试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=5&jwdCode=${jwdCode}">顶轮试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=4&jwdCode=${jwdCode}">高低压试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=3&jwdCode=${jwdCode}">试运行试验</a></li>
      </ul>
    </li>
    <li><a href="<%=basePath%>recorderAction!findJCPJ.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇配件记录</a></li>
    <li><a href="<%=basePath%>recorderAction!findJCTS.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇探伤记录</a></li>
    <li><a href="<%=basePath%>recorderAction!listZXLeftWorkRecord.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇未完成记录 </a></li>
    <li><a href="<%=basePath %>recorderAction!getAllInfoPre.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇报活记录</a></li>
    <li><a href="javascript:void(0);" onclick="SaveAsFile();">◇记录导出 </a></li>
    <li><a href="javascript:void(0);">◇打印 </a>
        <ul>
            <li><a href="javascript:void(0);" onclick="setup();">打印设置</a></li>
            <li><a href="javascript:void(0);" onclick="preview();">打印预览</a></li>
            <li><a href="javascript:void(0);" onclick="print();">直接打印</a></li>
      </ul>
    </li>
</ul>
</div>
<br><br>
<!-- 浮动导航菜单end -->
<center>
   <div id="content">
	<table style="text-align:center;" width="1100" border="1" cellspacing="0" cellpadding="0"  class="bluetable">
		<tr>
			<th class="style10" style="text-align: center; font-family: 宋体, Arial, Helvetica, sans-serif; font-size: 24px;font-weight: bolder;" colspan="28">
					内燃机车水阻试验记录
			</th>
		</tr>
		<tr class="bgf3f3f3">
			<td width="8%" class="bge3e3e3">
				机车号
			</td>
			<td colspan="3">
				${datePlan.jcType}-${datePlan.jcnum}
			</td>
			<td width="4%">
				修程
			</td>
			<td colspan="2">
				${datePlan.fixFreque}
			</td>
			<td width="4%">
				时间
			</td>
			<td colspan="4">
				${experiment.empAffirmTime}
			</td>
			<td colspan="16">
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				磨合试验
			</td>
			<td colspan="5">
				空载磨合
			</td>
			<td colspan="5">
				第一次负载磨合
			</td>
			<td colspan="3">
				第二次负载磨合
			</td>
			<td colspan="3">
				第三次负载磨合
			</td>
			<td colspan="11">
				各部状态及参数
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				手柄位
			</td>
			<td width="4%">
				0
			</td>
			<td width="4%">
				2
			</td>
			<td>
				4
			</td>
			<td>
				6
			</td>
			<td width="4%">
				8
			</td>
			<td width="4%">
				1
			</td>
			<td>
				2
			</td>
			<td width="4%">
				4
			</td>
			<td width="4%">
				6
			</td>
			<td width="4%">
				8
			</td>
			<td width="4%">
				10
			</td>
			<td width="4%">
				11
			</td>
			<td width="4%">
				13
			</td>
			<td width="4%">
				14
			</td>
			<td width="4%">
				15
			</td>
			<td width="4%">
				16
			</td>
			<td colspan="11">
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				转速(A型)
			</td>
			<td>
				500&plusmn;10
			</td>
			<td>
				&plusmn;10
			</td>
			<td>
				620&plusmn;10
			</td>
			<td>
				700&plusmn;10
			</td>
			<td>
				780&plusmn;10
			</td>
			<td>
				500&plusmn;10
			</td>
			<td>
				&plusmn;10
			</td>
			<td>
				620&plusmn;10
			</td>
			<td>
				700&plusmn;10
			</td>
			<td>
				780&plusmn;10
			</td>
			<td>
				860&plusmn;10
			</td>
			<td>
				&plusmn;10
			</td>
			<td>
				980&plusmn;10
			</td>
			<td>
				1020&plusmn;10
			</td>
			<td>
				&plusmn;10
			</td>
			<td>
				1100&plusmn;10
			</td>
			<td colspan="3" width="6%">
				1SJ 延时
			</td>
			<td colspan="2" width="6%">
				${jceis.c1}
			</td>
			<td colspan="4">
				双风泵打风时间
			</td>
			<td colspan="2">
				${jceis.d1}
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				rpm（B型）
			</td>
			<td>
				430&plusmn;10
			</td>
			<td>
				470&plusmn;10
			</td>
			<td>
				545&plusmn;10
			</td>
			<td>
				620&plusmn;10
			</td>
			<td>
				695&plusmn;10
			</td>
			<td>
				430&plusmn;10
			</td>
			<td>
				470&plusmn;10
			</td>
			<td>
				545&plusmn;10
			</td>
			<td>
				620&plusmn;10
			</td>
			<td>
				695&plusmn;10
			</td>
			<td>
				775&plusmn;10
			</td>
			<td>
				810&plusmn;10
			</td>
			<td>
				890&plusmn;10
			</td>
			<td>
				925&plusmn;10
			</td>
			<td>
				960&plusmn;10
			</td>
			<td>
				1000&plusmn;10
			</td>
			<td colspan="2">
				&nbsp;
			</td>
			<td colspan="3">
				&nbsp;
			</td>
			<td colspan="3">
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				时间（min）
			</td>
			<td>
				20
			</td>
			<td>
				15
			</td>
			<td>
				15
			</td>
			<td>
				20
			</td>
			<td>
				20
			</td>
			<td>
				20
			</td>
			<td>
				15
			</td>
			<td>
				15
			</td>
			<td>
				20
			</td>
			<td>
				20
			</td>
			<td>
				15
			</td>
			<td>
				15
			</td>
			<td>
				15
			</td>
			<td>
				15
			</td>
			<td>
				15
			</td>
			<td>
				60
			</td>
			<td colspan="3">
				联调波动次数
			</td>
			<td colspan="2">
				${jceis.c2 }
			</td>
			<td colspan="4">
				稳定时间（s）
			</td>
			<td colspan="2">
				${jceis.d2 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				起止时分
			</td>
			<td>
				${jceis.A1a1}
			</td>
			<td>
				${jceis.A1a2}
			</td>
			<td>
				${jceis.A1a3}
			</td>
			<td>
				${jceis.A1a4}
			</td>
			<td>
				${jceis.A1a5}
			</td>
			<td>
				${jceis.A1a6}
			</td>
			<td>
				${jceis.A1a7}
			</td>
			<td>
				${jceis.A1a8}
			</td>
			<td>
				${jceis.A1a9}
			</td>
			<td>
				${jceis.A1a10}
			</td>
			<td>
				${jceis.A1a11}
			</td>
			<td>
				${jceis.A1a12}
			</td>
			<td>
				${jceis.A1a13}
			</td>
			<td>
				${jceis.A1a14}
			</td>
			<td>
				${jceis.A1a15}
			</td>
			<td>
				${jceis.A1a16}
			</td>
			<td colspan="3">
				柴油机升速时间
			</td>
			<td colspan="2">
				${jceis.c3 }
			</td>
			<td colspan="4">
				柴油机降速时间
			</td>
			<td colspan="2">
				${jceis.d3 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				电流（A）
			</td>
			<td>
				${jceis.A2a1}
			</td>
			<td>
				${jceis.A2a2}
			</td>
			<td>
				${jceis.A2a3}
			</td>
			<td>
				${jceis.A2a4}
			</td>
			<td>
				${jceis.A2a5}
			</td>
			<td>
				${jceis.A2a6}
			</td>
			<td>
				${jceis.A2a7}
			</td>
			<td>
				${jceis.A2a8}
			</td>
			<td>
				${jceis.A2a9}
			</td>
			<td>
				${jceis.A2a10}
			</td>
			<td>
				${jceis.A2a11}
			</td>
			<td>
				${jceis.A2a12}
			</td>
			<td>
				${jceis.A2a13}
			</td>
			<td>
				${jceis.A2a14}
			</td>
			<td>
				${jceis.A2a15}
			</td>
			<td>
				${jceis.A2a16}
			</td>
			<td colspan="3">
				差示停机
			</td>
			<td colspan="2">
				${jceis.c4 }
			</td>
			<td colspan="4">
				过流继电器
			</td>
			<td colspan="2">
				${jceis.d4 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				电压（V）
			</td>
			<td>
				${jceis.A3a1}
			</td>
			<td>
				${jceis.A3a2}
			</td>
			<td>
				${jceis.A3a3}
			</td>
			<td>
				${jceis.A3a4}
			</td>
			<td>
				${jceis.A3a5}
			</td>
			<td>
				${jceis.A3a6}
			</td>
			<td>
				${jceis.A3a7}
			</td>
			<td>
				${jceis.A3a8}
			</td>
			<td>
				${jceis.A3a9}
			</td>
			<td>
				${jceis.A3a10}
			</td>
			<td>
				${jceis.A3a11}
			</td>
			<td>
				${jceis.A3a12}
			</td>
			<td>
				${jceis.A3a13}
			</td>
			<td>
				${jceis.A3a14}
			</td>
			<td>
				${jceis.A3a15}
			</td>
			<td>
				${jceis.A3a16}
			</td>
			<td colspan="3">
				紧急停机
			</td>
			<td colspan="2">
				${jceis.c5 }
			</td>
			<td colspan="4">
				接地继电器
			</td>
			<td colspan="2">
				${jceis.d5 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				缸号
			</td>
			<td>
				1
			</td>
			<td>
				2
			</td>
			<td>
				3
			</td>
			<td>
				4
			</td>
			<td>
				5
			</td>
			<td>
				6
			</td>
			<td>
				7
			</td>
			<td>
				8
			</td>
			<td>
				9
			</td>
			<td>
				10
			</td>
			<td>
				11
			</td>
			<td>
				12
			</td>
			<td>
				13
			</td>
			<td>
				14
			</td>
			<td>
				15
			</td>
			<td>
				16
			</td>
			<td colspan="3">
				极限停机
			</td>
			<td colspan="2">
				${jceis.c6 }
			</td>
			<td colspan="4">
				水温继电器
			</td>
			<td colspan="2">
				${jceis.d6 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				压缩压力(MPa)
			</td>
			<td>
				${jceis.B1b1}
			</td>
			<td>
				${jceis.B1b2}
			</td>
			<td>
				${jceis.B1b3}
			</td>
			<td>
				${jceis.B1b4}
			</td>
			<td>
				${jceis.B1b5}
			</td>
			<td>
				${jceis.B1b6}
			</td>
			<td>
				${jceis.B1b7}
			</td>
			<td>
				${jceis.B1b8}
			</td>
			<td>
				${jceis.B1b9}
			</td>
			<td>
				${jceis.B1b10}
			</td>
			<td>
				${jceis.B1b11}
			</td>
			<td>
				${jceis.B1b12}
			</td>
			<td>
				${jceis.B1b13}
			</td>
			<td>
				${jceis.B1b14}
			</td>
			<td>
				${jceis.B1b15}
			</td>
			<td>
				${jceis.B1b16}
			</td>
			<td colspan="3">
				油压继电器（停机）
			</td>
			<td colspan="2">
				${jceis.c7 }
			</td>
			<td colspan="4">
				空转减载
			</td>
			<td colspan="2">
				${jceis.d7 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				爆发压力(MPa)
			</td>
			<td>
				${jceis.B2b1}
			</td>
			<td>
				${jceis.B2b2}
			</td>
			<td>
				${jceis.B2b3}
			</td>
			<td>
				${jceis.B2b4}
			</td>
			<td>
				${jceis.B2b5}
			</td>
			<td>
				${jceis.B2b6}
			</td>
			<td>
				${jceis.B2b7}
			</td>
			<td>
				${jceis.B2b8}
			</td>
			<td>
				${jceis.B2b9}
			</td>
			<td>
				${jceis.B2b10}
			</td>
			<td>
				${jceis.B2b11}
			</td>
			<td>
				${jceis.B2b12}
			</td>
			<td>
				${jceis.B2b13}
			</td>
			<td>
				${jceis.B2b14}
			</td>
			<td>
				${jceis.B2b15}
			</td>
			<td>
				${jceis.B2b16}
			</td>
			<td colspan="3">
				油压继电器（卸载）
			</td>
			<td colspan="2">
				${jceis.c8 }
			</td>
			<td colspan="4">
				过压动作值（V）
			</td>
			<td colspan="2">
				${jceis.d8 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				支管温度(℃)
			</td>
			<td>
				${jceis.B3b1}
			</td>
			<td>
				${jceis.B3b2}
			</td>
			<td>
				${jceis.B3b3}
			</td>
			<td>
				${jceis.B3b4}
			</td>
			<td>
				${jceis.B3b5}
			</td>
			<td>
				${jceis.B3b6}
			</td>
			<td>
				${jceis.B3b7}
			</td>
			<td>
				${jceis.B3b8}
			</td>
			<td>
				${jceis.B3b9}
			</td>
			<td>
				${jceis.B3b10}
			</td>
			<td>
				${jceis.B3b11}
			</td>
			<td>
				${jceis.B3b12}
			</td>
			<td>
				${jceis.B3b13}
			</td>
			<td>
				${jceis.B3b14}
			</td>
			<td>
				${jceis.B3b15}
			</td>
			<td>
				${jceis.B3b16}
			</td>
			<td colspan="3">
				曲轴箱压力
			</td>
			<td colspan="2">
				${jceis.c9 }
			</td>
			<td colspan="4">
				故障发电值（V）
			</td>
			<td colspan="2">
				${jceis.d9 }
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				总管温度(℃)
			</td>
			<td>
				${jceis.B4b1}
			</td>
			<td>
				${jceis.B4b2}
			</td>
			<td>
				${jceis.B4b3}
			</td>
			<td>
				${jceis.B4b4}
			</td>
			<td>
				${jceis.B4b5}
			</td>
			<td>
				${jceis.B4b6}
			</td>
			<td>
				${jceis.B4b7}
			</td>
			<td>
				${jceis.B4b8}
			</td>
			<td>
				${jceis.B4b9}
			</td>
			<td>
				${jceis.B4b10}
			</td>
			<td>
				${jceis.B4b11}
			</td>
			<td>
				${jceis.B4b12}
			</td>
			<td>
				${jceis.B4b13}
			</td>
			<td>
				${jceis.B4b14}
			</td>
			<td>
				${jceis.B4b15}
			</td>
			<td>
				${jceis.B4b16}
			</td>
			<td colspan="3">
				供油齿条刻线
			</td>
			<td colspan="2">
				${jceis.c10 }
			</td>
			<td colspan="4">
				油马达位置
			</td>
			<td colspan="2">
				${jceis.d10 }
			</td>
		</tr>
		<tr>
			<td rowspan="11" class="bge3e3e3">
				恒 功 率 特 性 调 整
			</td>
			<td colspan="2" rowspan="2">
				柴油机转速（rpm）
			</td>
			<td colspan="6">
				主整流柜输出
			</td>
			<td colspan="8">
				励磁参数
			</td>
			<td colspan="4">
				增压空气压力（KPa）
			</td>
			<td width="4%" colspan="2">
				前
			</td>
			<td width="4%" colspan="2">
				${jceis.H1h1 }
			</td>
			<td width="4%" colspan="2">
				后
			</td>
			<td width="4%">
				${jceis.H1h2 }
			</td>
		</tr>
		<tr>
			<td colspan="2">
				电流（A）
			</td>
			<td colspan="2">
				电压（V）
			</td>
			<td colspan="2">
				功率（KW）
			</td>
			<td colspan="2">
				励磁电流（A）
			</td>
			<td colspan="2">
				励磁电压（V）
			</td>
			<td colspan="2">
				励磁机励磁电流（A）
			</td>
			<td colspan="2">
				测发电机励磁电 流（A）
			</td>
			<td colspan="4">
				柴油机转速(rpm)
			</td>
			<td colspan="4">
				430&plusmn;10
			</td>
			<td colspan="3">
				1000&plusmn;10
			</td>
		</tr>
		<tr>
			<td colspan="2">
				430&plusmn;10
			</td>
			<td colspan="2">
				${jceis.E1e1 }
			</td>
			<td colspan="2">
				${jceis.E1e2 }
			</td>
			<td colspan="2">
				${jceis.E1e3 }
			</td>
			<td colspan="2">
				${jceis.F1f1}
			</td>
			<td colspan="2">
				${jceis.F1f2}
			</td>
			<td colspan="2">
				${jceis.F1f3}
			</td>
			<td colspan="2">
				${jceis.F1f4}
			</td>
			<td colspan="4">
				主机油泵出口压力（KPa）
			</td>
			<td colspan="4">
				${jceis.H2h1 }
			</td>
			<td colspan="3">
				${jceis.H2h2 }
			</td>
		</tr>
		<tr>
			<td colspan="2">
				700&plusmn;10
			</td>
			<td colspan="2">
				${jceis.E2e1}
			</td>
			<td colspan="2">
				${jceis.E2e2}
			</td>
			<td colspan="2">
				${jceis.E2e3}
			</td>
			<td colspan="2">
				${jceis.F2f1}
			</td>
			<td colspan="2">
				${jceis.F2f2}
			</td>
			<td colspan="2">
				${jceis.F2f3}
			</td>
			<td colspan="2">
				${jceis.F2f4}
			</td>
			<td colspan="4">
				滤清器前机油压力(KPa)
			</td>
			<td colspan="4">
				${jceis.H3h1}
			</td>
			<td colspan="3">
				${jceis.H3h2}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				850&plusmn;10
			</td>
			<td colspan="2">
				${jceis.E3e1}
			</td>
			<td colspan="2">
				${jceis.E3e2}
			</td>
			<td colspan="2">
				${jceis.E3e3}
			</td>
			<td colspan="2">
				${jceis.F3f1}
			</td>
			<td colspan="2">
				${jceis.F3f2}
			</td>
			<td colspan="2">
				${jceis.F3f3}
			</td>
			<td colspan="2">
				${jceis.F3f4}
			</td>
			<td colspan="4">
				滤清器后机油压力(KPa)
			</td>
			<td colspan="4">
				${jceis.H4h1}
			</td>
			<td colspan="3">
				${jceis.H4h2}
			</td>
		</tr>
		<tr>
			<td colspan="2" rowspan="6">
				1000&plusmn;10
			</td>
			<td colspan="2">
				3000
			</td>
			<td colspan="2">
				${jceis.E4e1}
			</td>
			<td colspan="2">
				${jceis.E4e2}
			</td>
			<td colspan="2">
				${jceis.F4f1}
			</td>
			<td colspan="2">
				${jceis.F4f2}
			</td>
			<td colspan="2">
				${jceis.F4f3}
			</td>
			<td colspan="2">
				${jceis.F4f4}
			</td>
			<td colspan="4">
				机 油 末 端 压 力(KPa)
			</td>
			<td colspan="4">
				${jceis.H5h1}
			</td>
			<td colspan="3">
				${jceis.H5h2}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				3200
			</td>
			<td colspan="2">
				${jceis.E5e1}
			</td>
			<td colspan="2">
				${jceis.E5e1}
			</td>
			<td colspan="2">
				${jceis.F5f1}
			</td>
			<td colspan="2">
				${jceis.F5f2}
			</td>
			<td colspan="2">
				${jceis.F5f3}
			</td>
			<td colspan="2">
				${jceis.F5f4}
			</td>
			<td colspan="4">
				前增压器机油压力(KPa)
			</td>
			<td colspan="4">
				${jceis.H6h1}
			</td>
			<td colspan="3">
				${jceis.H6h2}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				3600
			</td>
			<td colspan="2">
				${jceis.E6e1}
			</td>
			<td colspan="2">
				${jceis.E6e2}
			</td>
			<td colspan="2">
				${jceis.F6f1}
			</td>
			<td colspan="2">
				${jceis.F6f2}
			</td>
			<td colspan="2">
				${jceis.F6f3}
			</td>
			<td colspan="2">
				${jceis.F6f4}
			</td>
			<td colspan="4">
				后增压器机油压力(KPa)
			</td>
			<td colspan="4">
				${jceis.H7h1}
			</td>
			<td colspan="3">
				${jceis.H7h2}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				4000
			</td>
			<td colspan="2">
				${jceis.E7e1}
			</td>
			<td colspan="2">
				${jceis.E7e2}
			</td>
			<td colspan="2">
				${jceis.F7f1}
			</td>
			<td colspan="2">
				${jceis.F7f2}
			</td>
			<td colspan="2">
				${jceis.F7f3}
			</td>
			<td colspan="2">
				${jceis.F7f4}
			</td>
			<td colspan="4">
				燃油压力(KPa)
			</td>
			<td colspan="4">
				${jceis.H8h1}
			</td>
			<td colspan="3">
				${jceis.H8h2}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				4400
			</td>
			<td colspan="2">
				${jceis.E8e1}
			</td>
			<td colspan="2">
				${jceis.E8e2}
			</td>
			<td colspan="2">
				${jceis.F8f1}
			</td>
			<td colspan="2">
				${jceis.F8f2}
			</td>
			<td colspan="2">
				${jceis.F8f3}
			</td>
			<td colspan="2">
				${jceis.F8f4}
			</td>
			<td colspan="4">
				机油温度(℃)
			</td>
			<td colspan="4">
				${jceis.H9h1}
			</td>
			<td colspan="3">
				${jceis.H9h2}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				4800
			</td>
			<td colspan="2">
				${jceis.E9e1}
			</td>
			<td colspan="2">
				${jceis.E9e2}
			</td>
			<td colspan="2">
				${jceis.F9f1}
			</td>
			<td colspan="2">
				${jceis.F9f2}
			</td>
			<td colspan="2">
				${jceis.F9f3}
			</td>
			<td colspan="2">
				${jceis.F9f4}
			</td>
			<td colspan="4">
				冷却水温度(℃)
			</td>
			<td colspan="4">
				${jceis.H10h1}
			</td>
			<td colspan="3">
				${jceis.H10h2}
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				故障功率调整
			</td>
			<td colspan="2">
				1000&plusmn;10
			</td>
			<td colspan="2">
				4800
			</td>

			<td colspan="2">
				${jceis.g1}
			</td>
			<td colspan="2">
				${jceis.g2}
			</td>
			<td colspan="2">
				${jceis.g3}
			</td>
			<td colspan="2">
				${jceis.g4}
			</td>
			<td colspan="2">
				${jceis.g5}
			</td>

			<td colspan="2">
				${jceis.g6}
			</td>
			<td colspan="2">
				
			</td>
			<td width="2%">
				
			</td>
			<td width="6%">
				
			</td>
			<td colspan="2">
				
			</td>
			<td colspan="2">
				
			</td>
			<td colspan="2">
				
			</td>
			<td>
				
			</td>
		</tr>
		<tr>
			<td class="bge3e3e3">
				平稳启动调整
			</td>
			<td colspan="8">
				${jceis.i1}
			</td>
			<td colspan="3">
				空磨拆检情况
			</td>
			<td colspan="3">
				第一次负磨拆检情况
			</td>
			<td colspan="4">
				第二次负磨拆检情况
			</td>
			<td colspan="5">
				第三次负磨拆检情况
			</td>
			<td colspan="5">
				试验记事
			</td>
		</tr>
		<tr>
			<td rowspan="2" class="bge3e3e3">
				绝缘电阻测定
			</td>
			<td colspan="2">
				测定日期
			</td>
			<td colspan="2">
				主电路/地
			</td>
			<td colspan="2">
				控制电路/地
			</td>
			<td colspan="2">
				主/控制电路
			</td>
			<td colspan="3" rowspan="2">
				${jceis.k1}
			</td>
			<td colspan="3" rowspan="2">
				${jceis.k2}
			</td>
			<td colspan="4" rowspan="2">
				${jceis.k3}
			</td>
			<td colspan="5" rowspan="2">
				${jceis.k4}
			</td>
			<td colspan="5" rowspan="2">
				${jceis.o1}
			</td>
		</tr>
		<tr>
			<td colspan="2">
				${jceis.j1}
			</td>
			<td colspan="2">
				${jceis.j2}
				M&Omega;
			</td>
			<td colspan="2">
				${jceis.j3}
				M&Omega;
			</td>
			<td colspan="2">
				${jceis.j4}
				M&Omega;
			</td>
		</tr>
		<tr>
			<td rowspan="2" class="bge3e3e3">
				试验人员
			</td>
			<td colspan="6">
				工人
			</td>
			<td colspan="4">
				工长
			</td>
			<td colspan="4">
				质检员
			</td>
			<td colspan="4">
				技术员
			</td>
			<td colspan="4">
				交车工长
			</td>
			<td colspan="6">
				验收员
			</td>
		</tr>
		<tr>
			<td colspan="6"><br/>
			    ${fn:substring(experiment.fixEmp,0,fn:length(experiment.fixEmp)-1)}
			</td>
			<td colspan="4">
				${experiment.leader}
			</td>
			<td colspan="4">
				${experiment.qi}
			</td>
			<td colspan="4">
				${experiment.teachName}
			</td>
			<td colspan="4">
				${experiment.commitLead}
			</td>
			<td colspan="6">
				${experiment.accepter}
			</td>
		</tr>

	</table>
	</div>
</center>
</form>
</body>
</html>