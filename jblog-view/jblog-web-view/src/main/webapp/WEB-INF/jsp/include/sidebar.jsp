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
  - Description: The sidebar.
  --%>
<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<div class="sidebar">
    <ul class="nav">
        <li><a href="${pageContext.request.contextPath}/index"><fmt:message key="siderbar.link.main" /></a></li>
        <li><a href="${pageContext.request.contextPath}/articles"><fmt:message key="siderbar.link.all_articles" /></a></li>
        <li><a href="${pageContext.request.contextPath}/add-article"><fmt:message key="siderbar.link.add_article" /></a></li>
        <jblog:ifAuth roles="moderator,admin">
            <li><a href="${pageContext.request.contextPath}/add-news"><fmt:message key="siderbar.link.add_news" /></a></li>
        </jblog:ifAuth>
        <li><a href="${pageContext.request.contextPath}/users"><fmt:message key="siderbar.link.users" /></a></li>
        <li><a href="${pageContext.request.contextPath}/public-room"><fmt:message key="siderbar.link.public_room" /></a></li>
    </ul>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
</div>
