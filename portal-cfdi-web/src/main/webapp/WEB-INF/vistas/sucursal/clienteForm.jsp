<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Registrar Cliente</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2 class="text-primary">Registrar Cliente</h2>
			<blockquote>
				<p class="text-info">Ingresa la Información de Facturación.</p>
			</blockquote>
			<hr>
			<div class="well col-md-10 col-md-offset-1">
				<c:url var="altaUrl" value="/confirmarDatos"/>
				<form:form id="receptorForm" action="${altaUrl}" method="POST" modelAttribute="receptor" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label class="control-label col-lg-2">RFC: </label>
						<div class="col-lg-4">
							<form:input path="rfc" id="rfc" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">Nombre: </label>
						<div class="col-lg-6">
							<form:input path="nombre" id="nombre" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">Calle: </label>
						<div class="col-lg-10">
							<form:input path="domicilio.calle" id="calle" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">No. Interior: </label>
						<div class="col-lg-2">
							<form:input path="domicilio.noInterior" id="noInterior" cssClass="form-control input-sm"/>
						</div>
						<label class="control-label col-lg-4">No. Exterior: </label>
						<div class="col-lg-2">
							<form:input path="domicilio.noExterior" id="noExterior" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">Colonia: </label>
						<div class="col-lg-4">
							<form:input path="domicilio.colonia" id="colonia" cssClass="form-control input-sm"/>
						</div>
						<label class="control-label col-lg-2">Localidad: </label>
						<div class="col-lg-4">
							<form:input path="domicilio.localidad" id="localidad" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">Referencia: </label>
						<div class="col-lg-10">
							<form:input path="domicilio.referencia" id="referencia" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">Municipio: </label>
						<div class="col-lg-4">
							<form:input path="domicilio.municipio" id="municipio" cssClass="form-control input-sm"/>
						</div>
						<label class="control-label col-lg-2">Estado: </label>
						<div class="col-lg-4">
							<form:input path="domicilio.estado" id="estado" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-2">País: </label>
						<div class="col-lg-4">
							<form:select path="domicilio.pais" id="pais" cssClass="form-control input-sm">
								<form:option value="México">México</form:option>
							</form:select>
						</div>
						<label class="control-label col-lg-2">Código Postal: </label>
						<div class="col-lg-4">
							<form:input path="domicilio.codigoPostal" id="codigoPostal" cssClass="form-control input-sm"/>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="guardar" type="submit" class="btn btn-primary">Guardar <span class="glyphicon glyphicon-floppy-disk"></span></button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>