<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type="text/javascript">
	$(document).ready(function() {
		$(".sortable").each(function() {
			$(this).click(function(event) {
				$.ajax({
					url : $(event.target).find("a").attr("href"),
					type : "GET",
					success : function(response) {
						$(".table-responsive").html(response);
					}
				});
			});
		});
	});
</script>
<c:set var="title"
	value="Nombre <span class='glyphicon glyphicon-sort'></span>" />
<div class="table-responsive">
	<display:table id="elem" name="documentos"
		class="table table-hover table-striped table-condensed"
		requestURI="/cfdi/documentos" pagesize="10">
		<display:column title="#" sortable="true">${elem_rowNum}</display:column>
		<display:column title="${title}" property="nombre" sortable="true" />
		<display:column title="Generar Factura"
			headerClass="text-primary text-center" class="text-center">
			<a href="<c:url value="/cfdi/menu/${elem_rowNum}"/>"><span
				class="glyphicon glyphicon-print"></span></a>
		</display:column>
	</display:table>
</div>
