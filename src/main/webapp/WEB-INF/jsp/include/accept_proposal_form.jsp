<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if
	test="${order.user.username eq pageContext.request.userPrincipal.name}">
	<form action="/proposal/accept?id=${proposal.id}" id="acceptProp"
		method="POST">
		<input type="submit" value="Accept"> <input type="hidden"
			name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
</c:if>

