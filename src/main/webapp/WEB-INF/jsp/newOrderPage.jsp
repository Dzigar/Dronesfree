<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="label.new.order.page.title" /></title>
<style type="text/css">
hr {
	-moz-border-bottom-colors: none;
	-moz-border-image: none;
	-moz-border-left-colors: none;
	-moz-border-right-colors: none;
	-moz-border-top-colors: none;
	border-color: #EEEEEE -moz-use-text-color #FFFFFF;
	border-style: solid none;
	border-width: 1px 0;
	margin: 18px 0;
}

#mapCanvas {
	width: 700px;
	height: 400px;
	float: left;
	border-style: solid;
	border-color: #BDBDBD;
}

#infoPanel {
	float: left;
	margin-left: 10px;
}

#infoPanel div {
	margin-bottom: 5px;
}
</style>

<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

<script type="text/javascript" src="/static/bower_components/jquery/dist/jquery.js" ></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false"></script>
<script type="text/javascript"
	src="/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/js/create_order.js"></script>

</head>
<body>
	<%@include file="include/header.jsp"%>

	<div class="container" style="background: #F2F2F2">
		<br>
		<div class="row">
			<div id="map-outer" class="col-md-12">
				<div class="col-md-4">

					<div class="form-group">
						<div class="form-inline">
							<input class="form-control" id="address1" type="text" value=""
								required placeholder="<spring:message code="label.new.order.page.order.geocode.placeholder" />">
							<input class="btn btn-default" id="codeAddress" type="button"
								value="<spring:message code="label.new.order.page.action.geocode" />">
						</div>
					</div>

					<hr>
					<form:form action="/order/create" commandName="newOrderForm"
						method="POST" acceptCharset="utf-8">
						<div class="form-group">
							<input class="form-control" type="text" name="description"
								required placeholder="<spring:message code="label.new.order.page.order.description.placeholder" />" />
						</div>
						<div class="form-group">
							<input class="form-control" id="latitude" type="text"
								name="latitude" required />
						</div>
						<div class="form-group">
							<input class="form-control" id="longitude" type="text"
								name="longitude" required />
						</div>
						<div class="form-group">
							<input class="form-control" id="address" type="text"
								name="address" required />
						</div>

						<input type="submit" value="<spring:message code="label.new.order.page.action.add.order" />" class="btn btn-success" />

						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form:form>
				</div>
				<div id="mapCanvas" class="col-md-8"></div>
			</div>
			<!-- /map-outer -->
		</div>
		<br>
	</div>
</body>
</html>