<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alta Serie y folio de establecimiento</title>
</head>
<body>
	<div class="container">
		<div class="white-panel row">
			<h2>REGISTRO DE SERIE Y FOLIO</h2>
			<blockquote>
				<p class="text-info">Ingresa serie y folio del Establecimiento para la factura y nota de credito.</p>
			</blockquote>
			<hr>
			<c:if test="${existSerie}">
				<div class="col-md-offset-2 col-md-8">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<spring:message code="messages.error.serie.existente"/>
						<br><br>
						<c:if test="${existSerieFactura}">
						<strong><i class="fa fa-ban"></i><spring:message code="messages.error.serie.factura" /></strong>
						</c:if>
						<c:if test="${existSerieNotaCredito}">
						<strong><i class="fa fa-ban"></i><spring:message code="messages.error.serie.notacredito" /></strong>
						</c:if>   
					</div>
				</div>
			</c:if>
			<c:choose>
				<c:when test="${guardar}">
					<c:url var="guardar" value="/guardarEstablecimiento" />					
				</c:when>
				<c:otherwise >
					<c:url var="guardar" value="/actualizarSerieFolio" />
				</c:otherwise>
			</c:choose>
			<form:form id="serieFolioForm" action="${guardar}" modelAttribute="nuevoEstablecimiento" method="post" cssClass="form-horizontal" role="form">
				<div class="row">
					<div class="col-md-5 col-md-offset-1">
						<div class="white-panel form-horizontal">
							<h4 class="text-primary">Factura</h4>
							<hr>
							<fieldset>
								<div class="form-group">
								<form:hidden id="idEstablecimiento" path="id" />
								<label for="txtSerieFactura" class="col-lg-5 col-md-5 control-label">Serie:</label>
								<div class="col-lg-5 col-md-5">
									<form:input path="serieFolioEstablecimientoLista[0].serie" cssClass="form-control input-sm validate[required] col-lg-5 col-md-5" id="txtSerieFactura" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtFolioFactura" class="col-lg-5 col-md-5 control-label">Folio:</label>
								<div class="col-lg-5 col-md-5">
									<form:input path="serieFolioEstablecimientoLista[0].folioInicial" cssClass="form-control input-sm validate[required,custom[onlyNumberSp]] col-lg-5 col-md-5" id="txtFolioFactura" />
								</div>
							</div>
							</fieldset>
						</div>
					</div>
					<div class="col-md-5" >
						<div class="white-panel form-horizontal">
							<h4 class="text-primary">Nota de credito</h4>
							<hr>
							<fieldset>
								<div class="form-group">
								<label for="txtSerieNotaDeCredito" class="col-lg-5 col-md-5 control-label">Serie:</label>
								<div class="col-lg-5 col-md-5">
									<form:input path="serieFolioEstablecimientoLista[1].serie" cssClass="form-control input-sm validate[required] col-lg-5 col-md-5" id="txtSerieNotaDeCredito" />
								</div>
							</div>
							<div class="form-group">
								<label for="txtFolioNotaDeCredito" class="col-lg-5 col-md-5 control-label">Folio:</label>
								<div class="col-lg-5 col-md-5">
									<form:input path="serieFolioEstablecimientoLista[1].folioInicial" cssClass="form-control input-sm validate[required,custom[onlyNumberSp]] col-lg-5 col-md-5" id="txtFolioNotaDeCredito" />
								</div>
							</div>
							</fieldset>
						</div>
					</div>
				</div>
				<div class="row">
					<p class="form-grup text-center">
					<button id="guardar" type="button" class="btn btn-primary">Guardar <i class="fa fa-floppy-o"></i></button>
<!-- 					<button id="cancelar" type="button" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></button> -->
					</p>
			</div>					
			</form:form>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#serieFolioForm").validationEngine();
			
			$("#guardar").click(function () {
				if ($("#serieFolioForm").validationEngine("validate")){
					$("#serieFolioForm").submit();
				}
			});
			
// 			$("#cancelar").click(function (){
// 				location.href = contextPath + "/establecimientoSerieFolio";
// 			});
		});
	</script>
</body>
</html>