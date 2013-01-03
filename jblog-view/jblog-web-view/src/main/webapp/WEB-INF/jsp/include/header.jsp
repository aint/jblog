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
  - Description: The header.
  --%>
<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<fmt:setLocale value="${sessionScope.userLanguage.languageCode}" scope="session" />

<c:set var="msg" value="${requestScope.BROWSER_MSG}" scope="page" />
<c:choose>
    <c:when test="${not empty msg}">
        <div class="browserMsg">
            <div class="browserMsgBody">
                <c:out value="${msg}" /><br>
                <c:choose>
                    <c:when test="${fn:containsIgnoreCase(msg, 'Chrome')}">
                        <a href="//www.google.com/chrome/index.html"><img src="${pageContext.request.contextPath}/resources/images/browsers/chrome.png" alt="chrome"></a>
                    </c:when>
                    <c:when test="${fn:containsIgnoreCase(msg, 'Firefox')}">
                        <a href="//www.mozilla.org/en-US/firefox/new/"><img src="${pageContext.request.contextPath}/resources/images/browsers/firefox.png" alt="firefox"></a>
                    </c:when>
                    <c:when test="${fn:containsIgnoreCase(msg, 'Opera')}">
                        <a href="//www.opera.com/download/"><img src="${pageContext.request.contextPath}/resources/images/browsers/opera.png" alt="opera"></a>
                    </c:when>
                    <c:when test="${fn:containsIgnoreCase(msg, 'Safari')}">
                        <a href="//www.apple.com/safari/download/"><img src="${pageContext.request.contextPath}/resources/images/browsers/safari.png" alt="safari"></a>
                    </c:when>
                    <c:otherwise>
                        <a href="//www.google.com/chrome/index.html"><img src="${pageContext.request.contextPath}/resources/images/browsers/chrome.png" alt="chrome"></a>
                        <a href="//www.mozilla.org/en-US/firefox/new/"><img src="${pageContext.request.contextPath}/resources/images/browsers/firefox.png" alt="firefox"></a>
                        <a href="//www.opera.com/download/"><img src="${pageContext.request.contextPath}/resources/images/browsers/opera.png" alt="opera"></a>
                        <a href="//www.apple.com/safari/download/"><img src="${pageContext.request.contextPath}/resources/images/browsers/safari.png" alt="safari"></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div> 
        <%-- This parameter equals browserMsg's height in main.css --%>
        <c:set var="margin" value="margin-top: 72px;" scope="page" />
    </c:when>
</c:choose>

<div class="header" style="${margin}">
    <div align="center">
        <%-- top level with logo --%>
        <table style="width: 100%; background: #6f7d96; border-bottom: 1px solid #000;">
            <tr>
                <td align="center" width="33%" bgcolor="#6f7d96">
                    <a href="${pageContext.request.contextPath}/index"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="jBlog logo" width="308" height="150" align="middle" /></a>
                </td>
                <td align="center" width="34%" bgcolor="#6f7d96">
                    <a href="${pageContext.request.contextPath}/index"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="jBlog logo" width="308" height="150" align="middle" /></a>
                </td>
                <td align="center" width="33%" bgcolor="#6f7d96">
                    <a href="${pageContext.request.contextPath}/index"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="jBlog logo" width="308" height="150" align="middle" /></a>
                </td>
            </tr>
        </table>
        <%-- second level with sign in/up button --%>
        <table style="width: 100%;">
            <tr>
                <td align="left" id="dateformat" width="50%"><script type="text/javascript">document.write(showDate());</script></td>
                <jblog:ifAuth>
                    <c:set var="show" value="true" scope="page"/>
                </jblog:ifAuth>
                <c:choose>
                    <c:when test="${not empty pageScope.show}">
                        <td width="30%" align="center"><fmt:message key="header.label.welcome_message" /> <c:out value="${sessionScope.userName}"/></td>
                        <td width="10%" align="center"><a href="${pageContext.request.contextPath}/logout"><fmt:message key="header.link.sign_out" /></a></td>
                    </c:when>
                    <c:otherwise>
                        <td width="30%" align="center"><a href="${pageContext.request.contextPath}/login"><fmt:message key="header.link.sign_in" /></a></td>
                        <td width="10%" align="center"><a href="${pageContext.request.contextPath}/registration"><fmt:message key="header.link.sign_up" /></a></td>
                    </c:otherwise>
                </c:choose>
                <td width="10%" align="right">
                    <a href="${pageContext.request.contextPath}/change-locale?ln=en"><img src="${pageContext.request.contextPath}/resources/images/flags/en.gif" alt="en"></a>
                    <a href="${pageContext.request.contextPath}/change-locale?ln=ru"><img src="${pageContext.request.contextPath}/resources/images/flags/ru.gif" alt="рус"></a>
                    <a href="${pageContext.request.contextPath}/change-locale?ln=uk"><img src="${pageContext.request.contextPath}/resources/images/flags/ua.gif" alt="укр"></a>
                </td>
            </tr>
        </table>
    </div>
</div>
