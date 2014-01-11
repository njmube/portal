<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:if test="${!emptyList}">
	<c:choose>
		<c:when test="${not empty documentos}">
			<div class="table-responsive">
				<display:table htmlId="documentos" id="documento" name="${documentos}"
					class="table table-hover table-striped table-condensed"
					requestURI="">
					<display:column title="#" headerClass="text-primary">${documento_rowNum}</display:column>
					<display:column title="Nombre Documento" property="nombre" headerClass="text-primary text-center" class="text-center" />
					<display:column title="PDF" headerClass="text-primary text-center" class="text-center">
						<sec:authorize access="hasAnyRole('ROLE_SUC', 'ROLE_CORP')">
							<a href="<c:url value="/documentoDownload/${documento.establecimiento.id}/${documento.nombre}/pdf" />" class="btn btn-xs btn-danger">
								Descargar <i class="fa fa-download"></i>
							</a>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a href="<c:url value="/portal/cfdi/documentoDownload/${documento.establecimiento.id}/${documento.nombre}/pdf" />" class="btn btn-xs btn-danger">
								Descargar <i class="fa fa-download"></i>
							</a>
						</sec:authorize>
					</display:column>
					<display:column title="XML" headerClass="text-primary text-center" class="text-center">
						<sec:authorize access="hasAnyRole('ROLE_SUC', 'ROLE_CORP')">
							<a href="<c:url value="/documentoDownload/${documento.establecimiento.id}/${documento.nombre}/xml"/>" class="btn btn-xs btn-primary">
								Descargar <i class="fa fa-download"></i>
							</a>						
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a href="<c:url value="/portal/cfdi/documentoDownload/${documento.establecimiento.id}/${documento.nombre}/xml"/>" class="btn btn-xs btn-primary">
								Descargar <i class="fa fa-download"></i>
							</a>
						</sec:authorize>
					</display:column>
					<display:column title="Enviar Docs." headerClass="text-primary text-center" class="text-center">
						<sec:authorize access="hasAnyRole('ROLE_SUC', 'ROLE_CORP')">
							<input type="hidden" id="idEstab" value="${documento.establecimiento.id}">
							<input type="hidden" id="fileName" value="${documento.nombre}">
							<button type="button" id="enviarDocumento" class="btn btn-xs btn-success" data-toggle="modal" data-target="#enviaDocsModal">Enviar <span class="glyphicon glyphicon-envelope"></span></button>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<input type="hidden" id="idEstab" value="${documento.establecimiento.id}">
							<input type="hidden" id="fileName" value="${documento.nombre}">
							<button type="button" id="enviarDocumento" class="btn btn-xs btn-success" data-toggle="modal" data-target="#enviaDocsModal">Enviar <span class="glyphicon glyphicon-envelope"></span></button>
						</sec:authorize>
					</display:column>
				</display:table>
			</div>
			<script type="text/javascript">
				$(document).ready(function() {
					$("#envioDocForm").validationEngine();
					
					$(document.body).on("click", "#enviarDocumento", function() {
						var tr = $(this).parent().parent();

						$("#idEstabModal").val(tr.find("#idEstab").val());
						$("#fileNameModal").val(tr.find("#fileName").val());
					});
					
					$(document.body).on("click", "#closeModal", function() {
						$("#email").val("");
					});
					
					$("#enviaMail").click(function() {
						var params = "idEstab=" + $("#idEstabModal").val() 
							+ "&fileName=" + $("#fileNameModal").val() + "&email=" + $("#email").val();  
						
						console.log(params);
						
						$("#enviaDocsModal").attr('aria-hidden', true);
						
						if($("#envioDocForm").validationEngine("validate")) {
							$.ajax({
								url: contextPath + "/portal/cfdi/documentoEnvio?ajax=true",
								data: params,
								type: "GET",								
								success : function(response) {
									$("#closeModal").click();
									
									message = "";
									messageClass = "";
									
									if(response) {
										message = "El correo fue enviado correctamente.";
										messageClass = "success";
									} else {
										message = "Error al enviar el correo electronico.";
										messageClass = "danger";
									}
									var messageContainer = "<div class=\'col-md-offset-2 col-md-8\'> " +
										"<div class=\'alert alert-" + messageClass + " alert-dismissable\'>" +
										"<button type=\'button\' class=\'close\' data-dismiss=\'alert\' aria-hidden=\'true\'>&times;</button>" +
										"Mensaje: " +
										"<br><br> <strong>" + message + "</strong>" +
										"</div>" +
										"</div>";
										
									$("#message_response").html(messageContainer);
									$("#email").val("");
									autoClosingAlert("div.alert", 2500);
									
								}
							});
						}
						
					});
					
				});
			</script>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<div class="centered">
					<p class="alert alert-info">El RFC proporcionado no tiene ninguna factura relacionada.</p>
				</div>
			</div>
		</c:otherwise>		
	</c:choose>
</c:if>
<script src="<c:url value="/resources/js/documento/documento.js" />"></script>

<div class="modal fade" id="enviaDocsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" id="closeModal" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title text-primary">Enviar Documentos</h4>
      </div>
      <div class="modal-body">
        <form action="#" id="envioDocForm" method="post" class="form-horizontal">
        	<blockquote>
        		<p>Proporcione un correo electronico para enviar los documentos.</p>
        	</blockquote>
        	<input type="hidden" id="idEstabModal">
			<input type="hidden" id="fileNameModal">
        	<div class="form-group">
				<label for="email" class="col-lg-4 control-label">Email: </label>
				<div class="col-lg-5">
		        	<input type="text" id="email" name="email" class="form-control input-sm validate[required, custom[email]] noUpper"/>
				</div>
			</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" id="closeModal" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <button type="button" id="enviaMail" class="btn btn-primary">Enviar</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>
