<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Ha ocurrido un error</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2 class="text-danger">Ha ocurrido un error:</h2>
			<hr>
			<div class="well col-md-6 col-md-offset-3">
				<p><strong>Mensaje: </strong></p>
				<p class="text-danger">${errMsg}</p>
				<hr>
				<p class="text-center">
					<a href="<c:url value="/facturaCorp" />" class="btn btn-warning btn-lg" role="button">Regresar
						<span class="glyphicon glyphicon-arrow-left"></span>
					</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>