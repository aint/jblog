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
  - Description: This page displays a form for adding news.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="add_news.title" /></title>
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
            <h3 align="center"><fmt:message key="add_news.title" /></h3>
            <form action="${pageContext.request.contextPath}/add-news" method="post" name="addNewsForm">
                <p>
                    <span style="color: red;"><jblog:printValidationErrors property="title" /></span><br>
                    <b><fmt:message key="add_news.label.title" /></b><br>
                    <input type="text" size="80" value="<c:out value="${requestScope.newsTitleField}" />" name="newsTitleField"><br>
                    <span style="font-size: 60%; color: grey;">length: 5-75</span><br><br>
                    
                    <input type="checkbox" value="true" name="newsIsPinnedField"> <b><fmt:message key="add_news.label.pinned" /></b><br>
                    
                    <span style="color: red;"><jblog:printValidationErrors property="importance" /></span><br>
                    <b><fmt:message key="add_news.label.news_importance" /></b><br>
                    <select name="newsImportanceField">
                        <option value="low"><fmt:message key="add_news.label.news_importance.low" /></option>
                        <option value="middle"><fmt:message key="add_news.label.news_importance.middle" /></option>
                        <option value="high"><fmt:message key="add_news.label.news_importance.high" /></option>
                    </select><br><br>
                    
                    <span id="tag_toolbar">
                        <input type="button" id="tag_bold" onclick="edInsertTag(newsBodyField, 0);" value="b" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_italic" onclick="edInsertTag(newsBodyField, 1);" value="i" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_del" onclick="edInsertTag(newsBodyField, 2);" value="del" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_link" onclick="edInsertLink(newsBodyField, 3);" value="link" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                        <input type="button" id="tag_block" onclick="edInsertTag(newsBodyField, 4);" value="b-quote" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                    </span><br>
                    <span style="color: red;"><jblog:printValidationErrors property="body" /></span><br>
                    <b><fmt:message key="add_news.label.body" /></b><br>
                    <textarea rows="33" cols="85" name="newsBodyField"><c:out value="${requestScope.newsBodyField}" /></textarea><br>
                    <span style="font-size: 60%; color: grey;">min length: 10, max length: 4000</span><br>
                    
                    <input type="submit" value="<fmt:message key="add_news.button.add_news" />" id="submit" name="addNewsButton">
                </p>
            </form>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
