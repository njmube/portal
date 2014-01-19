<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
$(document).ready(function() {
	$('#clientes').dataTable({
		"sDom": "<'row'<'col-xs-5 col-sm-6'l><'col-xs-7 col-sm-6 text-right'f>r>t<'row'<'col-xs-3 col-sm-4 col-md-5'i><'col-xs-9 col-sm-8 col-md-7 text-right'p>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sProcessing":     "Procesando...",
		    "sLengthMenu":     "Mostrar _MENU_ registros",
		    "sZeroRecords":    "No se encontraron resultados",
		    "sEmptyTable":     "Ningún dato disponible en esta tabla",
		    "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
		    "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
		    "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
		    "sInfoPostFix":    "",
		    "sSearch":         "Buscar:",
		    "sUrl":            "",
		    "sInfoThousands":  ",",
		    "sLoadingRecords": "Cargando...",
		    "oPaginate": {
		        "sFirst":    "Primero",
		        "sLast":     "Último",
		        "sNext":     "Siguiente",
		        "sPrevious": "Anterior"
		    },
		    "oAria": {
		        "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
		        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
		    }
		}
	});
});
</script>
<c:if test="${!emptyList}">
	<br>
	<div class="table-responsive">
		<display:table htmlId="clientes" id="cliente" name="${clientes}"
			class="table table-hover table-striped table-condensed"
			requestURI="/facturaCorp">
			<display:column title="#" property="id" headerClass="text-primary"></display:column>
			<display:column title="RFC" property="rfc" headerClass="text-primary"></display:column>
			<display:column title="Nombre" property="nombre" headerClass="text-primary" />
			<display:column title="Seleccionar" headerClass="text-primary text-center" class="text-center">
				<a href="<c:url value="/confirmarDatos/${cliente.id}"/>" class="btn btn-xs btn-success"><i class="fa fa-share"></i></a>
			</display:column>
		</display:table>
		</div>
</c:if>
