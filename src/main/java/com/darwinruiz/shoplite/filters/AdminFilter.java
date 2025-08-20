package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filtro de autorización para rutas /app/users/* (solo ADMIN)
 */
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        
        // Verificar si el usuario está autenticado
        boolean isAuthenticated = session != null && Boolean.TRUE.equals(session.getAttribute("auth"));
        
        if (!isAuthenticated) {
            // Si no está autenticado, redirigir a login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Verificar si tiene rol ADMIN
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            chain.doFilter(req, res);
        } else {
            // Si no es ADMIN, mostrar página 403
            request.getRequestDispatcher("/403.jsp").forward(req, res);
        }
    }
}
