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
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="rating" required="true" rtexprvalue="true" type="java.lang.Integer" %>
<%@ tag body-content="empty"%>

<c:choose>
    <c:when test="${rating gt 0}">
        <span style="color: green;">${rating}</span>
    </c:when>
    <c:when test="${rating lt 0}">
        <span style="color: red;">${rating}</span>
    </c:when>
    <c:otherwise>
        <span style="color: black;">${rating}</span>
    </c:otherwise>
</c:choose>
