<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<title>Ingresa tu RFC</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Facturación Electrónica<span class="text-info"> - Sucursal ${sessionScope.sucursal.nombre}</span> <span class="label label-primary">@</span></h2>
			<hr>
			<div class="well col-md-offset-2 col-md-8">
				<form:form id="receptorForm" action="muestraReceptor" method="GET" modelAttribute="receptor" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="rfc" class="col-lg-4 control-label">RFC: </label>
						<div class="col-lg-5">
							<form:input path="rfc" id="rfc" cssClass="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="nombre" class="col-lg-4 control-label">Nombre ó Razón Social</label>
						<div class="col-lg-8">
							<form:input path="nombre" id="nombre" cssClass="form-control"/>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buscarCliente" type="submit" class="btn btn-primary">Buscar</button>
						</div>
					</div>
				</form:form>
				<hr>
			</div>
		</div>
	</div>
</body>
</html>