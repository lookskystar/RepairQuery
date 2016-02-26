<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>交车记录</title>
		<!--框架必需start-->	
		<script type="text/javascript" src="js/jquery-1.4.js"></script>
		<script type="text/javascript" src="js/framework.js"></script>
		<script type="text/javascript" src="js/merge.js"></script>
		<script type="text/javascript" src="js/jquery.floatDiv.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
		<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
		<!-- 打印插件 -->
		<script type="text/javascript" src="<%=basePath %>js/LodopFuncs.js"></script>
		<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>  
		       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed> 
		</object> 
		<!-- 打印end -->
		<!--框架必需end-->
		<script type="text/javascript">
	        $(function() {
	            $('.bluetable').colspan(3);
	            $('.bluetable').colspan(4);
	            $('.bluetable').colspan(5);
	            $('.bluetable').colspan(6);
	            $('.bluetable').colspan(9);
	            $('.bluetable').colspan(10);
	            $('.bluetable').colspan(11);
	            $('.bluetable').colspan(12);
	            $('#scoll_div_id').floatdiv("middletop");
	        }); 
	        
	        //EXCEL导出
		    function excelExport(rjhmId){
		    	$.post("query!excelExport.do",{"rjhmId":rjhmId },function(data){
		    		var datas = data.split(',');
		    		if(datas[0] == "success"){
		    			window.location.href = "<%=basePath%>"+ datas[1];
		    		}else if(datas[0] == 'nodata') {
		    			alert("没有数据！");
		    		}else {
		    			alert("导出失败！");
		    		}
		    	})
		    }

			function SaveAsFile(){ 
				var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));   
				LODOP.PRINT_INIT(""); 
				LODOP.ADD_PRINT_TABLE(100,20,500,80,document.getElementById("scrollContent").innerHTML); 
				LODOP.SET_SAVE_MODE("Orientation",1); //Excel文件的页面设置：横向打印   1-纵向,2-横向;
				LODOP.SET_SAVE_MODE("LINESTYLE",1);//导出后的Excel是否有边框
				LODOP.SAVE_TO_FILE("${datePlan.jcType }-${datePlan.jcnum }-${datePlan.fixFreque}交车检修项目记录.xls"); 
			};	

			var LODOP; //声明为全局变量 
			function preview(){
				CreatePrintPage();
				LODOP.PREVIEW();
			}

			function setup(){
				CreatePrintPage();
				//LODOP.PRINT_SETUP();
				LODOP.PRINT_DESIGN();
			}

			function print(){
				CreatePrintPage();
				LODOP.PRINT();	
			}

			function CreatePrintPage(){
				LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
				var strBodyStyle="<style>table,td{border:1 solid #000000;border-collapse:collapse}</style>";
				var strFormHtml=strBodyStyle+"<body>"+document.getElementById("scrollContent").innerHTML+"</body>";
				LODOP.PRINT_INITA(10,10,754,453,"打印控件操作");
				LODOP.SET_PRINT_PAGESIZE (2, 0, 0,"A4");
				LODOP.ADD_PRINT_TEXT(21,300,300,30,"${datePlan.jcType }-${datePlan.jcnum }交车检测项目记录\n");
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
		<style>
		    /*导航样式start*/
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
				BORDER-BOTTOM: #2b3b58 1px solid; TEXT-ALIGN: center; BORDER-LEFT: #2b3b58 1px solid; PADDING-BOTTOM: 0.5em; PADDING-LEFT: 0.5em; PADDING-RIGHT: 0.5em; BORDER-TOP: #2b3b58 1px solid; BORDER-RIGHT: #2b3b58 1px solid; PADDING-TOP: 0.5em
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
	<body>
	<!-- 浮动导航菜单start -->
<div id="scoll_div_id" style="background:#328aa4;width:864px;height:23px;">
<ul id="nav">
    <li><a href="<%=basePath%>recorderAction!getInfoByJC.action?rjhmId=${datePlan.rjhmId}&psize=100&jwdCode=${jwdCode}">◇整车记录 </a></li>
    <c:if test="${datePlan.fixFreque!='LX'&&datePlan.fixFreque!='JG'&&datePlan.fixFreque!='ZZ'}">
	    <li><a href="javascript:void(0);">◇机车部件</a>
	      <ul>
	            <c:forEach items="${units}" var="unit">
	               <li><a href="<%=basePath%>recorderAction!queryRecorder.action?rjhmId=${datePlan.rjhmId }&fristUnit=${unit.firstunitid }&jwdCode=${jwdCode}">${unit.firstunitname }</a></li>
	            </c:forEach>
	      </ul>
	    </li>
    </c:if>
    <li><a href="javascript:void(0);">◇检修班组</a>
       <ul>
            <c:forEach items="${bzs}" var="bz" varStatus="index">
	            <li><a href="<%=basePath%>recorderAction!queryInfoByBz.action?rjhmId=${datePlan.rjhmId }&teamId=${bz.proteamid }&jwdCode=${jwdCode}">${bz.proteamname }</a></li>
            </c:forEach>
      </ul>
    </li>
    <li><a href="<%=basePath%>recorderAction!searchJcRec.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}" style="background-color:#fff;color:blue;">◇交车试验</a></li>
    <c:if test="${datePlan.fixFreque!='LX'&&datePlan.fixFreque!='JG'&&datePlan.fixFreque!='ZZ'}">
	    <li><a href="<%=basePath%>recorderAction!findXXJCPJ.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇配件记录</a></li>
	    <li><a href="<%=basePath%>recorderAction!listLeftWorkRecord.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇未完成记录 </a></li>
    </c:if>
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
<br/><br/>
<!-- 浮动导航菜单end -->
		<div id="scrollContent" style="margin-top: 50px;margin-left:280px;text-align: center;font-size: 14px;">
			<div style="background-color:#328AA4;width:1000px;height:50px;font-size: 20px;font-weight: bold;">
				<div style="float:left;padding-top: 20px;padding-left: 400px; color: #fff;">交车检测项目</div>
				<%--
				<div style="float:left;padding-top: 20px;padding-left: 350px;"><button class="icon_xls" onclick="excelExport(${rjhmId});">EXCEL导出</button></div>
				 --%>
			</div>
			<c:forEach var="obj" items="${itemListMap}" varStatus="s" >
				<c:choose>
					<c:when test="${obj.key == '压缩压力'}">
						<table  id="tbspan_${s.index}" class="bluetable" style="width: 1000px;text-align: center;margin-bottom: 10px;" border="1">
							<tr>
								<th colspan="20" align="center" style="font-weight: bold;">${obj.key }</th>
							</tr>
							<tr>
								<th width="20%">项目</th>
								<c:forEach items="${obj.value}" var="item">
									<th width="5%" style="font-weight: normal;font-size: 12px;">${item.itemName}</th>
								</c:forEach>
							</tr>
							<tr>
								<th >情况/${obj.value[0].unit}</th>
								<c:forEach items="${obj.value}" var="item">
									<td >${item.fixSituation}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>检修人</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.fixemp}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>质检/技术</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.tech}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>验收</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.accept}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>交车工长</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.commitLead}</td>
								</c:forEach>
							</tr>
						</table>
					</c:when>
					<c:when test="${obj.key == '滑油压力'}">
						<table  id="tbspan_${s.index}" class="bluetable" style="width: 1000px;text-align: center;margin-bottom: 10px;" border="1">
							<tr>
								<th colspan="20" align="center" style="font-weight: bold;">${obj.key }</th>
							</tr>
							<tr>
								<th width="20%">项目</th>
								<c:forEach items="${obj.value}" var="item" >
									<th width="5%" style="font-weight: normal;font-size: 12px;">${item.itemName}</th>
								</c:forEach>
							</tr>
							<tr>
								<th >情况</th>
								<c:forEach items="${obj.value}" var="item" varStatus="idx">
									<td >${item.fixSituation}<br/>${obj.value[idx.index].unit}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>检修人</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.fixemp}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>质检/技术</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.tech}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>验收</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.accept}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>交车工长</th>
								<c:forEach items="${obj.value}" var="item">
									<td>${item.commitLead}</td>
								</c:forEach>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<table  id="tbspan_${s.index}" class="bluetable" style="width: 1000px;text-align: center;margin-bottom: 10px;" border="1">
							<tr><th colspan="7" align="center" style="font-weight: bold;">${obj.key }</th></tr>
							<tr>
								<th width="20%">项目</th>
								<c:forEach var="rec" items="${obj.value}">
									<th style="font-weight: normal;">${rec.itemName }</th>
								</c:forEach>
							</tr>
							<tr>
								<th>情况</th>
								<c:forEach var="rec" items="${obj.value}">
									<td>${rec.fixSituation }${rec.unit}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>检修人</th>
								<c:forEach var="rec" items="${obj.value}">
									<td>${rec.fixemp }</td>
								</c:forEach>
							</tr>
							<tr>
								<th>质检/技术</th>
								<c:forEach var="rec" items="${obj.value}">
									<td>${rec.tech}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>验收</th>
								<c:forEach var="rec" items="${obj.value}">
									<td>${rec.accept}</td>
								</c:forEach>
							</tr>
							<tr>
								<th>交车工长</th>
								<c:forEach var="rec" items="${obj.value}">
									<td>${rec.commitLead }</td>
								</c:forEach>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</body>
</html>
