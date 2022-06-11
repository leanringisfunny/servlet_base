<%--
  Created by IntelliJ IDEA.
  User: 조민호
  Date: 2022-06-10
  Time: 오후 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--정대 경로가 아닌 상대 경로로 설정해서 어느 디렉토리로 부터 폼을 접근해도 현재 디렉토리까지의 패스를 그대로 사용한 save파일로 이동--%>
<form action="save" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>
