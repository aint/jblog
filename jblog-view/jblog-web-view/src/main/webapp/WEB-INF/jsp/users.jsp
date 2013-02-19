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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/users.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />
        <div class="content">
            <h3 align="center" style="color:#333333;font: 30px/118% normal Verdana, Tahoma, sans-serif;"><fmt:message key="users.title" /></h3>
            
            <div class="users_header">
                <div class="karma"><fmt:message key="users.label.rating" /></div>
            </div>
            
            <jblog:ifAuth roles="moderator,admin">
                <c:set var="show" value="true" scope="page" />
            </jblog:ifAuth>
            <c:choose>
            
                <c:when test="${not empty requestScope.USERS}">
                    <jsp:useBean id="currentDate" class="java.util.Date" />
                    <c:forEach items="${requestScope.USERS}" var="user" varStatus="var">
                        <div class="users">
                            <div class="user">
                                <div class="karma"><c:out value="${user.rating}" /></div>
                                <c:if test="${var.count eq 1}">
                                    <div class="king"></div>
                                </c:if>
                                <div class="info">
                                    <div class="userlogin">
                                        <div class="username">
                                            <a style="color: #6DA3BD;" href="${pageContext.request.contextPath}/user/${user.userName}"><c:out value="${user.userName}" /></a>
                                        </div>
                                    </div>
                                    <div class="lifetime">
                                        <fmt:message key="users.label.experience_in_days" />
                                        <!-- 1 * 24 * 60 * 60 * 1000 = 86400000 -->
                                        <fmt:formatNumber maxFractionDigits="0" value="${(currentDate.time - user.registrationDate.time) / 86400000}" />
                                    </div>
                                    <c:if test="${not empty user.articles}">
                                        <div class="last_post">
                                            <fmt:message key="users.label.latest_article" /> <jblog:linkToLatestArticle articles="${user.articles}" />
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        
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
                                <hr>
                            </form>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p><fmt:message key="users.label.have_no_users" /></p> 
                </c:otherwise>
            </c:choose>
        
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    </div>
</body>

</html>
