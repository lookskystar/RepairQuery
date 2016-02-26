﻿<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
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
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/float.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/print.js"></script>
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
		cellMege('datatabel2','preType');
		$('#scoll_div_id').floatdiv("middletop");
	});
	
	function SaveAsFile(){ 
		//得到LODOP对象
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));   
		LODOP.PRINT_INIT(""); //初始化，因为不需要打印，所以不需要为其设置标题
		//增加打印表格,（距离头部距离、距离左边、宽度、高度），得到相应表格中的html元素
		LODOP.ADD_PRINT_TABLE(100,20,500,80,document.getElementById("content").innerHTML); 
		//设置相应模式
		LODOP.SET_SAVE_MODE("Orientation",1); //Excel文件的页面设置：横向打印   1-纵向,2-横向;
		LODOP.SET_SAVE_MODE("LINESTYLE",1);//导出后的Excel是否有边框
		LODOP.SET_SAVE_MODE("CAPTION","行安检修记录");//标题栏文本内容
		//LODOP.SET_SAVE_MODE("CENTERHEADER","行安检修记录");//页眉内容
		//LODOP.SET_SAVE_MODE("PaperSize",9);  //Excel文件的页面设置：纸张大小   9-对应A4
		//LODOP.SET_SAVE_MODE("Zoom",90);       //Excel文件的页面设置：缩放比例
		//LODOP.SET_SAVE_MODE("CenterHorizontally",true);//Excel文件的页面设置：页面水平居中
		//LODOP.SET_SAVE_MODE("CenterVertically",true); //Excel文件的页面设置：页面垂直居中
		//LODOP.SET_SAVE_MODE("QUICK_SAVE",true);//快速生成（无表格样式,数据量较大时或许用到） 
		//保存文件，以窗口弹出对话框的方式，让用户去选择文件保存的位置，参数为文件保存的默认名称
		LODOP.SAVE_TO_FILE("${datePlan.jcType }-${datePlan.jcnum }-${unitType}检修记录.xls"); 
	};	

	var LODOP; //声明为全局变量 
	function preview(){
		CreatePrintPage();
		//打印预览
		LODOP.PREVIEW();
	}

	function setup(){
		CreatePrintPage();
		//LODOP.PRINT_SETUP();
		LODOP.PRINT_DESIGN();//打印设置
	}

	function print(){
		CreatePrintPage();
		LODOP.PRINT();	//打印
	}

	//初始化页面
	function CreatePrintPage(){
		//得到LODOP对象，注意head标签里面需引入object和embed标签
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		//封装我们的html元素  
		var strBodyStyle="<style>table,td{border:1 solid #000000;border-collapse:collapse}</style>";
		var strFormHtml=strBodyStyle+"<body>"+document.getElementById("content").innerHTML+"</body>";
		//打印初始化，页面距离顶部10px，距离左边10px，宽754px，高453px，给打印设置个标题
		LODOP.PRINT_INITA(10,10,754,453,"打印控件操作");
		//设置打印页面属性：2：表示横向打印，0：定义纸张宽度，为0表示无效设置，A4：设置纸张为A4
		LODOP.SET_PRINT_PAGESIZE (2, 0, 0,"A4");
		//设置文本，参数(距离页面头部，距离页面左边距离，文本宽度，文本高度，文本内容)
		LODOP.ADD_PRINT_TEXT(21,300,300,30,"${unitType}${flowval }检修记录\n");
		//给所添加的文本定义样式,0：表示新添加的元素，相应的属性，相应的值
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);//固定标题,设置卫页眉页脚
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		//同上
		LODOP.ADD_PRINT_TEXT(40,60,350,30,"机车号:${datePlan.jcType } ${datePlan.jcnum }  修程:${datePlan.fixFreque}  检修日期:${datePlan.kcsj }");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		//添加html元素
		LODOP.ADD_PRINT_HTM(63,38,684,330,strFormHtml);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		//添加一条线，参数(开始短点距离头部距离，开始端点距左边距离，结束端点距头部距离，结束端点距左边距离)
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
</head>

<body bgcolor="#f8f7f7">
<!-- 浮动导航菜单start -->
<div id="scoll_div_id" style="background:#328aa4;width:864px;height:23px;">
<ul id="nav">
    <li><a href="javascript:void(0);" style="background-color:#fff;color:blue;">◇整车记录 </a>
       <ul>
         <li><a href="<%=basePath%>recorderAction!getZxInfoByJC.action?rjhmId=${datePlan.rjhmId}&psize=100&jwdCode=${jwdCode}" style="background-color:#fff;color:blue;">整车检修记录</a></li>
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
    <li><a href="javascript:void(0);">◇试验记录</a>
       <ul>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=2&jwdCode=${jwdCode}">水阻试验</a></li>
            <li><a href="<%=basePath%>recorderAction!viewExperiment.action?id=${datePlan.rjhmId}&jceiId=5&jwdCode=${jwdCode}">顶轮试验</a></li>
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
<form id="form1" name="form1" method="post" action="">
<table width="864" border="0" align="center" cellspacing="0" vspace="0" style="padding-top:10px;">
<tr>
<td colspan="6" align="center" height="40"><h2 align="center"><font style="font-family:'隶书'">
<b>${flowval }整车检修记录</b></font></h2></td>
<td  align="right"></td>
</tr>
<tr>
<td width="163" align="right">机车号：</td>
<td width="240">${datePlan.jcType }  ${datePlan.jcnum }</td>
<td width="55" align="left">修程：</td><td width="251">${datePlan.fixFreque}</td>
<td width="140" align="left">检修日期：</td><td width="195" algin="left">${datePlan.kcsj }</td>
</tr>
<tr><td colspan="6">
<table width="864" border="0" align="center" cellspacing="0" vspace="0">
 	<tr>
		<td align="center" colspan="3" class="tbCELL3">
			<table width="864" border="0" align="center" cellspacing="0" vspace="0" id="datatabel">
				<tr style="line-height:40px;height: 40px;background-color: #328aa4;font-weight: bolder;">
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">部位</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="25%">检修项目</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">所处节点</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">检修情况</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">配件编号</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">检修人</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">工长</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">质检员</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">技术员</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">交车工长</td>
					<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">验收员</td>
				</tr>
				<c:forEach items="${zxFixRecs.datas}" var="item">
					<tr>
						<td class="tbCELL1" align="center" name="firstname">${item.unitName }</td>
						<td class="tbCELL1" align="center" name="fixitemname">${item.itemName }</td>
						<td class="tbCELL1" align="center">
						  <c:if test="${item.nodeId==100}">机车分解</c:if>
						  <c:if test="${item.nodeId==101}">车上组装</c:if>
						</td>
						<td class="tbCELL1" align="center">${item.fixSituation }${item.unit }</td>
						<td class="tbCELL1" align="center">
						<c:if test="${item.nodeId==100}">--</c:if>
						<c:if test="${item.nodeId==101 && !empty item.upPjNum}">
						      <c:forTokens items="${item.upPjNum }" delims="," var="num">
								<a href="<%=basePath%>recorderAction!zxFittingView.action?id=${datePlan.rjhmId}&pjNum=${num}&jwdCode=${jwdCode}" target="_blank">${num }</a>&nbsp;
							</c:forTokens>
						</c:if>
						</td>
						<td class="tbCELL1" align="center">
							<c:choose>
								<c:when test="${empty item.fixEmp}"><font color="#FF0000">待签</font></c:when>
								<c:otherwise>
									${fn:substring(item.fixEmp,1,fn:length(item.fixEmp)-1)}<br>${fn:substring(item.fixEmpTime, 5, 16) }
								</c:otherwise>
							</c:choose>
						</td>
						<td class="tbCELL1" align="center">
							<c:choose>
								<c:when test="${empty item.lead}"><font color="#FF0000">待签</font></c:when>
								<c:otherwise>
									${item.lead }<br>${fn:substring(item.ldAffirmTime, 5, 16) }
								</c:otherwise>
							</c:choose>
						</td>
						<td class="tbCELL1" align="center">
							<c:choose>
								<c:when test="${empty item.qi && item.itemCtrlQi==1}"><font color="#FF0000">待签</font></c:when>
								<c:when test="${empty item.qi && item.itemCtrlQi==0}">/</c:when>
								<c:otherwise>
									${item.qi }<br>${fn:substring(item.qiAffiTime, 5, 16) }
								</c:otherwise>	
							</c:choose>
						</td>
						<td class="tbCELL1" align="center">
							<c:choose>
								<c:when test="${empty item.teachName && item.itemCtrlTech==1}"><font color="#FF0000">待签</font></c:when>
								<c:when test="${empty item.teachName && item.itemCtrlTech==0}">/</c:when>
								<c:otherwise>
									${item.teachName }<br>${fn:substring(item.teachAffiTime, 5, 16) }
								</c:otherwise>	
							</c:choose>
						</td>
						<td class="tbCELL1" align="center">
							<c:choose>
								<c:when test="${empty item.commitLead && item.itemCtrlComld==1}"><font color="#FF0000">待签</font></c:when>
								<c:when test="${empty item.commitLead && item.itemCtrlComld==0}">/</c:when>
								<c:otherwise>
									${item.commitLead }<br>${fn:substring(item.comLdAffiTime, 5, 16) }
								</c:otherwise>	
							</c:choose>
						</td>
						<td class="tbCELL1" align="center">
							<c:choose>
								<c:when test="${empty item.acceptEr && item.itemCtrlAcce==1}"><font color="#FF0000">待签</font></c:when>
								<c:when test="${empty item.acceptEr && item.itemCtrlAcce==0}">/</c:when>
								<c:otherwise>
									${item.acceptEr }<br>${fn:substring(item.acceAffiTime, 5, 16) }
								</c:otherwise>	
							</c:choose>
						</td>
				</tr>
				</c:forEach>
				<c:if test="${!empty zxItemList}">
			  		<c:forEach items="${zxItemList}" var="item">
						<tr>
							<td class="tbCELL1" align="center" name="firstname">${item.unitName }</td>
							<td class="tbCELL1" align="center" name="fixitemname">${item.itemName }</td>
							<td class="tbCELL1" align="center">
							  <c:if test="${item.nodeId==100}">机车分解</c:if>
							  <c:if test="${item.nodeId==101}">车上组装</c:if>
							</td>
							<td class="tbCELL1" align="center"></td>
							<td class="tbCELL1" align="center">--</td>
							<td class="tbCELL1" align="center"><font color="#FF0000">待签</font></td>
							<td class="tbCELL1" align="center"><font color="#FF0000">待签</font></td>
							<td class="tbCELL1" align="center">
								<c:choose>
									<c:when test="${item.itemCtrlComld==0}">/</c:when>
									<c:otherwise><font color="#FF0000">待签</font></c:otherwise>
								</c:choose>
							</td>
							<td class="tbCELL1" align="center">
								<c:choose>
									<c:when test="${item.itemCtrlQi==0}">/</c:when>
									<c:otherwise><font color="#FF0000">待签</font></c:otherwise>
								</c:choose>
							</td>
							<td class="tbCELL1" align="center">
								<c:choose>
									<c:when test="${item.itemCtrlLead==0}">/</c:when>
									<c:otherwise><font color="#FF0000">待签</font></c:otherwise>
								</c:choose>
							</td>
							<td class="tbCELL1" align="center">
								<c:choose>
									<c:when test="${item.itemCtrlTech==0}">/</c:when>
									<c:otherwise><font color="#FF0000">待签</font></c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
		  		</c:if>
				<tr></tr>
			</table>
	  	</td>
	</tr>
  <tr>
  <td>
  <!-- 处理分页 -->
<div style="height:35px;" align="right">
	<div class="float_right padding5 paging">
		<div class="float_left padding_top4">	
		<span >共${zxFixRecs.count}条数据&nbsp;&nbsp;&nbsp;&nbsp;</span>	
		<pg:pager maxPageItems="100" url="recorderAction!getZxInfoByJC.action" items="${zxFixRecs.count}" export="page1=pageNumber">
			<pg:param name="rjhmId" value="${datePlan.rjhmId}"/>
		   	<pg:param name="jwdCode" value="${jwdCode }"/>
		    <pg:param name="pageSize" value="100"/>
		  	<pg:first>
			 	<span><a href="${pageUrl  }" id="first">首页</a></span>
		  	</pg:first>
		  	<pg:prev>
		  		
			  	<span><a href="${pageUrl  }">上一页</a></span>
		  	</pg:prev>
	  	  	<pg:pages>
				<c:choose>
					<c:when test="${page1==pageNumber}">
						<span class="paging_current"><a href="javascript:;" style="color:red;">${pageNumber }</a></span>
					</c:when>
					<c:otherwise>
						<span><a href="${pageUrl  }">${pageNumber }</a></span>
					</c:otherwise>
				</c:choose>
			</pg:pages>
			<pg:next>
				<span><a href="${pageUrl }">下一页</a></span>
			</pg:next>
			<pg:last>
				<span><a href="${pageUrl }">末页</a></span>
			</pg:last>
			</pg:pager>
			<div class="clear"></div>
		</div>
	</div>
</div>
  <!-- 报活项目 begin-->
	<table width="864" border="0" align="center" cellspacing="0" id="datatabel2">
		<tr style="line-height:40px;height: 40px;background-color: #328aa4;font-weight: bolder;">
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">报活类别</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="20%">报活内容</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">报活人</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">报活时间</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="15%">检修情况</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">检修人</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">工长</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">质检员</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">技术员</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">交车工长</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">验收员</td>
		</tr>
		<c:forEach items="${preDictRecs}" var="preItem">
			<tr>
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
				<td class="tbCELL1" align="center">${preItem.commitLd }<br>${fn:substring(preItem.comLdAffiTime, 5, 16) }&nbsp;</td>
				<td class="tbCELL1" align="center">${preItem.accepter }<br>${fn:substring(preItem.acceTime, 5, 16) }&nbsp;</td>
			</tr>
			<tr></tr>
		</c:forEach>
	</table>
	<!-- 报活项目 end-->
	</td>
	</tr>
  </table>
  </td>
  </tr>
</table>
</form>
</body>
</html>