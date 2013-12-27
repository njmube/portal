<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Factura Generada</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica - <span class="text-info">Corporativo</span>
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
						<a href="<c:url value="/reporte" />" target="_blank" class="btn btn-primary btn-lg">Ver PDF<img id="pdfImg"
							src="<c:url value="/resources/img/pdf.png" />"
							alt="PDF"></a>
					</p>
					<p>
						<a href="<c:url value="/documentoXml" />" target="_blank" class="btn btn-primary btn-lg">Ver XML<img id="xmlImg"
							src="<c:url value="/resources/img/xml.png" />"
							alt="XML"></a>
					</p>
				</div>
				<hr>
				<div class="text-center row">
					<a href="<c:url value="/menuPage" />" class="btn btn-danger btn-lg">Terminar</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>