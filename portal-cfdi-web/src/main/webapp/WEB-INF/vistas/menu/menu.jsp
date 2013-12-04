<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Facturación en Línea</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación en Línea - <span class="text-info">Sucursal ${sessionScope.sucursal.nombre}</span> <span class="label label-primary">@</span>
			</h2>
			<hr>

			<div class="well col-md-6 col-md-offset-3 centered">
				<p>
					<a href="#" class="btn btn-success" role="button">Generar
						Factura <span class="glyphicon glyphicon-arrow-right"></span>
					</a>
				</p>
				<hr>
<%-- 				<sec:authorize access="hasRole('ROLE_USER')"> --%>
<!-- 					<p> -->
<!-- 						<a href="#" class="btn btn-warning" role="button">Generar Nota -->
<!-- 							de Crédito <span class="glyphicon glyphicon-arrow-right"></span> -->
<!-- 						</a> -->
<!-- 					</p> -->
<!-- 					<hr> -->
<%-- 				</sec:authorize> --%>
				<p>
					<a href="#" class="btn btn-primary" role="button">Consultar Facturas
						<span class="glyphicon glyphicon-arrow-right"></span>
					</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>