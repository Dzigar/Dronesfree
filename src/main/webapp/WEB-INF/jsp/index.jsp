<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title><spring:message code="label.homepage.title" /></title>
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

<script src="/static/bower_components/jquery/dist/jquery.js" type="text/javascript"></script>
<script
	src="/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false"></script>
<script>
	var map;
	var infoWindow;
	var orders = new Array();
	<c:forEach items="${orders}" var="order">
	var order = new Object();
	order.id = '${order.id}';
	order.description = '${order.description}';
	order.address = "${order.geolocation.address}";
	order.user = '${order.user.username}';
	order.latitude = '${order.geolocation.latitude}';
	order.longitude = '${order.geolocation.longitude}';
	order.creationTime = new Date('${order.creationTime}');
	order.modificationTime = '${order.modificationTime}';
	orders.push(order);
	</c:forEach>

	function initialize() {
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom : 13,
			center : {
				lat : 49.231495,
				lng : 28.468204
			}
		});

		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				var pos = {
        			lat: position.coords.latitude,
        			lng: position.coords.longitude
      			};
				map.setCenter(pos);
			}, function showError(error) {
			switch(error.code) {
				case error.PERMISSION_DENIED:
					console.dir("User denied the request for Geolocation.");
					break;
				case error.POSITION_UNAVAILABLE:
					console.dir("Location information is unavailable.");
					break;
				case error.TIMEOUT:
					console.dir("The request to get user location timed out.");
					break;
				case error.UNKNOWN_ERROR:
					console.dir("An unknown error occurred.");
					break;
				default: 
					console.dir(error.code);
					break;
			} 
		});
		}

		infoWindow = new google.maps.InfoWindow;
		geocodeLatLng(map, orders);
	}

	function geocodeLatLng(map, orders) {
		for (var i = 0; i < orders.length; i++) {
			var order = orders[i];
			var latlng = new google.maps.LatLng(order.latitude, order.longitude);
			var username = order.user;
			var time = order.creationTime.toLocaleString("ru", options);
			var options = {
				era : 'long',
				year : 'numeric',
				month : 'long',
				day : 'numeric',
				weekday : 'long',
				timezone : 'UTC',
				hour : 'numeric',
				minute : 'numeric',
				second : 'numeric'
			};

			var contentString = '<div id="infowindow_content">'
				+ '<p><h5>' + order.description + '</h5>'
				+ '<p><strong><spring:message code="label.inowindow.address" />:</strong> '+order.address+'<br><spring:message code="label.inowindow.customer" />:<strong><a href="/'+order.user+'"> '+order.user+'</a></strong><strong><a style="float:right;" href="/order?id='+order.id+'"><spring:message code="label.navigation.inowindow.orderpage.link" /></a></strong><div id="callButton"><p><button type="button" class="btn btn-success" onclick="presenter('+i+'); return false;"><spring:message code="label.inowindow.action.call" /></button></div></div>';
			setMarker(map, latlng, contentString, i);
		}
	}

	function setMarker(map, latlng, contentString, i) {
	
		var marker = new google.maps.Marker({
			position : latlng,
			map : map
		});
		var onMarkerClick = function() {
			 if (infoWindow) infoWindow.close();
			infoWindow.setContent(contentString);
			infoWindow.open(map, marker);
			if(orders[i].user == '${pageContext.request.userPrincipal.name}') {
				document.getElementById('callButton').style.display = 'none';	
			};
		};
		google.maps.event.addListener(marker, 'click', onMarkerClick);

		google.maps.event.addListener(map, "click", function() {
			infoWindow.close();
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>

	<sec:authorize access="isAnonymous()">
		<%@include file="welcomePage.jsp"%>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<jsp:include page="include/header.jsp" />
		<div class="container">
			<!-- Main component for a primary marketing message or call to action -->
			<div style="width: 100%; height: 400px" id="map"></div>
			<br />
			<div>
				<a class="btn btn-info btn-lg" href="/order/create"><spring:message code="label.navigation.create.order.button" />:</a>
			</div>
			<br>
			<div class="list-group" style="width: 40%">
				<h3><spring:message code="text.homepage.orders" /></h3>

				<c:forEach var="order" items="${orders}">
					<a href="/order?id=${order.id}" class="list-group-item ">
						<h4 class="list-group-item-heading">
							<c:out value="${order.description}" />
						</h4>
						<p class="list-group-item-text">
							<c:out value="${order.geolocation.address}" />
							<br> <spring:message code="text.homepage.customer" />:
							<c:out value="${order.user.firstName} ${order.user.lastName}" />
							<div style="float:right"><c:out value="${order.creationTime.dayOfMonth}.${order.creationTime.monthOfYear}.${order.creationTime.year}" /></div>
						</p>
					</a>
				</c:forEach>
			</div>
		</div>

	</sec:authorize>
</body>
</html>
	<%-- Checkins:
						<c:forEach var="order" items="${orders}">
							<c:choose>
								<c:when test="${order.isBid eq false}">
									<li><a href="/order?id=${order.id}"> <c:out
												value="${order.id},${order.user.username}, ${order.location.address}" /><br />
											<c:out value="${order.description}" />
									</a></li>
								</c:when>
							</c:choose>
						</c:forEach> --%>



	<!-- <button id="checkinBtn">Checkin</button> -->
	<%-- <form:form action="/Dronesfree/find" method="POST"
			commandName="orderSeachForm">
			<form:input path="price" placeHolder="1" />
			<input type="submit" value="Find" />
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form> --%>

