<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>机务检修信息管理系统</title>
	<!--框架必需start-->
	<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link href="skins/sky/import_skin.css" rel="stylesheet" type="text/css" id="skin" themeColor="blue"/>
	<script type="text/javascript" src="js/jquery-1.4.js"></script>
	<script type="text/javascript" src="js/bsFormat.js"></script>
	<!--框架必需end-->
	
	<!--引入组件start-->
	<script type="text/javascript" src="js/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="js/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	
	<!--修正IE6支持透明png图片start-->
	<script type="text/javascript" src="js/method/pngFix/supersleight.js"></script>
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
	<!--修正IE6支持透明png图片end-->
	<!-- 打印插件 -->
	<script type="text/javascript" src="js/LodopFuncs.js"></script>
	<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>  
	       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed> 
	</object> 
	<!-- 打印end -->
	<script>
		$(function(){
			var htabIdx=0;
			var htabLiIdx=0;
			var htabIndex=jQuery.jCookie('htabIndex');
			var htabLiIndex=jQuery.jCookie('htabLiIndex');
			if(htabIndex!=false){
				htabIdx=parseInt(htabIndex);
			}
			if(htabLiIndex!=false){
				htabLiIdx=parseInt(htabLiIndex);
			}
			$(".top >ul").filter(':eq('+htabIdx+')').show();
			$(".tab_bar li a").filter(':eq('+htabIdx+')').addClass("current");
			$(".top li a").filter(':eq('+htabLiIdx+')').addClass("current");
			$(".tab_bar li a").each(function(i){//点击横向主tab
				$(this).click(function(){
					if($(".top").is(":hidden")){
						$(".top").show();
					}
					$(".tab_bar li a").removeClass("current");
					$(this).addClass("current");
					$(".top >ul").hide();
					$(".top >ul").eq(i).show();
					jQuery.jCookie('htabIndex',i.toString());
				})
			});
			$(".top li a").each(function(i){//点击tab下的横向子栏目
				$(this).click(function(){
					$(".top li a").removeClass("current");
					$(this).addClass("current");
					jQuery.jCookie('htabLiIndex',i.toString());
				})
			});
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
	<!-- 自适应iframe高度 -->
	<script type="text/javascript" language="javascript"> 
	function iFrameHeight() { 
		var ifm= document.getElementById("frmright"); 
		var subWeb = document.frames ? document.frames["frmright"].document : ifm.contentDocument; 
		if(ifm != null && subWeb != null) { 
			ifm.height = subWeb.body.scrollHeight + 1000; 
		} 
	} 
	</script> 
	<style type="text/css"> 
	<!-- 
	/*导航*/
	.menu{height:32px;padding-left:15px;_overflow:hidden;position: relative;z-index: -1;}
	.menu a{color:#fff;height:16px;line-height:16px;float:left;position:relative;}
	.top{background:#3583CB;position:relative;z-index:5}
	.menu li{font-size:13px;float:left;position:relative;padding:8px 12px;width:94px;}
	.menu li:hover{background-color:#fff;border:1px solid #629d2a;border-bottom:none;padding:8px 11px}
	.menu li:hover a{color:#357d13}
	.menu li.no_sub:hover{border:1px solid #629d2a;padding:7px 11px}
	.menu ul{width:100px;background-color:#fff;border:1px solid #629d2a;border-top:none;position:absolute;left:-1px;top:-999em;z-index:99999;padding:8px;display:none}
	.menu li:hover ul{top:20px;display:block}
	.menu li:hover ul li{font-size:12px;border:none;width:90px;float:left;padding:4px 0 4px 10px}
	.menu li:hover ul li a{color:#333;text-decoration:none;padding:0}
	.menu li:hover ul li a:hover{text-decoration:underline}
	/*IE6*/
	.menu li.hover{background-color:#fff;border:1px solid #629d2a;border-bottom:none;padding:8px 11px}
	.menu li.hover a{color:#357d13}
	.menu li.hover ul{top:31px;display:block}
	.menu li.hover ul li{border:none;width:90px;float:left;padding:4px 0 4px 10px}
	.menu li.hover ul li a{height:16px;line-height:16px;font-size:12px;color:#333;text-decoration:none;padding:0}
	.menu li.hover ul li a:hover{text-decoration:underline;color: #f00;}
	.menu li.no_sub.hover1{border:1px solid #629d2a;padding:7px 11px}
	--> 
	</style> 
	<script type=text/javascript><!--//--><![CDATA[//><!-- 
	function menuFix() { 
		$(".menu li").hover(function(){
				$(this).addClass("hover");
				$(this).children("ul li").attr('class','');
			},function(){
				$(this).removeClass("hover");  
				$(this).children("ul li").attr('class','');
			}
		); 
		$(".menu li.no_sub").hover(function(){
				$(this).addClass("hover1");
			},function(){
				$(this).removeClass("hover1");  
			}
		); 
	} 
	window.onload=menuFix; 
	//--><!]]></script> 
	<script type="text/javascript">
	
	function updatePwdShow(){
		top.Dialog.open({URL:"<%=basePath%>userAction!updatePasswdShow.action",Title:"修改密码",Width:450,Height:180});
	}
	</script>
</head>
<body>
<div id="floatPanel-1"></div>		
<div id="mainFrame" style="width:100%;margin:0 auto;">

<!--头部与导航start-->
<div id="hbox" class="tab_barNav">
	<div id="bs_bannercenter">
		<div id="bs_bannerleft">
			<div id="bs_bannerright2">
					<div class="bs_banner_title"></div>
					<div id="bs_bannerright2">
						<div class="bs_banner_title"></div>
						<div class="nav_icon_h">
							<div class="nav_icon_h_item">
							<a href="javascript:void(0);" target="frmright">
								<div class="nav_icon_h_item_img"><img src="icons/png/64.gif"/></div>
								<div class="nav_icon_h_item_text">帮助文档</div>
							</a>
							</div>
							<div class="nav_icon_h_item">
							<a href="javascript:updatePwdShow();">
								<div class="nav_icon_h_item_img"><img src="icons/png/pwd.gif"/></div>
								<div class="nav_icon_h_item_text">修改密码</div>
							</a>
							</div>
							<div class="nav_icon_h_item">
							<a onclick='Dialog.confirm("确定要退出系统吗",function(){window.location="userAction!loginOut.action"});'>
								<div class="nav_icon_h_item_img"><img src="icons/png/out.gif"/></div>
								<div class="nav_icon_h_item_text">退出系统</div>
							</a>
							</div>
						</div>
					</div>
			  </div>
			<div class="tab_bar">
				<ul>
					<li><a href="javascript:;">在修机车</a></li>
					<li><a href="javascript:;">配件检修</a></li>
					<li><a href="javascript:;">计划管理</a></li>
					<li><a href="javascript:;">记录查询</a></li>
					<li style="float: right;padding-right: 10px;">
				            欢迎您：${sessionScope.session_user.xm}，今天是
					<script>
					var weekDayLabels = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
					var now = new Date();
				    var year=now.getFullYear();
					var month=now.getMonth()+1;
					var day=now.getDate()
				    var currentime = year+"年"+month+"月"+day+"日 "+weekDayLabels[now.getDay()]
					document.write(currentime)
					</script>
					<div class="clear"></div>
					</li>
				</ul>
			</div>
			<div class="clear"></div>
				<div class="top">
					<ul class="menu">
						<li class="no_sub"><a href="<%=basePath%>locoAction!countLocoOnFix.action" class="tablink nosub" target="frmright"><span>在修机车</span></a></li>
					</ul>
					<ul class="menu" style="display: none;">
						<li class="no_sub"><a href="<%=basePath%>recorderAction!countPartRate.action" class="tablink nosub" target="frmright"><span>配件检修记录</span></a></li>
					</ul>
					<ul class="menu" style="display: none;">
						<li class="no_sub"><a href="<%=basePath%>recorderAction!countPlans.action" class="tablink nosub" target="frmright"><span>计划查询</span></a></li>
					</ul>
					<ul class="menu" style="display: none;">
						<li><a class="tablink" href="<%=basePath%>recorderAction!countFixRec.action" target="frmright"><span>机车检修记录</span></a></li>
						<li class="no_sub"><a href="<%=basePath%>recorderAction!countPartRate.action" class="tablink nosub" target="frmright"><span>配件检修记录</span></a></li>
						<li class="no_sub"><a href="<%=basePath%>recorderAction!countReport.action" class="tablink nosub" target="frmright"><span>报活记录查询</span></a></li>
						<%--
						<li class="no_sub"><a href="<%=basePath%>recorderAction!countJgPredict.action" class="tablink nosub" target="frmright"><span>加改记录查询</span></a></li>
						 --%>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!--头部与导航end-->

<!--中部区域start-->
<table width="100%" cellpadding="0" cellspacing="0" class="table_border0">
	<tr>
		<td class="ali01 ver01"  width="100%">
			<div id="rbox">
				<div id="rbox_topcenter">
					<div id="rbox_topleft">
						<div id="rbox_topright">
							<div class="rbox_title">
								操作内容
							</div>
						</div>
					</div>
				</div>
				<div id="rbox_middlecenter">
					<div id="rbox_middleleft">
						<div id="rbox_middleright">
							<div id="bs_right" >
								<iframe src="<%=basePath%>locoAction!countLocoOnFix.action" id="frmright" name="frmright" frameBorder=0 scrolling=no width="100%" onLoad="iFrameHeight()"></iframe>
							</div>
						</div>
					</div>
				</div>
				<div id="rbox_bottomcenter" >
					<div id="rbox_bottomleft">
						<div id="rbox_bottomright"></div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!--中部区域end-->

<!--浏览器resize事件修正start-->
<div id="resizeFix"></div>
<!--浏览器resize事件修正end-->

<!--载进度条start-->
<div class="progressBg" id="progress" style="display:none;"><div class="progressBar"></div></div>
<!--载进度条end-->
</body>
</html>
