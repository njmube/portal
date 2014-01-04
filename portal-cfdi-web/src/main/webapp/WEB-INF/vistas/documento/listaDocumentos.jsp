<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<c:if test="${!emptyList}">
	<c:choose>
		<c:when test="${not empty documentos}">
			<div class="table-responsive">
				<display:table htmlId="documentos" id="documento" name="${documentos}"
					class="table table-hover table-striped table-condensed"
					requestURI="">
					<display:column title="#" property="id" headerClass="text-primary"></display:column>
					<display:column title="Serie" property="comprobante.serie" headerClass="text-primary"></display:column>
					<display:column title="Folio" property="comprobante.folio" headerClass="text-primary" />
					<display:column title="PDF" headerClass="text-primary text-center" class="text-center">
						<a href="<c:url value="/documentoPdf/${documento.id}"/>" class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-download-alt"></span></a>
					</display:column>
					<display:column title="XML" headerClass="text-primary text-center" class="text-center">
						<a href="<c:url value="/documentoXml/${documento.id}"/>" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-download-alt"></span></a>
					</display:column>
				</display:table>
			</div>
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