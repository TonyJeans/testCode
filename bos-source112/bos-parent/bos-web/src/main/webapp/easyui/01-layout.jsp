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
	<div title="系统菜单" style="width: 200px" data-options="region:'west'">西部</div>
	<div data-options="region:'center'">中心</div>
	<div style="width: 100px" data-options="region:'east'">东区</div>
	<div style="height: 100px" data-options="region:'south'">南区</div>

</body>
</html>