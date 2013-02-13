<%--

    Copyright (C) 2012-2013  Olexandr Tyshkovets

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

--%>
<%-- 
  - Author:      Olexandr Tyshkovets
  - Description: This page displays the hub's articles.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="view_hub.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            <h3 align="center"><c:out value="${requestScope.HUB.name}" /></h3>
            <c:choose>
                <c:when test="${not empty requestScope.ARTICLES}">
                    <c:forEach items="${requestScope.ARTICLES}" var="article">
                        <table style="width: 100%;">
                            <tr>
                                <td>
                                    <div class="postTitle">
                                        <a href="${pageContext.request.contextPath}/display-article/${article.id}">${article.title}</a>
                                    </div>
                                    <div style="margin-left: 2%; margin-right: 1%;">
                                        <div style="background: url('${pageContext.request.contextPath}/resources/images/hub-icon.png') no-repeat 0px 0px;padding-left:25px;font-size:11px;padding-top:2px;padding-bottom:2px;color:#999;margin-bottom:5px;" title="hub">
                                            <c:out value="${article.hub.name}" /><br>
                                        </div>
                                        <c:out value="${article.preview}"/><br>
                                        <div class="articleInfo">
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/user-icon.png') no-repeat scroll 0 2px transparent;padding-left: 27px;" title="user">
                                                <c:out value="${article.author.userName}"/>
                                            </span>
                                            <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/date-icon.png') no-repeat scroll 0 3px transparent;padding-left: 20px; margin-right: 20px;" title="date">
                                                <fmt:formatDate value="${article.creationDate}" type="both" timeStyle="short" />
                                            </span>
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/pageViews-icon.png') no-repeat 0 6px;padding-left: 20px; margin-right: 20px;" title="views">
                                                <c:out value="${article.views}"/>
                                            </span>
                                            
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/voteUp2-icon.png') no-repeat scroll 0 4px transparent;padding-left: 15px;" title="rating"></span>
                                                <jblog:printRating rating="${article.rating}"/>
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/voteDown2-icon.png') no-repeat scroll 0 4px transparent;padding-left: 20px;" title="rating"></span>
                                            
                                            <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/comment1-icon.png') no-repeat scroll 0 6px transparent;padding-left: 20px;" title="comments">
                                                <c:out value="${fn:length(article.comments)}"/>
                                            </span>
                                        </div>
                                    </div>
                                    <hr>
                                </td>
                            </tr>
                        </table>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p><fmt:message key="view_hub.label.have_no_articles" /></p> 
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
