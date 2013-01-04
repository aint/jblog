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
  - Description: This page displays the registration form.
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jblog.tld" prefix="jblog" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><fmt:message key="registration.title" /></title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
    <script src="${pageContext.request.contextPath}/resources/js/dateFormat.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validate.js"></script>
    <script type="text/javascript">
        window.onload = function() {
            populateDropDownDate("dayField", "monthField", "yearField");
        }
    </script>
</head>

<body>
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp"/>
        <div class="content">
            <p>
                <span style="color: red;"><c:out value="${requestScope.error_msg}"/></span>
            </p>
            <div align="center">	
                <form action="${pageContext.request.contextPath}/registration" method="post" >
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="firstName" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.first_name" />:<br>
                    <input type="text" value="<c:out value="${requestScope.firstNameField}" />" name="firstNameField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, length: 2-30</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="lastName" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.last_name" />:<br>
                    <input type="text" value="<c:out value="${requestScope.lastNameField}" />" name="lastNameField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, length: 2-30</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="userName" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.username" />:<br>
                    <input type="text" value="<c:out value="${requestScope.userNameField}" />" name="userNameField"><br>
                    <span style="font-size: 60%; color: grey;">a-Z, а-Я, 0-9, _, length: 3-20</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="email" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.email" />:<br>
                    <input type="text" value="<c:out value="${requestScope.emailField}" />" name="emailField"><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="password" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.password" />:<br>
                    <input type="password" name="passwordField"><br>
                    <span style="font-size: 60%; color: grey;">length: 4-50</span><br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="rePassword" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.re_password" />:<br>
                    <input type="password" name="rePasswordField"><br>
                    <fmt:message key="registration.label.birthday" />:<br>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="year" errorMsgMap="${requestScope.errorMsgMap}" /><br>
                        <jblog:printValidationErrorMsg fieldName="month" errorMsgMap="${requestScope.errorMsgMap}" /><br>
                        <jblog:printValidationErrorMsg fieldName="day" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <select id="yearField" size="1" name="yearField"></select>
                    <select id="monthField" size="1" name="monthField"></select>
                    <select id="dayField" size="1" name="dayField"></select>
                    <fieldset style="width: 80%; height: 400px;">
                        <legend style="color: red;"><fmt:message key="registration.label.terms_of_service" /></legend>
                        <div style="height: 390px; overflow: auto; text-align: justify; font-size: 70%;">
                            <fmt:message key="registration.label.tos_text" />
                        </div>
                    </fieldset>
                    <span style="color: red;">
                        <jblog:printValidationErrorMsg fieldName="license" errorMsgMap="${requestScope.errorMsgMap}" />
                    </span><br>
                    <fmt:message key="registration.label.accept_the_terms" /> <input name="acceptLicenseField" type="checkbox"><br>
                    <input type="submit" value="<fmt:message key="registration.button.sign_up" />" name="registrationButton">
                </form>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
    </div>
</body>

</html>
