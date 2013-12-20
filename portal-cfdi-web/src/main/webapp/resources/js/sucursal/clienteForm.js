$(document).ready(function() {
	$("#continuar").click(function() {
		$("#agregar").hide();
		$("input[type=text]").attr("readonly", true);
		$("select").attr("disabled", true);
		$(this).hide();
		$("#botones").show();
		$("#tblDireccion tbody > tr").find("#delete").attr("disabled", true);
	});
	
	$("#corregir").click(function() {
		$("#agregar").show();
		$("input[type=text]").attr("readonly", false);
		$("select").attr("disabled", false);
		$("#continuar").show();
		$("#botones").hide();
		$("#tblDireccion tbody > tr").find("#delete").attr("disabled", false);
	});
	
	$("#guardar").click(function() {
		$("select").removeAttr("disabled", false);
	});	
	
	aux = 1;
	
	$(document.body).on('click',"#delete",function(){		
		$(this).parent().parent().remove();
		aux --;		
	});
	
	$("#agregar").click(function() {
		
		var opPais = $("#pais").html();
		var opEstado = $("#estado").html();	
		
		var tr = "<tr>"
		+ "<td width=\'100px\'><input id=\'calle\' name=\'domicilios["+ aux +"].calle\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'65px\'><input id=\'noExterior\' name=\'domicilios["+ aux +"].noExterior\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'65px\'><input id=\'noInterior\' name=\'domicilios["+ aux +"].noInterior\' class=\'form-control input-xsm\' type=\'text\'></td>"		
		+ "<td width=\'100px\'>"
	  	+ "<select class=\'form-control-xsm\' id=\'pais\' name=\'domicilios["+ aux +"].estado.pais.id\'>"
  		+ opPais
		+ "</select>"
		+ "</td>"
		+ "<td width=\'100px\'>"
	  	+ "<select class=\'form-control-xsm\' id=\'estado\' name=\'domicilios["+ aux +"].estado.id\'>"
  		+ opEstado
		+ "</select>"
		+ "</td>"
		+ "<td><input id=\'municipio\' name=\'domicilios["+ aux +"].municipio\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'200px\'><input id=\'colonia\' name=\'domicilios["+ aux +"].colonia\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'70px\'><input id=\'codigoPostal\' name=\'domicilios["+ aux +"].codigoPostal\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td><input id=\'referencia\' name=\'domicilios["+ aux +"].referencia\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td><input id=\'localidad\' name=\'domicilios["+ aux +"].localidad\' class=\'form-control input-xsm\' type=\'text\'></td>"				
		+ "<td><button id=\'delete\' type=\'button\' class=\'btn btn-danger btn-xs\'><span class=\'glyphicon glyphicon-trash\'></span></button></td>"
		+ "</tr>";
		
		$("#tblDireccion tbody").append(tr);
		
		aux++;
	});
	
$("#agregarCorregir").click(function() {
		
		var opPais = $("#pais").html();
		var opEstado = $("#estado").html();
		
		var idPais = $("#pais option:selected").val();
		var idEstado = $("#estado option:selected").val();
		
		var trSize = $("#tblDireccion tbody > tr").size();
		
		if(trSize >= 1) {
			aux = trSize;
		} else {
			aux = 0;
		}
		
		var tr = "<tr>"
		+ "<td width=\'100px\'><input id=\'calle\' name=\'domicilios["+ aux +"].calle\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'65px\'><input id=\'noExterior\' name=\'domicilios["+ aux +"].noExterior\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'65px\'><input id=\'noInterior\' name=\'domicilios["+ aux +"].noInterior\' class=\'form-control input-xsm\' type=\'text\'></td>"		
		+ "<td width=\'100px\'>"
		+ "<input type=\'hidden\' name=\'domicilios["+ aux +"].estado.pais.id\' id=\'paisOculto\' value=\'"+ idPais +"\'/>"
	  	+ "<select class=\'form-control-xsm\' id=\'pais\' name=\'domicilios["+ aux +"].estado.pais.id\'>"
  		+ opPais
		+ "</select>"
		+ "</td>"
		+ "<td width=\'100px\'>"
		+ "<input type=\'hidden\' name=\'domicilios["+ aux +"].estado.id\' id=\'estadoOculto\' value=\'"+ idEstado +"\'/>"
	  	+ "<select class=\'form-control-xsm\' id=\'estado\' name=\'domicilios["+ aux +"].estado.id\'>"
  		+ opEstado
		+ "</select>"
		+ "</td>"
		+ "<td><input id=\'municipio\' name=\'domicilios["+ aux +"].municipio\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'200px\'><input id=\'colonia\' name=\'domicilios["+ aux +"].colonia\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td width=\'70px\'><input id=\'codigoPostal\' name=\'domicilios["+ aux +"].codigoPostal\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td><input id=\'referencia\' name=\'domicilios["+ aux +"].referencia\' class=\'form-control input-xsm\' type=\'text\'></td>"
		+ "<td><input id=\'localidad\' name=\'domicilios["+ aux +"].localidad\' class=\'form-control input-xsm\' type=\'text\'></td>"				
		+ "<td><button id=\'delete\' type=\'button\' class=\'btn btn-danger btn-xs\'><span class=\'glyphicon glyphicon-trash\'></span></button></td>"
		+ "</tr>";
		
		$("#tblDireccion tbody").append(tr);
		
		aux++;
	});
	
	$(document.body).on("change", "#pais", function() {
		var tr = $(this).parent().parent();
		$(this).loadEstados(this, $(tr).find("#estado"));
	});
	
});