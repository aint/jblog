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
  - Description: This page displays a form for adding a hub.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="add_hub.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validate.js"></script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            <h3 align="center"><fmt:message key="add_hub.title" /></h3>
            <form action="${pageContext.request.contextPath}/add-hub" method="post">
                <p>
                    <span style="color: red;"><jblog:printValidationErrors property="name" /></span><br>
                    <b><fmt:message key="add_hub.label.hub_name" /></b><br>
                    <input type="text" size="55" value="<c:out value="${requestScope.hubNameField}" />" name="hubNameField"><br>
                    <span style="font-size: 60%; color: grey;">length: 4-55</span><br>
                    <span style="color: red;"><jblog:printValidationErrors property="type" /></span><br>
                    <b><fmt:message key="add_hub.label.hub_type" /></b> <i>(<fmt:message key="add_hub.hint.hub_type" />)</i> <br>
                    <input type="radio" value="public" name="hubTypeField"><fmt:message key="add_hub.label.public_type" />
                    <input type="radio" value="personal" name="hubTypeField"><fmt:message key="add_hub.label.personal_type" /><br>
                    <span style="color: red;"><jblog:printValidationErrors property="description" /></span><br>
                    <b><fmt:message key="add_hub.label.hub_description" /></b><br>
                    <textarea cols="55" rows="15" name="hubDescriptionField"><c:out value="${requestScope.hubDescriptionField}" /></textarea><br>
                    <span style="font-size: 60%; color: grey;">length: 10-300</span><br>
                    <input type="submit" value="<fmt:message key="add_hub.button.add_hub" />" id="submit" name="addHubButton">
                </p>
            </form>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
