<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Facturación en Línea</title>
</head>
<body>
	<div class="container main-content">
		<div class="white-panel row">
			<h2>
				Facturación Electrónica
				<sec:authorize access="hasRole('ROLE_SUC')">
					<span class="text-info"> - ${fn:toUpperCase(sessionScope.establecimiento.nombre)}</span> <span class="label label-primary">@</span>
					<c:url var="urlPage" value="/buscaTicket"/>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_CORP')">
					<span class="text-info"> - Corporativo</span> <span class="label label-primary">@</span>
					<c:url var="urlPage" value="/facturaCorp"/>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<span class="text-info"> - Administración</span> <span class="label label-primary">@</span>
					<c:url var="urlPage" value="/facturaCorp"/>
				</sec:authorize>
			</h2>
			<blockquote>
				<p class="text-info">Seleccione una opción.</p>
			</blockquote>
			<hr>

			<c:if test="${error}">
				<div class="col-md-offset-3 col-md-6 alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					<spring:message code="messages.error.autorizacion.cierre"/>
					<br><br> <strong>${messageError}</strong> 
				</div>
			</c:if>
			<c:if test="${success}">
				<div class="col-md-offset-3 col-md-6 alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					<spring:message code="messages.success.autorizacion.cierre"/>
					<br><br> <strong>${messageSuccess}</strong> 
				</div>
			</c:if>
			<div class="well col-md-6 col-md-offset-3 text-center">
				<hr>
				<sec:authorize access="hasAnyRole('ROLE_SUC', 'ROLE_CORP')">
					<p>
						<a href="${urlPage}" class="btn btn-primary btn-lg" role="button">Generar
							Factura <i class="fa fa-arrow-right"></i>
						</a>
					</p>
					<hr>
					<sec:authorize access="hasRole('ROLE_SUC')">
						<p>
							<a href="<c:url value="/refacturarForm"/>" class="btn btn-success btn-lg" role="button">Re-Facturar
								<i class="fa fa-arrow-right"></i>
							</a>
						</p>
						<hr>
					</sec:authorize>
					<p>
						<a href="<c:url value="/buscarDocs"/>" class="btn btn-info btn-lg" role="button">Consultar Facturas
							<i class="fa fa-arrow-right"></i>
						</a>
					</p>
					<hr>
					<sec:authorize access="hasRole('ROLE_SUC')">
						<p>
							<button type="button" id="cierre" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#autCierre">
							Cierre <i class="fa fa-arrow-right"></i></button>
						</p>
						<hr>
					</sec:authorize>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<p>
						<a href="<c:url value="/catalogoUsuarios"/>" class="btn btn-primary btn-lg" role="button">Usuarios <i class="fa fa-user"></i>
						</a>
					</p>
					<hr>
					<p>
						<a href="<c:url value="/catalogoEstablecimiento"/>" class="btn btn-warning btn-lg" role="button">Sucursales
							<i class="fa fa-dot-circle-o"></i>
						</a>
					</p>
					<hr>
					<p>
						<a href="<c:url value="/buscaRfc"/>" class="btn btn-success btn-lg" role="button">Clientes
							<i class="fa fa-users"></i>
						</a>
					</p>
					<hr>
					<p>
						<a href="<c:url value="/establecimientoSerieFolio"/>" class="btn btn-info btn-lg" role="button">Series y Folios
							<i class="fa fa-bars"></i>
						</a>
					</p>
					<hr>
				</sec:authorize>
			</div>
		</div>
	</div>
	<div class="modal fade" id="autCierre" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" id="closeAut" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title text-primary">Autorizaci&oacute;n de Cierre</h4>
	      </div>
	      <div class="modal-body">
	        <form action="<c:url value="/cierre"/>" id="autorizacionForm" method="post" class="form-horizontal">
	        	<blockquote>
	        		<p>Proporcione un nombre de usuario y contreseña de autorizaci&oacute;n para el cierre del d&iacute;a.</p>
	        	</blockquote>
	        	<div class="form-group">
	        		<label for="fechaCierre" class="col-lg-4 control-label">Fecha Cierre: </label>
					<div class="col-lg-5">
			        	<input type="text" id="fechaCierre" name="fechaCierre" class="form-control input-sm validate[required]" 
			        		readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="usuario" class="col-lg-4 control-label">Usuario: </label>
					<div class="col-lg-5">
			        	<input type="text" id="usuario" name="usuario" class="form-control input-sm validate[required] noUpper"/>
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-lg-4 control-label">Password: </label>
					<div class="col-lg-5">					
			        	<input type="password" id="password" name="password" class="form-control input-sm validate[required]"/>
					</div>
				</div>
				<div class="form-group">
					<label for="error" class="col-lg-1 control-label"></label>
			        <div class="text-danger col-lg-10 text-center" id="error"></div>
			    </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" id="closeAut" class="btn btn-default" data-dismiss="modal">Cerrar</button>
	        <button type="button" id="autorizar" class="btn btn-primary" data-toggle="confirmation" data-original-title="" title="">Autorizar</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
	<script type="text/javascript">
			$(document).ready(function() {
				$("#autorizacionForm").validationEngine();				
				
				$("#cierre").click(function() {
					$.ajax({
						url: contextPath + "/fechaCierre?ajax=true",
						type: "POST",
						dataType: "json",
						success : function(response) {
							if(response.fecha != null && response.fecha !== "" ) {
								$("#fechaCierre").val(response.fecha);
							} else  {								
								$("#fechaCierre").val("");
							}
						},
						error : function() {
							$("#fechaCierre").val("");
						}
					});
				});
				
				$(document.body).on("click", "#closeAut", function() {
					$("#usuario").val("");
					$("#password").val("");
				});
				
				$('[data-toggle="confirmation"]').confirmation({onConfirm: function(){
					if($("#autorizacionForm").validationEngine("validate")) {
						$(".page_loader").show();
						$("#page_loader_factura_content").show();
						$("#autorizacionForm").submit();
					}
				}});
			});
	</script>
</body>
</html>
