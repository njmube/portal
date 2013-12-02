function autoClosingAlert(selector, delay) {
	var alert = $(selector).alert();
	window.setTimeout(function() {alert.fadeOut("slow");}, delay);
}

$(document).ready(function(){
	autoClosingAlert("div.alert", 2500);
});