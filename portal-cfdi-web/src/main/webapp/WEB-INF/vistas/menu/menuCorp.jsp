<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title>Facturacion Corporativo</title>
<script type="text/javascript">
// $(document).ready(function() {
// 	$("#reloadPage").click(function(){
// 		$.ajax({
// 			url: "<c:url value='/cfdi/documentos?ajax=true'/>",
// 			type: "GET",
// 			success: function(response) {
// 				$(".table-responsive").html(response);
// 			}
// 		});
// 	});
	
// 	$(".sortable").each(function() {
// 		$(this).click(function(event) {
// 			$.ajax({
// 				url : $(event.target).find("a").attr("href"),
// 				type : "GET",
// 				success : function(response) {
// 					$(".table-responsive").html(response);
// 				}
// 			});
// 		});
// 	});
// });
</script>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación en Línea - <span class="text-info">Corporativo</span> <span class="label label-primary">@</span>
			</h2>
			<hr>
			<div class="col-md-offset-1 col-md-10">
				<div class="panel panel-danger">
					<div class="panel-heading">
						Documentos Pendientes por Facturar
						<a href="<c:url value="/menuCorp"/>" class="pull-right btn btn-primary btn-xs">
							Recargar <span class="glyphicon glyphicon-refresh"></span>
						</a>
					</div>
<%-- 					<jsp:include page="prueba.jsp"></jsp:include> --%>
					<c:set var="title" value="Nombre <span class='glyphicon glyphicon-sort'></span>"/>
					<div class="table-responsive">
						<display:table id="elem" name="documentos"
 							class="table table-hover table-striped table-condensed"
 							requestURI="/menuCorp" pagesize="10"> 
 							<display:column title="#" sortable="true">${elem_rowNum}</display:column>
 							<display:column title="${title}" property="nombre" sortable="true" />
 							<display:column title="Generar Factura" headerClass="text-primary text-center" class="text-center">
 								<a href="<c:url value="/cfdi/menu/${elem_rowNum}"/>" class="btn btn-xs btn-success"><span class="glyphicon glyphicon-print"></span></a>
 							</display:column>
 						</display:table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>