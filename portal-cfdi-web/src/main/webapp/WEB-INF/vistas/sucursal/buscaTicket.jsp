<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Buscar Ticket</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#divFecha").datepicker({
			language: "es",
			todayHighlight: true,
			autoclose: true
		});
	}); 
</script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Facturación Electrónica<span class="text-info"> - ${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span> <span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa los Datos del Ticket.</p>
			</blockquote>
			<hr>
			<c:if test="${invalidTicket}">
				<div class="col-md-offset-3 col-md-6">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<spring:message code="messages.ticket.failed"/>
						<br><br> <strong><spring:message code="messages.ticket.invalid.cause" /></strong> 
					</div>
				</div>
			</c:if>
			<div class="well col-md-offset-3 col-md-6">
				<c:url var="urlTicket" value="/validaTicket"/>
				<form:form id="ticketForm" action="${urlTicket}" method="post" modelAttribute="ticket" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="noTicket" class="col-lg-5 control-label">No. de Ticket: </label>
						<div class="col-lg-4">
							<form:input path="transaccion.transaccionHeader.idTicket" id="noTicket" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label for="caja" class="col-lg-5 control-label">Caja: </label>
						<div class="col-lg-4">
							<form:input path="transaccion.transaccionHeader.idCaja" id="caja" cssClass="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
						<label for="fecha" class="col-lg-5 control-label">Fecha: </label>
						<div class="col-lg-4">
							<div class="input-group date" id="divFecha" data-date="" data-date-format="dd-mm-yyyy">
								<form:input path="transaccion.transaccionHeader.fecha" id="fecha" cssClass="form-control input-sm" readonly="true"/>
							    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						    </div>
						</div>
					</div>
					<div class="form-group">
						<label for="hora" class="col-lg-5 control-label">Importe: </label>
						<div class="col-lg-2">
							<form:input path="transaccion.transaccionTotal.totalVenta" id="hora" cssClass="form-control input-sm"/>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buscarCliente" type="submit" class="btn btn-primary">Buscar <span class="glyphicon glyphicon-search"></span></button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/login/login.js" />"></script>	
</body>
</html>