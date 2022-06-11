<%@ page import="hello.servlet.domain.member.Member" %><%--
  Created by IntelliJ IDEA.
  User: 조민호
  Date: 2022-06-11
  Time: 오전 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
<%--  컨트롤러에서 servlet request로 전달받은 데이터를 꺼낼때 getAttribute()를 이용해서 해당 키에 상응하는 객체를 꺼내 데이터를 조회해야하나  --%>
<%-- jsp에서 ${키이름.멤버이름}으로 바로 조회가 가능하다(프로퍼티 접근법getMember가 호출) --%>
    <li>id= ${member.id}  </li>
    <li>username=<%= ((Member)request.getAttribute("member")).getUsername()%>  </li>
    <li>age= <%= ((Member)request.getAttribute("member")).getAge()%>  </li>
    <a href="/index.html" >메인</a>
</ul>


</body>
</html>
