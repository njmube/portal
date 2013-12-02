<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Sesión expirada</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>La sesión ha expirado :(</h2>
			<hr>
			<div class="well col-md-6 col-md-offset-3">
				<strong>Mensaje: </strong>
				<br><br>
				<p>Vuelva a iniciar sesión, redireccionando...</p>
			</div>
		</div>
	</div>

	<script src="<c:url value="/resources/js/login/sessionTimeout.js"/>"></script>
</body>
</html>