<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>Facturacion Corporativo</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación en Línea - <span class="text-info">Corporativo</span> <span class="label label-primary">@</span>
			</h2>
			<hr>
			<p class="wrapword">
				${comprobante.emisor.rfc}
			</p>
			<p class="wrapword">
				${comprobante.emisor.nombre}
			</p>
			<p class="wrapword">
				${comprobante.receptor.nombre}
			</p>
			<p class="wrapword">
				${comprobante.receptor.rfc}
			</p>
			<p class="wrapword">
				${comprobante.certificado}
			</p>
			<p class="wrapword">
				${comprobante.fecha}
			</p>
			<p class="wrapword">
				<c:forEach items="${comprobante.conceptos.concepto}" var="concepto">
					<p>${concepto.descripcion} ${concepto.importe}</p>
				</c:forEach>
			</p>
		</div>
	</div>
</body>
</html>