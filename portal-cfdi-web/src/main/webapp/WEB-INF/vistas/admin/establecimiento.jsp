<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
				<hr>
				<form:form id="establecimientoForm" action="" method="post" modelAttribute="establecimiento" cssClass="form-horizontal" role="form">
					<label for="txtClave" class="col-lg-5 control-label">Clave:  </label>
					<input type="text" id="txtClave" > <br />
					<label for="txtNombre" class="col-lg-5 control-label">Nombre:  </label>
					<input type="text" id="txtNombre" > <br />
					<label for="txtPassword" class="col-lg-5 control-label">Password:  </label>
					<input type="text" id="txtPassword" > <br />
				</form:form>
			</blockquote>	
		</div>
	</div>
</body>
</html>