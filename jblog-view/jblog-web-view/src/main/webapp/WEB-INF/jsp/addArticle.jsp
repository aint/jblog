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
  - Description: This page displays a form for adding article.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<!DOCTYPE HTML>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="add_article.title" /></title>
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
            <h3 align="center"><fmt:message key="add_article.title" /></h3>
            <form action="${pageContext.request.contextPath}/add-article" method="post" name="addStoryForm">
                <p>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="title" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <b><fmt:message key="add_article.label.title" /></b><br>
                    <input type="text" size="80" value="<c:out value="${requestScope.articleTitleField}" />" name="articleTitleField"><br>
                    <span style="font-size: 60%; color: grey;">length: 5-75</span><br>
                    
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="preview" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <b><fmt:message key="add_article.label.preview" /></b> (<fmt:message key="add_article.hint.preview" />)<br>
                    <textarea rows="5" cols="85" name="articlePreviewField"><c:out value="${requestScope.articlePreviewField}" /></textarea><br>
                    <span style="font-size: 60%; color: grey;">length: 100-750</span><br><br>
                    
                    <span id="tag_toolbar">
                        <input type="button" id="tag_bold" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;" onclick="edInsertTag(articleBodyField, 0);" value="b">
                        <input type="button" id="tag_italic" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;" onclick="edInsertTag(articleBodyField, 1);" value="i">
                        <input type="button" id="tag_del" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;" onclick="edInsertTag(articleBodyField, 2);" value="del">
                        <input type="button" id="tag_link" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;" onclick="edInsertLink(articleBodyField, 3);" value="link">
                        <input type="button" id="tag_block" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;" onclick="edInsertTag(articleBodyField, 4);" value="b-quote">
                    </span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="body" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <b><fmt:message key="add_article.label.body" /></b><br>
                    <textarea rows="33" cols="85" name="articleBodyField"><c:out value="${requestScope.articleBodyField}" /></textarea><br>
                    <span style="font-size: 60%; color: grey;">min length: 1500</span><br>
                    
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="keywords" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <b><fmt:message key="add_article.label.keywords" /></b> (<fmt:message key="add_article.hint.keywords" />)<br>
                    <textarea rows="3" cols="85" name="articleKeywords"><c:out value="${requestScope.articleKeywords}" /></textarea><br>
                    <span style="font-size: 60%; color: grey;">length: 5-115</span><br>
                    <input type="submit" value="<fmt:message key="add_article.button.add_article" />" id="submit" name="addArticleButton">
                </p>
            </form>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
