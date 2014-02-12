<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>Buscar Ticket</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Facturación Electrónica <span class="text-info"> - ${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span>
				<span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa los Datos del Ticket.</p>
			</blockquote>
			<hr>
			<c:if test="${invalidTicket}">
				<div class="col-md-offset-2 col-md-8">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<spring:message code="messages.ticket.failed"/>
						<br><br> <strong><spring:message code="messages.ticket.invalid.cause" /></strong> 
					</div>
				</div>
			</c:if>
			<c:if test="${ticketProcessed}">
				<div class="col-md-offset-2 col-md-8">
					<div class="alert alert-danger alert-dismissable auto-close">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						Ticket Facturado
						<br><br> <strong><spring:message code="messages.ticket.processed" /></strong> 
					</div>
				</div>
			</c:if>
			<div class="well col-md-offset-2 col-md-8">
				<c:url var="urlTicket" value="/validaTicket"/>
				<form:form id="ticketForm" action="${urlTicket}" method="post" modelAttribute="ticket" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="noTicket" class="col-lg-5 col-md-5 control-label">No. de Ticket: </label>
						<div class="col-lg-3 col-md-4">
							<form:input path="transaccion.transaccionHeader.idTicket" id="noTicket" cssClass="form-control input-sm validate[required]" />
						</div>
						<small class="errorForm"><strong><form:errors path="transaccion.transaccionHeader.idTicket" cssClass="text-danger"/></strong></small>
					</div>
					<div class="form-group">
						<label for="caja" class="col-lg-5 col-md-5 control-label">Caja: </label>
						<div class="col-lg-3 col-md-4">
							<form:input path="transaccion.transaccionHeader.idCaja" id="caja" cssClass="form-control input-sm validate[required]" />
						</div>
						<small class="errorForm"><strong><form:errors path="transaccion.transaccionHeader.idCaja" cssClass="text-danger"/></strong></small>
					</div>
					<div class="form-group">
						<label for="fecha" class="col-lg-5 col-md-5 control-label">Fecha: </label>
						<div class="col-lg-3 col-md-4">
							<div class="input-group date" id="divFecha" data-date="" data-date-format="dd-mm-yyyy">
								<form:input path="transaccion.transaccionHeader.fecha" id="fecha" cssClass="form-control input-sm validate[required] datepicker" readonly="true"/>
							    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						    </div>
						</div>
				    	<small class="errorForm"><strong><form:errors path="transaccion.transaccionHeader.fecha" cssClass="text-danger"/></strong></small>
					</div>
					<div class="form-group">
						<label for="hora" class="col-lg-5 col-md-5 control-label">Importe: </label>
						<div class="col-lg-2 col-md-3">
							<form:input path="transaccion.transaccionTotal.totalVenta" id="hora" cssClass="form-control input-sm validate[required]"/>
						</div>
						<small class="errorForm"><strong><form:errors path="transaccion.transaccionTotal.totalVenta" cssClass="text-danger"/></strong></small>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buscarCliente" type="button" class="btn btn-primary">Buscar <i class="fa fa-search"></i></button>
							<a id="cancelar" href="<c:url value="/menu"/>" class="btn btn-danger">Cancelar <i class="fa fa-times"></i></a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#ticketForm").validationEngine();

			$("#divFecha").datepicker({
				language: "es",
				todayHighlight: true,
				autoclose: true
			});
			
			$("#buscarCliente").click(function() {
				if($("#ticketForm").validationEngine("validate")) {
					$("#page_loader").show();
					$("#page_loader_ticket_content").show();
					$("#ticketForm").submit();
				}
			});
		});
	</script>
	<script src="<c:url value="/resources/js/login/login.js" />"></script>	
</body>
</html>