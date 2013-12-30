$(function() {
	function stripTrailingSlash(str) {
		if (str.substr(-1) == '/') {
			return str.substr(0, str.length - 1);
		}
		return str;
	}

	var url = window.location.pathname;
	var activePage = stripTrailingSlash(url);

	$('.nav li a').each(function() {
		var currentPage = stripTrailingSlash($(this).attr('href'));

		if (activePage == currentPage) {
			$(this).parent().addClass('active');
		}
	});
});

$(document).ready(function() {
	$(document.body).on('focusout',"input[type=text]",function(){
		$(this).val(function () {
			return this.value.toUpperCase();
		})
	});
});