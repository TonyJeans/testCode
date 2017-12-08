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
	function toLogin(){
		var username = $("#username").val();
		if(username == ""){
			alert("请输入用户名");
			$("#username").focus();
			return false;
		}
		var password = $("#password").val();
		if(password == ""){
			alert("请输入密码");
			$("#password").focus();
			return false;
		}
	}
</script>
</head>
<body>
	${test }

	<input type="button" onclick="aaa()" value="ajax测试">
	<form id="login">
			<table align="left">
		<tr>
			<th>用户名:</th>
			<td><input type="text" id="username"/></td>
		</tr>
			<tr>
			<th>密  码:</th>
			<td><input type="password" id="password"/></td>
		</tr>
		<tr><td rowspan="2"><input type="button" onclick="toLogin()" value="登录校验"></td></tr>
	</table>
	</form>
</body>
</html>