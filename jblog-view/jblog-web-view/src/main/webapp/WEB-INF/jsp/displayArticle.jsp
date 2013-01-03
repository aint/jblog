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
  - Description: This page displays article.
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
    <meta name="keywords" content="${article.keywords}" >
    <title><c:out value="${article.title}" /></title>
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
            <h3 align="center"><c:out value="${article.title}"/></h3>
            <div style="margin-left: 2%; margin-right: 1%;"><c:out value="${article.body}" escapeXml="false"/></div>
            <%-- article's details --%>
            <p><b><fmt:message key="display_article.label.keywords" />:</b>
            <jblog:printKeywords keywords="${article.keywords}"/><br></p>
            
            <div class="articleInfo">
                <span style="background: url('${pageContext.request.contextPath}/resources/images/user-icon.png') no-repeat scroll 0 2px transparent;padding-left: 27px;" title="user">
                    <c:out value="${article.author.userName}"/>(<fmt:message key="${article.author.rank}" />)
                </span>
                <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/date-icon.png') no-repeat scroll 0 3px transparent;padding-left: 20px; margin-right: 20px;" title="date">
                    <fmt:formatDate value="${article.creationDate}" type="both" timeStyle="short" />
                </span>
                <span style="background: url('${pageContext.request.contextPath}/resources/images/pageViews-icon.png') no-repeat 0 6px;padding-left: 20px; margin-right: 20px;" title="views">
                    <c:out value="${article.views}"/>
                </span>
                
                <jblog:showArticleVote article="${article}">
                    <a href="${pageContext.request.contextPath}/vote?forArticle=plus&articleId=${article.id}">
                        <span style="background: url('${pageContext.request.contextPath}/resources/images/voteUp1-icon.png') no-repeat scroll 0 4px transparent;padding-left: 15px;" title="rating"></span>
                    </a>
                </jblog:showArticleVote>
                <jblog:printRating rating="${article.rating}"/>
                <jblog:showArticleVote article="${article}">
                    <a href="${pageContext.request.contextPath}/vote?forArticle=minus&articleId=${article.id}">
                        <span style="background: url('${pageContext.request.contextPath}/resources/images/voteDown1-icon.png') no-repeat scroll 0 4px transparent;padding-left: 15px; margin-left: 5px;" title="rating"></span>
                    </a>
                </jblog:showArticleVote>
            </div>
            
            <%-- edit article --%>
            <jblog:ifCanEditArticle article="${article}">
                <a href="${pageContext.request.contextPath}/edit-article?articleId=${article.id}"><fmt:message key="display_article.label.edit_article" /></a>
            </jblog:ifCanEditArticle>
            
            <br>
            <br>
            <h4 align="center"><img src="${pageContext.request.contextPath}/resources/images/comment2-icon.png" height="12" width="26"><fmt:message key="display_article.label.comments" />(${fn:length(requestScope.COMMENTS)})</h4>
            <%-- show article's comments --%>
            <c:forEach items="${requestScope.COMMENTS}" var="comment">
                <hr>
                <div class="commentInfo">
                    <span style="background: url('${pageContext.request.contextPath}/resources/images/user-icon.png') no-repeat scroll 0 2px transparent;padding-left: 27px;" title="user">
                        <c:out value="${comment.author.userName}"/>(<fmt:message key="${comment.author.rank}" />)
                    </span>
                    <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/date-icon.png') no-repeat scroll 0 3px transparent;padding-left: 20px; margin-right: 20px;" title="date">
                        <fmt:formatDate value="${comment.creationDate}" type="both" timeStyle="short" />
                    </span>
                    
                    <jblog:showCommentVote comment="${comment}">
                        <a href="${pageContext.request.contextPath}/vote?forComment=plus&commentId=${comment.id}">
                            <span style="background: url('${pageContext.request.contextPath}/resources/images/voteUp1-icon.png') no-repeat scroll 0 4px transparent;padding-left: 15px;" title="rating"></span>
                        </a>
                    </jblog:showCommentVote>
                    <jblog:printRating rating="${comment.rating}"/>
                    <jblog:showCommentVote comment="${comment}">
                        <a href="${pageContext.request.contextPath}/vote?forComment=minus&commentId=${comment.id}">
                            <span style="background: url('${pageContext.request.contextPath}/resources/images/voteDown1-icon.png') no-repeat scroll 0 4px transparent;padding-left: 15px; margin-left: 5px;" title="rating"></span>
                        </a>
                    </jblog:showCommentVote>
                </div>
                <p><c:out value="${comment.body}" escapeXml="false"/></p>
                <hr>
            </c:forEach>
            
            <%-- add comment --%>
            <form action="${pageContext.request.contextPath}/add-comment?articleId=${article.id}" method="post">
                <br>
                <span id="tag_toolbar">
                    <input type="button" id="tag_bold" onclick="edInsertTag(commentBodyField, 0);" value="b" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                    <input type="button" id="tag_italic" onclick="edInsertTag(commentBodyField, 1);" value="i" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                    <input type="button" id="tag_del" onclick="edInsertTag(commentBodyField, 2);" value="del" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                    <input type="button" id="tag_link" onclick="edInsertLink(commentBodyField, 3);" value="link" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                    <input type="button" id="tag_block" onclick="edInsertTag(commentBodyField, 4);" value="b-quote" style="background: url('${pageContext.request.contextPath}/resources/images/tag-button.png') repeat-x 0 -2px;">
                </span><br>
                <span style="color: red;">
                    <jblog:printValidationErrorMsg fieldName="body" errorMsgMap="${requestScope.errorMsgMap}" />
                </span><br>
                <fmt:message key="display_article.label.comment" /><br>
                <textarea cols="55" rows="15" name="commentBodyField"><c:out value="${requestScope.commentBodyField}" /></textarea><br>
                <span style="font-size: 60%; color: grey;">length: 2-5000</span><br>
                <input type="submit" value="<fmt:message key="display_article.label.leave_comment" />" id="submit" name="leaveCommentButton">
            </form>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
