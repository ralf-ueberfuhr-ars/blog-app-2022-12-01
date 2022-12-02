<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blog Post App</title>
</head>
<body>
    <h1>Blog Posts</h1>
    <c:forEach items="${posts}" var="post">
        <h2><c:out value="${post.title} (${post.timestamp})"/></h2>
        (by <c:out value="${empty post.author ? 'anonymous' : post.author}"/>)
        <p>
            <c:out value="${post.content}"/>
        </p>
    </c:forEach>
    ${posts}
</body>
</html>
