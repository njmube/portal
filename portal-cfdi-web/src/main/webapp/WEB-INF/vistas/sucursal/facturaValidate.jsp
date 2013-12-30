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
				<c:when test="${comprobante.tipoDeComprobante == 'ingreso'}">
					<c:set var="tipoComprobante" value="Factura"/>
				</c:when>
				<c:when test="${comprobante.tipoDeComprobante == 'egreso'}">
					<c:set var="tipoComprobante" value="Nota de Crédito"/>
				</c:when>
			</c:choose>
			<div class="row">
				<div class="col-md-4">
					<div class="white-panel form-horizontal">
						<fieldset>
							<h5 class="text-primary">Datos Ticket</h5>
							<hr>
							<div class="form-group">
								<label for="noTicket" class="col-lg-4 control-label"><small>No. de Ticket: </small></label>
								<div class="col-lg-8">
									<input id="noTicket" class="form-control input-sm" value="${ticket.transaccion.transaccionHeader.idTicket}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="noCaja" class="col-lg-4 control-label"><small>No. de Caja: </small></label>
								<div class="col-lg-8">
									<input id="noCaja" class="form-control input-sm" value="${ticket.transaccion.transaccionHeader.idCaja}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="fechahora" class="col-lg-4 control-label"><small>Fecha y Hora: </small></label>
								<div class="col-lg-8">
									<input id="fechahora" class="form-control input-sm" value="${ticket.transaccion.transaccionHeader.fechaHora}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="moneda" class="col-lg-4 control-label"><small>Moneda: </small></label>
								<div class="col-lg-3">
									<input id="moneda" class="form-control input-sm" value="${comprobante.moneda}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="tcambio" class="col-lg-4 control-label"><small>Tipo de Cambio: </small></label>
								<div class="col-lg-3">
									<input id="tcambio" class="form-control input-sm" value="${comprobante.tipoCambio}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="expedicion" class="col-lg-5 control-label"><small>Lugar de Expedición: </small></label>
							</div>
							<div class="form-group">
								<div class="col-lg-12">
									<textarea id="expedicion" class="form-control input-sm" rows="2" readonly="readonly">${establecimiento.domicilio.localidad}</textarea>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col-md-8">
					<div class="white-panel form-horizontal">
						<fieldset>
							<h5 class="text-primary">Datos del Cliente</h5>
							<hr>
							<div class="form-group">
								<label for="rfc" class="col-lg-2 control-label"><small>RFC: </small></label>
								<div class="col-lg-10">
									<input id="rfc" class="form-control input-sm" value="${comprobante.receptor.rfc}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="nombre" class="col-lg-2 control-label"><small>Nombre: </small></label>
								<div class="col-lg-10">
									<input id="nombre" class="form-control input-sm" value="${comprobante.receptor.nombre}" readonly="readonly"/>
								</div>
							</div>
							<br>
							<h5 class="text-primary">Datos del Domicilio Fiscal</h5>
							<hr>
							<div class="form-group">
								<label for="calle" class="col-lg-2 control-label"><small>Calle: </small></label>
								<div class="col-lg-10">
									<input id="calle" class="form-control input-sm" value="${comprobante.receptor.domicilio.calle}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="noExterior" class="col-lg-2 control-label"><small>No. Exterior: </small></label>
								<div class="col-lg-2">
									<input id="noExterior" class="form-control input-sm" value="${comprobante.receptor.domicilio.noExterior}" readonly="readonly"/>
								</div>
								<label for="noInterior" class="col-lg-2 control-label"><small>No. Interior: </small></label>
								<div class="col-lg-2">
									<input id="noInterior" class="form-control input-sm" value="${comprobante.receptor.domicilio.noInterior}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="colonia" class="col-lg-2 control-label"><small>Colonia: </small></label>
								<div class="col-lg-4">
									<input id="colonia" class="form-control input-sm" value="${comprobante.receptor.domicilio.colonia}" readonly="readonly"/>
								</div>
								<label for="delmun" class="col-lg-2 control-label"><small>Delegación/Municipio: </small></label>
								<div class="col-lg-4">
									<input id="delmun" class="form-control input-sm" value="${comprobante.receptor.domicilio.municipio}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="localidad" class="col-lg-2 control-label"><small>Ciudad: </small></label>
								<div class="col-lg-4">
									<input id="localidad" class="form-control input-sm" value="${comprobante.receptor.domicilio.localidad}" readonly="readonly"/>
								</div>
								<label for="estado" class="col-lg-2 control-label"><small>Estado: </small></label>
								<div class="col-lg-4">
									<input id="estado" class="form-control input-sm" value="${comprobante.receptor.domicilio.estado}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="cp" class="col-lg-2 control-label"><small>Código Postal: </small></label>
								<div class="col-lg-2">
									<input id="cp" class="form-control input-sm" value="${comprobante.receptor.domicilio.codigoPostal}" readonly="readonly"/>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="white-panel table-responsive">
						<fieldset>
							<h5 class="text-primary">Artículos</h5>
							<hr>
							<display:table htmlId="conceptos" id="concepto" name="${comprobante.conceptos.concepto}"
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
									<small><fmt:formatNumber value="${concepto.valorUnitario}" type="currency"/></small>
								</display:column>
								<display:column title="Importe" headerClass="text-primary">
									<small><fmt:formatNumber value="${concepto.importe}" type="currency"/></small>
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
								<label for="formaPago" class="col-lg-2 control-label"><small>Forma de Pago: </small></label>
								<div class="col-lg-3">
									<input id="formaPago" class="form-control input-sm" value="${comprobante.formaDePago}" readonly="readonly"/>
								</div>
								<label for="descuento" class="col-lg-5 control-label"><small>Descuento: </small></label>
								<div class="col-lg-2">
									<input id="descuento" class="form-control input-sm" value="<fmt:formatNumber value="${comprobante.descuento}" type="currency"/>" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="condPago" class="col-lg-2 control-label"><small>Condiciones de Pago: </small></label>
								<div class="col-lg-3">
									<input id="condPago" class="form-control input-sm" value="${comprobante.condicionesDePago}" readonly="readonly"/>
								</div>
								<label for="subtotal" class="col-lg-5 control-label"><small>Subtotal: </small></label>
								<div class="col-lg-2">
									<input id="subtotal" class="form-control input-sm" value="<fmt:formatNumber value="${comprobante.subTotal}" type="currency"/>" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="metodoPago" class="col-lg-2 control-label"><small>Método de Pago: </small></label>
								<div class="col-lg-3">
									<input id="metodoPago" class="form-control input-sm" value="${comprobante.metodoDePago}" readonly="readonly"/>
								</div>
								<label for="iva" class="col-lg-5 control-label"><small>IVA: </small></label>
								<div class="col-lg-2">
									<input id="iva" class="form-control input-sm" value="<fmt:formatNumber value="${comprobante.impuestos.totalImpuestosTrasladados}" type="currency"/>" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="numCta" class="col-lg-2 control-label"><small>Número de Cuenta: </small></label>
								<div class="col-lg-3">
									<input id="numCta" class="form-control input-sm" value="${comprobante.numCtaPago}" readonly="readonly"/>
								</div>
								<label for="total" class="col-lg-5 control-label"><small>Total: </small></label>
								<div class="col-lg-2">
 									<input id="total" class="form-control input-sm" value="<fmt:formatNumber value="${comprobante.total}" type="currency"/>" readonly="readonly"/>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
			<p class="text-center">
				<button id="generaFactura" type="button" class="btn btn-primary btn-lg"><small>Generar ${tipoComprobante}</small> <span class="glyphicon glyphicon-list-alt"></span></button>
				<a id="cancel" href="<c:url value="/buscaTicket" />" class="btn btn-danger btn-lg"><small>Cancelar</small> <span class="glyphicon glyphicon-remove"></span></a>
			</p>
		</div>
	</div>
	<div id="page_loader" class="page_loader">
	</div>
	<div class="page_loader_content text-center">
		<div class="row">
			<div class="panel col-md-4 col-md-offset-4">
				<h3>Generando Factura...</h3>
				<div class="progress progress-striped active">
				  	<div class="progress-bar"  role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%"></div>
				</div>
			</div>
		</div>
	</div>
	<c:url value="/generarDocumento" var="urlDocumento"/>
	<form action="${urlDocumento}" id="formPdf" method="post"></form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#generaFactura").click(function() {
				$("#page_loader").show();
				$(".page_loader_content").show();
				$("#formPdf").submit();
			});
		});
	</script>
</body>
</html>