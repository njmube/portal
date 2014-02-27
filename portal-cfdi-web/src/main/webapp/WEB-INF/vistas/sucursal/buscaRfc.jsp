<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Ingresa tu RFC</title>
<script type="text/javascript" src="<c:url value="/resources/js/sucursal/clientes.js"/>"></script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Facturación Electrónica<span class="text-info"> - ${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span> <span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa el RFC ó Nombre del Cliente.</p>
			</blockquote>
			<hr>
			<sec:authorize access="hasRole('ROLE_SUC')">
				<c:url var="urlCancelar" value="/buscaTicket"/>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<c:url var="urlCancelar" value="/menu"/>
			</sec:authorize>
			<div class="well col-md-offset-2 col-md-8">
				<form:form id="receptorForm" action="#" method="get" modelAttribute="cliente" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="rfc" class="col-lg-4 col-md-4 control-label">RFC: </label>
						<div class="col-lg-5 col-md-5">
							<form:input path="rfc" id="rfc" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label for="nombre" class="col-lg-4 col-md-4 control-label">Nombre ó Razón Social</label>
						<div class="col-lg-8 col-md-8">
							<form:input path="nombre" id="nombre" cssClass="form-control input-sm"/>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
<%-- 						href="<c:url value="/clienteForm"/>" --%>
							<button id="crearCliente" type="button" class="btn btn-warning">Nuevo Cliente <i class="fa fa-plus"></i></button>
							<button id="buscarCliente" type="button" class="btn btn-primary">Buscar <i class="fa fa-search"></i></button>
							<a id="cancelar" href="${urlCancelar}" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</div>
					</div>
				</form:form>
				<div id="listClientesPage">
					<jsp:include page="listaClientes.jsp"/>
				</div>
			</div>
		</div>
	</div>
</body>
</html>