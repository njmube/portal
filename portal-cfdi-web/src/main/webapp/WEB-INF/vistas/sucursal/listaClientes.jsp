<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:if test="${!emptyList}">
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${not empty clientes}"> --%>
			<div class="centered">
				<a id="crearCliente" href="<c:url value="/clienteForm"/>" class="btn btn-warning">Nuevo Cliente <span class="glyphicon glyphicon-plus"></span></a>
			</div>
			<br>
			<div class="table-responsive">
				<display:table htmlId="clientes" id="cliente" name="${clientes}"
					class="table table-hover table-striped table-condensed"
					requestURI="/facturaCorp">
					<display:column title="#" property="id" headerClass="text-primary"></display:column>
					<display:column title="RFC" property="rfc" headerClass="text-primary"></display:column>
					<display:column title="Nombre" property="nombre" headerClass="text-primary" />
					<display:column title="Seleccionar" headerClass="text-primary text-center" class="text-center">
						<a href="<c:url value="/confirmarDatos/${cliente.id}"/>" class="btn btn-xs btn-success"><span class="glyphicon glyphicon-share-alt"></span></a>
					</display:column>
				</display:table>
			</div>
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<div class="col-md-offset-2 col-md-8"> -->
<!-- 				<div class="alert alert-danger alert-dismissable"> -->
<!-- 					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
<%-- 					<spring:message code="message.rfc.nonexistent.title"/> --%>
<%-- 					<br><br> <strong><spring:message code="message.rfc.nonexistent"/></strong>  --%>
<!-- 				</div> -->
<!-- 			</div> -->
<%-- 		</c:otherwise>	 --%>
<%-- 	</c:choose> --%>
</c:if>
<script src="<c:url value="/resources/js/sucursal/clientes.js" />"></script>
<script src="<c:url value="/resources/js/login/login.js" />"></script>
