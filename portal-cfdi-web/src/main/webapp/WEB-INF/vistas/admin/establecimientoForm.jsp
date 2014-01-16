<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Catalogo Establecimiento</title>

<script src="<c:url value="/resources/js/commons/direccionFunctions.js"/>"></script>
<script src="<c:url value="/resources/js/admin/establecimientoForm.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$(document.body).on("click","#cancelar",function() {
			location.href = contextPath + "/catalogoEstablecimiento";
		});
		
	});
</script>

</head>
<body>
	<div class="container">
		<div class="white-panel row">
			<h2>Actualizar Establecimiento</h2>
			<blockquote>
				<p class="text-info">Ingresa los datos del Establecimiento.</p>
			</blockquote>
			<hr>
			<c:url var="update" value="/guardarEstablecimiento" />
			<form:form id="establecimientoForm" action="${update }" method="post" modelAttribute="establecimiento" cssClass="form-horizontal"	role="form">
						<div class="row">
				<div class="col-md-5">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Sucursal</h4>
						<hr>
						<fieldset>
							<div class="form-group">
								<label for="txtClave" class="col-lg-5 control-label">Clave:	</label>
								<div class="col-lg-5"> 
									<form:hidden path="id"/>
									<form:input path="clave" cssClass="form-control input-sm validate[required]" id="txtClave" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtNombre" class="col-lg-5 control-label">Nombre:</label>
								<div class="col-lg-5">
									<form:input path="nombre" cssClass="form-control input-sm validate[required]" id="txtNombre" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtPassword" class="col-lg-5 control-label">Password:</label>
								<div class="col-lg-5">
									<form:input  path="password" cssClass="form-control input-sm validate[required]" id="txtPassword" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtConfirmarPassword" class="col-lg-5 control-label">Confirmar password: </label>
								<div class="col-lg-5">
									<input value="${establecimiento.password }" class="form-control input-sm validate[required, equals[txtPassword]]" id="txtConfirmarPassword" />
								</div>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col-md-7">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Ubicación de documentos</h4>
						<hr>
						<fieldset>
							<div class="form-group">
								<label for="txtRutaRepo" class="col-lg-3 control-label">Ruta Repositorio: </label>
								<div class="col-lg-9">
									<form:hidden path="rutaRepositorio.id"/>
									<form:input path="rutaRepositorio.rutaRepositorio" cssClass="form-control input-sm validate[required]" id="txtRutaRepo"/>
								</div>
							</div>
							<div class="form-group">
								<label for="txtOut" class="col-lg-3 control-label">Ruta Salida: </label>
								<div class="col-lg-9">
									<form:input path="rutaRepositorio.rutaRepoOut" cssClass="form-control input-sm validate[required]" id="txtOut"/>
								</div>
							</div>
							<div class="form-group">
								<label for="txtIn" class="col-lg-3 control-label">Ruta Entrada: </label>
								<div class="col-lg-9">
									<form:input path="rutaRepositorio.rutaRepoIn" cssClass="form-control input-sm validate[required]" id="txtIn"/>
								</div>
							</div>
							<c:choose>
								<c:when test="${establecimiento.tipoEstablecimiento.id eq 1 }">
									<div class="form-group">
										<label for="txtRutaProc" class="col-lg-3 control-label">Ruta Procesado: </label>
										<div class="col-lg-9">
											<form:input path="rutaRepositorio.rutaRepoInProc" cssClass="form-control input-sm " id="txtRutaProc"	/>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<br><br>
									<div style="padding: 3px"></div>
								</c:otherwise>
							</c:choose>
						</fieldset>
					</div>
				</div>
			</div>
			
			<div class="">
			<div class="col-md-12">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Dirección</h4>
						<hr>
						<fieldset>
							<div class="form-group">
								<label for="txtCalle" class="col-lg-1 control-label">Calle: </label>
								<div class="col-lg-3"> 
									<form:input path="domicilio.calle"  cssClass="form-control input-sm validate[required]" id="txtCalle" />
								</div>
								<label for="txtColonia" class="col-lg-1 control-label">Colonia: </label>
								<div class="col-lg-3"> 
								<form:hidden path="domicilio.id"/>
									<form:input path="domicilio.colonia"  cssClass="form-control input-sm validate[required]" id="txtColonia" />
								</div>
								<label for="txtLocalidad" class="col-lg-1 control-label">Localidad: </label>
								<div class="col-lg-3"> 
									<form:input path="domicilio.localidad"  cssClass="form-control input-sm validate[required]" id="txtLocalidad" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtNumeroInt" class="col-lg-1 control-label">Núm. Int. </label>
								<div class="col-lg-3"> 
									<form:input path="domicilio.noInterior"  cssClass="form-control input-sm " id="txtNumeroInt" />
								</div>
								<label for="txtNumeroExt" class="col-lg-1 control-label">Núm Ext. </label>
								<div class="col-lg-3"> 
									<form:input path="domicilio.noExterior"  cssClass="form-control input-sm validate[required]" id="txtNumeroExt" />
								</div>
								<label for="txtcp" class="col-lg-1 control-label">C.P. </label>
								<div class="col-lg-3"> 
									<form:input path="domicilio.codigoPostal" cssClass="form-control input-sm validate[required]" id="txtcp" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtMunicipio" class="col-lg-1 control-label">Municipio: </label>
								<div class="col-lg-3"> 
									<form:input path="domicilio.municipio"  cssClass="form-control input-sm validate[required]" id="txtMunicipio" />
								</div>
								<label for="cmbPais" class="col-lg-1 control-label">País: </label>
								<div class="col-lg-3">
									<select class="form-control input-sm validate[required]" id="pais">
									  		<option value="">- Seleccione una opción -</option>
									  		<c:forEach items="${listaPaises}" var="pais">
									    		<option value="${pais.id}" ${establecimiento.domicilio.estado.pais.id eq pais.id ? 'selected' : ''}>${pais.nombre}</option>
									  		</c:forEach>
  										</select>
								</div>
								<label for="txtEstado" class="col-lg-1 control-label">Estado: </label>
								<div class="col-lg-3">
								
								<form:select  path="domicilio.estado.id" id="estado" class="form-control input-sm validate[required]">
									<form:option value="">- Seleccione una opción -</form:option>
									<form:options items="${listaEstados }"  itemValue="id"></form:options>
								</form:select>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
					<p class="form-grup text-center">
					<button id="guardar" type="submit" class="btn btn-primary">Actualizar <i class="fa fa-floppy-o"></i></button>
					<button id="cancelar" type="button" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></button>
					</p>
			</div>
			</form:form>
		</div>
	</div>
</body>
</html>