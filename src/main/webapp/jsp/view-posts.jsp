<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${appConfig.title}"/></title>
</head>
<body>
<h1>Blog Posts</h1>
<c:forEach items="${posts}" var="post">
    <h2><c:out value="${post.title} (${post.timestamp})"/></h2>
    (by <c:out value="${empty post.author ? 'anonymous' : post.author}"/>)
    <p>
        <c:out value="${post.content}"/>
    </p>
    <c:if test="${not empty post.hashTags}">
        <p style="font-size: 0.7em; font-family: monospace;">
            <c:forEach items="${post.hashTags}" var="hashTag">
                <a href="?tag=${fn:escapeXml(hashTag.name)}">
                    <span style="margin-right: 0.5em;"
                          title="${fn:escapeXml(hashTag.description)}">
                        <c:out value="#${hashTag.name}"/>
                    </span>
                </a>
            </c:forEach>
        </p>
    </c:if>
</c:forEach>
</body>
</html>
