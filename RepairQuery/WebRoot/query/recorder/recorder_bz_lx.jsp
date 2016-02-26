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
</script>
</head>

<body bgcolor="#f8f7f7">
<form id="form1" name="form1" method="post" action="">
<div id="scoll_div_id" style="background:#328aa4;width:864px;height:23px;">
<ul id="nav">
    <li><a href="<%=basePath%>recorderAction!getInfoByJC.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇整车记录 </a></li>
    <li><a href="javascript:void(0);" style="background-color:#fff;color:blue;">◇检修班组</a>
       <ul>
           <c:forEach items="${bzs}" var="bz" varStatus="index">
            <li><a href="<%=basePath%>recorderAction!queryInfoByBz.action?rjhmId=${datePlan.rjhmId }&teamId=${bz.proteamid }&jwdCode=${jwdCode}">${bz.proteamname }</a></li>
           </c:forEach>
      </ul>
    </li>
	<li><a href="<%=basePath%>recorderAction!searchJcRec.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇交车试验</a></li>
    <li><a href="<%=basePath %>recorderAction!getAllInfoPre.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇报活记录</a></li>
    <li><a href="<%=basePath%>recorderAction!searchJCjungong.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇交车竣工 </a></li>
    <li><a href="javascript:void(0);">◇记录导出</a></li>
    <li><a href="javascript:void(0);">◇打印 </a>
        <ul>
            <li><a href="javascript:void(0);">直接打印</a></li>
            <li><a href="javascript:void(0);">打印设置</a></li>
            <li><a href="javascript:void(0);">打印预览</a></li>
      </ul>
    </li>
</ul>
</div>
<br><br>
<!-- 浮动导航菜单end -->
<div style="width:870px;margin-left:-435px;left:50%;position:absolute;" id="content2">
<table width="864" border="0" align="center" cellspacing="0" vspace="0" style="padding-top:10px;">
<tr>
<td colspan="8" align="center" height="40"><h2 align="center"><font style="font-family:'隶书'">
<b>
${currentTeam.proteamname }&nbsp;&nbsp;${flowval }&nbsp;&nbsp;
检修记录</b></font></h2></td>
<td  align="right"></td>
</tr>
<tr>
<td width="163" align="right">上车号：</td>
<td width="240">${datePlan.jcType }  ${datePlan.jcnum }</td>
<td width="55" align="left">修程：</td><td width="251">${datePlan.fixFreque}</td>
<td width="140" align="left">检修日期：</td><td width="195" algin="left">${datePlan.kcsj }</td>
<td width="100px" align="right">班组记录编号：</td><td width="100px" algin="left">${fn:substring(datePlan.jcType,0,1)}${datePlan.fixFreque}${currentTeam.py}${currentTeam.proteamid}</td>
</tr>
<tr><td colspan="8">
<table width="864" border="0" align="center" cellspacing="0" vspace="0">
 <tr>
	<td align="center" colspan="3" class="tbCELL3">
	<table width="864" border="0" align="center" cellspacing="0" vspace="0" id="datatabel">
		<tr style="line-height:40px;height: 40px;background-color: #328aa4;font-weight: bolder;">
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="25%">检修项目</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="10%">检修情况</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">检修人</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">工长</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">质检员</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">技术员</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">交车工长</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="6%">验收员</td>
		</tr>
		<c:forEach items="${preDictRecs}" var="item">
		<tr>
			<%--
			<td class="tbCELL1" align="center" name="firstname">${item.fixitem.unitName }</td>
			<td class="tbCELL1" align="center" name="secondname">${item.fixitem.secUnitName}</td>
			<td class="tbCELL1" align="center" name="fixitemname">${item.fixitem.itemName }</td>
			<td class="tbCELL1" align="center">${item.fixitem.posiName }</td>
			 --%>
			<td class="tbCELL1" align="center">${item.repsituation}</td>
			<td class="tbCELL1" align="center">${item.dealSituation }
			   	  <c:if test="${!empty item.upPjNum}">
				      <br/>
		              <c:forTokens items="${item.upPjNum }" delims="," var="num">
							<a href="recorderAction!findPjRecordByPjNum.action?rjhmId=${datePlan.rjhmId}&pjNum=${num}&jwdCode=${jwdCode}" target="_blank" style="color:blue;">${num }</a>&nbsp;
					  </c:forTokens>
				  </c:if>
			</td>
			<td class="tbCELL1" align="center">${fn:substring(item.dealFixEmp,1,fn:length(item.dealFixEmp)-1)}<br>${fn:substring(item.fixEndTime, 5, 16) }</td>
			<td class="tbCELL1" align="center">${item.lead}<br>${fn:substring(item.ldAffirmTime, 5, 16) }</td>
			<td class="tbCELL1" align="center">${item.qi }<br>${fn:substring(item.qiAffiTime, 5, 16) }&nbsp;</td>
			<td class="tbCELL1" align="center">${item.technician }<br>${fn:substring(item.techAffiTime, 5, 16) }&nbsp;</td>
			<td class="tbCELL1" align="center">${item.commitLd }<br>${fn:substring(item.comLdAffiTime, 5, 16) }&nbsp;</td>
			<td class="tbCELL1" align="center">${item.accepter }<br>${fn:substring(item.acceTime, 5, 16) }&nbsp;</td>
		</tr>
		</c:forEach>
	</table>
  </td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  </div>
</form>
</body>
</html>