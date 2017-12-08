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

<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
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
			
			<div title="2">
					<!-- 使用标准json构造ztree -->
				<ul id="ztree1" class="ztree"></ul>
				<script>
					$(function(){
						//页面加载完成后
						var setting={};
						//构造节点数据
						var zNodes=[
							{"name":"节点1","children":[
														{"name":"节点1-1"},
														{"name":"节点2-2"}
													]},
							{"name":"节点2"},
							{"name":"节点3"}
						];
						$.fn.zTree.init($("#ztree1"), setting, zNodes);
					});
				</script>
			</div> 
			<div title="3">
					<!-- 使用简单json构造ztree--Ajax -->
				<ul id="ztree2" class="ztree"></ul>
				<script>
					$(function(){
						//页面加载完成后
						var setting2={
								data: {
									simpleData: {
										enable: true//使用简单json
										
									}
								}
						};
						//构造节点数据
						var zNodes2=[
							{"id":"1","pId":"2","name":"节点1"},
							{"id":"2","pId":"3","name":"节点2"},
							{"id":"3","pId":"0","name":"节点3"}
						];
						$.fn.zTree.init($("#ztree2"), setting2, zNodes2);
					});
				</script>
			</div>
			<div title="4">
			
			<!-- 使用简单json构造ztree -->
				<ul id="ztree3" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后
						var setting3={
								data: {
									simpleData: {
										enable: true//使用简单json
										
									}
								},
									callback: {
										onClick: function (event, treeId, treeNode) {
										//	alert(treeNode.page);
										 //动态添加选项卡
										 if(treeNode.page!=undefined){
											 //判断存在
										var e = $("#mytabs").tabs("exists",treeNode.name);
											if(e){
												$("#mytabs").tabs("select",treeNode.name);
											} else{
												 $("#mytabs").tabs("add",{
													 title:treeNode.name,
														iconCls:'icon-edit',
														closable:true,
														content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
													 
												 });
												
											}
									
											 
									 }
										 
									}
								}
						};
						
						//发送Ajax请求获取Json数据
						var url = "${pageContext.request.contextPath }/json/menu2.json";
						
						//这个确实访问不到,结构是302然后跳转到登录页面了
					//var  url = "http://localhost:8295/a/sys/office/treeData";
				
						$.post(url,{},function(data){
							
							$.fn.zTree.init($("#ztree3"), setting3, data);
						},"json");
					});
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 制作tab选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- div表示子面板 -->
			<div data-options="iconCls:'icon-cut'" title="1">111</div>
			<div data-options="closable:true" title="2">
			
			</div>
			<div title="3">333</div>
		</div>
	</div>
	<div style="width: 100px" data-options="region:'east'">东区</div>
	<div style="height: 100px" data-options="region:'south'">南区</div>

</body>
</html>