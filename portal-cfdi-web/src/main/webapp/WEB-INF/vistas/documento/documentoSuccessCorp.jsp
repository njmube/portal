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
		<c:url var="urlReportePDF" value="/reporte/${filename}" />
		<c:url var="urlReporteXML" value="/documentoXml" />
		<c:url var="urlMenu" value="/menuPage" />
	</sec:authorize>
	<sec:authorize access="isAnonymous()">
		<c:url var="urlReportePDF" value="/portal/cfdi/reporte/${filename}" />
		<c:url var="urlReporteXML" value="/portal/cfdi/documentoXml" />
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
			<div class="well col-md-offset-2 col-md-8">
				<div class="row">		
					<div class="text-center col-md-6 col-md-offset-3">
						<h4 class="alert alert-success">
							<strong>Factura generada con éxito.</strong>
						</h4>
					</div>
				</div>
				<div class="text-center row">
					<p>
						<a href="${urlReportePDF}" target="_blank" class="btn btn-primary btn-lg">Ver PDF<img id="pdfImg"
							src="<c:url value="/resources/img/pdf.png" />"
							alt="PDF"></a>
					</p>
					<p>
						<a href="${urlReporteXML}" target="_blank" class="btn btn-primary btn-lg">Ver XML<img id="xmlImg"
							src="<c:url value="/resources/img/xml.png" />"
							alt="XML"></a>
					</p>
				</div>
				<hr>
				<div class="text-center row">
					<a href="${urlMenu}" class="btn btn-danger btn-lg">Terminar</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>