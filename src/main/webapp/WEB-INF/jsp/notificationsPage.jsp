<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="include/head.jsp" />

	<br />
	<ul>
		<c:forEach items="${notifs}" var="notif">

			<c:choose>
				<c:when test="${notif.type eq 'CREATE_PROPOSAL'}">
					<li><a href="/${notif.proposal.user.username}">
							${notif.proposal.user.firstName} ${notif.proposal.user.lastName}</a>
						accepted your order! Click <a
						href="
				<c:url value='/order?id=${notif.proposal.order.id}' />">here</a>
						to get the order page.</li>
				</c:when>
				<c:when test="${notif.type eq 'ACCEPT_PROPOSAL'}">
					<li><a href="/${notif.proposal.order.user.username}">
							${notif.proposal.order.user.firstName} ${notif.proposal.order.user.lastName}</a> accept your proposal!
						Click <a
						href="
				<c:url value='/order?id=${notif.proposal.order.id}' />">here</a>
						to get the order page.
					</li>
				</c:when>
			</c:choose>

		</c:forEach>
	</ul>
</body>
</html>