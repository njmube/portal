<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Catalogo usuarios</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		$(document.body).on("click","#cancelar",function() {
			location.href = contextPath + "/catalogoUsuarios";
		});
		
	});
</script>
<script src="<c:url value="/resources/js/commons/validatePassword.js"/>"></script>
<script src="<c:url value="/resources/js/admin/usuarioForm.js"/>"></script>
</head>
<body>
		<div class="container">
		<div class="white-panel row">
			<h2>Alta de  usuario</h2>
			<blockquote>
				<p class="text-info">Ingresa los datos del usuario.</p>
			</blockquote>
			<hr>
			<c:url var="update" value="/guardarUsuario" />
			<form:form id="usuarioForm" action="${update }" method="post" modelAttribute="usuario" cssClass="form-horizontal"	role="form">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Usuario</h4>
						<hr>
						<fieldset>
							<div class="form-group">
							<form:hidden path="id"/>
								<label for="txtNombre" class="col-lg-5 control-label">Usuario:</label>
								<div class="col-lg-5">
									<form:input path="usuario" cssClass="form-control input-sm validate[required]" id="txtNombre" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtPassword" class="col-lg-5 control-label">Password:</label>
								<div class="col-lg-5">
									<form:password  path="password" cssClass="form-control input-sm validate[required]" id="txtPassword" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtConfirmarPassword" class="col-lg-5 control-label">Confirmar password: </label>
								<div class="col-lg-5">
									<form:password  path="" cssClass="form-control input-sm validate[required, equals[txtPassword]]" id="txtConfirmarPassword" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtSucursal" class="col-lg-5 control-label">Sucursal: </label>
								<div class="col-lg-5">
								<form:select  path="establecimiento.id" id="establecimiento" class="form-control input-sm validate[required]">
									<form:option value="">- Seleccione una opción -</form:option>
									<c:forEach items="${listaEstablecimientos}" var="establecimiento">
										<option value="${establecimiento.id}"}>
										${establecimiento.nombre}</option>
									</c:forEach>
								</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="txtEstatus" class="col-lg-5 control-label">Estatus: </label>
								<div class="col-lg-5">
									<form:select  path="estatus" id="estatus" class="form-control input-sm validate[required]">
									<form:option value="">- Seleccione una opción -</form:option>
									<form:option value="ACTIVO">ACTIVO</form:option>
									<form:option value="INACTIVO">INACTIVO</form:option>
								</form:select>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
					<p class="form-grup text-center">
					<button id="guardar" type="submit" class="btn btn-primary">Guardar <i class="fa fa-floppy-o"></i></button>
					<button id="cancelar" type="button" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></button>
					</p>
			</div>
			</form:form>
		</div>
	</div>	

</body>
</html>