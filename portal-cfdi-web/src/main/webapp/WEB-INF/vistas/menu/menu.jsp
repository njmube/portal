<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Facturación en Línea</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica
				<sec:authorize access="hasRole('ROLE_SUC')">
					<span class="text-info"> - ${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span> <span class="label label-primary">@</span>
					<c:url var="urlPage" value="/buscaTicket"/>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_CORP')">
					<span class="text-info"> - Corporativo</span> <span class="label label-primary">@</span>
					<c:url var="urlPage" value="/facturaCorp"/>
				</sec:authorize>
			</h2>
			<blockquote>
				<p class="text-info">Seleccione una opción.</p>
			</blockquote>
			<hr>

			<div class="well col-md-6 col-md-offset-3 text-center">
				<hr>
				<p>
					<a href="${urlPage}" class="btn btn-success btn-lg" role="button">Generar
						Factura <i class="fa fa-arrow-right"></i>
					</a>
				</p>
				<hr>
				<p>
					<a href="<c:url value="/buscarDocs"/>" class="btn btn-primary btn-lg" role="button">Consultar Facturas
						<i class="fa fa-arrow-right"></i>
					</a>
				</p>
				<hr>
				<sec:authorize access="hasRole('ROLE_SUC')">
					<a href="<c:url value="/cierre"/>" class="btn btn-primary btn-lg" role="button">Cierre
						<i class="fa fa-arrow-right"></i>
					</a>
				</sec:authorize>
			</div>
		</div>
	</div>
</body>
</html>