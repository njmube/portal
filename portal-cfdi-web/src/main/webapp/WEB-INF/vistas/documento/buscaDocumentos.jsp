<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Busca Documentos</title>
<script type="text/javascript" src="<c:url value="/resources/js/documento/documento.js"/>"></script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2><span class="text-info">Facturación Electrónica</span> <span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa el RFC del Cliente y un rango de fechas.</p>
			</blockquote>
			<hr>
			<div id="message_response">
			</div>
			<div class="well col-md-offset-2 col-md-8">
				<form:form id="documentoForm" action="#" method="GET" modelAttribute="cliente" cssClass="form-horizontal" role="form">
					<input type="hidden" id="establecimiento" value="${establecimiento.id}">
					<div class="form-group">
						<label for="rfc" class="col-lg-4 col-md-4 control-label">RFC: </label>
						<div class="col-lg-5 col-md-5">
							<sec:authorize access="hasAnyRole('ROLE_SUC', 'ROLE_CORP')">
								<form:input path="rfc" id="rfc" cssClass="form-control input-sm validate[custom[rfc]]" />
							</sec:authorize>
							<sec:authorize access="isAnonymous()">
								<form:input path="rfc" id="rfc" cssClass="form-control input-sm validate[required, custom[rfc]]" />
							</sec:authorize>
						</div>
					</div>
					<div class="form-group">
						<label for="rfc" class="col-lg-4 col-md-4 control-label">Fecha Inicial: </label>
						<div class="col-lg-5 col-md-5">
							<div class="input-group date" id="divFechaInit" data-date="" data-date-format="dd-mm-yyyy">
								<input name="fechaInit" id="fechaInit" class="form-control input-sm validate[required]" />
							    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						    </div>
						</div>
					</div>
					<div class="form-group">
						<label for="rfc" class="col-lg-4 col-md-4 control-label">Fecha Final: </label>
						<div class="col-lg-5 col-md-5">
							<div class="input-group date" id="divFechaFin" data-date="" data-date-format="dd-mm-yyyy">
								<input name="fechaFin" id="fechaFin" class="form-control input-sm validate[required]" />
							    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						    </div>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buscarDocumento" type="button" class="btn btn-warning">Buscar <i class="fa fa-search"></i></button>
							<sec:authorize access="hasAnyRole('ROLE_SUC', 'ROLE_CORP')">
								<a id="cancelar" href="<c:url value="/menu"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
							</sec:authorize>
							<sec:authorize access="isAnonymous()">
								<a id="cancelar" href="<c:url value="/portal/cfdi/menu"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
							</sec:authorize>
						</div>
					</div>
				</form:form>
				<div id="listDocumentosPage">
					<jsp:include page="listaDocumentos.jsp" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>