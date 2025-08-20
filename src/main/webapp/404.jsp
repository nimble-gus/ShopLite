<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>404 - Página no encontrada</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center bg-light" style="min-height:100vh;">
<div class="container text-center">
    <h1 class="display-1 text-muted">404</h1>
    <h2 class="mb-3">Página no encontrada</h2>
    <p class="text-muted mb-4">La página que buscas no existe o ha sido movida.</p>
    <div class="d-flex justify-content-center gap-2">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Ir al Inicio</a>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/login.jsp">Ir a Login</a>
    </div>
</div>
</body>
</html>
