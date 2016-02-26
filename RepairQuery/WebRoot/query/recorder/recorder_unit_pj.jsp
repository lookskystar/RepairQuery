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
检修记录
</title>
<link href="<%=basePath %>css/test.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>css/linkcss.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>js/tree/dtree/dtree.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>js/test.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.js"></script>
<script type="text/javascript" src="<%=basePath %>js/tree/dtree/dtree.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/float.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath %>js/print.js"></script>
<script language="JavaScript" type="text/javascript">
			
function renovate(url, name){
	var substance = document.getElementById('substance');
	if(substance != undefined && url != null && url.length > 0) {
		if(url.indexOf("?")>=0)
			url+="&"+Math.random();
		else
			url+="?"+Math.random();
		substance.src = url;
	}
	
	if ('2' == '2' && (url.indexOf('enterApp=FI') > -1 || url.indexOf('fi.alisoft.com') > -1)) {
		mainbody.css('border-bottom=1px solid #4677b5,border-top=1px solid #4677b5,width=1030px,float=left,margin-left=-35px,_margin-left=0');
		mainbottom.hidden();
		iframe1.css('width=1030px,height=480px,float=left');
	} else {
		mainbody.css('border-bottom=0px,border-top=0px,width=958px,float=none,margin-left=0px');
		mainbottom.show();
		iframe1.css('width=938px,height=480px,float=none,margin-left=10px');
	}
	if(KB.bom.ie=='6.0'){
		mainbody.css('margin-left=0');
	}
	return false;
}


function setWinH(){
	SetWinHeight(document.getElementById('substance'));
}

 function SetWinHeight(obj){
      try {
      	var win=obj;
		var h = 0;
       if (document.getElementById) {
			if (win && !window.opera) {
				if (win.contentDocument && win.contentDocument.body.offsetHeight) {
					win.style.height = win.contentDocument.body.offsetHeight;
					h=win.contentDocument.body.offsetHeight;
				} else if(win.Document && win.Document.body.scrollHeight) {
					win.style.height = win.Document.body.scrollHeight;
					h=win.Document.body.scrollHeight;
				} else {
					h=win.contentDocument.body.offsetHeight;
				}
			} else {
				//win.height = 600;
			}
       } else {
		//win.height = 600;
	   }

		h = parseInt(h);
		iframe1.css('height='+h+'px');
      } catch (e) {
	     //win.height = 600;
      }
 }
function init(){
	var navs ;
	/*--初始导航--*/
	var navs = new KB('ul@nav:li');
	/*--初始导航--*/
	var debugx = 0;
	var debugInt = true;
	/*--解决IE6下背景图片加载缓慢问题--*/
	if(KB.bom.ie=='6.0'){
       try{
         document.execCommand("BackgroundImageCache", false, true);
        }catch(e){}
    }
	
	for(var i=0; i<navs.nod.length; i++){
		if(KB.isNode('subnav_'+navs.nod[i].id)){
			var pos = KB.getPos(navs.nod[i]);
			var x = parseInt(pos.x)-160;
			if(debugInt){
				if(x<110){
					debugx = 110-x;	
				}
				if(x>120){
					debugx = 120-x;
				}
				debugInt = false;
			}
			x = x+debugx+'px';
			var y = 45+'px';
			var s = new KB('dl=subnav_'+navs.nod[i].id);
			s.css('position=absolute,left='+x+',top='+y);
		}
		(function(i,k){
				 	KB.reg(k.nod[i],'mouseover',function(){
														 		if(k.nod[i].className=="nav_active")return;
																new KB('div@header').css('position=relative');
																if(KB.isNode('subnav_'+k.nod[i].id)){
																	var s = new KB('dl=subnav_'+k.nod[i].id);
																	s.show();
																	k.nod[i].className='vor';
																}
															}); 
					KB.reg(k.nod[i],'mouseout',function(){
														 		new KB('div@header').css('position=static');
																if(k.nod[i].className=="nav_active")return;
																if(KB.isNode('subnav_'+k.nod[i].id)){
																	var s = new KB('dl=subnav_'+k.nod[i].id);
																	s.hidden();
																	k.nod[i].className='';
																}
															});
				 })(i,navs);	
	}
	//KB.regHover(navs,'','','mainmenu_1_on',changeMenu);
	/*--初始top链接切换--*/
	navs = new KB('dl@top_right:li');
	KB.regHover(navs,'','','margin10',DHchange);
	navs = new KB('div=topnav1:div');
	KB.regHover(navs,'top_nav_hover','','');
	navs = new KB('div=topnav2:div');
	KB.regHover(navs,'top_nav_hover','','');
	 /*--初始ShortCut快捷方式--*/
	ShortCut.cut = new KB('div@shortcut');
	var f = ShortCut.cut.getChild('*');
	for(var i=0; i<f.length; i++){
		(function(i){
				  KB.reg(f[i],'mouseover',ShortCut.show);
				  })(i);	
	}
	var scubtn = new KB('b=short_cu');
	ShortCut.cuBtn = scubtn.nod[0];
	ShortCut.title1 = '开启前端显示';
	ShortCut.title2 = '关闭前端显示';
	ShortCut.cutBtn = new KB('div=shortcut_btn');
	scubtn.show();
	ShortCut.init();
	tips.width=80;
	tips.xdebug = 140;
}

function openTopnav(num,t){
	if(t){
		new KB('div=topnav'+num).show();
	}else{
		new KB('div=topnav'+num).hidden();
	}
}
</script>
<script type="text/javascript">
	try{
		if(top.location != self.location) {
		   //top.location.href = 'home.jspa';
		}
	}catch(e){}
	
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
	KB.ready(init);
	$(function(){
		cellMege('datatabel','firstname');
		cellMege('datatabel','secondname');
		cellMege('datatabel','fixitemname');
		//cellMege(0);
		//cellMege('secondname');
		$('.add-collect-to').float({
			position:'rm'
		});
	});
	$(function(){
		  d = new dTree('d');//创建一个树对象   

         d.add(0,-1,'检修记录','<%=basePath%>query!findJCAll.do?rjhmId=${datePlan.rjhmId}&jcStype=${datePlan.jcType }'); //创建一个树对象 
         d.add(1,0,'机车部件'); //创建一个树对象 
         var count = 0;
         <c:forEach items="${units}" var="unit" varStatus="index">
         	count = ${index.count}+1;
         	d.add(count,1,'${unit.firstunitname }','<%=basePath%>query!zxView.do?id=${datePlan.rjhmId}&fristUnit=${unit.firstunitid }');
 		 </c:forEach>
 		 if (count == 0) {
 			count = 2;
 		 }
 		 d.add(count+1, 0, "试验");
 		 d.add(count+2, count+1, "水阻试验", "<%=basePath%>query!viewExperiment.do?id=${datePlan.rjhmId}&jceiId=2");
 		 d.add(count+3, count+1, "顶轮试验", "<%=basePath%>query!viewExperiment.do?id=${datePlan.rjhmId}&jceiId=5");
 		 d.add(count+4, count+1, "高低压试验", "<%=basePath%>query!viewExperiment.do?id=${datePlan.rjhmId}&jceiId=4");
 		 d.add(count+5, count+1, "试运行试验", "<%=basePath%>query!viewExperiment.do?id=${datePlan.rjhmId}&jceiId=3");

 		 d.add(count+6, 0, "配件","<%=basePath%>query!findJCPJ.do?rjhmId=${datePlan.rjhmId}");
		 d.add(count+7, 0, "探伤","<%=basePath%>query!findJCTS.do?rjhmId=${datePlan.rjhmId}");
 		 //var pj = count+6;
 		 //<c:forEach items="${pjzy}" var="entry" varStatus="index">
	     	//count = count+6+${index.count};
	     	//d.add(count,pj,'${entry.firstunitname }','<%=basePath%>query!zxFittingView.do?id=${datePlan.rjhmId}&fristUnit=${entry.firstunitid }');
		// </c:forEach>	
         d.openAll();
         $('#tt').append(d.toString());
	});
</script>
</head>

<body bgcolor="#f8f7f7">
<form id="form1" name="form1" method="post" action="">
<!--top end-->
<div class="top" id="top" style="display:none">
  <!--top_right begin-->
  <dl class="top_right">
    <dd>
      <ul>
      
        <li style="position: static;" class=""><a onclick="openTopnav(1,true)" href="javascript:void(0)">系统设置</a><b class="icon9"></b>
          <div id="topnav1" class="top_nav" style="display: none;">
            <h2><a onclick="openTopnav(1,true)" href="javascript:void(0)">系统设置</a><b class="icon9"></b></h2>
            <div><a onclick="openUrlToNewPage('#','初始设置');'return false';" href="javascript:void(0)">初始设置</a></div>
            <iframe frameborder="0" style="z-index:-1; position:absolute; top:0; left:0; width:100px; height:140px; opacity:0; filter:alpha(opacity=0)"></iframe>
          </div>
        </li>
        <li class="margin10">|</li>
        <li style="position: static;" class=""><a onclick="openTopnav(2,true)" href="javascript:void(0)">在线帮助</a><b class="icon9"></b>
          <div id="topnav2" class="top_nav" style="display: none;">
            <h2><a onclick="openTopnav(2,false)" href="javascript:void(0)">在线帮助</a><b class="icon9"></b></h2>
            <div><a href="" target="_blank">在线帮助</a></div>
            <div><a href="javascript:changeFaceAdpter('STATIC_SERVER/c2c/face0/images','0');">新手上路</a></div>
            <iframe frameborder="0" style="z-index:-1; position:absolute; top:0; left:0; width:100px; height:140px; opacity:0; filter:alpha(opacity=0)"></iframe>
          </div>
        </li>
        <li class="margin10">|</li>
      </ul>
    </dd>
  </dl>
</div>
<!--left box-->
<div class="shortcut" style="z-index:1000">
  <div class="shortcut_top"><span>机车检修记录</span><b class="icon57" title="" id="short_cu" onclick="ShortCut.cu()" style="display:none;"></b></div>
  <div class="shortcut_wrap"></div>
  <div class="shortcut_body" id="left_menu">
  <ul id="tt" class="easyui-tree">  
</ul>  
  </div>
  <div class="shortcut_bottom"></div>
  <iframe style="position:absolute; left:0px; top:0px; width:142px; height:300px; border:1px solid #229900; z-index:-1; opacity:0; filter:alpha(opacity=0);"></iframe>
</div>
<div class="shortcut_btn" id="shortcut_btn"></div>
<!–startprint–>
<div style="width:870px;margin-left:-435px;left:50%;position:absolute;" id="content2">
<table width="864" border="0" align="center" cellspacing="0" vspace="0" style="padding-top:10px;">
<tr>
<td colspan="6" align="center" height="40"><h2 align="center"><font style="font-family:'隶书'">
<b>${unitType}&nbsp;&nbsp;${flowval }&nbsp;&nbsp;
检修记录
</b></font></h2></td>
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
		<tr style="line-height:40px;height: 40px;background-color: lightblue;font-weight: bolder;">
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="10%">名称</td>
			<!-- 
			<td class="tbCELL1" align="center" style="white-space:nowrap; min-width" width="15%">检修部件</td>
			 -->
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="25%">检修项目</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="12%">检修情况</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="12%">组装配件编号</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="6%">检修人</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="6%">工长</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="6%">质检员</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="6%">技术员</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="6%">交车工长</td>
			<td class="tbCELL1" align="center" style="white-space:nowrap" width="6%">验收员</td>
		</tr>
 		<c:forEach items="${pjFixRecs}" var="item">
		<tr>
			<td class="tbCELL1" align="center" name="firstname">${item.pjname }</td>
			<td class="tbCELL1" align="center" name="fixitemname"><c:if test="${!empty item.pjFixItem}">${item.pjFixItem.fixItem}</c:if></td>
			<td class="tbCELL1" align="center" name="fixsituation">${item.fixsituation}${item.pjFixItem.unit}</td>
			<td class="tbCELL1" align="center" name="pj">
			   <c:if test="${!empty item.pjNum}">
			    <c:forTokens items="${item.pjNum}" delims="," var="num">
			      <a href="pjManageAction!findPJRecorder.do?pjnum=${num}" target="_blank">${num}</a>&nbsp;&nbsp;
			    </c:forTokens>
			  </c:if>
			</td>
			<td class="tbCELL1" align="center">
				<c:choose>
					<c:when test="${empty item.fixemp}"><font color="#FF0000">待签</font></c:when>
					<c:otherwise>
						${item.fixemp}<br>${fn:substring(item.empaffirmtime, 5, 16) }
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tbCELL1" align="center">
				<c:choose>
					<c:when test="${empty item.lead}"><font color="#FF0000">待签</font></c:when>
					<c:otherwise>
						${item.lead }<br>${fn:substring(item.ldaffirmtime, 5, 16) }
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tbCELL1" align="center">
				<c:choose>
					<c:when test="${empty item.qi && item.pjFixItem.itemctrlqi==1}"><font color="#FF0000">待签</font></c:when>
					<c:when test="${empty item.qi && item.pjFixItem.itemctrlqi==0}">/</c:when>
					<c:otherwise>
						${item.qi }<br>${fn:substring(item.qiaffitime, 5, 16) }
					</c:otherwise>	
				</c:choose>
			</td>
			<td class="tbCELL1" align="center">
				<c:choose>
					<c:when test="${empty item.techName && item.pjFixItem.itemctrltech==1}"><font color="#FF0000">待签</font></c:when>
					<c:when test="${empty item.techName && item.pjFixItem.itemctrltech==0}">/</c:when>
					<c:otherwise>
						${item.techName }<br>${fn:substring(item.techTime, 5, 16) }
					</c:otherwise>	
				</c:choose>
			</td>
			<td class="tbCELL1" align="center">
				<c:choose>
					<c:when test="${empty item.commitlead && item.pjFixItem.itemctrlcomld==1}"><font color="#FF0000">待签</font></c:when>
					<c:when test="${empty item.commitlead && item.pjFixItem.itemctrlcomld==0}">/</c:when>
					<c:otherwise>
						${item.commitlead }<br>${fn:substring(item.comldaffitime, 5, 16) }
					</c:otherwise>	
				</c:choose>
			</td>
			<td class="tbCELL1" align="center">
				<c:choose>
					<c:when test="${empty item.accepter && item.pjFixItem.itemctrlacce==1}"><font color="#FF0000">待签</font></c:when>
					<c:when test="${empty item.accepter && item.pjFixItem.itemctrlacce==0}">/</c:when>
					<c:otherwise>
						${item.accepter }<br>${fn:substring(item.acceaffitime, 5, 16) }
					</c:otherwise>	
				</c:choose>
			</td>
		</tr>
		</c:forEach>
		<tr></tr>
	</table>
  </td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  </div>
  <!-- endprint -->
  <div class="add-collect-to" >
  <a href="javascript:screenPrint();" style="font-size:14px;font-weight: bold;">打印</a>
  </div>
</form>
</body>
</html>