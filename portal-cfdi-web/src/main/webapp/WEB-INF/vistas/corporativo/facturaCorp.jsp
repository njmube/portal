<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
			<div class="col-md-offset-1 col-md-10">
				<div class="panel panel-danger">
					<div class="panel-heading">
						Documentos Pendientes por Facturar
						<a href="<c:url value="/facturaCorp"/>" class="pull-right btn btn-primary btn-xs">
							Recargar <span class="glyphicon glyphicon-refresh"></span>
						</a>
					</div>
					<div class="table-responsive">
						<c:set var="titlename" value="Nombre <span class='glyphicon glyphicon-sort text-warning'></span>"/>
						<c:set var="foliosap" value="Folio SAP <span class='glyphicon glyphicon-sort text-warning'></span>"/>
						<display:table htmlId="documents" id="document" name="${documentos}" 
 							class="table table-hover table-striped table-condensed"
 							requestURI="/facturaCorp"> 
 							<display:column title="#" headerClass="text-primary">${document_rowNum}</display:column>
 							<display:column title="${foliosap}" property="folio" headerClass="text-primary"></display:column>
 							<display:column title="${titlename}" property="nombre" headerClass="text-primary"/>
 							<display:column title="Generar Factura" headerClass="text-primary text-center" class="text-center">
 								<a href="<c:url value="/facturaCorp/validate/${document.nombre}"/>" class="btn btn-xs btn-success"><span class="glyphicon glyphicon-print"></span></a>
 							</display:column>
 						</display:table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/corporativo/corporativo.js" />"></script>
</body>
</html>