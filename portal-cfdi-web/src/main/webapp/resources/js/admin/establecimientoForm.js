	
	$(document).ready(function(){
		$("#pais").change(function() {
			$(this).loadEstados(this, $("#estado"));
		});
		
		$("#establecimientoForm").validationEngine();
	});





