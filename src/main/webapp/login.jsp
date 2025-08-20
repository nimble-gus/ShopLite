<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>ShopLite • Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand text-primary" href="${pageContext.request.contextPath}/">ShopLite</a>
    </div>
</nav>

<section class="container py-5" style="max-width:720px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="mb-3">Iniciar sesión</h3>
            
            <!-- Mensaje de error -->
            <c:if test="${param.err=='1'}">
                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle"></i>
                    Credenciales inválidas
                </div>
            </c:if>
            
            <!-- Mensaje de despedida -->
            <c:if test="${param.bye=='1'}">
                <div class="alert alert-success">
                    <i class="bi bi-check-circle"></i>
                    Sesión cerrada exitosamente
                </div>
            </c:if>
            
            <form method="post" action="${pageContext.request.contextPath}/auth/login" class="row g-3">
                <div class="col-12">
                    <label class="form-label">Email</label>
                    <input class="form-control" name="email" value="admin@demo.com" required>
                </div>
                <div class="col-12">
                    <label class="form-label">Contraseña</label>
                    <input class="form-control" type="password" name="password" value="admin123" required>
                </div>
                <div class="col-12 d-flex gap-2">
                    <button class="btn btn-primary">Entrar</button>
                    <a class="btn btn-light" href="${pageContext.request.contextPath}/">Cancelar</a>
                </div>
            </form>
            
            <hr class="my-4">
            <div class="text-center">
                <small class="text-muted">
                    <strong>Usuarios de prueba:</strong><br>
                    Admin: admin@demo.com / admin123<br>
                    Usuario: user@demo.com / user123
                </small>
            </div>
        </div>
    </div>
</section>
</body>
</html>
