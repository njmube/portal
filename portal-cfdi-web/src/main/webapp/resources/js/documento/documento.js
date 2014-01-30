$(document).ready(function() {
	
	$("#documentoForm").validationEngine();
	
	var today = new Date(); 
	var beforTreeMonts = GetNewDate(new Date(), 4, false);
	var fechaFin = $("#divFechaFin");
	
	$("#divFechaInit").datepicker({
		startDate: beforTreeMonts, 
		endDate: today,
		language: "es",
		todayHighlight: true,
		autoclose: true
	}).on('changeDate', function(ev){
		
		$("#fechaFin").val("");
		fechaFin.datepicker('remove');
		
		fechaFin.datepicker({
			startDate: new Date(ev.date.valueOf()), 
			endDate: '-0m',
			language: "es",
			todayHighlight: true,
			autoclose: true 
			});
		});			
	
//	$("#fechaInit").datepicker({startDate: beforTreeMonts, endDate: today})
//		.on('changeDate', function(ev){
//			$("#fechaFin").val("");
//			$("#fechaFin").datepicker('remove');
//			$("#fechaFin").datepicker({
//				startDate: new Date(ev.date.valueOf()),
//				endDate: '-0m'}).on('changeDate', function(ev){
//				$("#fechaFin").datepicker('hide');
//			});			
//    });
	
	$("#buscarDocumento").click(function() {
		if($("#documentoForm").validationEngine('validate')) {
			var rfc = $("#rfc").val();
			var fchInicial = $("#fechaInit").val();
			var fchFinal = $("#fechaFin").val();
			
			var params = "rfc=" + rfc + "&fechaInicial=" + fchInicial + "&fechaFinal=" + fchFinal;
			
			$.ajax({
				url: contextPath + "/portal/cfdi/listaDocumentos?ajax=true",
				data: params,
				type: "GET",
				success: function(response) {
					$("#listDocumentosPage").html(response);
				}
			});
		}
	});
});

function GetNewDate(date, monCount, isAdd) {
	monCount = monCount - 1; 
	
	var d = date;
	
	if (isAdd) {
	    d.setMonth(d.getMonth() + monCount);
	} else {
	    d.setMonth(d.getMonth() - monCount);
	}
	
	return d;
}