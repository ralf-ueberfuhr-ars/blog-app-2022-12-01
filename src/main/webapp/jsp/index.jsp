<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${appConfig.title}"/></title>
</head>
<body>

<h1><c:out value="${appConfig.title}"/></h1>
<sec:authentication property="name" var="username"/>
<h2>Welcome<c:if test="${not empty username}">, <c:out value="${username}"/></c:if></h2>
<ul>
    <li><a href="./h2-console">H2 Console</a></li>
    <li><a href="./actuator">Actuator</a></li>
    <li><a href="./swagger-ui.html">Swagger UI (only DEV profile)</a></li>
    <li><a href="./v3/api-docs">Open API v3 (generated from code)</a></li>
    <li><a href="./blogpost-swagger.yml">Swagger v2 (manually configured)</a></li>
    <li><a href="./posts/view.html">Alle Posts (HTML view)</a></li>
    <li><a href="./posts/findall">Alle Posts (JSON)</a></li>
    <li><a href="./posts/find?id=1">Post mit ID 1 (JSON)</a></li>
    <li><a href="./tags/findall">Alle Tags</a></li>
    <li>
        <form action="./posts/find" method="get">
            <label for="txtId">Nach ID suchen:</label>
            <input name="id" type="number" id="txtId" required placeholder="(ID eingeben)">
            <input type="submit">
        </form>
    </li>
</ul>

<sec:authorize access="isAuthenticated()">
    <h2>Your profile</h2>
    <ul>
        <li>Roles: <sec:authentication property="principal.authorities"/></li>
        <li><a href="./logout">Logout</a></li>
    </ul>
</sec:authorize>

<sec:authorize access="hasRole('AUTHOR')">
    <h2>Create a blog post</h2>
    <!-- Spring MVC support: see https://www.baeldung.com/spring-mvc-form-tutorial -->
    <!-- Test controller validation: enter a title less that 3 chars-->
    <!-- Test service validation (not handled by controller): enter valid title (>=3 chars) and a content less than 10 chars-->
    <form action="./posts/create" method="post">
        <sec:csrfInput/>
        <label for="txtTitle">Title: </label><br>
        <input type="text" required id="txtTitle" name="title"><br>
        <c:if test="${param.error eq 'title'}">
            <span style="color: red;">Mindestens 3 Zeichen!</span><br>
        </c:if>
        <label for="txtContent">Content:</label><br>
        <textarea id="txtContent" name="content" required></textarea><br>
        <label for="txtHashTags">HashTags (sep. by ',', e.g. "private,debug,spring"):</label><br>
        <input type="text" id="txtHashTags" name="hashTags"></textarea><br>
        <input type="submit">
    </form>
    <h2>Describe HashTag</h2>
    <!-- Spring MVC support: see https://www.baeldung.com/spring-mvc-form-tutorial -->
    <!-- Test controller validation: enter a title less that 3 chars-->
    <!-- Test service validation (not handled by controller): enter valid title (>=3 chars) and a content less than 10 chars-->
    <form action="./tags/save" method="post">
        <sec:csrfInput/>
        <label for="txtName">Name: </label><br>
        <input type="text" required id="txtName" name="name"><br>
        <label for="txtDescription">Description:</label><br>
        <input type="text" id="txtDescription" name="description"></textarea><br>
        <input type="submit">
    </form>
</sec:authorize>
</body>
</html>
