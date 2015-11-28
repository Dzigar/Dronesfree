<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${user.firstName} ${user.lastName}</title>

<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

<script src="/static/bower_components/jquery/dist/jquery.js" type="text/javascript"></script>
<script
	src="/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="include/header.jsp" />

	<div class="container">
		<c:out value="${user.firstName}" />
		<c:out value="${user.lastName}" />
		<%-- <form action="<c:url value='${user.username}/removeUserAccount'/>"
		method="POST">
		<input type="submit" value="Remove account" class="button" /> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form> --%>

		<div class="list-group" style="width: 40%">
			<h3><spring:message code='label.user.page.orders' />:</h3>

			<c:forEach var="order" items="${user.orders}">
				<a href="/order?id=${order.id}" class="list-group-item ">
					<h4 class="list-group-item-heading">
						<c:out value="${order.description}" />
					</h4>
					<p class="list-group-item-text">
						<c:out value="${order.geolocation.address}" />
						<br>
						<c:out value="${order.creationTime.dayOfMonth}.${order.creationTime.monthOfYear}.${order.creationTime.year}" />
					</p>
				</a>
				<c:if
					test="${order.user.username eq pageContext.request.userPrincipal.name}">
					<form action="/order/remove/${order.id}" method="POST">
						<input class="btn btn-danger btn-sm" type="submit" value="<spring:message code='label.user.page.action.order.delete' />" />
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</c:if>
				<br>
			</c:forEach>
		</div>
	</div>
</body>
</html>