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
  - Description: This page displays all anonymous messages.
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
     <title><fmt:message key="anonymous_room.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validate.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/insertTags.js"></script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            <h3 align="center"><fmt:message key="anonymous_room.title" /></h3>
            <c:choose>
                <c:when test="${not empty requestScope.MESSAGES}">
                    <c:forEach items="${requestScope.MESSAGES}" var="message">
                        <div style="margin-left: 3%; margin-right: 3%;"><hr></div>
                        <div class="messageInfo">
                            <span style="background: url('${pageContext.request.contextPath}/resources/images/anonymous-icon.png') no-repeat scroll 0 0px transparent;padding-left: 30px;" title="anonymous">
                                <c:out value="${message.authorName}"/>
                            </span>
                            <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/date-icon.png') no-repeat scroll 0 3px transparent;padding-left: 20px; margin-right: 20px;" title="date">
                                <fmt:formatDate value="${message.creationDate}" type="both" timeStyle="medium" />
                            </span>
                        </div>
                        <p><c:out value="${message.body}" escapeXml="false"/></p>
                        <div style="margin-left: 3%; margin-right: 3%;"><hr></div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p><fmt:message key="anonymous_room.label.have_no_messages" /></p>
                </c:otherwise>
            </c:choose>
            <form action="${pageContext.request.contextPath}/anonymous-room" method="post" name="addMessageForm">
                <br>
                <hr>
                <h4 align="center"><img src="${pageContext.request.contextPath}/resources/images/comment2-icon.png" height="12" width="26"><fmt:message key="anonymous_room.label.messages" />(${fn:length(requestScope.MESSAGES)})</h4>
                <p>
                    <span style="color: red;"><jblog:printValidationErrors property="authorName" /></span><br>
                    <fmt:message key="anonymous_room.label.author_name" /><br>
                    <input type="text" value="<c:out value="${requestScope.messageAuthorField}" default="${sessionScope.userName}" />" name="messageAuthorField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, 0-9, _, length: 1-20</span><br><br>

                    <span id="tag_toolbar">
                        <input type="button" id="tag_bold" onclick="edInsertTag(messageBodyField, 0);" value="b" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_italic" onclick="edInsertTag(messageBodyField, 1);" value="i" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_del" onclick="edInsertTag(messageBodyField, 2);" value="del" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_link" onclick="edInsertLink(messageBodyField, 3);" value="link" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_block" onclick="edInsertTag(messageBodyField, 4);" value="b-quote" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                    </span><br>
                    <span style="color: red;"><jblog:printValidationErrors property="body" /></span><br>
                    <fmt:message key="anonymous_room.label.message" /><br>
                    <textarea cols="55" rows="15" name="messageBodyField"><c:out value="${requestScope.messageBodyField}" /></textarea><br>
                    <span style="font-size: 60%; color: grey;">length: 2-5000</span><br>
                    <br>

                    <span style="color: red;"><jblog:printValidationErrors property="captcha" /></span><br>
                    <fmt:message key="anonymous_room.label.captcha" /><br>
                    <img src="${pageContext.request.contextPath}/kaptcha.png"><br>
                    <input type="text" name="captchaAnswerField"><br>
                    <input type="submit" value="<fmt:message key="anonymous_room.label.leave_message" />" id="submit" name="leaveMessageButton">
                </p>
            </form>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
