<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title><spring:message code="label.user.welcome.page.title" /></title>

<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/static/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">
<link href="/static/css/login-register.css" rel="stylesheet" />

<script src="/static/bower_components/jquery/dist/jquery.js" type="text/javascript"></script>
<script
	src="/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/static/js/login-register.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#regForm').submit(function(e) {
			var frm = $('#regForm');
			e.preventDefault();

			var data = {}
			var Form = this;

			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});

			$.each(this, function(i, v) {
				var input = $(v);
				data[input.attr("path")] = input.val();
				delete data["undefined"];
			});
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : frm.attr('method'),
				url : frm.attr('action'),
				dataType : 'json',
				data : JSON.stringify(data),
				success : function(data) {
					alert("You have successfully registered! Please log in!");
					window.location.href = "/login";
				},
				error : function(data, status, er) {
					alert("error: "+data+" status: "+status+" er:"+er);
				}
			});
		});
	});
</script>
</head>
<body>

	<!--
    If the user is anonymous (not logged in), show the login form
    and social sign in buttons.
-->
	<sec:authorize access="isAnonymous()">
		<header class="header">
			<div class="text-vertical-center">
				<h1><spring:message code="project.name" /></h1>
				<h3><spring:message code="project.tagline" /></h3>
				<br>
				<div class="container">
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-4">
							<a class="btn btn-default btn-lg" data-toggle="modal"
								href="javascript:void(0)" onclick="openLoginModal();"><spring:message code="label.user.login.submit.button" /></a>

							<a class="btn btn-success btn-lg" data-toggle="modal"
								href="javascript:void(0)" onclick="openRegisterModal();"><spring:message code="label.user.registration.submit.button" /></a>
						</div>
						<div class="col-sm-4"></div>
					</div>

					<div class="modal fade login" id="loginModal">
						<div class="modal-dialog login animated">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<div id="legend">
										<legend class="">
											<h4 class="modal-title">Log in</h4>
										</legend>
									</div>
								</div>
								<div class="modal-body">
									<div class="box">
										<div class="content">
											<!-- <div class="social">
												<a class="circle github" href="/auth/github"> <i
													class="fa fa-github fa-fw"></i>
												</a> <a id="google_login" class="circle google"
													href="/auth/google_oauth2"> <i
													class="fa fa-google-plus fa-fw"></i>
												</a> <a id="facebook_login" class="circle facebook"
													href="/auth/facebook"> <i class="fa fa-facebook fa-fw"></i>
												</a>
											</div>
											<div class="division">
												<div class="line l"></div>
												<span>or</span>
												<div class="line r"></div>
											</div> -->
											<div class="error"></div>
											<div class="form loginBox">

												<form method="post" action="/login/authenticate"
													accept-charset="UTF-8" role="form">
													<input id="email" class="form-control" type="text"
														placeholder="<spring:message code="label.user.welcome.page.login.form.placeholder.email" />" name="username"> <input
														id="password" class="form-control" type="password"
														placeholder="<spring:message code="label.user.welcome.page.login.form.placeholder.password" />" name="password">
													<button class="btn btn-lg btn-success btn-block"
														type="submit"><spring:message code="label.user.login.submit.button" /></button>
													<input type="hidden" name="${_csrf.parameterName}"
														value="${_csrf.token}" />
												</form>
											</div>
										</div>
									</div>
									<div class="box">

										<div class="content registerBox" style="widdisplay: none;">
											<div class="form">
												<form id="regForm" action="/user/register"
													commandName="userForm" method="post" enctype="utf8"
													role="form" class="form-horizontal">

													<fieldset>
														<div class="control-group">
															<label class="control-label" for="firstName"
																style="float: left"><spring:message code="label.user.welcome.page.registration.form.firstname" /></label>
															<div class="controls">
																<input path="firstName" class="form-control input-lg"
																	type="text" placeholder="<spring:message code="label.user.welcome.page.registration.form.placeholder.firstname" />" required="true" />
															</div>
														</div>
														<div class="control-group">
															<label class="control-label" for="lastname"
																style="float: left"><spring:message code="label.user.welcome.page.registration.form.lastname" /></label>
															<div class="controls">
																<input path="lastName" class="form-control" type="text"
																	placeholder="<spring:message code="label.user.welcome.page.registration.form.placeholder.lastname" />" required="true" />
															</div>
														</div>
														<div class="control-group">
															<label class="control-label" for="username"
																style="float: left"><spring:message code="label.user.welcome.page.registration.form.username" /></label>
															<div class="controls">
																<input path="username" class="form-control" type="text"
																	placeholder="<spring:message code="label.user.welcome.page.registration.form.placeholder.username" />" required="true" />
															</div>
														</div>
														<div class="control-group">
															<label class="control-label" for="email"
																style="float: left"><spring:message code="label.user.welcome.page.registration.form.email" /></label>
															<div class="controls">
																<input path="email" class="form-control" type="text"
																	placeholder="<spring:message code="label.user.welcome.page.registration.form.placeholder.email" />" required="true" />
															</div>
														</div>

														<div class="control-group">
															<label class="control-label" for="password"
																style="float: left"><spring:message code="label.user.welcome.page.registration.form.password" /></label>
															<div class="controls">
																<input path="password" class="form-control"
																	type="password" placeholder="<spring:message code="label.user.welcome.page.registration.form.placeholder.password" />"
																	required="true" />
															</div>
														</div>
														<div class="control-group">
															<label class="control-label" for="passwordVerification"
																style="float: left"><spring:message code="label.user.welcome.page.registration.form.password.confirm" /></label>
															<div class="controls">
																<input path="passwordVerification" class="form-control"
																	type="password" placeholder="<spring:message code="label.user.welcome.page.registration.form.placeholder.password.confirm" />"
																	required="true" />
															</div>
														</div>
														<input id="submitId" type="submit" value="<spring:message code="label.user.registration.submit.button" />"
															class="btn btn-lg btn-success btn-block" />
													</fieldset>
												</form>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<div class="forgot login-footer">
											<span><spring:message code="text.login.form.footer.looking.to" /> <a
												href="javascript: showRegisterForm();"><spring:message code="label.registration.form.footer.create.account.link" /></a>
												?
											</span>
										</div>
										<div class="forgot register-footer" style="display: none">
											<span><spring:message code="text.registration.form.footer.account.exist" />?</span> <a
												href="javascript: showLoginForm();"><spring:message code="label.user.login.form.footer.login.link" /></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Modal -->
					<div class="modal fade" id="myModal" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Modal Header</h4>
								</div>
								<div class="modal-body">
									<p>You have successfully registered! Please login.</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</header>


		<%-- <div class="container">
			<c:if test="${param.error eq 'bad_credentials'}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">&times;</button>
					<spring:message code="text.login.page.login.failed.error" />
				</div>
			</c:if>

			<form class="form-signin" action="/login/authenticate" method="POST"
				role="form">

				<h2 class="form-signin-heading">Please sign in</h2>

				<input type="text" class="form-control" placeholder="Email address"
					required="" autofocus="" name="username"> <input
					type="password" class="form-control" placeholder="Password"
					required="" name="password"> <label class="checkbox">

					<input type="checkbox" value="remember-me"> Remember me
				</label>


				<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
					in</button>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>



			<a class="btn btn-primary btn-lg"
				href='<c:url value="/user/register" />'><spring:message
					code="label.navigation.registration.link" /></a>

		</div> --%>

		<%-- <div class="panel panel-default">
			<div class="panel-body">
				<h2>
					<spring:message code="label.social.sign.in.title" />
				</h2>
				<div class="row social-button-row">
					<div class="col-lg-4">
						<a href="<c:url value='/auth/facebook' />"><button
								class="btn btn-facebook">
								<i class="icon-facebook"></i> |
								<spring:message code="label.facebook.sign.in.button" />
							</button></a>
					</div>
				</div>
				<div class="row social-button-row">
					<div class="col-lg-4">
						<a href="<c:url value='/auth/twitter' />"><button
								class="btn btn-twitter">
								<i class="icon-twitter"></i> |
								<spring:message code="label.twitter.sign.in.button" />
							</button></a>
					</div>
				</div>
			</div>
		</div> --%>
	</sec:authorize>
	<!--
    If the user is already authenticated, show a help message instead
    of the login form and social sign in buttons.
-->
	<sec:authorize access="isAuthenticated()">
		<p>
			<spring:message code="text.login.page.authenticated.user.help" />
		</p>
	</sec:authorize>
</body>
</html>