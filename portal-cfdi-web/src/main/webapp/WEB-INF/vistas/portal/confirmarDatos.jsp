<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Confirmar Datos</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2 class="text-primary">Confirmación Datos Facturación</h2>
			<hr>
			<c:url var="altaUrl" value="/portal/cfdi/clienteForm"/>
			<div class="form-group">
				<label class="control-label col-lg-1">Nombre: </label>
				<div class="col-lg-3">
					<input id="nombre" class="form-control input-sm" value="${cliente.nombre}" disabled="disabled"/>
				</div>
				<label class="control-label col-lg-1">Rfc: </label>
				<div class="col-lg-2">
					<input id="rfc" class="form-control input-sm" value="${cliente.rfc}" disabled="disabled"/>
				</div>
			</div>
			<br/>
			<br/>
			<div class="well col-md-offset-0 col-md-12">
				<div class="table-responsive">
					<display:table htmlId="domicilios" id="domicilio" name="${cliente.domicilios}"
						class="table table-hover table-striped table-condensed"
						requestURI="/confirmarDatos">
						<display:column title="#" property="id" headerClass="text-primary"></display:column>
						<display:column title="Calle" property="calle" headerClass="text-primary"></display:column>
						<display:column title="No. Ext." property="noExterior" headerClass="text-primary" />
						<c:choose>
							<c:when test="${empty domicilio.noInterior}">
								<display:column title="No. Int." value="S/N" headerClass="text-primary" />
							</c:when>
							<c:otherwise>
								<display:column title="No. Int." property="noInterior" headerClass="text-primary" />
							</c:otherwise>
						</c:choose>
						<display:column title="País"  property="estado.pais.nombre" headerClass="text-primary" />
						<display:column title="Estado"  property="estado.nombre" headerClass="text-primary" />
						<display:column title="Municipio"  property="municipio" headerClass="text-primary" />
						<display:column title="Colonia" property="colonia" headerClass="text-primary" />
						<display:column title="Codigo Postal"  property="codigoPostal" headerClass="text-primary" />
<%-- 						<display:column title="Localidad"  property="localidad" headerClass="text-primary" /> --%>
<%-- 						<display:column title="Referencia"  property="referencia" headerClass="text-primary" /> --%>
						<display:column title="Fiscal" headerClass="text-primary text-center" class="text-center">
						<c:choose>
							<c:when test="${domicilio_rowNum == 1}">
								<input type="radio" id="domFiscal" name="domFiscal" value="${domicilio.id}" checked="checked">
							</c:when>
							<c:otherwise>
								<input type="radio" id="domFiscal" name="domFiscal" value="${domicilio.id}">
							</c:otherwise>
						</c:choose>											
						</display:column>
					</display:table>
				</div>
			</div>
				<p class="text-center"> 
					<a id="continue" href="#" class="btn btn-success"><span>Continuar</span></a>
					<a href="<c:url value="/portal/cfdi/clienteCorregir/${cliente.id}"/>" class="btn btn-warning"><span>Modificar</span></a>
					<a href="<c:url value="/portal/cfdi/buscaRfc"/>" class="btn btn-danger"><span>Cancelar</span></a>
				</p>
		</div>				
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#continue").click(function () {
				var idDom = $("input[id=domFiscal]:checked").val();
				location.href = contextPath + "/portal/cfdi/datosFacturacion/" + idDom;
			});
		});
	</script>
</body>
</html>
