<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Registrar Cliente</title>

<style type="text/css">
.input-xsm {
	height: 20px;
	padding: 5px 10px;
	font-size: 10px;
	line-height: 1.5;
	border-radius: 3px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#continuar").click(function() {
		$("#agregar").hide();
		$("input[type=text]").attr("readonly", true);
		$(this).hide();
		$("#corregir").show();
		$("#guardar").show();
	});
	
	$("#corregir").click(function() {
		$("#agregar").show();
		$("input[type=text]").attr("readonly", false);
		$("#continuar").show();
		$(this).hide();
		$("#guardar").hide();
	});
});
</script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2 class="text-primary">Registrar Cliente</h2>
			<blockquote>
				<p class="text-info">Ingresa la Información de Facturación.</p>
			</blockquote>
			<hr>
			<div class="well col-md-12 col-md-offset-0">
				<c:url var="altaUrl" value="/confirmarDatos"/>
				<form:form id="receptorForm" action="${altaUrl}" method="POST" modelAttribute="cliente" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label class="control-label col-lg-1">RFC: </label>
						<div class="col-lg-2">
							<form:input path="rfc" id="rfc" cssClass="form-control input-sm"/>
						</div>
						<label class="control-label col-lg-2">Nombre: </label>
						<div class="col-lg-6">
							<form:input path="nombre" id="nombre" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<div class="centered">
							<button id="agregar" type="button" class="btn btn-xs btn-warning">Agregar Dirección <span class="glyphicon glyphicon glyphicon-plus"></span> </button>
						</div>
					</div>
					<div class="white-panel row">
						<table class="table table-hover">
							<thead>
								<tr>
									<th><small>Calle</small></th>
									<th><small>No. Ext.</small></th>
									<th><small>No. Int.</small></th>								
									<th><small>País</small></th>
									<th><small>Estado</small></th>
									<th><small>Municipio</small></th>
									<th><small>Colonia</small></th>
									<th><small>Código Postal</small></th>
									<th><small>Referencia</small></th>
									<th><small>Localidades</small></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="200px"><form:input path="domicilios[0].calle" id="calle" cssClass="form-control input-xsm"/></td>
									<td width="65px"><form:input path="domicilios[0].noExterior" id="noExterior" cssClass="form-control input-xsm"/></td>
									<td width="65px"><form:input path="domicilios[0].noInterior" id="noExterior" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[0].estado.pais.id" id="pais" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[0].estado.id" id="estado" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[0].municipio" id="municipio" cssClass="form-control input-xsm"/></td>
									<td width="200px"><form:input path="domicilios[0].colonia" id="colonia" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[0].codigoPostal" id="codigoPostal" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[0].referencia" id="referencia" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[0].localidad" id="localidad" cssClass="form-control input-xsm"/></td>
								</tr>
								<tr>
									<td width="200px"><form:input path="domicilios[1].calle" id="calle" cssClass="form-control input-xsm"/></td>
									<td width="65px"><form:input path="domicilios[1].noExterior" id="noExterior" cssClass="form-control input-xsm"/></td>
									<td width="65px"><form:input path="domicilios[1].noInterior" id="noExterior" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[1].estado.pais.id" id="pais" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[1].estado.id" id="estado" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[1].municipio" id="municipio" cssClass="form-control input-xsm"/></td>
									<td width="200px"><form:input path="domicilios[1].colonia" id="colonia" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[1].codigoPostal" id="codigoPostal" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[1].referencia" id="referencia" cssClass="form-control input-xsm"/></td>
									<td><form:input path="domicilios[1].localidad" id="localidad" cssClass="form-control input-xsm"/></td>
								</tr>
							</tbody>
						</table>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="continuar" type="button" class="btn btn-success">Continuar <span class="glyphicon glyphicon-arrow-right"></span></button>
						</div>
					</div>
					<div class="form-group">
						<div class="centered">
							<button id="guardar" type="submit" class="btn btn-primary" style="display:none"><span class="glyphicon glyphicon-floppy-disk"></span> Guardar</button>
							<button id="corregir" type="button" class="btn btn-warning" style="display:none"><span class="glyphicon glyphicon-arrow-left"></span> Corregir</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>