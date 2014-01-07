
$(document).ready(function(){
	$("#pais").change(function() {
		$(this).loadEstados(this, $("#estado"));
	});
	 $("#establecimientoForm").validate({
		 rules: {
			 txtPassword : {
				 required : true,
				 minlength : 5
			 		},
			 txtConfirmarPassword: {
				 requiered : true,
				 minlength : 5,
				 equalTo : "#txtPassword"
			 		}
		 		},
		 messages: {
			 txtPassword : {
				required: "Por favo introduzca el password",
				minlength: "Mínimo 5 caracteres"
			 },
			 txtConfirmarPassword: {
				required: "Vueve a introducir la contraseña",
				minlength: "Mínimo 5 caracteres",
				equalTo: "Las contraseñas no coinciden. Favor de verificar"
			 }
		 }
	 });
});
