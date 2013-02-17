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
  - Description: This page is displayed when the user's browser is MSIE.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Internet Explorer?</title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
</head>

<body>
    <div align="center">
        <img src="${pageContext.request.contextPath}/resources/images/msie.jpg" align="middle" alt="Internet Explorer? FUCK OFF!!!">
        <div style="font-size: 200%; color: red; padding-bottom: 30px;"><fmt:message key="msie.label.gtfoh" /></div>
        <b><fmt:message key="msie.label.advice" /></b><br>
        <i><a href="//www.google.com/chrome/index.html">Google Chrome</a>, 
        <a href="//www.mozilla.org/en-US/firefox/new/">Mozilla Firefox</a>, 
        <a href="//www.apple.com/safari/download/">Safari</a>,
        <a href="//www.opera.com/download/">Opera</a>.</i>
    </div>
</body>

</html>
