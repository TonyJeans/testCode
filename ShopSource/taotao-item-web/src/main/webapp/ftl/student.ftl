<html>
<head>
    <title>测试页面</title>
</head>
<body>
学生信息:<br>
学号:${student.id}<br>
姓名:${student.name}<br>
年龄:${student.age}<br>
家庭住址:${student.address}<br>
学生列表:<br>
<table border="1">
    <tr>
        <th>序号</th>
        <th>学号</th>
        <th>姓名</th>
        <th>年龄</th>
        <th>家庭住址</th>
    </tr>
<#list s as stu>
    <#if stu_index%2==0>
    <tr bgcolor="#ff7f50">
    <#else>
    <tr bgcolor="aqua">
    </#if>

    <td>${stu_index}</td>
    <td>${stu.id}</td>
    <td>${stu.name}</td>
    <td>${stu.age}</td>
    <td>${stu.address}</td>
</tr>
</#list>

</table>
<br>
日期类型:${data?string("yyyy/MM/dd HH:mm:ss")}

include标签测试:
<#include "hello.ftl">
</body>
</html>