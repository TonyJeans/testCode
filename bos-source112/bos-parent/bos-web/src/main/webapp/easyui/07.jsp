<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<!-- 静态html渲染成datagrid样式 -->
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">111</th>
				<th data-options="field:'name'">222</th>
				<th data-options="field:'age'">333</th>
			</tr>
		</thead>
		<tbody>
		 	<tr>
		 		<td>001</td>
				<td>xiaom</td>
				<td>90</td>
		 	</tr>
		 	<tr>
		 		<td>002</td>
				<td>xiaom</td>
				<td>30</td>
		 	</tr>
		</tbody>
	</table>
	<hr>
	<!-- 方式二 -->
	<table data-options="url:'${pageContext.request.contextPath }/json/datagrid_data.json'" class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">111</th>
				<th data-options="field:'name'">222</th>
				<th data-options="field:'age'">333</th>
			</tr>
		</thead>
		<tbody>
		 
		</tbody>
	</table>
	<hr/>
		<!-- 方式三 -->
	<table id="mytable">
	</table>
	<script type="text/javascript">
		$(function () {
			$("#mytable").datagrid({
				 columns:[[
					 {title:'编号',field:'id',checkbox:true},
					 {title:'姓名',field:'name'},
					 {title:'年龄',field:'age'},
					 {title:'地址',field:'address'}
				 ]],
				 url:'${pageContext.request.contextPath }/json/datagrid_data.json',
				 rownumbers:true,
				 singleSelect:true,
				 //自定义工具栏
				 toolbar:[
					 {text:'添加',iconCls:'icon-add',handler:function(){
						 alert(0);
					 }},
					 {text:'删除',iconCls:'icon-remove'},
					 {text:'修改',iconCls:'icon-edit'},
					 {text:'修改',iconCls:'icon-search'}
				 ],
				 pagination:true 
			});
		});
	</script>
</body>
</html>