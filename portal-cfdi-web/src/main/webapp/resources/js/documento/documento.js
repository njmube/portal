$(document).ready(function() {
	
	$("#documentoForm").validationEngine();
	
	$("#buscarDocumento").click(function() {
		if($("#documentoForm").validationEngine('validate')) {
			var rfc = $("#rfc").val();
			$.ajax({
				url: contextPath + "/portal/cfdi/listaDocumentos?ajax=true",
				data: "rfc=" + rfc,
				type: "GET",
				success: function(response) {
					$("#listDocumentosPage").html(response);
				}
			});
		}
	});
});