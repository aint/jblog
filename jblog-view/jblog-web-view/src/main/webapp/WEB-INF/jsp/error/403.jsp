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
  - Description: HTTP status codes 403. Forbidden.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/errorPage.css" />
    <title>HTTP Status 403</title>
</head>

<body>
    <div class="centerBlock">
        <div class="head">
            <b>HTTP Status 403</b>
        </div>
        <div class="body">
            <b>Access denied.</b><br>
            <c:if test="${not empty requestScope['javax.servlet.error.message']}">
                <fmt:message key="${requestScope['javax.servlet.error.message']}" />
            </c:if>
        </div>
    </div>
    <div align="center">
        <img alt="Applauses" src="${pageContext.request.contextPath}/resources/images/applauses.gif" height="540" width="720">
    </div>
</body>

</html>
