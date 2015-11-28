<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="modalBody" class="modal-body" style="display: none">
		<div class="container">
			<div class="row">
				<div class="col-md-1">
					<div id="videoBig">
						<video id="video" autoplay width="540px" height="420px"></video>
					</div>
					<div class="col-md-12">
						<a id="viewer" href="#" class="btn btn-primary"
							onclick="viewer(); return false;"><span
							class="glyphicon glyphicon-user"></span> Viewer</a> <a id="terminate"
							href="#" class="btn btn-danger" onclick="stop(); return false;"><span
							class="glyphicon glyphicon-stop"></span> Stop</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>