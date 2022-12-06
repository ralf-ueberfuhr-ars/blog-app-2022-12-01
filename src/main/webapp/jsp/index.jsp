<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${appConfig.title}"/></title>
</head>
<body>

<h1><c:out value="${appConfig.title}"/></h1>
<h2>Welcome</h2>
<ul>
    <li><a href="./h2-console">H2 Console</a></li>
    <li><a href="./posts/view.html">Alle Posts (HTML view)</a></li>
    <li><a href="./posts/findall">Alle Posts (JSON)</a></li>
    <li><a href="./posts/find?id=1">Post mit ID 1 (JSON)</a></li>
    <li>
        <form action="./posts/find" method="get">
            <label for="txtId">Nach ID suchen:</label>
            <input name="id" type="number" id="txtId" required placeholder="(ID eingeben)">
            <input type="submit">
        </form>
    </li>
</ul>
<h2>Create a blog post</h2>
<!-- Spring MVC support: see https://www.baeldung.com/spring-mvc-form-tutorial -->
<!-- Test controller validation: enter a title less that 3 chars-->
<!-- Test service validation (not handled by controller): enter valid title (>=3 chars) and a content less than 10 chars-->
<form action="./posts/create" method="post">
    <label for="txtTitle">Title: </label><br>
    <input type="text" required id="txtTitle" name="title"><br>
    <c:if test="${param.error eq 'title'}">
        <span style="color: red;">Mindestens 3 Zeichen!</span><br>
    </c:if>
    <label for="txtContent">Content:</label><br>
    <textarea id="txtContent" name="content" required></textarea><br>
    <input type="submit">
</form>

</body>
</html>
