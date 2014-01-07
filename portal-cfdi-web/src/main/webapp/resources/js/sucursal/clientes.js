$(document).ready(function() {
	$("#buscarCliente").click(function(){
		var rfc = $("#rfc").val();
		var nombre = $("#nombre").val();
		$.ajax({
			url: contextPath + "/listaClientes?ajax=true",
			data: "rfc=" + rfc + "&nombre=" + nombre,
			type: "post",
			success: function(response) {
				$("#listClientesPage").html(response);
			}
		});
	});
	
	$("#receptorFormPortal").validationEngine();

		
});