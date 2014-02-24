<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Factura Generada</title>
</head>
<body>
	<c:set var="filename" value="FACTURA_${documento.comprobante.serie}_${documento.comprobante.folio}" />
	<sec:authorize access="hasAnyRole('ROLE_CORP', 'ROLE_SUC')">
		<c:url var="urlMenu" value="/menuPage" />
	</sec:authorize>
	<sec:authorize access="isAnonymous()">
		<c:url var="urlMenu" value="/portal/cfdi/menu" />
	</sec:authorize>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica 
				<sec:authorize access="hasRole('ROLE_SUC')">
					- <span class="text-info">${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_CORP')">
					- <span class="text-info">Corporativo</span>
				</sec:authorize>
				
				<span class="label label-primary">@</span>
			</h2>
			<hr>
			<div class="white-panel col-md-offset-2 col-md-8">
				<h2 class="text-primary">Solicitud de Factura recibida con éxito!</h2>
				<hr>
				<sec:authorize access="isAnonymous()">
					<div class="bg-info">
						<p><strong>En unos minutos recibirá la factura electrónica 
						<span class="label label-warning">${documento.comprobante.serie}-${documento.comprobante.folio}</span> 
						en el correo proporcionado. Así mismo ponemos a su disposición la descarga de la misma a través de la 
						opción de consulta.</strong>
						</p>
						<p>
						<strong>
						Para futuras aclaraciones, le pedimos que conserve su ticket de venta.
						</strong>
						</p>
					</div>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_SUC')">
					<div class="bg-info">
						<p><strong>En unos minutos se enviará la factura electrónica 
						<span class="label label-warning">${documento.comprobante.serie}-${documento.comprobante.folio}</span> 
						al correo proporcionado. Para la impresión del PDF y XML proporcione el RFC en la 
						opción de consulta.</strong>
						</p>
					</div>
				</sec:authorize>
				<hr>
				<p class="text-center">
					<a href="${urlMenu}" class="btn btn-danger btn-lg">Terminar</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>