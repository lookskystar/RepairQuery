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
			background:url(gdt/ss/1.jpg) left top no-repeat;
			width:99%;
			height:550px;
			position:absolute;
			border-left:1px solid #a0a0a0;
			left:0px;
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
			left:-5px;
			top:-10px;
			width:280px;
			height:120px;
		}
		div.k3{
			border:3px dashed yellow;
			position: absolute;
			left:365px;
			top:648px;
			width:92px;
			height:30px;
		}
		div.s2{
			border:3px dashed black;
			position: absolute;
			left:100px;
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
			left:100px;
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
			left:100px;
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
		div.k4{
			border:3px dashed red;
			position: absolute;
			left:435px;
			top:373px;
			width:92px;
			height:126px;
		}
		div.k5{
			border:3px dashed #000;
			position: absolute;
			left:440px;
			top:556px;
			width:182px;
			height:90px;
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
        							<!--15-->
							        <div group="15" class="box" style="left:75px;top:278px;">1 
							        	<div class="s2">水阻台</div> 
							        	<div class="s3">小辅修库</div> 
							        	<div class="s4">中修库</div> 
							        </div>
							        <div group="15" class="box" style="left:115px;top:278px;">2 
							        </div>
							        <div group="15" class="box" style="left:155px;top:278px;">3 
							        </div>
							        <div group="15" class="box" style="left:195px;top:278px;">4 
							        </div>
							        <div group="15" class="box" style="left:235px;top:278px;">5 
							        </div>
							        <!--11-->
							        <div group="11" class="box" style="left:305px;top:278px;">1 
							        </div>
							        <div group="11" class="box" style="left:345px;top:278px;">2 
							        </div>
							        <!--12-->
							        <div group="12" class="box" style="left:115px;top:318px;">1 
							        	<div class="k2"></div>
							        </div>
							        <div group="12" class="box" style="left:155px;top:318px;">2 
							        </div>
							        <div group="12" class="box" style="left:195px;top:318px;">3 
							        </div>
							        <div group="12" class="box" style="left:235px;top:318px;">4 
							        </div>
							        <div group="12" class="box" style="left:275px;top:318px;">5 
							        </div>
							        <!--13-->
							        <div group="13" class="box" style="left:207px;top:358px;">1 
							        </div>
							        <div group="13" class="box" style="left:247px;top:358px;">2 
							        </div>
							        <div group="13" class="box" style="left:287px;top:358px;">3 
							        </div>
							        <div group="13" class="box" style="left:327px;top:358px;">4 
							        </div>
							        <!--14-->
							        <div group="14" class="box" style="left:218px;top:395px;">1 
							        </div>
							        <div group="14" class="box" style="left:258px;top:395px;">2 
							        </div>
							        <div group="14" class="box" style="left:298px;top:395px;">3 
							        </div>
							        <div group="14" class="box" style="left:338px;top:395px;">4 
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
