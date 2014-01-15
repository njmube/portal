<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>Catalogo Usuarios</title>
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
				<a href="<c:url value="/altaUsuario"/>" class="btn btn-warning" > Nuevo Usuario </a>
				</p>
			</div>
			<div class="col-md-offset-1 col-md-10">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<strong>Usuarios registrados</strong>
						<a href="<c:url value="/catalogoUsuarios"/>" class="pull-right btn btn-primary btn-xs">
							Recargar <span class="glyphicon glyphicon-refresh"></span>
						</a>
					</div>
					
					<div class="table-responsive">
						<c:set var="usuario" value="Usuario <i class='fa fa-sort'></i>"/>
						<c:set var="sucursal" value="Sucursal <i class='fa fa-sort'></i>"/>
						<c:set var="estatus" value="Estatus <i class='fa fa-sort'></i>"/>
						<display:table htmlId="documents" id="document" name="${usuarios}" 
 							class="table table-hover table-striped table-condensed"
 							requestURI="/catalogoUsuarios"> 
 							<display:column title="#" headerClass="text-primary">${document_rowNum}</display:column>
 							<display:column title="${usuario}" property="usuario" headerClass="text-primary"></display:column>
 							<display:column title="${sucursal }" property="establecimiento.nombre" headerClass="text-primary"/>
 							<display:column title="${estatus }" property="estatus.nombre" headerClass="text-primary"/>
 							<display:column title="Modificar" headerClass="text-primary text-center" class="text-center">
 								<a href="<c:url value="/mostrarUsuario/${document.id}"/>" class="btn btn-xs btn-success">
 								<i class="fa fa-list-alt"></i></a>
 							</display:column>
 						</display:table>
 						
					</div>
				</div>
				</div>
		</div>
	</div>
</body>
</html>