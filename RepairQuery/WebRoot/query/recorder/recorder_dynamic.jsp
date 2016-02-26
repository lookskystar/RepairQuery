<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<!--框架必需start-->
<script type="text/javascript" src="js/jquery-1.4.js"></script>
<script type="text/javascript" src="js/framework.js"></script>
<link href="css/import_basic.css" rel="stylesheet" type="text/css"/>
<link  rel="stylesheet" type="text/css" id="skin" prePath="<%=basePath%>"/>
<!--框架必需end-->

<!--截取文字start-->
<script type="text/javascript" src="js/text/text-overflow.js"></script>
<!--截取文字end-->

<!--修正IE6支持透明PNG图start-->
<script type="text/javascript" src="js/method/pngFix/supersleight.js"></script>
<!--修正IE6支持透明PNG图end-->
<!-- 引入分页JS -->
<script type="text/javascript" src="admin/js/page.js"></script>
<script type="text/javascript" src="experiment/js/jquery.messager.js"></script>
<script src="js/menu/contextmenu.js" type="text/javascript"></script>

<script type="text/javascript">
   //根据机车类型得到一级部件信息
   function getFirstUnit(){
	   var jcsType=$("#jcsType").val();
	   if(jcsType!=""){
		   $.post("<%=basePath%>pjManageAction!ajaxGetFirstUnit.do",{"jcsType":jcsType},function(data){
               var result=eval("("+data+")");
               var firstUnits=result.firstUnits;
               var str="<option value=''>请选择大部件</option>";
               for(var i=0;i<firstUnits.length;i++){
                   str+="<option value="+firstUnits[i].firstUnitId+">"+firstUnits[i].firstUnitName+"</option>";
               }
               $("#first").children().remove();
               $("#first").append(str);
           });
	   }
   }
    //得到二级部件信息
    function getSecondUnit(){
        var firstId=$("#first").val();
        if(firstId!=""&&firstId!=0){
            $.post("<%=basePath%>fixItemAction!ajaxGetSecondUnit.do",{"firstUnitId":firstId},function(data){
                var result=eval("("+data+")");
                var secunits=result.secunits;
                var str="<option value=''>请选择二级部件</option>";
                for(var i=0;i<secunits.length;i++){
                	 str+="<option value="+secunits[i].secunitId+">"+secunits[i].secunitName+"</option>";
                }
                $("#second").children().remove();
                $("#second").append(str);
            });
        }
    }

    //得到项目详情信息
    function getItemInfo(itemId){
    	top.Dialog.open({URL:"<%=basePath%>fixItemAction!itemInfoInput.do?itemId="+itemId,Width:600,Height:450,Title:"查看项目详情"});
    }
    //添加项目
    function addPjDyInfo(){
    	top.Dialog.open({URL:"<%=basePath%>pjManageAction!addPjDyInfoInput.do",Width:650,Height:280,Title:"添加待修配件信息"});
    }
    
    //添加检修配件
    function addFixPjDyInfo(pjdId,pjsId){
    	top.Dialog.open({URL:"<%=basePath%>pjManageAction!addPjDyInfoInput.do?pjdId="+pjdId+"&pjsId="+pjsId,Width:650,Height:280,Title:"添加待修配件信息"});
    }
     //修改动态配件信息
    function updatePjDyInfo(pjId){
    	top.Dialog.open({URL:"<%=basePath%>pjManageAction!updatePjDyInfoInput.do?pjId="+pjId,Width:650,Height:280,Title:"修改动态配件信息"});
    }
     //删除动态配件信息
    function delPjDyInfo(pjId,pjName){
    	var pjIdArry=[];
    	top.Dialog.confirm("确认要删除吗?",function(){
    		if(pjId!=0){//单个删除
    			pjIdArry.push(pjId);
    		}else{
    			$("input[name='pjIds']:checked").each(function(){
    				pjIdArry.push($(this).val());
    			});
    		}
    		if(pjIdArry.length==0){
    			top.Dialog.alert("请选择配件!");
    		}else{
    			var pjIds=pjIdArry.join(",");
    			window.location.href="<%=basePath%>pjManageAction!delPjDyInfo.do?pjIds="+pjIds+"&pjName="+pjName;
    		}
    	});
    }
     
    function updateStatus(){
    	var pjIdArry=[];
    	$("input[name='pjIds']:checked").each(function(){
    		pjIdArry.push($(this).val());
    	});
    	if(pjIdArry.length==0){
    		top.Dialog.alert("请选择配件!");
        }else{
        	var pjIds=pjIdArry.join(",");
        	window.location.href="<%=basePath%>pjManageAction!updateStatus.do?pjIds="+pjIds;
    	}
    }
    
    $(function(){
    	top.Dialog.close();
    	//getFirstUnit();
  		<c:if test="${!empty message}">
			top.Dialog.alert('${message}');
		</c:if>
		
		$("#all").click(function(){
	    	if($(this).attr("checked")){
	    		$("input[name='pjIds']").each(function(){
	    			$(this).attr("checked",true);
	    		});
	    	}else{
	    		$("input[name='pjIds']").each(function(){
	    			$(this).attr("checked",false);
	    		});
	    	}
    	});
		
		$("input[name='comments']").change(function(){
			var pjdId=$(this).attr("pjdId");
			var val = $(this).val();
			if($.trim(val).length>0){
				$.post("<%=basePath%>pjManageAction!ajaxUpdatePjComments.do",{"pjdId":pjdId,"comments":val},function(data){
					if(data=="success"){
						$.messager.show(0, '备注信息保存成功', 2000);
					}else{
						top.Dialog.alert("操作失败");
					}
				});
			}
		});
    });
    
    $(function() {
   				 var option = { width: 100, items: [
                    { text: "中修", icon: "<%=basePath%>images/icons/ico4.gif", alias: "中修",  width: 100,action: menuAction},
                    { text: "小辅修", icon: "<%=basePath%>images/icons/ico4.gif", alias: "小辅修",  width: 100,action: menuAction},
                    { text: "临修", icon: "<%=basePath%>images/icons/ico4.gif", alias: "临修",  width: 100,action: menuAction}
                    ], onShow: applyrule,
        onContextMenu: BeforeContextMenu
    };
    function menuAction(object) {
    	var obj = $(object).val();
        $(object).val(obj+this.data.alias);
        $(object).focus();
    }
    function BeforeContextMenu() {
        return this.id != "target3";
    }
    
    function applyrule(menu) {               
       menu.applyrule({ name: "all",
           disable: true,
           items: []
       });
    }
    $("input[name='comments']").contextmenu(option);
   });
    
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：记录查询>> 动态配件查询</span>
		</div>	
		</div>	
		</div>
	</div>
	<div class="box2" panelTitle="综合条件查询">
		<form id="form" action="recorderAction!dyPJListInput.action" method="post">
			<div style="float: left;margin-left: 10px;margin-top: 5px;">车型：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<select id="jcsType" class="default" name="jcsType">
		        	<option value="">请选择机车型号</option>
		            <c:forEach var="jc" items="${jcsTypes}">
		                <option value="${jc.jcStypeValue }" <c:if test="${jc.jcStypeValue==jcsType}">selected="selected"</c:if>>${jc.jcStypeValue  }</option>
		            </c:forEach>
		        </select>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">状态：</div>
	        <div style="float: left;margin-left: 10px;margin-top: 5px;">
	        	<select id="pjStatus" class="default" name="pjStatus">
	                  <option value="">请选择状态</option>
	                  <option value="0" <c:if test="${pjStatus==0}">selected="selected"</c:if>>合格</option>
	                  <option value="1" <c:if test="${pjStatus==1}">selected="selected"</c:if>>待修</option>
	                  <option value="2" <c:if test="${pjStatus==2}">selected="selected"</c:if>>报废</option>
	                  <option value="3" <c:if test="${pjStatus==3}">selected="selected"</c:if>>检修中</option>
	           	</select>
	        </div>
	        <div style="float: left;margin-left: 10px;margin-top: 5px;">存储位置：</div>
	   		<div style="float: left;margin-left: 10px;margin-top: 5px;">
		   		<select id="storePosition" class="default" name="storePosition">
					<option value="">请选择</option>
				    <option value="0" <c:if test="${storePosition==0}">selected="selected"</c:if>>中心库</option>
			       	<option value="1" <c:if test="${storePosition==1}">selected="selected"</c:if>>班组</option>
			       	<option value="2" <c:if test="${storePosition==2}">selected="selected"</c:if>>车上</option>
			       	<option value="3" <c:if test="${storePosition==3}">selected="selected"</c:if>>外地</option>
			       	<option value="4" <c:if test="${storePosition==4}">selected="selected"</c:if>>配件上</option>
			  	</select>
		  	</div>
		  	<div style="float: left;margin-left: 10px;margin-top: 5px;">上车号 ：</div>
		  	<div style="float: left;margin-left: 10px;margin-top: 5px;">
		  		<input type="text" style="width: 100px" name="getOnNum" value="${getOnNum}"/>
		  	</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">配件编号：</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" style="width: 100px" name="pjNum" value="${pjNum }"/>
			</div>
	 		<div style="float: left;margin-left: 10px;margin-top: 5px;">配件名称：</div>
	 		<div style="float: left;margin-left: 10px;margin-top: 5px;">
				<input type="text" style="width: 100px" name="pjName" value="${pjName }"/>
			</div>
			<div style="float: left;margin-left: 10px;margin-top: 5px;">
	        	<input type="submit" value="查询"/>
	        </div>
		</form> 
	</div>
	<div id="scrollContent">
		<table width="100%" class="tableStyle" useCheckBox="false">
			<tr>
				<th class="ver01" width="10%" align="center">配件名称</th>
				<th class="ver01" width="17%" align="center">所属静态配件</th>
				<th class="ver01" width="10%" align="center">配件编号</th>
				<th class="ver01" width="5%" align="center">机车类型</th>
				<th class="ver01" width="5%" align="center">出厂编号</th>
				<th class="ver01" width="10%" align="center">存储位置</th>
			    <th class="ver01" width="5%" align="center">配件状态</th>
			    <th class="ver01" width="8%" align="center">备注</th>
				<th class="ver01" width="20%" align="center">操作</th>
			</tr>
			<c:if test="${!empty pm.datas}">
				<c:forEach items="${pm.datas}" var="pjd">
				   <tr>
				    <td class="ver01" align="center">${pjd.pjName}</td>
					<td class="ver01" align="center">${pjd.pjSname}</td>
					<td class="ver01" align="center">${pjd.pjNum}</td>
					<td class="ver01" align="center">
						${fn:substring(pjd.jcType, 1, fn:length(pjd.jcType) - 1)}
					</td>
					<td class="ver01" align="center">${pjd.factoryNum}</td>
					<td class="ver01" align="center">
					  <c:if test="${pjd.storePosition==0}">中心库</c:if>
					  <c:if test="${pjd.storePosition==1}">班组</c:if>
					  <c:if test="${pjd.storePosition==2}">车上 ${pjd.getOnNum}</c:if>
					  <c:if test="${pjd.storePosition==3}">外地</c:if>
					  <c:if test="${pjd.storePosition==4}">配件上</c:if>
					</td>
					<td class="ver01" align="center">
					  <c:if test="${pjd.pjStatus==0}">合格</c:if>
					  <c:if test="${pjd.pjStatus==1}">待修</c:if>
					  <c:if test="${pjd.pjStatus==2}">报废</c:if>
					  <c:if test="${pjd.pjStatus==3}">检修中</c:if>
					</td>
					<td class="ver01" align="center">
					  <input type="text" style="width:80px;" value="${pjd.comments}" name="comments" pjdId="${pjd.pjdid}"/>
					</td>
					<td class="ver01" align="center">
					     <a href="recorderAction!findPJRecorder.action?pjId=${pjd.pjdid}&jwdCode=${jwdCode}" style="color:blue;" target="_blank">检修记录</a>&nbsp;&nbsp;
					  </td>
				  </tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty pm.datas}">
				 <tr> <td class="ver01" align="center" colspan="9">没有相应的数据记录!</td></tr>
			</c:if>
		</table>
	</div>
	<!-- 处理分页 -->
	<div style="height:75px;">
	总共${pm.count}条记录,每页显示10条记录
	<div class="float_right padding5 paging">
		<div class="float_left padding_top4">
			<pg:pager maxPageItems="${pageSize }" url="recorderAction!dyPJListInput.action" items="${pm.count}" export="page1=pageNumber">
				<pg:param name="jcsType" value="${jcsType}"/>
			  	<pg:param name="firstUnitId" value="${firstUnitId}"/>
			  	<pg:param name="pjName" value="${urlName}"/>
			  	<pg:param name="pjNum" value="${pjNum}"/>
			  	<pg:param name="pjStatus" value="${pjStatus}"/>
			  	<pg:param name="storePosition" value="${storePosition}"/>
			  	<pg:param name="getOnNum" value="${getOnNum}"/>
			  	<pg:param name="jwdCode" value="${jwdCode }"/>
			  	<pg:first>
					<span><a href="${pageUrl  }" id="first">首页</a></span>
			  	</pg:first>
			  	<pg:prev>
					<span><a href="${pageUrl  }">上一页</a></span>
			  	</pg:prev>
		  	  	<pg:pages>
					<c:choose>
						<c:when test="${page1==pageNumber}">
							<span class="paging_current"><a href="javascript:;">${pageNumber }</a></span>
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
</body>
</html>

