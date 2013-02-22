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
                        <td class="item ${requestScope.ITEM_PROFILE}">
                            <a href="${pageContext.request.contextPath}/user/${requestScope.USER.userName}"><span class="name"><fmt:message key="user_profile.label.profile" /></span></a>
                        </td>
                        <td class="item ${requestScope.ITEM_ARTICLES}">
                            <a href="${pageContext.request.contextPath}/user/${requestScope.USER.userName}/articles">
                                <span class="name"><fmt:message key="user_profile.label.articles" /> (<c:out value="${requestScope.USER.articleCount}" />)</span>
                            </a>
                        </td>
                        <td class="item">
                            <a href=""><span class="name"><fmt:message key="user_profile.label.comments" /> (${fn:length(requestScope.USER.comments)})</span></a>
                        </td>
                    </tr>
                </tbody>
            </table>
            
            <c:choose>
                <c:when test="${not empty requestScope.ITEM_PROFILE}">
                    <div class="user_profile">
                        <div class="fullname"><c:out value="${requestScope.USER.firstName} ${requestScope.USER.lastName}" /></div>
                        <div class="rating-place"><c:out value="${USER_POSITION}" /><fmt:message key="user_profile.label.position" /></div>
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
                </c:when>
                <c:otherwise>
                    <div style="margin-top: 20px;">
                        <c:choose>
                            <c:when test="${not empty requestScope.USER_ARTICLES}">
                                <c:forEach items="${requestScope.USER_ARTICLES}" var="article">
                                    <table style="width: 100%;">
                                        <tr><td>
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
                                        </td></tr>
                                    </table>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div style="margin-left: 2%; margin-right: 1%;">
                                    <fmt:message key="user_profile.label.have_no_articles" />
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
            
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
