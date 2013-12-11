<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>Ingresa tu RFC</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#buscarCliente").click(function(){
		$.ajax({
			url: "<c:url value='/listaClientes?ajax=true&rfc=" + $("#rfc").val() + "'/>",
			type: "GET",
			success: function(response) {
				$("#listClientesPage").html(response);
			}
		});
	});
});
</script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>Facturación Electrónica<span class="text-info"> - Sucursal ${sessionScope.establecimiento.nombre}</span> <span class="label label-primary">@</span></h2>
			<blockquote>
				<p class="text-info">Ingresa el RFC ó Nombre del Cliente.</p>
			</blockquote>
			<hr>
			<div class="well col-md-offset-2 col-md-8">
				<form:form id="receptorForm" action="muestraReceptor" method="GET" modelAttribute="receptor" cssClass="form-horizontal" role="form">
					<div class="form-group">
						<label for="rfc" class="col-lg-4 control-label">RFC: </label>
						<div class="col-lg-5">
							<form:input path="rfc" id="rfc" cssClass="form-control input-sm" />
						</div>
					</div>
					<div class="form-group">
						<label for="nombre" class="col-lg-4 control-label">Nombre ó Razón Social</label>
						<div class="col-lg-8">
							<form:input path="nombre" id="nombre" cssClass="form-control input-sm"/>
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="centered">
							<button id="buscarCliente" type="button" class="btn btn-primary">Buscar <span class="glyphicon glyphicon-search"></span></button>
						</div>
					</div>
				</form:form>
				<div id="listClientesPage" class="container">
					<jsp:include page="listaClientes.jsp"/>
				</div>
			</div>
		</div>
	</div>
</body>
</html>