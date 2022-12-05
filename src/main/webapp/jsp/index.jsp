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
Welcome!
<ul>
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

</body>
</html>
