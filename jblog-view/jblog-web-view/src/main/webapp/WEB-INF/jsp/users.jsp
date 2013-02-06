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
  - Description: This page displays all registered users.
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
    <title><fmt:message key="users.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
    <script type="text/javascript">
        function sh(elem) {
            obj = document.getElementById(elem);
            if (obj.style.display == "none") { obj.style.display = "block"; } else { obj.style.display = "none"; }
        }
    </script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />
        <div class="content">
            <h3 align="center"><fmt:message key="users.title" /></h3>
            <jblog:ifAuth roles="moderator,admin">
                <c:set var="show" value="true" scope="page" />
            </jblog:ifAuth>
            <c:choose>
                <c:when test="${not empty requestScope.USERS}">
                    <c:forEach items="${requestScope.USERS}" var="user" varStatus="var">
                        
                        <div class="userNameInfo">
                            <c:out value="${user.userName}: ${user.firstName} ${user.lastName}" /><br>
                        </div>
                        
                        <p>
                            <fmt:formatDate value="${user.birthday}" pattern="dd.MM.yyyy" var="birthday" />
                            <fmt:message key="users.label.birthday" /> ${birthday}<br>
                            <fmt:message key="users.label.posted_articles" /> ${fn:length(user.articles)}<br>
                            <c:if test="${fn:length(user.articles) ne 0}">
                                <a href="javascript:sh('userArticles-${var.count}')"><fmt:message key="users.label.show_user_articles" /></a>
                                <span id="userArticles-${var.count}" style="margin-left:10px; float:none; padding:1px 15px 3px 15px; border:thin solid #e0e0e0;background-color: whiteSmoke; display:none">
                                    <c:forEach items="${user.articles}" var="article">
                                        <a href="${pageContext.request.contextPath}/display-article/${article.id}"><c:out value="${article.title}" /></a>;
                                    </c:forEach>
                                </span>
                                <br>
                            </c:if>
                            <fmt:message key="users.label.posted_comments" /> ${fn:length(user.comments)}<br>
                            <fmt:message key="users.label.registration_date" /> <fmt:formatDate value="${user.registrationDate}" />,
                            <fmt:message key="users.label.last_login_time" /> <fmt:formatDate value="${user.lastLoginTime}" type="both" timeStyle="medium" /><br>
                            <fmt:message key="users.label.rank" /> <fmt:message key="${user.rank}" />,
                            <fmt:message key="users.label.rating" /> <jblog:printRating rating="${user.rating}" /><br>
                            <jblog:ifBanned user="${user}">
                                <fmt:message key="users.label.ban_expiration_date" /> <fmt:formatDate value="${user.banExpirationDate}" type="both" timeStyle="medium" />,
                                <fmt:message key="users.label.banReason" /> ${user.banReason}
                                <c:set var="banned" value="true" scope="page"/>
                             </jblog:ifBanned>
                        </p>
                        
                        <c:if test="${not empty show}">
                            <form action="${pageContext.request.contextPath}/users" method="post">
                                <p>
                                    <input type="hidden" value="${user.userName}" name="userName">
                                    <c:choose>
                                        <c:when test="${empty banned}">
                                            <fmt:message key="users.label.ban_length" />
                                            <input type="number" size="5" min="1" max="99" value="1" name="banLength"><br>
                                            <span style="font-size: 60%; color: grey;">max = 99</span><br>
                                            <fmt:message key="users.label.ban_reason" /><br>
                                            <input type="text" size="55" value="" name="banReason"><br>
                                            <input type="hidden" value="ban" name="action">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="hidden" value="unban" name="action">
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="submit" value="ban/unban" name="BanUnbanUserButton">
                                </p>
                            </form>
                        </c:if>
                        <div style="margin-left: 3%; margin-right: 3%;"><hr></div>
                        
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>
                        empty<br>
                        -_O<br>
                        <a href="${pageContext.request.contextPath}/registration">registration</a>
                    </p> 
                </c:otherwise>
            </c:choose>
        
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    </div>
</body>

</html>
