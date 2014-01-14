<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Inicio de Sesión</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2 class="text-primary">
				Iniciar Sesión <i class="fa fa-user"></i>
			</h2>
			<hr>
			<c:if test="${error}">
				<div class="col-md-offset-3 col-md-6">
					<div class="alert alert-danger alert-dismissable auto-close">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<spring:message code="messages.login.failed"/>
						<br><br> <strong><spring:message code="messages.login.cause" />:</strong> 
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</div>
				</div>
			</c:if>
			<div class="well col-md-offset-3 col-md-6">
				<form id="loginForm" method="post" action="j_spring_security_check" role="form"
					class="form-horizontal">
					<div class="form-group">
						<label for="j_username" class="col-lg-4 control-label">Sucursal:
						</label>
						<div class="col-lg-8">
							<input id="j_username" name="j_username" type="text"
								class="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label for="j_password" class="col-lg-4 control-label">Password:
						</label>
						<div class="col-lg-8">
							<input id="j_password" name="j_password" type="password"
								class="form-control input-sm" />
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buttonLogin" type="submit" class="btn btn-primary">Iniciar
								Sesión <i class="fa fa-sign-in"></i></button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/login/login.js" />"></script>
</body>
</html>