<%@ page import="hello.servlet.basic.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.basic.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();


    String username = request.getParameter("username");
    int age =Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);



%>


<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <li>id= <%=member.getId()%></li>
    <li>username= <%= member.getUserName()%></li>
    <li>age= <%=member.getAge()%></li>
</ul>

<a href="/index.html">메인</a>

</body>
</html>
