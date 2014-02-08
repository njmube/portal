<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>Registrar Cliente</title>

<script src="<c:url value="/resources/js/commons/direccionFunctions.js"/>"></script>
<script src="<c:url value="/resources/js/sucursal/clienteForm.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/clienteForm.css"/>">
<script type="text/javascript">
$(document).ready(function() {
	var rfcExtranjeros = "${rfcExtranjeros}";
	
	$(document.body).on('change',"#pais",function() {
		if($("option:selected", this).val() > 1) {
			if($("#personaFisica").is(":checked", true)) {
				$("#rfc").removeClass("validate[required, custom[rfcFisica]]");
				$("#rfc").addClass("validate[required, custom[rfc]]");
			} else if ($("#personaMoral").is(":checked", true)) {
				$("#rfc").removeClass("validate[required, custom[rfcMoral]]");
				$("#rfc").addClass("validate[required, custom[rfc]]");
			}			
			$("#rfc").val(rfcExtranjeros);
			$("#rfc").attr('readonly', true);
		} else {
			if($("#rfc").val() === rfcExtranjeros) {
				$("#rfc").val("");
				if($("#personaFisica").is(":checked", true)) {
					$("#rfc").removeClass("validate[required, custom[rfc]]");
					$("#rfc").addClass("validate[required, custom[rfcFisica]]");
				} else if ($("#personaMoral").is(":checked", true)) {
					$("#rfc").removeClass("validate[required, custom[rfc]]");
					$("#rfc").addClass("validate[required, custom[rfcMoral]]");
				}
			}
			$("#rfc").attr('readonly', false);
		}
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
			<div class="well">
				<sec:authorize access="hasAnyRole('ROLE_SUC')">
					<c:url var="altaUrl" value="/confirmarDatos/clienteForm"/>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<c:url var="altaUrl" value="/portal/cfdi/confirmarDatos/clienteForm"/>
				</sec:authorize>
				<form:form id="clienteForm" action="${altaUrl}" method="POST" modelAttribute="cliente" cssClass="form-horizontal" role="form">
					<div class="row">
						<c:if test="${errorSave}">
							<div class="col-md-offset-2 col-md-8">
								<p>
									<div class="alert alert-danger alert-dismissable">
										<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
										Error al guardar
										<br><br> <strong>${errorMessage}</strong> 
									</div>
								</p>
							</div>
						</c:if>
					</div>
					<div class="row">
    					<div class="text-center">
    						<div class="form-group">
								<input type="hidden" id="tipoPersona" name="tipoPersona.id" value="1"/>
   								<label class="checkbox-inline">
   									<strong>Persona Fisica:</strong> <input type="radio" id="personaFisica" name="personaFisica" checked="checked">
								</label>
								<label class="checkbox-inline">
								  	<strong>Persona Moral:</strong> <input type="radio" id="personaMoral" name="personaMoral">
								</label>
    						</div>
    					</div>
					</div>
					<div class="form-group">
						<label class="control-label col-lg-1">*RFC: </label>
						<div class="col-lg-2">
							<form:input path="rfc" id="rfc" cssClass="form-control input-sm validate[required, custom[rfcFisica]]"/>
						</div>
						<label class="control-label col-lg-1">*Nombre: </label>
						<div class="col-lg-4">
							<form:input path="nombre" id="nombre" cssClass="form-control input-sm validate[required]"/>
						</div>
						<label class="control-label col-lg-1">Email: </label>
						<div class="col-lg-3">
							<form:input path="email" id="email" cssClass="form-control input-sm validate[custom[email]] noUpper"/>
						</div>
					</div>
					<p class="text-center">
						<button id="agregar" type="button" class="btn btn-xs btn-warning">Agregar Dirección <i class="fa fa-plus"></i> </button>
					</p>
					<div class="white-panel row">
						<table class="table table-hover" id="tblDireccion">
							<thead>
								<tr>
									<th><small>* Calle</small></th>
									<th><small>* No. Ext.</small></th>
									<th><small>No. Int.</small></th>								
									<th><small>* País</small></th>
									<th><small>* Estado</small></th>
									<th><small>* Municipio</small></th>
									<th><small>* Colonia</small></th>
									<th><small>* C.P.</small></th>
									<th><small>	Estatus</small></th>
<!-- 									<th><small>Referencia</small></th> -->
<!-- 									<th><small>Localidad</small></th> -->
									<th><small></small></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="200px"><form:input path="domicilios[0].calle" id="calle0" 
										cssClass="form-control input-xsm validate[required] calle"/></td>
									<td width="68px"><form:input path="domicilios[0].noExterior" id="noExterior0" cssClass="form-control input-xsm validate[required, integer, minSize[1]] noExterior"/></td>
									<td width="68px"><form:input path="domicilios[0].noInterior" id="noInterior0" cssClass="form-control input-xsm validate[integer] noInterior" /></td>
									<td width="100px">
									  	<select class="validate[required] form-control-xsm pais" id="pais0" name="domicilios[0].estado.pais.id">
									  		<option value="">- Seleccione una opción -</option>
									  		<c:forEach items="${listaPaises}" var="pais">
									    		<option value="${pais.id}">${pais.nombre}</option>
									  		</c:forEach>
  										</select>
									</td>
									<td width="100px">
<%-- 										<form:hidden path="domicilios[0].estado.id" id="estadoOculto"/> --%>
										<select class="validate[required] form-control-xsm estado" id="estado0" name="domicilios[0].estado.id">
									  		<option value="">- Seleccione una opción -</option>									  		
  										</select>
									</td>
									<td><form:input path="domicilios[0].municipio" id="municipio0" cssClass="form-control input-xsm validate[required] municipio" /></td>
									<td width="200px"><form:input path="domicilios[0].colonia" id="colonia0" cssClass="form-control input-xsm validate[required] colonia" /></td>
									<td width="70px"><form:input path="domicilios[0].codigoPostal" id="codigoPostal0" 
										cssClass="form-control input-xsm validate[required, custom[onlyNumberSp], maxSize[5], minSize[5]] codigoPostal"/></td>
									<td align="center">
										<form:hidden value="ACTIVO" path="domicilios[0].estatus" id="estatus0"/>
										<i class="fa fa-check-square-o"></i>
									</td>
<%-- 									<td><form:input path="domicilios[0].referencia" id="referencia" cssClass="form-control input-xsm" /></td> --%>
<%-- 									<td><form:input path="domicilios[0].localidad" id="localidad" cssClass="form-control input-xsm" /></td> --%>
								</tr>
							</tbody>
						</table>
					</div>
					<hr>
					<p class="text-center" id="botones1">
						<button id="continuar" type="button" class="btn btn-success">Continuar <i class="fa fa-arrow-right"></i></button>
						<sec:authorize access="hasAnyRole('ROLE_SUC')">
							<a href="<c:url value="/buscaRfc"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a href="<c:url value="/portal/cfdi/buscaRfc"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</sec:authorize>
					</p>
					<p class="text-center" id="botones2" style="display:none">
						<button id="guardar" type="submit" class="btn btn-primary">Guardar <i class="fa fa-floppy-o"></i></button>
						<button id="corregir" type="button" class="btn btn-warning">Modificar <i class="fa fa-arrow-left"></i></button>
						<sec:authorize access="hasAnyRole('ROLE_SUC')">
							<a href="<c:url value="/buscaRfc"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a href="<c:url value="/portal/cfdi/buscaRfc"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</sec:authorize>
					</p>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>