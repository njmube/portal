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
	
	$("#crearCliente").click(function(){
		var rfc = $("#rfc").val();
		var nombre = $("#nombre").val();
		var urlContinue = contextPath + "/clienteForm?rfc=" + rfc + "&nombre=" + nombre;
		location.href = urlContinue;
	});
	
	$("#receptorFormPortal").validationEngine();

		
});