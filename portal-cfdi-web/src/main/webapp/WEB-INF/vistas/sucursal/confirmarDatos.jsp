<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Confirmar Datos</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2 class="text-primary">Confirmación</h2>
			<hr>
			${receptor.rfc}
			${receptor.nombre}
			${receptor.domicilio.calle}
			${receptor.domicilio.noInterior}
		</div>
	</div>
</body>
</html>