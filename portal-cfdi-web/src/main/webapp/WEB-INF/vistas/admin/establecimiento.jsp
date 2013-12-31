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
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Catalogo Establecimientos</h2>
			<blockquote>
				<p class="text-info">Ingresa los datos del Establecimiento.</p>
			</blockquote>
				<hr>
				<form:form id="establecimientoForm" action="" method="post"
					modelAttribute="establecimiento" cssClass="form-horizontal"
					role="form">
					<div class="form-group">
						<label for="txtClave" class="col-lg-5 control-label">Clave: </label>
						<div class="col-lg-2">
							<form:input path="clave" cssClass="form-control input-sm validate[required]" />
						</div>
					</div>
					<div class="form-group">
						<label for="txtNombre" class="col-lg-5 control-label">Nombre:</label>
						<div class="col-lg-2">
							<form:input path="nombre" cssClass="form-control input-sm validate[required]" />
						</div>
					</div>
					<div class="form-group">
						<label for="txtPassword" class="col-lg-5 control-label">Password:</label>
						<div class="col-lg-2">
							<form:input path="password" cssClass="form-control input-sm validate[required]" />
						</div>
					</div>
					<p class="form-grup text-center" >
						<a class="btn btn-primary" href="<c:url value="/guardarEstblecimiento" />"><span class="glyphicon glyphicon-floppy-disk"></span> Guardar</a>
					</p>
				</form:form>
		</div>
	</div>
</body>
</html>