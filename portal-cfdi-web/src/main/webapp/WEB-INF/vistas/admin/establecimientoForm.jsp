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
</head>
<body>
	<div class="container">
		<div class="white-panel row">
			<h2>Catalogo Establecimientos</h2>
			<blockquote>
				<p class="text-info">Ingresa los datos del Establecimiento.</p>
			</blockquote>
			<hr>
			<form:form id="establecimientoForm" action="" method="post" modelAttribute="establecimiento" cssClass="form-horizontal"	role="form">
			<div class="row">
				<div class="col-md-5">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Cuenta</h4>
						<fieldset>
							<div class="form-group">
								<label for="txtClave" class="col-lg-5 control-label">Clave:	</label>
								<div class="col-lg-5"> 
									<input class="form-control input-sm validate[required]" id="txtClave" value="${clave } readonly="readonly""/>
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
									<input class="form-control input-sm validate[required]" id="txtPassword" type="password" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtPassword" class="col-lg-5 control-label">Confirmar password:</label>
								<div class="col-lg-5">
									<input class="form-control input-sm validate[required]" id="txtPassword" type="password" />
								</div>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col-md-7">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Dirección</h4>
						<fieldset>
							<div class="form-group">
								<label for="txtColonia" class="col-lg-2 control-label">Colonia: </label>
								<div class="col-lg-4"> 
									<input  Class="form-control input-sm validate[required]" id="txtColonia" />
								</div>
								<label for="txtCalle" class="col-lg-1 control-label">Calle: </label>
								<div class="col-lg-5"> 
									<input  Class="form-control input-sm validate[required]" id="txtCalle" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtNumeroInt" class="col-lg-2 control-label">Núm. Int. </label>
								<div class="col-lg-2"> 
									<input  Class="form-control input-sm validate[required]" id="txtNumeroInt" />
								</div>
								<label for="txtNumeroExt" class="col-lg-2 control-label">Núm Ext. </label>
								<div class="col-lg-2"> 
									<input  Class="form-control input-sm validate[required]" id="txtNumeroExt" />
								</div>
								<label for="txtcp" class="col-lg-1 control-label">C.P. </label>
								<div class="col-lg-2"> 
									<input  Class="form-control input-sm validate[required]" id="txtcp" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtMunicipio" class="col-lg-2 control-label">Municipio: </label>
								<div class="col-lg-4"> 
									<input  Class="form-control input-sm validate[required]" id="txtMunicipio" />
								</div>
								<label for="txtLocalidad" class="col-lg-2 control-label">Localidad: </label>
								<div class="col-lg-4"> 
									<input  Class="form-control input-sm validate[required]" id="txtLocalidad" />
								</div>
							</div>
							<div class="form-group">
								<label for="cmbPais" class="col-lg-2 control-label">País: </label>
								<div class="col-lg-4">
									<select class="validate[require	d] form-control-xsm" id="cmbPais">
										<option>Seleccione una opción</option>
									</select> 
								</div>
								<label for="txtEstado" class="col-lg-2 control-label">Estado: </label>
								<div class="col-lg-4">
									<select class="validate[require	d] form-control-xsm" id="cmbEstado">
										<option>Seleccione una opción</option>
									</select> 
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Datos adicionales</h4>
						<fieldset>
							<div class="form-group">
								 <label for="cmbTipoEstablecimiento" class="col-lg-1 control-label">Tipo : </label>
								 <div class="col-lg-2">
								 	<select class="validate[require	d] form-control-xsm" id="cmbTipoEstablecimiento">
								 		<option value="">Seleccione una opción</option>
								 	</select>
								 </div>
								 <label for="cmbEmisor" class="col-lg-2 control-label">Emisor: </label>
								 <div class="col-lg-2">
								 	<select class="validate[require	d] form-control-xsm" id="cmbEmisor">
								 		<option value="">Seleccione una opción</option>
								 	</select>
								 </div>
								 <label for="cmbRutaEstablecimiento" class="col-lg-2 control-label">Ruta : </label>
								 <div class="col-lg-2">
								 	<select class="validate[require	d] form-control-xsm" id="cmbRutaEstablecimiento">
								 		<option value="">Seleccione una opción</option>
								 	</select>
								 </div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>	
			<div class="row">
					<p class="form-grup text-center">
						<a class="btn btn-primary" 	href="<c:url value="/guardarEstablecimiento" />"><span
									class="glyphicon glyphicon-floppy-disk"></span> Guardar</a>
					</p>
			</div>
			</form:form>
		</div>
	</div>
</body>
</html>