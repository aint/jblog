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
  - Description: This page displays all hubs.
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
    <title><fmt:message key="hubs.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
    <script type="text/javascript">
        function spoiler(elem) {
            obj = document.getElementById(elem);
            if (obj.style.display == "none") { obj.style.display = "block"; } else { obj.style.display = "none"; }
        }
    </script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            <h3 align="center"><fmt:message key="hubs.title" /></h3>
            <c:choose>
                <c:when test="${not empty requestScope.HUBS}">
                    <c:forEach items="${requestScope.HUBS}" var="hub" varStatus="var">
                        <table style="width: 100%;">
                            <tr>
                                <td>
                                    <div class="postTitle">
                                        <c:out value="${hub.name}" />
                                    </div>
                                    <div style="margin-left: 2%; margin-right: 1%;">
                                        <div class="hubInfo">
                                            <a href="javascript:spoiler('hubDescription-${var.count}')"><fmt:message key="hubs.label.description" /></a>
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/user-icon.png') no-repeat scroll 0 2px transparent; padding-left: 30px; margin-right: 20px; margin-left: 20px;" title="user">
                                                <c:out value="${hub.author.userName}" />
                                            </span>
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/voteUp2-icon.png') no-repeat scroll 0 4px transparent; padding-left: 15px;" title="rating"></span>
                                            <jblog:printRating rating="${hub.rating}" />
                                            <span style="background: url('${pageContext.request.contextPath}/resources/images/voteDown2-icon.png') no-repeat scroll 0 4px transparent; padding-left: 20px;" title="rating"></span>
                                            <span style="margin-left: 20px; background: url('${pageContext.request.contextPath}/resources/images/articles.png') no-repeat scroll 0 1px transparent; padding-left: 30px;" title="articles">
                                                <c:out value="${fn:length(hub.articles)}" />
                                            </span>
                                        </div>
                                        <span id="hubDescription-${var.count}" style="float:none; padding:5px 10px 0px 15px; border:thin solid #e0e0e0; background-color:whiteSmoke; border-radius: 8px; display:none;">
                                            <c:out value="${hub.description}" />
                                        </span>
                                    </div>
                                    <hr>
                                </td>
                            </tr>
                        </table>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p><fmt:message key="hubs.label.have_no_hubs" /></p> 
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
