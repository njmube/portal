<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Facturacion Corporativo</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Facturación Electrónica<span class="text-info"> - ${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span> <span class="label label-primary">@</span></h2>
			<hr>
			<fmt:setLocale value="en_US" scope="session"/>
			<c:choose>
				<c:when test="${documento.comprobante.tipoDeComprobante == 'ingreso'}">
					<c:set var="tipoComprobante" value="Factura"/>
				</c:when>
				<c:when test="${documento.comprobante.tipoDeComprobante == 'egreso'}">
					<c:set var="tipoComprobante" value="Nota de Crédito"/>
				</c:when>
			</c:choose>
			<c:url value="/refacturarDocumento" var="urlDocumento"/>
			<form:form action="${urlDocumento}" id="formPdf" method="post" modelAttribute="documento">
			<div class="row">
				<div class="col-md-12">
					<div class="white-panel form-horizontal">
						<fieldset>
							<h5 class="text-primary">Datos Factura</h5>
							<hr>
							<div class="form-group">
								<label for="factura" class="col-lg-5 col-md-5 control-label"><small>Factura a modificar: </small></label>
								<div class="col-lg-2 col-md-2">
									<input id="factura" class="form-control input-sm" value="${documento.documentoOrigen.comprobante.serie} - ${documento.documentoOrigen.comprobante.folio}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="moneda" class="col-lg-1 col-md-1 control-label"><small>Moneda: </small></label>
								<div class="col-lg-1 col-md-1">
									<input id="moneda" class="form-control input-sm" value="${documento.comprobante.moneda}" readonly="readonly"/>
								</div>
								<label for="tcambio" class="col-lg-2 col-md-2 control-label"><small>Tipo de Cambio: </small></label>
								<div class="col-lg-2 col-md-2">
									<input id="tcambio" class="form-control input-sm" value="${documento.comprobante.tipoCambio}" readonly="readonly"/>
								</div>
								<label for="expedicion" class="col-lg-2 col-md-2 control-label"><small>Lugar de Expedición: </small></label>
								<div class="col-lg-4 col-md-4">
									<textarea id="expedicion" class="form-control input-sm" rows="2" readonly="readonly">${documento.comprobante.lugarExpedicion}</textarea>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-center">
					<p>
						<button id="btnArticulos" type="button" class="btn btn-warning" data-toggle="collapse" data-target="#articulos">
						  Mostrar Artículos <i class="fa fa-sort"></i>
						</button>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div id="articulos" class="white-panel table-responsive collapse">
						<fieldset>
							<h5 class="text-primary">Artículos</h5>
							<hr>
							<display:table htmlId="conceptos" id="concepto" name="${documento.comprobante.conceptos.concepto}"
								class="table table-hover table-striped table-condensed"
								requestURI="/facturaCorp">
								<display:column title="Cantidad" headerClass="text-primary">
									<small>${concepto.cantidad}</small>
								</display:column>
								<display:column title="Unidad" headerClass="text-primary">
									<small>${concepto.unidad}</small>
								</display:column>
								<display:column title="Descripción" headerClass="text-primary">
									<small>${concepto.descripcion}</small>
								</display:column>
								<display:column title="Precio Unitario" headerClass="text-primary">
									<small><fmt:formatNumber value="${concepto.valorUnitario}" type="currency" maxFractionDigits="4"/></small>
								</display:column>
								<display:column title="Importe" headerClass="text-primary">
									<small><fmt:formatNumber value="${concepto.importe}" type="currency" maxFractionDigits="4"/></small>
								</display:column>
							</display:table>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="white-panel form-horizontal">
						<fieldset>
							<br>
							<div class="form-group">
								<label for="formaPago" class="col-lg-2 col-md-2 control-label"><small>Forma de Pago: </small></label>
								<div class="col-lg-3 col-md-3">
									<input id="formaPago" class="form-control input-sm" value="${documento.comprobante.formaDePago}" readonly="readonly"/>
								</div>
								<label for="descuento" class="col-lg-5 col-md-5 control-label"><small>Descuento: </small></label>
								<div class="col-lg-2 col-md-2">
									<input id="descuento" class="form-control input-sm" value="<fmt:formatNumber value="${documento.comprobante.descuento}" type="currency" maxFractionDigits="4"/>" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="condPago" class="col-lg-2 col-md-2 control-label"><small>Condiciones de Pago: </small></label>
								<div class="col-lg-3 col-md-3">
									<input id="condPago" class="form-control input-sm" value="${documento.comprobante.condicionesDePago}" readonly="readonly"/>
								</div>
								<label for="subtotal" class="col-lg-5 col-md-5 control-label"><small>Subtotal: </small></label>
								<div class="col-lg-2 col-md-2">
									<input id="subtotal" class="form-control input-sm" value="<fmt:formatNumber value="${documento.comprobante.subTotal}" type="currency" maxFractionDigits="4"/>" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="metodoPago" class="col-lg-2 col-md-2 control-label"><small>Método de Pago: </small></label>
								<div class="col-lg-3 col-md-3">
									<input id="metodoPago" class="form-control input-sm" value="${documento.comprobante.metodoDePago}" readonly="readonly"/>
								</div>
								<label for="iva" class="col-lg-5 col-md-5 control-label"><small>IVA: </small></label>
								<div class="col-lg-2 col-md-2">
									<input id="iva" class="form-control input-sm" value="<fmt:formatNumber value="${documento.comprobante.impuestos.totalImpuestosTrasladados}" type="currency" maxFractionDigits="4"/>" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="numCta" class="col-lg-2 col-md-2 control-label"><small>Número de Cuenta: </small></label>
								<div class="col-lg-3 col-md-3">
									<input id="numCta" class="form-control input-sm" value="${documento.comprobante.numCtaPago}" readonly="readonly"/>
								</div>
								<label for="total" class="col-lg-5 col-md-5 control-label"><small>Total: </small></label>
								<div class="col-lg-2 col-md-2 has-error">
 									<input id="total" class="form-control input-sm" value="<fmt:formatNumber value="${documento.comprobante.total}" type="currency"/>" readonly="readonly"/>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
				<c:url var="cambiarCliente" value="/refacturacion/buscaRfc"/>
				<div class="col-md-12 text-center">
					<p>
						<a id="cambiaCliente" href="${cambiarCliente}" class="btn btn-primary">
						  Cambiar Cliente <i class="fa fa-exchange"></i>
						</a>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="white-panel form-horizontal">
						<fieldset>
							<h5 class="text-primary">Datos de Facturación</h5>
							<hr>
							<div class="form-group">
								<label for="rfc" class="col-lg-4 col-md-4 control-label"><small>RFC: </small></label>
								<div class="col-lg-2 col-md-2">
									<form:input id="rfc" path="comprobante.receptor.rfc" cssClass="form-control input-sm" readonly="true"/>
								</div>
							</div>
							<div class="form-group">
								<label for="nombre" class="col-lg-4 col-md-4 control-label"><small>Nombre: </small></label>
								<div class="col-lg-6 col-md-6">
									<form:input id="nombre" path="comprobante.receptor.nombre" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="calle" class="col-lg-4 col-md-4 control-label"><small>Calle: </small></label>
								<div class="col-lg-6 col-md-6">
									<form:input id="calle" path="comprobante.receptor.domicilio.calle" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="noExterior" class="col-lg-4 col-md-4 control-label"><small>No. Exterior: </small></label>
								<div class="col-lg-2 col-md-2">
									<form:input id="noExterior" path="comprobante.receptor.domicilio.noExterior" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="noInterior" class="col-lg-4 col-md-4 control-label"><small>No. Interior: </small></label>
								<div class="col-lg-2 col-md-2">
									<form:input id="noInterior" path="comprobante.receptor.domicilio.noInterior" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="colonia" class="col-lg-4 col-md-4 control-label"><small>Colonia: </small></label>
								<div class="col-lg-4 col-md-4">
									<form:input id="colonia" path="comprobante.receptor.domicilio.colonia" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="delmun" class="col-lg-4 col-md-4 control-label"><small>Delegación/Municipio: </small></label>
								<div class="col-lg-4 col-md-3">
									<form:input id="delmun" path="comprobante.receptor.domicilio.municipio" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="estado" class="col-lg-4 col-md-4 control-label"><small>Estado: </small></label>
								<div class="col-lg-4 col-md-4">
									<form:input id="estado" path="comprobante.receptor.domicilio.estado" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="pais" class="col-lg-4 col-md-4 control-label"><small>País: </small></label>
								<div class="col-lg-4 col-md-4">
									<form:input id="pais" path="comprobante.receptor.domicilio.pais" cssClass="form-control input-sm"/>
								</div>
							</div>
							<div class="form-group">
								<label for="cp" class="col-lg-4 col-md-4 control-label"><small>Código Postal: </small></label>
								<div class="col-lg-2 col-md-2">
									<form:input id="cp" path="comprobante.receptor.domicilio.codigoPostal" cssClass="form-control input-sm"/>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<p class="text-center">
				<button id="generaFactura" type="button" class="btn btn-primary btn-lg"><small>Modificar ${tipoComprobante}</small> <i class="fa fa-list-alt"></i></button>
				<a id="goback" href="<c:url value="/refacturarForm"/>" class="btn btn-warning btn-lg"><small>Regresar</small> <i class="fa fa-arrow-left"></i></a>
				<a id="cancel" href="<c:url value="/menu" />" class="btn btn-danger btn-lg"><small>Cancelar</small> <i class="fa fa-times"></i></a>
			</p>
			</form:form>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#generaFactura").click(function() {
				$("#page_loader").show();
				$("#page_loader_factura_content").show();
				$(this).attr("disabled", "disabled");
				$("#goback").attr("disabled", "disabled");
				$("#cancel").attr("disabled", "disabled");
				$("#formPdf").submit();
			});
			
			$('#articulos').on('shown.bs.collapse', function () {
				  $('#btnArticulos').html("Ocultar Artículos <i class=\"fa fa-sort\"></i>");
			});
			
			$('#articulos').on('hidden.bs.collapse', function () {
				  $('#btnArticulos').html("Mostrar Artículos <i class=\"fa fa-sort\"></i>");
			});
		});
	</script>
</body>
</html>
