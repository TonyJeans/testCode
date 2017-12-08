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

	<!-- 方式三 -->
	<table id="mytable">
	</table>
	<script type="text/javascript">
		$(function () {
			var myIndex =-1;//全局变量
			$("#mytable").datagrid({
				 columns:[[
					 {width:150,title:'编号',field:'id',checkbox:true},
					 {width:150,title:'姓名',field:'name',editor:{
						 								type:'validatebox',
						 								options:{}
					 								}},
					 {width:150,title:'年龄',field:'age',editor:{
							type:'numberbox',
								options:{}
							}},
					 {width:150,title:'日期',field:'address',editor:{
							type:'datebox',
								options:{editable:false}
							}}
				 ]],
				 url:'${pageContext.request.contextPath }/json/datagrid_data.json',
				 rownumbers:true,
				 singleSelect:true,
				 fitColumns:true,
				 //自定义工具栏
				 toolbar:[
					 {text:'添加',iconCls:'icon-add',handler:function(){
						 $("#mytable").datagrid("insertRow",{
							 index:0,//在那行插入
							 row:{}//插入的数据
						 });
						 $("#mytable").datagrid("beginEdit",0);
						 myIndex=0;
					 }},
					 {text:'删除',iconCls:'icon-remove',handler:function(){
						 
						//获得选择行
							var rows = $("#mytable").datagrid("getSelections");
							if(rows.length==1){
								var row = rows[0];
								//获得指定行对象的索引
								myIndex = $("#mytable").datagrid("getRowIndex",row);
							}
						 
						 $("#mytable").datagrid("deleteRow",myIndex);
					 }},
					 {text:'修改',iconCls:'icon-edit',handler:function(){
						//获得选择行
						var rows = $("#mytable").datagrid("getSelections");
						if(rows.length==1){
							var row = rows[0];
							//获得指定行对象的索引
							myIndex = $("#mytable").datagrid("getRowIndex",row);
						}
						 
						 $("#mytable").datagrid("beginEdit",myIndex);
					 }},
					 {text:'保存',iconCls:'icon-search',handler:function(){
						 $("#mytable").datagrid("endEdit",myIndex);
					 }}
				 ],
				 pagination:true ,
				 pageList:[3,5,7,10],
				 //监听结束编辑
				 onAfterEdit:function(index,data,changes){
					 alert(11+""+data.name);
				 }
				
			});
		});
	</script>
</body>
</html>