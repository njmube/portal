$(document).ready(function(){
	


var passwordParent = $("#txtPassword").parent().parent();
		var passwordConfirmParent = $("#txtConfirmarPassword").parent().parent();
		
		$("#txtPassword").keyup(function (){
			if (!$(this).val() && !$("#txtConfirmarPassword").val() ) {
				removeClass(this, passwordConfirmParent);
			} else 	if ($(this).val() === $("#txtConfirmarPassword").val()) {
				removeErrorAndAddSucces(passwordParent, passwordConfirmParent);
			} else {
				removeSuccesAndAddError(passwordParent, passwordConfirmParent);
			}
		});
		
		$("#txtConfirmarPassword").keyup(function (){
			if (!$(this).val() && !$("#txtPassword").val() ) {
				removeClass(passwordConfirmParent, passwordParent);
			} else	if ($(this).val() === $("#txtPassword").val()) {
				removeErrorAndAddSucces(passwordParent, passwordConfirmParent);
			} else {
				removeSuccesAndAddError(passwordParent, passwordConfirmParent);
			}
		});


	
	function removeClass(field, field2) {
			$(field).parent().parent().removeClass( "has-success has-error" );
			field2.removeClass( "has-success has-error" );
	}
	
	function removeErrorAndAddSucces (field, field2){
		field.removeClass( "has-error " );
		field.addClass( "has-success" );
		field2.removeClass( "has-error" );
		field2.addClass( "has-success" );
	}
	
	function removeSuccesAndAddError (field, field2){
		field.removeClass( "has-success" );
		field.addClass( "has-error" );
		field2.removeClass( "has-success" );
		field2.addClass( "has-error" );
	}

});