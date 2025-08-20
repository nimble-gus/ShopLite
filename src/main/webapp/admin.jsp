<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>ShopLite • Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand text-danger" href="${pageContext.request.contextPath}/home">
            <i class="bi bi-shield-check"></i> ShopLite • Admin
        </a>
        <div class="ms-auto d-flex gap-2 align-items-center">
            <span class="text-muted small">
                <c:out value="${sessionScope.userEmail}"/> (ADMIN)
            </span>
            <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/home">Ver productos</a>
            <form method="post" action="${pageContext.request.contextPath}/auth/logout" class="d-inline">
                <button class="btn btn-sm btn-outline-danger">Cerrar sesión</button>
            </form>
        </div>
    </div>
</nav>

<section class="container my-5" style="max-width:720px;">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Administración de Productos</h2>
        <span class="badge bg-danger">Solo ADMIN</span>
    </div>
    
    <c:if test="${param.err=='1'}">
        <div class="alert alert-danger">
            <i class="bi bi-exclamation-triangle"></i>
            Error: Verifique que el nombre no esté vacío y el precio sea mayor a 0.
        </div>
    </c:if>
    
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="mb-3">Nuevo producto</h3>
            <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-3">
                <div class="col-12">
                    <label class="form-label">Nombre del producto</label>
                    <input class="form-control" name="name" placeholder="Ej: Teclado mecánico RGB" required>
                    <div class="form-text">Ingrese un nombre descriptivo para el producto</div>
                </div>
                <div class="col-12">
                    <label class="form-label">Precio</label>
                    <div class="input-group">
                        <span class="input-group-text">$</span>
                        <input class="form-control" name="price" type="number" step="0.01" min="0.01" placeholder="59.99" required>
                    </div>
                    <div class="form-text">El precio debe ser mayor a 0</div>
                </div>
                <div class="col-12 d-flex gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-plus-circle"></i> Crear producto
                    </button>
                    <a class="btn btn-light" href="${pageContext.request.contextPath}/home">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    
    <div class="mt-4">
        <div class="alert alert-info">
            <h5><i class="bi bi-info-circle"></i> Información</h5>
            <ul class="mb-0">
                <li>Los productos se guardan en la base de datos PostgreSQL</li>
                <li>El ID se genera automáticamente</li>
                <li>Los productos son visibles para todos los usuarios autenticados</li>
                <li>Solo los administradores pueden crear nuevos productos</li>
            </ul>
        </div>
    </div>
</section>
</body>
</html>
