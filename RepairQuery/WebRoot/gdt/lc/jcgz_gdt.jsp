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
	<script type="text/javascript" src="js/bsFormat.js"></script>
	<script type="text/javascript" src="js/framework.js  "></script>
	<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
	<script type="text/javascript" src="js/menu/jkmegamenu.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.js"></script>
	<!--框架必需end-->
	<!--[if IE 6]>
	<script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
	<![endif]-->
	<script type="text/javascript">
	//-1-新建 , 0-在修  1-待验  2-已交
	var data=${jcJson};
	var dd;
	
	$(document).ready(function(){
		for(var i=0;i<data.length;i++){
			var gdh=data[i].gdh;
			var tar=$('div[group="'+gdh+'"]').eq(data[i].tw-1);
			if(data[i].statue==2){//已交
				tar.addClass('green');
			}else if(data[i].statue==1){//待验
				tar.addClass('yellow');
			}else if(data[i].statue==0){
				tar.addClass('blue');
			}else{//新建
				tar.addClass('red');
			}
			tar.html('<a href="javascript:void(0);"id="'+data[i].jcnum+'" class="jTip" name="<%=basePath%>locoAction!findPlanInfoDetail.action?jwdCode=0'+ data[i].jwdcode +'&rjhmId='+data[i].rjhmId+'">'+data[i].jcnum+'</a>');
		}

		$('.jTip').bind('click',function(event){
			var url=$(event.target).attr('name');
			 $(document).ready(function () {
            	dd =$.dialog({
	                title:"机车信息",
	                content:'url:'+url+'&temp='+new Date().getTime(),
	                height:550,
	                width:568,
	                max:false,
	                min:false
	            });
        	})
		});
	});
	</script>
	<style>
		body,html{
			padding:0px;
			margin:0px;
			width:100%;
			height:100%;
		}
		#maps{
			background:url(gdt/lc/1.jpg) left top no-repeat;
			width:100%;
			height:700px;
			position:absolute;
			border-left:1px solid #a0a0a0;
			left:0px;
			background-color: #CCCCCC;
		}
		#content{
			position:relative;
			left:0px;
			width:100%;
			float:left;
			height:100%;
			background-color:#c0c0c0;
			text-align: center;
			overflow: auto;
		}
		.box{
			width:35px;
			height:16px;
			line-height:16px;
			border:1px solid #999;
			text-align:center;
			position:absolute;
			background-color:#fff;	
		}
		div.k2{
			border:3px dashed red;
			position: absolute;
			left:0px;
			top:-5px;
			width:320px;
			height:200px;
		}
		div.k3{
			border:3px dashed #C0F;
			position: absolute;
			left:-10x;
			top:-5px;
			width:180px;
			height:180px;
		}
		div.k4{
			border:3px dashed #000;
			position: absolute;
			left:-10px;
			top:-5px;
			width:150px;
			height:30px;
		}
		div.s2{
			border:3px dashed black;
			position: absolute;
			left:300px;
			bottom:150px;
			width:60px;
			border-top-width: 3px;
			border-left-width: 0px;
			border-right-width: 0px;
			border-bottom-width: 0px;
			font-size: 14px;
			font-weight: bold;
			padding-top: 5px;
		}
		div.s3{
			border:3px dashed red;
			position: absolute;
			left:300px;
			bottom:120px;
			width:60px;
			border-top-width: 3px;
			border-left-width: 0px;
			border-right-width: 0px;
			border-bottom-width: 0px;
			font-size: 14px;
			font-weight: bold;
			padding-top: 5px;
		}
		div.s4{
			border:3px dashed #C0F;
			position: absolute;
			left:300px;
			bottom:90px;
			width:60px;
			border-top-width: 3px;
			border-left-width: 0px;
			border-right-width: 0px;
			border-bottom-width: 0px;
			font-size: 14px;
			font-weight: bold;
			padding-top: 5px;
		}
		.box a{
			display:block;
			text-decoration:none;
			font-size:12px;
			font-weight:bold;
		}
		div.blue{
			background:blue;
		}
		div.green{
			background:#067d06;
		}
		div.red{
			background-color:red;
		}
		div.black{
			background-color:black;
		}
		div.yellow{
			background-color: #ff9600
		}
		div.blue a,div.red a,div.black a,div.yellow a{
			color:#FFF;
		}
		div.green a{
			color:#FFF;
		}
	</style>
</head>
<body>
	<div id="scrollContent">
		<div id="rbox">
			<div id="rbox_middlecenter">
				<div id="rbox_middleleft">
					<div id="rbox_middleright">
						<div id="bs_right">
			  				<div id="content">
	    						<div id="maps">
									 <!--16-->
							        <div group="16" class="box" style="left:125px;top:172px;">1 
							        	<div class="k2"></div>
							        </div>
							        <div group="16" class="box" style="left:165px;top:172px;">2 
							        </div>
							        <div group="16" class="box" style="left:205px;top:172px;">3 
							        </div>
							        <div group="16" class="box" style="left:245px;top:172px;">4 
							        </div>
							        <div group="16" class="box" style="left:285px;top:172px;">5 
							        </div>
							        <div group="16" class="box" style="left:325px;top:172px;">6 
							        </div>
							        <div group="16" class="box" style="left:365px;top:172px;">7 
							        </div>
							        <!--37-->
							        <div group="37" class="box" style="left:525px;top:172px;">1 
							        	<div class="k4"></div> 
							        	<div class="s2">水阻台</div> 
							        	<div class="s3">小辅修库</div> 
							        	<div class="s4">中修库</div> 
							        </div>
							        <div group="37" class="box" style="left:565px;top:172px;">2 
							        </div>
							        <div group="37" class="box" style="left:605px;top:172px;">3 
							        </div>
							        <!--17-->
							        <div group="17" class="box" style="left:145px;top:245px;">1 
							        </div>
							        <div group="17" class="box" style="left:185px;top:245px;">2 
							        </div>
							        <div group="17" class="box" style="left:225px;top:245px;">3 
							        </div>
							        <div group="17" class="box" style="left:265px;top:245px;">4 
							        </div>
							        <div group="17" class="box" style="left:305px;top:245px;">5 
							        </div>
							        <div group="17" class="box" style="left:345px;top:245px;">6 
							        </div>
							        <div group="17" class="box" style="left:385px;top:245px;">7 
							        </div>
							        <!--19-->
							        <div group="19" class="box" style="left:555px;top:242px;">1 
							        </div>
							        <div group="19" class="box" style="left:595px;top:242px;">2 
							        </div>
							        <!--18-->
							        <div group="18" class="box" style="left:175px;top:318px;">1 
							        </div>
							        <div group="18" class="box" style="left:215px;top:318px;">2 
							        </div>
							        <div group="18" class="box" style="left:255px;top:318px;">3 
							        </div>
							        <div group="18" class="box" style="left:295px;top:318px;">4 
							        </div>
							        <div group="18" class="box" style="left:335px;top:318px;">5
							        </div>
							        <div group="18" class="box" style="left:375px;top:318px;">6 
							        </div>
							        <div group="18" class="box" style="left:415px;top:318px;">7 
							        </div>        
							        <!--33-->
							        <div group="33" class="box" style="left:295px;top:464px;">1
							        	<div class="k3"></div> 
							        </div>
							        <div group="33" class="box" style="left:335px;top:464px;">2 
							        </div>
							        <div group="33" class="box" style="left:375px;top:464px;">3 
							        </div>
							        <div group="33" class="box" style="left:415px;top:464px;">4 
							        </div>
							        <!--34-->
							        <div group="34" class="box" style="left:295px;top:540px;">1 
							        </div>
							        <div group="34" class="box" style="left:335px;top:540px;">2 
							        </div>
							        <div group="34" class="box" style="left:375px;top:540px;">3 
							        </div>
							        <div group="34" class="box" style="left:415px;top:540px;">4 
							        </div>
							        <!--35-->
							        <div group="35" class="box" style="left:295px;top:610px;">1 
							        </div>
							        <div group="35" class="box" style="left:335px;top:610px;">2 
							        </div>
									<div group="35" class="box" style="left:375px;top:610px;">3 
							        </div>
							        <div group="35" class="box" style="left:415px;top:610px;">4 
							        </div>
	    						</div>
	    					</div>
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
	</div>
</body>
</html>
