(function($){
   $.fn.loadEstados = function(value, target) {
      var val = value.value;
      var opDefault = "<option value =\"\">- Seleccione una opción -</option>";
      target.html(opDefault);
      
      if(val !== null || val !== "") {
    	  
    	  var url = contextPath + "/portal/cfdi/listaEstados?ajax=true";
    	  $.ajax ({
    		url : url,
    		data : {pais : val},
    		type : "post",
    		success : function(data) {
    			$.each(data, function(index, item) {
    				var opcion = "<option value =" + item.id + ">" + item.nombre + "</option>";
    				target.append(opcion);
    			});
    		},
    	  });
      } else {
    	  target.append(opDefault);
      }
   }; 
})(jQuery);