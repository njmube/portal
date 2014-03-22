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
<title>Catalogo Establecimientos</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica - <span class="text-info">Administrador</span> <span class="label label-primary">@</span>
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
						<c:set var="nombre" value="Establecimiento <i class='fa fa-sort'></i>"/>
						<c:set var="serieF" value="Serie F.<i class='fa fa-sort'></i>"/>
						<c:set var="folioIF" value="Folio I. F. <i class='fa fa-sort'></i>"/>
						<c:set var="folioCF" value="Folio C. F. <i class='fa fa-sort'></i>"/>
						<c:set var="serieNC" value="Serie N_C<i class='fa fa-sort'></i>"/>
						<c:set var="folioINC" value="Folio I. N_C <i class='fa fa-sort'></i>"/>
						<c:set var="folioCNC" value="Folio C. N_C <i class='fa fa-sort'></i>"/>
						<display:table htmlId="documents" id="document" name="${establecimientos}" 
 							class="table table-hover table-striped table-condensed"
 							requestURI="/catalogoEstablecimiento"> 
 							<display:column title="#" headerClass="text-primary">${document_rowNum}</display:column>
 							<display:column title="${nombre}" property="nombre" headerClass="text-primary"></display:column>
 							<display:column title="${serieF}" property="serieFolioEstablecimientoLista[0].serie" headerClass="text-primary"/>
 							<display:column title="${folioIF}" property="serieFolioEstablecimientoLista[0].folioInicial" headerClass="text-primary"/>
 							<display:column title="${folioCF}" property="serieFolioEstablecimientoLista[0].folioConsecutivo" headerClass="text-primary"/>
 							<display:column title="${serieNC}" property="serieFolioEstablecimientoLista[1].serie" headerClass="text-primary"/>
 							<display:column title="${folioINC}" property="serieFolioEstablecimientoLista[1].folioInicial" headerClass="text-primary"/>
 							<display:column title="${folioCNC}" property="serieFolioEstablecimientoLista[1].folioConsecutivo" headerClass="text-primary"/>
 							<display:column title="Asignar" headerClass="text-primary text-center" class="text-center">
 								<a href="<c:url value="/reAsignarSerieFolio/${document.id}"/>" class="btn btn-xs btn-success">
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