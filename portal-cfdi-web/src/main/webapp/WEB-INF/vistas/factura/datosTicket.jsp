<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Facturación en Línea</title>
</head>
<body>
	<div class="container marketing">
		<div class="main-content">
			<div class="white-panel row">
				<h2>
					Factura Electrónica <span class="label label-primary">@</span>
				</h2>
				<br>
				<p>
					<strong>Instrucciones: </strong>Para generar la factura electrónica
					ingresa los siguientes datos.
				</p>
				<br>
				<div class="well col-md-6 col-md-offset-3">
					<form:form id="formTicket" method="post"
						action="${pageContext.request.contextPath}/cfdi/validarTicket"
						role="form" cssClass="form-horizontal" modelAttribute="ticketHeader">
						<div class="form-group">
							<label for="idTicket" class="col-lg-4 control-label">Número
								de Ticket:</label>
							<div class="col-lg-8">
								<form:input id="idTicket" cssClass="form-control"
									path="idTicket" placeholder="#" />
							</div>
						</div>
						<div class="form-group">
							<label for="idCaja" class="col-lg-4 control-label">Número de Caja:</label>
							<div class="col-lg-8">
								<form:input id="idCaja" cssClass="form-control" 
								path="idCaja" placeholder="#" />
							</div>
						</div>
						<hr>
						<div class="form-group">
							<div class="col-lg-offset-8 col-lg-4">
								<div>
									<button id="generar" type="submit" class="btn btn-warning">
										Validar Ticket</button>
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>