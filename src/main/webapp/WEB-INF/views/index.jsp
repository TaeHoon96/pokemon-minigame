<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>나만의 포켓몬 만들기</title>
</head>
<body>
    <p>나에게 딱 맞는 포켓몬 만들어보자!</p>
    <nav>
        <a href="sign-up">회원가입</a>
        <%
            if (session.getAttribute("pokeUserID") != null) {
        %>
        <a href="logout">로그아웃</a>
        <% } %>
    </nav>
    <%
        if (session.getAttribute("pokeUserID") != null) {
    %>
    <h3>로그인 되었습니다!</h3>
    <p>
<%--        <%= request.getAttribute("pokeUserID") %>--%>
        <%= request.getAttribute("username") %> 님 환영합니다!
    </p>
        <form action="more">
            <button>더 그려줘!</button>
        </form>
        <section>
            <% for (String url : (List<String>) request.getAttribute("images")) { %>
            <img width="320" src="<%= url %>" alt="유저의 포켓몬">
            <% } %>
        </section>
    <%
        } else {
    %>
    <h3>로그인하세요!</h3>
    <section>
        <form method="POST">
            <label>
                닉네임 : <input type="text" name="username">
            </label>
            <label>
                비밀번호 : <input type="password" name="password">
            </label>
            <button>로그인</button>
        </form>
    </section>
    <%
        }
    %>
</body>
</html>
