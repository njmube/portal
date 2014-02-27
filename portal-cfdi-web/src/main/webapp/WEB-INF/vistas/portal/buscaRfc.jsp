<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Ingresa tu RFC</title>
<script type="text/javascript" src="<c:url value="/resources/js/sucursal/clientes.js"/>"></script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2><span class="text-info">Facturación Electrónica</span> <span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa tu RFC.</p>
			</blockquote>
			<hr>
			<c:if test="${errorMsg}">
				<div class="col-md-offset-2 col-md-8">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<spring:message code="message.rfc.nonexistent.title"/>
						<br><br> <strong><spring:message code="message.rfc.nonexistent"/></strong> 
					</div>
				</div>
			</c:if>
			<div class="well col-md-offset-2 col-md-8">
				<c:url var="urlBuscaRfc" value="/portal/cfdi/buscaPorRfc" />
				<form:form id="receptorFormPortal" action="${urlBuscaRfc}" method="GET" modelAttribute="cliente" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="rfc" class="col-lg-4 col-md-4 control-label">RFC: </label>
						<div class="col-lg-5 col-md-5">
							<form:input path="rfc" id="rfc" cssClass="form-control input-sm validate[required, custom[rfc]]" />
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<a id="crearCliente" href="<c:url value="/portal/cfdi/clienteForm"/>" class="btn btn-warning">Registrar RFC <i class="fa fa-plus"></i></a>
							<button id="buscarClienteRfc" type="submit" class="btn btn-primary">Buscar <i class="fa fa-search"></i></button>
							<a id="cancelar" href="<c:url value="/portal/cfdi/buscaTicket"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/login/login.js" />"></script>
</body>
</html>