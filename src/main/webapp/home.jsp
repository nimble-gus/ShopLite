<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>ShopLite • Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand text-primary" href="${pageContext.request.contextPath}/home">ShopLite</a>
        <div class="ms-auto d-flex gap-2 align-items-center">
            <span class="text-muted small">
                <c:out value="${sessionScope.userEmail}"/> 
                (<c:out value="${sessionScope.role}"/>)
            </span>
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/admin">Nuevo producto</a>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/auth/logout" class="d-inline">
                <button class="btn btn-sm btn-outline-danger">Cerrar sesión</button>
            </form>
        </div>
    </div>
</nav>

<section class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Productos</h2>
        <div class="text-muted">
            Total: <c:out value="${total}"/> productos
        </div>
    </div>

    <!-- Lista de productos -->
    <div class="row g-3">
        <c:forEach var="p" items="${items}">
            <div class="col-md-4 col-lg-3">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${p.name}"/></h5>
                        <p class="text-primary fw-bold">$ <c:out value="${p.price}"/></p>
                        <small class="text-muted">ID: <c:out value="${p.id}"/></small>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Controles de paginación -->
    <c:if test="${total > 0}">
        <div class="d-flex justify-content-between align-items-center mt-4">
            <div>
                <c:if test="${hasPrevious}">
                    <a class="btn btn-outline-secondary" 
                       href="${pageContext.request.contextPath}/home?page=${page-1}&size=${size}">
                        « Anterior
                    </a>
                </c:if>
            </div>
            
            <div class="text-center">
                <span class="text-muted">
                    Página ${page} de ${totalPages} 
                    (${size} por página, ${total} total)
                </span>
            </div>
            
            <div>
                <c:if test="${hasNext}">
                    <a class="btn btn-outline-secondary" 
                       href="${pageContext.request.contextPath}/home?page=${page+1}&size=${size}">
                        Siguiente »
                    </a>
                </c:if>
            </div>
        </div>
        
        <!-- Navegación rápida de páginas -->
        <c:if test="${totalPages > 1}">
            <div class="d-flex justify-content-center mt-3">
                <div class="btn-group" role="group">
                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                        <c:choose>
                            <c:when test="${pageNum == page}">
                                <span class="btn btn-primary disabled">${pageNum}</span>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-outline-secondary" 
                                   href="${pageContext.request.contextPath}/home?page=${pageNum}&size=${size}">
                                    ${pageNum}
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </c:if>
    
    <!-- Mensaje si no hay productos -->
    <c:if test="${empty items}">
        <div class="text-center py-5">
            <h4 class="text-muted">No hay productos disponibles</h4>
            <p class="text-muted">Los productos se cargan desde la base de datos PostgreSQL</p>
        </div>
    </c:if>
</section>
</body>
</html>
