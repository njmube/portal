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
			<h2>Actualizar usuario</h2>
			<blockquote>
				<p class="text-info">Ingresa los datos del usuario.</p>
			</blockquote>
			<hr>
			<c:url var="update" value="/guardarUsuario" />
			<c:if test="${error}">
				<div class="col-md-offset-3 col-md-6 alert alert-danger alert-dismissable alert-fixed auto-close">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					<spring:message code="messages.error.usuario.existente"/>
					<br><br> <strong>${messageError}</strong> 
				</div>
			</c:if>
			<form:form id="usuarioForm" action="${update}" method="post" modelAttribute="usuario" cssClass="form-horizontal"	role="form">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="white-panel form-horizontal">
						<h4 class="text-primary">Usuario</h4>
						<hr>
						<fieldset>
							<div class="form-group">
								<form:hidden path="id"/>
								<label for="txtNombre" class="col-lg-5 col-md-5 control-label">Usuario:</label>
								<div class="col-lg-5 col-md-5">
									<form:input path="usuario" cssClass="form-control input-sm validate[required] noUpper" id="txtNombre" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtPassword" class="col-lg-5 col-md-5 control-label">Password:</label>
								<div class="col-lg-5 col-md-5">
									<form:input  path="password" cssClass="form-control input-sm validate[required] noUpper" id="txtPassword" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtConfirmarPassword" class="col-lg-5 col-md-5 control-label">Confirmar password: </label>
								<div class="col-lg-5 col-md-5">
									<input value="${usuario.password }" class="form-control input-sm validate[required, equals[txtPassword]] noUpper" id="txtConfirmarPassword" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtSucursal" class="col-lg-5 col-md-5 control-label">Sucursal: </label>
								<div class="col-lg-5 col-md-5">
								<form:select  path="establecimiento.id" id="establecimiento" class="form-control input-sm validate[required]">
									<form:option value="">- Seleccione una opción -</form:option>
									<c:forEach items="${listaEstablecimientos}" var="establecimiento">
										<option value="${establecimiento.id}" ${usuario.establecimiento.id eq establecimiento.id  ? 'selected' : '' }>
										${establecimiento.nombre}</option>
									</c:forEach>
								</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="txtEstatus" class="col-lg-5 col-md-5 control-label">Estatus: </label>
								<div class="col-lg-5 col-md-5">
								<form:select  path="estatus" id="estatus" class="form-control input-sm validate[required]">
									<form:option value="">- Seleccione una opción -</form:option>
										<c:if test="${usuario.estatus.id eq 1}">
											<option value="ACTIVO" selected>ACTIVO</option>
											<option value="INACTIVO">INACTIVO</option>
										</c:if>
										<c:if test="${usuario.estatus.id eq 2}">
											<option value="ACTIVO">ACTIVO</option>
											<option value="INACTIVO" selected>INACTIVO</option>
										</c:if>
								</form:select>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
				<p class="form-grup text-center">
					<button id="guardar" type="submit" class="btn btn-primary">Actualizar <i class="fa fa-floppy-o"></i></button>
					<button id="cancelar" type="button" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></button>
				</p>
			</form:form>
		</div>
	</div>	

</body>
</html>