<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Modificar Facturas</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2><span class="text-info">Facturación Electrónica</span> <span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa la <kbd>Serie</kbd>, <kbd>Folio</kbd> e <kbd>Importe</kbd> de la Factura que desee modificar.</p>
			</blockquote>
			<hr>
			<c:if test="${error}">
				<div class="col-md-offset-2 col-md-8 alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					<i class="fa fa-ban"></i> No es posible modificar la factura:
					<br><br> <strong>${messageError}</strong> 
				</div>
			</c:if>
			<div class="well col-md-offset-2 col-md-8">
				<c:url var="urlPage" value="/validaSerieFolio"/>
				<form:form id="documentoForm" action="${urlPage}" method="post" modelAttribute="documento" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="serie" class="col-lg-5 col-md-5 control-label">Serie: </label>
						<div class="col-lg-3 col-md-3">
							<form:input path="comprobante.serie" id="serie" cssClass="form-control input-sm validate[required, custom[onlyLetterNumber]]" />
						</div>
					</div>
					<div class="form-group">
						<label for="folio" class="col-lg-5 col-md-5 control-label">Folio: </label>
						<div class="col-lg-3 col-md-3">
							<form:input path="comprobante.folio" id="folio" cssClass="form-control input-sm validate[required, custom[onlyNumberSp]]" />
						</div>
					</div>
					<div class="form-group">
						<label for="importe" class="col-lg-5 col-md-5 control-label">Importe: </label>
						<div class="col-lg-3 col-md-3">
							<form:input path="comprobante.total" id="importe" cssClass="form-control input-sm validate[required, custom[cantidad]]" />
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buscarDocumento" type="submit" class="btn btn-success">Buscar <i class="fa fa-search"></i></button>
							<a id="cancelar" href="<c:url value="/menu"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#documentoForm").validationEngine();
		});
	</script>
</body>
</html>