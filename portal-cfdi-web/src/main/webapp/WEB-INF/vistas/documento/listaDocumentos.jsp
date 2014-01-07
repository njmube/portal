<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:if test="${!emptyList}">
	<c:choose>
		<c:when test="${not empty documentos}">
			<div class="table-responsive">
				<display:table htmlId="documentos" id="documento" name="${documentos}"
					class="table table-hover table-striped table-condensed"
					requestURI="">
					<display:column title="#" property="id" headerClass="text-primary"></display:column>
					<display:column title="Nombre Documento" property="nombre" headerClass="text-primary text-center" class="text-center" />
<%-- 					<display:column title="Serie" property="comprobante.serie" headerClass="text-primary"></display:column> --%>
<%-- 					<display:column title="Folio" property="comprobante.folio" headerClass="text-primary" /> --%>
					<display:column title="PDF" headerClass="text-primary text-center" class="text-center">
						<button type="button" id="descargaDocPdf" class="btn btn-xs btn-danger">Descargar <span class="glyphicon glyphicon-download-alt"></span></button>
					</display:column>
					<display:column title="XML" headerClass="text-primary text-center" class="text-center">
						<button type="button" id="descargaDocXml" class="btn btn-xs btn-primary">Descargar <span class="glyphicon glyphicon-download-alt"></span></button>
					</display:column>
					<display:column title="Reenviar" headerClass="text-primary text-center" class="text-center">						
						<button type="button" id="reenviarDocumento" class="btn btn-xs btn-primary">Reenviar <span class="glyphicon glyphicon-envelope"></span></button>
					</display:column>
				</display:table>
			</div>
			<c:url value="/documentoDownload/${documento.establecimiento.id}/${documento.nombre}/pdf" var="urlDocumentoPdf"/>
			<c:url value="/documentoDownload/${documento.establecimiento.id}/${documento.nombre}/xml" var="urlDocumentoXml"/>
			<form id="formDocument" method="post"></form>
			<script type="text/javascript">
				$(document).ready(function() {
					var urlPdf = "${urlDocumentoPdf}";
					var urlXml = "${urlDocumentoXml}";
						
					$("#descargaDocPdf").click(function() {
						$("#formDocument").attr("action", urlPdf);
						$("#formDocument").submit();
					});
					
					$("#descargaDocXml").click(function() {
						alert("Funciona XML!");
						$("#formDocument").attr("action", urlXml);
						$("#formDocument").submit();
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

<div class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">Reenviar Documento</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>
