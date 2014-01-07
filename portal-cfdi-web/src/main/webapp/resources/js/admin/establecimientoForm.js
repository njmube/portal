
$(document).ready(function(){
	$("#pais").change(function() {
		$(this).loadEstados(this, $("#estado"));
	});
	
	
	
	$("#establecimientoForm").validationEngine();
	
	$("#txtConfirmarPassword").keyup(function (){
		
		if (!$(this).val() && !$("#txtPassword").val() ) {
			$(this).parent().parent().removeClass( "has-success" );
			$("#txtPassword").parent().parent().removeClass( "has-success" );
			$(this).parent().parent().removeClass( "has-error" );
			$("#txtPassword").parent().parent().removeClass( "has-error" );
		} else	if ($(this).val() === $("#txtPassword").val()) {
			$(this).parent().parent().removeClass( "has-error" );
			$("#txtPassword").parent().parent().removeClass( "has-error" );
			$(this).parent().parent().addClass( "has-success" );
			$("#txtPassword").parent().parent().addClass( "has-success" );
			
			
		} else {
			$(this).parent().parent().removeClass( "has-success" );
			$("#txtPassword").parent().parent().removeClass( "has-success" );
			$(this).parent().parent().addClass( "has-error" );
			$("#txtPassword").parent().parent().addClass( "has-error" );
		}
		
	});
	
	$("#txtPassword").keyup(function (){
		
		if (!$(this).val() && !$("#txtConfirmarPassword").val() ) {
			$(this).parent().parent().removeClass( "has-success" );
			$("#txtConfirmarPassword").parent().parent().removeClass( "has-success" );
			$(this).parent().parent().removeClass( "has-error" );
			$("#txtConfirmarPassword").parent().parent().removeClass( "has-error" );
		} else 	if ($(this).val() === $("#txtConfirmarPassword").val()) {
			
			
			$(this).parent().parent().removeClass( "has-error" );
			$("#txtConfirmarPassword").parent().parent().removeClass( "has-error" );
			$(this).parent().parent().addClass( "has-success" );
			$("#txtConfirmarPassword").parent().parent().addClass( "has-success" );
		} else {
			$(this).parent().parent().removeClass( "has-success" );
			$("#txtConfirmarPassword").parent().parent().removeClass( "has-success" );
			$(this).parent().parent().addClass( "has-error" );
			$("#txtConfirmarPassword").parent().parent().addClass( "has-error" );
		}
		
		
	});
	
});



