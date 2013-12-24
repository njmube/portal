<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<title>Fin de Sesión</title>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Ha cerrado sesión correctamente.</h2>
			<hr>
			<div class="well col-md-6 col-md-offset-3">
				<strong>Mensaje: </strong> <br> <br>
				<p class="text-danger"><strong>Vuelva a iniciar sesión, redireccionando...</strong></p>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			window.setTimeout(function() {
				url = "<c:url value='/login' />";
				window.location.replace(url);
			}, 2000);
		});
	</script>
</body>
</html>