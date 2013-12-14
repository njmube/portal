<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>Facturacion Corporativo</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica - <span class="text-info">Corporativo</span> <span class="label label-primary">@</span>
			</h2>
			<hr>
			<div class="row">
				<div class="col-md-4">
					<div class="white-panel form-horizontal">
						<fieldset>
							<h5 class="text-primary">Datos Factura</h5>
							<hr>
							<div class="form-group">
								<label for="folioSap" class="col-lg-4 control-label"><small>Folio SAP: </small></label>
								<div class="col-lg-8">
									<input id="folioSap" class="form-control input-sm" value="${folioSap}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="factura" class="col-lg-4 control-label"><small>Factura: </small></label>
								<div class="col-lg-8">
									<input id="factura" class="form-control input-sm" value="${comprobante.serie}${comprobante.folio}" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="fechahora" class="col-lg-4 control-label"><small>Fecha y Hora: </small></label>
								<div class="col-lg-8">
									<input id="fechahora" class="form-control input-sm" value="${comprobante.fecha}" readonly="readonly"/>
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
								<div class="col-lg-12">
									<textarea id="expedicion" class="form-control input-sm" rows="2" readonly="readonly">${comprobante.lugarExpedicion}</textarea>
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
								<display:column title="Cantidad" property="cantidad" headerClass="text-primary"/>
								<display:column title="Unidad" property="unidad" headerClass="text-primary" />
								<display:column title="Descripción" property="descripcion" headerClass="text-primary" />
								<display:column title="Precio Unitario" property="valorUnitario" headerClass="text-primary" />
								<display:column title="Importe" property="importe" headerClass="text-primary" />
							</display:table>
						</fieldset>
					</div>
				</div>
			</div>
<!-- 			<div class="row"> -->
<!-- 				<div class="col-md-12"> -->
<!-- 					<div class="white-panel">hole</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<p class="text-center">
					<button id="generaFactura" type="button" class="btn btn-primary btn-lg">Sellar Factura <span class="glyphicon glyphicon-list-alt"></span></button>
					<button id="generaFactura" type="button" class="btn btn-danger btn-lg">Cancelar <span class="glyphicon glyphicon-remove"></span></button>
			</p>
		</div>
	</div>
</body>
</html>