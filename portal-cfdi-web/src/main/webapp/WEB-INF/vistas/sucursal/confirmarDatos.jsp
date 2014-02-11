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
			<c:url var="altaUrl" value="/clienteForm"/>
			<div class="form-group">
				<label class="control-label col-lg-1">Rfc: </label>
				<div class="col-lg-2">
					<input id="rfc" class="form-control input-sm" value="${cliente.rfc}" disabled="disabled"/>
				</div>
				<label class="control-label col-lg-1">Nombre: </label>
				<div class="col-lg-4">
					<input id="nombre" class="form-control input-sm" value="${cliente.nombre}" disabled="disabled"/>
				</div>
				<label class="control-label col-lg-1">Email: </label>
				<div class="col-lg-3">
					<input id="email" class="form-control input-sm" value="${cliente.email}" disabled="disabled"/>
				</div>
			</div>
			<br/>
			<br/>
			<div class="well col-md-offset-0 col-md-12">
				<div class="table-responsive">
					<display:table htmlId="domicilios" id="domicilio" name="${cliente.domicilios}"
						class="table table-hover table-striped table-condensed"
						requestURI="/confirmarDatos">
								<display:column title="#" property="id" headerClass="text-primary small" class="small"></display:column>
								<display:column title="Calle" property="calle" headerClass="text-primary small" class="small"></display:column>
								<display:column title="No. Ext." property="noExterior" headerClass="text-primary small" class="small"/>
								<c:choose>
									<c:when test="${empty domicilio.noInterior}">
										<display:column title="No. Int." value="S/N" headerClass="text-primary small" class="small"/>
									</c:when>
									<c:otherwise>
										<display:column title="No. Int." property="noInterior" headerClass="text-primary small" class="small"/>
									</c:otherwise>
								</c:choose>
								<display:column title="País"  property="estado.pais.nombre" headerClass="text-primary small" class="small"/>
								<display:column title="Estado"  property="estado.nombre" headerClass="text-primary small" class="small"/>
								<display:column title="Delegación/Municipio"  property="municipio" headerClass="text-primary small" class="small"/>
								<display:column title="Colonia" property="colonia" headerClass="text-primary small" class="small"/>
								<display:column title="Código Postal"  property="codigoPostal" headerClass="text-primary small" class="small"/>
								<display:column title="Estatus" headerClass="text-primary small" class="small">
									<span id="estatus" class="${domicilio.estatus.id}">${domicilio.estatus.nombre}</span>
								</display:column>
								<display:column title="" headerClass="text-primary text-center small" class="small">
								<c:choose>
									<c:when test="${domicilio.estatus.id eq 1}">
										<input type="radio" id="domFiscal" name="domFiscal" value="${domicilio.id}">
									</c:when>
								</c:choose>											
<%-- 								<c:choose> --%>
<%-- 									<c:otherwise> --%>
<!-- 										No existen direcciones activas para el cliente. -->
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>
								</display:column>
					</display:table>
				</div>
			</div>
				<p class="text-center"> 
					<a id="continue" href="#" class="btn btn-success"><span>Continuar</span> <i class="fa fa-arrow-right"></i></a>
					<a href="<c:url value="/clienteCorregir/${cliente.id}"/>" class="btn btn-warning"><span>Modificar</span> <i class="fa fa-pencil-square-o"></i></a>
					<a href="<c:url value="/buscaRfc"/>" class="btn btn-danger"><span>Cancelar</span> <i class="fa fa-times"></i></a>
				</p>
		</div>				
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			
			$("#domicilios > tbody > tr").each(function() {
				if($(this).find("#estatus").hasClass("1")) {
					$(this).find("#domFiscal").attr("checked", true);
					return false;
				}
			});
			
			$("#continue").click(function () {
				var idDom = $("input[id=domFiscal]:checked").val();
				location.href = contextPath + "/datosFacturacion/" + idDom;
			});
		});
	</script>
</body>
</html>
