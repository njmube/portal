<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<title>. : Modatelas - <decorator:title default="Main" /> : .
</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/modatelas-style.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/bootstrap-datatables.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datepicker.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/validationEngine.jquery.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/prettyLoader.css" />" />
<script
	src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery/jquery.dataTables.min.js" />"></script>

<script src="<c:url value="/resources/js/jquery/jquery.validate.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery/jquery.validationEngine.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery/jquery.validationEngine-es.js" />"></script>

<script src="<c:url value="/resources/js/datatable/datatable.js" />"></script>
<script
	src="<c:url value="/resources/js/datepicker/bootstrap-datepicker.js" />"></script>
<script
	src="<c:url value="/resources/js/datepicker/bootstrap-datepicker.es.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery/jquery.prettyLoader.js" />"></script>
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";

	function autoClosingAlert(selector, delay) {
		var alert = $(selector).alert();
		window.setTimeout(function() {
			alert.fadeOut("slow");
		}, delay);
	}	
	$(function() {
		$.prettyLoader();
		autoClosingAlert("div.alert", 2500);
	});
</script>
<decorator:head />
</head>
<body>
	<div class="wrap">
		<!-- HEADER -->
		<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<c:url value="/menuPage" />">
						Facturación en Línea <span class="glyphicon glyphicon-globe"></span>
					</a>
				</div>

				<div class="collapse navbar-collapse pull-right">
					<sec:authorize access="isAnonymous()">
						<c:set var="urlMenu" value="/portal/cfdi/menu"/>	
					</sec:authorize>
					<sec:authorize
							access="hasAnyRole('ROLE_SUC', 'ROLE_CORP', 'ROLE_ADMIN')">
						<c:set var="urlMenu" value="/menuPage"/>		
					</sec:authorize>
					<ul class="nav navbar-nav">

						<li class="dropdown"><sec:authorize access="hasAnyRole('ROLE_ADMIN')">
<!-- 								<a class="dropdown-toggle" data-toggle="dropdown">Catálogos <b class="caret"></b></a> -->
								<a class="dropdown-toggle" data-toggle="dropdown">Catálogos <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<c:url var="logoutUrl" value="/perform_logout" />
									<c:url var="catalogoEstablecimiento" value="/catalogoEstablecimiento" />
									<li><a href="#"><span class="glyphicon "></span>Formas de pago</a></li>
									<li><a href="#"><span class="glyphicon "></span>Condiciones de pago</a></li>
									<li><a href="#"><span class="glyphicon "></span>Iva</a></li>
									<li><a href="${catalogoEstablecimiento}"><span class="glyphicon "></span>Establecimiento</a></li>
								</ul>
							</sec:authorize></li>
						<c:if test="${!isLoginPage}">	
							<li><a href="<c:url value="${urlMenu}" />">Menú Principal
									<span class="glyphicon glyphicon-home"></span>
							</a></li>
						</c:if>
					</ul>
					<sec:authorize
						access="hasAnyRole('ROLE_SUC', 'ROLE_CORP', 'ROLE_ADMIN')">
						<div class="navbar-right">
							<div class="btn-group">
								<button type="button"
									class="btn btn-warning btn-sm dropdown-toggle"
									data-toggle="dropdown">
									<span class="glyphicon glyphicon-user"></span>
									${fn:toUpperCase(sessionScope.establecimiento.nombre)} <span
										class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<c:url var="logoutUrl" value="/perform_logout" />
									<li><a href="${logoutUrl}"><span
											class="glyphicon glyphicon-log-out"></span> Cerrar sesión</a></li>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		<div class="logo-header">
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<div class="logo">
							<a href="#"><img id="logoImg"
								src="<c:url value="/resources/img/modatelas_logo.jpg" />"
								alt="Logo"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- CONTENT -->
		<div class="content">
			<decorator:body />
		</div>
	</div>

	<!-- FOOTER -->
	<div id="footer" class="footer">
		<div class="row">
			<p class="credit">
				<span class="glyphicon glyphicon-registration-mark"></span> <strong>2013
					Modatelas S.A.P.I de C.V.</strong>
			</p>
			<p class="credit">
				<!-- 				<a href="#"><strong>Acerca de </strong><span -->
				<!-- 					class="glyphicon glyphicon-question-sign"></span></a> &middot;  -->
				<a href="#"><strong> Ir arriba </strong><span
					class="glyphicon glyphicon-circle-arrow-up"></span></a>
			</p>
		</div>
	</div>
	<script src="<c:url value="/resources/js/vendor/bootstrap.min.js" />"></script>
	<script
		src="<c:url value="/resources/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js" />"></script>
	<script src="<c:url value="/resources/js/main/modatelas.js"/>"></script>
</body>
</html>
