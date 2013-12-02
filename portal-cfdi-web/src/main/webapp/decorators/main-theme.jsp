<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<script src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
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
					<a class="navbar-brand" href="<c:url value="/menu" />"> Facturación
						en Línea <span class="glyphicon glyphicon-globe"></span>
					</a>
				</div>
				<div class="collapse navbar-collapse pull-right">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value="/menu" />">Menu Principal <span
								class="glyphicon glyphicon-home"></span>
						</a></li>
						<li><a href="<c:url value="/about" />">Acerca de <span
								class="glyphicon glyphicon-question-sign"></span></a></li>
					</ul>
					<sec:authorize access="hasRole('ROLE_USER')">
						<div class="navbar-right">
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> 
									${sessionScope.sucursal.nombre} <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<c:url var="logoutUrl" value="/j_spring_security_logout" />
									<li><a href="${logoutUrl}"><span class="glyphicon glyphicon-off"></span> Cerrar sesión</a></li>
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
				<a href="#"><strong>Acerca de </strong><span
					class="glyphicon glyphicon-question-sign"></span></a> &middot; <a
					href="#"><strong> Ir arriba </strong><span
					class="glyphicon glyphicon-circle-arrow-up"></span></a>
			</p>
		</div>
	</div>
	<script src="<c:url value="/resources/js/vendor/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js" />"></script>
	<script src="<c:url value="/resources/js/main/modatelas.js"/>"></script>
</body>
</html>
