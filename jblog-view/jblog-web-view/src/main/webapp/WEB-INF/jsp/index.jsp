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
  - Description: This page displays the most popular articles and the latest news.
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
    <title><fmt:message key="index.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
        
            <c:if test="${not empty news}">
                <h3 align="center"><fmt:message key="index.label.latest_news" /></h3>
                <c:forEach items="${news}" var="n">
                    <table width="100%">
                        <tr>
                            <td>
                                <div class="postTitle">
                                    <c:choose>
                                        <c:when test="${n.newsImportance.importance eq 'minor'}">
                                            <c:set var="newsColor" value="1dff00" scope="page" />
                                        </c:when>
                                        <c:when test="${n.newsImportance.importance eq 'intermediate'}">
                                            <c:set var="newsColor" value="e3e006" scope="page" />
                                        </c:when>
                                        <c:when test="${n.newsImportance.importance eq 'major'}">
                                            <c:set var="newsColor" value="f31212" scope="page" />
                                        </c:when>
                                    </c:choose>
                                    <font color="${newsColor}">&#9612;</font><c:out value="${n.title}" />
                                </div>
                                <div style="margin-left: 2%; margin-right: 1%;">
                                    <c:out value="${n.body}" escapeXml="false" />
                                    <div style="font-weight:bold;text-indent:3%;width:85%;height:25px;background-color:#${newsColor};border-radius:20px;margin-top:5px;margin-bottom:5px;">
                                        <span style="background: url('${pageContext.request.contextPath}/resources/images/user-icon.png') no-repeat scroll 0 2px transparent;padding-left: 27px;" title="user">
                                            <c:out value="${n.author.userName}"/>
                                        </span>
                                        <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/date-icon.png') no-repeat scroll 0 3px transparent;padding-left: 20px; margin-right: 20px;" title="date">
                                            <fmt:formatDate value="${n.creationDate}" type="both" timeStyle="short" />
                                        </span>
                                    </div>
                                </div>
                                <hr>
                            </td>
                        </tr>
                    </table>
                </c:forEach>
            </c:if>
            
            <c:if test="${not empty articles}">
                <h3 align="center"><fmt:message key="index.label.most_popular_articles" /></h3>
                <c:forEach items="${articles}" var="article">
                    <table width="100%">
                        <tr>
                            <td>
                                <div class="postTitle">
                                    <a href="${pageContext.request.contextPath}/display-article/${article.id}">
                                        <c:out value="${article.title}" />
                                    </a>
                                </div>
                                <div style="margin-left: 2%; margin-right: 1%;">
                                    <div style="background: url('${pageContext.request.contextPath}/resources/images/hub-icon.png') no-repeat 0px 0px;padding-left:25px;font-size:11px;padding-top:2px;padding-bottom:2px;color:#999;margin-bottom:5px;" title="hub">
                                        <c:out value="${article.hub.name}" />
                                    </div>
                                    <c:out value="${article.preview}"/>
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
            </c:if>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
