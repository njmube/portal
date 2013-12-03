<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<html>
<head>
<title>Facturacion Corporativo</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación en Línea <span class="label label-primary">@</span>
			</h2>
			<hr>
			<div class="well col-md-offset-1 col-md-10">
				<div class="table-responsive">
					<display:table id="elem" name="lista" class="table table-hover table-striped">
						<display:column title="#">${elem_rowNum}</display:column>
						<display:column title="Nombre" property="nombre" />
						<display:column title="Password" property="password" />
					</display:table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>