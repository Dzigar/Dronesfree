<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title></title>

<script src="../js/kurento-utils.js"></script>
<script src="/static/js/index.js"></script>
<script src="/static/js/sockjs.js"></script>
<script src="/static/js/stomp.js"></script>
<script src="/static/bower_components/adapter.js/adapter.js"></script>

</head>
<body>
<nav class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<a class="navbar-brand" href="/"><spring:message code="project.name" /></a>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/${pageContext.request.userPrincipal.name}">${pageContext.request.userPrincipal.name}</a></li>
				<li><a href="/logout"><spring:message code="label.navigation.logout.link" /></a></li>
			</ul>
		</div>
	</div>
</nav>
	
	<!-- Modal -->
	<div class="modal fade" id="notificationModal" role="dialog"
	style="width: 100%">
	<div class="modal-dialog">	
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">X</button>
				<h4 id="modal-title" class="modal-title"></h4>
			</div>
			<div id="modalBody" class="modal-body" style="display: none">
				<center><video id="video" autoplay width="540px" height="420px"></video></center>	
				<p class="tpbutton btn-toolbar">		
					<button id="terminate" type="button" class="btn btn-danger" onclick="stop(); return false;" data-dismiss="modal"><span class="glyphicon glyphicon-stop"></span> <spring:message code="label.modal.action.stop" /></button>
					<button id="completed" type="button" class="btn btn-success" onclick="completeOrder()" data-dismiss="modal" style="display: none"><span
						class="glyphicon glyphicon-ok"></span> <spring:message code="label.modal.action.complete" /></button>
					</p>
				</div>
				<div class="modal-footer">
					<div id="ok" style="display: none">
						<button type="button" class="btn btn-success"
						onclick="viewer()"><spring:message code="label.modal.action.ok" /></button></div>
						<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.modal.action.close" /></button>
					</div>
				</div>
			</div>
		</div>
<script type="text/javascript">
	var userAuthName = '${pageContext.request.userPrincipal.name}';
</script>
</body>
</html>