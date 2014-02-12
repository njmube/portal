<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Corregir Datos de Cliente</title>

<script src="<c:url value="/resources/js/commons/direccionFunctions.js"/>"></script>
<script src="<c:url value="/resources/js/sucursal/clienteForm.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/clienteForm.css"/>">

<script type="text/javascript">
	$(document).ready(function() {
		$(document.body).on("change",".pais",function() {
			var tr = $(this).parent().parent();
			$(tr).find("input:hidden[id=paisOculto]").val($("option:selected", this).val());
		});
		$(document.body).on("change",".estado",function() {
			var tr = $(this).parent().parent();
			$(tr).find("input:hidden[id=estadoOculto]").val($("option:selected", this).val());
		});
		
		$(document.body).on("click","#cancelar",function() {
			location.href = contextPath + "/confirmarDatos/${cliente.id}";
		});
		
		var rfcExtranjeros = "${rfcExtranjeros}";
		
		$(document.body).on('change',"#pais0",function() {
			if($("option:selected", this).val() > 1){
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
				<c:url var="altaUrl" value="/confirmarDatos/clienteCorregir"/>
				<form:form id="clienteCorregirForm" action="${altaUrl}" method="POST" modelAttribute="clienteCorregir" cssClass="form-horizontal" role="form">
					<div class="row">
    					<div class="text-center">
    						<div class="form-group">
    							<c:choose >
    								<c:when test="${clienteCorregir.tipoPersona.id eq 1}">
										<input type="hidden" id="tipoPersona" name="tipoPersona.id" value="${clienteCorregir.tipoPersona.id}"/>
	    								<label class="checkbox-inline">
	    									<strong>Persona Fisica:</strong> <input type="radio" id="personaFisica" name="personaFisica" checked="checked">
										</label>
										<label class="checkbox-inline">
										  	<strong>Persona Moral:</strong> <input type="radio" id="personaMoral" name="personaMoral">
										</label>
    								</c:when>
	    							<c:when test="${clienteCorregir.tipoPersona.id eq 2}">
	    								<input type="hidden" id="tipoPersona" name="tipoPersona.id" value="${clienteCorregir.tipoPersona.id}"/>
	    								<label class="checkbox-inline">
	    									Persona Fisica: <input type="radio" id="personaFisica" name="personaFisica">
										</label>
										<label class="checkbox-inline">
										  	Persona Moral: <input type="radio" id="personaMoral" name="personaMoral" checked="checked">
										</label>
	    							</c:when>
    							</c:choose>
    						</div>
    					</div>
					</div>
					<div class="form-group">
						<form:hidden path="id" id="idCliente"/>
						<label class="control-label col-lg-1 col-md-1">RFC: </label>
						<div class="col-lg-2 col-md-2">
							<c:choose>
								<c:when test="${clienteCorregir.tipoPersona.id eq 1}">
									<form:input path="rfc" id="rfc" cssClass="form-control input-sm validate[required, custom[rfcFisica]]"/>
								</c:when>
								<c:when test="${clienteCorregir.tipoPersona.id eq 2}">
									<form:input path="rfc" id="rfc" cssClass="form-control input-sm validate[required, custom[rfcMoral]]"/>
								</c:when>
							</c:choose>
						</div>
						<label class="control-label col-lg-1 col-md-1">Nombre: </label>
						<div class="col-lg-4 col-md-4">
							<form:input path="nombre" id="nombre" cssClass="form-control input-sm validate[required]"/>
						</div>
						<label class="control-label col-lg-1 col-md-1">Email: </label>
						<div class="col-lg-3 col-md-3">
							<form:input path="email" id="email" cssClass="form-control input-sm validate[custom[email]] noUpper"/>
						</div>
					</div>
					<p class="text-center">
						<button id="agregarCorregir" type="button" class="btn btn-xs btn-warning">Agregar Dirección <i class="fa fa-plus"></i> </button>
					</p>
					<div class="white-panel row">
						<table class="table table-hover" id="tblDireccion">
							<thead>
								<tr>
									<th><small>*Calle</small></th>
									<th><small>*No. Ext.</small></th>
									<th><small>No. Int.</small></th>								
									<th><small>*País</small></th>
									<th><small>*Estado</small></th>
									<th><small>*Delegación/Municipio</small></th>
									<th><small>*Colonia</small></th>
									<th><small>*C.P.</small></th>
									<th><small>Estatus</small></th>
<!-- 									<th><small>Referencia</small></th> -->
<!-- 									<th><small>Localidad</small></th> -->
									<th><small></small></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty clienteCorregir.domicilios}">
										<c:forEach items="${clienteCorregir.domicilios}" var="domicilio" varStatus="theCount">
											<tr>
												<form:hidden path="domicilios[${theCount.index}].id" id="id${theCount.index}"/>
												<td width="200px"><form:input path="domicilios[${theCount.index}].calle" id="calle${theCount.index}" cssClass="form-control input-xsm validate[required] calle"/></td>
												<td width="65px"><form:input path="domicilios[${theCount.index}].noExterior" id="noExterior${theCount.index}" cssClass="form-control input-xsm validate[required, integer, minSize[1]] noExterior"/></td>
												<td width="65px"><form:input path="domicilios[${theCount.index}].noInterior" id="noInteriorr${theCount.index}" cssClass="form-control input-xsm validate[integer] noInterior"/></td>
												<td width="100px">
													<form:hidden path="domicilios[${theCount.index}].estado.pais.id" id="paisOculto"/>
												  	<select class="form-control-xsm validate[required] pais" id="pais${theCount.index}">
												  		<option value="">- Seleccione una opción -</option>
												    	<option value="${domicilio.estado.pais.id}" selected>
												    		${domicilio.estado.pais.nombre}</option>
			  										</select>
												</td>
												<td width="100px">
													<form:hidden path="domicilios[${theCount.index}].estado.id" id="estadoOculto"/>
													<select class="form-control-xsm validate[required] estado" id="estado${theCount.index}" >
												  		<option value="">- Seleccione una opción -</option>
												  		<c:forEach items="${listaEstados}" var="estado">
												  			<option value="${estado.id}" ${domicilio.estado.id eq estado.id ? 'selected' : ''}>
												  				${estado.nombre}</option>
												  		</c:forEach>									  		
			  										</select>
												</td>
												<td><form:input path="domicilios[${theCount.index}].municipio" id="municipio${theCount.index}" cssClass="form-control input-xsm validate[required] municipio"/></td>
												<td width="200px"><form:input  path="domicilios[${theCount.index}].colonia" id="colonia${theCount.index}" cssClass="form-control input-xsm validate[required] colonia"/></td>
												<td width="70px"><form:input  path="domicilios[${theCount.index}].codigoPostal" id="codigoPostal${theCount.index}" 
													cssClass="form-control input-xsm validate[required, custom[onlyNumberSp], maxSize[5], minSize[5] codigoPostal"/></td>
												<c:choose>
													<c:when test="${theCount.index > 0 }">
														<c:if test="${domicilio.estatus.id eq 1}">														
															<td align="center">
																<input type="hidden" value="${domicilio.estatus.nombre}" name="domicilios[${theCount.index}].estatus" id="hdn_estatus"/>
																<input type="checkbox" id="estatus${theCount.index}" class="checkbox estatus" checked="true"/>
															</td>
														</c:if>
														<c:if test="${domicilio.estatus.id eq 2}">
															<td align="center">
																<input type="hidden" value="${domicilio.estatus.nombre}" name="domicilios[${theCount.index}].estatus" id="hdn_estatus"/>
																<input type="checkbox" id="estatus${theCount.index}" class="checkbox estatus"/>
															</td>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if test="${domicilio.estatus.id eq 1}">
															<td align="center">
																<input type="hidden" value="${domicilio.estatus.nombre}" name="domicilios[${theCount.index}].estatus" id="hdn_estatus"/>																
																<i class="fa fa-check-square-o"></i>
															</td>
														</c:if>
														<c:if test="${domicilio.estatus.id eq 2}">
															<td align="center">
																<input type="hidden" value="${domicilio.estatus.nombre}" name="domicilios[${theCount.index}].estatus" id="hdn_estatus"/>																
																<i class="fa fa-times"></i>															
															</td>
														</c:if>
													</c:otherwise>
												</c:choose>
											</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td width="200px"><input type="text" name="domicilios[0].calle" id="calle0" class="form-control input-xsm validate[required] calle"/></td>
												<td width="65px"><input type="text" name="domicilios[0].noExterior" id="noExterior0" class="form-control input-xsm validate[required, integer, minSize[1]] noExterior"/></td>
												<td width="65px"><input type="text" name="domicilios[0].noInterior" id="noInteriorr0" class="form-control input-xsm validate[integer] noInterior"/></td>
												<td width="100px">
												  	<select class="form-control-xsm validate[required] pais" id="pais0" name="">
												  		<option value="">- Seleccione una opción -</option>
												    	<option value="${domicilio.estado.pais.id}" selected>
												    		${domicilio.estado.pais.nombre}</option>
												    </select>
												</td>
												<td width="100px">
													<input type="hidden" name="domicilios[0].estado.id" id="estadoOculto"/>
													<select class="form-control-xsm validate[required] estado" id="estado0" >
												  		<option value="">- Seleccione una opción -</option>									  		
													</select>
												</td>
												<td><input type="text" name="domicilios[0].municipio" id="municipio0" class="form-control input-xsm validate[required] municipio"/></td>
												<td width="200px"><input type="text" name="domicilios[0].colonia0" id="colonia" class="form-control input-xsm validate[required] colonia"/></td>
												<td width="70px"><input type="text" name="domicilios[0].codigoPostal0" id="codigoPostal" 
													class="form-control input-xsm validate[required, custom[onlyNumberSp], maxSize[5], minSize[5]] codigoPostal"/></td>
												<td align="center"><form:checkbox value="1" path="domicilios[0].estatus" id="estatus" cssClass="checkbox estatus" checked="true"/></td>
<!-- 												<td><input type="text" name="domicilios[0].referencia" id="referencia" class="form-control input-xsm"/></td> -->
<!-- 												<td><input type="text" name="domicilios[0].localidad" id="localidad" class="form-control input-xsm"/></td> -->
											</tr>
										</c:otherwise>
									</c:choose>
							</tbody>
						</table>
					</div>
					<hr>
					<p class="text-center" id="botones1">
						<button id="continuar" type="button" class="btn btn-success">Continuar <i class="fa fa-arrow-right"></i></button>
						<button id="cancelar" type="button" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></button>						
					</p>
					<p class="text-center" id="botones2" style="display:none">
						<button id="actualizar" type="submit" class="btn btn-primary">Actualizar <i class="fa fa-refresh"></i></button>
						<button id="corregir" type="button" class="btn btn-warning">Modificar <i class="fa fa-arrow-left"></i></button>
						<button id="cancelar" type="button" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></button>
					</p>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
