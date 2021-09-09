<%--
  Created by IntelliJ IDEA.
  User: fsp
  Date: 2021/9/6
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<script type="text/javascript" >
    $.ajax({
        url : "",
        type : "",
        data : {
        },
        dataType : "json",
        success : function (data) {

        }
    })
</script>

<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

</body>
</html>
