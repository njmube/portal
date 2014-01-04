<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Formulario de alta establecimiento</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica - <span class="text-info">ADMINISTRADOR</span> <span class="label label-primary">@</span>
			</h2>
				<hr>
			<div class="col-md-offset-1 col-md-10">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<strong>Establecimientos registrados</strong>
						<a href="<c:url value="/catalogoEstablecimiento"/>" class="pull-right btn btn-primary btn-xs">
							Recargar <span class="glyphicon glyphicon-refresh"></span>
						</a>
					</div>
					<div class="table-responsive">
						<c:set var="nombre" value="Establecimiento <span class='glyphicon glyphicon-sort text-warning'></span>"/>
						<c:set var="clave" value="Clave <span class='glyphicon glyphicon-sort text-warning'></span>"/>
						<c:set var="password" value="Password <span class='glyphicon glyphicon-sort text-warning'></span>"/>
						<display:table htmlId="documents" id="document" name="${establecimientos}" 
 							class="table table-hover table-striped table-condensed"
 							requestURI="/facturaCorp"> 
 							<display:column title="#" headerClass="text-primary">${document_rowNum}</display:column>
 							<display:column title="${nombre}" property="nombre" headerClass="text-primary"></display:column>
 							<display:column title="${clave}" property="clave" headerClass="text-primary"/>
 							<display:column title="${password}" property="password" headerClass="text-primary"/>
 							<display:column title="Seleccionar" headerClass="text-primary text-center" class="text-center">
 								<a href="<c:url value="/actualizarEstablecimiento/${document.id}"/>" class="btn btn-xs btn-success">
 								<span class="glyphicon glyphicon-print"></span></a>
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