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
			<div class="text-center">
				<p>
				<a href="<c:url value="/altaEstablecimiento"/>" class="btn btn-warning" > Nuevo Establecimiento </a>
				</p>
			</div>
			<div class="col-md-offset-1 col-md-10">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<strong>Establecimientos registrados</strong>
						<a href="<c:url value="/catalogoEstablecimiento"/>" class="pull-right btn btn-primary btn-xs">
							Recargar <span class="glyphicon glyphicon-refresh"></span>
						</a>
					</div>
					
					<div class="table-responsive">
						<c:set var="nombre" value="Establecimiento <i class='fa fa-sort'></i>"/>
						<c:set var="clave" value="Clave <i class='fa fa-sort'></i>"/>
						<display:table htmlId="documents" id="document" name="${establecimientos}" 
 							class="table table-hover table-striped table-condensed"
 							requestURI="/facturaCorp"> 
 							<display:column title="#" headerClass="text-primary">${document_rowNum}</display:column>
 							<display:column title="${nombre}" property="nombre" headerClass="text-primary"></display:column>
 							<display:column title="${clave}" property="clave" headerClass="text-primary"/>
 							<display:column title="Modificar" headerClass="text-primary text-center" class="text-center">
 								<a href="<c:url value="/mostrarEstablecimiento/${document.id}"/>" class="btn btn-xs btn-success">
 								<i class="fa fa-list-alt"></i></a>
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