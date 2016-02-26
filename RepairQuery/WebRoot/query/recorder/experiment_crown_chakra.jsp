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
顶试验验检修记录
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

<style>
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
			LODOP.SAVE_TO_FILE("${datePlan.jcType }-${datePlan.jcnum }中修轴检记名记录.xls"); 
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
			LODOP.ADD_PRINT_TEXT(21,300,300,30,"${datePlan.jcType }-${datePlan.jcnum }中修轴检记名记录\n");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"Horient",2);
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			LODOP.ADD_PRINT_TEXT(40,30,350,30,"机车号:${datePlan.jcType } ${datePlan.jcnum }  修程:${datePlan.fixFreque}  检修日期:${datePlan.kcsj }");
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
	body{margin:0; padding:0; font-size:12px;}
		tr{text-algin:center;}
		td{height:20px; line-height: 20px;}
		input{width:100px;}
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
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=2&jwdCode=${jwdCode}">水阻试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=5&jwdCode=${jwdCode}" style="background-color:#fff;color:blue;">顶轮试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=4&jwdCode=${jwdCode}">高低压试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=3&jwdCode=${jwdCode}">试运行试验</a></li>
      </ul>
    </li>
    <li><a href="<%=basePath%>recorderAction!findJCPJ.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇配件记录</a></li>
    <li><a href="<%=basePath%>recorderAction!findJCTS.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇探伤记录</a></li>
    <li><a href="<%=basePath%>recorderAction!listZXLeftWorkRecord.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇未完成记录 </a></li>
    <li><a href="<%=basePath %>recorderAction!getAllInfoPre.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇报活记录</a></li>
    <li><a href="<%=basePath%>recorderAction!searchJCjungong.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇交车竣工 </a></li>
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

<div align="center" id="content">
<br/>
  <table border="1" cellspacing="0" cellpadding="0" width="621" class="bluetable">
    <tr>
      <th class="style10" style="text-align: center; font-family: 宋体, Arial, Helvetica, sans-serif; font-size: 24px;font-weight: bolder;" colspan="8">
      ${datePlan.jcType}型机车中修轴检记名检修记录
	  </th>
    </tr>
	<tr>
      <td colspan="2" style="text-align: center;">
      	机车号
      </td>
      <td colspan="2">${datePlan.jcnum}</td>
      <td width="35">修程</td>
      <td width="66">${datePlan.fixFreque}</td>
      <td width="77">试验时间</td>
      <td width="186">
      	${experiment.empAffirmTime}
      </td>
	</tr>
	<tr height="40">
      <td colspan="4">工作内容  </td>
      <td colspan="2"><p align="center">检  次 </p></td>
      <td colspan="2"><p align="center">工作者 </p></td>
    </tr>
    <tr>
      <td colspan="4" rowspan="2"><p align="center">用JL—601A或JL-201型机车轴承检测仪对轴箱轴承和抱轴承进行振动检测</p></td>
      <td colspan="2" rowspan="2"><p align="center">中修试运前</p></td>
      <td width="77"><p align="center">轴箱轴承</p></td>
      <td width="186"><p align="center">${jceis.a1 }</p></td>
    </tr>
    <tr>
      <td width="77"><p align="center">抱轴承</p></td>
      <td width="186"><p align="center">${jceis.a2}</p></td>
    </tr>
    <tr height="80">
      <td colspan="2"><p align="center">轮  位 </p></td>
      <td width="78"><p align="center">轴 箱 <br />
              轴 承 </p></td>
      <td width="65"><p align="center">抱轴承 </p></td>
      <td colspan="4"><p align="center">主 要 技 术 要 求 </p></td>
    </tr>
    <tr>
      <td width="45" rowspan="2"><p align="center">1</p></td>
      <td width="51"><p align="center">L</p></td>
      <td width="78"><p align="center">${jceis.b1}</p></td>
      <td width="65"><p align="center">${jceis.c1}</p></td>
      <td colspan="4" rowspan="12"><ol>
        <li>轮对转速500r/min(110Km/h)</li>
        <li>故障判断标准</li>
      </ol>
          <table border="1" cellspacing="0" cellpadding="0">
            <tr>
              <td width="41" valign="top"><p align="center">名称</p></td>
              <td width="144" valign="top"><p>注意域<br />
                （任一种情况）</p></td>
              <td width="158" valign="top"><p>故障域<br />
                （任一种情况）</p></td>
            </tr>
            <tr>
              <td width="41"><p align="center">轴箱轴承</p></td>
              <td width="144" valign="top"><ol>
                <li>KV≥15</li>
                <li>20g≤Gmax＜30g</li>
                <li>3g≤Grms＜6g</li>
              </ol></td>
              <td width="158" valign="top"><p>1、Gmax≥30g<br />
                2、Grms≥6g <br />
                3、解调谱对应故障频率纵标度≥10-1 级 </p></td>
            </tr>
            <tr>
              <td width="41"><p align="center">抱轴承</p></td>
              <td width="144" valign="top"><p>1、KV≥4.6<br />
                2、25g≤Gmax＜40g<br />
                3、5g≤Grms＜10g <br />
                （1、3、4、6位）<br />
                4g≤Grms＜8g（2、5位）</p></td>
              <td width="158" valign="top"><p>1、Gmax≥40g<br />
                2、Grms≥10g <br />
                （1、3、4、6位）<br />
                Grms≥5g（2、5位） <br />
                3、解调谱对应故障频率纵标度≥10-1 级 </p></td>
            </tr>
          </table>
        <ol>
          <li>轴承计算故障频率（单位：HZ）</li>
        </ol>
        <table border="1" cellspacing="0" cellpadding="0">
            <tr>
              <td width="98" rowspan="2"><p align="center">名 称</p></td>
              <td width="99" rowspan="2"><p align="center">轴箱轴承</p></td>
              <td width="144" colspan="2" valign="top"><p align="center">抱轴承</p></td>
            </tr>
            <tr>
              <td width="72" valign="top"><p align="center">齿侧</p></td>
              <td width="72" valign="top"><p align="center">非齿侧</p></td>
            </tr>
            <tr>
              <td width="98" valign="top"><p align="center">内圈故障</p></td>
              <td width="99" valign="top"><p align="center">89.7</p></td>
              <td width="72" valign="top"><p align="center">209</p></td>
              <td width="72" valign="top"><p align="center">232</p></td>
            </tr>
            <tr>
              <td width="98" valign="top"><p align="center">外圈故障</p></td>
              <td width="99" valign="top"><p align="center">66.0</p></td>
              <td width="72" valign="top"><p align="center">185</p></td>
              <td width="72" valign="top"><p align="center">208</p></td>
            </tr>
            <tr>
              <td width="98" valign="top"><p align="center">滚子故障</p></td>
              <td width="99" valign="top"><p align="center">29.6</p></td>
              <td width="72" valign="top"><p align="center">76</p></td>
              <td width="72" valign="top"><p align="center">88</p></td>
            </tr>
            <tr>
              <td width="98" valign="top"><p align="center">保持架故障</p></td>
              <td width="99" valign="top"><p align="center">3.9</p></td>
              <td width="72" valign="top"><p align="center">4.3</p></td>
              <td width="72" valign="top"><p align="center">4.3</p></td>
            </tr>
        </table></td>
    </tr>
    <tr>
      <td width="51"><p align="center">R</p></td>
      <td width="78"><p align="center">${jceis.b2}</p></td>
      <td width="65"><p align="center">${jceis.c2}</p></td>
    </tr>
    <tr>
      <td width="45" rowspan="2"><p align="center">2</p></td>
      <td width="51"><p align="center">L</p></td>
      <td width="78"><p align="center">${jceis.b3}</p></td>
      <td width="65"><p align="center">${jceis.c3}</td>
    </tr>
    <tr>
      <td width="51"><p align="center">R</p></td>
      <td width="78"><p align="center">${jceis.b4}</p></td>
      <td width="65"><p align="center">${jceis.c4}</p></td>
    </tr>
    <tr>
      <td width="45" rowspan="2"><p align="center">3</p></td>
      <td width="51"><p align="center">L</p></td>
      <td width="78"><p align="center">${jceis.b5}</p></td>
      <td width="65"><p align="center">${jceis.c5}</p></td>
    </tr>
    <tr>
      <td width="51"><p align="center">R</p></td>
      <td width="78"><p align="center">${jceis.b6}</p></td>
      <td width="65"><p align="center">${jceis.c6}</p></td>
    </tr>
    <tr>
      <td width="45" rowspan="2"><p align="center">4</p></td>
      <td width="51"><p align="center">L</p></td>
      <td width="78"><p align="center">${jceis.b7}</p></td>
      <td width="65"><p align="center">${jceis.c7}</p></td>
    </tr>
    <tr>
      <td width="51"><p align="center">R</p></td>
      <td width="78"><p align="center">${jceis.b8}</p></td>
      <td width="65"><p align="center">${jceis.c8}</p></td>
    </tr>
    <tr>
      <td width="45" rowspan="2"><p align="center">5</p></td>
      <td width="51"><p align="center">L</p></td>
      <td width="78"><p align="center">${jceis.b9}</p></td>
      <td width="65"><p align="center">${jceis.c9}</p></td>
    </tr>
    <tr>
      <td width="51"><p align="center">R</p></td>
      <td width="78"><p align="center">${jceis.b10}</p></td>
      <td width="65"><p align="center">${jceis.c10}</p></td>
    </tr>
    <tr>
      <td width="45" rowspan="2"><p align="center">6</p></td>
      <td width="51"><p align="center">L</p></td>
      <td width="78"><p align="center">${jceis.b11}</p></td>
      <td width="65"><p align="center">${jceis.c11}</p></td>
    </tr>
    <tr>
      <td width="51"><p align="center">R</p></td>
      <td width="78"><p align="center">${jceis.b12}</p></td>
      <td width="65"><p align="center">${jceis.c12}</p></td>
    </tr>
    <tr height="100">
      <td width="45"><p align="center">备注 </p></td>
      <td colspan="7">${jceis.d1 } </td>
    </tr>
    <tr>
      <td width="45" rowspan="2">试验人员</td>
      <td width="45" colspan="2">工人</td>
      <td width="45">工长</td>
      <td width="50">质检员</td>
      <td width="45">技术员</td>
      <td width="45">交车工长</td>
      <td width="45">验收员</td>
    </tr>
    <tr>
      <td width="45" colspan="2">${fn:substring(experiment.fixEmp,0,fn:length(experiment.fixEmp)-1)}</td>
      <td width="45">${experiment.leader}</td>
      <td width="45">${experiment.qi}</td>
      <td width="45">${experiment.teachName}</td>
      <td width="45">${experiment.commitLead}</td>
      <td width="45">${experiment.accepter}</td>
    </tr>
  </table>
</div>
</form>
</body>
</html>