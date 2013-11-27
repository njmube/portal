<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					Iniciar Sesión <span class="label label-primary">@</span>
				</h2>
				<br>

				<div class="well col-md-6 col-md-offset-3">
					<c:url var="loginUrl" value="/login" />
					<form:form id="sucForm" method="post" role="form"
						cssClass="form-horizontal" modelAttribute="sucursal" action="${loginUrl}">
						<div class="form-group">
							<label for="suc" class="col-lg-4 control-label">Sucursal:</label>
							<div class="col-lg-8">
								<form:input path="suc" cssClass="form-control" id="suc"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-8 col-lg-4">
								<button id="ingresar" type="submit" class="btn btn-warning">
									Ingresar</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>