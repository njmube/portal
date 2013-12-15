<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Prueba</title>
</head>
<body>
hola
<br>
${comprobante.serie}${comprobante.folio}
<a href="<c:url value="/limpia"/>">limpia</a>
</body>
</html>