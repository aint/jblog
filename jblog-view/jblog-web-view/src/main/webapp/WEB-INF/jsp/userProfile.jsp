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
  - Description: This page displays the user profile.
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
    <title><c:out value="${requestScope.USER.userName}" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/userProfile.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            
            <div class="user_header">
                <h2 class="username"><a href=""><c:out value="${requestScope.USER.userName}" /></a></h2>
                <div class="karma">
                    <div class="label"><fmt:message key="user_profile.label.rating" /></div>
                    <div class="score">
                        <div class="num"><c:out value="${requestScope.USER.rating}" /></div>
                    </div>
                </div>
            </div>
            
            <table class="menu">
                <tbody>
                    <tr>
                        <td class="item  active">
                            <a href=""><span class="name"><fmt:message key="user_profile.label.profile" /></span></a>
                        </td>
                        <td class="item ">
                            <a href=""><span class="name"><fmt:message key="user_profile.label.articles" /> (${fn:length(requestScope.USER.articles)})</span></a>
                        </td>
                        <td class="item ">
                            <a href=""><span class="name"><fmt:message key="user_profile.label.comments" /> (${fn:length(requestScope.USER.comments)})</span></a>
                        </td>
                    </tr>
                </tbody>
            </table>
            
            <div class="user_profile">
                <div class="fullname"><c:out value="${requestScope.USER.firstName} ${requestScope.USER.lastName}" /></div>
                <dl>
                    <dt><fmt:message key="user_profile.label.birthday" /></dt>
                    <dd><fmt:formatDate value="${requestScope.USER.birthday}" /></dd>
                </dl>
                <dl>
                    <dt><fmt:message key="user_profile.label.registration_date" /></dt>
                    <dd><fmt:formatDate value="${requestScope.USER.registrationDate}" /></dd>
                </dl>
                <dl>
                    <dt><fmt:message key="user_profile.label.last_login_time" /></dt>
                    <dd><fmt:formatDate value="${requestScope.USER.lastLoginTime}" type="both" timeStyle="medium" /></dd>
                </dl>
                <dl>
                    <dt><fmt:message key="user_profile.label.rank" /></dt>
                    <dd><fmt:message key="${requestScope.USER.rank}" /></dd>
                </dl>
                <jblog:ifBanned user="${requestScope.USER}">
                    <dl>
                        <dt><fmt:message key="user_profile.label.ban_expiration_date" /></dt>
                        <dd><fmt:formatDate value="${requestScope.USER.banExpirationDate}" type="both" timeStyle="medium" /></dd>
                    </dl>
                    <dl>
                        <dt><fmt:message key="user_profile.label.ban_reason" /></dt>
                        <dd><c:out value="${requestScope.USER.banReason}" /> </dd>
                    </dl>
                </jblog:ifBanned>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
