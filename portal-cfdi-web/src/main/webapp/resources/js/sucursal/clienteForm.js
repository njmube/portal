$(document).ready(function() {
	
	$("#clienteForm").validationEngine();
	$("#clienteCorregirForm").validationEngine();	
	
	if($("#pais option:selected").val() > 1){			
		$("#rfc").attr('readonly', true);		
	}
	
	$("#continuar").click(function() {
		$("#agregar").hide();
		$("input[type=text]").attr("readonly", true);
		$("select").attr("disabled", true);
		$("#botones2").show();
		$("#botones1").hide();
		$("#tblDireccion tbody > tr").find("#delete").attr("disabled", true);
	});
	
	$("#corregir").click(function() {
		$("#agregar").show();
		$("input[type=text]").attr("readonly", false);
		if($("#pais option:selected").val() > 1){			
			$("#rfc").attr('readonly', true);
		}
		$("select").attr("disabled", false);
		$("#botones1").show();
		$("#botones2").hide();
		$("#tblDireccion tbody > tr").find("#delete").attr("disabled", false);
		
		$(".formError").fadeOut(150, function() {
            $(this).parent('.formErrorOuter').remove();
            $(this).remove();
        });

	});
	
	$("#guardar").click(function() {
		$("select").removeAttr("disabled", false);
	});	
	
	aux = 1;
	
	$(document.body).on('click',"#delete",function(){		
		$(this).parent().parent().remove();
		
		var trSize = $("#tblDireccion tbody > tr").size();
		
		if(trSize == 1) {
			$("#pais").removeAttr("disabled");
		}
		aux --;		
	});
	
	$("#agregar").click(function() {
		var opPais = "<option value=" +$("#pais option:selected").val()+ ">" 
			+ $("#pais option:selected").text() + "</option>" ;
		var opEstado = $("#estado").html();	
		
		if($("#clienteForm").validationEngine('validate')) {
			
			var trSize = $("#tblDireccion tbody > tr").size();						
			
			if(trSize >= 1) {
				$("#pais").attr("disabled", true);
			}
			
			var tr = "<tr>"
			+ "<td width=\'100px\'><input id=\'calle"+ aux +"\' name=\'domicilios["+ aux +"].calle\' class=\'form-control input-xsm validate[required]\' type=\'text\'></td>"
			+ "<td width=\'65px\'><input id=\'noExterior"+ aux +"\' name=\'domicilios["+ aux +"].noExterior\' class=\'form-control input-xsm validate[required, integer, minSize[1]]\' type=\'text\'></td>"
			+ "<td width=\'65px\'><input id=\'noInterior"+ aux +"\' name=\'domicilios["+ aux +"].noInterior\' class=\'form-control input-xsm validate[integer]\' type=\'text\'></td>"		
			+ "<td width=\'100px\'>"
		  	+ "<select class=\'form-control-xsm validate[required]\' id=\'pais"+ aux +"\' name=\'domicilios["+ aux +"].estado.pais.id\'>"
	  		+ opPais
			+ "</select>"
			+ "</td>"
			+ "<td width=\'100px\'>"
		  	+ "<select class=\'form-control-xsm validate[required]\' id=\'estado"+ aux +"\' name=\'domicilios["+ aux +"].estado.id\'>"
	  		+ opEstado
			+ "</select>"
			+ "</td>"
			+ "<td><input id=\'municipio"+ aux +"\' name=\'domicilios["+ aux +"].municipio\' class=\'form-control input-xsm validate[required]\' type=\'text\'></td>"
			+ "<td width=\'200px\'><input id=\'colonia"+ aux +"\' name=\'domicilios["+ aux +"].colonia\' class=\'form-control input-xsm validate[required]\' type=\'text\'></td>"
			+ "<td width=\'70px\'><input id=\'codigoPostal"+ aux +"\' name=\'domicilios["+ aux +"].codigoPostal\' class=\'form-control input-xsm validate[required, custom[onlyNumberSp], maxSize[5], minSize[5]]\' type=\'text\'></td>"
	//		+ "<td><input id=\'referencia" + aux +"\' name=\'domicilios["+ aux +"].referencia\' class=\'form-control input-xsm\' type=\'text\'></td>"
	//		+ "<td><input id=\'localidad" + aux +"\' name=\'domicilios["+ aux +"].localidad\' class=\'form-control input-xsm\' type=\'text\'></td>"				
			+ "<td><button id=\'delete\' type=\'button\' class=\'btn btn-danger btn-xs\'><span class=\'glyphicon glyphicon-trash\'></span></button></td>"
			+ "</tr>";
			
			$("#tblDireccion tbody").append(tr);
			
			aux++;
		}
	});
	
$("#agregarCorregir").click(function() {
		
		var opPais = $("#pais").html();
		var opEstado = $("#estado").html();
		
		var idPais = $("#pais option:selected").val();
		var idEstado = $("#estado option:selected").val();
		
		if($("#clienteCorregirForm").validationEngine('validate')){
			
			var trSize = $("#tblDireccion tbody > tr").size();						
			
			if(trSize >= 1) {
				$("#pais").attr("disabled", true);
				aux = trSize;
			} else {				
				aux = 0;
			}
			
			var tr = "<tr>"
			+ "<td width=\'100px\'>" 
			+ "<input id=\'calle"+ aux +"\' name=\'domicilios["+ aux +"].calle\' class=\'form-control input-xsm validate[required]\' type=\'text\'>"
			+ "<input id=\'id\' name=\'domicilios["+ aux +"].id\' type=\'hidden\'>"
			+ "</td>"
			+ "<td width=\'65px\'><input id=\'noExterior"+ aux +"\' name=\'domicilios["+ aux +"].noExterior\' class=\'form-control input-xsm validate[required, integer, minSize[1]]\' type=\'text\'></td>"
			+ "<td width=\'65px\'><input id=\'noInterior"+ aux +"\' name=\'domicilios["+ aux +"].noInterior\' class=\'form-control input-xsm validate[integer]\' type=\'text\'></td>"		
			+ "<td width=\'100px\'>"
			+ "<input type=\'hidden\' name=\'domicilios["+ aux +"].estado.pais.id\' id=\'paisOculto\' value=\'"+ idPais +"\'/>"
		  	+ "<select class=\'form-control-xsm validate[required]\' id=\'pais"+ aux +"\' name=\'domicilios["+ aux +"].estado.pais.id\'>"
	  		+ opPais
			+ "</select>"
			+ "</td>"
			+ "<td width=\'100px\'>"
			+ "<input type=\'hidden\' name=\'domicilios["+ aux +"].estado.id\' id=\'estadoOculto\' value=\'"+ idEstado +"\'/>"
		  	+ "<select class=\'form-control-xsm validate[required] estado\' id=\'estado"+ aux +"\' name=\'domicilios["+ aux +"].estado.id\'>"
	  		+ opEstado
			+ "</select>"
			+ "</td>"
			+ "<td><input id=\'municipio"+ aux +"\' name=\'domicilios["+ aux +"].municipio\' class=\'form-control input-xsm validate[required]\' type=\'text\'></td>"
			+ "<td width=\'200px\'><input id=\'colonia"+ aux +"\' name=\'domicilios["+ aux +"].colonia\' class=\'form-control input-xsm validate[required]\' type=\'text\'></td>"
			+ "<td width=\'70px\'><input id=\'codigoPostal"+ aux +"\' name=\'domicilios["+ aux +"].codigoPostal\' class=\'form-control input-xsm validate[required, custom[onlyNumberSp], maxSize[5], minSize[5]]\' type=\'text\'></td>"
	//		+ "<td><input id=\'referencia\' name=\'domicilios["+ aux +"].referencia\' class=\'form-control input-xsm\' type=\'text\'></td>"
	//		+ "<td><i/resources/js/sucursal/clienteForm.jsnput id=\'localidad\' name=\'domicilios["+ aux +"].localidad\' class=\'form-control input-xsm\' type=\'text\'></td>"				
			+ "<td><button id=\'delete\' type=\'button\' class=\'btn btn-danger btn-xs\'><span class=\'glyphicon glyphicon-trash\'></span></button></td>"
			+ "</tr>";
			
			$("#tblDireccion tbody").append(tr);
			
			aux++;
		}
	});
	
	$(document.body).on("change", "#pais", function() {
		var tr = $(this).parent().parent();
		$(this).loadEstados(this, $(tr).find("#estado"));
	});
	
	$("#personaFisica").click(function() {
		$("input:hidden[id=tipoPersona]").val("1");
		if($("#personaMoral").is(":checked")) {
			$("#rfc").removeClass("validate[required, custom[rfcMoral]]");
			$("#personaMoral").prop('checked', false);
			$("#rfc").addClass("validate[required, custom[rfcFisica]]");
		}		
	});
	
	$("#personaMoral").click(function() {
		$("input:hidden[id=tipoPersona]").val("2");
		if($("#personaFisica").is(":checked")) {			
			$("#rfc").removeClass("validate[required, custom[rfcFisica]]");
			$("#personaFisica").prop('checked', false);
			$("#rfc").addClass("validate[required, custom[rfcMoral]]");
			var formName = $(this).closest("form").attr('id');
			if(formName === 'clienteForm') {
				$("#clienteForm").validate().element("#rfc");				
			} else {
				$("#clienteCorregirForm").validate().element("#rfc");
			}
		}
	});
	
	autoClosingAlert(".errorForm", 3500);
	autoClosingAlert("#errorMessage", 3500);	
	
});
