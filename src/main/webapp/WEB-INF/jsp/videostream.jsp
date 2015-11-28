<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>Order page</title>
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/static/bower_components/ekko-lightbox/dist/ekko-lightbox.min.css">
<link rel="stylesheet"
	href="/static/bower_components/demo-console/index.css">
<link rel="stylesheet" href="/static/css/style.css">

<script src="/static/bower_components/jquery/dist/jquery.min.js"></script>
<script
	src="/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script
	src="/static/bower_components/ekko-lightbox/dist/ekko-lightbox.min.js"></script>
<script src="/static/bower_components/adapter.js/adapter.js"></script>
<script src="/static/bower_components/demo-console/index.js"></script>

<script src="../js/kurento-utils.js"></script>
<script src="/static/js/index.js"></script>
</head>
<body>
	<br />

	<div class="container">
		<div class="row">
			<div class="col-md-5">
				<div class="row">
					<div class="col-md-12">

						<a id="call" href="#" class="btn btn-success"
							onclick="presenter(); return false;"><span
							class="glyphicon glyphicon-play"></span> Presenter </a> <a
							id="viewer" href="#" class="btn btn-primary"
							onclick="viewer(); return false;"><span
							class="glyphicon glyphicon-user"></span> Viewer</a> <a id="terminate"
							href="#" class="btn btn-danger" onclick="stop(); return false;"><span
							class="glyphicon glyphicon-stop"></span> Stop</a>
					</div>
				</div>
				<br /> <label class="control-label" for="console">Console</label><br>
				<br>
				<div id="console" class="democonsole">
					<ul></ul>
				</div>
			</div>
			<div class="col-md-7">
				<div id="videoBig">
					<video id="video" autoplay width="640px" height="480px"></video>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" value="${order.id}" id="orderId">
	<input type="hidden" value="${pageContext.request.userPrincipal.name}"
		id="username">
</body>
</html>
