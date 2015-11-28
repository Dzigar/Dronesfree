<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>${order.description}</title>
<style>
body {
	background-color: #CCC
}

#map-outer {
	height: 440px;
	padding: 20px;
	border: 2px solid #CCC;
	margin-bottom: 20px;
	background-color: #FFF
}

#map-container {
	height: 400px
}

@media all and (max-width: 991px) {
	#map-outer {
		height: 650px
	}
}
</style>

<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

<script src="/static/bower_components/jquery/dist/jquery.min.js"></script>
<script
	src="/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false"></script>

<script type="text/javascript">
	$(document).ready(initMap);

	function initMap() {

		var latlng = new google.maps.LatLng('${order.geolocation.latitude}',
				'${order.geolocation.longitude}');

		var contentString = "<div id='infowindow_content'>"
				+ "<p><h5>${order.description}</h5></p>"
				+ "<p><strong><spring:message code='label.order.page.infowindow.address' />:</strong> ${order.geolocation.address}<br>"
				+ "<spring:message code='label.order.page.infowindow.customer' />:<strong> <a href='/${order.user.username}'>${order.user.firstName} ${order.user.lastName} </a></strong> <div id='callButton' style='float:right;'><button type='button'class='btn btn-success'onclick='presenter(${order.id}); return false;'>Call</button></div></div>";

		var map = new google.maps.Map(document.getElementById('map-container'),
				{
					zoom : 16,
					center : latlng
				});

		var var_marker = new google.maps.Marker({
			position : latlng,
			map : map,
			title : 'Order № ${order.id}'
		});

		var infowindow = new google.maps.InfoWindow({
			content : contentString
		});

		google.maps.event.addListener(var_marker, 'click', function() {
			infowindow.open(map, var_marker);
			if('${order.user.username}' == '${pageContext.request.userPrincipal.name}') {
				document.getElementById('callButton').style.display = 'none';	
			};	
		});

		google.maps.event.addListener(map, "click", function() {
			infowindow.close();
		});
	}
</script>
<script type="text/javascript">
	function setVisibility(id, visibility) {
		document.getElementById(id).style.display = visibility;
		viewer();
	}
</script>
</head>
<body>
	<jsp:include page="include/header.jsp" />

	<div class="container">
		<div class="row">
			<div id="map-outer" class="col-md-12">
				<div id="address" class="col-md-4">
					<h2>
						<c:out value=" ${order.description}" />
					</h2>
					<address>
						<spring:message code="label.order.page.order.address" />: <strong> ${order.geolocation.address}</strong><br>
						<spring:message code="label.order.page.order.time" />: <strong><c:out value="${order.creationTime.dayOfMonth}.${order.creationTime.monthOfYear}.${order.creationTime.year}" /></strong><br> <spring:message code="label.order.page.order.customer" />:<strong><a
							href="/${order.user.username}"> <c:out
									value="${order.user.firstName} ${order.user.lastName}" />
						</a></strong>
					</address>
					<c:if
						test="${order.user.username ne pageContext.request.userPrincipal.name}">
						<button type="button" class="btn btn-success"
							onclick="presenter(${order.id}); return false;"><spring:message code="label.order.page.action.call" /></button>
					</c:if>
				</div>
				<div id="map-container" class="col-md-8"></div>
			</div>
			<!-- /map-outer -->
		</div>
		<!-- /row -->
	</div>
</body>
</html>