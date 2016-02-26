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
中修各班组未完成作业记录单
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
		cellMege('datatabel','proteamname');
		cellMege('datatabel','fixitemname');
		$('#scoll_div_id').floatdiv("middletop");
	});
</script>
</head>

<body bgcolor="#f8f7f7">
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
    <li><a href="<%=basePath%>recorderAction!listZXLeftWorkRecord.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}" style="background-color:#fff;color:blue;">◇未完成记录 </a></li>
    <li><a href="<%=basePath %>recorderAction!getAllInfoPre.action?rjhmId=${datePlan.rjhmId}&jwdCode=${jwdCode}">◇报活记录</a></li>
    <li><a href="<%=basePath%>recorderAction!searchJCjungong.action?rjhmId=${datePlan.rjhmId }&jwdCode=${jwdCode}">◇交车竣工 </a></li>
    <li><a href="javascript:void(0);">◇记录导出 </a></li>
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

<table width="864" border="0" align="center" cellspacing="0" vspace="0" style="padding-top:10px;">
	<tr>
		<td colspan="6" align="center" height="40"><h2 align="center"><font style="font-family:'隶书'">
			<b>整车未完成检修记录</b></font></h2>
		</td>
		<td  align="right"></td>
	</tr>
	<tr>
		<td width="163" align="right">机车号：</td>
		<td width="240">${datePlan.jcType }  ${datePlan.jcnum }</td>
		<td width="55" align="left">修程：</td><td width="251">${datePlan.fixFreque}</td>
		<td width="140" align="left">检修日期：</td><td width="195" algin="left">${datePlan.kcsj }</td>
	</tr>
	<tr>
		<td colspan="6">
			<table width="864" border="0" align="center" cellspacing="0" vspace="0">
				<tr>
					<td align="center" colspan="3" class="tbCELL3">
						<table width="864" border="0" align="center" cellspacing="0" vspace="0" id="datatabel">
							<tr style="line-height:40px;height: 40px;background-color: #328aa4;font-weight: bolder;">
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">班组</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="20%">检修项目</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">所处节点</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">检修/探伤情况</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">配件编号</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">检修/探伤人</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">工长</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">质检员</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">技术员</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">交车工长</td>
								<td class="tbCELL1" align="center" style="white-space:nowrap;color:#ffffff;" width="8%">验收员</td>
							</tr>
							<c:if test="${empty leftWorkRecordMap}">
								<tr>
									<td class="tbCELL1" align="center" colspan="11">各班组均已完成作业!</td>
								</tr>
							</c:if>
					 		<c:forEach items="${leftWorkRecordMap}" var="map">
					 			<c:forEach items="${map.value}" var="item">
									<tr>
										<td class="tbCELL1" align="center" name="proteamname">${map.key }</td>
										<td class="tbCELL1" align="center" name="fixitemname">${item.itemName }</td>
										<td class="tbCELL1" align="center">
										  <c:if test="${item.nodeId==100}">机车分解</c:if>
										  <c:if test="${item.nodeId==101}">车上组装</c:if>
										</td>
										<td class="tbCELL1" align="center">
											<c:choose>
												<c:when test="${item.bzId==tsbzid}">
														探伤结果：${item.fixSituation}<br/>
														<c:if test="${!empty item.dealSituation}">裂损情况：${item.dealSituation}</c:if>
												</c:when>
												<c:otherwise>${item.fixSituation}${item.unit}</c:otherwise>
											</c:choose>
										</td>
										<td class="tbCELL1" align="center">
										<c:if test="${item.nodeId==100}">--</c:if>
										<c:if test="${item.nodeId==101 && !empty item.upPjNum}">
										      <c:forTokens items="${item.upPjNum }" delims="," var="num">
												<%-- <a href="<%=basePath%>query!zxFittingView.action?id=${datePlan.rjhmId}&pjNum=${num}" target="_blank">${num }</a>&nbsp;--%>
												<a href="recorderAction!findPjRecordByPjNum.action?rjhmId=${datePlan.rjhmId}&pjNum=${num}&jwdCode=${jwdCode}" target="_blank">${num }</a>&nbsp;
											</c:forTokens>
										</c:if>
										</td>
										<td class="tbCELL1" align="center">
											<c:choose>
												<c:when test="${item.bzId==tsbzid}">
														主探:
														<c:choose>
															<c:when test="${empty item.fixEmp}"><font color="#FF0000">待签</font></c:when>
															<c:otherwise>${fn:substring(item.fixEmp,1,fn:length(item.fixEmp)-1)}<br>${fn:substring(item.fixEmpTime, 5, 16) }</c:otherwise>
														</c:choose><br/>
														<c:if test="${empty item.rept && item.itemCtrlRept==1}">复探:<font color="#FF0000">待签</font></c:if>
														<c:if test="${!empty item.reptAffirmTime}">复探:${item.rept}<br/>${fn:substring(item.reptAffirmTime,5,fn:length(item.reptAffirmTime)-3)}</c:if>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${empty item.fixEmp}"><font color="#FF0000">待签</font></c:when>
														<c:otherwise>
															${fn:substring(item.fixEmp,1,fn:length(item.fixEmp)-1)}<br>${fn:substring(item.fixEmpTime, 5, 16) }
														</c:otherwise>
													</c:choose>
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
							</c:forEach>
							<tr></tr>
						</table>
				  	</td>
			  	</tr>
			</table>
	 	</td>
	</tr>
</table>
<div class="add-collect-to" >
  	<a href="javascript:screenPrint();" style="font-size:14px;font-weight: bold;">打印</a>
</div>
</body>
</html>