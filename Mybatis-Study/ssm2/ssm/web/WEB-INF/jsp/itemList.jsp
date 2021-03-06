<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.form.min.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form action="${pageContext.request.contextPath }/list.action" method="post">
		查询条件：
		<table width="100%" border=1>
			<tr>
			<tr>
				<th>名称:</th>
				<td><input type="text" name="mingcheng" value="${mingcheng }" /></td>
			</tr>
			<tr>
				<th>详情:</th>
				<td><input type="text" name="detail" value="${detail }" /></td>
			</tr>
			<tr>
				<td rowspan="2"><input type="submit"
					value="查询"></td>
			</tr>
			</tr>

		</table>
	</form>
	<form action="${pageContext.request.contextPath }/deleteItem"
		method="post">
		商品列表：
		<table width="100%" border=1>
			<tr>
				<td><input type="checkbox" onclick="chk('ids',this.checked)"></td>
				<td>商品名称</td>
				<td>商品价格</td>
				<td>生产日期</td>
				<td>商品描述</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${itemList }" var="item" varStatus="vs">
				<tr>
					<td><input type="checkbox" name="itemsList[${vs.index}].id"
						value="${item.id}"></td>
					<td><input type="text" name="itemsList[${vs.index}].name"
						value="${item.name }"></td>
					<td>${item.price }</td>
					<td><fmt:formatDate value="${item.createtime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${item.detail }</td>
					<td><a
						href="${pageContext.request.contextPath }/itemDelete/${item.id}">删除</a></td>
				</tr>
			</c:forEach>
			<input type="submit" value="删除">
		</table>
	</form>
</body>

</html>