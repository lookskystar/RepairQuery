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
<title>高低压试检修记录</title>
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
			LODOP.SAVE_TO_FILE("${datePlan.jcType }-${datePlan.jcnum }高低压试验记录.xls"); 
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
			LODOP.ADD_PRINT_TEXT(21,300,300,30,"${datePlan.jcType }-${datePlan.jcnum }高低压试验记录\n");
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
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=2&jwdCode=${jwdCode}">水阻试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=5&jwdCode=${jwdCode}">顶轮试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=4&jwdCode=${jwdCode}" style="background-color:#fff;color:blue;">高低压试验</a></li>
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
<br><br><br/>
<!-- 浮动导航菜单end -->
<center>
	<div id="content">
    <table border=1 cellspacing=0 cellpadding=0 width=580 class="bluetable">
      <tr>
        <th class="style10" style="text-align: center; font-family: 宋体, Arial, Helvetica, sans-serif; font-size: 24px;font-weight: bolder;" colspan="38">
          ${datePlan.jcType}型机车低高压试验及试运作业过程控制记录<br/><br/>QR-JX-302-2011
        </th>
      </tr>
      <tr>
        <td colspan=3>机车号</td>
        <td width=67 colspan=7>${datePlan.jcType}-${datePlan.jcnum}</td>
        <td width=40 colspan=3>修程</td>
        <td width=48 colspan=4>${datePlan.fixFreque}</td>
        <td width=81 colspan=5>扣车时间</span></p></td>
        <td width=81 colspan=6>${datePlan.kcsj}</td>
        <td width=81 colspan=5>交车时间</td>
        <td width=86 colspan=5>${datePlan.sjjcsj}</td>
      </tr>
      <tr>
        <td width=586 colspan=38>上台前高压试验情况：${jceis.gdy_1}</td>
      </tr>
      <tr>
        <td width=112 colspan=4>试验时间</td>
        <td width=95 colspan=10>${experiment.empAffirmTime}</td>
        <td width=108 colspan=7>地勤司机</td>
        <td width=96 colspan=7>${jceis.gdy_2} </td>
        <td width=84 colspan=5>交车工长</td>
        <td width=92 colspan=5>${experiment.commitLead}</td>
      </tr>
      <tr>
        <td width=51 rowspan=10>低 压 试 验</td>
        <td width=535 colspan=37>低压试验前绝缘检测</td>
      </tr>
      <tr>
        <td width=144 colspan=9>主对地</td>
        <td width=129 colspan=13>辅对地</td>
        <td width=129 colspan=9>控对地</td>
        <td width=133 colspan=6>原边对地</td>
      </tr>
      <tr>
        <td width=144 colspan=9>${jceis.gdy_3}MΩ</td>
        <td width=129 colspan=13>${jceis.gdy_4}MΩ</td>
        <td width=129 colspan=9>${jceis.gdy_5}MΩ</td>
        <td width=133 colspan=6>${jceis.gdy_6}MΩ</td>
      </tr>
      <tr>
        <td width=535 colspan=37>试验人</td>
      </tr>
      <tr>
        <td width=89 colspan=6>电器质检员</td>
        <td width=89 colspan=6>电子质检员</td>
        <td width=90 colspan=6>制动质检员</td>
        <td width=87 colspan=6>台车质检员</td>
        <td width=78 colspan=6>交车工长</td>
        <td width=103 colspan=7>&nbsp;</td>
      </tr>
      <tr>
        <td width=89 colspan=6>${jceis.gdy_7} </td>
        <td width=89 colspan=6>${jceis.gdy_8} </td>
        <td width=90 colspan=6>${jceis.gdy_9} </td>
        <td width=87 colspan=6>${jceis.gdy_10} </td>
        <td width=78 colspan=6>${experiment.commitLead}</td>
        <td width=103 colspan=7>&nbsp;</td>
      </tr>
      <tr>
        <td width=89 colspan=6>受电弓</td>
        <td width=89 colspan=6>制动</td>
        <td width=90 colspan=6>主断</td>
        <td width=87 colspan=6>架电</td>
        <td width=78 colspan=6>电子</td>
        <td width=103 colspan=7>监控</td>
      </tr>
      <tr>
        <td width=89 colspan=6><br/>${jceis.gdy_11}</td>
        <td width=89 colspan=6>${jceis.gdy_12} </td>
        <td width=90 colspan=6>${jceis.gdy_13} </td>
        <td width=87 colspan=6>${jceis.gdy_14}</td>
        <td width=78 colspan=6>${jceis.gdy_15}</td>
        <td width=103 colspan=7>${jceis.gdy_16} </td>
      </tr>
      <tr>
        <td width=144 colspan=9>试验开始时间</td>
        <td width=129 colspan=9>${jceis.gdy_17}</td>
        <td width=129 colspan=9>试验结束时间</td>
        <td width=133 colspan=10>${jceis.gdy_18} </td>
      </tr>
      <tr>
        <td width=145 colspan=9>试验合格后签认</td>
        <td width=123 colspan=9>交车工长</td>
        <td width=70 colspan=7>${experiment.commitLead}</td>
        <td width=95 colspan=7>验收员</td>
        <td width=103 colspan=5>${experiment.accepter}</td>
      </tr>
      <tr>
        <td width=51 rowspan=8>高压试验</td>
        <td width=144 colspan=9>开始时间</td>
        <td width=129 colspan=9>${jceis.gdy_19} </td>
        <td width=129 colspan=9>结束时间</td>
        <td width=133 colspan=10>${jceis.gdy_20}</td>
      </tr>
      <tr>
        <td width=92 colspan=5 rowspan=2>牵引试验</td>
        <td width=79 colspan=7>1M</td>
        <td width=69 colspan=6>2M</td>
        <td width=75 colspan=5>3M</td>
        <td width=72 colspan=5>4M</td>
        <td width=72 colspan=5>5M</td>
        <td width=76 colspan=4>6M</td>
      </tr>
      <tr>
        <td width=79 colspan=7>${jceis.gdy_21}A</td>
        <td width=69 colspan=6>${jceis.gdy_22}A</td>
        <td width=75 colspan=5>${jceis.gdy_23}A</td>
        <td width=72 colspan=5>${jceis.gdy_24}A</td>
        <td width=72 colspan=5>${jceis.gdy_25}A</td>
        <td width=76 colspan=4>${jceis.gdy_26}A</td>
      </tr>
      <tr>
        <td width=92 colspan=5>制动试验</td>
        <td width=79 colspan=7>励磁电流</td>
        <td width=69 colspan=6>${jceis.gdy_27}A</td>
        <td width=75 colspan=5>制动电流</td>
        <td width=72 colspan=5>${jceis.gdy_28}A</td>
        <td width=72 colspan=5>励磁电流</td>
        <td width=76 colspan=4>${jceis.gdy_29}A</td>
      </tr>
      <tr>
        <td width=92 colspan=5 rowspan=2>空载试验</td>
        <td width=93 colspan=7>0&nbsp;位</td>
        <td width=86 colspan=6>1M电压</td>
        <td width=86 colspan=8>${jceis.gdy_30}V</td>
        <td width=74 colspan=7>6M电压</td>
        <td width=103 colspan=4>${jceis.gdy_31}V</td>
      </tr>
      <tr>
        <td width=93 colspan=7>限&nbsp;压</td>
        <td width=86 colspan=6>1M电压</td>
        <td width=86 colspan=8>${jceis.gdy_32}V</td>
        <td width=74 colspan=7>6M电压</td>
        <td width=103 colspan=4>${jceis.gdy_33}V</td>
      </tr>
      <tr>
        <td width=72 colspan=5>试验人</td>
        <td width=64 colspan=7>${experiment.fixEmp}</td>
        <td width=132 colspan=7>试验开始时间</td>
        <td width=70 colspan=6>${jceis.gdy_34} </td>
        <td width=122 colspan=7>试验结束时间</td>
        <td width=76 colspan=5>${jceis.gdy_35} </td>
      </tr>
      <tr>
        <td width=136 colspan=8>试验合格后签认</td>
        <td width=80 colspan=9>交车工长</td>
        <td width=122 colspan=8>${experiment.commitLead}</td>
        <td width=89 colspan=7>验收员</td>
        <td width=108 colspan=5>${experiment.accepter}</td>
      </tr>
      <tr>
        <td width=171 colspan=8>试运时间</td>
        <td width=415 colspan=30></td>
      </tr>
      <tr>
        <td width=171 colspan=8>交车时间</td>
        <td width=415 colspan=30>${datePlan.sjjcsj}</td>
      </tr>
      <tr height=0>
        <td width=51></td>
        <td width=33></td>
        <td width=27></td>
        <td width=12></td>
        <td width=16></td>
        <td width=4></td>
        <td width=8></td>
        <td width=20></td>
        <td width=16></td>
        <td width=8></td>
        <td width=1></td>
        <td width=11></td>
        <td width=3></td>
        <td width=13></td>
        <td width=6></td>
        <td width=8></td>
        <td width=21></td>
        <td width=9></td>
        <td width=24></td>
        <td width=23></td>
        <td width=4></td>
        <td width=4></td>
        <td width=1></td>
        <td width=15></td>
        <td width=28></td>
        <td width=22></td>
        <td width=17></td>
        <td width=4></td>
        <td width=1></td>
        <td width=9></td>
        <td width=19></td>
        <td width=15></td>
        <td width=25></td>
        <td width=6></td>
        <td width=11></td>
        <td width=6></td>
        <td width=10></td>
        <td width=76></td>
      </tr>
    </table>
    </div>
</center>
</form>
</body>
</html>