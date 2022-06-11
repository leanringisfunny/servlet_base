<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //request response는 그냥 사용 가능->jsp도 서블릿으로 변환되서 사용됨-> servlet response request 쓸수 있도록 지원된
    //< % = % >로 둘러쌓이지 않으면 다 http response에 담긴다.
    MemberRepository memberRepository= MemberRepository.getInstance();
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));
    Member member = new Member(username,age);
    memberRepository.save(member);

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%= member.getId()%>  </li>
    <li>username=<%= member.getUsername()%>  </li>
    <li>age= <%= member.getAge()%>  </li>
    <a href="/index.html" >메인</a>
</ul>


</body>
</html>
