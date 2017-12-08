<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<!--使用div描述区域  -->
	<div title="xxx管理系统" style="height: 100px" data-options="region:'north'">北部</div>
	<div title="系统菜单" style="width: 200px" data-options="region:'west'">
		<!-- accordion折叠面板 fit:true填充父容器 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- div表示子面板 -->
			<div data-options="iconCls:'icon-cut'" title="1">
				<a id="but1" class="easyui-linkbutton">添加一个选项卡</a>
				<script>
				$(function(){
					$("#but1").click(function(){
						
						//判断选项卡是否存在
					var e =	$("#mytabs").tabs("exists","系统管理");
						if(e){
							$("#mytabs").tabs("select","系统管理");
						}else{
							$("#mytabs").tabs("add",{
								title:'系统管理',
								iconCls:'icon-edit',
								closable:true,
								content:'<iframe frameborder="0" height="100%" width="100%" src="https://www.baidu.com"></iframe>'
							});
							
						}
							
						
						
					});
				});
				</script>
			</div>
			
			<div title="2">222</div>
			<div title="3">333</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 制作tab选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- div表示子面板 -->
			<div data-options="iconCls:'icon-cut'" title="1">111</div>
			<div data-options="closable:true" title="2">222</div>
			<div title="3">333</div>
		</div>
	</div>
	<div style="width: 100px" data-options="region:'east'">东区</div>
	<div style="height: 100px" data-options="region:'south'">南区</div>

</body>
</html>