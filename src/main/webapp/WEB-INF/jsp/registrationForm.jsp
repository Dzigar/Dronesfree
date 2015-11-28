<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="label.user.registration.page.title" /></title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/reg-form-style.css" />" media="all" />
<script type="text/javascript" src="/static/js/registration/reg-form.js"></script>
</head>

<body>
	<a href="<c:url value="/" />"><spring:message
			code="label.navigation.home.link" /> </a>
	<!--
        If the user is anonymous (not logged in), show the registration form.
    -->

	<sec:authorize access="isAnonymous()">
		<div class="container">
			<header>
				<h1>
					<spring:message code="label.user.registration.page.title" />
				</h1>
			</header>
			<div class="form">
				<!--
                    Ensure that when the form is submitted, a POST request is send to url
                    '/user/register'.
                -->
				<form:form action="/user/register" commandName="user"
					method="POST" role="form" acceptCharset="utf-8">
					<!-- Add CSRF token to the request. -->
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<!--
                        If the user is using social sign in, add the signInProvider
                        as a hidden field.
                    -->
					<c:if test="${user.signInProvider != null}">
						<form:hidden path="signInProvider" />
					</c:if>
					<p class="contact">
						<label class="control-label" for="user-firstName"><spring:message
								code="label.user.firstName" />:</label>
						<form:errors id="error-firstName" path="firstName"
							cssClass="help-block" />
					</p>
					<form:input id="firstName" path="firstName" cssClass="form-control" />

					<p class="contact">
						<label class="control-label" for="user-lastName"><spring:message
								code="label.user.lastName" />:</label>
						<form:errors id="error-lastName" path="lastName"
							cssClass="help-block" />
					</p>

					<form:input id="user-lastName" path="lastName"
						cssClass="form-control" />


					<p class="contact">
						<label class="control-label" for="user-email"><spring:message
								code="label.user.email" />:</label>
						<form:errors id="error-email" path="email" cssClass="help-block" />
					</p>
					<!--
                                Add the email field to the form and ensure
                                that validation errors are shown.
                            -->
					<form:input id="user-email" path="email" cssClass="form-control" />



					<p class="contact">
						<label class="control-label" for="user-username"><spring:message
								code="label.user.username" />:</label>
						<form:errors id="error-username" path="username"
							cssClass="help-block" />
					</p>
					<!--
                                Add the email field to the form and ensure
                                that validation errors are shown.
                            -->
					<form:input id="user-username" path="username"
						cssClass="form-control" />


					<!--
                        If the user is creating a normal user account, add password fields
                        to the form.
                    -->
					<c:if test="${user.signInProvider == null}">

						<p class="contact">
							<label class="control-label" for="user-password"><spring:message
									code="label.user.password" />:</label>
							<form:errors id="error-password" path="password"
								cssClass="help-block" />
						</p>
						<!--
                                    Add the password field to the form and ensure
                                    that validation errors are shown.
                                -->
						<form:password id="user-password" path="password"
							cssClass="form-control" />

						<p class="contact">
							<label class="control-label" for="user-passwordVerification"><spring:message
									code="label.user.passwordVerification" />:</label>
							<form:errors id="error-passwordVerification"
								path="passwordVerification" cssClass="help-block" />
						</p>
						<!--
                                    Add the passwordVerification field to the form and ensure
                                    that validation errors are shown.
                                -->
						<form:password id="user-passwordVerification"
							path="passwordVerification" cssClass="form-control" />


					</c:if>
					<!-- Add the submit button to the form. -->

					<button type="submit" class="btn btn-default">
						<spring:message code="label.user.registration.submit.button" />
					</button>
				</form:form>
			</div>
		</div>
	</sec:authorize>
	<!--
        If the user is authenticated, show a help message instead
        of registration form.
    -->
	<sec:authorize access="isAuthenticated()">
		<p>
			<spring:message code="text.registration.page.authenticated.user.help" />
		</p>
	</sec:authorize>
</body>
</html>